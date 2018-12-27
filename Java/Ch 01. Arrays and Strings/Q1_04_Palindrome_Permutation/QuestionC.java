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
