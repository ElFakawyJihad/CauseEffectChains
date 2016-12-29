package bs;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bsh.EvalError;
import dd.impl.CECElement;
import dd.impl.State;

public class BeanShellTest {

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
	public void test_readFile() {
		BeanShell bs = new BeanShell();
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/test/bs/readFileExample.txt";
		
		String readedFile = bs.readFile(filePath);

		assertEquals("Bonjour. Je suis un essai.", readedFile);
	}
	
	@Test
	public void test_readFile_exception() {
		BeanShell bs = new BeanShell();
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/test/bs/iDontExist.lol";
		
		String readedFile = bs.readFile(filePath);

		assertEquals("", readedFile);
	}

	@Test
	public void test_constructor() {
		BeanShell bs = new BeanShell("FirstChallenge");
		assertEquals("FirstChallenge", bs.challengeName);
	}
	
	@Test
	public void test_initInterpreter() throws EvalError {
		BeanShell bs = new BeanShell();

		bs.initInterpreter();
		
		assertTrue(bs.interpreter != null);
		
		Object o = bs.interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN");

		assertTrue(o != null);
		
	}

	/*@Test
	public void test_getTrace() throws EvalError {
		BeanShell bs = new BeanShell("TestChallenge");
		
		List<CECElement> trace = bs.getTrace(1);
		
		assertEquals(1, trace.size());
		
		CECElement c = trace.get(0);
		
		assertEquals("33", c.getLine());
		assertEquals("2", c.getVariable());
	}*/
	
	/*@Test
	public void test_printTrace() throws EvalError {
		BeanShell bs = new BeanShell("TestChallenge");
		
		List<State> trace = bs.getTrace(1);
		
		bs.printTrace(trace);
		
		assertTrue(outContent.toString().contains("_____ TRACE BEGIN _____"));
		assertTrue(outContent.toString().contains("_____ TRACE ENDS _____"));
		assertTrue(outContent.toString().contains("Line [33]"));
		assertTrue(outContent.toString().contains("2	is the new value of \"hello\""));
	}*/
	
}
