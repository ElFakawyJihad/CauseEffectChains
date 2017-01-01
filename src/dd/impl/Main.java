package dd.impl;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import dd.CauseEffectChain;
import dd.Challenge;
import dd.DDebugger;

public class Main {

	public final static ChallengesLoader classLoader = new ChallengesLoader( ChallengesLoader.class.getClassLoader());
	public final static DDebugger d = new DDebuggerImpl();
	
	
	public static void main(String[] args) {

	    String curDir = System.getProperty("user.dir");
	    System.out.println ("Le r�pertoire courant est: "+curDir);
		if(args[0].equals("-f")){
			runOnFile( Paths.get(args[1]) );
		}else if(args[0].equals("-d")){
			runOnDir(args[1], args[2].split(" "));
		}
	}
	
	public static void executeOnClass(Class cls){
		try {
			Object aChallenge = cls.newInstance();
			if(aChallenge instanceof Challenge){
				CauseEffectChain cec = d.debug((Challenge) aChallenge);
				System.out.println(cec);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void runOnFile( Path path){
		Class aClass;
		try {//"D:\\git\\CauseEffectChains\\src\\classes\\challenges\\FirstChallenge.java"
			aClass = classLoader.loadClass(path);
			executeOnClass(aClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runOnDir(String path, String[] classes){
		// Create a File object on the root of the directory containing the class file
		File file = new File(path);

		try {
		    // Convert File to a URL
		    URL url = file.toURL();   
		    URL[] urls = new URL[]{url};

		    // Create a new class loader with the directory
		    ClassLoader cl = new URLClassLoader(urls);
		    int numberOfClass=Array.getLength(classes);
		    for(int i=0; i< numberOfClass;i++){
		    	declareNewChallenge(classes[i]);
			    Class cls = cl.loadClass(classes[i]);
		    	executeOnClass(cls);
		    }
		    
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		}
	}
	
	public static void declareNewChallenge(String cls){
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.format("|| Challenge :  %s   ||\n",cls);
		System.out.println("------------------------------------------------");
		System.out.println("");
	}
}
