import bsh.Interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellExperiments {
	
	private static String program = "int add_ten_and_divide(int var) { \n"
						+ "var += 10; \n" 
						+ "var /= 2; \n" 
						+ "return var;\n"
					+ "}\n";
	
	public static List<String> separateStringMethod(String method) {
		
		//On sépare chaque instruction. Le Arrays.asList retourne une liste de taille fixe, on converti au passage
		List<String> instructions = new LinkedList<String>(Arrays.asList(method.split("\n"))); 
		
		instructions.remove(0); //On retire la déclaration de la méthode
		instructions.remove(instructions.size()-1); //On retire la fermeture de la méthode
		
		for(String i : instructions) {
			if(i.contains("return")) {
				instructions.remove(i); //On retire l'instruction return
			}
		}
		
		return instructions;		
	}
	

	public static void main(String[] args) throws EvalError {
		
		Interpreter interpreter = new Interpreter();
		
		interpreter.set("var", 5); //On teste un input
		List<String> instructions = separateStringMethod(program);	
		
		for(String i : instructions) {
			interpreter.eval(i);
		}	
		
		System.out.println( interpreter.get("var") );
	}
	
	

}
