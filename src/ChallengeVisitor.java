import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.*;

/**
 * Permet d'it�rer � haut niveau dans les nodes, ici on r�cup�re une m�thode en
 * particulier.
 * 
 * @author Quentin
 *
 */
public class ChallengeVisitor extends VoidVisitorAdapter {
	public String challengeMethod;
	public List<Node> nodeList;

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if (n.getName().toString().equals("challenge")) {
			challengeMethod = n.toString();
			nodeList = n.getBody().get().getChildNodes();
		}
	}
}