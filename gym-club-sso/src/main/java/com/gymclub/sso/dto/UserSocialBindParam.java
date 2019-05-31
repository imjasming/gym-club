package com.gymclub.sso.dto;

/**
 * @author Xiaoming.
 * Created on 2019/05/30 19:28.
 */

public class UserSocialBindParam extends UserSignUpRequest {
    private String key;

    public UserSocialBindParam() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
