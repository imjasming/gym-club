package com.gymclub.sso.controller;

import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.model.UmUser;
import com.gymclub.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 17:49.
 */
@RestController
public class UserSignUpController {
    @Autowired
    private UserService userService;

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
}
