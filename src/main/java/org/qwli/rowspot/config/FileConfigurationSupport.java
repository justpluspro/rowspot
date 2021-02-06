package org.qwli.rowspot.config;

import org.qwli.rowspot.service.file.FileResourceHttpRequestHandler;
import org.qwli.rowspot.service.file.FileResourceResolver;
import org.qwli.rowspot.service.file.FileService;
import org.qwli.rowspot.service.condition.FileCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.PathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.util.Collections;

/**
 * @author qwli7
 * @date 2021/2/6 12:41
 * 功能：FileConfigurationSupport
 **/
@Configuration
@Conditional(value = FileCondition.class)
@ConditionalOnWebApplication
public class FileConfigurationSupport implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(UrlPathHelper urlPathHelper,
                                                           PathMatcher pathMatcher,
                                                           ContentNegotiationManager contentNegotiationManager,
                                                           ServletContext servletContext,
                                                           ApplicationContext applicationContext,
                                                           ResourceProperties resourceProperties,
                                                           FileService fileService) {

        logger.info("createSimpleUrlHandlerMapping registered!");

        FileResourceResolver fileResourceResolver = new FileResourceResolver(fileService);
        ResourceHttpRequestHandler resourceHttpRequestHandler = new FileResourceHttpRequestHandler(fileResourceResolver, resourceProperties);
        if(urlPathHelper != null) {
            resourceHttpRequestHandler.setUrlPathHelper(urlPathHelper);
        }

        if(contentNegotiationManager != null) {
            resourceHttpRequestHandler.setContentNegotiationManager(contentNegotiationManager);
        }

        resourceHttpRequestHandler.setServletContext(servletContext);
        resourceHttpRequestHandler.setApplicationContext(applicationContext);

        try{
            resourceHttpRequestHandler.afterPropertiesSet();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/**", resourceHttpRequestHandler));
        simpleUrlHandlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE);
        if(urlPathHelper != null) {
            simpleUrlHandlerMapping.setUrlPathHelper(urlPathHelper);
        }
        simpleUrlHandlerMapping.setPathMatcher(pathMatcher);
        return simpleUrlHandlerMapping;
    }
}
