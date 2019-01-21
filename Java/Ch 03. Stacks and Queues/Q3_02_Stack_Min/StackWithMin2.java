/*
There's just one issue with this: if we have a large stack, we waste a lot of space by keeping track of the min
for every single element. Can we do better?

We can (maybe) do a bit better than this by using an additional stack which keeps track of the mins.
Why might this be more space efficient? Suppose we had a very large stack and the first element inserted
happened to be the minimum. In the first solution, we would be keeping n integers, where n is the size of
the stack. In the second solution though, we store just a few pieces of data: a second stack with one element
and the members within this stack.
*/

package Q3_02_Stack_Min;

import java.util.Stack;

public class StackWithMin2 extends Stack<Integer> {
	Stack<Integer> s2;
	
	public StackWithMin2() {
		s2 = new Stack<Integer>();		
	}
	
	public void push(int value){
		// "=" in "<=" is important as we push min number of times we see it so its in sync w/ pop
		//Remember, push method decides what is min, not min() method, min() just gives current min
		if (value <= min()) { 
			s2.push(value);
		}
		super.push(value);
	}
	
	public Integer pop() {
		int value = super.pop();
		if (value == min()) {
			s2.pop();			
		}
		return value;
	}
	
	public int min() {
		if (s2.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return s2.peek();
		}
	}
}


