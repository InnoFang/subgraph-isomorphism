package io.github.innofang.bean;

import org.apache.hadoop.io.ArrayWritable;

public class EdgeArrayWritable extends ArrayWritable {
    public EdgeArrayWritable() {
        super(Edge.class);
    }
}
