/*
To do this, observe that both methods follow similar logic: compare each character and ensure that the
strings are only different by one. The methods vary in how they handle that difference. The method
oneEditReplace does nothing other than flag the difference, whereas oneEditinsert increments
the pointer to the longer string. We can handle both of these in the same method.

Some people might argue the first approach is better, as it is clearer and easier to follow. Others, however,
will argue that the second approach is better, since it's more compact and doesn't duplicate code (which
can facilitate maintainability).
You don't necessarily need to "pick a side:'You can discuss the tradeoffs with your interviewer.
*/

package Q1_05_One_Away;

public class QuestionB {	
public static boolean oneEditAway(String first, String second) {
		/* Length checks. */
		if (Math.abs(first.length() - second.length()) > 1) {
			return false;
		}
		
		/* Get shorter and longer string.*/
		String s1 = first.length() < second.length() ? first : second;
		String s2 = first.length() < second.length() ? second : first;

		int index1 = 0;
		int index2 = 0;
		boolean foundDifference = false;
		while (index2 < s2.length() && index1 < s1.length()) {
			if (s1.charAt(index1) != s2.charAt(index2)) {
				/* Ensure that this is the first difference found.*/
				if (foundDifference) return false;
				foundDifference = true;
				if (s1.length() == s2.length()) { // On replace, move shorter pointer
					index1++;
				}
			} else {
				index1++; // If matching, move shorter pointer 
			}
			index2++; // Always move pointer for longer string 
		}
		return true;
	}
	
	
	
	public static void main(String[] args) {
		String a = "palee";
		String b = "pale";
		boolean isOneEdit1 = oneEditAway(a, b);
		System.out.println(a + ", " + b + ": " + isOneEdit1);

		String c = "pale";
		String d = "pkle";
		boolean isOneEdit2 = oneEditAway(c, d);
		System.out.println(c + ", " + d + ": " + isOneEdit2);
	}

}
