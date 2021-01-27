package org.qwli.rowspot.web.interceptor;

import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.util.EnvironmentContext;
import org.qwli.rowspot.web.annotations.AuthenticatedRequired;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;


/**
 * 登录拦截器
 * @author liqiwen
 */
public class LoginInterceptor extends AbstractInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {

        LoggedUser loggedUser = (LoggedUser) request.getSession().getAttribute("user");
        request.setAttribute("user", loggedUser);

        final String requestURI = request.getRequestURI();
        if(requestURI.equals("/login")) {
            logger.info("preHandle requestUri: [{}]", requestURI);
            if(EnvironmentContext.isAuthenticated() && request.getSession().getAttribute("user") != null) {
                response.sendRedirect("/");
                return false;
            }
        }

        if(handler instanceof HandlerMethod) {
            HandlerMethod requestMethod = (HandlerMethod) handler;
            final AuthenticatedRequired requestMethodAnno = requestMethod.getMethodAnnotation(AuthenticatedRequired.class);
            if(requestMethodAnno != null) {
                if (loggedUser == null) {

                    //反之将资源重定向到首页，并且设置登录之后的重定向地址
                    final StringBuffer requestUrlBuffer = request.getRequestURL();

                    final String requestUrl = URLEncoder.encode(requestUrlBuffer.toString(), Charset.defaultCharset().name());

                    response.sendRedirect("/login?redirect=" + requestUrl);

                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<String> urlPatterns() {
        return Collections.singletonList("/**");
    }

    @Override
    public List<String> excludePatterns() {
        return Collections.emptyList();
    }
}
