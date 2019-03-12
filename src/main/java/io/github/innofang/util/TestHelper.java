package io.github.innofang.util;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.GraphReader;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.graph.datasets.DataSetStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TestHelper {

    /**
     *
     * @param targetGraphPath   target graph file path
     * @param queryGraphPath    query graph file path
     * @param algorithm         which isomorphism algorithm do you want to use
     * @param dataSetStrategy   which data set do you want to import
     * @param showMapping       show mapping or not
     * @param listener          isomorphism listener, call it when two graph isomorphism
     */
    public static void testIsomorphismAlgorithm(String targetGraphPath,
                                         String queryGraphPath,
                                         IsomorphismAlgorithm algorithm,
                                         DataSetStrategy dataSetStrategy,
                                         boolean showMapping,
                                         IsomorphismListener listener)  throws IOException {

        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(dataSetStrategy);
        List<Graph> queryGraphList = reader.read(queryGraphPath);
        List<Graph> targetGraphList = reader.read(targetGraphPath);

        int queryGraphSize = queryGraphList.size();
        int targetGraphSize = targetGraphList.size();

        System.out.println("Target graph File: " + targetGraphPath);
        System.out.println("Query graph File: " + queryGraphPath);

        System.out.println("The size of target graph: " + targetGraphSize);
        System.out.println("The size of query graph: " + queryGraphSize);
        System.out.println();
        System.out.println("Start searching ...");
        System.out.println("===================\n");
        int matchNum = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < queryGraphSize; ++ i) {
            for (int j = 0; j < targetGraphSize; ++ j) {
                Graph query = queryGraphList.get(i);
                Graph target = targetGraphList.get(j);
                if (algorithm.match(target, query)) {
                    ++ matchNum;
                    listener.match(algorithm, i, j);
                    if (showMapping) {
                        printMapping(algorithm.getMapping());
                    }
                }
            }
        }
        long used = System.currentTimeMillis() - start;
        double seconds = used * 1.0 / 1000;

        System.out.println("\n===================");
        System.out.printf("End searching, used %f s\n, %d graph isomorphism.", seconds, matchNum);
    }


    private static void printMapping(HashMap<String, String> mapping) {
        for (HashMap.Entry<String, String> entry : mapping.entrySet()) {
            System.out.println(" (" + entry.getKey() + ", " + entry.getValue() + ") ");
        }
    }

    public interface IsomorphismListener {
        void match(IsomorphismAlgorithm algorithm, int queryGraphIndex, int targetGraphIndex);
    }

}
