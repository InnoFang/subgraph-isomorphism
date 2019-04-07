package io.github.innofang.util;

import io.github.innofang.bean.Edge;
import io.github.innofang.bean.Graph;
import io.github.innofang.bean.Vertex;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class GraphReader {

    private static final String CA_GRQC_TXT = "CA-GrQc.txt";
    private static final String COLLEGE_MSG_TXT = "CollegeMsg.txt";
    private static final String EMAIL_EU_CORE_TXT = "email-Eu-core.txt";
    private static final String EMAIL_EU_CORE_DEPARTMENT_LABELS_TXT = "email-Eu-core-department-labels.txt";

    private Path path; // graph file path;
    FileSystem fs;

    public GraphReader(FileSystem fs, String filename) {
        this(fs, new Path(filename));
    }

    public GraphReader(FileSystem fs, Path path) {
        this.fs = fs;
        this.path = path;
    }

    public Graph loadGraph() throws IOException {
        assert path != null : "Path cannot be null, please create a GraphReader instance before use loadGraph()";
        switch (path.getName()) {
            case CA_GRQC_TXT:
                return loadGraphBy(new ReaderListener() {
                    @Override
                    public boolean onRead(String line, HashSet<Vertex> vertices, HashSet<Edge> edges) {
                        if (line.startsWith("#"))
                            return true;

                        String[] info = line.split("\\s+");
                        if (info.length == 2) {
                            vertices.add(new Vertex(info[0]));
                            vertices.add(new Vertex(info[1]));
                            edges.add(new Edge(info[0], info[1]));
                        } else {
                            System.err.println("Wrong Line: " + line);
                            return false;
                        }
                        return true;
                    }
                });
            case COLLEGE_MSG_TXT:
                return loadGraphBy(new ReaderListener() {
                    @Override
                    public boolean onRead(String line, HashSet<Vertex> vertices, HashSet<Edge> edges) {
                        String[] info = line.split("\\s+");
                        if (info.length == 3) {
                            vertices.add(new Vertex(info[0]));
                            vertices.add(new Vertex(info[1]));
                            edges.add(new Edge(info[0], info[1], info[2]));
                        } else {
                            System.err.println("Wrong Line: " + line);
                            return false;
                        }
                        return true;
                    }
                });
            case EMAIL_EU_CORE_DEPARTMENT_LABELS_TXT:
            case EMAIL_EU_CORE_TXT:
                return loadGraphBy(new ReaderListener() {
                    @Override
                    public boolean onRead(String line, HashSet<Vertex> vertices, HashSet<Edge> edges) {
                        String[] info = line.split("\\s+");
                        if (info.length == 2) {
                            vertices.add(new Vertex(info[0]));
                            vertices.add(new Vertex(info[1]));
                            edges.add(new Edge(info[0], info[1]));
                        } else {
                            System.err.println("Wrong Line: " + line);
                            return false;
                        }
                        return true;
                    }
                });
            default:
                System.out.println("Cannot load any graph file, '" + path.getName() + "' is NOT exist.");
                return null;
        }
    }

    private Graph loadGraphBy(ReaderListener readerListener) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
        String line;
        Graph graph;
        HashSet<Vertex> vertices = new HashSet<>();
        HashSet<Edge> edges = new HashSet<>();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!readerListener.onRead(line, vertices, edges)) {
                break;
            }
        }
        graph = new Graph(vertices.toArray(new Vertex[0]), edges.toArray(new Edge[0]));
        return graph;
    }

    private interface ReaderListener {
        boolean onRead(String line, HashSet<Vertex> vertices, HashSet<Edge> edges);
    }
}
