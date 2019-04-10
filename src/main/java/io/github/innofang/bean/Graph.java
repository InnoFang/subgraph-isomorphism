package io.github.innofang.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * graph Data Template:
 * ================
 * t # n    // nth graph
 * v i j    // the i is the vertex number, and the j is the vertex label
 * e i j k  // edge <i, j> which label is k
 *
 * graph Data Example:
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

public class Graph {

    private int graphId;

    private List<Vertex> vertexList;
    private List<Edge>   edgeList;

    private HashMap<Integer, List<Integer>> neighborVertexMap;

    private int[] vertexInDegree;
    private int[] vertexOutDegree;


    public Graph() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public Graph(Collection<Vertex> vertexList, Collection<Edge> edgeList) {
        this.vertexList = new ArrayList<>(vertexList);
        this.edgeList = new ArrayList<>(edgeList);
        reset();
    }

    void reset() {
        this.neighborVertexMap = new HashMap<>();

        vertexInDegree =  new int[vertexList.size()];
        vertexOutDegree = new int[vertexList.size()];

        for (Edge edge : edgeList) {
            int from = edge.getVertexFrom();
            int to = edge.getVertexTo();
            ++ vertexInDegree[to];
            ++ vertexOutDegree[from];
        }
    }

    public int getGraphId() {
        return graphId;
    }

    public void setGraphId(int graphId) {
        this.graphId = graphId;
    }

    public void setVertexesAndEdges(Collection<Vertex> vertexes, Collection<Edge> edges) {
        this.vertexList = new ArrayList<>(vertexList);
        this.edgeList = new ArrayList<>(edgeList);
        reset();
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public int[][] getAdjacencyMatrix() {
        return getAdjacencyMatrix(false);
    }

    public int[][] getAdjacencyMatrix(boolean directed) {
        int vertexSize = vertexList.size();
        int[][] matrix = new int[vertexSize][vertexSize];
        for (Edge edge: edgeList) {
            int i = edge.getVertexFrom();
            int j = edge.getVertexTo();
            matrix[i][j] = 1;
            if (directed) {
                matrix[j][i] = 1;
            }
        }
        return matrix;
    }

    public int getVertexInDegree(int v) {
        return vertexInDegree[v];
    }

    public int getVertexOutDegree(int v) {
        return vertexOutDegree[v];
    }

    public int getVertexDegree(int v) {
        return vertexInDegree[v] + vertexOutDegree[v];
    }

    public String getVertexLabel(String vertex) {
        return vertexList.get(Integer.parseInt(vertex)).getLabel();
    }

    public String getEdgeLabel(int vertexFrom, int vertexTo) {
        for (Edge edge: edgeList) {
            if (edge.getVertexFrom() == vertexFrom &&
                    edge.getVertexTo() == vertexTo) {
                return edge.getLabel();
            }
        }
        return null;
    }

    public List<Integer> getNeighborVertexList(int vertex) {

        if (neighborVertexMap.containsKey(vertex)) {
            return neighborVertexMap.get(vertex);
        }

        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge: edgeList) {
            if (edge.contain(vertex)) {
                neighbors.add(edge.getAdjacent(vertex));
            }
        }
        return neighbors;
    }

    public Vertex getVertex(int vertexIndex) {
        return vertexList.get(vertexIndex);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Vertex vertex: vertexList) {
            ret.append(String.format("v %s %s\n", vertex.getVertex(), vertex.getLabel()));
        }
        for (Edge edge: edgeList) {
            ret.append(String.format("e %s %s %s\n", edge.getVertexFrom(), edge.getVertexTo(), edge.getLabel()));
        }
        return ret.toString();
    }
}
