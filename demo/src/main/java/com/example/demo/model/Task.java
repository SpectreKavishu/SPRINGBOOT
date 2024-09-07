package com.example.demo.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Task implements Runnable {

	private String taskName;

	@Override
	public void run() {
		System.out.println("Executing Task " + taskName + " on thread " + Thread.currentThread().getName());
	}

}
