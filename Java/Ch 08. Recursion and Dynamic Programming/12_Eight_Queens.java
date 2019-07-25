/*
Q:
Eight Queens:Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
so that none of them share the same row, column, or diagonal. In this case, "diagonal" means all
diagonals, not just the two that bisect the board.

A:
We have eight queens which must be lined up on an 8x8 chess board such that none share the same row,
column or diagonal. So, we know that each row and column (and diagonal) must be used exactly once .

|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|
|_|_|_|_|_|_|_|_|

Picture the queen that is placed last, which we'll assume is on row 8. (This is an okay assumption to make
since the ordering of placing the queens is irrelevant.) On which cell in row 8 is this queen? There are eight
possibilities, one for each column.

So if we want to know all the valid ways of arranging 8 queens on an 8x8 chess board, it would be:
ways to arrange 8 queens on an 8x8 board=
ways to arrange 8 queens on an 8x8 board with queen at (7, 0) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 1) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 2) + 
ways to arrange 8 queens on an 8x8 board with queen at (7, 3) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 4) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 5) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 6) +
ways to arrange 8 queens on an 8x8 board with queen at (7, 7)

We can compute each one of these using a very similar approach:
ways to arrange 8 queens on an 8x8 board with queen at (7, 3)
ways to with queens at (7, 3) and (6, 0) +
ways to with queens at (7, 3) and (6, 1) +
ways to with queens at (7, 3) and (6, 2) +
ways to with queens at (7, 3) and (6, 4) +
ways to with queens at (7, 3) and (6, 5) +
ways to with queens at (7, 3) and (6, 6) +
ways to with queens at (7, 3) and (6, 7)
Note that we don't need to consider combinations with queens at (7, 3) and (6,3) since it is a 
violation of the requirement that every queen is in its own row, column and diagonal.

Observe that since each row can only have one queen, we don't need to store our board as a full 8x8 matrix.
We only need a single array where column[r] = c indicates that row r has a queen at column c.

According to below code

1st queen placed at (0,0) i.e columns[0] = 0;
2nd queen placed at (1,2) i.e columns[1] = 2;
3rd queen placed at (2,4) i.e columns[2] = 4;
etc
*/
package Q8_12_Eight_Queens;

import java.util.ArrayList;

public class Question {
	public static int GRID_SIZE = 8;
	
	public static void main(String[] args) {
		ArrayList<Integer[]> results = new ArrayList<Integer[]>();
		Integer[] columns = new Integer[GRID_SIZE];
		clear(columns);
		placeQueens(0, columns, results);
		printBoards(results);
		System.out.println(results.size());
	}
	
	//Start here ... Called with row=0
	//1st queen placed at (0,0) next at (1,2) etc
	public static void placeQueens(int row, Integer[] columns, ArrayList<Integer[]> results) {
		if (row == GRID_SIZE) { // Found valid placement
			results.add(columns.clone()); 
		} else {
			//check each col for i/p "row"
			for (int col = 0; col < GRID_SIZE; col++) {			
				if (checkValid(columns, row, col)) {
					columns[row] = col;	// Place queen for "row" at "col"
					placeQueens(row + 1, columns, results);	
				}		
			}
		}
	}
	
	/*  Check if (row1, column1) is a valid spot for 
	 *  a queen by checking if there
	 *  is a queen in the same column or diagonal. 
	 *  We don't need to check it for queens
	 *  in the same row because the calling placeQueen 
	 *  only attempts to place one queen at
	 *  a time. We know this row is empty. 
	 */
	public static boolean checkValid(Integer[] columns, int row1, int column1) {
		//row2 < row1 traverses all rows in which queens are placed till now placing queen starts from row 0
		for (int row2 = 0; row2 < row1; row2++) {    
			//columns[row2] is where queen was placed in row2
			int column2 = columns[row2]; 
		
			/* Check if rows have a queen in the same column */
			if (column1 == column2) { 
				return false;
			}
			
			/* Check diagonals: if the distance between the columns equals the distance
			 * between the rows, then they’re in the same diagonal. */
			int columnDistance = Math.abs(column2 - column1); 
			// row1 > row2, so no need to use absolute value
			int rowDistance = row1 - row2; 
		    	if (columnDistance == rowDistance) {
		    		return false;
		    	}
		}
		return true;
	}
	
	public static void clear(Integer[] columns) {
		for (int i = 0; i < GRID_SIZE; i++) {
			columns[i] = -1;
		}
	}
	
	public static void printBoard(Integer[] columns) {
        drawLine();
        for(int i = 0; i < GRID_SIZE; i++){
			System.out.print("|");
			for (int j = 0; j < GRID_SIZE; j++){
			    if (columns[i] == j) {
			    	System.out.print("Q|");
			    } else {
			    	System.out.print(" |");
			    }
			}
            System.out.print("\n");
            drawLine();
		}
		System.out.println("");
	}

    private static void drawLine() {
        StringBuilder line = new StringBuilder();
        for (int i=0;i<GRID_SIZE*2+1;i++)
            line.append('-');
        System.out.println(line.toString());
    }

    public static void printBoards(ArrayList<Integer[]> boards) {
		for (int i = 0; i < boards.size(); i++) {
			Integer[] board = boards.get(i);
			printBoard(board);
		}
    }
	
}
