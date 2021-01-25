package org.qwli.rowspot.exception;


import org.qwli.rowspot.Message;

/**
 * 业务异常
 * @author liqiwen
 */
public class BizException extends RuntimeException {

    /**
     * 错误信息
     */
    private final Message error;

    public BizException(Message error){
        this.error = error;
    }

    public Message getError() {
        return error;
    }
}
