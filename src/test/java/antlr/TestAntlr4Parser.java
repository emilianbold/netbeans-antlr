package antlr;

import com.github.mcheung63.AntlrLib;
import com.github.mcheung63.syntax.antlr4.Ast;
import com.github.mcheung63.syntax.antlr4.AstNode;
import com.github.mcheung63.syntax.antlr4.MyANTLRv4ParserListener;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.awt.image.BufferedImage;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.parser.antlr4.ANTLRv4Parser.GrammarSpecContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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
		AntlrLib.filterUnwantedSubNodes(root, new String[]{"ruleblock"});
		//removeOneLeftNodes(root);
		AntlrLib.printAst("", root);

		String dot = AntlrLib.exportDot(root);

		System.out.println(dot);

//		FileUtils.writeStringToFile(new File("/Users/peter/Desktop/a.dot"), dot, "UTF-8");
		MutableGraph g = Parser.read(dot);
		BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
//		Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("/Users/peter/Desktop/test.png"));
	}

}
