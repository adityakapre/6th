/*
HashMap
-------

The HashMap class is outstanding for insertions, removals, and searches, on average, 
but if you also need a sorted collection, you should probably use a TreeMap Instead. 
In trying to decide on fields for the HashMap class, you might at first be tempted 
to bring back a contiguous or linked design from a previous chapter. 
But if the elements are unordered, we will need sequential searches, and these take 
linear time. Even if the elements are ordered and we utilize that ordering, 
the best we can get is logarithmic time for searching.

The rest of this chapter is devoted to showing how, through the miracle of hashing, 
we can achieve searches—and insertions and removals—in constant time, on average.
*/

transient Entry[] table; // an array of entries;
transient int size; // number of mappings in the HashMap object

/*
Java transient keyword is used in serialization. 
If you define any data member as transient, it will not be serialized.

You might already have noticed a potential pitfall with this scheme: 
two distinct keys might produce the same index. For example, 214-30-3261 and 
323-56-8157 both produce the index 541. Such a phenomenon is called a collision, 
and the colliding keys are called synonyms. We will deal with collisions shortly. 
For now we simply acknowledge that the possibility of collisions always exists 
when the size of the key space, that is, the number of legal key values, 
is larger than the table capacity.

Hashing is the process of transforming a key into an index in a table. 

One component of this transformation is the key class’s hashCode() method.

The hashCode() method in the Object class will most likely return the reference itself, 
that is, the machine address, as an int. Then the hashCode() method applied 
to two equivalent objects would return different int values!
*/
/*
* @return a hash code value for this String object.
*/
public int hashCode()
{
	int h = 0;
	int off = offset; // index of first character in array value
	char val[] = value; // value is the array of char that holds the String
	int len = count; // count holds the number of characters in the String
	for (int i = 0; i < len; i++)
		h = 31*h + val[off++];
	return h;
} // method hashCode

/*
The multiplication of partial sums by 31 increases the likelihood that the 
int value returned will be greater than table.length, so the resulting indexes 
can span the entire table.

In the HashMap class, the hashCode() method for a key is supplemented 
by a static hash method whose only parameter is the int returned by the 
hashCode() method. The hash function scrambles that int value. 
In fact, the basic idea of hashing is to “make hash” out of the key. 
The additional scrambling is accomplished with a few right shifts 
and bit-wise “exclusive-or” operators (return 1 if the two bits are different, 
otherwise 0).

Here is the hash method:
*/

static int hash(int h) {
	h ∧= (h >>> 20) ∧ (h >>> 12);
	return h ∧ (h >>> 7) ∧ (h >>> 4);
} // method hash

/*
This value is bit-wise “anded” with table.length - 1 to obtain an index 
in the range from 0 to table.length - 1. 
For example, if table.length = 1024,
84042326 & 1023 Returns 598


       hash (key.hashCode()) & table.length - 1
key −−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−> index

One of the requirements of the HashMap class is that table.length must be 
a power of 2. (This requirement is specific to the Java Collections Framework,
and does not apply generally.) 
For example, if table.length = 1024 = 2^10, then the index returned by
hash (key.hashCode()) & table.length - 1
consists of the rightmost 10 bits of hash (key). 

Because a poor-quality hashCode() method may not adequately distinguish 
the rightmost bits in the keys, the additional scrambling from the bit-wise 
operators in the hash method is usually enough to prevent a large number 
of collisions from occurring.

You may have noticed that, because the table size is a power of 2, the result of
hash (key.hashCode()) & table.length - 1
is the same as the result of
hash (key.hashCode()) % table.length

The advantage of the former expression is that its calculation is quite a 
bit faster than for the latter expression.
Modular arithmetic is computationally expensive, but is commonly utilized in hashing.

The Uniform Hashing Assumption
------------------------------
For each value of key in an application, a table index is calculated as

int hash = hash (key.hashCode()),
index = hash & table.length - 1; // table.length will be a power of 2

Hashing is most efficient when the values of index are spread throughout the table. 
This notion is referred to as the Uniform Hashing Assumption. 
Probabilistically speaking, the Uniform Hashing Assumption states that 
the set of all possible keys is uniformly distributed over the set of all table indexes. 
That is, each key is equally likely to hash to any one of the table indexes. 
No class’s hashCode method will satisfy the Uniform Hashing Assumption for all applications, 
although the supplemental scrambling in the hash function makes it extremely likely 
(but not guaranteed) that the assumption will hold.

Even if the Uniform Hashing Assumption holds, we must still deal with 
the possibility of collisions. That is, two distinct keys may hash to the same index. 
In the HashMap class, collisions are handled by a simple but quite effective technique 
called “chaining”.

Chaining
--------
To resolve collisions, we store at each table location the singly-linked list 
of all elements whose keys have hashed to that index in table. 
This design is called chained hashing because the elements in each list form a chain.
*/

static class Entry<K,V> implements Map.Entry<K,V> {
	final K key;
	V value;
	final int hash; // to avoid re-calculation
	Entry<K,V> next; // reference to next Entry in linked list
}

/*
The final modifier mandates that the key and hash fields can be assigned to only once. 
A singly-linked list, instead of the LinkedList class, is used to save space: 
There is no header, and no previous field

If the keys were chosen randomly, it is unlikely there would have been any collisions, 
so there would have been eight linked lists, each with a single entry. 
Of course, if the number of entries is large enough, there will be collisions and 
multi-entry lists. And that raises an interesting question: Should the table be subject 
to re-sizing? Suppose the initial table length is 1024. If n represents the number of 
entries and n can continue to increase, the average size of each linked list will be n/1024, 
which is linear in n. But then, for searching, inserting, and removing, worstTime(n) 
will be linear in n — a far cry from the constant time that was promised earlier.

Given that re-sizing must be done under certain circumstances, what are those circumstances? 
We will re-size whenever the size of the map reaches a pre-set threshold. 
In the HashMap class, the default threshold is 75% of table.length. 
So when size is >= (int)(table.length * 0.75), the table will be re-sized. 
That means that the average size of each linked list will be less than one, and that is 
how constant average time can be achieved for inserting, deleting, and searching.

There are two additional fields relating to this discussion: 
loadFactor (how large can the ratio of size to table length get before resizing will occur) 
and threshold (how large can size get before resizing will occur).
*/

/**
* The default initial capacity - MUST be a power of two.
*/
static final int DEFAULT_INITIAL_CAPACITY = 16;
/**
* The maximum capacity, 2 to the 30th power, used if a higher value
* is implicitly specified by either of the constructors with arguments.
*
*/
static final int MAXIMUM_CAPACITY = 1 << 30; // = 230
/**
* The load factor used when none specified in constructor.
*/
static final float DEFAULT_LOAD_FACTOR = 0.75f;

/*

The value for loadFactor presents an interesting time-space tradeoff. 
With a low value (say, less than 1.0), searches and removals are fast, and insertions 
are fast until an insertion triggers a re-sizing. That insertion will require 
linear-in-n time and space. 
On the other hand, with a high value for loadFactor, searches, removals, and insertions 
will be slower than with a low loadFactor, but there will be fewer re-sizings. 
Similary, if table.length is large, there will be fewer re-sizings, but more space consumed. 
Ultimately, the application will dictate the need for speed (how small a load factor) 
and the memory available (how large a table).

Before we leave this section on the implementation of the HashMap class, there is an 
important detail about insertions that deserves mention. 
When a re-sizing takes place, that is, when size++ >= threshold
the table is doubled in size. But the old entries cannot simply be copied to the new table: 
They must be re-hashed. 
Why? 
Because the index where an entry is stored is calculated as
hash & table.length –1

When table.length changes, the index of the entry changes, so a re-hashing is required. 

If the hashCode method is inappropriate for the keys in the application, the additional 
scrambling in the hash function may not be enough to prevent an inordinate number of keys 
to hash to just a few locations, leading to linear-in-n searches. 
Even if the Uniform Hashing Assumption holds, we could have a worst-case scenario: 
the given key hashes to index, and the number of keys at table [index] is linear in n. 
So worstTime (n,m), for both successful and unsuccessful searches, is linear in n. 
The independence from m allows us to write that worstTime(n) is linear in n for both 
successful and unsuccessful searches for the containsKey method.

Similarly, for the get and remove methods, worstTime(n) is linear in n. 
What about worstTime(n,m) for the put method? In the worst case, the size of the underlying 
table will be at the threshold, so we will need to double the size of the table, and then 
iterate through all m of the linked lists (many will be empty) to re-hash the n elements 
into the new table. So it appears that worstTime(n,m) is (n + m). 

But at the threshold, n/m = loadFactor (a constant), so m = n/loadFactor. Then
worstTime(n,m) = worstTime(n) = (n + n/loadFactor), since m is a function of n. 
We conclude that worstTime(n) is (n), or in plain English, worstTime(n) is linear in n. 
The bottom line is this: unless you are confident that the Uniform Hashing Assumption is 
reasonable for the key space in the application or you are not worried about linear-in-n worst time, 
use a TreeMap object, with its guarantee of logarithmic-in-n worst time 
for searching, inserting, and deleting.

The HashSet Class
With hashing, all of the work involves the key-to-index relationship. 
It doesn’t matter if the value associated with a key has meaning in the application 
or if each key is associated with the same dummy value. 
In the former case, we have a HashMap object and in the latter case, we have a HashSet object. 
The method definitions in the HashSet class are almost identical to those of the TreeSet class 
from Chapter 12. 
The major difference—besides efficiency—is that several of the constructor headings for the 
HashSet class involve the initial capacity and load factor.

As indicated, the Java Collections Framework’s implementation of hashing 
uses chaining to handle collisions.

Open-Address Hashing (optional)
-------------------------------
To handle collisions with chaining, the basic idea is this: 
when a key hashes to a given index in table, that key’s entry is inserted at the front 
of the linked list at table [index]. Each entry contains, not only hash, key, and value fields, 
but a next field that points to another entry in the linked list. 
The total number of Entry references is equal to size + table.length. 
For some applications, this number may be too large.

Open addressing provides another approach to collision handling. 
With open addressing, each table location contains a single entry; there are no linked lists, 
and the total number of Entry references is equal to table.length. 
To insert an entry, if the entry’s key hashes to an index whose entry contains a different key, 
the rest of the table is searched systematically until an empty—that is, “open”—location is found. 
The simplest open-addressing strategy is to use an offset of 1. That is, to insert an entry whose 
key hashes to index j, if table [j] is empty, the entry is inserted there. Otherwise, the entry 
at index j + 1 is investigated, then at index j + 2, and so on until an open slot is found. 
We will not require that the table length be a power of two. 
In fact, we will see in Section 14.5.2 that we may want the table length to be a prime number.

There are a couple of minor details with open addressing:
a. To ensure that an open location will be found if one is available, the table must wrap around: 
   If the location at index table.length - 1 is not open, the next index tried is 0.
b. The number of entries cannot exceed the table length, so the load factor cannot exceed 1.0. 

It will simplify the implementation and efficiency of the containsKey, put, and remove methods 
if the table always has at least one open (that is, empty) location. 
So we require that the load factor be strictly less than 1.0. 
Recall that with chaining, the load factor can exceed 1.0.

The remove Method

we want to remove the entry with key 033-52-0048 from the table in Figure 14.7. 
If we simply make that entry null, we will get the table in Figure 14.8. 
Do you see the pitfall with this removal strategy? 
The path taken by synonyms of 033-52-0048 has been blocked. 
A search for the entry with key 214-30-3495 would be unsuccessful, even though there is such 
an entry in the table.
Instead of nulling out a removed entry, we will add another field to the Entry class:

	boolean markedForRemoval;

This field is initialized to false when an entry is inserted into the table. 
The remove method sets this field to true . The markedForRemoval field, when true , 
indicates that its entry is no longer part of the hash map, but allows the offset-of-1 collision 
handler to continue along its path. Note that an entry’s key is examined only if that entry 
is not marked for removal. The containsKey method loops until an empty or matching entry is found. 
As with the remove method, an entry’s key is checked only if that entry is not marked for removal. 
The definition of the containsKey method is only slightly revised from the chained-hashing version. 
For example, here we use modular arithmetic instead of the & operator because the table length 
need not be a power of 2.
*/
/**
* Determines if this HashMap object contains a mapping for the
* specified key.
* The worstTime(n) is O(n). If the Uniform Hashing Assumption holds,
* averageTime(n) is constant.
*
* @param key The key whose presence in this HashMap object is to be tested.
*
* @return true - if this map contains a mapping for the specified key.
*
*/
public boolean containsKey (Object key) {
	Object k = maskNull (key); // use NULL_KEY if key is null
	int hash = hash (k);
	int i = indexFor (hash, table.length);
	Entry e = table [i];
	while (e != null) {
		if (!e.markedForRemoval && e.hash == hash && eq(k, e.key))
		return true;
		e = table [(++i) % table.length]; // table.length may not be a power of 2
	} // while
	return false;
} // method containsKey

/*
With the help of the markedForRemoval field, we solved the problem of removing an element 
without breaking the offset-of-1 path. The put method hashes a key to an index and stores 
the key and value at that index if that location is unoccupied: either a null key or 
marked for removal.

Primary Clustering
------------------
There is still a disturbing feature of the offset-of-1 collision handler: 
all the keys that hash to a given index will probe the same path: index, index + 1, index + 2, 
and so on. What’s worse, all keys that hash to any index in that path will follow the same path 
from that index on. 
For example, Figure 14.11 shows part of the table from Figure 14.9: In Figure 14.11, 
the path traced by keys that hash to 212 is 212, 213, 214, 215, . . . . And the path traced 
by keys that hash to 213 is 213, 214, 215, . . . . 
A cluster is a sequence of non-empty locations (assume the elements at those locations are 
not marked for removal). 
With the offset-of-1 collision handler, clusters are formed by synonyms, including synonyms 
from different collisions. 
In Figure 14.11, the locations at indexes 212, 213, and 214 form a cluster. 
As each entry is added to a cluster, the cluster not only gets bigger, but also grows faster, 
because any keys that hash to that new index will follow the same path as keys already stored 
in the cluster. 
Primary clustering is the phenomenon that occurs when the collision handler allows the growth 
of clusters to accelerate.
Clearly, the offset-of-1 collision handler is susceptible to primary clustering. 
The problem with primary clustering is that we get ever-longer paths that are sequentially 
traversed during searches, insertions, and removals. Long sequential traversals are the 
bane of hashing, so we should try to solve this problem.

What if we choose an offset of, say, 32 instead of 1? We would still have primary clustering: 
keys that hashed to index would overlap the path traced by keys that hashed to index + 32, 
index + 64, and so on. 
In fact, this could create an even bigger problem than primary clustering. 
For example, suppose the table size is 128 and a key hashes to index 45. 
Then the only locations that would be allowed in that cluster have the following indexes:
45, 77, 109, 13, 45 ...

Once those locations fill up, there would be no way to insert any entry whose key hashed 
to any one of those indexes. The reason we have this additional problem is that the offset 
and table size have a common factor. We can avoid this problem by making the table size 
a prime number, instead of a power of 2. 

But then we would still have primary clustering.

Double Hashing
--------------
We can avoid primary clustering if, instead of using the same offset for all keys, 
We make the offset dependent on the key. 
Basically, the offset will be hash/table.length. 
There is a problem here: hash may be negative. 
To remedy this, we perform a bit-wise “and” with hash and the largest positive 
int value, written as 
0x7FFFFFFF (0111 1111 1111 1111 1111 1111 1111 1111)  in hexadecimal (base 16) notation. 
The result is guaranteed to be nonnegative.
The assignment for offset is
	int offset = (hash & 0x7FFFFFFF) / table.length;

Because the table size must be a prime number, the call to the resize method—within 
the put method—must be changed from the offset-of-1 version:
	resize (2 * table.length);

With the quotient-offset collision handler, we have
	resize (nextPrime (2 * table.length));
The static method nextPrime returns the smallest prime larger than the argument.

Chaining:
----------
averageTimeS (n,m) ≈ n/2m iterations
averageTimeU (n,m) ≈ n/m iterations

Double Hashing:
--------------
averageTimeS (n,m) ≈ (m/n) ln(1/(1 − n/m)) iterations
averageTimeU (n,m) ≈ 1/(1 − n/m) iterations

ABOVE Estimates of average times for successful and unsuccessful calls to 
the containsKey method, under both chaining and double hashing. 
In the figure, n = number of elements inserted; m = table.length

A cursory look at Figure 14.14 suggests that chained hashing is much 
faster than double hashing.

For open addressing, we eliminated the threat of primary clustering with double hashing. 
Another way to avoid primary clustering is through quadratic probing: 
the sequence of offsets is 12, 22, 32, . . . .

SUMMARY
--------
HashMap class, for which key-searches, insertions, and removals can be very fast, on average. 
This exceptional performance is due to hashing, the process of transforming a key into an
index in a table. A hashing algorithm must include a collision handler for the possibility 
that two keys might hash to the same index. 
A widely used collision handler is chaining. With chaining, the HashMap object is represented 
as an array of singly linked lists. Each list contains the elements whose keys hashed to that 
index in the array. 
Let n represent the number of elements currently in the table, and let m represent the 
capacity of the table. 
The load factor is the maximum ratio of n to m before rehashing will take place. 

The load factor is an upper-bound estimate of the average size of each list, 
assuming that the hash method scatters the keys uniformly throughout the table. 
With that assumption—the Uniform Hashing Assumption—the average time for successful and 
unsuccessful searches depends only on the ratio of n to m. 
The same is true for insertions and removals. If we double the table size whenever the ratio 
of n to m equals or exceeds the load factor, then the size of each list, on average, will
be less than or equal to the load factor. 
This shows that with chained hashing, the average time to insert, remove,or search is constant.

A HashSet object is simply a HashMap in which each key has the same dummy value. 
Almost all of the HashSet methods are one-liners that invoke the corresponding HashMap method.
An alternative to chaining is open addressing. When open addressing is used to handle collisions, 
each location in the table consists of entries only; there are no linked lists. 
If a key hashes to an index at which another key already resides, the table is searched 
systematically until an open address, that is, empty location is found. 
With an offset of 1, the sequence searched if the key hashes to index, is index, index + 1, 
index + 2, . . .,table.length - 1, 0, 1, . . ., index - 1.
The offset-of-1 collision handler is susceptible to primary clustering: 
the phenomenon of having accelerating growth in the size of collision paths. 
Primary clustering is avoided with double hashing: 
the offset is the (positivized) hash value divided by table.length. 
If the Uniform Hashing Assumption holds and the table size is prime, the average time for 
both successful and unsuccessful searches with double hashing is constant.
*/
