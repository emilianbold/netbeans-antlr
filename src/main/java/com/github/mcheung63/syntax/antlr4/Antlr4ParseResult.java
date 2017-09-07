package com.github.mcheung63.syntax.antlr4;

import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser.Result;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class Antlr4ParseResult extends Result {

	public Antlr4ParseResult(Snapshot _snapshot) {
		super(_snapshot);
	}

	@Override
	protected void invalidate() {

	}

}
