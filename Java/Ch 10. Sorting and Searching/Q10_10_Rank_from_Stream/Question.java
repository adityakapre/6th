/*
A relatively easy way to implement this would be to have an array that holds all the elements in sorted
order. When a new element comes in, we would need to shift the other elements to make room. Implementing
getRankOfNumber would be quite efficient, though. We would simply perform a binary search
for n, and return the index.
However, this is very inefficient for inserting elements (that is, the track(int x) function). We need a
data structure which is good at keeping relative ordering, as well as updating when we insert new elements.
A binary search tree can do just that.
Instead of inserting elements into an array, we insert elements into a binary search tree. The method
track(int x) will run in O(log n) time, where n is the size of the tree (provided, of course, that the
tree is balanced).
To find the rank of a number, we could do an in-order traversal, keeping a counter as we traverse. The goal
is that, by the time we find x, counter will equal the number of elements less than x.
As long as we're moving left during searching for x, the counter won't change. Why? Because all the values
we're skipping on the right side are greater than x. After all, the very smallest element (with rank of 1) is the
leftmost node.
When we move to the right though, we skip over a bunch of elements on the left. All of these elements are
less than x, so we'll need to increment counter by the number of elements in the left subtree.
Rather than counting the size of the left subtree (which would be inefficient), we can track this information
as we add new elements to the tree.
Let's walk through an example on the following tree. In the below example, the value in parentheses indicates
the number of nodes in the left subtree (or, in other words, the rank of the node relative to its subtree).

					
					20(4)
					/  \
			               /    \
				   15(3)     25(2)
			            /         /
				   /         /
			       10(1)     23(0)
				/  \        \
			       /    \        \
			    5(0)     13(0)    24(0)


Suppose we want to find the rank of 24 in the tree above. We would compare 24 with the root 20, and find
that 24 must reside on the right. The root has 4 nodes in its left subtree, and when we include the root itself,
this gives us five total nodes smaller than 24. We set counter to 5.
Then, we compare 24 with node 25 and find that 24 must be on the left. The value of counter does not
update, since we're not"passing over"any smaller nodes. The value of counter is still 5.
Next, we compare 24 with node 23, and find that 24 must be on the right. Counter gets incremented by
just 1 (to 6), since 23 has no left nodes.
Finally, we find 24 and we return counter: 6.
Recursively, the algorithm is the following:

The track method and the getRankOfNumber method will both operate in O(log N) on a balanced
tree and O(N) on an unbalanced tree.
Note how we've handled the case in which d is not found in the tree. We check for the -1 return value, and,
when we find it, return -1 up the tree. It is important that you handle cases like this.
*/
package Q10_10_Rank_from_Stream;

import CtCILibrary.AssortedMethods;

public class Question {
	private static RankNode root = null;
	
	public static void track(int number) {
		if (root == null) {
			root = new RankNode(number);
		} else {
			root.insert(number);
		}
	}
	
	public static int getRankOfNumber(int number) {
		return root.getRank(number);
	}
	
	public static void main(String[] args) {
		int size = 100;
		int[] list = AssortedMethods.randomArray(size, -100, 100);
		for (int i = 0; i < list.length; i++) {
			track(list[i]);
		}
		
		int[] tracker = new int[size];
		for (int i = 0; i < list.length; i++) {
			int v = list[i];
			int rank1 = root.getRank(list[i]);
			tracker[rank1] = v;		
		}
		
		for (int i = 0; i < tracker.length - 1; i++) {
			if (tracker[i] != 0 && tracker[i + 1] != 0) {
				if (tracker[i] > tracker[i + 1]) {
					System.out.println("ERROR at " + i);
				}
			}
		}

		System.out.println("Array: " + AssortedMethods.arrayToString(list));
		System.out.println("Ranks: " + AssortedMethods.arrayToString(tracker));
	}

}
