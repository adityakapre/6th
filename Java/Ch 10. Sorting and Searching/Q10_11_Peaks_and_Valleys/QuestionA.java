/*
Since this problem asks us to sort the array in a particular way, one thing we can try is doing a normal sort
and then "fixing" the array into an alternating sequence of peaks and valleys.
Suboptimal Solution
Imagine we were given an unsorted array and then sort it to become the following:
0 1 4 7 8 9
We now have an ascending list of integers.
How can we rearrange this into a proper alternating sequence of peaks and valleys? Let's walk through it
and try to do that.
• The 0 is okay.
• The 1 is in the wrong place. We can swap it with either the 0 or 4. Let's swap it with the 0.
1 0 4 7 8 9
• The 4 is okay.
• The 7 is in the wrong place. We can swap it with either the 4 or the 8. Let's swap it with the 4.
1 0 7 4 8 9
- Th<> 9 is in the ""ron9 pk,ce. Let's swap \t with \he e.
1 0 7 4 9 8
Observe that there's nothing special about the array having these values. The relative order of the elements
matters, but all sorted arrays will have the same relative order. Therefore, we can take this same approach
on any sorted array.
Before coding, we should clarify the exact algorithm, though.
1. Sort the array in ascending order.
2. Iterate through the elements, starting from index 1 (not 0) and jumping two elements at a time.
3. At each element, swap it with the previous element. Since every three elements appear in the order
small <= medium <= large, swapping these elements will always put medium as a peak: medium
<= small <= large.
This approach will ensure that the peaks are in the right place: indexes 1, 3, 5, and so on. As long as the oddnumbered
elements (the peaks) are bigger than the adjacent elements, then the even-numbered elements
(the valleys) must be smaller than the adjacent elements.
The code to implement this is below.

This algorithm runs in O(n log n) time.
*/
package Q10_11_Peaks_and_Valleys;

import java.util.Arrays;

import CtCILibrary.AssortedMethods;

public class QuestionA {
	public static void sortValleyPeak(int[] array) {
		Arrays.sort(array);
		for (int i = 1; i < array.length; i += 2) {
			swap(array, i - 1, i);
		}
	}
	
	public static void swap(int[] array, int left, int right) {
		int temp = array[left];
		array[left] = array[right];
		array[right] = temp;
	}

	public static void main(String[] args) {
		int[] array = {48, 40, 31, 62, 28, 21, 64, 40, 23, 17};
		System.out.println(AssortedMethods.arrayToString(array));
		sortValleyPeak(array);
		System.out.println(AssortedMethods.arrayToString(array));
		System.out.println(Tester.confirmValleyPeak(array));
	}

}
