/*
Q. Java Threading

So what exactly is a thread? In Java, "thread" means two different things:
■ An instance of class java.lang.Thread
■ A thread of execution

An instance of Thread is just...an object. Like any other object in Java, 
it has variables and methods, and lives and dies on the heap. 
But a thread of execution is an individual process (a "lightweight" process) 
that has its own call stack. 
In Java, there is one thread per call stack—or, to think of it in reverse, 
one call stack per thread. Even if you don't create any new threads in your program, 
threads are back there running.

The main() method, that starts the whole ball rolling, runs in one thread, 
called (surprisingly) the main thread.

But as soon as you create a new thread, a new stack materializes and methods called 
from that thread run in a call stack that's separate from the main() call stack. 
That second new call stack is said to run concurrently with the main thread, 
but we'll refine that notion as we go through this chapter.

The JVM, which gets its turn at the CPU by whatever scheduling mechanism the 
underlying OS uses, operates like a mini-OS and schedules its own threads regardless 
of the underlying operating system. In some JVMs, the Java threads are actually 
mapped to native OS threads, but we won't discuss that here;

When it comes to threads, very little is guaranteed.
Don't make the mistake of designing your program to be dependent on a particular 
implementation of the JVM. As you'll learn a little later, different JVMs can run 
threads in profoundly different ways. For example, one JVM might be sure that all 
threads get their turn, with a fairly even amount of time allocated for each thread 
in a nice, happy, round-robin fashion. But in other JVMs, a thread might start 
running and then just hog the whole show, never stepping out so others can have a turn. 
If you test your application on the "nice turn-taking" JVM, and you don't know 
what is and is not guaranteed in Java, then you might be in for a big shock when 
you run it under a JVM with a different thread scheduling mechanism.

You and the operating system can create a second kind of thread called a daemon thread. 
The difference between these two types of threads (user and daemon) is that the 
JVM exits an application only when all user threads are complete—the JVM doesn't 
care about letting daemon threads complete, so once all user threads are complete, 
the JVM will shut down, regardless of the state of any daemon threads.

Making a thread

start()
yield()
sleep()
run()

The action happens in the run() method. 
Think of the code you want to execute in a separate thread as the job to do. 
In other words, you have some work that needs to be done, say, downloading 
stock prices in the background while other things are happening in the program, 
so what you really want is that job to be executed in its own thread. 
So if the work you want done is the job, the one doing the work 
(actually executing the job code) is the thread. 
And the job always starts from a run() method as follows:
*/

public void run() {
	// your job code goes here
}

/*
the thread of execution—the new call stack—always begins by invoking run().

You can define and instantiate a thread in one of two ways:
■ Extend the java.lang.Thread class.
■ Implement the Runnable interface.

the real world you're much more likely to implement Runnable than extend Thread. 
Extending the Thread class is the easiest, but it's usually not a good OO practice. 
Why? 
Because subclassing should be reserved for specialized versions of more general 
superclasses. So the only time it really makes sense (from an OO perspective) 
to extend Thread is when you have a more specialized version of a Thread class. 
In other words, because you have more specialized thread-specific behavior. 
Chances are, though, that the thread work you want is really just a job to be done 
by a thread. In that case, you should design a class that implements the 
Runnable interface, which also leaves your class free to extend some other class.
*/

class MyThread extends Thread {
	public void run() {
		System.out.println("Important job running in MyThread");
	}
	public void run(String s) {
		System.out.println("overloaded run");
	}
}
/*
The limitation with this approach (besides being a poor design choice 
in most cases) is that if you extend Thread, you can't extend anything else.
The overloaded run(String s) method will be ignored by the Thread class 
unless you call it yourself. The Thread class expects a run() method 
with no arguments, and it will execute this method for you in a separate call stack
after the thread has been started. With a run(String s) method, the Thread class 
won't call the method for you, and even if you call the method directly 
yourself, execution won't happen in a new thread of execution with a separate
call stack. It will just happen in the same call stack as the code that 
you made the call from, just like any other normal method call.
*/

class MyRunnable implements Runnable {
	public void run() {
		System.out.println("Important job running in MyRunnable");
	}
}
/*
If you extended the Thread class, instantiation is dead simple 
(we'll look at some additional overloaded constructors in a moment):

MyThread t = new MyThread()
If you implement Runnable, instantiation is only slightly less simple. 
To have code run by a separate thread, you still need a Thread instance. 
(Another common way to think about this is that the Thread is the "worker," 
and the Runnable is the "job" to be done.)

MyRunnable r = new MyRunnable();
Thread t = new Thread(r); // Pass your Runnable to the Thread

If you create a thread using the no-arg constructor, the thread will call 
its own run() method when it's time to start working. 
That's exactly what you want when you extend Thread, but when you use Runnable, 
you need to tell the new thread to use your run() method rather than its own. 
The Runnable you pass to the Thread constructor is called the target 
or the target Runnable.
The Thread class itself implements Runnable. 
(After all, it has a run() method that we were overriding.) 
This means that you could pass a Thread to another Thread’s constructor:

Thread t = new Thread(new MyThread());

This is a bit silly, but it’s legal. 
In this case, you really just need a Runnnable, and creating a whole other 
Thread is overkill.

The constructors we care about are
■ Thread()
■ Thread(Runnable target)
■ Thread(Runnable target, String name)
■ Thread(String name)

When a thread has been instantiated but not started (in other words, the start() 
method has not been invoked on the Thread instance), the thread is said to be 
in the new state. At this stage, the thread is not yet considered to be alive. 
Once the start() method is called, the thread is considered to be alive 
(even though the run() method may not have actually started executing yet). 
A thread is considered dead (no longer alive) after the run() method completes. 
The isAlive() method is the best way to determine if a thread has been started 
but has not yet completed its run() method. 
(Note: The getState() method is very useful for debugging)

Starting the thread :
--------------------
t.start();

So what happens after you call start()? The good stuff:
■ A new thread of execution starts (with a new call stack).
■ The thread moves from the new state to the runnable state.
■ When the thread gets a chance to execute, its target run() method will run.

Be sure you remember the following: You start a Thread, not a Runnable. 
You call start() on a Thread instance, not on a Runnable instance.

There’s nothing special about the run() method as far as Java is concerned. 
Like main(), it just happens to be the name (and signature) of the method that 
the new thread knows to invoke. So if you see code that calls the run() method 
on a Runnable (or even on a Thread instance), that’s perfectly legal. 
But it doesn’t mean the run() method will run in a separate thread! 
Calling a run() method directly just means you’re invoking a method from whatever 
thread is currently executing, and the run() method goes onto the current call stack 
rather than at the beginning of a new call stack.

The following code does not start a new thread of execution:
Thread t = new Thread();
t.run(); // Legal, but does not start a new thread

We can use the getName() method of class Thread, and have each Runnable print out 
the name of the thread executing that Runnable object's run() method.
To get the name of a thread you call—who would have guessed—getName() on the 
Thread instance. But the target Runnable instance doesn't even have a reference 
to the Thread instance, so we first invoked the static Thread.currentThread() method, 
which returns a reference to the currently executing thread, and then we invoked 
getName() on that returned reference.

Starting and running multiple threads : 
---------------------------------------
class NameRunnable implements Runnable {
	public void run() {
		for (int x = 1; x <= 3; x++) {
		System.out.println
			("Run by "+ Thread.currentThread().getName()+ ", x is " + x);
		}
	}
}

public class ManyNames {
	public static void main(String [] args) {
		// Make one Runnable
		NameRunnable nr = new NameRunnable();
		Thread one = new Thread(nr);
		Thread two = new Thread(nr);
		Thread three = new Thread(nr);
		one.setName("Fred");
		two.setName("Lucy");
		three.setName("Ricky");
		one.start();
		two.start();
		three.start();
	}
}

Running this code might produce the following:
% java ManyNames
Run by Fred, x is 1
Run by Fred, x is 2
Run by Fred, x is 3
Run by Lucy, x is 1
Run by Lucy, x is 2
Run by Lucy, x is 3
Run by Ricky, x is 1
Run by Ricky, x is 2
Run by Ricky, x is 3

Well, at least that's what it printed when we ran it—this time, on our machine. 
But the behavior you see above is not guaranteed. 
This is so crucial that you need to stop right now, take a deep breath, 
and repeat after me, "The behavior is not guaranteed." You need to know, for 
your future as a Java programmer as well as for the exam, that there is nothing 
in the Java specification that says threads will start running in the order in which 
they were started (in other words, the order in which start() was invoked on each 
thread). And there is no guarantee that once a thread starts executing, 
it will keep executing until it's done. Or that a loop will complete before another 
thread begins. No siree Bob. Nothing is guaranteed in the preceding code except this:
	Each thread will start, and each thread will run to completion.

Each one individually is behaving in a nice orderly manner. But together—chaos!
but then Lucy butts in when it was Fred's turn. What nerve!

There is a way, however, to start a thread but tell it not to run until some other 
thread has finished. You can do this with the join() method, which we'll look at a 
little later.
A thread is done being a thread when its target run() method completes. 
When a thread completes its run() method, the thread ceases to be a thread of execution. 
The stack for that thread dissolves, and the thread is considered dead.
(Technically the API calls a dead thread "terminated", but we'll use "dead" in this chapter.) 
Not dead and gone, however, just dead. It's still a Thread object, just not a 
thread of execution. So if you've got a reference to a Thread instance, 
then even when that Thread instance is no longer a thread of execution, you can still 
call methods on the Thread instance, just like any other Java object. 
What you can't do, though, is call start() again. Once a thread has been started, 
it can never be started again. If you have a reference to a Thread, and you call 
start(), it's started. If you call start() a second time, it will cause an exception 
(an IllegalThreadStateException, which is a kind of RuntimeException, but you don't 
need to worry about the exact type). This happens whether or not the run() method 
has completed from the first start() call. Only a new thread can be started, 
and then only once. A runnable thread or a dead thread cannot be restarted.

In addition to using setName() and getName to identify threads, you might see getld(). 
The getld() method returns a positive, unique, long number, and that number will 
be that thread's only ID number for the thread's entire life.

Thread Scheduler
----------------
Assuming a single processor machine, only one thread can actually run at a time. 
Only one stack can ever be executing at one time. And it's the thread scheduler that 
decides which thread—of all that are eligible—will actually run. When we say eligible, 
we really mean in the runnable state.Any thread in the runnable state can be chosen 
by the scheduler to be the one and only running thread. If a thread is not in a runnable state, 
then it cannot be chosen to be the currently running thread. And just so we're clear about 
how little is guaranteed here: The order in which runnable threads are chosen to run is 
not guaranteed. Threads aren't all lined up in some guaranteed order. 
Although we don't control the thread scheduler (we can't, for example, tell a specific 
thread to run), we can sometimes influence it. The following methods give us some tools 
for influencing the scheduler. Just don't ever mistake influence for control.

Methods from the java.lang.Thread Class Some of the methods that can help us influence 
thread scheduling are as follows:

	public static void sleep(long millis) throws InterruptedException
	public static void yield()
	public final void join() throws InterruptedException
	public final void setPriority(int newPriority)

Note that both sleep() and join() have overloaded versions not shown here.

Methods from the java.lang.Object Class Every class in Java inherits the following 
three thread-related methods:

public final void wait() throws InterruptedException
public final void notify()
public final void notifyAll()

Thread States and Transitions : 

		     Waiting/Blocking/Sleeping
			|              ^
			|              |
			v              |
	New ------> Runnable <-----> Running ------> Dead

While the thread scheduler can move a thread from the running state back to runnable, 
other factors can cause a thread to move out of running, but not back to runnable. 
One of these is when the thread's run()method completes, in which case the thread moves 
from the running state directly to the dead state. 
Next we'll look at some of the other ways in which a thread can leave the 
running state, and where the thread goes.

Thread States
-------------

A thread can be only in one of five states (see Figure 9-2):

■ New This is the state the thread is in after the Thread instance has been created, 
  but the start() method has not been invoked on the thread. It is a live Thread object, 
  but not yet a thread of execution. At this point, the thread is considered not alive.
  
■ Runnable This is the state a thread is in when it's eligible to run, but the scheduler 
  has not selected it to be the running thread. A thread first enters the runnable state 
  when the start() method is invoked, but a thread can also return to the runnable state 
  after either running or coming back from a blocked, waiting, or sleeping state. 
  When the thread is in the runnable state, it is considered alive.
  
■ Running This is it. The "big time." Where the action is. This is the state a thread is 
  in when the thread scheduler selects it (from the runnable pool) to be the currently 
  executing process. A thread can transition out of a running state for several reasons, 
  including because "the thread scheduler felt like it." We'll look at those other reasons 
  shortly. Note that in Figure 9-2, there are several ways to get to the runnable state, 
  but only one way to get to the running state: the scheduler chooses a thread from the 
  runnable pool.
  
■ Waiting/blocked/sleeping This is the state a thread is in when it's not eligible to run. 
  Okay, so this is really three states combined into one, but they all have one thing in common: 
  the thread is still alive, but is currently not eligible to run. In other words, it is not 
  runnable, but it might return to a runnable state later if a particular event occurs. 
  A thread may be blocked waiting for a resource (like I/O or an object's lock), in which case 
  the event that sends it back to runnable is the availability of the resource—for example, 
  if data comes in through the input stream the thread code is reading from, or if the object's 
  lock suddenly becomes available. A thread may be sleeping because the thread's run code tells 
  it to sleep for some period of time, in which case the event that sends it back to runnable is 
  that it wakes up because its sleep time has expired. Or the thread may be waiting, because the 
  thread's run code causes it to wait, in which case the event that sends it back to runnable is 
  that another thread sends a notification that it may no longer be necessary for the thread to 
  wait. The important point is that one thread does not tell another thread to block. Some methods 
  may look like they tell another thread to block, but they don't. If you have a reference t to 
  another thread, you can write something like this: t.sleep(); or t.yield()
  But those are actually static methods of the Thread class—they don't affect the instance t; 
  instead they are defined to always affect the thread that's currently executing. 
  (This is a good example of why it's a bad idea to use an instance variable to access a static 
  method—it's misleading. There is a method, suspend(), in the Thread class, that lets one thread 
  tell another to suspend, but the suspend() method has been deprecated and won't be on the exam 
  (nor will its counterpart resume()). There is also a stop() method, but it too has been deprecated.
  
■ Dead A thread is considered dead when its run() method completes. It may still be a viable Thread 
  object, but it is no longer a separate thread of execution. Once a thread is dead, it can never be 
  brought back to life! (The whole "I see dead threads" thing.) If you invoke start() on a dead Thread 
  instance, you'll get a runtime (not compiler) exception. And it probably doesn't take a rocket 
  scientist to tell you that if a thread is dead, it is no longer considered to be alive.

Sleeping :
---------

The sleep() method is a static method of class Thread. You use it in your code to "slow a thread down" 
by forcing it to go into a sleep mode before coming back to runnable (where it still has to beg to be 
the currently running thread). When a thread sleeps, it drifts off somewhere and doesn't return to 
runnable until it wakes up. 
So why would you want a thread to sleep? 
Well, you might think the thread is moving too quickly through its code. Or you might need to force 
your threads to take turns, since reasonable turn-taking isn't guaranteed in the Java specification. 
Or imagine a thread that runs in a loop, downloading the latest stock prices and analyzing them. 
Downloading prices one after another would be a waste of time, as most would be quite similar—and 
even more important, it would be an incredible waste of precious bandwidth. The simplest way to solve 
this is to cause a thread to pause (sleep) for five minutes after each download.
You do this by invoking the static Thread.sleep() method, giving it a time in milliseconds as follows:

try {
	Thread.sleep(5*60*1000); // Sleep for 5 minutes
} catch (InterruptedException ex) { 
}

Notice that the sleep() method can throw a checked InterruptedException (you'll usually know if that 
is a possibility, since another thread has to explicitly do the interrupting), so you must acknowledge 
the exception with a handle or declare.
Typically, you wrap calls to sleep() in a try/catch, as in the preceding code.
Still, using sleep() is the best way to help all threads get a chance to run! Or at least to guarantee 
that one thread doesn't get in and stay until it's done. When a thread encounters a sleep call, 
it must go to sleep for at least the specified number of milliseconds (unless it is interrupted before 
its wake-up time, in which case it immediately throws the InterruptedException).

Just because a thread’s sleep() expires, and it wakes up, does not mean it will return to running! 
Remember, when a thread wakes up, it simply goes back to the runnable state. So the time specified 
in sleep() is the minimum duration in which the thread won’t run, but it is not the exact duration 
in which the thread won’t run. So you can’t, for example, rely on the sleep() method to give you a 
perfectly accurate timer. Although in many applications using sleep() as a timer is certainly good enough,
you must know that a sleep() time is not a guarantee that the thread will start running again 
as soon as the time expires and the thread wakes.

Remember that sleep() is a static method, so don't be fooled into thinking that one thread can put 
another thread to sleep. You can put sleep() code anywhere, since all code is being run by some thread. 
When the executing code (meaning the currently running thread's code) hits a sleep() call, 
it puts the currently running thread to sleep.

Thread Priorities and yield():
------------------------------
To understand yield(), you must understand the concept of thread priorities. Threads always run with 
some priority, usually represented as a number between 1 and 10 (although in some cases the range is 4
less than 10). The scheduler in most JVMs uses preemptive, priority-based scheduling (which implies 
some sort of time slicing). This does not mean that all JVMs use time slicing. The JVM specification 
does not require a VM to implement a time-slicing scheduler, where each thread is allocated a fair amount 
of time and then sent back to runnable to give another thread a chance. Although many JVMs do use 
time slicing, some may use a scheduler that lets one thread stay running until the thread completes 
its run() method.
In most JVMs, however, the scheduler does use thread priorities in one important way: 
If a thread enters the runnable state, and it has a higher priority than any of the threads in the pool 
and a higher priority than the currently running thread, the lower-priority running thread usually 
will be bumped back to runnable and the highest-priority thread will be chosen to run. In other words, 
at any given time the currently running thread usually will not have a priority that is lower than any of
the threads in the pool. In most cases, the running thread will be of equal or greater priority than the 
highest priority threads in the pool. This is as close to a guarantee about scheduling as you'll get 
from the JVM specification, so you must never rely on thread priorities to guarantee the correct behavior 
of your program.
Don't rely on thread priorities when designing your multithreaded application. Because thread-scheduling 
priority behavior is not guaranteed, use thread priorities as a way to improve the efficiency of your program, 
but just be sure your program doesn't depend on that behavior for correctness.
What is also not guaranteed is the behavior when threads in the pool are of equal priority, or when the 
currently running thread has the same priority as threads in the pool.

Setting a Thread's Priority 
---------------------------
A thread gets a default priority that is the priority of the thread of execution that creates it. 
For example, in the code

public class TestThreads {
	public static void main (String [] args) {
		MyThread t = new MyThread();
	}
} 

The thread referenced by t will have the same priority as the main thread, since the main thread 
is executing the code that creates the MyThread instance.

FooRunnable r = new FooRunnable();
Thread t = new Thread(r);
t.setPriority(8);
t.start();

Priorities are set using a positive integer, usually between 1 and 10, and the JVM will never 
change a thread's priority. However, the values 1 through 10 are not guaranteed. Some JVM's might 
not recognize ten distinct values.

Although the default priority is 5, the Thread class has the three following constants 
(static final variables) that define the range of thread priorities:

Thread.MIN_PRIORITY (1)
Thread.NORM_PRIORITY (5)
Thread.MAX_PRIORITY (10)

The yield() Method
------------------
So what does the static Thread.yield() have to do with all this? Not that much, in practice. 
What yield() is supposed to do is make the currently running thread head back to runnable 
to allow other threads of the same priority to get their turn. So the intention is to use 
yield() to promote graceful turn-taking among equal-priority threads. In reality, though, 
the yield() method isn't guaranteed to do what it claims, and even if yield() does cause 
a thread to step out of running and back to runnable, there's no guarantee the yielding thread 
won't just be chosen again over all the others! So while yield() might—and often does—make a 
running thread give up its slot to another runnable thread of the same priority, there's 
no guarantee. A yield() won't ever cause a thread to go to the waiting/sleeping/ blocking state. 
At most, a yield() will cause a thread to go from running to runnable, but again, 
it might have no effect at all.

The join() Method
-----------------
The non-static join() method of class Thread lets one thread "join onto the end" of another thread. 
If you have a thread B that can't do its work until another thread A has completed its work, 
then you want thread B to "join" thread A. This means that thread B will not become runnable until 
A has finished (and entered the dead state).

Thread t = new Thread();
t.start();
t.join();

The preceding code takes the currently running thread (if this were in the main() method, then that 
would be the main thread) and joins it to the end of the thread referenced by t. This blocks the current 
thread from becoming runnable until after the thread referenced by t is no longer alive. 
In other words, the code t.join() means "Join me (the current thread) to the end of t, so that t must 
finish before I (the current thread) can run again." You can also call one of the overloaded versions 
of join() that takes a timeout duration, so that you're saying, "wait until thread t is done, 
but if it takes longer than 5,000 milliseconds, then stop waiting and become runnable anyway." 
Figure 9-3 shows the effect of the join() method.

So far we've looked at three ways a running thread could leave the running state:

■ A call to sleep() Guaranteed to cause the current thread to stop executing for at least the 
  specified sleep duration (although it might be interrupted before its specified time).
■ A call to yield() Not guaranteed to do much of anything, although typically it will cause the 
  currently running thread to move back to runnable so that a thread of the same priority can have a chance.
■ A call to join() Guaranteed to cause the current thread to stop executing until the thread it joins 
  with (in other words, the thread it calls join() on) completes, or if the thread it's trying to join 
  with is not alive, however,the current thread won't need to back out.

Besides those three, we also have the following scenarios in which a thread might leave the running state:

■ The thread's run() method completes. Duh.
■ A call to wait() on an object (we don't call wait() on a thread, as we'll see in a moment).
■ A thread can't acquire the lock on the object whose method code it's attempting to run.
■ The thread scheduler can decide to move the current thread from running to runnable in order to give 
  another thread a chance to run. No reason is needed—the thread scheduler can trade threads in and 
  out whenever it likes.

Synchronization and Locks
-------------------------
How does synchronization work? With locks. Every object in Java has a built-in lock that only comes into 
play when the object has synchronized method code. When we enter a synchronized non-static method, 
we automatically acquire the lock associated with the current instance of the class whose code we're 
executing (the this instance). Acquiring a lock for an object is also known as getting the lock, or 
locking the object, locking on the object, or synchronizing on the object. We may also use the term monitor 
to refer to the object whose lock we're acquiring. Technically the lock and the monitor are two different 
things, but most people talk about the two interchangeably, and we will too.

Remember the following key points about locking and synchronization:
■ Only methods (or blocks) can be synchronized, not variables or classes.
■ Each object has just one lock.
■ Not all methods in a class need to be synchronized. A class can have both synchronized 
  and non-synchronized methods.
■ If two threads are about to execute a synchronized method in a class, and both threads are using the 
  same instance of the class to invoke the method, only one thread at a time will be able to execute 
  the method. The other thread will need to wait until the first one finishes its method call. 
  In other words, once a thread acquires the lock on an object, no other thread can enter any of the 
  synchronized methods in that class (for that object).
■ If a class has both synchronized and non-synchronized methods, multiple threads can still access the 
  class's non-synchronized methods! If you have methods that don't access the data you're trying to protect, 
  then you don't need to synchronize them. Synchronization can cause a hit in some cases (or even deadlock 
  if used incorrectly), so you should be careful not to overuse it.
■ If a thread goes to sleep, it holds any locks it has—it doesn't release them.
■ A thread can acquire more than one lock. For example, a thread can enter a synchronized method, 
  thus acquiring a lock, and then immediately invoke a synchronized method on a different object, 
  thus acquiring that lock as well. As the stack unwinds, locks are released again. Also, if a thread 
  acquires a lock and then attempts to call a synchronized method on that same object, no problem. 
  The JVM knows that this thread already has the lock for this object, so the thread is free to call 
  other synchronized methods on the same object, using the lock the thread already has.
■ You can synchronize a block of code rather than a method.

Because synchronization does hurt concurrency, you don't want to synchronize any more code than is necessary 
to protect your data. So if the scope of a method is more than needed, you can reduce the scope of the 
synchronized part to something less than a full method—to just a block.

class SyncTest {
	public void doStuff() {
		System.out.println("not synchronized");
		synchronized(this) {
			System.out.println("synchronized");
		}
	}
}

The real question is, synchronized on what? Or, synchronized on which object's lock?
When you synchronize a method, the object used to invoke the method is the object whose lock must be acquired. 
But when you synchronize a block of code, you specify which object's lock you want to use as the lock, 
so you could, for example, use some third-party object as the lock for this piece of code. That gives you 
the ability to have more than one lock for code synchronization within a single object.
Or you can synchronize on the current instance (this) as in the code above. Since that's the same instance 
that synchronized methods lock on, it means that you could always replace a synchronized method with a 
non-synchronized method containing a synchronized block. In other words, this:

public synchronized void doStuff() {
	System.out.println("synchronized");
}

is equivalent to this:

public void doStuff() {
	synchronized(this) {
		System.out.println("synchronized");
	}
}

So What About Static Methods? Can They Be Synchronized?
static methods can be synchronized. There is only one copy of the static data you're trying to protect, 
so you only need one lock per class to synchronize static methods—a lock for the whole class. 
There is such a lock; every class loaded in Java has a corresponding instance of java.lang.Class representing 
that class. It's that java.lang.Class instance whose lock is used to protect the static methods of the class 
(if they're synchronized). There's nothing special you have to do to synchronize a static method:

public static synchronized int getCount() {
	return count;
}

public static int getCount() {
	synchronized(MyClass.class) {
		return count;
	}
}

Wait—what's that MyClass.class thing? That's called a class literal. It's a special feature in the Java 
language that tells the compiler (who tells the JVM): go and find me the instance of Class that represents 
the class called MyClass. You can also do this with the following code:

public static void classMethod() {
	Class cl = Class.forName("MyClass");
	synchronized (cl) {
		// do stuff
	}
}

What Happens If a Thread Can't Get the Lock?
If a thread tries to enter a synchronized method and the lock is already taken, the thread is said to be 
blocked on the object's lock. Essentially, the thread goes into a kind of pool for that particular object 
and has to sit there until the lock is released and the thread can again become runnable/running. Just because 
a lock is released doesn't mean any particular thread will get it. There might be three threads waiting 
for a single lock, for example, and there's no guarantee that the thread that has waited the longest will 
get the lock first.
When thinking about blocking, it's important to pay attention to which objects are being used for locking.
■ Threads calling non-static synchronized methods in the same class will only block each other if they're 
  invoked using the same instance. That's because they each lock on this instance, and if they're called using 
  two different instances, they get two locks, which do not interfere with each other.
■ Threads calling static synchronized methods in the same class will always block each other—they all 
  lock on the same Class instance.
■ A static synchronized method and a non-static synchronized method will not block each other, ever. 
  The static method locks on a Class instance while the non-static method locks on the this instance—these 
  actions do not interfere with each other at all.
■ For synchronized blocks, you have to look at exactly what object has been used for locking. (What's inside 
  the parentheses after the word synchronized?) Threads that synchronize on the same object will block each other.
  Threads that synchronize on different objects will not.


So When Do I Need to Synchronize?
Generally, any time more than one thread is accessing mutable (changeable) data, you synchronize 
to protect that data, to make sure two threads aren't changing it at the same time (or that one isn't changing 
it at the same time the other is reading it, which is also confusing). You don't need to worry about 
local variables—each thread gets its own copy of a local variable. Two threads executing the same method 
at the same time will use different copies of the local variables, and they won't bother each other. 
However, you do need to worry about static and non-static fields, if they contain data that can be changed. 
For changeable data in a non-static field, you usually use a non-static method to access it.

However—what if you have a non-static method that accesses a static field? Or a static method that accesses 
a non-static field (using an instance)? In these cases things start to get messy quickly, and there's a 
very good chance that things will not work the way you want. 
If you've got a static method accessing a non-static field, and you synchronize the method, 
you acquire a lock on the Class object. But what if there's another method that also accesses the non-static field, 
this time using a non-static method? It probably synchronizes on the current instance (this) instead. 
Remember that a static synchronized method and a non-static synchronized method will not block each other—they 
can run at the same time. Similarly, if you access a static field using a non-static method, two threads might 
invoke that method using two different this instances. Which means they won't block each other, because they 
use different locks. Which means two threads are simultaneously accessing the same static field—exactly 
the sort of thing we're trying to prevent.
It gets very confusing trying to imagine all the weird things that can happen here. 
To keep things simple: in order to make a class thread-safe, methods that access changeable fields need 
to be synchronized. Access to static fields should be done from static synchronized methods. 
Access to non-static fields should be done from non-static synchronized methods.

Thread-Safe Classes
-------------------
Many classes in the Java APIs already use synchronization internally in order to make the class "thread-safe." 
For example, StringBuffer and StringBuilder are nearly identical classes, except that all the methods in 
StringBuffer are synchronized when necessary, while those in StringBuilder are not. 
Generally, this makes StringBuffer safe to use in a multithreaded environment, while StringBuilder is not. 
(In return, StringBuilder is a little bit faster because it doesn't bother synchronizing.)

import java.util.*;
public class NameList {
	private List names = Collections.synchronizedList(new LinkedList());

	public void add(String name) {
		names.add(name);
	}
	public String removeFirst() {
		if (names.size() > 0)
			return (String) names.remove(0);
		else
			return null;
	}
}

The method Collections.synchronizedList() returns a List whose methods are all synchronized and 
"thread-safe" according to the documentation (like a Vector—but since this is the 21st century, 
we're not going to use a Vector here).
The question is, can the NameList class be used safely from multiple threads? It's tempting to think 
that yes, since the data in names is in a synchronized collection, the NameList class is "safe" too. 
However that's not the case—the removeFirst() may sometimes throw a NoSuchElementException. 
What's the problem? Doesn't it correctly check the size() of names before removing anything, to make 
sure there's something there? How could this code fail? Let's try to use NameList like this:

public static void main(String[] args) {
	final NameList nl = new NameList();
	nl.add("Ozymandias");
	
	class NameDropper extends Thread {
		public void run() {
			String name = nl.removeFirst();
			System.out.println(name);
		}
	}
	Thread t1 = new NameDropper();
	Thread t2 = new NameDropper();
	t1.start();
	t2.start();
}

The thing to realize here is that in a "thread-safe" class like the one returned by synchronizedList(), 
each individual method is synchronized. So names.size() is synchronized, and names.remove(0) is synchronized. 
But nothing prevents another thread from doing something else to the list in between those two calls. 
And that's where problems can happen.

Thread Deadlock
---------------
Necessary Conditions to be hold simultaneously, following four conditions hold
1. Mutual exclusion.
2. Hold and wait.
3. No preemption.
4. Circular wait.

Perhaps the scariest thing that can happen to a Java program is deadlock. Deadlock occurs when two 
threads are blocked, with each waiting for the other's lock. Neither can run until the other gives 
up its lock, so they'll sit there forever. This can happen, for example, when thread A hits synchronized code, 
acquires a lock B, and then enters another method (still within the synchronized code it has the lock on) 
that's also synchronized. But thread A can't get the lock to enter this synchronized code—block C—because 
another thread D has the lock already. So thread A goes off to the waiting-for-the-C-lock pool, hoping that 
thread D will hurry up and release the lock (by completing the synchronized method). But thread A will wait 
a very long time indeed, because while thread D picked up lock C, it then entered a method synchronized on lock B. 
Obviously, thread D can't get the lock B because thread A has it. And thread A won't release it until thread D 
releases lock C. But thread D won't release lock C until after it can get lock B and continue. And there they sit. 
The following example demonstrates deadlock:

1. public class DeadlockRisk {
2. 	private static class Resource {
3. 		public int value;
4. 	}
5. 	private Resource resourceA = new Resource();
6. 	private Resource resourceB = new Resource();
7. 	public int read() {
8. 		synchronized(resourceA) { // May deadlock here
9. 			synchronized(resourceB) {
10. 				return resourceB.value + resourceA.value;
11. 			}
12. 		}
13. 	}
14.
15. 	public void write(int a, int b) {
16. 		synchronized(resourceB) { // May deadlock here
17. 			synchronized(resourceA) {
18. 				resourceA.value = a;
19. 				resourceB.value = b;
20. 			}
21. 		}
22. 	}
23. }

The preceding simple example is easy to fix; just swap the order of locking for either the reader 
or the writer at lines 16 and 17 (or lines 8 and 9). More complex deadlock situations can take a long time 
to figure out. Regardless of how little chance there is for your code to deadlock, the bottom line is, 
if you deadlock, you're dead. There are design approaches that can help avoid deadlock, including strategies 
for always acquiring locks in a predetermined order.

Thread Interaction:
-------------------
The Object class has three methods, wait(), notify(), and notifyAll() that help threads communicate about 
the status of an event that the threads care about. Using the wait and notify mechanism, the mail-processor 
thread could check for mail, and if it doesn't find any it can say, "Hey, I'm not going to waste my time checking 
for mail every two seconds. I'm going to go hang out, and when the mail deliverer puts something in the mailbox, 
have him notify me so I can go back to runnable and do some work." In other words, using wait() and notify() 
lets one thread put itself into a "waiting room" until some other thread notifies it that there's
a reason to come back out.

wait(), notify(), and notifyAll() must be called from within a synchronized context! A thread can't invoke a 
wait or notify method on an object unless it owns that object's lock.

In the same way that every object has a lock, every object can have a list of threads that are waiting for a 
signal (a notification) from the object. A thread gets on this waiting list by executing the wait() method 
of the target object. From that moment, it doesn't execute any further instructions until the notify() method 
of the target object is called. If many threads are waiting on the same object, only one will be chosen 
(in no guaranteed order) to proceed with its execution.

1. class ThreadA {
2. public static void main(String [] args) {
3. 	ThreadB b = new ThreadB();
4. 	b.start();
5.
6. 	synchronized(b) {
7. 	try {
8. 		System.out.println("Waiting for b to complete...");
9. 		b.wait();
10. 	} catch (InterruptedException e) {}
11. 		System.out.println("Total is: " + b.total);
12. 	}
13. 	}
14. }
15.
16. class ThreadB extends Thread {
17. 	int total;
18.
19. 	public void run() {
20. 		synchronized(this) {
21. 			for(int i=0;i<100;i++) {
22. 				total += i;
23.			 }
24.		 	notify();
25. 		}
26. 	}
27. }

As soon as line 4 calls the start() method, ThreadA will continue with the next line of code 
in its own class, which means it could get to line 11 before ThreadB has finished the calculation. 
To prevent this, we use the wait() method in line 9.
Notice in line 6 the code synchronizes itself with the object b—this is because in order to 
call wait() on the object, ThreadA must own a lock on b. For a thread to call wait() or notify(), 
the thread has to be the owner of the lock for that object.
When the thread waits, it temporarily releases the lock for other threads to use, but it will 
need it again to continue execution.

synchronized(anotherObject) { // this has the lock on anotherObject
	try {
		anotherObject.wait();
		// the thread releases the lock and waits
		// To continue, the thread needs the lock,
		// so it may be blocked until it gets it.
	} 
	catch(InterruptedException e){}
}

The preceding code waits until notify() is called on anotherObject.

synchronized(this) { 
	notify(); 
}

Note that if the thread calling wait() does not own the lock, it will throw an  
IllegalMonitorStateException. This exception is not a checked exception, so you don't have 
to catch it explicitly. You should always be clear whether a thread has the lock of an object 
in any given block of code.

A waiting thread can be interrupted in the same way as a sleeping thread, so you have to 
take care of the exception:

try {
	wait();
} catch(InterruptedException e) {
	// Do something about it
}

When the wait() method is invoked on an object, the thread executing that code gives up its 
lock on the object immediately. However, when notify() is called, that doesn’t mean the thread 
gives up its lock at that moment. If the thread is still completing synchronized code, the lock 
is not released until the thread moves out of synchronized code. So just because notify() is called 
doesn’t mean the lock becomes available at that moment.
-------------------------------------------------------------------------------------------------
Give Up Locks | Keep Locks                                            | Class Defining the Method
-------------------------------------------------------------------------------------------------
wait()        | notify() (Although the thread will probably exit the  | java.lang.Object
              | synchronized code shortly after this call, and thus   | 
	      | give up its locks)                                    |
	      | join()                                                | java.lang.Thread
	      | sleep()                                               | java.lang.Thread
	      | yield()                                               | java.lang.Thread
-------------------------------------------------------------------------------------------------

Using notifyAll) When Many Threads May Be Waiting
-------------------------------------------------
In most scenarios, it's preferable to notify all of the threads that are waiting on a particular object. 
If so, you can use notifyAll() on the object to let all the threads rush out of the waiting area and 
back to runnable. This is especially important if you have several threads waiting on one object, 
but for different reasons, and you want to be sure that the right thread (along with all of the others) 
gets notified.

notifyAll(); // Will notify all waiting threads

As we said earlier, an object can have many threads waiting on it, and using notify() will affect only 
one of them. Which one, exactly, is not specified and depends on the JVM implementation, so you should 
never rely on a particular thread being notified in preference to another. In cases in which there might 
be a lot more waiting, the best way to do this is by using notifyAll().

class Operator extends Thread {
	public void run(){
		while(true){
			// Get shape from user
			synchronized(this){
				// Calculate new machine steps from shape
			notify();
			}
		}
	}
}

class Machine extends Thread {
	Operator operator; // assume this gets initialized
	public void run(){
		while(true){
		synchronized(operator){
			try {
				operator.wait();
			} catch(InterruptedException ie) {}
				// Send machine steps to hardware
			}
			}
		}
	}
}


Using wait() in a Loop : 
------------------------
Actually both of the previous examples (Machine/Operator and Reader/Calculator) 
had a common problem. In each one, there was at least one thread calling wait(), and another 
thread calling notify() or notifyAll(). This works well enough as long as the waiting threads 
have actually started waiting before the other thread executes the notify() or notifyAll(). 
But what happens if, for example, the Calculator runs first and calls notify() before the 
Readers have started waiting?
This could happen, since we can't guarantee what order the different parts of the thread will 
execute in. Unfortunately, when the Readers run, they just start waiting right away. 
They don't do anything to see if the event they're waiting for has already happened. 
So if the Calculator has already called notifyAll(), it's not going to call notifyAll() again—and 
the waiting Readers will keep waiting forever. This is probably not what the programmer wanted 
to happen. Almost always, when you want to wait for something, you also need to be able to check 
if it has already happened. Generally the best way to solve this is to put in some sort of loop 
that checks on some sort of conditional expressions, and only waits if the thing you're waiting 
for has not yet happened. Here's a modified, safer version of the earlier fabric-cutting machine example:


class Operator extends Thread {
	Machine machine; // assume this gets initialized
	public void run() {
		while (true) {
			Shape shape = getShapeFromUser();
			MachineInstructions job = calculateNewInstructionsFor(shape);
			machine.addJob(job);
		}
	}
}

public void run() {
	while (true) {
		synchronized (jobs) {
		// wait until at least one job is available
			while (jobs.isEmpty()) {
			try {
				jobs.wait();
			} catch (InterruptedException ie) { 
			}
			}
			// If we get here, we know that jobs is not empty
			MachineInstructions instructions = jobs.remove(0);
			// Send machine steps to hardware
			}
		}
	}
}

Meanwhile the run() method just keeps looping, looking for any jobs on the list. 
If there are no jobs, it will start waiting. If it's notified, it will stop waiting and 
then recheck the loop condition: is the list still empty? In practice this double-check is 
probably not necessary, as the only time a notify() is ever sent is when a new job has been 
added to the list. However, it's a good idea to require the thread to recheck the isEmpty() 
condition whenever it's been woken up, because it's possible that a thread has accidentally 
sent an extra notify() that was not intended. There's also a possible situation called spontaneous 
wakeup that may exist in some situations—a thread may wake up even though no code has called notify() 
or notifyAll(). (At least, no code you know about has called these methods. Sometimes the JVM may call 
notify() for reasons of its own, or code in some other class calls it for reasons you just don't know.) 
What this means is, when your thread wakes up from a wait(), you don't know for sure why it was awakened. 
By putting the wait() method in a while loop and re-checking the condition that represents what we were 
waiting for, we ensure that whatever the reason we woke up, we will re-enter the wait() if (and only if) 
the thing we were waiting for has not happened yet. 
In the Machine class, the thing we were waiting for is for the jobs list to not be empty. If it's empty, 
we wait, and if it's not, we don't.

The moral here is that when you use wait() and notify() or notifyAll(), you should almost always also 
have a while loop around the wait() that checks a condition and forces continued waiting until the 
condition is met. And you should also make use of the required synchronization for the wait() and notify() 
calls, to also protect whatever other data you're sharing between threads. If you see code which fails to 
do this, there's usually something wrong with the code—even if you have a hard time seeing what 
exactly the problem is.

The methods wait() , notify(), and notifyAll() are methods of only java.lang.Object, 
not of java.lang.Thread or java.lang.Runnable. Be sure you know which methods are defined in Thread, 
which in Object, and which in Runnable (just run(), so that’s an easy one). Of the key methods in Thread, 
be sure you know which are static—sleep() and yield(), and which are not static—join() and start(). 
Table 9-2 lists the key methods you’ll need to know for the exam, with the static methods shown in italics.
------------------------------------------------------
Class Object | Class Thread	 | Interface Runnable
------------------------------------------------------
wait()       | start() 	         | run()
notify()     | join()            |
notifyAll()  | sleep() {static}  |
             | yield() {static}  |
------------------------------------------------------


Q. Runnable Vs Callable
https://www.baeldung.com/java-runnable-callable
