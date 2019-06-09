package io.github.innofang.mapper.vf2;

import io.github.innofang.bean.Graph;
import io.github.innofang.lib.Matcher;
import io.github.innofang.lib.VF2State;
import io.github.innofang.util.GraphReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class VF2Mapper extends Mapper<IntWritable, Graph, Graph, MapWritable> {

    private Graph targetGraph;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            FileSystem fs = FileSystem.get(context.getConfiguration());
            Path path = new Path(cacheFiles[0].toString());
            GraphReader reader = new GraphReader(fs, path);
            targetGraph = reader.loadGraph();
        } else {
            System.err.println(VF2Mapper.class.getName() + " cannot get the distributed cache files.");
        }

    }

    @Override
    protected void map(IntWritable key, Graph value, Context context) throws IOException, InterruptedException {
        Matcher.match(new VF2State(value, targetGraph), mapping -> {
            MapWritable map = new MapWritable();
            for (Map.Entry<Integer, Integer> entry : mapping.entrySet()) {
                map.put(new IntWritable(entry.getKey()), new IntWritable(entry.getValue()));
            }
            System.out.println("[Mapping]: " + mapping);
            context.write(value, map);
            return false;
        });
    }
}
