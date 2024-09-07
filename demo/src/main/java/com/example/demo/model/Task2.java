package com.example.demo.model;

import java.util.concurrent.Callable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Task2 implements Callable<Integer> {
	
	private Integer value;

	@Override
	public Integer call() throws Exception {
		return value;
	}

}
