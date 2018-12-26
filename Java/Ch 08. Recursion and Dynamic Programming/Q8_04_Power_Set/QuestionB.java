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
