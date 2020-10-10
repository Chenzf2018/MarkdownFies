package com.chenzf.advices;

// 注意使用的是aopalliance包中的
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 代理
 * 自定义环绕通知来记录目标方法运行时长
 */
public class RunningTimeAdvice implements MethodInterceptor {

    /**
     *
     * @param invocation Spring将原始动态代理中的三个参数进行了包装
     *                   获取当前执行方法
     *                   获取当前执行方法参数
     *                   获取目标对象
     *                   放行目标方法的执行（拦截器）
     * @return
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("=========进入环绕通知=========");
        System.out.println("当前执行方法：" + invocation.getMethod().getName());
        System.out.println("方法的参数：" + invocation.getArguments()[0]);
        System.out.println("获取当前的目标对象：" + invocation.getThis());

        try {
            System.out.println("=========进入环绕通知，开始记录时间=========");
            System.out.println("使用System.currentTimeMillis()");
            long start = System.currentTimeMillis();

            System.out.println("=========使用proceed()放行目标方法，继续执行=========");
            Object proceedMethod = invocation.proceed();

            long end = System.currentTimeMillis();
            System.out.println("方法 " + invocation.getMethod().getName() + " 执行时长为：" + (end - start));

            return proceedMethod;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }

        return null;
    }
}
