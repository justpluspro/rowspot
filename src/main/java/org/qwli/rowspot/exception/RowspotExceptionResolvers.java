package org.qwli.rowspot.exception;

import org.qwli.rowspot.exception.reader.AuthenticationExceptionReader;
import org.qwli.rowspot.exception.reader.BizExceptionReader;
import org.qwli.rowspot.exception.reader.ExceptionReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/1/28 8:14
 * 功能：RowspotExceptionResolvers
 **/
public class RowspotExceptionResolvers implements HandlerExceptionResolver {

    private final static String ERROR_ATTRIBUTES = RowspotExceptionResolvers.class.getSimpleName() + ".ERROR";

    /**
     * 异常读取器列表
     */
    private final List<ExceptionReader> exceptionReaders;

    public RowspotExceptionResolvers() {

        exceptionReaders = new ArrayList<>();
        exceptionReaders.add(new AuthenticationExceptionReader());
        exceptionReaders.add(new BizExceptionReader());


    }



    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        for(ExceptionReader exceptionReader: exceptionReaders) {
            boolean match = exceptionReader.match(e);
            if(match) { //匹配上
                int status = exceptionReader.getStatus(httpServletRequest, httpServletResponse);

//                httpServletRequest.setAttribute(DispatcherServlet.);


                break;
            }
        }

        return null;
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
