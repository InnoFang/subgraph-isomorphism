package io.github.innofang.util;

import org.junit.Test;

public class MatrixOperatorTest {

    @Test
    public void testMatrixOperator() {
        int[][] A = {{1, 2, 3},
                     {4, 5, 6}};

        int[][] B = {{1, 2},
                     {3, 4},
                     {5, 6}};

        int[][] C = MatrixOperator.set(A).dot(B).T().get();
        print(C);
        System.out.println("========");
        C = MatrixOperator.set(A).T().get();
        print(C);

    }

    private void print(int[][] matrix) {
        for (int[] row: matrix) {
            for (int elem: row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

}