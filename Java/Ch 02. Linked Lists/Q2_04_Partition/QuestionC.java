/*
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
		LinkedListNode head = node;	//both initialised to point to same node, so its same LL
		LinkedListNode tail = node;
		
		/* Partition list */
		while (node != null) {
			LinkedListNode next = node.next;	//temporary cache
			if (node.data < x) {
				/* Insert node at head. */
				node.next = head;
				head = node;
			} else {
				/* Insert node at tail. */
				tail.next = node;
				tail = node;
			}	
			node = next;
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
