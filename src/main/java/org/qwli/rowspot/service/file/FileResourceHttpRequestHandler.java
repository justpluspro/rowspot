package org.qwli.rowspot.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;

/**
 * @author qwli7
 * @date 2021/2/6 8:27
 * 功能：FileResourceHttpRequestHandler
 **/
public class FileResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    public FileResourceHttpRequestHandler(FileResourceResolver fileResourceResolver, ResourceProperties resourceProperties) {
        super();
        this.setResourceResolvers(Collections.singletonList(fileResourceResolver));

        //handle static resource cache
        Duration period = resourceProperties.getCache().getPeriod();
        if(period != null) {
            this.setCacheSeconds((int) period.getSeconds());
        }
        CacheControl cacheControl = resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        this.setCacheControl(cacheControl);
        logger.info("FileResourceHttpRequestHandler registered!");
    }

    @Override
    protected MediaType getMediaType(HttpServletRequest request, Resource resource) {
        if(resource instanceof ReadablePathFileResource) {
           return (((ReadablePathFileResource) resource)).getMediaType().orElse(null);
        }
        return super.getMediaType(request, resource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("FileResourceHttpRequestHandler afterPropertiesSet...");
        super.getLocations().add(null);
        super.afterPropertiesSet();
    }
}
