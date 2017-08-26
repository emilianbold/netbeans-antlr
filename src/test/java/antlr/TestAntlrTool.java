package antlr;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.runtime.tree.Tree;
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
import org.antlr.v4.tool.ast.GrammarAST;
import org.antlr.v4.tool.ast.GrammarASTErrorNode;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.junit.Test;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class TestAntlrTool {

	@Test
	public void testTool() throws Exception {
//		String[] arg0 = {pathOfG4File, "-package", "mypackage"};
//        Tool tool = new Tool(arg0);
		Tool tool = new Tool();
		String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("Assembler.g4").toURI())));
		GrammarRootAST ast = tool.parseGrammarFromString(content);
		System.out.println("ast.grammarType=" + ast.grammarType);
		if (ast.grammarType == ANTLRParser.COMBINED) {
			System.out.println("ast=" + ast);
			if ((GrammarAST) ast instanceof GrammarASTErrorNode) {
				System.out.println("GrammarASTErrorNode");
				return;
			}

			if (ast.hasErrors) {
				System.out.println("hasErrors");
				return;
			}

			System.out.println(ast.toStringTree());
			Grammar g = tool.createGrammar(ast);
			tool.process(g, false);
			System.out.println("g=" + g);
			System.out.println("tool=" + tool);
			for (int x = 0; x < ast.getChildCount(); x++) {
				printAST(ast.getChild(x));
			}
			LexerInterpreter lex = g.createLexerInterpreter(new ANTLRInputStream(";comment1"));
			for (Token token : lex.getAllTokens()) {
				System.out.println("token=" + token);
			}
			lex.reset();

			BaseErrorListener printError = new BaseErrorListener() {
				@Override
				public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol,
						final int line, final int position, final String msg,
						final RecognitionException e) {
					System.out.println(line + ":" + position + ": " + msg);
				}
			};

			CommonTokenStream tokenStream = new CommonTokenStream(lex);
			ParserInterpreter parser = g.createParserInterpreter(tokenStream);
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			parser.removeErrorListeners();
			parser.addErrorListener(printError);
			String startRule = "assemble";
			Rule start = g.getRule(startRule);
			ParserRuleContext parserRuleContext = parser.parse(start.index);
			System.out.println("parserRuleContext=" + parserRuleContext);
			while (parserRuleContext.getParent() != null) {
				parserRuleContext = parserRuleContext.getParent();
			}
			System.out.println("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());
		}
	}

	private void printAST(Tree child) {
		printAST(child, "");
	}

	private void printAST(Tree child, String prefix) {
		System.out.println(prefix + "-" + child);
		prefix += "  ";
		if (child.getChildCount() > 0) {
			for (int x = 0; x < child.getChildCount(); x++) {
				printAST(child.getChild(x), prefix);
			}
		}
	}
}
