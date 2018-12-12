/*
Recall that an in-order traversal traverses the left subtree, then the current node, then the right subtree. 
To approach this problem, we need to think very, very carefully about what happens.
Let's suppose we have a hypothetical node. We know that the order goes left subtree, then current side,
then right subtree. So, the next node we visit should be on the right side.
But which node on the right subtree? It should be the first node we'd visit if we were doing an in-order
traversal of that subtree. This means that it should be the leftmost node on the right subtree. Easy enough!
But what if the node doesn't have a right subtree? This is where it gets a bit trickier.
If a node n doesn't have a right subtree, then we are done traversing n's subtree. We need to pick up where
we left off with n's parent, which we'll call q.
If n was to the left of q, then the next node we should traverse should be q (again, since left - > current
-> right).
If n were to the right of q, then we have fully traversed q's subtree as well. We need to traverse upwards from
q until we find a node x that we have not fully traversed. How do we know that we have not fully traversed
a node x? We know we have hit this case when we move from a left node to its parent. The left node is fully
traversed, but its parent is not.
*/

package Q4_06_Successor;

import CtCILibrary.TreeNode;

public class Question {

	public static TreeNode inorderSucc(TreeNode n) { 
		if (n == null) return null;
		
		// Found right children -> return left most node of right subtree
		if (n.parent == null || n.right != null) { 
			return leftMostChild(n.right); 
		} else { 
			TreeNode q = n;
			TreeNode x = q.parent;
			// Go up until we're on left instead of right
			while (x != null && x.left != q) {
				q = x;
				x = x.parent;
			}
			return x;
		}  
	} 
		
	public static TreeNode leftMostChild(TreeNode n) {
		if (n == null) {
			return null;
		}
		while (n.left != null) {
			n = n.left; 
		}
		return n; 
	}
	
	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);
		for (int i = 0; i < array.length; i++) {
			TreeNode node = root.find(array[i]);
			TreeNode next = inorderSucc(node);
			if (next != null) {
				System.out.println(node.data + "->" + next.data);
			} else {
				System.out.println(node.data + "->" + null);
			}
		}
	}

}
