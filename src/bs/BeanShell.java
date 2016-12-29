package bs;
import bsh.Interpreter;
import dd.impl.CECElement;
import dd.impl.State;
import dd.impl.Trace;
import dd.impl.Variable;
import parser.ChallengeVisitor;
import parser.NodeNavigator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.table.TableColumn;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import bsh.EvalError;

public class BeanShell {
	public Interpreter interpreter;
	public String inputName;
	public Object inputValue;
	public String challengeMethod;
	public String challengeName;
	public int methodLine;
	public ChallengeException ex=null;
	
	public BeanShell() {
		this.challengeName = "BeanShell";
	}
	
	public BeanShell(String challengeName) {
		this.challengeName = challengeName;
	}

	public void initInterpreter() throws EvalError {
		List<State> DEBUG_CAUSE_EFFECT_CHAIN = new ArrayList<State>();
		
		interpreter = new Interpreter();
		
		//Des import utiles pour l'interpreter
		interpreter.eval("import java.util.*;");
		//interpreter.eval("import dd.impl.CECElement;");
		interpreter.eval("import dd.impl.State;");
		
		//On donne � l'interpreteur un objet dans le quel remplir sa trace. 
		//Astuce pour lui faire conna�tre des classes �trang�res par la m�me occasion.
		//Le nom � rallonge permet d'�viter un doublon de variables
		interpreter.set("DEBUG_CAUSE_EFFECT_CHAIN", DEBUG_CAUSE_EFFECT_CHAIN);
	}
	
	/**
	 * execute la methode et retourne sa trace
	 * @param input
	 * @return
	 * @throws EvalError
	 */
	public Trace getTrace(Object input) throws EvalError {
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
			
		int line=0;
		// On ex�cute le code bloc par bloc
		for (Node n : newnodes) {
			//System.out.println(n);
			line = n.getBegin().get().line;
			
			try {
				interpreter.eval(n.toString());
			} catch (EvalError e) {
				//line =  line de la boucle + line dans le block de la boucle - 1
				Logger.getGlobal().info(e.getErrorText());
				ex = new ChallengeException( (line+e.getErrorLineNumber()-1), e.getErrorText(), e.getCause().toString() );
				break;
			}
		}
		
		// On r�cup�re la trace enti�re et on la retourne	
		List<State> states = new ArrayList<State>();
		if(input instanceof String){
			inputValue="\""+input+"\"";
		}else{
			inputValue = input;
		}
		states.add(new State(0,methodLine,inputName,input,true));
		states.addAll( (List<State>) interpreter.get("DEBUG_CAUSE_EFFECT_CHAIN") );
		
		cascadeState(states);
		
		
		
		Trace trace = new Trace(states,ex!=null);
		trace.exception=ex;
		trace.nameMethod=challengeName;
		trace.lineMethod=methodLine;
		trace.nameClass=challengeName;
		trace.nameInput=inputName;
		return trace;
	}

	/**
	 * affiche la liste des etats 
	 * @param states
	 */
	public void printTrace(List<State> states) {
		System.out.println("_____ TRACE BEGIN _____");
		System.out.println( "Line [" + methodLine + "] : "+challengeName+".challenge("+inputName/*+"="+inputValue*/+")" );
		
		List<State> printedList = new ArrayList<State>();
		int nb=0;
		// On affiche la trace
		for (State e : states) {
			//int loopIteration = 0;
			e.stateNumber=nb++;
			System.out.print("Line [" + e.lineNumber + "] :");
			System.out.println("	" + e.varCurrent.name + " = " + e.varCurrent.value);
			printedList.add(e);
		}
		
		if(ex!=null){
			System.out.println("Line [" + ex.lineNumber + "] : "+ex.textCause+" = "+ex.message);
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
		methodLine=visitor.line;

		return visitor.nodeList;
	}
	
	/**
	 * Genere la liste des variables dans un etat (state)
	 * une state = (liste des variables de la state-1) + (variable courrante)
	 * @param states
	 */
	public void cascadeState(List<State> states){
		List<State> stateToDel = new ArrayList<>(); 
		for(int i=0;i<states.size();i++){
			State state = states.get(i);
			if(!state.addVar){
				stateToDel.add(state);
			}
			if(i>0){
				for (Variable item : states.get(i-1).variables) 
					state.variables.add( new Variable(item.name,item.value,item.line) );
			}
			updateState(state);
		}
		
		/*les states qui indique de supprimer une variable (fin de loop)
		ne doivent pas apparaitre dans la trace, donc on les supprime*/
		states.removeAll(stateToDel);
	}
	
	/**
	 * ajout, modifie, ou supprime la variable courrante dans la liste
	 * @param state
	 */
	private void updateState(State state){
		//recup l'id de la variable courrante si elle est deja dans la liste
		int idVar=-1;
		for(int j=0;j<state.variables.size();j++){
			if(state.variables.get(j).name.equals(state.varCurrent.name)){
				idVar=j;
			}
		}

		if(idVar == -1 && state.addVar){
			//il faut ajouter la variable dans liste
			state.variables.add(state.varCurrent);
			
		}else if( state.addVar ){
			//il faut modifier la variable dans la liste
			state.variables.get(idVar).value=state.varCurrent.value;
			state.variables.get(idVar).line=state.varCurrent.line;
			
		}else if( !state.addVar){
			//il faut supprimer une variable de la liste
			state.variables.remove(idVar);
		}
	}
}