package io.github.innofang.lib;

import io.github.innofang.graph.datasets.NormalDataSet;
import io.github.innofang.util.TestHelper;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UllmannTest {


    @Test
    public void testUllmannWithBigData() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4-100.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\mygraphdb.data";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                UllmannState.class,
                new NormalDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmann() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\query_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\target_graph.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new NormalDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }
}