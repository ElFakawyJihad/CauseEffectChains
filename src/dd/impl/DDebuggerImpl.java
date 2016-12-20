package fr.univ_lille1.m2iagl.dd.impl;

import fr.univ_lille1.m2iagl.dd.CauseEffectChain;
import fr.univ_lille1.m2iagl.dd.Challenge;
import fr.univ_lille1.m2iagl.dd.DDebugger;

public class DummyDDebugger implements DDebugger<String> {
	public DummyCauseEffectChain debug(Challenge<String> c) {
		for (String input: c.getInputs()) {
			// run 4 times
			for (int i = 0; i<4; i++) {
				// instrument & modify
				internalDebug(c.getJavaProgram(), input);
				// reason about execution
				c.oracle(input);
			}
		}
		return new DummyCauseEffectChain();
	}

	private void internalDebug(String javaProgram, String input) {
		// a dummy debugger do thing
	}

}
