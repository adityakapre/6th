/*
This works, but it's not as optimal as it could be. The issue is that we will be recursively calling makeChange
several times for the same values of amount and index.
We can resolve this issue by storing the previously computed values. We'll need to store a mapping from
each pair(amount, index) to the precomputed result.

Note that we've used a two-dimensional array of integers to store the previously computed values. This is
simpler, but takes up a little extra space. Alternatively, we could use an actual hash table that maps from
amount to a new hash table, which then maps from denom to the precomputed value. There are other
alternative data structures as well.
*/
package Q8_11_Coins;

public class QuestionB {

	public static int makeChange(int n, int[] denoms) {
		int[][] map = new int[n + 1][denoms.length];
		return makeChangeHelper(n, denoms, 0, map);
	}
	
	public static int makeChangeHelper(int total, int[] denoms, int index, int[][] map) {
		/* Check cache for prior result. */
		if (map[total][index] > 0) { // retrieve value
			return map[total][index];
		}
		
		int coin = denoms[index];
		if (index == denoms.length - 1) {
			int remaining = total % coin; 
			return remaining == 0 ? 1 : 0;
		}
		int numberOfWays = 0;
		/* Try 1 coin, then 2 coins, 3 three, ... (recursing each time on rest). */
		for (int amount = 0; amount <= total; amount += coin) { 
			numberOfWays += makeChangeHelper(total - amount, denoms, index + 1, map); // go to next denom
		}
		
		/* Update cache with current result. */
		map[total][index] = numberOfWays;
		
		return numberOfWays;
	}	
	
	public static void main(String[] args) {
		int[] denoms = {25, 10, 5, 1};
		int ways = makeChange(10, denoms);
		System.out.println(ways);
	}

}
