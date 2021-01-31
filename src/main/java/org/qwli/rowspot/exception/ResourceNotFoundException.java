package org.qwli.rowspot.exception;

import org.qwli.rowspot.Message;

/**
 * resource not found exception
 * @author liqiwen
 * @since 1.2
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * error info
     */
    private final Message error;

    public ResourceNotFoundException(Message error) {
        super(null, null, false, false);
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
