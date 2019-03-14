package io.github.innofang.util;

public class MatrixOperator {

    public static Operator set(int[][] matrix) {
        assert matrix != null :
                "matrix cannot be null.";
        assert matrix[0].length != 0 :
                "Matrix cannot be empty.";

        return new Operator(matrix);
    }

    public static class Operator {
        private int[][] selfMatrix;

        private Operator(int[][] matrix) {
            this.selfMatrix = matrix;
        }

        public Operator dot(int[][] matrix) {
            assert selfMatrix[0].length == matrix.length :
                    String.format("Incompatible matrix multiplication conditions, " +
                    "the number of rows of matrix (%d) is not equal to " +
                    "the number of columns of the current matrix (%d).",
                            matrix.length, selfMatrix[0].length) ;

            int rows = selfMatrix.length;
            int cols = matrix[0].length;
            int mid = matrix.length;
            int[][] newMatrix = new int[rows][cols];

            for (int i = 0; i < rows; ++ i) {
                for (int j = 0; j < cols; ++ j) {
                    for (int k = 0; k < mid; ++ k) {
                        newMatrix[i][j] += selfMatrix[i][k] * matrix[k][j];
                    }
                }
            }

            selfMatrix = newMatrix;
            return this;
        }

        public Operator T() {
            int rows = selfMatrix.length;
            int cols = selfMatrix[0].length;
            int[][] newMatrix = new int[cols][rows];

            for (int i = 0; i < rows; ++ i) {
                for (int j = 0; j < cols; ++ j) {
                    newMatrix[j][i] = selfMatrix[i][j];
                }
            }

            selfMatrix = newMatrix;
            return this;
        }

        public int[][] get() {
            return selfMatrix;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
}
