/*
Q:
Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3
steps at a time. Implement a method to count how many possible ways the child can run up the
stairs.

A:
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
taking one path AND then taking the other. That's not what's happening here. We want one path OR other.

Brute Force Solution
This is a fairly straightforward algorithm to implement recursively. We just need to follow logic like this:
countWays(n-1) + countWays(n-2) + countWays(n-3)
The one tricky bit is defining the base case. If we have O steps to go (we're currently standing on the step),
are there zero paths to that step or one path?
That is, what is countWays(0)? Is it 1 or 0?
You could define it either way. There is no"right" answer here.
However, it's a lot easier to define it as 1. If you defined it as 0, then you would need some additional base
cases (or else you'd just wind up with a series of 0s getting added).

Like the Fibonacci problem, the runtime of this algorithm is exponential (roughly O(3 raisedTo n) ), since each call
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

/*
Memoization Solution
The previous solution for countWays is called many times for the same values, which is unnecessary. We
can fix this through memoization.
Essentially, if we've seen this value of n before, return the cached value. Each time we compute a fresh value,
add it to the cache.
Typically we use a HashMap<Integer, Integer> for a cache. In this case, the keys will be exactly 1
through n. It's more compact to use an integer array.
Regardless of whether or not you use memoization, note that the number of ways will quickly overflow the
bounds of an integer. By the time you get to just n 37, the result has already overflowed. Using a long
will delay, but not completely solve, this issue.
It is great to communicate this issue to your interviewer. He probably won't ask you to work around it
(although you could, with a Biginteger class). but it's nice to demonstrate that you think about these
issues.
*/
package Q8_01_Triple_Step;

import java.util.Arrays;

public class QuestionB {

	public static int countWays(int n) {
		int[] map = new int[n + 1];
		//Remember to fill map using Arrays.fill
		Arrays.fill(map, -1);	
		return countWays(n, map);
	}
	
	public static int countWays(int n, int[] memo) {
		if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;   //avoid just wind up with a series of 0s getting added, return 1 instead of 0
		} else if (memo[n] > -1) {
			return memo[n];
		} else {
			memo[n] = countWays(n - 1, memo) + countWays(n - 2, memo) + countWays(n - 3, memo);
			return memo[n];
		}
	}
	
	public static void main(String[] args) {
		int n = 50;
		int ways = countWays(n);
		System.out.println(ways);
	}
}

/*
Problem
You are climbing a stair case. It takes n steps to reach to the top.
Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
Note: Given n will be a positive integer.

Program Structure:
public class Solution {
    public int climbStairs(int n) {
        
    }
}

Solution
The problem seems to be a dynamic programming one.
Here are the steps to get the solution incrementally.
Base cases:
if n <= 0, then the number of ways should be zero.
if n == 1, then there is only way to climb the stair.
if n == 2, then there are two ways to climb the stairs. 
One solution is one step by another; the other one is two steps at one time.
The key intuition to solve the problem is that given a number of stairs n, if we know the number ways 
to get to the points [n-1] and [n-2] respectively, denoted as n1 and n2 , then the total ways to get 
to the point [n] is n1 + n2. Because from the [n-1] point, we can take one single step to reach [n]. 
And from the [n-2] point, we could take two steps to get there. There is NO overlapping between these two solution sets, 
because we differ in the final step.
Now given the above intuition, one can construct an array where each node stores the solution for each number n. 
Or if we look at it closer, it is clear that this is basically a fibonacci number, with the starting numbers as 1 and 2, 
instead of 1 and 1.
The implementation in Java as follows:
*/

public class Solution {
  public int climbStairs(int n) {
    // base cases
    if(n <= 0) return 0;
    if(n == 1) return 1;
    if(n == 2) return 2;
    
    int one_step_before = 2;
    int two_steps_before = 1;
    int all_ways = 0;
    
    for(int i=2; i<n; i++){
      all_ways = one_step_before + two_steps_before;
      two_steps_before = one_step_before;
      one_step_before = all_ways;
    }
    return all_ways;
}
}
