/*
Q:
Magic Index: A magic index in an array A[ 1 ... n-1] is defined to be an index such that 
A[i] = i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in
array A.
FOLLOW UP
What if the values are not distinct?  

A:
Immediately, the brute force solution should jump to mind-and there's no shame in mentioning it. We
simply iterate through the array, looking for an element which matches this condition.

Given that the array is sorted, though, it's very likely that we're supposed to use this condition.
We may recognize that this problem sounds a lot like the classic binary search problem. Leveraging the
Pattern Matching approach for generating algorithms, how might we apply binary search here?
In binary search, we find an element k by comparing it to the middle element, x, and determining if k
would land on the left or the right side of x.
Building off this approach, is there a way that we can look at the middle element to determine where a
magic index might be? Let's look at a sample array:

-40  -20  -1  1  2  3  5  7  9  12  13
0     1    2  3  4  5  6  7  8   9  10

When we look at the middle element A [5] = 3, we know that the magic index must be on the right side,
since A[mid] < mid.
Why couldn't the magic index be on the left side? Observe that when we move from i to i-1, the value
at this index must decrease by at least 1, if not more (since the array is sorted and all the elements are
distinct). So, if the middle element is already too small to be a magic index, then when we move to the left,
subtracting k indexes and (at least) k values, all subsequent elements will also be too small.
We continue to apply this recursive algorithm, developing code that looks very much like binary search.
*/
package Q8_03_Magic_Index;

import java.util.Arrays;

import CtCILibrary.AssortedMethods;

public class Question {

	public static int magicSlow(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == i) {
				return i;
			}
		}
		return -1;
	}
	
	public static int magicFast(int[] array, int start, int end) {
		if (end < start) {
			return -1;
		}
		int mid = (start + end) / 2;
		if (array[mid] == mid) {
			return mid;
		} else if (array[mid] > mid){
			return magicFast(array, start, mid - 1);
		} else {
			return magicFast(array, mid + 1, end);
		}
	}
	
	public static int magicFast(int[] array) {
		return magicFast(array, 0, array.length - 1);
	}
	
	/* Creates an array that is distinct and sorted */
	public static int[] getDistinctSortedArray(int size) {
		int[] array = AssortedMethods.randomArray(size, -1 * size, size);
		Arrays.sort(array);
		for (int i = 1; i < array.length; i++) {
			if (array[i] == array[i-1]) {
				array[i]++;
			} else if (array[i] < array[i - 1]) {
				array[i] = array[i-1] + 1;
			}
		}
		return array;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			int size = AssortedMethods.randomIntInRange(5, 20);
			int[] array = getDistinctSortedArray(size);
			int v2 = magicFast(array);
			if (v2 == -1 && magicSlow(array) != -1) {
				int v1 = magicSlow(array);
				System.out.println("Incorrect value: index = -1, actual = " + v1 + " " + i);
				System.out.println(AssortedMethods.arrayToString(array));
				break;
			} else if (v2 > -1 && array[v2] != v2) {
				System.out.println("Incorrect values: index= " + v2 + ", value " + array[v2]);
				System.out.println(AssortedMethods.arrayToString(array));
				break;
			}
		}
	}

}
