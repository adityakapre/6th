package IntroductionLocks;

public class Intro {

	public static void main(String[] args) {
		NoLockATM noLockATM = new NoLockATM();
		LockedATM lockedATM = new LockedATM();
		MyClass thread1 = new MyClass(noLockATM, lockedATM);
		MyClass thread2 = new MyClass(noLockATM, lockedATM);
		
		thread1.start();
		thread2.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread1.waitUntilDone();
		thread2.waitUntilDone();
		
		System.out.println("NoLock ATM: " + noLockATM.getBalance());
		System.out.println("Locked ATM: " + lockedATM.getBalance());
		int v = thread1.delta + thread2.delta + 100;
		System.out.println("Should Be: " + v);
		System.out.println("Program terminating.");
	}
}

package IntroductionLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedATM {
	private Lock lock;
	private int balance = 100;
	
	public LockedATM() {
		lock = new ReentrantLock();
	}
	
	public int withdraw(int value) {
		lock.lock();
		int temp = balance;
		try {
			Thread.sleep(100);
			temp = temp - value;
			Thread.sleep(100);
			balance = temp;
		} catch (InterruptedException e) {		}
		lock.unlock();
		return temp;
	}
	
	public int deposit(int value) {
		lock.lock();
		int temp = balance;
		try {
			Thread.sleep(100);
			temp = temp + value;
			Thread.sleep(100);
			balance = temp;
		} catch (InterruptedException e) {		}
		lock.unlock();
		return temp;
	} 
	
	public int getBalance() {
		return balance;
	}
}

package IntroductionLocks;

public class NoLockATM {
	private int balance = 100;
	
	public NoLockATM() {
	}
	
	public int withdraw(int value) {
		int temp = balance;
		try {
			Thread.sleep(300);
			temp = temp - value;
			Thread.sleep(300);
			balance = temp;
		} catch (InterruptedException e) {		}
		return temp;
	}
	
	public int deposit(int value) {
		int temp = balance;
		try {
			Thread.sleep(300);
			temp = temp + value;
			Thread.sleep(300);
			balance = temp;
		} catch (InterruptedException e) {		}
		return temp;
	} 
	
	public int getBalance() {
		return balance;
	}	
}
