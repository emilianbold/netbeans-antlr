package antlr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.parser.antlr4.ANTLRv4ParserListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author peter
 */
public class MyANTLRv4ParserListener implements ANTLRv4ParserListener {

	private Stack<String> sctx = new Stack<>();
	private StringBuffer glob = new StringBuffer();
	private Ast ast = null;
	private AstNode nodeptr = null;
	private Predicate<String> filter = null;
	private final Map<String, Integer> rmap = new HashMap<>();

	public MyANTLRv4ParserListener(Parser parser) {
		sctx.add("S");
		ast = new Ast("root", "root");
		nodeptr = ast.getRoot();
		this.filter = x -> !x.isEmpty();
		rmap.clear();
		rmap.putAll(parser.getRuleIndexMap());
	}

	@Override
	public void enterGrammarSpec(ANTLRv4Parser.GrammarSpecContext ctx) {
		System.out.println("enterGrammarSpec." + ctx.getText());
	}

	@Override
	public void exitGrammarSpec(ANTLRv4Parser.GrammarSpecContext ctx) {
		System.out.println("exitGrammarSpec." + ctx.getText());
	}

	@Override
	public void enterGrammarType(ANTLRv4Parser.GrammarTypeContext ctx) {
		System.out.println("enterGrammarType." + ctx.getText());
	}

	@Override
	public void exitGrammarType(ANTLRv4Parser.GrammarTypeContext ctx) {
		System.out.println("exitGrammarType." + ctx.getText());
	}

	@Override
	public void enterPrequelConstruct(ANTLRv4Parser.PrequelConstructContext ctx) {
		System.out.println("enterPrequelConstruct." + ctx.getText());
	}

	@Override
	public void exitPrequelConstruct(ANTLRv4Parser.PrequelConstructContext ctx) {
		System.out.println("exitPrequelConstruct." + ctx.getText());
	}

	@Override
	public void enterOptionsSpec(ANTLRv4Parser.OptionsSpecContext ctx) {
		System.out.println("enterOptionsSpec." + ctx.getText());
	}

	@Override
	public void exitOptionsSpec(ANTLRv4Parser.OptionsSpecContext ctx) {
		System.out.println("exitOptionsSpec." + ctx.getText());
	}

	@Override
	public void enterOption(ANTLRv4Parser.OptionContext ctx) {
		System.out.println("enterOption." + ctx.getText());
	}

	@Override
	public void exitOption(ANTLRv4Parser.OptionContext ctx) {
		System.out.println("exitOption." + ctx.getText());
	}

	@Override
	public void enterOptionValue(ANTLRv4Parser.OptionValueContext ctx) {
		System.out.println("enterOptionValue." + ctx.getText());
	}

	@Override
	public void exitOptionValue(ANTLRv4Parser.OptionValueContext ctx) {
		System.out.println("exitOptionValue." + ctx.getText());
	}

	@Override
	public void enterDelegateGrammars(ANTLRv4Parser.DelegateGrammarsContext ctx) {
		System.out.println("enterDelegateGrammars." + ctx.getText());
	}

	@Override
	public void exitDelegateGrammars(ANTLRv4Parser.DelegateGrammarsContext ctx) {
		System.out.println("exitDelegateGrammars." + ctx.getText());
	}

	@Override
	public void enterDelegateGrammar(ANTLRv4Parser.DelegateGrammarContext ctx) {
		System.out.println("enterDelegateGrammar." + ctx.getText());
	}

	@Override
	public void exitDelegateGrammar(ANTLRv4Parser.DelegateGrammarContext ctx) {
		System.out.println("exitDelegateGrammar." + ctx.getText());
	}

	@Override
	public void enterTokensSpec(ANTLRv4Parser.TokensSpecContext ctx) {
		System.out.println("enterTokensSpec." + ctx.getText());
	}

	@Override
	public void exitTokensSpec(ANTLRv4Parser.TokensSpecContext ctx) {
		System.out.println("exitTokensSpec." + ctx.getText());
	}

	@Override
	public void enterChannelsSpec(ANTLRv4Parser.ChannelsSpecContext ctx) {
		System.out.println("enterChannelsSpec." + ctx.getText());
	}

	@Override
	public void exitChannelsSpec(ANTLRv4Parser.ChannelsSpecContext ctx) {
		System.out.println("exitChannelsSpec." + ctx.getText());
	}

	@Override
	public void enterIdList(ANTLRv4Parser.IdListContext ctx) {
		System.out.println("enterIdList." + ctx.getText());
	}

	@Override
	public void exitIdList(ANTLRv4Parser.IdListContext ctx) {
		System.out.println("exitIdList." + ctx.getText());
	}

	@Override
	public void enterAction(ANTLRv4Parser.ActionContext ctx) {
		System.out.println("enterAction." + ctx.getText());
	}

	@Override
	public void exitAction(ANTLRv4Parser.ActionContext ctx) {
		System.out.println("exitAction." + ctx.getText());
	}

	@Override
	public void enterActionScopeName(ANTLRv4Parser.ActionScopeNameContext ctx) {
		System.out.println("enterActionScopeName." + ctx.getText());
	}

	@Override
	public void exitActionScopeName(ANTLRv4Parser.ActionScopeNameContext ctx) {
		System.out.println("exitActionScopeName." + ctx.getText());
	}

	@Override
	public void enterActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
		System.out.println("enterActionBlock." + ctx.getText());
	}

	@Override
	public void exitActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
		System.out.println("exitActionBlock." + ctx.getText());
	}

	@Override
	public void enterArgActionBlock(ANTLRv4Parser.ArgActionBlockContext ctx) {
		System.out.println("enterArgActionBlock." + ctx.getText());
	}

	@Override
	public void exitArgActionBlock(ANTLRv4Parser.ArgActionBlockContext ctx) {
		System.out.println("exitArgActionBlock." + ctx.getText());
	}

	@Override
	public void enterModeSpec(ANTLRv4Parser.ModeSpecContext ctx) {
		System.out.println("enterModeSpec." + ctx.getText());
	}

	@Override
	public void exitModeSpec(ANTLRv4Parser.ModeSpecContext ctx) {
		System.out.println("exitModeSpec." + ctx.getText());
	}

	@Override
	public void enterRules(ANTLRv4Parser.RulesContext ctx) {
		System.out.println("enterRules." + ctx.getText());
	}

	@Override
	public void exitRules(ANTLRv4Parser.RulesContext ctx) {
		System.out.println("exitRules." + ctx.getText());
	}

	@Override
	public void enterRuleSpec(ANTLRv4Parser.RuleSpecContext ctx) {
		System.out.println("enterRuleSpec." + ctx.getText());
	}

	@Override
	public void exitRuleSpec(ANTLRv4Parser.RuleSpecContext ctx) {
		System.out.println("exitRuleSpec." + ctx.getText());
	}

	@Override
	public void enterParserRuleSpec(ANTLRv4Parser.ParserRuleSpecContext ctx) {
		System.out.println("enterParserRuleSpec." + ctx.getText());
	}

	@Override
	public void exitParserRuleSpec(ANTLRv4Parser.ParserRuleSpecContext ctx) {
		System.out.println("exitParserRuleSpec." + ctx.getText());
	}

	@Override
	public void enterExceptionGroup(ANTLRv4Parser.ExceptionGroupContext ctx) {
		System.out.println("enterExceptionGroup." + ctx.getText());
	}

	@Override
	public void exitExceptionGroup(ANTLRv4Parser.ExceptionGroupContext ctx) {
		System.out.println("exitExceptionGroup." + ctx.getText());
	}

	@Override
	public void enterExceptionHandler(ANTLRv4Parser.ExceptionHandlerContext ctx) {
		System.out.println("enterExceptionHandler." + ctx.getText());
	}

	@Override
	public void exitExceptionHandler(ANTLRv4Parser.ExceptionHandlerContext ctx) {
		System.out.println("exitExceptionHandler." + ctx.getText());
	}

	@Override
	public void enterFinallyClause(ANTLRv4Parser.FinallyClauseContext ctx) {
		System.out.println("enterFinallyClause." + ctx.getText());
	}

	@Override
	public void exitFinallyClause(ANTLRv4Parser.FinallyClauseContext ctx) {
		System.out.println("exitFinallyClause." + ctx.getText());
	}

	@Override
	public void enterRulePrequel(ANTLRv4Parser.RulePrequelContext ctx) {
		System.out.println("enterRulePrequel." + ctx.getText());
	}

	@Override
	public void exitRulePrequel(ANTLRv4Parser.RulePrequelContext ctx) {
		System.out.println("exitRulePrequel." + ctx.getText());
	}

	@Override
	public void enterRuleReturns(ANTLRv4Parser.RuleReturnsContext ctx) {
		System.out.println("enterRuleReturns." + ctx.getText());
	}

	@Override
	public void exitRuleReturns(ANTLRv4Parser.RuleReturnsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterThrowsSpec(ANTLRv4Parser.ThrowsSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitThrowsSpec(ANTLRv4Parser.ThrowsSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLocalsSpec(ANTLRv4Parser.LocalsSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLocalsSpec(ANTLRv4Parser.LocalsSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleAction(ANTLRv4Parser.RuleActionContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleAction(ANTLRv4Parser.RuleActionContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleModifiers(ANTLRv4Parser.RuleModifiersContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleModifiers(ANTLRv4Parser.RuleModifiersContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleModifier(ANTLRv4Parser.RuleModifierContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleModifier(ANTLRv4Parser.RuleModifierContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleBlock(ANTLRv4Parser.RuleBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleBlock(ANTLRv4Parser.RuleBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleAltList(ANTLRv4Parser.RuleAltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleAltList(ANTLRv4Parser.RuleAltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLabeledAlt(ANTLRv4Parser.LabeledAltContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLabeledAlt(ANTLRv4Parser.LabeledAltContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerRuleSpec(ANTLRv4Parser.LexerRuleSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerRuleSpec(ANTLRv4Parser.LexerRuleSpecContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerRuleBlock(ANTLRv4Parser.LexerRuleBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerRuleBlock(ANTLRv4Parser.LexerRuleBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerAltList(ANTLRv4Parser.LexerAltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerAltList(ANTLRv4Parser.LexerAltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerAlt(ANTLRv4Parser.LexerAltContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerAlt(ANTLRv4Parser.LexerAltContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerElements(ANTLRv4Parser.LexerElementsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerElements(ANTLRv4Parser.LexerElementsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerElement(ANTLRv4Parser.LexerElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerElement(ANTLRv4Parser.LexerElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLabeledLexerElement(ANTLRv4Parser.LabeledLexerElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLabeledLexerElement(ANTLRv4Parser.LabeledLexerElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerBlock(ANTLRv4Parser.LexerBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerBlock(ANTLRv4Parser.LexerBlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerCommands(ANTLRv4Parser.LexerCommandsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerCommands(ANTLRv4Parser.LexerCommandsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerCommand(ANTLRv4Parser.LexerCommandContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerCommand(ANTLRv4Parser.LexerCommandContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerCommandName(ANTLRv4Parser.LexerCommandNameContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerCommandName(ANTLRv4Parser.LexerCommandNameContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerCommandExpr(ANTLRv4Parser.LexerCommandExprContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerCommandExpr(ANTLRv4Parser.LexerCommandExprContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterAltList(ANTLRv4Parser.AltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitAltList(ANTLRv4Parser.AltListContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterAlternative(ANTLRv4Parser.AlternativeContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitAlternative(ANTLRv4Parser.AlternativeContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterElement(ANTLRv4Parser.ElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitElement(ANTLRv4Parser.ElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLabeledElement(ANTLRv4Parser.LabeledElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLabeledElement(ANTLRv4Parser.LabeledElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterEbnf(ANTLRv4Parser.EbnfContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitEbnf(ANTLRv4Parser.EbnfContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterBlockSuffix(ANTLRv4Parser.BlockSuffixContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitBlockSuffix(ANTLRv4Parser.BlockSuffixContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterEbnfSuffix(ANTLRv4Parser.EbnfSuffixContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitEbnfSuffix(ANTLRv4Parser.EbnfSuffixContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterLexerAtom(ANTLRv4Parser.LexerAtomContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitLexerAtom(ANTLRv4Parser.LexerAtomContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterAtom(ANTLRv4Parser.AtomContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitAtom(ANTLRv4Parser.AtomContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterNotSet(ANTLRv4Parser.NotSetContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitNotSet(ANTLRv4Parser.NotSetContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterBlockSet(ANTLRv4Parser.BlockSetContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitBlockSet(ANTLRv4Parser.BlockSetContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterSetElement(ANTLRv4Parser.SetElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitSetElement(ANTLRv4Parser.SetElementContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterBlock(ANTLRv4Parser.BlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitBlock(ANTLRv4Parser.BlockContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterRuleref(ANTLRv4Parser.RulerefContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitRuleref(ANTLRv4Parser.RulerefContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterCharacterRange(ANTLRv4Parser.CharacterRangeContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitCharacterRange(ANTLRv4Parser.CharacterRangeContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterTerminal(ANTLRv4Parser.TerminalContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitTerminal(ANTLRv4Parser.TerminalContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterElementOptions(ANTLRv4Parser.ElementOptionsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitElementOptions(ANTLRv4Parser.ElementOptionsContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterElementOption(ANTLRv4Parser.ElementOptionContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitElementOption(ANTLRv4Parser.ElementOptionContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void enterIdentifier(ANTLRv4Parser.IdentifierContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void exitIdentifier(ANTLRv4Parser.IdentifierContext ctx) {
		System.out.println("Not supported yet." + ctx.getText());
	}

	@Override
	public void visitTerminal(TerminalNode tn) {
		System.out.println("visitTerminal.");
	}

	@Override
	public void visitErrorNode(ErrorNode en) {
		System.out.println("visitErrorNode.");
	}

	@Override
	public void enterEveryRule(ParserRuleContext prc) {
		String rule = getRuleByKey(prc.getRuleIndex());
		System.out.println("enterEveryRule, rule=" + rule + " prc.getText()=" + prc.getText());
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
		System.out.println("exitEveryRule.");
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
