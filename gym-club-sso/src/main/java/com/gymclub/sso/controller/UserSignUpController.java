package com.gymclub.sso.controller;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.model.UmUser;
import com.gymclub.sso.oauth.model.SocialUserInfo;
import com.gymclub.sso.oauth.utils.SocialRedisHelper;
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
    private SocialRedisHelper socialRedisHelper;

    @Value("${gymclub.security.social.register-url}")
    private String registerUrl;

    @Value("${gymclub.security.social.bind-url}")
    private String bindUrl;

    //@ApiOperation("sign up")
    @PostMapping(path = "/register")
    public ResponseEntity signUp(
            //@ApiParam(required = true, name = "user sign up params", value = "{username, email, password}")
            @RequestBody UserSignUpRequest request) {
        UmUser user = userService.register(request);
        if (user != null) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }
        //return user == null ? ResponseEntity.badRequest().body("Username or email already exists") : ResponseEntity.created(null).build();
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
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        userInfo.setImgUrl(connectionFromSession.getImageUrl());
        userInfo.setDisplayName(connectionFromSession.getDisplayName());
        userInfo.setProviderId(connectionFromSession.getKey().getProviderId());
        userInfo.setProviderUserId(connectionFromSession.getKey().getProviderUserId());

        log.info("userSignUpController - socialSignUp: userInfo {}", JSON.toJSONString(userInfo));

        socialRedisHelper.saveConnectionData(uuid, connectionFromSession.createData());
        response.sendRedirect(bindUrl + "?key=" + uuid);
    }

    /**
     * 社交账号绑定
     * 这里的参数是举例，后面可以创建一个User类代替并使用@ResponseBody注解
     *
     * @param key
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/social/bind")
    public ResponseEntity<?> bind(String key) throws AuthenticationException {
        String userId = UUID.randomUUID().toString();

        // 如果是社交注册，既进行社交保存数据操作
        if (!org.springframework.util.StringUtils.isEmpty(key)) {
            socialRedisHelper.doPostSignUp(key, userId);
        }
        //todo:保存自有平台系统用户数据库，并生成token返回给前端
        return null;
    }
}
