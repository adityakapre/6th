/*
Q:
Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
before all nodes greater than or equal to x. If x is contained within the list the values of x only need
to be after the elements less than x (see below). The partition element x can appear anywhere in the
"right partition"; it does not need to appear between the left and right partitions.
EXAMPLE
Input: 3->5->8->5->10->2->1 [partition= 5]
Output: 3->1->2->10->5->5-> 8

A:
If this were an array, we would need to be careful about how we shifted elements. Array shifts are very
expensive.
However, in a linked list, the situation is much easier. Rather than shifting and swapping elements, we can
actually create two different linked lists: one for elements less than x, and one for elements greater than or
equal to x.
We iterate through the linked list, inserting elements into our before list or our after list. Once we reach
the end of the linked list and have completed this splitting, we merge the two lists.
This approach is mostly "stable" in that elements stay in their original order, other than the necessary movement
around the partition. The code below implements this approach.
*/

package Q2_04_Partition;

import CtCILibrary.LinkedListNode;

public class Question {

	public static LinkedListNode partition(LinkedListNode node, int x) {
		LinkedListNode beforeStart = null;
		LinkedListNode beforeEnd = null;
		LinkedListNode afterStart = null;
		LinkedListNode afterEnd = null;
		
		/* Partition list */
		while (node != null) {
			LinkedListNode next = node.next;
			node.next = null;			// Imp to set node.next to null
			if (node.data < x) {
				if (beforeStart == null) {
					beforeStart = node;
					beforeEnd = beforeStart;
				} else {
					beforeEnd.next = node;
					beforeEnd = node;
				}
			} else {
				if (afterStart == null) {
					afterStart = node;
					afterEnd = afterStart;
				} else {
					afterEnd.next = node;
					afterEnd = node;
				}
			}	
			node = next;
		}
		
		/* Merge before list and after list */
		if (beforeStart == null) {
			return afterStart;
		}
		
		beforeEnd.next = afterStart;
		return beforeStart;
	}
	
	public static void main(String[] args) {
		/* Create linked list */
		int[] vals = {33,9,2,3,10,10389,838,874578,5};
		LinkedListNode head = new LinkedListNode(vals[0], null, null);
		LinkedListNode current = head;
		for (int i = 1; i < vals.length; i++) {
			current = new LinkedListNode(vals[i], null, current);
		}
		System.out.println(head.printForward());
		
		/* Partition */
		LinkedListNode h = partition(head, 3);
		
		/* Print Result */
		System.out.println(h.printForward());
	}
}

/*
A:
If it bugs you to keep around four different variables for tracking two linked lists, you're not alone. We can
make this code a bit shorter.
If we don't care about making the elements of the list "stable" (which there's no obligation to, since the
interviewer hasn't specified that), then we can instead rearrange the elements by growing the list at the
head and tail.
In this approach, we start a"new" list (using the existing nodes). Elements bigger than the pivot element are
put at the tail and elements smaller are put at the head. Each time we insert an element, we update either
the head or tail.
*/

package Q2_04_Partition;

import CtCILibrary.LinkedListNode;

public class QuestionC {

	public static LinkedListNode partition(LinkedListNode node, int x) {
		LinkedListNode head = node;	//IMP : both initialised to point to same node, so its same LL
		LinkedListNode tail = node;
		
		/* Partition list */
		while (node != null) {
			if (node.data < x) {
				/* Insert node at head. */
				node.next = head;
				head = node;
			} else {
				/* Insert node at tail. */
				tail.next = node;
				tail = node;
			}	
			node = node.next;
		}
		tail.next = null;				//remember to set tails's next to null
		 
		return head;					//return start of list
	}
	
	public static void main(String[] args) {
		int length = 20;
		LinkedListNode[] nodes = new LinkedListNode[length];
		for (int i = 0; i < length; i++) {
			nodes[i] = new LinkedListNode(i >= length / 2 ? length - i - 1 : i, null, null);
		}
		
		for (int i = 0; i < length; i++) {
			if (i < length - 1) {
				nodes[i].setNext(nodes[i + 1]);
			}
			if (i > 0) {
				nodes[i].setPrevious(nodes[i - 1]);
			}
		}
		
		LinkedListNode head = nodes[0];
		System.out.println(head.printForward());
		
		LinkedListNode h = partition(head, 8);
		System.out.println(h.printForward());
	}

}
