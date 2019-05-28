package io.github.innofang.lib;

import io.github.innofang.bean.Pair;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Matcher {

    public static int match(State state, Visitor visitor) {
        AtomicInteger count = new AtomicInteger(0);
        match(state, visitor, count);
        return count.get();
    }

    private static boolean match(State state, Visitor visitor, AtomicInteger count) {
        if (state.isSuccess()) {
            count.incrementAndGet();
            return visitor.visit(state.getMapping());
        }

        if (state.isFailure()) {
            return false;
        }

        State.PairIterator iterator = state.iterator();
        while(iterator.hasNextPair()) {
            Pair<Integer, Integer> pair = iterator.nextPair();
            if (state.isFeasiblePair(pair)) {
                State s = state.clone();
                s.addPair(pair);
                if (match(s, visitor, count)) {
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
        boolean visit(HashMap<Integer, Integer> mapping);
    }
}
