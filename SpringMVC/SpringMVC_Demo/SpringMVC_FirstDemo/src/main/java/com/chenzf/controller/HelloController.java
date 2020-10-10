package com.chenzf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Controller 作用：在类上标识这是一个控制器组件类，
 *                                  并创建这个类实例用来创建HelloController对象
 *                       参数：工厂中唯一标识
 * @author Chenzf
 */
@Controller("helloController")
@RequestMapping("/HelloSpringMVC")
public class HelloController {

    /**
     * RequestMapping 用来指定类以及类中方法的请求路径
     * @return 返回页面逻辑名
     */
    @RequestMapping(value = "/hello")
    public String hello() {
        // 1.收集数据

        // 2.调用业务方法
        System.out.println("HelloController控制器中调用业务方法！");

        // 3.处理响应：返回页面逻辑名
        return "index";
    }
}
