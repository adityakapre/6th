public class TreeNode {
  
  public int data;
  public TreeNode left;
  public TreeNode right;
  
  public static TreeNode createMinimalBST(int[] array) {
    return createMinimalBST(array, 0, array.length-1);
  }
  
  public static TreeNode createMinimalBST(int[] array, int start, int end) {
    if(start > end) {
      return null;
    }
    TreeNode node = new TreeNode();
    int mid=(start+end)/2;
    node.data = array[mid];
    node.left = createMinimalBST(array, start, mid-1);
    node.right = createMinimalBST(array, mid+1, end);
    return node;
  }
  
  public void insertInOrder(int newData) {
    insertInOrder(this, newData);
  }
  
  public TreeNode insertInOrder(TreeNode root, int newData) {
    System.out.print((null!=root) ? root.data+" -> " :  "NULL");
    if(null==root) {
      TreeNode newNode = new TreeNode();
      newNode.data=newData;
      return newNode;
    } else {
      if(newData < root.data) {
      	root.left = insertInOrder(root.left, newData);
      } else {
        root.right = insertInOrder(root.right, newData);
      }
      return root;
    }
  }
}
