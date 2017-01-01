package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeltaTest {

	@Test
	public void toStringTest() {
		int i=7;
		int j=5;
		Delta d = new Delta("Delta-plane", i, j, 42);
		
		assertEquals("delta : 42,Delta-plane,7", d.toString());
	}

}
