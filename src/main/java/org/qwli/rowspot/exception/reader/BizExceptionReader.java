package org.qwli.rowspot.exception.reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BizExceptionReader implements ExceptionReader {

    @Override
    public Map<String, Object> readErrors(Exception ex) {
        return null;
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return 0;
    }

    @Override
    public boolean match(Exception ex) {
        return false;
    }
}
