package com.github.mcheung63;

import java.io.File;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class RealTimeComboModel extends AbstractListModel implements ComboBoxModel {

	public TreeSet<File> files = new TreeSet<>();

	File selection = null;

	public Object getElementAt(int index) {
		return files.toArray()[index];
	}

	public int getSize() {
		return files.size();
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (File) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

}
