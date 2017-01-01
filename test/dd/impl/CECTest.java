package dd.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dd.ChainElement;

public class CECTest {

	@Test
	public void getChainTest() {
		List<ChainElement> l = new ArrayList<ChainElement>();
		l.add(new CECElement("5", 9, ""));
		l.add(new CECElement("6", "Hello", ""));
		
		CEC c = new CEC(l);
		
		assertEquals(l, c.getChain());
	}
	
	@Test
	public void toStringTest() {
		List<ChainElement> l = new ArrayList<ChainElement>();
		l.add(new CECElement("5", 9, ""));
		l.add(new CECElement("6", "Hello", ""));
		
		CEC c = new CEC(l);
		
		assertEquals("_____ CAUSE EFFECT CHAIN _____\nLine [5] :  = 9\nLine [6] :  = Hello\n", c.toString());
	}

}
