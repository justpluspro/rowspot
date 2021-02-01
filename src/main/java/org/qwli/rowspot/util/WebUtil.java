package org.qwli.rowspot.util;

import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author qwli7
 * @date 2021/2/1 8:12
 * 功能：WebUtil
 **/
public final class WebUtil extends WebUtils {

    public static final String AJAX_HEADER = "X-Requested-With";
    public static final String AJAX_TYPE = "XMLHttpRequest";
    public static final String API_REQUEST_URI = "api/";

    public static final String[] SPIDERS = new String[]{"Googlebot", "Baiduspider", "360Spider", "Bingbot", "msnbot",
            "DuckDuckBot", "slurp", "YandexBot", "Sogou", "YoudaoBot", "Sosospider", "Yisouspider"};


    private WebUtil() {
        super();
    }


    public static boolean isSpider(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if(StringUtils.hasText(userAgent)) {
            return Arrays.stream(SPIDERS).anyMatch(userAgent::contains);
        }
        return false;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(AJAX_HEADER);
        return AJAX_TYPE.equals(header);
    }


    public static boolean isApiRequest(HttpServletRequest request) {
        String requestPath = request.getRequestURI().substring(request.getContextPath().length() + 1);
        if(requestPath.startsWith(API_REQUEST_URI)){
            return true;
        }
        String forwardUri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        return forwardUri != null && forwardUri.startsWith("/" + API_REQUEST_URI);
    }

}
