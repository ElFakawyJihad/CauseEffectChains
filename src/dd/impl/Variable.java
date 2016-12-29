package dd.impl;

/**
 * Represente une variable 
 * @author Admin
 *
 */
public class Variable {

	public String name;
	public Object value;
	public int line;
	
	public Variable(String nameVar, Object valueVar,int line) {
		this.line=line;
		this.name=nameVar;
		value=valueVar;
	}
	
	@Override
	public String toString() {
		return "{"+line+','+name+","+value+"}";
	}

}
