package com.github.mcheung63;

import com.github.mcheung63.syntax.antlr4.AstNode;

/**
 *
 * @author Peter <peter@quantr.hk>
 */
public class AntlrLib {

	static int id = 0;

	public static void removeOneLeafNodes(AstNode node) {
//		for (int x = 0; x < node.getChildren().size(); x++) {
//			AstNode nn = node.getChild(x);
//			if (nn.getChildren().size() == 1) {
//				System.out.println("bingo " + processLabel(nn.getRule(), nn.getLabel()));
//				AstNode nextNextNode = nn.getChild(0);
//				while (nextNextNode.getChildren().size() == 1) {
//					nextNextNode = nextNextNode.getChild(0);
//					System.out.println("\t\t\tmiddle " + processLabel(nextNextNode.getRule(), nextNextNode.getLabel()));
//				}
//				System.out.println("\t\t\t\tfinal " + processLabel(nextNextNode.getRule(), nextNextNode.getLabel()));
//				node.getChildren().remove(nn);
//				node.addChild(nextNextNode);
//			}
//		}

		//System.out.println("current=" + processLabel(node));
		for (int x = 0; x < node.getChildren().size(); x++) {
			AstNode nextNode = node.getChild(x);

			//System.out.println("\tnextNode=" + processLabel(nextNode));

			String nextNodeLabel = processLabel(nextNode);
//			if (nextNodeLabel.equals("STRING")) {
//				System.out.println("break");
//			}

			if (nextNode.getChildrenSize() == 1) {
				AstNode nextNextNode = nextNode.getChild(0);
				String nextNextNodeLabel = processLabel(nextNextNode);
				if (!nextNodeLabel.equals(nextNextNodeLabel)) {
					break;
				}

				while (nextNextNode.getChildrenSize() == 1) {
					String temp = processLabel(nextNextNode.getChild(0));
					if (!nextNextNodeLabel.equals(temp)) {
						break;
					}

					nextNextNode = nextNextNode.getChild(0);
				}
				//System.out.println("\t\tremove " + processLabel(node) + " -> " + processLabel(nextNextNode));

				node.getChildren().set(x, nextNextNode);
			}
		}

		for (int x = 0; x < node.getChildren().size(); x++) {
			AstNode nextNode = node.getChild(x);
			removeOneLeafNodes(nextNode);
		}
	}

	public static void filterUnwantedSubNodes(AstNode node, String[] unwantedString) {
		String realLabel = node.getLabel().replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		if (realLabel.contains(":")) {
			realLabel = realLabel.split(":")[0];
		}
		for (String s : unwantedString) {
			if (node.getRule().toLowerCase().contains("ruleblock")) {
				node.getChildren().clear();
				return;
			}
		}
		for (AstNode nn : node.getChildren()) {
			filterUnwantedSubNodes(nn, unwantedString);
		}
	}

	public static void printAst(String prefix, AstNode node) {
		String realLabel = node.getLabel().replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		if (realLabel.contains(":")) {
			realLabel = realLabel.split(":")[0];
		}
		System.out.println(prefix + node.getRule() + "[" + realLabel + "]");
		for (AstNode nn : node.getChildren()) {
			printAst(prefix + "    ", nn);
		}
	}

	public static String exportDot(AstNode node) {
		StringBuffer sb = new StringBuffer();
		sb.append("digraph \"Grammar\" {\n"
				+ "	graph [	"
				+ "		fontname = \"Arial\",\n"
				+ "		splines  = ortho,\n"
				+ "		fontsize = 8,"
				+ "		rankdir=\"LR\",\n"
				+ "	];\n"
				+ "	node [	"
				+ "		shape    = \"box\",\n"
				+ "     style    = \"filled\",\n"
				+ "		fontname = \"Arial\"\n"
				+ "];\n");

		for (AstNode nn : node.getChildren()) {
			if (nn.getRule().toLowerCase().contains("ruleblock")) {
				continue;
			}
			exportDotChild(node.getRule(), nn, sb);
		}
		sb.append("}\n");
		return sb.toString();
	}

	public static void exportDotChild(String currentNodeText, AstNode node, StringBuffer sb) {
		String nodeText = processLabel(node);
		String nodeID = node.getRule() + "-" + (id++);
		sb.append("\"" + nodeID + "\" [label=\"" + nodeText + "\"]\n");
		sb.append("\"" + currentNodeText + "\" -> \"" + nodeID + "\"\n");
		if (node.getRule().toLowerCase().contains("ruleblock")) {
			return;
		}
		for (AstNode nn : node.getChildren()) {
			exportDotChild(nodeID, nn, sb);
		}
	}

//	private void rank(TreeNode<Tree> rootNode, StringBuffer sb, String lastDotNode, HashMap<Integer, List<String>> nodesInLevel, int level) {
//		String nodeText = rootNode.object.getText();
//		List<String> list = nodesInLevel.get(level);
//		if (list == null) {
//			list = new ArrayList<String>();
//			nodesInLevel.put(level, list);
//		}
//		list.add(nodeText);
//		for (int x = 0; x < rootNode.children.size(); x++) {
//			rank(rootNode.children.get(x), sb, lastDotNode, nodesInLevel, level + 1);
//		}
//	}
	public static String processLabel(AstNode node) {
		String rule = node.getRule();
		String label = node.getLabel();
		if (label.contains(":")) {
			label = label.split(":")[0];
		}
		//String realLabel = rule.equals("rules") ? rule : rule + "[" + label + "]";//.replaceAll("\\\\t", "\\\\\\t").replaceAll("\\\\n", "\\\\\\n");
		String realLabel = rule.equals("rules") ? rule : label;

		realLabel = realLabel.replace("\"", "\\\"");
		if (realLabel.equals("")) {
			realLabel = "none";
		}
		return realLabel;
	}

	public static void buildTree(AstNode node, AntlrTreeNode rootNode) {
		for (AstNode nn : node.getChildren()) {
			String realLabel = processLabel(nn);

			AntlrTreeNode newNode = new AntlrTreeNode(realLabel, realLabel);
			rootNode.add(newNode);
			newNode.setParent(rootNode);
			buildTree(nn, newNode);
		}
	}
}
