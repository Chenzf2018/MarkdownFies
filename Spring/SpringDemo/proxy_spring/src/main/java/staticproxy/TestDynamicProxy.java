package staticproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDynamicProxy {
    public static void main(String[] args) {

        // 目标类
        final UserService userService =  new UserServiceImpl();

        //参数1：当前线程类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        //参数2：目标对象接口类型的数组
        Class[] classes =  new Class[] {UserService.class};

        // 参数3：InvocationHandler接口类型，invoke方法用来设置额外功能
        // 返回值：创建好的动态代理对象
        UserService userServiceDynamicProxy = (UserService) Proxy.newProxyInstance(contextClassLoader, classes, new InvocationHandler() {
            @Override
            // 通过代理类对象调用方法时会优先进入参数三中的invoke方法
            // 参数1：当前创建好的代理对象；参数2：当前代理对象执行的方法对象；参数3：当前代理执行方法的参数封装成数组
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    System.out.println("当前执行的方法：" + method.getName());
                    System.out.println("当前执行的方法的参数：" + args[0]);
                    System.out.println();
                    System.out.println("开启事务");  //附加操作
                    System.out.println("通过反射，调用目标类业务对应的方法");
                    Object invoke = method.invoke(userService, args);
                    //Object invoke = method.invoke(new UserServiceImpl(), args);
                    System.out.println("提交事务");  //附加操作
                    return invoke;  // 返回值
                } catch (Exception e) {
                    System.out.println("回滚事务");  //附加操作
                }
                return null;
            }
        });

        // 通过代理优先执行代理中的操作
        String result = userServiceDynamicProxy.findAll("chenzufeng");
        System.out.println("代理返回值：" + result);
    }
}
