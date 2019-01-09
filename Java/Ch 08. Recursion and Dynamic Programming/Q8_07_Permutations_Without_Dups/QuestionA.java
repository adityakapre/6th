/*
Approach 1: Building from permutations of first n-1 characters.
Base Case: permutations of first character substring
The only permutation of a1 is the string a1. So:
P(a1) = a1

Case: permutations of a1 a2
P(a1a2) = a1a2 and a2a1

Case: permutations of a1a2a3
P(a1a2a3) = a3a1a2, a1a3a2, a1a2a3, a3a2a1, a2a3a1, a2a1a3

Case: permutations of a1a2a3a4
This is the first interesting case. How can we generate permutations of a1a2a3a4 from a1a2a3 ?
Each permutation of a1a2a3a4 represents an ordering of a1a2a3. For example, a2a4a1a3 represents the order
a2a1a3.
Therefore, if we took all the permutations of a1a2a3 and added a4 into all possible locations, we would get all
permutations of a1a2a3a4.
a1a2a3 -> a4a1a2a3 a1a4a2a3 a1a2a4a3 a1a2a3a4
a1a3a2 -> a4a1a3a2 a1a4a3a2 a1a3a4a2 a1a3a2a4
a3a1a2 -> a4a3a1a2 a3a4a1a2 a3a1a4a2 a3a1a2a4
a2a1a3 -> a4a2a1a3 a2a4a1a3 a2a1a4a3 a2a1a3a4
a2a3a1 -> a4a2a3a1 a2a4a3a1 a2a3a4a1 a2a3a1a4
a3a2a1 -> a4a3a2a1 a3a4a2a1 a3a2a4a1 a3a2a1a4

We can now implement this algorithm recursively.


stack wind		stack unwind (returns [list])

(a1a2a3)		[a1a2a3, a2a1a3, a2a3a1, a1a3a2, a3a1a2, a3a2a1] 	first=a1
(a2a3)			[a2a3, a3,a2] 						first=a2
(a3)			[a3]							first=a3
()			[""]							str.length()=0	

*/
package Q8_07_Permutations_Without_Dups;

import java.util.*;

public class QuestionA {

	public static ArrayList<String> getPerms(String str) {
		if (str == null) {
			return null;
		}
		ArrayList<String> permutations = new ArrayList<String>();
		if (str.length() == 0) { // base case
			permutations.add(""); //add empty string to list
			return permutations;
		}
	            
		char first = str.charAt(0); // get the first character
		String remainder = str.substring(1); // remove the first character
		ArrayList<String> words = getPerms(remainder);
		for (String word : words) {
			for (int j = 0; j <= word.length(); j++) {
				String newPermu = insertCharAt(word, first, j);
				permutations.add(newPermu);
			}
		}
		return permutations;
	}
	
	public static String insertCharAt(String word, char c, int i) {
		String start = word.substring(0, i);
		String end = word.substring(i);
		return start + c + end;
	}
	
	public static void main(String[] args) {
		ArrayList<String> list = getPerms("abcde");
		System.out.println("There are " + list.size() + " permutations.");
		for (String s : list) {
			System.out.println(s);
		}
	}

}
