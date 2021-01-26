package org.qwli.rowspot.util;

import java.util.Date;

/**
 * 日期工具类
 * @author liqiwen
 * @since 1.2
 */
public final class DateUtil {
    private DateUtil() {
        super();
    }


    public static Integer getHoursFromNow(Date start) {
        if(start == null) {
            return 0;
        }
        if(start.after(new Date())) {
            return 0;
        }
        return 1;
    }
}
