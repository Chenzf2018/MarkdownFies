# 基本概念
* 一个进程(process)可以有多个线程(thread)：视频中声音与画面；
* 进程是程序的一次执行过程；真正的多线程是指有多个CPU（线程是CPU调度和执行的单位）。在一个CPU情况下，在一个时间点，CPU只能执行一个代码，模拟出来的多线程是因为切换得很快，产生同时执行的假象；
* 对一份资源，会存在资源抢夺的问题，需要加入并发控制。

# 线程的实现(重点)
* 继承`Thread类`（重点）
* 实现`Runnable接口`（核心）
* 实现`Callable接口`

## 继承`Thread类`
* 将一个类声明为`Thread`的子类；
* 重写`run`方法；
* 创建实例，调用`start`方法启动线程。
```java
package Thread;

/*
创建线程方法一：继承Thread类；重写run方法；创建实列，调用start开启线程
 */

public class CreateThread extends Thread
{
    @Override
    public void run()
    {
        // run方法线程体
        for (int i = 0; i < 200; i++)
            System.out.println("In run() method! " + i);
    }

    public static void main(String[] args)
    {
        CreateThread thread = new CreateThread();
        thread.start();
        // thread.run();

        for (int i = 0; i < 200; i++)
            System.out.println("In main() method! " + i);
    }
}

/*
Output:
start()
In main() method! 14
In main() method! 15
In run() method! 0
In run() method! 1
In run() method! 2
In run() method! 3
In run() method! 4
In run() method! 5
In main() method! 16
In main() method! 17

run():
In run() method! 196
In run() method! 197
In run() method! 198
In run() method! 199
In main() method! 0
In main() method! 1
In main() method! 2
In main() method! 3
 */
 ```
 `main, start`方法是交替进行的；而`main, run`方法则有先后之分。
 <div align=center><img src=Thread/普通方法调用和多线程.png width=80%></div>

### 案例：下载图片
下载`Commons IO`，复制到工程目录下，按`Add as Library`。
```java
package Thread;

/*
案例：下载图片
 */

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ThreadWebDownloader extends Thread
{
    private String url;  // 图片地址
    private String name;  // 保存的文件名

    public ThreadWebDownloader(String url, String name)
    {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run()
    {
        // 下载图片线程
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("将下载的图片命名为 " + name);
    }

    public static void main(String[] args)
    {
        // 创建线程实例
        ThreadWebDownloader threadWebDownloader1 = new ThreadWebDownloader(
                "http://5b0988e595225.cdn.sohucs.com/images/20180927/f245ec2141d84315ae98b0038f044eab.jpeg", "扬州1.jpeg"
        );
        ThreadWebDownloader threadWebDownloader2 = new ThreadWebDownloader(
                "http://res.yznews.cn/a/12163/201904/1e0c4d4be49aa367c10a6a26cb3988f6.jpeg", "扬州2.jpeg"
        );
        ThreadWebDownloader threadWebDownloader3 = new ThreadWebDownloader(
                "http://photocdn.sohu.com/20160227/mp60782450_1456509627780_16.jpeg", "扬州3.jpeg"
        );

        // 启动线程
        threadWebDownloader1.start();
        threadWebDownloader2.start();
        threadWebDownloader3.start();
    }
}

// 下载器
class WebDownloader
{
    // 下载方法
    public void downloader(String url, String name)
    {
        try
        {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("IO异常，downloader方法产生问题！");
        }
    }
}

/*
Output:
将下载的图片命名为 扬州3.jpeg
将下载的图片命名为 扬州2.jpeg
将下载的图片命名为 扬州1.jpeg
 */
 ```

 ## 实现`Runnable`

 * 定义`MyRunnable`类实现`Runnable`接口；
 * 实现`run()`方法，编写线程执行体；
 * 创建线程对象（执行线程需要丢入`Runnable`接口实现类的对象），调用`start()`方法启动线程。
```java
package Thread;

/*
创建线程方法二：实现Runnable接口；重写run方法；执行线程需要丢入Runnable接口，调用start开启线程
 */

public class CreateThread2 implements Runnable
{
    @Override
    public void run()
    {
        for (int i = 0; i < 200; i++)
            System.out.println("In run() method! " + i);
    }

    public static void main(String[] args)
    {
        // 创建实现Runnable接口的类对象
        CreateThread2 createThread = new CreateThread2();

        /*
        // 创建线程对象，丢入实现Runnable接口的类对象
        Thread thread = new Thread(createThread);

        // 开启线程
        thread.start();
        */

        new Thread(createThread).start();

        for (int i = 0; i < 200; i++)
            System.out.println("In main() method! " + i);
    }
}
/*
Output:
In main() method! 141
In main() method! 142
In run() method! 71
In run() method! 72
In run() method! 73
In run() method! 74
In run() method! 75
In run() method! 76
In run() method! 77
In run() method! 78
In run() method! 79
In run() method! 80
In run() method! 81
In main() method! 143
In main() method! 144
 */
 ``` 

 ### 初识并发问题
 ```java
 package Thread;

/*
实现Runnable接口
 */

public class BuyTicket implements Runnable
{
    private int numberOfTickets = 10;

    @Override
    public void run()
    {
        while (true)
        {
            if (numberOfTickets <= 0)
                break;

            System.out.println(Thread.currentThread().getName() + " 获得第 " + numberOfTickets-- + " 票！");
        }
    }

    public static void main(String[] args)
    {
        BuyTicket buyTicket = new BuyTicket();

        new Thread(buyTicket, "chen").start();
        new Thread(buyTicket, "zu").start();
        new Thread(buyTicket, "feng").start();
    }
}
/*
Output:
feng 获得第 10 票！
zu 获得第 9 票！
chen 获得第 10 票！
zu 获得第 7 票！
feng 获得第 8 票！
zu 获得第 5 票！
chen 获得第 6 票！
zu 获得第 3 票！
feng 获得第 4 票！
zu 获得第 1 票！
chen 获得第 2 票！
 */
 ```
 结果显示多个线程获得同一张票：`feng 获得第 10 票！...chen 获得第 10 票！`。即<font color=red>多个线程操作同一个资源情况下，线程不安全！</font>

 ### 案例：龟兔赛跑
 ```java
 package Thread;

/*
模拟龟兔赛跑
 */

public class Race implements Runnable
{
    private static String winner;

    @Override
    public void run()
    {
        for (int i = 1; i <= 100; i++)
        {
            if (gameOver(i))
                break;

            System.out.println(Thread.currentThread().getName() + " 跑了 " + i + " 步！");
        }

        //System.out.println("比赛结束！");
    }

    // 判断比赛是否结束
    /*
    private boolean gameOver(int step)
    {
        if (step == 100)
        {
            System.out.println("Winner is " + Thread.currentThread().getName() + " !");
            return true;
        }

        return false;
    }*/
    /*
    Output:
    兔子 跑了 19 步！
    Winner is 乌龟 !
    兔子 跑了 20 步！
    .........
    兔子 跑了 99 步！
    Winner is 兔子 !
     */

    private boolean gameOver(int step)
    {
        if (winner != null)  // 一方胜利后，确保比赛结束！
            return true;

        if (step == 100)
        {
            winner = Thread.currentThread().getName();
            System.out.println("Winner is " + winner);
            return true;
        }

        return false;
    }

    public static void main(String[] args)
    {
        Race race = new Race();  // 一条赛道

        new Thread(race, "乌龟").start();
        new Thread(race, "兔子").start();
    }
}
/*
Output:
兔子 跑了 99 步！
乌龟 跑了 38 步！
Winner is 兔子
 */
```

 ### 小结
 继承`Thread`类：
 * 子类继承`Thread`类，具备多线程能力；
 * 启动线程：`子类对象.start()`。
 * 不建议使用：OOP单继承的局限性。

实现`Runnable`接口：
* 实现`Runnable`接口，具有多线程能力；
* 启动线程：`传入实现接口类的对象 + Thread对象.start()`。
* 推荐使用：避免了OOP单继承的局限性；方便同一个对象被多个线程使用。
```java
// 一份资源
StartThread station = new StartThread();
// 多个代理
new Thread(station, "chen").start();
new Thread(station, "zu").start();
new Thread(station, "feng").start();
 ```

## 实现`Callable`接口
* 实现`Callable`接口，需要返回值类型；
* 重写`call`方法，需要抛出异常；
* 创建目标对象；
* 创建执行服务（开启服务）：`ExecutorService ser = Executors.newFixedThreadPool(1)`；
* 提交执行：`Future<Boolean> result1 = ser.submit(t1)`；
* 获取结果：`boolean r1 = result1.get()`；
* 关闭服务：`ser.shutdownNow()`。
```java
package Thread;

/*
线程创建方式三：实现Callable接口
 */

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

public class CreateThread3 implements Callable<Boolean>
{
    private String url;  // 图片地址
    private String name;  // 保存的文件名

    public CreateThread3(String url, String name)
    {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call()
    {
        // 下载图片线程
        WebDownloader2 webDownloader = new WebDownloader2();
        webDownloader.downloader(url, name);
        System.out.println("将下载的图片命名为 " + name);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        // 创建线程实例
        CreateThread3 webDownloader1 = new CreateThread3(
                "http://5b0988e595225.cdn.sohucs.com/images/20180927/f245ec2141d84315ae98b0038f044eab.jpeg", "扬州1.jpeg"
        );
        CreateThread3 webDownloader2 = new CreateThread3(
                "http://res.yznews.cn/a/12163/201904/1e0c4d4be49aa367c10a6a26cb3988f6.jpeg", "扬州2.jpeg"
        );
        CreateThread3 webDownloader3 = new CreateThread3(
                "http://photocdn.sohu.com/20160227/mp60782450_1456509627780_16.jpeg", "扬州3.jpeg"
        );

        // 创建执行服务
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> r1 = executorService.submit(webDownloader1);
        Future<Boolean> r2 = executorService.submit(webDownloader2);
        Future<Boolean> r3 = executorService.submit(webDownloader3);

        // 获取结果
        boolean result1 = r1.get();
        boolean result2 = r2.get();
        boolean result3 = r3.get();

        // 关闭服务
        executorService.shutdown();
    }
}

// 下载器
class WebDownloader2
{
    // 下载方法
    public void downloader(String url, String name)
    {
        try
        {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("IO异常，downloader方法产生问题！");
        }
    }
}
 ```
 `Callable`可以定义返回值；抛出异常。

# 知识点补充

## 静态代理
&emsp;&emsp;静态代理模式：真实对象和代理对象都要实现同一个接口；代理对象要代理真实角色。

好处：
* 代理对象可以做很多真实对象做不了的事情；
* 真实对象专注自己该做的事情。

```java
package Thread;

public class StaticProxy
{
    public static void main(String[] args)
    {
        WeddingCompany weddingCompany = new WeddingCompany(new People());
        weddingCompany.HappyMarry();
    }
}

interface Marry
{
    void HappyMarry();
}

// 真实角色
class People implements Marry
{
    @Override
    public void HappyMarry(){System.out.println("Get married.");}
}

// 代理角色
class WeddingCompany implements Marry
{
    // 代理真实目标角色
    private Marry target;

    public WeddingCompany(Marry target)
    {
        this.target = target;
    }

    @Override
    public void HappyMarry()
    {
        before();
        this.target.HappyMarry();  // 这是真实对象
        after();
    }

    private void before(){System.out.println("结婚前：婚庆公司布置现场！");}
    private void after(){System.out.println("结婚后：婚庆公司收尾款！");}
}
/*
结婚前：婚庆公司布置现场！
Get married.
结婚后：婚庆公司收尾款！
 */
 ```

 对比与多线程关系：
 ```java
 // Thread代理了System.out.println("Get married.")
 new Thread(() -> System.out.println("Get married.")).start();

 new WeddingCompany(new People()).HappyMarry();
 ```


## `Lambda`表达式

&emsp;&emsp;可以避免匿名内部类定义过多；留下核心的逻辑，让代码看起来简洁。
`new Thread(() -> System.out.println("Get married.")).start();`

&emsp;&emsp;函数式接口的定义：任何接口，如果只包含唯一一个抽象方法，那么它就是一个函数式接口。
```java
public interface Runnable
{
    public abstract void run();
}
 ```
对于函数式接口，可以通过`Lambda`表达式来创建该接口的对象。下面将一步步展现代码简化过程：

1. 使用外部类`：
```java {.line-numbers highlight=20}
package Thread;

public class TestLambda
{
    public static void main(String[] args)
    {
        // 创建一个接口对象
        FunctionalInterface functionalInterface = new FunctionalInterface1();
        functionalInterface.testLambda();
    }
}

// 1. 定义一个函数式接口
interface FunctionalInterface
{
    void testLambda();
}

// 2. 实现类
class FunctionalInterface1 implements FunctionalInterface
{
    @Override
    public void testLambda(){System.out.println("尚未使用Lambdad！");}
}
```

2. 使用静态内部类：
```java {.line-numbers highlight=6}
package Thread;

public class TestLambda
{
    // 3. 使用静态内部类
    static class FunctionalInterface2 implements FunctionalInterface
    {
        @Override
        public void testLambda(){System.out.println("使用静态内部类！");}
    }

    public static void main(String[] args)
    {
        // 创建一个接口对象
        FunctionalInterface functionalInterface = new FunctionalInterface1();
        functionalInterface.testLambda();

        functionalInterface = new FunctionalInterface2();
        functionalInterface.testLambda();
    }
}

// 1. 定义一个函数式接口
interface FunctionalInterface
{
    void testLambda();
}

// 2. 实现类
class FunctionalInterface1 implements FunctionalInterface
{
    @Override
    public void testLambda(){System.out.println("尚未使用Lambda！");}
}
```
3. 局部内部类：
```java {.line-numbers highlight=22}
package Thread;

public class TestLambda
{
    // 3. 使用静态内部类
    static class FunctionalInterface2 implements FunctionalInterface
    {
        @Override
        public void testLambda(){System.out.println("使用静态内部类！");}
    }

    public static void main(String[] args)
    {
        // 创建一个接口对象
        FunctionalInterface functionalInterface = new FunctionalInterface1();
        functionalInterface.testLambda();

        functionalInterface = new FunctionalInterface2();
        functionalInterface.testLambda();

        // 4. 局部内部类
        class FunctionalInterface3 implements FunctionalInterface
        {
            @Override
            public void testLambda(){System.out.println("使用局部内部类！");}
        }

        functionalInterface = new FunctionalInterface3();
        functionalInterface.testLambda();
    }
}

// 1. 定义一个函数式接口
interface FunctionalInterface
{
    void testLambda();
}

// 2. 实现类
class FunctionalInterface1 implements FunctionalInterface
{
    @Override
    public void testLambda(){System.out.println("尚未使用Lambda！");}
}
```
4. 匿名内部类：
```java {.line-numbers highlight=32}
package Thread;

public class TestLambda
{
    // 3. 使用静态内部类
    static class FunctionalInterface2 implements FunctionalInterface
    {
        @Override
        public void testLambda(){System.out.println("使用静态内部类！");}
    }

    public static void main(String[] args)
    {
        // 创建一个接口对象
        FunctionalInterface functionalInterface = new FunctionalInterface1();
        functionalInterface.testLambda();

        functionalInterface = new FunctionalInterface2();
        functionalInterface.testLambda();

        // 4. 局部内部类
        class FunctionalInterface3 implements FunctionalInterface
        {
            @Override
            public void testLambda(){System.out.println("使用局部内部类！");}
        }

        functionalInterface = new FunctionalInterface3();
        functionalInterface.testLambda();

        // 5. 匿名内部类：没有类的名称，必须借助接口或者父类
        functionalInterface = new FunctionalInterface()
        {
            @Override
            public void testLambda() { System.out.println("匿名内部类"); }
        };
        functionalInterface.testLambda();
    }
}

// 1. 定义一个函数式接口
interface FunctionalInterface
{
    void testLambda();
}

// 2. 实现类
class FunctionalInterface1 implements FunctionalInterface
{
    @Override
    public void testLambda(){System.out.println("尚未使用Lambda！");}
}
```

5. 使用`Lambda`：
```java {.line-numbers highlight=40}
package Thread;

public class TestLambda
{
    // 3. 使用静态内部类
    static class FunctionalInterface2 implements FunctionalInterface
    {
        @Override
        public void testLambda(){System.out.println("使用静态内部类！");}
    }

    public static void main(String[] args)
    {
        // 创建一个接口对象
        FunctionalInterface functionalInterface = new FunctionalInterface1();
        functionalInterface.testLambda();

        functionalInterface = new FunctionalInterface2();
        functionalInterface.testLambda();

        // 4. 局部内部类
        class FunctionalInterface3 implements FunctionalInterface
        {
            @Override
            public void testLambda(){System.out.println("使用局部内部类！");}
        }

        functionalInterface = new FunctionalInterface3();
        functionalInterface.testLambda();

        // 5. 匿名内部类：没有类的名称，必须借助接口或者父类
        functionalInterface = new FunctionalInterface()
        {
            @Override
            public void testLambda() { System.out.println("匿名内部类"); }
        };
        functionalInterface.testLambda();

        // 6. 使用Lambda简化
        functionalInterface = () -> { System.out.println("使用Lambda");};  // ()中可添加参数
        functionalInterface.testLambda();

    }
}

// 1. 定义一个函数式接口
interface FunctionalInterface
{
    void testLambda();
}

// 2. 实现类
class FunctionalInterface1 implements FunctionalInterface
{
    @Override
    public void testLambda(){System.out.println("尚未使用Lambda！");}
}
```

### `Lambda`表达式简化
```java
package Thread;

public class TestLambda2
{
    public static void main(String[] args)
    {
        FunctionInterface functionInterface = (int a) ->
        {
            System.out.println(a);
        };
        functionInterface.testLambda(100);

        // 简化参数类型
        functionInterface = (a) ->
        {
            System.out.println(a);
        };
        functionInterface.testLambda(200);

        // 简化括号
        functionInterface = a ->
        {
            System.out.println(a);
        };
        functionInterface.testLambda(300);

        // 简化花括号(只有一行时能简化)
        functionInterface = a -> System.out.println(a);
        functionInterface.testLambda(400);
    }


}

interface FunctionInterface
{
    void testLambda(int a);
}
```
`Lambda`表达式只能在只有一行代码的情况下进行简化，如果有多行，必须得用代码块包裹；接口必须是函数式接口；多个参数也可以简化参数类型，但需要加括号。


# 线程状态
线程的五大状态：
<div align=center><img src=Thread/线程五大状态.png width=80%></div>
<div align=center><img src=Thread/线程五大状态2.png width=80%></div>

线程方法：
<div align=center><img src=Thread/线程方法.png width=60%></div>

## 停止线程
* 不推荐使用`JDK`提供的`stop(), destroy()`方法（已废弃）。
* 推荐线程自己停下来；
* 建议使用一个标志位进行终止变量：当`flag == false`，则终止线程。
```java
package Thread;

/*
* 1. 建议线程自动停止：利用次数
* 2. 使用标志位
 */

public class StopThread implements Runnable
{
    // 1. 设置一个线程体中使用的标识
    private boolean flag = true;

    @Override
    public void run()
    {
        int i = 0;
        // 2. 线程中使用该标识
        while (flag)
            System.out.println("正在运行线程：" + i++);
    }

    // 3. 设置一个公开的方法停止线程，转换标志位
    public void stopThreadMethod(){ this.flag = false; }

    public static void main(String[] args)
    {
        StopThread thread1 = new StopThread();
        new Thread(thread1).start();

        for (int j = 0; j < 1000; j++)
        {
            System.out.println("正在运行main线程：" + j);
            if (j == 900)
            {
                thread1.stopThreadMethod();
                System.out.println("main线程运行至：" + j + " 时，线程停止！");
            }
        }
    }
}
/*
正在运行线程：1286
正在运行线程：1287
正在运行main线程：900
正在运行线程：1288
main线程运行至：900 时，线程停止！
正在运行main线程：901
正在运行main线程：902
 */
 ```

## 线程休眠
* `sleep`指定当前线程阻塞的毫秒时间，进入阻塞状态；
* `sleep`存在异常`InterruptedException`；
* `sleep`时间到达后，线程进入就绪状态；
* `sleep`可以模拟网络延时、倒计时等；
* 每一个对象都有一个锁，`sleep`不会释放锁。

### 模拟网络延时
```java
package Thread;

/*
* 模拟网络延时
 */

public class SleepThread implements Runnable
{
    private int ticketNumber = 10;

    @Override
    public void run()
    {
        while (true)
        {
            if (ticketNumber <= 0)
                break;

            // 模拟延时放大问题
            try
            {
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "购买了第 " + ticketNumber-- + " 票！");
        }
    }

    public static void main(String[] args)
    {
        SleepThread sleepThread = new SleepThread();
        new Thread(sleepThread, "chen").start();
        new Thread(sleepThread, "zu").start();
    }
}
/*
zu购买了第 10 票！
chen购买了第 8 票！
zu购买了第 8 票！
zu购买了第 7 票！
chen购买了第 7 票！
chen购买了第 6 票！
zu购买了第 6 票！
 */
```

### 模拟倒计时
```java
package Thread;

/*
* 模拟倒计时
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepThread1
{
    public static void main(String[] args)
    {
        /*
        try
        {
            countDown();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/

        // 输出当前系统时间
        Date startTime = new Date(System.currentTimeMillis());  // 获取当前时间
        while (true)
        {
            try
            {
                Thread.sleep(1000);
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(startTime));
                startTime = new Date(System.currentTimeMillis());  // 更新时间
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    // 模拟倒计时
    public static void countDown() throws InterruptedException
    {
        int number = 10;
        while (true)
        {
            Thread.sleep(1000);
            System.out.println(number--);
            if (number <= 0)
                break;
        }
    }
}
```

## 线程礼让
* 礼让线程，让当前正在执行的线程暂停，但不阻塞；
* 将线程从运行状态转为就绪状态；
* 让CPU重新调度，礼让不一定成功。
```java
package Thread;

public class Yield
{
    public static void main(String[] args)
    {
        MyYield myYield = new MyYield();
        new Thread(myYield, "线程A").start();
        new Thread(myYield, "线程B").start();
    }
}

class MyYield implements Runnable
{
    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + " 线程开始执行！");
        Thread.yield();  // 线程礼让
        System.out.println(Thread.currentThread().getName() + " 线程停止执行！");
    }
}
/*
礼让成功：
线程A 线程开始执行！
线程B 线程开始执行！
线程A 线程停止执行！
线程B 线程停止执行！

礼让不成功：
线程A 线程开始执行！
线程A 线程停止执行！
线程B 线程开始执行！
线程B 线程停止执行！
 */
 ```

## 线程强制执行
* `join`合并线程，待此线程执行完成后，再执行其他线程，其他线程阻塞；
* 可以理解为插队。
```java
package Thread;

public class Join implements Runnable
{
    @Override
    public void run()
    {
        for (int i = 0; i < 1000; i++)
            System.out.println("线程VIP来插队了 " + i);
    }

    public static void main(String[] args) throws InterruptedException
    {
        // 启动线程
        Join testJoin = new Join();
        Thread thread = new Thread(testJoin);
        thread.start();

        // 主线程
        for (int i = 0; i < 500; i++)
        {
            if (i == 200)
                thread.join();  // i = 200时插队
            System.out.println("此时主线程执行到 " + i);
        }

    }
}
/*
Output:
...
线程VIP来插队了 223
此时主线程执行到 196
线程VIP来插队了 224
...
线程VIP来插队了 304
线程VIP来插队了 305
此时主线程执行到 197
此时主线程执行到 198
此时主线程执行到 199  // 主线程暂停
线程VIP来插队了 306
线程VIP来插队了 307
...
线程VIP来插队了 999
此时主线程执行到 200
此时主线程执行到 201
...
此时主线程执行到 498
此时主线程执行到 499
 */
 ```

 ## 观测线程状态
 `Thread.State`：
 * `NEW`：尚未启动的线程处于此状态；
 * `RUNNABLE`：在`Java`虚拟机中执行的线程处于此状态；
 * `BLOCKED`：被阻塞等待监视器锁定的线程处于此状态；
 * `WAITING`：正在等待另一个线程执行特定动作的线程处于此状态；
 * `TIMED_WAITING`：正在等待另一个线程执行动作达到指定等待时间的线程处于此状态；
 * `TERMINATED`：已退出的线程处于此状态。
 ```java
 package Thread;

public class ThreadState
{
    public static void main(String[] args) throws InterruptedException
    {
        // 线程体
        Thread thread = new Thread(() ->
        {
            for (int i = 0; i < 5; i++)
            {
                try
                {
                    Thread.sleep(1000);  // TIMED_WAITING
                    System.out.println();
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            int i = 1;
            System.out.println("观察线程的状态 " + i++);
        });

        // 观察状态
        Thread.State state = thread.getState();  // NEW
        System.out.println(state);

        // 观察启动后
        thread.start();  // 启动线程
        state = thread.getState();
        System.out.println(state);  // RUNNABLE

        int i = 1;
        while (state != Thread.State.TERMINATED)  //只要线程不终止就一直输出状态
        {
            Thread.sleep(500);  // 1000/500=2 2*5 = 10
            state = thread.getState();  // 更新线程状态
            System.out.println(state + " " + i++);
        }
    }
}
/*
Output:
NEW
RUNNABLE
TIMED_WAITING 1

TIMED_WAITING 2
TIMED_WAITING 3

TIMED_WAITING 4
TIMED_WAITING 5

TIMED_WAITING 6
TIMED_WAITING 7

TIMED_WAITING 8
TIMED_WAITING 9

观察线程的状态 1
TERMINATED 10

//for (int i = 0; i < 5; i++)
//Thread.sleep(1000);
//Thread.sleep(500);
//1000/500=2 2*5 = 10
 */
 ```
 线程只能启动一次！

## 线程优先级
* `Java`提供一个线程调度器来监控程序中启动后进入就绪状态的所有线程，线程调度器按照优先级决定应该调度哪个线程来执行。
* 线程的优先级用数字表示，范围从1~10：
`Thread.MIN_PRIORITY = 1;`
`Thread.MAX_PRIORITY = 10;`
`Thread.NORM_PRIORITY = 5;`
* 使用`getPriority(), setPriority(int x)`获得或改变优先级。
```java
package Thread;

public class Priority
{
    public static void main(String[] args)
    {
        // 主线程默认优先级
        System.out.println(Thread.currentThread().getName() + " 的优先级是 "
                + Thread.currentThread().getPriority());

        MyPriority myPriority = new MyPriority();

        Thread thread0 = new Thread(myPriority);
        Thread thread1 = new Thread(myPriority);
        Thread thread2 = new Thread(myPriority);
        Thread thread3 = new Thread(myPriority);
        Thread thread4 = new Thread(myPriority);
        Thread thread5 = new Thread(myPriority);

        // 先设置优先级，再启动
        thread0.start();

        thread1.setPriority(1);
        thread1.start();

        thread2.setPriority(4);
        thread2.start();

        thread3.setPriority(Thread.MAX_PRIORITY);
        thread3.start();

        /*
        thread4.setPriority(-1);
        thread4.start();

        thread5.setPriority(11);
        thread5.start();*/
    }
}

class MyPriority implements Runnable
{
    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + " 的优先级是 "
                + Thread.currentThread().getPriority());
    }
}
/*
main 的优先级是 5  // 默认是5，公平竞争
Thread-0 的优先级是 5  // 并不是优先级高的就一定先执行
Thread-3 的优先级是 10
Thread-1 的优先级是 1
Thread-2 的优先级是 4
 */
 ```

## 守护线程
* 线程分为用户线程和守护(daemon)线程；
* 虚拟机必须确保用户线程(main线程)执行完毕；
* 虚拟机不用等待守护线程(垃圾回收线程)执行完毕；
* 守护线程应用：后台记录操作日志、监控内存、垃圾回收等。
```java
package Thread;

public class Daemon
{
    public static void main(String[] args)
    {
        God god = new God();
        Peoples you = new Peoples();

        Thread thread = new Thread(god);
        thread.setDaemon(true);  // 默认是false表示用户线程，正常的线程一般都是用户线程
        thread.start();  // 守护线程启动

        // 虚拟机不用等待守护线程执行完毕；
        // 如果用户线程结束了，则守护线程也结束
        new Thread(you).start();  // 用户线程启动
    }
}

class God implements Runnable
{
    @Override
    public void run()
    {
        while (true)
            System.out.println("God blesses you !");
    }
}

class Peoples implements Runnable
{
    @Override
    public void run()
    {
        for (int i = 0; i < 36500; i++)
            System.out.println("开心过第 " + i + " 天！");
        System.out.println("Goodbye World !");
    }
}
/*
Output:
......
开心过第 36499 天！
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
God blesses you !
Goodbye World !  // 用户线程结束
God blesses you !
God blesses you !  // 随后守护线程也结束了
 */
 ```

# 线程的同步(重点)

# 线程通信