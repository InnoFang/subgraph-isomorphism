package io.github.innofang.Graph.datasets;

import io.github.innofang.Graph.bean.Graph;

import java.io.IOException;
import java.util.List;

/**
 * Strategy Pattern
 *
 * which data set do you want to load?
 *
 * Base upon the format of the data set to implement the specify load method
 */
public interface DataSetStrategy {
    List<Graph> load(String filePath) throws IOException;
}
