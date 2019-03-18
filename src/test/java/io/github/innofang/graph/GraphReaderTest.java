package io.github.innofang.graph;

import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.datasets.CAAstroPhDataSet;
import io.github.innofang.graph.datasets.NormalDataSet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class GraphReaderTest {

    @Test
    public void testNormalDataSet() throws IOException {
        String path = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\Q4.my";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new NormalDataSet());
        List<Graph> graphList = reader.read(path);
        for (Graph graph: graphList) {
            System.out.println(graph);
        }
    }

    @Test
    public void testCAAstroPhDataSet() throws IOException {
        String path = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\ca-AstroPh\\CA-AstroPh.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new CAAstroPhDataSet());
        List<Graph> graphList = reader.read(path);
        for (Graph graph : graphList) {
            System.out.println(graph);
        }
    }

}