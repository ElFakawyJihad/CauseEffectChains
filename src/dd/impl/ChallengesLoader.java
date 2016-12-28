package dd.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChallengesLoader extends ClassLoader {
	
	public ChallengesLoader(ClassLoader parent) {
        super(parent);
    }
	
	public Class loadClass(Path name) throws ClassNotFoundException {
        try {
            byte[] classData = Files.readAllBytes(name);

            return defineClass(name.getFileName().toString(),
                    classData, 0, classData.length);

        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
	

}
