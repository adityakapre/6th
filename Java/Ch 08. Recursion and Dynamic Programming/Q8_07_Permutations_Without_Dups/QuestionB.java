/*
Approach 2: Building from permutations of all n-1 character substrings.
Base Case: single-character strings
The only permutation of a1 is the string a1. So:
P(a1) = a1
Case: two-character strings
P(a1a2) = a1a2 and a2a1 
P(a2a3) = a2a3 and a3a2
P(a1a3) = a1a3 and a3a1

Case: three-character strings
Here is where the cases get more interesting. How can we generate all permutations of three-character
strings, such as a1a2a3, given the permutations of two-character strings?
Well, in essence, we just need to "try" each character as the first character and then append the permutations.
P(a1a2a3) = {a1 + P(a2a3)} + {a2 + P(a1a3)} + {a3 + P(a1a2)}
{a1 + P(a2a3)} -> a1a2a3, a1a3a2
{a2 + P(a1a3)} -> a2a1a3, a2a3a1
{a3 + P(a1a2)} -> a3a1a2, a3a2a1

Now that we can generate all permutations of three-character strings, we can use this to generate permutations
of four-character strings.
P(a1a2a3a4) = {a1 + P(a2a3a4)} + {a2 + P(a1a3a4)} + {a3, + P(a1a2a4)} + {a4 + P(a1a2a3)}
This is now a fairly straightforward algorithm to implement.
*/
package Q8_07_Permutations_Without_Dups;

import java.util.*;

public class QuestionB {
	public static ArrayList<String> getPerms(String remainder) {
		int len = remainder.length();
		ArrayList<String> result = new ArrayList<String>();
		
		/* Base case. */
		if (len == 0) {
			result.add(""); // Be sure to return empty string!
			return result;
		}
		
		
		for (int i = 0; i < len; i++) {
			/* Remove char i and find permutations of remaining characters.*/
			String before = remainder.substring(0, i);
			String after = remainder.substring(i + 1, len);
			ArrayList<String> partials = getPerms(before + after);
			
			/* Prepend char i to each permutation.*/
			for (String s : partials) {
				result.add(remainder.charAt(i) + s);
			}			
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		ArrayList<String> list = getPerms("abc");
		System.out.println("There are " + list.size() + " permutations.");
		for (String s : list) {
			System.out.println(s);
		}
	}

}
