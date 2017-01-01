package parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;

import bsh.EvalError;

public class NodeHandlerTest {

	@Test
	public void test_expressionStmtHandler() {
		Node e = JavaParser.parseStatement("int hello = input * 2;");
	
		List<Node> nodes = NodeHandler.expressionStmtHandler(e, "");
	
		assertEquals(2, nodes.size());
		assertTrue(nodes.get(1).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(1).toString().contains("hello"));
	}
	
	@Test
	public void test_loopStmtHandler_for() throws EvalError {
		Node e = JavaParser.parseStatement("for(int i=0; i<10; i++) {hello++;}");
	
		List<Node> nodes = NodeHandler.loopStmtHandler(e, "");
	
		assertEquals(2, nodes.size());
		assertEquals(4, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_loopStmtHandler_foreach() throws EvalError {
		Node e = JavaParser.parseStatement("for(int i : ilist) {hello++;}");
	
		List<Node> nodes = NodeHandler.loopStmtHandler(e, "");
	
		assertEquals(2, nodes.size());
		assertEquals(3, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_ifStmtHandler() throws EvalError {
		Node e = JavaParser.parseStatement("if(i > 15) { hello++; } else { hello--; }");
	
		List<Node> nodes = NodeHandler.ifStmtHandler(e, "");
	
		assertEquals(1, nodes.size());
		assertEquals(3, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_tryStmtHandler() throws EvalError {
		Node e = JavaParser.parseStatement("try{ hello++; } catch (Exception e) { hello--; }");
	
		List<Node> nodes = NodeHandler.tryStmtHandler(e, "");
	
		assertEquals(1, nodes.size());
		assertEquals(2, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_switchStmtHandler() throws EvalError {
		Node e = JavaParser.parseStatement("switch(i){case 0: hello++;break; case 1: hello--;break; default:hello++;break;}");
	
		List<Node> nodes = NodeHandler.switchStmtHandler(e, "");
	
		assertEquals(1, nodes.size());
		assertEquals(4, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_switchStmtHandler_only_default() throws EvalError {
		Node e = JavaParser.parseStatement("switch(i){default:hello++;break;}");
	
		List<Node> nodes = NodeHandler.switchStmtHandler(e, "");
	
		assertEquals(1, nodes.size());
		assertEquals(2, nodes.get(0).getChildNodes().size());
		assertTrue(nodes.get(0).toString().contains("DEBUG_CAUSE_EFFECT_CHAIN.add(new State("));
		assertTrue(nodes.get(0).toString().contains("hello"));
	}
	
	@Test
	public void test_handleSubExpression_UnaryExpr() throws EvalError {
		Expression e = JavaParser.parseExpression("hello++");
		
		String name = NodeHandler.handleSubExpression(e);
	
		assertEquals("hello", name);
	}
	
	@Test
	public void test_handleSubExpression_AssignExpr() throws EvalError {
		Expression e = JavaParser.parseExpression("input = 5;");
		
		String name = NodeHandler.handleSubExpression(e);
	
		assertEquals("input", name);
	}
	
	@Test
	public void test_handleSubExpression_MethodCallExpr() throws EvalError {
		Expression e = JavaParser.parseExpression("hello()");
		
		String name = NodeHandler.handleSubExpression(e);
	
		assertEquals("hello", name);
	}
	
}
