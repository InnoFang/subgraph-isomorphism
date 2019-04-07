package io.github.innofang.bean;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TextArrayWritable extends ArrayWritable {
    public TextArrayWritable() {
        super(Text.class);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Writable[] writableArray = get();
        builder.append((Text) writableArray[0]);
        for (int i = 1; i < writableArray.length; i++) {
            Text text = (Text) writableArray[i];
            builder.append(", \n\t").append(text.toString());
        }
        builder.append("]");
        return builder.toString();
    }
}
