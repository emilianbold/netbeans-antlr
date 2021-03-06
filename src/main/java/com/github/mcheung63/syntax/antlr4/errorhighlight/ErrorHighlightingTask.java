package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.syntax.antlr4.realtimecompile.RealTimeCompileErrorListener;
import com.github.mcheung63.FileTypeG4VisualElement;
import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.syntax.antlr4.ChooseRealTimecompileFilePanel;
import com.github.mcheung63.syntax.antlr4.realtimecompile.RealTimeCompileHighlighter;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.netbeans.api.editor.EditorRegistry;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.netbeans.api.editor.settings.AttributesUtilities;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.editor.lib2.highlighting.ProxyHighlightsContainer;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.highlighting.HighlightsContainer;
import org.netbeans.spi.editor.highlighting.HighlightsSequence;
import org.netbeans.spi.editor.highlighting.support.OffsetsBag;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorHighlightingTask extends ParserResultTask {

	public static ArrayList<ErrorInfo> errorInfos = new ArrayList<>();
	public static ArrayList<ErrorInfo> targetErrorInfos = new ArrayList<>();

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

	private void realTimeCompile() {
		try {
			JTextComponent currentTextComponent = EditorRegistry.lastFocusedComponent();
			if (currentTextComponent == null) {
				return;
			}
			Document doc = currentTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File targetFile = FileTypeG4VisualElement.maps.get(dataObject);
			if (targetFile == null) {
				ModuleLib.log("targetFile is null");
				return;
			}
			FileObject fileObject = FileUtil.toFileObject(targetFile);
			DataObject targetDataObject = DataObject.find(fileObject);
			EditorCookie ecA = targetDataObject.getLookup().lookup(EditorCookie.class);
			Document targetDoc = ecA.getDocument();
			if (targetDoc == null) {
				ModuleLib.log("targetDoc is null");
				return;
			}

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(currentTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);

				targetErrorInfos.clear();
				RealTimeCompileErrorListener targetErrorListener = new RealTimeCompileErrorListener(targetDataObject, targetErrorInfos);
				LexerInterpreter lexer = grammar.createLexerInterpreter(new ANTLRInputStream(new FileInputStream(targetFile)));
				lexer.removeErrorListeners();
				lexer.addErrorListener(targetErrorListener);
//				for (Token token : lexer.getAllTokens()) {
//					ModuleLib.log("token=" + token + " = " + grammar.getTokenNames()[token.getType()]);
//				}
//				lexer.reset();

				CommonTokenStream tokenStream = new CommonTokenStream(lexer);
				ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
				parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				parser.removeErrorListeners();
				parser.addErrorListener(targetErrorListener);
				String startRule = FileTypeG4VisualElement.startRules.get(dataObject);
				Rule start = grammar.getRule(startRule);
				if (start == null) {
					return;
				}
				ParserRuleContext parserRuleContext = parser.parse(start.index);

				RealTimeCompileHighlighter realTimeCompileHighlighter = (RealTimeCompileHighlighter) targetDoc.getProperty(RealTimeCompileHighlighter.class);
				if (realTimeCompileHighlighter != null) {
					realTimeCompileHighlighter.bag.clear();
					for (ErrorInfo errorInfo : ErrorHighlightingTask.targetErrorInfos) {
						realTimeCompileHighlighter.bag.addHighlight(errorInfo.offsetStart, errorInfo.offsetEnd, realTimeCompileHighlighter.defaultColors);
					}

					OffsetsBag bag = new OffsetsBag(doc);
					bag.addHighlight(5, 10, AttributesUtilities.createImmutable(StyleConstants.Background, Color.cyan));
					HighlightsContainer[] layers = new HighlightsContainer[]{bag};
					ProxyHighlightsContainer hb = new ProxyHighlightsContainer(doc, layers);
//				HighlightsSequence hs = hb.getHighlights(0, Integer.MAX_VALUE);
					hb.setLayers(doc, layers);

					TopComponent topComponent = TopComponent.getRegistry().getActivated();
					ChooseRealTimecompileFilePanel chooseRealTimecompileFilePanel = (ChooseRealTimecompileFilePanel) ModuleLib.getJComponent(topComponent, ChooseRealTimecompileFilePanel.class, "\t");
					if (chooseRealTimecompileFilePanel != null) {
						if (targetErrorListener.compilerError) {
							chooseRealTimecompileFilePanel.compileStatusLabel.setBackground(Color.red);
						} else {
							chooseRealTimecompileFilePanel.compileStatusLabel.setBackground(Color.green);
						}
					}
				}
			}
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

}
