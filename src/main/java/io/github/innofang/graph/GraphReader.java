package io.github.innofang.graph;

import io.github.innofang.bean.Graph;
import io.github.innofang.graph.datasets.DataSetStrategy;

import java.io.*;
import java.util.List;

public class GraphReader {

    private DataSetStrategy dataSetStrategy;

    public void setDataSetStrategy(DataSetStrategy dataSetStrategy) {
        this.dataSetStrategy = dataSetStrategy;
    }

    public List<Graph> read(String filePath) throws IOException {
        assert dataSetStrategy != null :
                "Please specify the test set loading strategy before loading the test set you want.";
        return dataSetStrategy.load(filePath);
    }

}
