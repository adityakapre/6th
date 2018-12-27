/*
In problems like this, it's useful to attempt to solve the problem assuming that there is just a small amount
of data. This will give us a basic idea of an approach that might work.

The Simple Approach
In this smaller, simpler problem, we could consider comparing string representations of traversals of each
tree. If T2 is a subtree of Tl, then T2's traversal should be a substring of Tl. Is the reverse true? If so, should
we use an in-order traversal or a pre-order traversal?
An in-order traversal will definitely not work. After all, consider a scenario in which we were using binary
search trees. A binary search tree's in-order traversal always prints out the values in sorted order. Therefore,
two binary search trees with the same values will always have the same in-order traversals, even if their
structure is different.

What about a pre-order traversal? This is a bit more promising. At least in this case we know certain things,
like the first element in the pre-order traversal is the root node. The left and right elements will follow.
Unfortunately, trees with different structures could still have the same pre-order traversal.
   
    3            3
  /               \
4                  4 

There's a simple fix though. We can store NULL nodes in the pre-order traversal string as a special character,
like an 'X'. (We'll assume that the binary trees contain only integers.) The left tree would have the traversal
{ 3, 4, X} and the right tree will have the traversal { 3, X, 4}.
Observe that, as long as we represent the NULL nodes, the pre-order traversal of a tree is unique. That is, if
two trees have the same pre-order traversal, then we know they are identical trees in values and structure.

To see this, consider reconstructing a tree from its pre-order traversal (with NULL nodes indicated). For
example: 1, 2, 4, X, X, X, 3, X, X.
The root is 1, and its left node, 2, follows it. 2.left must be 4. 4 must have two NULL nodes (since it is followed
by two Xs). 4 is complete, so we move back up to its parent, 2. 2.right is another X (NULL). 1 's left subtree
is now complete, so we move to 1 's right child. We place a 3 with two NULL children there. The tree is now
complete.
                                   1
				  / \
				 2   3
				/ \  / \
			       4   X X  X
			      / \
			     X   X

This whole process was deterministic, as it will be on any other tree. A pre-order traversal always starts at
the root and, from there, the path we take is entirely defined by the traversal. Therefore, two trees are identical
if they have the same pre-order traversal.
Now consider the subtree problem. If T2's pre-order traversal is a substring of Tl's pre-order traversal, then
T2's root element must be found in Tl. If we do a pre-order traversal from this element in Tl, we will follow
an identical path to T2's traversal. Therefore, T2 is a subtree of Tl.
Implementing this is quite straightforward. We just need to construct and compare the pre-order traversaIs.

This approach takes O(n + m) time and O(n + m) space, where n and m are the number of nodes in T1
and T2, respectively. Given millions of nodes, we might want to reduce the space complexity.

*/
package Q4_10_Check_Subtree;

import CtCILibrary.AssortedMethods;
import CtCILibrary.TreeNode;

public class QuestionA {
	
	public static boolean containsTree(TreeNode t1, TreeNode t2) {
		StringBuilder string1 = new StringBuilder();
		StringBuilder string2 = new StringBuilder();
		
		getOrderString(t1, string1);
		getOrderString(t2, string2);
		
		return string1.indexOf(string2.toString()) != -1;
	}
	
	public static void getOrderString(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("X");             // Add null indicator
			return;
		}
		sb.append(node.data);           // Add root 
		getOrderString(node.left, sb);  // Add left
		getOrderString(node.right, sb); // Add right
	}

	public static void main(String[] args) {
		// t2 is a subtree of t1
		int[] array1 = {1, 2, 1, 3, 1, 1, 5};
		int[] array2 = {2, 3, 1};
		
		TreeNode t1 = AssortedMethods.createTreeFromArray(array1);
		TreeNode t2 = AssortedMethods.createTreeFromArray(array2);

		if (containsTree(t1, t2)) {
			System.out.println("t2 is a subtree of t1");
		} else {
			System.out.println("t2 is not a subtree of t1");
		}

		// t4 is not a subtree of t3
		int[] array3 = {1, 2, 3};
		TreeNode t3 = AssortedMethods.createTreeFromArray(array1);
		TreeNode t4 = AssortedMethods.createTreeFromArray(array3);

		if (containsTree(t3, t4)) {
			System.out.println("t4 is a subtree of t3");
		} else {
			System.out.println("t4 is not a subtree of t3");
		}
	}

}
