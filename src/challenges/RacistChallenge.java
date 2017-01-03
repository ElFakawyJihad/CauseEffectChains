package challenges;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import dd.Challenge;

/**
 * The racist challenge. Throw exception when a given color has no blue in it.
 * 
 * @author Quentin
 *
 */
public class RacistChallenge implements Challenge<Color> {

	@Override
	public Class<Color> getInputFormat() {
		return Color.class;
	}

	@Override
	public List<Color> getInputs() {
		return Arrays.asList(new Color[] { Color.WHITE, Color.BLUE, null, Color.CYAN, Color.RED, Color.DARK_GRAY, Color.PINK, Color.GREEN});
	}

	@Override
	public Object doIt(Color input) {
		return null;
	}

	@Override
	public void challenge(Color input) {		
		if (input != null) {
			int blueLevel = input.getBlue();//NOSONAR
			assert blueLevel != 0;
		}
	}

}
