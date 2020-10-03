package sjtu.chenzf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chenzf")

public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello Thymeleaf");
        return "Hello SpringBoot_Thymeleaf";
    }
}
