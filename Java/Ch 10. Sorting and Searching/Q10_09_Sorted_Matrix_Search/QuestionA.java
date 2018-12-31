/*
We can approach this in two ways: a more naive solution that only takes advantage of part of the sorting,
and a more optimal way that takes advantage of both parts of the sorting.
Solution #1: Naive Solution
As a first approach, we can do binary search on every row to find the element. This algorithm will be O(M
log( N)), since there are M rows and it takes 0( log( N)) time to search each one. This is a good approach
to mention to your interviewer before you proceed with generating a better algorithm.
To develop an algorithm, let's start with a simple example.
15 20 40 85
20 35 80 95
30 55 95 105
40 80 100 120
Suppose we are searching for the element 55. How can we identify where it is?
If we look at the start of a row or the start of a column, we can start to deduce the location. If the start of a
column is greater than 55, we know that 55 can't be in that column, since the start of the column is always
the minimum element. Additionally, we know that 55 can't be in any columns on the right, since the first
element of each column must increase in size from left to right. Therefore, if the start of the column is
greater than the element x that we are searching for, we know that we need to move further to the left.
For rows, we use identical logic. If the start of a row is bigger than x, we know we need to move upwards.

Observe that we can also make a similar conclusion by looking at the ends of columns or rows. If the end
of a column or row is less than x, then we know that we must move down (for rows) or to the right (for
columns) to find x. This is because the end is always the maximum element.
We can bring these observations together into a solution.1he observations are the following:
If the start of a column is greater than x, then xis to the left of the column.
If the end of a column is less than x, then x is to the right of the column.
If the start of a row is greater than x, then x is above that row.
If the end of a row is less than x, then x is below that row.
We can begin in any number of places, but let's begin with looking at the starts of columns.
We need to start with the greatest column and work our way to the left. This means that our first element
for comparison is array[0] [ c-1], where c is the number of columns. By comparing the start of columns
to x (which is 55), we'll find that x must be in columns 0, 1, or 2. We will have stopped at array [ 0] [ 2].
This element may not be the end of a row in the full matrix, but it is an end of a row of a submatrix. The
same conditions apply. The value at array[0] [2], which is 40, is less than 55, so we know we can move
downwards.
We now have a submatrix to consider that looks like the following (the gray squares have been eliminated).
We can repeatedly apply these conditions to search for 55. Note that the only conditions we actually use
are conditions 1 and 4.
The code below implements this elimination algorithm.
*/
package Q10_09_Sorted_Matrix_Search;
import CtCILibrary.*;

public class QuestionA {

	public static boolean findElement(int[][] matrix, int elem) {
		int row = 0;
		int col = matrix[0].length - 1; 
		while (row < matrix.length && col >= 0) {
			if (matrix[row][col] == elem) {
				return true;
			} else if (matrix[row][col] > elem) {
				col--;
			} else {
				row++; 
			}
		} 
		return false; 
	}
	
	public static void main(String[] args) {
		int M = 10;
		int N = 5;
		int[][] matrix = new int[M][N];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				matrix[i][j] = 10 * i + j;
			}
		}
		
		AssortedMethods.printMatrix(matrix);
		
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				int v = 10 * i + j;
				System.out.println(v + ": " + findElement(matrix, v));
			}
		}
		
	}

}
