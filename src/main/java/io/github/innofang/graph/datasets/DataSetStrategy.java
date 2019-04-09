package io.github.innofang.graph.datasets;

import io.github.innofang.bean.Graph;

import java.io.IOException;
import java.util.List;

/**
 * Strategy Pattern
 *
 * which test set do you want to load?
 *
 * Base upon the format of the test set to implement the specify load method
 */
public interface DataSetStrategy {
    /**
     * Input a Graph path, then return a list of Graph based on the format the Graph file,
     * or throw an IOException if not match the regular of the load method
     * @param filePath Graph File
     * @return a list of Graph
     * @throws IOException
     */
    List<Graph> load(String filePath) throws IOException;
}


