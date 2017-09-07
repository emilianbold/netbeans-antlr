package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorHighlightingTask extends ParserResultTask {

	@Override
	public void run(Result result, SchedulerEvent event) {
		ModuleLib.log("ErrorHighlightingTask run");
		System.out.println(" ---------------------------- ErrorHighlightingTask run");

		try {
			Document document = result.getSnapshot().getSource().getDocument(false);
			List<ErrorDescription> errors = new ArrayList<>();
			ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
					Severity.ERROR,
					"fuck error 1",
					document,
					document.createPosition(0),
					document.createPosition(5)
			);
			errors.add(errorDescription);
			HintsController.setErrors(document, "simple-antlr-error", errors);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
//            List<ParsingError> errors = result.getDiagnostics();
//            List<ErrorDescription> errDescList = new ArrayList<ErrorDescription> ();
//            if(errors!=null){
//                Document doc = result.getSnapshot().getSource().getDocument(false);
//                for(KalangError e:errors){
////                    String excepted = e.getExpectedTokens().toString(KalangParser.VOCABULARY);
////                    Token errTk = e.getOffendingToken();
////                    String msg = errTk.getText() + " is unexcepted ,excepted " + excepted;
////                    int start = errTk.getStartIndex();
////                    int stop = errTk.getStopIndex();
//                    String msg = e.getDescription();
//                    int start = e.getStartPosition();
//                    int stop = e.getEndPosition();
//                    try{
//                        ErrorDescription errorDesc = ErrorDescriptionFactory.createErrorDescription(
//                            Severity.ERROR,
//                            msg,
//                            doc,
//                            doc.createPosition(start),
//                            doc.createPosition(stop)
//                        );
//                        errDescList.add(errorDesc);
//                    } catch (BadLocationException ex) {
//                        Exceptions.printStackTrace(ex);
//                    }
//                }
//                HintsController.setErrors (doc, "kalang", errDescList);
//            }
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
