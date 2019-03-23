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
        // Nodes: 18772 Edges: 396160

        String path = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\ca-AstroPh\\CA-AstroPh.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new CAAstroPhDataSet());
        List<Graph> graphList = reader.read(path);

        Graph ca_astro_ph = graphList.get(0);

        Assert.assertEquals(ca_astro_ph.getVertexList().size(), 18772);
        Assert.assertEquals(ca_astro_ph.getEdgeList().size(), 396160);
    }

    @Test
    public void testRoadNetCA() throws IOException {
        // Nodes: 1965206 Edges: 5533214

        String path = "F:\\IDEA\\subgraph-isomorphism\\src\\test\\resources\\roadNet-CA\\roadNet-CA.txt";
        GraphReader reader = new GraphReader();
        reader.setDataSetStrategy(new RoadNetCADataSet());
        List<Graph> graphList = reader.read(path);

        Graph ca_astro_ph = graphList.get(0);

        Assert.assertEquals(ca_astro_ph.getVertexList().size(), 1965206);
        Assert.assertEquals(ca_astro_ph.getEdgeList().size(), 5533214);
    }
}