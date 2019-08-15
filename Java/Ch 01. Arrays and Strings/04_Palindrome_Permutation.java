/*
Q:
Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome.
A palindrome is a word or phrase that is the same forwards and backwards. A permutation
is a rearrangement of letters. The palindrome does not need to be limited to just dictionary words.

EXAMPLE
Input: Tact Coa
Output: True (permutations: "taco cat", "atco eta", etc.)

A:
This is a question where it helps to figure out what it means for a string to be a permutation of a palindrome.
This is like asking what the "defining features" of such a string would be.
A palindrome is a string that is the same forwards and backwards. Therefore, to decide if a string is a permutation
of a palindrome, we need to know if it can be written such that it's the same forwards and backwards.
What does it take to be able to write a set of characters the same way forwards and backwards? We need to
have an even number of almost all characters, so that half can be on one side and half can be on the other
side. At most one character (the middle character) can have an odd count.
For example, we know tactcoapapa is a permutation of a palindrome because it has two Ts, four As, two
Cs, two Ps, and one O. That O would be the center of all possible palindromes.

To be more precise, strings with even length (after removing all non-letter characters) must have
all even counts of characters. Strings of an odd length must have exactly one character with
an odd count. Of course, an "even" string can't have an odd number of exactly one character,
otherwise it wouldn't be an even-length string (an odd number+ many even numbers= an odd
number). Likewise, a string with odd length can't have all characters with even counts (sum of
evens is even). It's therefore sufficient to say that, to be a permutation ot a palindrome, a string
can have no more than one character that is odd. This will cover both the odd and the even cases.
This leads us to our first algorithm.

Solution#1
Implementing this algorithm is fairly straightforward. We use a hash table to count how many times each
character appears. Then, we iterate through the hash table and ensure that no more than one character has
an odd count.

hash table in this case is int[] where "key"=ascii value of char & "value"=count of char

This algorithm takes O (N) time, where N is the length of the string.
*/
package Q1_04_Palindrome_Permutation;

public class QuestionA {	
	
	//start here...
	public static boolean isPermutationOfPalindrome(String phrase) {
		int[] table = Common.buildCharFrequencyTable(phrase);
		return checkMaxOneOdd(table);
	}
	
	public static int[] buildCharFrequencyTable(String phrase) {
		//decide size of hash table
		int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
		for (char c : phrase.toCharArray()) {
			int x = getCharNumber(c);
			if (x != -1) {
				table[x]++;
			}
		}
		return table;
	}
	
	public static int getCharNumber(Character c) {
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		
		int val = Character.getNumericValue(c);
		if (a <= val && val <= z) {
			return val - a;
		}
		return -1;
	}
	
	public static boolean checkMaxOneOdd(int[] table) {
		boolean foundOdd = false;
		for (int count : table) {
			if (count % 2 == 1) {
				if (foundOdd) {		//already have found odd number of character once
					return false;
				}
				foundOdd = true;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		String pali = "Rats live on no evil star";
		System.out.println(isPermutationOfPalindrome(pali));
	}
}

/*
A:
We can't optimize the big O time here since any algorithm will always have to look through the entire
string. However, we can make some smaller incremental improvements. Because this is a relatively simple
problem, it can be worthwhile to discuss some small optimizations or at least some tweaks.
Instead of checking the number of odd counts at the end, we can check as we go along. Then, as soon as
we get to the end, we have our answer.
It's important to be very clear here that this is not necessarily more optimal. It has the same big O time and
might even be slightly slower. We have eliminated a final iteration through the hash table, but now we have
to run a few extra lines of code for each character in the string.
You should discuss this with your interviewer as an alternate, but not necessarily more optimal, solution.
*/
package Q1_04_Palindrome_Permutation;

public class QuestionB {	

	public static boolean isPermutationOfPalindrome(String phrase) {
		int countOdd = 0;
		int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
		for (char c : phrase.toCharArray()) {
			int x = Common.getCharNumber(c);
			if (x != -1) {
				table[x]++;
				if (table[x] % 2 == 1) {
					countOdd++;
				} else { 		
					countOdd--;	// IMP to reduce countOdd
				}
			}
		}
		return countOdd <= 1;
	}
	
	public static int getCharNumber(Character c) {
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		
		int val = Character.getNumericValue(c);
		if (a <= val && val <= z) {
			return val - a;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		String pali = "Ratzs live on no evil starz";
		System.out.println(isPermutationOfPalindrome(pali));
		String pali2 = "Zeus was deified, saw Suez";
		System.out.println(isPermutationOfPalindrome(pali2));
	}
}

/*
Solution #3
If you think more deeply about this problem, you might notice that we don't actually need to know the
counts. We just need to know if the count is even or odd. Think about flipping a light on/off (that is initially
off). If the light winds up in the off state, we don't know how many times we flipped it, but we do know it
was an even count.
Given this, we can use a single integer (as a bit vector). When we see a letter, we map it to an integer
between 0 and 26 (assuming an English alphabet). Then we toggle the bit at that value. At the end of the
iteration, we check that at most one bit in the integer is set to 1.
We can easily check that no bits in the integer are 1: just compare the integer to 0. There is actually a very
elegant way to check that an integer has exactly one bit set to 1.
Picture an integer like 00010000. We could of course shift the integer repeatedly to check that there's only
a single 1. Alternatively, if we subtract 1 from the number, we'll get 00001111. What's notable about this
is that there is no overlap between the numbers (as opposed to say 00101000, which, when we subtract 1
from, we get 00100111.) So, we can check to see that a number has exactly one 1 because if we subtract 1
from it and then AND it with the new number, we should get 0.
00010000 - 1 = 00001111
00010000 & 00001111 = 0
Like the other solutions, this is O(N).
It's interesting to note a solution that we did not explore. We avoided solutions along the lines of"create
all possible permutations and check if they are palindromes:'While such a solution would work, it's entirely
infeasible in the real world. Generating all permutations requires factorial time (which is actually worse than
exponential time), and it is essentially infeasible to perform on strings longer than about 10-15 characters.
I mention this (impractical) solution because a lot of candidates hear a problem like this and say, "In order
to check if A is in group B, I must know everything that is in B and then check if one of the items equals A:'
That's not always the case, and this problem is a simple demonstration of it. You don't need to generate all
permutations in order to check if one is a palindrome.
*/
package Q1_04_Palindrome_Permutation;

public class QuestionC {
	/* Toggle the ith bit in the integer. */
	public static int toggle(int bitVector, int index) {
		if (index < 0) return bitVector;
		
		int mask = 1 << index;
		if ((bitVector & mask) == 0) {
			bitVector |= mask;
		} else {
			bitVector &= ~mask;
		}
		return bitVector;
	}
	
	/* Create bit vector for string. For each letter with value i,
	 * toggle the ith bit. */
	public static int createBitVector(String phrase) {
		int bitVector = 0;
		for (char c : phrase.toCharArray()) {
			int x = Common.getCharNumber(c);
			bitVector = toggle(bitVector, x);
		}
		return bitVector;
	}
	
	/* Check that at most one bit is set by subtracting one from the 
	 * integer and ANDing it with the original integer. */
	public static boolean checkAtMostOneBitSet(int bitVector) {
		return (bitVector & (bitVector - 1)) == 0;
	}
	
	public static boolean isPermutationOfPalindrome(String phrase) {
		int bitVector = createBitVector(phrase);
		return checkAtMostOneBitSet(bitVector);
	}
	
	public static int getCharNumber(Character c) {
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		
		int val = Character.getNumericValue(c);
		if (a <= val && val <= z) {
			return val - a;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		String pali = "Rats live on no evil star";
		System.out.println(isPermutationOfPalindrome(pali));
	}
}
