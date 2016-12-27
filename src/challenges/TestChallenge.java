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
public class TestChallenge implements Challenge<String> {
	@Override
	public Class getInputFormat() {
		return Integer.class;
	}

	@Override
	public List<String> getInputs() {
		return Arrays.asList(new String[]{"toto", null});
	}

	@Override
	public Object doIt(String input) {
		return null;
	}

	@Override
	public void challenge(String input) {
		int size = input.length();
		
	}
}
