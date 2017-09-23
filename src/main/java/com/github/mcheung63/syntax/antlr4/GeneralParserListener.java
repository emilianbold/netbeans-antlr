package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
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

	public GeneralParserListener(Parser parser) {
		ast = new Ast("root", "root");
		parentNode = ast.getRoot();
		rmap.putAll(parser.getRuleIndexMap());
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		//System.out.println("ccc=" + ctx.getText());
		String ruleName = getRuleByKey(ctx.getRuleIndex());
		String text = ctx.getText();
		//System.out.println("enterEveryRule:" + ruleName + ", " + ctx.getRuleIndex() + ", text=" + text);
		Token s = ctx.getStart();
		Token e = ctx.getStop();
		//System.out.println("    s:" + s.getText());
		if (s != null) {
			System.out.println(ruleName + "\ts.getText():\t" + s.getText()+",t\t"+s.getStartIndex());
		}
//		System.out.println("    e:" + e);
//		if (e != null) {
//			System.out.println("    e.getText():" + e.getText());
//		}
//		System.out.println("    ctx=" + ctx);
//		System.out.println("    ctx.getText()=" + ctx.getText() + "<");

		AstNode n = ast.newNode(parentNode, ruleName, text, s != null ? s.getStartIndex() : 0, e != null ? e.getStopIndex() : 0);
		parentNode.addChild(n);
		parentNode = n;
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		String ruleName = getRuleByKey(ctx.getRuleIndex());
		parentNode = parentNode.getParent();
	}

	public String getRuleByKey(int key) {
		return rmap.entrySet().stream()
				.filter(e -> Objects.equals(e.getValue(), key))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElse(null);
	}

	@Override
	public void visitTerminal(TerminalNode tn) {
	}

	@Override
	public void visitErrorNode(ErrorNode en) {
	}

}
