package bs;

/**
 * Represente l'exeception generer a la fin d'une trace qui fail
 * @author Admin
 *
 */
public class ChallengeException {
	public int lineNumber;
	public String textCause;
	public String message;

	public ChallengeException(int line, String cause, String mess) {
		lineNumber=line;
		textCause=cause;
		message=mess;
	}
}
