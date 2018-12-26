/*
Brute Force
Consider an expression like 0^0&0^1|1 and the target result true. How can we break down
countEval(0^0&0^1|1, true) into smaller problems?
We could just essentially iterate through each possible place to put a parenthesis.

countEval(0^0&0^1|1, true)=
countEval(0^0&0^1|1, where paren around char 1, true)
+ countEval(0^0&0^1|1, where paren around char 3, true)
+ countEval(0^0&0^1|1, where paren around char 5, true)
+ countEval(0^0&0^1|1, where paren around char 7, true)

Now what? Let's look at just one of those expressions-the paren around char 3. This gives us (0^0)&(0^1).
In order to make that expression true, both the left and right sides must be true. So:
left= "0^0"
right = "0^1|1"
countEval(left & right, true)= countEval(left, true) * countEval(right, true)

The reason we multiply the results of the left and right sides is that each result from the two sides can be
paired up with each other to form a unique combination.
Each of those terms can now be decomposed into smaller problems in a similar process.

What happens when we have an "|"(OR)? Or an"^"(XOR)?
If it's an OR, then either the left or the right side must be true-or both.
countEval(left | right, true) = countEval(left, true) * countEval(right, false)
+ countEval(left, false) * countEval(right, true)
+ countEval(left, true) * countEval(right, true)

If it's an XOR, then the left or the right side can be true, but not both.
countEval(left ^ right, true)= countEval(left, true) * countEval(right, false)
+ countEval(left, false) * countEval(right, true)

What if we were trying to make the result false instead? We can switch up the logic from above:
countEval(left & right, false) = countEval(left, true) * countEval(right, false)
+ countEval(left, false) * countEval(right, true) + countEval(left, false) * countEval(right, false)

countEval(left | right, false) = countEval(left, false) * countEval(right, false)

countEval(left ^ right, false) = countEval(left, false) * countEval(right, false) + countEval(left, false) * countEval(right, false)
+ countEval(left, true) * countEval(right, true)
*/
package Q8_14_Boolean_Evaluation;

public class QuestionA {
	public static int count = 0;
	public static boolean stringToBool(String c) {
		return c.equals("1") ? true : false;
	}
	
	public static int countEval(String s, boolean result) {
		count++;
		if (s.length() == 0) return 0;
		if (s.length() == 1) return stringToBool(s) == result ? 1 : 0;

		int ways = 0;
		
		for (int i = 1; i < s.length(); i += 2) {
			char c = s.charAt(i);
			String left = s.substring(0, i);
			String right = s.substring(i + 1, s.length());
			int leftTrue = countEval(left, true);
			int leftFalse = countEval(left, false);
			int rightTrue = countEval(right, true);
			int rightFalse = countEval(right, false);
			int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);
			
			int totalTrue = 0;
			if (c == '^') { // required: one true and one false
				totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
			} else if (c == '&') { // required: both true
				totalTrue = leftTrue * rightTrue;
			} else if (c == '|') { // required: anything but both false
				totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
			}
			
			int subWays = result ? totalTrue : total - totalTrue;
			ways += subWays;
		}
		
		return ways;
	}
	
	public static void main(String[] args) {
		String expression = "0^0|1&1^1|0|1";
		boolean result = true;
		
		System.out.println(countEval(expression, result));
		System.out.println(count);
	}

}
