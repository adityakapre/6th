/*
Solution #2: Iterative Approach
We want to detect linked lists where the front half of the list is the reverse of the second half. How would we
do that? By reversing the front half of the list. A stack can accomplish this.
We need to push the first half of the elements onto a stack. We can do this in two different ways, depending
on whether or not we know the size of the linked list.
If we know the size of the linked list, we can iterate through the first half of the elements in a standard for
loop, pushing each element onto a stack. We must be careful, of course, to handle the case where the length
of the linked list is odd.
If we don't know the size of the linked list, we can iterate through the linked list, using the fast runner/ slow
runner technique described in the beginning of the chapter. At each step in the loop, we push the data from
the slow runner onto a stack. When the fast runner hits the end of the list, the slow runner will have reached
the middle of the linked list. By this point, the stack will have all the elements from the front of the linked
list, but in reverse order.
*/

package Q2_06_Palindrome;

import CtCILibrary.LinkedListNode;

import java.util.Stack;

public class QuestionB {
	public static boolean isPalindrome(LinkedListNode head) {
		LinkedListNode fast = head;
		LinkedListNode slow = head;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		while (fast != null && fast.next != null) {
			stack.push(slow.data);
			slow = slow.next;
			fast = fast.next.next;			
		}
		
		/* Has odd number of elements, so skip the middle */
		if (fast != null) { 
			slow = slow.next;
		}
		
		while (slow != null) {
			int top = stack.pop().intValue();
			if (top != slow.data) {
				return false;
			}
			slow = slow.next;
		}
		return true;
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
		//nodes[length - 2].data = 9; // Uncomment to ruin palindrome
		
		LinkedListNode head = nodes[0];
		System.out.println(head.printForward());
		System.out.println(isPalindrome(head));
	}

}
