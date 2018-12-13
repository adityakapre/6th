/*
If the elements are not distinct, then this algorithm fails. Consider the following array:

-10  -5  2  2  2  3  4  7  9  12  13
 0   1   2  3  4  5  6  7  8  9   10

When we see that A [mid] < mid, we cannot conclude which side the magic index is on. It could be on
the right side, as before. Or, it could be on the left side (as it, in fact, is).
Could it be anywhere on the left side? Not exactly. Since A[ 5] = 3, we know that A[ 4] couldn't be a magic
index. A[ 4] would need to be 4 to be the magic index, but A[ 4] must be less than or equal to A[ 5].
In fact, when we see that A[ 5] = 3, we'll need to recursively search the right side as before. But, to search
the left side, we can skip a bunch of elements and only recursively search elements A [ 0] through A [ 3].
A [ 3] is the first element that could be a magic index.
The general pattern is that we compare mid Index and midValue for equality first. Then, if they are not
equal, we recursively search the left and right sides as follows:
• Left side: search indices start through Math. min (midlndex - 1, midValue ).
Right side: search indices Math. max(midlndex + 1, midValue) through end.

Note that in the above code, if the elements are all distinct, the method operates almost identically to the
first solution.
*/

package Q8_03_Magic_Index;

import java.util.Arrays;

import CtCILibrary.AssortedMethods;

public class QuestionB {

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
		int midIndex = (start + end) / 2;
		int midValue = array[midIndex];
		if (midValue == midIndex) {
			return midIndex;
		}
		/* Search left */
		int leftIndex = Math.min(midIndex - 1, midValue);
		int left = magicFast(array, start, leftIndex);
		if (left >= 0) {
			return left;
		}
		
		/* Search right */
		int rightIndex = Math.max(midIndex + 1, midValue);
		int right = magicFast(array, rightIndex, end);
		
		return right;
	}
	
	public static int magicFast(int[] array) {
		return magicFast(array, 0, array.length - 1);
	}
	
	/* Creates an array that is sorted */
	public static int[] getSortedArray(int size) {
		int[] array = AssortedMethods.randomArray(size, -1 * size, size);
		Arrays.sort(array);
		return array;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			int size = AssortedMethods.randomIntInRange(5, 20);
			int[] array = getSortedArray(size);
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
