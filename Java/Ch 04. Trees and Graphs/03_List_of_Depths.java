/*
Q:
List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes
at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).

A:
Though we might think at first glance that this problem requires a level-by-level traversal (BFS), this isn't actually
necessary. We can traverse the graph any way that we'd like, provided we know which level we're on as we
do so.
We can implement a simple modification of the pre-order (add to op first) traversal algorithm, where we pass in 
level + 1 to the next recursive call. The code below provides an implementation using depth-first search.
*/
public class QuestionDFS {

	/*
	op: ArrayList of LinkedList
	ArrayList[0] = LinkedList of nodes at level 0
	ArrayList[1] = LinkedList of nodes at level 1
	ArrayList[2] = LinkedList of nodes at level 2
	*/
	public static void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
		if (root == null) return; // base condition
		LinkedList<TreeNode> currLvl = null;
		// current level not contained in list e.g when size=0 & level=0, lists doesn't have this level entries
		if (lists.size() == level) { 
			currLvl = new LinkedList<TreeNode>();
			/* Levels are always traversed in order. So, if this is the first time we've visited level i,
			 * we must have seen levels 0 through i - 1. We can therefore safely add the level at the end. */
			lists.add(currLvl);  
		} else {
			currLvl = lists.get(level);
		}
		currLvl.add(root);
		createLevelLinkedList(root.left, lists, level + 1);
		createLevelLinkedList(root.right, lists, level + 1);
	}
	
	public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
		ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
		createLevelLinkedList(root, lists, 0);
		return lists;
	}	
	
	public static void main(String[] args) {
		int[] nodes_flattened = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = AssortedMethods.createTreeFromArray(nodes_flattened);
		ArrayList<LinkedList<TreeNode>> list = createLevelLinkedList(root);
		printResult(list);
	}
}


/*
A:
Alternatively, we can also implement a modification of breadth-first search. With this implementation, we
want to iterate through the root first, then level 2, then level 3, and so on.
With each level i, we will have already fully visited all nodes on level i-1. This means that to get which
nodes are on level i, we can simply look at all children of the nodes of level i-1.

first solution = dfs
second solution = bfs

One might ask which of these solutions is more efficient. Both run in O (N) time, but what about the space
efficiency? At first, we might want to claim that the second solution is more space efficient.
In a sense, that's correct. The first solution uses 0(log N) recursive calls (in a balanced tree), each of which
adds a new level to the stack. The second solution, which is iterative, does not require this extra space.
However, both solutions require returning O(N) data at output. The extra O(log N) space usage from the recursive
implementation is dwarfed (i.e made insignificant) by the O (N) data that must be returned. 
So while the first solution may actually use more data, they are equally efficient when it comes to "big O:'
*/
package Q4_03_List_of_Depths;

import CtCILibrary.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class QuestionBFS {

	public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
		ArrayList<LinkedList<TreeNode>> result = new ArrayList<LinkedList<TreeNode>>();

		/* "Visit" the root */
		LinkedList<TreeNode> currLvl = new LinkedList<TreeNode>();
		if (root != null) {
			currLvl.add(root);
		}
		while (currLvl.size() > 0) {
			result.add(currLvl); // Add previous level
			LinkedList<TreeNode> nxtLvl = new LinkedList<TreeNode>();  //Create LL for new level
			for (TreeNode parent : currLvl) {
				/* Visit the children */
				if (parent.left != null) {
					nxtLvl.add(parent.left);
				}
				if (parent.right != null) {
					nxtLvl.add(parent.right);
				}
			}
            		currLvl = nxtLvl; //go to next level
		}
		return result;
	}

	public static void main(String[] args) {
		int[] nodes_flattened = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = AssortedMethods.createTreeFromArray(nodes_flattened);
		ArrayList<LinkedList<TreeNode>> list = createLevelLinkedList(root);
		printResult(list);
	}
}


