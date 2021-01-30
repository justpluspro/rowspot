package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.exception.LoginFailException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class LoginFailExceptionReader implements ExceptionReader {
    @Override
    public Map<String, Object> readErrors(Exception ex) {
        LoginFailException loginFailException = (LoginFailException) ex;
        return Collections.singletonMap("errors", loginFailException.getError());
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.UNAUTHORIZED.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof LoginFailException;
    }
}
