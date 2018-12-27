/*
We need to cut out some of the calls to getHeight.

If we inspect this method, we may notice that getHeight could actually check if the tree is balanced at
the same time as it's checking heights. What do we do when we discover that the subtree isn' t balanced?
Just return an error code.

This improved algorithm works by checking the height of each subtree as we recurse down from the root.
On each node, we recursively get the heights of the left and right subtrees through the checkHeight
method. If the subtree is balanced, then checkHeight will return the actual height of the subtree. If the
subtree is not balanced, then check Height will return an error code. We will immediately break and
return an error code from the current call.

What do we use for an error code? The height of a null tree is generally defined to be -1, so that's
not a great idea for an error code. Instead, we' ll use Integer. MIN_ VALUE.
*/

package Q4_04_Check_Balanced;
import CtCILibrary.TreeNode;

public class QuestionImproved {
		
	public static int checkHeight(TreeNode root) {
		if (root == null) {
			return -1;
		}
		int leftHeight = checkHeight(root.left);
		if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE; // Propagate error up
		
		int rightHeight = checkHeight(root.right);
		if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE; // Propagate error up
		
		int heightDiff = leftHeight - rightHeight;
		if (Math.abs(heightDiff) > 1) {
			return Integer.MIN_VALUE; // Found error -> pass it back
		} else {
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}
	
	public static boolean isBalanced(TreeNode root) {
		return checkHeight(root) != Integer.MIN_VALUE;
	}
	
	public static void main(String[] args) {
		// Create balanced tree
		int[] array = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);

		
		System.out.println("Is balanced? " + isBalanced(root));
		
		root.insertInOrder(4); // Add 4 to make it unbalanced

		System.out.println("Is balanced? " + isBalanced(root));
	}

}
