package io.github.innofang.lib;

import io.github.innofang.graph.datasets.EmailEuCoreDataSet;
import io.github.innofang.graph.datasets.GraphDBDataSet;
import io.github.innofang.graph.datasets.UnweightedDiGraphDataSet;
import io.github.innofang.graph.datasets.UnweightedGraphDBDataSet;
import io.github.innofang.util.MatchHelper;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class VF2Test {

    @Test
    public void testVF2WithBigData() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\dataset.txt";

        MatchHelper.testIsomorphismAlgorithm(
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
    public void testVF2WithGraphDB100() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB-100\\Q24.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB-100\\dataset.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                VF2State.class,
                new GraphDBDataSet(),
                mapping -> false
        );
    }

    @Test
    public void testVF2() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\source_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\target_graph.txt";

        MatchHelper.testIsomorphismAlgorithm(
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
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4.txt-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new EmailEuCoreDataSet(),
                new UnweightedGraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }


    @Test
    public void testVF2WithEmailEuCoreAndQueryGraph() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V3E3.txt";
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V4E5.txt";
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V5E6.txt";
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V6E7.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new EmailEuCoreDataSet(),
                new UnweightedDiGraphDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithEmailEuCoreAndQueryGraph2() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V7E7.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new EmailEuCoreDataSet(),
                new UnweightedDiGraphDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithEmailEuCoreDepartmentLabel() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4.txt-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core-department-labels.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new EmailEuCoreDataSet(),
                new UnweightedGraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testVF2WithUnweightedDiGraph() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        // If you want to use the following data sets, you should run the unweighted-digraph-generator.py first.

        // python unweighted-digraph-generator.py multiple -v 5 -e 7 -n 5
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\Q5-5.txt";
        // python unweighted-digraph-generator.py single -v 100 -e 1560
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V100E1560.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                VF2State.class,
                new UnweightedDiGraphDataSet(),
                new UnweightedGraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }
}