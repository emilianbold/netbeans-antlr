//package com.github.mcheung63.syntax.antlr4;
//
//import com.github.mcheung63.ModuleLib;
//import javax.swing.text.StyledDocument;
//import org.netbeans.modules.parsing.spi.indexing.ErrorsCache;
//
///**
// *
// * @author Peter <peter@quantr.hk>
// */
//public class ErrorConvertor implements ErrorsCache.Convertor<ParsingError> {
//
//	private final StyledDocument sDoc;
//
//	public ErrorConvertor(StyledDocument sDoc) {
//		this.sDoc = sDoc;
//	}
//
//	@Override
//	public ErrorsCache.ErrorKind getKind(ParsingError err) {
//		ModuleLib.log("ErrorConvertor getKind");
//		return ErrorsCache.ErrorKind.ERROR;
//	}
//
//	@Override
//	public int getLineNumber(ParsingError err) {
//		ModuleLib.log("ErrorConvertor getLineNumber");
//		return 10;
//	}
//
//	@Override
//	public String getMessage(ParsingError err) {
//		ModuleLib.log("ErrorConvertor getMessage");
//		return "fuck 123";
//	}
//}
