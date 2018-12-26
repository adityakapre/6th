/*
Optimized Solutions
If we follow the recursive path, we'll note that we end up doing the same computation repeatedly.
Consider the expression 0^0&0^1|1 and these recursion paths:
Add parens around char 1. (0)^(0&0|1|1)
	» Add parens around char 3. (0)^((0)&(0^1|1))
• Add parens around char 3. (0/\0)&(0/\1 I 1)
	» Add parens around char 1. ((0)^(0))&(0^1|1)
Although these two expressions are different, they have a similar component: (0^1|1). We should reuse our
effort on this.
We can do this by using memoization, or a hash table. We just need to store the result of
countEval (expression, result) for each expression and result. If we see an expression that we've
calculated before, we just return it from the cache.

The added benefit of this is that we could actually end up with the same substring in multiple parts of the
expression. For example, an expression like 0^1^0&0^1^0 has two instances of 0^1^0. By caching the
result of the substring value in a memoization table, we'll get to reuse the result for the right part of the
expression after computing it for the left.
There is one further optimization we can make, but it's far beyond the scope of the interview. There is
a closed form expression for the number of ways of parenthesizing an expression, but you wouldn't be
expected to know it. It is given by the Catalan numbers, where n is the number of operators:
Cn= (2n)!/(n+l)!n!
We could use this to compute the total ways of evaluating the expression. Then, rather than computing
leftTrue and leftFalse, we just compute one of those and calculate the other using the Catalan
numbers. We would do the same thing for the right side.
*/
package Q8_14_Boolean_Evaluation;

import java.util.HashMap;

public class QuestionB {
	
	public static int count = 0;
	public static boolean stringToBool(String c) {
		return c.equals("1") ? true : false;
	}
	
	public static int countEval(String s, boolean result, HashMap<String, Integer> memo) {
		count++;
		if (s.length() == 0) return 0;
		if (s.length() == 1) return stringToBool(s) == result ? 1 : 0;
		if (memo.containsKey(result + s)) return memo.get(result + s);

		int ways = 0;
		
		for (int i = 1; i < s.length(); i += 2) {
			char c = s.charAt(i);
			String left = s.substring(0, i);
			String right = s.substring(i + 1, s.length());
			int leftTrue = countEval(left, true, memo);
			int leftFalse = countEval(left, false, memo);
			int rightTrue = countEval(right, true, memo);
			int rightFalse = countEval(right, false, memo);
			int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);
			
			int totalTrue = 0;
			if (c == '^') {
				totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
			} else if (c == '&') {
				totalTrue = leftTrue * rightTrue;
			} else if (c == '|') {
				totalTrue = leftTrue * rightTrue + leftFalse * rightTrue + leftTrue * rightFalse;
			}
			
			int subWays = result ? totalTrue : total - totalTrue;
			ways += subWays;
		}
		
		memo.put(result + s, ways);
		return ways;
	}
	
	public static int countEval(String s, boolean result) {
		return countEval(s, result, new HashMap<String, Integer>());
	}
	
	public static void main(String[] args) {
		String expression = "0^0|1&1^1|0|1";
		boolean result = true;
		
		System.out.println(countEval(expression, result));
		System.out.println(count);
	}

}
