package com.gymclub.sso.handler;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.service.OAuthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 16:23.
 */
@Slf4j
@Component("oAuthAuthenticationSuccessHandler")
public class OAuthAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
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


        OAuth2Request oAuth2Request = getOAuth2Request(request, response, authentication);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(token));
    }

    public OAuth2Request getOAuth2Request(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String header = request.getHeader("Authorization");
//        String name = authentication.getName();
//        String password = (String) authentication.getCredentials();
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        String oauthClientSecret = clientDetails.getClientSecret();
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (clientSecret != null && oauthClientSecret != null && !clientSecret.equals(oauthClientSecret.substring(6, 12))) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
        return tokenRequest.createOAuth2Request(clientDetails);
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
/*
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("oauth2 login success");
        //getRedirectStrategy().sendRedirect(request, response, "http://127.0.0.1:8088/#/me/profile" + request.getParameter("state"));

        response.getWriter().println(JSON.toJSONString("success"));
        log.info("from: {}:{}, request: {} {} {}, cookies: {}",
                request.getRemoteAddr(),
                request.getRemotePort(),
                request.getMethod(),
                request.getRequestURL(),
                JSON.toJSONString(request.getParameterMap()),
                JSON.toJSONString(request.getCookies())
        );
    }*/
}