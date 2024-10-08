package com.example.demo;

public class MatrixExample {
	
	public static void main(String[] args) {
		MatrixAddition();
		System.out.println("----");
		MatrixMultiplication();
	}

	public static void MatrixAddition() {
		int[][] matrixA = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

		int[][] matrixB = { { 9, 8, 7 }, { 6, 5, 4 }, { 3, 2, 1 } };

		int[][] sum = new int[3][3];

		// Add the matrices
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixA[i].length; j++) {
				sum[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}

		// Print the sum matrix
		for (int[] row : sum) {
			for (int value : row) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}

	public static void MatrixMultiplication() {
		int[][] matrixA = { { 1, 2 }, { 3, 4 } };

		int[][] matrixB = { { 5, 6 }, { 7, 8 } };

		int[][] product = new int[matrixA.length][matrixB[0].length];

		// Multiply the matrices
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixB[0].length; j++) {
				for (int k = 0; k < matrixA[0].length; k++) {
					product[i][j] += matrixA[i][k] * matrixB[k][j];
				}
			}
		}

		// Print the product matrix
		for (int[] row : product) {
			for (int value : row) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}

	public class MatrixTranspose {
		public static void main(String[] args) {
			int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 } };

			int[][] transposed = new int[matrix[0].length][matrix.length];

			// Transpose the matrix
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					transposed[j][i] = matrix[i][j];
				}
			}

			// Print the transposed matrix
			for (int[] row : transposed) {
				for (int value : row) {
					System.out.print(value + " ");
				}
				System.out.println();
			}
		}
	}

	public class SymmetricMatrixCheck {
		public static void main(String[] args) {
			int[][] matrix = { { 1, 2, 3 }, { 2, 4, 5 }, { 3, 5, 6 } };

			boolean isSymmetric = true;

			// Check if the matrix is symmetric
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[i][j] != matrix[j][i]) {
						isSymmetric = false;
						break;
					}
				}
			}

			System.out.println("Is the matrix symmetric: " + isSymmetric);
		}
	}
}
