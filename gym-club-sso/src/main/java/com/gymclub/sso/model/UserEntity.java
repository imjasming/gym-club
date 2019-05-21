package com.gymclub.sso.model;

import lombok.Data;

@Data
public class UserEntity {
    /**
     * id
     */
    private int id;

    private String loginName;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     *
     */
    private String email;
    /**
     * 头像url
     */
    private String headimg;
    /**
     * GitHub主页
     */
    private String url;
    /**
     * 注册时间
     */
    private String createTime;

    private String githubid;

    private Integer status;

}
