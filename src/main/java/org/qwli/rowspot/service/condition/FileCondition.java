package org.qwli.rowspot.service.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author qwli7 
 * @date 2021/2/6 13:43
 * 功能：FileCondition
 **/
public class FileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext,
                           AnnotatedTypeMetadata annotatedTypeMetadata) {

        Environment environment = conditionContext.getEnvironment();
        String fileEnabled = environment.getProperty("file.enabled");
        return "true".equals(fileEnabled);
    }
}
