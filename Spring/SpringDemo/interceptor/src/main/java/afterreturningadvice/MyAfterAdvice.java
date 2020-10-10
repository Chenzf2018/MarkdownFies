package afterreturningadvice;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

public class MyAfterAdvice implements AfterReturningAdvice, ThrowsAdvice {
    /**
     * 自定义后置通知与异常通知
     * @param returnValue 目标方法返回值（因为是先执行目标方法）
     * @param method 当前执行的方法
     * @param args 执行方法的参数
     * @param target 目标对象
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        System.out.println("===========进入后置通知===========");
        System.out.println("返回值：" + returnValue);
        System.out.println("方法名：" + method.getName());
        System.out.println("方法的参数：" + args[0]);
        System.out.println("目标对象：" + target);
    }

    /**
     * 出现异常时执行通知处理
     * @param method
     * @param args
     * @param target
     * @param ex
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println("==========进入异常通知==========");
        System.out.println("方法名：" + method.getName());
        System.out.println("方法的参数：" + args[0]);
        System.out.println("目标对象：" + target);
        System.out.println("异常信息：" + ex.getMessage());
    }
}
