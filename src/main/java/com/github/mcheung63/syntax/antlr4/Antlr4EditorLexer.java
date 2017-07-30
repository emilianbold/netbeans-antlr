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
package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class Antlr4EditorLexer implements Lexer<Antlr4TokenId> {

	private LexerRestartInfo<Antlr4TokenId> info;
	private ANTLRv4Lexer lexer;

	public Antlr4EditorLexer(LexerRestartInfo<Antlr4TokenId> info) {
		this.info = info;
		AntlrCharStream charStream = new AntlrCharStream(info.input(), "Antlr4Editor");
		lexer = new ANTLRv4Lexer(charStream);
	}

	@Override
	public Token<Antlr4TokenId> nextToken() {
		org.antlr.v4.runtime.Token token = lexer.nextToken();
		ModuleLib.log("nextToken(), token=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()) + "\t= " + token);
		if (token.getType() != ANTLRv4Lexer.EOF) {
			Antlr4TokenId tokenId = Antlr4LanguageHierarchy.getToken(token.getType());
			ModuleLib.log("     tokenId" + tokenId);
			ModuleLib.log("     info.tokenFactory().createToken(tokenId)=" + info.tokenFactory().createToken(tokenId));
			return info.tokenFactory().createToken(tokenId);
		}
		return null;
	}

	@Override
	public Object state() {
		return null;
	}

	@Override
	public void release() {
	}

}
