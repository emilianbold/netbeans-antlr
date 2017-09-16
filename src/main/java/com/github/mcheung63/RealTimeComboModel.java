package com.github.mcheung63;

import java.io.File;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class RealTimeComboModel implements ComboBoxModel {

	public ArrayList<File> files = new ArrayList<>();

	File selection = null;

	@Override
	public Object getElementAt(int index) {
		return files.toArray()[index];
	}

	@Override
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

	@Override
	public void addListDataListener(ListDataListener l) {
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}

}
