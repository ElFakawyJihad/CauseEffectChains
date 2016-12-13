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

public class BeanShellExperiments {
	public static Interpreter interpreter = new Interpreter();

	public void challenge(int input) {
		int bonjour = 5;
		input += 10; 
		input /= 2;
		
		for (int i = 0; i < 5; i++) {
			input += i;
		}
		
		List temp = new ArrayList();
		temp.add(1);
		temp.add(3);
		temp.add(5);
		
		for(Object j : temp) {
			input = input + Integer.parseInt(j.toString());
		}
		
		bonjour += 1;
	}

	/**
	 * Le coeur de l'ex�cution
	 * 
	 * @param args
	 * @throws EvalError
	 * @throws IOException
	 */
	public static void main(String[] args) throws EvalError, IOException {
		getTrace();
	}

	public static void getTrace() throws EvalError, IOException {
		List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN = new ArrayList<CECElement>();
		
		interpreter.eval("import java.util.*;");
		//On donne � l'interpreteur un objet dans le quel remplir sa trace. 
		//Astuce pour lui faire conna�tre des classes �trang�res par la m�me occasion.
		//Le nom � rallonge permet d'�viter un doublon de variables
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN);

		interpreter.set("input", 5); // On ajoute l'input, ici le 5 sera ensuite
		// en dynamique.

		// On r�cup�re la classe o� se trouve la m�thode challenge qui nous
		// int�resse
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/" + "BeanShellExperiments.java";
		String javaCode = readFile(filePath);

		// On r�cup�re la m�thode challenge sous forme de nodes
		List<Node> nodes = getChallengeMethodToNodes(javaCode);

		// On transforme le code de la m�thode pour pouvoir cr�er la trace au
		// fur et � mesure
		List<Node> newnodes = NodeNavigator.transformNodes(nodes);

		// On ex�cute le code bloc par bloc
		for (Node n : newnodes) {
			interpreter.eval(n.toString());
		}

		// On r�cup�re la trace enti�re
		DEBUG_CAUSE_EFFECT_CHAIN = (List<CECElement>) interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN");
		
		printTrace(DEBUG_CAUSE_EFFECT_CHAIN);
	}

	public static void printTrace(List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN) {
		List<CECElement> printedList = new ArrayList<CECElement>();

		// On affiche la trace
		for (CECElement e : DEBUG_CAUSE_EFFECT_CHAIN) {
			int loopIteration = 0;

			System.out.print("Line [" + e.getLine() + "] :");
			System.out.print("	New value of \"" + e.getDescription() + "\" is " + e.getVariable());

			// Pour d�tecter la premi�re it�ration dans une boucle
			for (CECElement e2 : DEBUG_CAUSE_EFFECT_CHAIN) {
				if (loopIteration == 0 && e2.getLine().equals(e.getLine()) && !e2.equals(e)) {
					loopIteration++;
				}
			}

			// Pour d�tecter la n-i�me it�ration dans une boucle
			for (CECElement e2 : printedList) {
				if (e2.getLine().equals(e.getLine())) {
					loopIteration++;
				}
			}

			// Pour g�rer l'affichage des it�rations de boucles
			if (loopIteration == 0) {
				System.out.println("");
			} else {
				System.out.println(" | Loop iteration : " + (loopIteration));
			}

			printedList.add(e);
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
	 * M�thode de r�cup�ration de la m�thode qui nous int�resse depuis la classe
	 * compl�te
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