package dd.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bs.BeanShell;
import bs.BeanShellTest;
import bsh.EvalError;
import dd.Challenge;
import dd.DDebugger;

public class DDebuggerImpl implements DDebugger<Object> {
	
	@Override
	public CEC debug(Challenge<Object> c) {
		//recup les trace de chaque input
		ArrayList<Trace> traces=new ArrayList<Trace>();
		for (Object input: c.getInputs()) {
				System.out.println("_____ Execution avec input : "+input);
				Trace trace =  getTrace(input, c.getClass().getSimpleName());
				traces.add( trace );
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
		/*System.out.println("___ traceF"+traceFail.states.size());
		for(State s: traceFail.states){
			System.out.println(s);
		}
		System.out.println("___ traceO"+traceOk.states.size());
		for(State s: traceOk.states){
			System.out.println(s);
		}*/
		//on recup les differences
		StateDelta stateDelta =  new StateDelta(traceFail, traceOk);
		List<Delta> deltas  = stateDelta.getDeltas();
		//deltas.add
		System.out.println("___ listdelta");
		for(Delta d: deltas){
			System.out.println(d);
		}
		//on cree la chaine
		CEC cec = getCEC(deltas);
		return cec;
	}

	private Trace getTrace(Object input, String challengeName) {
		BeanShell beanshell = new BeanShell(challengeName);
		Trace trace =null;

		try {
			trace = beanshell.getTrace(input);
		} catch (EvalError e) {
			e.printStackTrace();
		}

		//C'est temporaire, jusqu'à ce qu'on fasse une vraie chaine de cause à effet
		beanshell.printTrace(trace.states);
		return trace;
	}
	
	private CEC getCEC(List<Delta> deltas){
		//for()
		
		return new CEC();
	}

}
