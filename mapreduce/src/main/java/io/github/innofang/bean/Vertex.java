package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Vertex implements Writable {

    private IntWritable vertex = new IntWritable();
    private Text label  = new Text();

    public Vertex() {
        this(-1, "");
    }

    public Vertex(int vertex) {
        this(vertex, "");
    }

    public Vertex(int vertex, String label) {
        this.vertex.set(vertex);
        this.label .set(label);
    }

    public void setVertex(int vertex) {
        this.vertex.set(vertex);
    }

    public int getVertex() {
        return vertex.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getLabel() {
        return label.toString();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        vertex.write(dataOutput);
        label.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        vertex.readFields(dataInput);
        label.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex1 = (Vertex) o;
        return Objects.equals(vertex, vertex1.vertex) &&
                Objects.equals(label, vertex1.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, label);
    }
}
