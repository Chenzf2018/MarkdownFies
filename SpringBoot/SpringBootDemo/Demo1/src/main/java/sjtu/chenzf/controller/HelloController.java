package sjtu.chenzf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sjtu.chenzf.entity.User;
import sjtu.chenzf.service.UserService;
import sjtu.chenzf.service.UserServiceImp;

import java.util.Calendar;

@RestController
@RequestMapping("/chenzf")  // 为避免路径冲突，再添加一级命名空间

public class HelloController {

    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private Calendar calendar;

    @Autowired
    private Calendar calendar1;

    @GetMapping("/hello")  // 最好与方法名对应
    public String hello() {
        System.out.println("Hello Spring Boot !");
        System.out.println(user);
        userService.save("chenzufeng");

        System.out.println(calendar.getTime());
        System.out.println(calendar);
        System.out.println(calendar1);
        System.out.println(calendar == calendar1);

        return "hello springboot";
    }
}
