package com.example.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.example.demo.model.Task;
import com.example.demo.model.Task2;

public class ThreadPoolExample {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		ExecutorService executor2 = Executors.newCachedThreadPool();

		Callable<Integer> callableTask = () -> {
			return 10;
		};

		Callable<Integer> ct = new Task2(30);

		Future<Integer> future = executor.submit(callableTask);
		System.out.println(future.get());

		Future<Integer> future2 = executor.submit(ct);
		System.out.println(future2.get());

		// use execute() for simple tasks where you don't need a return value or
		// advanced exception handling. Use submit() when you need to track the task's
		// status, retrieve a result, or handle exceptions more gracefully.

		for (int i = 0; i < 10; i++) {
			Runnable task = new Task("" + i);
			executor.submit(task);
			// executor.execute(task);
		}

		executor.shutdown();
		executor2.shutdown();

		try {
			// Wait for all tasks to complete or timeout after 60 seconds
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				// If timeout happens, force shutdown
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
		
		System.out.println("All tasks completed");

	}
}
