package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CECElementTest {

	@Test
	public void getLineTest() {
		int i=5;
		CECElement e = new CECElement("7", i, "Test");
		
		assertEquals("7", e.getLine());
	}

	@Test
	public void getVariableTest() {
		int i=5;
		CECElement e = new CECElement("7", i, "Test");
		
		assertEquals("5", e.getVariable());
	}
	
	@Test
	public void getVariableNullTest() {
		int i=5;
		CECElement e = new CECElement("7", null, "Test");
		
		assertEquals(null, e.getVariable());
	}
	
	@Test
	public void getDescriptionTest() {
		int i=5;
		CECElement e = new CECElement("7", null, "Test");
		
		assertEquals("Test", e.getDescription());
	}
	
	@Test
	public void setLineTest() {
		int i=5;
		CECElement e = new CECElement("7", i, "Test");
		
		assertEquals("7", e.getLine());
		
		e.setLine("18");
		
		assertEquals("18", e.getLine());
	}
	
}
