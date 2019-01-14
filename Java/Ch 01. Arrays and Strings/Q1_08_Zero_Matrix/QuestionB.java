/*
To make this somewhat more space efficient we could use a bit vector instead of a boolean array. It would
still be O(N) space.
We can reduce the space to 0(1) by using the first row as a replacement for the row array and the first
column as a replacement for the column array. This works as follows:

1. Check if the first row and first column have any zeros, and set variables rowHasZero and
columnHasZero. (We'll nullify the first row and first column later, if necessary.)
2. Iterate through the rest of the matrix, setting matrix[r][0] and matrix[0][c] to zero whenever
there's a zero in matrix[r][c].
3. Iterate through rest of matrix, nullifying row r if there's a zero in matrix[r][0].
4. Iterate through rest of matrix, nullifying column c if there's a zero in matrix[0][c].
5. Nullify the first row and first column, if necessary (based on values from Step 1 ).

This code has a lot of "do this for the rows, then the equivalent action for the column:' In an interview, you
could abbreviate this code by adding comments and TODOs that explain that the next chunk of code looks
the same as the earlier code, but using rows. This would allow you to focus on the most important parts of
the algorithm.
*/
package Q1_08_Zero_Matrix;

import CtCILibrary.AssortedMethods;

public class QuestionB {
	
	public static void setZeros(int[][] matrix) {
		boolean rowHasZero = false;
		boolean colHasZero = false;		
		
		// Check if first row has a zero
		for (int c = 0; c < matrix[0].length; c++) {
			if (matrix[0][c] == 0) {
				rowHasZero = true;
				break;
			}
		}		
		
		// Check if first column has a zero
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][0] == 0) {
				colHasZero = true;
				break;
			}
		}		
		
		// Check for zeros in the rest of the array
		for (int r = 1; r < matrix.length; r++) {
			for (int c = 1; c < matrix[0].length; c++) {
				if (matrix[r][c] == 0) {
					matrix[r][0] = 0;
					matrix[0][c] = 0;
		 		}
			}
		}		
		
		// Nullify rows based on values in first column
		for (int r = 1; r < matrix.length; r++) {
			if (matrix[r][0] == 0) {
				nullifyRow(matrix, r);
			}
		}		
		
		// Nullify columns based on values in first row
		for (int c = 1; c < matrix[0].length; c++) {
			if (matrix[0][c] == 0) {
				nullifyColumn(matrix, c);
			}
		}	
		
		// Nullify first row
		if (rowHasZero) {
			nullifyRow(matrix, 0);
		}
		
		// Nullify first column
		if (colHasZero) {
			nullifyColumn(matrix, 0);
		}
	}
	
	public static void nullifyRow(int[][] matrix, int row) {
		for (int c = 0; c < matrix[0].length; c++) {
			matrix[row][c] = 0;
		}		
	}

	public static void nullifyColumn(int[][] matrix, int col) {
		for (int r = 0; r < matrix.length; r++) {
			matrix[r][col] = 0;
		}		
	}		
	
	public static boolean matricesAreEqual(int[][] m1, int[][] m2) {
		if (m1.length != m2.length || m1[0].length != m2[0].length) {
			return false;
		}
		
		for (int k = 0; k < m1.length; k++) {
			for (int j = 0; j < m1[0].length; j++) {
				if (m1[k][j] != m2[k][j]) {
					return false;
				}
			}
		}	
		return true;
	}
	
	public static int[][] cloneMatrix(int[][] matrix) {
		int[][] c = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				c[i][j] = matrix[i][j];
			}
		}
		return c;
	}
	
	public static void main(String[] args) {
		int nrows = 10;
		int ncols = 15;
		int[][] matrix = AssortedMethods.randomMatrix(nrows, ncols, -10, 10);		

		AssortedMethods.printMatrix(matrix);
		
		setZeros(matrix);
		
		System.out.println();
		
		AssortedMethods.printMatrix(matrix);
	}
}
