package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Graph implements WritableComparable<Graph> {

    private IntWritable graphId = new IntWritable();
    private EdgeArrayWritable edgeArray = new EdgeArrayWritable();
    private VertexArrayWritable vertexArray = new VertexArrayWritable();

    public Graph() {
        this(-1, new Vertex[0], new Edge[0]);

    }

    public Graph(int graphId, Vertex[] vertexArray, Edge[] edgeArray) {
        this.graphId.set(graphId);
        this.edgeArray.set(vertexArray);
        this.vertexArray.set(edgeArray);
    }

    public void setGraphId(int graphId) {
        this.graphId.set(graphId);
    }

    public void setVertexArray(Vertex[] vertexArray) {
        this.vertexArray.set(vertexArray);
    }

    public void setEdgeArray(Edge[] edgeArray) {
        this.edgeArray.set(edgeArray);
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
        for (Vertex vertex: (Vertex[]) vertexArray.get()) {
            ret.append(String.format("v %s %s\n", vertex.getVertex(), vertex.getLabel()));
        }
        for (Edge edge: (Edge[]) edgeArray.get()) {
            ret.append(String.format("e %s %s %s\n", edge.getVertexI(), edge.getVertexJ(), edge.getLabel()));
        }
        return ret.toString();
    }
}
