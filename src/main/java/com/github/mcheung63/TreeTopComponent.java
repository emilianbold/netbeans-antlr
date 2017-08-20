/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mcheung63;

import com.peterswing.CommonLib;
import java.util.Collection;
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

	public TreeTopComponent() {
		initComponents();
		setName(Bundle.CTL_TreeTopComponent());
		setToolTipText(Bundle.HINT_TreeTopComponent());

		result = Utilities.actionsGlobalContext().lookupResult(DataObject.class);
		result.addLookupListener(this);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        graphvizScrollPane = new javax.swing.JScrollPane();
        graphvizLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshGraphvizButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(graphvizLabel, org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.graphvizLabel.text")); // NOI18N
        graphvizScrollPane.setViewportView(graphvizLabel);

        jPanel1.add(graphvizScrollPane, java.awt.BorderLayout.CENTER);

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

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TreeTopComponent.class, "TreeTopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jScrollPane1.setViewportView(jTree1);

        jTabbedPane1.addTab("tab1", jScrollPane1);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshGraphvizButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshGraphvizButtonActionPerformed
		try {

//			Lookup.Result<Openable> lookupResults = Utilities.actionsGlobalContext().lookupResult(Openable.class);
//			for (Openable o : lookupResults.allInstances()) {
//				ModuleLib.log("o=" + o);
//			}
//			Lookup.Result<FileObject> result = Utilities.actionsGlobalContext().lookupResult(FileObject.class);
//			Lookup.Result<DataObject> result = Lookup.getDefault().lookupAll(DataObject.class);
//			Collection<? extends FileObject> c = Lookup.getDefault().lookupAll(FileObject.class);
//			ModuleLib.log("c2=" + result.allInstances());
//			for (FileObject o : result.allInstances()) {
//				ModuleLib.log("o=" + o);
//			}
			DataObject DataObject = Utilities.actionsGlobalContext().lookup(DataObject.class);
			ModuleLib.log("DataObject=" + DataObject);

			/*
			ANTLRv4Lexer lexer = new ANTLRv4Lexer(new ANTLRInputStream(getClass().getResourceAsStream("simple1.g4")));

//		Token token = lexer.nextToken();
//		while (token.getType() != Lexer.EOF) {
//			System.out.println(token + "=" + ANTLRv4Lexer.VOCABULARY.getSymbolicName(token.getType()));
//			token = lexer.nextToken();
//		}
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
//		parser.addParseListener(listener);

			ANTLRv4Parser.GrammarSpecContext context = parser.grammarSpec();

			ParseTreeWalker walker = new ParseTreeWalker();
			MyANTLRv4ParserListener listener = new MyANTLRv4ParserListener(parser);
			walker.walk(listener, context);

			//ParseTree tree = parser.grammarSpec();
			//parser.grammarSpec();
			Ast ast = listener.getAst();
			List<AstNode> nodes = ast.getNodes();
//		System.out.println("nodes.size=" + nodes.size());
//		for (AstNode n : nodes) {
//			System.out.println(n);
//			loop("", n);
//		}
			loop("", nodes.get(0));

			MutableGraph g = Parser.read("graph {\n"
					+ "    { rank=same; white}\n"
					+ "    { rank=same; cyan; yellow; pink}\n"
					+ "    { rank=same; red; green; blue}\n"
					+ "    { rank=same; black}\n"
					+ "\n"
					+ "    white -- cyan -- blue\n"
					+ "    white -- yellow -- green\n"
					+ "    white -- pink -- red\n"
					+ "\n"
					+ "    cyan -- green -- black\n"
					+ "    yellow -- red -- black\n"
					+ "    pink -- blue -- black\n"
					+ "}");
//		Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-1.png"));
			g.generalAttrs()
					.add(Color.WHITE.gradient(Color.rgb("888888")).background().angle(90))
					.nodeAttrs().add(Color.WHITE.fill())
					.nodes().forEach(node
							-> node.add(
							Color.named(node.label().toString()),
							Style.lineWidth(4).and(Style.FILLED)));

			BufferedImage image = Graphviz.fromGraph(g).render(Format.PNG).toImage();
			graphvizLabel.setIcon(new ImageIcon(image));
			 */
		} catch (Exception ex) {
			ModuleLib.log(CommonLib.printException(ex));
		}
    }//GEN-LAST:event_refreshGraphvizButtonActionPerformed

	void loop(String s, AstNode n) {
		System.out.println(s + n);
		for (AstNode nn : n.getChildren()) {
			loop(s + "    ", nn);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel graphvizLabel;
    private javax.swing.JScrollPane graphvizScrollPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton refreshGraphvizButton;
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
		Collection<? extends DataObject> shits = result.allInstances();
		for (DataObject d : shits) {
			ModuleLib.log("resultChanged=" + d);
		}
		ModuleLib.log("-------------");
	}
}
