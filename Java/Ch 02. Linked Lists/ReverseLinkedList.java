/*
Q. Code writing: write a function to reverse a linked list 

Iterative Method

prev -> current -> next

Iterate through the linked list. In loop, change next to prev, prev to current and current to next.          

Key things to remember is during iteration, onlu modify 1 link, i.e current.next=prev
*/

static void reverse(Node head) {
    if(null == head) return;
    Node prev   = null;
    Node current = head;
    Node next = null;
    while (current != null) {
        next  = current.next; 
        current.next = prev; //change only 1 next link at a time  
        prev = current;
        current = next;
    }
    head = prev; //head is prev since current is null here - while loop condition
}

/*
2. Recursive Method:
   	1) Divide list in 2 parts - first node & rest list 
   	2) Recursively Call reverse for rest  
3) Link rest to first. 
4) Fix head
*/

void recursiveReverse(Node head) {
    Node first; Node rest;
    if (head == null) return;  /* empty list */
    /* suppose first = {1, 2, 3}, then rest = {2, 3} */
    first = head; 
    rest  = first.next;
    if (rest == null) return;  /* List has only one node */
    recursiveReverse(rest); 	/* reverse the rest list and put the first element at the end */
    first.next.next  = first; 
    first.next = null;      	/* tricky step -- see the diagram */
    head = rest;          	 /* fix the head pointer */
}
