package io.github.innofang.graph.datasets;

import io.github.innofang.graph.bean.Edge;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.graph.exceptions.GraphDataErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Data File Format
 * "t # N" means the Nth graph,
 * "v M L" means that the Mth vertex in this graph has label L,
 * "e P Q L" means that there is an edge connecting the Pth vertex with the Qth vertex. The edge has label L.
 *
 * Each data file or query file end with "t # -1".
 *
 * graph Data Example:
 * ================
 * t # 0
 * v 0 2
 * v 1 2
 * v 2 6
 * v 3 6
 * e 0 1 2
 * e 0 2 2
 * e 1 3 2
 * e 2 4 3
 *
 * t # -1
 */

public class NormalDataSet implements DataSetStrategy {

    @Override
    public List<Graph> load(String filePath) throws IOException {
        File file = new File(filePath);
        assert file.exists() :
                String.format("File path %s isn't exists, please check it.", filePath);

        List<Graph> graphList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Graph graph = null;
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            ++ lineNumber;
            line = line.trim();
            if (line.equals("")) {
                continue;
            }
            String[] info = line.split(" ");
            if (info[0].equals("t")) {
                if (graph != null) {
                    graphList.add(graph);
                }
                graph = new Graph();
            }
            else if (info[0].equals("v") && info.length == 3) {
                Vertex vertex = new Vertex(info[1], info[2]);
                graph.addVertex(vertex);
            } else if (info[0].equals("e") && info.length == 4) {
                Edge edge = new Edge(info[1], info[2], info[3]);
                graph.addEdge(edge);
            } else {
                throw new GraphDataErrorException(
                        String.format("graph data isn't standard, " +
                                "'%s' is the reason, " +
                                "which is located at Line %d, File '%s'.", line, lineNumber, filePath));
            }
        }
        reader.close();
        return graphList;
    }
}
