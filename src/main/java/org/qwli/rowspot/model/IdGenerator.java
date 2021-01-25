package org.qwli.rowspot.model;

import org.hibernate.annotations.common.reflection.ReflectionUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 自定义 id 生成器
 * @author liqiwen
 */
public class IdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Object id = getFieldValue("id", obj);
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(s, obj);
    }

    public Object getFieldValue(@NonNull String fieldName, @NonNull Object object) {
        Assert.notNull(fieldName, "FieldName must not be null");
        Assert.notNull(object, "Object type must not be null");
        Object value = null;
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            value = method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
