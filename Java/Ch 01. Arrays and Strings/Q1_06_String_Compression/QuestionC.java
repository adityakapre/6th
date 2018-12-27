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
