package org.qwli.rowspot.web.filter;

import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.util.EnvironmentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 认证 AuthenticateFilter
 * @author liqiwen
 */
public class AuthenticateFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String requestUrl = request.getRequestURL().toString();
        final String requestURI = request.getRequestURI();
        final String remoteHost = request.getRemoteHost();
        final int remotePort = request.getRemotePort();
        final String method = request.getMethod();

        logger.info("requestUrl:[{}], requestURI:[{}], remoteHost:[{}]", requestUrl, requestURI, remoteHost);
        logger.info("remotePort: [{}], method: [{}]", remotePort, method);

        LoggedUser loggedUser = (LoggedUser) request.getSession().getAttribute("user");
        EnvironmentContext.setAuthenticated(loggedUser != null);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
