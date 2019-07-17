/*
Q:
Diameter of a Binary Tree. 
The diameter of a tree (sometimes called the width) is the number of nodes on the longest path between two end nodes.
*/

static int height(Node node) { 
        /* base case tree is empty */
        if (node == null) 
            return 0; 
  
        /* If tree is not empty then height = 1 + max of left 
           height and right heights */
        return (1 + Math.max(height(node.left), height(node.right))); 
} 

int diameter(Node root) 
    { 
        /* base case if tree is empty */
        if (root == null) 
            return 0; 
  
        /* get the height of left and right sub trees */
        int lheight = height(root.left); 
        int rheight = height(root.right); 
  
        /* get the diameter of left and right subtrees */
        int ldiameter = diameter(root.left); 
        int rdiameter = diameter(root.right); 
  
        /* Return max of following three 
         1) Diameter of left subtree 
         2) Diameter of right subtree 
         3) Height of left subtree + height of right subtree + 1  i.e diameter of this tree
         */
        return Math.max(lheight + rheight + 1, Math.max(ldiameter, rdiameter)); 
  
} 
