/*
Optimal Solution
int right) {
To optimize past the prior solution, we need to cut out the sorting step. The algorithm must operate on an
unsorted array.
Let's revisit an example.
9 1 0 4 8 7
For each element, we'll look at the adjacent elements. Let's imagine some sequences. We'll just use the
numbers 0, 1 and 2. The specific values don't matter.
0 1 2
0 2 1  // peak
1 0 2
1 2 0  // peak
2 1 0

2 0 1
If the center element needs to be a peak, then two of those sequences work. Can we fix the other ones to
make the center element a peak?
Yes. We can fix this sequence by swapping the center element with the largest adjacent element.
0 1 2 -> 0 2 1
0 2 1   //peak
1 0 2 -> 1 2 0 
1 2 0   //peak
2 1 0 -> 1 2 0
2 0 1 -> 0 2 1

As we noted before, if we make sure the peaks are in the right place then we know the valleys are in the
right place.
We should be a little cautious here. Is it possible that one of these swaps could "break" an earlier
part of the sequence that we'd already processed? This is a good thing to worry about, but it's
not an issue here. If we're swapping middle with left, then left is currently a valley. Middle
is smaller than left, so we're putting an even smaller element as a valley. Nothing will break.
All is good!
The code to implement this is below.
This algorithm takes O ( n) time.
*/
package Q10_11_Peaks_and_Valleys;

import CtCILibrary.AssortedMethods;

public class QuestionB {
	public static void swap(int[] array, int left, int right) {
		int temp = array[left];
		array[left] = array[right];
		array[right] = temp;
	}
	
	public static void sortValleyPeak(int[] array) {
		for (int i = 1; i < array.length; i += 2) {
			int biggestIndex = maxIndex(array, i - 1, i, i + 1);
			if (i != biggestIndex) {
				swap(array, i, biggestIndex);
			}
		}
	}	
	
	public static int maxIndex(int[] array, int a, int b, int c) {
		int len = array.length;
		int aValue = a >= 0 && a < len ? array[a] : Integer.MIN_VALUE; 
		int bValue = b >= 0 && b < len ? array[b] : Integer.MIN_VALUE; 
		int cValue = c >= 0 && c < len ? array[c] : Integer.MIN_VALUE; 
		
		int max = Math.max(aValue, Math.max(bValue, cValue));
		
		if (aValue == max) {
			return a;
		} else if (bValue == max) {
		 	return b;
		} else {
			return c;
		}
	}

	public static void main(String[] args) {
		int[] array = {48, 40, 31, 62, 28, 21, 64, 40, 23, 17};
		System.out.println(AssortedMethods.arrayToString(array));
		sortValleyPeak(array);
		System.out.println(AssortedMethods.arrayToString(array));
		System.out.println(Tester.confirmValleyPeak(array));
	}

}
