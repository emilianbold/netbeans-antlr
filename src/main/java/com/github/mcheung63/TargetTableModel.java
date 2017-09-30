package com.github.mcheung63;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class TargetTableModel extends DefaultTableModel {

	String columnNames[] = new String[]{"Line", "Token"};
	ArrayList<String> lines = new ArrayList<>();
	ArrayList<String> tokens = new ArrayList<>();

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return lines.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		try {
			if (column == 0) {
				return lines.get(row);
			} else if (column == 1) {
				return tokens.get(row);
			} else {
				return null;
			}
		} catch (Exception ex) {
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
