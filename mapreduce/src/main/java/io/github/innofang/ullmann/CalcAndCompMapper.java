package io.github.innofang.ullmann;

import io.github.innofang.bean.*;
import io.github.innofang.util.GraphReader;
import io.github.innofang.util.MatrixOperator;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

/**
 * ChainMapper: [ConstructMMapper, CalcAndCompMapper]
 * Reducer: IdentityReducer
 * <p>
 * The second mapper computes the further elements of the permutation matrix,
 * and at each step compares the resulting matrix after calculation to the input matrix,
 * which again it reads from the distributed cache. [1]
 * <p>
 * [1] Ashish Sharma, Santosh Bahir, Sushant Narsale, Unmil Tambe,
 * "A  Parallel Algorithm for Finding Sub-graph Isomorphism",
 * CS420-ProjectReport  (www.cs.jhu.edu/~snarsal/CS420-ProjectReport.pdf),
 * CS420: Parallel Programming. Fall 2008.
 */

// Calculation and comparison Mapper
public class CalcAndCompMapper extends Mapper<Graph, IntMatrixWritable, Graph, MapWritable> {

    private Graph targetGraph;

    private int[][] MA; // for query graph
    private int[][] MB; // for target graph
    private int[][] M0;
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
            MB = targetGraph.getAdjacencyMatrix();
        } else {
            System.err.println(ConstructMMapper.class.getName() + " cannot get the distributed cache files.");
        }
    }

    @Override
    protected void map(Graph queryGraph, IntMatrixWritable intMatrixWritable, Context context) throws IOException, InterruptedException {
        int row = intMatrixWritable.get().length;
        int col = intMatrixWritable.get()[0].length;

        MA = queryGraph.getAdjacencyMatrix();
        M0 = new int[row][col];
        Writable[][] matrix = intMatrixWritable.get();
        for (int i = 0; i < M0.length; i++) {
            for (int j = 0; j < M0[0].length; j++) {
                M0[i][j] = ((IntWritable) matrix[i][j]).get();
            }
        }

        refineM(targetGraph, queryGraph);
        checkIsomorphism(targetGraph, queryGraph);
        MapWritable resultMapping = new MapWritable();
        resultMapping.putAll(getMapping());
        if (!resultMapping.isEmpty()) {
            context.write(queryGraph, resultMapping);
        }
    }


    /**
     * for any x, (MA[i][x] == 1) ===> exist y s.t. (M0[x][y] == 1 and MB[y][j] == 1).
     * or M0[i][j] = 0
     *
     * @return
     */
    private void refineM(Graph target, Graph query) {

        Vertex[] queryVertices = query.getVertexArray();

        int row = queryVertices.length;
        int col = targetVertices.length;

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (M0[i][j] == 1) {
                    String labelI = queryVertices[i].getLabel();
                    String labelJ = targetVertices[j].getLabel();
                    if (labelI.equals(labelJ)) {
                        for (int x = 0; x < row; ++x) {
                            boolean match = false;
                            if (MA[i][x] == 1) {
                                // label of MA[i][x]
                                String labelIX = query.getEdgeLabel(
                                        String.valueOf(i),
                                        String.valueOf(x));
                                for (int y = 0; y < col; ++y) {
                                    if (M0[x][y] * MB[y][j] == 1) {
                                        // label of MB[y][j]
                                        String labelYJ = target.getEdgeLabel(
                                                String.valueOf(y),
                                                String.valueOf(j));
                                        if (labelIX.equals(labelYJ)) {
                                            match = true;
                                            break;
                                        }
                                    }
                                }
                                if (!match) {
                                    M0[i][j] = 0;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    M0[i][j] = 0;
                }
            }
        }
    }

    /**
     * check if (MA[i, j] = 1) ==> (M0[i, j] == 1) for any i, j
     *
     * @return
     */
    private boolean checkIsomorphism(Graph target, Graph query) {

        Vertex[] queryVertices = query.getVertexArray();

        int row = queryVertices.length;
        int col = targetVertices.length;

        for (int i = 0; i < row; ++i) {
            int sumOfRow = 0;
            for (int j = 0; j < col; ++j) {
                sumOfRow += M0[i][j];
            }
            // if the sum of a row is 0, return false directly.
            if (sumOfRow == 0) {
                return false;
            }
        }

        int F[] = new int[col]; // F[i] = 1，represent the column i have been selected
        int H[] = new int[row]; // H[i] = j，represent the column j have been selected by row i.

        int d = 0; // row d
        int k = 0; // column k

        int[][][] matrixList = new int[row][][]; // which is column d corresponding to the M0

        for (int i = 0; i < F.length; i++) { // initialize -1
            F[i] = -1;
        }
        for (int i = 0; i < H.length; i++) { // initialize -1
            H[i] = -1;
        }

        while (true) {
            if (H[d] == -1) {   // if row d have not been selected, search it
                k = 0;
                matrixList[d] = M0;
            } else {            // or backtracking
                k = H[d] + 1;
                F[H[d]] = -1;
                M0 = matrixList[d];
            }
            // To find which column is match the condition
            while (k < col) {
                if (M0[d][k] == 1 && F[k] == -1) {
                    break;
                }
                k++;
            }
            if (k == col) { // row d is not match the condition, so backtracking
                H[d] = -1;
                d--;
            } else {
                // 找到对应的列，设置M0，d+=1，进行下一列搜索
                for (int j = 0; j < col; j++) {
                    M0[d][j] = 0;
                }
                M0[d][k] = 1;
                H[d] = k;
                F[k] = 1;
                d++;
            }
            // end search, cannot find the map between query graph and target graph
            if (d == -1) {
                return false;
            }

            // find a M0, verify it if match the condition
            if (d == row) {
                if (verify(MA, MB, M0)) {
                    return true;
                } else {
                    d = row - 1;
                }
            }
        }
    }

    /**
     * MC = M0 · (M0 · MB)T
     *
     * @param MA
     * @param MB
     * @param M0
     * @return
     */
    private boolean verify(int[][] MA, int[][] MB, int[][] M0) {
        int[][] MC = MatrixOperator.set(M0).dot(
                MatrixOperator.set(M0).dot(MB).T().get()
        ).get();


        for (int i = 0; i < MA.length; ++i) {
            for (int j = 0; j < MA[0].length; ++j) {
                if (MA[i][j] == 1 && MA[i][j] != MC[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public HashMap<Text, Text> getMapping() {
        HashMap<Text, Text> mapping = new HashMap<>();
        for (int i = 0; i < M0.length; i++) {
            for (int j = 0; j < M0[0].length; j++) {
                if (M0[i][j] == 1) {
                    mapping.put(
                            new Text(String.valueOf(i)),
                            new Text(String.valueOf(j)));
                }
            }
        }
        return mapping;
    }
}
