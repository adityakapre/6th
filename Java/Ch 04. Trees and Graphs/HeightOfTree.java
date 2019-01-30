static int height(Node node) { 
        /* base case tree is empty */
        if (node == null) 
            return 0; 
  
        /* If tree is not empty then height = 1 + max of left 
           height and right heights */
        return (1 + Math.max(height(node.left), height(node.right))); 
} 
