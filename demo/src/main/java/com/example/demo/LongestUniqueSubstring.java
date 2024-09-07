package com.example.demo;

import java.util.HashSet;
import java.util.Set;

public class LongestUniqueSubstring {
    public static void main(String[] args) {
        String input = "abcabcbb";
        int longestLength = findLongestUniqueSubstring(input);
        System.out.println("Longest unique substring length: " + longestLength);  // Output: 3 ("abc")
    }

    public static int findLongestUniqueSubstring(String input) {
        Set<Character> set = new HashSet<>();  // Stores unique characters in the current window
        int maxLength = 0;  // Track the maximum length of the substring
        int i = 0, j = 0;   // Two pointers to represent the sliding window

        // While j (right pointer) hasn't reached the end of the string
        while (j < input.length()) {
            // If the character at j is not in the set, it's unique in the window
            if (!set.contains(input.charAt(j))) {
                set.add(input.charAt(j));   // Add the character to the set
                j++;                        // Move the right pointer to expand the window
                maxLength = Math.max(maxLength, j - i);  // Update max length of the unique substring
            } else {
                // If there's a duplicate, remove the character at the left pointer and shrink the window
                set.remove(input.charAt(i));
                i++;  // Move the left pointer to shrink the window
            }
        }
        return maxLength;
    }
}

/*
 Explanation:

Sliding Window Approach:
We use two pointers (i and j) to define the window.
The HashSet stores characters in the current window. If a duplicate is found, we shrink the window from the left.
The maxLength is updated when we find a longer valid substring.
 */
