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
		} else {
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
