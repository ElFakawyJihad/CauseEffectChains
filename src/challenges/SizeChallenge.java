package challenges;

import java.util.Arrays;
import java.util.List;

import dd.Challenge;

public class SizeChallenge implements Challenge<String> {

	@Override
	public Class<? extends String> getInputFormat() {
		return String.class;
	}

	@Override
	public List<String> getInputs() {
		return Arrays.asList(new String[]{"titoto", null});
	}

	@Override
	public Object doIt(String input) {
		return null;
	}

	@Override
	public void challenge(String input) {		
		String tmp = "titi";
		
		int size = tmp.length();//NO SONAR
		
		for (int i = 0; i < 2; i++) {
			size=i;//NO SONAR
		}
		
		tmp = input;
		
		size = tmp.length();//NO SONAR
	}

}
