/*
One thing we might notice when we look at this code is that a call to minProduct on an even number is
much faster than one on an odd number. For example, if we call min Product(30, 35), then we'll just
do minProduct(15, 35) and double the result. However, if we do min Product(31, 35), then we'll
need to call minProduct(15, 35) and minProduct(16, 35).
This is unnecessary. Instead, we can do:
minProduct(31, 35) = 2 * minProduct(15, 35) + 35
After all, since 31 = 2*15+1, then 31x35 = 2*15*35+35.
The logic in this final solution is that, on even numbers, we just divide smaller by 2 and double the result
of the recursive call. On odd numbers, we do the same, but then we also add b igger to this result.
In doing so, we have an unexpected "win". Our minProduct function just recurses straight downwards,
with increasingly small numbers each time. It will never repeat the same call, so there's no need to cache
any information.

This algorithm will run in O(log s) time, wheres is the smaller of the two numbers.

e.g 15*30 i.e smaller = 15, larger=30
inital call (15, 30) 

Stack wind (i/p params)   Stack unwind (returns to above call)
(15, 30)			210+210+30=450
(7, 30)				90+90+30=210
(3, 30)				30+30+30=90
(1, 30)				30


e.g 16*30 i.e smaller = 16, larger=30
inital call (16, 30) 

Stack wind (i/p params)   Stack unwind (returns to above call)
(16, 30)			240+240=480
(8, 30)				120+120=240
(4, 30)				60+60=120
(2, 30)				30+30=60
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
		
		int s = smaller >> 1; //divide by 2
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
