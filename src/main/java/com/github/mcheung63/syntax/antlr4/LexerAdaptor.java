package com.github.mcheung63.syntax.antlr4;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.parser.antlr4.*;

public abstract class LexerAdaptor extends Lexer {

	public LexerAdaptor(CharStream input) {
		super(input);
	}

	private int _currentRuleType = Token.INVALID_TYPE;

	public int getCurrentRuleType() {
		return _currentRuleType;
	}

	public void setCurrentRuleType(int ruleType) {
		this._currentRuleType = ruleType;
	}

	protected void handleBeginArgument() {
		if (inLexerRule()) {
			pushMode(ANTLRv4Lexer.LexerCharSet);
			more();
		} else {
			pushMode(ANTLRv4Lexer.Argument);
		}
	}

	protected void handleEndArgument() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(ANTLRv4Lexer.ARGUMENT_CONTENT);
		}
	}

	protected void handleEndAction() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(ANTLRv4Lexer.ACTION_CONTENT);
		}
	}

	@Override
	public Token emit() {
		if (_type == ANTLRv4Lexer.ID) {
			String firstChar = _input.getText(Interval.of(_tokenStartCharIndex, _tokenStartCharIndex));
			if (Character.isUpperCase(firstChar.charAt(0))) {
				_type = ANTLRv4Lexer.TOKEN_REF;
			} else {
				_type = ANTLRv4Lexer.RULE_REF;
			}

			if (_currentRuleType == Token.INVALID_TYPE) { // if outside of rule def
				_currentRuleType = _type; // set to inside lexer or parser rule
			}
		} else if (_type == ANTLRv4Lexer.SEMI) { // exit rule def
			_currentRuleType = Token.INVALID_TYPE;
		}

		return super.emit();
	}

	private boolean inLexerRule() {
		return _currentRuleType == ANTLRv4Lexer.TOKEN_REF;
	}

	@SuppressWarnings("unused")
	private boolean inParserRule() { // not used, but added for clarity
		return _currentRuleType == ANTLRv4Lexer.RULE_REF;
	}
}