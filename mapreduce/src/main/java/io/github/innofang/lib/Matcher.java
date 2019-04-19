package io.github.innofang.lib;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Pair;
import io.github.innofang.mapper.vf2.VF2Mapper;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Matcher {

    public static boolean match(State state, Mapper.Context context, Graph graph, Visitor visitor) throws IOException, InterruptedException {
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
                s.addPair(pair);
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
        boolean visit(Mapper.Context context, Graph graph, HashMap<Integer, Integer> mapping) throws IOException, InterruptedException;
    }
}
