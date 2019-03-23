package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.impl.VF2;
import io.github.innofang.graph.datasets.NormalDataSet;
import io.github.innofang.util.TestHelper;
import org.junit.Test;

import java.io.IOException;

public class VF2Test {

    @Test
    public void testVF2WithBigData() throws IOException {

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\graphDB\\Q4.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\graphDB\\mygraphdb.data";
        VF2 vf2 = new VF2();

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                vf2,
                new NormalDataSet(),
                true,
                (algorithm, queryGraphIndex, targetGraphIndex) ->
                        System.out.printf("t # %d is isomorphic T # %d\n", queryGraphIndex, targetGraphIndex)
        );
    }

    @Test
    public void testVF2() throws IOException {

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\test\\query_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\test\\target_graph.txt";
        VF2 vf2 = new VF2();

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                vf2,
                new NormalDataSet(),
                true,
                (algorithm, queryGraphIndex, targetGraphIndex) ->
                        System.out.printf("t # %d is isomorphic T # %d\n", queryGraphIndex, targetGraphIndex)
        );
    }

}