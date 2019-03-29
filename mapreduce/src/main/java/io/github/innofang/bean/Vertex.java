package io.github.innofang.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Vertex implements Writable {

    private Text vertex = new Text();
    private Text label  = new Text();

    public Vertex() {
        this("", "");
    }

    public Vertex(String vertex, String label) {
        this.vertex.set(vertex);
        this.label .set(label);
    }

    public void setVertex(String vertex) {
        this.vertex.set(vertex);
    }

    public void setLabel(String label) {
        this.label.set(label);
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
}
