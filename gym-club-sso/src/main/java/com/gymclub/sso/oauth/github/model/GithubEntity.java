package com.gymclub.sso.oauth.github.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class GithubEntity {

    private String gistsUrl;
    private String reposUrl;
    private Boolean twoFactorAuthentication;
    private String followingUrl;
    private String bio;
    private String createdAt;
    private String login;
    private String type;
    private String blog;
    private Integer privateGists;
    private Integer totalPrivateRepos;
    private String subscriptionsUrl;
    private String updatedAt;
    private Boolean siteAdmin;
    private Integer diskUsage;
    private Integer collaborators;
    private String company;
    private Integer ownedPrivateRepos;
    @Id
    private Integer id;
    private Integer publicRepos;
    private String gravatarId;
    private String plan;
    private String email;
    private String organizationsUrl;
    private String hireable;
    private String starredUrl;
    private String followersUrl;
    private Integer publicGists;
    private String url;
    private String receivedEventsUrl;
    private Integer followers;
    private String avatarUrl;
    private String eventsUrl;
    private String htmlUrl;
    private Integer following;
    private String name;
    private String location;
    private String nodeId;

    public GithubEntity() {
    }
}
