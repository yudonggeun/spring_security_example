package com.example.redis.security;

import com.example.redis.repository.MyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyPrincipalService implements UserDetailsService {

    private final MyRepository repository;

    @Autowired
    public MyPrincipalService(MyRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername()");
        MyUserEntity user = repository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("not found");
        return new MyUserDetail(user);
    }
}
