/*
Q : 
Given two strings, write a method to decide if one is a permutation of the other

Like in many questions, we should confirm some details with our interviewer. We should understand if the
permutation comparison is case sensitive. That is: is God a permutation of dog? Additionally, we should
ask if whitespace is significant. We will assume for this problem that the comparison is case sensitive and
whitespace is significant. So, "god " is different from "dog".
Observe first that strings of different lengths cannot be permutations of each other. There are two easy
ways to solve this problem, both of which use this optimization.

Solution #1: Sort the strings.
If two strings are permutations, then we know they have the same characters, but in different orders. Therefore,
sorting the strings will put the characters from two permutations in the same order. We just need to
compare the sorted versions of the strings.

Though this algorithm is not as optimal in some senses, it may be preferable in one sense: It's clean, simple
and easy to understand. In a practical sense, this may very well be a superior way to implement the problem.
However, if efficiency is very important, we can implement it a different way.
*/
package Q1_02_Check_Permutation;

public class QuestionA {	
	public static String sort(String s) {
		char[] content = s.toCharArray();
		java.util.Arrays.sort(content);
		return new String(content);
	}
	
	public static boolean permutation(String s, String t) {
		return sort(s).equals(sort(t));
	}	
	
	public static void main(String[] args) {
		String[][] pairs = {{"apple", "papel"}, {"carrot", "tarroc"}, {"hello", "llloh"}};
		for (String[] pair : pairs) {
			String word1 = pair[0];
			String word2 = pair[1];
			boolean anagram = permutation(word1, word2);
			System.out.println(word1 + ", " + word2 + ": " + anagram);
		}
	}
}

/*
Solution #2: Check if the two strings have identical character counts.
We can also use the definition of a permutation-two words with the same character counts-to implement
this algorithm. We simply iterate through this code, counting how many times each character appears.
Then, afterwards, we compare the two arrays.
Note the assumption on line 6. In your interview, you should always check with your interviewer about the
size of the character set. We assumed that the character set was ASCII.
*/
package Q1_02_Check_Permutation;

public class QuestionB {	
	public static boolean permutation(String s, String t) {
		if (s.length() != t.length()) return false; // Permutations must be same length
		
		int[] letters = new int[128]; // Assumption: ASCII
		for (int i = 0; i < s.length(); i++) {
			letters[s.charAt(i)]++;
		}
		  
		for (int i = 0; i < t.length(); i++) {
			letters[t.charAt(i)]--;
		    if (letters[t.charAt(i)] < 0) {
		    	return false;
		    }
		}
		  
		return true; // letters array has no negative values, and therefore no positive values either
	}
	
	public static void main(String[] args) {
		String[][] pairs = {{"apple", "papel"}, {"carrot", "tarroc"}, {"hello", "llloh"}};
		for (String[] pair : pairs) {
			String word1 = pair[0];
			String word2 = pair[1];
			boolean anagram = permutation(word1, word2);
			System.out.println(word1 + ", " + word2 + ": " + anagram);
		}
	}
}
