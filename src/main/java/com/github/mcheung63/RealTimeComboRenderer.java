package com.github.mcheung63;

import java.awt.Component;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class RealTimeComboRenderer extends JLabel implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		File file = (File) value;
		if (file != null) {
			setText(file.getName());
		}
		return this;
	}

}
