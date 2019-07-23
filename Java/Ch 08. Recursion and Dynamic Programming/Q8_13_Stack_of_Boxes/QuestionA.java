/*
Q:
Stack of Boxes: You have a stack of n boxes, with widths wi, heights hi, and depths di . The boxes
cannot be rotated and can only be stacked on top of one another if each box in the stack is strictly
larger than the box above it in width, height, and depth. Implement a method to compute the
height of the tallest possible stack. The height of a stack is the sum of the heights of each box.

A:
Solution#1
Imagine we had the following boxes: b1 , b2 , ••• , bn. The biggest stack that we can build with all the
boxes equals the max of (biggest stack with bottom b1, biggest stack with bottom b2 , ••• , biggest stack
with bottom bn). That is, if we experimented with each box as a bottom and built the biggest stack possible
with each, we would find the biggest stack possible.
But, how would we find the biggest stack with a particular bottom? Essentially the same way. We experiment
with different boxes for the second level, and so on for each level.
Of course, we only experiment with valid boxes. If b5 is bigger than b1, then there's no point in trying to
build a stack that looks like {b1 , b 5 , ••• }. We already know b1 can't be below b5•
We can perform a small optimization here. The requirements of this problem stipulate that the lower boxes
must be strictly greater than the higher boxes in all dimensions. Therefore, if we sort (descending order) the
boxes on a dimension-any dimension-then we know we don't have to look backwards in the list. The
box b1 cannot be on top of box b5, since its height (or whatever dimension we sorted on) is greater than
b5's height.
*/
package Q8_13_Stack_of_Boxes;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionA {	
	public static int createStack(ArrayList<Box> boxes) {
		//sort boxes in descending order
		Collections.sort(boxes, new BoxComparator());
		int maxHeight = 0;
		//create stack taking 1 then 2 then 3 then 4 etc boxes
		//for each stack find maxHeight
		for (int i = 0; i < boxes.size(); i++) {
			int height = createStack(boxes, i);
			maxHeight = Math.max(maxHeight, height);
		}
		return maxHeight;
	}
	
	//calculate maxHeight for stack with num of boxes = bottomIndex
	public static int createStack(ArrayList<Box> boxes, int bottomIndex) {
		Box bottom = boxes.get(bottomIndex);
		int maxHeight = 0;
		for (int i = bottomIndex + 1; i < boxes.size(); i++) {
			if (boxes.get(i).canBeAbove(bottom)) {
				int height = createStack(boxes, i);
				maxHeight = Math.max(height, maxHeight);
			}
		}		
		maxHeight += bottom.height;
		return maxHeight;
	}
		
	
	public static void main(String[] args) {
		Box[] boxList = { new Box(6, 4, 4), new Box(8, 6, 2), new Box(5, 3, 3), new Box(7, 8, 3), new Box(4, 2, 2), new Box(9, 7, 3)};
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (Box b : boxList) {
			boxes.add(b);
		}
		
		int height = createStack(boxes);
		System.out.println(height);
	}

}

class Box {
	public int width;
	public int height;
	public int depth;
	public Box(int w, int h, int d) {
		width = w;
		height = h;
		depth = d;
	}
	
	public boolean canBeUnder(Box b) {
		if (width > b.width && height > b.height && depth > b.depth) {
			return true;
		}
		return false;
	}
	
	public boolean canBeAbove(Box b) {
		if (b == null) {
			return true;
		}
		if (width < b.width && height < b.height && depth < b.depth) {
			return true;
		}
		return false;		
	}
	
	public String toString() {
		return "Box(" + width + "," + height + "," + depth + ")";
	}
}

class BoxComparator implements Comparator<Box> {
	@Override
	public int compare(Box x, Box y){
		return y.height - x.height;
	}
}

/*
The problem in this code is that it gets very inefficient. We try to find the best solution that looks like {b3,b4, ••• } 
even though we may have already found the best solution with b4 at the bottom. Instead of
generating these solutions from scratch, we can cache these results using memoization.
Because we're only mapping from an index to a height, we can just use an integer array for our"hash table:'
Be very careful here with what each spot in the hash table represents. In this code, stackMap[i] represents
the tallest stack with box i at the bottom. Before pulling the value from the hash table, you have to
ensure that box i can be placed on top of the current bottom.
It helps to keep the line that recalls from the hash table symmetric with the one that inserts. For example, in
this code, we recall from the hash table with bottomindex at the start of the method. We insert into the
hash table with bottomindex at the end.
*/
package Q8_13_Stack_of_Boxes;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionB {	
	public static int createStack(ArrayList<Box> boxes) {
		Collections.sort(boxes, new BoxComparator());
		int maxHeight = 0;
		//K=arrayindex=box-number ; V=stackMap[K]=maxHeight-Using-Box-K
		int[] stackMap = new int[boxes.size()];	
		//for each box find maxHeight till last box
		for (int i = 0; i < boxes.size(); i++) {
			int height = createStack(boxes, i, stackMap);
			maxHeight = Math.max(maxHeight, height);
		}
		return maxHeight;
	}
	
	public static int createStack(ArrayList<Box> boxes, int bottomIndex, int[] stackMap) {
		//returned memoized result
		if (bottomIndex < boxes.size() && stackMap[bottomIndex] > 0) {
			return stackMap[bottomIndex];
		}
		
		Box bottom = boxes.get(bottomIndex);
		int maxHeight = 0;
		for (int i = bottomIndex + 1; i < boxes.size(); i++) {
			if (boxes.get(i).canBeAbove(bottom)) {
				//go find height of stack with "i" at bottom
				int height = createStack(boxes, i, stackMap);
				maxHeight = Math.max(height, maxHeight);
			}
		}
		//add current boxes height i.e bottom.height as current box is bottom
		maxHeight += bottom.height;
		//memoization
		stackMap[bottomIndex] = maxHeight;
		return maxHeight;
	}
		
	
	public static void main(String[] args) {
		Box[] boxList = { new Box(6, 4, 4), new Box(8, 6, 2), new Box(5, 3, 3), new Box(7, 8, 3), new Box(4, 2, 2), new Box(9, 7, 3)};
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (Box b : boxList) {
			boxes.add(b);
		}
		
		int height = createStack(boxes);
		System.out.println(height);
	}

}
