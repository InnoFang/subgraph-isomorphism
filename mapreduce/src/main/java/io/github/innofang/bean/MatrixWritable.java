package io.github.innofang.bean;

import org.apache.hadoop.io.TwoDArrayWritable;

public class MatrixWritable extends TwoDArrayWritable {
    public MatrixWritable(Class valueClass) {
        super(valueClass);
    }
}
