package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.ModuleLib;
import java.util.ArrayList;
import java.util.BitSet;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.modules.parsing.spi.Parser;
import org.openide.loaders.DataObject;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class MyBaseErrorListener extends BaseErrorListener {

	public boolean compilerError = false;
	DataObject dataObject;
	ArrayList<ErrorInfo> targetErrorInfos;

	MyBaseErrorListener(DataObject targetDataObject, ArrayList<ErrorInfo> targetErrorInfos) {
		dataObject = targetDataObject;
		this.targetErrorInfos = targetErrorInfos;
	}

	@Override
	public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int position, final String msg, final RecognitionException e) {
		compilerError = true;

		Token offendingToken = (Token) offendingSymbol;
		int start = offendingToken.getStartIndex();
		int stop = offendingToken.getStopIndex();
		ModuleLib.log("ERROR " + line + ":" + position + ", " + start + ", " + stop + ": " + msg);
		targetErrorInfos.add(new ErrorInfo(start, stop, msg));
	}

	public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
		ModuleLib.log("reportAmbiguity");
	}

	public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
		ModuleLib.log("reportAttemptingFullContext");
	}

	public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
		ModuleLib.log("reportContextSensitivity");
	}
}
