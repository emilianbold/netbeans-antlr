package antlr;

import java.util.List;
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
		for (AstNode n : nodes) {
			System.out.println(n);
			loop("", n);
		}
	}

	void loop(String s, AstNode n) {
		System.out.println(s + n);
		for (AstNode nn : n.getChildren()) {
			loop(s + "    ", nn);
		}
	}
}
