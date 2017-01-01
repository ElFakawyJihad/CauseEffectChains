package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class VariableTest {

	@Test
	public void toStringTest() {
		int i=7;
		Variable v = new Variable("i", i, 42);
		
		assertEquals("{42,i,7}", v.toString());
	}

}
