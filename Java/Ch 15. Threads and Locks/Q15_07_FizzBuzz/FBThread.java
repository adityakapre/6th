/*
FizzBuzz: In the classic problem FizzBuzz, you are told to print the numbers from 1 to n. However,
when the number is divisible by 3, print "Fizz''. When it is divisible by 5, print "Buzz''. When it is
divisible by 3 and 5, print"FizzBuzz''. In this problem, you are asked to do this in a multithreaded way.
Implement a multithreaded version of FizzBuzz with four threads. One thread checks for divisibility
of 3 and prints"Fizz''. Another thread is responsible for divisibility of 5 and prints"Buzz''. A third thread
is responsible for divisibility of 3 and 5 and prints "FizzBuzz''. A fourth thread does the numbers.
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
