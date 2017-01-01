package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import bsh.EvalError;

public class EvalErrorRuntimeExceptionTest {

	@Test
	public void fileNotFoundRuntimeExceptionTest() {
		EvalError e = new EvalError(null, null, null);
		EvalErrorRuntimeException e2 = new EvalErrorRuntimeException(e);
		
		assertEquals(null, e2.getMessage());
	}

}
