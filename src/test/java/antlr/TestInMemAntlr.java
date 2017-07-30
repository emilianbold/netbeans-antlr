package antlr;

import java.io.File;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;
import org.snt.inmemantlr.GenericParser;
import org.snt.inmemantlr.listener.DefaultTreeListener;
import org.snt.inmemantlr.tree.Ast;
import org.snt.inmemantlr.tree.AstNode;

public class TestInMemAntlr {

	@Test
	public void test() throws Exception {
		try {
			File f = new File("/Users/peter/Desktop/Calculator.g4");
			GenericParser gp = new GenericParser(f);
			DefaultTreeListener treeListener = new DefaultTreeListener();
			gp.setListener(treeListener);
			gp.compile();
			ParserRuleContext ctx = gp.parse("1+2");
			Ast ast = treeListener.getAst();

			List<AstNode> nodes = ast.getNodes();
			for (AstNode n : nodes) {
				loop("", n);
			}
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
}
