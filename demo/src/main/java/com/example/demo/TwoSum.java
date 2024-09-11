package com.example.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TwoSum {
	public int[] twoSum(int[] nums, int target) {
		// Create a HashMap to store the values and their indices
		HashMap<Integer, Integer> map = new HashMap<>();

		// Traverse the array
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i]; // Calculate the complement

			// If the complement exists in the map, return the indices
			if (map.containsKey(complement)) {
				return new int[] { map.get(complement), i };
			}

			// Otherwise, store the current number with its index
			map.put(nums[i], i);
			System.out.println(map);
		}

		// Return an empty array if no solution is found
		throw new IllegalArgumentException("No two sum solution");
	}

	public static void main(String[] args) {
		TwoSum ts = new TwoSum();
		int[] nums = { 2, 3, 7, 11, 15 };
		int target = 9;

		int[] result = ts.twoSum(nums, target);
		System.out.println("Indices: " + result[0] + ", " + result[1]);
		System.out.println("Pair: " + nums[result[0]] + ", " + nums[result[1]]);
		
		// Create a Stack of integers
        Stack<Integer> stack = new Stack<>();
        
        // Push elements onto the stack
        stack.push(10);
        stack.push(20);
        stack.push(30);
        
        // Display the stack elements
        System.out.println("\nStack after pushing elements:");
        stack.display();
        
        // Peek the top element
        System.out.println("\nTop element is: " + stack.peek());
        
        // Pop the top element
        System.out.println("\nPopped element: " + stack.pop());
        
        // Display the stack after popping
        System.out.println("\nStack after popping an element:");
        stack.display();
        
        // Check if the stack is empty
        System.out.println("\nIs stack empty? " + stack.isEmpty());
        
        // Display the current size of the stack
        System.out.println("Current stack size: " + stack.size());
	}

}

class SemaphoreExample {

	public static void main(String[] args) throws InterruptedException {
		// Only 2 permits available
		Semaphore semaphore = new Semaphore(2);

		// Create 5 threads trying to access the resource
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire(); // Acquire a permit
					try {
						// Simulate access to a shared resource
						System.out.println(Thread.currentThread().getName() + " is accessing the resource...");
						Thread.sleep(1000); // Simulate some work with the resource
					} finally {
						System.out.println(Thread.currentThread().getName() + " has finished.");
						semaphore.release(); // Release the permit
					}
				} catch (InterruptedException e) {
					e.printStackTrace(); // Handle thread interruption
				}
			});
			// .start();
		}

		CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Barrier reached!"));
		CountDownLatch latch = new CountDownLatch(3);

		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " is accessing the resource...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getName() + " has finished.");
					latch.countDown();
				}
				// barrier.await();
			}).start();
		}
		latch.await();

		ReentrantLock lock1 = new ReentrantLock(true);
		ReentrantLock lock2 = new ReentrantLock(true);

		if (lock1.tryLock()) {
			try {
				if (lock2.tryLock()) {
					try {
						// Do work
					} finally {
						lock2.unlock();
					}
				}
			} finally {
				lock1.unlock();
			}
		}

		AtomicInteger val = new AtomicInteger(0);
		val.incrementAndGet();

	}
}

class Stack<T> {
	// FILO or LIFO
	
	LinkedList<T> list = new LinkedList<>();
	
	void push(T data) {
		list.addLast(data);
	}
	
	T pop() {
        if (!list.isEmpty()) {
            return list.removeLast();  // Remove and return the last element (top of the stack)
        }
        throw new RuntimeException("Stack is empty!");
    }
	
	T peek() {
        if (!list.isEmpty()) {
            return list.getLast();  // Get the last element (top of the stack) without removing
        }
        throw new RuntimeException("Stack is empty!");
    }
	
	boolean isEmpty() {
        return list.isEmpty();
    }
	
	// Get the size of the stack
    int size() {
        return list.size();
    }
	
	void display() {
		for(T item : list) {
			System.out.println(item);
		}
	}
	
	
} 
