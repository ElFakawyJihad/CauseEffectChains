package parser;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Permet de créer des Statement qui peuvent être ajoutés dans des Nodes. On ne
 * peut pas ajouter directement un Node dans les listes.
 * 
 * @author Quentin
 *
 */
public class StatementFactory {
	public static Statement addInputToList(String line, String var, String description, String nameOfLoopIterationVar) {
		//description = "\"\\\"" + description + "\\\"\"";	
		
		String varName = "\"" + var + "\""; //sous forme de chaine
		description = "\" = \"+"+var; //sous forme de variable
		
		/*if(nameOfLoopIterationVar.contains(";")) {
			description += " + \"	| Loop iterations : \"";
			for(String b : nameOfLoopIterationVar.split(";")) {
				description += " + \"" + b + "=\" + " + b + " + \";\"";
			}
		}*/
		
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement(\"" + line + "\", " + varName + ", " + description
				+ "));";

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
		
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement(\"" + line + "\", " + varName + ", " + description+"));";

		Statement statement = JavaParser.parseStatement(code);
		
		return statement;
	}
	
}
