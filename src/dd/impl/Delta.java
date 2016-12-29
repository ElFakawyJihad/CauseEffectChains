package dd.impl;

import java.util.HashMap;

public class Delta {
	public String nameVariable;
	public Object valueFail;
	public Object valueOk;
	
	public Delta(String name,Object valueF,Object valueO) {
		nameVariable=name;
		valueFail=valueF;
		valueOk=valueO;
	}

	 
	public String toString(){
		return nameVariable+","+valueFail;
	}
	

}
