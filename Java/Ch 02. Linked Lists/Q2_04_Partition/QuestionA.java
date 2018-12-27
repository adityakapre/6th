/*
If this were an array, we would need to be careful about how we shifted elements. Array shifts are very
expensive.
However, in a linked list, the situation is much easier. Rather than shifting and swapping elements, we can
actually create two different linked lists: one for elements less than x, and one for elements greater than or
equal to x.
We iterate through the linked list, inserting elements into our before list or our after list. Once we reach
the end of the linked list and have completed this splitting, we merge the two lists.
This approach is mostly "stable" in that elements stay in their original order, other than the necessary movement
around the partition. The code below implements this approach.*/

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
			node.next = null;
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
