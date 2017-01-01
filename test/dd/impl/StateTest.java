package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class StateTest {

	@Test
	public void toString_test() {
		int i=5;
		State s = new State(3, 42, "i", i, true);
		
		assertEquals("[]", s.toString());
	}

}
