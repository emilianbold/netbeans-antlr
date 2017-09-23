package com.github.mcheung63.syntax.antlr4;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public class Ast {

	private AstNode root = null;
	List<AstNode> nodes = null;

	private Ast() {
		nodes = new Vector<>();
	}

	public Ast(String type, String label) {
		this();
		root = newNode(null, type, label, 0, 0);
	}

//	public Ast(Ast tree) {
//		this();
//		root = newNode(tree.getRoot());
//	}

//	private Ast(AstNode nod) {
//		this();
//		root = newNode(nod);
//	}

	public AstNode getRoot() {
		return root;
	}

//	private AstNode newNode(AstNode parent) {
//		AstNode rn = new AstNode(this, parent);
//		nodes.add(rn);
//		return rn;
//	}

	public AstNode newNode(AstNode parent, String type, String label, int sidx, int eidx) {
		AstNode rn = new AstNode(this, parent, type, label, sidx, eidx);
		nodes.add(rn);
		return rn;
	}

	public Set<AstNode> getLeafs() {
		return nodes.stream().filter(n -> !n.hasChildren()).collect(toSet());
	}

	public List<AstNode> getNodes() {
		return nodes;
	}

	public String toDot() {
		return AstSerializer.INSTANCE.toDot(this);
	}

//	public boolean replaceSubtree(Ast oldTree, Ast newTree) {
//		if (hasSubtree(oldTree)) {
//			nodes.stream()
//					.filter(n -> oldTree.getRoot().equals(n))
//					.forEach(n -> n.getParent().replaceChild(oldTree.getRoot(), newTree.getRoot()));
//			nodes.addAll(newTree.nodes);
//			return nodes.removeAll(oldTree.nodes);
//		}
//		return false;
//	}

//	public boolean removeSubtree(Ast subtree) {
//		if (hasSubtree(subtree)) {
//			nodes.stream()
//					.filter(n -> subtree.getRoot().equals(n))
//					.forEach(n -> n.getParent().delChild(n));
//			return nodes.removeAll(subtree.nodes);
//		}
//		return false;
//	}

//	public Set<Ast> getDominatingSubtrees(Predicate<AstNode> p) {
//		Set<AstNode> selected = new HashSet<>();
//		searchDominatingNodes(root, selected, p);
//		return getSubtrees(selected::contains);
//	}

	private void searchDominatingNodes(AstNode n, Set<AstNode> selected, Predicate<AstNode> p) {
		if (p.test(n)) {
			selected.add(n);
		} else {
			n.getChildren().forEach(an -> searchDominatingNodes(an, selected, p));
		}
	}

//	public Set<Ast> getSubtrees(Predicate<AstNode> p) {
//		return nodes.stream().filter(p).map(Ast::new).collect(toSet());
//	}

//	public boolean hasSubtree(Ast subtree) {
//		Set<Ast> subtrees = getSubtrees(n -> subtree.getRoot().equals(n));
//		return subtrees.stream().filter(subtree::equals).count() > 0;
//	}
//
//	public Ast getSubtree(Ast subtree) {
//		Set<Ast> subtrees = getSubtrees(n -> n.equals(subtree.getRoot()));
//		return subtrees.stream().filter(subtree::equals).findFirst().orElse(null);
//	}

	@Override
	public int hashCode() {
		return root.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Ast)) {
			return false;
		}

		Ast ast = (Ast) o;
		return root.equals(ast.getRoot());
	}
}
