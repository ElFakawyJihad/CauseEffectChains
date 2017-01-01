package dd.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import bs.BeanShell;
import bsh.EvalError;
import dd.ChainElement;
import dd.Challenge;
import dd.DDebugger;

public class DDebuggerImpl implements DDebugger<Object> {
	
	@Override
	public CEC debug(Challenge<Object> c) {
		//recup les trace de chaque input
		ArrayList<Trace> traces=new ArrayList<Trace>();
		for (Object input: c.getInputs()) {
				System.out.println("_____ Execution avec input : "+input);
				Trace trace;
				try {
					trace = getTrace(input, c.getClass().getSimpleName());
					traces.add( trace );
				} catch (FileNotFoundException e) {
					throw new FileNotFoundRuntimeException();
				}
				//c.challenge(input);
		}
		
		//recup une trace fail et une trace ok
		Trace traceFail=null;
		Trace traceOk=null;
		for(Trace t : traces){
			if(t.fail){
				traceFail=t;
			}else{
				traceOk=t;
			}
		}
		
		if(traceFail==null){
			System.out.println("Aucune trace fail");
			return null;
		}
		if(traceOk==null){
			System.out.println("Aucune trace Ok");
			return null;
		}
		System.out.println("___ TRACE FAIL STATES ___");
		System.out.print(traceFail);
		System.out.println("___ TRACE OK STATES ___");
		System.out.print(traceOk);
		
		//on recup les differences entre les traces
		StateDelta stateDelta =  new StateDelta(traceFail, traceOk);
		List<Delta> deltas  = stateDelta.getDeltas();
		
		//on cree la chaine
		CEC cec = getCEC(deltas,traceFail);
		return cec;
	}

	/**
	 * execute la methode avec un parametre
	 * @param input
	 * @param challengeName
	 * @return
	 * @throws FileNotFoundException 
	 */
	private Trace getTrace(Object input, String challengeName) throws FileNotFoundException {
		BeanShell beanshell = new BeanShell(challengeName);
		Trace trace =null;

		try {
			trace = beanshell.getTrace(input);
		} catch (EvalError e) {
			e.printStackTrace();
		}

		beanshell.printTrace(trace.states);
		return trace;
	}
	
	/**
	 * Retourne la chaine de cause a effet a partir des deltas
	 * @param deltas
	 * @param traceF
	 * @return
	 */
	private CEC getCEC(List<Delta> deltas,Trace traceF){
		ArrayList<ChainElement> ce =  new ArrayList<>();
		
		for(Delta d:deltas){
			ce.add(new CECElement(d.line+"", d.valueFail, d.nameVariable));
		}
		//exception a la fin
		ce.add(new CECElement(
				traceF.exception.lineNumber+"",
				traceF.exception.message,
				traceF.exception.textCause
				));
		
		return new CEC(ce);
	}

}
