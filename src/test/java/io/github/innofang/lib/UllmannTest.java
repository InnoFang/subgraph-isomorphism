package io.github.innofang.lib;

import io.github.innofang.graph.datasets.EmailEuCoreDataSet;
import io.github.innofang.graph.datasets.GraphDBDataSet;
import io.github.innofang.graph.datasets.UnweightedDiGraphDataSet;
import io.github.innofang.graph.datasets.UnweightedGraphDBDataSet;
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
                new GraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmann() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\source_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\target_graph.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new GraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithEmailEuCore() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new UnweightedGraphDBDataSet(),
                new EmailEuCoreDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithEmailEuCoreDepartmentLabel() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core-department-labels.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new UnweightedGraphDBDataSet(),
                new EmailEuCoreDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithUnweightedDiGraph()  throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        // python unweighted-digraph-generator.py multiple -v 5 -e 7 -n 5

        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\Q5-5.txt";
        // python unweighted-digraph-generator.py single -v 100 -e 1560
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V100E1560.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new UnweightedGraphDBDataSet(),
                new UnweightedDiGraphDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }
}