package bs;
import bsh.Interpreter;
import dd.impl.CECElement;
import parser.ChallengeVisitor;
import parser.NodeNavigator;

import java.io.File;
import java.io.FileNotFoundException;
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
	public String challengeName;
	public int lineMethod;
	
	public BeanShell() {
		this.challengeName = "BeanShell";
	}
	
	public BeanShell(String challengeName) {
		this.challengeName = challengeName;
	}

	public void initInterpreter() throws EvalError {
		List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN = new ArrayList<CECElement>();
		
		interpreter = new Interpreter();
		
		//Des import utiles pour l'interpreter
		interpreter.eval("import java.util.*;");
		interpreter.eval("import dd.impl.CECElement;");
		
		//On donne à l'interpreteur un objet dans le quel remplir sa trace. 
		//Astuce pour lui faire connaître des classes étrangères par la même occasion.
		//Le nom à rallonge permet d'éviter un doublon de variables
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN);
	}
	
	public List<CECElement> getTrace(Object input) throws EvalError {
		initInterpreter();

		// On ajoute l'input, ici le 5 sera ensuite en dynamique.
		interpreter.set("input", input);

		// On récupère la classe où se trouve la méthode challenge qui nous intéresse
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/" + challengeName + ".java";
		String javaCode = readFile(filePath);

		// On récupère la méthode challenge sous forme de nodes
		List<Node> nodes = getChallengeMethodToNodes(javaCode);
		
		// On transforme le code de la méthode pour pouvoir créer la trace au fur et à mesure
		List<Node> newnodes = NodeNavigator.transformNodes(nodes, "");
		
		
		CECElement errorElem = null;
		int line=0;
		// On exécute le code bloc par bloc
		for (Node n : newnodes) {
			//System.out.println(n);
			line = n.getBegin().get().line;
			
			try {
				interpreter.eval(n.toString());

			} catch (EvalError e) {
				//line =  line de la boucle + line dans le block de la boucle - 1
				errorElem =new CECElement(""+(line+e.getErrorLineNumber()-1), e.getErrorText(), ", fail : "+e.getCause().toString());
				//System.out.println(e.getCause());
				break;
			}
		}
		
		// On récupère la trace entière et on la retourne	
		List<CECElement> cec = new ArrayList<CECElement>();
		if(input instanceof String){
			input="\""+input+"\"";
		}
		cec.add(new CECElement(lineMethod+"",challengeName+".challenge("+inputName+"="+input+")"," , appel de methode"));
		cec.addAll( (List<CECElement>) interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN") );
		if(errorElem != null){
			//errorElem.setLine(cec.get(cec.size()-1).getLine());
			cec.add(errorElem);
		}
		return cec;
	}

	public void printTrace(List<CECElement> DEBUG_CAUSE_EFFECT_CHAIN) {
		System.out.println("_____ TRACE BEGIN _____");
		
		List<CECElement> printedList = new ArrayList<CECElement>();

		// On affiche la trace
		for (CECElement e : DEBUG_CAUSE_EFFECT_CHAIN) {
			int loopIteration = 0;

			System.out.print("Line [" + e.getLine() + "] :");
			System.out.println("	" + e.getVariable() + " " + e.getDescription());

			printedList.add(e);
		}
		
		System.out.println("_____ TRACE ENDS _____");
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
		lineMethod=visitor.line;

		return visitor.nodeList;
	}
}