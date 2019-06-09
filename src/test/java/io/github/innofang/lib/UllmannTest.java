package io.github.innofang.lib;

import io.github.innofang.graph.datasets.EmailEuCoreDataSet;
import io.github.innofang.graph.datasets.GraphDBDataSet;
import io.github.innofang.graph.datasets.UnweightedDiGraphDataSet;
import io.github.innofang.graph.datasets.UnweightedGraphDBDataSet;
import io.github.innofang.util.MatchHelper;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UllmannTest {


    @Test
    public void testUllmannWithBigData() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\dataset.txt";

        MatchHelper.testIsomorphismAlgorithm(
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
    public void testUllmannWithGraphDB100() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB-100\\Q16.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB-100\\dataset.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                queryGraphPath,
                UllmannState.class,
                new GraphDBDataSet(),
                mapping -> false
        );
    }

    @Test
    public void testUllmann() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\source_graph.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\test\\isomorphism\\target_graph.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,        // 数据图路径
                sourceGraphPath,        // 查询图路径
                UllmannState.class,     // 选取Ullmann算法
                new GraphDBDataSet(),   // 图数据读取策略
                mapping -> {            // 对子图同构结果进行处理
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithEmailEuCore() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4.txt-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new EmailEuCoreDataSet(),
                new UnweightedGraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithEmailEuCoreAndQueryGraph() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V3E3.txt";
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V4E5.txt";
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V5E6.txt";
//        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V6E7.txt";
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\unweighted-digraph\\V7E7.txt";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new EmailEuCoreDataSet(),
                new UnweightedDiGraphDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }

    @Test
    public void testUllmannWithEmailEuCoreDepartmentLabel() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String sourceGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\Q4.txt-10-unweighted.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\datasets\\email-Eu-core\\email-Eu-core-department-labels.txt";

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new EmailEuCoreDataSet(),
                new UnweightedGraphDBDataSet(),
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

        MatchHelper.testIsomorphismAlgorithm(
                targetGraphPath,
                sourceGraphPath,
                UllmannState.class,
                new UnweightedDiGraphDataSet(),
                new UnweightedGraphDBDataSet(),
                mapping -> {
                    System.out.println(mapping.toString());
                    return false;
                }
        );
    }
}