package io.github.innofang.bean;

import java.util.Objects;

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

    private int vertexFrom;
    private int vertexTo;
    private String label  = "";  // default empty string

    public Edge() { }

    public Edge(int vertexI, int vertexJ) {
        this(vertexI, vertexJ, "");
    }

    public Edge(int vertexI, int vertexJ, String label) {
        this.vertexFrom = vertexI;
        this.vertexTo = vertexJ;
        this.label = label;
    }

    public int getVertexFrom() {
        return vertexFrom;
    }

    public void setVertexFrom(int vertexFrom) {
        this.vertexFrom = vertexFrom;
    }

    public int getVertexTo() {
        return vertexTo;
    }

    public void setVertexTo(int vertexTo) {
        this.vertexTo = vertexTo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean contain(int vertex) {
        return vertexFrom == vertex || vertexTo  == vertex;
    }

    public int getAdjacent(int vertex) {
        if (vertex == vertexFrom) return vertexTo;
        if (vertex == vertexTo) return vertexFrom;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Objects.equals(getVertexFrom(), edge.getVertexFrom()) &&
                Objects.equals(getVertexTo(), edge.getVertexTo()) &&
                Objects.equals(getLabel(), edge.getLabel());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getVertexFrom(), getVertexTo(), getLabel());
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertexFrom='" + vertexFrom + '\'' +
                ", vertexTo='" + vertexTo + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
