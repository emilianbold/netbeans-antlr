package antlr;

import com.github.mcheung63.syntax.antlr4.CallGraphComponent;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.junit.Test;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class TestJGraph {

	@Test
	public void testJGraph() {
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
		Object parent = graph.getDefaultParent();
		mxCell newNode = (mxCell) graph.insertVertex(parent, null, "fuck", 40, 40, 150, 30);
		mxCell newNode2 = (mxCell) graph.insertVertex(parent, null, "fuck", 140, 140, 150, 30);
		graph.insertEdge(parent, null, "", newNode, newNode2, mxConstants.STYLE_STROKECOLOR + "=#ff0000;edgeStyle=elbowEdgeStyle;");

		graph.setCellsDisconnectable(false);
		CallGraphComponent graphComponent = new CallGraphComponent(graph);

		graph.getModel().beginUpdate();

		mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);
		morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {
			public void invoke(Object sender, mxEventObject evt) {
				graph.getModel().endUpdate();
			}
		});

		morph.startAnimation();

		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		frame.setLayout(new BorderLayout());
		frame.add(graphComponent, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
