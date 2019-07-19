public class RunnableThreadExample implements Runnable {
	public int count = 0;

	public void run() {
		System.out.println("RunnableThread starting.");
		try {
			while (count < 5) {
				Thread.sleep(500);
				System.out.println("RunnableThread count: " + count);
				count++;
			} 
		} catch (InterruptedException exc) {
			System.out.println("RunnableThread interrupted.");
		}
		System.out.println("RunnableThread terminating.");
	}
}

public class ExampleA {

	public static void main(String[] args) {
		RunnableThreadExample instance = new RunnableThreadExample();
		Thread thread = new Thread(instance);
		thread.start();
		
		/* waits until earlier thread counts to 5 (slowly) */
		while (instance.count != 5) {		
			try {
				Thread.sleep(250);
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		
		System.out.println("Program Terminating.");
	}
}

