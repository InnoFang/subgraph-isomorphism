package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.util.Pair;

import java.util.*;


/**
 * VF2 for undirected graph matcher
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
    public boolean match(Graph targetGraph, Graph queryGraph) {
        initialize(targetGraph, queryGraph);
        //writer.write("(" + core_2[i] + "-" + i + ") ");
        matchRecursive(state, targetGraph, queryGraph);
        return state.matched;
    }

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

    private boolean matchRecursive(State state, Graph targetGraph, Graph queryGraph) {

        if (state.depth == queryGraph.getVertexList().size()) {    // Found a match
            state.matched = true;
            return true;
        } else {    // Extend the state
            Map<String, String> candidates = generateCandidates();
            for (Map.Entry<String, String> entry : candidates.entrySet()) {
                if (checkSyntacticFeasibility(entry.getKey(), entry.getValue())) {
                    state.extendMatch(entry.getKey(), entry.getValue()); // extend mapping
                    if (matchRecursive(state, targetGraph, queryGraph)) {    // Found a match
                        return true;
                    }
                    state.backtrack(entry.getKey(), entry.getValue()); // remove the match added before
                }
            }
        }
        return false;
    }

    private Map<String, String> generateCandidates() {
//        List<Pair<Integer, Integer>> pairList = new ArrayList<>();
        Map<String, String> candidates = new LinkedHashMap<>();



        if (!state.T1inout.isEmpty() && !state.T2inout.isEmpty()) {
            // Generate candidates from T1out and T2out if they are not empty

            // Faster Version
            // Since every node should be matched in query graph
            // Therefore we can only extend one node of query graph (with biggest id)
            // instead of generate the whole Cartesian product of the target and query
//            int queryNodeIndex = -1;
//            for (String vertex : state.T2inout) {
//                queryNodeIndex = Math.max(v, queryNodeIndex);
//            }
//            for (int i : state.T1inout) {
////                pairList.add(new Pair<>(i, queryNodeIndex));
//                candidates.put(String.valueOf(i), String.valueOf(queryNodeIndex));
//            }

            // Slow Version
//			for (int i : state.T1out){
//				for (int j : state.T2out){
//					pairList.add(new Pair<Integer,Integer>(i, j));
//				}
//			}

            String vertex2 = Collections.max(state.T2inout);
            for (String vertex1 : state.T1inout) {
                candidates.put(vertex1, vertex2);
            }
            return candidates;
        } else {
            // Generate from all unmapped nodes

            // Faster Version
            // Since every node should be matched in query graph
            // Therefore we can only extend one node of query graph (with biggest id)
            // instead of generate the whole Cartesian product of the target and query
//            int queryNodeIndex = -1;
//            for (int i : state.unmapped2) {
//                queryNodeIndex = Math.max(i, queryNodeIndex);
//            }
//            for (int i : state.unmapped1) {
////                pairList.add(new Pair<>(i, queryNodeIndex));
//                candidates.put(String.valueOf(i), String.valueOf(queryNodeIndex));
//            }

            // Slow Version
//			for (int i : state.unmapped1){
//				for (int j : state.unmapped2){
//					pairList.add(new Pair<Integer,Integer>(i, j));
//				}
//			}

            String otherVertex = Collections.max(state.unmapped2);
            for (String vertex : state.unmapped1) {
                if (!state.inM1(vertex)) {
                    candidates.put(vertex, otherVertex);
                }
            }
            return candidates;
        }
    }

    /**
     * Check the feasibility of adding this match
     *
     * @param targetVertex Target Graph Node Index
     * @param queryVertex  Query Graph Node Index
     * @return Feasible or not
     */
    private Boolean checkSyntacticFeasibility(String targetVertex, String queryVertex) {
        // Node Label Rule
        // The two nodes must have the same label
        if (!targetGraph.getVertexLabel(targetVertex).equals(
                queryGraph.getVertexLabel(queryVertex))) {
            return false;
        }

        // Predecessor Rule and Successor Rule
        if (!checkNeighborVertexes(targetVertex, queryVertex)) {
            return false;
        }

        // In Rule and Out Rule
        if (!checkInAndOut(targetVertex, queryVertex)) {
            return false;
        }

        // New Rule
        if (!checkNew(targetVertex, queryVertex)) {
            return false;
        }

        return true;
    }

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
     * Check the in rule and out rule
     * This prunes the search tree using 1-look-ahead
     *
     * @param targetVertex Target Graph Node Index
     * @param queryVertex  Query Graph Node Index
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
     * Check the new rule
     * This prunes the search tree using 2-look-ahead
     *
     * @param targetVertex Target Graph Node Index
     * @param queryVertex  Query Graph Node Index
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

        if (num1 < num2) {
            return false;
        }
        return true;
    }

    public class State {

        private HashMap<String, String> core_1; // stores for each target graph node to which query graph node it maps ("-1" indicates no mapping)
        private HashMap<String, String> core_2; // stores for each query graph node to which target graph node it maps ("-1" indicates no mapping)

        private HashMap<String, Integer> inout_1; // stores for each target graph node the depth in the search tree at which it entered "T_1 out" or the mapping ("-1" indicates that the node is not part of the set)
        private HashMap<String, Integer> inout_2; // stores for each query graph node the depth in the search tree at which it entered "T_2 out" or the mapping ("-1" indicates that the node is not part of the set)

        private HashSet<String> T1inout;    // nodes that not yet in the partial mapping, that are the origin of branches end into target graph
        private HashSet<String> T2inout;    // nodes that not yet in the partial mapping, that are the origin of branches end into query graph

        private HashSet<String> unmapped1;    // unmapped nodes in target graph
        private HashSet<String> unmapped2;    // unmapped nodes in query graph

        private int depth = 0; // current depth of the search tree

        private boolean matched = false;

        private Graph targetGraph;
        private Graph queryGraph;

        /**
         * Initialize a State
         *
         * @param targetGraph The big graph
         * @param queryGraph  The small graph
         */
        public State(Graph targetGraph, Graph queryGraph) {

            this.targetGraph = targetGraph;
            this.queryGraph = queryGraph;

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

//            core_1 = new int[targetSize];
//            core_2 = new int[querySize];

//            inout_1 = new int[targetSize];
//            inout_2 = new int[querySize];

            // initialize values ("-1" means no mapping / not contained in the set)
            // initially, all sets are empty and no nodes are mapped
            for (int i = 0; i < targetSize; i++) {
//                core_1[i] = -1;
//                inout_1[i] = -1;
                unmapped1.add(String.valueOf(i));
            }
            for (int i = 0; i < querySize; i++) {
//                core_2[i] = -1;
//                inout_2[i] = -1;
                unmapped2.add(String.valueOf(i));
            }
        }

        public Boolean inM1(String vertex) {
//            return (core_1[nodeId] > -1);
            return core_1.containsKey(vertex);
        }

        public Boolean inM2(String vertex) {
//            return (core_2[nodeId] > -1);
            return core_2.containsKey(vertex);
        }

        public Boolean inT1inout(String vertex) {
//            return ((core_1[nodeId] == -1) && (inout_1[nodeId] > -1));
            return core_1.containsKey(vertex) && inout_1.containsKey(vertex);
        }


        public Boolean inT2inout(String vertex) {
//            return ((core_2[nodeId] == -1) && (inout_2[nodeId] > -1));
            return core_2.containsKey(vertex) && inout_2.containsKey(vertex);
        }
//
//        public Boolean inT1(String vertex) {
//            return this.inT1inout(nodeId);
//        }
//
//        public Boolean inT2(String vertex) {
//            return this.inT2inout(nodeId);
//        }
//
//        public Boolean inN1Tilde(String vertex) {
//            return (core_1[nodeId] == -1) && (inout_1[nodeId] == -1);
//        }
//
//        public Boolean inN2Tilde(String vertex) {
//            return (core_2[nodeId] == -1) && (inout_2[nodeId] == -1);
//        }

        /**
         * Add a new match (targetIndex, queryIndex) to the state
         *
         * @param targetVertex Index of the node in target graph
         * @param queryVertex  Index of the node in query graph
         */
        public void extendMatch(String targetVertex, String queryVertex) {

            core_1.put(targetVertex, queryVertex);
            core_2.put(queryVertex, targetVertex);
            unmapped1.remove(targetVertex);
            unmapped2.remove(queryVertex);
            T1inout.remove(targetVertex);
            T2inout.remove(queryVertex);

//            core_1[targetIndex] = queryIndex;
//            core_2[queryIndex] = targetIndex;
//            unmapped1.remove(targetIndex);
//            unmapped2.remove(queryIndex);
//            T1inout.remove(targetIndex);
//            T2inout.remove(queryIndex);

            depth++;    // move down one level in the search tree

            for (String neighborVertex : targetGraph.getNeighborVertexList(targetVertex)) {
                if (!inout_1.containsKey(neighborVertex)) {
//                    inout_1[neighborIdx] = depth;
                    inout_1.put(neighborVertex, depth);
                    if (!inM1(neighborVertex)) {
                        T1inout.add(neighborVertex);
                    }
                }
            }

            for (String neighborVertex : queryGraph.getNeighborVertexList(queryVertex)) {
                if (!inout_2.containsKey(neighborVertex)) {
//                    inout_2[neighborIdx] = depth;
                    inout_2.put(neighborVertex, depth);
                    if (!inM2(neighborVertex)) {
                        T2inout.add(neighborVertex);
                    }
                }
            }
        }

        /**
         * Remove the match of (targetVertex, queryVertex) for backtrack
         *
         * @param targetVertex
         * @param queryVertex
         */
        public void backtrack(String targetVertex, String queryVertex) {

            core_1.remove(targetVertex);
            core_2.remove(queryVertex);
//            core_1[targetVertex] = -1;
//            core_2[queryVertex] = -1;
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

//            for (int i = 0; i < core_1.length; i++) {
//                if (inout_1[i] == depth) {
//                    inout_1[i] = -1;
//                    T1inout.remove(i);
//                }
//            }
//            for (int i = 0; i < core_2.length; i++) {
//                if (inout_2[i] == depth) {
//                    inout_2[i] = -1;
//                    T2inout.remove(i);
//                }
//            }

            // put targetVertex and queryVertex back into Tin and Tout sets if necessary
            if (inT1inout(targetVertex))
                T1inout.add(targetVertex);
            if (inT2inout(queryVertex))
                T2inout.add(queryVertex);

            depth--;
        }
    }
}
