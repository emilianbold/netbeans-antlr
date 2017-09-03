package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.TreeTopComponent;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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

		try {
			if (TreeTopComponent.lastDataObject != null) {
				ANTLRv4Lexer lexer2 = new ANTLRv4Lexer(new ANTLRInputStream(TreeTopComponent.lastDataObject.getPrimaryFile().getInputStream()));
				CommonTokenStream tokenStream = new CommonTokenStream(lexer2);
				ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
				ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();
				ParseTreeWalker walker = new ParseTreeWalker();
				MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
				walker.walk(listener, context);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Token<Antlr4TokenId> nextToken() {
		org.antlr.v4.runtime.Token token = lexer.nextToken();
		//ModuleLib.log("nextToken(), token=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()) + "\t= " + token);
		if (token.getType() != ANTLRv4Lexer.EOF) {
			Antlr4TokenId tokenId = Antlr4LanguageHierarchy.getToken(token.getType());
//			ModuleLib.log("     tokenId" + tokenId);
//			ModuleLib.log("     info.tokenFactory().createToken(tokenId)=" + info.tokenFactory().createToken(tokenId));
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
