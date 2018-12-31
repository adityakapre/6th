/*
Alternatively, we can apply a solution that more directly looks like binary search. The code is considerably
more complicated, but it applies many of the same learnings.
Solution #2: Binary Search
Let's again look at a simple example.

15 20 70 85
213 3S 80 95
30 55 95 105
40 80 100 120
We want to be able to leverage the sorting property to more efficiently find an element. So, we might ask
ourselves, what does the unique ordering property of this matrix imply about where an element might be
located?
We are told that every row and column is sorted. This means that element a [ i] [ j] will be greater than
the elements in row i between columns O and j - 1 and the elements in column j between rows O and
i - 1.
Or, in other words:
a[i][0] <= a[i][l] <= ... <= a[i][j-1] <= a[i][j]
a[0][j] <= a[l][j] <= ... <= a[i-l][j] <= a[i][j]
Looking at this visually, the dark gray element below is bigger than all the light gray elements.

The light gray elements also have an ordering to them: each is bigger than the elements to the left of it,
as well as the elements above it. So, by transitivity, the dark gray element is bigger than the entire square.

This means that for any rectangle we draw in the matrix, the bottom right hand corner will always be the
biggest.
Likewise, the top left hand corner will always be the smallest. The colors below indicate what we know
about the ordering of elements (light gray< dark gray < black):
Let's return to the original problem: suppose we were searching for the value 85. If we look along the diagonal,
we'll find the elements 35 and 95. What does this tell us about the location of 85?

85 can't be in the black area, since 95 is in the upper left hand corner and is therefore the smallest element
in that square.
85 can't be in the light gray area either, since 35 is in the lower right hand corner of that square.
85 must be in one of the two white areas.
So, we partition our grid into four quadrants and recursively search the lower left quadrant and the upper
right quadrant. These, too, will get divided into quadrants and searched.
Observe that since the diagonal is sorted, we can efficiently search it using binary search.

If you read all this code and thought, "there's no way I could do all this in an interview!" you're probably
right. You couldn't. But, your performance on any problem is evaluated compared to other candidates on
the same problem. So while you couldn't implement all this, neither could they. You are at no disadvantage
when you get a tricky problem like this.
You help yourself out a bit by separating code out into other methods. For example, by pulling
parti tionAndSearch out into its own method, you will have an easier time outlining key aspects of the
code. You can then come back to fill in the body for partitionAndSearch if you have time.
*/
package Q10_09_Sorted_Matrix_Search;
import CtCILibrary.*;

public class QuestionB {
	
	public static Coordinate partitionAndSearch(int[][] matrix, Coordinate origin, Coordinate dest, Coordinate pivot, int x) {
		Coordinate lowerLeftOrigin = new Coordinate(pivot.row, origin.column);
		Coordinate lowerLeftDest = new Coordinate(dest.row, pivot.column - 1);
		Coordinate upperRightOrigin = new Coordinate(origin.row, pivot.column);
		Coordinate upperRightDest = new Coordinate(pivot.row - 1, dest.column);
		
		Coordinate lowerLeft = findElement(matrix, lowerLeftOrigin, lowerLeftDest, x);
		if (lowerLeft == null) {
			return findElement(matrix, upperRightOrigin, upperRightDest, x);
		}
		return lowerLeft;
	}
	
	public static Coordinate findElement(int[][] matrix, Coordinate origin, Coordinate dest, int x) {
		if (!origin.inbounds(matrix) || !dest.inbounds(matrix)) {
			return null;
		}
		if (matrix[origin.row][origin.column] == x) {
			return origin;
		} else if (!origin.isBefore(dest)) {
			return null;
		}
		
		/* Set start to start of diagonal and end to the end of the diagonal. Since
		 * the grid may not be square, the end of the diagonal may not equal dest.
		 */
		Coordinate start = (Coordinate) origin.clone();
		int diagDist = Math.min(dest.row - origin.row, dest.column - origin.column);
		Coordinate end = new Coordinate(start.row + diagDist, start.column + diagDist);
		Coordinate p = new Coordinate(0, 0);
		
		/* Do binary search on the diagonal, looking for the first element greater than x */
		while (start.isBefore(end)) {
			p.setToAverage(start, end);
			if (x > matrix[p.row][p.column]) {
				start.row = p.row + 1;
				start.column = p.column + 1;
			} else {
				end.row = p.row - 1;
				end.column = p.column - 1;
			}
		}
		
		/* Split the grid into quadrants. Search the bottom left and the top right. */ 
		return partitionAndSearch(matrix, origin, dest, start, x);
	}
	
	public static Coordinate findElement(int[][] matrix, int x) {
		Coordinate origin = new Coordinate(0, 0);
		Coordinate dest = new Coordinate(matrix.length - 1, matrix[0].length - 1);
		return findElement(matrix, origin, dest, x);
	}
	
	public static void main(String[] args) {
		int[][] matrix = {{15, 30,  50,  70,  73}, 
				 	 	  {35, 40, 100, 102, 120},
				 	 	  {36, 42, 105, 110, 125},
				 	 	  {46, 51, 106, 111, 130},
				 	 	  {48, 55, 109, 140, 150}};
	
		AssortedMethods.printMatrix(matrix);
		int m = matrix.length;
		int n = matrix[0].length;
		
		int count = 0;
		int littleOverTheMax = matrix[m - 1][n - 1] + 10;
		for (int i = 0; i < littleOverTheMax; i++) {
			Coordinate c = findElement(matrix, i);
			if (c != null) {
				System.out.println(i + ": (" + c.row + ", " + c.column + ")");
				count++;
			}
		}
		System.out.println("Found " + count + " unique elements.");
	}

}
