/*
Q:
One Away: There are three types of edits that can be performed on strings: insert a character,
remove a character, or replace a character. Given two strings, write a function to check if they are
one edit (or zero edits) away.
EXAMPLE
pale, ple -> true
pales, pale -> true
pale, bale -> true
pale, bae -> false

A:
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
	
	public static boolean oneEditAway(String first, String second) {
		if (first.length() == second.length()) {                //same lengths, so might be replacement
			return oneEditReplace(first, second);
		} else if (first.length() + 1 == second.length()) {
			return oneEditInsert(first, second);
		} else if (first.length() - 1 == second.length()) {
			return oneEditInsert(second, first);
		} 
		return false;
	}
	
	/* Check if you can insert a character into s1 to make s2. s1->smaller s2->larger */
	public static boolean oneEditInsert(String s1, String s2) {
		int index1 = 0;
		int index2 = 0;
		while (index2 < s2.length() && index1 < s1.length()) {
			//This condition should be true exactly once for string to be one insert away
			if (s1.charAt(index1) != s2.charAt(index2)) {
				//since s2 larger in length, we progress its ptr only once
				//when chars are not same to allow 1 distince character
				if (index1 == index2) {
					index2++; 
				} else {
					return false;	
				}
			} else {
				index1++;
				index2++;
			}
		}
		return true;
	}	
	
	public static void main(String[] args) {
		String a = "pse";
		String b = "pale";
		boolean isOneEdit = oneEditAway(a, b);
		System.out.println(a + ", " + b + ": " + isOneEdit);
	}
}

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
		
		/* Get shorter s1 and longer string s2.*/
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
