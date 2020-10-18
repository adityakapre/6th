/**
 * Date 04/11/2015
 * @author tusroy
 * 
 * Youtube link - https://youtu.be/Jg4E4KZstFE
 * 
 * Given a binary tree and a sum, find if there is a path from root to leaf
 * which sums to this sum.
 * 
 * Solution
 * Keep going left and right and keep subtracting node value from sum.
 * If leaf node is reached check if whatever sum is remaining same as leaf node data.
 * 
 * Time complexity is O(n) since all nodes are visited.
 * 
 * Test cases:
 * Negative number, 0 and positive number
 * Tree with 0, 1 or more nodes
 * 
 * Reference http://www.geeksforgeeks.org/root-to-leaf-path-sum-equal-to-a-given-number/
 */
public class RootToLeafToSum {

    public boolean printPath(Node root, int sum, List<Node> path){
        
        if(root == null){
            return false;
        }
        if(root.left == null && root.right == null){ //leaf detected, NOW check if sum is equal
            if(root.data == sum){
                path.add(root);
                return true;
            }else{
                return false;
            }
        }
        if(printPath(root.left, sum - root.data, path)){ // returns true iff recursion returns true
            path.add(root);
            return true;
        }
        if(printPath(root.right, sum - root.data, path)){
            path.add(root);
            return true;
        }
        return false;
    }
}

/*
In above solution look at code :

if(printPath(root.left, sum - root.data, path)){
   path.add(root);
   return true;
}
if(printPath(root.right, sum - root.data, path)){
   path.add(root);
   return true;
}

This can be replaced by following code since both mean the same thing:

if(printPath(root.left, sum-root.data, path) || printPath(root.right, sum - root.data, path)){
   path.add(root);
   return true;
}
*/
