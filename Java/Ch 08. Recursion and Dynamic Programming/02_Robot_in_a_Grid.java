/*

Q:
Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns.
The robot can only move in two directions, right and down, but certain cells are "off limits" such that
the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to
the bottom right.

A:

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

class Point {
	public int row, column;
	public Point(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
	
	@Override
	public int hashCode() {
        	return this.toString().hashCode();
    	}
	
	@Override
	public boolean equals(Object o) {
		return ((o instanceof Point) && (((Point) o).row == this.row) && (((Point) o).column == this.column));
	}
}

public class QuestionA {
	
	public static ArrayList<Point> getPath(boolean[][] maze) {
		if (maze == null || maze.length == 0) return null;
		ArrayList<Point> path = new ArrayList<Point>();
		//start from right bottom pixel to find path to origin (0,0)
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
		// 1. If at origin, add (row, col) to path OR
		// 2. If has path from (row, col-1), add (row, col) to path OR
		// 3. If has path from (row-1, col), add (row, col) to path
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

/*
This algorithm is called FLOOD-FILL ALGORITHM (application:SOLVING MAZE), complexity O(rc) for r rows, c columns in matrix
Solving a Maze: 
Given a matrix with some starting point, and some destination with some obstacles in between, 
this algorithm helps to find out the path from source to destination
We should look for a faster way.
Often, we can optimize exponential algorithms by finding duplicate work. What work are we repeating?
If we walk through the algorithm, we'll see that we are visiting squares multiple times. In fact, we visit
each square many, many times. After all, we have rc squares but we're doing O(2 raisedTo r+c) work. If we were
only visiting each square once, we would probably have an algorithm that was O (rc) (unless we were
somehow doing a lot of work during each visit).
How does our current algorithm work? To find a path to (r, c), we look for a path to an adjacent coordinate:
(r-1, c) or (r, c-1). Of course, if one of those squares is off limits, we ignore it. Then, we look
at their adjacent coordinates: (r-2, c), (r-1, c-1), (r-1, c-1), and (r, c -2). The spot (r-1, c-1)
appears twice, which means that we're duplicating effort. Ideally, we should remember that we already
visited (r-1, c-1) so that we don't waste our time.
         
                                            r,c
                  r-1,c                                             r,c-1
      r-2,c                  r-1,c-1                   r-1,c-1                  r,c-2
 r-3,c     r-2,c-1    r-2,c-1       r-1,c-2     r-2,c-1       r-1,c-2    r-1,c-2     r,c-3


notice  (r-1,c-1) repeats twice at level 2
(r-2,c-1) & (r-1,c-2) each repeat thrice at level 3
similarly, (r-2,c-2) & (r-1,c-3) & (r-3,c-1) will repeat 4 times at level 4

This simple change will make our code run substantially faster. The algorithm will now take O(rc) time
because we hit each cell just once.

Hacker rank dynamic programming
https://www.youtube.com/watch?v=P8Xa2BitN3I

*/
package Q8_02_Robot_in_a_Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import CtCILibrary.AssortedMethods;

public class QuestionB {
	public static ArrayList<Point> getPath(boolean[][] maze) {
		if (maze == null || maze.length == 0) return null;
		ArrayList<Point> path = new ArrayList<Point>();
		HashSet<Point> failedPoints = new HashSet<Point>();
		if (getPath(maze, maze.length - 1, maze[0].length - 1, path, failedPoints)) {
			return path;	//returns actual path from src (maze.length - 1, maze[0].length - 1) to dest(0,0)
		}
		return null;
	}
	
	//finds path going back from destination (r,c) to (0,0)
	public static boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path, HashSet<Point> failedPoints) {
		/* If out of bounds or not available, return.*/
		if (col < 0 || row < 0 || !maze[row][col]) {
			return false;
		}
		
		Point p = new Point(row, col);
		
		/* If we've already visited this cell, return. */
		if (failedPoints.contains(p)) { 
			return false;
		}	
		
		boolean isAtOrigin = (row == 0) && (col == 0);
		
		/* If there's a path from the start to my current location, add my location.*/
		if (isAtOrigin || getPath(maze, row, col - 1, path, failedPoints) || getPath(maze, row - 1, col, path, failedPoints)) {
			path.add(p);
			return true;
		}
		
		failedPoints.add(p); // Cache result
		return false;
	}
	
	public static void main(String[] args) {
		int size = 20;
		boolean[][] maze = AssortedMethods.randomBooleanMatrix(size, size, 60);
		AssortedMethods.printMatrix(maze);
		ArrayList<Point> path = getPath(maze);
		if (path != null) {
			System.out.println(path.toString());
		} else {
			System.out.println("No path found.");
		}
	}
}

/*
Dynamic programming approach :
Consider 1x1 grid, then 1x2, then 1x3 grid then ... till 3X3 grid.
Assume each time, assume robot starting from (1,1) and destinations are right corners.
Find in total how many ways can robot reach destination each time.
Generalize the formula

   j
   1 2 3
1  0 1 1
2  1 2 3
3  1 3 6

General formula on noticing:

curr_cell_val = top_cell_val + left_cell_val
i.e 
new_dp[j] = old_dp[j] + dp[j-1]
i.e
dp[j] = dp[j] + dp[j-1]

Now, if there is obstacle marked in a cell, for such cell, mark dp[j]=0
*/

uniquePathsWithObstacles(int[][] obstacles) {
	
	int width = obstacles[0].length;
	int dp[] = new int[width];
	dp[0]=1;
	for(int[] row : obstacles) {
		for(int j=0; j< width; j++) {
			if(row[j]==1) {	//obstacle found
				dp[j]=0;
			} else if(j>0) {
				dp[j]=dp[j]+dp[j-1];
			}
		}
	}
	return dp[j-1];
}
