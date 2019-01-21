/*
Let's start with the smallest possible example: n = 1.

	|	             |		|
      -----                  |		|
    ---------                |		|
   -----------               |		|
  -------------              |		|
 ---------------             |		|
        

Case n = 1. Can we move Disk 1 from Tower 1 to Tower 3? Yes.
1. We simply move Disk 1 from Tower 1 to Tower 3.

Case n = 2. Can we move Disk 1 and Disk 2 from Tower 1 to Tower 3? Yes.
1. Move Disk 1 from Tower 1 to Tower 2
2. Move Disk 2 from Tower 1 to Tower 3
3. Move Disk 1 from Tower 2 to Tower 3

Note how in the above steps, Tower 2 acts as a buffer, holding a disk while we move other disks to Tower 3.

Case n = 3. Can we move Disk 1, 2, and 3 from Tower 1 to Tower 3? Yes.
1. We know we can move the top two disks from one tower to another (as shown earlier), so let's assume
we've already done that. But instead, let's move them to Tower 2.
2. Move Disk 3 to Tower 3.
3. Move Disk 1 and Disk 2 to Tower 3. We already know how to do this-just repeat what we did in Step 1.

Case n = 4. Can we move Disk 1, 2, 3 and 4 from Tower 1 to Tower 3? Yes.
1. Move Disks 1, 2, and 3 to Tower 2. We know how to do that from the earlier examples.
2. Move Disk 4 to Tower 3.
3. Move Disks 1, 2 and 3 back to Tower 3.

Remember that the labels of Tower 2 and Tower 3 aren't important. They're equivalent towers. So, moving
disks to Tower 3 with Tower 2 serving as a buffer is equivalent to moving disks to Tower 2 with Tower 3
serving as a buffer.

This approach leads to a natural recursive algorithm. In each part, we are doing the following steps, outlined
below with pseudocode:
1 moveDisks(int n, Tower origin, Tower destination, Tower buffer) {
2 	// Base case 
3 	if (n <= 0) return;
4
s 	// move top n - 1 disks from origin to buffer, using destination as a buffer. 
6 	moveDisks(n - 1, origin, buffer, destination);
7
8 	// move top i.e nth from origin to destination
9 	moveTop(origin, destination);
10
11 	// move top n - 1 disks from buffer to destination, using origin as a buffer. 
12 	moveDisks(n - 1, buffer, destination, origin);
13 }
The following code provides a more detailed implementation of this algorithm, using concepts of objectoriented
design.

Implementing the towers as their own objects is not strictly necessary, but it does help to make the code
cleaner in some respects.
*/
package Q8_06_Towers_of_Hanoi;

import java.util.Stack;

public class Tower {
	private Stack<Integer> disks = new Stack<Integer>();
	public String name;
	
	public void add(int d) {
		if (!disks.isEmpty() && disks.peek() <= d) {
			System.out.println("Error placing disk " + d);
		} else {
			disks.push(d);
		}
	}
	
	public void moveTopTo(Tower t) {
		int top = disks.pop();
		t.add(top);
	}
	
	public void print() {
		System.out.println("Contents of Tower " + name + ": " + disks.toString());
	}
	
    public void moveDisks(int quantity, Tower destination, Tower buffer){
    	if (quantity <= 0) return;
    	
		moveDisks(quantity - 1, buffer, destination);	//n-1 disks to buffer tower
		System.out.println("Move " + disks.peek() + " from " + this.name + " to " + destination.name);
		moveTopTo(destination);
		buffer.moveDisks(quantity - 1, destination, this);//n-1 disks to final destination tower
	}
}

class Question {
	public static void main(String[] args) {
		Tower source = new Tower();
		Tower destination = new Tower();
		Tower buffer = new Tower();
		
		source.name = "s";
		destination.name = "d";
		buffer.name = "b";
		
		/* Load up tower */
		int numberOfDisks = 5;
		for (int disk = numberOfDisks - 1; disk >= 0; disk--) {
			source.add(disk);
		}
		
		source.print();
		source.moveDisks(numberOfDisks, destination, buffer);
		destination.print();
	}
}
