/*
It's useful to kick off this question with a good example.

						 50
						/  \
					       20   60
					      /  \    \
					     10   25   70
					    / \        / \
					   5   15     65  80
											
We should also think about the ordering of items in a binary search tree. Given a node, all nodes on its left
must be less than all nodes on its right. Once we reach a place without a node, we insert the new value
there.
What this means is that the very first element in our array must have been a 50 in order to create the above
tree. If it were anything else, then that value would have been the root instead.
What else can we say? Some people jump to the conclusion that everything on the left must have been
inserted before elements on the right, but that's not actually true. In fact, the reverse is true: the order of the
left or right items doesn't matter.
Once the 50 is inserted, all items less than 50 will be routed to the left and all items greater than 50 will be
routed to the right. The 60 or the 20 could be inserted first, and it wouldn't matter.
Let's think about this problem recursively. If we had all arrays that could have created the subtree rooted
at 20 (call this arraySet20), and all arrays that could have created the subtree rooted at 60 (call this
a rray5et60), how would that give us the full answer? We could just"weave" each array from a rray5et20
with each array from arraySet60-and then prepend each array with a 50.
*/
package Q4_09_BST_Sequences;

import java.util.ArrayList;
import java.util.LinkedList;

import CtCILibrary.TreeNode;

public class Question {
	
	public static void weaveLists(LinkedList<Integer> first, LinkedList<Integer> second, ArrayList<LinkedList<Integer>> results, LinkedList<Integer> prefix) {
		/* One list is empty. Add the remainder to [a cloned] prefix and
		 * store result. */
		if (first.size() == 0 || second.size() == 0) {
			LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
			result.addAll(first);
			result.addAll(second);
			results.add(result);
			return;
		}
		
		/* Recurse with head of first added to the prefix. Removing the
		 * head will damage first, so we’ll need to put it back where we
		 * found it afterwards. */
		int headFirst = first.removeFirst();
		prefix.addLast(headFirst);
		weaveLists(first, second, results, prefix);
		prefix.removeLast();
		first.addFirst(headFirst);
		
		/* Do the same thing with second, damaging and then restoring
		 * the list.*/
		int headSecond = second.removeFirst();
		prefix.addLast(headSecond);
		weaveLists(first, second, results, prefix);
		prefix.removeLast();	
		second.addFirst(headSecond);
	}
	
	public static ArrayList<LinkedList<Integer>> allSequences(TreeNode node) {
		ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
		
		if (node == null) {
			result.add(new LinkedList<Integer>());
			return result;
		} 
		
		LinkedList<Integer> prefix = new LinkedList<Integer>();
		prefix.add(node.data);
		
		/* Recurse on left and right subtrees. */
		ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);
		ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);
		
		/* Weave together each list from the left and right sides. */
		for (LinkedList<Integer> left : leftSeq) {
			for (LinkedList<Integer> right : rightSeq) {
				ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
				weaveLists(left, right, weaved, prefix);
				result.addAll(weaved);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		TreeNode node = new TreeNode(100);
		int[] array = {100, 50, 20, 75, 150, 120, 170};
		for (int a : array) {
			node.insertInOrder(a);
		}
		ArrayList<LinkedList<Integer>> allSeq = allSequences(node);
		for (LinkedList<Integer> list : allSeq) {
			System.out.println(list);
		}
		System.out.println(allSeq.size());
	}

}
