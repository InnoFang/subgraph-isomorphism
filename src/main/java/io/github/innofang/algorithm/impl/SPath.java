package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Graph;

import java.util.HashMap;

public class SPath implements IsomorphismAlgorithm {

    private Graph targetGraph;
    private Graph queryGraph;

    private void initialize(Graph targetGraph, Graph queryGraph) {
        this.targetGraph = targetGraph;
        this.queryGraph = queryGraph;
    }

    @Override
    public boolean isSubGraphIsomorphism(Graph targetGraph, Graph queryGraph) {
        initialize(targetGraph, queryGraph);
        return false;
    }



    @Override
    public HashMap<String, String> getMapping() {
        return null;
    }
}
