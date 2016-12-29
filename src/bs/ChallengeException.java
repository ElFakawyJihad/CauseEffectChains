package bs;

public class ChallengeException {
	int lineNumber;
	String textCause;
	String message;

	public ChallengeException(int line, String cause, String mess) {
		lineNumber=line;
		textCause=cause;
		message=mess;
	}
}
