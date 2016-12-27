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
		for (Object input: c.getInputs()) {
				System.out.println("Execution avec input : "+input);
				internalDebug(input, c.getClass().getSimpleName());
				//c.challenge(input);
		}
		return new CEC();
	}

	private void internalDebug(Object input, String challengeName) {
		BeanShell beanshell = new BeanShell(challengeName);
		
		List<CECElement> trace = new ArrayList<CECElement>();

		try {
			trace = beanshell.getTrace(input);
		} catch (EvalError e) {
			e.printStackTrace();
		}
		
		//C'est temporaire, jusqu'à ce qu'on fasse une vraie chaine de cause à effet
		beanshell.printTrace(trace);
	}

}
