package com.github.mcheung63.syntax.antlr4.errorhighlight;

import java.util.Collection;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.ParserFactory;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class Antlr4ParserFactory extends ParserFactory {

	@Override
	public org.netbeans.modules.parsing.spi.Parser createParser(Collection<Snapshot> clctn) {
		return new Antlr4Parser();
	}

}
