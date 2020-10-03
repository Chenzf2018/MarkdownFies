package sjtu.chenzf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sjtu.chenzf.entity.User;
import sjtu.chenzf.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/chenzf")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAllUser")
    public String findAllUser(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "showAllUser";
    }

    @GetMapping("/saveUser")  // 便于在地址栏操作
    public String saveUser(User user) {
        userService.saveUser(user);
        // 重定向至findAllUser去查询数据库再渲染到页面
        return "redirect:/chenzf/findAllUser";
    }
}
