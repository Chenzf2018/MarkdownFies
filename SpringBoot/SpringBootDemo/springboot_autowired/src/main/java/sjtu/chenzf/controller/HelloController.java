package sjtu.chenzf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sjtu.chenzf.entity.User;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chenzf")

public class HelloController {

    @Value("${name}")
    private String name;

    @Value("${server.port}")
    private int port;

    @Value("${stringList}")
    private List<String> stringList;

    @Value("#{${map}}")
    private Map<String, String> map;

    @Autowired
    private User user;

    @GetMapping("hello")
    public String hello() {
        System.out.println("Spring Boot 注入");

        System.out.println("验证注入是否有效：");

        System.out.println("属性注入");
        System.out.println("name = " + name);
        System.out.println("server.port = " + port);
        stringList.forEach(item -> System.out.println("item = " + item));
        map.forEach((key, value) -> System.out.println("key = " + key + "; value = " + value));

        System.out.println("对象注入");
        System.out.println("user = " + user);

        return "学习Spring Boot注入";
    }
}
