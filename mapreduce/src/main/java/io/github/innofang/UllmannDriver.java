package io.github.innofang;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.TextArrayWritable;
import io.github.innofang.mapper.ullmann.UllmannMapper;
import io.github.innofang.reducer.IdentityReducer;
import io.github.innofang.util.ArgsParser;
import io.github.innofang.util.SourceGraphFileInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UllmannDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();

        ArgsParser parser = ArgsParser.parse(args);

        Path outputPath = new Path(parser.getOutputFolderPath());
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
            System.out.println("Output file exists, but it has deleted.");
        }

        Job job = Job.getInstance(conf, "ullmann");
        job.addCacheFile(new URI(parser.getTargetGraphFilePath()));
        job.setJarByClass(UllmannDriver.class);
        job.setInputFormatClass(SourceGraphFileInputFormat.class);
        FileInputFormat.setInputPaths(job, parser.getSourceGraphFilePath());

        job.setMapperClass(UllmannMapper.class);
        job.setMapOutputKeyClass(Graph.class);
        job.setMapOutputValueClass(MapWritable.class);

        job.setNumReduceTasks(1);

        job.setReducerClass(IdentityReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TextArrayWritable.class);

        FileOutputFormat.setOutputPath(job, outputPath);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
