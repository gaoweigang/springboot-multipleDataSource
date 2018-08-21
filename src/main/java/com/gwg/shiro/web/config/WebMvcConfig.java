package com.gwg.shiro.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 解决跨域问题，通过WebMvcConfigurerAdapter#addCorsMappings去配置
     * Spring mvc4.2中增加了对CROS(Cross-Origin Resource Sharing)的支持
     * http://www.ruanyifeng.com/blog/2016/04/cors.html
     * https://www.cnblogs.com/520playboy/p/7306008.html
     * https://www.jianshu.com/p/d05303d34222
     *
     * 第二种方式，使用这种方式需要继承WebMvcConfigurerAdapter
     * CorsInterceptor: 拦截器阶段的CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置CorsInterceptor的CORS参数
        this.configCorsParams(registry.addMapping("/**").allowedOrigins("*"));
    }

    private void configCorsParams(CorsRegistration corsRegistration) {
        corsRegistration.allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.OPTIONS.name())
                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }



}
