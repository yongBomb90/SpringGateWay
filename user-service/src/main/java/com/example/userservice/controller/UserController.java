package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Greeting;


@RestController
public class UserController {

    private Environment environment;

    private Greeting greeting;

    @Autowired
    public UserController(Environment environment, Greeting greeting) {
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");

        this.environment = environment;
        this.greeting = greeting;
    }

    @GetMapping("/heath_check")
    public String status(){
        return "It's Working User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
        greeting.getMessage();
        return environment.getProperty("greeting.message");
    }
}
