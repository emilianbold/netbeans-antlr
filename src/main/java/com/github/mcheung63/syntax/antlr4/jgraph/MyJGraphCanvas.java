package com.github.mcheung63.syntax.antlr4.jgraph;

import com.github.mcheung63.syntax.antlr4.AstNode;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JLabel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxCellState;

public class MyJGraphCanvas extends mxInteractiveCanvas {

	protected CellRendererPane rendererPane = new CellRendererPane();

	protected JLabel label = new JLabel();

	protected mxGraphComponent graphComponent;

	static Color borderColor = new Color(176, 176, 176);
	static Color backgroundcolor = new Color(239, 239, 239);

	public MyJGraphCanvas(mxGraphComponent graphComponent) {
		this.graphComponent = graphComponent;

		label.setBorder(BorderFactory.createLineBorder(borderColor));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(backgroundcolor);
		label.setOpaque(true);
		label.setForeground(Color.black);
		label.setFont(new Font("arial", Font.PLAIN, 10));
	}

	public void drawVertex(mxCellState state, AstNode node) {
		this.label.setText(node.getType() + "\n" + node.getLabel());
		rendererPane.paintComponent(g, this.label, graphComponent, (int) (state.getX() + translate.getX()), (int) (state.getY() + translate.getY()), (int) state.getWidth(), (int) state.getHeight(), true);
	}

}
