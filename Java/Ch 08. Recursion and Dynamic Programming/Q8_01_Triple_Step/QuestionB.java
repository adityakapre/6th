/*
Memoization Solution
The previous solution for c ountWays is called many times for the same values, which is unnecessary. We
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
		Arrays.fill(map, -1);
		return countWays(n, map);
	}
	
	public static int countWays(int n, int[] memo) {
		if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;
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
