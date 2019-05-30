package com.gymclub.sso.oauth.github.connect;

import com.gymclub.sso.oauth.github.api.GithubApi;
import com.gymclub.sso.oauth.github.model.GithubEntity;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 23:33.
 */
public class GithubAdapter implements ApiAdapter<GithubApi> {
    @Override
    public boolean test(GithubApi api) {
        return true;
    }

    @Override
    public void setConnectionValues(GithubApi api, ConnectionValues values) {
        GithubEntity user = api.getGithubEntity();

        values.setProviderUserId(user.getId().toString());
        values.setDisplayName(user.getLogin());
        values.setImageUrl(user.getAvatarUrl());
        values.setProfileUrl(user.getUrl());
    }

    @Override
    public UserProfile fetchUserProfile(GithubApi api) {
        GithubEntity entity = api.getGithubEntity();
        return new UserProfile(entity.getId().toString(), entity.getName(), null, null, entity.getEmail(), entity.getLogin());
    }

    @Override
    public void updateStatus(GithubApi api, String message) {
        //TODO
    }
}
