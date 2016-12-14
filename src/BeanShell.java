import bsh.Interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import bsh.EvalError;

public class BeanShell {
	public Interpreter interpreter;
	public String inputName;
	public String challengeMethod;
	
	public void challenge(int input) {		
		int bonjour = 5;
		input += 10; 
		input /= 2;
		
		List temp = new ArrayList();
		temp.add(1);
		temp.add(3);
		temp.add(5);
		
		for (int i = 0; i < 5; i++) {
			input += i;
			
			try {
				bonjour += 5;
				bonjour /= 0;
			} catch(Exception e) {
				bonjour += 5;
			} 
		}
		

		
		for(Object j : temp) {
			input = input + Integer.parseInt(j.toString());
		}
		
		bonjour += 1;
	}

	public void initInterpreter() throws EvalError {
		List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN = new ArrayList<CECElement>();
		
		interpreter = new Interpreter();
		
		//Un import au cas où pour l'interpreter
		interpreter.eval("import java.util.*;");
		
		//On donne à l'interpreteur un objet dans le quel remplir sa trace. 
		//Astuce pour lui faire connaître des classes étrangères par la même occasion.
		//Le nom à rallonge permet d'éviter un doublon de variables
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN);
	}
	
	public List<CECElement> getTrace(Object input) throws EvalError, IOException {
		initInterpreter();

		// On ajoute l'input, ici le 5 sera ensuite en dynamique.
		interpreter.set("input", input);

		// On récupère la classe où se trouve la méthode challenge qui nous intéresse
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/" + "BeanShell.java";
		String javaCode = readFile(filePath);

		// On récupère la méthode challenge sous forme de nodes
		List<Node> nodes = getChallengeMethodToNodes(javaCode);

		// On transforme le code de la méthode pour pouvoir créer la trace au fur et à mesure
		List<Node> newnodes = NodeNavigator.transformNodes(nodes, "");

		// On exécute le code bloc par bloc
		for (Node n : newnodes) {
			interpreter.eval(n.toString());
		}
		
		// On récupère la trace entière et on la retourne	
		return (List<CECElement>) interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN");
	}

	public void printTrace(List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN) {
		List<CECElement> printedList = new ArrayList<CECElement>();

		// On affiche la trace
		for (CECElement e : DEBUG_CAUSE_EFFECT_CHAIN) {
			int loopIteration = 0;

			System.out.print("Line [" + e.getLine() + "] :");
			System.out.println("	" + e.getVariable() + "	is the new value of " + e.getDescription());

			printedList.add(e);
		}
	}
	
	/**
	 * Permet de lire un fichier et de le renvoyer en String
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		try {
			return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return "";
		}
	}

	/**
	 * Méthode de récupération de la méthode qui nous intéresse depuis la classe
	 * complète
	 * 
	 * @param fullClass
	 * @return
	 */
	public List<Node> getChallengeMethodToNodes(String fullClass) {
		CompilationUnit cu = JavaParser.parse(fullClass);

		ChallengeVisitor visitor = new ChallengeVisitor();
		visitor.visit(cu, null);
		inputName = visitor.inputName;
		challengeMethod = visitor.challengeMethod;

		return visitor.nodeList;
	}
}