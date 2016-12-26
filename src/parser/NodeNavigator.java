package parser;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
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
	public static List<Node> transformNodes(List<Node> nodes, String nameOfLoopIterationVar) throws EvalError {
		int index = 1;
		List<Node> newNodes = new ArrayList<Node>();

		ListIterator<Node> iterator = nodes.listIterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			newNodes.addAll(transformNode(node, index, nameOfLoopIterationVar));
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
	public static List<Node> transformNode(Node node, int index, String nameOfLoopIterationVar) throws EvalError {
		// On clone car on ne peut pas modifier une linkedList en cours d'itération
		Node cNode = node.clone();
		cNode.setParentNode(node.getParentNode().get().clone());

		//La node donnée + les nouvelles nodes pour la trace
		List<Node> newNodes = new ArrayList<Node>();

		
		//DoStmt, ForeachStmt, ForStmt, WhileStmt
		if (cNode instanceof NodeWithBody) {
			newNodes.addAll(NodeHandler.loopStmtHandler(cNode, nameOfLoopIterationVar));
			return newNodes;
		}
		
		switch (cNode.getClass().getSimpleName()) {
			case "ExpressionStmt":			
				newNodes.addAll(NodeHandler.expressionStmtHandler(cNode, nameOfLoopIterationVar));			
				break;
			case "IfStmt":
				newNodes.addAll(NodeHandler.ifStmtHandler(cNode, nameOfLoopIterationVar));
				break;
			case "TryStmt":
				newNodes.addAll(NodeHandler.tryStmtHandler(cNode, nameOfLoopIterationVar));
				break;
			case "SwitchStmt":
				newNodes.addAll(NodeHandler.switchStmtHandler(cNode, nameOfLoopIterationVar));
				break;
			default:
				newNodes.add(cNode);
				break;
		}

		return newNodes;
	}

}
