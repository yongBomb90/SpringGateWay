package com.example.userservice.security;


import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCyptEncoder;
    private Environment environment;

    @Autowired
    public WebSecurity(UserService userService, BCryptPasswordEncoder bCyptEncoder, Environment environment) {
        this.userService = userService;
        this.bCyptEncoder = bCyptEncoder;
        this.environment = environment;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
    //    http.authorizeRequests().antMatchers("/users/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("127.0.0.1")
                .and()
                .addFilter(getAuthentionFilter())

        ;

        http.headers().frameOptions().disable();


    }

    private AuthenticationFilter getAuthentionFilter() throws Exception {

        AuthenticationFilter authenticationFilter
                = new AuthenticationFilter(authenticationManager(),userService,environment);
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCyptEncoder);
    }
}
