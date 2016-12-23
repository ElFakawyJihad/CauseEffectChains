package parser;

import org.junit.Test;
import static org.junit.Assert.*;

import com.github.javaparser.ast.stmt.Statement;

public class StatementFactoryTest {
	@Test
	public void test_addInputToList_no_iteration() {
		Statement s = StatementFactory.addInputToList("666", "42", "reponse", "");
		
		assertTrue(s.toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement"));
		assertTrue(s.toString().contains("42"));
		assertTrue(s.toString().contains("666"));
		assertTrue(s.toString().contains("reponse"));
	}
	
	@Test
	public void test_addInputToList_with_iteration() {
		Statement s = StatementFactory.addInputToList("666", "42", "reponse", "i;j");
		
		assertTrue(s.toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement"));
		assertTrue(s.toString().contains("42"));
		assertTrue(s.toString().contains("666"));
		assertTrue(s.toString().contains("| Loop iterations :"));
		assertTrue(s.toString().contains("\"i=\" + i + \";\""));
		assertTrue(s.toString().contains("\"j=\" + j + \";\""));
	}
}
