import bsh.Interpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

import bsh.EvalError;


public class BeanShellExperiments {

	public static void main(String[] args) throws EvalError, IOException {
		Interpreter interpreter = new Interpreter();		

		//On lit le fichier pour récupérer son code en String
		File tempFile = new File("");
		//String filePath = tempFile.getAbsolutePath() + "\\src\\" + "ExampleClass.java"; //Windows
		String filePath = tempFile.getAbsolutePath() + "/src/" + "ExampleClass.java"; //Linux
		String javaClass = readFile(filePath);

		//On compile le code de la classe
		Class c = (Class) interpreter.eval(javaClass);
		
		//getAllFields(c); //Print tous les attributs de la classe
		//getAllMethods(c); //Print toutes les méthodes de la classe
		
		//Ici il existe plusieurs moyen d'exécuter le main :
		
		//interpreter.eval(c.getCanonicalName()+".main(null);"); //1- La façon simple puisque le main est statique
		
		int i = 5;
		
		/* 2- Sinon on peut aussi créer un objet dans l'interpreteur				
		interpreter.eval(c.getCanonicalName()+" o = new " + c.getCanonicalName() + "(" + i + ")");
		interpreter.eval("o.main(null);");*/
		
		/* 3- Ou alors on instancie l'objet ici et on le donne dans l'interpreteur */
		Object o = createObjectFromClass(c, i);
		interpreter.set("o", o);
		interpreter.eval("o.main(null);");
		
		/*
		 * Problèmes :
		 * - Le pas à pas devient compliqué quand on donne toute une classe d'un coup (Pour un exemple de pas à pas, voir les commits précédents qui ont été écrasés
		 * - Le logging devient difficile
		 * 
		 * Solutions potentielles :
		 * - Il faudrait faire du parsing massif pour pouvoir simplifier l'exécution de la classe mais c'est compliqué et il reste le problème des instructions partielles (boucles, if, délcaration de méthode...)
		 * - Spoon, mais je sais pas comment m'en servir et apparament y'a des problèmes pour éxécuter en runtime
		 * - Trouver un moyen de pouvoir faire du debug directement en lignes de code, mais j'ai trouvé aucune solution pour faire ça
		 * - Savoir monitorer une variable pour faire des logs à chaque fois qu'elle est modifiée, mais là pareil, j'ai rien trouvé pour
		 */
	}
	

	public static String readFile(String fileName) {
		try {
			return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return "";
		}
	}

	
	public static Object createObjectFromClass(Class c, Object i) {
		try {
			Constructor<?> ctor = c.getConstructor(int.class);		
			return ctor.newInstance(new Object[] { i });
		} catch (Exception e) {
			return null;
		}
	}
	
	private static HashMap<String, Object> getAllFields(Class c) {
		Field[] fields = c.getDeclaredFields();
		HashMap<String, Object> stringFields = new HashMap<String, Object>();
		
		System.out.println("Liste des variables :");
		for(Field f : fields) {
			if(f.getType().getName() != "bsh.This") {
				System.out.println("	" + f.getType().getName() + " " + f.getName());
				stringFields.put(f.getName(), f.getType());
			}
		}
		
		return stringFields;
	}

	private static HashMap<String, Object> getAllMethods(Class c) {
		Method[] methods = c.getDeclaredMethods();
		HashMap<String, Object> stringMethods = new HashMap<String, Object>();
		
		System.out.println("Liste des méthodes :");
		for(Method m : methods) {
				System.out.println("	" + m.toString());
				stringMethods.put(m.toString(), m.getReturnType());
		}
		
		return stringMethods;
	}
	
}
