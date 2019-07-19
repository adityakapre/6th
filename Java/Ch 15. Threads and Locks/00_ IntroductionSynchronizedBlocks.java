package IntroductionSynchronizedBlocks;

public class Intro {

	public static void main(String[] args) {
		try {
			MyObject obj1 = new MyObject();
			MyObject obj2 = new MyObject();
			MyClass thread1 = new MyClass(obj1, "1");
			MyClass thread2 = new MyClass(obj1, "2");
			
			thread1.start();
			thread2.start();
			
			Thread.sleep(3000 * 3);
		}  catch (InterruptedException exc) {
			System.out.println("Program Interrupted.");
		}
		System.out.println("Program terminating.");
	}
}

public class MyClass extends Thread  {
	private String name;
	private MyObject myObj;
	
	public MyClass(MyObject obj, String n) {
		name = n;
		myObj = obj;
	}
	
	public void run() {
		myObj.foo(name);
	}
}

public class MyObject {
	public void foo(String name) {
		synchronized(this) {
			try {
				System.out.println("Thread " + name + ".foo(): starting");
				Thread.sleep(3000);
				System.out.println("Thread " + name + ".foo(): ending");
			} catch (InterruptedException exc) {
				System.out.println("Thread " + name + ": interrupted.");
			}
		}
	} 	
}
