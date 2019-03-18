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
 * Directed graph (each unordered pair of nodes is saved once): CA-AstroPh.txt
 * Collaboration network of Arxiv Astro Physics category (there is an edge if authors coauthored at least one paper)
 * Nodes: 18772 Edges: 396160
 *
 * FromNodeId	ToNodeId
 */

public class CAAstroPhDataSet implements DataSetStrategy {
    @Override
    public List<Graph> load(String filePath) throws IOException {
        File file = new File(filePath);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", filePath);

        List<Graph> graphList = new ArrayList<>();
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
            } else {
                throw new GraphDataErrorException(
                        String.format("graph data isn't standard, " +
                                "'%s' is the reason, " +
                                "which is located at Line %d, File '%s'.", line, lineNumber, filePath));
            }
        }
        Graph graph = new Graph(vertices, edges);
        graphList.add(graph);
        return graphList;
    }
}
