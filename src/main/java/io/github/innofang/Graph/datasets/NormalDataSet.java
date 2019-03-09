package io.github.innofang.Graph.datasets;

import io.github.innofang.Graph.bean.Edge;
import io.github.innofang.Graph.bean.Graph;
import io.github.innofang.Graph.bean.Vertex;
import io.github.innofang.Graph.exceptions.GraphDataErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NormalDataSet implements DataSetStrategy {

    @Override
    public List<Graph> load(String filePath) throws IOException {
        File file = new File(filePath);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", filePath);

        List<Graph> graphList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Graph graph;
        String line;
        int lineNumber = 1;
        if (reader.readLine().startsWith("t #")) {
            graph = new Graph();
            while ((line = reader.readLine()) != null) {
                ++ lineNumber;
                if (line.equals("")) {
                    continue;
                }
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
                                    "which is located at Line %d, File '%s'.", line, lineNumber, filePath));
                }
            }
        }
        reader.close();
        return graphList;
    }
}
