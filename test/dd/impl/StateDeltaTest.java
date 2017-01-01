package dd.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StateDeltaTest {
	
	private StateDelta initStateDelta() {
		List<State> sFail = new ArrayList<State>();
		int i=2, j=9;
		sFail.add(new State(1, 6, "i", i, true));
		sFail.add(new State(2, 7, "j", j, true));
		
		List<State> sOk = new ArrayList<State>();
		i=2; j=10;
		sOk.add(new State(1, 6, "i", i, true));
		sOk.add(new State(2, 7, "j", j, true));
		
		Trace traceFail = new Trace(sFail, true);
		Trace traceOk = new Trace(sOk, false);
		return new StateDelta(traceFail, traceOk);
	}

	@Test
	public void getDeltasTest() {
		StateDelta s = initStateDelta();
		
		List<Delta> ss = s.getDeltas();
		
		assertEquals(0, ss.size());
	}
	
	@Test
	public void compareTest() {
		StateDelta s = initStateDelta();
		
		List<Delta> ss = s.compare( new State(1, 6, "i", 9, true), 
									new State(2, 6, "i", 10, true));
		
		assertEquals(0, ss.size());
	}

}
