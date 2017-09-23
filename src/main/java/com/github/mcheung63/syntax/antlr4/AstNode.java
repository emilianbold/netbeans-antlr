package com.github.mcheung63.syntax.antlr4;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public class AstNode {

	private String label;
	private String type;
	private AstNode parent;
	private Ast tree;
	private int id;

	private int sidx = 0;
	private int eidx = 0;

	private List<AstNode> children = new Vector<>();
	private static int cnt = 0;
	private static final Set<Character> SPECIAL = Stream.of('+', '{', '}', '(', ')', '[', ']', '&', '^', '-', '?', '*', '\"', '$', '<', '>', '.', '|', '#').collect(toSet());

	private AstNode(Ast tree) {
		this.tree = tree;
		id = cnt++;
	}

	protected AstNode(Ast tree, AstNode parent, String type, String label, int sidx, int eidx) {
		this(tree);
		this.type = type;
		this.label = label;
		this.parent = parent;
		this.sidx = sidx;
		this.eidx = eidx;
	}

	protected AstNode(Ast tree, AstNode node) {
		this(tree);
		id = node.id;
		type = node.type;
		label = node.label;
		this.eidx = node.eidx;
		this.sidx = node.sidx;
		for (AstNode c : node.children) {
			AstNode cnod = new AstNode(tree, c);
			cnod.parent = this;
			this.tree.nodes.add(cnod);
			children.add(cnod);
		}
	}

	public AstNode getChild(int i) {
		if (i < 0 || i > children.size()) {
			throw new IllegalArgumentException("Index must be greater than or equal to zero and less than the children size");
		}

		return children.get(i);
	}

	public AstNode getLastChild() {
		if (!children.isEmpty()) {
			return children.get(children.size() - 1);
		}
		return null;
	}

	public AstNode getFirstChild() {
		if (!children.isEmpty()) {
			return children.get(0);
		}
		return null;
	}

	public void setParent(AstNode par) {
		parent = par;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public AstNode getParent() {
		return parent;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public List<AstNode> getChildren() {
		return children;
	}

	public void addChild(AstNode n) {
		children.add(n);
	}

	public void delChild(AstNode n) {
		children.remove(n);
	}

	public void replaceChild(AstNode oldNode, AstNode newNode) {
		if (children.contains(oldNode)) {
			children.set(children.indexOf(oldNode), newNode);
			newNode.parent = this;
		}
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getEscapedLabel() {
		return AstNode.escapeSpecialCharacters(label);
	}

	public int getSidx() {
		return sidx;
	}

	public int getEidx() {
		return eidx;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AstNode)) {
			return false;
		}

		AstNode n = (AstNode) o;
		return n.getId() == getId() && n.type.equals(type) && n.label.equals(label) && children.equals(n.children);
	}

	@Override
	public String toString() {
		return id + " " + type + " " + label;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public static String escapeSpecialCharacters(String s) {
		if (s == null) {
			return "";
		}

		StringBuilder out = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (SPECIAL.contains(c)) {
				out.append("\\").append(c);
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public int getChildrenSize() {
		return children.size();
	}
}
