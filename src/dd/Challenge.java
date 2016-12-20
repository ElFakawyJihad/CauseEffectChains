package dd;

import java.util.List;

import static javafx.scene.input.KeyCode.T;

/**
 * Defines a challenge asked to the others
 *
 * An example challenge name is GroupAChallenge1.java.
 */
public interface Challenge<I> {
	/** Gives the actual value of I */
	public Class<? extends I> getInputFormat();

	/** Gives a list of inputs */
	public List<I> getInputs();

	/** perform the computation, without any assert */
	public Object doIt(I input);

	/** the core challenge function to debug. if it depends on additional classes they can be put as inner class or as package-visible classes
	 * in the same file. */
	public void challenge(I input);

}
