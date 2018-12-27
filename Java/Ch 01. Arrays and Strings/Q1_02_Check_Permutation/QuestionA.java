/*
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
