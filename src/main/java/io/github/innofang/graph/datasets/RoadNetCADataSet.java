package io.github.innofang.graph.datasets;

import io.github.innofang.graph.bean.Edge;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.graph.exceptions.GraphDataErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Directed graph (each unordered pair of nodes is saved once): roadNet-CA.txt
 * California road network
 * Nodes: 1965206 Edges: 5533214
 *
 * FromNodeId	ToNodeId
 */

public class RoadNetCADataSet implements DataSetStrategy{
    @Override
    public List<Graph> load(String filePath) throws IOException {
        File file = new File(filePath);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        int lineNumber = 0;
        HashSet<Vertex> vertices = new HashSet<>();
        HashSet<Edge> edges = new HashSet<>();
        while ((line = reader.readLine()) != null) {
            ++ lineNumber;
            line = line.trim();
            if (line.startsWith("#"))
                continue;

            String[] info = line.split("\\s+");
            if (info.length == 2) {
                vertices.add(new Vertex(info[0]));
                vertices.add(new Vertex(info[1]));
                edges.add(new Edge(info[0], info[1]));
            } else if (info.length == 1) {
                continue;
            } else {
                throw new GraphDataErrorException(
                        String.format("graph data isn't standard, " +
                                "'%s' is the reason, " +
                                "which is located at Line %d, File '%s'.", line, lineNumber, filePath));
            }
        }

        List<Graph> graphList = new ArrayList<>();
        Graph graph = new Graph(vertices, edges);
        graphList.add(graph);
        return graphList;
    }
}
