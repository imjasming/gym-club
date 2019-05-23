package com.gymclub.core.service.impl;

import com.gymclub.core.domain.primary.UmUser;
import com.gymclub.core.domain.secondary.UserInfo;
import com.gymclub.core.dto.UserProfile;
import com.gymclub.core.repository.primary.UserRepository;
import com.gymclub.core.repository.secondary.UserInfoRepository;
import com.gymclub.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 20:46.
 */
@Slf4j
@Service("userService")
//@CacheConfig(cacheNames = "com.xm.service.userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /*@Autowired
    private AuthUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;*/


    /*@Override
    public String login(String username, String password) {
        String token = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            final Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            token = jwtTokenUtil.generateToken(userDetails);

            //TODO: maybe we can update user's lastlogin or log
            log.info("user[{}] login", username);
        } catch (AuthenticationException e) {
            log.warn("user[{}] login failed: {}", username, e.getMessage());
            e.printStackTrace();
        }

        return token;
    }*/

    private UserInfo getInfoByUser(UmUser user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Override
    @Cacheable(value = "umuser", key = "#username")
    public UmUser getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    //@Cacheable(value = "userInfo", key = "#username")
    public UserInfo getUserInfoByName(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null) {
            userInfo = getInfoByUser(userRepository.findByUsername(username));
            userInfoRepository.save(userInfo);
        }
        return userInfo;
    }

    @Transactional
    @Override
    //@CachePut(key = "#username")
    public UserInfo updateProfile(UserProfile newProfile, String username) {
        final String email = newProfile.getEmail();
        if (userRepository.existsByEmail(email)) {
            return null;
        }
        userRepository.updateUmUserEmail(username, email);
        userInfoRepository.deleteById(username);

        final UserInfo userInfo = getInfoByUser(userRepository.findByUsername(username));
        userInfoRepository.save(userInfo);

        return userInfo;
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public UmUser changePassword(String username, String oldPassword, String newPassword) {
        UmUser user = userRepository.findUserByUsernameAndPassword(username, oldPassword);
        if (user == null) {
            return null;
        }
        userRepository.updateUmUserPassword(username, newPassword);
        return userRepository.findByUsername(username);
    }
}
