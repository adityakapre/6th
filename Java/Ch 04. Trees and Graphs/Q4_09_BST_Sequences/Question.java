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
arraySet60), how would that give us the full answer? We could just"weave" each array from arraySet20
with each array from arraySet60-and then prepend each array with a 50.

Here's what we mean by weaving. We are merging two arrays in all possible ways, while keeping the
elements within each array in the same relative order.
arrayl: {l, 2}
array2: {3, 4}
weaved: {l, 2, 3, 4}, {l, 3, 2, 4}, {1, 3, 4, 2}, {3, 1, 2, 4}, {3, 1, 4, 2}, {3, 4, 1, 2}

Note that, as long as there aren't any duplicates in the original array sets, we won't have to worry that
weaving will create duplicates.
The last piece to talk about here is how the weaving works . Let's think recursively about how to weave {1,2,3}
and {4,5,6}. What are the subproblems?
Prepend a l to all weaves of {2,3} and {4,5,6}.
Prepend a 4 to all weaves of {l,2,3} and {5,6}.
To implement this, we'll store each as linked lists. This will make it easy to add and remove elements. When
we recurse, we'll push the prefixed elements down the recursion . When first or second are empty, we
add the remainder to prefix and store the result.

It works something like this:
weave(first, second, prefix):
	weave({l, 2}, {3, 4}, {})
		weave({2}, {3, 4}, {1})
			weave({}, {3, 4}, {l, 2})
				{1, 2, 3, 4}
			weave({2}, {4}, {1, 3})
				weave({}, {4}, {l, 3, 2})
					{l, 3, 2, 4}
				weave({2}, {}, {l, 3, 4})
					{l, 3, 4, 2}
			weave({l, 2}, {4}, {3})
				weave({2}, {4}, {3, 1})
					weave({}, {4}, {3, 1, 2})
						{3, 1, 2, 4}
					weave({2}, {}, {3, 1, 4})
						{3, 1, 4, 2}
				weave({l, 2}, {}, {3, 4})
					{3, 4, 1, 2}
Now, let's think through the implementation of removing, say, 1 from { 1, 2} and recursing. We need to be
careful about modifying this list, since a later recursive call (e .g., weave( {1, 2}, { 4}, {3})) might need
the 1 still in {1, 2}.
We could clone the list when we recurse, so that we only modify the recursive calls. Or, we could modify the
list but then "revert"the changes after we're done with recursing.
We've chosen to implement it the latter way. Since we're keeping the same reference to first, second, and
prefix the entire way down the recursive call stack, then we'll need to clone prefix just before we store
the complete result.

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
