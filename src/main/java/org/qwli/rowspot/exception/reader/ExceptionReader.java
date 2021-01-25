package org.qwli.rowspot.exception.reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常读取器
 * @author liqiwen
 */
public interface ExceptionReader {

    /**
     * 从异常中读取信息
     * @param ex ex
     * @return Map
     */
    Map<String, Object> readErrors(Exception ex);

    /**
     * 返回 status
     * @return int
     */
    int getStatus(HttpServletRequest request, HttpServletResponse response);


    /**
     * 是否匹配异常
     * @param ex ex
     * @return true | false
     */
    boolean match(Exception ex);
}
