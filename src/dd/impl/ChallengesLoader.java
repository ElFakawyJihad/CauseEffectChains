package dd.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class ChallengesLoader extends ClassLoader {
	protected static Pattern p = Pattern.compile("[\\\\/]");

	public ChallengesLoader(ClassLoader parent) {
		super(parent);
	}

	public Class loadClass(Path name) throws ClassNotFoundException {
		try {
			byte[] classData = Files.readAllBytes(name);
			String[] nameArr = p.split(name.toString());
			nameArr[nameArr.length - 1] = nameArr[nameArr.length - 1].substring(0,
					nameArr[nameArr.length - 1].lastIndexOf("."));

			return tryFindClass(nameArr, 1, classData);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Class tryFindClass(String[] name, int deep, byte[] classData) {
		if(deep>name.length){
			System.out.println("Class not found");
			return null;
		}
		try {
			String className="";
			for(int i=name.length-deep; i<name.length-1;i++){
				className=className+name[i]+ ".";
			}
			className=className+name[name.length-1];
			return defineClass(className, classData, 0, classData.length);
		} catch (NoClassDefFoundError e) {
			return tryFindClass(name, deep+1, classData);
		}
	}

}