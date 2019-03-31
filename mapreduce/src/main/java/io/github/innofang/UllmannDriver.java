package io.github.innofang;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class UllmannDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();

        if (args.length < 2) {
            System.out.println("Please specify the complete command.");
            System.out.println("    hadoop jar <jar file> <main class> <input file> <output file>.");
            System.exit(0);
        }

        Path outputPath = new Path(args[1]);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
            System.out.println("Output file exists, but it has deleted.");
        }

        Job job = Job.getInstance(conf, "ullmann");
        job.setJarByClass(UllmannDriver.class);
        job.setInputFormatClass(QueryGraphFileInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        job.setMapperClass(UllmannMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.out.println(job.waitForCompletion(true) ? 0 : 1);
    }

}
