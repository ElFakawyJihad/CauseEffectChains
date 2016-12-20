package dd.impl;

import challenges.FirstChallenge;
import dd.DDebugger;

public class Main {

	public static void main(String[] args) {
		DDebugger d = new DDebuggerImpl();
		System.out.println(d.debug(new FirstChallenge()));
	}
}
