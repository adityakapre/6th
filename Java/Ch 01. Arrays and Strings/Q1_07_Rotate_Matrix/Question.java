/*
Because we're rotating the matrix by 90 degrees, the easiest way to do this is to implement the rotation in
layers. We perform a circular rotation on each layer, moving the top edge to the right edge, the right edge
to the bottom edge, the bottom edge to the left edge, and the left edge to the top edge.

How do we perform this four-way edge swap? One option is to copy the top edge to an array, and then
move the left to the top, the bottom to the left, and so on. This requires O(N) memory, which is actually
unnecessary.

A better way to do this is to implement the swap index by index. In this case, we do the following:
1 for i = 0 to n
2 	temp= top[i];
3 	top[i] = left[i]
4 	left[i] = bottom[i]
5 	bottom[i] = right[i]
6 	right[i] = temp
We perform such a swap on each layer, starting from the outermost layer and working our way inwards.
(Alternatively, we could start from the inner layer and work outwards.)

This algorithm is O (N sq.), which is the best we can do since any algorithm must touch all N sq. elements.
*/
package Q1_07_Rotate_Matrix;

import CtCILibrary.*;

public class Question {

	public static boolean rotate(int[][] matrix) {
		if (matrix.length == 0 || matrix.length != matrix[0].length) return false; // Not a square
		int n = matrix.length;
		
		for (int layer = 0; layer < n / 2; layer++) {
			int first = layer;
			int last = n - 1 - layer;
			for(int i = first; i < last; i++) {
				int offset = i - first;
				int top = matrix[first][i]; // save top

				// left -> top
				matrix[first][i] = matrix[last-offset][first]; 			

				// bottom -> left
				matrix[last-offset][first] = matrix[last][last - offset]; 

				// right -> bottom
				matrix[last][last - offset] = matrix[i][last]; 

				// top -> right
				matrix[i][last] = top; // right <- saved top
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		int[][] matrix = AssortedMethods.randomMatrix(3, 3, 0, 9);
		AssortedMethods.printMatrix(matrix);
		rotate(matrix);
		System.out.println();
		AssortedMethods.printMatrix(matrix);
	}

}
