package dd.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileNotFoundRuntimeExceptionTest {

	@Test
	public void fileNotFoundRuntimeExceptionTest() {
		RuntimeException e = new RuntimeException();
		FileNotFoundRuntimeException e2 = new FileNotFoundRuntimeException(e);
		
		assertEquals(null, e2.getMessage());
	}

}
