import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Permet de créer des Statement qui peuvent être ajoutés dans des Nodes. On ne peut pas ajouter directement un Node dans les listes.
 * 
 * @author Quentin
 *
 */
public class StatementFactory {
	public static Statement printVar(String var) {
		String code = "System.out.println(\"	\" + "+var+");";
		Statement statement = JavaParser.parseStatement(code);
		
		return statement;
	}
	
	public static Statement printString(String var) {
		String code = "System.out.println(\"	\" + \""+var+"\");";
		Statement statement = JavaParser.parseStatement(code);
		
		return statement;
	}
	
	public static Statement addInputToList(String line, String var, String description) {
		String code = "DEBUG_CAUSE_EFFECT_CHAIN.add(new CECElement(\"" + line + "\", " + var + ", \"" + description + "\"));";
		
		Statement statement = JavaParser.parseStatement(code);
		
		return statement;		
	}
}
