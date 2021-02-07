package org.qwli.rowspot.config;

import org.qwli.rowspot.exception.RowspotExceptionResolvers;
import org.qwli.rowspot.web.filter.AuthenticateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author qwli7
 * 自定义 Web 配置
 */
@Configuration
public class WebMvcConfigurationSupport implements WebMvcConfigurer {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

//    /**
//     * 添加静态资源映射
//     * @param registry registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        logger.info("addResourceHandlers");
//        //将静态资源 /static/** 的映射
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//    }
    
    /**
     * Registry MessageCodeResolver
     * @return RowspotCodeResolver
     */
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return RowspotMessageCodeResolver.INSTANCE;
    }

    
     /**
     * Registry RowspotExceptionResolvers
     * @return RowspotExceptionResolvers
     */
    @Bean
    public RowspotExceptionResolvers rowspotExceptionResolvers() {
        return new RowspotExceptionResolvers();
    }

    /**
     * 定义自定义的异常解析器
     * @param resolvers resolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //自定义的异常放在最前面
        resolvers.add(0, rowspotExceptionResolvers());
        resolvers.add(1, (httpServletRequest, httpServletResponse, o, e) -> {
            if(e instanceof MaxUploadSizeExceededException) {
                //客户端上传了过大的文件
                //这里返回 400 更加合适
                try{
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
                } catch (IOException ex){
                    logger.error("error:[{}]", ex.getMessage(), ex);
                }
            }
            return new ModelAndView();
        });
    }

    /**
     * 过滤器配置
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<AuthenticateFilter> authenticateFilterFilterRegistrationBean() {

        FilterRegistrationBean<AuthenticateFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new AuthenticateFilter());
        filterFilterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        filterFilterRegistrationBean.setName(AuthenticateFilter.class.getName());

        return filterFilterRegistrationBean;
    }
}
