package dd.impl;

import challenges.*;
import dd.CauseEffectChain;
import dd.DDebugger;

public class Main {

	public static void main(String[] args) {
		DDebugger d = new DDebuggerImpl();
		CauseEffectChain cec = d.debug(new FirstChallenge());
		//CauseEffectChain cec = d.debug(new TestChallenge());
		System.out.println(cec);
	}
}
