package org.qwli.rowspot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.MessageCodesResolver;

/**
 * @author qwli7
 * @date 2021/2/1 18:03
 * 功能：RowspotMessageCodeResolver
 **/
public class RowspotMessageCodeResolver implements MessageCodesResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static RowspotMessageCodeResolver INSTANCE = new RowspotMessageCodeResolver();

    private RowspotMessageCodeResolver() {
        super();
    }


    @Override
    public String[] resolveMessageCodes(String objectName, String errorCode) {
        logger.info("resolveMessageCodes:[{}], [{}]", objectName, errorCode);
        return new String[]{objectName + "." + errorCode};
    }

    @Override
    public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
        logger.info("resolveMessageCodes:[{}], [{}], [{}], [{}]", objectName, errorCode, field, fieldType);
        return new String[]{objectName + "." + field + "." + errorCode};
    }
}
