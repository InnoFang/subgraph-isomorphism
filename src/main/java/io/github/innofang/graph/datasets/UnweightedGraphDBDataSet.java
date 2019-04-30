package io.github.innofang.graph.datasets;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;
import io.github.innofang.graph.exceptions.GraphDataErrorException;

import java.io.*;
import java.util.*;



@SuppressWarnings("Duplicates")
public class UnweightedGraphDBDataSet implements DataSetStrategy {

    @Override
    public List<Graph> load(String filePath) throws IOException {
        File file = new File(filePath);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", filePath);

        List<Graph> graphList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        int lineNumber = 0;
        // IMPORTANT: the vertex index is corresponding to the vertex, so must be keep ordered.
        Set<Vertex> vertices = new TreeSet<>(Comparator.comparingInt(Vertex::getVertex));
        Set<Edge> edges = new HashSet<>();
        int oldGraphId = 0;
        boolean first = true;
        while ((line = reader.readLine()) != null) {
            ++lineNumber;
            line = line.trim();
            if (line.equals("")) {
                continue;
            }
            String[] info = line.split("\\s+");
            if (info[0].equals("t") && info.length == 3) {
                int newGraphId = Integer.valueOf(info[2]);
                if (newGraphId == -1) {
                    Graph graph = new Graph(vertices, edges);
                    graph.setGraphId(oldGraphId);
                    vertices.clear();
                    edges.clear();
                    graphList.add(graph);
                    break;
                }
                if (first) {
                    oldGraphId = newGraphId;
                    first = false;
                    continue;
                }
                Graph graph = new Graph(vertices, edges);
                graph.setGraphId(oldGraphId);
                vertices.clear();
                edges.clear();
                graphList.add(graph);
                oldGraphId = newGraphId;
            } else if (info[0].equals("v") && info.length == 2) {
                vertices.add(new Vertex(Integer.parseInt(info[1])));
            } else if (info[0].equals("e") && info.length == 3) {
                edges.add(new Edge(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
            } else {
                throw new GraphDataErrorException(
                        String.format("graph test isn't standard, " +
                                "'%s' is the reason, " +
                                "which is located at Line %d, File '%s'.", line, lineNumber, filePath));
            }
        }
        reader.close();
        return graphList;
    }
}
