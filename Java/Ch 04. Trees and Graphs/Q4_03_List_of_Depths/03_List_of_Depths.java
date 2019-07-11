/*
Q:
List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes
at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).

A:
Alternatively, we can also implement a modification of breadth-first search. With this implementation, we
want to iterate through the root first, then level 2, then level 3, and so on.
With each level i, we will have already fully visited all nodes on level i-1. This means that to get which
nodes are on level i, we can simply look at all children of the nodes of level i-1.

first solution = dfs
seconf solution = bfs

One might ask which of these solutions is more efficient. Both run in O (N) time, but what about the space
efficiency? At first, we might want to claim that the second solution is more space efficient.
In a sense, that's correct. The first solution uses 0(log N) recursive calls (in a balanced tree), each of which
adds a new level to the stack. The second solution, which is iterative, does not require this extra space.
However, both solutions require returning O(N) data. The extra 0(log N) space usage from the recursive
implementation is dwarfed by the O (N) data that must be returned. So while the first solution may actually
use more data, they are equally efficient when it comes to "big O:'

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
	
	public static void printResult(ArrayList<LinkedList<TreeNode>> result){
		int depth = 0;
		for(LinkedList<TreeNode> entry : result) {
			Iterator<TreeNode> i = entry.listIterator();
			System.out.print("Link list at depth " + depth + ":");
			while(i.hasNext()){
				System.out.print(" " + ((TreeNode)i.next()).data);
			}
			System.out.println();
			depth++;
		}
	}
	

	public static void main(String[] args) {
		int[] nodes_flattened = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		TreeNode root = AssortedMethods.createTreeFromArray(nodes_flattened);
		ArrayList<LinkedList<TreeNode>> list = createLevelLinkedList(root);
		printResult(list);
	}

}
