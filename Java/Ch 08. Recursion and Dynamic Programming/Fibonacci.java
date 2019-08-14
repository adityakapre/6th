/*
The Fibonacci Sequence is the series of numbers: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ... 
The next number is found by adding up the two numbers before it.
f(n) = f(n-1) + f(n-2)

for fibonacci w/o memoization
suppose n=5, fibonacci(5)

			         f(5)                       height=1
			   /                \
		         f(4)                f(3)
		     /       \              /  \
		  f(3)       f(2)        f(2)   f(1)
		  /  \        / \      /   \                
               f(2)   f(1) f(1)  f(0)  f(1) f(0)         
	       /   \              
	     f(1) f(0)     				    height=5
	     
	     
so in above, the height of tree generated as result of 2 fibonacci calls is n (above its 5)
so number of nodes in tree with height n is roughly 2^n
so we need 2^n computations
so the time complexity of the recurrsive algorithm is 2^n

Had it been 3 recurrsive calls, would have been 3^n

if t is empty,
	height(t) = −1
Else
	height(t) = 1+ max (height(leftTree(t)), height(rightTree(t)))
*/

package Introduction;

	public class FibonacciA {
		public static int fibonacci(int i) {
			if (i == 0) {
				return 0;
			}
			if (i == 1) {
				return 1;
			}
			return fibonacci(i - 1) + fibonacci(i - 2);
	}
	
	public static void main(String[] args) {
		int max = 35; // WARNING: If you make this above 40ish, your computer may serious slow down.
		int trials = 10; // Run code multiple times to compute average time.
		double[] times = new double[max]; // Store times
		
		
		for (int j = 0; j < trials; j++) { // Run this 10 times to compute
			for (int i = 0; i < max; i++) {
				long start = System.currentTimeMillis();
				fibonacci(i);
				long end = System.currentTimeMillis();
				long time = end - start;
				times[i] += time; 
			}
		}
		
		for (int j = 0; j < max; j++) {
			System.out.println(j + ": " + times[j] / trials + "ms");
		}
	}
}

/*
Fibonacci with memoization
*/
package Introduction;

public class FibonacciB {

	public static int fibonacci(int n) {
		return fibonacci(n, new int[n + 1]);
	}
	
	public static int fibonacci(int i, int[] memo) {
		if (i == 0) return 0;
		else if (i == 1) return 1;
		
		if (memo[i] == 0) {
			memo[i] = fibonacci(i - 1, memo) + fibonacci(i - 2, memo);
		}
		return memo[i];
	}
	
	public static void main(String[] args) {
		int max = 100; // Make this as big as you want! (Though you'll exceed the bounds of a long around 46)
		int trials = 10; // Run code multiple times to compute average time.
		double[] times = new double[max]; // Store times
		
		for (int j = 0; j < trials; j++) { // Run this 10 times to compute
			for (int i = 0; i < max; i++) {
				long start = System.currentTimeMillis();
				System.out.println(fibonacci(i));
				long end = System.currentTimeMillis();
				long time = end - start;
				times[i] += time; 
			}
		}
		
		for (int j = 0; j < max; j++) {
			//System.out.println(j + ": " + times[j] / trials + "ms");
		}
	}
}

/*
Iterative fibonacci with memo table
*/
package Introduction;

public class FibonacciC {
	
	public static int fibonacci(int n) {
		if (n == 0) 
			return 0;
		else if (n == 1) 
			return 1;
		
		int[] memo = new int[n];
		memo[0] = 0;
		memo[1] = 1;
		for (int i = 2; i < n; i++) {
			memo[i] = memo[i - 1] + memo[i - 2];
		}
		return memo[n - 1] + memo[n - 2];
	}

	public static void main(String[] args) {
		int max = 100; // Make this as big as you want! (Though you'll exceed the bounds of a long around 46)
		int trials = 10; // Run code multiple times to compute average time.
		double[] times = new double[max]; // Store times
		
		for (int j = 0; j < trials; j++) { // Run this 10 times to compute
			for (int i = 0; i < max; i++) {
				long start = System.currentTimeMillis();
				System.out.println(fibonacci(i));
				long end = System.currentTimeMillis();
				long time = end - start;
				times[i] += time; 
			}
		}
		
		for (int j = 0; j < max; j++) {
			//System.out.println(j + ": " + times[j] / trials + "ms");
		}
	}
}

/*
Iterative fibonacci without memo table
*/
package Introduction;

public class FibonacciD {
	public static int fibonacci(int n) {
		if (n == 0) return 0;
		int a = 0;
		int b = 1;
		for (int i = 2; i < n; i++) {
			int c = a + b;
			a = b;
			b = c;
		}
		return a + b;
	}
	
	public static void main(String[] args) {
		int max = 100; // Make this as big as you want! (Though you'll exceed the bounds of a long around 46)
		int trials = 10; // Run code multiple times to compute average time.
		double[] times = new double[max]; // Store times
		
		for (int j = 0; j < trials; j++) { // Run this 10 times to compute
			for (int i = 0; i < max; i++) {
				long start = System.currentTimeMillis();
				System.out.println(fibonacci(i));
				long end = System.currentTimeMillis();
				long time = end - start;
				times[i] += time; 
			}
		}
		
		for (int j = 0; j < max; j++) {
			//System.out.println(j + ": " + times[j] / trials + "ms");
		}
	}
}
