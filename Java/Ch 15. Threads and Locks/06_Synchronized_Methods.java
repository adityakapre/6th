/*
Q:
Synchronized Methods: You are given a class with synchroniz ed method A and a normal
method B. If you have two threads in one instance of a program, can they both execute A at the
same time? Can they execute A and B at the same time?
pg 180

A:
By applying the word synchroniz ed to a method, we ensure that two threads cannot execute synchronized
methods on the same object instance at the same time.
So, the answer to the first part really depends. If the two threads have the same instance of the object, then
no, they cannot simultaneously execute method A. However, if they have different instances of the object,
then they can.
Conceptually, you can see this by considering locks. A synchronized method applies a "lock" on all synchronized
methods in that instance of the object. This blocks other threads from executing synchronized
methods within that instance.
In the second part, we're asked if threadl can execute synchronized method A while thread2 is
executing non-synchronized method B. Since B is not synchronized, there is nothing to block threadl
from executing A while thread2 is executing B. This is true regardless of whether threadl and thread2
have the same instance of the object.
Ultimately, the key concept to remember is that only one synchronized method can be in execution per
instance of that object. Other threads can execute non-synchronized methods on that instance, or they can
execute any method on a different instance of the object.
*/

public class Foo {
	private String name;
	
	public Foo(String nm) {
		name = nm;
	}
	
	public String getName() {
		return name;
	}
	
	public void pause() {
		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public synchronized void methodA(String threadName) {
		System.out.println("thread " + threadName + " starting: " + name + ".methodA()");
		pause();
		System.out.println("thread " + threadName + " ending: " + name + ".methodA()");
	}
	
	public void methodB(String threadName) {
		System.out.println("thread " + threadName + " starting: " + name + ".methodB()");
		pause();
		System.out.println("thread " + threadName + " ending: " + name + ".methodB()");
	}	
}

package Q15_06_Synchronized_Methods;

public class MyThread extends Thread {
	private Foo foo;
	public String name;
	public String firstMethod;
	public MyThread(Foo f, String nm, String fM) {
		foo = f;
		name = nm;
		firstMethod = fM;
	}
	
	public void run() {
		if (firstMethod.equals("A")) {
			foo.methodA(name);
		} else {
			foo.methodB(name);
		}
	}
}

public class Question {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Part 1 Demo -- same instance */
		System.out.println("Part 1 Demo with same instance.");
		Foo fooA = new Foo("ObjectOne");
		MyThread thread1a = new MyThread(fooA, "Dog", "A");
		MyThread thread2a = new MyThread(fooA, "Cat", "A");
		thread1a.start();
		thread2a.start();
		while (thread1a.isAlive() || thread2a.isAlive()) { };
		System.out.println("\n\n");
		
		/* Part 1 Demo -- difference instances */
		System.out.println("Part 1 Demo with different instances.");
		Foo fooB1 = new Foo("ObjectOne");
		Foo fooB2 = new Foo("ObjectTwo");
		MyThread thread1b = new MyThread(fooB1, "Dog", "A");
		MyThread thread2b = new MyThread(fooB2, "Cat", "A");
		thread1b.start();
		thread2b.start();
		while (thread1b.isAlive() || thread2b.isAlive()) { };
		System.out.println("\n\n");
		
		/* Part 2 Demo */
		System.out.println("Part 2 Demo.");
		Foo fooC = new Foo("ObjectOne");
		MyThread thread1c = new MyThread(fooC, "Dog", "A");
		MyThread thread2c = new MyThread(fooC, "Cat", "B");
		thread1c.start();
		thread2c.start();		
	}

}
