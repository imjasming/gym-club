package com.gymclub.sso.dto;

import lombok.Data;

/**
 * @author Xiaoming.
 * Created on 2019/05/29 20:54.
 */
@Data
public class SocialConnectionStatus {
    private String provider;
    private boolean status;

    public SocialConnectionStatus withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public SocialConnectionStatus withStatus(boolean status) {
        this.status = status;
        return this;
    }
}
