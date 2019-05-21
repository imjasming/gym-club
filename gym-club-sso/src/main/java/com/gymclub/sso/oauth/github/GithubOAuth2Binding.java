package com.gymclub.sso.oauth.github;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xiaoming.
 * Created on 2019/05/21 01:22.
 */
@Slf4j
public class GithubOAuth2Binding extends AbstractOAuth2ApiBinding {
    public static final String USER_INFO_URL_TEMP = "https://api.github.com/user";
    public static final String AUTHORIZATION_CODE_URL = "https://github.com/login/oauth/authorize";
    public static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.clientSecret}")
    private String clientSecret;

    private final String ACCESS_TOKEN;

    @Autowired
    private RestTemplate restTemplate;

    public GithubOAuth2Binding(String accessToken, String clientId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.ACCESS_TOKEN = accessToken;
    }

    public GithubEntity getUser() {
        String url = USER_INFO_URL_TEMP + ACCESS_TOKEN;

        String response = getRestTemplate().getForObject(url, String.class);
        return JSON.parseObject(response, GithubEntity.class);
    }
}
