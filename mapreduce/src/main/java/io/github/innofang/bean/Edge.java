package io.github.innofang.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Edge implements Writable {

    private Text vertexI = new Text();   // or Vertex 'from'
    private Text vertexJ = new Text();   // or Vertex 'to'
    private Text label   = new Text();

    public Edge() {
        this("", "", "");
    }

    public Edge(String vertexI, String vertexJ, String label) {
        this.vertexI.set(vertexI);
        this.vertexJ.set(vertexJ);
        this.label.set(label);
    }

    public void setVertexI(String vertexI) {
        this.vertexI.set(vertexI);
    }

    public void setVertexJ(String vertexJ) {
        this.vertexJ.set(vertexJ);
    }

    public void setLabel(String label) {
        this.label.set(label);
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
}
