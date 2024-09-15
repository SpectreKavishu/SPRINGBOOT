package com.example.demo.service;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class SumTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private final int[] numbers;
	private final int start, end;
	private final int THRESHOLD = 10; // Threshold for splitting

	public SumTask(int[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		if (end - start <= THRESHOLD) {
			int sum = 0;
			for (int i = start; i < end; i++) {
				sum += numbers[i];
			}
			return sum;
		} else {
			int mid = (start + end) / 2;
			SumTask leftTask = new SumTask(numbers, start, mid);
			SumTask rightTask = new SumTask(numbers, mid, end);
			leftTask.fork(); // Fork the left task
			int rightResult = rightTask.compute(); // Directly compute the right task
			int leftResult = leftTask.join(); // Wait for left task result
			return leftResult + rightResult;
		}
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		int[] numbers = new int[100];
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i + 1;

		SumTask task = new SumTask(numbers, 0, numbers.length);
		int result = pool.invoke(task);
		System.out.println("Sum: " + result);
		pool.shutdown();
	}
}
