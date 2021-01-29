package org.qwli.rowspot.exception;

import org.qwli.rowspot.Message;

public class BadRequestException extends BizException {

    public BadRequestException(Message error) {
        super(error);
    }
}
