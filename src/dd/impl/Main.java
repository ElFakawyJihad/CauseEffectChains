package dd.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import dd.CauseEffectChain;
import dd.Challenge;
import dd.DDebugger;

public class Main {

	public final static ChallengesLoader classLoader = new ChallengesLoader( ChallengesLoader.class.getClassLoader());
	public final static DDebugger d = new DDebuggerImpl();
	
	
	public static void main(String[] args) {

	    String curDir = System.getProperty("user.dir");
	    System.out.println ("Le répertoire courant est: "+curDir);
		System.out.println(args[0]);
		System.out.println(args[1]);
		if(args[0].equals("-f")){
			runOnFile( Paths.get(args[1]) );
		}
	}
	
	
	public static void runOnFile( Path path){
		Class aClass;
		try {//"D:\\git\\CauseEffectChains\\src\\classes\\challenges\\FirstChallenge.java"
			aClass = classLoader.loadClass(path);
		
			Object aChallenge= aClass.newInstance();
			System.out.println(path);
			if(aChallenge instanceof Challenge){
				CauseEffectChain cec = d.debug((Challenge) aChallenge);
				System.out.println(cec);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
