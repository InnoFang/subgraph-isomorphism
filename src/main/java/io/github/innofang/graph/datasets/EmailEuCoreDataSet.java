package io.github.innofang.graph.datasets;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;

import java.io.*;
import java.util.*;

public class EmailEuCoreDataSet implements DataSetStrategy {
    @Override
    public List<Graph> load(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;

        Set<Vertex> vertices = new TreeSet<>(Comparator.comparingInt(Vertex::getVertex));
        Set<Edge> edges = new HashSet<>();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String[] info = line.split("\\s+");
            if (info.length == 2) {
                int from = Integer.parseInt(info[0]);
                int to   = Integer.parseInt(info[1]);
                vertices.add(new Vertex(from));
                vertices.add(new Vertex(to));
                edges.add(new Edge(from, to));
            } else {
                System.err.println("Wrong Line: " + line);
            }
        }
        reader.close();

        Graph graph = new Graph(vertices, edges);
        List<Graph> graphs = new ArrayList<>();
        graphs.add(graph);

        return graphs;
    }
}
