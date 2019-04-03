package io.github.innofang;

import io.github.innofang.ullmann.UllmannMapper;
import io.github.innofang.util.QueryGraphFileInputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.io.IOException;

public class UllmannDriverTest extends Configured implements Tool {

    private static final String INPUT_FILE =
            "/home/innofang/Documents/Github/subgraph-isomorphism/src/test/resources/graphDB/Q4.my";

    private static final String JOB_NAME = "ullmann";
    private static final String OUTPUT_FOLDER =
            "/home/innofang/Documents/Github/subgraph-isomorphism/output/" + JOB_NAME;

    private static final String OUTPUT_FILE = "part-m-00000";

    @Test
    public void testUllmann() throws Exception {
        // the follow one is the solution for
        // https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j
        BasicConfigurator.configure();

        Tool tool = new UllmannDriverTest();
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

        Job job = Job.getInstance(getConf(), JOB_NAME);
        job.setJarByClass(getClass());
        job.setInputFormatClass(QueryGraphFileInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(INPUT_FILE));

        job.setMapperClass(UllmannMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);

        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_FOLDER));

        job.waitForCompletion(true);
        return 0;
    }

    private void print(Tool tool) throws IOException {
        FileSystem fs = FileSystem.get(tool.getConf());
        Path path = new Path(OUTPUT_FOLDER, OUTPUT_FILE);
        FSDataInputStream is = fs.open(path);
        int length = 0;
        byte[] buff = new byte[128];
        while ((length = is.read(buff, 0, 128)) != -1) {
            System.out.println(new String(buff, 0, length));
        }
    }

}