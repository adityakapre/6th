package Q16_25_LRU_Cache;

import java.util.HashMap;

public class Cache {
	
	private int maxCacheSize;
	private HashMap<Integer, LinkedListNode> map = new HashMap<Integer, LinkedListNode>();
	private LinkedListNode head = null;
	public LinkedListNode tail = null;
	
	public Cache(int maxSize) {
		maxCacheSize = maxSize;
	}
	
	private class LinkedListNode {
		private LinkedListNode next;
		private LinkedListNode prev;
		public int key;
		public String value;
		
		public LinkedListNode(int k, String v) {
			key = k;
			value = v;
		}
		
		public String printForward() {
			String data = "(" + key + "," + value + ")";
			if (next != null) {
				return data + "->" + next.printForward();
			} else {
				return data;
			}
		}
	}
	
	/* SET OPERATION:
	`* Put key, value pair in cache. Removes old value for key if
	 * necessary. Inserts pair into linked list and hash table.
	 * In map, (K-> integer, V -> LinkedListNode)
	 */
	public void setKeyValue(int key, String value) {
		/* Remove if already there. */
		removeKey(key); 
		
		/* If full, remove least recently used item from cache. 
		   Least recently used item is at the tail
		*/
		if (map.size() >= maxCacheSize && tail != null) {
			removeKey(tail.key);
		}
		
		/* Insert new node. */
		LinkedListNode node = new LinkedListNode(key, value);
		insertAtFrontOfLinkedList(node);
		map.put(key, node);
	}
	
	/* Remove key, value pair from cache, deleting from hash table
	 * and linked list. */
	public boolean removeKey(int key) {
		LinkedListNode node = map.get(key);
		removeFromLinkedList(node);
		map.remove(key);
		return true;
	}
	
	/* Insert node at front of linked list. 
	 * Inserting at front means it was used very recently 
	 * and hence should NOT be removed.
	 */
	private void insertAtFrontOfLinkedList(LinkedListNode node) {
		if (head == null) {
			head = node;
			tail = node;
		} else {
			head.prev = node;
			node.next = head;
			head = node;
			head.prev = null;
		}
	}
	
	/* GET OPERATION:
	 * Get value for key and mark as most recently used. 
	 */
	public String getValue(int key) {
		LinkedListNode item = map.get(key);
		if (item == null) {
			return null;
		}
		
		/* Move to front of list to mark as most recently used. */
		if (item != head) { 
			removeFromLinkedList(item);
			insertAtFrontOfLinkedList(item);
		}
		return item.value;
	}
	
	/* Remove node from linked list. */
	private void removeFromLinkedList(LinkedListNode node) {
		if (node == null) {
			return;
		}
		if (node.prev != null) {
			node.prev.next = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		if (node == tail) {
			tail = node.prev;
		}
		if (node == head) {
			head = node.next;
		}		
	}
	
	public String getCacheAsString() {
		if (head == null) return "";
		return head.printForward();
	}
	
}
