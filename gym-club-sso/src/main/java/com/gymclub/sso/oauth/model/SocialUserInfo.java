package com.gymclub.sso.oauth.model;

import lombok.Data;

/**
 * @author Xiaoming.
 * Created on 2019/05/28 15:39.
 */
@Data
public class SocialUserInfo {
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imgUrl;
}
