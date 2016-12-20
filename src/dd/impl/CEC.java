package dd.impl;

import java.util.ArrayList;
import java.util.List;

import dd.CauseEffectChain;
import dd.ChainElement;

public class CEC implements CauseEffectChain {

	public List<ChainElement> chain;

	public CEC() {
		chain = new ArrayList<ChainElement>();
	}

	@Override
	public List<ChainElement> getChain() {
		return chain;
	}

}
