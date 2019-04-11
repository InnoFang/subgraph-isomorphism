package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Edge implements Writable {

    private IntWritable vertexFrom = new IntWritable();
    private IntWritable vertexTo = new IntWritable();
    private Text label   = new Text();

    public Edge() {
        this(-1, -1, "");
    }

    public Edge(int vertexFrom, int vertexTo) {
        this(vertexFrom, vertexTo, "");
    }

    public Edge(int vertexFrom, int vertexTo, String label) {
        this.vertexFrom.set(vertexFrom);
        this.vertexTo.set(vertexTo);
        this.label.set(label);
    }

    public void setVertexFrom(int vertexFrom) {
        this.vertexFrom.set(vertexFrom);
    }

    public int getVertexFrom() {
        return vertexFrom.get();
    }

    public void setVertexTo(int vertexTo) {
        this.vertexTo.set(vertexTo);
    }

    public int getVertexTo() {
        return vertexTo.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getLabel() {
        return label.toString();
    }

    public boolean contain(int vertex) {
        return vertexFrom.get() == vertex || vertexTo.get()  == vertex;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        vertexFrom.write(dataOutput);
        vertexTo.write(dataOutput);
        label.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        vertexFrom.readFields(dataInput);
        vertexTo.readFields(dataInput);
        label.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(vertexFrom, edge.vertexFrom) &&
                Objects.equals(vertexTo, edge.vertexTo) &&
                Objects.equals(label, edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexFrom, vertexTo, label);
    }
}
