package com.github.mcheung63.syntax.antlr4.errorhighlight;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
class ErrorInfo {

	public int offsetStart;
	public int offsetEnd;
	public String message;

	public ErrorInfo(int offsetStart, int offsetEnd, String message) {
		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		this.message = message;
	}
}
