package parser;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import bs.BeanShell;

public class ChallengeVisitorTest {

	@Test
	public void test_visit() {
		BeanShell bs = new BeanShell("TestChallenge");
		
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/TestChallenge.java";
		
		CompilationUnit cu = JavaParser.parse(bs.readFile(filePath));

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		
		assertEquals("input", visitor.inputName);
		assertTrue(visitor.challengeMethod.contains("@Override"));
		assertTrue(visitor.challengeMethod.contains("public void challenge(Integer input)"));
		assertTrue(visitor.challengeMethod.contains("int hello = input * 2;"));
		
		assertEquals(1, visitor.nodeList.size());
		
		assertEquals("int hello = input * 2;", visitor.nodeList.get(0).toString());

	}

}
