package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/first-service/")
public class FirstServiceController {

    Environment environment;

    @Autowired
    public FirstServiceController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("welcome")
    public String welcome(){

        return "Welcome to the FirstService";

    }

    @GetMapping("message")
    public String message(@RequestHeader("first-request") String header){
        System.out.println(header);
        log.info(header);
        return "hello world in firstService : "+header;
    }

    @GetMapping("check")
    public String check(){
        log.info("server port : {}",environment.getProperty("local.server.port"));
        return "Hi, there. This is First Server port is "+environment.getProperty("local.server.port");
    }

}
