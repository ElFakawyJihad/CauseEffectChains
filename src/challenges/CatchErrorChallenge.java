package challenges;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import dd.Challenge;

/**
 * The catch error challenge. There is a catch for error in case
 * input strings are an error source. But the default value is null-
 * initialized so it causes an error in the catch.
 * 
 * @author Quentin
 *
 */
public class CatchErrorChallenge implements Challenge<String> {

	@Override
	public Class<String> getInputFormat() {
		return String.class;
	}

	@Override
	public List<String> getInputs() {
		return Arrays.asList(new String[] {"I", "hope", "you", "don't", "get", "points :)", null});
	}

	@Override
	public Object doIt(String input) {
		return null;
	}

	@Override
	public void challenge(String string) {
		String defaultValue = null;
		int size=0;
		
		try {
			size = string.length();
		} catch(Exception e) {
			defaultValue.length();
		}
		
		assert (size!=0); 
	}

}
