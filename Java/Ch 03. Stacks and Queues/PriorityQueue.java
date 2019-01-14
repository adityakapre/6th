/**
 ** Java Program to implement Priority Queue
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
       heap[++heapSize] = newJob;
       int pos = heapSize;
       //repeat till parent < child i.e heap[pos/2] < newJob since this is maxHeap
       while (pos != 1 && newJob.priority > heap[pos/2].priority)
       {
           heap[pos] = heap[pos/2]; //do child=parent
           pos /=2;
       }
       heap[pos] = newJob;   
   }
   /** function to remove task **/
   public Task remove()
   {
       int parent, child;
       Task item, temp;
       if (isEmpty() )
       {
           System.out.println("Heap is empty");
           return null;
       }
 
       item = heap[1];
       temp = heap[heapSize--];
 
       parent = 1;
       child = 2;
       while (child <= heapSize)
       {
           //if siblingr priority greater, select sibling i.e do child++
           if (child < heapSize && heap[child].priority < heap[child + 1].priority)
               child++;
           if (temp.priority >= heap[child].priority)
               break;
 
           heap[parent] = heap[child];
           parent = child;
           child *= 2;
       }
       heap[parent] = temp;
 
       return item;
   }
}
