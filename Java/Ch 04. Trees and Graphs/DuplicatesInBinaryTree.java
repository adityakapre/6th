/*
Q. Count duplicates in a binary tree  

Approach is to do inoorder traversal and check if each node matches its previous node
use inorder traversal (LCR) since it goes sequentially in increasing order
*/

int duplicates=0;    // global variable to count duplicates
public Node countDuplicatesLCR(Node root, Node prev) {
 	 if(root==null) 
        return duplicates;
 	 if(root.left!=null) 
        duplicates += countDuplicatesLCR(root, root.left);  //L                              
   if(root!=prev && root.val==prev.val) 
        duplicates += 1;                                    // C (actual processing)
   if(root.right!=null) 
        duplicates += countDuplicatesLCR(root.right, root); //R                                             
 	 return duplicates;
} 

//Recursion based Generic
public int countDuplicatesGeneric(Node root, Node prev) {
    if(root==null) 
        return 0;     //Basic edge case .. root==null
    int count=0;
    if (root.data==prev.data) 
        count++;
    return count + countDuplicatesGeneric(root.left,root) + countDuplicatesGeneric(root.right,root);       
}