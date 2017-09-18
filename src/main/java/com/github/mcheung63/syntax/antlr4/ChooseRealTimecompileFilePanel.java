package com.github.mcheung63.syntax.antlr4;

import com.github.mcheung63.ModuleLib;
import com.github.mcheung63.RealTimeComboModel;
import com.github.mcheung63.RealTimeComboRenderer;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Set;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class ChooseRealTimecompileFilePanel extends javax.swing.JPanel {
	
	public static HashMap<DataObject, File> maps = new HashMap<>();
	
	RealTimeComboModel realTimeComboModel = new RealTimeComboModel();
	
	public ChooseRealTimecompileFilePanel() {
		initComponents();
		initComboBox();
		setMaximumSize(new Dimension(150, 25));
		setPreferredSize(new Dimension(150, 25));
		comboBox.setVisible(false);
		refreshButton.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        comboBox = new javax.swing.JComboBox<>();
        refreshButton = new javax.swing.JButton();
        compileStatusLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setLayout(new java.awt.GridBagLayout());

        comboBox.setModel(realTimeComboModel);
        comboBox.setRenderer(new RealTimeComboRenderer());
        comboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxItemStateChanged(evt);
            }
        });
        comboBox.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                comboBoxComponentShown(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(comboBox, gridBagConstraints);

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/mcheung63/arrow_refresh.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(ChooseRealTimecompileFilePanel.class, "ChooseRealTimecompileFilePanel.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        add(refreshButton, new java.awt.GridBagConstraints());

        org.openide.awt.Mnemonics.setLocalizedText(compileStatusLabel, org.openide.util.NbBundle.getMessage(ChooseRealTimecompileFilePanel.class, "ChooseRealTimecompileFilePanel.compileStatusLabel.text")); // NOI18N
        compileStatusLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ChooseRealTimecompileFilePanel.class, "ChooseRealTimecompileFilePanel.compileStatusLabel.toolTipText")); // NOI18N
        compileStatusLabel.setOpaque(true);
        add(compileStatusLabel, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_comboBoxComponentShown
		initComboBox();
    }//GEN-LAST:event_comboBoxComponentShown

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
		initComboBox();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void comboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			File file = (File) evt.getItem();
			JTextComponent jTextComponent = EditorRegistry.lastFocusedComponent();
			ModuleLib.log("jTextComponent=" + jTextComponent);
			Document document = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(document);
			maps.put(dataObject, file);
		}
    }//GEN-LAST:event_comboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    public javax.swing.JLabel compileStatusLabel;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

	private void initComboBox() {
		synchronized (realTimeComboModel.files) {
			realTimeComboModel.files.clear();
			
			Set<TopComponent> comps = TopComponent.getRegistry().getOpened();
			for (TopComponent tc : comps) {
				Node[] arr = tc.getActivatedNodes();
				if (arr != null) {
					for (int j = 0; j < arr.length; j++) {
						DataObject dataObject = (DataObject) arr[j].getCookie(DataObject.class);
						File file = new File(dataObject.getPrimaryFile().getPath());
						if (file.exists() && file.isFile()/* && !temp.contains(file.getName())*/) {
							realTimeComboModel.files.add(file);
						}
					}
				}
//			}
			}
		}
		
		comboBox.setRenderer(new RealTimeComboRenderer()); // if no this line, combobox will show nothing after refeshing with few files
	}
}
