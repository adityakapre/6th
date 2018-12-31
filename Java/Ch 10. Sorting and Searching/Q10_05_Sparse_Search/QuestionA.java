/*
If it weren't for the empty strings, we could simply use binary search. We would compare the string to be
found, str, with the midpoint of the array, and go from there.
With empty strings interspersed, we can implement a simple modification of binary search. All we need to
do is fix the comparison against mid, in case mid is an empty string. We simply move mid to the closest
non-empty string.
The recursive code below to solve this problem can easily be modified to be iterative. We provide such an
implementation in the code attachment.

The worst-case runtime for this algorithm is O(n). In fact, it's impossible to have an algorithm for this
problem that is better than O(n) in the worst case. After all, you could have an array of all empty strings
except for one non-empty string. There is no "smart" way to find this non-empty string. In the worst case,
you will need to look at every element in the array.
Careful consideration should be given to the situation when someone searches for t he empty string. Should
we find the location (which is an O(n) operation)? Or should we handle this as an error?
There's no correct answer here. This is an issue you should raise with your interviewer. Simply asking this
question will demonstrate that you are a careful coder.
*/
package Q10_05_Sparse_Search;

public class QuestionA {
	public static int search(String[] strings, String str, int first, int last) {
		if (first > last) {
			return -1;
		}
		
		/* Move mid to the middle */
		int mid = (last + first) / 2;
		
		/* If mid is empty, find closest non-empty string. */
		if (strings[mid].isEmpty()) { 
			int left = mid - 1;
			int right = mid + 1;
			while (true) {
				if (left < first && right > last) {
					return -1;
				} else if (right <= last && !strings[right].isEmpty()) {
					mid = right;
					break;
				} else if (left >= first && !strings[left].isEmpty()) {
					mid = left;
					break;
				}
				right++;
				left--;
			}
		}
			
		/* Check for string, and recurse if necessary */
		if (str.equals(strings[mid])) { // Found it!
			return mid;
		} else if (strings[mid].compareTo(str) < 0) { // Search right
			return search(strings, str, mid + 1, last);
		} else { // Search left
			return search(strings, str, first, mid - 1);
		}
	}	
		
	public static int search(String[] strings, String str) {
		if (strings == null || str == null || str.isEmpty()) {
			return -1;
		}
		return search(strings, str, 0, strings.length - 1);
	}
	
	public static void main(String[] args) {
        String[] stringList = {"apple", "", "", "banana", "", "", "", "carrot", "duck", "", "", "eel", "", "flower"};
        System.out.println(search(stringList, "ac"));
        
		//for (String s : stringList) {
		//	String cloned = new String(s);
        //	System.out.println("<" + cloned + "> " + " appears at location " + search(stringList, cloned));
		//}
	}
}
