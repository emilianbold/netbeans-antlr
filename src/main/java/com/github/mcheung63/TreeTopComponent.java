/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mcheung63;

import com.github.mcheung63.syntax.antlr4.Ast;
import com.github.mcheung63.syntax.antlr4.AstNode;
import com.github.mcheung63.syntax.antlr4.CallGraphComponent;
import com.github.mcheung63.syntax.antlr4.GeneralParserListener;
import com.github.mcheung63.syntax.antlr4.MyANTLRv4ParserListener;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
import com.peterswing.CommonLib;
import com.peterswing.advancedswing.jprogressbardialog.JProgressBarDialog;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.antlr.parser.antlr4.ANTLRv4Lexer;
import org.antlr.parser.antlr4.ANTLRv4Parser;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.loaders.DataObject;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
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
		iconBase = "com/github/mcheung63/antlr.png",
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
public final class TreeTopComponent extends TopComponent /*implements LookupListener*/ {

//	Lookup.Result<DataObject> result;
//	public static DataObject lastDataObject;
	File pngFileName;
	File pngTargetGraphFileName;
	int preferWidth;
	int preferHeight;
	AntlrTreeNode rootNode = new AntlrTreeNode("Grammar", "Grammar");
	AntlrTreeModel treeModel = new AntlrTreeModel(rootNode);
	mxGraph graph = new mxGraph() {
		public boolean isPort(Object cell) {
			mxGeometry geo = getCellGeometry(cell);

			return (geo != null) ? geo.isRelative() : false;
		}

		public String getToolTipForCell(Object cell) {
			if (model.isEdge(cell)) {
				return convertValueToString(model.getTerminal(cell, true)) + " -> " + convertValueToString(model.getTerminal(cell, false));
			}

			return super.getToolTipForCell(cell);
		}

		public boolean isCellFoldable(Object cell, boolean collapse) {
			return false;
		}
	};
	CallGraphComponent graphComponent = new CallGraphComponent(graph);

	public TreeTopComponent() {
		initComponents();
		setName(Bundle.CTL_TreeTopComponent());
		setToolTipText(Bundle.HINT_TreeTopComponent());

		targetJGraphPanel.add(graphComponent, BorderLayout.CENTER);

//		result = Utilities.actionsGlobalContext().lookupResult(DataObject.class);
//		result.addLookupListener(this);
		antlrTree.setModel(treeModel);
		antlrTree.setCellRenderer(new AntlrTreeRenderer());
		antlrTree.setShowsRootHandles(true);

		layoutButton.removeAll();
		layoutButton.add(new JMenuItem("Hierarchical Layout"));
		layoutButton.add(new JMenuItem("Compact tree"));
		layoutButton.add(new JMenuItem("Circle Layout"));
		layoutButton.add(new JMenuItem("Organic Layout"));
		layoutButton.add(new JMenuItem("Compact Tree Layout"));
		layoutButton.add(new JMenuItem("Edge Label Layout"));
		layoutButton.add(new JMenuItem("Fast Organic Layout"));
		layoutButton.add(new JMenuItem("Orthogonal Layout"));
		layoutButton.add(new JMenuItem("Parallel Edge Layout"));
		layoutButton.add(new JMenuItem("Stack Layout"));
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        targetJGraphPanel = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        refreshTargetJgraphButton = new javax.swing.JButton();
        zoomOutJGraphButton = new javax.swing.JButton();
        show100ImageJGraphButton = new javax.swing.JButton();
        zoomInJGraphButton = new javax.swing.JButton();
        layoutButton = new com.peterswing.advancedswing.jdropdownbutton.JDropDownButton();
        targetGraphPanel = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        refreshTargetGraphvizButton = new javax.swing.JButton();
        zoomOutTargetGraphButton = new javax.swing.JButton();
        show100TargetGraphImageButton = new javax.swing.JButton();
        zoomInTargetGraphButton = new javax.swing.JButton();
        graphvizFitTargetGraphWidthButton = new javax.swing.JButton();
        graphvizFitTargetGraphHeightButton = new javax.swing.JButton();
        graphvizScrollPane1 = new javax.swing.JScrollPane();
        graphvizTargetGraphLabel = new javax.swing.JLabel();
        graphvizPanel = new javax.swing.JPanel();
        graphvizScrollPane = new javax.swing.JScrollPane();
        graphvizLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshGraphvizButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        show100ImageButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        graphvizFitWidthButton = new javax.swing.JButton();
        graphvizFitHeightButton = new javax.swing.JButton();
        treePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        antlrTree = new javax.swing.JTree();
        jToolBar2 = new javax.swing.JToolBar();
        refreshAntlrTreeButton = new javax.swing.JButton();
        expandTreeButton = new javax.swing.JButton();
        collapseTreeButton = new javax.swing.JButton();
        searchAntlrTreeTextField = new com.peterswing.advancedswing.searchtextfield.JSearchTextField();

        setLayout(new java.awt.BorderLayout());

        targetJGraphPanel.setLayout(new java.awt.BorderLayout());

        jToolBar4.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(refreshTargetJgraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.refreshTargetJgraphButton.text")); // NOI18N
        refreshTargetJgraphButton.setFocusable(false);
        refreshTargetJgraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshTargetJgraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshTargetJgraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshTargetJgraphButtonActionPerformed(evt);
            }
        });
        jToolBar4.add(refreshTargetJgraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomOutJGraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomOutJGraphButton.text")); // NOI18N
        zoomOutJGraphButton.setFocusable(false);
        zoomOutJGraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutJGraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutJGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutJGraphButtonActionPerformed(evt);
            }
        });
        jToolBar4.add(zoomOutJGraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(show100ImageJGraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.show100ImageJGraphButton.text")); // NOI18N
        show100ImageJGraphButton.setFocusable(false);
        show100ImageJGraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        show100ImageJGraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        show100ImageJGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show100ImageJGraphButtonActionPerformed(evt);
            }
        });
        jToolBar4.add(show100ImageJGraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomInJGraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomInJGraphButton.text")); // NOI18N
        zoomInJGraphButton.setFocusable(false);
        zoomInJGraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInJGraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInJGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInJGraphButtonActionPerformed(evt);
            }
        });
        jToolBar4.add(zoomInJGraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(layoutButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.layoutButton.text")); // NOI18N
        layoutButton.setFocusable(false);
        layoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        layoutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        layoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layoutButtonActionPerformed(evt);
            }
        });
        jToolBar4.add(layoutButton);

        targetJGraphPanel.add(jToolBar4, java.awt.BorderLayout.PAGE_START);

        mainTabbedPane.addTab(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.targetJGraphPanel.TabConstraints.tabTitle"), targetJGraphPanel); // NOI18N

        targetGraphPanel.setLayout(new java.awt.BorderLayout());

        jToolBar3.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(refreshTargetGraphvizButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.refreshTargetGraphvizButton.text")); // NOI18N
        refreshTargetGraphvizButton.setFocusable(false);
        refreshTargetGraphvizButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshTargetGraphvizButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshTargetGraphvizButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshTargetGraphvizButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(refreshTargetGraphvizButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomOutTargetGraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomOutTargetGraphButton.text")); // NOI18N
        zoomOutTargetGraphButton.setFocusable(false);
        zoomOutTargetGraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutTargetGraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutTargetGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutTargetGraphButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomOutTargetGraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(show100TargetGraphImageButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.show100TargetGraphImageButton.text")); // NOI18N
        show100TargetGraphImageButton.setFocusable(false);
        show100TargetGraphImageButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        show100TargetGraphImageButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        show100TargetGraphImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show100TargetGraphImageButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(show100TargetGraphImageButton);

        org.openide.awt.Mnemonics.setLocalizedText(zoomInTargetGraphButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.zoomInTargetGraphButton.text")); // NOI18N
        zoomInTargetGraphButton.setFocusable(false);
        zoomInTargetGraphButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInTargetGraphButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInTargetGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInTargetGraphButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(zoomInTargetGraphButton);

        org.openide.awt.Mnemonics.setLocalizedText(graphvizFitTargetGraphWidthButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizFitTargetGraphWidthButton.text")); // NOI18N
        graphvizFitTargetGraphWidthButton.setFocusable(false);
        graphvizFitTargetGraphWidthButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        graphvizFitTargetGraphWidthButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        graphvizFitTargetGraphWidthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphvizFitTargetGraphWidthButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(graphvizFitTargetGraphWidthButton);

        org.openide.awt.Mnemonics.setLocalizedText(graphvizFitTargetGraphHeightButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizFitTargetGraphHeightButton.text")); // NOI18N
        graphvizFitTargetGraphHeightButton.setFocusable(false);
        graphvizFitTargetGraphHeightButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        graphvizFitTargetGraphHeightButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        graphvizFitTargetGraphHeightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphvizFitTargetGraphHeightButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(graphvizFitTargetGraphHeightButton);

        targetGraphPanel.add(jToolBar3, java.awt.BorderLayout.PAGE_START);

        org.openide.awt.Mnemonics.setLocalizedText(graphvizTargetGraphLabel, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizTargetGraphLabel.text")); // NOI18N
        graphvizScrollPane1.setViewportView(graphvizTargetGraphLabel);

        targetGraphPanel.add(graphvizScrollPane1, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.targetGraphPanel.TabConstraints.tabTitle"), targetGraphPanel); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(graphvizFitWidthButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizFitWidthButton.text")); // NOI18N
        graphvizFitWidthButton.setFocusable(false);
        graphvizFitWidthButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        graphvizFitWidthButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        graphvizFitWidthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphvizFitWidthButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(graphvizFitWidthButton);

        org.openide.awt.Mnemonics.setLocalizedText(graphvizFitHeightButton, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizFitHeightButton.text")); // NOI18N
        graphvizFitHeightButton.setFocusable(false);
        graphvizFitHeightButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        graphvizFitHeightButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        graphvizFitHeightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphvizFitHeightButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(graphvizFitHeightButton);

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
			ANTLRv4Lexer lexer;
			JTextComponent jTextComponent = EditorRegistry.focusedComponent();
			if (jTextComponent != null) {
				lexer = new ANTLRv4Lexer(new ANTLRInputStream(jTextComponent.getText()));
			} else {
				TopComponent activeTC = TopComponent.getRegistry().getActivated();
				DataObject dataObject = activeTC.getLookup().lookup(DataObject.class);
				if (dataObject == null || dataObject.getPrimaryFile() == null) {
					return;
				}
				lexer = new ANTLRv4Lexer(new ANTLRInputStream(dataObject.getPrimaryFile().getInputStream()));
			}

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
//			Files.copy(dotPngFile.toPath(), new File("/Users/peter/Desktop/b.png").toPath(), REPLACE_EXISTING);
			ImageIcon icon = new ImageIcon(dotPngFile.getAbsolutePath());
			pngFileName = dotPngFile;
//				icon.getImage().flush();

			preferWidth = graphvizLabel.getWidth() > icon.getIconWidth() ? icon.getIconWidth() : graphvizLabel.getWidth();
			float ratio = ((float) preferWidth) / icon.getIconWidth();
			if (ratio == 0) {
				ratio = 1;
			}
			preferHeight = (int) (icon.getIconHeight() * ratio);
			graphvizLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));

			dotFile.delete();
			dotPngFile.deleteOnExit();
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
			ANTLRv4Lexer lexer;
			JTextComponent jTextComponent = EditorRegistry.focusedComponent();
			if (jTextComponent != null) {
				lexer = new ANTLRv4Lexer(new ANTLRInputStream(jTextComponent.getText()));
			} else {
				TopComponent activeTC = TopComponent.getRegistry().getActivated();
				DataObject dataObject = activeTC.getLookup().lookup(DataObject.class);
				if (dataObject == null || dataObject.getPrimaryFile() == null) {
					return;
				}
				lexer = new ANTLRv4Lexer(new ANTLRInputStream(dataObject.getPrimaryFile().getInputStream()));
			}
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
			treeModel.visibleNode(rootNode, false);
			//treeModel.filterTreeNode(rootNode, searchAntlrTreeTextField.getText());
			//antlrTree.updateUI();
			treeModel.nodeStructureChanged(rootNode);
			CommonLib.expandAll(antlrTree, true);
		}
    }//GEN-LAST:event_searchAntlrTreeTextFieldKeyPressed

    private void graphvizFitWidthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphvizFitWidthButtonActionPerformed
		if (pngFileName != null && pngFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngFileName.getAbsolutePath());
			icon.getImage().flush();
			preferWidth = graphvizLabel.getWidth() > icon.getIconWidth() ? icon.getIconWidth() : graphvizLabel.getWidth();
			float ratio = ((float) preferWidth) / icon.getIconWidth();
			if (ratio == 0) {
				ratio = 1;
			}
			preferHeight = (int) (icon.getIconHeight() * ratio);
			graphvizLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}
    }//GEN-LAST:event_graphvizFitWidthButtonActionPerformed

    private void graphvizFitHeightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphvizFitHeightButtonActionPerformed
		if (pngFileName != null && pngFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngFileName.getAbsolutePath());
			icon.getImage().flush();
			preferHeight = graphvizLabel.getHeight() > icon.getIconHeight() ? icon.getIconHeight() : graphvizLabel.getHeight();
			float ratio = ((float) preferHeight) / icon.getIconHeight();
			if (ratio == 0) {
				ratio = 1;
			}
			preferWidth = (int) (icon.getIconWidth() * ratio);
			graphvizLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}
    }//GEN-LAST:event_graphvizFitHeightButtonActionPerformed

    private void refreshTargetGraphvizButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshTargetGraphvizButtonActionPerformed
		try {
			JTextComponent jTextComponent = EditorRegistry.lastFocusedComponent();
			if (jTextComponent == null) {
				return;
			}
			Document doc = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File targetFile = FileTypeG4VisualElement.maps.get(dataObject);
			String targetFileName = targetFile.getName();
			if (targetFile == null) {
				return;
			}

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(jTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);

				LexerInterpreter lexer = grammar.createLexerInterpreter(new ANTLRInputStream(new FileInputStream(targetFile)));
				lexer.removeErrorListeners();

				CommonTokenStream tokenStream = new CommonTokenStream(lexer);
				ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
				//parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				String startRule = FileTypeG4VisualElement.startRules.get(dataObject);
				Rule start = grammar.getRule(startRule);
				if (start == null) {
					return;
				}
				ParserRuleContext context = parser.parse(start.index);

				ParseTreeWalker walker = new ParseTreeWalker();
				GeneralParserListener listener = new GeneralParserListener(parser);
				walker.walk(listener, context);

				Ast astNode = listener.ast;
				AstNode root = astNode.getRoot();
				root.setLabel(targetFile.getName());
				//AntlrLib.filterUnwantedSubNodes(root, new String[]{"ruleblock"});
				//AntlrLib.removeOneLeafNodes(root);
				AntlrLib.printAst("", root);

				String dot = AntlrLib.exportDot(root);

				File dotFile = File.createTempFile(targetFileName, ".dot");
				File dotPngFile = File.createTempFile(targetFileName, ".png");
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
				//Files.copy(dotPngFile.toPath(), new File("/Users/peter/Desktop/b.png").toPath(), REPLACE_EXISTING);
				ImageIcon icon = new ImageIcon(dotPngFile.getAbsolutePath());
				pngTargetGraphFileName = dotPngFile;
//				icon.getImage().flush();

				preferWidth = graphvizLabel.getWidth() > icon.getIconWidth() ? icon.getIconWidth() : graphvizLabel.getWidth();
				float ratio = ((float) preferWidth) / icon.getIconWidth();
				if (ratio == 0) {
					ratio = 1;
				}
				preferHeight = (int) (icon.getIconHeight() * ratio);
				graphvizTargetGraphLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));

				dotFile.delete();
				dotPngFile.deleteOnExit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }//GEN-LAST:event_refreshTargetGraphvizButtonActionPerformed

    private void zoomOutTargetGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutTargetGraphButtonActionPerformed
		if (pngTargetGraphFileName != null && pngTargetGraphFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngTargetGraphFileName.getAbsolutePath());

			icon.getImage().flush();
			preferWidth = (int) (((float) preferWidth) * 1.1);
			preferHeight = (int) (((float) preferHeight) * 1.1);
			graphvizTargetGraphLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}

    }//GEN-LAST:event_zoomOutTargetGraphButtonActionPerformed

    private void show100TargetGraphImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show100TargetGraphImageButtonActionPerformed
		if (pngTargetGraphFileName != null && pngTargetGraphFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngTargetGraphFileName.getAbsolutePath());
			icon.getImage().flush();
			preferWidth = icon.getIconWidth();
			preferHeight = icon.getIconHeight();
			graphvizTargetGraphLabel.setIcon(resizeImage(icon, icon.getIconWidth(), icon.getIconHeight()));
		}

    }//GEN-LAST:event_show100TargetGraphImageButtonActionPerformed

    private void zoomInTargetGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInTargetGraphButtonActionPerformed

		if (pngTargetGraphFileName != null && pngTargetGraphFileName.exists()) {
			ImageIcon icon = new ImageIcon(pngTargetGraphFileName.getAbsolutePath());
			icon.getImage().flush();
			preferWidth = (int) (((float) preferWidth) * 0.9);
			preferHeight = (int) (((float) preferHeight) * 0.9);
			graphvizTargetGraphLabel.setIcon(resizeImage(icon, preferWidth, preferHeight));
		}

    }//GEN-LAST:event_zoomInTargetGraphButtonActionPerformed

    private void graphvizFitTargetGraphWidthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphvizFitTargetGraphWidthButtonActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_graphvizFitTargetGraphWidthButtonActionPerformed

    private void graphvizFitTargetGraphHeightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphvizFitTargetGraphHeightButtonActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_graphvizFitTargetGraphHeightButtonActionPerformed

    private void zoomOutJGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutJGraphButtonActionPerformed
		if (graphComponent != null) {
			graphComponent.zoomOut();
		}
    }//GEN-LAST:event_zoomOutJGraphButtonActionPerformed

    private void show100ImageJGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show100ImageJGraphButtonActionPerformed
		if (graphComponent != null) {
			graphComponent.zoomActual();
		}
    }//GEN-LAST:event_show100ImageJGraphButtonActionPerformed

    private void zoomInJGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInJGraphButtonActionPerformed
		if (graphComponent != null) {
			graphComponent.zoomIn();
		}
    }//GEN-LAST:event_zoomInJGraphButtonActionPerformed

    private void layoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layoutButtonActionPerformed
		final mxGraph graph = graphComponent.getGraph();
		Object cell = graph.getSelectionCell();

		if (cell == null || graph.getModel().getChildCount(cell) == 0) {
			cell = graph.getDefaultParent();
		}
		graph.getModel().beginUpdate();

		String str;
		if (layoutButton.getEventSource() == null) {
			str = "Hierarchical Layout";
		} else {
			str = ((JMenuItem) layoutButton.getEventSource()).getText();
		}
		layoutButton.setText(str);
		if (str.equals("Hierarchical Layout")) {
			mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Circle Layout")) {
			mxCircleLayout layout = new mxCircleLayout(graph);
			layout.setDisableEdgeStyle(false);
			layout.execute(cell);
		} else if (str.equals("Organic Layout")) {
			mxOrganicLayout layout = new mxOrganicLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Compact Tree Layout")) {
			mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
			layout.execute(cell);
		} else if (str.equals("Edge Label Layout")) {
			mxEdgeLabelLayout layout = new mxEdgeLabelLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Fast Organic Layout")) {
			mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Orthogonal Layout")) {
			mxOrthogonalLayout layout = new mxOrthogonalLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Parallel Edge Layout")) {
			mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Stack Layout")) {
			mxStackLayout layout = new mxStackLayout(graph);
			layout.execute(cell);
		} else if (str.equals("Compact tree")) {
			mxCompactTreeLayout layout=new mxCompactTreeLayout(graph);
			layout.execute(cell);
		} else {
			System.out.println("no this layout");
		}

		mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);
		morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {
			public void invoke(Object sender, mxEventObject evt) {
				graph.getModel().endUpdate();
			}
		});

		morph.startAnimation();
    }//GEN-LAST:event_layoutButtonActionPerformed

    private void refreshTargetJgraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshTargetJgraphButtonActionPerformed
      try {
			JTextComponent jTextComponent = EditorRegistry.lastFocusedComponent();
			if (jTextComponent == null) {
				return;
			}
			Document doc = jTextComponent.getDocument();
			DataObject dataObject = NbEditorUtilities.getDataObject(doc);
			File targetFile = FileTypeG4VisualElement.maps.get(dataObject);
			if (targetFile == null) {
				return;
			}
			String targetFileName = targetFile.getName();

			Tool tool = new Tool();
			GrammarRootAST ast = tool.parseGrammarFromString(jTextComponent.getText());
			if (ast.grammarType == ANTLRParser.COMBINED) {
				Grammar grammar = tool.createGrammar(ast);
				tool.process(grammar, false);

				LexerInterpreter lexer = grammar.createLexerInterpreter(new ANTLRInputStream(new FileInputStream(targetFile)));
				lexer.removeErrorListeners();

				CommonTokenStream tokenStream = new CommonTokenStream(lexer);
				ParserInterpreter parser = grammar.createParserInterpreter(tokenStream);
				//parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				String startRule = FileTypeG4VisualElement.startRules.get(dataObject);
				Rule start = grammar.getRule(startRule);
				if (start == null) {
					return;
				}
				ParserRuleContext context = parser.parse(start.index);

				ParseTreeWalker walker = new ParseTreeWalker();
				GeneralParserListener listener = new GeneralParserListener(parser);
				walker.walk(listener, context);

				Ast astNode = listener.ast;
				AstNode root = astNode.getRoot();
				root.setLabel(targetFile.getName());
				//AntlrLib.filterUnwantedSubNodes(root, new String[]{"ruleblock"});
				//AntlrLib.removeOneLeafNodes(root);
				AntlrLib.printAst("", root);

				buildJGraph(root);
				layoutButtonActionPerformed(null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }//GEN-LAST:event_refreshTargetJgraphButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree antlrTree;
    private javax.swing.JButton collapseTreeButton;
    private javax.swing.JButton expandTreeButton;
    private javax.swing.JButton graphvizFitHeightButton;
    private javax.swing.JButton graphvizFitTargetGraphHeightButton;
    private javax.swing.JButton graphvizFitTargetGraphWidthButton;
    private javax.swing.JButton graphvizFitWidthButton;
    private javax.swing.JLabel graphvizLabel;
    private javax.swing.JPanel graphvizPanel;
    private javax.swing.JScrollPane graphvizScrollPane;
    private javax.swing.JScrollPane graphvizScrollPane1;
    private javax.swing.JLabel graphvizTargetGraphLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private com.peterswing.advancedswing.jdropdownbutton.JDropDownButton layoutButton;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JButton refreshAntlrTreeButton;
    private javax.swing.JButton refreshGraphvizButton;
    private javax.swing.JButton refreshTargetGraphvizButton;
    private javax.swing.JButton refreshTargetJgraphButton;
    private com.peterswing.advancedswing.searchtextfield.JSearchTextField searchAntlrTreeTextField;
    private javax.swing.JButton show100ImageButton;
    private javax.swing.JButton show100ImageJGraphButton;
    private javax.swing.JButton show100TargetGraphImageButton;
    private javax.swing.JPanel targetGraphPanel;
    private javax.swing.JPanel targetJGraphPanel;
    private javax.swing.JPanel treePanel;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomInJGraphButton;
    private javax.swing.JButton zoomInTargetGraphButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JButton zoomOutJGraphButton;
    private javax.swing.JButton zoomOutTargetGraphButton;
    // End of variables declaration//GEN-END:variables

	void writeProperties(java.util.Properties p) {
		p.setProperty("version", "1.0");
	}

	void readProperties(java.util.Properties p) {
		String version = p.getProperty("version");
	}

//	@Override
//	public void resultChanged(LookupEvent le) {
//		Collection<? extends DataObject> dataObjects = result.allInstances();
//		if (dataObjects.size() > 0) {
//			DataObject d = (DataObject) dataObjects.toArray()[dataObjects.size() - 1];
//			if (d.getPrimaryFile().getExt().equals("g4")) {
//				lastDataObject = d;
//			}
//		}
//	}
	ImageIcon resizeImage(ImageIcon icon, int width, int height) {
		return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	private void buildJGraph(AstNode root) {
		buildJGraphNode(root, null);
		graph.setCellsDisconnectable(false);
		graph.getModel().beginUpdate();
		mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);
		morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {
			public void invoke(Object sender, mxEventObject evt) {
				graph.getModel().endUpdate();
			}
		});

		morph.startAnimation();
	}

	private void buildJGraphNode(AstNode node, mxCell mxNode) {
		mxCell rootNode = (mxCell) graph.insertVertex(null, null, node.getLabel(), 40, 40, 150, 30);
		if (mxNode != null) {
			graph.insertEdge(null, null, "", mxNode, rootNode, mxConstants.STYLE_STROKECOLOR + "=#000000;edgeStyle=elbowEdgeStyle;");
		}

		for (AstNode nn : node.getChildren()) {
			buildJGraphNode(nn, rootNode);
		}
	}

}
