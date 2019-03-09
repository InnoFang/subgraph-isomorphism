package io.github.innofang.Graph;

import io.github.innofang.Graph.bean.Graph;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class GraphReaderTest {

    @Test
    public void testRead() throws IOException {
        String path = "F:\\IDEA\\subgraph-isomorphism\\src\\main\\resources\\graphDB\\Q4.my";
        List<Graph> graphList = new GraphReader().read(path);
        for (Graph graph: graphList) {
            System.out.println(graph);
        }
    }

}