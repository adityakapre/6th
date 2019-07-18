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
