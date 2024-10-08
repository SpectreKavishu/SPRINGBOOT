package com.example.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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

		// Create a Stack of integers
		Stack<Integer> stack = new Stack<>();

		// Push elements onto the stack
		stack.push(10);
		stack.push(20);
		stack.push(30);

		// Display the stack elements
		System.out.println("\nStack after pushing elements:");
		stack.display();

		// Peek the top element
		System.out.println("\nTop element is: " + stack.peek());

		// Pop the top element
		System.out.println("\nPopped element: " + stack.pop());

		// Display the stack after popping
		System.out.println("\nStack after popping an element:");
		stack.display();

		// Check if the stack is empty
		System.out.println("\nIs stack empty? " + stack.isEmpty());

		// Display the current size of the stack
		System.out.println("Current stack size: " + stack.size());
	}

}

class SemaphoreExample {

	public static void main(String[] args) throws InterruptedException {
		// Only 2 permits available
		Semaphore semaphore = new Semaphore(2);

		// Create 5 threads trying to access the resource
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire(); // Acquire a permit
					try {
						// Simulate access to a shared resource
						System.out.println(Thread.currentThread().getName() + " is accessing the resource...");
						Thread.sleep(1000); // Simulate some work with the resource
					} finally {
						System.out.println(Thread.currentThread().getName() + " has finished.");
						semaphore.release(); // Release the permit
					}
				} catch (InterruptedException e) {
					e.printStackTrace(); // Handle thread interruption
				}
			}).start();
		}

		CountDownLatch latch = new CountDownLatch(3);
		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " started accessing the resource");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getName() + " has finished");
					latch.countDown();
				}
			}).start();
		}
		latch.await();
		System.out.println("All latch down -> All tasks completed!");
		System.out.println();

		CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Barrier reached!"));
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " started");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getName() + " ended");
				}
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
		}

		ReentrantLock lock1 = new ReentrantLock(true); // fair lock to avoid starvation problem
		ReentrantLock lock2 = new ReentrantLock(true);

		// tryLock to avoid deadlock problem
		// use tryAcquireResource i.e. retry with back-off strategy to avoid live-lock
		// problem

		if (lock1.tryLock()) {
			try {
				if (lock2.tryLock()) {
					try {
						// Do work
					} finally {
						lock2.unlock();
					}
				}
			} finally {
				lock1.unlock();
			}
		}

		AtomicInteger val = new AtomicInteger(0); // atomic variables are synchronized
		val.incrementAndGet();

	}
}

class Stack<T> {
	// FILO or LIFO

	LinkedList<T> list = new LinkedList<>();

	void push(T data) {
		list.addLast(data);
	}

	T pop() {
		if (!list.isEmpty()) {
			return list.removeLast(); // Remove and return the last element (top of the stack)
		}
		throw new RuntimeException("Stack is empty!");
	}

	T peek() {
		if (!list.isEmpty()) {
			return list.getLast(); // Get the last element (top of the stack) without removing
		}
		throw new RuntimeException("Stack is empty!");
	}

	boolean isEmpty() {
		return list.isEmpty();
	}

	// Get the size of the stack
	int size() {
		return list.size();
	}

	void display() {
		for (T item : list) {
			System.out.println(item);
		}
	}

}

class DuplicateFinder {
	public static void main(String[] args) {

		int[] array = { 1, 3, 1, 5, 2, 1, 2, 5, 7, 5, 1, 4 };

		java.util.List<Integer> a = List.of(1, 3, 1, 5, 2, 1, 2, 5, 7, 5, 1, 4);

		Map<Integer, Long> duplicates = Arrays.stream(array).boxed()
				.collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

		System.out.println(a.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())));

		System.out.println(duplicates);

	}
}

class AnagramCheck {
	// check if they have the same characters with the same frequencies
	public static void main(String[] args) {

		String s1 = "a gentleman";
		String s2 = "elegant man";

		Map<Character, Long> map1 = s1.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
		Map<Character, Long> map2 = s2.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));

		System.out.println(map1.equals(map2));

	}
}

class AnagramGrouping {

	public static Map<String, List<String>> groupAnagrams(List<String> words) {
		return words.stream().collect(Collectors.groupingBy(AnagramGrouping::sortString));
	}

	private static String sortString(String str) {
		char[] chars = str.toCharArray();
		java.util.Arrays.sort(chars);
		return new String(chars);
	}

	public static void main(String[] args) {
		List<String> list = List.of("listen", "goyalk", "silent", "monday", "enlist");
		Map<String, List<String>> anagramGroups = groupAnagrams(list);
		System.out.println(anagramGroups.values().stream().filter(value -> value.size() > 1).toList());
	}
}

class QueueUsingStacks {

	private Stack<Integer> stack1;
	private Stack<Integer> stack2;

	public QueueUsingStacks() {
		stack1 = new Stack<>();
		stack2 = new Stack<>();
	}

	public void enqueue(int x) {
		stack1.push(x);
	}

	public int dequeue() {
		if (stack2.isEmpty()) {
			while (!stack1.isEmpty()) {
				stack2.push(stack1.pop()); // this will push elements to stack2 in reverse order

			}
		}

		if (stack2.isEmpty()) {
			return -1; // Queue is empty
		} else {
			return stack2.pop(); // LIFO
		}
	}

	public static void main(String[] args) {
		QueueUsingStacks queue = new QueueUsingStacks();

		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		System.out.println(queue.dequeue()); // Output: 1
		System.out.println(queue.dequeue()); // Output: 2
		System.out.println(queue.dequeue()); // Output: 3
	}
}

class BalancedParentheses {

	public static boolean isBalanced(String expression) {
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			if (c == '(' || c == '{' || c == '[') {
				stack.push(c);
			} else if (c == ')' || c == '}' || c == ']') {
				if (stack.isEmpty() || !isMatchingPair(stack.peek(), c)) {
					return false;
				}
				stack.pop();
			}
		}

		return stack.isEmpty();
	}

	private static boolean isMatchingPair(char openingBracket, char closingBracket) {
		return (openingBracket == '(' && closingBracket == ')') || (openingBracket == '{' && closingBracket == '}')
				|| (openingBracket == '[' && closingBracket == ']');
	}

	public static void main(String[] args) {
		String expression1 = "()";
		String expression2 = "({[]})";
		String expression3 = "([)]";

		System.out.println(isBalanced(expression1)); // Output: true
		System.out.println(isBalanced(expression2)); // Output: true
		System.out.println(isBalanced(expression3)); // Output: false
	}
}

class BinaryTreeHeight {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static int findHeight(TreeNode root) {
		if (root == null) {
			return -1;
		}

		int leftHeight = findHeight(root.left);
		int rightHeight = findHeight(root.right);

		return Math.max(leftHeight, rightHeight) + 1;
	}

	public static boolean isBalanced(TreeNode root) {
		if (root == null) {
			return true;
		}

		int leftHeight = findHeight(root.left);
		int rightHeight = findHeight(root.right);

		if (Math.abs(leftHeight - rightHeight) > 1) {
			return false;
		}

		return isBalanced(root.left) && isBalanced(root.right);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);

		int height = findHeight(root);
		System.out.println("Height of the binary tree: " + height); // Output: 2
		System.out.println("Is the binary tree balanced? " + isBalanced(root)); // Output: true
	}
}

class LowestCommonAncestor {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) {
			return root;
		}

		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);

		if (left != null && right != null) {
			return root;
		}

		return left != null ? left : right;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);

		TreeNode p = root.left.left; // Node with value 4
		TreeNode q = root.right.right; // Node with value 7

		TreeNode lca = lowestCommonAncestor(root, p, q);
		System.out.println("Lowest Common Ancestor: " + lca.val); // Output: 1
	}
}

class Graphs {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	class DFS {
		public static void traverse(TreeNode root) {
			if (root == null) {
				return;
			}

			// Visit the root
			System.out.print(root.val + " ");

			// Traverse left subtree
			traverse(root.left);

			// Traverse right subtree
			traverse(root.right);
		}
	}

	class BFS {
		public static void traverse(TreeNode root) {
			if (root == null) {
				return;
			}

			Queue<TreeNode> queue = new LinkedList<>();
			queue.offer(root);

			while (!queue.isEmpty()) {
				TreeNode current = queue.poll();
				System.out.print(current.val + " ");

				// Enqueue left child
				if (current.left != null) {
					queue.offer(current.left);
				}
				// Enqueue right child
				if (current.right != null) {
					queue.offer(current.right);
				}
			}
		}
	}

	public static void main(String[] args) {
		// Create a simple binary tree
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);

		// Perform DFS
		System.out.println("Depth-First Search (DFS):");
		DFS.traverse(root);

		System.out.println("\n\nBreadth-First Search (BFS):");
		// Perform BFS
		BFS.traverse(root);
	}
}

class KthLargestElement {
	public static int findKthLargest(int[] nums, int k) {
		// Create a min-heap to store the k largest elements
		PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

		for (int num : nums) {
			minHeap.add(num); // Add the current number to the min-heap

			// If the size of the min-heap exceeds k, remove the smallest element
			if (minHeap.size() > k) {
				minHeap.poll();
			}
		}

		// The root of the min-heap is the k-th largest element
		return minHeap.peek();
	}

	public static int findKthSmallest(int[] nums, int k) {
		// Create a min-heap to store the k largest elements
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

		for (int num : nums) {
			maxHeap.add(num); // Add the current number to the min-heap

			// If the size of the min-heap exceeds k, remove the smallest element
			if (maxHeap.size() > k) {
				maxHeap.poll();
			}
		}

		// The root of the min-heap is the k-th largest element
		return maxHeap.peek();
	}

	public static void main(String[] args) {
		int[] array = { 3, 2, 1, 5, 6, 4 };
		int k = 2;

		int kthLargest = findKthLargest(array, k);
		System.out.println("The " + k + "-th largest element is: " + kthLargest); // Should return 5

		int kthSmallest = findKthSmallest(array, k);
		System.out.println("The " + k + "-th smallest element is: " + kthSmallest); // Should return 2
	}
}

class TreeTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public static void inorderTraversal(TreeNode root) {
		if (root != null) {
			inorderTraversal(root.left);
			System.out.print(root.val + " ");
			inorderTraversal(root.right);
		}
	}

	public static void preorderTraversal(TreeNode root) {
		if (root != null) {
			System.out.print(root.val + " ");
			preorderTraversal(root.left);
			preorderTraversal(root.right);
		}
	}

	public static void postorderTraversal(TreeNode root) {
		if (root != null) {
			postorderTraversal(root.left);
			postorderTraversal(root.right);
			System.out.print(root.val + " ");
		}
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);

		System.out.println("Inorder Traversal:");
		inorderTraversal(root);
		System.out.println();

		System.out.println("Preorder Traversal:");
		preorderTraversal(root);
		System.out.println();

		System.out.println("Postorder Traversal:");
		postorderTraversal(root);
	}
}

class ArrayRotator {

	// Function to rotate the array to the right
	public static void rotateRight(int[] arr, int positions) {
		int n = arr.length;
		positions = positions % n; // Handle positions greater than array length
		reverse(arr, 0, n - 1); // Reverse the entire array
		reverse(arr, 0, positions - 1); // Reverse the first 'positions' elements
		reverse(arr, positions, n - 1); // Reverse the remaining elements
	}

	// Function to rotate the array to the left
	public static void rotateLeft(int[] arr, int positions) {
		int n = arr.length;
		positions = positions % n; // Handle positions greater than array length
		reverse(arr, 0, positions - 1); // Reverse the first 'positions' elements
		reverse(arr, positions, n - 1); // Reverse the remaining elements
		reverse(arr, 0, n - 1); // Reverse the entire array
	}

	// Helper function to reverse a portion of the array
	private static void reverse(int[] arr, int start, int end) {
		while (start < end) {
			int temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++;
			end--;
		}
	}

	// Main method for testing
	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 4, 5 };

		// Rotate right by 2 positions
		rotateRight(array, 2);
		System.out.println("Array after right rotation: " + java.util.Arrays.toString(array));

		// Rotate left by 2 positions
		rotateLeft(array, 2);
		System.out.println("Array after left rotation: " + java.util.Arrays.toString(array));
	}
}

class BinarySearch {

	public static int binarySearch(int[] arr, int target) {
		int left = 0;
		int right = arr.length - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2; // To prevent overflow

			// Check if the target is present at mid
			if (arr[mid] == target) {
				return mid; // Target found, return its index
			}

			// If target is greater, ignore left half
			if (arr[mid] < target) {
				left = mid + 1;
			} else {
				// If target is smaller, ignore right half
				right = mid - 1;
			}
		}

		// Target was not found
		return -1;
	}

	public static void main(String[] args) {
		int[] arr = { 2, 3, 4, 10, 40 };
		int target = 10;
		int result = binarySearch(arr, target);

		if (result == -1) {
			System.out.println("Element not found in the array.");
		} else {
			System.out.println("Element found at index: " + result);
		}
	}
}

class BinarySearchInRotatedArray {
	public static int search(int[] nums, int target) {
		int left = 0;
		int right = nums.length - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;

			// Check if the mid element is the target
			if (nums[mid] == target) {
				return mid; // Target found
			}

			// Determine which side is sorted
			if (nums[left] <= nums[mid]) { // Left side is sorted
				if (nums[left] <= target && target < nums[mid]) {
					right = mid - 1; // Target is in the left side
				} else {
					left = mid + 1; // Target is in the right side
				}
			} else { // Right side is sorted
				if (nums[mid] < target && target <= nums[right]) {
					left = mid + 1; // Target is in the right side
				} else {
					right = mid - 1; // Target is in the left side
				}
			}
		}

		return -1; // Target not found
	}

	public static void main(String[] args) {
		int[] array = { 4, 5, 6, 7, 0, 1, 2 };
		int target = 0;

		int result = search(array, target);
		if (result != -1) {
			System.out.println("Element found at index: " + result);
		} else {
			System.out.println("Element not found.");
		}
	}
}

class DuplicateCharacterCount {
	public static void main(String[] args) {
		String s = "test";

		long duplicateCount = s.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(c -> c, Collectors.counting())).values().stream()
				.filter(count -> count > 1).count();

		System.out.println(
				"--- " + s.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting()))
						.entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).toList());

		System.out.println("Number of duplicate characters: " + duplicateCount);
		System.out.println(
				s.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting())));
	}
}

class LiftTripCount {
	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 3, 3 };
		// int[] arrayx = { 2, 2, 2, 2 };
		int N = array.length;
		int K = 2;
		calculateTrips(N, K, array);
	}

	static int calculateTrips(int N, int K, int[] array) {
		Map<Integer, Integer> map = new HashMap<>();

		// Count the number of people on each floor
		for (int x : array) {
			map.put(x, map.getOrDefault(x, 0) + 1);
		}

		// Print the number of floors
		int floorCount = map.size();
		System.out.println("No. of floors: " + floorCount);

		// Calculate the number of trips needed
		int totalTrips = 0;

		for (int personCount : map.values()) {
			int tripsForFloor = (personCount + K - 1) / K; // This formula calculates the ceiling of personCount / K
			totalTrips += tripsForFloor;
		}

		// Calculate the number of trips needed using Streams
		int totalTrips2 = map.values().stream().mapToInt(personCount -> (personCount + K - 1) / K) // Calculate trips
																									// for each floor
				.sum(); // Sum up all the trips

		System.out.println("Trips: " + totalTrips);
		System.out.println("Trips: " + totalTrips2);

		return totalTrips;
	}

}

class Factorial {

	public static void main(String[] args) {
		int number = 5; // Change this number to calculate a different factorial
		long result = factorial(number);
		System.out.println("Factorial of " + number + " is " + result);
	}

	public static long factorial(int n) {
		long result = 1;
		for (int i = 2; i <= n; i++) {
			result *= i;
		}
		return result;
	}
}

class StringPermutations {

	public static void main(String[] args) {
		String str = "abc";
		System.out.println(Factorial.factorial(str.length()));
		generatePermutations("", str);
	}

	private static void generatePermutations(String prefix, String remaining) {
		if (remaining.isEmpty()) {
			System.out.println(prefix);
		} else {
			for (int i = 0; i < remaining.length(); i++) {
				// Choose the current character and generate permutations for the rest
				generatePermutations(prefix + remaining.charAt(i),
						remaining.substring(0, i) + remaining.substring(i + 1));
			}
		}
	}
}

class FirstRepeatingCharacter {

	public static void main(String[] args) {
		String str = "kavishugoyal";
		System.out.println("First repeating character: " + firstRepeating(str));
	}

	public static char firstRepeating(String s) {
		HashMap<Character, Integer> charCount = new HashMap<>();

		for (char c : s.toCharArray()) {
			charCount.put(c, charCount.getOrDefault(c, 0) + 1);
			if (charCount.get(c) > 1) {
				return c;
			}
		}

		return '\0'; // Return null character if none found
	}
}

class FirstNonRepeatingCharacter {

	public static void main(String[] args) {
		String str = "kkavishugoyal";
		System.out.println("First non-repeating character: " + firstNonRepeating(str));
	}

	public static char firstNonRepeating(String s) {
		Map<Character, Integer> charCount = new HashMap<>(); // No specific order
		Map<Character, Integer> charCount2 = new LinkedHashMap<>(); // Maintains insertion order

		for (char c : s.toCharArray()) {
			charCount.put(c, charCount.getOrDefault(c, 0) + 1);
			charCount2.put(c, charCount2.getOrDefault(c, 0) + 1);
		}

		for (Map.Entry<Character, Integer> map : charCount2.entrySet()) {
			if (map.getValue() == 1) {
				System.out.println("via LinkedHashMap: " + map.getKey());
				break;
			}
		}

		// Find the first non-repeating character
		for (char c : s.toCharArray()) { // Iterate through the string again
			if (charCount.get(c) == 1) {
				return c;
			}
		}

		return Character.MIN_VALUE; // Return a special value if none found

	}
}
