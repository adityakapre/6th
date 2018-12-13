package g;

import java.util.Random;

/* One node of a binary tree. The data element stored is a single 
 * character.
 */

public class Question {

	public static void main(String[] args) {
		int[] counts = new int[10];
		//for (int i = 0; i < 1000000; i++) {
		for (int i = 0; i < 1; i++) {
			Tree tree = new Tree();
			int[] array = { 1, 0, 6, 2, 3, 9, 4, 5, 8, 7 };
			for (int x : array) {
				tree.insertInOrder(x);
			}
			int d = tree.getRandomNode().data;
			counts[d]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + ": " + counts[i]);
		}
	}
}

class Tree {
	TreeNode root = null;

	public void insertInOrder(int value) {
		if (root == null) {
			root = new TreeNode(value);
		} else {
			root.insertInOrder(value);
		}
	}

	public int size() {
		return root == null ? 0 : root.size();
	}

	public TreeNode getRandomNode() {
		if (root == null)
			return null;

		Random random = new Random();
		int i = random.nextInt(size());
		return root.getIthNode(i);
	}
	
	public TreeNode get(TreeNode n, int d) {
		if(null == n)
			return null;
		if(n.data == d)
			return n;
		TreeNode node = get(n.left, d);
		if(null == node) {
			return get(n.right, d);
		} else {
			return node;
		}
		
	}
}

class TreeNode {
	public int data;
	public TreeNode left;
	public TreeNode right;
	private int size = 0;

	public TreeNode(int d) {
		data = d;
		size = 1;
	}

	public void insertInOrder(int d) {
		if (d <= data) {
			if (left == null) {
				left = new TreeNode(d);
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (right == null) {
				right = new TreeNode(d);
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}

	public int size() {
		return size;
	}

	public TreeNode find(int d) {
		if (d == data) {
			return this;
		} else if (d <= data) {
			return left != null ? left.find(d) : null;
		} else if (d > data) {
			return right != null ? right.find(d) : null;
		}
		return null;
	}

	public TreeNode getRandomNode() {
		int leftSize = left == null ? 0 : left.size();
		Random random = new Random();
		int index = random.nextInt(size);
		if (index < leftSize) {
			return left.getRandomNode();
		} else if (index == leftSize) {
			return this;
		} else {
			return right.getRandomNode();
		}
	}

	public TreeNode getIthNode(int i) {
		int leftSize = left == null ? 0 : left.size();
		if (i < leftSize) {
			return left.getIthNode(i);
		} else if (i == leftSize) {
			return this;
		} else {
			return right.getIthNode(i - (leftSize + 1));
		}
	}
}
