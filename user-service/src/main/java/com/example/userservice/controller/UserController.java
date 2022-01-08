package com.example.userservice.controller;

import com.example.userservice.vo.RequestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.userservice.vo.Greeting;


@RestController
public class UserController {

    private Environment environment;

    private Greeting greeting;

    @Autowired
    public UserController(Environment environment, Greeting greeting) {
        this.environment = environment;
        this.greeting = greeting;
    }

    @GetMapping("/heath_check")
    public String status(){
        return "It's Working User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return  greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser requestUser){

        return  greeting.getMessage();
    }
}
