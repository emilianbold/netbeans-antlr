package com.github.mcheung63;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.antlr.parser.antlr4.ANTLRv4ParserBaseListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.snt.inmemantlr.tree.Ast;
import org.snt.inmemantlr.tree.AstNode;

/**
 *
 * @author peter
 */
public class MyANTLRv4ParserListener extends ANTLRv4ParserBaseListener {

//	private Stack<String> sctx = new Stack<>();
//	private StringBuffer glob = new StringBuffer();
	private Ast ast = null;
	private AstNode nodeptr = null;
	private Predicate<String> filter = null;
	private final Map<String, Integer> rmap = new HashMap<>();

	public MyANTLRv4ParserListener(Parser parser) {
		super();
//		sctx.add("S");
		ast = new Ast("root", "root");
		nodeptr = ast.getRoot();
		filter = x -> !x.isEmpty();
		rmap.clear();
		rmap.putAll(parser.getRuleIndexMap());
	}

	@Override
	public void enterEveryRule(ParserRuleContext prc) {
		//System.out.println("enterEveryRule, prc.getText()=" + prc.getText());
		String rule = getRuleByKey(prc.getRuleIndex());
		if (filter.test(rule)) {
			Token s = prc.getStart();
			Token e = prc.getStop();
			AstNode n = ast.newNode(nodeptr, rule, prc.getText(),
					s != null ? s.getStartIndex() : 0,
					e != null ? e.getStopIndex() : 0);
			nodeptr.addChild(n);
			nodeptr = n;
		}
	}

	@Override
	public void exitEveryRule(ParserRuleContext prc) {
		//System.out.println("exitEveryRule.");
		String rule = getRuleByKey(prc.getRuleIndex());
		if (filter.test(rule)) {
			nodeptr = nodeptr.getParent();
		}
	}

	public String getRuleByKey(int key) {
		return rmap.entrySet().stream()
				.filter(e -> Objects.equals(e.getValue(), key))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}

	public Ast getAst() {
		return ast;
	}

	public Set<AstNode> getNodes() {
		return new HashSet<>(ast.getNodes());
	}

}
