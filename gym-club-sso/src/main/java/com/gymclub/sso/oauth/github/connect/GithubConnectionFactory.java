package com.gymclub.sso.oauth.github.connect;

import com.gymclub.sso.oauth.github.api.GithubApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 23:26.
 */
public class GithubConnectionFactory extends OAuth2ConnectionFactory<GithubApi> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * @param serviceProvider the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
     * @param apiAdapter      the ApiAdapter for mapping the provider-specific service API model to the uniform {@link Connection} interface.
     */
    public GithubConnectionFactory(String providerId, OAuth2ServiceProvider<GithubApi> serviceProvider, ApiAdapter<GithubApi> apiAdapter) {
        super(providerId, serviceProvider, apiAdapter);
    }
}
