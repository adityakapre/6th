/*
This is very similar to the previous problem, except that now we could potentially have duplicate characters
in the word.
One simple way of handling this problem is to do the same work to check if a permutation has been created
before and then, if not, add it to the list. A simple hash table will do the trick here. This solution will take
O(n!) time in the worst case (and, in fact in all cases).
While it's true that we can't beat this worst case time, we should be able to design an algorithm to beat
this in many cases. Consider a string with all duplicate characters, like aaaaaaaaaaaaaaa. This will take
an extremely long time (since there are over 6 billion permutations of a 13-character string), even though
there is only one unique permutation.
Ideally, we would like to only create the unique permutations, rather than creating every permutation and
then ruling out the duplicates.
We can start with computing the count of each letter (easy enough to get this-just use a hash table). For a
string such as aabbbbc, this would be:
a->2 | b->4 | c->1
Let's imagine generating a permutation of this string (now represented as a hash table). The first choice we
make is whether to use an a, b, or c as the first character. After that, we have a subproblem to solve: find all
permutations of the remaining characters, and append those to the already picked "prefix:'

P(a->2 | b->4 | c->1) ={a + P(a->1 | b->4 | c->1)} + {b + P(a->2 | b->3 | c->1)} + {c + P(a->2 | b->4 | c->0)}
P(a->1 | b->4 | c->1) ={a + P(a->0 | b->4 | c->l)} + {b + P(a->1 | b->3 | c->1)} + {c + P(a->1 | b->4 | c->0)}
P(a->2 | b->3 | c->1) ={a + P(a->1 | b->3 | c->l)} + {b + P(a->2 | b->2 | c->1)} + {c + P(a->2 | b->3 | c->0)}
P(a->2 | b->4 | c->0) ={a + P(a->1 | b->4 | c->0)} + {b + P(a->2 | b->3 | c->0)}
Eventually, we'll get down to no more characters remaining.
*/
package Q8_08_Permutations_With_Dups;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {	
	public static HashMap<Character, Integer> buildFreqTable(String s) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : s.toCharArray()) {
			if (!map.containsKey(c)) {
				map.put(c, 0);
			}
			map.put(c, map.get(c) + 1);
		}
		return map;
	}
	
	public static void printPerms(HashMap<Character, Integer> map, String prefix, int remaining, ArrayList<String> result) {
		if (remaining == 0) {
			result.add(prefix);
			return;
		}
		
		for (Character c : map.keySet()) {
			int count = map.get(c);
			if (count > 0) {
				map.put(c,  count - 1);
				printPerms(map, prefix + c, remaining - 1, result);
				map.put(c,  count);
			}
		}
	}
	
	public static ArrayList<String> printPerms(String s) {
		ArrayList<String> result = new ArrayList<String>();
		HashMap<Character, Integer> map = buildFreqTable(s);
		printPerms(map, "", s.length(), result);
		return result;
	}
	
	public static void main(String[] args) {
		String s = "aabbccc";
		ArrayList<String> result = printPerms(s);
		System.out.println("Count: " + result.size());
		for (String r : result) {
			System.out.println(r);
		}
	}

}
