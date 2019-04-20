package io.github.innofang.lib;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Pair;
import io.github.innofang.bean.Vertex;

import java.util.*;

@SuppressWarnings("Duplicates")
public class VF2State extends State {

    private Graph sourceGraph;
    private Graph targetGraph;
    private Vertex[] sourceVertexList;
    private Vertex[] targetVertexList;

    private int sourceVertexSize;
    private int targetVertexSize;

    private int[] core_1;
    private int[] core_2;
    private int[] in_1;
    private int[] in_2;
    private int[] out_1;
    private int[] out_2;

    private HashSet<Integer> t1in;
    private HashSet<Integer> t1out;
    private HashSet<Integer> t2in;
    private HashSet<Integer> t2out;
    private HashSet<Integer> unmapped1;
    private HashSet<Integer> unmapped2;

    private HashMap<Integer, Integer> mapping;

    public VF2State(Graph sourceGraph, Graph targetGraph) {
        super(sourceGraph, targetGraph);
        this.targetGraph = targetGraph;
        this.sourceGraph = sourceGraph;
        this.sourceVertexList = sourceGraph.getVertexArray();
        this.targetVertexList = targetGraph.getVertexArray();
        this.sourceVertexSize = this.sourceVertexList.length;
        this.targetVertexSize = this.targetVertexList.length;
        this.mapping = new HashMap<>();

        this.core_1 = new int[sourceVertexSize];
        this.core_2 = new int[targetVertexSize];
        this.in_1 = new int[sourceVertexSize];
        this.in_2 = new int[targetVertexSize];
        this.out_1 = new int[sourceVertexSize];
        this.out_2 = new int[targetVertexSize];

        this.t1in = new HashSet<>(sourceVertexSize * 2);
        this.t1out = new HashSet<>(sourceVertexSize * 2);
        this.t2in = new HashSet<>(targetVertexSize * 2);
        this.t2out = new HashSet<>(targetVertexSize * 2);

        this.unmapped1 = new HashSet<>(sourceVertexSize * 2);
        this.unmapped2 = new HashSet<>(targetVertexSize * 2);

        for (int i = 0; i < sourceVertexSize; i++) {
            core_1[i] = -1;
            in_1[i] = -1;
            out_1[i] = -1;
            unmapped1.add(i);
        }

        for (int i = 0; i < targetVertexSize; i++) {
            core_2[i] = -1;
            in_2[i] = -1;
            out_2[i] = -1;
            unmapped2.add(i);
        }
    }

    public VF2State(VF2State state) {
        super(state);
        this.targetGraph = state.targetGraph;
        this.sourceGraph = state.sourceGraph;
        this.sourceVertexList = state.sourceVertexList;
        this.targetVertexList = state.targetVertexList;
        this.sourceVertexSize = state.sourceVertexSize;
        this.targetVertexSize = state.targetVertexSize;

        this.mapping = new HashMap<>(state.mapping);

        this.core_1    = state.core_1   ;
        this.core_2    = state.core_2   ;
        this.in_1      = state.in_1     ;
        this.in_2      = state.in_2     ;
        this.out_1     = state.out_1    ;
        this.out_2     = state.out_2    ;
        this.t1in      = state.t1in     ;
        this.t1out     = state.t1out    ;
        this.t2in      = state.t2in     ;
        this.t2out     = state.t2out    ;
        this.unmapped1 = state.unmapped1;
        this.unmapped2 = state.unmapped2;
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

        // Vertex Label Rule
        // The two vertex must have the same label
        if (!sourceVertexList[sourceVertex].getLabel()
                .equals(targetVertexList[targetVertex].getLabel())) {
            return false;
        }

        int termout1 = 0, termout2 = 0,
            termin1  = 0, termin2  = 0,
            new1     = 0, new2     = 0;

        // Check the 'out' edges of sourceVertex
        List<Edge> sourceOutEdges = sourceGraph.getOutEdges(sourceVertex);
        if (sourceOutEdges != null) {
            for (Edge outEdge : sourceOutEdges) {
                int other1 = outEdge.getVertexTo();
                if (core_1[other1] != -1) {
                    int other2 = core_1[other1];
                    String targetEdgeLabel = targetGraph.getEdgeLabel(targetVertex, other2);
                    if (!outEdge.getLabel().equals(targetEdgeLabel)) {
                        return false;
                    }
                } else {
                    if (in_1[other1] != -1) {
                        ++ termin1;
                    }
                    if (out_1[other1] != -1) {
                        ++ termout1;
                    }
                    if (in_1[other1] == -1 && out_1[other1] == -1) {
                        ++ new1;
                    }
                }
            }
        }

        // Check the 'in' edges of sourceVertex
        List<Edge> sourceInEdges = sourceGraph.getInEdges(sourceVertex);
        if (sourceInEdges != null) {
            for (Edge inEdge : sourceInEdges) {
                int other1 = inEdge.getVertexFrom();
                if (core_1[other1] != -1) {
                    int other2 = core_1[other1];
                    String targetEdgeLabel = targetGraph.getEdgeLabel(other2, targetVertex);
                    if (!inEdge.getLabel().equals(targetEdgeLabel)) {
                        return false;
                    }
                } else {
                    if (in_1[other1] != -1) {
                        ++ termin1;
                    }
                    if (out_1[other1] != -1) {
                        ++ termout1;
                    }
                    if (in_1[other1] == -1 && out_1[other1] == -1) {
                        ++ new1;
                    }
                }
            }
        }

        // Check the 'out' edges of targetVertex
        List<Edge> targetOutEdges = targetGraph.getOutEdges(targetVertex);
        if (targetOutEdges != null) {
            for (Edge outEdge : targetOutEdges) {
                int other2 = outEdge.getVertexTo();
                if (core_2[other2] != -1) {
                    int other1 = core_2[other2];
                    String sourceEdgeLabel = sourceGraph.getEdgeLabel(sourceVertex, other1);
                    if (!outEdge.getLabel().equals(sourceEdgeLabel)) {
                        return false;
                    }
                } else {
                    if (in_2[other2] != -1) {
                        ++ termin2;
                    }
                    if (out_2[other2] != -1) {
                        ++ termout2;
                    }
                    if (in_2[other2] == -1 && out_2[other2] == -1) {
                        ++ new2;
                    }
                }
            }
        }

        // Check the 'in' edges of targetVertex
        List<Edge> targetInEdges = targetGraph.getInEdges(targetVertex);
        if (targetInEdges != null) {
            for (Edge inEdge : targetInEdges) {
                int other2 = inEdge.getVertexFrom();
                if (core_2[other2] != -1) {
                    int other1 = core_2[other2];
                    String sourceEdgeLabel = sourceGraph.getEdgeLabel(other1, sourceVertex);
                    if (!inEdge.getLabel().equals(sourceEdgeLabel)) {
                        return false;
                    }
                } else {
                    if (in_2[other2] != -1) {
                        ++ termin2;
                    }
                    if (out_2[other2] != -1) {
                        ++ termout2;
                    }
                    if (in_2[other2] == -1 && out_2[other2] == -1) {
                        ++ new2;
                    }
                }
            }
        }
        return termin1 <= termin2 && termout1 <= termout2 && new1 <= new2;
    }

    @Override
    public void addPair(Pair<Integer, Integer> pair) {
        int sourceVertex = pair.getKey();
        int targetVertex = pair.getValue();

        mapping.put(sourceVertex, targetVertex);
        int depth = mapping.size();

        core_1[sourceVertex] = targetVertex;
        core_2[targetVertex] = sourceVertex;
        unmapped1.remove(sourceVertex);
        unmapped2.remove(targetVertex);
        t1in.remove(sourceVertex);
        t1out.remove(sourceVertex);
        t2in.remove(targetVertex);
        t2out.remove(targetVertex);

        List<Edge> sourceInEdges = sourceGraph.getInEdges(sourceVertex);
        List<Edge> sourceOutEdges = sourceGraph.getOutEdges(sourceVertex);
        List<Edge> targetInEdges = targetGraph.getInEdges(targetVertex);
        List<Edge> targetOutEdges = targetGraph.getOutEdges(targetVertex);

        if (sourceInEdges != null) {
            for (Edge inEdge : sourceInEdges) {
                int from = inEdge.getVertexFrom();
                if (in_1[from] == -1) {
                    in_1[from] = depth;
                    if (core_1[from] == -1) {
                        t1in.add(from);
                    }
                }
            }
        }

        if (sourceOutEdges != null) {
            for (Edge outEdge : sourceOutEdges) {
                int to = outEdge.getVertexTo();
                if (out_1[to] == -1) {
                    out_1[to] = depth;
                    if (core_1[to] == -1) {
                        t1out.add(to);
                    }
                }
            }
        }

        if (targetInEdges != null) {
            for (Edge inEdge : targetInEdges) {
                int from = inEdge.getVertexFrom();
                if (in_2[from] == -1) {
                    in_2[from] = depth;
                    if (core_2[from] == -1) {
                        t2in.add(from);
                    }
                }
            }
        }

        if (targetOutEdges != null) {
            for (Edge outEdge : targetOutEdges) {
                int to = outEdge.getVertexTo();
                if (out_2[to] == -1) {
                    out_2[to] = depth;
                    if (core_2[to] == -1) {
                        t2out.add(to);
                    }
                }
            }
        }
    }

    @Override
    public boolean isSuccess() {
        return mapping.size() == sourceVertexSize;
    }

    @Override
    public boolean isFailure() {
        return sourceVertexSize > targetVertexSize;
    }

    @Override
    public HashMap<Integer, Integer> getMapping() {
        return mapping;
    }

    @Override
    public void backTrack(Pair<Integer, Integer> pair) {
        int sourceVertex = pair.getKey();
        int targetVertex = pair.getValue();

        int depth = mapping.size();

        core_1[sourceVertex] = -1;
        core_2[targetVertex] = -1;
        unmapped1.add(sourceVertex);
        unmapped2.add(targetVertex);

        for (int i = 0; i < core_1.length; i++) {
            if (in_1[i] == depth) {
                in_1[i] = -1;
                t1in.remove(i);
            }
            if (out_1[i] == depth) {
                out_1[i] = -1;
                t1out.remove(i);
            }
        }

        for (int i = 0; i < core_2.length; i++) {
            if (in_2[i] == depth) {
                in_2[i] = -1;
                t2in.remove(i);
            }
            if (out_2[i] == depth) {
                out_2[i] = -1;
                t2out.remove(i);
            }
        }

        if (core_1[sourceVertex] == -1 && in_1[sourceVertex] != -1) {
            t1in.add(sourceVertex);
        }
        if (core_1[sourceVertex] == -1 && out_1[sourceVertex] != -1) {
            t1out.add(sourceVertex);
        }
        if (core_2[targetVertex] == -1 && in_2[targetVertex] != -1) {
            t2in.add(targetVertex);
        }
        if (core_2[targetVertex] == -1 && out_2[targetVertex] != -1) {
            t2out.add(targetVertex);
        }

        mapping.remove(sourceVertex, targetVertex);
    }

    @Override
    public State clone() {
        return new VF2State(this);
    }

    @Override
    public PairIterator iterator() {
        return new VF2StatePairIterator();
    }

    public class VF2StatePairIterator implements PairIterator {

        private final Iterator<Pair<Integer, Integer>> iterator;
        private ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();

        public VF2StatePairIterator() {
            if (!t1out.isEmpty() && !t2out.isEmpty()) {
                int vertex1 = Collections.min(t1out);
                for (Integer vertex2 : t2out) {
                    pairs.add(new Pair<>(vertex1, vertex2));
                }
            } else if (!t1in.isEmpty() && !t2in.isEmpty()) {
                int vertex1 = Collections.min(t1in);
                for (Integer vertex2 : t2in) {
                    pairs.add(new Pair<>(vertex1, vertex2));
                }
            } else {
                int unmappedVertex1 = Collections.min(unmapped1);
                for (Integer unmappedVertex2 : unmapped2) {
                    pairs.add(new Pair<>(unmappedVertex1, unmappedVertex2));
                }
            }
            iterator = pairs.iterator();
        }

        @Override
        public boolean hasNextPair() {
            return iterator.hasNext();
        }

        @Override
        public Pair<Integer, Integer> nextPair() {
            return iterator.next();
        }
    }
}
