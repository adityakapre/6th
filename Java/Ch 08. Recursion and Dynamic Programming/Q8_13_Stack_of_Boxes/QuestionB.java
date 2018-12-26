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
		int[] stackMap = new int[boxes.size()];
		for (int i = 0; i < boxes.size(); i++) {
			int height = createStack(boxes, i, stackMap);
			maxHeight = Math.max(maxHeight, height);
		}
		return maxHeight;
	}
	
	public static int createStack(ArrayList<Box> boxes, int bottomIndex, int[] stackMap) {
		if (bottomIndex < boxes.size() && stackMap[bottomIndex] > 0) {
			return stackMap[bottomIndex];
		}
		
		Box bottom = boxes.get(bottomIndex);
		int maxHeight = 0;
		for (int i = bottomIndex + 1; i < boxes.size(); i++) {
			if (boxes.get(i).canBeAbove(bottom)) {
				int height = createStack(boxes, i, stackMap);
				maxHeight = Math.max(height, maxHeight);
			}
		}		
		maxHeight += bottom.height;
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
