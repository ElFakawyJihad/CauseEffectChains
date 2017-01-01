package bs;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bsh.EvalError;
import dd.impl.CECElement;
import dd.impl.State;
import dd.impl.Trace;

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
	public void test_readFile() throws FileNotFoundException {
		BeanShell bs = new BeanShell();
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/test/bs/readFileExample.txt";
		
		String readedFile = bs.readFile(filePath);

		assertEquals("Bonjour. Je suis un essai.", readedFile);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void test_readFile_exception() throws FileNotFoundException {
		BeanShell bs = new BeanShell();
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/test/bs/iDontExist.lol";
		
		bs.readFile(filePath);
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

	@Test
	public void test_getTrace() throws EvalError, FileNotFoundException {
		BeanShell bs = new BeanShell("TestChallenge");
		
		Trace trace = bs.getTrace(1);
		
		assertEquals(2, trace.states.size());
		
		State s = trace.states.get(0);
		State s2 = trace.states.get(1);
		
		assertEquals(32, s.lineNumber);
		assertEquals(1, s.varCurrent.value);
		
		assertEquals(33, s2.lineNumber);
		assertEquals(2, s2.varCurrent.value);
	}
	
	@Test
	public void test_printTrace() throws EvalError, FileNotFoundException {
		BeanShell bs = new BeanShell("TestChallenge");
		
		Trace trace = bs.getTrace(1);
		
		bs.printTrace(trace.states);
		
		assertTrue(outContent.toString().contains("_____ TRACE BEGIN _____"));
		assertTrue(outContent.toString().contains("_____ TRACE ENDS _____"));
		assertTrue(outContent.toString().contains("[33]"));
		assertTrue(outContent.toString().contains("hello"));
	}
	
}
