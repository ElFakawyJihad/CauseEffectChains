package dd.impl;

import bsh.EvalError;

public class EvalErrorRuntimeException extends RuntimeException {

	public EvalErrorRuntimeException(EvalError e) {
		System.out.println(e);
	}

}
