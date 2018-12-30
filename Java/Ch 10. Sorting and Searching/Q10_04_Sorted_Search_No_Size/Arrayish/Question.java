/*
Our first thought here should be binary search. The problem is that binary search requires us knowing the
length of the list, so that we can compare it to the midpoint. We don't have that here.
Could we compute the length? Yes!
We know that elementAt will return -1 when i is too large. We can therefore just try bigger and bigger
values until we exceed the size of the list.
But how much bigger? If we just went through the list linearly-1, then 2, then 3, then 4, and so on-we'd
wind up with a linear time algorithm. We probably want something faster than this. Otherwise, why would
the interviewer have specified the list is sorted?
It's better to back off exponentially. Try 1, then 2, then 4, then 8, then 16, and so on. This ensures that, if the
list has length n, we'll find the length in at most O ( log n) time.
I Why O(log n)? Imagine we start with pointer q at q = 1. At each iteration, this pointer q
doubles, until q is bigger than the length n. How many times can q double in size before it's
bigger than n? Or, in other words, for what value of k does 2k = n?This expression is equal when
k = log n, as this is precisely what log means. Therefore, it will take 0( log n) steps to find
the length.
Once we find the length, we just perform a (mostly) normal binary search. I say "mostly" because we need
to make one small tweak. If the mid point is -1, we need to treat this as a "too big" value and search left. This
is on line 16 below.
There's one more little tweak. Recall that the way we figure out the length is by calling elementAt and
comparing it to -1. If, in the process, the element is bigger than the value x (the one we're searching for),
we'll jump over to the binary search part early.

It turns out that not knowing the length didn't impact the runtime of the search algorithm. We find the
length in O(log n) time and then do the search in 0( log n) time. Ouroverall runtime is O(log n ),just
as it would be in a normal array
*/
package Q10_04_Sorted_Search_No_Size.Arrayish;

public class Question {

	public static int binarySearch(Listy list, int value, int low, int high) {
		int mid;
		
		while (low <= high) {
			mid = (low + high) / 2;
			int middle = list.elementAt(mid);
			if (middle > value || middle == -1) {
				high = mid - 1;
			} else if (middle < value) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;		
	}
	
	public static int search(Listy list, int value) {
		int index = 1;
		while (list.elementAt(index) != -1 && list.elementAt(index) < value) {
			index *= 2;
		}
		return binarySearch(list, value, index / 2, index);
	}
	
	public static void main(String[] args) {
		int[] array = {1, 2, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 16, 18};
		Listy list = new Listy(array);
		for (int a : array) {
			System.out.println(search(list, a));
		}
		System.out.println(search(list, 15));
	}

}
