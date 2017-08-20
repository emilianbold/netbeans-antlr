package com.github.mcheung63;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author peter
 */
public class AstNode {

	private String label;
	private String ntype;
	private AstNode parent;
	private Ast tree;
	private int id;

	private int sidx = 0;
	private int eidx = 0;

	private List<AstNode> children;
	private static int cnt = 0;

	private AstNode(Ast tree) {
		this.tree = tree;
		id = cnt++;
		children = new Vector<>();
	}

	protected AstNode(Ast tree, AstNode parent, String nt, String label, int sidx, int eidx) {
		this(tree);
		ntype = nt;
		this.label = label;
		this.parent = parent;
		this.sidx = sidx;
		this.eidx = eidx;
	}

	protected AstNode(Ast tree, AstNode nod) {
		this(tree);
		id = nod.id;
		ntype = nod.ntype;
		label = nod.label;
		this.eidx = nod.eidx;
		this.sidx = nod.sidx;
		for (AstNode c : nod.children) {
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

	public String getRule() {
		return ntype;
	}

	public String getEscapedLabel() {
		return EscapeUtils.escapeSpecialCharacters(label);
	}

	public int getSidx() {
		return sidx;
	}

	public int getEidx() {
		return eidx;
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
		return n.getId() == getId() && n.ntype.equals(ntype)
				&& n.label.equals(label) && children.equals(n.children);
	}

	@Override
	public String toString() {
		return "id=" + id + ", type=" + ntype + ", label=" + label;
		//return label;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}
}
