/*
Like many problems, this one somewhat depends on how well we'd like to support these stacks. If we're
okay with simply allocating a fixed amount of space for each stack, we can do that. This may mean though
that one stack runs out of space, while the others are nearly empty.
Alternatively, we can be flexible in our space allocation, but this significantly increases the complexity of
the problem.
Approach 1: Fixed Division
We can divide the array in three equal parts and allow the individual stack to grow in that limited space.
Note: We will use the notation "[" to mean inclusive of an end point and "(" to mean exclusive of an end
point.
For stack 1, we will use [0, X).
For stack 2, we will use [ X , 2X ) .
For stack 3, we will use [ 2X , n) .

If we had additional information about the expected usages of the stacks, then we could modify this algorithm
accordingly. For example, if we expected Stack 1 to have many more elements than Stack 2, we could
allocate more space to Stack 1 and less space to Stack 2.
*/
package Q3_01_Three_in_One;

import java.util.EmptyStackException;

import CtCILibrary.AssortedMethods;

public class FixedMultiStack {
	private int numberOfStacks = 3;
	private int stackCapacity;	//max no of items that a stack can hold
	private int[] values;		//hold stack items
	private int[] sizes;		//hold size of each stack
	
	public FixedMultiStack(int stackSize) {
		stackCapacity = stackSize;
		values = new int[stackSize * numberOfStacks];
		sizes = new int[numberOfStacks];
	}

	/* Push value onto stack. */
	public void push(int stackNum, int value) throws FullStackException {
		/* Check that we have space for the next element */
		if (isFull(stackNum)) { 
			throw new FullStackException();
		}
		
		/* Increment stack pointer and then update top value. */		
		sizes[stackNum]++;
		values[indexOfTop(stackNum)] = value;	
	}

	/* Pop item from top stack. */
	public int pop(int stackNum) {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}
		
		int topIndex = indexOfTop(stackNum);
		int value = values[topIndex]; // Get top
		values[topIndex] = 0; // Clear 
		sizes[stackNum]--; // Shrink
		return value;
	}

	/* Return top element. */
	public int peek(int stackNum) {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}		
		return values[indexOfTop(stackNum)];
	}

	/* Return if stack is empty. */
	public boolean isEmpty(int stackNum) {
		return sizes[stackNum] == 0;
	}
	
	/* Return if stack is full. */
	public boolean isFull(int stackNum) {
		return sizes[stackNum] == stackCapacity;
	}
	
	/* Returns index of the top of the stack. */
	private int indexOfTop(int stackNum) {
		int offset = stackNum * stackCapacity;
		int size = sizes[stackNum];
		return offset + size - 1;
	}	
	
	public int[] getValues() {
		return values;
	}
	
	public int[] getStackValues(int stackNum) {
		int[] items = new int[sizes[stackNum]];
		for (int i = 0; i < items.length; i++) {
			items[i] = values[stackNum * stackCapacity + i];
		}
		return items;
	}
	
	public String stackToString(int stackNum) {
		int[] items = getStackValues(stackNum);
		return stackNum + ": " + AssortedMethods.arrayToString(items);
	}	
}
