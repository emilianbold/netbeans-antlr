package antlr;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class TestAntlr {

	@Test
	public void test() throws Exception {
		System.out.println("test()");
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(getClass().getResourceAsStream("simple1.g4")));

//		Token token = lexer.nextToken();
//		while (token.getType() != Lexer.EOF) {
//			System.out.println(token + "=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()));
//			token = lexer.nextToken();
//		}
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
//		parser.addParseListener(listener);

		ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();

		ParseTreeWalker walker = new ParseTreeWalker();
		MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
		walker.walk(listener, context);

		//ParseTree tree = parser.grammarSpec();
		//parser.grammarSpec();
		Ast ast = listener.getAst();
		List<AstNode> nodes = ast.getNodes();
//		System.out.println("nodes.size=" + nodes.size());
//		for (AstNode n : nodes) {
//			System.out.println(n);
//			loop("", n);
//		}
		loop("", nodes.get(0));

		MutableGraph g = Parser.read("graph {\n"
				+ "    { rank=same; white}\n"
				+ "    { rank=same; cyan; yellow; pink}\n"
				+ "    { rank=same; red; green; blue}\n"
				+ "    { rank=same; black}\n"
				+ "\n"
				+ "    white -- cyan -- blue\n"
				+ "    white -- yellow -- green\n"
				+ "    white -- pink -- red\n"
				+ "\n"
				+ "    cyan -- green -- black\n"
				+ "    yellow -- red -- black\n"
				+ "    pink -- blue -- black\n"
				+ "}");
//		Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-1.png"));
		g.generalAttrs()
				.add(Color.WHITE.gradient(Color.rgb("888888")).background().angle(90))
				.nodeAttrs().add(Color.WHITE.fill())
				.nodes().forEach(node
						-> node.add(
						Color.named(node.label().toString()),
						Style.lineWidth(4).and(Style.FILLED)));
		
		
		BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
		new JFrameWin(image).setVisible(true);
//		Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-2.png"));
	}

	void loop(String s, AstNode n) {
		System.out.println(s + n);
		for (AstNode nn : n.getChildren()) {
			loop(s + "    ", nn);
		}
	}

	public static class JFrameWin extends JFrame {

		public JFrameWin(BufferedImage bufferedImage) {
			this.setSize(800, 800);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));

			JPanel jPanel = new JPanel();
			jPanel.add(jLabel);
			this.add(jPanel);
		}

	}
}
