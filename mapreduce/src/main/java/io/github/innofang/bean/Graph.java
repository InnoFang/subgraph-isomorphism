package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Graph implements WritableComparable<Graph> {

    private IntWritable graphId = new IntWritable();
    private EdgeArrayWritable edgeArray = new EdgeArrayWritable();
    private VertexArrayWritable vertexArray = new VertexArrayWritable();

    // the other end of an edge leaving a vertex
    // Edge: from -----> to, for 'from' vertex, is the 'out' edge
    private HashMap<Integer, ArrayList<Edge>> out;
    // Edge: from -----> to, for 'to    vertex, is the 'in'  edge
    private HashMap<Integer, ArrayList<Edge>> in;

    public Graph() {
        this(-1, new Vertex[0], new Edge[0]);
    }

    public Graph(Vertex[] vertexArray, Edge[] edgeArray) {
        this(-1, vertexArray, edgeArray);
    }

    public Graph(int graphId, Vertex[] vertexArray, Edge[] edgeArray) {
        this.graphId.set(graphId);
        this.vertexArray.set(vertexArray);
        this.edgeArray.set(edgeArray);

        init();
    }

    private void init() {
        this.out = new HashMap<>();
        this.in  = new HashMap<>();

        for (Edge edge : getEdgeArray()) {
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

    public void setGraphId(int graphId) {
        this.graphId.set(graphId);
    }

    public int getGraphId() {
        return graphId.get();
    }

    public void setVertexArray(Vertex[] vertexArray) {
        this.vertexArray.set(vertexArray);
    }

    public Vertex[] getVertexArray() {
        return Arrays.copyOf(vertexArray.get(), vertexArray.get().length, Vertex[].class);
    }

    public void setEdgeArray(Edge[] edgeArray) {
        this.edgeArray.set(edgeArray);
    }

    public Edge[] getEdgeArray() {
        return Arrays.copyOf(edgeArray.get(), edgeArray.get().length, Edge[].class);
    }

    public int[][] getAdjacencyMatrix() {
        return getAdjacencyMatrix(false);
    }

    public int[][] getAdjacencyMatrix(boolean directed) {
        // some vertex id is from 1 to vertexSize
        int vertexSize = vertexArray.get().length + 1;

        int[][] matrix = new int[vertexSize][vertexSize];
        for (Edge edge: getEdgeArray()) {
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

    public String getEdgeLabel(int vertexFrom, int vertexTo) {
        for (Edge edge: getEdgeArray()) {
            if (edge.getVertexFrom() == vertexFrom &&
                    edge.getVertexTo() == vertexTo) {
                return edge.getLabel();
            }
        }
        return null;
    }

    public ArrayList<Edge> getOutEdges(int vertex) {
        assert vertex < getVertexArray().length;
        return out.get(vertex);
    }

    public String getOutEdgeLabel(int vertex, int otherVertex) {
        ArrayList<Edge> outEdges = out.get(vertex);
        if (outEdges == null)
            return null;
        for (Edge outEdge : outEdges) {
            if (outEdge.contain(otherVertex)) {
                return outEdge.getLabel();
            }
        }
        return null;
    }

    public ArrayList<Edge> getInEdges(int vertex) {
        assert vertex < getVertexArray().length;
        return in.get(vertex);
    }

    public String getInEdgeLabel(int vertex, int otherVertex) {
        ArrayList<Edge> inEdges = in.get(vertex);
        if (inEdges == null)
            return null;
        for (Edge inEdge: inEdges) {
            if (inEdge.contain(otherVertex)) {
                return inEdge.getLabel();
            }
        }
        return null;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        graphId.write(dataOutput);
        edgeArray.write(dataOutput);
        vertexArray.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        graphId.readFields(dataInput);
        edgeArray.readFields(dataInput);
        vertexArray.readFields(dataInput);
        init();
    }

    @Override
    public int compareTo(Graph graph) {
        return this.graphId.get() - graph.graphId.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(graphId, graph.graphId) &&
                edgeArray.equals(graph.edgeArray) &&
                Objects.equals(vertexArray, graph.vertexArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graphId, edgeArray, vertexArray);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Vertex vertex : getVertexArray()) {
            ret.append(String.format("v %s %s\n", vertex.getVertex(), vertex.getLabel()));
        }
        for (Edge edge : getEdgeArray()) {
            ret.append(String.format("e %s %s %s\n", edge.getVertexFrom(), edge.getVertexTo(), edge.getLabel()));
        }
        return ret.toString();
    }
}
