package com.gymclub.core.domain;

import lombok.Data;

@Data
public class UserGithubEntity {

    private String login;

    private String avatarUrl;

    private String htmlUrl;

    private String email;

    private Integer userid;

}
