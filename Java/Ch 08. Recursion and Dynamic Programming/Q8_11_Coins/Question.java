/*
Q:
Coins: Given an infinite number of quarters (25 cents), dimes (1O cents), nickels (5 cents), and
pennies (1 cent), write code to calculate the number of ways of representing n cents.

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
