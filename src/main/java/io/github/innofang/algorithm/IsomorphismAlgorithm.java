package io.github.innofang.algorithm;

import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;

import java.util.HashMap;

public interface IsomorphismAlgorithm {


    boolean match(Graph targetGraph, Graph queryGraph);

    HashMap<String, String> getMapping() ;
}
