package com.gymclub.sso.service;

import com.gymclub.sso.dto.CommonRestResult;
import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.dto.UserSocialBindParam;
import com.gymclub.sso.model.UmUser;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 21:36.
 */
public interface UserService {

    UmUser createUser(UserSignUpRequest request);

    CommonRestResult register(UserSignUpRequest signUpParam);

    CommonRestResult bindOAuthUser(UserSocialBindParam param);
}
