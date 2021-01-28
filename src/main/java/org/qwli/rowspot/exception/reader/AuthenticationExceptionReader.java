package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.exception.AuthenticationException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author qwli7
 * @date 2021/1/28 8:10
 * 功能：AuthenticationExceptionReader
 **/
public class AuthenticationExceptionReader implements ExceptionReader {
    @Override
    public Map<String, Object> readErrors(Exception ex) {
        Message error = ((AuthenticationException) ex).getError();
        if(error == null) {
            return Collections.emptyMap();
        } else {
            return Collections.singletonMap("errors", error);
        }
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof AuthenticationException;
    }
}
