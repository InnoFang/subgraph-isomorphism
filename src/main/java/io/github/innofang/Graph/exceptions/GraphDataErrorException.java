package io.github.innofang.Graph.exceptions;

/**
 * throw this exception when read a specify data set but failed.
 */
public class GraphDataErrorException extends RuntimeException {
    public GraphDataErrorException(String message) {
        super(message);
    }
}