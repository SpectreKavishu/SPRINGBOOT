package com.example.demo;

import java.util.concurrent.Semaphore;

public class ThreadExamples {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(2); // Only 2 permits available
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					// Access resource
				} finally {
					semaphore.release(); // Release permit
				}
			}).start();
		}
	}
	
	String test() {
		return "Testing";
	}

}
