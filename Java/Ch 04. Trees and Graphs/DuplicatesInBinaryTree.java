/*
Q. Count duplicates in a binary tree  

Approach is to do inoorder traversal and check if each node matches its previous node
use inorder traversal (LCR) since it goes sequentially in increasing order
*/

int duplicates=0;    // global variable to count duplicates
public Node countDuplicatesLCR(Node prev, Node root) {
 	 if(root==null) 
     return duplicates;
 	 if(root.left!=null) 
      duplicates += countDuplicatesLCR(root.left, root);  //L                              
   if(root!=prev && root.val==prev.val) 
      duplicates += 1;                   // C (actual processing)
   if(root.right!=null) 
      duplicates += countDuplicatesLCR(root, root.right); //R                                             
 	 return duplicates;
} 

//Recursion based Generic
public int countDuplicatesGeneric(Node prev, Node root) {
    if(root==null) 
        return 0;     //Basic edge case .. root==null
    int count=0;
    if (root.data==prev.data) 
        count++;
    return count + countDuplicatesGeneric(root, root.left) + countDuplicatesGeneric(root, root.right);       
}
//=============================================================================================================

//Q. Check if a Binary Tree (not BST) has duplicate values
/*
A simple solution is to store inorder traversal of given binary tree in an array. Then check if array has duplicates or not. 
We can avoid the use of array and solve the problem in O(n) time. 
The idea is to use hashing. 
We traverse the given tree, for every node, we check if it already exists in hash table. 
If exists, we return true (found duplicate). If it does not exist, we insert into hash table.*/

//Function that used HashSet to find presence of duplicate nodes 
    public static boolean checkDupUtil(Node root, HashSet<Integer> s) {  
        // If tree is empty, there are no  
        // duplicates.   
        if (root == null)  
            return false;  
    
        // If current node's data is already present.  
        if (s.contains(root.data))  
            return true;  
    
        // Insert current node  
        s.add(root.data);  
        
        // Recursively check in left and right  
        // subtrees.  
        return checkDupUtil(root.left, s) || checkDupUtil(root.right, s);  
    }  
    
    // To check if tree has duplicates  
    public static boolean checkDup(Node root){  
        HashSet<Integer> s=new HashSet<>(); 
        return checkDupUtil(root, s);  
    }  
