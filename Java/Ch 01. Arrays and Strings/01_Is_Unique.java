/*
Q :
Implement an algorithm to determine if a string has all unique characters. What if you can not use additional data structures?

A1: 
You should first ask your interviewer if the string is an ASCII string or a Unicode string. Asking this question
will show an eye for detail and a solid foundation in computer science. We'll assume for simplicity the character
set is ASCII. If this assumption is not valid, we would need to increase the storage size.
One solution is to create an array of boolean values, where the flag at index i indicates whether character
i in the alphabet is contained in the string. The second time you see this character you can immediately
return false.
We can also immediately return false if the string length exceeds the number of unique characters in the
alphabet. After all, you can't form a string of 280 unique characters out of a 128-character alphabet.
It's also okay to assume 256 characters. This would be the case in extended ASCII. You should
clarify your assumptions with your interviewer.

The basic ASCII set uses 7 bits for each character, giving it a total of 128 unique symbols. 
The extended ASCII character set uses 8 bits, which gives it an additional 128 characters. 
The extra characters represent characters from foreign languages and special symbols for drawing pictures.

The time complexity for this code is O(n), where n is the length of the string. The space complexity is O(l).
(You could also argue the time complexity is O(1), since the for loop will never iterate through more than
128 characters.) If you didn't want to assume the character set is fixed, you could express the complexity as
O(c) space and O(min(c, n)) or O(c) time, where c is the size of the character set.
*/
package Q1_01_Is_Unique;

public class QuestionA {
	public static boolean isUniqueChars(String str) {
		if (str.length() > 128) {
			return false;
		}
		boolean[] char_set = new boolean[128];
		for (int i = 0; i < str.length(); i++) {
			int val = str.charAt(i);	//remember charAt(i) to get each string character
			if (char_set[val]) return false;
			char_set[val] = true;
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

/*
A2:

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
checker will be 32 bits (4 bytes) since int in Java is 4 bytes
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
			int val = str.charAt(i) - 'a';		       // acsii value
			if ((checker & (1 << val)) > 0) return false;  // left shift 1 val times, check if it already exists
			checker |= (1 << val);			       // put 1 in val-th position in checker by OR-ing
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
