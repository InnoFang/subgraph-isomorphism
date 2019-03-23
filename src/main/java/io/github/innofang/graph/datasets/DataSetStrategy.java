package io.github.innofang.graph.datasets;

import io.github.innofang.graph.bean.Graph;

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
    List<Graph> load(String filePath) throws IOException;
}
