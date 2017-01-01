package challenges;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.Test;

public class RacistChallengeTest {

	@Test
	public void test() {
		RacistChallenge c = new RacistChallenge();
		c.challenge(Color.WHITE);
		
		assertEquals(Color.class, c.getInputFormat());
		assertEquals(Arrays.asList(new Color[]{Color.WHITE, Color.BLUE, Color.CYAN, Color.RED}), c.getInputs());
	}

}
