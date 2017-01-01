package challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dd.Challenge;

/**
 * USE ONLY FOR UNIT TESTS !
 * 
 * @author Quentin
 *
 */
public class TestChallenge implements Challenge<Integer> {
	@Override
	public Class getInputFormat() {
		return Integer.class;
	}

	@Override
	public List<Integer> getInputs() {
		return Arrays.asList(new Integer[]{1, 5});
	}

	@Override
	public Object doIt(Integer input) {
		return null;
	}

	@Override
	public void challenge(Integer input) {
		int hello = input * 2;
		assert (hello > 4);
	}
}
