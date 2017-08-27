package antlr;

import com.github.mcheung63.TreeNode;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
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
import org.snt.inmemantlr.utils.Tuple;

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
			
			System.out.println("ast.toStringTree()=" + ast.toStringTree());
			Grammar grammar = tool.createGrammar(ast);
			tool.process(grammar, false);
			System.out.println("grammar=" + grammar);
			System.out.println("tool=" + tool);
			
			for (String rule : grammar.getRuleNames()) {
				System.out.println("rule=" + rule);
			}

			//printAST(ast);
			TreeNode rootNode = buildTree(ast);
			travelTree(rootNode);
			String dot = exportDot(rootNode);
			System.out.println(dot);
			MutableGraph g = Parser.read(dot);
//			BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
//			new JFrameWin(image).setVisible(true);
			Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("/Users/peter/Desktop/test.png"));
			System.out.println(dot);
			
			LexerInterpreter lex = grammar.createLexerInterpreter(new ANTLRInputStream(";comment1"));
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
			ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			parser.removeErrorListeners();
			parser.addErrorListener(printError);
			String startRule = "assemble";
			Rule start = grammar.getRule(startRule);
			ParserRuleContext parserRuleContext = parser.parse(start.index);
			System.out.println("parserRuleContext=" + parserRuleContext);
			while (parserRuleContext.getParent() != null) {
				parserRuleContext = parserRuleContext.getParent();
			}
			System.out.println("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());
		}
	}
	
	private void printAST(GrammarRootAST ast) {
		for (int x = 0; x < ast.getChildCount(); x++) {
			printAST(ast.getChild(x), "");
		}
	}
	
	private void printAST(Tree child, String prefix) {
		if (child.getText().equals("BLOCK")) {
			prefix += " --- ";
		}
		System.out.println(prefix + "-" + child.getText());
		prefix += " --- ";
		for (int x = 0; x < child.getChildCount(); x++) {
			printAST(child.getChild(x), prefix);
		}
	}
	
	private TreeNode<Tree> buildTree(GrammarRootAST ast) {
		TreeNode<Tree> node = new TreeNode<Tree>(ast);
		TreeNode<Tree> lastNode = null;
		for (int x = 0; x < ast.getChildCount(); x++) {
			Tuple<Boolean, TreeNode<Tree>> tuple = buildTree(ast.getChild(x), null);
			
			if (tuple.getFirst() && lastNode != null) {
				lastNode.children.add(tuple.getSecond());
			} else {
				lastNode = tuple.getSecond();
				node.children.add(tuple.getSecond());
			}
		}
		
		return node;
	}
	
	private Tuple<Boolean, TreeNode<Tree>> buildTree(Tree child, TreeNode<Tree> lastNode) {
		boolean shouldPutInChild = false;
		if (child.getText().equals("RULES") || child.getText().equals("BLOCK")) {
			shouldPutInChild = true;
		}
		TreeNode<Tree> node = new TreeNode<Tree>(child);
		for (int x = 0; x < child.getChildCount(); x++) {
			Tuple<Boolean, TreeNode<Tree>> tuple = buildTree(child.getChild(x), lastNode);
			if (tuple.getFirst() && lastNode != null) {
				lastNode.children.add(tuple.getSecond());
			} else {
				lastNode = tuple.getSecond();
				node.children.add(tuple.getSecond());
			}
		}
		return new Tuple(shouldPutInChild, node);
	}
	
	private void travelTree(TreeNode<Tree> rootNode) {
		System.out.println(rootNode.object + ", " + rootNode.object.getType());
		for (int x = 0; x < rootNode.children.size(); x++) {
			travelTree(rootNode.children.get(x), "  ");
		}
	}
	
	private void travelTree(TreeNode<Tree> rootNode, String prefix) {
		System.out.println(prefix + rootNode.object);
		for (int x = 0; x < rootNode.children.size(); x++) {
			travelTree(rootNode.children.get(x), prefix + " +-- ");
		}
	}
	
	private String exportDot(TreeNode<Tree> rootNode) {
		StringBuffer sb = new StringBuffer();
		sb.append("digraph \"Grammar\" {\n"
				+ "	graph [	"
				+ "		fontname = \"Arial\",\n"
				+ "		splines  = ortho,\n"
				+ "		fontsize = 12,"
				+ "		rankdir=\"LR\",\n"
				+ "	];\n"
				+ "	node [	"
				+ "		shape    = \"box\",\n"
				+ "      style   = \"rounded\",\n"
				+ "		color    = white,\n"
				+ "		fontname = \"Arial\"\n"
				+ "];\n");
		for (int x = 0; x < rootNode.children.size(); x++) {
			sb.append("\"" + rootNode.object + "\"");
			exportDot(rootNode.children.get(x), sb, rootNode.object.getText());
		}

//		int level = 0;
//		HashMap<Integer, List<String>> nodesInLevel = new HashMap();
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			rank(rootNode.children.get(x), sb, rootNode.object.getText(), nodesInLevel, level);
//		}
//
//		for (Integer tempLevel : nodesInLevel.keySet()) {
//			List<String> list = nodesInLevel.get(tempLevel);
//			sb.append("	{\n");
//			sb.append("		rank = same\n");
//			for (String tempNode : list) {
//				sb.append("		\"" + tempNode + "\"\n");
//				if (!list.iterator().hasNext()) {
//					sb.append(",\n");
//				}
//			}
//			sb.append("	}\n");
//		}
		sb.append("}\n");
		return sb.toString();
	}
	
	private void exportDot(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode) {
		String nodeText = rootNode.object.getText() + "-" + new Random().nextInt(100000);
		sb.append("\"" + lastDotNode + "\" -> \"" + nodeText + "\"\n");
		lastDotNode = nodeText;
		for (int x = 0; x < rootNode.children.size(); x++) {
			exportDot(rootNode.children.get(x), sb, lastDotNode);
		}
	}
	
	private void rank(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode, HashMap<Integer, List<String>> nodesInLevel, int level) {
		String nodeText = rootNode.object.getText();
		List<String> list = nodesInLevel.get(level);
		if (list == null) {
			list = new ArrayList<String>();
			nodesInLevel.put(level, list);
		}
		list.add(nodeText);
		for (int x = 0; x < rootNode.children.size(); x++) {
			rank(rootNode.children.get(x), sb, lastDotNode, nodesInLevel, level + 1);
		}
	}

//	private void exportDot(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode, int childIndex) {
//		String nodeText = rootNode.object.getText() + childIndex;
//		sb.append("\"" + lastDotNode + "\" -> \"" + nodeText + "\"\n");
//		lastDotNode = nodeText;
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			exportDot(rootNode.children.get(x), sb, lastDotNode, x);
//		}
//	}
//
//	private void rank(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode, HashMap<Integer, List<String>> nodesInLevel, int level, int childIndex) {
//		String nodeText = rootNode.object.getText() + childIndex;
//		List<String> list = nodesInLevel.get(level);
//		if (list == null) {
//			list = new ArrayList<String>();
//			nodesInLevel.put(level, list);
//		}
//		list.add(nodeText);
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			rank(rootNode.children.get(x), sb, lastDotNode, nodesInLevel, level + 1, x);
//		}
//	}
}
