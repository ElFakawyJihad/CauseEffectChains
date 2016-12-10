import bsh.Interpreter;
import fr.univ_lille1.m2iagl.dd.ChainElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;

import bsh.EvalError;

public class BeanShellExperiments {
	
	private static String neededInterpretorRessources = "import java.util.ArrayList;" +
														"import java.util.List;";
	
	public static Interpreter interpreter = new Interpreter();
	
	public void challenge(int input) {
		int bonjour = 5;
		input += 10;
		input /= 2;
		for(int i=0; i<5; i++) {
			input += i;
		}
		bonjour+=1;
	}
	
	/**
	 * Le coeur de l'ex�cution
	 * 
	 * @param args
	 * @throws EvalError
	 * @throws IOException
	 */
	public static void main(String[] args) throws EvalError, IOException {
		List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN = new ArrayList<CECElement>();
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN); //On donne � l'interpreteur de quoi remplir sa trace
		
		interpreter.set("input", 5); //On ajoute l'input, ici le 5 sera ensuite en dynamique.
		
		//On r�cup�re la classe o� se trouve la m�thode challenge qui nous int�resse
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/" + "BeanShellExperiments.java";
		String javaCode = readFile(filePath);
		
		//On r�cup�re la m�thode challenge sous forme de nodes
		List<Node> nodes = getChallengeMethodToNodes(javaCode);
		
		//On transforme le code de la m�thode pour pouvoir cr�er la trace au fur et � mesure
		List<Node> newnodes = NodeNavigator.transformNodes(nodes);
		
		//On ex�cute le code bloc par bloc
		for(Node n : newnodes) {
			interpreter.eval(n.toString());
		}
		
		//On r�cup�re la trace enti�re
		DEBUG_CAUSE_EFFECT_CHAIN = (List<CECElement>)interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN");
		
		//On affiche la trace
		for(CECElement n : DEBUG_CAUSE_EFFECT_CHAIN) {
			System.out.print("Line [" + n.getLine() + "] :");
			System.out.println("	New value of \""+n.getDescription()+"\" is " + n.getVariable());
		}
	}
	
	/**
	 * Permet de lire un fichier et de le renvoyer en String
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		try {
			return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return "";
		}
	}
	
	/**
	 * M�thode de r�cup�ration de la m�thode qui nous int�resse depuis la classe compl�te
	 * 
	 * @param fullClass
	 * @return
	 */
	public static List<Node> getChallengeMethodToNodes(String fullClass) {		
		CompilationUnit cu = JavaParser.parse(fullClass);
		
		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		
		return visitor.nodeList;		
	}
}