package com.github.mcheung63.syntax.antlr4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.parser.antlr4.ANTLRv4ParserBaseListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public class MyANTLRv4ParserListener extends ANTLRv4ParserBaseListener {

	public Ast ast = null;
	private final Map<String, Integer> rmap = new HashMap<>();
	Predicate<String> filter = x -> !x.isEmpty();
	AstNode parentNode = null;

	public static ArrayList<TokenDocumentLocation> ruleTokenDocumentLocations = new ArrayList<TokenDocumentLocation>();

	public MyANTLRv4ParserListener(Parser parser) {
		ast = new Ast("root", "root");
		parentNode = ast.getRoot();
		rmap.putAll(parser.getRuleIndexMap());
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		String ruleName = getRuleByKey(ctx.getRuleIndex());
		String text = ctx.getText();
		Token s = ctx.getStart();
		Token e = ctx.getStop();
		if (ruleName.equals("ruleSpec")) {
			System.out.println("rule " + ruleName + " -- " + text);
			if (s != null) {
				System.out.println("\t" + s.getStartIndex() + ", " + s.getStopIndex() + ", " + s.getText());
			}
			if (e != null) {
				System.out.println("\t\t" + e.getStartIndex() + ", " + e.getStopIndex() + ", " + e.getText());
			}
		}
		AstNode n = ast.newNode(parentNode, ruleName, text, s != null ? s.getStartIndex() : 0, e != null ? e.getStopIndex() : 0);
		parentNode.addChild(n);
		parentNode = n;
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		String rule = getRuleByKey(ctx.getRuleIndex());
		parentNode = parentNode.getParent();
	}

	@Override
	public void enterGrammarSpec(ANTLRv4Parser.GrammarSpecContext ctx) {
		System.out.println("\t\t\t\t\tenterGrammarSpec=" + ctx.identifier().getText());
		ast.getRoot().setLabel(ctx.identifier().getText());
	}

	public String getRuleByKey(int key) {
		return rmap.entrySet().stream()
				.filter(e -> Objects.equals(e.getValue(), key))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}
}
