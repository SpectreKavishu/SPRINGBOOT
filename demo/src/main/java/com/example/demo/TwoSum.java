package com.example.demo;

import java.util.HashMap;

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
	}
}
