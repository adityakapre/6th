/*
Q:
Recursive Multiply: Write a recursive function to multiply two positive integers without using
the * operator (or / operator). You can use addition, subtraction, and bit shifting, but you should
minimize the number of those operations.


Solution ::
We can think about multiplying 8x7 as doing 8+8+8+8+8+8+8 (or adding 7 eight times). We can also think
about it as the number of squares in an 8x7 grid.

A:
Solution #1
How would we count the number of squares in this grid? We could just count each cell. That's pretty slow,
though.
Alternatively, we could count half the squares and then double it (by adding this count to itself). To count
half the squares, we repeat the same process.
Of course, this "doubling" only works if the number is in fact even. When it's not even, we need to do the
counting/summing from scratch.
*/
package Q8_05_Recursive_Multiply;

public class QuestionA {

	public static int counter = 0;
	
	public static int sum(int x, int y) {
		counter++;
		return x + y;
	}	
	
	public static int minProductHelper(int smaller, int bigger) {
		if (smaller == 0) { // 0 x bigger = 0
			return 0;
		} else if (smaller == 1) { // 1 x bigger = bigger
			return bigger;
		}
		
		/* Compute half. If uneven, compute other half. If even,
		 * double it. */
		int s = smaller >> 1; // Divide by 2
		int side1 = minProductHelper(s, bigger);
		int side2 = side1;
		if (smaller % 2 == 1) {
			counter++;
			side2 = minProductHelper(smaller - s, bigger);
		}
		counter++;
		return side1 + side2;
	}	
	
	public static int minProduct(int a, int b) {
		int bigger = a < b ? b : a;
		int smaller = a < b ? a : b;
		return minProductHelper(smaller, bigger);
	}
	
	public static void main(String[] args) {
		int a = 13494;
		int b = 22323;
		int product = a * b;
		int minProduct = minProduct(a, b);
		if (product == minProduct) {
			System.out.println("Success: " + a + " * " + b + " = " + product);
		} else {
			System.out.println("Failure: " + a + " * " + b + " = " + product + " instead of " + minProduct);
		}
		System.out.println("Adds: " + counter);
	}
}
/*
Solution #2
If we observe how the recursion operates, we'll notice that we have duplicated work. Consider this example:
minProduct(17, 23)
	minProduct(8, 23)
		minProduct(4, 23) * 2
			...
	+ minProduct(9, 23)
		minProduct(4, 23)
			...
	+ minProduct(5, 23)
The second call to min Product (4, 23) is unaware of the prior call, and so it repeats the same work. We
should cache these results.
*/
package Q8_05_Recursive_Multiply;

public class QuestionB {

	public static int counter = 0;
	
	public static int sum(int x, int y) {
		counter += 1;
		return x + y;
	}
	
	public static int minProduct(int smaller, int bigger, int[] memo) {
		if (smaller == 0) {
			return 0;
		} else if (smaller == 1) {
			return bigger;
		} else if (memo[smaller] > 0) {
			return memo[smaller];
		}
			
		/* Compute half. If uneven, compute other half. If even,
		 * double it. */
		int s = smaller >> 1; // Divide by 2
		int side1 = minProduct(s, bigger, memo); // Compute half
		int side2 = side1;
		if (smaller % 2 == 1) {
			counter++;
			side2 = minProduct(smaller - s, bigger, memo);
		}
		
		/* Sum and cache.*/
		counter++;
		memo[smaller] = side1 + side2; 
		return memo[smaller];
	}
	
	public static int minProduct(int a, int b) {
		int bigger = a < b ? b : a;
		int smaller = a < b ? a : b;
		
		int memo[] = new int[sum(smaller, 1)];
		return minProduct(smaller, bigger, memo);
	}
	
	public static void main(String[] args) {
		int a = 13494;
		int b = 22323;
		int product = a * b;
		int minProduct = minProduct(a, b);
		if (product == minProduct) {
			System.out.println("Success: " + a + " * " + b + " = " + product);
		} else {
			System.out.println("Failure: " + a + " * " + b + " = " + product + " instead of " + minProduct);
		}
		System.out.println("Adds: " + counter);
	}
}
