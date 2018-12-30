/*
This problem asks us to group the strings in an array such that the anagrams appear next to each other.
Note that no specific ordering of the words is required, other than this.
We need a quick and easy way of determining if two strings are anagrams of each other. What defines if two
words are anagrams of each other? Well, anagrams are words that have the same characters but in different
orders. It follows then that if we can put the characters in the same order, we can easily check if the new
words are identical.
One way to do this is to just apply any standard sorting algorithm, like merge sort or quick sort, and modify
the comparator. This comparator will be used to indicate that two strings which are anagrams of each other
are equivalent.
What's the easiest way of checking if two words are anagrams? We could count the occurrences of the
distinct characters in each string and return true if they match. Or, we could just sort the string. After all,
two words which are anagrams will look the same once they're sorted.

This algorithm will take O(n log(n)) time.

This may be the best we can do for a general sorting algorithm, but we don't actually need to fully sort the
array. We only need to group the strings in the array by anagram.
We can do this by using a hash table which maps from the sorted version of a word to a list of its anagrams.
So, for example, acre will map to the list {acre, race, care}. Once we've grouped all the words into
these lists by anagram, we can then put them back into the array.

*/
package Q10_02_Group_Anagrams;

import java.util.ArrayList;
import java.util.Arrays;

import CtCILibrary.AssortedMethods;
import CtCILibrary.HashMapList;

public class QuestionB {
	public static void sort(String[] array) {
		HashMapList<String, String> mapList = new HashMapList<String, String>();
		
		/* Group words by anagram */
		for (String s : array) {
			String key = sortChars(s); 
			mapList.put(key, s);
		}
		
		/* Convert hash table to array */
		int index = 0;
		for (String key : mapList.keySet()) {
			ArrayList<String> list = mapList.get(key);
			for (String t : list) {
				array[index] = t;
				index++;
			}
		}
	}
	
	public static String sortChars(String s) {
		char[] content = s.toCharArray();
		Arrays.sort(content);
		return new String(content);
	}
	
	public static void main(String[] args) {
		String[] array = {"apple", "banana", "carrot", "ele", "duck", "papel", "tarroc", "cudk", "eel", "lee"};
		sort(array);
		System.out.println(AssortedMethods.stringArrayToString(array));
	}
}

public class HashMapList<T, E> {
	private HashMap<T, ArrayList<E>> map = new HashMap<T, ArrayList<E>>();
	
	/* Insert item into list at key. */
	public void put(T key, E item) {
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<E>());
		}
		map.get(key).add(item);
	}
	
	/* Insert list of items at key. */
	public void put(T key, ArrayList<E> items) {
		map.put(key, items);
	}
	
	/* Get list of items at key. */
	public ArrayList<E> get(T key) {
		return map.get(key);
	}
	
	/* Check if hashmaplist contains key. */
	public boolean containsKey(T key) {
		return map.containsKey(key);
	}
	
	/* Check if list at key contains value. */
	public boolean containsKeyValue(T key, E value) {
		ArrayList<E> list = get(key);
		if (list == null) return false;
		return list.contains(value);
	}
	
	/* Get the list of keys. */
	public Set<T> keySet() {
		return map.keySet();
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
