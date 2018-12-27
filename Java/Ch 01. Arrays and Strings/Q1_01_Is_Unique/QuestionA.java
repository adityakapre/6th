/*
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
*/
package Q1_01_Is_Unique;

public class QuestionA {
	public static boolean isUniqueChars(String str) {
		if (str.length() > 128) {
			return false;
		}
		boolean[] char_set = new boolean[128];
		for (int i = 0; i < str.length(); i++) {
			int val = str.charAt(i);	//remember this string api to get each string character
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
