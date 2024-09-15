package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.example.demo.model.Task;
import com.example.demo.model.Task2;
import com.example.demo.service.TestInterface;

public class ThreadPoolExample {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		ExecutorService executor2 = Executors.newCachedThreadPool();
		
		String st = null;
		System.out.println("string: "+Optional.ofNullable(st).orElse("Unknown"));

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

		// functional interface, lambdas, method references
		Predicate<Integer> p = (num) -> num % 2 == 0;
		System.out.println("is 4 even: " + p.test(4));

		Consumer<String> c = (msg) -> System.out.println(msg);
		c.accept("kavishu");

		TestInterface obj = (msg) -> System.out.println(msg);
		obj.displayMessage("goyal");

		Supplier<ThreadExamples> s = ThreadExamples::new;
		System.out.println(s.get().test());

		Function<Integer, String> f = (num) -> "" + num;
		System.out.println(f.apply(8));

		List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		List<Integer> squared = values.stream().distinct().filter(p).map(num -> num * num).sorted((a, b) -> b - a)
				.collect(Collectors.toList());
		System.out.println(squared);

		int sum = values.stream().mapToInt(Integer::intValue).sum();
		int sum2 = values.stream().reduce(0, Integer::sum);
		int max = values.stream().reduce(0, Integer::max);
		System.out.println("sum: " + sum);
		System.out.println("sum2: " + sum2);
		System.out.println("max: " + max);

		List<String> words = List.of("python", "axe", "java");
		Predicate<String> lengthGreaterThan3 = word -> word.length() > 3;
		List<String> filteredAndUppercasedWords = words.stream().filter(lengthGreaterThan3).map(String::toUpperCase)
				.collect(Collectors.toList());
		System.out.println(filteredAndUppercasedWords);
		System.out.println(filteredAndUppercasedWords.get(0));
		System.out.println(words.stream().filter(lengthGreaterThan3).findFirst());

		List<List<String>> listOfLists = Arrays.asList(Arrays.asList("a", "b", "c"), Arrays.asList("d", "e"),
				Arrays.asList("f", "g", "h"));
		// Flatten the lists into a single stream
		List<String> flattenedList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());
		System.out.println(flattenedList);

		values.parallelStream().filter(p).forEach(System.out::println);

		String input = "madam";
		boolean isPalindrome = input.equals(new StringBuilder(input).reverse().toString());
		System.out.println("Is palindrome: " + isPalindrome);

	}
}
