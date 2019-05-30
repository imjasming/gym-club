package com.gymclub.sso.oauth.github.model;

import lombok.Data;

import javax.persistence.Id;

@Data
//@Entity
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
    private GithubPlan plan;
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

    public class GithubPlan {
        private String name;
        private Integer space;
        private Integer collaborators;
        private Integer privateRepos;

        public GithubPlan() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSpace() {
            return space;
        }

        public void setSpace(Integer space) {
            this.space = space;
        }

        public Integer getCollaborators() {
            return collaborators;
        }

        public void setCollaborators(Integer collaborators) {
            this.collaborators = collaborators;
        }

        public Integer getPrivateRepos() {
            return privateRepos;
        }

        public void setPrivateRepos(Integer privateRepos) {
            this.privateRepos = privateRepos;
        }
    }
}
