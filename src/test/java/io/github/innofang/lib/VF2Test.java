package io.github.innofang.lib;

import io.github.innofang.graph.datasets.EmailEuCoreDataSet;
import io.github.innofang.graph.datasets.GraphDBDataSet;
import io.github.innofang.graph.datasets.UnweightedDiGraphDataSet;
import io.github.innofang.graph.datasets.UnweightedGraphDBDataSet;
import io.github.innofang.util.TestHelper;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class VF2Test {

    @Test
    public void testVF2WithBigData() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4-100.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\mygraphdb.data";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                VF2State.class,
                new GraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\source_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\target_graph.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new GraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithEmailEuCore() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new UnweightedGraphDBDataSet(),
                new EmailEuCoreDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithEmailEuCoreDepartmentLabel() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core-department-labels.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new UnweightedGraphDBDataSet(),
                new EmailEuCoreDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithUnweightedDiGraph()  throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4-10-unweighted.my";
        // If you want to use the following data set, you should run the unweighted-digraph-generator.py first.
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V100E1560.txt";

        TestHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new UnweightedGraphDBDataSet(),
                new UnweightedDiGraphDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }
}