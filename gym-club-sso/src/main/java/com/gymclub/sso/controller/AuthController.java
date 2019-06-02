package com.gymclub.sso.controller;

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

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${gymclub.security.social.filter-processes-url}")
    private String oauthUrl;

    @RequestMapping("/login")
    public ResponseEntity loginInfo() {
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

        StringBuffer url = request.getRequestURL();
        url.delete(url.length() - request.getRequestURI().length(), url.length());
        Map<String, String> client = new HashMap<>();
        client.put("clientName", "Auth with Github");
        client.put("clientUrl", url.append(oauthUrl).append("/github").toString());

        return
                ResponseEntity.ok(Collections.singletonList(client))
                //ResponseEntity.ok(data)
                ;
    }
}
