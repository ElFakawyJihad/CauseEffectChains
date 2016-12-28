package parser;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import bs.BeanShell;

public class ChallengeVisitorTest {

	@Test 
	public void test_visit_inputName(){
		BeanShell bs = new BeanShell("TestChallenge");
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));
		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		assertEquals("input", visitor.inputName);
	}
	@Test 
	public void test_visit_override(){
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		assertTrue(visitor.challengeMethod.contains("@Override"));
	}
	@Test 
	public void test_visit_Method(){
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		System.out.println(visitor.challengeMethod);
		assertTrue(visitor.challengeMethod.contains("public void challenge(String input)"));
	}
	@Test 
	public void test_visit_variable(){
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		assertTrue(visitor.challengeMethod.contains("int size = input.length();"));
	}
	@Test 
	public void test_visit_size_Node_List(){
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		assertEquals(1, visitor.nodeList.size());
	}
	
	@Test
	public void test_verif_first_element_List(){
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		assertEquals("int size = input.length();", visitor.nodeList.get(0).toString());
		
	}

}
