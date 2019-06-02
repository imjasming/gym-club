package com.gymclub.sso.handler;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.service.OAuthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 16:23.
 */
@Slf4j
@Component("formLoginAuthenticationSuccessHandler")
public class FormLoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${front-end.login-success-redirect-uri}")
    private String FRONT_END_REDIRECT_URI;

    @Autowired
    private OAuthClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        // 从前端 Authentication 中 decode 出 clientId，clientSecret，原 header：{username: 'client', password: 'secret'}
        String[] tokens = OAuthTokenUtil.extractAndDecodeHeader(header);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        OAuth2AccessToken token = OAuthTokenUtil.extractOAuthAccessToken(clientId, clientSecret,
                authorizationServerTokenServices, clientDetailsService, authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(token));
    }
}