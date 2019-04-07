package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Graph implements WritableComparable<Graph> {

    private IntWritable graphId = new IntWritable();
    private EdgeArrayWritable edgeArray = new EdgeArrayWritable();
    private VertexArrayWritable vertexArray = new VertexArrayWritable();

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
        return getAdjacencyMatrix(true);
    }

    public int[][] getAdjacencyMatrix(boolean undirected) {
        // some vertex id is from 1 to vertexSize
        int vertexSize = vertexArray.get().length + 1;

        int[][] matrix = new int[vertexSize][vertexSize];
        for (Edge edge: getEdgeArray()) {
            int i = Integer.parseInt(edge.getVertexI());
            int j = Integer.parseInt(edge.getVertexJ());
            matrix[i][j] = 1;
            if (undirected) {
                matrix[j][i] = 1;
            }
        }
        return matrix;
    }

    public int getVertexDegree(String vertex) {
        int degree = 0;
        for (Edge edge: getEdgeArray()) {
            if (edge.contain(vertex)) {
                ++degree;
            }
        }
        return degree;
    }

    public String getEdgeLabel(String vertexI, String vertexJ) {
        for (Edge edge: getEdgeArray()) {
            if (edge.contain(vertexI) && edge.contain(vertexJ)) {
                return edge.getLabel();
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
            ret.append(String.format("e %s %s %s\n", edge.getVertexI(), edge.getVertexJ(), edge.getLabel()));
        }
        return ret.toString();
    }
}
