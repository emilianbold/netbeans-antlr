package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.SchedulerTask;
import org.netbeans.modules.parsing.spi.TaskFactory;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
//@MimeRegistration(mimeType = "text/x-g4", service = TaskFactory.class)
public class ErrorHighlightingTaskFactory extends TaskFactory {

	@Override
	public Collection<? extends SchedulerTask> create(Snapshot snapshot) {
		ModuleLib.log("ErrorHighlightingTaskFactory create");
		return Collections.singleton(new ErrorHighlightingTask());
	}

}
