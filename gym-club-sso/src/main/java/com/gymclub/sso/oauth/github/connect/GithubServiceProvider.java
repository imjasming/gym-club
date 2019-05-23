package com.gymclub.sso.oauth.github.connect;

import com.gymclub.sso.oauth.github.api.GithubApi;
import com.gymclub.sso.oauth.github.api.impl.GithubOAuth2ApiBinding;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author Xiaoming.
 * Created on 2019/05/24 00:36.
 */
public class GithubServiceProvider extends AbstractOAuth2ServiceProvider<GithubApi> {
    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private final String CLIENT_ID;

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     * <p>
     * the OAuth2Operations template for conducting the OAuth 2 flow with the provider.
     *
     * @param clientId     3th part auth client id.
     * @param clientSecret 3th part auth client secret.
     */
    public GithubServiceProvider(String clientId, String clientSecret) {
        super(new GithubOAuth2Template(clientId, clientSecret, GITHUB_AUTHORIZE_URL, GITHUB_ACCESS_TOKEN_URL));
        this.CLIENT_ID = clientId;
    }

    @Override
    public GithubApi getApi(String accessToken) {
        return new GithubOAuth2ApiBinding(accessToken);
    }
}
