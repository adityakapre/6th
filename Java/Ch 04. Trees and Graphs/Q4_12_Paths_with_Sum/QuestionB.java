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
t
t
s
runningSum y
\
t
X
targets um
i '
t
y
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

