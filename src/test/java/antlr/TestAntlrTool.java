package antlr;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.runtime.tree.Tree;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.tool.Grammar;
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

		String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("Calculator.g4").toURI())));
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

//		GrammarRootAST ast = (GrammarRootAST) t;
			System.out.println(ast.toStringTree());

//		String grammarName = ast.getChild(0).getText();
//		System.out.println("grammarName=" + grammarName);
//		String parserName = grammarName + "Parser";
//		//String lexerName = grammarName+"Lexer";
//		ClassLoader cl = Thread.currentThread().getContextClassLoader();
//		Class<? extends Parser> parserClass = cl.loadClass(parserName).asSubclass(Parser.class);
//		Constructor<? extends Parser> parserCtor = parserClass.getConstructor(TokenStream.class);
//		Parser parser = parserCtor.newInstance((TokenStream) null);
//		GrammarAST tokenVocabNode = findOptionValueAST(root, "tokenVocab");
//		if (tokenVocabNode != null) {
//			String vocabName = tokenVocabNode.getText();
//			ModuleLib.log("vocabName=" + vocabName);
//		}
			Grammar g = tool.createGrammar(ast);
			System.out.println("g=" + g);
			System.out.println("tool=" + tool);
			//tool.process(g, true);

			//Grammar g = new Grammar(tool, (GrammarRootAST) t);
			for (int x = 0; x < ast.getChildCount(); x++) {
				printAST(ast.getChild(x));
			}

			//		GrammarTransformPipeline.setGrammarPtr(g, ast);
			//		System.out.println("g=" + g);
			/*for (String token : g.getTokenNames()) {
				System.out.println("token=" + token);
			}
			for (String ruleName : g.getRuleNames()) {
				System.out.println("ruleName=" + ruleName);
			}*/
			g.createParserInterpreter();
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
