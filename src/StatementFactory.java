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
	public static Statement addInputToList(String line, String var, String description, String nameOfLoopIterationVar) {
		description = "\"\\\"" + description + "\\\"\"";
		
		
		if(nameOfLoopIterationVar.contains(";")) {
			description += " + \"	| Loop iterations : \"";
			for(String b : nameOfLoopIterationVar.split(";")) {
				description += " + \"" + b + "=\" + " + b + " + \";\"";
			}
		}
		
		
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement(\"" + line + "\", " + var + ", " + description
				+ "));";

		Statement statement = JavaParser.parseStatement(code);


			
		
		
		return statement;
	}
}
