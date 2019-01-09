/*

|_|_|_|_|
|_|_|_|_|
|_|_|_|_|
|_|_|_|_|

If we picture this grid, the only way to move to spot (r, c) is by moving to one of the adjacent spots:
(r-1, c) or (r, c-1). So, we need to find a path to either (r-1, c) or (r, c-1).
How do we find a path to those spots? To find a path to (r-1, c) or (r, c-1), we need to move to one
of its adjacent cells. So, we need to find a path to a spot adjacent to (r-1, c), which are coordinates
(r-2, c) and (r-1, c-1). or a spot adjacent to (r, c-1). which are soots (r-1, c-1) and (r, c-2).
Observe that we list the point (r-1, c-1) twice; we'll discuss that issue later.

I Tip: A lot of people use the variable names x and y when dealing with two-dimensional arrays.
This can actually cause some bugs. People tend to think about x as the first coordinate in the
matrix and y as the second coordinate (e.g., matrix[x] [y]). But, this isn't really correct. The
first coordinate is usually thought of as the row number, which is in fact they value (it goes vertically!).
You should write matrix[y] [x]. Or, just make your life easier by using r (row) and c
(column) instead.

So then, to find a path from the origin, we just work backwards like this. Starting from the last cell, we try to
find a path to each of its adjacent cells. The recursive code below implements this algorithm.

This solution is O (2 raised to (r+c) ), since each path has r+c steps and there are two choices we can make at each step.
*/
package Q8_02_Robot_in_a_Grid;

import java.util.ArrayList;

import CtCILibrary.AssortedMethods;

public class QuestionA {
	
	public static ArrayList<Point> getPath(boolean[][] maze) {
		if (maze == null || maze.length == 0) return null;
		ArrayList<Point> path = new ArrayList<Point>();
		if (getPath(maze, maze.length - 1, maze[0].length - 1, path)) {
			return path;
		}
		return null;
	}	
	
	public static boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path) {
		// If out of bounds or not available, return.
		if (col < 0 || row < 0 || !maze[row][col]) {
			return false;
		}
		
		boolean isAtOrigin = (row == 0) && (col == 0);
		
		// If there's a path from the start to my current location, add my location.
		if (isAtOrigin || getPath(maze, row, col - 1, path) || getPath(maze, row - 1, col, path)) { 
			Point p = new Point(row, col);
			path.add(p);
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		int size = 5;
		boolean[][] maze = AssortedMethods.randomBooleanMatrix(size, size, 70);
		
		AssortedMethods.printMatrix(maze);
		
		ArrayList<Point> path = getPath(maze);
		if (path != null) {
			System.out.println(path.toString());
		} else {
			System.out.println("No path found.");
		}
	}

}
