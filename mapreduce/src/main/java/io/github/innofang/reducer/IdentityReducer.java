package io.github.innofang.reducer;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.TextArrayWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class IdentityReducer extends Reducer<Graph, MapWritable, Text, TextArrayWritable> {

    @Override
    protected void reduce(Graph graph, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
        int isomorphicNumber = 0;
        List<Text> mappingResult = new ArrayList<>();

        for (MapWritable mapWritable : values) {
            ++ isomorphicNumber;
            HashMap<Text, Text> mapping = new HashMap<>();
            for (Map.Entry<Writable, Writable> pair : mapWritable.entrySet()) {
                mapping.put((Text) pair.getKey(), (Text) pair.getValue());
            }
            mappingResult.add(new Text(mapping.toString()));
        }

        TextArrayWritable outputValue = new TextArrayWritable();
        outputValue.set(mappingResult.toArray(new Text[0]));
        context.write(
                new Text(String.format("Query Graph %s is isomorphic Target Graph, %d pair of mapping.\n",
                        graph.getGraphId(), isomorphicNumber)),
                outputValue);
    }
}
