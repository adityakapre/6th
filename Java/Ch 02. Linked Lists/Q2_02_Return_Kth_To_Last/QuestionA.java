/*Solution #2: Recursive
This algorithm recurses through the linked list. When it hits the end, the method passes back a counter set
to 0. Each parent call adds 1 to this counter. When the counter equals k, we know we have reached the kth
to last element of the linked list.
Implementing this is short and sweet-provided we have a way of"passing back" an integer value through
the stack. Unfortunately, we can't pass back a node and a counter using normal return statements. So how
do we handle this?
Approach A: Don't Return the Element.
One way to do this is to change the problem to simply printing the kth to last element. Then, we can pass
back the value of the counter simply through return values.
*/

package Q2_02_Return_Kth_To_Last;

import CtCILibrary.*;

public class QuestionA {

	public static int printKthToLast(LinkedListNode head, int k) {
		if (head == null) {
			return 0;
		}
		int index = printKthToLast(head.next, k) + 1;
		if (index == k) {
			System.out.println(k + "th to last node is " + head.data);
		}
		return index;
	}
	
	public static void main(String[] args) {
		int[] array = {0, 1, 2, 3, 4, 5, 6};
		LinkedListNode head = AssortedMethods.createLinkedListFromArray(array);
		for (int i = 0; i <= array.length + 1; i++) {
			printKthToLast(head, i);
		}
	}

}
