package com.github.mcheung63.syntax.antlr4.realtimecompile;

import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import org.netbeans.api.editor.settings.AttributesUtilities;
import org.netbeans.spi.editor.highlighting.support.OffsetsBag;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class RealTimeCompileHighlighter {

	public OffsetsBag bag;
	public static final AttributeSet defaultColors = AttributesUtilities.createImmutable(StyleConstants.Background, Color.pink);

	public RealTimeCompileHighlighter(Document doc) {
		bag = new OffsetsBag(doc);
	}

	public OffsetsBag getHighlightsBag() {
		return bag;
	}
}
