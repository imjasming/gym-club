package com.gymclub.sso.oauth.github.connect;

import com.gymclub.sso.oauth.github.api.GithubApi;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 23:26.
 */
public class GithubConnectionFactory extends OAuth2ConnectionFactory<GithubApi> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     */
    public GithubConnectionFactory(String providerId, String clientId, String clientSecret) {
        super("github", new GithubServiceProvider(clientId, clientSecret), new GithubAdapter());
    }
}
