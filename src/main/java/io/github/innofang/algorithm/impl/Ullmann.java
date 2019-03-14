package io.github.innofang.algorithm.impl;

import io.github.innofang.algorithm.IsomorphismAlgorithm;
import io.github.innofang.graph.bean.Edge;
import io.github.innofang.graph.bean.Graph;
import io.github.innofang.graph.bean.Vertex;
import io.github.innofang.util.MatrixOperator;

import java.util.HashMap;
import java.util.List;

/**
 * Using Ullmann algorithm to implement subgraph isomorphism
 */

public class Ullmann implements IsomorphismAlgorithm {

    private Graph queryGraph;
    private Graph targetGraph;

    private List<Vertex> queryVertexList;
    private List<Vertex> targetVertexList;

    private List<Edge> queryEdgeList;
    private List<Edge> targetEdgeList;

    private int[][] MA; // for query graph
    private int[][] MB; // for target graph
    private int[][] M0;

    private HashMap<String, String> mapping;

    @Override
    public boolean isSubGraphIsomorphism(Graph targetGraph, Graph queryGraph) {
        initialize(targetGraph, queryGraph);
        return checkIsomorphism();
    }

    private void initialize(Graph targetGraph, Graph queryGraph) {
        this.targetGraph = targetGraph;
        this.queryGraph = queryGraph;

        targetVertexList = targetGraph.getVertexList();
        queryVertexList = queryGraph.getVertexList();

        targetEdgeList = targetGraph.getEdgeList();
        queryEdgeList = queryGraph.getEdgeList();

        MA = queryGraph.getAdjacencyMatrix();
        MB = targetGraph.getAdjacencyMatrix();
        M0 = getMatrixM(targetGraph, queryGraph);

        mapping = new HashMap<>();
    }

    @Override
    public HashMap<String, String> getMapping() {
        assert mapping != null : "Haven't isSubGraphIsomorphism yet.";
//        return mapping;
        // the result of mapping is wrong
        return new HashMap<>();
    }

    /**
     * construct Graph_query x Graph_large element matrix M0 in according with:
     * <p>
     * Mij = 1 if the degree of the jth point of Graph_large is greater than or
     * equal to the degree of the ith point of Graph_query
     * = 0 otherwise
     *
     * @param query  Query graph
     * @param target Large graph
     * @return M0
     */
    private int[][] getMatrixM(Graph target, Graph query) {
        int row = query.getVertexList().size();
        int col = target.getVertexList().size();
        int[][] M0 = new int[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                String vertexJ = target.getVertexList().get(j).getVertex();
                String vertexI = query.getVertexList().get(i).getVertex();
                int degreeJ = query.getVertexDegree(vertexJ);
                int degreeI = query.getVertexDegree(vertexI);
                if (degreeJ >= degreeI) {
                    M0[i][j] = 1;
                } else {
                    M0[i][j] = 0;
                }
            }
        }
        return M0;
    }


    /**
     * for any x, (MA[i][x] == 1) ===> exist y s.t. (M0[x][y] == 1 and MB[y][j] == 1).
     * or M0[i][j] = 0
     *
     * @return
     */
    private void refineM() {

        assert targetGraph != null && queryGraph != null && M0 != null :
                "Haven't initial parameter before executing refinement procedure.";

        int row = queryVertexList.size();
        int col = targetVertexList.size();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (M0[i][j] == 1) {
                    String labelI = queryVertexList.get(i).getLabel();
                    String labelJ = targetVertexList.get(j).getLabel();
                    if (labelI.equals(labelJ)) {
                        for (int x = 0; x < row; ++x) {
                            boolean match = false;
                            if (MA[i][x] == 1) {
                                // label of MA[i][x]
                                String labelIX = queryGraph.getEdgeLabel(
                                        String.valueOf(i),
                                        String.valueOf(x));
                                for (int y = 0; y < col; ++y) {
                                    if (M0[x][y] * MB[y][j] == 1) {
                                        // label of MB[y][j]
                                        String labelYJ = targetGraph.getEdgeLabel(
                                                String.valueOf(y),
                                                String.valueOf(j));
                                        if (labelIX.equals(labelYJ)) {
                                            match = true;
                                            break;
                                        }
                                    }
                                }
                                if (!match) {
                                    M0[i][j] = 0;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    M0[i][j] = 0;
                }
            }
        }
    }

    /**
     * check if (MA[i, j] = 1) ==> (M0[i, j] == 1) for any i, j
     *
     * @return
     */
    private boolean checkIsomorphism() {
        assert targetGraph != null && queryGraph != null && M0 != null :
                "Haven't initial parameter before checking isomorphism.";
        refineM();
        // check M0
        int row = queryVertexList.size();
        int col = targetVertexList.size();
        for (int i = 0; i < row; ++i) {
            int sumOfRow = 0;
            for (int j = 0; j < col; ++j) {
                sumOfRow += M0[i][j];
            }
            // if the sum of a row is 0, return false directly.
            if (sumOfRow == 0) {
                return false;
            }
        }

        int F[] = new int[col]; // F[i] = 1，表示第i列已选过
        int H[] = new int[row]; // H[i] = j，表示第i行选第j列

        int d = 0; // 第d行
        int k = 0; // 第k列

        int[][][] matrixList = new int[row][][]; // 记录每个d对应的M0矩阵

        for (int i = 0; i < F.length; i++) { // 初始化为-1
            F[i] = -1;
        }
        for (int i = 0; i < H.length; i++) { // 初始化为-1
            H[i] = -1;
        }

        while (true) {
            if (H[d] == -1) { // 第d行未选择，进行搜索
                k = 0;
                matrixList[d] = M0;
            } else { // 否则进行回溯
                k = H[d] + 1;
                F[H[d]] = -1;
                M0 = matrixList[d];
            }
            // 查找符合条件的列
            while (k < col) {
                if (M0[d][k] == 1 && F[k] == -1) {
                    break;
                }
                k++;
            }
            if (k == col) { // 第d行中找不到满足条件的列，回溯到上一层
                H[d] = -1;
                d--;
            } else {
                // 找到对应的列，设置M0，d+=1，进行下一列搜索
                for (int j = 0; j < col; j++) {
                    M0[d][j] = 0;
                }
                M0[d][k] = 1;
                H[d] = k;
                F[k] = 1;
                d++;
            }
            // 搜索结束，未找到同构的映射
            if (d == -1) {
                return false;
            }

            // 找到一个M0，验证是否符合条件
            if (d == row) {
                if (verify(MA, MB, M0)) {// 条件成立
                    return true;
                } else {// 回溯
                    d = row - 1;
                }
            }
        }
    }

    /**
     * MC = M0 · (M0 · MB)T
     *
     * @param MA
     * @param MB
     * @param M0
     * @return
     */
    private boolean verify(int[][] MA, int[][] MB, int[][] M0) {
        int[][] MC = MatrixOperator.set(M0).dot(
                MatrixOperator.set(M0).dot(MB).T().get()
        ).get();

        for (int i = 0; i < MA.length; ++i) {
            for (int j = 0; j < MA[0].length; ++j) {
                if (MA[i][j] == 1 && MA[i][j] != MC[i][j]) {
//                    mapping.clear();
                    return false;
                } else if (MA[i][j] == 1 && MA[i][j] == MC[i][j]) {
//                    mapping.put(queryGraph.getVertex(i).getVertex(), targetGraph.getVertex(j).getVertex());
                }
            }
        }
        return true;
    }
}
