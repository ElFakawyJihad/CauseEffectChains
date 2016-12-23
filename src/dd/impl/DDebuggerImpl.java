package dd.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bs.BeanShell;
import bs.BeanShellTest;
import bsh.EvalError;
import dd.Challenge;
import dd.DDebugger;

public class DDebuggerImpl implements DDebugger<Integer> {
	
	@Override
	public CEC debug(Challenge<Integer> c) {
		for (Integer input: c.getInputs()) {
				internalDebug(input, c.getClass().getSimpleName());
				//c.challenge(input);
		}
		return new CEC();
	}

	private void internalDebug(Integer input, String challengeName) {
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
