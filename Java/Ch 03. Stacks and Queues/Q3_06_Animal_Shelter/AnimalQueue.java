/*
We could explore a variety of solutions to this problem. For instance, we could maintain a single queue.
This would make dequeueAny easy, but dequeueDog and dequeueCat would require iteration through
the queue to find the first dog or cat. This would increase the complexity of the solution and decrease the
efficiency.
An alternative approach that is simple, clean and efficient is to simply use separate queues for dogs and
cats, and to place them within a wrapper class called AnimalQueue. We then store some sort of timestamp
to mark when each animal was enqueued. When we call dequeueAny, we peek at the heads of both the
dog and cat queue and return the oldest.

It is important that Dog and Cat both inherit from an Animal class since dequeueAny() needs to be able
to support returning both Dog and Cat objects.
If we wanted, order could be a true timestamp with the actual date and time. The advantage of this is that
we wouldn't have to set and maintain the numerical order. If we somehow wound up with two animals with
the same timestamp, then (by definition) we don't have an older animal and we could return either one.
*/
package Q3_06_Animal_Shelter;

import java.util.LinkedList;

public class AnimalQueue {
	LinkedList<Dog> dogs = new LinkedList<Dog>();
	LinkedList<Cat> cats = new LinkedList<Cat>();
	private int order = 0;				//used as timestamp
	
	public void enqueue(Animal a) {
		a.setOrder(order);
		order++;
		if (a instanceof Dog) {
			dogs.addLast((Dog) a);
		} else if (a instanceof Cat) {
			cats.addLast((Cat)a);
		}
	}
	
	public Animal dequeueAny() {
		if (dogs.size() == 0) {
			return dequeueCats();
		} else if (cats.size() == 0) {
			return dequeueDogs();
		}
		Dog dog = dogs.peek();
		Cat cat = cats.peek();
		if (dog.isOlderThan(cat)) {
			return dogs.poll();
		} else {
			return cats.poll();
		}
	}
	
	public Animal peek() {
		if (dogs.size() == 0) {
			return cats.peek();
		} else if (cats.size() == 0) {
			return dogs.peek();
		}
		Dog dog = dogs.peek();
		Cat cat = cats.peek();
		if (dog.isOlderThan(cat)) {
			return dog;
		} else {
			return cat;
		}
	}
	
	public int size() {
		return dogs.size() + cats.size();
	}
	
	public Dog dequeueDogs() {
		return dogs.poll();
	}
	
	public Dog peekDogs() {
		return dogs.peek();
	}
	
	public Cat dequeueCats() {
		return cats.poll();
	}
	
	public Cat peekCats() {
		return cats.peek();
	}
}
