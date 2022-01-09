package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.userservice.vo.Greeting;


@RestController
public class UserController {

    private Environment environment;

    private Greeting greeting;

    private UserService userService;

    @Autowired
    public UserController(Environment environment, Greeting greeting,  UserService userService) {
        this.environment = environment;
        this.greeting = greeting;
        this.userService = userService;
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
    public ResponseEntity createUser(@RequestBody RequestUser requestUser){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDto = modelMapper.map(requestUser, UserDTO.class);

        userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(userDto,ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


}
