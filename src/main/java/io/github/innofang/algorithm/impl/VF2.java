package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * VF2 for undirected graph matcher
 */

public class VF2 implements IsomorphismAlgorithm {

    private State state;

    private void initialize(Graph targetGraph, Graph queryGraph) {
        state = new State(targetGraph, queryGraph);
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
            for (int i = 0; i < state.core_2.length; ++i) {
                mapping.put(String.valueOf(i), String.valueOf(state.core_2[i]));
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
            List<Pair<Integer, Integer>> candidatePairs = generateCandidatePairs(state, targetGraph, queryGraph);
            for (Pair<Integer, Integer> entry : candidatePairs) {
                if (checkSyntacticFeasibility(state, entry.getKey(), entry.getValue())) {
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

    private List<Pair<Integer, Integer>> generateCandidatePairs(State state, Graph targetGraph, Graph queryGraph) {
        List<Pair<Integer, Integer>> pairList = new ArrayList<>();

        if (!state.T1inout.isEmpty() && !state.T1inout.isEmpty()) {
            // Generate candidates from T1out and T2out if they are not empty

            // Faster Version
            // Since every node should be matched in query graph
            // Therefore we can only extend one node of query graph (with biggest id)
            // instead of generate the whole Cartesian product of the target and query
            int queryNodeIndex = -1;
            for (int i : state.T2inout) {
                queryNodeIndex = Math.max(i, queryNodeIndex);
            }
            for (int i : state.T1inout) {
                pairList.add(new Pair<>(i, queryNodeIndex));
            }

            // Slow Version
//			for (int i : state.T1out){
//				for (int j : state.T2out){
//					pairList.add(new Pair<Integer,Integer>(i, j));
//				}
//			}
            return pairList;
        } else {
            // Generate from all unmapped nodes

            // Faster Version
            // Since every node should be matched in query graph
            // Therefore we can only extend one node of query graph (with biggest id)
            // instead of generate the whole Cartesian product of the target and query
            int queryNodeIndex = -1;
            for (int i : state.unmapped2) {
                queryNodeIndex = Math.max(i, queryNodeIndex);
            }
            for (int i : state.unmapped1) {
                pairList.add(new Pair<>(i, queryNodeIndex));
            }

            // Slow Version
//			for (int i : state.unmapped1){
//				for (int j : state.unmapped2){
//					pairList.add(new Pair<Integer,Integer>(i, j));
//				}
//			}
            return pairList;
        }
    }

    /**
     * Check the feasibility of adding this match
     *
     * @param state             VF2 State
     * @param targetVertexIndex Target Graph Node Index
     * @param queryVertexIndex  Query Graph Node Index
     * @return Feasible or not
     */
    private Boolean checkSyntacticFeasibility(State state, int targetVertexIndex, int queryVertexIndex) {
        // Node Label Rule
        // The two nodes must have the same label
        if (!state.targetGraph.getVertexLabel(targetVertexIndex + "").equals(
                state.queryGraph.getVertexLabel(queryVertexIndex + ""))) {
            return false;
        }

        // Predecessor Rule and Successor Rule
        if (!checkNeighborVertexes(state, targetVertexIndex, queryVertexIndex)) {
            return false;
        }

        // In Rule and Out Rule
        if (!checkInAndOut(state, targetVertexIndex, queryVertexIndex)) {
            return false;
        }

        // New Rule
        if (!checkNew(state, targetVertexIndex, queryVertexIndex)) {
            return false;
        }

        return true;
    }

    private boolean checkNeighborVertexes(State state, int targetVertexIndex, int queryVertexIndex) {
        for (String neighbor : state.targetGraph.getNeighborVertexList(targetVertexIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (state.inM1(neighborIdx)) {
                if (!state.queryGraph.getNeighborVertexList(queryVertexIndex + "").contains(state.core_1[neighborIdx] + "")) {
                    return false;
                }
                if (!state.targetGraph.getEdgeLabel(neighbor, targetVertexIndex + "").equals(
                        state.targetGraph.getEdgeLabel(state.core_1[neighborIdx] + "", queryVertexIndex + ""))) {
                    return false;
                }
            }
        }

        for (String neighbor : state.queryGraph.getNeighborVertexList(queryVertexIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (state.inM2(neighborIdx)) {
                if (!state.targetGraph.getNeighborVertexList(targetVertexIndex + "").contains(state.core_2[neighborIdx] + "")) {
                    return false;
                }
                if (!state.targetGraph.getEdgeLabel(state.core_2[neighborIdx] + "", targetVertexIndex + "").equals(
                        state.targetGraph.getEdgeLabel(neighbor, queryVertexIndex + ""))) {
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
     * @param state           VF2 State
     * @param targetNodeIndex Target Graph Node Index
     * @param queryNodeIndex  Query Graph Node Index
     * @return Feasible or not
     */
    private boolean checkInAndOut(State state, int targetNodeIndex, int queryNodeIndex) {
        int num1 = 0;
        for (String neighbor : state.targetGraph.getNeighborVertexList(targetNodeIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (state.inT1inout(neighborIdx) && !state.inM1(neighborIdx)) {
                ++num1;
            }
        }

        int num2 = 0;
        for (String neighbor : state.queryGraph.getNeighborVertexList(queryNodeIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (state.inT2inout(neighborIdx) && !state.inM2(neighborIdx)) {
                ++num2;
            }
        }

        // false if target < query
        if (num1 < num2) {
            return false;
        }
        return true;
    }

    /**
     * Check the new rule
     * This prunes the search tree using 2-look-ahead
     *
     * @param state           VF2 State
     * @param targetNodeIndex Target Graph Node Index
     * @param queryNodeIndex  Query Graph Node Index
     * @return Feasible or not
     */
    private boolean checkNew(State state, int targetNodeIndex, int queryNodeIndex) {
        int num1 = 0;
        for (String neighbor : state.targetGraph.getNeighborVertexList(targetNodeIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (!state.inT1inout(neighborIdx)) {
                ++num1;
            }
        }

        int num2 = 0;
        for (String neighbor : state.queryGraph.getNeighborVertexList(queryNodeIndex + "")) {
            int neighborIdx = Integer.parseInt(neighbor);
            if (!state.inT2inout(neighborIdx)) {
                ++num2;
            }
        }

        if (num1 < num2) {
            return false;
        }
        return true;
    }

    public class State {

        public int[] core_1; // stores for each target graph node to which query graph node it maps ("-1" indicates no mapping)
        public int[] core_2; // stores for each query graph node to which target graph node it maps ("-1" indicates no mapping)

        public int[] inout_1; // stores for each target graph node the depth in the search tree at which it entered "T_1 out" or the mapping ("-1" indicates that the node is not part of the set)
        public int[] inout_2; // stores for each query graph node the depth in the search tree at which it entered "T_2 out" or the mapping ("-1" indicates that the node is not part of the set)

        public HashSet<Integer> T1inout;    // nodes that not yet in the partial mapping, that are the origin of branches end into target graph
        public HashSet<Integer> T2inout;    // nodes that not yet in the partial mapping, that are the origin of branches end into query graph

        public HashSet<Integer> unmapped1;    // unmapped nodes in target graph
        public HashSet<Integer> unmapped2;    // unmapped nodes in query graph

        public int depth = 0; // current depth of the search tree

        public boolean matched = false;

        public Graph targetGraph;
        public Graph queryGraph;

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

            core_1 = new int[targetSize];
            core_2 = new int[querySize];

            inout_1 = new int[targetSize];
            inout_2 = new int[querySize];

            // initialize values ("-1" means no mapping / not contained in the set)
            // initially, all sets are empty and no nodes are mapped
            for (int i = 0; i < targetSize; i++) {
                core_1[i] = -1;
                inout_1[i] = -1;
                unmapped1.add(i);
            }
            for (int i = 0; i < querySize; i++) {
                core_2[i] = -1;
                inout_2[i] = -1;
                unmapped2.add(i);
            }
        }

        public Boolean inM1(int nodeId) {
            return (core_1[nodeId] > -1);
        }

        public Boolean inM2(int nodeId) {
            return (core_2[nodeId] > -1);
        }

        public Boolean inT1inout(int nodeId) {
            return ((core_1[nodeId] == -1) && (inout_1[nodeId] > -1));
        }


        public Boolean inT2inout(int nodeId) {
            return ((core_2[nodeId] == -1) && (inout_2[nodeId] > -1));
        }

        public Boolean inT1(int nodeId) {
            return this.inT1inout(nodeId);
        }

        public Boolean inT2(int nodeId) {
            return this.inT2inout(nodeId);
        }

        public Boolean inN1Tilde(int nodeId) {
            return (core_1[nodeId] == -1) && (inout_1[nodeId] == -1);
        }

        public Boolean inN2Tilde(int nodeId) {
            return (core_2[nodeId] == -1) && (inout_2[nodeId] == -1);
        }

        /**
         * Add a new match (targetIndex, queryIndex) to the state
         *
         * @param targetIndex Index of the node in target graph
         * @param queryIndex  Index of the node in query graph
         */
        public void extendMatch(int targetIndex, int queryIndex) {

            core_1[targetIndex] = queryIndex;
            core_2[queryIndex] = targetIndex;
            unmapped1.remove(targetIndex);
            unmapped2.remove(queryIndex);
            T1inout.remove(targetIndex);
            T2inout.remove(queryIndex);

            depth++;    // move down one level in the search tree

            Vertex targetVertex = targetGraph.getVertex(targetIndex);
            Vertex queryVertex = queryGraph.getVertex(queryIndex);

            for (String neighbor : targetGraph.getNeighborVertexList(targetVertex + "")) {
                int neighborIdx = Integer.parseInt(neighbor);
                if (inout_1[neighborIdx] == -1) {
                    inout_1[neighborIdx] = depth;
                    if (!inM1(neighborIdx)) {
                        T1inout.add(neighborIdx);
                    }
                }
            }

            for (String neighbor : queryGraph.getNeighborVertexList(queryVertex + "")) {
                int neighborIdx = Integer.parseInt(neighbor);
                if (inout_2[neighborIdx] == -1) {
                    inout_2[neighborIdx] = depth;
                    if (!inM2(neighborIdx)) {
                        T2inout.add(neighborIdx);
                    }
                }
            }
        }

        /**
         * Remove the match of (targetVertexIndex, queryVertexIndex) for backtrack
         *
         * @param targetVertexIndex
         * @param queryVertexIndex
         */
        public void backtrack(int targetVertexIndex, int queryVertexIndex) {

            core_1[targetVertexIndex] = -1;
            core_2[queryVertexIndex] = -1;
            unmapped1.add(targetVertexIndex);
            unmapped2.add(queryVertexIndex);

            for (int i = 0; i < core_1.length; i++) {
                if (inout_1[i] == depth) {
                    inout_1[i] = -1;
                    T1inout.remove(i);
                }
            }
            for (int i = 0; i < core_2.length; i++) {
                if (inout_2[i] == depth) {
                    inout_2[i] = -1;
                    T2inout.remove(i);
                }
            }

            // put targetVertexIndex and queryVertexIndex back into Tin and Tout sets if necessary
            if (inT1inout(targetVertexIndex))
                T1inout.add(targetVertexIndex);
            if (inT2inout(queryVertexIndex))
                T2inout.add(queryVertexIndex);

            depth--;
        }
    }
}
