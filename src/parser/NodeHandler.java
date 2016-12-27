package parser;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.TryStmt;

import bsh.EvalError;

public class NodeHandler {
	public static List<Node> expressionStmtHandler(Node cNode, String nameOfLoopIterationVar) {
		ExpressionStmt e = (ExpressionStmt) cNode;
		
		List<Node> newNodes = new ArrayList<Node>();
		
		String varName = handleSubExpression(e.getExpression());
		
		int line = cNode.getBegin().get().line; // On récupère la ligne

		newNodes.add(cNode); // On ajoute la Node de base
		
		Statement t = StatementFactory.addInputToList(String.valueOf(line), varName, varName, nameOfLoopIterationVar);
		
		// On ajoute la nouvelle Node, qui consiste à remplir la trace
		newNodes.add((Node) t); 
		
		return newNodes;
	}
	
	public static List<Node> loopStmtHandler(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		List<Node> newNodes = new ArrayList<Node>();
		
		
		//On récupère les instructions dans la boucle
		BlockStmt blockStmt = (BlockStmt) ((NodeWithBody) cNode).getBody();

		//La récupération est différente selon les types de boucles.
		if(cNode instanceof ForeachStmt) {
			nameOfLoopIterationVar += handleSubExpression((Expression) cNode.getChildNodes().get(0));
		} else if (cNode instanceof ForStmt) {
			nameOfLoopIterationVar += handleSubExpression((Expression) cNode.getChildNodes().get(1));
		}
		
		
		//On ajoute une var de loop itération pour afficher les itérations de boucles
		//handleBlockStmt(blockStmt, nameOfLoopIterationVar+";");
		loopHandleBlockStmt(blockStmt, nameOfLoopIterationVar+";");
		
		newNodes.add(cNode);
		
		return newNodes;
	}
	
	/**
	 * Body block for loop
	 * @param cNode
	 * @param nameOfLoopIterationVar
	 * @throws EvalError
	 */
	public static void loopHandleBlockStmt(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		BlockStmt blockStmt = (BlockStmt) cNode;

		//On altère chacune de ses nodes recursivement
		List<Node> tempNodes = NodeNavigator.transformNodes(blockStmt.getChildNodes(), nameOfLoopIterationVar); 

		//On efface le bloc pour mettre les nouvelles nodes dedans
		blockStmt.getStatements().clear();

		
		Statement t = StatementFactory.loopBegin(
				String.valueOf(cNode.getBegin().get().line),nameOfLoopIterationVar );
		blockStmt.getStatements().add(t);

		for (Node n : tempNodes) {
			blockStmt.getStatements().add((Statement) n);
		}
		
		Statement tEnd = StatementFactory.loopBegin(
				String.valueOf(cNode.getEnd().get().line),nameOfLoopIterationVar );
		blockStmt.getStatements().add(tEnd);
	}
	
	/**
	 * Permet de gérer les if. Prend en compte le cas des if sans accolade
	 * mais je n'ai pas encore réussi à ajouter la trace pour l'expression dans ce if.
	 * @param cNode
	 * @return
	 * @throws EvalError
	 */
	public static List<Node> ifStmtHandler(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		List<Node> newNodes = new ArrayList<Node>();
		
		IfStmt ifStmt = (IfStmt)cNode;
		Node ifBlockNode = ifStmt.getChildNodes().get(1);
		
		//On récupère les instructions dans la boucle		
		if(ifBlockNode instanceof BlockStmt) {			
			handleBlockStmt(ifBlockNode, nameOfLoopIterationVar);
			newNodes.add(ifStmt);
		} else { //TODO, C'est compliqué à gérer les if sans accolade, pas de trace pour l'instant
			//newNodes.addAll(expressionStmtHandler(ifBlockNode));
			newNodes.add(ifStmt);
		}
		
		//Gestion du else
		if(ifStmt.getChildNodes().size() > 2) {
			Node elseBlockNode = ifStmt.getChildNodes().get(2);
			if(elseBlockNode instanceof BlockStmt) {			
				handleBlockStmt(elseBlockNode, nameOfLoopIterationVar);
			} else { //TODO, C'est compliqué à gérer les else sans accolade, pas de trace pour l'instant
				//newNodes.addAll(expressionStmtHandler(elseBlockNode));
				newNodes.add(ifStmt);
			}
		}
		
		return newNodes;
	}
	
	/**
	 * Permet de gérer un bloc.
	 * @param cNode
	 * @throws EvalError
	 */
	public static void handleBlockStmt(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		BlockStmt blockStmt = (BlockStmt) cNode;
		
		//On altère chacune de ses nodes recursivement
		List<Node> tempNodes = NodeNavigator.transformNodes(blockStmt.getChildNodes(), nameOfLoopIterationVar); 
		
		//On efface le bloc pour mettre les nouvelles nodes dedans
		blockStmt.getStatements().clear();
		
		
		for (Node n : tempNodes) {
			blockStmt.getStatements().add((Statement) n);
		}
	}

	public static List<Node> tryStmtHandler(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		List<Node> newNodes = new ArrayList<Node>();
		
		TryStmt tryStmt = (TryStmt)cNode;
		handleBlockStmt(tryStmt.getTryBlock().get(), nameOfLoopIterationVar);
		
		for(CatchClause cc :tryStmt.getCatchClauses()) {
			handleBlockStmt(cc.getBody(), nameOfLoopIterationVar);
		}
		
		newNodes.add(tryStmt);
		
		return newNodes;
	}
	
	public static List<Node> switchStmtHandler(Node cNode, String nameOfLoopIterationVar) throws EvalError {
		List<Node> newNodes = new ArrayList<Node>();
		SwitchStmt switchStmt = (SwitchStmt) cNode;
		
		for (SwitchEntryStmt e : switchStmt.getEntries()) {
			List<Node> tempNodes = NodeNavigator.transformNodes(e.getChildNodes(), nameOfLoopIterationVar); 
			
			if(e.getLabel().isPresent()) //Si ce n'est pas le default, on dégage le "case"
					tempNodes.remove(0);
			
			//On efface le bloc pour mettre les nouvelles nodes dedans
			e.getStatements().clear();

			for (Node n : tempNodes) {
				e.getStatements().add((Statement)n);
			}
		}
		newNodes.add(switchStmt);
		
		return newNodes;
	}
	
	/**
	 * Pour gérer une expression, qui est un cran en dessous de ExpressionStmt
	 * niveau hiérarchie. Permet surtout de récupérer le nom de la variable
	 * concernée lors d'assignations simples.
	 * 
	 * @param e
	 * @return
	 */
	public static String handleSubExpression(Expression e) {
		switch (e.getClass().getSimpleName()) {
			case "VariableDeclarationExpr" : 
				VariableDeclarationExpr variableDeclarationExpr = (VariableDeclarationExpr) e;
				List<Node> vde_Nodes = variableDeclarationExpr.getChildNodes();
				VariableDeclarator variableDeclarator = (VariableDeclarator) vde_Nodes.get(1);
				return variableDeclarator.getIdentifier().getName().toString();
			case "UnaryExpr":
				UnaryExpr unaryExpr = (UnaryExpr) e;
				List<Node> u_Nodes = unaryExpr.getChildNodes();
				NameExpr u_nameExpr = (NameExpr) u_Nodes.get(0);
				return u_nameExpr.getName().toString();
			case "AssignExpr":
				AssignExpr assignExpr = (AssignExpr) e;
				List<Node> a_Nodes = assignExpr.getChildNodes();
				NameExpr a_nameExpr = (NameExpr) a_Nodes.get(0);
				return a_nameExpr.getName().toString();
			case "MethodCallExpr":
				MethodCallExpr mc_Expr = (MethodCallExpr) e;
				List<Node> mc_Nodes = mc_Expr.getChildNodes();
				SimpleName mc_nameExpr = (SimpleName) mc_Nodes.get(0);
				return mc_nameExpr.toString();
			default:
				return "";
		}
	}
}
