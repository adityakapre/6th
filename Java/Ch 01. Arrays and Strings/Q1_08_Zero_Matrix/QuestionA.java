/*
At first glance, this problem seems easy: just iterate through the matrix and every time we see a cell with
value zero, set its row and column to 0. There's one problem with that solution though: when we come
across other cells in that row or column, we'll see the zeros and change their row and column to zero. Pretty
soon, our entire matrix will be set to zeros.

One way around this is to keep a second matrix which flags the zero locations. We would then do a second
pass through the matrix to set the zeros. This would take O(MN) space.
Do we really need O(MN) space? No. Since we're going to set the entire row and column to zero, we don't
need to track that it was exactly cell[2][4] (row 2, column 4). We only need to know that row 2 has a
zero somewhere, and column 4 has a zero somewhere. We'll set the entire row and column to zero anyway,
so why would we care to keep track of the exact location of the zero?
The code below implements this algorithm. We use two arrays to keep track of all the rows with zeros and all
the columns with zeros. We then nullify rows and columns based on the values in these arrays.
*/
package Q1_08_Zero_Matrix;

import CtCILibrary.AssortedMethods;

public class QuestionA {
	
	public static void setZeros(int[][] matrix) {
		boolean[] row = new boolean[matrix.length];	
		boolean[] column = new boolean[matrix[0].length];

		// Store the row and column index with value 0
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length;j++) {
				if (matrix[i][j] == 0) {
					row[i] = true; 
					column[j] = true;
		 		}
			}
		}
		
		// Nullify rows
		for (int r = 0; r < row.length; r++) {
			if (row[r]) {
				nullifyRow(matrix, r);
			}
		}
		
		// Nullify columns
		for (int j = 0; j < column.length; j++) {
			if (column[j]) {
				nullifyColumn(matrix, j);
			}
		}
	}
	
	public static void nullifyRow(int[][] matrix, int row) {
		for (int c = 0; c < matrix[0].length; c++) {  // matrix[0].length will give no of columns in a row to be zeroed
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
