package org.cachet.core;

/**
 * Created by sriram on 12/12/13.
 */
public class CacheException extends Exception {

    /**
     * Creates a new <code>CacheException</code>.
     */
    public CacheException() {
        super();
    }

    /**
     * Creates a new <code>CacheException</code>.
     *
     * @param message the reason for the exception.
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * Creates a new <code>CacheException</code>.
     *
     * @param cause the underlying cause of the exception.
     */
    public CacheException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new <code>CacheException</code>.
     *
     * @param message the reason for the exception.
     * @param cause   the underlying cause of the exception.
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

}
