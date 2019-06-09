package io.github.innofang.lib;

import io.github.innofang.bean.Pair;

import java.io.IOException;
import java.util.HashMap;

public class Matcher {

    public static boolean match(State state, Visitor visitor) throws IOException, InterruptedException {
        if (state.isSuccess()) {
            return visitor.visit(state.getMapping());
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
                if (match(s, visitor)) {
                    s.backTrack(pair);
                    return true;
                } else {
                    s.backTrack(pair);
                }
            }
        }
        return false;
    }

    public interface Visitor {
        boolean visit(HashMap<Integer, Integer> mapping) throws IOException, InterruptedException;
    }
}
