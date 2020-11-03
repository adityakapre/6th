/*
Q:
Coins: Given an infinite number of quarters (25 cents), dimes (1O cents), nickels (5 cents), and
pennies (1 cent), write code to calculate the number of ways of representing n cents.

ANSWER:
https://leetcode.com/problems/coin-change-2/

A:
This is a recursive problem, so let's figure out how to compute makeChange(n) using prior solutions (i.e.,subproblems).
Let's say n = 100. We want to compute the number of ways of making change for 100 cents. What is the relationship between 
this problem and its subproblems?

We know that making change for 100 cents will involve either 0, 1, 2, 3, or 4 quarters. So:

makeChange(100) = makeChange(100 using 0 quarters)+makeChange(100 using 1 quarter) +makeChange(100 using 2 quarters)+
makeChange(100 using 3 quarters)+makeChange(100 using 4 quarters)

Inspecting this further, we can see that some of these problems reduce. For example, makeChange(100 using 1 quarter) 
will equal makeChange(75 using 0 quarters). This is because, if we must use exactly one quarter to make change for 100 cents, 
then our only remaining choices involve making change for the remaining 75 cents.

We can apply the same logic to makeChange(100 using 2 quarters),makeChange(100 using 3 quarters) 
and makeChange(100 using 4 quarters). We have thus reduced the above statement to the following.

makeChange(100) = makeChange(100 using 0 quarters)+makeChange(75 using 0 quarters)+makeChange(50 using 0 quarters)+
makeChange(25 using 0 quarters)+1

Note that the final statement from above, makeChange(100 using 4 quarters), equals 1. We callthis "fully reduced:'

Now what? We've used up all our quarters, so now we can start applying our next biggest denomination:dimes.

Our approach for quarters applies to dimes as well, but we apply this for each of the four of five parts of the
above statement. So, for the first part, we get the following statements:

makeChange(100 using 0 quarters)= makeChange(100 using 0 quarters, 0 dimes)+
makeChange(l00 using 0 quarters, 1 dime) +makeChange(100 using 0 quarters, 2 dimes)+makeChange(75 using 0 quarters)
makeChange(75 using 0 quarters, 1 dime) +makeChange(75 using 0 quarters, 2 dimes)+
...
makeChange(75 using 0 quarters, 7 dimes)
makeChange(50 using 0 quarters, 0 dimes)+makeChange(50 using 0 quarters, 1 dime) +makeChange(50 using 0 quarters, 2 dimes)+
...
makeChange(50 using 0 quarters, 5 dimes)

makeChange(25 using 0 quarters)= makeChange(25 using 0 quarters, 0 dimes)+
makeChange(25 using 0 quarters, 1 dime) +
makeChange(25 using 0 quarters, 2 dimes)

Each one of these, in turn, expands out once we start applying nickels. We end up with a tree-like recursive
structure where each call expands out to four or more calls.
The base case of our recursion is the fully reduced statement. For example, makeChange(50 using 0 quarters, 5 dimes) 
is fully reduced to 1, since 5 dimes equals 50 cents.
*/
package Q8_11_Coins;

public class Question {	
	public static int makeChangeHelper(int total, int[] denoms, int index) {
		int coin = denoms[index];
		// One denom left, either you can do it or not
		if (index == denoms.length - 1) { 
			int remaining = total % coin; 
			//use coin if total=coin i.e remaining=0
			return remaining == 0 ? 1 : 0;
		}
		int ways = 0;
		/* Try 1 coin, then 2 coins, 3 three, ... (recursing each time on rest). */
		for (int amount = 0; amount <= total; amount += coin) { 
			ways += makeChangeHelper(total - amount, denoms, index + 1); // go to next denom
		}
		return ways;
	}
	
	public static int makeChange(int amount, int[] denoms) {
		return makeChangeHelper(amount, denoms, 0);
	}
	
	public static void main(String[] args) {
		int[] denoms = {25, 10, 5, 1};
		int ways = makeChange(10, denoms);
		System.out.println(ways);
	}
}

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

/*
Q:
Q:
Coins: Given an infinite number of quarters (25 cents), dimes (1O cents), nickels (5 cents), and
pennies (1 cent), write code to calculate the number of ways of representing n cents.

A:
1) Optimal Substructure
To count the total number of solutions, we can divide all set solutions into two sets.
1) Solutions that do not contain mth coin (or Sm).
2) Solutions that contain at least one Sm.
Let count(S[], m, n) be the function to count the number of solutions, then it can be written as sum of count(S[], m-1, n) 
and count(S[], m, n-Sm).

Therefore, the problem has optimal substructure property as the problem can be solved using solutions to subproblems.

2) Overlapping Subproblems
Following is a simple recursive implementation of the Coin Change problem. The implementation simply follows 
the recursive structure mentioned above.
*/

// Recursive java program for coin change problem. 
import java.io.*; 
  
class GFG { 
      
    // Returns the count of ways we can sum S[0...m-1] coins to get sum n 
    static int count( int S[], int m, int n ) { 
        // If n is 0 then there is 1 solution (do not include any coin) 
        if (n == 0) 
            return 1; 
          
        // If n is less than 0 then no solution exists 
        if (n < 0) 
            return 0; 
      
        // If there are no coins and n  
        // is greater than 0, then no 
        // solution exist 
        if (m <=0 && n >= 1) 
            return 0; 
      
        // count is sum of solutions   
        // (i) including S[m-1] (ii) excluding S[m-1] 
        return count( S, m - 1, n ) + count( S, m, n-S[m-1] ); 
    } 
      
    // Driver program to test above function 
    public static void main(String[] args) { 
        int arr[] = {1, 2, 3}; 
        int m = arr.length; 
        System.out.println( count(arr, m, 4)); 
    } 
} 

/*
Since same suproblems are called again, this problem has Overlapping Subprolems property. 
So the Coin Change problem has both properties (see Overlapping Subproblems and Optimal Substructure) of a dynamic programming problem. 
Like other typical Dynamic Programming(DP) problems, recomputations of same subproblems can be avoided 
by constructing a temporary array table[][] in bottom up manner.

		  0 1 2 3 4 5 ->j  n=total=5
	        1 1 1 1 1 1 1
	        2 1 1 2 2 3 3
	        3 1 1 2 3 4 5
	coins-> i
*/

public static int count( int S[], int m, int n ) { 
    // table[i] will be storing the number of solutions for value i.  
    // We need n+1 rows as the table is constructed in bottom up manner using the base case (n = 0) 
    int table[]=new int[n+1]; 
  
    // Base case (If given value is 0) 
    table[0] = 1; 
  
    // Pick all coins one by one and update the table[] values 
    // after the index greater than or equal to the value of the 
    // picked coin 
    for(int i=0; i<m; i++) 
        for(int j=S[i]; j<=n; j++) 
            table[j] += table[j-S[i]]; 
  
    return table[n]; 
} 


/*
Q:
Find minimum number of coins that make a given value

A:
The minimum number of coins for a value V can be computed using below recursive formula.

If V == 0, then 0 coins required.
If V > 0
   minCoin(coins[0..m-1], V) = min {1 + minCoins(V-coin[i])} 
                               where i varies from 0 to m-1 
                               and coin[i] <= V 
*/
// A Naive recursive JAVA program to find minimum of coins 
// to make a given change V 
class coin { 
    // m is size of coins array (number of different coins) 
    static int minCoins(int coins[], int m, int V) { 
       // base case 
       if (V == 0) return 0; 
       
       // Initialize result 
       int res = Integer.MAX_VALUE; 
       
       // Try every coin that has smaller value than V 
       for (int i=0; i<m; i++) { 
         if (coins[i] <= V) { 
             int sub_res = minCoins(coins, m, V-coins[i]); 
             // Check for INT_MAX to avoid overflow and see if result can minimized 
             if (sub_res != Integer.MAX_VALUE && sub_res + 1 < res) 
                res = sub_res + 1; 
         } 
       } 
       return res; 
    } 
    public static void main(String args[]) { 
       int coins[] =  {9, 6, 5, 1}; 
       int m = coins.length; 
       int V = 11; 
       System.out.println("Minimum coins required is "+ minCoins(coins, m, V) ); 
    } 
}
/*
The time complexity of above solution is exponential. If we draw the complete recursion tree, 
we can observe that many subproblems are solved again and again. For example, when we start from V = 11, 
we can reach 6 by subtracting one 5 times and by subtracting 5 one times. So the subproblem for 6 is called twice.
Since same suproblems are called again, this problem has Overlapping Subproblems property. 
So the min coins problem has both properties (see this and this) of a dynamic programming problem. 
Like other typical Dynamic Programming(DP) problems, recomputations of same subproblems can be avoided 
by constructing a temporary array table[][] in bottom up manner. Below is Dynamic Programming based solution.
*/
// A Dynamic Programming based Java 
// program to find minimum of coins 
// to make a given change V 
import java.io.*; 
  
class GFG { 
    // m is size of coins array (number of different coins) 
    static int minCoins(int coins[], int m, int V) { 
        // table[i] will be storing the minimum number of coins required for i value.  
        // So table[V] will have result 
        int table[] = new int[V + 1]; 
  
        // Base case (If given value V is 0) 
        table[0] = 0; 
  
        // Initialize all table values as Infinite 
        for (int j = 1; j <= V; j++) 
        table[j] = Integer.MAX_VALUE; 
  
        // Compute minimum coins required for all values from 1 to V 
        for (int j = 1; j <= V; j++) { 
            // Go through all coins smaller than i 
            for (int i = 0; i < m; i++) 
            if (coins[i] <= j) { 
                int sub_res = table[j - coins[i]]; 
                if (sub_res != Integer.MAX_VALUE  && sub_res + 1 < table[j]) 
                       table[j] = sub_res + 1;                         
            } 
        } 
        return table[V];
    } 
    // Driver program  
    public static void main (String[] args) { 
        int coins[] = {9, 6, 5, 1}; 
        int m = coins.length; 
        int V = 11; 
        System.out.println ( "Minimum coins required is " + minCoins(coins, m, V)); 
    } 
} 
