package io.github.innofang.bean;

import org.apache.hadoop.io.ArrayWritable;

public class VertexArrayWritable extends ArrayWritable {
    public VertexArrayWritable() {
        super(Vertex.class);
    }
}
