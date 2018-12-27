/*
We know that push() should behave identically to a single stack, which means that we need push() to
call push() on the last stack in the array of stacks. We have to be a bit careful here though: if the last stack
is at capacity, we need to create a new stack. Our code should look something like this.

What should pop() do? It should behave similarly to push() in that it should operate on the last stack. If
the last stack is empty (after popping), then we should remove the stack from the list of stacks.

Follow Up: Implement popAt(int index)
This is a bit trickier to implement, but we can imagine a "rollover" system. If we pop an element from stack
1, we need to remove the bottom of stack 2 and push it onto stack 1. We then need to rollover from stack 3
to stack 2, stack 4 to stack 3, etc.
You could make an argument that, rather than "rolling over;' we should be okay with some stacks not
being at full capacity. This would improve the time complexity (by a fair amount, with a large number of
elements), but it might get us into tricky situations later on if someone assumes that all stacks (other than
the last) operate at full capacity. There's no "right answer" here; you should discuss this trade-off with your
interviewer.

This problem is not conceptually that tough, but it requires a lot of code to implement it fully. Your interviewer
would not ask you to implement the entire code.
A good strategy on problems like this is to separate code into other methods, like a leftShift method
that popAt can call. This will make your code cleaner and give you the opportunity to lay down the skeleton
of the code before dealing with some of the details.
*/
package Q3_03_Stack_of_Plates;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class SetOfStacks {
	ArrayList<Stack> stacks = new ArrayList<Stack>();
	public int capacity;
	
	public SetOfStacks(int capacity) { 
		this.capacity = capacity; 
	}
	
	public Stack getLastStack() {
		if (stacks.size() == 0) {
			return null;
		}
		return stacks.get(stacks.size() - 1);
	}
	
	public void push(int v) {
		Stack last = getLastStack();
		if (last != null && !last.isFull()) { // add to last
			last.push(v);
		} else { // must create new stack
			Stack stack = new Stack(capacity);
			stack.push(v);
			stacks.add(stack);
		}
	}
	
	public int pop() {
		Stack last = getLastStack();
		if (last == null) throw new EmptyStackException();
		int v = last.pop();
		if (last.size == 0) {
			stacks.remove(stacks.size() - 1);
		}
		return v;
	}
	
	public int popAt(int index) {
		return leftShift(index, true);
	}
	
	public int leftShift(int index, boolean removeTop) {
		Stack stack = stacks.get(index);
		int removed_item;
		if (removeTop) removed_item = stack.pop();
		else removed_item = stack.removeBottom();
		if (stack.isEmpty()) {
			stacks.remove(index);
		} else if (stacks.size() > index + 1) {
			int v = leftShift(index + 1, false);
			stack.push(v);
		}
		return removed_item;
	}
	
	public boolean isEmpty() {
		Stack last = getLastStack();
		return last == null || last.isEmpty();
	}
}

