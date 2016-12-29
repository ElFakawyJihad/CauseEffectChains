package parser;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Permet de cr�er des Statement qui peuvent �tre ajout�s dans des Nodes. On ne
 * peut pas ajouter directement un Node dans les listes.
 * 
 * @author Quentin
 *
 */
public class StatementFactory {
	public static Statement addInputToList(String line, String var, String valueVar, String nameOfLoopIterationVar) {
		//description = "\"\\\"" + description + "\\\"\"";	
		
		String varName = "\"" + var + "\""; //sous forme de chaine
		//decription= "\" = \"+"+var; //sous forme de variable
		
		/*if(nameOfLoopIterationVar.contains(";")) {
			description += " + \"	| Loop iterations : \"";
			for(String b : nameOfLoopIterationVar.split(";")) {
				description += " + \"" + b + "=\" + " + b + " + \";\"";
			}
		}*/
		
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new State(" + 0 + ", " + line +", "+ varName + ", " + valueVar
				+ ",true));";

		Statement statement = JavaParser.parseStatement(code);

		return statement;
	}
	
	public static Statement loopBegin(String line,String loopIteration) {
		//description = "\"\\\"" + description + "\\\"\"";
		String iterationVar=null;
		
		if(loopIteration.contains(";")) {
			//description += " + \"	| Loop iterations : \"";
			for(String b : loopIteration.split(";")) {
				iterationVar=b;
				//description += " + \"" + b + "=\" + " + b + " + \";\"";
			}
		}
		String varName="\""+iterationVar+"\"";
		String description= "\" = \"+"+iterationVar;
		
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new State(0," + line + ", " + varName + ", " + iterationVar+",true));";

		Statement statement = JavaParser.parseStatement(code);
		
		return statement;
	}
	
}
