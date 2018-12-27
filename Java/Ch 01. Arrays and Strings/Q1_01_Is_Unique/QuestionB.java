/*
We can reduce our space usage by a factor of eight by using a bit vector. We will assume, in the below code,
that the string only uses the lowercase letters a through z. This will allow us to use just a single int

If we can't use additional data structures, we can do the following:
1. Compare every character of the string to every other character of the string. This will take 0(n sq.) time
and 0(1) space.
2. If we are allowed to modify the input string, we could sort the string in O(nlog(n)) time and then
linearly check the string for neighboring characters that are identical. Careful, though: many sorting
algorithms take up extra space.
These solutions are not as optimal in some respects, but might be better depending on the constraints of
the problem.

checker will be 32 bits (4 bytes)
*/
package Q1_01_Is_Unique;

public class QuestionB {

	/* Assumes only letters a through z. */
	public static boolean isUniqueChars(String str) {
		if (str.length() > 26) { // Only 26 characters
			return false;
		}
		int checker = 0;
		for (int i = 0; i < str.length(); i++) {
			int val = str.charAt(i) - 'a';		//acsii value
			if ((checker & (1 << val)) > 0) return false;
			checker |= (1 << val);
		}
		return true;
	}
	
	public static void main(String[] args) {
		String[] words = {"abcde", "hello", "apple", "kite", "padle"};
		for (String word : words) {
			System.out.println(word + ": " + isUniqueChars(word));
		}
	}

}
