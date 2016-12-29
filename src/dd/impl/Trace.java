package dd.impl;

import java.util.ArrayList;
import java.util.List;

import bs.ChallengeException;

/**
 * Trace represente les etats d'un programme lors de son execution
 * 
 * @author Admin
 *
 */
public class Trace {
	public List<State> states;
	public boolean fail;
	public ChallengeException exception;
	
	public Trace(List<State> s, boolean fail) {
		this.states=s;
		this.fail=fail;
	}
}
