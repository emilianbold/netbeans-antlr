package com.github.mcheung63.syntax.antlr4;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public enum AstSerializer {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(AstSerializer.class);

	public String toDot(Ast ast, boolean rulesOnly) {
		List<AstNode> nodes = ast.getNodes();
		StringBuilder sb = new StringBuilder()
				.append("graph {\n")
				.append("\tnode [fontname=Helvetica,fontsize=11];\n")
				.append("\tedge [fontname=Helvetica,fontsize=10];\n");

		for (AstNode n : nodes) {
			sb.append("\tn");
			sb.append(n.getId());
			sb.append(" [label=\"");
			if (!rulesOnly) {
				sb.append("(");
				sb.append(n.getId());
				sb.append(")\\n");
				sb.append(n.getEscapedLabel());
				sb.append("\\n");
			}
			sb.append(n.getType());
			sb.append("\"];\n");
		}

		nodes.forEach(n -> n.getChildren().stream()
				.filter(AstNode::hasParent)
				.forEach(c -> sb
				.append("\tn")
				.append(c.getParent().getId())
				.append(" -- n")
				.append(c.getId())
				.append(";\n")));

		sb.append("}\n");

		return sb.toString();
	}

	public String toDot(Ast ast) {
		return toDot(ast, false);
	}

}
