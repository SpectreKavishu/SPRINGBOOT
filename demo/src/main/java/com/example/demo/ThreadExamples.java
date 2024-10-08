package com.example.demo;

import java.util.Arrays;
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

class Median {

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 5, 7 };
		int[] a2 = { 2, 4, 6, 8 };
		int[] combined = new int[a1.length + a2.length];
		int k = 0;

		for (int i : a1) {
			combined[k++] = i;
		}
		for (int j : a2) {
			combined[k++] = j;
		}
		java.util.Arrays.sort(combined);
		Arrays.stream(combined).forEach(val -> System.out.print(val + " "));
		int left = 0;
		int right = combined.length - 1;
		int mid = left + (right - left) / 2;
		if (combined.length % 2 == 0)
			System.out.println("Median: " + (combined[mid] + combined[mid + 1]) / 2.0); // for even length array
		else
			System.out.println("Median: " + combined[mid]); // for odd length array
	}

}
