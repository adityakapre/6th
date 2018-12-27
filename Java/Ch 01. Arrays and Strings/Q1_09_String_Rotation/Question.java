/*
If we imagine that S2 is a rotation of S1, then we can ask what the rotation point is. For example, if you
rotate waterbottle after wat. you get erbottlewat. In a rotation, we cut S1 into two parts, x and y,
and rearrange them to get S2.
S1 = xy = waterbottle
x = wat
y = erbottle
s2 = yx = erbottlewat
So, we need to check if there's a way to split s1 into x and y such that xy = s1 and yx = s2. Regardless of
where the division between x and y is, we can see that yx will always be a substring of xyxy.That is, s2 will
always be a substring of s1s1.
And this is precisely how we solve the problem: simply do isSubstring(slsl, s2).

The runtime of this varies based on the runtime of isSubstring. But if you assume that isSubstring
runs in O(A+B) time (on strings of length A and B), then the runtime of is Rotation isO(N).
*/
package Q1_09_String_Rotation;

public class Question {
	public static boolean isSubstring(String big, String small) {
		if (big.indexOf(small) >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isRotation(String s1, String s2) {
	    int len = s1.length();
	    /* check that s1 and s2 are equal length and not empty */
	    if (len == s2.length() && len > 0) { 
	    	/* concatenate s1 and s1 within new buffer */
	    	String s1s1 = s1 + s1;
	    	return isSubstring(s1s1, s2);
	    }
	    return false;
	}
	
	public static void main(String[] args) {
		String[][] pairs = {{"apple", "pleap"}, {"waterbottle", "erbottlewat"}, {"camera", "macera"}};
		for (String[] pair : pairs) {
			String word1 = pair[0];
			String word2 = pair[1];
			boolean is_rotation = isRotation(word1, word2);
			System.out.println(word1 + ", " + word2 + ": " + is_rotation);
		}
	}

}
