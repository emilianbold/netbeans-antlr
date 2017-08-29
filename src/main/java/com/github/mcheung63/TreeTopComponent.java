/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mcheung63;

import com.github.mcheung63.syntax.antlr4.Ast;
import com.github.mcheung63.syntax.antlr4.AstNode;
import com.github.mcheung63.syntax.antlr4.MyANTLRv4ParserListener;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Collection;
import javax.swing.ImageIcon;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import javax.swing.tree.DefaultTreeModel;
import org.openide.util.Exceptions;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
		dtd = "-//com.github.mcheung63//Tree//EN",
		autostore = false
)
@TopComponent.Description(
		preferredID = "TreeTopComponent",
		iconBase = "com/github/mcheung63/ruby.png",
		persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.github.mcheung63.TreeTopComponent")
@ActionReference(path = "Menu/Window/Antlr" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
		displayName = "#CTL_TreeAction",
		preferredID = "TreeTopComponent"
)
@Messages({
	"CTL_TreeAction=Tree",
	"CTL_TreeTopComponent=Tree Window",
	"HINT_TreeTopComponent=This is a Tree window"
})
public final class TreeTopComponent extends TopComponent implements LookupListener {

	Lookup.Result<DataObject> result;
	DataObject lastDataObject;
	File pngFileName;
	int preferWidth;
	int preferHeight;
	AntlrTreeNode rootNode = new AntlrTreeNode("Grammar", "Grammar");
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

	public TreeTopComponent() {
		initComponents();
		setName(Bundle.CTL_TreeTopComponent());
		setToolTipText(Bundle.HINT_TreeTopComponent());

		result = Utilities.actionsGlobalContext().lookupResult(DataObject.class);
		result.addLookupListener(this);

		antlrTree.setModel(treeModel);
		antlrTree.setCellRenderer(new AntlrTreeRenderer());
		antlrTree.setShowsRootHandles(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        graphvizPanel = new javax.swing.JPanel();
        graphvizScrollPane = new javax.swing.JScrollPane();
        graphvizLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshGraphvizButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        show100ImageButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        treePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        antlrTree = new javax.swing.JTree();
        jToolBar2 = new javax.swing.JToolBar();
        refreshAntlrTreeButton = new javax.swing.JButton();
        expandTreeButton = new javax.swing.JButton();
        collapseTreeButton = new javax.swing.JButton();
        searchAntlrTreeTextField = new com.peterswing.advancedswing.searchtextfield.JSearchTextField();

        setLayout(new java.awt.BorderLayout());

        graphvizPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(graphvizLabel, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizLabel.text")); // NOI18N
        graphvizScrollPane.setViewportView(graphvizLabel);

        graphvizPanel.add(graphvizScrollPane, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(refreshGraphvizButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.refreshGraphvizButton.text")); // NOI18N
        refreshGraphvizButton.setFocusable(false);
        refreshGraphvizButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshGraphvizButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshGraphvizButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshGraphvizButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshGraphvizButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomOutButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomOutButton.text")); // NOI18N
        zoomOutButton.setFocusable(false);
        zoomOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(zoomOutButton);

        org.openide.awt.Mnemonics.setLocalizedText(show100ImageButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.show100ImageButton.text")); // NOI18N
        show100ImageButton.setFocusable(false);
        show100ImageButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        show100ImageButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        show100ImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show100ImageButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(show100ImageButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomInButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomInButton.text")); // NOI18N
        zoomInButton.setFocusable(false);
        zoomInButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(zoomInButton);

        graphvizPanel.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        mainTabbedPane.addTab(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizPanel.TabConstraints.tabTitle"), graphvizPanel); // NOI18N

        treePanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(antlrTree);

        treePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar2.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(refreshAntlrTreeButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.refreshAntlrTreeButton.text")); // NOI18N
        refreshAntlrTreeButton.setFocusable(false);
        refreshAntlrTreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshAntlrTreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshAntlrTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshAntlrTreeButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(refreshAntlrTreeButton);

        org.openide.awt.Mnemonics.setLocalizedText(expandTreeButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.expandTreeButton.text")); // NOI18N
        expandTreeButton.setFocusable(false);
        expandTreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        expandTreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        expandTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandTreeButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(expandTreeButton);

        org.openide.awt.Mnemonics.setLocalizedText(collapseTreeButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.collapseTreeButton.text")); // NOI18N
        collapseTreeButton.setFocusable(false);
        collapseTreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        collapseTreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        collapseTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapseTreeButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(collapseTreeButton);

        searchAntlrTreeTextField.setText(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.searchAntlrTreeTextField.text")); // NOI18N
        searchAntlrTreeTextField.setMaximumSize(new java.awt.Dimension(200, 25));
        searchAntlrTreeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchAntlrTreeTextFieldKeyPressed(evt);
            }
        });
        jToolBar2.add(searchAntlrTreeTextField);

        treePanel.add(jToolBar2, java.awt.BorderLayout.PAGE_START);

        mainTabbedPane.addTab(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.treePanel.TabConstraints.tabTitle"), treePanel); // NOI18N

        add(mainTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshGraphvizButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshGraphvizButtonActionPerformed
		try {
			if (lastDataObject != null) {
//				String content = IOUtils.toString(lastDataObject.getPrimaryFile().getInputStream(), "UTF-8");
				ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(lastDataObject.getPrimaryFile().getInputStream()));
				CommonTokenStream tokenStream = new CommonTokenStream(lexer);
				ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
				ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();
				ParseTreeWalker walker = new ParseTreeWalker();
				MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
				walker.walk(listener, context);

				Ast ast = listener.ast;
				AstNode root = ast.getRoot();
				AntlrLib.filterUnwantedSubNodes(root, new String[]{"ruleblock"});
				AntlrLib.removeOneLeafNodes(root);
				AntlrLib.printAst("", root);

				String dot = AntlrLib.exportDot(root);

				System.out.println(dot);

				File dotFile = File.createTempFile("netbeans-antlr", ".dot");
				File dotPngFile = File.createTempFile("netbeans-antlr", ".png");
				FileUtils.writeStringToFile(dotFile, dot, "UTF-8");
//				MutableGraph g = Parser.read(dot);
//				BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
				JProgressBarDialog d = new JProgressBarDialog("Generating dot...", true);
				d.progressBar.setIndeterminate(true);
				d.progressBar.setStringPainted(true);
				d.progressBar.setString("running dot command : " + "dot -Tpng " + dotFile.getAbsolutePath() + " -o " + dotPngFile.getAbsolutePath());
				if (ModuleLib.isMac()) {
					CommonLib.runCommand("/opt/local/bin/dot -Tpng " + dotFile.getAbsolutePath() + " -o " + dotPngFile.getAbsolutePath());
				} else {
					CommonLib.runCommand("dot -Tpng " + dotFile.getAbsolutePath() + " -o " + dotPngFile.getAbsolutePath());
				}
				Files.copy(dotPngFile.toPath(), new File("/Users/peter/Desktop/b.png").toPath(), REPLACE_EXISTING);
				ImageIcon icon = new ImageIcon(dotPngFile.getAbsolutePath());
				pngFileName = dotPngFile;
//				icon.getImage().flush();
				graphvizLabel.setIcon(icon);

				preferWidth = graphvizLabel.getWidth() > icon.getIconWidth() ? icon.getIconWidth() : graphvizLabel.getWidth();
				float ratio = ((float) preferWidth) / icon.getIconWidth();
				if (ratio == 0) {
					ratio = 1;
				}
				preferHeight = (int) (icon.getIconHeight() * ratio);

				dotFile.delete();
				dotPngFile.deleteOnExit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }//GEN-LAST:event_refreshGraphvizButtonActionPerformed

    private void show100ImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show100ImageButtonActionPerformed
		if (pngFileName != null && pngFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngFileName.getAbsolutePath());
			icon.getImage().flush();
			preferWidth = icon.getIconWidth();
			preferHeight = icon.getIconHeight();
			graphvizLabel.setIcon(resizeImage(icon, icon.getIconWidth(), icon.getIconHeight()));
		}
    }//GEN-LAST:event_show100ImageButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
		if (pngFileName != null && pngFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngFileName.getAbsolutePath());
			icon.getImage().flush();
			preferWidth = (int) (((float) preferWidth) * 0.9);
			preferHeight = (int) (((float) preferHeight) * 0.9);
			graphvizLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
		if (pngFileName != null && pngFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngFileName.getAbsolutePath());

			icon.getImage().flush();
			preferWidth = (int) (((float) preferWidth) * 1.1);
			preferHeight = (int) (((float) preferHeight) * 1.1);
			graphvizLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void refreshAntlrTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshAntlrTreeButtonActionPerformed
		try {
			ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(lastDataObject.getPrimaryFile().getInputStream()));
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
			ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();
			ParseTreeWalker walker = new ParseTreeWalker();
			MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
			walker.walk(listener, context);

			Ast ast = listener.ast;
			AstNode root = ast.getRoot();
			AntlrLib.filterUnwantedSubNodes(root, new String[]{"ruleblock"});
			AntlrLib.removeOneLeafNodes(root);
			rootNode.removeAllChildren();
			AntlrLib.buildTree(root, rootNode);
			CommonLib.expandAll(antlrTree, true);
		} catch (Exception ex) {
			Exceptions.printStackTrace(ex);
		}
    }//GEN-LAST:event_refreshAntlrTreeButtonActionPerformed

    private void expandTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandTreeButtonActionPerformed
		CommonLib.expandAll(antlrTree, true);
    }//GEN-LAST:event_expandTreeButtonActionPerformed

    private void collapseTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collapseTreeButtonActionPerformed
		CommonLib.expandAll(antlrTree, false);
    }//GEN-LAST:event_collapseTreeButtonActionPerformed

    private void searchAntlrTreeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchAntlrTreeTextFieldKeyPressed
		ModuleLib.log(evt.getKeyCode());
		if (evt.getKeyCode() == 10) {
			visibleNode(rootNode, false);
			filterTreeNode(rootNode, searchAntlrTreeTextField.getText());
			treeModel.reload();
			CommonLib.expandAll(antlrTree, true);
		}
    }//GEN-LAST:event_searchAntlrTreeTextFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree antlrTree;
    private javax.swing.JButton collapseTreeButton;
    private javax.swing.JButton expandTreeButton;
    private javax.swing.JLabel graphvizLabel;
    private javax.swing.JPanel graphvizPanel;
    private javax.swing.JScrollPane graphvizScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JButton refreshAntlrTreeButton;
    private javax.swing.JButton refreshGraphvizButton;
    private com.peterswing.advancedswing.searchtextfield.JSearchTextField searchAntlrTreeTextField;
    private javax.swing.JButton show100ImageButton;
    private javax.swing.JPanel treePanel;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables
	@Override
	public void componentOpened() {
		// TODO add custom code on component opening
	}

	@Override
	public void componentClosed() {
		// TODO add custom code on component closing
	}

	void writeProperties(java.util.Properties p) {
		// better to version settings since initial version as advocated at
		// http://wiki.apidesign.org/wiki/PropertyFiles
		p.setProperty("version", "1.0");
		// TODO store your settings
	}

	void readProperties(java.util.Properties p) {
		String version = p.getProperty("version");
		// TODO read your settings according to their version
	}

	@Override
	public void resultChanged(LookupEvent le) {
		Collection<? extends DataObject> dataObjects = result.allInstances();
		for (DataObject d : dataObjects) {
			if (d.getPrimaryFile().getExt().equals("g4")) {
				lastDataObject = d;
			}
		}
	}

	ImageIcon resizeImage(ImageIcon icon, int width, int height) {
		return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	private void visibleNode(AntlrTreeNode node, boolean b) {
		rootNode.visible = b;
		for (int x = 0; x < node.getChildCount(); x++) {
			visibleNode((AntlrTreeNode) node.getChildAt(x), b);
		}
	}

	private void filterTreeNode(AntlrTreeNode node, String text) {
		if (node.text.toLowerCase().contains(text.toLowerCase())) {
			ModuleLib.log("hit=" + node.text);
			AntlrTreeNode nn = node;
			do {
				nn.visible = true;
				nn = (AntlrTreeNode) nn.getParent();
			} while (nn != null);
		}

		for (int x = 0; x < node.getChildCount(); x++) {
			filterTreeNode((AntlrTreeNode) node.getChildAt(x), text);
		}
	}
}
