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
		
		//On donne � l'interpreteur un objet dans le quel remplir sa trace. 
		//Astuce pour lui faire conna�tre des classes �trang�res par la m�me occasion.
		//Le nom � rallonge permet d'�viter un doublon de variables
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN);
	}
	
	public List<CECElement> getTrace(Object input) throws EvalError {
		initInterpreter();

		// On ajoute l'input, ici le 5 sera ensuite en dynamique.
		interpreter.set("input", input);

		// On r�cup�re la classe o� se trouve la m�thode challenge qui nous int�resse
		File tempFile = new File("");
		String filePath = tempFile.getAbsolutePath() + "/src/challenges/" + challengeName + ".java";
		String javaCode = readFile(filePath);

		// On r�cup�re la m�thode challenge sous forme de nodes
		List<Node> nodes = getChallengeMethodToNodes(javaCode);
		
		// On transforme le code de la m�thode pour pouvoir cr�er la trace au fur et � mesure
		List<Node> newnodes = NodeNavigator.transformNodes(nodes, "");
		
		
		CECElement errorElem = null;
		int line=0;
		// On ex�cute le code bloc par bloc
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
		
		// On r�cup�re la trace enti�re et on la retourne	
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
	 * M�thode de r�cup�ration de la m�thode qui nous int�resse depuis la classe
	 * compl�te
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