package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
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

	private Snapshot snapshot;

	@Override
	public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
		ModuleLib.log("Antlr4Parser parse " + task);
		this.snapshot = snapshot;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Result getResult(Task task) throws ParseException {
		ModuleLib.log("Antlr4Parser getResult " + task);
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return new Antlr4ParseResult(snapshot);
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
