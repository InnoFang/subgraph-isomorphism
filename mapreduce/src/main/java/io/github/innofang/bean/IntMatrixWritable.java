package io.github.innofang.bean;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.TwoDArrayWritable;

public class IntMatrixWritable extends TwoDArrayWritable {
    public IntMatrixWritable() {
        super(IntWritable.class);
    }
}
