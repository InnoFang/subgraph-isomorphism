package io.github.innofang.graph.exceptions;

/**
 * throw this exception when read a specify test set but failed.
 */
public class GraphDataErrorException extends RuntimeException {
    public GraphDataErrorException(String message) {
        super(message);
    }
}