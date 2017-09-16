package com.github.mcheung63.syntax.antlr4.errorhighlight;

import com.github.mcheung63.FileTypeG4DataObject;
import com.github.mcheung63.FileTypeG4VisualElement;
import com.github.mcheung63.ModuleLib;
import java.util.ArrayList;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.core.api.multiview.MultiViews;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.windows.TopComponent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorHighlightingTask extends ParserResultTask {

	public static ArrayList<ErrorInfo> errorInfos = new ArrayList<>();

	@Override
	public void run(Result result, SchedulerEvent event) {
		try {
			Document document = result.getSnapshot().getSource().getDocument(false);
			ArrayList<ErrorDescription> errors = new ArrayList<>();
			for (ErrorInfo errorInfo : errorInfos) {
				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
						Severity.ERROR,
						errorInfo.message,
						document,
						document.createPosition(errorInfo.offsetStart),
						document.createPosition(errorInfo.offsetEnd + 1)
				);
				errors.add(errorDescription);
			}
			HintsController.setErrors(document, "simple-antlr-error", errors);

			TopComponent topComponent = TopComponent.getRegistry().getActivated();
			ModuleLib.log("real time compile 1 " + topComponent.getLookup().lookup(FileTypeG4DataObject.class));
			ModuleLib.log("real time compile 2 " + MimeLookup.getDefault().lookup(MultiViewElement.class));
//			for (int x = 0; x < MultiViews.findMultiViewHandler(topComponent).getPerspectives().length; x++) {
//				ModuleLib.log("\t" + x + " real time compile 3 " + MultiViews.findMultiViewHandler(topComponent).getPerspectives()[x]);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Class getSchedulerClass() {
		return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
	}

	@Override
	public void cancel() {
	}

}
