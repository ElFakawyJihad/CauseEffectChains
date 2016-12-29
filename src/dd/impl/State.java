package dd.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * State represente l'etat du programme a un instant t : ses variables et leurs valeurs
 * @author remi kruczek
 *
 */
public class State {
	public int stateNumber;
	public int lineNumber;
	public List<Variable> variables=new ArrayList<Variable>();
	public Variable varCurrent;
	public boolean addVar;
	
	public State(int nb, int line, String nameVar, Object valueVar, boolean add) {
		stateNumber= nb;
		lineNumber=line;
		varCurrent = new Variable(nameVar, valueVar);
		addVar=add;
	}
	
	public String toString(){
		return varCurrent.toString()+","+variables.toString();
	}
}
