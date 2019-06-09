package io.github.innofang.util;


import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;
import java.util.ArrayList;

public class SourceGraphFileInputFormat extends FileInputFormat<IntWritable, Graph> {

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    @Override
    public RecordReader<IntWritable, Graph> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new GraphFileRecordReader();
    }

    public class GraphFileRecordReader extends RecordReader<IntWritable, Graph> {

        private LineRecordReader lineReader;
        private ArrayList<Vertex> vertexList;
        private ArrayList<Edge> edgeList;
        private IntWritable graphId;
        private Graph graph;


        @Override
        public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            lineReader = new LineRecordReader();
            lineReader.initialize(inputSplit, taskAttemptContext); // should be initialized before used.
            vertexList = new ArrayList<>();
            edgeList = new ArrayList<>();
        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            while (lineReader.nextKeyValue()) {
                String line = lineReader.getCurrentValue().toString();
                if (line.equals("")) {
                    continue;
                }
                String[] info = line.split("\\s+");
                if (info[0].equals("t") && info.length == 3) {  // t # graphId
                    int graphId = Integer.valueOf(info[2]);
                    if (graphId == -1) {
                        return false;
                    }
                    if (graph != null) {
                        this.graphId = new IntWritable(graphId); // set key: GraphID

                        // set value: Graph
                        graph.setGraphId(graphId);
                        graph.setVertexArray(vertexList.toArray(new Vertex[0]));
                        graph.setEdgeArray(edgeList.toArray(new Edge[0]));
                        vertexList.clear();
                        edgeList.clear();
                        return true;
                    }
                    graph = new Graph();
                } else if (info[0].equals("v") && info.length == 2) {    // v vertex label
                    vertexList.add(new Vertex(Integer.parseInt(info[1])));
                } else if (info[0].equals("e") && info.length == 3) {   // e from to label
                    edgeList.add(new Edge(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
                } else {
                    System.err.println("Wrong line ??: " + line);
                    return false;
                }
            }
            return false;
        }

        @Override
        public IntWritable getCurrentKey() throws IOException, InterruptedException {
            return graphId;
        }

        @Override
        public Graph getCurrentValue() throws IOException, InterruptedException {
            return graph;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return lineReader.getProgress();
        }

        @Override
        public void close() throws IOException {
            lineReader.close();
        }
    }
}