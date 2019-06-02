package com.gymclub.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author Xiaoming.
 * Created on 2019/05/18 17:07.
 */
@Configuration
@EnableResourceServer
public class MyResourcesServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    protected AuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 登录的时候我们只要提交的action，不要跳转到登录页
        http.formLogin()
                //登录页面，前后端分离用不到，只需自定义 FailureHandler，只作登陆失败跳转
                .loginPage("/auth/login")
                // 登录提交action，app会用到
                // 用户名登录地址
                .loginProcessingUrl("/auth/form/token")
                //成功处理器 返回Token
                .successHandler(formLoginAuthenticationSuccessHandler)
                //失败处理器
                .failureHandler(authenticationFailureHandler);

        http
                .apply(mySocialSecurityConfig)
                // 手机验证码登录
                //.apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                // 手机验证码登录地址
                //.antMatchers("/mobile/token", "/email/token")
                //.permitAll()
                //.and()
                //.authorizeRequests()
                .antMatchers(
                        "/sso/**",
                        "/signup/**",
                        "/connect/**",
                        "/auth/**",
                        "/register",
                        "/social/**",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff2",
                        "/code/image")
                .permitAll() //以上的请求都不需要认证
                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .anyRequest()
                .authenticated()
                .and().cors()
                .and().csrf().disable();
        // 以上在 webSecurityConfig 已配置过，但好像没用，故重新在 ResourceServerConfig 再次配一次，艹
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
}
