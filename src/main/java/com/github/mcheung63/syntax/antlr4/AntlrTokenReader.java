///*
// * Copyright (C) 2017 Peter (mcheung63@hotmail.com)
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package com.github.mcheung63.syntax.antlr4;
//
//import com.github.mcheung63.ModuleLib;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.openide.util.Exceptions;
//
//public class AntlrTokenReader {
//
////	private HashMap<String, String> tokenTypes = new HashMap<String, String>();
//	private final ArrayList<Antlr4TokenId> tokens = new ArrayList<Antlr4TokenId>();
//
//	public AntlrTokenReader() {
//	}
//
//	/**
//	 * Reads the token file from the ANTLR parser and generates appropriate tokens.
//	 *
//	 * @return
//	 */
//	public List<Antlr4TokenId> readTokenFile() {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		//ModuleLib.log("readTokenFile(), classLoader=" + classLoader.getResource("").getPath());
//
////		AntlrTokenReader obj = Lookup.getDefault().lookup(AntlrTokenReader.class);
////		ModuleLib.log("obj=" + obj + " , " + (obj == this));
//		//InputStream inp = classLoader.getResourceAsStream("R.tokens");
////		for (String file : getResourceFiles(".")) {
////			ModuleLib.log("		->	readTokenFile(), file=" + file);
////		}
////		ModuleLib.log("	1=" + classLoader.getResourceAsStream("/R.tokens"));
////		ModuleLib.log("	2=" + classLoader.getResourceAsStream("/com/github/mcheung63/netbeansr/mainmodule/Bundle.properties"));
////		ModuleLib.log("	3=" + this.getClass().getResourceAsStream("/R.tokens"));
////		ModuleLib.log("	4=" + this.getClass().getResourceAsStream("/com/github/mcheung63/netbeansr/mainmodule/Bundle.properties"));
//		InputStream inputStream = classLoader.getResourceAsStream("/org/antlr/parser/antlr4/ANTLRv4Lexer.tokens");
//		BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
//		readTokenFile(input);
//		return tokens;
//	}
//
//	/**
//	 * Reads in the token file.
//	 *
//	 * @param buff
//	 */
//	private void readTokenFile(BufferedReader buff) {
//		try {
//			ModuleLib.log("readTokenFile");
//			Pattern pattern = Pattern.compile("(.*)=(.*)");
//			String line = null;
//			while ((line = buff.readLine()) != null) {
//				if (line.contains("T__")) {
//					continue;
//				}
////				ModuleLib.log(" >>> " + line);
//				Matcher m = pattern.matcher(line);
//				if (m.find()) {
//					String name = m.group(1);
//					int id = Integer.parseInt(m.group(2));
////					String tokenCategory = tokenTypes.get(name);
//					Antlr4TokenId token = new Antlr4TokenId(name, "ID", id);
////					if (tokenCategory != null) {
////						id = new RTokenId(name, tokenCategory, tok);
////					} else {
////						id = new RTokenId(name, "NL", tok);
////					}
//					tokens.add(token);
//				} else {
//					ModuleLib.log("  fuck up, " + line);
//				}
//				//add it into the vector of tokens
////				tokens.add(id);
////				}
////				String[] splLine = line.split("=");
////				String name = splLine[0];
////				int tok = Integer.parseInt(splLine[1].trim());
////				RTokenId id;
////				String tokenCategory = tokenTypes.get(name);
////				if (tokenCategory != null) {
////					//if the value exists, put it in the correct category
////					id = new RTokenId(name, tokenCategory, tok);
////				} else {
////					//if we don't recognize the token, consider it to a separator
////					id = new RTokenId(name, "separator", tok);
////				}
////				//add it into the vector of tokens
////				tokens.add(id);
//			}
////			ModuleLib.log(" >> end");
//		} catch (Exception ex) {
//			ModuleLib.log(" exception=" + ModuleLib.printException(ex));
//			Exceptions.printStackTrace(ex);
//		}
//	}
//}
