package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.FileTypeG4VisualElement;
import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.syntax.antlr4.ChooseRealTimecompileFilePanel;
import com.github.mcheung63.syntax.antlr4.RealTimeCompileHighlighter;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
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
import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
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
			JTextComponent jTextComponent = EditorRegistry.lastFocusedComponent();
			if (jTextComponent == null) {
				return;
			}
			Document doc = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File targetFile = FileTypeG4VisualElement.maps.get(dataObject);
			if (targetFile == null) {
				return;
			}
			FileObject fileObject = FileUtil.toFileObject(targetFile);
			DataObject targetDataObject = DataObject.find(fileObject);
			EditorCookie ecA = targetDataObject.getLookup().lookup(EditorCookie.class);
			Document targetDoc = ecA.getDocument();

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(jTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);

				targetErrorInfos.clear();
				MyBaseErrorListener targetErrorListener = new MyBaseErrorListener(targetDataObject, targetErrorInfos);
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

				RealTimeCompileHighlighter realTimeCompileHighlight = (RealTimeCompileHighlighter) targetDoc.getProperty(RealTimeCompileHighlighter.class);
				for (ErrorInfo errorInfo : ErrorHighlightingTask.targetErrorInfos) {
					realTimeCompileHighlight.bag.addHighlight(errorInfo.offsetStart, errorInfo.offsetEnd, realTimeCompileHighlight.defaultColors);
				}

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
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

}
