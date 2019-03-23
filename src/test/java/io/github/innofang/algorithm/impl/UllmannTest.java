package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.impl.Ullmann;
import io.github.innofang.graph.datasets.NormalDataSet;
import io.github.innofang.util.TestHelper;
import org.junit.Test;

import java.io.IOException;

public class UllmannTest {


    @Test
    public void testUllmannWithBigData() throws IOException {

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\graphDB\\Q4.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\graphDB\\mygraphdb.test";
        Ullmann ullmann = new Ullmann();

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                ullmann,
                new NormalDataSet(),
                true,
                (algorithm, queryGraphIndex, targetGraphIndex) ->
                        System.out.printf("t # %d is isomorphic T # %d\n", queryGraphIndex, targetGraphIndex)
        );
    }

    @Test
    public void testUllmann() throws IOException {

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\test\\query_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\test\\target_graph.txt";
        Ullmann ullmann = new Ullmann();

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                ullmann,
                new NormalDataSet(),
                true,
                (algorithm, queryGraphIndex, targetGraphIndex) ->
                        System.out.printf("t # %d is isomorphic T # %d\n", queryGraphIndex, targetGraphIndex)
        );
    }
}