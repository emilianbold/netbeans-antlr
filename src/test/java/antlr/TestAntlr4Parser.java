package antlr;

import com.github.mcheung63.syntax.antlr4.Ast;
import com.github.mcheung63.syntax.antlr4.AstNode;
import com.github.mcheung63.syntax.antlr4.MyANTLRv4ParserListener;
import java.io.File;
import java.util.stream.IntStream;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.parser.antlr4.ANTLRv4Parser.GrammarSpecContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import static org.apache.commons.exec.util.MapUtils.prefix;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class TestAntlr4Parser {

	static int id = 0;

	@Test
	public void testAntrParser() throws Exception {
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(getClass().getResourceAsStream("Assembler.g4")));
//		Token token = lexer.nextToken();
//		while (token.getType() != Lexer.EOF) {
//			System.out.println(token + "=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()));
//			token = lexer.nextToken();
//		}
//
//		lexer.reset();
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
		GrammarSpecContext context = parser.grammarSpec();
		ParseTreeWalker walker = new ParseTreeWalker();
		MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
		walker.walk(listener, context);

		Ast ast = listener.ast;
		AstNode root = ast.getRoot();
		filterUnwantedSubNodes(root, new String[]{"ruleblock"});
		//removeOneLeftNodes(root);
		printAst("", root);

		String dot = exportDot(root);

		System.out.println(dot);

		FileUtils.writeStringToFile(new File("/Users/peter/Desktop/a.dot"), dot, "UTF-8");

//		MutableGraph g = Parser.read(dot);
//		Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("/Users/peter/Desktop/test.png"));
	}

	void removeOneLeftNodes(AstNode node) {
		for (int x = 0; x < node.getChildren().size(); x++) {
			AstNode nn = node.getChild(x);
			if (nn.getChildren().size() == 1) {
				System.out.println("bingo " + processLabel(node.getRule(), node.getLabel()));
				AstNode nextNextNode = nn.getChild(0);
				while (nextNextNode.getChildren().size() == 1) {
					nextNextNode = nextNextNode.getChild(0);
					System.out.println("\t\t\tmiddle " + processLabel(nextNextNode.getRule(), nextNextNode.getLabel()));
				}
				System.out.println("\t\t\t\tfinal " + processLabel(nextNextNode.getRule(), nextNextNode.getLabel()));
				node.getChildren().remove(nn);
				node.addChild(nextNextNode);
			}
			removeOneLeftNodes(nn);
		}

		for (AstNode nn : node.getChildren()) {
			removeOneLeftNodes(nn);
		}
	}

	void filterUnwantedSubNodes(AstNode node, String[] unwantedString) {
		String realLabel = node.getLabel().replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		if (realLabel.contains(":")) {
			realLabel = realLabel.split(":")[0];
		}
		for (String s : unwantedString) {
			if (node.getRule().toLowerCase().contains("ruleblock")) {
				node.getChildren().clear();
				return;
			}
		}
		for (AstNode nn : node.getChildren()) {
			filterUnwantedSubNodes(nn, unwantedString);
		}
	}

	void printAst(String prefix, AstNode node) {
		String realLabel = node.getLabel().replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		if (realLabel.contains(":")) {
			realLabel = realLabel.split(":")[0];
		}
		System.out.println(prefix + node.getRule() + "[" + realLabel + "]");
		for (AstNode nn : node.getChildren()) {
			printAst(prefix + "    ", nn);
		}
	}

	private String exportDot(AstNode node) {
		StringBuffer sb = new StringBuffer();
		sb.append("digraph \"Grammar\" {\n"
				+ "	graph [	"
				+ "		fontname = \"Arial\",\n"
				+ "		splines  = ortho,\n"
				+ "		fontsize = 8,"
				+ "		rankdir=\"LR\",\n"
				+ "	];\n"
				+ "	node [	"
				+ "		shape    = \"box\",\n"
				+ "     style    = \"filled\",\n"
				+ "		fontname = \"Arial\"\n"
				+ "];\n");

		for (AstNode nn : node.getChildren()) {
			if (nn.getRule().toLowerCase().contains("ruleblock")) {
				continue;
			}
			exportDotChild(node.getRule(), nn, sb);
		}
		sb.append("}\n");
		return sb.toString();
	}

	private void exportDotChild(String currentNodeText, AstNode node, StringBuffer sb) {
		String nodeText = processLabel(node.getRule(), node.getLabel());
		String nodeID = node.getRule() + "-" + (id++);
		sb.append("\"" + nodeID + "\" [label=\"" + nodeText + "\"]\n");
		sb.append("\"" + currentNodeText + "\" -> \"" + nodeID + "\"\n");
		if (node.getRule().toLowerCase().contains("ruleblock")) {
			return;
		}
		for (AstNode nn : node.getChildren()) {
			exportDotChild(nodeID, nn, sb);
		}
	}

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
	private String processLabel(String rule, String label) {
		if (label.contains(":")) {
			label = label.split(":")[0];
		}
		//String realLabel = rule.equals("rules") ? rule : rule + "[" + label + "]";//.replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		String realLabel = rule.equals("rules") ? rule : label;

		realLabel = realLabel.replace("\"", "\\\"");
		if (realLabel.equals("")) {
			realLabel = "none";
		}
		return realLabel;
	}
}
