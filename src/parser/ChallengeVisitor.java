package parser;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Permet d'it�rer � haut niveau dans les nodes, ici on r�cup�re une m�thode en
 * particulier.
 * 
 * @author Quentin
 *
 */
public class ChallengeVisitor extends VoidVisitorAdapter {
	public String challengeMethod;
	public String inputName;
	public List<Node> nodeList;
	public int line;
	
	private Interpreter interpreter;
	
	public ChallengeVisitor(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if ("challenge".equals(n.getName().toString())) {
			challengeMethod = n.toString();
			nodeList = n.getBody().get().getChildNodes();
			inputName = ((Parameter)n.getParameters().get(0)).getName().toString();
			//ligne de la declaration de la methode = ligne des param
			line = n.getParameters().get(0).getBegin().get().line;
		} else { //Si ce n'est pas le challenge, on interprete tout de m�me la m�thode
			try {				
				n.getAnnotations().clear();
				
				List<Node> nodes = n.getBody().get().getChildNodes();
				List<Node> nodesWithTrace = NodeNavigator.transformNodes(nodes, "");

				String stringBlockWithTrace = "{";				
				for(Node nodeWithTrace : nodesWithTrace) {
					stringBlockWithTrace += nodeWithTrace.toString();
				}				
				stringBlockWithTrace += "}";
				
				BlockStmt newBlock = JavaParser.parseBlock(stringBlockWithTrace);				
				n.setBody(newBlock);				
				String method = n.toString();
				
				method = method.replaceAll("<.*?>", ""); //On retire la genericite
				
				interpreter.eval(method);			
			} catch (EvalError e) {
				e.printStackTrace();
			}
		}
	}
	
}