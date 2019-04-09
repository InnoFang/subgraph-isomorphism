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

    private List<Vertex> vertexList;
    private List<Edge>   edgeList;

    private HashMap<String, List<String>> neighborVertexMap;

    private int[] vertexInDegree;
    private int[] vertexOutDegree;


    public Graph() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public Graph(Collection<Vertex> vertexList, Collection<Edge> edgeList) {
        this.vertexList = new ArrayList<>(vertexList);
        this.edgeList = new ArrayList<>(edgeList);
        this.neighborVertexMap = new HashMap<>();

        vertexInDegree =  new int[vertexList.size()];
        vertexOutDegree = new int[vertexList.size()];

        for (Edge edge : edgeList) {
           int from =  Integer.parseInt(edge.getVertexFrom());
           int to = Integer.parseInt(edge.getVertexTo());
           ++ vertexInDegree[to];
           ++ vertexOutDegree[from];
        }
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public int[][] getAdjacencyMatrix() {
        return getAdjacencyMatrix(false);
    }

    public int[][] getAdjacencyMatrix(boolean directed) {
        int vertexSize = vertexList.size();
        int[][] matrix = new int[vertexSize][vertexSize];
        for (Edge edge: edgeList) {
            int i = Integer.parseInt(edge.getVertexFrom());
            int j = Integer.parseInt(edge.getVertexTo());
            matrix[i][j] = 1;
            if (directed) {
                matrix[j][i] = 1;
            }
        }
        return matrix;
    }

    public int getVertexInDegree(String vertex) {
        int v = Integer.parseInt(vertex);
        return vertexInDegree[v];
    }

    public int getVertexOutDegree(String vertex) {
        int v = Integer.parseInt(vertex);
        return vertexOutDegree[v];
    }

    public int getVertexDegree(String vertex) {
        int v = Integer.parseInt(vertex);
        return vertexInDegree[v] + vertexOutDegree[v];
    }

    public String getVertexLabel(String vertex) {
        return vertexList.get(Integer.parseInt(vertex)).getLabel();
    }

    public String getEdgeLabel(int vertexFrom, int vertexTo) {
        for (Edge edge: edgeList) {
            if (edge.getVertexFrom().equals(vertexFrom + "") &&
                    edge.getVertexTo().equals(vertexTo + "")) {
                return edge.getLabel();
            }
        }
        return null;
    }

    public List<String> getNeighborVertexList(String vertex) {

        if (neighborVertexMap.containsKey(vertex)) {
            return neighborVertexMap.get(vertex);
        }

        List<String> neighbors = new ArrayList<>();
        for (Edge edge: edgeList) {
            if (edge.contain(vertex)) {
                neighbors.add(edge.getAdjacent(vertex));
            }
        }
        return neighbors;
    }

    public void addVertex(Vertex vertex) {
        vertexList.add(vertex);
    }

    public Vertex getVertex(String vertex) {
        for (Vertex v : vertexList) {
            if (v.getVertex().equals(vertex)) {
                return v;
            }
        }
        return null;
    }

    public Vertex getVertex(int vertexIndex) {
        return vertexList.get(vertexIndex);
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
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
