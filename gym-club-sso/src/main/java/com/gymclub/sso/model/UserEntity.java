package com.gymclub.sso.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {
    /**
     * id
     */
    private Integer id;

    private String loginName;
    /**
     * 登录名
     */
    private String username;
    /**
     *
     */
    private String email;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 主页
     */
    private String url;
    /**
     * 注册时间
     */
    private Date createTime;

    private Boolean status;
}
