/**
 ** Java Program to implement Priority Queue
 
Children of the element at position i are at positions (i << 1) + 1 and (i << 1) + 2 i.e (2i+1) & (2i+2)
In general, if i is a positive integer, the position of the parent of the element in position i 
is in position (i − 1) >> 1 i.e (i-1)/2; we use a right shift of one bit instead of division by 2.

In below program, we start heap at index 1
So Children of the element at position i are at 2i & 2i+1
Parent of the element in position i is at i/2

                       1
                     /   \
                    2     3
                   / \   / \
                  4  5  6   7
                 / \
                8   9
                
Above heap we are starting from index 1 in array.
so parent(8) = (int) 8/2 = 4 = i/2
so parent(9) = (int) 9/2 = 4 = i/2

child(4) = 2*4 = 8 = 2i
child(4) = 2*4 + 1 = 9 = 2i+1

So for simplicity, below program starts with index 1 rather than index 0

 **/
 
import java.util.Scanner;
 
/** class Task **/
class Task
{
   String job;
   int priority;
 
   /** Constructor **/
   public Task(String job, int priority)
   {
       this.job = job;
       this.priority = priority;
   }
   /** toString() **/
   @Override
   public String toString()
   {
       return "Job Name : "+ job +"\nPriority : "+ priority;
   }
}
 
/** Class PriorityQueue **/
class PriorityQueue
{
   private Task[] heap;
   private int heapSize, capacity;
 
   /** Constructor **/
   public PriorityQueue(int capacity)
   {   
       this.capacity = capacity + 1;
       heap = new Task[this.capacity];
       heapSize = 0;
   }
   /** function to clear **/
   public void clear()
   {
       heap = new Task[capacity];
       heapSize = 0;
   }
   /** function to check if empty **/
   public boolean isEmpty()
   {
       return heapSize == 0;
   }
   /** function to check if full **/
   public boolean isFull()
   {
       return heapSize == capacity - 1;
   }
   /** function to get Size **/
   public int size()
   {
       return heapSize;
   }
   /** function to insert task **/
   public void insert(String job, int priority)
   {
       Task newJob = new Task(job, priority);
       //first element inserted at "1" as preincrement done (++heapSize)
       //new element inserted at last available location in array as per definition of heap
       heap[++heapSize] = newJob;
       //start with child position at "heapSize" (i.e end) and start checking its parent at pos/2
       int pos = heapSize;
       //repeat till parent < child i.e heap[pos/2] < newJob since this is maxHeap
       while (pos != 1 && newJob.priority > heap[pos/2].priority) {
           heap[pos] = heap[pos/2]; //do child=parent (bring parent down)
           pos /=2;
       }
       heap[pos] = newJob;   
   }
   /** function to remove task **/
   public Task remove() {
       int parent, child;
       Task item, temp;
       if (isEmpty() )
       {
           System.out.println("Heap is empty");
           return null;
       }
       //remove item from priority queue
       item = heap[1];
       //consider last item in heap
       temp = heap[heapSize--];
       //define & start with 1st parent child
       parent = 1;
       child = 2;
       while (child <= heapSize)
       {
           //select max priority between child & child+1
           if (child < heapSize && heap[child].priority < heap[child + 1].priority)
               child++;
           if (temp.priority >= heap[child].priority)
               break;
           //copy greater child (from child & child+1) to empty space at parent (bubble up)
           //bring larger child up
           heap[parent] = heap[child];
           //repeat for empty place created at (child or child+1) after above step
           parent = child;
           child = parent*2;
       }
       heap[parent] = temp;
       return item;
   }
}
