package antlr;

import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.antlr.v4.runtime.tree.Tree;
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
public class TestAntlrToolGrammarTree {

	@Test
	public void testToolGrammarTreeProgramatically() throws Exception {
//		String[] arg0 = {pathOfG4File, "-package", "mypackage"};
//        Tool tool = new Tool(arg0);
		Tool tool = new Tool();
		String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("Assembler.g4").toURI())));
		GrammarRootAST ast = tool.parseGrammarFromString(content);
		printAST(ast, "");
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

//			DefaultToolListener defaultToolListener = new DefaultToolListener(tool);
//			System.out.println("ast.class=" + ast.getClass());
//			System.out.println("ast.toStringTree()=" + ast.toStringTree().replaceAll(" \\(RULE", "\n \\(RULE"));
//			System.out.println("2 ast.toStringTree()=" + toStringTree(ast, ""));
			Grammar grammar = tool.createGrammar(ast);
			
			tool.process(grammar, false);
			System.out.println("grammar=" + grammar);
			System.out.println("tool=" + tool);

			for (String rule : grammar.getRuleNames()) {
				System.out.println("rule=" + rule);
			}
//			TreeNode<Tree> rootNode = new TreeNode<Tree>(null);
//			buildTree(ast, rootNode);
//			travelTree(rootNode);
//			String dot = exportDot(rootNode);
//			System.out.println(dot);
//			MutableGraph g = Parser.read(dot);
//			BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
//			new JFrameWin(image).setVisible(true);
//			Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("/Users/peter/Desktop/test.png"));

			LexerInterpreter lex = grammar.createLexerInterpreter(new ANTLRInputStream(";comment1\nmov ac,bx"));
			for (Token token : lex.getAllTokens()) {
				System.out.println("token=" + token);
			}
			lex.reset();

			BaseErrorListener printError = new BaseErrorListener() {
				@Override
				public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol,
						final int line, final int position, final String msg,
						final RecognitionException e) {
					System.out.println("ERROR :" + line + ":" + position + ": " + msg);
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
			System.out.println("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());
		}
	}

	private void printAST(GrammarAST child, String prefix) {
		System.out.println(prefix + child.toString());
		for (int x = 0; x < child.getChildCount(); x++) {
			printAST((GrammarAST) child.getChild(x), prefix + "+---");
		}
	}

//	private TreeNode<Tree> buildTree(GrammarAST ast, TreeNode<Tree> lastNode) {
//		if (ast.getChildren() == null || ast.getChildren().isEmpty()) {
//			TreeNode<Tree> temp = new TreeNode<Tree>(ast);
//			return temp;
//		}
//
//		TreeNode<Tree> temp = new TreeNode<Tree>(ast);
//		lastNode.children.add(temp);
//
//		for (int i = 0; ast.getChildren() != null && i < ast.getChildren().size(); i++) {
//			Tree t = (Tree) ast.getChildren().get(i);
//
//			temp.children.add(buildTree((GrammarAST) t, temp));
//		}
//		return lastNode;
//	}
//	private void travelTree(TreeNode<Tree> rootNode) {
//		if (rootNode.object != null) {
//			System.out.println(rootNode.object + ", " + rootNode.object.getType());
//		}
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			travelTree(rootNode.children.get(x), " +++ ");
//		}
//	}
//
//	private void travelTree(TreeNode<Tree> rootNode, String prefix) {
//		System.out.println(prefix + rootNode.object);
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			travelTree(rootNode.children.get(x), prefix + " +-- ");
//		}
//	}
//
//	private String exportDot(TreeNode<Tree> rootNode) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("digraph \"Grammar\" {\n"
//				+ "	graph [	"
//				+ "		fontname = \"Arial\",\n"
//				+ "		splines  = ortho,\n"
//				+ "		fontsize = 12,"
//				+ "		rankdir=\"LR\",\n"
//				+ "	];\n"
//				+ "	node [	"
//				+ "		shape    = \"box\",\n"
//				+ "     style    = \"filled\",\n"
//				+ "		fontname = \"Arial\"\n"
//				+ "];\n");
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			sb.append("\"" + rootNode.object + "\"");
//			exportDot(rootNode.children.get(x), sb, rootNode.object.getText());
//		}
//
////		int level = 0;
////		HashMap<Integer, List<String>> nodesInLevel = new HashMap();
////		for (int x = 0; x < rootNode.children.size(); x++) {
////			rank(rootNode.children.get(x), sb, rootNode.object.getText(), nodesInLevel, level);
////		}
////
////		for (Integer tempLevel : nodesInLevel.keySet()) {
////			List<String> list = nodesInLevel.get(tempLevel);
////			sb.append("	{\n");
////			sb.append("		rank = same\n");
////			for (String tempNode : list) {
////				sb.append("		\"" + tempNode + "\"\n");
////				if (!list.iterator().hasNext()) {
////					sb.append(",\n");
////				}
////			}
////			sb.append("	}\n");
////		}
//		sb.append("}\n");
//		return sb.toString();
//	}
//
//	private void exportDot(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode) {
//		String nodeText = rootNode.object.getText();
//		String nodeTextWithID = rootNode.object.getText() + "-" + new Random().nextInt(100000);
////		if (rootNode.object.getText().equals("RULE") || rootNode.object.getText().equals("ALT")) {
////			sb.append("\"" + lastDotNode + "\" -> \"" + nodeText + "\" [ style = invis ]\n");
////		} else {
//
////		if (nodeText.equals("RULE")) {
////			sb.append("subgraph {\n");
////		}
////		if (nodeText.equals("BLOCK") || nodeText.equals("ALT")) {
////			sb.append("\"" + nodeTextWithID + "\" [ label=\"" + nodeText + "\"]\n");
////			sb.append("\"" + lastDotNode + "\" -> \"" + nodeTextWithID + "\" [dir=none] \n");
////		} else {
//		sb.append("\"" + nodeTextWithID + "\" [ label=\"" + nodeText + "\" ]\n");
//		sb.append("\"" + lastDotNode + "\" -> \"" + nodeTextWithID + "\"\n");
////		}
////		}
//		for (int x = 0; x < rootNode.children.size(); x++) {
////			if (nodeText.equals("BLOCK") || nodeText.equals("ALT")) {
////				exportDot(rootNode.children.get(x), sb, lastDotNode);
////			} else {
//			exportDot(rootNode.children.get(x), sb, nodeTextWithID);
////			}
//		}
//
////		if (nodeText.equals("RULE")) {
////			sb.append("}\n");
////		}
//	}
//
//	private void rank(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode, HashMap<Integer, List<String>> nodesInLevel, int level) {
//		String nodeText = rootNode.object.getText();
//		List<String> list = nodesInLevel.get(level);
//		if (list == null) {
//			list = new ArrayList<String>();
//			nodesInLevel.put(level, list);
//		}
//		list.add(nodeText);
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			rank(rootNode.children.get(x), sb, lastDotNode, nodesInLevel, level + 1);
//		}
//	}
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
//	public String toStringTree(GrammarAST ast, String prefix) {
//		if (ast.getChildren() == null || ast.getChildren().isEmpty()) {
//			return ast.toString();
//		}
//		StringBuilder buf = new StringBuilder();
//		prefix += " --- ";
//		buf.append("\n" + prefix + "(");
//		buf.append(ast.getText());
//		buf.append(" \t");
//		for (int i = 0; ast.getChildren() != null && i < ast.getChildren().size(); i++) {
//			Tree t = (Tree) ast.getChildren().get(i);
//			if (i > 0) {
//				buf.append(' ');
//			}
//			buf.append(toStringTree((GrammarAST) t, prefix));
//		}
//		buf.append(")");
//		return buf.toString();
//	}
}
