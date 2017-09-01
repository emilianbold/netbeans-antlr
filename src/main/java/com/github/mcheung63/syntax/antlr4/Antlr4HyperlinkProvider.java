package com.github.mcheung63.syntax.antlr4;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;

/**
 *
 * @author peter
 */
@MimeRegistration(mimeType = "text/x-g4", service = HyperlinkProvider.class)
public class Antlr4HyperlinkProvider implements HyperlinkProvider {

	@Override
	public boolean isHyperlinkPoint(Document dcmnt, int i) {
		return true;
	}

	@Override
	public int[] getHyperlinkSpan(Document dcmnt, int i) {
		return new int[]{10, 20};
	}

	@Override
	public void performClickAction(Document dcmnt, int i) {
		 JTextComponent target = EditorRegistry.lastFocusedComponent();
		 target.setCaretPosition(30);
	}

}
