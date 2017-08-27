package com.github.mcheung63;

import java.util.ArrayList;

/**
 *
 * @author Peter (peter@quantr.hk)
 */
public class TreeNode<T> {

	public T object;
	public ArrayList<TreeNode<T>> children = new ArrayList<>();

	public TreeNode(T object) {
		this.object = object;
	}
}
