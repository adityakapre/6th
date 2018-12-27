/*
We described earlier that the issue was that we couldn't simultaneously return a counter and an index. If
we wrap the counter value with simple class (or even a single element array), we can mimic passing by
reference.
Each of these recursive solutions takes 0( n) space due to the recursive calls.
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
