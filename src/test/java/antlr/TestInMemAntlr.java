package antlr;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.antlr.v4.Tool;
import static org.antlr.v4.Tool.findOptionValueAST;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.ast.GrammarAST;
import org.antlr.v4.tool.ast.GrammarASTErrorNode;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.junit.Test;
import org.snt.inmemantlr.GenericParser;
import org.snt.inmemantlr.listener.DefaultTreeListener;
import org.snt.inmemantlr.tree.Ast;
import org.snt.inmemantlr.tree.AstNode;

public class TestInMemAntlr {

	//@Test
	public void test() throws Exception {
		try {
			System.out.println(Paths.get(getClass().getResource("Calculator.g4").toURI()));
			String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("Calculator.g4").toURI())));
			GenericParser gp = new GenericParser(content);
			DefaultTreeListener treeListener = new DefaultTreeListener();
			gp.setListener(treeListener);
			gp.compile();

//			MemoryTupleSet set = gp.getAllCompiledObjects();
//			for (MemoryTuple tup : set) {
//				System.out.println("tuple name " + tup.getClassName());
//				System.out.println("source " + tup.getSource().getClassName());
//				for (MemoryByteCode mc : tup.getByteCodeObjects()) {
//					Objects.requireNonNull(mc, "MemoryByteCode must not be null");
//					System.out.println("bc name: " + mc.getClassName());
//
//					if (!mc.isInnerClass()) {
//						mc.getClassName().equals(tup.getSource().getClassName());
//					} else {
//						mc.getClassName().startsWith(tup.getSource().getClassName());
//					}
//				}
//			}
			ParserRuleContext ctx = gp.parse("1+2*(3+4)");
			Ast ast = treeListener.getAst();
			List<AstNode> nodes = ast.getNodes();
			for (AstNode n : nodes) {
				loop("", n);
			}
			System.out.println(ast.toDot());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void loop(String s, AstNode n) {
		System.out.println(s + "n=" + n);
		for (AstNode nn : n.getChildren()) {
			loop(s + "    ", nn);
		}
	}

	@Test
	public void testTool() throws Exception {
		Tool tool = new Tool();
		String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("Calculator.g4").toURI())));
		GrammarAST t = tool.parseGrammarFromString(content);
		System.out.println("ast=" + t);
		if (t instanceof GrammarASTErrorNode) {
			System.out.println("error");
			return;
		}

		if (((GrammarRootAST) t).hasErrors) {
			System.out.println("hasErrors");
			return;
		}

		GrammarRootAST root = (GrammarRootAST) t;

		String grammarName = root.getChild(0).getText();
		System.out.println("grammarName=" + grammarName);

		GrammarAST tokenVocabNode = findOptionValueAST(root, "tokenVocab");
		// Make grammars depend on any tokenVocab options
		if (tokenVocabNode != null) {
			String vocabName = tokenVocabNode.getText();
			//g.addEdge(grammarName, vocabName);
		}
		// add cycle to graph so we always process a grammar if no error
		// even if no dependency
		//g.addEdge(grammarName, grammarName);
	}
}
