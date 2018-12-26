/*
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
How can we use P (2) to create P(3)? We can simply clone the subsets in P(2) and add a3 to them:

P(2)  = {}, {a1}, {a2}, {a1, a2}
P(2) + a3 = {a3}, {a1, a3}, {a2 , a3}, {a1, a2, a 3}

When merged together, the lines above make P (3).

Case:n > 0
Generating P (n) for the general case is just a simple generalization of the above steps. We compute
P (n-1), clone the results, and then add a n to each of these cloned sets.

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
				newsubset.addAll(subset); 
				newsubset.add(item);
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
