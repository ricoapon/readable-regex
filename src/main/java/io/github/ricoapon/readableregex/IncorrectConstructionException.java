package io.github.ricoapon.readableregex;

/**
 * Exception that will be thrown when the builder methods are called in incorrect order or at the wrong time.
 */
public class IncorrectConstructionException extends RuntimeException {
    public IncorrectConstructionException(String message) {
        super(message);
    }
}
