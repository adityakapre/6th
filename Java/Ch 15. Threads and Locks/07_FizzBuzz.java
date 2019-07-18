/*
Q:
FizzBuzz: In the classic problem FizzBuzz, you are told to print the numbers from 1 to n. However,
when the number is divisible by 3, print "Fizz''. When it is divisible by 5, print "Buzz''. When it is
divisible by 3 and 5, print"FizzBuzz''. In this problem, you are asked to do this in a multithreaded way.
Implement a multithreaded version of FizzBuzz with four threads. One thread checks for divisibility
of 3 and prints"Fizz''. Another thread is responsible for divisibility of 5 and prints"Buzz''. A third thread
is responsible for divisibility of 3 and 5 and prints "FizzBuzz''. A fourth thread does the numbers.

A:
Although this problem (in the single threaded version) shouldn't be hard, a lot of candidates overcomplicate
it. They look for something "beautiful"that reuses the fact that the divisible by 3 and 5 case ("FizzBuzz")
seems to resemble the individual cases ("Fizz" and "Buzz").
*/
public class QuestionA {

	public static void fizzbuzz(int n) {
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				System.out.println("FizzBuzz");
			} else if (i % 3 == 0) {
				System.out.println("Fizz");
			} else if (i % 5 == 0) {
				System.out.println("Buzz");
			} else {
				System.out.println(i);
			}
		}
	}
	
	public static void main(String[] args) {
		fizzbuzz(100);
	}

}

/*
A:
Multithreaded
To do this multithreaded, we want a structure that looks something like this:

FizzBuzz Thread
if i div by 3 && 5
print FizzBuzz
increment i
repeat until i > n

Fizz Thread
if i div by only 3
print Fizz
increment i
repeat until i > n

Buzz Thread
if i div by only 5
print Buzz
increment i
repeat until i > n

Nunber Thread
if i not div by 3 or 5
print i
increment i
repeat until i > n

The code for this will look something like:
1 while (true) {
2 if (current> max) {
3 return;
4 }
5 if(/divisibility test/) {
6 System.out.println(/print something/);
7 current++;
8 }
9 }

We'll need to add some synchronization in the loop. Otherwise, the value of current could change
between lines 2 - 4 and lines 5 - 8, and we can inadvertently exceed the intended bounds of the loop. 
Additionally, incrementing is not thread-safe.
To actually implement this concept, there are many possibilities. One possibility is to have four entirely
separate thread classes that share a reference to the current variable (which can be wrapped in an object).
The loop for each thread is substantially similar. They just have different target values for the divisibility
checks, and different print values.

                 FizzBuzz  Fizz   Buzz   Number
current%3==0     true 	   true   false  false
current%5==0     true      false  true   false
to print         FizzBuzz  Fizz   Buzz   current

For the most part, this can be handled by taking in "target" parameters and the value to print. The output for
the Number thread needs to be overwritten, though, as it's not a simple, fixed string.
We can implement a FizzBuzzThread class which handles most of this. A Number Thread class can
extend FizzBuzzThread and override the print method.

*/

public class FizzBuzzThread extends Thread {
	private static Object lock = new Object();
	protected static int current = 1;
	private int max;
	private boolean div3, div5;
	private String toPrint;
	
	public FizzBuzzThread(boolean div3, boolean div5, int max, String toPrint) {
		this.div3 = div3;
		this.div5 = div5;
		this.max = max;
		this.toPrint = toPrint;
	}
	
	public void print() {
		System.out.println(toPrint);
	}
	
	public void run() {
		while (true) {
			synchronized (lock) {
				if (current > max) {
					return;
				}
				if ((current % 3 == 0) == div3 && (current % 5 == 0) == div5) {
					print();
					current++;
				}
			}
		}
	}
}

public class NumberThread extends FizzBuzzThread {
	
	public NumberThread(boolean div3, boolean div5, int max) {
		super(div3, div5, max, null);
	}

	public void print() {
		System.out.println(current);
	}
}

public class QuestionB {

	public static void main(String[] args) {
		int n = 100;
		Thread[] threads = {new FizzBuzzThread(true, true, n, "FizzBuzz"), 
				    new FizzBuzzThread(true, false, n, "Fizz"), 
				    new FizzBuzzThread(false, true, n, "Buzz"),
				    new NumberThread(false, false, n)};
		for (Thread thread : threads) {
			thread.start();
		}
	}
}

/*
A:
Alternatively, if we're working in a language which supports this (Java 8 and many other languages do), we
can pass in a validate method and a print method as parameters.
*/

package Q15_07_FizzBuzz;

import java.util.function.Function;
import java.util.function.Predicate;

public class FBThread extends Thread {
	private static Object lock = new Object();
	protected static int current = 1;
	private int max;
	private Predicate<Integer> validate;
	private Function<Integer, String> printer;
	int x = 1;
	
	public FBThread(Predicate<Integer> validate, Function<Integer, String> printer, int max) {
		this.validate = validate;
		this.printer = printer;
		this.max = max;
	}
	
	public void run() {
		while (true) {
			synchronized (lock) {
				if (current > max) {
					return;
				}
				if (validate.test(current)) {									
					System.out.println(printer.apply(current));
					current++;
				}
			}
		}
	}
}

public class QuestionC {

	public static void main(String[] args) {
		int n = 100;
		Thread[] threads = {new FBThread(i -> i % 3 == 0 && i % 5 == 0, i -> "FizzBuzz", n), 
				    new FBThread(i -> i % 3 == 0 && i % 5 != 0, i -> "Fizz", n),
				    new FBThread(i -> i % 3 != 0 && i % 5 == 0, i -> "Buzz", n),
				    new FBThread(i -> i % 3 != 0 && i % 5 != 0, i -> Integer.toString(i), n)};
		for (Thread thread : threads) {
			thread.start();
		}
	}

}
