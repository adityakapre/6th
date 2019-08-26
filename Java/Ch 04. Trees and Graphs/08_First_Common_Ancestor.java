/*
Q:
First Common Ancestor: Design an algorithm and write code to find the first common ancestor
of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not
necessarily a binary search tree.

A:
If this were a binary search tree, we could modify the find operation for the two nodes and see where the
paths diverge. Unfortunately, this is not a binary search tree, so we must try other approaches.
Let's assume we're looking for the common ancestor of nodes p and q. One question to ask here is if each
node in our tree has a link to its parents.

Solution #1: With Links to Parents
If each node has a link to its parent, we could trace p and q's paths up until they intersect. This is essentially
the same problem as question 2.7 which find the intersection of two linked lists. The "linked list" in this case
is the path from each node up to the root.

This approach will take O(d) time, where d is the depth of the deeper node.
*/
package Q4_08_First_Common_Ancestor;

import CtCILibrary.TreeNode;

public class QuestionB {
	public static TreeNode commonAncestor(TreeNode p, TreeNode q) {
		int delta = depth(p) - depth(q); // get difference in depths
		TreeNode shallower = delta > 0 ? q : p; // get shallower node
		TreeNode deeper = delta > 0 ? p : q; // get deeper node
		deeper = goUpBy(deeper, Math.abs(delta)); // move deeper node up
		/* Find where paths intersect. */
		while (shallower != deeper && shallower != null && deeper != null) {
			shallower = shallower.parent;
			deeper = deeper.parent;
		}
		return shallower == null || deeper == null ? null : shallower;
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
		if (!exists(root, p) || !exists(root, q)) { // Error check - one node is not in tree
			return null;
		}
		return ancestorHelper(root, p, q);
	}
	
	//main logic
	public static TreeNode ancestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) {
			return root;
		}
		boolean pIsOnLeft = exists(root.left, p);
		boolean qIsOnLeft = exists(root.left, q);
		if (pIsOnLeft != qIsOnLeft) { // Nodes are on different side
			return root;
		}
		//If both on left, go to left
		if(pIsOnLeft) {
            		return ancestorHelper(root.left, p, q);
        	} else {
            		return ancestorHelper(root.right, p, q);
        	}
	}
	
	//checks if n is within tree rooted at root
	public static boolean exists(TreeNode root, TreeNode n) { 
		if (root == null) return false;
		if (root == n) return true;
		return exists(root.left, n) || exists(root.right, n); 
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
