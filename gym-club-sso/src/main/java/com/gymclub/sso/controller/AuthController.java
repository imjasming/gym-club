package com.gymclub.sso.controller;

import com.gymclub.sso.exception.LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    /*@ApiOperation("user sign in")
    @PostMapping(path = "/login")
    public ResponseEntity login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        String token = userService.login(username, password);

        if (token == null) {
            return ResponseEntity.badRequest().body("Username or password incorrect");
        }

        log.info("user[{}] login, token: {}", username, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseEntity.ok(tokenMap);
    }*/

    @RequestMapping("/login")
    public ResponseEntity loginInfo(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败");
    }

    /**
     * return the oauth client list
     *
     * @param request
     * @return oauth client list
     */
    @GetMapping("/oauth2-client")
    public ResponseEntity getOauth2Client(HttpServletRequest request) {
        /*Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();

        List data = new ArrayList();
        clientRegistrations.forEach(registration -> {
            Map client = new HashMap();
            client.put("clientName", registration.getClientName());
            client.put("clientUrl", *//*tempContextUrl +*//* OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId());
            data.add(client);
        });*/

        Map client = new HashMap();
        client.put("clientName", "Auth with Github");
        client.put("clientUrl", "https://github.com/login/oauth/authorize?client_id=de87e995aa6c1c726646&state=github");

        return
                ResponseEntity.ok(Collections.singletonList(client))
                //ResponseEntity.ok(data)
                ;
    }
/*
    @GetMapping("/oauth2/code/{client}")
    public ResponseEntity oauthLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @PathVariable("client") String client) throws IOException {

        if (code == null || "".equals(code)) {
            return ResponseEntity.badRequest().build();
        }

        code = code.trim();

        // 向github请求token后获取用户信息，并将其保存到数据库
        String id = githubAuthentication.getUsername(code);
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        MyAuthenticationToken authRequest = new MyAuthenticationToken(id);

        authRequest.setDetails(new OAuth2AuthenticationDetails(request));
        // 认证处理后返回token
        OAuth2AccessToken accessToken = loginSuccessHandler.getAccessToken(request, response, authRequest);
        return ResponseEntity.ok(githubService.getTokenByUsername(id));
    }*/
}
