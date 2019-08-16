/*
Q:
Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
intersecting node. Note that the intersection is defined based on reference, not value. That is, if the
kth node of the first linked list is the exact same node (by reference) as the jth node of the second
linked list, then they are intersecting.

A:
Let's draw a picture of intersecting linked lists to get a better feel for what is going on.
Here is a picture of intersecting linked lists:
And here is a picture of non-intersecting linked lists:

We should be careful here to not inadvertently draw a special case by making the linked lists the same
length.
Let's first ask how we would determine if two linked lists intersect.

Determining if there's an intersection.
How would we detect if two linked lists intersect? One approach would be to use a hash table and just
throw all the linked lists nodes into there. We would need to be careful to reference the linked lists by their
memory location, not by their value.
There's an easier way though. Observe that two intersecting linked lists will always have the same last node.
Therefore, we can just traverse to the end of each linked list and compare the last nodes.
How do we find where the intersection is, though?

Finding the intersecting node.
One thought is that we could traverse backwards through each linked list. When the linked lists"split'; that's
the intersection. Of course, you can't really traverse backwards through a singly linked list.
If the linked lists were the same length, you could just traverse through them at the same time. When they
collide, that's your intersection.

When they're not the same length, we'd like to just"chop off"-or ignore-those excess (gray) nodes.
How can we do this? Well, if we know the lengths of the two linked lists, then the difference between those
two linked lists will tell us how much to chop off.
We can get the lengths at the same time as we get the tails of the linked lists (which we used in the first step
to determine if there's an intersection).
Putting it all together.
We now have a multistep process.
1. Run through each linked list to get the lengths and the tails.
2. Compare the tails. If they are different (by reference, not by value), return immediately. There is no intersection.
3. Set two pointers to the start of each linked list.
4. On the longer linked list, advance its pointer by the difference in lengths.
5. Now, traverse on each linked list until the pointers are the same.
*/

package Q2_07_Intersection;

import CtCILibrary.AssortedMethods;
import CtCILibrary.LinkedListNode;

public class Question {

	//start here...
	public static LinkedListNode findIntersection(LinkedListNode list1, LinkedListNode list2) {
		if (list1 == null || list2 == null) return null;
		
		/* Get tail and sizes. */
		Result result1 = getTailAndSize(list1);
		Result result2 = getTailAndSize(list2);
		
		/* If different tail nodes, then there's no intersection. */
		if (result1.tail != result2.tail) {
			return null;			//tails not equal so lists never intersect
		}
		
		/* Set pointers to the start of each linked list. */
		LinkedListNode shorter = result1.size < result2.size ? list1 : list2;
		LinkedListNode longer = result1.size < result2.size ? list2 : list1;
		
		/* Advance the pointer for the longer linked list by the difference in lengths. */
		longer = getKthNode(longer, Math.abs(result1.size - result2.size));
		
		/* Move both pointers until you have a collision. */
		while (shorter != longer) {
			shorter = shorter.next;
			longer = longer.next;
		}
		
		/* Return either one. */
		return longer; 
	}
	
	public static class Result {
		public LinkedListNode tail;
		public int size;
		public Result(LinkedListNode tail, int size) {
			this.tail = tail;
			this.size = size;
		}
	}
	
	public static Result getTailAndSize(LinkedListNode list) {
		if (list == null) 
			return null;
		int size = 1;
		LinkedListNode current = list;
		while (current.next != null) {
			size++;
			current = current.next;
		}
		return new Result(current, size);
	}
	
	public static LinkedListNode getKthNode(LinkedListNode head, int k) {
		LinkedListNode current = head;
		while (k > 0 && current != null) {
			current = current.next;
			k--;
		}
		return current;
	}
	
	public static void main(String[] args) {
		/* Create linked list */
		int[] vals = {-1, -2, 0, 1, 2, 3, 4, 5, 6, 7, 8};
		LinkedListNode list1 = AssortedMethods.createLinkedListFromArray(vals);
		
		int[] vals2 = {12, 14, 15};
		LinkedListNode list2 = AssortedMethods.createLinkedListFromArray(vals2);
		
		list2.next.next = list1.next.next.next.next;
		
		System.out.println(list1.printForward());
		System.out.println(list2.printForward());
		
		
		LinkedListNode intersection = findIntersection(list1, list2);
		
		System.out.println(intersection.printForward());
	}

}
