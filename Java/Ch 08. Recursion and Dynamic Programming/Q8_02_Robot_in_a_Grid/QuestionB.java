/*
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

This simple change will make our code run substantially faster. The algorithm will now take O(XY) time
because we hit each cell just once.
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
