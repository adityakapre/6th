/*Solution #1: Reverse and Compare
Our first solution is to reverse the linked list and compare the reversed list to the original list. If they're the
same, the lists are identical.
Note that when we compare the linked list to the reversed list, we only actually need to compare the first
half of the list. If the first half of the normal list matches the first half of the reversed list, then the second half
of the normal list must match the second half of the reversed list.

Observe that we've modularized this code into reverse and is Equa 1 functions. We've also created a new
class so that we can return both the head and the tail of this method. We could have also returned a twoelement
array, but that approach is less maintainable.
*/

package Q2_06_Palindrome;

import CtCILibrary.LinkedListNode;

public class QuestionA {
	public static boolean isPalindrome(LinkedListNode head) {
		LinkedListNode reversed = reverseAndClone(head);
		return isEqual(head, reversed);
	}
		
	public static LinkedListNode reverseAndClone(LinkedListNode node) {
		LinkedListNode head = null;
		while (node != null) {
			LinkedListNode n = new LinkedListNode(node.data); // Clone
			n.next = head;
			head = n;
			node = node.next;
		}
		return head;
	}	
		
	public static boolean isEqual(LinkedListNode one, LinkedListNode two) {
		while (one != null && two != null) {
			if (one.data != two.data) {
				return false;
			}
			one = one.next;
			two = two.next;
		}
		return one == null && two == null;
	}
	
	public static void main(String[] args) {
		int length = 9;
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
		// nodes[length - 2].data = 9; // Uncomment to ruin palindrome
		
		LinkedListNode head = nodes[0];
		System.out.println(head.printForward());
		System.out.println(isPalindrome(head));
	}

}
