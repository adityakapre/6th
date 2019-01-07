/*
First, let's visualize how this method works. When we call paintFill (i.e., "click" paint fill in the image
editing application) on, say, a green pixel, we want to "bleed" outwards. Pixel by pixel, we expand outwards
by calling paintFill on the surrounding pixel. When we hit a pixel that is not green, we stop.

If you used the variable names x and y to implement this, be careful about the ordering of the variables in
screen [y] [x]. Because x represents the horizontal axis (that is, it's left to right), it actually corresponds
to the column number, not the row number. The value of y equals the number of rows. This is a very easy
place to make a mistake in an interview, as well as in your daily coding. It's typically clearer to use row and
column instead, as we've done here.
Does this algorithm seem familiar? It should! This is essentially depth-first search on a graph. At each pixel.
we are searching outwards to each surrounding pixel. We stop once we've fully traversed all the surrounding
pixels of this color.
We could alternatively implement this using breadth-first search.

ocolor -> old color ncolor -> new color
*/
package Q8_10_Paint_Fill;

public class Question {

	public enum Color {
		Black, White, Red, Yellow, Green
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
	
	public static int randomInt(int n) {
		return (int) (Math.random() * n);
	}
	
	public static boolean PaintFill(Color[][] screen, int r, int c, Color ocolor, Color ncolor) {
		if (r < 0 || r >= screen.length || c < 0 || c >= screen[0].length) {
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
	
	public static boolean PaintFill(Color[][] screen, int r, int c, Color ncolor) {
		if (screen[r][c] == ncolor) return false;
		return PaintFill(screen, r, c, screen[r][c], ncolor);
	}
	
	public static void main(String[] args) {
		int N = 10;
		Color[][] screen = new Color[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				screen[i][j] = Color.Black;
			}			
		}
		for (int i = 0; i < 100; i++) {
			screen[randomInt(N)][randomInt(N)] = Color.Green;
		}
		PrintScreen(screen);
		PaintFill(screen, 2, 2, Color.White);
		System.out.println();
		PrintScreen(screen);
	}

}
