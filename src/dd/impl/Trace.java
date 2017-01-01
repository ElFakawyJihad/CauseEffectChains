package dd.impl;

import java.util.ArrayList;
import java.util.List;

import bs.ChallengeException;

/**
 * Trace represente l'enchainement des etats d'un programme lors de son execution
 * 
 * @author Admin
 *
 */
public class Trace {
	public List<State> states;
	public boolean fail;
	public ChallengeException exception;
	public String nameClass;
	public String nameMethod;
	public int lineMethod;
	public String nameInput;
	
	public Trace(List<State> s, boolean fail) {
		this.states=s;
		this.fail=fail;
	}
	
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(State st: states){
			s.append(st.toString()+"\n");
		}
		return s.toString();
	}
}
