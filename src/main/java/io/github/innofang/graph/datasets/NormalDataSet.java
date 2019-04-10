package io.github.innofang.graph.datasets;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;
import io.github.innofang.graph.exceptions.GraphDataErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Data File Format
 * "t # N" means the Nth graph,
 * "v M L" means that the Mth vertex in this graph has label L,
 * "e P Q L" means that there is an edge connecting the Pth vertex with the Qth vertex. The edge has label L.
 * <p>
 * Each test file or query file end with "t # -1".
 * <p>
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
 * <p>
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
        String line;
        int lineNumber = 0;
        HashSet<Vertex> vertices = new HashSet<>();
        HashSet<Edge> edges = new HashSet<>();
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
            } else if (info[0].equals("v") && info.length == 3) {
                vertices.add(new Vertex(Integer.parseInt(info[1]), info[2]));
            } else if (info[0].equals("e") && info.length == 4) {
                edges.add(new Edge(Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]));
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
