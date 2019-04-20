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

    // the other end of an edge leaving a vertex
    // Edge: from -----> to, for 'from' vertex, is the 'out' edge
    private HashMap<Integer, ArrayList<Edge>> out;
    // Edge: from -----> to, for 'to    vertex, is the 'in'  edge
    private HashMap<Integer, ArrayList<Edge>> in;


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
        this.out = new HashMap<>();
        this.in  = new HashMap<>();

        for (Edge edge : edgeList) {
            int from = edge.getVertexFrom();
            int to = edge.getVertexTo();

            ArrayList<Edge> outEdges;
            if (out.containsKey(from)) {
                outEdges = out.get(from);
            } else {
                outEdges = new ArrayList<>();
            }
            outEdges.add(edge);
            out.put(from, outEdges);

            ArrayList<Edge> inEdges;
            if (in.containsKey(to)) {
                inEdges = in.get(to);
            } else {
                inEdges = new ArrayList<>();
            }
            inEdges.add(edge);
            in.put(to, inEdges);
        }
    }

    public int getGraphId() {
        return graphId;
    }

    public void setGraphId(int graphId) {
        this.graphId = graphId;
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
        ArrayList<Edge> inEdges = in.get(v);
        return inEdges == null ? 0 : inEdges.size();
    }

    public int getVertexOutDegree(int v) {
        ArrayList<Edge> outEdges = out.get(v);
        return outEdges == null ? 0 : outEdges.size();
    }

    public int getVertexDegree(int v) {
        return getVertexOutDegree(v) + getVertexInDegree(v);
    }

    public String getVertexLabel(int v) {
        return vertexList.get(v).getLabel();
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

    public ArrayList<Edge> getOutEdges(int vertex) {
        assert vertex < vertexList.size();
        return out.get(vertex);
    }

    public ArrayList<Edge> getInEdges(int vertex) {
        assert vertex < vertexList.size();
        return in.get(vertex);
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
