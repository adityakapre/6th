/*
Remember 1 KiB = 1024 bytes = 1024 * 8 bits = 32768 bits > 32000 bits
We have 4 kilobytes of memory which means we can address up to 8 * 4 * 210 bits. Note that 32 * 2 raisedTo 10
bits is greater than 32000. We can create a bit vector with 32000 bits, where each bit represents one integer.
Using this bit vector, we can then iterate through the array, flagging each element v by setting bit v to 1.
When we come across a duplicate element, we print it.

Note that while this isn't an especially difficult problem, it's important to implement this cleanly. This is why
we defined our own bit vector class to hold a large bit vector. If our interviewer lets us (she may or may not),
we could have of course used Java's built in BitSet class.
*/
package Q10_08_Find_Duplicates;

import CtCILibrary.AssortedMethods;

public class Question {

	public static void checkDuplicates(int[] array) {
		BitSet bs = new BitSet(32000);
		for (int i = 0; i < array.length; i++) {
			int num = array[i];
			int num0 = num - 1; // bitset starts at 0, numbers start at 1
			if (bs.get(num0)) { 
				System.out.println(num);
			} else {
				bs.set(num0);				
			}
		}
	}
	
	public static void main(String[] args) {
		int[] array = AssortedMethods.randomArray(30, 1, 30);
		System.out.println(AssortedMethods.arrayToString(array));
		checkDuplicates(array);
	}

}

class BitSet {
	int[] bitset;
	
	public BitSet(int size) {
		bitset = new int[(size >> 5) + 1]; // divide by 32
	}

	boolean get(int pos) {
		int wordNumber = (pos >> 5); // divide by 32
		int bitNumber = (pos & 0x1F); // mod 32
		return (bitset[wordNumber] & (1 << bitNumber)) != 0;
	}
	
	void set(int pos) {
		int wordNumber = (pos >> 5); // divide by 32
		int bitNumber = (pos & 0x1F); // mod 32
		bitset[wordNumber] |= 1 << bitNumber;
	}
}
