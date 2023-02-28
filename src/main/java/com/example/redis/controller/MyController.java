package com.example.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.web.servlet.session.SessionSecurityMarker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Slf4j
@Controller
public class MyController {

    @GetMapping("/home")
    public String home() {
        log.info("home log");
        return "home.html";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/success")
    public String loginSuccess(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Iterator<String> iter = session.getAttributeNames().asIterator();
        while (iter.hasNext()) {
            String name = iter.next();
            log.info(name + " : " + session.getAttribute(name));
        }
        log.info("login success");
        return "success.html";
    }

    @Secured("ROLE_ADMIN")//ROLE_ prefix 설정을 하지 않으면 이상하게 동작하는데 ?
    @GetMapping("/role")
    public String loginLimitPage() {
        log.info("loginLimitPage");
        return "role.html";
    }

    @GetMapping("/login_form")
    public String formPage() {
        log.info("login form");
        return "loginForm.html";
    }
}
