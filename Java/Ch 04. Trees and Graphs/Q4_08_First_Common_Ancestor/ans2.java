/*
Solution #2: With Links to Parents (Better Worst-Case Runtime)
Similar to the earlier approach, we could trace p's path upwards and check if any of the nodes cover q.
The first node that covers q (we already know that every node on this path will cover p) must be the first
common ancestor.

Observe that we don't need to re-check the entire subtree. As we move from a node x to its parent y, all the
nodes under x have already been checked for q. Therefore, we only need to check the new nodes "uncovered';
which will be the nodes under x's sibling.

To implement this, we can just traverse upwards from p, storing the parent and the sibling node in
a variable. (The sibling node is always a child of parent and refers to the newly uncovered subtree.)
At each iteration, sibling gets set to the old parent's sibling node and parent gets set to parent.
parent.

This algorithm takes O(t) time, where tis the size of the subtree for the first common ancestor. In the
worst case, this will be O( n), where n is the number of nodes in the tree. We can derive this runtime by
noticing that each node in that subtree is searched once.
*/
package Q4_08_First_Common_Ancestor;

import CtCILibrary.TreeNode;

public class QuestionC {
	
	public static TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (!covers(root, p) || !covers(root, q)) {
			return null;
		} else if (covers(p, q)) {
			return p;
		} else if (covers(q, p)) {
			return q;
		}
		
		TreeNode sibling = getSibling(p);
		TreeNode parent = p.parent;
		while (!covers(sibling, q)) {
			sibling = getSibling(parent);
			parent = parent.parent;
		}
		return parent;
	}
	
	public static boolean covers(TreeNode root, TreeNode p) { 
		if (root == null) return false;
		if (root == p) return true;
		return covers(root.left, p) || covers(root.right, p); 
	}
	
	public static TreeNode getSibling(TreeNode node) {
		if (node == null || node.parent == null) {
			return null;
		}
		
		TreeNode parent = node.parent;
		return parent.left == node ? parent.right : parent.left;
	}
	
	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);
		TreeNode n3 = root.find(1);
		TreeNode n7 = root.find(7);
		TreeNode ancestor = commonAncestor(root, n3, n7);
		System.out.println(ancestor.data);
	}

}
