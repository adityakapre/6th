/*
Q:
Validate BST: Implement a function to check if a binary tree is a binary search tree.

Definition BST :
A binary search tree (BST) is a binary tree where each node has a Comparable key (and an associated value) 
and satisfies the restriction that the key in any node is larger than the keys in all nodes in that node's left subtree 
and smaller than the keys in all nodes in that node's right subtree.

A:
Solution #2: The Min / Max Solution
In the second solution, we leverage the definition of the binary search tree.
What does it mean for a tree to be a binary search tree? We know that it must, of course, satisfy the condition
left.data <= current.data < right.data for each node, but this isn't quite sufficient. Consider
the following small tree:

Although each node is bigger than its left node and smaller than its right node, this is clearly not a binary
search tree since 25 is in the wrong place.
***********
More precisely, the condition is that all left nodes must be less than or equal to the current node, which
must be less than all the right nodes.
***********
Using this thought, we can approach the problem by passing down the min and max values. As we iterate
through the tree, we verify against progressively narrower ranges.

We start with a range of (min = NULL, max = NULL), which the root obviously meets. (NULL indicates
that there is no min or max.) We then branch left, checking that these nodes are within the range (min =NULL, max = 20). 
Then, we branch right, checking that the nodes are within the range (min = 20,max = NULL).
We proceed through the tree with this approach. When we branch left, the max gets updated (to value of root). When we
branch right, the min gets updated (to value of root). If anything fails these checks, we stop and return false.

The time complexity for this solution is O(N), where N is the number of nodes in the tree. We can prove that
this is the best we can do, since any algorithm must touch all N nodes.
Due to the use of recursion, the space complexity is O(log N) on a balanced tree. There are up to O(log N) 
recursive calls on the stack since we may recurse up to the depth of the tree.

Remember that in recursive algorithms, you should always make sure that your base cases, as well as your
null cases, are well handled.
*/

package Q4_05_Validate_BST;

import CtCILibrary.AssortedMethods;
import CtCILibrary.TreeNode;

public class QuestionB {
	
	public static boolean checkBST(TreeNode n) {
		return checkBST(n, null, null);
	}
	
	public static boolean checkBST(TreeNode n, Integer min, Integer max) {
		if (n == null) {  // base case - null node is a valid BST
			return true;
		}
		//check if n.data are out of bounds
		if ((min != null && n.data <= min) || (max != null && n.data > max)) {
			return false;
		}
		//check if n.left.data OR n.right.data are out of bounds
		if (!checkBST(n.left, min, n.data) ||
			!checkBST(n.right, n.data, max)) {
			return false;
		}
		return true;
	}
	
	public static boolean checkBSTAlternate(TreeNode n) {
		return checkBSTAlternate(n, new IntWrapper(0), new IntWrapper(0));
	}		

	public static boolean checkBSTAlternate(TreeNode n, IntWrapper min, IntWrapper max) {
		/* An alternate, less clean approach. This is not provided in the book, but is used to test the other method. */
		if (n.left == null) {
			min.data = n.data;
		} else {
			IntWrapper leftMin = new IntWrapper(0);
			IntWrapper leftMax = new IntWrapper(0);
			if (!checkBSTAlternate(n.left, leftMin, leftMax)) {
				return false;
			}
			if (leftMax.data > n.data) {
				return false;
			}
			min.data = leftMin.data;
		}
		if (n.right == null) {
			max.data = n.data;
		} else {
			IntWrapper rightMin = new IntWrapper(0);
			IntWrapper rightMax = new IntWrapper(0);
			if (!checkBSTAlternate(n.right, rightMin, rightMax)) {
				return false;
			}
			if (rightMin.data <= n.data) {
				return false;
			}
			max.data = rightMax.data;
		}
		return true;
	}

	/* Create a tree that may or may not be a BST */
	public static TreeNode createTestTree() {
		/* Create a random BST */
		TreeNode head = AssortedMethods.randomBST(10, -10, 10); 
		
		/* Insert an element into the BST and potentially ruin the BST property */
		TreeNode node = head;
		do {
			int n = AssortedMethods.randomIntInRange(-10, 10);
			int rand = AssortedMethods.randomIntInRange(0, 5);
			if (rand == 0) {
				node.data = n;
			} else if (rand == 1) {
				node = node.left;
			} else if (rand == 2) {
				node = node.right;
			} else if (rand == 3 || rand == 4) {
				break;
			}
		} while (node != null);	
		
		return head;
	}
	
	public static void main(String[] args) {
		/* Simple test -- create one */
		int[] array = {Integer.MIN_VALUE, 3, 5, 6, 10, 13, 15, Integer.MAX_VALUE};
		TreeNode node = TreeNode.createMinimalBST(array);
		//node.left.data = 6; // "ruin" the BST property by changing one of the elements
		node.print();
		boolean isBst = checkBST(node);
		System.out.println(isBst);
		
		/* More elaborate test -- creates 100 trees (some BST, some not) and compares the outputs of various methods. */
		/*for (int i = 0; i < 100; i++) {
			TreeNode head = createTestTree();
			
			// Compare results 
			boolean isBst1 = checkBST(head);
			boolean isBst2 = checkBSTAlternate(head);
			
			if (isBst1 != isBst2) {
				System.out.println("*********************** ERROR *******************");
				head.print();
				break;
			} else {
				System.out.println(isBst1 + " | " + isBst2);
				head.print();
			}
		}*/
	}
}

public class IntWrapper {
	public IntWrapper(int m) {
		data = m;
	}
	public int data;
}
