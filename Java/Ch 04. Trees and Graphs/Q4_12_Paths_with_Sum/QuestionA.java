/*
Q:
Paths with Sum: You are given a binary tree in which each node contains an integer value (which
might be positive or negative). Design an algorithm to count the number of paths that sum to a
given value. The path does not need to start or end at the root or a leaf, but it must go downwards
(traveling only from parent nodes to child nodes).

A:
One option is the brute force approach.
Solution #1: Brute Force
In the brute force approach, we just look at all possible paths. To do this, we traverse to each node. At each
node, we recursively try all paths downwards, tracking the sum as we go. As soon as we hit our target sum,
we increment the total.

What is the time complexity of this algorithm?
Consider that node at depth d will be "touched" (via countPathsWithSumFromNode) by d nodes above it.
In a balanced binary tree, d will be no more than approximately log N. Therefore, we know that with N
nodes in the tree, countPathsWithSumFromNode will be called O(Nlog N) times. The runtime is *** O(Nlog N) ***

We can also approach this from the other direction. At the root node, we traverse to all N - 1 nodes beneath
it (via countPathsWithSumFromNode). At the second level (where there are two nodes), we traverse to N - 3
nodes. At the third level (where there are four nodes, plus three above those), we traverse to N - 7 nodes.
Following this pattern, the total work is roughly:
(N - 1) + (N - 3) + (N - 7) + (N - 15) + (N - 31) + ••• + (N - N)
To simplify this, notice that the left side of each term is always N and the right side is one less than a power
of two. The number of terms is the depth of the tree, which is O(log N). For the right side, we can ignore
the fact that it's one less than a power of two. Therefore, we really have this:
O(N * [number of terms] - [sum of powers of two from 1 through N])
O(N log N - N)
O(N log N)
If the value of the sum of powers of two from 1 through N isn't obvious to you, think about what the powers
of two look like in binary:
0001
+ 0010
+ 0100
+ 1000
= 1111

[sum of powers of two from 1 through N] = 2^0 + 2^1 + 2^2 + 2^3 +...+2^N = 2^(N+1) 
[sum of powers of two from 1 through log N] = 2^0 + 2^1 + 2^2 + 2^3 +...+2^(logN) = 2^(N+1) ~ 2*2^(logN) ~ 2N 
(since 2^(logN) = N when log base is 2)

Therefore, the runtime is O(Nlog N) in a balanced tree.
In an unbalanced tree, the runtime could be much worse. Consider a tree that is just a straight line down. At
the root, we traverse to N - 1 nodes. At the next level (with just a single node), we traverse to N - 2 nodes.
At the third level, we traverse to N - 3 nodes, and so on. This leads us to the sum of numbers between 1
and N, which is O(N sq.).
*/
package Q4_12_Paths_with_Sum;

import CtCILibrary.TreeNode;

public class QuestionA {
	
	public static int countPathsWithSum(TreeNode root, int targetSum) {
		if (root == null) return 0;
		
		/* Count paths with sum starting from the root. */
		int pathsFromRoot = countPathsWithSumFromNode(root, targetSum, 0);
		
		/* Try the nodes on the left and right of root. */
		int pathsOnLeft = countPathsWithSum(root.left, targetSum);
		int pathsOnRight = countPathsWithSum(root.right, targetSum);
		
		return pathsFromRoot + pathsOnLeft + pathsOnRight;
	}
	
	/* Returns the number of paths with this sum starting from this node. */
	public static int countPathsWithSumFromNode(TreeNode node, int targetSum, int currentSum) {
		if (node == null) return 0;
	
		currentSum += node.data;
		
		int totalPaths = 0;
		if (currentSum == targetSum) { // Found a path from the root
			totalPaths++;
		}
		
		totalPaths += countPathsWithSumFromNode(node.left, targetSum, currentSum); // Go left
		totalPaths += countPathsWithSumFromNode(node.right, targetSum, currentSum); // Go right
		
		return totalPaths;
	}	

	public static void main(String [] args) {
		/*
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);		
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(-8);
		root.left.right = new TreeNode(8);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(6);	
		System.out.println(countPathsWithSum(root, 0));*/
		
		/*TreeNode root = new TreeNode(-7);
		root.left = new TreeNode(-7);
		root.left.right = new TreeNode(1);
		root.left.right.left = new TreeNode(2);
		root.right = new TreeNode(7);
		root.right.left = new TreeNode(3);
		root.right.right = new TreeNode(20);
		root.right.right.left = new TreeNode(0);
		root.right.right.left.left = new TreeNode(-3);
		root.right.right.left.left.right = new TreeNode(2);
		root.right.right.left.left.right.left = new TreeNode(1);
		System.out.println(countPathsWithSum(root, -14));*/
		
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(0);
		root.right = new TreeNode(0);
		root.right.left = new TreeNode(0);
		root.right.left.right = new TreeNode(0);
		root.right.right = new TreeNode(0);
		System.out.println(countPathsWithSum(root, 0));
		System.out.println(countPathsWithSum(root, 4));
	}
}

/*
Solution #2: Optimized
In analyzing the last solution, we may realize that we repeat some work. For a path such as 10 -> 5 ->
3 -> -2, we traverse this path (or parts of it) repeatedly. We do it when we start with node 10, then when
we go to node 5 (looking at 5, then 3, then -2), then when we go to node 3, and then finally when we go to
node -2. Ideally, we'd like to reuse this work.
                                       10
				      /  \
                                     5    -3
				    / \    \
				   3   1    11
				  / \   \
				 3   -2  2
				  
Let's isolate a given path and treat it as just an array. Consider a (hypothetical, extended) path like:
10 -> 5 -> 1 -> 2 -> -1 -> -1 -> 7 -> 1 -> 2
What we're really saying then is: How many contiguous subsequences in this array sum to a target sum such
as 8? In other words, for each y, we're trying to find the x values below. (Or, more accurately, the number
of x values below.)
t t t
s X y
If each value knows its running sum (the sum of values from s through itself), then we can find this pretty
easily. We just need to leverage this simple equation: runningSumx = runningSumY - targetSum.
We then look for the values of x where this is true.
             targetSum
s          x           y
         runningSumY
    ----------------------
   |                      |
  runningSumX     targetSum 
s              x           y
Since we're just looking for the number of paths, we can use a hash table. As we iterate through the array,
build a hash table that maps from a runningSum to the number of times we've seen that sum. Then, for
each y, look up runningSumY - targetSum in the hash table. The value in the hash table will tell you
the number of paths with sum targetSum that end at y.
For example:
index: 0     1    2    3     4     5    6    7    8
value: 10 -> 5 -> 1 -> 2 -> -1 -> -1 -> 7 -> 1 -> 2
sum:   10    15   16   18    17    16   23   24   26
The value of runningSum7 is 24. lf targetSum is 8, then we'd look up 16 in the hash table. This would have
a value of 2 (originating from index 2 and index 5). As we can see above, indexes 3 through 7 and indexes
6 through 7 have sums of 8.
Now that we've settled the algorithm for an array, let's review this on a tree. We take a similar approach.
We traverse through the tree using depth-first search. As we visit each node:
1. Track its runningSum. We'll take this in as a parameter and immediately increment it by node. value.
2. Look up runningSum - targetSum in the hash table. The value there indicates the total number. Set
totalPaths to this value.
3. If runningSum == targetSum, then there's one additional path that starts at the root. Increment
totalPaths.
4. Add runningSum to the hash table (incrementing the value if it's already there).
5. Recurse left and right, counting the number of paths with sum targetSum.
6. After we're done recursing left and right, decrement the value of runningSum in the hash table. This is
essentially backing out of our work; it reverses the changes to the hash table so that other nodes don't
use it (since we're now done with node).
*/
package Q4_12_Paths_with_Sum;
import java.util.HashMap;

import CtCILibrary.TreeNode;

public class QuestionB {
	
	public static int countPathsWithSum(TreeNode root, int targetSum) {
		return countPathsWithSum(root, targetSum, 0, new HashMap<Integer, Integer>());
	}
	
	public static int countPathsWithSum(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount) {
		if (node == null) return 0; // Base case
		
		runningSum += node.data;
		
		/* Count paths with sum ending at the current node. */
		int sum = runningSum - targetSum;
		int totalPaths = pathCount.getOrDefault(sum, 0);
		
		/* If runningSum equals targetSum, then one additional path starts at root. Add in this path.*/
		if (runningSum == targetSum) {
			totalPaths++;
		}

		/* Add runningSum to pathCounts. */
		incrementHashTable(pathCount, runningSum, 1);
		
		/* Count paths with sum on the left and right. */
		totalPaths += countPathsWithSum(node.left, targetSum, runningSum, pathCount);
		totalPaths += countPathsWithSum(node.right, targetSum, runningSum, pathCount);
		
		incrementHashTable(pathCount, runningSum, -1); // Remove runningSum
		return totalPaths;
	}
	
	public static void incrementHashTable(HashMap<Integer, Integer> hashTable, int key, int delta) {
		int newCount = hashTable.getOrDefault(key, 0) + delta;
		if (newCount == 0) { // Remove when zero to reduce space usage
			hashTable.remove(key);
		} else {
			hashTable.put(key, newCount);
		}
	}

	public static void main(String [] args) {
		/*
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);		
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(-8);
		root.left.right = new TreeNode(8);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(6);	
		root.right.left.left = new TreeNode(0);	
		System.out.println(countPathsWithSum(root, 0));
		*/
		
		/*TreeNode root = new TreeNode(-7);
		root.left = new TreeNode(-7);
		root.left.right = new TreeNode(1);
		root.left.right.left = new TreeNode(2);
		root.right = new TreeNode(7);
		root.right.left = new TreeNode(3);
		root.right.right = new TreeNode(20);
		root.right.right.left = new TreeNode(0);
		root.right.right.left.left = new TreeNode(-3);
		root.right.right.left.left.right = new TreeNode(2);
		root.right.right.left.left.right.left = new TreeNode(1);
		System.out.println(countPathsWithSum(root, 0));*/
		
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(0);
		root.right = new TreeNode(0);
		root.right.left = new TreeNode(0);
		root.right.left.right = new TreeNode(0);
		root.right.right = new TreeNode(0);
		System.out.println(countPathsWithSum(root, 0));
		System.out.println(countPathsWithSum(root, 4));
	}
}
