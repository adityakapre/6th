/*
Q:
Power Set: Write a method to return all subsets of a set.

We should first have some reasonable expectations of our time and space complexity.
How many subsets of a set are there? When we generate a subset, each element has the "choice" of either
being in there or not. That is, for the first element, there are two choices: it is either in the set or it is not. For
the second, there are two, etc. So, doing { 2 * 2 * . . . } n times gives us 2" subsets.
Assuming that we're going to be returning a list of subsets, then our best case time is actually the total
number of elements across all of those subsets. There are 2" subsets and each of the n elements will be
contained in half of the subsets (i.e (2 raisedTo n / 2) = 2 raisedTo (n- 1) subsets). Therefore, the total number of elements 
across all of those subsets is n * 2 raisedTo (n-1).
We will not be able to beat O(n * 2 raisedTo n) in space or time complexity.
The subsets of {a1 , a2 , ••• , an} are also called the powerset P({a1, a2 , ••• , an}), or just P(n).

A:
Solution #1: Recursion
This problem is a good candidate for the Base Case and Build approach. Imagine that we are trying to find
all subsets of a set like S = {a1, a2, ••• , an}. We can start with the Base Case.
Base Case: n = 0.
There is just one subset of the empty set: {}.
Case:n = 1.
There are two subsets of the set {a}: {}, {aJ.
Case:n = 2.
There are four subsets of the set {a1, a2}: {}, {a1}, {a2), {a1, a 2}.
Case:n = 3.
Now here's where things get interesting. We want to find a way of generating the solution for n=3 based
on the prior solutions.

What is the difference between the solution for n=3 and the solution for n=2? Let's look at this more
deeply:
P(2) = {}, {a1}, {a2}, {a1, a2}
P(3) = {}, {a1}, {a2}, {a3}, {a1, a2}, {a1, a3}, {a2, a3}, {a1, a2, a3}
The difference between these solutions is that P (2) is missing all the subsets containing a3
P(3) - P(2) = {a3}, {a1, a3}, {a2, a3}, {a1, a2, a3}
How can we use P(2) to create P(3)? We can simply clone the subsets in P(2) and add a3 to them:

P(2)  = {}, {a1}, {a2}, {a1, a2}
P(2) + a3 = {a3}, {a1, a3}, {a2 , a3}, {a1, a2, a 3}

When merged together, the lines above make P(3).

Case:n > 0
Generating P(n) for the general case is just a simple generalization of the above steps. We compute
P(n-1), clone the results, and then add a n to each of these cloned sets.

This solution will be O(n * 2 raisedTo n) in time and space, which is the best we can do. For a slight optimization, we
could also implement this algorithm iteratively.
*/
package Q8_04_Power_Set;

import java.util.*;

public class QuestionA {

	public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index) {
		ArrayList<ArrayList<Integer>> allsubsets;
		if (set.size() == index) { // Base case - add empty set
			allsubsets = new ArrayList<ArrayList<Integer>>();
			allsubsets.add(new ArrayList<Integer>()); 
		} else {
			allsubsets = getSubsets(set, index + 1);
			int item = set.get(index);
			ArrayList<ArrayList<Integer>> moresubsets = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> subset : allsubsets) {
				ArrayList<Integer> newsubset = new ArrayList<Integer>();
				newsubset.add(item);
				newsubset.addAll(subset); 
				moresubsets.add(newsubset);
			}
			allsubsets.addAll(moresubsets);
		}
		return allsubsets;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			list.add(i);
		}
		ArrayList<ArrayList<Integer>> subsets = getSubsets(list, 0);
		System.out.println(subsets.toString());	
	}
}

/*
Solution #2: Combinatorics
While there's nothing wrong with the above solution, there's another way to approach it.
Recall that when we're generating a set, we have two choices for each element: (1) the element is in the set
(the "yes" state) or (2) the element is not in the set (the "no" state). This means that each subset is a sequence
of yeses/nos-e.g., "yes, yes, no, no, yes, no"
This gives us 2 raisedTo n possible subsets. How can we iterate through all possible sequences of"yes" /"no" states for
all elements? If each "yes" can be treated as a 1 and each "no" can be treated as a 0, then each subset can be
represented as a binary string.
Generating all subsets, then, really just comes down to generating all binary numbers (that is, all integers).
We iterate through all numbers from 0 to 2 raisedTo n (exclusive) and translate the binary representation of the
numbers into a set. Easy!
*/
package Q8_04_Power_Set;

import java.util.*;

public class QuestionB {
	
	public static ArrayList<Integer> convertIntToSet(int x, ArrayList<Integer> set) {
		ArrayList<Integer> subset = new ArrayList<Integer>(); 
		int index = 0;
		for (int k = x; k > 0; k >>= 1) {
			if ((k & 1) == 1) {
				subset.add(set.get(index));
			}
			index++;
		}
		return subset;
	}
	
	public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set) {
		ArrayList<ArrayList<Integer>> allsubsets = new ArrayList<ArrayList<Integer>>();
		int max = 1 << set.size(); /* Compute 2^n */ 
		for (int k = 0; k < max; k++) {
			ArrayList<Integer> subset = convertIntToSet(k, set);
			allsubsets.add(subset);
		}
		return allsubsets;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			list.add(i);
		}
		
		ArrayList<ArrayList<Integer>> subsets2 = getSubsets(list);
		System.out.println(subsets2.toString());		
	}

}
