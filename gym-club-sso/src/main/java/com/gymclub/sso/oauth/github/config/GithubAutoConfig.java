package com.gymclub.sso.oauth.github.config;

import com.gymclub.sso.oauth.SocialAutoConfigurerAdapter;
import com.gymclub.sso.oauth.github.connect.GithubAdapter;
import com.gymclub.sso.oauth.github.connect.GithubConnectionFactory;
import com.gymclub.sso.oauth.github.connect.GithubServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 22:40.
 */
@Configuration
public class GithubAutoConfig extends SocialAutoConfigurerAdapter {
    @Value("${gymclub.security.oauth2.github.provider-id}")
    private String providerId;
    @Value("${gymclub.security.oauth2.github.client-id}")
    private String clientId;
    @Value("${gymclub.security.oauth2.github.client-secret}")
    private String clientSecret;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {

        return new GithubConnectionFactory(providerId, new GithubServiceProvider(clientId, clientSecret), new GithubAdapter());
    }
}
