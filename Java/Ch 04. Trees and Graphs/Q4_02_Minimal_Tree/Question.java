/*
To create a tree of minimal height, we need to match the number of nodes in the left subtree to the number
of nodes in the right subtree as much as possible. This means that we want the root to be the middle of the
array, since this would mean that half the elements would be less than the root and half would be greater
than it.
We proceed with constructing our tree in a similar fashion. The middle of each subsection of the array
becomes the root of the node. The left half of the array will become our left subtree, and the right half of
the array will become the right subtree.
One way to implement this is to use a simple root. insertNode(int v) method which inserts the
value v through a recursive process that starts with the root node. This will indeed construct a tree with
minimal height but it will not do so very efficiently. Each insertion will require traversing the tree, giving a
total cost ofO ( N log N) to the tree.
Alternatively, we can cut out the extra traversals by recursively using the createMinimalBST method.
This method is passed just a subsection of the array and returns the root of a minimal tree for that array.
The algorithm is as follows:
1. Insert into the tree the middle element of the array.
2. Insert (into the left subtree) the left subarray elements.
3. Insert (into the right subtree) the right subarray elements.
4. Recurse.
*/
package Q4_02_Minimal_Tree;

import CtCILibrary.TreeNode;

public class Question {	
	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		// We needed this code for other files, so check out the code in the library
		TreeNode root = TreeNode.createMinimalBST(array);
		System.out.println("Root? " + root.data);
		System.out.println("Created BST? " + root.isBST());
		System.out.println("Height: " + root.height());
	}

	private static TreeNode createMinimalBST(int arr[], int start, int end){
		if (end < start) {
			return null;
		}
		int mid = (start + end) / 2;
		TreeNode n = new TreeNode(arr[mid]);
		n.setLeftChild(createMinimalBST(arr, start, mid - 1));
		n.setRightChild(createMinimalBST(arr, mid + 1, end));
		return n;
	}
}
