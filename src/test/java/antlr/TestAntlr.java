package antlr;

import java.util.List;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class TestAntlr {

	@Test
	public void test() throws Exception {
		System.out.println("test()");
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(getClass().getResourceAsStream("Simple1.g4")));

		Token token = lexer.nextToken();
		while (token.getType() != Lexer.EOF) {
			System.out.println(token + "=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()));
			token = lexer.nextToken();
		}
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
		MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
		parser.addParseListener(listener);
		
		ParseTree tree = parser.grammarSpec();

		listener.visit(tree);
		
		parser.grammarSpec();
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
