package com.example.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.example.demo.model.Task;
import com.example.demo.model.Task2;

public class ThreadPoolExample {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Callable<Integer> callableTask = () -> {
			return 10;
		};
		
		Callable<Integer> ct = new Task2(30);
		
		Future<Integer> future = executor.submit(callableTask);
		System.out.println(future.get());
		
		Future<Integer> future2 = executor.submit(ct);
		System.out.println(future2.get());

		for (int i = 0; i < 10; i++) {
			Runnable task = new Task("" + i);
			executor.submit(task);
		}
		
		executor.shutdown();
	}
}
