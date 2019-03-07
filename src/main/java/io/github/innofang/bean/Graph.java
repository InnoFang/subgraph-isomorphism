package io.github.innofang.bean;

import com.sun.javafx.binding.StringFormatter;

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
        return getAdjacencyMatrix(false);
    }

    public int[][] getAdjacencyMatrix(boolean directed) {
        int edgeSize = edgeList.size();
        int[][] matrix = new int[edgeSize][edgeSize];
        for (Edge edge: edgeList) {
            int i = Integer.parseInt(edge.getVertexI());
            int j = Integer.parseInt(edge.getVertextJ());
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

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Vertex vertex: vertexList) {
            ret.append(String.format("v %s %s\n", vertex.getVertex(), vertex.getLabel()));
        }
        for (Edge edge: edgeList) {
            ret.append(String.format("e %s %s %s\n", edge.getVertexI(), edge.getVertextJ(), edge.getLabel()));
        }
        return ret.toString();
    }
}
