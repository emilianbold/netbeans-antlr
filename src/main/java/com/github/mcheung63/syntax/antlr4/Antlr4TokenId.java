/*
 * Copyright (C) 2017 peter
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
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

public class Antlr4TokenId implements TokenId {

	private static final Language<Antlr4TokenId> language = new Antlr4LanguageHierarchy().language();
	private final String name;
	private final String primaryCategory;
	private final int id;

	public Antlr4TokenId(String name, String primaryCategory, int id) {
		this.name = name;
		this.primaryCategory = primaryCategory;
		this.id = id;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int ordinal() {
		return id;
	}

	@Override
	public String primaryCategory() {
		return primaryCategory;
	}

	public static final Language<Antlr4TokenId> getLanguage() {
		ModuleLib.log("getLanguage()");
		return language;
	}

	public String toString() {
		return id + ", " + name + ", " + primaryCategory;
	}
}
