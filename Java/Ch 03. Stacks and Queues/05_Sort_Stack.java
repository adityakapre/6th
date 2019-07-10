/*
Q:
Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use
an additional temporary stack, but you may not copy the elements into any other data structure
(such as an array). The stack supports the following operations: push, pop, peek, and isEmpty.

A:
One approach is to implement a rudimentary sorting algorithm. We search through the entire stack to find
the minimum element and then push that onto a new stack. Then, we find the new minimum element
and push that. This will actually require a total of three stacks: s1 is the original stack, s2 is the final sorted
stack, and s3 acts as a buffer during our searching of sl. To search sl for each minimum, we need to pop
elements from sl and push them onto the buffer, s3.
Unfortunately, this requires two additional stacks, and we can only use one. Can we do better? Yes.
Rather than searching for the minimum repeatedly, we can sort sl by inserting each element from sl in
order into s2. How would this work?

Let S is the stack to be sorted & R stack is used to sort S. R is"sorted"
We must store R in descending order from top and then reverse R into S to that we get elemets sorted in ascending order in S
which is our goal.

S : (top) 
      5
      10 
      7
R : (top)
     12
     8
     3
     1

When we pop 5 from S, we need to find the right place in R to insert this number. In this case, the correct
place is on R just above 3. How do we get it there? We can do this by popping 5 from S and holding it
in a temporary variable. Then, we move 12 and 8 over to S (by popping them from s2 and pushing them
onto sl) and then push 5 onto R.
Note that 8 and 12 are still in S-and that's okay! We just repeat the same steps for those two numbers as
we did for 5, each time popping off the top of S and putting it into the "right place" on R. (Of course, since
8 and 12 were moved from R to S precisely because they were larger than 5, the "right place" for these
elements will be right on top of 5. We won't need to muck around with R's other elements, and the inside
of the below while loop will not be run when tmp is 8 or 12.)

This algorithm is O (N sq.) time and O (N) space.
If we were allowed to use unlimited stacks, we could implement a modified quicksort or mergesort.
With the mergesort solution, we would create two extra stacks and divide the stack into two parts. We
would recursively sort each stack, and then merge them back together in sorted order into the original
stack. Note that this would require the creation of two additional stacks per level of recursion.
With the quicksort solution, we would create two additional stacks and divide the stack into the two stacks
based on a pivot element. The two stacks would be recursively sorted, and then merged back together
into the original stack. Like the earlier solution, this one involves creating two additional stacks per level of
recursion.
*/
package Q3_05_Sort_Stack;

import java.util.Stack;

import CtCILibrary.AssortedMethods;

public class Question {
	
	//solution start
	public static void sort(Stack<Integer> s) {
		Stack<Integer> r = new Stack<Integer>();
		while(!s.isEmpty()) {
			/* Insert each element in s in sorted order into r. */
			int tmp = s.pop();
			while(!r.isEmpty() && r.peek() > tmp) {
				s.push(r.pop());
			}
			r.push(tmp);
		}
		
		/* Copy the elements back. */
		while (!r.isEmpty()) {
			s.push(r.pop());
		}
	}
	//solution end
	
	//solution iff unlimited stacks allowed
	public static Stack<Integer> mergesort(Stack<Integer> inStack) {
		if (inStack.size() <= 1) {
			return inStack;
		}

		Stack<Integer> left = new Stack<Integer>();
		Stack<Integer> right = new Stack<Integer>();
		int count = 0;
		while (inStack.size() != 0) {
			count++;
			if (count % 2 == 0) {
				left.push(inStack.pop());
			} else {
				right.push(inStack.pop());
			}
		}

		left = mergesort(left);
		right = mergesort(right);

		while (left.size() > 0 || right.size() > 0) {
			if (left.size() == 0) {
				inStack.push(right.pop());
			} else if (right.size() == 0) {
				inStack.push(left.pop());
			} else if (right.peek().compareTo(left.peek()) <= 0) {
				inStack.push(left.pop());
			} else {
				inStack.push(right.pop());
			}
		}

		Stack<Integer> reverseStack = new Stack<Integer>();
		while (inStack.size() > 0) {
			reverseStack.push(inStack.pop());
		}

		return reverseStack;
	}
	
	
		
	public static void main(String [] args) {
		Stack<Integer> s = new Stack<Integer>();
		for (int i = 0; i < 10; i++) {
			int r = AssortedMethods.randomIntInRange(0,  1000);
			s.push(r);
		}
		
		sort(s);
		
		while(!s.isEmpty()) {
			System.out.println(s.pop());
		}
	}
}
