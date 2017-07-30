package antlr;

import java.nio.file.Files;
import java.nio.file.Paths;
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
}
