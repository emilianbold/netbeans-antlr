package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.FileTypeG4VisualElement;
import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.syntax.antlr4.ChooseRealTimecompileFilePanel;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
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
import org.netbeans.api.editor.settings.AttributesUtilities;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
import org.netbeans.spi.editor.highlighting.support.OffsetsBag;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.cookies.EditorCookie;
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
			Document doc = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File file = FileTypeG4VisualElement.maps.get(dataObject);
			if (file == null) {
				return;
			}

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(jTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);
				System.out.println("grammar=" + grammar);
				System.out.println("tool=" + tool);

//				for (String rule : grammar.getRuleNames()) {
//					ModuleLib.log("rule=" + rule);
//				}
//				for (String displayName : grammar.getTokenDisplayNames()) {
//					ModuleLib.log("displayNames=" + displayName);
//				}
//				for (String literalName : grammar.getTokenLiteralNames()) {
//					ModuleLib.log("literalName=" + literalName);
//				}
//				for (String tokenName : grammar.getTokenNames()) {
//					ModuleLib.log("tokenName=" + tokenName);
//				}
				MyBaseErrorListener errorListener = new MyBaseErrorListener();

				LexerInterpreter lexer = grammar.createLexerInterpreter(new ANTLRInputStream(new FileInputStream(file)));
				lexer.removeErrorListeners();
				lexer.addErrorListener(errorListener);
				for (Token token : lexer.getAllTokens()) {
					ModuleLib.log("token=" + token + " = " + grammar.getTokenNames()[token.getType()]);
				}
				lexer.reset();

				CommonTokenStream tokenStream = new CommonTokenStream(lexer);
				ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
				parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				parser.removeErrorListeners();
				parser.addErrorListener(errorListener);
				String startRule = "assemble";
				Rule start = grammar.getRule(startRule);
				ParserRuleContext parserRuleContext = parser.parse(start.index);

				//ModuleLib.log("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());
				TopComponent topComponent = TopComponent.getRegistry().getActivated();
				//ModuleLib.print(topComponent, "\t");
				ChooseRealTimecompileFilePanel chooseRealTimecompileFilePanel = (ChooseRealTimecompileFilePanel) ModuleLib.getJComponent(topComponent, ChooseRealTimecompileFilePanel.class, "\t");
				if (errorListener.compilerError) {
					chooseRealTimecompileFilePanel.compileStatusLabel.setBackground(Color.red);
				} else {
					chooseRealTimecompileFilePanel.compileStatusLabel.setBackground(Color.green);
				}

				Highlighter highlighter = jTextComponent.getHighlighter();
				HighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
				highlighter.addHighlight(5, 10, highlightPainter);
//			jTextComponent.repaint();

				EditorCookie ec = dataObject.getLookup().lookup(EditorCookie.class);
				Document doc2 = ec.getDocument();
				OffsetsBag bag = new OffsetsBag(doc2, true);
				AttributeSet defaultColors = AttributesUtilities.createImmutable(StyleConstants.Background, new Color(0, 0, 255));
				bag.addHighlight(5, 10, defaultColors);
				AttributeSet defaultColors2 = AttributesUtilities.createImmutable(StyleConstants.Foreground, new Color(0, 255, 255));
				bag.addHighlight(2, 5, defaultColors2);

//				StyleContext sc = StyleContext.getDefaultStyleContext();
//				AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.blue);
//				aset = sc.addAttribute(aset, StyleConstants.Background, Color.red);
//				((JTextPane) jTextComponent).setCharacterAttributes(aset, true);
//			getBag(doc).setHighlights(bag);
//			ArrayList<Lookup> lookups = new ArrayList<Lookup>();
//			for (MimePath mimePath : mimePaths) {
//				lookups.add(MimeLookup.getLookup(mimePath));
//			}
//			ProxyLookup lookup = new ProxyLookup(lookups.toArray(new Lookup[lookups.size()]));
				Lookup.Result<HighlightsLayerFactory> factories = Utilities.actionsGlobalContext().lookup(new Lookup.Template<HighlightsLayerFactory>(HighlightsLayerFactory.class));
				//ModuleLib.log("factories=" + factories);
				Collection<? extends HighlightsLayerFactory> all = factories.allInstances();
				ModuleLib.log("all = " + all);
				for (HighlightsLayerFactory fac : all) {
					ModuleLib.log("fac = " + fac);
				}

//			HighlightsContainer hc = HighlightingManager.getInstance(jTextComponent).getBottomHighlights();
			}
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		} catch (BadLocationException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

}
