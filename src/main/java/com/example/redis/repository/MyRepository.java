package com.example.redis.repository;

import com.example.redis.security.MyUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {

    public MyUserEntity findByUsername(String username){
        if(username.equals("ydong")) return new MyUserEntity("ydong", "1234", "ADMIN");
        return null;
    }
}
