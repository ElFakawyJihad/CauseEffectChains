package dd.impl;

import java.util.HashMap;

public class Delta {
	public String nameVariable;
	public Object valueFail;
	public Object valueOk;
	public int line;
	
	public Delta(String name,Object valueF,Object valueO,int line) {
		nameVariable=name;
		valueFail=valueF;
		valueOk=valueO;
		this.line=line;
	}

	 
	public String toString(){
		return "delta : "+line+","+nameVariable+","+valueFail;
	}
	

}
