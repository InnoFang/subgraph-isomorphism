package io.github.innofang.algorithms.impl;

import io.github.innofang.algorithms.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.util.Pair;

import java.util.HashSet;

public class VF2 implements IsomorphismAlgorithm {

    @Override
    public boolean match(Graph targetGraph, Graph queryGraph) {
        return false;
    }

    @Override
    public HashSet<Pair<Integer, Integer>> getMapping() {
        return null;
    }
}
