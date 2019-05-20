package com.gymclub.core.service;


import com.gymclub.core.domain.primary.UmUser;
import com.gymclub.core.domain.secondary.UserInfo;
import com.gymclub.core.dto.UserProfile;
import com.gymclub.core.dto.UserSignUpRequest;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface UserService {
    UmUser createUser(UserSignUpRequest signUpParam);

    UmUser register(UserSignUpRequest signUpParam);

    //String login(String username, String password);

    UmUser getUserByName(String username);

    UserInfo getUserInfoByName(String username);

    UserInfo updateProfile(UserProfile newProfile, String username);

    //changePassword
    UmUser changePassword(String username, String oldPassword, String password);
}
