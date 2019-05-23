package com.gymclub.sso.oauth.github.api.impl;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.oauth.github.api.GithubApi;
import com.gymclub.sso.oauth.github.model.GithubEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author Xiaoming.
 * Created on 2019/05/21 01:22.
 */
@Slf4j
public class GithubOAuth2ApiBinding extends AbstractOAuth2ApiBinding implements GithubApi {
    public static final String USER_INFO_URL_TEMP = "https://api.github.com/user";

    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.clientSecret}")
    private String clientSecret;

    public GithubOAuth2ApiBinding(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    public GithubEntity getGithubEntity() {

        String response = getRestTemplate().getForObject(USER_INFO_URL_TEMP, String.class);
        return JSON.parseObject(response, GithubEntity.class);
    }
}
