package challenges;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.Test;

public class SizeChallengeTest {

	@Test
	public void test() {
		SizeChallenge c = new SizeChallenge();
		c.challenge("Hello");
		
		assertEquals(String.class, c.getInputFormat());
		assertEquals(Arrays.asList(new String[]{"titoto", null}), c.getInputs());
	}

}
