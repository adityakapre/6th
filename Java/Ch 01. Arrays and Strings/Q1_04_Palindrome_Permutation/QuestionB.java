/*
Solution #2
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
					countOdd--;
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
