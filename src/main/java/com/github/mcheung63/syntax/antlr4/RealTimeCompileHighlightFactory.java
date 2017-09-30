package com.github.mcheung63.syntax.antlr4;

import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.highlighting.HighlightsLayer;
import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
import org.netbeans.spi.editor.highlighting.ZOrder;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
@MimeRegistration(mimeType = "", service = HighlightsLayerFactory.class)
public class RealTimeCompileHighlightFactory implements HighlightsLayerFactory {

	public static RealTimeCompileHighlighter getRealTimeCompileHighlighter(Document doc) {
		RealTimeCompileHighlighter highlighter = (RealTimeCompileHighlighter) doc.getProperty(RealTimeCompileHighlighter.class);
		if (highlighter == null) {
			highlighter = new RealTimeCompileHighlighter(doc);
			doc.putProperty(RealTimeCompileHighlighter.class, highlighter);
		}
		return highlighter;
	}

	@Override
	public HighlightsLayer[] createLayers(Context context) {
		return new HighlightsLayer[]{
			HighlightsLayer.create(RealTimeCompileHighlighter.class.getName(), ZOrder.CARET_RACK.forPosition(2000), true, getRealTimeCompileHighlighter(context.getDocument()).getHighlightsBag())
		};
	}

}
