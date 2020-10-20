/*
13.5 TreeMap, HashMap, LinkedHashMap: Explain the differences between TreeMap, HashMap, and
LinkedHashMap. Provide an example of when each one would be best.


SOLUTION
--------
All offer a key->value map and a way to iterate through the keys. The most important distinction between
these classes is the time guarantees and the ordering of the keys.
HashMap offers 0(1) lookup and insertion. If you iterate through the keys, though, the ordering of the
keys is essentially arbitrary. It is implemented by an array of linked lists.
• TreeMap offers O(log N) lookup and insertion. Keys are ordered, so if you need to iterate through
the keys in sorted order, you can. This means that keys must implement the Comparable interface.
TreeMap is implemented by a Red-Black Tree.
LinkedHashMap offers 0(1) lookup and insertion. Keys are ordered by their insertion order. It is
implemented by doubly-linked buckets.
Imagine you passed an empty TreeMap, HashMap, and LinkedHashMap into the following function:

1 void insertAndPrint(AbstractMap<Integer, String> map) {
2 int[] array= {1, -1, 0};
3 for (int x : array) {
4 map.put(x, Integer.toString(x));
5 }
6
7 for (int k: map.keySet()) {
8 System.out.print(k + ", ");
g }
10 }

The output for each will look like the results below.

HashMap : any ordering
LinkedHashMap : {-1, 1, 0}
TreeMap: {-1, 0, 1}

Very important: The output of LinkedHashMap and TreeMap must look like the above. For HashMap,
the output was, in my own tests, { 0, 1, -1}, but it could be any ordering. There is no guarantee on the
ordering.
When might you need ordering in real life?
Suppose you were creating a mapping of names to Person objects. You might want to periodically
output the people in alphabetical order by name. A TreeMap lets you do this.
• A TreeMap also offers a way to, given a name, output the next 10 people. This could be useful for a
"More"function in many applications.
A LinkedHashMap is useful whenever you need the ordering of keys to match the ordering of insertion.
This might be useful in a caching situation, when you want to delete the oldest item.
A special constructor is provided to create a linked hash map whose order of iteration is the order in which its entries 
were last accessed, from least-recently accessed to most-recently (access-order). 
This kind of map is well-suited to building LRU caches.
Generally, unless there is a reason not to, you would use HashMap. That is, if you need to get the keys back
in insertion order, then use LinkedHashMap. If you need to get the keys back in their true/natural order,
then use TreeMap. Otherwise, HashMap is probably best. It is typically faster and requires less overhead.
*/
