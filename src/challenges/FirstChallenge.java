package challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dd.Challenge;

public class FirstChallenge implements Challenge<Integer> {
	@Override
	public Class getInputFormat() {
		return Integer.class;
	}

	@Override
	public List<Integer> getInputs() {
		return Arrays.asList(new Integer[]{5, 10, 15});
	}

	@Override
	public Object doIt(Integer input) {
		return null;
	}

	@Override
	public void challenge(Integer input) {
		int bonjour = 5;
		input += 10; 
		input /= 2;
		
		List temp = new ArrayList();
		temp.add(1);
		temp.add(3);
		temp.add(5);		
		
		for (int i = 0; i < 5; i++) {
			input += i;
		}
		
		bonjour += 1;
	}
}
