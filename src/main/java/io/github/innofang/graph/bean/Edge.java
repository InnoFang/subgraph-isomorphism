package io.github.innofang.graph.bean;

/**
 *
 * graph Data Template:
 * ================
 * t # n    // nth graph
 * v i j    // the i is the vertex number, and the j is the vertex label
 * e i j k  // edge <i, j> which label is k
 *
 * graph Data Example:
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

public class Edge {

    private String vertexI;
    private String vertexJ;
    private String label;

    public Edge() {}

    public Edge(String vertexI, String vertexJ, String label) {
        this.vertexI = vertexI;
        this.vertexJ = vertexJ;
        this.label = label;
    }

    public String getVertexI() {
        return vertexI;
    }

    public void setVertexI(String vertexI) {
        this.vertexI = vertexI;
    }

    public String getVertexJ() {
        return vertexJ;
    }

    public void setVertexJ(String vertexJ) {
        this.vertexJ = vertexJ;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean contain(String vertex) {
        return vertexI.equals(vertex) || vertexJ.equals(vertex);
    }

    public String getAdjacent(String vertex) {
        if (vertex.equals(vertexI)) return vertexJ;
        if (vertex.equals(vertexJ)) return vertexI;
        return null;
    }
}
