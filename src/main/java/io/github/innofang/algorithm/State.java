package io.github.innofang.algorithm;

import io.github.innofang.bean.Graph;

import java.util.HashMap;

public interface State extends Cloneable {

    /**
     * Get the source graph
     *
     * @return source graph
     */
    Graph getSourceGraph();

    /**
     * Get the target graph
     *
     * @return target graph
     */
    Graph getTargetGraph();

    /**
     * Generate a new pair
     *
     * @return true if have more pairs, otherwise false
     */
    HashMap<Integer, Integer> generatePair();

    /**
     * Check the new pair is match or not
     *
     * @return true if new pair is match, otherwise false
     */
    boolean isFeasiblePair(int sourceVertex, int targetVertex);

    /**
     * Add a pair to the mapping
     *
     * @param targetVertex the vertex of target graph
     * @param sourceVertex the vertex of source graph
     */
    void addPair(int sourceVertex, int targetVertex);

    /**
     * Is the source graph isomorphic the target graph or not
     *
     * @return true if sub-graph isomorphism, otherwise false
     */
    boolean isSuccess();

    /**
     * It's not simply the opposite of the return value of isSuccess(),
     * but a part of the logic of the algorithm is checked to see if
     * the algorithm can be continue to executed.
     *
     * @return
     */
    boolean isFailure();

    /**
     * Get the mapping between source graph and target graph when they're
     * sub-graph isomorphism.
     *
     * @return
     */
    HashMap<Integer, Integer> getMapping();

    /**
     * Allow a state to clean up things before reverting to its parent
     * when execute DFS
     */
    void backTrack();
}
