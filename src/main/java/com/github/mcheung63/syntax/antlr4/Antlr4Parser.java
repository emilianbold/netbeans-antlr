package com.github.mcheung63.syntax.antlr4;

import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class Antlr4Parser extends Parser {

	@Override
	public void parse(Snapshot snpsht, Task task, SourceModificationEvent sme) throws ParseException {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Result getResult(Task task) throws ParseException {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return new Antlr4ParseResult(null);
	}

	@Override
	public void addChangeListener(ChangeListener cl) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeChangeListener(ChangeListener cl) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


}
