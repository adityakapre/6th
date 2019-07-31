package Introduction;

import CtCILibrary.TreeNode;

public class Traversals {
	public static void visit(TreeNode node) {
		if (node != null) {
			System.out.println(node.data);
		}
	}
	
	//write inorder traversal of tree to array
	public void inOrder(TreeNode node, E[] array, int index){
    		if(node == null){  //when node is null, empty leaf is reached (doesn't matter if its left or right, end method call)
       			return;
    		}
    		inOrder(node.left, array, index);   // first do every left child tree
    		array[index++]= node.data;          // then write the data in the array
    		inOrder(node.right, array, index);  // do the same with the right child
	}
	
	public static void inOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.left);
			visit(node);
			inOrderTraversal(node.right);
		}
	}
	
	public static void preOrderTraversal(TreeNode node) {
		if (node != null) {
			visit(node);
			inOrderTraversal(node.left);
			inOrderTraversal(node.right);
		}
	}
	
	public static void postOrderTraversal(TreeNode node) {
		if (node != null) {
			inOrderTraversal(node.left);
			inOrderTraversal(node.right);
			visit(node);
		}
	}	
	
	public static int getHeight(TreeNode root) {
		if (root == null) {
			return -1;
		}
		return 1 + Math.max(getHeight(root.left), getHeight(root.right));
	}
	
	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		// We needed this code for other files, so check out the code in the library
		TreeNode root = TreeNode.createMinimalBST(array);
		inOrderTraversal(root);
	}

}
