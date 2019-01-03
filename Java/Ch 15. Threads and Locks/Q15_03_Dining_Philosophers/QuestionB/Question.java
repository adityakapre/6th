/*
Solution #1: All or Nothing
To prevent deadlocks, we can implement a strategy where a philosopher will put down his left chopstick if
he is unable to obtain the right one.

In the above code, we need to be sure to release the left chopstick if we can't pick up the right one-and to
not call putDown() on the chopsticks if we never had them in the first place.
One issue with this is that if all the philosophers were perfectly synchronized, they could simultaneously
pick up their left chopstick, be unable to pick up the right one, and then put back down the left one-only
to have the process repeated again.

Solution #2: Prioritized Chopsticks
Alternatively, we can label the chopsticks with a number from 0 to N - 1. Each philosopher attempts to
pick up the lower numbered chopstick first. This essentially means that each philosopher goes for the left
chopstick before right one (assuming that's the way you labeled it), except for the last philosopher who
does this in reverse. This will break the cycle.

With this solution, a philosopher can never hold the larger chopstick without holding the smaller one. This
prevents the ability to have a cycle, since a cycle means that a higher chopstick would "point"to a lower one.
*/
package Q15_03_Dining_Philosophers.QuestionB;

public class Question {
	public static int size = 3;
	
	public static int leftOf(int i) {
		return i;
	}
	
	public static int rightOf(int i) {
		return (i + 1) % size;
	}
	
	public static void main(String[] args) {		
		Chopstick[] chopsticks = new Chopstick[size + 1];
		for (int i = 0; i < size + 1; i++) {
			chopsticks[i] = new Chopstick(i);
		}
		
		Philosopher[] philosophers = new Philosopher[size];
		for (int i = 0; i < size; i++) {
			Chopstick left = chopsticks[leftOf(i)];
			Chopstick right = chopsticks[rightOf(i)];
			philosophers[i] = new Philosopher(i, left, right);
		}
		
		for (int i = 0; i < size; i++) {
			philosophers[i].start();
		}		
	}

}
