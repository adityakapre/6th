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

A:
The general logic is to check if first() has completed before executing second(), and if second()
has completed before calling third (). Because we need to be very careful about thread safety, simple
boolean flags won't do the job.

What about using a lock to do something like the below code?
*/

import java.util.concurrent.locks.ReentrantLock;

public class FooBad {
	public int pauseTime = 1000;
	public ReentrantLock lock1;
	public ReentrantLock lock2;
	
	public FooBad() {	
		try {
			lock1 = new ReentrantLock();
			lock2 = new ReentrantLock();
			
			lock1.lock();
			lock2.lock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void first() {
		try {
			System.out.println("Started Executing 1");
			Thread.sleep(pauseTime);
			System.out.println("Finished Executing 1");
			lock1.unlock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void second() {
		try {
			lock1.lock();
			lock1.unlock();
			System.out.println("Started Executing 2");
			Thread.sleep(pauseTime);
			System.out.println("Finished Executing 2");
			lock2.unlock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
	
	public void third() {
		try {
			lock2.lock();
			lock2.unlock();
			System.out.println("Started Executing 3");
			Thread.sleep(pauseTime);
			System.out.println("Finished Executing 3");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
}

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
/*
This code won't actually quite work due to the concept of lock ownership. One thread is actually performing
the lock (in the Fo oBad constructor), but different threads attempt to unlock the locks. This is not allowed,
and your code will raise an exception. A lock in Java is owned by the same thread which locked it.

A2:
Instead, we can replicate this behavior with semaphores. The logic is identical as below.
*/
