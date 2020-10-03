package sjtu.chenzf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sjtu.chenzf.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("chenzf")

public class UserController {

    @GetMapping("/findThymeleaf")
    public String findThymeleaf(HttpServletRequest request, Model model) {
        System.out.println("学习thymeleaf");

        model.addAttribute("name", "祖峰");
        model.addAttribute("username", "<a href=''>chenzf</a>");
        model.addAttribute("user", new User("1", "chenzufeng", 27, new Date()));

        List<User> users = Arrays.asList(new User("2", "chen", 26, new Date()),
                                                                new User("3", "zufeng", 25, new Date()));
        model.addAttribute("users", users);

        // 返回逻辑名，去resources/templates中寻找"逻辑名.html"
        return "index";
    }
}
