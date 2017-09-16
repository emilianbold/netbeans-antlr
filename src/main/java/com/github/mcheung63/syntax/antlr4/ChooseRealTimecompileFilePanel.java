package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.RealTimeComboModel;
import com.github.mcheung63.RealTimeComboRenderer;
import java.awt.Dimension;
import java.io.File;
import java.util.Set;
import javax.swing.JEditorPane;
import org.netbeans.core.spi.multiview.MultiViewFactory;
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
		setMaximumSize(new Dimension(300, 25));
		setPreferredSize(new Dimension(300, 25));
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBox = new javax.swing.JComboBox<>();
        refreshButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        comboBox.setModel(realTimeComboModel);
        comboBox.setRenderer(new RealTimeComboRenderer());
        comboBox.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                comboBoxComponentShown(evt);
            }
        });
        add(comboBox, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(ChooseRealTimecompileFilePanel.class, "ChooseRealTimecompileFilePanel.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        add(refreshButton, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_comboBoxComponentShown
		initComboBox();
    }//GEN-LAST:event_comboBoxComponentShown

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
		initComboBox();
    }//GEN-LAST:event_refreshButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

	private void initComboBox() {
		synchronized (realTimeComboModel.files) {
			realTimeComboModel.files.clear();

			Set<TopComponent> comps = TopComponent.getRegistry().getOpened();
//		ArrayList<String> temp = new ArrayList<>();
			for (TopComponent tc : comps) {
				ModuleLib.log("tc1 = " + tc);
//			if (tc.getPeer() instanceof MultiViewFactory) {
				Node[] arr = tc.getActivatedNodes();
				if (arr != null) {
					for (int j = 0; j < arr.length; j++) {
						JEditorPane editorPane = (JEditorPane) tc.getLookup().lookup(JEditorPane.class);
						ModuleLib.log("		editorPane = " + editorPane);
						DataObject dataObject = (DataObject) arr[j].getCookie(DataObject.class);
						File file = new File(dataObject.getPrimaryFile().getPath());
						if (file.exists() && file.isFile()/* && !temp.contains(file.getName())*/) {
							realTimeComboModel.files.add(file);
//						temp.add(file.getName());
						}
					}
				}
//			}
			}
		}
//		comboBox.validate();
//		comboBox.repaint();
//		ModuleLib.log("size=" + realTimeComboModel.files.size());
//		for (File file : realTimeComboModel.files) {
//			ModuleLib.log("\t" + file.getName());
//		}
	}
}
