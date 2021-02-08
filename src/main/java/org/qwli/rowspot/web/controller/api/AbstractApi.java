package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.Constants;
import org.qwli.rowspot.model.LoggedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractApi {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    public LoggedUser getLoggedUser(HttpServletRequest request) {
        return ((LoggedUser) request.getAttribute(Constants.USER));
    }
}
