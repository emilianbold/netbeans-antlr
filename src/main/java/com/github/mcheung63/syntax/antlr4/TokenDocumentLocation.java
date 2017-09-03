package com.github.mcheung63.syntax.antlr4;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public class TokenDocumentLocation {

	public String rule;
	public String text;
	public int start;
	public int stop;

	public String toString() {
		return rule + ", " + text + ", " + start + " -> " + stop;
	}
}
