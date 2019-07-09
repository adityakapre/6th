/*
Q:
Sum Lists: You have two numbers represented by a linked list, where each node contains a single
digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
function that adds the two numbers and returns the sum as a linked list.
EXAMPLE
Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
Output: 2 -> 1 -> 9. That is, 912.
FOLLOW UP
Suppose the digits are stored in forward order. Repeat the above problem.
Input: (6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
Output: 9 -> 1 -> 2. That is, 912.

A:
Advantage of storing units place at head of LL :  if the two numbers are of different number of digits, nulls can be handled on fly
unlike storing units place at tail 
GENERAL CONCEPT  : Store head from place where we start summing numbers

It's useful to remember in this problem how exactly addition works. Imagine the problem:
6 1 7 
+ 2 9 5

We start addition from units place. (i.e head in this problem)

First, we add 7 and 5 to get 12. The digit 2 becomes the last digit of the number, and 1 gets carried over to
the next step. Second, we add 1, 1, and 9 to get 11. The 1 becomes the second digit, and the other 1 gets
carried over the final step. Third and finally, we add 1, 6 and 2 to get 9. So, our value becomes 912.
We can mimic this process recursively by adding node by node, carrying over any "excess" data to the next
node. Let's walk through this for the below linked list:
7 -> 1 -> 6
+ 5 -> 9 -> 2
We do the following:
1. We add 7 and 5 first, getting a result of 12. 2 becomes the first node in our linked list, and we "carry" the
1 to the next sum.
List: 2 -> ?
2. We then add 1 and 9, as well as the "carry;' getting a result of 11. 1 becomes the second element of our
linked list, and we carry the 1 to the next sum.
List: 2 -> 1 ->?
3. Finally, we add 6, 2 and our"carrY:'to get 9. This becomes the final element of our linked list.
List: 2 -> 1 -> 9.
*/

package Q2_05_Sum_Lists;

import CtCILibrary.LinkedListNode;

public class QuestionA {
	private static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
		return addLists(l1, l2, 0);
	}
	
	private static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
		if (l1 == null && l2 == null && carry == 0) {
             		return null;
		}
		
		LinkedListNode result = new LinkedListNode();
		int value = carry;
		if (l1 != null) {
			value += l1.data;
		}
		if (l2 != null) {
			value += l2.data;
		}
		result.data = value % 10;                         //modulus, returns remainder
		if (l1 != null || l2 != null) {
			LinkedListNode more = addLists(l1 == null ? null : l1.next, 
										   l2 == null ? null : l2.next, 
										   value >= 10 ? 1 : 0);
			result.setNext(more);
		}
		return result;
	}
	
	public static int linkedListToInt(LinkedListNode node) {
		int value = 0;
		if (node.next != null) {
			value = 10 * linkedListToInt(node.next);
		}
		return value + node.data;
	}	
	
	public static void main(String[] args) {
		LinkedListNode lA1 = new LinkedListNode(9, null, null);
		LinkedListNode lA2 = new LinkedListNode(9, null, lA1);
		LinkedListNode lA3 = new LinkedListNode(9, null, lA2);
		
		LinkedListNode lB1 = new LinkedListNode(1, null, null);
		LinkedListNode lB2 = new LinkedListNode(0, null, lB1);
		LinkedListNode lB3 = new LinkedListNode(0, null, lB2);	
		
		LinkedListNode list3 = addLists(lA1, lB1);
		
		System.out.println("  " + lA1.printForward());		
		System.out.println("+ " + lB1.printForward());			
		System.out.println("= " + list3.printForward());	
		
		int l1 = linkedListToInt(lA1);
		int l2 = linkedListToInt(lB1);
		int l3 = linkedListToInt(list3);
		
		System.out.print(l1 + " + " + l2 + " = " + l3 + "\n");
		System.out.print(l1 + " + " + l2 + " = " + (l1 + l2));		
	}
}
