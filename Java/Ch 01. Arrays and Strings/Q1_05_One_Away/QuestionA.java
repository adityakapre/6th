/*
There is a "brute force" algorithm to do this. We could check all possible strings that are one edit away by
testing the removal of each character (and comparing), testing the replacement of each character (and
comparing), and then testing the insertion of each possible character (and comparing).
That would be too slow, so let's not bother with implementing it.

This is one of those problems where it's helpful to think about the "meaning" of each of these operations.
What does it mean for two strings to be one insertion, replacement, or removal away from each other?
• Replacement: Consider two strings, such as bale and pale, that are one replacement away. Yes, that
does mean that you could replace a character in bale to make pale. But more precisely, it means that
they are different only in one place.
• Insertion: The strings apple and aple are one insertion away. This means that if you compared the
strings, they would be identical-except for a shift at some point in the strings.
• Removal: The strings apple and aple are also one removal away, since removal is just the inverse of
insertion.

We can go ahead and implement this algorithm now. We'll merge the insertion and removal check into one
step, and check the replacement step separately.
Observe that you don't need to check the strings for insertion, removal, and replacement edits. The lengths
of the strings will indicate which of these you need to check.

This algorithm (and almost any reasonable algorithm) takes O (n) time, where n is the length of the shorter
string.

Why is the runtime dictated by the shorter string instead of the longer string? If the strings are
the same length (plus or minus one character), then it doesn't matter whether we use the longer
string or the shorter string to define the runtime. If the strings are very different lengths, then the
algorithm will terminate in 0(1) time. One really, really long string therefore won't significantly
extend the runtime. It increases the runtime only if both strings are long.

We might notice that the code for one EditReplace is very simtlar to that for one Editinsert. We can
merge them into one method.
*/
package Q1_05_One_Away;

public class QuestionA {

	public static boolean oneEditReplace(String s1, String s2) {
		boolean foundDifference = false;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				if (foundDifference) {
					return false;
				}
				
				foundDifference = true;
			}
		}
		return true;
	}
	
	/* Check if you can insert a character into s1 to make s2. */
	public static boolean oneEditInsert(String s1, String s2) {
		int index1 = 0;
		int index2 = 0;
		while (index2 < s2.length() && index1 < s1.length()) {
			if (s1.charAt(index1) != s2.charAt(index2)) {
				if (index1 != index2) {
					return false;
				}		
				index2++;
			} else {
				index1++;
				index2++;
			}
		}
		return true;
	}	
	
	public static boolean oneEditAway(String first, String second) {
		if (first.length() == second.length()) {
			return oneEditReplace(first, second);
		} else if (first.length() + 1 == second.length()) {
			return oneEditInsert(first, second);
		} else if (first.length() - 1 == second.length()) {
			return oneEditInsert(second, first);
		} 
		return false;
	}
	
	public static void main(String[] args) {
		String a = "pse";
		String b = "pale";
		boolean isOneEdit = oneEditAway(a, b);
		System.out.println(a + ", " + b + ": " + isOneEdit);
	}

}
