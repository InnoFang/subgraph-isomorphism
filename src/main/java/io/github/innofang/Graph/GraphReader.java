package io.github.innofang.Graph;

import io.github.innofang.Graph.bean.Graph;
import io.github.innofang.Graph.datasets.DataSetStrategy;

import java.io.*;
import java.util.List;

/**
 *
 * Graph Data Template:
 * ================
 * t # n    // nth graph
 * v i j    // the i is the vertex number, and the j is the vertex label
 * e i j k  // edge <i, j> which label is k
 *
 * Graph Data Example:
 * ================
 * t # 1
 * v 0 2
 * v 1 2
 * v 2 6
 * v 3 6
 * e 0 1 2
 * e 0 2 2
 * e 1 3 2
 * e 2 4 3
 *
 */

public class GraphReader {

    private DataSetStrategy dataSetStrategy;

    public void setDataSetStrategy(DataSetStrategy dataSetStrategy) {
        this.dataSetStrategy = dataSetStrategy;
    }

    public List<Graph> read(String filePath) throws IOException {
        assert dataSetStrategy != null :
                "Please specify the data set loading strategy before loading the data set you want.";
        return dataSetStrategy.load(filePath);
    }

}
