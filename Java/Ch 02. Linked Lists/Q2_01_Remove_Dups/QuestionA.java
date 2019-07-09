/*
Q:
Remove Dups: Write code to remove duplicates from an unsorted linked list.
FOLLOW UP
How would you solve this problem if a temporary buffer is not allowed?

A:
In order to remove duplicates from a linked list, we need to be able to track duplicates. A simple hash table
will work well here.
In the below solution, we simply iterate through the linked list, adding each element to a hash table. When
we discover a duplicate element, we remove the element and continue iterating. We can do this all in one
pass since we are using a linked list.

The above solution takes O(N) time, where N is the number of elements in the linked list.
*/
package Q2_01_Remove_Dups;

import java.util.HashSet;
import CtCILibrary.LinkedListNode;
//buffer = hashset
public class QuestionA {
	public static void deleteDups(LinkedListNode n) {
		HashSet<Integer> set = new HashSet<Integer>();
		LinkedListNode previous = null;
		while (n != null) {
			if (set.contains(n.data)) {
				previous.next = n.next;
			} else {
				set.add(n.data);
				previous = n;
			}
			n = n.next;
		}
	}
	
	public static void main(String[] args) {	
		LinkedListNode first = new LinkedListNode(0, null, null); //AssortedMethods.randomLinkedList(1000, 0, 2);
		LinkedListNode head = first;
		LinkedListNode second = first;
		for (int i = 1; i < 8; i++) {
			second = new LinkedListNode(i % 2, null, null);
			first.setNext(second);
			second.setPrevious(first);
			first = second;
		}
		System.out.println(head.printForward());
		deleteDups(head);
		System.out.println(head.printForward());
	}
}
