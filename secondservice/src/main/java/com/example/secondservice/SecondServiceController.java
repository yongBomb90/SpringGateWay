package com.example.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/second-service/")
public class SecondServiceController {

    Environment environment;

    @Autowired
    public SecondServiceController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("welcome")
    public String welcome(){
        return "Welcome to the SecondService";
    }

    @GetMapping("message")
    public String message(@RequestHeader("second-request") String header){
        System.out.println(header);
        log.info(header);
        return "hello world in SecondService : "+header;
    }

    @GetMapping("check")
    public String check(){
        log.info("server port : {}",environment.getProperty("local.server.port"));
        return "Hi, there. This is second Server port is "+environment.getProperty("local.server.port");
    }
}
