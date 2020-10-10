package com.chenzf.controller;

import com.chenzf.entity.User;
import com.chenzf.vo.CollectionVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 用来测试参数接收
 */

@Controller
@RequestMapping("/ParamController")
public class ParamController {

    /**
     * 测试零散类型的参数接收
     * @return 页面
     */
    @RequestMapping("/testScatteredType")
    public String testScatteredType(String name, Integer age, Boolean sexual, Double salary, Date birth) {
        System.out.println("==========测试零散类型的参数接收==========");
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("性别：" + sexual);
        System.out.println("工资：" + salary);
        System.out.println("生日：" + birth);
        return "index";
    }

    /**
     * 测试对象类型的参数接收
     * @param user 接收的对象：直接将要接收对象作为控制器方法参数声明
     * @return 返回index.jsp
     */
    @RequestMapping("/testObject")
    public String testObject(User user, String name) {
        System.out.println("==========测试对象类型的参数接收==========");
        System.out.println("ParamController接收到的对象：" + user);
        System.out.println("ParamController接收到的对象的姓名：" + name);
        return "index";
    }

    /**
     * 测试数组类型参数接收
     * @return 返回index.jsp
     */
    @RequestMapping("/testArray")
    public String testArray(String[] names) {
        System.out.println("==========测试数组类型的参数接收==========");
        for (String name : names) {
            System.out.println(name);
        }
        return "index";
    }

    /**
     * 测试集合类型List的参数接收
     * @param collectionVO 接收的对象
     * @return 返回index.jsp
     */
    @RequestMapping("/testCollectionList")
    public String testCollectionList(CollectionVO collectionVO) {
        System.out.println("==========测试集合类型List的参数接收==========");
        collectionVO.getLists().forEach(str -> System.out.println(str));
        return "index";
    }

    /**
     * 测试集合类型Map的参数接收
     * @param collectionVO 接收的对象
     * @return 返回index.jsp
     */
    @RequestMapping("/testCollectionMap")
    public String testCollectionMap(CollectionVO collectionVO) {
        System.out.println("==========测试集合类型Map的参数接收==========");
        collectionVO.getMap().forEach((key, value) -> System.out.println("key: " + key + (", value: ") + value));
        return "index";
    }
}
