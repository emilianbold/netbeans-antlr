package com.github.mcheung63.syntax.antlr4;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
@MimeRegistration(mimeType = "text/x-g4", service = HyperlinkProvider.class)
public class Antlr4HyperlinkProvider implements HyperlinkProvider {

	@Override
	public boolean isHyperlinkPoint(Document dcmnt, int i) {
		for (TokenDocumentLocation tokenDocumentLocation : MyANTLRv4ParserListener.ruleTokenDocumentLocationSources) {
			//System.out.println(i + " ===> " + tokenDocumentLocation);
			if (MyANTLRv4ParserListener.containTarget(tokenDocumentLocation.text) && tokenDocumentLocation.start <= i && i <= tokenDocumentLocation.stop) {
				//System.out.println("good ===> " + tokenDocumentLocation);
				return true;
			} else {
				//System.out.println("shit ===> " + tokenDocumentLocation);
			}
		}
		return false;
	}

	@Override
	public int[] getHyperlinkSpan(Document dcmnt, int i) {
		for (TokenDocumentLocation tokenDocumentLocation : MyANTLRv4ParserListener.ruleTokenDocumentLocationSources) {
			if (tokenDocumentLocation.start <= i && i <= tokenDocumentLocation.stop) {
				return new int[]{tokenDocumentLocation.start, tokenDocumentLocation.stop + 1};
			}
		}
		return null;
	}

	@Override
	public void performClickAction(Document dcmnt, int i) {
		JTextComponent jtextComponent = EditorRegistry.lastFocusedComponent();
		String text = null;
		for (TokenDocumentLocation tokenDocumentLocation : MyANTLRv4ParserListener.ruleTokenDocumentLocationSources) {
			if (MyANTLRv4ParserListener.containTarget(tokenDocumentLocation.text)) {
				if (tokenDocumentLocation.start <= i && i <= tokenDocumentLocation.stop) {
					text = tokenDocumentLocation.text;
					break;
				}
			}
		}
		System.out.println("text=" + text);
		if (text != null) {
			for (TokenDocumentLocation tokenDocumentLocation : MyANTLRv4ParserListener.ruleTokenDocumentLocationTargets) {
				System.out.println("===" + tokenDocumentLocation.text);
				if (tokenDocumentLocation.text.equals(text)) {
					System.out.println("fuck=" + tokenDocumentLocation.start);
					jtextComponent.setCaretPosition(tokenDocumentLocation.start);
					return;
				}
			}
		}
	}

}
