package org.qwli.rowspot.exception;

import org.qwli.rowspot.exception.reader.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/1/28 8:14
 * 功能：RowspotExceptionResolvers
 **/
public class RowspotExceptionResolvers implements HandlerExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final static String ERROR_ATTRIBUTES = RowspotExceptionResolvers.class.getSimpleName() + ".ERROR_ATTRIBUTE";

    /**
     * 异常读取器列表
     */
    private final List<ExceptionReader> exceptionReaders;

    public RowspotExceptionResolvers() {

        exceptionReaders = new ArrayList<>();
        exceptionReaders.add(new AuthenticationExceptionReader());
        exceptionReaders.add(new BizExceptionReader());
        exceptionReaders.add(new ResourceNotFoundExceptionReader());
        exceptionReaders.add(new BadRequestExceptionReader());
        exceptionReaders.add(new LoginFailExceptionReader());

        logger.info("Registry all exception readers, total count is {}!", exceptionReaders.size());
    }



    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        if(resolve(httpServletRequest, httpServletResponse, e)){
            return new ModelAndView();
        }
        return null;
    }


    private boolean resolve(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        request.removeAttribute(ERROR_ATTRIBUTES);

        for(ExceptionReader reader: exceptionReaders) {
            if(!reader.match(ex)){
                continue;
            }
            int status = reader.getStatus(request, response);
            final Map<String, Object> readErrors = reader.readErrors(ex);
            request.setAttribute(ERROR_ATTRIBUTES, readErrors);

            try{
                response.sendError(status);
            } catch (IOException e){
                logger.error(e.getMessage(), e);
            }
            return true;
        }
        return false;

    }

    /**
     * 获取 Http 状态码
     * @param request request
     * @param response response
     * @return HttpStatus
     */
    public int getStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        for(ExceptionReader exceptionReader: exceptionReaders) {
            if(exceptionReader.match(ex)){
                return exceptionReader.getStatus(request, response);
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
