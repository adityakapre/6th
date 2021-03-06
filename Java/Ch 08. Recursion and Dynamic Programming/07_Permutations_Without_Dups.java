/*
Q:
Permutations without Dups: Write a method to compute all permutations of a string of unique characters.

Solution::
Like in many recursive problems, the Base Case and Build approach will be useful. Assume we have a string S
represented by the characters a1, a2 ... an .

A:
Formula : 

The number of permutations of n objects taken r at a time is determined by the following formula:
P(n,r)=n!/(n−r)!

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

e.g [a1a2a3]
read below numerically
{} indicates extra info
stack
wind (str)  unwind (returns "word" [list]) to above level
---------   ---------------------------------------------
1.(a1a2a3)  8.[a1a2a3, a2a1a3, a2a3a1, a1a3a2, a3a1a2, a3a2a1] {first=a1}
2.(a2a3)    7.[a2a3, a3,a2] {first=a2}
3.(a3)	    6.[a3]	{first=a3}
4.()   ---> 5.[""]	{str.length()=0}	

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
		//interleave "first" with each "word"
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

package Q8_07_Permutations_Without_Dups;

import java.util.*;

public class QuestionC {

	public static void getPerms(String prefix, String remainder, ArrayList<String> result) {
		if (remainder.length() == 0) {
			result.add(prefix);
		}
		int len = remainder.length();
		for (int i = 0; i < len; i++) {
			String before = remainder.substring(0, i);
			String after = remainder.substring(i + 1, len);
			char c = remainder.charAt(i);
			getPerms(prefix + c, before + after, result);
		}
	}
	
	public static ArrayList<String> getPerms(String str) {
		ArrayList<String> result = new ArrayList<String>();
		getPerms("", str, result);
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
