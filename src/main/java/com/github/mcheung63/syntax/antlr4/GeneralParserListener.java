package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class GeneralParserListener implements ParseTreeListener {

	public Ast ast = null;
	private final Map<String, Integer> rmap = new HashMap<>();
	Predicate<String> filter = x -> !x.isEmpty();
	AstNode parentNode = null;

	public static ArrayList<TokenDocumentLocation> ruleTokenDocumentLocationTargets = new ArrayList<TokenDocumentLocation>();
	public static ArrayList<TokenDocumentLocation> ruleTokenDocumentLocationSources = new ArrayList<TokenDocumentLocation>();

	public GeneralParserListener(Parser parser) {
		ast = new Ast("root", "root");
		parentNode = ast.getRoot();
		rmap.putAll(parser.getRuleIndexMap());
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		String ruleName = getRuleByKey(ctx.getRuleIndex());
		ModuleLib.log(" ++++--+++ enterEveryRule:" + ruleName);
		String text = ctx.getText();
		Token s = ctx.getStart();
		Token e = ctx.getStop();

		if (ruleName.equals("ruleSpec")) {
//			if (s != null) {
//				System.out.println("\t" + s.getStartIndex() + ", " + s.getStopIndex() + ", " + s.getText());
//			}
//			if (e != null) {
//				System.out.println("\t\t" + e.getStartIndex() + ", " + e.getStopIndex() + ", " + e.getText());
//			}
			TokenDocumentLocation tokenDocumentLocation = new TokenDocumentLocation();
			tokenDocumentLocation.rule = ruleName;
			tokenDocumentLocation.text = text.split(":")[0];
			tokenDocumentLocation.start = s.getStartIndex();
			tokenDocumentLocation.stop = s.getStopIndex();
			ruleTokenDocumentLocationTargets.add(tokenDocumentLocation);
		} else {
			TokenDocumentLocation tokenDocumentLocation = new TokenDocumentLocation();
			tokenDocumentLocation.rule = ruleName;
			tokenDocumentLocation.text = text.split(":")[0];
			tokenDocumentLocation.start = s.getStartIndex();
			tokenDocumentLocation.stop = s.getStopIndex();
			ruleTokenDocumentLocationSources.add(tokenDocumentLocation);
		}
		AstNode n = ast.newNode(parentNode, ruleName, text, s != null ? s.getStartIndex() : 0, e != null ? e.getStopIndex() : 0);
		parentNode.addChild(n);
		parentNode = n;
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		String ruleName = getRuleByKey(ctx.getRuleIndex());
		ModuleLib.log(" ++++--+++ exitEveryRule:" + ruleName);
		parentNode = parentNode.getParent();
	}

	public String getRuleByKey(int key) {
		return rmap.entrySet().stream()
				.filter(e -> Objects.equals(e.getValue(), key))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}

	public static boolean containTarget(String text) {
		for (TokenDocumentLocation tokenDocumentLocation : MyANTLRv4ParserListener.ruleTokenDocumentLocationTargets) {
			if (tokenDocumentLocation.text.equals(text)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visitTerminal(TerminalNode tn) {
	}

	@Override
	public void visitErrorNode(ErrorNode en) {
	}

}
