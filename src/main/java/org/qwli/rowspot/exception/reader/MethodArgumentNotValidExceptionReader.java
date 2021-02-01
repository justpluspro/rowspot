package org.qwli.rowspot.exception.reader;

import org.qwli.rowspot.MessageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author qwli7 
 * @date 2021/2/1 18:27
 * 功能：MethodArgumentNotValidExceptionReader
 **/
public class MethodArgumentNotValidExceptionReader implements ExceptionReader{

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public Map<String, Object> readErrors(Exception ex) {
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        logger.info("allErrors:[{}]", allErrors);
                
        return Collections.singletonMap("errors", MessageEnum.CATEGORY_HAS_EXISTS);
    }

    @Override
    public int getStatus(HttpServletRequest request, HttpServletResponse response) {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public boolean match(Exception ex) {
        return ex instanceof MethodArgumentNotValidException;
    }
}
