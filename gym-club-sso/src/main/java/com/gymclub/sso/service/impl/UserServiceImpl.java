package com.gymclub.sso.service.impl;

import com.gymclub.sso.dto.UserSignUpRequest;
import com.gymclub.sso.model.Role;
import com.gymclub.sso.model.RoleRepository;
import com.gymclub.sso.model.UmUser;
import com.gymclub.sso.model.UserRepository;
import com.gymclub.sso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    //@Autowired
    //private UserDao userDao;

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
    public Integer createUser(UmUser newUser) {
        /*if (newUser.getUsername() == null) {
            int nextId = userDao.getNextId();
            //newUser.setId(nextId);
            newUser.setUsername("uid_" + nextId);
        }
        createCommonUser(newUser, Role.RoleName.ROLE_USER);*/

        return newUser.getId();
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

    @Override
    public UmUser register(UserSignUpRequest signUpParam) {
        if (userRepository.findByUsername(signUpParam.getUsername()) != null || userRepository.findByEmail(signUpParam.getEmail()) != null) {
            log.warn("username or email exited: {} {}", signUpParam.getUsername(), signUpParam.getEmail());
            return null;
        }

        return createUser(signUpParam);
    }
}
