package parser;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Permet d'itérer à haut niveau dans les nodes, ici on récupère une méthode en
 * particulier.
 * 
 * @author Quentin
 *
 */
public class ChallengeVisitor extends VoidVisitorAdapter {
	public String challengeMethod;
	public String inputName;
	public List<Node> nodeList;

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if (n.getName().toString().equals("challenge")) {
			challengeMethod = n.toString();
			nodeList = n.getBody().get().getChildNodes();
			inputName = ((Parameter)n.getParameters().get(0)).getName().toString();
		}
	}
}