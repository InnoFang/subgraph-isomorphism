package io.github.innofang.algorithms;

import io.github.innofang.graph.GraphReader;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.datasets.NormalDataSet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UllmannTest {

    @Test
    public void testUllmann() throws IOException {
        Ullmann ullmann = new Ullmann();

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\Q4.my";
        String targetGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\mygraphdb.data";

        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new NormalDataSet());
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

        long start = System.currentTimeMillis();
        for (int i = 0; i < queryGraphSize; ++ i) {
            for (int j = 0; j < targetGraphSize; ++ j) {
                Graph query = queryGraphList.get(i);
                Graph target = targetGraphList.get(j);
                if (ullmann.isIsomorphism(target, query)) {
                    System.out.printf("t # %d is isomorphic T # %d\n", i, j);
                }
            }
        }
        long used = System.currentTimeMillis() - start;
        double seconds = used * 1.0 / 1000;

        System.out.println("\n===================");
        System.out.printf("End searching, used %f s\n.", seconds);
    }
}