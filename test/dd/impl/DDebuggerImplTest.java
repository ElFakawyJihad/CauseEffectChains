package dd.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import challenges.*;
import dd.CauseEffectChain;
import dd.DDebugger;

public class DDebuggerImplTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void deltaDebuggerTest() {		
		DDebugger dd = new DDebuggerImpl();
		
		CauseEffectChain cec = dd.debug(new TestChallenge());
		
		assertEquals("_____ CAUSE EFFECT CHAIN _____\nLine [32] : input = 1\nLine [33] : hello = 2\nLine [34] : throw new AssertionError ( ) ;  = Assertion Exception\n", cec.toString());
	}

}
