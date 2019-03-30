package io.github.innofang;

import io.github.innofang.bean.Graph;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class QueryFile2GraphMapper extends Mapper<IntWritable, Graph, IntWritable, Graph> {

    @Override
    protected void map(IntWritable key, Graph value, Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
