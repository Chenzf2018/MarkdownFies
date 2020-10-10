package service;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 记录业务方法名称
 * 自定义前置通知
 */
public class MyBeforeAdvice implements MethodBeforeAdvice {
    /**
     * 封装了动态代理的invoke方法
     * 前置通知：目标方法执行前先执行的额外操作
     * @param method 当前执行的方法的对象
     * @param args 当前执行方法的参数
     * @param target 目标对象——被代理的对象EmpServiceImpl
     */
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("前置通知");
        System.out.println("当前执行的方法：" + method.getName());
        // 如果没有参数会出现空指针
        System.out.println("当前执行方法的参数：" + args[0]);
        System.out.println("目标对象：" + target);
        System.out.println();
    }
}
