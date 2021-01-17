package io.github.ricoapon.readableregex;

/**
 * Exception that will be thrown when a new object could not be instantiated using methods in {@link RegexObjectInstantiation}.
 */
public class RegexObjectInstantiationException extends RuntimeException {
    /**
     * Constructor.
     * @param message The message of the exception.
     */
    public RegexObjectInstantiationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     */
    public RegexObjectInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
