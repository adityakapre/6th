/*
In this problem, you are not given access to the head of the linked list. You only have access to that node.
The solution is simply to copy the data from the next node over to the current node, and then to delete the
next node.
Note that this problem cannot be solved if the node to be deleted is the last node in the linked list. That's
okay-your interviewer wants you to point that out, and to discuss how to handle this case. You could, for
example, consider marking the node as dummy.
*/

package Q2_03_Delete_Middle_Node;

import CtCILibrary.AssortedMethods;
import CtCILibrary.LinkedListNode;

public class Question {

	public static boolean deleteNode(LinkedListNode n) {
		if (n == null || n.next == null) {
			return false; // Failure
		} 
		LinkedListNode next = n.next; 
		n.data = next.data; 
		n.next = next.next; 
		return true;
	}
	
	public static void main(String[] args) {
		LinkedListNode head = AssortedMethods.randomLinkedList(10, 0, 10);
		System.out.println(head.printForward());
		deleteNode(head.next.next.next.next); // delete node 4
		System.out.println(head.printForward());
	}

}
