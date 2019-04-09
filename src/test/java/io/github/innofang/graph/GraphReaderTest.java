package io.github.innofang.graph;

import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.datasets.CAAstroPhDataSet;
import io.github.innofang.graph.datasets.NormalDataSet;
import io.github.innofang.graph.datasets.RoadNetCADataSet;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class GraphReaderTest {

    @Test
    public void testNormalDataSet() throws IOException {
        String path = "../../../../../resources/graphDB/Q4.my";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new NormalDataSet());
        List<Graph> graphList = reader.read(path);
        for (Graph graph: graphList) {
            System.out.println(graph);
        }
    }

    @Test
    public void testGraphDB() throws IOException {
        String path = "../../../../../resources/graphDB/mygraphdb.data";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new NormalDataSet());
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
}