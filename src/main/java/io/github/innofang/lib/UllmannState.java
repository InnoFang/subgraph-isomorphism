package io.github.innofang.lib;

import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Pair;
import io.github.innofang.bean.Vertex;
import io.github.innofang.util.MatrixOperator;

import java.util.HashMap;
import java.util.List;

public class UllmannState extends State {

    private Graph sourceGraph;
    private Graph targetGraph;
    private List<Vertex> sourceVertexList;
    private List<Vertex> targetVertexList;

    private int[][] M;

    private HashMap<Integer, Integer> mapping;

    public UllmannState(Graph sourceGraph, Graph targetGraph) {
        super(sourceGraph, targetGraph);
        this.targetGraph = targetGraph;
        this.sourceGraph = sourceGraph;
        this.sourceVertexList = sourceGraph.getVertexList();
        this.targetVertexList = targetGraph.getVertexList();
        this.mapping = new HashMap<>();

        M = generateMatrixM();
    }

    public UllmannState(UllmannState state) {
        super(state);
        this.targetGraph = state.targetGraph;
        this.sourceGraph = state.sourceGraph;
        this.sourceVertexList = state.sourceVertexList;
        this.targetVertexList = state.targetVertexList;
        this.mapping = new HashMap<>(state.mapping);

        this.M = new int[state.M.length][state.M[0].length];
        for (int i = 0; i < state.M.length; i++) {
            System.arraycopy(state.M[i], 0, this.M[i], 0, state.M[i].length);
        }
    }

    /**
     * construct Graph_source x Graph_target element matrix M in according with:
     * <p>
     * Mij = 1 if the degree of the jth point of Graph_target is greater than or
     *       equal to the degree of the ith point of Graph_source
     *     = 0 otherwise
     *
     * @return M
     */
    private int[][] generateMatrixM() {
        int row = sourceVertexList.size();
        int col = targetVertexList.size();
        int[][] M = new int[row][col];

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                M[i][j] = (sourceGraph.getVertexInDegree(i) <= targetGraph.getVertexInDegree(j) &&
                        sourceGraph.getVertexOutDegree(i) <= targetGraph.getVertexOutDegree(j))
                        ? 1 : 0;
            }
        }

        return M;
    }

    @Override
    public Graph getSourceGraph() {
        return sourceGraph;
    }

    @Override
    public Graph getTargetGraph() {
        return targetGraph;
    }

    @Override
    public boolean isFeasiblePair(Pair<Integer, Integer> pair) {
        int sourceVertex = pair.getKey();
        int targetVertex = pair.getValue();
        assert sourceVertex < sourceVertexList.size();
        assert targetVertex < targetVertexList.size();

        // Whether to think about the vertex label is a question

        String sourceVertexLabel = sourceGraph.getVertexLabel(sourceVertex);
        String targetVertexLabel = targetGraph.getVertexLabel(targetVertex);

        return sourceVertexLabel.equals(targetVertexLabel) && M[sourceVertex][targetVertex] != 0;
    }

    @Override
    public void addPair(Pair<Integer, Integer> pair) {
        int sourceVertex = pair.getKey();
        int targetVertex = pair.getValue();
        assert sourceVertex < sourceVertexList.size();
        assert targetVertex < targetVertexList.size();

        mapping.put(sourceVertex, targetVertex);

        int len = mapping.size();
        for (int i = len; i < sourceVertexList.size(); i++) {
            M[i][targetVertex] = 0;
        }
        refine();

    }

    /**
     * for any x, (MA[i][x] == 1) ===> exist y s.t. (M[x][y] == 1 and MB[y][j] == 1).
     * or M[i][j] = 0
     *
     * @return
     */
    private void refine() {
        int row = sourceVertexList.size();
        int col = targetVertexList.size();
        int len = mapping.size();

        for (int i = len; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (M[i][j] != 0) {
                    for (int k = len - 1; k < len; ++k) {
                        Integer l = mapping.get(k);
                        assert l != null;

                        String edgeIK = sourceGraph.getEdgeLabel(i, k);
                        boolean existIK = edgeIK != null;

                        String edgeKI = sourceGraph.getEdgeLabel(k, i);
                        boolean existKI = edgeKI != null;

                        String edgeJL = targetGraph.getEdgeLabel(j, l);
                        boolean existJL = edgeJL != null;

                        String edgeLJ = targetGraph.getEdgeLabel(l, j);
                        boolean existLJ = edgeLJ != null;

                        if (existIK != existJL || existKI != existLJ) {
                            M[i][j] = 0;
                            break;
                        } else if (edgeIK != null && !edgeIK.equals(edgeJL)) {
                            M[i][j] = 0;
                            break;
                        } else if (edgeKI != null && !edgeKI.equals(edgeLJ)) {
                            M[i][j] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isSuccess() {
        return mapping.size() == sourceVertexList.size();
    }

    @Override
    public boolean isFailure() {
        int sourceSize = sourceVertexList.size();
        int targetSize = targetVertexList.size();
        int mappingSize = mapping.size();
        if (sourceSize > targetSize) {
            return true;
        }

        boolean flag = false;
        for (int i = mappingSize; i < sourceSize; ++i) {
            for (int j = 0; j < targetSize; ++j) {
                if (M[i][j] != 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HashMap<Integer, Integer> getMapping() {
        return mapping;
    }

    @Override
    public void backTrack(Pair<Integer, Integer> pair) {

    }

    @Override
    public State clone() {
        return new UllmannState(this);
    }

    @Override
    public PairIterator iterator() {
        return new UllmannStatePairIterator(mapping.size(), sourceVertexList.size(), targetVertexList.size());
    }

    public class UllmannStatePairIterator implements PairIterator {

        private int sourceIndex = -1;    // the index along the direction of the row of matrix M
        private int targetIndex = -1;    // the index along the direction of the column of matrix M

        private int mappingSize;
        private int sourceVertexSize;
        private int targetVertexSize;

        public UllmannStatePairIterator(int mappingSize, int sourceVertexSize, int targetVertexSize) {
            this.mappingSize = mappingSize;
            this.sourceVertexSize = sourceVertexSize;
            this.targetVertexSize = targetVertexSize;
        }

        @Override
        public boolean hasNextPair() {
            if (sourceIndex == -1) {
                sourceIndex = mappingSize;
                targetIndex = 0;
            } else if (targetIndex == -1) {
                targetIndex = 0;
            } else {
                ++targetIndex;
            }

            if (targetIndex >= targetVertexSize) {
                ++sourceIndex;
                targetIndex = 0;
            }

            if (sourceIndex != mappingSize) {
                return false;
            }

            while (targetIndex < targetVertexSize && M[sourceIndex][targetIndex] == 0) {
                ++ targetIndex;
            }

            return targetIndex < targetVertexSize;
        }

        @Override
        public Pair<Integer, Integer> nextPair() {
            return new Pair<>(sourceIndex, targetIndex);
        }
    }
}
