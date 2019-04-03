package io.github.innofang.ullmann;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashSet;

public class UllmannMapper extends Mapper<IntWritable, Graph, IntWritable, Text> {

    Graph targetGraph;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            FileSystem fs = FileSystem.get(context.getConfiguration());
            Path path = new Path(cacheFiles[0].toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
            String line;
            HashSet<Vertex> vertices = new HashSet<>();
            HashSet<Edge> edges = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#"))
                    continue;

                String[] info = line.split("\\s+");
                if (info.length == 2) {
                    vertices.add(new Vertex(info[0]));
                    vertices.add(new Vertex(info[1]));
                    edges.add(new Edge(info[0], info[1]));
                } else {
                    System.err.println("Wrong Line: " + line);
                }
            }
            targetGraph = new Graph(vertices.toArray(new Vertex[0]), edges.toArray(new Edge[0]));
        } else {
            System.err.println(UllmannMapper.class.getName() + " cannot get the distributed cache files.");
        }
    }

    @Override
    protected void map(IntWritable key, Graph value, Context context) throws IOException, InterruptedException {
        context.write(key, new Text(value.toString()));
    }
}
