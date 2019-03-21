package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Graph;

import java.util.*;


/**
 * vf2 for undirected graph matcher
 */

public class VF2 implements IsomorphismAlgorithm {

    private State state;

    private Graph targetGraph;
    private Graph queryGraph;

    private void initialize(Graph targetGraph, Graph queryGraph) {
        state = new State(targetGraph, queryGraph);
        this.targetGraph = targetGraph;
        this.queryGraph = queryGraph;
    }

    @Override
    public boolean isSubGraphIsomorphism(Graph targetGraph, Graph queryGraph) {
        initialize(targetGraph, queryGraph);
        matchRecursive(state, targetGraph, queryGraph);
        return state.matched;
    }

    /**
     * @return the map result between Query Graph and Target Graph
     */
    @Override
    public HashMap<String, String> getMapping() {
        if (state.matched) {
            HashMap<String, String> mapping = new HashMap<>();
            for (Map.Entry<String, String> entry : state.core_2.entrySet()) {
                mapping.put(entry.getKey(), entry.getValue());
            }
            return mapping;
        }
        System.out.println("Not isomorphism");
        return null;
    }

    /**
     * Extends the isomorphism mapping.
     * This function is called recursively to determine if a complete
     * isomorphism can be found between TargetGraph and QueryGraph.
     * @param state         vf2 state
     * @param targetGraph   Target Graph (big one)
     * @param queryGraph    Query Graph  (small one)
     * @return  Match or not
     */
    private boolean matchRecursive(State state, Graph targetGraph, Graph queryGraph) {

        if (state.depth == queryGraph.getVertexList().size()) {    // Found a isSubGraphIsomorphism
            state.matched = true;
            return true;
        } else {    // Extend the state
            Map<String, String> candidates = generateCandidates();
            for (Map.Entry<String, String> entry : candidates.entrySet()) {
                if (checkSyntacticFeasibility(entry.getKey(), entry.getValue())) {
                    state.extendMatch(entry.getKey(), entry.getValue()); // extend mapping
                    if (matchRecursive(state, targetGraph, queryGraph)) {    // Found a isSubGraphIsomorphism
                        return true;
                    }
                    state.backtrack(entry.getKey(), entry.getValue()); // remove the isSubGraphIsomorphism added before
                }
            }
        }
        return false;
    }

    private Map<String, String> generateCandidates() {
        Map<String, String> candidates = new LinkedHashMap<>();

        // If T1_inout and T2_inout are both nonempty.
        // P(s) = T1_inout x {min T2_inout}
        if (!state.T1inout.isEmpty() && !state.T2inout.isEmpty()) {
            // Generate candidates from T1out and T2out if they are not empty
            String vertex2 = Collections.min(state.T2inout);
            for (String vertex1 : state.T1inout) {
                candidates.put(vertex1, vertex2);
            }
            return candidates;
        } else {
            // Generate from all unmapped nodes
            String otherVertex = Collections.min(state.unmapped2);
            for (String vertex : state.unmapped1) {
                if (!state.inM1(vertex)) {
                    candidates.put(vertex, otherVertex);
                }
            }
            return candidates;
        }
    }

    /**
     * Check the feasibility of adding this isSubGraphIsomorphism
     *
     * @param targetVertex Target Graph Vertex
     * @param queryVertex  Query Graph Vertex
     * @return return true if adding (targetVertex, queryVertex) is syntactically feasible.
     */
    private Boolean checkSyntacticFeasibility(String targetVertex, String queryVertex) {
        // Rule_self
        // The number of self-loops for targetVertex must equal the number of
        // self-loops for queryVertex. Without this check, we would fail on
        // Rule_neighbor at the next recursion level. But it is good to prune the
        // search tree now.
        if (!targetGraph.getVertexLabel(targetVertex).equals(
                queryGraph.getVertexLabel(queryVertex))) {
            return false;
        }

        // Rule_neighbor
        // For each neighbor n' of n in the partial mapping, the corresponding
        // node m' is a neighbor of m, and vice versa. Also, the number of
        // edges must be equal.
        if (!checkNeighborVertexes(targetVertex, queryVertex)) {
            return false;
        }

        // Look ahead 1

        // Rule_terminout
        // The number of neighbors of n that are in T_1^{inout} is equal to the
        // number of neighbors of m that are in T_2^{inout}, and vice versa.
        if (!checkInAndOut(targetVertex, queryVertex)) {
            return false;
        }

        // Look ahead 2

        // Rule_new
        // The number of neighbors of n that are neither in the core_1 nor
        // T_1^{inout} is equal to the number of neighbors of m
        // that are neither in core_2 nor T_2^{inout}.
        if (!checkNew(targetVertex, queryVertex)) {
            return false;
        }

        // Otherwise, this vertex pair is syntactically feasible!
        return true;
    }

    /**
     * Rule_neighbor
     * For each neighbor n' of n in the partial mapping, the corresponding
     * node m' is a neighbor of m, and vice versa. Also, the number of
     * edges must be equal.
     *
     * @param targetVertex Target Graph Vertex
     * @param queryVertex  Query Graph Vertex
     * @return Feasible or not
     */
    private boolean checkNeighborVertexes(String targetVertex, String queryVertex) {
        for (String neighbor : targetGraph.getNeighborVertexList(targetVertex)) {
            if (state.inM1(neighbor)) {
                if (!queryGraph.getNeighborVertexList(queryVertex).contains(state.core_1.get(neighbor))) {
                    return false;
                }
                if (!targetGraph.getEdgeLabel(neighbor, targetVertex).equals(
                        queryGraph.getEdgeLabel(state.core_1.get(neighbor), queryVertex))) {
                    return false;
                }
            }
        }

        for (String neighbor : queryGraph.getNeighborVertexList(queryVertex)) {
            if (state.inM2(neighbor)) {
                if (!targetGraph.getNeighborVertexList(targetVertex).contains(state.core_2.get(neighbor))) {
                    return false;
                }
                if (!targetGraph.getEdgeLabel(state.core_2.get(neighbor), targetVertex).equals(
                        targetGraph.getEdgeLabel(neighbor, queryVertex))) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Rule_terminout
     * The number of neighbors of n that are in T_1^{inout} is equal to the
     * number of neighbors of m that are in T_2^{inout}, and vice versa.
     *
     * @param targetVertex Target Graph Vertex
     * @param queryVertex  Query Graph Vertex
     * @return Feasible or not
     */
    private boolean checkInAndOut(String targetVertex, String queryVertex) {
        int num1 = 0;
        for (String neighbor : targetGraph.getNeighborVertexList(targetVertex)) {
            if (state.inT1inout(neighbor) && !state.inM1(neighbor)) {
                ++ num1;
            }
        }

        int num2 = 0;
        for (String neighbor : queryGraph.getNeighborVertexList(queryVertex)) {
            if (state.inT2inout(neighbor) && !state.inM2(neighbor)) {
                ++ num2;
            }
        }

        return num1 >= num2;
    }

    /**
     * Rule_new
     * The number of neighbors of n that are neither in the core_1 nor
     * T_1^{inout} is equal to the number of neighbors of m
     * that are neither in core_2 nor T_2^{inout}.
     *
     * @param targetVertex Target Graph vertex
     * @param queryVertex  Query Graph vertex
     * @return Feasible or not
     */
    private boolean checkNew(String targetVertex, String queryVertex) {
        int num1 = 0;
        for (String neighbor : targetGraph.getNeighborVertexList(targetVertex)) {
            if (!state.inT1inout(neighbor)) {
                ++num1;
            }
        }

        int num2 = 0;
        for (String neighbor : queryGraph.getNeighborVertexList(queryVertex)) {
            if (!state.inT2inout(neighbor)) {
                ++num2;
            }
        }

        return num1 >= num2;
    }

    public class State {

        /**
         * core_1[n] contains the index of the node paired with n, which is m,
         *           provided n is in the mapping.
         * core_2[m] contains the index of the node paired with m, which is n,
         *           provided m is in the mapping.
         */
        private HashMap<String, String> core_1;
        private HashMap<String, String> core_2;

        // See the paper for definitions of M_x and T_x^{y}

        /**
         *  inout_1[n]  is non-zero if n is in M_1 or in T_1^{inout}
         *  inout_2[m]  is non-zero if m is in M_2 or in T_2^{inout}
         *
         *  The value stored is the depth of the SSR tree when the node became
         *  part of the corresponding set.
         */
        private HashMap<String, Integer> inout_1;
        private HashMap<String, Integer> inout_2;

        /**
         * // T1inout[n] is that not yet in the partial mapping, that are the origin of branches end into target graph
         * // T2inout[m] is that not yet in the partial mapping, that are the origin of branches end into query graph
         */
        private HashSet<String> T1inout;
        private HashSet<String> T2inout;

        private HashSet<String> unmapped1;    // unmapped nodes in target graph
        private HashSet<String> unmapped2;    // unmapped nodes in query graph

        private int depth = 0; // current depth of the search tree

        private boolean matched = false;


        /**
         * Initialize a State
         *
         * @param targetGraph The big graph
         * @param queryGraph  The small graph
         */
        public State(Graph targetGraph, Graph queryGraph) {

            int targetSize = targetGraph.getVertexList().size();
            int querySize = queryGraph.getVertexList().size();

            T1inout = new HashSet<>(targetSize * 2);
            T2inout = new HashSet<>(querySize * 2);

            unmapped1 = new HashSet<>(targetSize * 2);
            unmapped2 = new HashSet<>(querySize * 2);

            core_1 = new HashMap<>();
            core_2 = new HashMap<>();

            inout_1 = new HashMap<>();
            inout_2 = new HashMap<>();

            // initially, all sets are empty and no nodes are mapped
            for (int i = 0; i < targetSize; i++) {
                unmapped1.add(String.valueOf(i));
            }
            for (int i = 0; i < querySize; i++) {
                unmapped2.add(String.valueOf(i));
            }
        }

        public Boolean inM1(String vertex) {
            return core_1.containsKey(vertex);
        }

        public Boolean inM2(String vertex) {
            return core_2.containsKey(vertex);
        }

        public Boolean inT1inout(String vertex) {
            return core_1.containsKey(vertex) && inout_1.containsKey(vertex);
        }


        public Boolean inT2inout(String vertex) {
            return core_2.containsKey(vertex) && inout_2.containsKey(vertex);
        }

        /**
         * Add a new isSubGraphIsomorphism (targetVertex, queryVertex) to the state
         *
         * @param targetVertex The vertex in target graph
         * @param queryVertex  The vertex in query graph
         */
        public void extendMatch(String targetVertex, String queryVertex) {

            core_1.put(targetVertex, queryVertex);
            core_2.put(queryVertex, targetVertex);
            unmapped1.remove(targetVertex);
            unmapped2.remove(queryVertex);
            T1inout.remove(targetVertex);
            T2inout.remove(queryVertex);

            depth++;    // move down one level in the search tree

            for (String neighborVertex : targetGraph.getNeighborVertexList(targetVertex)) {
                if (!inout_1.containsKey(neighborVertex)) {
                    inout_1.put(neighborVertex, depth);
                    if (!inM1(neighborVertex)) {
                        T1inout.add(neighborVertex);
                    }
                }
            }

            for (String neighborVertex : queryGraph.getNeighborVertexList(queryVertex)) {
                if (!inout_2.containsKey(neighborVertex)) {
                    inout_2.put(neighborVertex, depth);
                    if (!inM2(neighborVertex)) {
                        T2inout.add(neighborVertex);
                    }
                }
            }
        }

        /**
         * Remove the isSubGraphIsomorphism of (targetVertex, queryVertex) for backtrack
         *
         * @param targetVertex
         * @param queryVertex
         */
        public void backtrack(String targetVertex, String queryVertex) {

            core_1.remove(targetVertex);
            core_2.remove(queryVertex);
            unmapped1.add(targetVertex);
            unmapped2.add(queryVertex);

            for (String key : core_1.keySet()) {
                if (inout_1.containsKey(key) && inout_1.get(key) == depth) {
                    inout_1.remove(key);
                    T1inout.remove(key);
                }
            }

            for (String key : core_2.keySet()) {
                if (inout_2.containsKey(key) && inout_2.get(key) == depth) {
                    inout_2.remove(key);
                    T2inout.remove(key);
                }
            }

            // put targetVertex and queryVertex back into Tin and Tout sets if necessary
            if (inT1inout(targetVertex))
                T1inout.add(targetVertex);
            if (inT2inout(queryVertex))
                T2inout.add(queryVertex);

            depth--;
        }
    }
}
