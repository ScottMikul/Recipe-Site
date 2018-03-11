package com.teamtreehouse.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by scott on 6/19/2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/login**","/register**","/css/**","/images/**")
                    .permitAll()
                .anyRequest().authenticated().
                and().formLogin().loginPage("/login").permitAll()
                .and()
                    .httpBasic()
                .and()
                    .csrf()
                    .disable();
    }
}
