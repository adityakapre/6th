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
	
	/*
	Like in commented code {makeChange2 function} we could have also used 2D array for memoization 
	but it will consume extra space with some positions in array having no values.
	So we use hashmap.
	While using hashmap, seperator is important
	
	https://youtu.be/sn0DWI-JdNA 
	*/
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
			//for 1st time in each recurrsive call, remaining=money as amountWithCoin=0
			ways += makeChange(coins, remaining, index+1, memo);
			amountWithCoin += coins[index];
		}
		memo.put(key, ways);
		return ways;
	}
	
	/*
	Iterative  - 2D array
	https://www.youtube.com/watch?v=_fgjrs570YE
	
		  0 1 2 3 4 5 ->j  total=5
	        1 1 1 1 1 1 1
	        2 1 1 2 2 3 3
	        3 1 1 2 3 4 5
	coins-> i
	*/
	public int numberOfSolutions(int coins[], int total){
		int temp[][] = new int[coins.length+1][total+1];
		for(int i=0; i <= coins.length; i++){
		    temp[i][0] = 1;
		}
		for(int i=1; i <= coins.length; i++){
		    for(int j=1; j <= total ; j++){
			if(coins[i-1] > j){
			    temp[i][j] = temp[i-1][j];
			}
			else{
			    temp[i][j] = temp[i][j-coins[i-1]] + temp[i-1][j];
			}
		    }
		}
		return temp[coins.length][total];
	    }

    /**
       Iterative - 1D array
       https://www.youtube.com/watch?v=_fgjrs570YE
       Space efficient DP solution
     */
    public int numberOfSolutionsOnSpace(int coins[], int total){
        int temp[] = new int[total+1];
        temp[0] = 1;
        for(int i=0; i < coins.length; i++){
            for(int j=1; j <= total ; j++){
                if(j >= coins[i]){
		    /*
		    for 1st iteration, temp[1]=temp[1]+temp[1-1]=0+1=1
		    */
                    temp[j] += temp[j-coins[i]];
                }
            }
        }
        return temp[total];
    }

    /**
     * Given a total and coins of certain denomination with infinite supply, what is the minimum number
     * of coins it takes to form this total.
     *
     * Top down dynamic programing. Using map to store intermediate results.
     * Returns Integer.MAX_VALUE if total cannot be formed with given coins
     */
    public int minimumCoinTopDown(int total, int coins[], Map<Integer, Integer> map) {

        //if total is 0 then there is nothing to do. return 0.
        if ( total == 0 ) {
            return 0;
        }

        //if map contains the result means we calculated it before. Lets return that value.
        if ( map.containsKey(total) ) {
            return map.get(total);
        }

        //iterate through all coins and see which one gives best result.
        int min = Integer.MAX_VALUE;
        for ( int i=0; i < coins.length; i++ ) {
            //if value of coin is greater than total we are looking for just continue.
            if( coins[i] > total ) {
                continue;
            }
            //recurse with total - coins[i] as new total
            int val = minimumCoinTopDown(total - coins[i], coins, map);

            //if val we get from picking coins[i] as first coin for current total is less
            // than value found so far make it minimum.
            if( val < min ) {
                min = val;
            }
        }

        //if min is MAX_VAL dont change it. Just result it as is. Otherwise add 1 to it.
        min =  (min == Integer.MAX_VALUE ? min : min + 1);

        //memoize the minimum for current total.
        map.put(total, min);
        return min;
    }
	/*public static int makeChange2(int n, int[] denoms) {
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
