/*
Q:
Check Subtree: Tl and T2 are two very large binary trees, with T1 much bigger than T2. Create an
algorithm to determine if T2 is a subtree of T1.
A tree T2 is a subtree of T1 if there exists a node n in T1 such that the subtree of n is identical to T2.
That is, if you cut off the tree at node n, the two trees would be identical.

A:
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

e.g for above
    	       4
	    /    \
  	   2      6
	 /  \    / \
	1    3  5   7
	
	   5
	 /  \
	4    6

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
		StringBuilder sb1 = new StringBuilder(); //notice recursive method users StringBuilder
		StringBuilder sb2 = new StringBuilder();
		getOrderString(t1, sb1);
		getOrderString(t2, sb2);
		return sb1.indexOf(sb2.toString()) != -1;
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

/*
The Alternative Approach
An alternative approach is to search through the larger tree, T1. Each time a node in T1 matches the root
of T2, call matchTree. The matchTree method will compare the two subtrees to see if they are identical.
Analyzing the runtime is somewhat complex. A naive answer would be to say that it is O(nm) time, where
n is the number of nodes in T1 and m is the number of nodes in T2. While this is technically correct, a little
more thought can produce a tighter bound.

We do not actually call matchTree on every node in T1. Rather, we call it k times, where k is the number
of occurrences of T2's root in T1. The runtime is closer to O(n + km).
In fact, even that overstates the runtime. Even if the root were identical, we exit matchTree when we find
a difference between T1 and T2. We therefore probably do not actually look at m nodes on each call of
match Tree.

When might the simple solution be better, and when might the alternative approach be better? This is a
great conversation to have with your interviewer. Here are a few thoughts on that matter:

1. The simple solution takes O(n + m) memory. The alternative solution takes O(log(n) + log(m))
memory. Remember: memory usage can be a very big deal when it comes to scalability.
2. The simple solution is O(n + m) time and the alternative solution has a worst case time of O (nm).
However, the worst case time can be deceiving; we need to look deeper than that.
3. A slightly tighter bound on the runtime, as explained earlier, is O(n + km), where k is the number of
occurrences of T2's root in T1. Let's suppose the node data for T1 and T2 were random numbers picked
between O and p. The value of k would be approximately n/p. Why? Because each of n nodes in T1 has
a 1/p chance of equaling the root, so approximately n/p nodes in Tl should equal T2. root. So, let's
say p = 1000, n = 1000000 and m = 100. We would do somewhere around l,100,000 node checks
(1100000 = 1000000 + 100*1000000/1000 ).
4. More complex mathematics and assumptions could get us an even tighter bound. We assumed in #3
above that if we call matchTree, we would end up traversing all m nodes of T2. It's far more likely,
though, that we will find a difference very early on in the tree and will then exit early.
In summary, the alternative approach is certainly more optimal in terms of space and is likely more optimal
in terms of time as well. It all depends on what assumptions you make and whether you prioritize reducing 
the average case runtime at the expense of the worst case runtime. This is an excellent point to make to
your interviewer
*/
package Q4_10_Check_Subtree;

import CtCILibrary.AssortedMethods;
import CtCILibrary.TreeNode;

public class QuestionB {

	public static boolean containsTree(TreeNode t1, TreeNode t2) {
		if (t2 == null) {
			return true; // The empty tree is a subtree of every tree.
		}
		return findRootInLargerTree(t1, t2);
	}
	
	/* Checks if the binary tree rooted at r1 contains the binary tree 
	 * rooted at r2 as a subtree somewhere within it.
	 * T1 is much larger than T2	 
	 */
	public static boolean findRootInLargerTree(TreeNode r1, TreeNode r2) {
		if (r1 == null) {
			return false; // big tree empty & subtree still not found.
		} else if (r1.data == r2.data && matchTree(r1,r2)) { //we found root of T2 in T1 so start matching
			return true;
		}
		//if T2's root not yet found, try to find it in r1.left or r1.right
		return findRootInLargerTree(r1.left, r2) || findRootInLargerTree(r1.right, r2); 
	}

	/* Checks if the binary tree rooted at r1 contains the 
	 * binary tree rooted at r2 as a subtree starting at r1.
	 */
	public static boolean matchTree(TreeNode r1, TreeNode r2) {
		if (r1 == null && r2 == null) {
			return true; // nothing left in the subtree
		} else if (r1 == null || r2 == null) { 
			return false; // exactly one tree is empty, therefore trees don't match
		} else if (r1.data != r2.data) {  
			return false;  // data doesn't match
		} else { //r1 r2 match, so now check remaining tree
			return matchTree(r1.left, r2.left) && matchTree(r1.right, r2.right);
		}
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
