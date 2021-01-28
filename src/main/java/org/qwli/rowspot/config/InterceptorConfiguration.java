package org.qwli.rowspot.config;

import org.qwli.rowspot.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liqiwen
 * 拦截器配置
 **/
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    /**
     * 登陆拦截器
     **/
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor());
    }
}
