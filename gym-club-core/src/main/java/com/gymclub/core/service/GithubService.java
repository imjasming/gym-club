package com.gymclub.core.service;


import com.gymclub.core.domain.primary.UmUser;
import com.gymclub.core.dto.GithubAuthServiceResult;

/**
 * @author Xiaoming.
 * Created on 2019/05/15 00:11.
 */
public interface GithubService {
    UmUser getUserByGithubId(String id);

    GithubAuthServiceResult getTokenByUsername(String username);
}
