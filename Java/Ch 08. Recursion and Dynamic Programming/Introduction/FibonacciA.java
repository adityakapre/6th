/*
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
	
	/**
	 * @param args
	 */
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
