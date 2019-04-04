package io.github.innofang.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Edge implements Writable {

    private Text vertexI = new Text();   // or Vertex 'from'
    private Text vertexJ = new Text();   // or Vertex 'to'
    private Text label   = new Text();

    public Edge() {
        this("", "", "");
    }

    public Edge(String vertexI, String vertexJ) {
        this(vertexI, vertexJ, "");
    }

    public Edge(String vertexI, String vertexJ, String label) {
        this.vertexI.set(vertexI);
        this.vertexJ.set(vertexJ);
        this.label.set(label);
    }

    public void setVertexI(String vertexI) {
        this.vertexI.set(vertexI);
    }

    public String getVertexI() {
        return vertexI.toString();
    }

    public void setVertexJ(String vertexJ) {
        this.vertexJ.set(vertexJ);
    }

    public String getVertexJ() {
        return vertexJ.toString();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getLabel() {
        return label.toString();
    }

    public boolean contain(String vertex) {
        return vertexI.toString().equals(vertex) || vertexJ.toString().equals(vertex);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        vertexI.write(dataOutput);
        vertexJ.write(dataOutput);
        label.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        vertexI.readFields(dataInput);
        vertexJ.readFields(dataInput);
        label.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(vertexI, edge.vertexI) &&
                Objects.equals(vertexJ, edge.vertexJ) &&
                Objects.equals(label, edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexI, vertexJ, label);
    }
}
