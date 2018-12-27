/*
If this were a binary search tree, we could modify the find operation for the two nodes and see where the
paths diverge. Unfortunately, this is not a binary search tree, so we must try other approaches.
Let's assume we're looking for the common ancestor of nodes p and q. One question to ask here is if each
node in our tree has a link to its parents.

Solution #1: With Links to Parents
If each node has a link to its parent, we could trace p and q's paths up until they intersect. This is essentially
the same problem as question 2.7 which find the intersection of two linked lists. The "linked list" in this case
is the path from each node up to the root.

This approach will take 0( d) time, where d is the depth of the deeper node.
*/
package Q4_08_First_Common_Ancestor;

import CtCILibrary.TreeNode;

public class QuestionB {
	public static TreeNode commonAncestor(TreeNode p, TreeNode q) {
		int delta = depth(p) - depth(q); // get difference in depths
		TreeNode first = delta > 0 ? q : p; // get shallower node
		TreeNode second = delta > 0 ? p : q; // get deeper node
		second = goUpBy(second, Math.abs(delta)); // move shallower node to depth of deeper
		while (first != second && first != null && second != null) {
			first = first.parent;
			second = second.parent;
		}
		return first == null || second == null ? null : first;
	}
	
	public static TreeNode goUpBy(TreeNode node, int delta) {
		while (delta > 0 && node != null) {
			node = node.parent;
			delta--;
		}
		return node;
	}
	
	public static int depth(TreeNode node) {
		int depth = 0;
		while (node != null) {
			node = node.parent;
			depth++;
		}
		return depth;
	}	
	
	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);
		TreeNode n3 = root.find(3);
		TreeNode n7 = root.find(7);
		TreeNode ancestor = commonAncestor(n3, n7);
		System.out.println(ancestor.data);
	}

}
