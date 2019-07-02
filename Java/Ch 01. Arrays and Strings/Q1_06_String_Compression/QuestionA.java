/*
Q:
Implement a method to perform basic string compression using the counts
of repeated characters. For example, the string aabcccccaaa would become
a2blc5a3. If the "compressed" string would not become smaller than the original
string, your method should return the original string.

A:
At first glance, implementing this method seems fairly straightforward, but perhaps a bit tedious. We iterate
through the string, copying characters to a new string and counting the repeats. At each iteration, check
if the current character is the same as the next character. If not, add its compressed version to the result.

This works. ls it efficient, though?Take a look at the runtime of this code.
The runtime is O(p + k sq.), where p is the size of the original string and k is thelnumber of character
sequences. For example, if the string is aabccdeeaa, then there are six characte sequences. It's slow
because string concatenation operates in O(n sq.) time (see StringBuilder on pg 8 ).
We can fix this by using a StringBuilder.
*/
package Q1_06_String_Compression;

public class QuestionA {	
	public static String compressBad(String str) {
		String compressedString = "";
		int countConsecutive = 0;
		for (int i = 0; i < str.length(); i++) {
			countConsecutive++;
			
			/* If next character is different than current, append this char to result.*/
			if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
				compressedString += "" + str.charAt(i) + countConsecutive;
				countConsecutive = 0;
			}
		}
		return compressedString.length() < str.length() ? compressedString : str;
	}
	
	public static void main(String[] args) {
		String str = "aa";
		System.out.println(str);
		System.out.println(compressBad(str));
	}
}

/*
A:
Both of these solutions create the compressed string first and then return the shorter of the input string
and the compressed string.
*/
package Q1_06_String_Compression;

public class QuestionB {	
	public static String compress(String str) {
		StringBuilder compressed = new StringBuilder();
		int countConsecutive = 0;
		for (int i = 0; i < str.length(); i++) {
			countConsecutive++;
			
			/* If next character is different than current, append this char to result.*/
			if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
				compressed.append(str.charAt(i));
				compressed.append(countConsecutive);
				countConsecutive = 0;
			}
		}
		return compressed.length() < str.length() ? compressed.toString() : str;
	}
	
	public static void main(String[] args) {
		String str = "aa";
		System.out.println(str);
		System.out.println(compress(str));
	}
}
