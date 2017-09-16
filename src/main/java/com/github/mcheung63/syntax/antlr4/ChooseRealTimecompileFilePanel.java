package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.RealTimeComboModel;
import com.github.mcheung63.RealTimeComboRenderer;
import java.awt.Dimension;
import java.io.File;
import java.util.Set;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ChooseRealTimecompileFilePanel extends javax.swing.JPanel {

	RealTimeComboModel realTimeComboModel = new RealTimeComboModel();

	public ChooseRealTimecompileFilePanel() {
		initComponents();
		initComboBox();
		setMaximumSize(new Dimension(150, 25));
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBox = new javax.swing.JComboBox<>();

        setLayout(new java.awt.BorderLayout());

        comboBox.setModel(realTimeComboModel);
        comboBox.setRenderer(new RealTimeComboRenderer());
        comboBox.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                comboBoxComponentShown(evt);
            }
        });
        add(comboBox, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_comboBoxComponentShown
		// TODO add your handling code here:
    }//GEN-LAST:event_comboBoxComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    // End of variables declaration//GEN-END:variables

	private void initComboBox() {
		realTimeComboModel.files.clear();

		Set<TopComponent> comps = TopComponent.getRegistry().getOpened();
//		ArrayList<String> temp = new ArrayList<>();
		for (TopComponent tc : comps) {
			Node[] arr = tc.getActivatedNodes();
			if (arr != null) {
				for (int j = 0; j < arr.length; j++) {
					DataObject dataObject = (DataObject) arr[j].getCookie(DataObject.class);
					File file = new File(dataObject.getPrimaryFile().getPath());
					if (file.exists() && file.isFile()/* && !temp.contains(file.getName())*/) {
						realTimeComboModel.files.add(file);
//						temp.add(file.getName());
					}
				}
			}
		}
	}
}
