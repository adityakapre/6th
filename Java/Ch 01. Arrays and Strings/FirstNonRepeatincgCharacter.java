/*
Q. Given a string, find the first char that only appears once. (both iterative & recursion way) 

Sol 1: 
We can use string characters as index and build a count array. Following is the algorithm.
1) Scan the string from left to right and construct the count array.
2) Again, scan string from left to right, check for count of character, if you find element who's count is 1, return it.
In terms of Big O Notation, this means that the algorithm will be O(n2). For very large strings, it’s very likely that 
this algorithm will do close to n2 comparisons if not n2. This is because there’s a higher probability of there being 
more repeated characters in larger strings when compared to smaller strings.

Sol 2
Using a hash table or an array to find the first nonrepeated character in a string
If we use an array or a hashtable, the question is what will we store in the index for the array, or the key for the hashtable? 
Well, because we need to be able to search the data structure by character, the most obvious choice for the key or index 
is the character. Because array indices have to be numeric, we could just convert a given character to an integer and then 
use it as an index. This means for ASCII strings we would have 128 possible values, and for Unicode strings we would 
have 65,536 possible values. But, what do we actually store inside the data structures? Because the problem asks us to find 
the first nonrepeated character, then why don’t we just store the number of times each character appears in the string. 
So, all we have to do is scan the string once and we can get a count for each of the characters that would tell us how many 
times each one appears in the string.
And once we have a count for all of the characters, then all we have to do is scan the data structure for the very 
first “1” that we encounter in the data structure. This would give us the first non-repeated character!
What’s the Big-O Notation of the new solution to finding the first nonrepeated character?
But, is the new algorithm actually an improvement over what we previously had? Well, let’s break it down: 
we scan the entire string once to build the data structure. And then, once we have built the data structure, 
we scan it to find the ‘1’ – which tells us that this is the first nonrepeated character. This means that in the worst case, 
there will be 2 ‘operations’ on each character in a given string, which would be 2n for a string of length n. 
In Big-O Notation, this is O(n), which is a lot more efficient than our previous algorithm which was O(n2).

Hash tables versus arrays
Now the question is whether we should use an array or a hashtable as our data structure? Let’s examine the differences between the two. Hashtables do have a higher lookup overhead when compared to arrays, so that’s one advantage of arrays. But, the biggest difference is in the memory that each data structure would require. A hash table would only need to store the characters that actually exist in the input string – so if a string contains the characters “abcdef”, then a hashtable would only need to store the characters in the string “abcdef”. An array, on the other hand, would need an element for every single possible value of a character. This is because with an array you can not skip indices – so we can not have an array with just 2 elements, where one index is 10 and one index is 99. That’s impossible – you would have to have elements at index 0 and 1 for a 2 element array. And, remember that we have to store the numeric values of the characters in the index position for this problem. This means that if we have a Unicode string, we would need to have 65,536 elements (assuming a 16 bit Unicode encoding) in our array to account for every possible character that could be in the string – whether or not it is actually in the string. This is because we simply do not know what is going to be in the string that’s being passed in – but with an array we have to be ready to accept all possible values. But for an ASCII string, an array would really only need 128 elements, because there are only 128 possible ASCII values.
Because of the memory requirements, arrays would be better when there are not many possible character values (like in an ASCII string) and when the strings are long (because hash tables have a higher lookup overhead than arrays). But, hashtables are much more efficient when strings are smaller in size or when there are lot of possible character values.

sol 3, 4 & 2 code IN ORDER BELOW
*/

public class Programming { 

/* Sol 3
* Using LinkedHashMap to find first non repeated character of String 
* Algorithm : 
* Step 1: get char array and loop through it to build a hash table with char and their count. 
* Step 2: loop LinkedHashMap to find an entry with value 1, that's your first non-repeated 
* character, as LinkedHashMap maintains insertion order. 
*/
public static char getFirstNonRepeatedChar(String str) {
	Map<Character, Integer> counts = new LinkedHashMap<Character, Integer>(str.length());
	for (char c: str.toCharArray()) {
		counts.put(c, counts.containsKey(c) ? counts.get(c) + 1 : 1);
	}
	for (Entry<Character, Integer> entry: counts.entrySet()) {
		if (entry.getValue() == 1) {
			return entry.getKey();
		}
	}
	throw new RuntimeException("didn't find any non repeated Character");
} 

/* Sol 4
* Finds first non repeated character in a String in just one pass. It uses two storages to cut 
* down one iteration, standard space vs time trade-off.Since we store repeated and non-repeated
* character separately, at the end of iteration, first element from List is our first non 
* repeated character from String. List -> non-rep chars, Set -> rep chars
*/
public static char firstNonRepeatingChar(String word) {
	Set<Character> repeating = new HashSet<Character>();
	List<Character> nonRepeating = new ArrayList<Character>();
	for (char c: word.toCharArray()) {
		if (repeating.contains(c)) continue;     // set contains char, its repeating
		if (nonRepeating.contains(c)) {  // set doesn’t contain char but list contains
			nonRepeating.remove((Character) c); // remove from list
			repeating.add(c);                   //add to set
		} else 	nonRepeating.add(c);       //add to list	
	}
	return nonRepeating.get(0);  //get 0th from list
}
 
/* Sol 2
* Using HashMap to find first non-repeated character from String in Java. 
* Algorithm : 
* Step 1 : Scan String and store count of each character in HashMap 
* Step 2 : traverse String and get count for each character from Map. Since we are going through
* String from first to last character, when count for any character is 1, we break, it's first 
* non repeated character. Here order is achieved by going through String again. */
public static char firstNonRepeatedCharacter(String word) {
	HashMap < Character, Integer > scoreboard = new HashMap < > (); 
 	// build table [char -> count] 
 	for (int i = 0; i < word.length(); i++) { 
 		char c = word.charAt(i); 
		scoreboard.put(c, scoreboard.containsKey(c) ? scoreboard.get(c) + 1 : 1); 
 	} 
 	// since HashMap doesn't maintain order, going through string again 
 	for (char c: word.toCharArray()) { 
 		if (scoreboard.get(c) == 1) { 
 			return c; 
 		} 
 	} 
 	throw new RuntimeException("Undefined behaviour"); 
} 
}
