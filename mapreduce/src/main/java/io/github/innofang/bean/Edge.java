package io.github.innofang.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Edge implements Writable {

    private Text vertexI;   // or Vertex 'from'
    private Text vertexJ;   // or Vertex 'to'
    private Text label;

    public Edge() {
        this("", "", "");
    }

    public Edge(String vertexI, String vertexJ, String label) {
        this.vertexI = new Text(vertexI);
        this.vertexJ = new Text(vertexJ);
        this.label   = new Text(label);
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
