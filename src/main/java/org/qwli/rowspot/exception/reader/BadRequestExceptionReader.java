package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.exception.BadRequestException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class BadRequestExceptionReader implements ExceptionReader {
    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return Collections.emptyMap();
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof BadRequestException;
    }
}
