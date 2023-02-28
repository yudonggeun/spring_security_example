package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebSecurityCustomizer {

    @Bean
    PasswordEncoder setPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }

    @Bean
    SecurityFilterChain setSecurityConfig(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()

                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.info("권한 없는 사용자 접근");
                    response.sendRedirect("/home");
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    HttpSession session = request.getSession();
                    Iterator<String> stringIterator = session.getAttributeNames().asIterator();
                    while (stringIterator.hasNext()) {
                        String name = stringIterator.next();
                        log.info("name : " + name);
                    }
                    log.info("가입되지 않은 사용자 접근");
                    response.sendRedirect("/home");
                })
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()

                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()

                .formLogin()
                .loginPage("/login_form")
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl("/success")

                .and()
                .build();
    }

    @Override
    public void customize(WebSecurity web) {

    }
}
