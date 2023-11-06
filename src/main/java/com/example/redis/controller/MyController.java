package com.example.redis.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Controller
public class MyController {

    @GetMapping("/home")
    public String home() {
        return "home.html";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/success")
    public String loginSuccess(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Iterator<String> iter = session.getAttributeNames().asIterator();
        while (iter.hasNext()) {
            String name = iter.next();
        }
        return "success.html";
    }

    @Secured("ROLE_ADMIN")//ROLE_ prefix 설정을 하지 않으면 이상하게 동작하는데 ?
    @GetMapping("/role")
    public String loginLimitPage() {
        return "role.html";
    }

    @GetMapping("/login_form")
    public String formPage() {
        return "loginForm.html";
    }
}
