package org.qwli.rowspot.service;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author qwli7
 */
public abstract class AbstractService<T, R> implements ApplicationEventPublisherAware {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    /**
     * method convert
     * use pojo object -> dto
     * @param t target
     * @return R
     */
    @SuppressWarnings("unchecked")
    protected R convert(T t) {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        if(actualTypeArguments == null || actualTypeArguments.length != 2) {
            //泛型参数长度不对
            throw new RuntimeException("");
        }
        Type actualTypeArgument = actualTypeArguments[1];
        String typeName = actualTypeArgument.getTypeName();
        if(StringUtils.isEmpty(typeName)) {
            //类型名称为空
            throw new RuntimeException("");
        }
        try {
            Class<?> convertClass = Class.forName(typeName);
            R target = (R) convertClass.newInstance();
            BeanUtils.copyProperties(t, target);
            return target;
        } catch (Exception ex){
            logger.error("AbstractService method[convert] has occurred error:[{}]", ex.getMessage(), ex);
            throw new BizException(MessageEnum.SERVER_ERROR);
        }
    }
}
