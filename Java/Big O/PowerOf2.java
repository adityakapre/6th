/*
What It Means
We can also approach the runtime by thinking about what the code is supposed to be doing. It's supposed
to be computing the powers of 2 from 1 through n.
Each call to powers0f2 results in exactly one number being printed and returned (excluding what happens
in the recursive calls). So if the algorithm prints 13 values at the end, then powers0f2 was called 13 times.
In this case, we are told that it prints all the powers of 2 between 1 and n. Therefore, the number of times
the function is called (which will be its runtime) must equal the number of powers of 2 between 1 and n.
There are log N powers of 2 between 1 and n. Therefore, the runtime is 0( log n ).

Rate of Increase
A final way to approach the runtime is to think about how the runtime changes as n gets bigger. After all,
this is exactly what big O time means.
If N goes from P to P+l, the number of calls to powersOfTwo might not change at all. When will the
number of calls to powersOfTwo increase? It will increase by 1 each time n doubles in size.
So, each time n doubles, the number of calls to powersOfTwo increases by 1. Therefore, the number of
calls to powersOfTwo is the number of times you can double 1 until you get n. It is x in the equation 2
x= n.
What is x? The value of x is log n. This is exactly what meant by x
Therefore, the runtime is O ( log n).
*/

package Example_16;

public class Example {

	public static int powersOf2(int n) {
		if (n < 1) {
			return 0;
		} else if (n == 1) {
			System.out.println(1);
			return 1;
		} else {
			int prev = powersOf2(n / 2);
			int curr = prev * 2;
			System.out.println(curr);
			return curr;
		}
	}
	
	public static void main(String[] args) {
		powersOf2(4);
	}

}
