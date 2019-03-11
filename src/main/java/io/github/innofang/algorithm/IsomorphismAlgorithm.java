package io.github.innofang.algorithm;

import io.github.innofang.graph.bean.Graph;

import java.util.HashMap;

public interface IsomorphismAlgorithm {


    boolean match(Graph targetGraph, Graph queryGraph);

    HashMap<Integer, Integer> getMapping() ;
}
