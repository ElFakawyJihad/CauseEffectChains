package bs;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChallengeExceptionTest {

	@Test
	public void ChallengeException() {
		ChallengeException c = new ChallengeException(1, "Pas de challenge trouv�", "");
	
		assertEquals(1, c.lineNumber);
		assertEquals("Pas de challenge trouv�", c.textCause);
		assertEquals("", c.message);
	}

}
