package io.github.innofang.ullmann;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.MatrixWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

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
public class CalcAndCompMapper extends Mapper<Graph, MatrixWritable, Text, MapWritable> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    protected void map(Graph key, MatrixWritable value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
    }
}
