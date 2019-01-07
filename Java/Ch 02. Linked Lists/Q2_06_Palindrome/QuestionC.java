/*
Solution #3: Recursive Approach
First, a word on notation: in this solution, when we use the notation node Kx, the variable K indicates the
value of the node data, and x (which is either for b) indicates whether we are referring to the front node
with that value or the back node. For example, in the below linked list node 2b would refer to the second
(back) node with value 2.

Now, like many linked list problems, you can approach this problem recursively. We may have some intuitive
idea that we want to compare element 0 and element n - 1, element 1 and element n - 2, element 2
and element n-3, and so on, until the middle element(s). For example:
0 ( 1 ( 2 ( 3 ) 2 ) 1 ) 0
In order to apply this approach, we first need to know when we've reached the middle element, as this will
form our base case. We can do this by passing in (length - 2) for the length each time. 
When the length equals 0 or 1, we're at the center of the linked list. This is because the length is reduced by 2 each time. 
Once we've recursed N/2 times, length will be down to 0.

1 recurse(Node n, int length) {
2 	if (length== 0 || length== 1) {
3 		return [something]; // At middle
4 	}
5 	recurse(n.next, length - 2);
7 }

This method will form the outline of the isPalindrome method. The "meat" of the algorithm though is
comparing node i to node n - i to check if the linked list is a palindrome. How do we do that?
Let's examine what the call stack looks like:
1 vl = is Palindrome: list = 0 ( 1 ( 2 ( 3 ) 2 ) 1 ) 0. length = 7
2 	v2 = isPalindrome: list = 1 ( 2 ( 3 ) 2 ) 1 ) 0. length = 5
3 		v3 = isPalindrome: list = 2 ( 3 ) 2 ) 1 ) 0. length = 3
4 			v4 = isPalindrome: list = 3 ) 2 ) 1 ) 0. length = 1
5 		returns v3 //middle element
6 	returns v2
7 returns vl
8 returns ?

In the above call stack, each call wants to check if the list is a palindrome by comparing its head node with
the corresponding node from the back of the list. That is:

Line 1 needs to compare node 0f with node 0b
Line 2 needs to compare node 1f with node lb
Line 3 needs to compare node 2f with node 2b
Line 4 needs to compare node 3f with node 3b

If we rewind the stack, passing nodes back as described below, we can do just that:

Line 4 sees that it is the middle node (since length = 1), and passes back head.next. The value head
equals node 3, so head.next is node 2b.
Line 3 compares its head, node 2f, to returned_node (the value from the previous recursive call),
which is node 2b. lf the values match, it passes a reference to node lb (returned_node. next) up
to line 2.
Line 2 compares its head (node 1f) to returned_node (node lb). If the values match, it passes a
reference to node 0b (or, returned_node.next) up to line 1.
Line 1 compares its head, node 0f, to returned_node, which is node 0b. If the values match, it
returns true.

To generalize, each call compares its head to returned_node, and then passes returned_node. next
up the stack. In this way, every node i gets compared to node n - i. If at any point the values do not
match, we return false, and every call up the stack checks for that value.
But wait, you might ask, sometimes we said we'll return a boolean value, and sometimes we're returning
a node. Which is it?
It's both. We create a simple class with two members, a boolean and a node, and return an instance of
that class.

1 class Result {
2 	public LinkedlistNode node;
3 	public boolean result;
4 }

The example below illustrates the parameters and return values from this sample list.
1 isPalindrome: list = 0 ( 1 ( 2 ( 3 ( 4 ) 3 ) 2 ) 1 ) 0. len = 9
2 is Palindrome: list = 1 ( 2 ( 3 ( 4 ) 3 ) 2 ) 1 ) 0. len = 7
3 is Palindrome: list = 2 ( 3 ( 4 ) 3 ) 2 ) 1 ) 0. len = 5
4 is Palindrome: list = 3 ( 4 ) 3 ) 2 ) 1 ) 0, len = 3
5 isPalindrome: list = 4 ) 3 ) 2 ) 1 ) 0. len = 1
6 returns node 3b, true
7 returns node 2b, true
8 returns node lb, true
9 returns node 0b, true
10 returns null, true
Implementing this code is now just a matter of filling in the details.
*/

package Q2_06_Palindrome;

import CtCILibrary.LinkedListNode;

public class QuestionC {
	
	public static class Result {
		public LinkedListNode node;
		public boolean result;
		public Result(LinkedListNode n, boolean res) {
			node = n;
			result = res;
		}
	}

	public static Result isPalindromeRecurse(LinkedListNode head, int length) {
		if (head == null || length <= 0) { // Even number of nodes
			return new Result(head, true);				//At middle [even]
		} else if (length == 1) { // Odd number of nodes
			return new Result(head.next, true);			//At middle [odd]
		} 
		
		/* Recurse on sublist. */
		Result res = isPalindromeRecurse(head.next, length - 2);
		
		/* If child calls are not a palindrome, pass back up a failure. */
		if (!res.result || res.node == null) {
			return res;
		} 
		
		/* Check if matches corresponding node on other side. */
		res.result = (head.data == res.node.data); 
		
		/* Return corresponding node. */
		res.node = res.node.next;
		
		return res;
	}
	
	public static int lengthOfList(LinkedListNode n) {
		int size = 0;
		while (n != null) {
			size++;
			n = n.next;
		}
		return size;
	}
	
	//start here...
	public static boolean isPalindrome(LinkedListNode head) {
		int length = lengthOfList(head);
		Result p = isPalindromeRecurse(head, length);
		return p.result;
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
