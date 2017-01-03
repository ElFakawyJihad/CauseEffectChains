package challenges;

import java.util.Arrays;
import java.util.List;

import dd.Challenge;

public class ParseChallenge  implements Challenge<String> {

	@Override
	public Class<? extends String> getInputFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getInputs() {
		// TODO Auto-generated method stub
		return Arrays.asList(new String[]{"titoto", "10"});
	}

	@Override
	public Object doIt(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void challenge(String input) {
		int number = Integer.parseInt(input);
		if(number==10){
			String s = "ok";
		}
		
	}

	
}
