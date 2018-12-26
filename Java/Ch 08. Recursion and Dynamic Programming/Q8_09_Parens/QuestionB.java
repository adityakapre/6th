/*
We can avoid this duplicate string issue by building the string from scratch. Under this approach, we add
left and right parens, as long as our expression stays valid.
On each recursive call, we have the index for a particular character in the string. We need to select either a
left or a right paren. When can we use a left paren, and when can we use a right paren?
1. Left Paren: As long as we haven't used up all the left parentheses, we can always insert a left paren.
2. Right Paren: We can insert a right paren as long as it won't lead to a syntax error. When will we get a
syntax error? We will get a syntax error if there are more right parentheses than left.
So, we simply keep track of the number of left and right parentheses allowed. If there are left parens
remaining, we'll insert a left paren and recurse. If there are more right parens remaining than left (i.e., if
there are more left parens in use than right parens), then we'll insert a right paren and recurse.
*/
package Q8_09_Parens;

import java.util.ArrayList;

public class QuestionB {
	
	public static void addParen(ArrayList<String> list, int leftRem, int rightRem, char[] str, int index) {
		if (leftRem < 0 || rightRem < leftRem) return; // invalid state
		
		if (leftRem == 0 && rightRem == 0) { /* all out of left and right parentheses */
			list.add(String.copyValueOf(str));
		} else {
			str[index] = '('; // Add left and recurse
			addParen(list, leftRem - 1, rightRem, str, index + 1);
			
			str[index] = ')'; // Add right and recurse
			addParen(list, leftRem, rightRem - 1, str, index + 1);
		}
	}
	
	public static ArrayList<String> generateParens(int count) {
		char[] str = new char[count*2];
		ArrayList<String> list = new ArrayList<String>();
		addParen(list, count, count, str, 0);
		return list;
	}
	
	public static void main(String[] args) {
		ArrayList<String> list = generateParens(6);
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println(list.size());		
	}

}
