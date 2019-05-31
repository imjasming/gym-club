package com.gymclub.sso.controller;

import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.dto.UserSocialBindParam;
import com.gymclub.sso.oauth.utils.SocialConnectRedisHelper;
import com.gymclub.sso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 17:49.
 */
@Slf4j
@RestController
public class UserSignUpController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private SocialConnectRedisHelper socialConnectRedisHelper;
    @Value("${gymclub.security.social.register-url}")
    private String registerUrl;
    @Value("${front-end.social-bind-url}")
    private String bindUrl;

    @PostMapping(path = "/register")
    public ResponseEntity signUp(
            //@ApiParam(required = true, name = "user sign up params", value = "{username, email, password}")
            @RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }


    /**
     * 获取社交账号数据并保存到redis里面待前端绑定时使用
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/signup/social")
    public void socialSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString();
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialConnectRedisHelper.saveConnectionData(uuid, connectionFromSession.createData());

        response.sendRedirect(bindUrl + "?key=" + uuid);
    }

    /**
     * 社交账号绑定
     */
    @PostMapping("/social/bind")
    public ResponseEntity<?> bind(@RequestBody UserSocialBindParam param) throws AuthenticationException {
        return ResponseEntity.ok(userService.bindOAuthUser(param));
    }
}
