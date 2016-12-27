package parser;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

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

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if (n.getName().toString().equals("challenge")) {
			challengeMethod = n.toString();
			nodeList = n.getBody().get().getChildNodes();
			inputName = ((Parameter)n.getParameters().get(0)).getName().toString();
			//ligne de la declaration de la methode = ligne des param
			line = n.getParameters().get(0).getBegin().get().line;

		}
	}
	
}