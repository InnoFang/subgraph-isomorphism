package io.github.innofang.util;

import org.junit.Test;

public class MatrixOperatorTest {

    @Test
    public void testMatrixOperator() {
        int[][] MA = {
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 0}};

        int[][] MB = {
                {0, 1, 1, 0},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {0, 1, 1, 0}};

        int[][] M0 = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0}};

        int[][] MC = MatrixOperator.set(M0).dot(
                MatrixOperator.set(M0).dot(MB).T().get()
        ).get();

        MatrixOperator.printMatrix(MC);

        loop:
        for (int i = 0; i < MA.length; ++i) {
            for (int j = 0; j < MA[0].length; ++j) {
                if (MA[i][j] == 1 && MC[i][j] != 1) {
                    System.out.println("It is not");
                    break loop;
                }
            }
        }
        System.out.println("isomorphism");
    }
}