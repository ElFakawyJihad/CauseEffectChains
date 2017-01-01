package parser;

import org.junit.Test;
import static org.junit.Assert.*;

import com.github.javaparser.ast.stmt.Statement;

public class StatementFactoryTest {
	@Test
	public void test_addInputToList_no_iteration_42() {
		Statement s = StatementFactory.addInputToList("666", "42", "reponse", "");
		assertTrue(s.toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State"));
		assertTrue(s.toString().contains("42"));
		assertTrue(s.toString().contains("666"));
		assertTrue(s.toString().contains("reponse"));
	}	
	@Test
	public void test_addInputToList_no_iteration_666() {
		Statement s = StatementFactory.addInputToList("666", "42", "reponse", "");
		assertTrue(s.toString().contains("666"));
		assertTrue(s.toString().contains("reponse"));
	}
	@Test
	public void test_addInputToList_no_iteration_reponse() {
		Statement s = StatementFactory.addInputToList("666", "42", "reponse", "");
		assertTrue(s.toString().contains("reponse"));
	}
	
	@Test
	public void test_addInputToList_with_iteration() {
		Statement s = StatementFactory.addInputToList("666", "| Loop iterations :", "\"i=\" + i + \";\"","");
		assertFalse(s.toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement"));
		assertTrue(s.toString().contains("| Loop iterations :"));
		assertTrue(s.toString().contains("\"i=\" + i + \";\""));
	}
	@Test
	public void test_addInputToList_with_iteration_Loop_Iterations() {
		Statement s = StatementFactory.addInputToList("666", "| Loop iterations :", "\"i=\" + i + \";\"","");
		assertTrue(s.toString().contains("| Loop iterations :"));
	}
	@Test
	public void test_addInputToList_with_iteration_66() {
		Statement s = StatementFactory.addInputToList("666", "| Loop iterations :", "\"i=\" + i + \";\"","");
		assertTrue(s.toString().contains("666"));
	}
	
	@Test
	public void test_addInputToList_with_iteration_i_Iterations() {
		Statement s = StatementFactory.addInputToList("666", "| Loop iterations :", "\"i=\" + i + \";\"","");
		assertTrue(s.toString().contains("\"i=\" + i + \";\""));
	}
}
