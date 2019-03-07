package io.github.innofang.Graph;

import io.github.innofang.Graph.bean.Edge;
import io.github.innofang.Graph.bean.Graph;
import io.github.innofang.Graph.bean.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Graph Data Template:
 * ================
 * t # n    // nth graph
 * v i j    // the i is the vertex number, and the j is the vertex label
 * e i j k  // edge <i, j> which label is k
 *
 * Graph Data Example:
 * ================
 * t # 1
 * v 0 2
 * v 1 2
 * v 2 6
 * v 3 6
 * e 0 1 2
 * e 0 2 2
 * e 1 3 2
 * e 2 4 3
 *
 */

public class GraphReader {

    public List<Graph> read(String path) throws IOException {
        File file = new File(path);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", path);

        List<Graph> graphList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Graph graph;
        String line;
        int lineNumber = 1;
        if (reader.readLine().startsWith("t #")) {
            graph = new Graph();
            while ((line = reader.readLine()) != null) {
                ++ lineNumber;
                if (line.startsWith("t #")) {
                    graphList.add(graph);
                    graph = new Graph();
                    continue;
                }

                String[] info = line.trim().split(" ");
                if (info[0].equals("v") && info.length == 3) {
                    Vertex vertex = new Vertex(info[1], info[2]);
                    graph.getVertexList().add(vertex);
                } else if (info[0].equals("e") && info.length == 4) {
                    Edge edge = new Edge(info[1], info[2], info[3]);
                    graph.getEdgeList().add(edge);
                } else {
                    throw new GraphDataErrorException(
                            String.format("Graph data isn't standard, " +
                                    "'%s' is the reason, " +
                                    "which is located at Line %d, File '%s'.", line, lineNumber, path));
                }
            }
        }
        reader.close();
        return graphList;
    }

    public class GraphDataErrorException extends RuntimeException {
        public GraphDataErrorException(String message) {
            super(message);
        }
    }
}
