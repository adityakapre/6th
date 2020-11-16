/*
Q:
Recursive Multiply: Write a recursive function to multiply two positive integers without using
the * operator (or / operator). You can use addition, subtraction, and bit shifting, but you should
minimize the number of those operations.

FINAL ANSWER:

One thing we might notice when we look at this code is that a call to minProduct on an even number is
much faster than one on an odd number. For example, if we call min Product(30, 35), then we'll just
do minProduct(15, 35) and double the result. However, if we do min Product(31, 35), then we'll
need to call minProduct(15, 35) and minProduct(16, 35).
This is unnecessary. Instead, we can do:

minProduct(31, 35) = 2 * minProduct(15, 35) + 35

After all, since 31 = 2*15+1, then 31x35 = 2*15*35+35.
The logic in this final solution is that, on even numbers, we just divide smaller by 2 and double the result
of the recursive call. 
On odd numbers, we do the same, but then we also add bigger to this result.
In doing so, we have an unexpected "win". Our minProduct function just recurses straight downwards,
with increasingly small numbers each time. It will never repeat the same call, 
*** so there's NO NEED TO CACHE any information.***
This algorithm will run in O(log s) time, where s is the smaller of the two numbers.
e.g 15*30 i.e smaller = 15, larger=30
inital call (15, 30) 
Stack wind (i/p params)   Stack unwind (returns to above halfProd+halfProd+bigger)	Description (divide by 2 smaller)
(15, 30) 			210+210+30=450						((7*2+1), 30)
(7, 30)				90+90+30=210						((3*2+1), 30)
(3, 30)				30+30+30=90						((1*2+1), 30)
(1, 30)				30							((0*2+1), 30)
e.g 16*30 i.e smaller = 16, larger=30
inital call (16, 30) 
Stack wind (i/p params)   Stack unwind (returns to above call halfProd+halfProd)	Description
(16, 30)			240+240=480						(8*2, 30)						
(8, 30)				120+120=240						(4*2, 30)
(4, 30)				60+60=120						(2*2, 30)
(2, 30)				30+30=60						(1*2, 30)
(1, 30)				30
*/
package Q8_05_Recursive_Multiply;

public class QuestionC {

	public static int counter = 0;
	
	public static int minProductHelper(int smaller, int bigger) {
		if (smaller == 0) {
			return 0;
		} else if (smaller == 1) {
			return bigger;
		} 
		
		int s = smaller/2;	// smaller >> 1; divide by 2 - right shift once
		int halfProd = minProductHelper(s, bigger);
		
		if (smaller % 2 == 0) {
			counter++;
			return halfProd + halfProd;
		} else {
			counter+=2;
			return halfProd + halfProd + bigger;
		}
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

public class QuestionD {
	public static int counter = 0;
	
	/* This is an algorithm called the peasant algorithm. 
	 * https://en.wikipedia.org/wiki/Multiplication_algorithm#Peasant_or_binary_multiplication 
	 */
	public static int minProduct(int a, int b) {
		if (a < b) return minProduct(b, a);
		int value = 0;
		while (a > 0) {
			counter++;
			if ((a % 10) % 2 == 1) {
				value += b;
			}
			a >>= 1;
			b <<= 1;
		}	
		return value;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				int prod1 = minProduct(i, j);
				int prod2 = i * j;
				if (prod1 != prod2) {
					System.out.println("ERROR: " + i + " * " + j + " = " + prod2 + ", not " + prod1);
				}
			}
		}
	}
}

/*
Solution ::
We can think about multiplying 8x7 as doing 8+8+8+8+8+8+8 (or adding 7 eight times). 
i.e ADD BIGGER TO ITSELF FOR N NUMBER OF TIMES WHERE N=SMALLER
We can also think about it as the number of squares in an 8x7 grid.

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
