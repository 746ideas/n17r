package impl;

import adt.Queue;
import adt.Stack;

public class test {
	public static TreeNode<Integer> mirror(TreeNode<Integer> node) {
		TreeNode<Integer> temp = node;
		if (node.getRight() != null) {
			temp.setLeft(mirror(node.getRight()));
		}
		if (node.getLeft() != null) {
			temp.setRight(mirror(node.getLeft()));
		}

		return temp;

	}

	public static void main(String[] args) {
		BSTSet<Integer> a = new BSTSet<>();
		a.add(23);
		a.add(33);
		a.add(21);
		a.add(22);
		a.add(10);
		a.add(44);
		a.add(32);
		System.out.println(a);
	}
}
