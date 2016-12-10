import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;

import bsh.EvalError;

/**
 * Classe permettant de naviguer entre les nodes pour transformer les informations qui nous int�ressent
 * 
 * @author Quentin
 *
 */
public class NodeNavigator {

	/**
	 * Permet d'it�rer une list enti�re de Node pour la transformer et remplir la trace
	 * 
	 * @param nodes
	 * @return
	 * @throws EvalError
	 */
	public static List<Node> transformNodes(List<Node> nodes) throws EvalError {
		int index=1;
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
	 * Permet de modifier un Node donn�. On g�re les expressions basiques et les boucles for
	 * 
	 * @param node
	 * @param index
	 * @param iterator
	 * @return
	 * @throws EvalError
	 */
	public static List<Node> transformNode(Node node, int index) throws EvalError {
		//On clone car on ne peut pas modifier une linkedList en cours d'it�ration
		Node cNode = node.clone();
		cNode.setParentNode(node.getParentNode().get().clone()); 
		
		List<Node> newNodes = new ArrayList<Node>(); //La node donn�e + les nouvelles nodes
		
		int line = 0;
		
		switch(cNode.getClass().getSimpleName()) {
			case "ExpressionStmt":
				ExpressionStmt expressionStmt = (ExpressionStmt)cNode;
				String varName = handleExpression(expressionStmt.getExpression()); //On r�cup�re la variable concern�e
				line = cNode.getBegin().get().line; //On r�cup�re la ligne
				
				//parentBlock.getStatements().add(index, StatementFactory.printVar("input")); //TODO Ne pas effacer svp
				
				newNodes.add(cNode); //On ajoute la Node de base
				//newNodes.add((Node)StatementFactory.printVar("input")); //TODO Ne pas effacer svp
				Statement t = StatementFactory.addInputToList(String.valueOf(line), varName, varName);
				newNodes.add((Node)t); //On ajoute la nouvelle Node, qui consiste � remplir la trace
				break;
			case "ForStmt":
				BlockStmt blockStmt = (BlockStmt) ((ForStmt)cNode).getBody(); //On r�cup�re les instructions dans la boucle

				List<Node> tempNodes = transformNodes(blockStmt.getChildNodes()); //On alt�re chacune de ces nodes recursivement
				blockStmt.getStatements().clear(); //On efface le bloc pour mettre les nouvelles Node dedans

				for(Node n : tempNodes) {
					blockStmt.getStatements().add((Statement) n);
				}
				
				newNodes.add(cNode);
				
				break;			
			default:
				break;				
		}
		
		return newNodes;
	}
	
	/**
	 * Pour g�rer une expression, qui est un cran en dessous de ExpressionStmt niveau hi�rarchie.
	 * Permet surtout de r�cup�rer le nom de la variable concern�e.
	 * 
	 * @param e
	 * @return
	 */
	public static String handleExpression(Expression e) {
		switch(e.getClass().getSimpleName()) {
			case "VariableDeclarationExpr" :
				VariableDeclarationExpr variableDeclarationExpr = (VariableDeclarationExpr)e;
				List<Node> VDENodes = variableDeclarationExpr.getChildNodes();
				VariableDeclarator variableDeclarator = (VariableDeclarator) VDENodes.get(1);
				return variableDeclarator.getIdentifier().getName().toString();
			case "AssignExpr" :
				AssignExpr assignExpr = (AssignExpr)e;
				List<Node> ANodes = assignExpr.getChildNodes();
				NameExpr nameExpr = (NameExpr) ANodes.get(0);
				return nameExpr.getName().toString();
			default:
				return "";
		}
	}
	
}
