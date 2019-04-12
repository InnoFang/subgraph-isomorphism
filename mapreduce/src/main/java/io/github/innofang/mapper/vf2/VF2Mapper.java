package io.github.innofang.mapper.vf2;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Pair;
import io.github.innofang.bean.Vertex;
import io.github.innofang.lib.State;
import io.github.innofang.lib.VF2State;
import io.github.innofang.mapper.ullmann.ConstructMMapper;
import io.github.innofang.util.GraphReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VF2Mapper extends Mapper<IntWritable, Graph, Graph, MapWritable> {

    private Graph targetGraph;

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
    protected void map(IntWritable key, Graph value, Context context) throws IOException, InterruptedException {
        match(new VF2State(value, targetGraph), context, value, (cxt, graph, mapping) -> {
            MapWritable map = new MapWritable();
            for (Map.Entry<Integer, Integer> entry : mapping.entrySet()) {
                map.put(new IntWritable(entry.getKey()), new IntWritable(entry.getValue()));
            }
            cxt.write(graph, map);
            return false;
        });

    }

    private static boolean match(State state, Context context, Graph graph, Visitor visitor) throws IOException, InterruptedException {
        if (state.isSuccess()) {
            return visitor.visit(context, graph, state.getMapping());
        }

        if (state.isFailure()) {
            return false;
        }

        State.PairIterator iterator = state.iterator();
        while (iterator.hasNextPair()) {
            Pair<Integer, Integer> pair = iterator.nextPair();
            if (state.isFeasiblePair(pair)) {
                State s = state.clone();
                state.addPair(pair);
                if (match(s, context, graph, visitor)) {
                    s.backTrack(pair);
                    s = null;
                    return true;
                } else {
                    s.backTrack(pair);
                    s = null;
                }
            }
        }
        return false;
    }

    public interface Visitor {
        boolean visit(Context context, Graph graph, HashMap<Integer, Integer> mapping) throws IOException, InterruptedException;
    }
}
