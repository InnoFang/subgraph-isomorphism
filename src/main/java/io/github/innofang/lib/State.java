package io.github.innofang.lib;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Pair;

import java.util.HashMap;

public abstract class State implements Cloneable {

    public State(Graph sourceGraph, Graph targetGraph){}
    public State(State state){}

    /**
     * Get the source graph
     *
     * @return source graph
     */
    abstract Graph getSourceGraph();

    /**
     * Get the target graph
     *
     * @return target graph
     */
    abstract Graph getTargetGraph();

    /**
     * Check the new pair is match or not
     *
     * @return true if new pair is match, otherwise false
     */
    abstract boolean isFeasiblePair(Pair<Integer, Integer> pair);

    /**
     * Add a pair to the mapping
     */
    abstract void addPair(Pair<Integer, Integer> pair);

    /**
     * Is the source graph isomorphic the target graph or not
     *
     * @return true if sub-graph isomorphism, otherwise false
     */
    abstract boolean isSuccess();

    /**
     * It's not simply the opposite of the return value of isSuccess(),
     * but a part of the logic of the algorithm is checked to see if
     * the algorithm can be continue to executed.
     *
     * @return
     */
    abstract boolean isFailure();

    /**
     * Get the mapping between source graph and target graph when they're
     * sub-graph isomorphism.
     *
     * @return
     */
    abstract HashMap<Integer, Integer> getMapping();

    /**
     * Allow a state to clean up things before reverting to its parent
     * when execute DFS
     */
    abstract void backTrack();

    /**
     * Clone a same new state instance
     * @return a same new state
     */
    protected abstract State clone();

    /**
     * Get the state iterator, which make you can to get the next pair
     * @return a iterator instance
     */
    abstract Iterator iterator();

    interface Iterator {
        /**
         * Check if the state have next pair
         *
         * @return true if the state have next pair, otherwise false
         */
        boolean hasNextPair();

        /**
         * Generate a new pair
         *
         * @return true if have more pairs, otherwise false
         */
        Pair<Integer, Integer> nextPair();
    }
}
