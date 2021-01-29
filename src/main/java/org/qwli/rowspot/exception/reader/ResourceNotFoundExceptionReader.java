package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * 资源未找到异常
 * @author liqiwen
 **/
public class ResourceNotFoundExceptionReader implements ExceptionReader {
    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.singletonMap("errors", "page not found");
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof ResourceNotFoundException;
    }
}
