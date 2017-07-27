/*
 * Copyright (C) 2017 Peter (mcheung63@hotmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.mcheung63.syntax;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class Antlr4EditorLexer implements Lexer<Antlr4TokenId> {

	private LexerRestartInfo<Antlr4TokenId> info;
	private Antlr4Lexer lexer;

	public Antlr4EditorLexer(LexerRestartInfo<Antlr4TokenId> info) {
		this.info = info;
		Antlr4CharStream charStream = new Antlr4CharStream(info.input(), "Antlr4Editor", true);
		lexer = new Antlr4Lexer(charStream);
	}

	@Override
	public Token<Antlr4TokenId> nextToken() {
		org.antlr.v4.runtime.Token token = lexer.nextToken();
		//ModuleLib.log("nextToken(), token=" + token + ", " + token.getType() + ", " + token.getText());
		if (token.getType() != Antlr4Lexer.EOF) {
			Antlr4TokenId tokenId = Antlr4LanguageHierarchy.getToken(token.getType());
			return info.tokenFactory().createToken(tokenId);
		}

//		RTokenId tokenId = RLanguageHierarchy.getToken(RLexer.NL);
//		ModuleLib.log("nextToken() shit=" + tokenId);
//		return info.tokenFactory().createToken(tokenId);
		return null;

//		org.antlr.v4.runtime.Token token = lexer.nextToken();
//		Token<RTokenId> createdToken = null;
//
//		if (token.getType() != -1) {
//			RTokenId tokenId = RLanguageHierarchy.getToken(token.getType());
//			createdToken = info.tokenFactory().createToken(tokenId);
//		} else if (info.input().readLength() > 0) {
//			RTokenId tokenId = RLanguageHierarchy.getToken(RLexer.WS);
//			createdToken = info.tokenFactory().createToken(tokenId);
//		}
//		return createdToken;
	}

	@Override
	public Object state() {
		return null;
	}

	@Override
	public void release() {
	}

}
