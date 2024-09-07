package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.demo.model.Task;

public class ThreadPoolExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 10; i++) {
			Runnable task = new Task("" + i);
			executor.submit(task);
		}
		executor.shutdown();
	}
}
