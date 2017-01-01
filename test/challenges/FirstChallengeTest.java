package challenges;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Test;

public class FirstChallengeTest {

	@Test(expected=ArithmeticException.class)
	public void testChallenge() {
		FirstChallenge c = new FirstChallenge();
		
		assertEquals(Integer.class, c.getInputFormat());
		assertEquals(Arrays.asList(new Integer[]{5, 10, 15}), c.getInputs());
		
		c.challenge(10);
	}

}
