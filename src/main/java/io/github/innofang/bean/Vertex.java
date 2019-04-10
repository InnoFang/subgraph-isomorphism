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

public class Vertex {
    private int vertex;
    private String label = "";  // default empty string

    public Vertex() {}

    public Vertex(int vertex) {
        this(vertex, "");
    }

    public Vertex(int vertex, String label) {
        this.vertex = vertex;
        this.label = label;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex1 = (Vertex) o;
        return Objects.equals(getVertex(), vertex1.getVertex()) &&
                Objects.equals(getLabel(), vertex1.getLabel());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getVertex(), getLabel());
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "vertex='" + vertex + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
