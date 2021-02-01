package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.exception.BizException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author qwli7
 * 业务异常 Reader
 */
public class BizExceptionReader implements ExceptionReader {

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.singletonMap("errors", ((BizException) ex).getError());
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof BizException;
    }
}
