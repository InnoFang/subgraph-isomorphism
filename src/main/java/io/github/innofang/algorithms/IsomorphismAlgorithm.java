package io.github.innofang.algorithms;

import io.github.innofang.graph.bean.Graph;
import io.github.innofang.util.Pair;

import java.util.HashSet;

public interface IsomorphismAlgorithm {


    boolean match(Graph targetGraph, Graph queryGraph);

    HashSet<Pair<Integer, Integer>> getMapping() ;
}
