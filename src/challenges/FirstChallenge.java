package fr.univ_lille1.m2iagl.dd.impl;

import fr.univ_lille1.m2iagl.dd.Challenge;

import java.util.Arrays;
import java.util.List;

public class DummyChallenge implements Challenge {
	public Class getInputFormat() {
		return String.class;
	}

	public List getInputs() {
		return Arrays.asList(new String[]{"foo", "bar"});
	}

	public boolean oracle(Object input) {
		return false;
	}

	public String getJavaProgram() {
		return "foo";
	}
}
