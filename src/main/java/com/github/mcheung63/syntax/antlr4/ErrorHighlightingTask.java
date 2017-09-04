package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorHighlightingTask extends ParserResultTask<ParserResult> {

	@Override
	public void run(ParserResult result, SchedulerEvent event) {
		ModuleLib.log("ErrorHighlightingTask run");
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
		return 50;
	}

	@Override
	public Class getSchedulerClass() {
		return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
	}

	@Override
	public void cancel() {
	}

}
