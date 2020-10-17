/*
https://www.geeksforgeeks.org/trapping-rain-water/

Water trapped at any element i = min(max_left, max_right) – arr[i]
where arr[i] contains height of ith bar

    |
|   |
| | |
|_|_|

Above will store water = 7  (left dominant as its smaller of leftMax and rightMax)

|
|   |
| | | 
|_|_|

Again, above will store water = 7  (right dominant as its smaller of leftMax and rightMax)


Algorithm: 
1. Traverse the array from start to end.
2. For every element, traverse the array from start to that index and find the maximum height (a) 
   and traverse the array from the current index to end and find the maximum height (b).
3. The amount of water that will be stored in this column is min(a,b) – array[i], 
   add this value to total amount of water stored
4.Print the total amount of water stored.

Java program to find maximum amount of water that can be trapped within given set of bars. 
Output:
Maximum water that can be accumulated is 6
Time Complexity: O(n)
Auxiliary Space: O(n)
*/
class Test { 
	static int arr[] = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}; 
	
	// Method for maximum amount of water 
	static int findWater(int n) { 
		// left[i] contains height of tallest bar to the 
		// left of i'th bar including itself 
		int[] leftMax = new int[n]; 
	
		// Right [i] contains height of tallest bar to 
		// the right of ith bar including itself 
		int[] rightMax = new int[n]; 
	
		// Initialize result 
		int water = 0; 
	
		// Fill left array 
		leftMax[0] = arr[0]; 
		for (int i = 1; i < n; i++) 
			leftMax[i] = Math.max(leftMax[i-1], arr[i]); 
	
		// Fill right array 
		rightMax[n-1] = arr[n-1]; 
		for (int i = n-2; i >= 0; i--) 
			rightMax[i] = Math.max(rightMax[i+1], arr[i]); 
	
		// Calculate the accumulated water element by element 
		// consider the amount of water on i'th bar, the 
		// amount of water accumulated on this particular 
		// bar will be equal to min(leftMax[i], rightMax[i]) - arr[i] . 
		for (int i = 0; i < n; i++) 
			water += Math.min(leftMax[i],rightMax[i]) - arr[i]; 
	
		return water; 
	} 
	
	// Driver method to test the above function 
	public static void main(String[] args) { 
		
		System.out.println("Maximum water that can be accumulated is " + 
										findWater(arr.length)); 
	} 
} 
