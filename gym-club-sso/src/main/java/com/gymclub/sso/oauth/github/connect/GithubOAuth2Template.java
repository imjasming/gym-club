package com.gymclub.sso.oauth.github.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 23:49.
 */
@Slf4j
public class GithubOAuth2Template extends OAuth2Template {
    public GithubOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String response = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("github - POST {}, response: {}", accessTokenUrl, response);

        String[] responseItems = StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");
        assert responseItems != null;
        // github api 貌似只返回 access_token&token_type
        String accessToken = StringUtils.substringAfterLast(responseItems[0], "=");

        return new AccessGrant(accessToken);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
