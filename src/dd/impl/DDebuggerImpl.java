package dd.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bs.BeanShell;
import bsh.EvalError;
import dd.CauseEffectChain;
import dd.Challenge;
import dd.DDebugger;

public class DDebuggerImpl implements DDebugger<Integer> {
	
	@Override
	public CEC debug(Challenge<Integer> c) {
		for (Integer input: c.getInputs()) {
			//for (int i = 0; i<4; i++) {
				internalDebug(input, c.getClass().getSimpleName());
				c.challenge(input);
			//}
		}
		return new CEC();
	}

	private void internalDebug(Integer input, String challengeName) {
		BeanShell beanshell = new BeanShell(challengeName);
		
		List<CECElement> trace = new ArrayList<CECElement>();
		
		try {
			trace = beanshell.getTrace(input);
		} catch (EvalError | IOException e) {
			//e.printStackTrace();
			System.err.println("A CRASH HAS HAPPEN IN THE INTERPRETER.");
		}
		
		beanshell.printTrace(trace);
	}

}
