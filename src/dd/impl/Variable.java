package dd.impl;

/**
 * Represente une variable 
 * @author Admin
 *
 */
public class Variable {

	public String name;
	public Object value;
	
	public Variable(String nameVar, Object valueVar) {
		this.name=nameVar;
		value=valueVar;
	}
	
	@Override
	public String toString() {
		return name+","+value;
	}

}
