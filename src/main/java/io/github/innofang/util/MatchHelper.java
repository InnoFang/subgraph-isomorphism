package io.github.innofang.util;

import io.github.innofang.lib.Matcher;
import io.github.innofang.lib.State;
import io.github.innofang.graph.GraphReader;
import io.github.innofang.bean.Graph;
import io.github.innofang.graph.datasets.DataSetStrategy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MatchHelper {

    /**
     * @param targetGraphPath a file path of target graph
     * @param sourceGraphPath a file path of source graph
     * @param stateClass      specify a state class
     * @param dataSetStrategy specify a strategy to read a data set
     * @param visitor         how to visit a mapping
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static void testIsomorphismAlgorithm(String targetGraphPath,
                                                String sourceGraphPath,
                                                Class<? extends State> stateClass,
                                                DataSetStrategy dataSetStrategy,
                                                Matcher.Visitor visitor) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        testIsomorphismAlgorithm(targetGraphPath, sourceGraphPath, stateClass, dataSetStrategy, dataSetStrategy, visitor);
    }


    public static void testIsomorphismAlgorithm(String targetGraphPath,
                                                String sourceGraphPath,
                                                Class<? extends State> stateClass,
                                                DataSetStrategy targetDataSetStrategy,
                                                DataSetStrategy sourceDataSetStrategy,
                                                Matcher.Visitor visitor) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(sourceDataSetStrategy);
        List<Graph> sourceGraphList = reader.read(sourceGraphPath);
        reader.setDataSetStrategy(targetDataSetStrategy);
        List<Graph> targetGraphList = reader.read(targetGraphPath);

        int sourceGraphSize = sourceGraphList.size();
        int targetGraphSize = targetGraphList.size();

        System.out.println("Target graph File: " + targetGraphPath);
        System.out.println("Source graph File: " + sourceGraphPath);

        System.out.println("The size of target graph: " + targetGraphSize);
        System.out.println("The size of source graph: " + sourceGraphSize);
        System.out.println();
        System.out.println("Start search ...");
        System.out.println("===================\n");
        int matchNum = 0;
        long start = System.currentTimeMillis();
        for (Graph sourceGraph : sourceGraphList) {
            for (Graph targetGraph : targetGraphList) {
                Constructor<? extends State> constructor = stateClass.getDeclaredConstructor(Graph.class, Graph.class);
                State state = constructor.newInstance(sourceGraph, targetGraph);
                long oneSearch = System.nanoTime();
                int count = Matcher.match(state, visitor);
                if (count != 0) {
                    long oneSearchUsed = System.nanoTime() - oneSearch;
                    System.out.println(String.format(
                            "Source graph #%d is sub-graph isomorphic target graph #%d, %d pairs of mapping, used %d ns.\n",
                            sourceGraph.getGraphId(), targetGraph.getGraphId(), count, oneSearchUsed));
                    ++matchNum;
                }
            }
        }
        long used = System.currentTimeMillis() - start;
        double seconds = used * 1.0 / 1000;

        System.out.println("\n===================");
        System.out.printf("End search, totally used %f s, %d pairs of SubGraphs Isomorphism.\n", seconds, matchNum);
    }

}
