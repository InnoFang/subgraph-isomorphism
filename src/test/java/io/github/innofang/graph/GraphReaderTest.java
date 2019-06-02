package io.github.innofang.graph;

import io.github.innofang.bean.Graph;
import io.github.innofang.graph.datasets.GraphDBDataSet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class GraphReaderTest {

    @Test
    public void testNormalDataSet() throws IOException {
        String path = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new GraphDBDataSet());
        List<Graph> graphList = reader.read(path);
        for (Graph graph: graphList) {
            System.out.println(graph);
        }
    }

    @Test
    public void testGraphDB() throws IOException {
        String path = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\dataset.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new GraphDBDataSet());
        List<Graph> graphList = reader.read(path);
        int vertexSum = 0;
        int edgeSum = 0;
        for (Graph graph: graphList) {
            vertexSum += graph.getVertexList().size();
            edgeSum += graph.getEdgeList().size();
        }
        System.out.println("The number of vertex (AVR): " + (vertexSum / 10000.0)); // 24.8075
        System.out.println("The number of edge (AVR): " + (edgeSum / 10000.0));     // 26.8058
    }

    @Test
    public void testGraphDBStatistics() throws IOException {
        String graphDB = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\dataset.txt";
        String q4 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q4.txt";
        String q8 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q8.txt";
        String q12 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q12.txt";
        String q16 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q16.txt";
        String q20 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q20.txt";
        String q24 = "F:\\IDEA\\subgraph-isomorphism\\datasets\\graphDB\\Q24.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new GraphDBDataSet());
        String[] files = {graphDB, q4, q8, q12, q16, q20, q24};
        int idx = 0;
        for (String file : files) {
            System.out.println("[INDEX] " + (idx++));
            int vertexSum = 0;
            int edgeSum = 0;
            int maxVertex = 0;
            int minVertex = 0x3f3f3f;
            int maxEdge = 0;
            int minEdge = 0x3f3f3f;
            List<Graph> graphList = reader.read(file);
            for (Graph graph: graphList) {
                int vertexNum = graph.getVertexList().size();
                int edgeNum = graph.getEdgeList().size();
                vertexSum += vertexNum;
                edgeSum += edgeNum;
                if (maxVertex < vertexNum) maxVertex = vertexNum;
                if (minVertex > vertexNum) minVertex = vertexNum;
                if (maxEdge < edgeNum) maxEdge = edgeNum;
                if (minEdge > edgeNum) minEdge = edgeNum;
            }
            System.out.println("The number of vertex (MAX): " + (maxVertex));
            System.out.println("The number of vertex (MIN): " + (minVertex));
            System.out.println("The number of vertex (AVR): " + (vertexSum / graphList.size() * 1.0f));
            System.out.println("The number of edge (MAX): " + (maxEdge));
            System.out.println("The number of edge (MIN): " + (minEdge));
            System.out.println("The number of edge (AVR): " + (edgeSum / graphList.size() * 1.0f));
            System.out.println("The number of graph: " + graphList.size());
            System.out.println();
            System.out.println();
        }
    }
}