package com.gymclub.sso.handler;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 交给前端使用的认证成功处理器
 */
@Slf4j
@Component("jsAuthenticationSuccessHandler")
public class JsAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${front-end.login-success-redirect-uri}")
    private String callbackUri;

    private String openIdParameter = "openId";

    private String providerIdParameter = "providerId";

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 通过验证生成token
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            log.info("============= oauth login: userDetails: {}", JSON.toJSONString(user));

            // 生成 token
            //String token = jwtAccessTokenConverter.convertAccessToken()
            String token = jwtTokenUtil.generateToken(user);
            StringBuilder redirectUri = new StringBuilder().append(callbackUri).append("?user=")
                    .append(user.getUsername()).append("&token=").append(token).append("&tokenType=Bearer");

            // 社交登录成功跳转到成功页面
            //getRedirectStrategy().sendRedirect(request, response, redirectUri.toString());
            response.sendRedirect(redirectUri.toString());
        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
//            throw new DomainException(ResultCode.USER_AUTH_FAILD);
        }
    }

    /**
     * 获取openId
     */
    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    /**
     * 获取提供商id
     */
    protected String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }
}
