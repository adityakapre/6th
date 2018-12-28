/*
Solution #3: Without Links to Parents
Alternatively, you could follow a chain in which p and q are on the same side. That is, if p and q are both on
the left of the node, branch left to look for the common ancestor. If they are both on the right, branch right
to look for the common ancestor. When p and q are no longer on the same side, you must have found the
first common ancestor.

This algorithm runs in O(n) time on a balanced tree. This is because covers is called on 2n nodes in the
first call (n nodes for the left side, and n nodes for the right side). After that the algorithm branches left or
right, at which point covers will be called on 2n/2 nodes, then 2n/4, and so on. This results in a runtime
of O(n).
We know at this point that we cannot do better than that in terms of the asymptotic runtime since we need
to potentially look at every node in the tree. However, we may be able to improve it by a constant multiple.
*/

package Q4_08_First_Common_Ancestor;

import CtCILibrary.TreeNode;

public class QuestionD {
	
	public static TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (!covers(root, p) || !covers(root, q)) { // Error check - one node is not in tree
			return null;
		}
		return ancestorHelper(root, p, q);
	}
	
	public static TreeNode ancestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) {
			return root;
		}
		
		boolean pIsOnLeft = covers(root.left, p);
		boolean qIsOnLeft = covers(root.left, q);
		if (pIsOnLeft != qIsOnLeft) { // Nodes are on different side
			return root;
		}
		TreeNode childSide = pIsOnLeft ? root.left : root.right;
		return ancestorHelper(childSide, p, q);
	}	
	
	public static boolean covers(TreeNode root, TreeNode p) { 
		if (root == null) return false;
		if (root == p) return true;
		return covers(root.left, p) || covers(root.right, p); 
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
