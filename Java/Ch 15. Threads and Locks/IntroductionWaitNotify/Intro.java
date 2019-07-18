package synchronization;

public class Intro {

	public static void main(String[] args) {
		try {
			MyObject obj = new MyObject();
			MyClass thread1 = new MyClass(obj, "1");
			MyClass thread3 = new MyClass(obj, "3");
			MyClass thread2 = new MyClass(obj, "2");
			
			thread3.start();
			thread2.start();
			thread1.start();
			
			Thread.sleep(1000 * 3);
		}  catch (InterruptedException exc) {
			System.out.println("Program Interrupted.");
		}
		System.out.println("Program terminating.");
	}
}

package synchronization;

public class MyObject {
	
	private static int callerNumber=1;
	
	public void foo(String name) {
		synchronized(this) {
			try {
				callerNumber++;
				System.out.println("Thread " + name + ".foo(): starting logic execution");
				Thread.sleep(3000);
				System.out.println("Thread " + name + ".foo(): ending");
				Thread.sleep(3000);
			} catch (InterruptedException exc) {
				System.out.println("Thread " + name + ": interrupted.");
			}
		}
	} 	
	
	public static synchronized int getCallerNumber() {
		return callerNumber;
	}
}

package synchronization;

public class MyClass extends Thread  {
	private String name;
	private MyObject myObj;
	
	public MyClass(MyObject obj, String n) {
		name = n;
		myObj = obj;
	}
	
	public void run() {
		try {
			synchronized (myObj) {
				/* if this is first call to myObj, no one is going to call notify on myObj, 
				 * so wait till 1 sec and then continue or no need to wait (remove wait statement)
				 */
				if(myObj.getCallerNumber()==1) { 
					myObj.wait(1000);
				} else {
					myObj.wait();
				}
				
				myObj.foo(name);
				myObj.notify();	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
