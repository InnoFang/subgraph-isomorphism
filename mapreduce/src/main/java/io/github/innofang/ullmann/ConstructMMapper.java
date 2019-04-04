package io.github.innofang.ullmann;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.MatrixWritable;
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

/**
 * ChainMapper: [ConstructMMapper, CalcAndCompMapper]
 * Reducer: IdentityReducer
 * <p>
 * The first mapper calculates the dimensions(N) from the target graph
 * which is its input -- this is file is read from the distributed cache
 * It then sets the distinct columns of first row to 1.This is the output of first mapper.[1]
 * <p>
 * [1] Ashish Sharma, Santosh Bahir, Sushant Narsale, Unmil Tambe,
 * "A  Parallel Algorithm for Finding Sub-graph Isomorphism",
 * CS420-ProjectReport  (www.cs.jhu.edu/~snarsal/CS420-ProjectReport.pdf),
 * CS420: Parallel Programming. Fall 2008.
 */

public class ConstructMMapper extends Mapper<IntWritable, Graph, Graph, MatrixWritable> {

    private Graph targetGraph;

    private IntWritable one = new IntWritable(1);
    private IntWritable zero = new IntWritable(0);

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
            System.err.println(ConstructMMapper.class.getName() + " cannot get the distributed cache files.");
        }
    }

    @Override
    protected void map(IntWritable graphId, Graph graph, Context context) throws IOException, InterruptedException {
        if (targetGraph == null) return;

        MatrixWritable matrixWritable = new MatrixWritable(Integer.class);
        matrixWritable.set(getMatrixM(targetGraph, graph));

        context.write(graph, matrixWritable);
    }


    /**
     * construct Graph_query x Graph_large element matrix M0 in according with:
     * <p>
     * Mij = 1 if the degree of the jth point of Graph_large is greater than or
     * equal to the degree of the ith point of Graph_query
     * = 0 otherwise
     *
     * @param query  Query graph
     * @param target Large graph
     * @return M0
     */
    private IntWritable[][] getMatrixM(Graph target, Graph query) {
        Vertex[] queryVertices = query.getVertexArray();
        Vertex[] targetVertices = target.getVertexArray();
        int row = queryVertices.length;
        int col = targetVertices.length;
        IntWritable[][] M0 = new IntWritable[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                String vertexJ = targetVertices[j].getVertex();
                String vertexI = queryVertices[i].getVertex();
                int degreeJ = query.getVertexDegree(vertexJ);
                int degreeI = query.getVertexDegree(vertexI);
                if (degreeJ >= degreeI) {
                    M0[i][j] = one;
                } else {
                    M0[i][j] = zero;
                }
            }
        }
        return M0;
    }

}
