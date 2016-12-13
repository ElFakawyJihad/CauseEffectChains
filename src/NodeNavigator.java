import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;

import bsh.EvalError;

/**
 * Classe permettant de naviguer entre les nodes pour transformer les
 * informations qui nous intéressent
 * 
 * @author Quentin
 *
 */
public class NodeNavigator {

	/**
	 * Permet d'itérer une list entière de Node pour la transformer et remplir
	 * la trace
	 * 
	 * @param nodes
	 * @return
	 * @throws EvalError
	 */
	public static List<Node> transformNodes(List<Node> nodes) throws EvalError {
		int index = 1;
		List<Node> newNodes = new ArrayList<Node>();

		ListIterator<Node> iterator = nodes.listIterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			newNodes.addAll(transformNode(node, index));
			index++;
		}

		return newNodes;
	}

	/**
	 * Permet de modifier un Node donné. On gère les expressions basiques et les
	 * boucles for
	 * 
	 * @param node
	 * @param index
	 * @param iterator
	 * @return
	 * @throws EvalError
	 */
	public static List<Node> transformNode(Node node, int index) throws EvalError {
		// On clone car on ne peut pas modifier une linkedList en cours d'itération
		Node cNode = node.clone();
		cNode.setParentNode(node.getParentNode().get().clone());

		//La node donnée + les nouvelles nodes pour la trace
		List<Node> newNodes = new ArrayList<Node>();

		
		//DoStmt, ForeachStmt, ForStmt, WhileStmt
		if (cNode instanceof NodeWithBody) {
			newNodes.addAll(NodeHandler.loopStmtHandler(cNode));
		}
		
		switch (cNode.getClass().getSimpleName()) {
			case "ExpressionStmt":			
				newNodes.addAll(NodeHandler.expressionStmtHandler(cNode));			
				break;
			case "IfStmt":
				newNodes.addAll(NodeHandler.ifStmtHandler(cNode));
				break;
			default:
				break;
		}

		return newNodes;
	}

}
