package dd.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TraceTest {

	@Test
	public void toStringTest() {
		List<State> s = new ArrayList<State>();
		int i=2, j=9;
		s.add(new State(1, 6, "i", i, true));
		s.add(new State(2, 7, "j", j, true));
		
		Trace t = new Trace(s, true);
		
		assertEquals("[]\n[]\n", t.toString());
	}

}
