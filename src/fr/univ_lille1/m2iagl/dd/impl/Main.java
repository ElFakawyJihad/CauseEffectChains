package fr.univ_lille1.m2iagl.dd.impl;

import fr.univ_lille1.m2iagl.dd.DDebugger;

public class Main {

	public static void main(String[] args){
		DDebugger d = new DummyDDebugger();
		System.out.println(d.debug(new DummyChallenge()));
	}
}
