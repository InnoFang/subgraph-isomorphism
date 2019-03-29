package io.github.innofang;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class File2GraphMapper extends Mapper<LongWritable, Text, Text, Text> {
}
