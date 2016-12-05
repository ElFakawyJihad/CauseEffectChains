package fr.univ_lille1.m2iagl.dd;

import java.util.List;

/**
 * Defines a challenge asked to the others
 */
public interface Challenge<I> {
	Class<? extends I> getInputFormat();
	List<I> getInputs();
	boolean oracle(I input);
	String getJavaProgram();
}
