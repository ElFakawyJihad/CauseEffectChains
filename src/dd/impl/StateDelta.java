package dd.impl;

import java.util.ArrayList;
import java.util.List;

public class StateDelta {
	public List<Delta> deltas;		
	
	public StateDelta(State state1, State state2) {
		deltas = new ArrayList<Delta>();
	}
	
	public List<Delta> getDeltas() {
		
		return deltas;
	}
	
}
