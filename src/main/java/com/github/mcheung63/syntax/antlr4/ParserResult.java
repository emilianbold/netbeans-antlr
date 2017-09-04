package com.github.mcheung63.syntax.antlr4;

import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ParserResult extends Parser.Result {

	public ParserResult(String clsName, Snapshot snapshot, Parser parser) {
		super(snapshot);
	}

	@Override
	protected void invalidate() {

	}

}
