package io.github.innofang;

import io.github.innofang.bean.Graph;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UllmannMapper extends Mapper<IntWritable, Graph, IntWritable, Text> {

    @Override
    protected void map(IntWritable key, Graph value, Context context) throws IOException, InterruptedException {
        context.write(key, new Text(value.toString()));
    }
}
