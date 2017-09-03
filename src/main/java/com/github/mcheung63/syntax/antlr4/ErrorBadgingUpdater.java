package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledDocument;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.ErrorsCache;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ErrorBadgingUpdater extends EmbeddingIndexer {

	private final static Logger LOG = Logger.getLogger(ErrorBadgingUpdater.class.getName());

	@Override
	protected void index(Indexable indxbl, Parser.Result result, Context cntxt) {
		ModuleLib.log("+++++++++++ ErrorBadgingUpdater index");
		System.out.println("+++++++++++ ErrorBadgingUpdater index");
		List<ParsingError> errorList = new ArrayList<ParsingError>();
		errorList.add(new ParsingError());
		StyledDocument sDoc = getDocument(result.getSnapshot().getSource().getFileObject());
		ErrorsCache.Convertor<ParsingError> convertor = new ErrorConvertor(sDoc);
		ErrorsCache.setErrors(cntxt.getRootURI(),
				indxbl,
				errorList,
				convertor);
	}

	StyledDocument getDocument(FileObject fileObject) {
		ModuleLib.log("+++++++++++ ErrorBadgingUpdater getDocument");
		System.out.println("+++++++++++ ErrorBadgingUpdater getDocument");
		assert fileObject != null;
		StyledDocument doc;
		try {
			DataObject dataObject = DataObject.find(fileObject);
			EditorCookie doec = dataObject.getLookup().lookup(EditorCookie.class);
			doc = doec.openDocument();
		} catch (IOException ex) {
			ex.printStackTrace();
			doc = null;
		}
		return doc;
	}

	public static class Factory extends EmbeddingIndexerFactory {

		private static final String MIME = "text/x-g4";
		private static final int VERSION = 1;
		private static final String NAME = "antlr4";

		@Override
		public EmbeddingIndexer createIndexer(Indexable indxbl, Snapshot snpsht) {
			ModuleLib.log("+++++++++++ Factory createIndexer " + indxbl.getMimeType());
			System.out.println("+++++++++++ Factory createIndexer " + indxbl.getMimeType());
			EmbeddingIndexer answer;
			if (indxbl.getMimeType().equals(MIME)) {
				answer = new ErrorBadgingUpdater();
			} else {
				answer = null;
			}
			return answer;
		}

		@Override
		public void filesDeleted(Iterable<? extends Indexable> itrbl, Context cntxt) {
			ModuleLib.log("Factory filesDeleted");
			for (Indexable ixbl : itrbl) {
				try {
					IndexingSupport.getInstance(cntxt).removeDocuments(ixbl);
				} catch (IOException e) {
					LOG.log(Level.WARNING, null, e);
				}
			}
		}

		@Override
		public void filesDirty(Iterable<? extends Indexable> itrbl, Context cntxt) {
			ModuleLib.log("Factory filesDirty");
			for (Indexable ixbl : itrbl) {
				try {
					IndexingSupport.getInstance(cntxt).markDirtyDocuments(ixbl);
				} catch (IOException e) {
					LOG.log(Level.WARNING, null, e);
				}
			}
		}

		@Override
		public int getIndexVersion() {
			return VERSION;
		}

		@Override
		public String getIndexerName() {
			return NAME;
		}

	}

}
