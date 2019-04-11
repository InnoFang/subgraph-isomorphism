package io.github.innofang.mapper;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.IntMatrixWritable;
import io.github.innofang.bean.Vertex;
import io.github.innofang.util.GraphReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;

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

public class ConstructMMapper extends Mapper<IntWritable, Graph, Graph, IntMatrixWritable> {

    private Graph targetGraph;

    private IntWritable one = new IntWritable(1);
    private IntWritable zero = new IntWritable(0);
    private Vertex[] targetVertices;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            FileSystem fs = FileSystem.get(context.getConfiguration());
            Path path = new Path(cacheFiles[0].toString());
            GraphReader reader = new GraphReader(fs, path);
            targetGraph = reader.loadGraph();
            targetVertices = targetGraph.getVertexArray();
        } else {
            System.err.println(ConstructMMapper.class.getName() + " cannot get the distributed cache files.");
        }
    }

    @Override
    protected void map(IntWritable graphId, Graph graph, Context context) throws IOException, InterruptedException {
        if (targetGraph == null) return;

        IntMatrixWritable intMatrixWritable = new IntMatrixWritable();
        intMatrixWritable.set(getMatrixM(targetGraph, graph));

        context.write(graph, intMatrixWritable);
    }


    /**
     * construct Graph_query x Graph_large element matrix M0 in according with:
     * <p>
     * Mij = 1 if the degree of the jth point of Graph_large is greater than or
     *         equal to the degree of the ith point of Graph_query
     *     = 0 otherwise
     *
     * @param query  Query graph
     * @param target Large graph
     * @return M0
     */
    private IntWritable[][] getMatrixM(Graph target, Graph query) {
        Vertex[] queryVertices = query.getVertexArray();
        int row = queryVertices.length;
        int col = targetVertices.length;
        IntWritable[][] M0 = new IntWritable[row][col];
        for (int i = 0; i < row; ++ i) {
            for (int j = 0; j < col; ++ j) {
                int vertexJ = targetVertices[j].getVertex();
                int vertexI = queryVertices[i].getVertex();
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
