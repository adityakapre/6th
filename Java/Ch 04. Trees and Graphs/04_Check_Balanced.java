/*
Q:
Check Balanced: Implement a function to check if a binary tree is balanced. For the purposes of
this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any
node never differ by more than one.

A:
In this question, we've been fortunate enough to be told exactly what balanced means: that for each node,
the two subtrees differ in height by no more than one. We can implement a solution based on this definition.
We can simply recurse through the entire tree, and for each node, compute the heights of each subtree.

Although this works. it's not very efficient. On each node. we recurse through its entire subtree. This means
that getHeight is called repeatedly on the same nodes. The algorithm is O(Nlog N) since each node is
"touched" once per node above it.
*/
package Q4_04_Check_Balanced;

import CtCILibrary.AssortedMethods;
import CtCILibrary.TreeNode;

public class QuestionBrute {
	
	public static boolean isBalanced(TreeNode root) {	//recurssive method 1
		if (root == null) {
			return true;
		}
		int heightDiff = getHeight(root.left) - getHeight(root.right);
		if (Math.abs(heightDiff) > 1) {
			return false;
		}
		else {
			return isBalanced(root.left) && isBalanced(root.right);
		}
	}
	
	public static int getHeight(TreeNode root) {		//recurssive method 2
		if (root == null) {
			return -1;
		}
		return Math.max(getHeight(root.left), getHeight(root.right)) + 1; //+1 is very important
	}
		
	public static void main(String[] args) {
		// Create balanced tree
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);
		System.out.println("Root? " + root.data);
		System.out.println("Is balanced? " + isBalanced(root));
		
		// Could be balanced, actually, but it's very unlikely...
		TreeNode unbalanced = new TreeNode(10);
		for (int i = 0; i < 10; i++) {
			unbalanced.insertInOrder(AssortedMethods.randomIntInRange(0, 100));
		}
		System.out.println("Root? " + unbalanced.data);
		System.out.println("Is balanced? " + isBalanced(unbalanced));
	}
}

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
not a great idea for an error code. Instead, we'll use Integer.MIN_VALUE.
This code runs in O(N) time and O(H) space, where H is the height of the tree.
*/

package Q4_04_Check_Balanced;
import CtCILibrary.TreeNode;

public class QuestionImproved {
	//start here...
	public static boolean isBalanced(TreeNode root) {
		return checkHeight(root) != Integer.MIN_VALUE;
	}
	
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
          		System.out.println("UnBalanced !!!->"+root.data);
			return Integer.MIN_VALUE; // Found error -> pass it back
		} else {
          		System.out.println("Balanced ->"+root.data);
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}
		
	public static void main(String[] args) {
		// Create balanced tree
		int[] array = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10};
		TreeNode root = TreeNode.createMinimalBST(array);
		BTreePrinter.printNode(root);
		System.out.println("Is balanced? " + isBalanced(root));
		root.insertInOrder(4); // Add 4 to make it unbalanced
        	System.out.println();
      		BTreePrinter.printNode(root);
		System.out.println("Is balanced? " + isBalanced(root));
	}
}

/*
       5               
      / \       
     /   \      
    /     \     
   /       \    
   1       8       
  / \     / \   
 /   \   /   \  
 0   2   6   9   
      \   \   \ 
      3   7   10 
                                
Balanced ->0
Balanced ->3
Balanced ->2
Balanced ->1
Balanced ->7
Balanced ->6
Balanced ->10
Balanced ->9
Balanced ->8
Balanced ->5
Is balanced? true
5 -> 1 -> 2 -> 3 -> NULL
               5                               
              / \               
             /   \              
            /     \             
           /       \            
          /         \           
         /           \          
        /             \         
       /               \        
       1               8               
      / \             / \       
     /   \           /   \      
    /     \         /     \     
   /       \       /       \    
   0       2       6       9       
            \       \       \   
             \       \       \  
             3       7       10   
              \                 
              4                 
                                                                
Balanced ->0
Balanced ->4
Balanced ->3
UnBalanced !!!->2
Is balanced? false
*/
