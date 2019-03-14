package io.github.innofang.graph.bean;

import java.util.ArrayList;
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

    private List<Vertex> vertexList = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();

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
        return getAdjacencyMatrix(true);
    }

    public int[][] getAdjacencyMatrix(boolean directed) {
        int vertexSize = vertexList.size();
        int[][] matrix = new int[vertexSize][vertexSize];
        for (Edge edge: edgeList) {
            int i = Integer.parseInt(edge.getVertexI());
            int j = Integer.parseInt(edge.getVertexJ());
            matrix[i][j] = 1;
            if (directed) {
                matrix[j][i] = 1;
            }
        }
        return matrix;
    }

    public int getVertexDegree(String vertex) {
        int degree = 0;
        for (Edge edge: edgeList) {
            if (edge.contain(vertex)) {
                ++ degree;
            }
        }
        return degree;
    }

    public String getVertexLabel(String vertex) {
        return vertexList.get(Integer.parseInt(vertex)).getLabel();
    }

    public String getEdgeLabel(String vertexI, String vertexJ) {
        for (Edge edge: edgeList) {
            if (edge.contain(vertexI) && edge.contain(vertexJ)) {
                return edge.getLabel();
            }
        }
        return null;
    }

    public List<String> getNeighborVertexList(String vertex) {
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
            ret.append(String.format("e %s %s %s\n", edge.getVertexI(), edge.getVertexJ(), edge.getLabel()));
        }
        return ret.toString();
    }
}
