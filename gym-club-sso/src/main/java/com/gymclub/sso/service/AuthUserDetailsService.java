package com.gymclub.sso.service;

import com.gymclub.sso.bo.MyUserDetails;
import com.gymclub.sso.model.UmUser;
import com.gymclub.sso.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Xiaoming.
 * Created on 2019/03/11 23:25.
 */
@Service("authUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByIdentify(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return (SocialUserDetails) getUserByIdentify(userId);
    }

    private UserDetails getUserByIdentify(String id) {
        UmUser user = userRepository.findByUsername(id);
        if (user == null) {
            throw new UsernameNotFoundException("用户名错误");
        } else {
            return new MyUserDetails(user);
        }
    }
}
