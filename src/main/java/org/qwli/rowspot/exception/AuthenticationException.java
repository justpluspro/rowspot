package org.qwli.rowspot.exception;


import org.qwli.rowspot.Message;

/**
 * 认证异常
 * @author liqiwen
 */
public class AuthenticationException extends BizException {

    public AuthenticationException(Message error) {
        super(error);
    }
}
