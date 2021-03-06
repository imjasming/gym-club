package com.gymclub.sso.oauth;

import com.gymclub.sso.oauth.processor.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author Xiaoming.
 * Created on 2019/05/24 14:15.
 */
@Configuration
@EnableSocial
@Order(1)
public class MySocialConfig extends SocialConfigurerAdapter {

    @Value("${gymclub.security.social.filter-processes-url}")
    private String filterProcessesUrl;

    @Value("${gymclub.security.social.register-url}")
    private String registerUrl;

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    ConnectionSignUp connectionSignUp;

    // 后处理器
    @Autowired(required = false)
    SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 已用 AutoConfig 代替，否则须把所有 providerConnectionFactory add 到 connectionFactoryConfigurer
     *
     * @param connectionFactoryLocator
     * @return
     */
    /*@Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        super.addConnectionFactories(connectionFactoryConfigurer, environment);
    }*/

    //@Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

        //建立jdbc的连接
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        //repository.setTablePrefix("gymclub_");
        // 默认注册用户
//        if (connectionSignUp != null) {
//            repository.setConnectionSignUp(connectionSignUp);
//        }
        return repository;
    }


    /**
     * 自定义qq登录路径和注册路径
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer mySocialSecurityConfig() {
        MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
        //1、认证失败跳转注册页面
        // 跳转到signUp controller，从session中获取用户信息并通过生成的uuid保存到redis里面，然后跳转bind页面
        // 前端绑定后发送用户信息到后台bind controller，1）保存到自己系统用户；2）保存一份userconnection表数据，Spring Social通过这里面表数据进行判断是否绑定
        configurer.signupUrl(registerUrl);
        //2、认证成功跳转后处理器，跳转带token的成功页面
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
