package antlr;
	
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
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
	}
}
