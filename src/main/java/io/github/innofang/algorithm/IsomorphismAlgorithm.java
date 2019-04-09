package io.github.innofang.algorithm;

import io.github.innofang.bean.Graph;

import java.util.HashMap;

public interface IsomorphismAlgorithm {


    boolean isSubGraphIsomorphism(Graph targetGraph, Graph queryGraph);

    HashMap<String, String> getMapping() ;
}
