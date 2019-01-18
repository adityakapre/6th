/*
This works, but it's not as optimal as it could be. The issue is that we will be recursively calling makeChange
several times for the same values of amount and index.
We can resolve this issue by storing the previously computed values. We'll need to store a mapping from
each pair(amount, index) to the precomputed result.

Note that we've used a two-dimensional array of integers to store the previously computed values. This is
simpler, but takes up a little extra space. Alternatively, we could use an actual hash table that maps from
amount to a new hash table, which then maps from denom to the precomputed value. There are other
alternative data structures as well.

refer following for explanation : 
https://www.youtube.com/watch?v=sn0DWI-JdNA&list=PLI1t_8YX-ApvMthLj56t1Rf-Buio5Y8KL&index=10 

*/
package Q8_11_Coins;

public class QuestionB {

	public static long makeChage(int[] coins, int money) {
		return makeChange(coins, money, 0, new HashMap<String, Long>());
	}
	
	public static long makeChange(int[] coins, int money, int index, HashMap<String, Long> memo ) {
		if(money == 0) {
			return 1;
		}
		if(index >coins.length) {
			return 0;
		}
		String key = money+"-"+index;
		if(memo.containsKey(key)) {
			return memo.get(key);
		}
		int amountWithCoin = 0;
		long ways = 0;
		while(amountWithCoin <= money) {
			int remaining = money - amountWithCoin;
			ways += makeChange(coins, remaining, index+1, memo);
			amountWithCoin += coins[index];
		}
		memo.put(key, ways);
		return ways;
	}
	
	/*public static int makeChange(int n, int[] denoms) {
		int[][] map = new int[n + 1][denoms.length];
		return makeChangeHelper(n, denoms, 0, map);
	}
	
	public static int makeChangeHelper(int total, int[] denoms, int index, int[][] map) {
		//returned memoized result
		if (map[total][index] > 0) { // retrieve value
			return map[total][index];
		}
		
		int coin = denoms[index];
		if (index == denoms.length - 1) {
			int remaining = total % coin;
			//add "1" way if total=coin i.e remaining=0
			return remaining == 0 ? 1 : 0;
		}
		int numberOfWays = 0;
		// Try 1 coin, then 2 coins, 3,... (recursing each time on rest) 
		for (int amount = 0; amount <= total; amount += coin) { 
			numberOfWays += makeChangeHelper(total - amount, denoms, index + 1, map); // go to next denom
		}
		
		//memoization
		map[total][index] = numberOfWays;
		
		return numberOfWays;
	}*/	
	
	public static void main(String[] args) {
		int[] denoms = {25, 10, 5, 1};
		int ways = makeChange(10, denoms);
		System.out.println(ways);
	}

}
