package dd.impl;

import java.util.ArrayList;
import java.util.List;

import dd.CauseEffectChain;
import dd.ChainElement;

public class CEC implements CauseEffectChain {

	public List<ChainElement> chain;

	public CEC(List<ChainElement> list) {
		chain = list;
	}

	@Override
	public List<ChainElement> getChain() {
		return chain;
	}
	
	public String toString(){
		String s ="_____ CAUSE EFFECT CHAIN _____\n";
		for(ChainElement c : chain){
			s=s+"Line ["+c.getLine()+"] : "+c.getDescription()+" = "+c.getVariable()+"\n";
		}
		return s;
	}

}
