/*
Follow Up: What if we have only 10 MB memory?
It's possible to find a missing integer with two passes of the data set. We can divide up the integers into
blocks of some size (we'll discuss how to decide on a size later). Let's just assume that we divide up the
integers into blocks of 1000. So, block O represents the numbers O through 999, block 1 represents numbers
1000 - 1999, and so on.
Since all the values are distinct, we know how many values we should find in each block. So, we search
through the file and count how many values are between O and 999, how many are between 1000 and
1999, and so on. If we count only 999 values in a particular range, then we know that a missing int must be
in that range.
In the second pass, we'll actually look for which number in that range is missing. We use the bit vector
approach from the first part of this problem. We can ignore any number outside of this specific range.
The question, now, is what is the appropriate block size? Let's define some variables as follows:

-> Let rangeSize be the size of the ranges that each block in the first pass represents.
-> Let arrayS1ze represent the number of blocks in the first pass. Note that arraySize = 2 rasiedTo 31 / rangesize
since there are 2 raisedTo 31 non-negative integers.

We need to select a value for rangeSize such that the memory from the first pass (the array) and the
second pass (the bit vector) fit.

First Pass: The Array
The array in the first pass can fit in 10 megabytes, or roughly 2 rasiedTo 23 bytes, of memory. Since each element in the
array is an int, and an int is 4 bytes, we can hold an array of at most about 2 rasiedTo 21 elements. So, we can deduce
the following:

arraySize = 2 rasiedTo 31/rangeSize <= 2 rasiedTo 21
rangeS1ze >= 2 rasiedTo 31/2 rasiedTo 21
rangeSize >= 2 rasiedTo 10

Second Pass: The Bit Vector
We need to have enough space to store rangeSize bits. Since we can fit 2 rasiedTo 23 bytes in memory, we can fit
2 rasiedTo 26 bits in memory. Therefore, we can conclude the following:
2 rasiedTo 11 <= rangeSize <= 2 rasiedTo 26

These conditions give us a good amount of"wiggle room;' but the nearer to the middle that we pick, the
less memory will be used at any given time.
The below code provides one implementation for this algorithm.

What if, as a follow up question, you are asked to solve the problem with even less memory? In this case, we
can do repeated passes using the approach from the first step. We'd first check to see how many integers
are found within each sequence of a million elements. Then, in the second pass, we'd check how many integers
are found in each sequence of a thousand elements. Finally, in the third pass, we'd apply the bit vector.
*/
package Q10_07_Missing_Int;

import java.io.*;
import java.util.*;

public class QuestionB {

	public static int findOpenNumber(String filename) throws FileNotFoundException {
		int rangeSize = (1 << 20); // 2^20 bits (2^17 bytes)
		
		/* Get count of number of values within each block. */
		int[] blocks = getCountPerBlock(filename, rangeSize);
		
		/* Find a block with a missing value. */
		int blockIndex = findBlockWithMissing(blocks, rangeSize);
		if (blockIndex < 0) return -1;
		
		/* Create bit vector for items within this range. */
		byte[] bitVector = getBitVectorForRange(filename, blockIndex, rangeSize);
		
		/* Find a zero in the bit vector */
		int offset = findZero(bitVector);
		if (offset < 0) return -1;
		
		/* Compute missing value. */
		return blockIndex * rangeSize + offset;
	}
	
	/* Get count of items within each range. */
	public static int[] getCountPerBlock(String filename, int rangeSize) throws FileNotFoundException {
		int arraySize = Integer.MAX_VALUE / rangeSize + 1;
		int[] blocks = new int[arraySize];
		
		Scanner in = new Scanner (new FileReader(filename));
		while (in.hasNextInt()) {
		    int value = in.nextInt();
		    blocks[value / rangeSize]++;
		}
		in.close();
		return blocks;
	}
	
	/* Find a block whose count is low. */
	public static int findBlockWithMissing(int[] blocks, int rangeSize) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] < rangeSize){
				return i;
			}
		}
		return -1;
	}
	
	/* Create a bit vector for the values within a specific range. */
	public static byte[] getBitVectorForRange(String filename, int blockIndex, int rangeSize) throws FileNotFoundException {
		int startRange = blockIndex * rangeSize;
		int endRange = startRange + rangeSize;
		byte[] bitVector = new byte[rangeSize/Byte.SIZE];
		
		Scanner in = new Scanner(new FileReader(filename));
		while (in.hasNextInt()) {
			int value = in.nextInt();
			/* If the number is inside the block that's missing 
			 * numbers, we record it */
			if (startRange <= value && value < endRange) {
				int offset = value - startRange;
				int mask = (1 << (offset % Byte.SIZE));
				bitVector[offset / Byte.SIZE] |= mask;
			}
		}
		in.close();
		return bitVector;
	}
	
	/* Find bit index that is 0 within byte. */
	public static int findZero(byte b) {
		for (int i = 0; i < Byte.SIZE; i++) {
			int mask = 1 << i;
			if ((b & mask) == 0) {
				return i;
			}
		}
		return -1;
	}
	
	/* Find a zero within the bit vector and return the index. */
	public static int findZero(byte[] bitVector) {
		for (int i = 0; i < bitVector.length; i++) {
			if (bitVector[i] != ~0) { // If not all 1s
				int bitIndex = findZero(bitVector[i]);
				return i * Byte.SIZE + bitIndex;
			}
		}		
		return -1;
	}
		
	public static void generateFile(String filename, int max, int missing) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(filename);

		for (int i = 0; i < max && i >= 0; i++) {
			if (i != missing) {
				writer.println(i);
			}
			if (i % 10000 == 0) {
				System.out.println("Now at location: " + i);
			}
		}
		writer.flush();
		writer.close();		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		String filename = "Ch 10. Scalability and Memory Limits/Q10_04_Missing_Int/input.txt";
		int max = 10000000;
		int missing = 1234325;
		System.out.println("Generating file...");
		generateFile(filename, max, missing);
		System.out.println("Generated file from 0 to " + max + " with " + missing + " missing.");
		System.out.println("Searching for missing number...");
		System.out.println("Missing value: " + findOpenNumber(filename));
	}

}
