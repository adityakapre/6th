/*
This algorithm is called FLOOD-FILL ALGORITHM (application:BUCKET FILL), complexity O(rc) for r rows, c columns in matrix

First, let's visualize how this method works. When we call paintFill (i.e., "click" paint fill in the image
editing application) on, say, a green pixel, we want to "bleed" outwards. Pixel by pixel, we expand outwards
by calling paintFill on the surrounding pixel. When we hit a pixel that is not green, we stop.

Does this algorithm seem familiar? It should! This is essentially depth-first search on a graph. At each pixel.
we are searching outwards to each surrounding pixel. We stop once we've fully traversed all the surrounding
pixels of this color.
We could alternatively implement this using breadth-first search.

This code visits each and every cell of a matrix of size n*m starting with some source cell. 
Time Complexity of above algorithm is O(r*c)

ocolor -> old color ncolor -> new color
*/
package Q8_10_Paint_Fill;

enum Color {
	
	BLACK("black"),
	GREEN("green"),
	RED("red");
	
	private String color;
	
	private Color(String color) {
		this.color = color;	
	}
	
	@Override
    	public String toString() {
        	return color;
    	}
}

public class Paint {

	private int N = 10;
	private Color[][] screen = new Color[N][N];
	
	public void initScreen() {
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				screen[r][c]=Color.BLACK;
			}
		}
	}
		
	public void randomizeScreen() {
		for(int i=0; i<(N*N); i++) {
			screen[getRandom(N)][getRandom(N)]=Color.GREEN;
		}
	}
	
	public static int getRandom(int n) {
		return (int) (Math.random() * n);
	}
	
	//dfs
	public static boolean PaintFill(Color[][] screen, int r, int c, Color ocolor, Color ncolor) {
		if (r < 0 || c < 0 || r >= screen.length || c >= screen[0].length) {
			return false;
		}
		if (screen[r][c] == ocolor) {
			screen[r][c] = ncolor;			     //visit : similar to preorder traversal CLR
			PaintFill(screen, r - 1, c, ocolor, ncolor); // up
			PaintFill(screen, r + 1, c, ocolor, ncolor); // down
			PaintFill(screen, r, c - 1, ocolor, ncolor); // left
			PaintFill(screen, r, c + 1, ocolor, ncolor); // right
		}
		return true;
	}
	
	//bfs
	public void paintFillIterativeQueue(Color oldC, Color newC, Color[][] screen, int r, int c) {
			if (r < 0 || c < 0 || r >= screen.length || c >= screen[0].length) {
				return;
			}
			Queue<Integer> row = new LinkedList<Integer>();
			Queue<Integer> col = new LinkedList<Integer>();
			row.add(r);
			col.add(c);
			while (!row.isEmpty()) {
				r = row.remove();
				c = col.remove();
				if (screen[r][c] == oldC) {
					screen[r][c] = newC;
					if (r - 1 >= 0) {
						row.add(r - 1);
						col.add(c);
					}
					if (r + 1 < screen.length) {
						row.add(r + 1);
						col.add(c);
					}
					if (c - 1 >= 0) {
						row.add(r);
						col.add(c - 1);
					}
					if (c + 1 < screen[0].length) {
						row.add(r);
						col.add(c + 1);
					}
				}
			}

		}
	
	//dfs
	public void paintFillIterativeStack(Color oldC, Color newC, Color[][] screen, int r, int c) {
			if (r < 0 || c < 0 || r >= screen.length || c >= screen[0].length) {
				return;
			}
			Stack<Integer> row = new Stack<Integer>();
			Stack<Integer> col = new Stack<Integer>();
			row.push(r);
			col.push(c);
			while (!row.isEmpty()) {
				r = row.pop();
				c = col.pop();
				if (screen[r][c] == oldC) {
					screen[r][c] = newC;
					if (r - 1 >= 0) {
						row.push(r - 1);
						col.push(c);
					}
					if (r + 1 < screen.length) {
						row.push(r + 1);
						col.push(c);
					}
					if (c - 1 >= 0) {
						row.push(r);
						col.push(c - 1);
					}
					if (c + 1 < screen[0].length) {
						row.push(r);
						col.push(c + 1);
					}
				}
			}

		}
	
	public static boolean PaintFill(Color[][] screen, int r, int c, Color ncolor) {
		if (screen[r][c] == ncolor) return false;
		return PaintFill(screen, r, c, screen[r][c], ncolor);
	}
	
	public static String PrintColor(Color c) {
		switch(c) {
		case Black:
			return "B";
		case White:
			return "W";
		case Red:
			return "R";
		case Yellow:
			return "Y";
		case Green:
			return "G";
		}
		return "X";
	}
	
	public static void PrintScreen(Color[][] screen) {
		for (int r = 0; r < screen.length; r++) {
			for (int c = 0; c < screen[0].length; c++) {
				System.out.print(PrintColor(screen[r][c]));
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Paint oPaint = new Paint();
		oPaint.initScreen();
		oPaint.randomizeScreen();
		PrintScreen(screen);
		oPaint.paintFill(Color.GREEN, Color.RED, oPaint.screen, 9, 9); 
		PrintScreen(screen);
		oPaint.paintFillIterativeQueue(Color.GREEN, Color.RED, oPaint.screen, 9, 9);
		PrintScreen(screen);
		oPaint.paintFillIterativeStack(Color.GREEN, Color.RED, oPaint.screen, 9, 9); 
		PrintScreen(screen);
	}

	
}
