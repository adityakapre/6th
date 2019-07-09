/*
Q:
Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.

Solution #1: If linked list size is known
If the size of the linked list is known, then the kth to last element is the ( length - k)th element. We can
just iterate through the linked list to find this element. Because this solution is so trivial, we can almost be
sure that this is not what the interviewer intended.
*/

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
Of course, this is only a valid solution if the interviewer says it is valid
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

/*
We described earlier that the issue was that we couldn't simultaneously return a counter and an index. If
we wrap the counter value with simple class (or even a single element array), we can mimic passing by
reference.
Each of these recursive solutions takes 0(n) space due to the recursive calls.
There are a number of other solutions that we haven't addressed. We could store the counter in a static variable.
Or, we could create a class that stores both the node and the counter, and return an instance of that
class. Regardless of which solution we pick, we need a way to update both the node and the counter in a
way that all levels of the recursive stack will see.
*/

package Q2_02_Return_Kth_To_Last;

import CtCILibrary.*;

public class QuestionC {
	public static class Index {
		public int value = 0;
	}	
	
	public static LinkedListNode kthToLast(LinkedListNode head, int k) {
		Index idx = new Index();
		return kthToLast(head, k, idx);
	}
	
	public static LinkedListNode kthToLast(LinkedListNode head, int k, Index idx) {
		if (head == null) {
			return null;
		}
		LinkedListNode node = kthToLast(head.next, k, idx);
		idx.value = idx.value + 1;
		if (idx.value == k) {
			return head;
		} 
		return node;
	}		
	
	public static void main(String[] args) {
		int[] array = {0, 1, 2, 3, 4, 5, 6};
		LinkedListNode head = AssortedMethods.createLinkedListFromArray(array);
		for (int i = 0; i <= array.length + 1; i++) {
			LinkedListNode node = kthToLast(head, i);
			String nodeValue = node == null ? "null" : "" + node.data;
			System.out.println(i + ": " + nodeValue);
		}
	}
}
