package com.github.mcheung63.syntax.antlr4;


import com.github.mcheung63.ModuleLib;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxGraph;

public class CallGraphComponent extends mxGraphComponent {

	public CallGraphComponent(mxGraph graph) {
		super(graph);
	}

	@Override
	public mxInteractiveCanvas createCanvas() {
		ModuleLib.log("createCanvas");
		return new MyJGraphCanvas(this);
	}

	/*protected void paintBackground(Graphics g) {
		double scale = this.getGraph().getView().getScale();
		super.paintGrid(g);

		if (scale >= 0.5) {
			Graphics2D g2 = (Graphics2D) g;
			FontMetrics fm = g2.getFontMetrics();

			int minX = (int) (50 * scale);
			int minY = (int) (20 * scale);
			int tricker = 10;

			g2.setColor(Color.darkGray);

			// address line
			int actualWidth = this.getViewport().getComponent(0).getWidth();
			int actualHeight = this.getViewport().getComponent(0).getHeight();
			//			g2.drawLine(0, minY, actualWidth, minY);
			//			int wordWidth = fm.stringWidth("Address  ");
			//			g2.drawString("Address", minX, minY - 10);

			//			int width = (int) ((markerEnd - markerOffset) / addressPerPixel);

			//			for (int x = 0; x <= width; x += pixelPerMarker) {
			//				float positionX = x;
			//				float scaledPositionX = (float) (positionX * scale);
			//				scaledPositionX += minX;
			//				g2.drawLine((int) scaledPositionX, (int) (minY - (tricker * scale)), (int) scaledPositionX, (int) (minY + (tricker * scale)));
			//				g2.drawString("0x" + Long.toHexString((long) (x * addressPerPixel) + markerOffset), (int) scaledPositionX, minY);
			//			}
			// end address line

			// time line
			//			g2.drawLine(minX, 0, minX, actualHeight);
			//			wordWidth = fm.stringWidth("Time");
			//			g2.drawString("Time", minX - wordWidth, minY + 15);
			// end time line

		}
	}*/
	
	/*@Override
	public Component[] createComponents(mxCellState state) {
		if (getGraph().getModel().isVertex(state.getCell())) {
			return new Component[]{new JButton(state.getCell().toString())};
		}

		return null;
	}*/
}
