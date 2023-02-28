package com.example.redis.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public class MyUserDetail implements UserDetails {

    private final MyUserEntity myUserEntity;

    public MyUserDetail(MyUserEntity myUserEntity) {
        this.myUserEntity = myUserEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("권한 확인");
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + myUserEntity.getRole()));
    }

    @Override
    public String getPassword() {
        return myUserEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return myUserEntity.getUsername();
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
        return true;
    }
}
