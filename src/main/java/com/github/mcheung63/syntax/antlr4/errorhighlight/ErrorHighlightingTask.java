package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.syntax.antlr4.ChooseRealTimecompileFilePanel;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.netbeans.api.editor.EditorRegistry;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorHighlightingTask extends ParserResultTask {

	public static ArrayList<ErrorInfo> errorInfos = new ArrayList<>();

	@Override
	public void run(Result result, SchedulerEvent event) {
		try {
			Document document = result.getSnapshot().getSource().getDocument(false);
			ArrayList<ErrorDescription> errors = new ArrayList<>();
			for (ErrorInfo errorInfo : errorInfos) {
				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
						Severity.ERROR,
						errorInfo.message,
						document,
						document.createPosition(errorInfo.offsetStart),
						document.createPosition(errorInfo.offsetEnd + 1)
				);
				errors.add(errorDescription);
			}
			HintsController.setErrors(document, "simple-antlr-error", errors);

//			TopComponent topComponent = TopComponent.getRegistry().getActivated();
			//print(topComponent, "\t");
//			ModuleLib.log("real time compile 1 " + topComponent.getLookup().lookup(FileTypeG4DataObject.class));
//			ModuleLib.log("real time compile 1.1 " + topComponent.getLookup().lookup(JToolBar.class));
//			ModuleLib.log("real time compile 2 " + MimeLookup.getDefault().lookup(MultiViewElement.class));
//			for (int x = 0; x < MultiViews.findMultiViewHandler(topComponent).getPerspectives().length; x++) {
//				ModuleLib.log("\t" + x + " real time compile 3 " + MultiViews.findMultiViewHandler(topComponent).getPerspectives()[x]);
//			}
			realTimeCompile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Class getSchedulerClass() {
		return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
	}

	@Override
	public void cancel() {
	}

	private void print(JComponent component, String str) {
		ModuleLib.log(str + component);
		for (int x = 0; x < component.getComponentCount(); x++) {
			print((JComponent) component.getComponent(x), str + "\t");
		}
	}

	private void realTimeCompile() {
		try {
			JTextComponent jTextComponent = EditorRegistry.lastFocusedComponent();
			Document doc = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File file = ChooseRealTimecompileFilePanel.maps.get(dataObject);
			if (file == null) {
				return;
			}

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(jTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);
				ModuleLib.log("grammar=" + grammar);
				ModuleLib.log("tool=" + tool);

				for (String rule : grammar.getRuleNames()) {
					ModuleLib.log("rule=" + rule);
				}

				LexerInterpreter lex = grammar.createLexerInterpreter(new ANTLRInputStream(new FileInputStream(file)));
				for (Token token : lex.getAllTokens()) {
					ModuleLib.log("token=" + token);
				}
				lex.reset();

				BaseErrorListener printError = new BaseErrorListener() {
					@Override
					public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol,
							final int line, final int position, final String msg,
							final RecognitionException e) {
						ModuleLib.log("ERROR :" + line + ":" + position + ": " + msg);
					}
				};
				CommonTokenStream tokenStream = new CommonTokenStream(lex);
				ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
				parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				parser.removeErrorListeners();
				parser.addErrorListener(printError);
				String startRule = "assemble";
				Rule start = grammar.getRule(startRule);
				ParserRuleContext parserRuleContext = parser.parse(start.index);
				ModuleLib.log("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());

			}

//			ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(jTextComponent.getText()));
//			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//			ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
//			parser.addErrorListener(new ANTLRErrorListener() {
//				@Override
//				public void syntaxError(Recognizer<?, ?> rcgnzr, Object offendingSymbol, int lineNumber, int charOffsetFromLine, String message, RecognitionException re) {
//					//ModuleLib.log("error " + rcgnzr + ", " + lineNumber + ", " + charOffsetFromLine + ", " + message + ", " + re);
//
//					Token offendingToken = (Token) offendingSymbol;
//					int start = offendingToken.getStartIndex();
//					int stop = offendingToken.getStopIndex();
//					ModuleLib.log(" oh yeah " + start + ", " + stop + " = " + message + ", token=" + offendingToken);
//				}
//
//				@Override
//				public void reportAmbiguity(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
//				}
//
//				@Override
//				public void reportAttemptingFullContext(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
//				}
//
//				@Override
//				public void reportContextSensitivity(org.antlr.v4.runtime.Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
//				}
//			});
//			ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();
//			ParseTreeWalker walker = new ParseTreeWalker();
//			MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
//			walker.walk(listener, context);
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

}
