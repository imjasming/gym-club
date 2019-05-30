package com.gymclub.sso.service;

import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.model.UmUser;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 21:36.
 */
public interface UserService {
    Integer createUser(UmUser newUser);

    UmUser createUser(UserSignUpRequest request);

    UmUser register(UserSignUpRequest signUpParam);
}
