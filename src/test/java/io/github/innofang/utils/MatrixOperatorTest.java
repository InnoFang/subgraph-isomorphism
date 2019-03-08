package io.github.innofang.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixOperatorTest {

    @Test
    public void testMatrixOperator() {
        int[][] A = {{1, 2, 3},
                     {4, 5, 6}};

        int[][] B = {{1, 2},
                     {3, 4},
                     {5, 6}};

        int[][] C = MatrixOperator.set(A).dot(B).T().done();
        for (int[] row: C) {
            for (int elem: row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

}