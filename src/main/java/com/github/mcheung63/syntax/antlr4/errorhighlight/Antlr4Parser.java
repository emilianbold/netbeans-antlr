package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.syntax.antlr4.MyANTLRv4ParserListener;
import java.util.BitSet;
import javax.swing.event.ChangeListener;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class Antlr4Parser extends Parser {

	private Snapshot snapshot;
	public int embeddedOffset;

	@Override
	public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
		this.snapshot = snapshot;
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(snapshot.getText().toString()));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
		ErrorHighlightingTask.errorInfos.clear();
		parser.addErrorListener(new ANTLRErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> rcgnzr, Object offendingSymbol, int lineNumber, int charOffsetFromLine, String message, RecognitionException re) {

				Token offendingToken = (Token) offendingSymbol;
				int start = offendingToken.getStartIndex() + snapshot.getOriginalOffset(0);
				int stop = offendingToken.getStopIndex() + snapshot.getOriginalOffset(0);
				//ModuleLib.log("syntaxError " + rcgnzr + ", " + lineNumber + ", " + charOffsetFromLine + ", " + start + ", " + stop + ", " + message + ", " + re);
				//ModuleLib.log("\t\t " + rcgnzr + ", " + offendingToken.getStartIndex() + ", " + offendingToken.getStopIndex());

				ErrorHighlightingTask.errorInfos.add(new ErrorInfo(start, stop, message));
			}

			@Override
			public void reportAmbiguity(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
				ModuleLib.log("reportAmbiguity");
			}

			@Override
			public void reportAttemptingFullContext(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
				ModuleLib.log("reportAttemptingFullContext");
			}

			@Override
			public void reportContextSensitivity(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
				ModuleLib.log("reportContextSensitivity");
			}
		});
		ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();
		ParseTreeWalker walker = new ParseTreeWalker();
		MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
		walker.walk(listener, context);
	}

	@Override
	public Result getResult(Task task) throws ParseException {
		return new Antlr4ParseResult(snapshot);
	}

	@Override
	public void addChangeListener(ChangeListener cl) {
	}

	@Override
	public void removeChangeListener(ChangeListener cl) {
	}

}
