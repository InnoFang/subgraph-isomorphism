package io.github.innofang.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Vertex implements Writable {

    private Text vertex;
    private Text label;

    public Vertex() {
        this("", "");
    }

    public Vertex(String vertex, String label) {
        this.vertex = new Text(vertex);
        this.label  = new Text(label);
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
