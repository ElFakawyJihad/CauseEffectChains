package dd.impl;

public class FileNotFoundRuntimeException extends RuntimeException {
	public FileNotFoundRuntimeException(Exception e){
		System.out.println(e);
	}
}
