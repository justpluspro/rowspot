package org.qwli.rowspot.config;

import org.qwli.rowspot.web.filter.AuthenticateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebMvcConfigurationSupport implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("addResourceHandlers");
        //将静态资源 /static/** 的映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


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