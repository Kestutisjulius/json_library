package com.example.Library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * /myAccount
     * /welcome
     * /register
     * /adminPage
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.formLogin();
        http.headers().frameOptions().disable(); //for h2 database visiblity in browser
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/myAccount").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/books").hasAuthority("ADMIN")
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/welcome").permitAll()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
