package com.gymclub.sso.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gymclub.sso.model.UmUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Xiaoming.
 * Created on 2019/03/14 01:10.
 */
public class MyUserDetails implements UserDetails, SocialUserDetails {
    private final UmUser user;

    public MyUserDetails(UmUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .filter(Objects::nonNull)
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return
                user.getPassword()
                ;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return user.getLastPasswordReset();
    }

    @Override
    public String getUserId() {
        return user.getUsername();
    }
}
