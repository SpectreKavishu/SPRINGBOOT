package com.example.demo;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        String input = "babad";
        String longestPalindrome = findLongestPalindromicSubstring(input);
        System.out.println("Longest palindromic substring: " + longestPalindrome);  // Output: "bab" or "aba"
    }

    public static String findLongestPalindromicSubstring(String input) {
        if (input == null || input.length() < 1) return "";  // Base case

        int start = 0, end = 0;  // Track the start and end of the longest palindrome

        for (int i = 0; i < input.length(); i++) {
            // Expand around a single character (odd-length palindrome)
            int len1 = expandAroundCenter(input, i, i);

            // Expand around two consecutive characters (even-length palindrome)
            int len2 = expandAroundCenter(input, i, i + 1);

            // Get the maximum length of palindrome between the two
            int len = Math.max(len1, len2);

            // If we found a new longer palindrome, update the start and end indices
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return input.substring(start, end + 1);  // Return the longest palindromic substring
    }

    // Helper method to expand around a center and return the length of the palindrome
    private static int expandAroundCenter(String s, int left, int right) {
        // Expand as long as the characters are equal and within bounds
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;  // Length of the palindrome
    }
}

/*
Explanation:

Expanding Around Center:
Each palindrome is centered at either a single character (for odd-length palindromes) or two consecutive characters (for even-length palindromes).
We expand outward from the center to find the largest palindromic substring.
 */
