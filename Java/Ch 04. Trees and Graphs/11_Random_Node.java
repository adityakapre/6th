/*
Q:
Random Node: You are implementing a binary search tree class from scratch, which, in addition
to insert, find, and delete, has a method getRandomNode() which returns a random node
from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm
for getRandomNode, and explain how you would implement the rest of the methods.

A:
We're going to explore many solutions until we get to an optimal one that works.
One thing we should realize here is that the question was phrased in a very interesting way. The interviewer
did not simply say, "Design an algorithm to return a random node from a binary tree:'We were told that this
is a class that we're building from scratch. There is a reason the question was phrased that way. We probably
need access to some part of the internals of the data structure.

Option #1 [Slow & Working]
One solution is to copy all the nodes to an array and return a random element in the array. This solution will
take O(N) time and O(N) space, where N is the number of nodes in the tree.
We can guess our interviewer is probably looking for something more optimal, since this is a little too
straightforward (and should make us wonder why the interviewer gave us a binary tree, since we don't
need that information).
We should keep in mind as we develop this solution that we probably need to know something about the
internals of the tree. Otherwise, the question probably wouldn't specify that we're developing the tree class
from scratch.

Option #2 [Slow & Working)
Returning to our original solution of copying the nodes to an array, we can explore a solution where we
maintain an array at all times that lists all the nodes in the tree. The problem is that we'll need to remove
nodes from this array as we delete them from the tree, and that will take O ( N) time.

Option #3 [Slow & Working]
We could label all the nodes with an index from 1 to N and label them in binary search tree order (that
is, according to its inorder traversal). Then, when we call getRandomNode, we generate a random index
between 1 and N. If we apply the label correctly, we can use a binary search tree search to find this index.
However, this leads to a similar issue as earlier solutions. When we insert a node or a delete a node, all of the
indices might need to be updated. This can take O(N) time.

Option #4 [Fast & Not Working]
What if we knew the depth of the tree? (Since we're building our own class, we can ensure that we know
this. It's an easy enough piece of data to track.)
We could pick a random depth, and then traverse left/right randomly until we go to that depth. This
wouldn't actually ensure that all nodes are equally likely to be chosen though.
First, the tree doesn't necessarily have an equal number of nodes at each level. This means that nodes on
levels with fewer nodes might be more likely to be chosen than nodes on a level with more nodes.
Second, the random path we take might end up terminating before we get to the desired level. Then what?
We could just return the last node we find, but that would mean unequal probabilities at each node.

Option #5 [Fast & Not Working]
We could try just a simple approach: traverse randomly down the tree. At each node:
, With 1/3 odds, we return the current node.
• With 1/3 odds, we traverse left.
With 1/3 odds, we traverse right.
This solution, like some of the others, does not distribute the probabilities evenly across the nodes. The root
has a 1/3 probability of being selected-the same as all the nodes in the left put together.

Option #6 [Fast & Working]
Rather than just continuing to brainstorm new solutions, let's see if we can fix some of the issues in the
previous solutions. To do so, we must diagnose-deeply-the root problem in a solution.
Let's look at Option #5. It fails because the probabilities aren't evenly distributed across the options. Can we
fix that while keeping the basic algorithm the same?
We can start with the root. With what probability should we return the root? Since we have N nodes, we
must return the root node with 1/N probability. (In fact, we must return each node with 1/N probability.
After all, we have N nodes and each must have equal probability. The total must be 1 (100%), therefore each
must have 1/N probability.)
We've resolved the issue with the root. Now what about the rest of the problem? With what probability
should we traverse left versus right? It's not 50/50. Even in a balanced tree, the number of nodes on each
side might not be equal. If we have more nodes on the left than the right, then we need to go left more
often.
One way to think about it is that the odds of picking something-anything-from the left must be the sum
of each individual probability. Since each node must have probability 1/N, the odds of picking something
from the left must have probability LEFT SIZE * (1/N). This should therefore be the odds of going left.
Likewise, the odds of going right should be RIGHT SIZE * (1/N).
This means that each node must know the size of the nodes on the left and the size of the nodes on the
right. Fortunately, our interviewer has told us that we're building this tree class from scratch. It's easy to
keep track of this size information on inserts and deletes. We can just store a size variable in each node.
Increment size on inserts and decrement it on deletes.

In a balanced tree, this algorithm will be O(log N), where N is the number of nodes.

Option #7 [Fast & Working]
Random number calls can be expensive. If we'd like, we can reduce the number of random number calls
substantially.
Imagine we called getRandomNode on the tree below, and then traversed left.

                                              20
                                             /  \
                                            10   30
                                           /  \    \
                                          5    15   35
                                         / \    \
                                        3   7    17


We traversed left because we picked a number between O and 5 (inclusive). When we traverse left, we again
pick a random number between O and 5. Why re-pick? The first number will work just fine.
But what if we went right instead? We have a number between 7 and 8 (inclusive) but we would need a
number between O and 1 (inclusive). That's easy to fix:just subtract out LEFT SIZE + 1.
Another way to think about what we're doing is that the initial random number call indicates which node
(i) to return, and then we're locating the ith node in an in-order traversal. Subtracting LEFT SIZE + 1
from i reflects that, when we go right, we skip over LEFT SIZE + 1 nodes in the in-order traversal.

Like the previous algorithm, this algorithm takes O(log N) time in a balanced tree. We can also describe
the runtime as O(D), where Dis the max depth of the tree. Note that O(D) is an accurate description of the
runtime whether the tree is balanced or not.
*/

package g;
//Remember to import Random class
import java.util.Random;

/* One node of a binary tree. The data element stored is a single 
 * character.
 */
class Tree {
	TreeNode root = null;

	//start here ...
	public TreeNode getRandomNode() {
		if (root == null)
			return null;

		Random random = new Random();
		int i = random.nextInt(size());
		return root.getIthNode(i);
	}
	
	public int size() {
		return root == null ? 0 : root.size();
	}
	
	public void insertInOrder(int value) {
		if (root == null) {
			root = new TreeNode(value);
		} else {
			root.insertInOrder(value);
		}
	}

	public TreeNode get(TreeNode n, int d) {
		if(null == n)
			return null;
		if(n.data == d)
			return n;
		TreeNode node = get(n.left, d);
		if(null == node) {
			return get(n.right, d);
		} else {
			return node;
		}
		
	}
}

public class Question {

	public static void main(String[] args) {
		int[] counts = new int[10];
		//for (int i = 0; i < 1000000; i++) {
		for (int i = 0; i < 1; i++) {
			Tree tree = new Tree();
			int[] array = { 1, 0, 6, 2, 3, 9, 4, 5, 8, 7 };
			for (int x : array) {
				tree.insertInOrder(x);
			}
			System.out.println(tree.get(tree.root, 3).size());
			int d = tree.getRandomNode().data;
			counts[d]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + ": " + counts[i]);
		}
	}
}

class TreeNode {
	public int data;
	public TreeNode left;
	public TreeNode right;
	private int size = 0; //total size of tree (left subtree + right subtree) under this node

	public TreeNode(int d) {
		data = d;
		size = 1;
	}

	//creates BST from array, each node contains size of tree under it
	public void insertInOrder(int d) {
		if (d <= this.data) {
			if (this.left == null) {
				this.left = new TreeNode(d);
			} else {
				this.left.insertInOrder(d); //call insertInOrder on left child
			}
		} else {
			if (this.right == null) {
				this.right = new TreeNode(d);
			} else {
				this.right.insertInOrder(d);
			}
		}
		size++;	 //increment number of nodes under this node every time insert is called
	}

	public int size() {
		return size;
	}

	public TreeNode find(int d) {
		if (d == this.data) {
			return this;
		} else if (d <= this.data) {
			return this.left != null ? this.left.find(d) : null;
		} else if (d > data) {
			return this.right != null ? this.right.find(d) : null;
		}
		return null;
	}

	//anwers option#6
	public TreeNode getRandomNode() {
		int leftSize = this.left == null ? 0 : this.left.size();
		Random random = new Random();
		int index = random.nextInt(size); // size here is total size of tree below this node
		if (index < leftSize) {
			return this.left.getRandomNode();
		} else if (index == leftSize) {
			return this;
		} else {
			return this.right.getRandomNode();
		}
	}

	//anwers option#7
	public TreeNode getIthNode(int i) {
		int leftSize = this.left == null ? 0 : this.left.size();
		if (i < leftSize) {
			return left.getIthNode(i);
		} else if (i == leftSize) {
			return this;
		} else {
			//go right as rand num > leftSize, subtract leftSize+1 from rand
			return this.right.getIthNode(i - (leftSize + 1)); 
		}
	}
}
