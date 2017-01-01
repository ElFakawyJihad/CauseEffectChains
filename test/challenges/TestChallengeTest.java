package challenges;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TestChallengeTest {

	@Test
	public void test() {
		TestChallenge c = new TestChallenge();
		c.challenge(5);
		
		assertEquals(Integer.class, c.getInputFormat());
		assertEquals(Arrays.asList(new Integer[]{1, 5}), c.getInputs());
	}

}
