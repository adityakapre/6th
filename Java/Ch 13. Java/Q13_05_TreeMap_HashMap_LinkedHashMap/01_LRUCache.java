/*
Q. Design a LRU cache
A cache is a mechanism by which future requests for that data are served faster and/or at a lower cost. 

Requirements
-------------
- Fixed size: The cache needs to have some bounds to limit memory usage.
- Fast access: The cache insert and lookup operations need to be fast preferably O(1) time.

Entry replacement algo: When cache is full, less useful cache entries are purged from cache. 
Algo to replace entries is Least Recently Used (LRU) - or cache entries which have not been accessed recently will be replaced.

Design discussion
------------------
Since the lookup and insert operations need to be fast a HashMap would be a good candidate. 
The HashMap accepts an initial capacity parameter but it re-sizes itself if more entries are inserted. 
So we need to override the put() operation and remove (or purge) an entry before inserting. 
How do we select the entry to be purged? One approach is to maintain  a timestamp at which the entry was inserted 
and select the entry with the oldest timestamp. But this search would be linear taking O(N) time.
So we need entries to be maintained in sorted list based on order in which entries were accessed. 
An alternate way to achieve this would be to maintain entries in doubly linked list using which 
everytime an entry is accessed (a cache lookup operation), entry is also moved to end of list. 
When we need to purge entries it is done from top (start) of list. In ArrayList when element is removed 
rest of entries need to be moved by one to fill gap. A doubly linked list does not have this issue.
We have come up with design that meets our requirements and guarantees O(1) insert and O(1) lookup operations 
and also has configurable limit on no of entries. 
JDK already provides a class that is suitable for our purpose - LinkedHashMap. 
This class maintains entries in HashMap for fast lookup at the same time maintains doubly linked list of entries 
either in AccessOrder or InsertionOrder. This is configurable so use AccessOrder as true. 
It also has method removeEldestEntry() which we can override to return true when cache size exceeds capacity(upper limit). 
We can put key = element and value = element
*/

public class LRUCache extends LinkedHashMap<Integer, Integer> {
   
    private int capacity;

    public LRUCache(int capacity) {
        //constructor:LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
        super(capacity+1, 1.0f, true);  // for access order & not insertion order
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(super.get(key) == null) return -1;
        else return ((int) super.get(key));
    }
    
    public void set(int key, int value) {
        super.put(key, value);
    }
    
    // This override will allow map to grow up to {#capacity} entries and then delete eldest 
    // entry each time new entry is added, maintaining a steady state of {#capacity} entries.   
    // if we do not override, map will just keep on growing as usual whenever we add entries and 
    // its capacity is reached. So return true if capacity is reached to remove eldest entry
    @override
    protected boolean removeEldestEntry(Entry entry) {
        return (size() > this.capacity);
    }
}

/*
A structural modification is any operation that adds or deletes one or more mappings or, 
in the case of access-ordered linked hash maps, affects iteration order. 
In insertion-ordered linked hash maps, merely changing the value associated with a key 
that is already contained in the map is not a structural modification. 
In access-ordered linked hash maps, merely querying the map with get is a structural modification.

Note that this implementation is not synchronized. 
If multiple threads access a linked hash map concurrently, and at least one of the threads modifies 
the map structurally, it must be synchronized externally. This is typically accomplished by synchronizing 
on some object that naturally encapsulates the map. If no such object exists, the map should be "wrapped" 
using the Collections.synchronizedMap method. This is best done at creation time, 
to prevent accidental unsynchronized access to the map:
*/

Map m = Collections.synchronizedMap(new LinkedHashMap(...));
