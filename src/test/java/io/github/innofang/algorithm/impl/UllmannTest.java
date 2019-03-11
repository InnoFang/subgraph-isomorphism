package io.github.innofang.algorithm;

import io.github.innofang.algorithm.impl.Ullmann;
import io.github.innofang.graph.datasets.NormalDataSet;
import io.github.innofang.util.TestHelper;
import org.junit.Test;

import java.io.IOException;

public class UllmannTest {

    @Test
    public void testUllmann() throws IOException {

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\Q4.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\mygraphdb.data";
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