import java.io.IOException;
import java.util.List;

import bsh.EvalError;

public class Main {

	public static void main(String[] args) throws EvalError, IOException {
		// TODO Auto-generated method stub
		BeanShell beanshell = new BeanShell();
		List<CECElement> trace = beanshell.getTrace(5);
		beanshell.printTrace(trace);
	}

}
