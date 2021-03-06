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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class Antlr4LanguageHierarchy extends LanguageHierarchy<Antlr4TokenId> {

	private static List<Antlr4TokenId> tokens = new ArrayList<Antlr4TokenId>();
	private static Map<Integer, Antlr4TokenId> idToToken = new HashMap<Integer, Antlr4TokenId>();

	static {
		for (int x = 0; x <= ANTLRv4Lexer.VOCABULARY.getMaxTokenType(); x++) {
			String name = ANTLRv4Lexer.VOCABULARY.getSymbolicName(x);
			if (name == null) {
				name = "<INVALID>";
			}
			Antlr4TokenId token = new Antlr4TokenId(name, name, x);
			idToToken.put(x, token);
			tokens.add(token);
		}
	}

	public static synchronized Antlr4TokenId getToken(int id) {
		return idToToken.get(id);
	}

	@Override
	protected synchronized Collection<Antlr4TokenId> createTokenIds() {
		return tokens;
	}

	@Override
	protected synchronized Lexer<Antlr4TokenId> createLexer(LexerRestartInfo<Antlr4TokenId> info) {
		return new Antlr4EditorLexer(info);
	}

	@Override
	protected String mimeType() {
		return "text/x-g4";
	}
}
