package com.gymclub.sso.service.impl;

import com.alibaba.fastjson.JSON;
import com.gymclub.sso.dto.CommonRestResult;
import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.dto.UserSocialBindParam;
import com.gymclub.sso.model.Role;
import com.gymclub.sso.model.RoleRepository;
import com.gymclub.sso.model.UmUser;
import com.gymclub.sso.model.UserRepository;
import com.gymclub.sso.oauth.utils.SocialConnectRedisHelper;
import com.gymclub.sso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Xiaoming.
 * Created on 2019/05/23 21:38.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SocialConnectRedisHelper redisHelper;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private void createCommonUser(UmUser newUser, Role.RoleName rname) {
        newUser.setLastPasswordReset(new Date());
        newUser.setEnable(true);

        if (!roleRepository.existsById(1)) {
            Role role1 = new Role(Role.RoleName.ROLE_USER);
            Role role2 = new Role(Role.RoleName.ROLE_ADMIN);
            roleRepository.save(role1);
            roleRepository.save(role2);
        }

        newUser.getRoles().add(roleRepository.findByName(rname));
        userRepository.save(newUser);
    }

    @Override
    public UmUser createUser(UserSignUpRequest signUpParam) {
        UmUser newUser = new UmUser();
        BeanUtils.copyProperties(signUpParam, newUser);

        final String password = signUpParam.getPassword();
        if (password != null) {
            final String rawPassword = passwordEncoder.encode(password);
            newUser.setPassword(rawPassword);
        }

        createCommonUser(newUser, Role.RoleName.ROLE_USER);
        return newUser;
    }

    private String validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return "用户名不能为空。";
        }
        if (userRepository.existsByUsername(username)) {
            return "用户名已存在。";
        }
        return null;
    }

    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "邮箱不能为空。";
        }
        if (userRepository.existsByUsername(email)) {
            return "邮箱已存在。";
        }
        return null;
    }

    @Override
    public CommonRestResult register(UserSignUpRequest param) {
        String message;
        if ((message = validateUsername(param.getUsername())) != null) {
            return CommonRestResult.failure(message);
        }
        if ((message = validateEmail(param.getEmail())) != null) {
            return CommonRestResult.failure(message);
        }
        return CommonRestResult.ok(createUser(param));
    }

    @Override
    public CommonRestResult bindOAuthUser(UserSocialBindParam param) {
        // Verify and get the cached data in redis
        ConnectionData connectionData = redisHelper.getConnectionData(param.getKey());
        if (connectionData == null) {
            return CommonRestResult.failure("需绑定数据已失效.");
        }
        // verify username and email
        String message;
        if ((message = validateUsername(param.getUsername())) != null) {
            return CommonRestResult.failure(message);
        }
        if ((message = validateEmail(param.getEmail())) != null) {
            return CommonRestResult.failure(message);
        }

        createUser(param);
        // create connection and save to db userConnection
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        log.info("========== connectionData {}", JSON.toJSONString(connectionData));
        usersConnectionRepository.createConnectionRepository(param.getUsername()).addConnection(connection);

        redisHelper.removeData(param.getKey());

        return CommonRestResult.ok();
    }
}
