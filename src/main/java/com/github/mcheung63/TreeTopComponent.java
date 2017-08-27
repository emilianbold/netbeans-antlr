/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mcheung63;

import com.peterswing.CommonLib;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.tree.Tree;
import org.antlr.v4.Tool;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.antlr.v4.tool.ast.GrammarAST;
import org.antlr.v4.tool.ast.GrammarASTErrorNode;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.apache.commons.io.IOUtils;
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
import org.snt.inmemantlr.GenericParser;
import org.snt.inmemantlr.listener.DefaultTreeListener;
import org.snt.inmemantlr.tree.Ast;
import org.snt.inmemantlr.tree.AstNode;

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
			Tool tool = new Tool();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			System.out.println("getClass().getResource(\"/test/Assembler.g4\")=" + classLoader.getResource("../../../test/Assembler.g4"));
			System.out.println("getClass().getResource(\"/test/Assembler.g4\")=" + classLoader.getResource("../../test/Assembler.g4"));
			System.out.println("getClass().getResource(\"/test/Assembler.g4\")=" + classLoader.getResource("../test/Assembler.g4"));
			String content = IOUtils.toString(classLoader.getResourceAsStream("/test/Assembler.g4"), "UTF-8");
			GrammarRootAST ast = tool.parseGrammarFromString(content);
			System.out.println("ast.grammarType=" + ast.grammarType);
			if (ast.grammarType == ANTLRParser.COMBINED) {
				System.out.println("ast=" + ast);
				if ((GrammarAST) ast instanceof GrammarASTErrorNode) {
					System.out.println("GrammarASTErrorNode");
					return;
				}

				if (ast.hasErrors) {
					System.out.println("hasErrors");
					return;
				}

				System.out.println(ast.toStringTree());
				Grammar g = tool.createGrammar(ast);
				tool.process(g, false);
				System.out.println("g=" + g);
				System.out.println("tool=" + tool);
				for (int x = 0; x < ast.getChildCount(); x++) {
					printAST(ast.getChild(x));
				}
				LexerInterpreter lex = g.createLexerInterpreter(new ANTLRInputStream(";comment1"));
				for (Token token : lex.getAllTokens()) {
					System.out.println("token=" + token);
				}
				lex.reset();

				BaseErrorListener printError = new BaseErrorListener() {
					@Override
					public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol,
							final int line, final int position, final String msg,
							final RecognitionException e) {
						System.out.println(line + ":" + position + ": " + msg);
					}
				};

				CommonTokenStream tokenStream = new CommonTokenStream(lex);
				ParserInterpreter parser = g.createParserInterpreter(tokenStream);
				parser.getInterpreter().setPredictionMode(PredictionMode.LL);
				parser.removeErrorListeners();
				parser.addErrorListener(printError);
				String startRule = "assemble";
				Rule start = g.getRule(startRule);
				ParserRuleContext parserRuleContext = parser.parse(start.index);
				System.out.println("parserRuleContext=" + parserRuleContext);
				while (parserRuleContext.getParent() != null) {
					parserRuleContext = parserRuleContext.getParent();
				}
				System.out.println("parserRuleContext.toStringTree()=" + parserRuleContext.toStringTree());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }//GEN-LAST:event_refreshGraphvizButtonActionPerformed

	private void printAST(Tree child) {
		printAST(child, "");
	}

	private void printAST(Tree child, String prefix) {
		System.out.println(prefix + "-" + child);
		prefix += "  ";
		if (child.getChildCount() > 0) {
			for (int x = 0; x < child.getChildCount(); x++) {
				printAST(child.getChild(x), prefix);
			}
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
			lastDataObject = d;
		}
		ModuleLib.log("-------------");
	}
}