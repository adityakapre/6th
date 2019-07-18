/*
Q:
Call In Order: Suppose we have the following code:
public class Foo {
public Foo() { ... }
public void first() { ... }
public void second() { ... }
public void third() { ... }
}
The same instance of Foo will be passed to three different threads. ThreadA will call first threadB
will call second, and thread( will call third. Design a mechanism to ensure that first is called
before second and second is called before third.


*/
package Q15_05_Call_In_Order;

public class Question {
	public static void main(String[] args) {
		FooBad foo = new FooBad();
		
		MyThread thread1 = new MyThread(foo, "first");
		MyThread thread2 = new MyThread(foo, "second");
		MyThread thread3 = new MyThread(foo, "third");
		
		thread3.start();
		thread2.start();
		thread1.start();
	}
}
