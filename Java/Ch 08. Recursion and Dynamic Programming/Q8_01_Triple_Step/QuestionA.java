/*
Let's think about this with the following question: What is the very last step that is done?
The very last hop the child makes-the one that lands her on the nth step-was either a 3-step hop, a
2-step hop, or a 1-step hop.
How many ways then are there to get up to the nth step? We don't know yet, but we can relate it to some
subproblems.

If we thought about all of the paths to the nth step, we could just build them off the paths to the three
previous steps. We can get up to the nth step by any of the following:
• Going to the (n - l)st step and hopping 1 step.
• Going to the (n - 2)nd step and hopping 2 steps.
• Going to the (n - 3)rd step and hopping 3 steps.

Therefore, we just need to add the number of these paths together.
Be very careful here. A lot of people want to multiply them. Multiplying one path with another would signify
taking one path and then taking the other. That's not what's happening here.

Brute Force Solution
This is a fairly straightforward algorithm to implement recursively. We just need to follow logic like this:
countWays(n-1) + countWays(n-2) + countWays(n-3)
The one tricky bit is defining the base case. If we have O steps to go (we're currently standing on the step),
are there zero paths to that step or one path?
That is, what is countWays(0)? Is it 1 or O?
You could define it either way. There is no"right" answer here.
However, it's a lot easier to define it as 1. If you defined it as 0, then you would need some additional base
cases (or else you'd just wind up with a series of Os getting added).

Like the Fibonacci problem, the runtime of this algorithm is exponential (roughly O ( 3 raised to n) ), since each call
branches out to three more calls.
*/
package Q8_01_Triple_Step;

public class QuestionA {
	
	public static int countWays(int n) {
		if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;
		} else {
			return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
		}
	}
	
	public static void main(String[] args) {
		int n = 20;
		int ways = countWays(n);
		System.out.println(ways);
	}

}
