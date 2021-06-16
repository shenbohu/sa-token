package com.bohu.satoken;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName SaTokenConfigure
 * @Author shenbohu
 * @Date 2021/6/149:11 下午
 * @Version 1.0
 **/
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册sa-token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
