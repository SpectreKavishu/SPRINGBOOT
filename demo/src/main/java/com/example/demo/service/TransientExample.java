package com.example.demo.service;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private transient String password; // Transient field

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{name='" + name + "', password='" + password + "'}";
	}
}

@Slf4j
public class TransientExample {
	public static void main(String[] args) {
		User user = new User("JohnDoe", "secret123");

		// Serialize the object
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
			out.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// De-serialize the object
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("user.ser"))) {
			User deserializedUser = (User) in.readObject();
			log.info("Deserialized User: " + deserializedUser);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class LongestIncreasingSubsequence {

	public static int lengthOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}

		int n = nums.length;
		int[] dp = new int[n];

		// Initialize dp array, each element is at least a subsequence of length 1
		Arrays.fill(dp, 1);

		// Fill dp array
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
		}

		// Find the maximum length from the dp array
		int maxLength = 0;
		for (int length : dp) {
			maxLength = Math.max(maxLength, length);
		}

		return maxLength;
	}

	public static void main(String[] args) {
		int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
		int lisLength = lengthOfLIS(nums);
		System.out.println("Length of Longest Increasing Subsequence: " + lisLength);
	}
}

class CoinChange {

	public static int coinChange(int[] coins, int amount) {
		// Create a DP array to store the minimum number of coins for each amount
		int[] dp = new int[amount + 1];
		// Initialize the DP array with a large number (infinity)
		Arrays.fill(dp, amount + 1);
		dp[0] = 0; // No coins needed to make amount 0

		// Iterate through each coin
		for (int coin : coins) {
			// Update the DP array for amounts that can be formed using the coin
			for (int i = coin; i <= amount; i++) {
				dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}

		// If dp[amount] is still amount + 1, it means we couldn't form that amount
		return dp[amount] == amount + 1 ? -1 : dp[amount];
	}

	public static void main(String[] args) {
		int[] coins = { 1, 2, 5 }; // Coin denominations
		int amount = 11; // Target amount
		int minCoins = coinChange(coins, amount);

		if (minCoins != -1) {
			System.out.println("Minimum number of coins needed: " + minCoins);
		} else {
			System.out.println("Cannot make the target amount with the given coins.");
		}
	}
}

class MaximumSubarraySum {

	public static int maxSubArray(int[] nums) {
		// Initialize current sum and max sum
		int maxSum = nums[0];
		int currentSum = nums[0];

		// Iterate through the array starting from the second element
		for (int i = 1; i < nums.length; i++) {
			// Update current sum to either the current element or the current sum plus the
			// current element
			currentSum = Math.max(nums[i], currentSum + nums[i]);
			// Update max sum if current sum is greater
			maxSum = Math.max(maxSum, currentSum);
		}

		return maxSum;
	}

	public static void main(String[] args) {
		int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		int maxSum = maxSubArray(nums);
		System.out.println("Maximum subarray sum: " + maxSum);
	}
}

class Fibonacci {

	// Map to store previously computed Fibonacci numbers
	private static Map<Integer, Long> memo = new HashMap<>();

	public static long fibonacci(int n) {
		// Base cases
		if (n <= 1) {
			return n;
		}

		// Check if the value is already computed
		if (memo.containsKey(n)) {
			return memo.get(n);
		}

		// Compute Fibonacci and store it in the map
		long fibValue = fibonacci(n - 1) + fibonacci(n - 2);
		memo.put(n, fibValue);

		return fibValue;
	}

	public static void main(String[] args) {
		int n = 50; // Example input
		long result = fibonacci(n);
		System.out.println("Fibonacci number at position " + n + " is: " + result);
	}
}

class QuickSort {
	public static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int pivotIndex = partition(arr, low, high);
			quickSort(arr, low, pivotIndex - 1);
			quickSort(arr, pivotIndex + 1, high);
		}
	}

	private static int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int i = low - 1;

		for (int j = low; j < high; j++) {
			if (arr[j] <= pivot) {
				i++;
				swap(arr, i, j);
			}
		}

		swap(arr, i + 1, high);
		return i + 1;
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		int[] arr = { 64, 34, 25, 12, 22, 11, 90 };
		System.out.println("Unsorted array:");
		printArray(arr);

		quickSort(arr, 0, arr.length - 1);

		System.out.println("\nSorted array:");
		printArray(arr);
	}

	private static void printArray(int[] arr) {
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}
}
