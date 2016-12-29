package challenges;

import java.util.Arrays;
import java.util.List;

import dd.Challenge;

public class SizeChallenge implements Challenge<String> {

	@Override
	public Class<? extends String> getInputFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getInputs() {
		return Arrays.asList(new String[]{"titoto", null});
	}

	@Override
	public Object doIt(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void challenge(String input) {
		String tmp = "titi";
		
		int size=tmp.length();
		
		tmp =input;
		
		size = tmp.length();
	}

}
