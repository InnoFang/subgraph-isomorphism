package io.github.innofang;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.TextArrayWritable;
import io.github.innofang.mapper.ullmann.UllmannMapper;
import io.github.innofang.reducer.IdentityReducer;
import io.github.innofang.util.SourceGraphFileInputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

public class UllmannTest extends Configured implements Tool {

    private static final String INPUT_FILE =
            "/home/innofang/Documents/Github/subgraph-isomorphism/datasets/graphDB/Q4.txt";

    private static final String OUTPUT_FOLDER =
            "/home/innofang/Documents/Github/subgraph-isomorphism/output/ullmann/";

    private static final String OUTPUT_FILE = "part-r-00000";

    private static final String MAIN_GRAPH_FILE =
            "/home/innofang/Documents/Github/subgraph-isomorphism/datasets/CollegeMsg/CollegeMsg.txt";

    @Test
    public void testUllmann() throws Exception {
        // the follow one is the solution for
        // https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j
        BasicConfigurator.configure();

        Tool tool = new UllmannTest();
        ToolRunner.run(tool, new String[0]);
        print(tool);
    }

    @Override
    public int run(String[] args) throws Exception {

        FileSystem fs = FileSystem.get(getConf());
        Path outputPath = new Path(OUTPUT_FOLDER);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
            System.out.println("Output file exists, but it has deleted.");
        }

        Job job = Job.getInstance(getConf(), "ullmann");
        job.addCacheFile(new URI(MAIN_GRAPH_FILE));
        job.setJarByClass(getClass());
        job.setInputFormatClass(SourceGraphFileInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(INPUT_FILE));

        job.setMapperClass(UllmannMapper.class);
        job.setMapOutputKeyClass(Graph.class);
        job.setMapOutputValueClass(MapWritable.class);

        job.setNumReduceTasks(1);

        job.setReducerClass(IdentityReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TextArrayWritable.class);

        FileOutputFormat.setOutputPath(job, outputPath);

        job.waitForCompletion(true);
        return 0;
    }

    private void print(Tool tool) throws IOException {
        FileSystem fs = FileSystem.get(tool.getConf());
        Path path = new Path(OUTPUT_FOLDER, OUTPUT_FILE);
        if (fs.exists(path)) {
            FSDataInputStream is = fs.open(path);
            int length = 0;
            byte[] buff = new byte[128];
            while ((length = is.read(buff, 0, 128)) != -1) {
                System.out.println(new String(buff, 0, length));
            }
        } else {
            System.err.println("Haven't generate the result correctly yet!");
        }
    }

}