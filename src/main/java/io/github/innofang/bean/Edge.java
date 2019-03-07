package io.github.innofang.bean;

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

public class Edge {

    private String vertexI;
    private String vertextJ;
    private String label;

    public String getVertexI() {
        return vertexI;
    }

    public void setVertexI(String vertexI) {
        this.vertexI = vertexI;
    }

    public String getVertextJ() {
        return vertextJ;
    }

    public void setVertextJ(String vertextJ) {
        this.vertextJ = vertextJ;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean contain(String vertex) {
        return vertexI.equals(vertex) || vertextJ.equals(vertex);
    }
}
