package com.gymclub.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author Xiaoming.
 * Created on 2019/05/19 03:01.
 * 跨域配置
 */
@Configuration
@Order(1)
public class CorsConfig {
    /**
     * 允许跨域调用的过滤器
     */
    /*@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");  // 1 设置访问源地址
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");  // 2 设置访问源请求头
        config.addAllowedMethod("*");  // 3 设置访问源请求方法
        //config.addExposedHeader("Content-Range");//这里是需要额外配置的header内容

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // 4 对接口配置跨域设置
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }*/
}
