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

Why is string concatenation in java is n^2 ? 
: String is immutable in java, so each "+" creates a new string all over again

public String joinWords(String[] words) {
    String sentence = "";
    for(String w : words) {
        sentence = sentence + w;
    }
    return sentence;
}

e.g consider building "hello"

Since java strings are immutable each + gets you a new copy of the string. Since java strings are immutable 
each + gets you a new copy of the string. So as you were saying, first you build "h", then "he", then "hel" 
and so on. 
Let's suppose I have an n character string. The first + requires building a string of length 2. 
The next plus requires building a string of length 3, and so on for a total cost of 2 + 3 + ... + n. 
This is the arithmetic series (minus 1, but constant differences don't affect big-O). The sum of the 
arithmetic series is (n^2 + n) / 2, which is O(n^2). (dividing by 2 doesn't change that!)

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

/*
Both of these solutions create the compressed string first and then return the shorter of the input string
and the compressed string.
Instead, we can check in advance. This will be more optimal in cases where we don't have a large number of
repeating characters. It will avoid us having to create a string that we never use. The downside of this is that
it causes a second loop through the characters and also adds nearly duplicated code.
One other benefit of this approach is that we can initialize StringBuilder to its necessary capacity
up-front. Without this, StringBuilder will (behind the scenes) need to double its capacity every time it
hits capacity. The capacity could be double what we ultimately need.
*/
package Q1_06_String_Compression;

public class QuestionC {	
	public static String compress(String str) {
		int finalLength = countCompression(str);
		if (finalLength >= str.length()) return str;
		
		StringBuffer compressed = new StringBuffer(finalLength); // initialize capacity
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
		return compressed.toString();
	}
	
	public static int countCompression(String str) {
		int compressedLength = 0;
		int countConsecutive = 0;
		for (int i = 0; i < str.length(); i++) {
			countConsecutive++;
			
			/* If next character is different than current, append this char to result.*/
			if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
				compressedLength += 1 + String.valueOf(countConsecutive).length();
				countConsecutive = 0;
			}
		}
		return compressedLength;
	}		
	
	public static void main(String[] args) {
		String str = "aa";
		System.out.println(str);
		System.out.println(compress(str));
	}
}
