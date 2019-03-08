package io.github.innofang.algorithms;

import io.github.innofang.Graph.GraphReader;
import io.github.innofang.Graph.bean.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UllmannTest {

    @Test
    public void testUllmann() throws IOException {
        Ullmann ullmann = new Ullmann();

        String queryGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\Q4.my";
        String largeGraphPath = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\mygraphdb.data";

        GraphReader reader = new GraphReader();
        List<Graph> queryGraphList = reader.read(queryGraphPath);
        List<Graph> largeGraphList = reader.read(largeGraphPath);

        int queryGraphSize = queryGraphList.size();
        int largeGraphSize = largeGraphList.size();

        System.out.println("Large Graph File: " + largeGraphPath);
        System.out.println("Query Graph File: " + queryGraphPath);

        System.out.println("The size of large Graph: " + largeGraphSize);
        System.out.println("The size of query Graph: " + queryGraphSize);
        System.out.println();
        System.out.println("Start searching ...");
        System.out.println("===================\n");

        long start = System.currentTimeMillis();
        for (int i = 0; i < queryGraphSize; ++ i) {
            for (int j = 0; j < largeGraphSize; ++ j) {
                Graph query = queryGraphList.get(i);
                Graph large = largeGraphList.get(j);
                if (ullmann.isIsomorphism(large, query)) {
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