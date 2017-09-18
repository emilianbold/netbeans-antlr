package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.ModuleLib;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class MyBaseErrorListener extends BaseErrorListener {

	public boolean compilerError = false;

	@Override
	public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int position, final String msg, final RecognitionException e) {
		compilerError = true;
		ModuleLib.log("ERROR :" + line + ":" + position + ": " + msg);
	}
}
