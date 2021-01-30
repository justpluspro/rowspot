package org.qwli.rowspot.exception;

import org.qwli.rowspot.Message;

public class LoginFailException extends RuntimeException {


    private final Message error;

    public LoginFailException(Message error) {
        super(null, null, false, false);
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
