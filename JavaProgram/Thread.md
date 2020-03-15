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

<div align=center><img src=Thread/Thread类.png width=90%></div>

不推荐使用这种方法，因为它将任务和运行任务的机制混在了一起。将任务从线程中分离出来是比较好的设计。

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
        thread.start();  // 等价于new CreateThread().start();
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

// Client class
public class TaskThreadDemo
{
    public static void main(String[] args)
    {
        // 1.创建任务
        Runnable printA = new PrintChar('a', 10);
        Runnable printB = new PrintChar('b', 10);

        // 2.创建线程
        // 任务必须在线程中执行
        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);

        //3.启动线程
        // 调用start()方法告诉Java虚拟机该线程准备运行
        // 运行这个程序，则两个线程将共享CPU
        thread1.start();
        thread2.start();
    }
}

// Custom task class
// 通过实现Runnable 接口定义一个任务类
class PrintChar implements Runnable
{
    private char charToPrint;
    private int times;

    public PrintChar(char charToPrint, int times)
    {
        this.charToPrint = charToPrint;
        this.times = times;
    }

    // 告诉系统线程将如何运行
    @Override
    public void run()
    {
        for (int i = 0; i < times; i++)
            System.out.println(Thread.currentThread().getName() + " " + charToPrint);
    }
}
/*
Thread-1 b
Thread-0 a
Thread-1 b
Thread-0 a
......
 */
 ```
任务中的`run()`方法指明如何完成这个任务。Java虚拟机会自动调用该方法，无需特意调用它。直接调用`run()`只是在同一个线程中执行该方法，而没有新线程被启动。


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

 ## 三种实现方式总结
```java
package Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CreateThread4
{
    public static void main(String[] args)
    {
        // 1.继承Thread类
        new MyThread1().start();

        // 2.实现Runnable接口
        new Thread(new MyTread2()).start();

        // 3.实现Callable接口  //参考线程池
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread3());
        new Thread(futureTask).start();
        try
        {
            Integer integer = futureTask.get();
            System.out.println(integer);
        }catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }catch (ExecutionException ee)
        {
            ee.printStackTrace();
        }
    }
}

// 1.继承Thread类
class MyThread1 extends Thread
{
    @Override
    public void run()
    {
        System.out.println("1.继承Thread类");
    }
}

// 2.实现Runnable接口
class MyTread2 implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("2.实现Runnable接口");
    }
}

// 3.实现Callable接口
class MyThread3 implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception
    {
        System.out.println("3.实现Callable接口");
        return 100;
    }
}
/*
1.继承Thread类
2.实现Runnable接口
3.实现Callable接口
100
 */
 ```

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

<div align=center><img src=Thread/join.png width=90%></div>

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
        // 创建任务
        Join testJoin = new Join();
        // 创建线程
        Thread thread = new Thread(testJoin);
        // 启动线程
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

# 线程池
&emsp;&emsp;经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对性能影响很大。因此，可以提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中。可以避免频繁创建、销毁，实现重复利用。<font color=red>类似每次需要骑车时，去站点使用共享单车，而不是每次都去买一辆</font>。 

这么做可以：
* 提高响应速度（减少了创建新线程的时间）；
* 降低资源消耗（重复利用线程池中线程，不需要每次都创建）；
* 便于线程管理：
    `corePoolSize`：管理核心池的大小；
    `maximumPoolSize`：管理最大线程数；
    `keepAliveTime`：管理线程没有任务时最多保持多长时间后会终止。

## 使用线程池

&emsp;&emsp;实现`java.lang.Runnable`来定义一个任务类，以及如何创建一个线程来运行一个任务`Runnable task= new TaskClass(task); new Thread(task). start();`该方法对单一任务的执行是很方便的，但是由于必须为每个任务创建一个线程，因此对大量的任务而言是不够高效的。为每个任务开始一个新线程可能会限制吞吐最并且造成性能降低。线程池是管理并发执行任务个数的理想方法。Java提供`Executor`接口来执行线程池中的任务，提供`ExecutorService`接口来管理和控制任务。`ExecutorService`是`Executor`的子接口。


* `JDK5.0`起提供了线程池相关`API`：`ExecutorService`和`Executors`；
* `ExecutorService`：真正的线程池接口。常见子类`ThreadPoolExecutor`：
    1.`void execute(Runnable command)`：执行任务/命令，没有返回值，一般用来执行`Runnable`；
    2.`<T>Future<T> submit(Callable<T> task)`：执行任务，有返回值，一般用来执行`Callable`；
    3.`void shutdown()`：关闭连接池。
* `Executors`：工具类、线程池的工厂类，用于创建并返回不同类型的线程池。

`Executor`接口（`Execute方法`）执行线程，而子接口`ExecutorService`管理线程：
<div align=center><img src=Thread/Executor.png width=90%></div>

`Executors`类提供创建`Executor`对象的静态方法：
<div align=center><img src=Thread/Executors.png width=90%></div>


```java
package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestPool
{
    public static void main(String[] args)
    {
        // 1. 创建服务，创建线程池
        // Create a fixed thread pool with maximum ten threads
        ExecutorService service = Executors.newFixedThreadPool(10);  // 线程池的大小为10

        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        // 2.关闭连接
        service.shutdown();
    }
}

class MyThread implements Runnable
{
    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName());
    }
}
/*
pool-1-thread-1
pool-1-thread-3
pool-1-thread-4
pool-1-thread-2
 */
 ```

&emsp;&emsp;如果仅需要为一个任务创建一个线程，就使用`Thread`类。如果需要为多个任务创建线程，最好使用线程池。
* `ExecutorService executor = Executors.newFixedThreadPool(1);`：这四个可运行的任务将顺次执行，因为在线程池中只有一个线程。
* `ExecutorService executor = Executors.newCachedThreadPool();`：将为每个等待的任务创建一个新线程，所以，所有的任务都并发地执行。
* 方法`shutdown()`通知执行器关闭。不能接受新的任务，但是现有的任务将继续执行直至完成。


# 线程的同步(重点)
&emsp;&emsp;多个线程操作同一个资源——<font color=red>并发</font>。处理多线程问题时，多个线程访问同一个对象，并且某些线程还想修改这个对象，这时需要线程同步。<font color=red>线程同步其实就是一个等待机制，多个需要同时访问此对象的线程进入这个对象的等待池形成队列(排队)</font>，等待前面线程使用完毕，下一个线程再使用。
&emsp;&emsp;队列与锁：排队去厕所，第一个人进入隔间锁上门独享资源。每个对象都有一把锁来保证线程安全。

&emsp;&emsp;由于同一进程的多个线程共享同一块存储空间，在带来方便的同时，也存在访问冲突问题。为了保证数据在方法中被访问时的正确性，在访问时加入<font color=red>锁机制(synchronized)</font>，当一个线程获得对象的<font color=red>排它锁</font>，独占资源时，其他线程必须等待，使用后释放锁。

性能与安全的平衡：
* 一个线程持有锁会导致其他所有需要此锁的线程挂起；
* 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题；
* 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致<font color=red>优先级倒置</font>，引起性能问题。

## 线程不安全案例
&emsp;&emsp;每个线程都在自己的工作内存交互，内存控制不当会造成数据不一致。
### 不安全地买票
`chen 买到了第 -1 票！`：当票还剩一张时，由于没有排队，每个人都买了，于是变成-1了。
```java
package Thread;

public class UnsafeBuyTickets
{
    public static void main(String[] args)
    {
        BuyTickets buyTickets = new BuyTickets();
        new Thread(buyTickets, "chen").start();
        new Thread(buyTickets, "zu").start();
        new Thread(buyTickets, "feng").start();
    }
}

class BuyTickets implements Runnable
{
    private int ticketsNumber = 10;
    boolean flag = true;  // 外部停止方式

    @Override
    public void run()
    {
        // 买票
        while (flag)
        {
            try
            {
                buy();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    // 买票方法
    private void buy() throws InterruptedException
    {
        // 判断是否邮票
        if (ticketsNumber <= 0)
        {
            flag = false;
            return;
        }

        // 模拟延时
        Thread.sleep(1000);

        System.out.println(Thread.currentThread().getName() + " 买到了第 " + ticketsNumber-- + " 票！");
    }
}
/*
Output:
feng 买到了第 9 票！
zu 买到了第 10 票！
chen 买到了第 10 票！
chen 买到了第 8 票！
zu 买到了第 6 票！
feng 买到了第 7 票！
zu 买到了第 3 票！
chen 买到了第 4 票！
feng 买到了第 5 票！
feng 买到了第 2 票！
chen 买到了第 2 票！
zu 买到了第 2 票！
feng 买到了第 1 票！
chen 买到了第 -1 票！ // 线程不安全
zu 买到了第 0 票！
 */
```

### 不安全地取钱
```java
package Thread;

/*
* 不安全地取钱
* 两个人去银行取钱，账户
 */

public class UnsafeBank
{
    public static void main(String[] args)
    {
        // 创建一个账户
        Account account = new Account(1000000, "购房基金");

        Drawing customer1 = new Drawing(account, 10000, "customer1");
        Drawing customer2 = new Drawing(account, 2000000, "customer2");

        customer1.start();
        customer2.start();
    }
}

// 账户
class Account
{
    int money;  // 余额
    String name;  // 卡名

    public Account(int money, String name)
    {
        this.money = money;
        this.name = name;
    }
}

// 银行：模拟取款
class Drawing extends Thread
{
    Account account;
    int drawingMoney;  // 取了多少钱
    int leftMoney;  // 剩余钱

    public Drawing(Account account, int drawingMoney, String customerName)
    {
        super(customerName);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    // 取钱
    @Override
    public void run()
    {
        if (account.money - drawingMoney < 0)
            System.out.println("金额不足，" + Thread.currentThread().getName() + " 无法取钱！");

        try
        {
            Thread.sleep(1000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // 卡内余额
        account.money -= drawingMoney;
        // 手中余额
        leftMoney += drawingMoney;

        System.out.println("客户：" + Thread.currentThread().getName() + " 取了 " + drawingMoney + " 元！" +
                "账户：" + account.name + " 还剩余金额：" + account.money);
        // this.getName() = Thread.currentThread().getName()
        // 因为类Drawing继承了Thread
        System.out.println("客户：" + this.getName() + " 手中尚余金额：" + leftMoney);
    }
}
/*
Output:
金额不足，customer2 无法取钱！
客户：customer1 取了 10000 元！账户：购房基金 还剩余金额：990000
客户：customer1 手中尚余金额：10000
客户：customer2 取了 2000000 元！账户：购房基金 还剩余金额：-1010000
客户：customer2 手中尚余金额：2000000
 */
```

### 线程不安全的集合
```java
package Thread;

import java.util.ArrayList;
import java.util.List;

public class UnsafeList
{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10000; i++)
        {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());  // 期望加入10000个线程
            }).start();
        }

        /*
        try
        {
            Thread.sleep(100);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/

        System.out.println(list.size());
    }
}
/*
Output:9997
 */
 ```

 ### 线程不安全案例四
 &emsp;&emsp;假设创建并启动100个线程，每个线程都往同一个账户中添加一个便士。定义一个名为`Account`的类模拟账户，一个名为`AddAPennyTask`的类用来向账户里添加一便士，以及一个用于创建和启动线程的主类。
<div align=center><img src=Thread/线程不安全案例4.png width=90%></div>

```java
package Thread;

import java.util.concurrent.*;

public class AccountWithoutSync
{
    private static Account account = new Account();

    public static void main(String[] args)
    {
        // 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建线程，创建任务
        for (int i = 0; i < 100; i++)
        {
            executorService.execute(new AddAPennyTask());  // submit task
            System.out.println(Thread.currentThread().getName() + " 正在存钱中！");
        }

        executorService.shutdown();

        // Wait until all tasks are finished
        while (!executorService.isTerminated()){}
        System.out.println("What's balance? " + account.balance);
    }

    // A thread for adding a penny to the account
    private static class AddAPennyTask implements Runnable
    {
        @Override
        public void run()
        {
            account.deposit(1);
        }
    }

    // An inner class for account
    private static class Account
    {
        private int balance = 0;

        public int getBalance(){ return balance; }

        public void deposit(int amount)
        {
            int newBalance = balance + amount;

            // This delay is deliberately added to magnify the
            // data-corruption problem and make it easy to see.
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }

            balance = newBalance;
            
            // 47-60的代码可由balance += amount;代替，但此时无法清楚地看出问题！
        }
    }
}
/*
......
main 正在存钱中！
What's balance? 7
 */
 ```

当所有的线程都完成时，余额应该是100, 但是输出结果`What's balance? 7`并不是可预测的。出现问题的原因：
<div align=center><img src=Thread/案例四线程不安全原因.png width=80%></div>

step | balance | newBalance | Task1                     | Task2 |
:-:  | :-:     | :-:        | :-:                       | :-:   |
1    | 0       | 1          | newBalance = balance + 1; |       | 
2    | 0       | 1          |                           | newBalance = balance + 1;|
3    | 1       | 1          | balance = newBalance;     |       |
4    | 1       | 1          |                           | balance = newBalance;     |

问题是任务1和任务2以一种会引起冲突的方式访问一个公共资源。这是多线程程序中的一个普遍问题，称为竞争状态(`race condition`) 。如果一个类的对象在多线程程序中没有导致竞争状态，则称这样的类为线程安全的(`thread-safe`) 。


 ## 同步的方法
&emsp;&emsp;为避免竞争状态，应该防止多个线程同时进入程序的某一特定部分，程序中的这部分称为临界区(`critical region`) 。可以使用关键字`synchronized`来同步方法，以便<font color=red>一次只有一个线程可以访问这个方法</font>。
`public synchronized void deposit(int amount)`

&emsp;&emsp;一个同步方法在执行之前需要加锁。锁是一种实现资源排他使用的机制。<font color=red>对于实例方法，要给调用该方法的对象加锁。对于静态方法，要给这个类加锁</font>。如果一个线程调用一个对象上的同步实例方法（静态方法），首先给该对象（类）加锁，然后执行该方法，最后解锁。在解锁之前，另一个调用那个对象（类）中方法的线程将被阻塞，直到解锁。
<div align=center><img src=Thread/同步.png width=60%></div>

 * `synchronized`方法控制`对象`的访问，每个对象对应一把锁。每个`synchronized`方法都必须获得调用该方法对象的锁才能执行，否则线程会阻塞。方法一旦执行，就独占该锁，直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行。
 * 若将一个大的方法声明为`synchronized`将会影响效率。
 例如，A代码：只读；B代码：修改，方法里需要修改的内容才需要锁，锁得太多，浪费资源。

&emsp;&emsp;调用一个对象上的同步实例方法，需要给该对象加锁。而调用一个类上的同步静态方法，需要给该类加锁。当执行方法中某一个代码块时，同步语句不仅可用于对`this对象`加锁，而且可用于对任何对象加锁。这个代码块称为`同步块(synchronized block)`。

* 同步块`sychronized(Obj){}`中，`Obj`被称为`同步监视器`，它可以是任何对象，但推荐使用共享资源作为同步监视器；同步方法中无需指定监视器，因为同步方法中监视器就是`this`，即该对象本身。或者是`class`(反射中讲解)。
* 同步监视器得执行过程：1.第一个线程访问，锁定同步监视器，执行其中代码；2.第二个线程访问，发现同步监视器被锁定，无法访问；3.第一个线程访问完毕，解锁同步监视器；4.第二个线程访问，发现同步监视器没有锁，然后锁定并访问。

对`unSafeBuyTickets.java`进行修改，变成线程安全的：
* `private int ticketsNumber = 1000;`
* `private synchronized void buy() throws InterruptedException`

```java {.line-numbers highlight=36}
package Thread;

public class SafeBuyTickets
{
    public static void main(String[] args)
    {
        BuyTickets buyTickets = new BuyTickets();
        new Thread(buyTickets, "chen").start();
        new Thread(buyTickets, "zu").start();
        new Thread(buyTickets, "feng").start();
    }
}

class BuyTickets implements Runnable
{
    private int ticketsNumber = 1000;
    boolean flag = true;  // 外部停止方式

    @Override
    public void run()
    {
        // 买票
        while (flag)
        {
            try
            {
                buy();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    // 买票方法
    private synchronized void buy() throws InterruptedException
    {
        // 判断是否邮票
        if (ticketsNumber <= 0)
        {
            flag = false;
            return;
        }

        // 模拟延时
        Thread.sleep(10);

        System.out.println(Thread.currentThread().getName() + " 买到了第 " + ticketsNumber-- + " 票！");
    }
}
/*
Output:
...
chen 买到了第 996 票！
chen 买到了第 995 票！
feng 买到了第 994 票！
feng 买到了第 993 票！
feng 买到了第 992 票！
...
feng 买到了第 334 票！
feng 买到了第 333 票！
zu 买到了第 332 票！
zu 买到了第 331 票！
...
zu 买到了第 167 票！
zu 买到了第 166 票！
feng 买到了第 165 票！
feng 买到了第 164 票！
...
feng 买到了第 2 票！
feng 买到了第 1 票！
 */
```

对`UnsafeBank.java`修改，变成线程安全的：

```java {.line-numbers highlight=53}
package Thread;

/*
* 两个人去银行取钱，账户
 */

public class SafeBank
{
    public static void main(String[] args)
    {
        // 创建一个账户
        Account account = new Account(1000000, "购房基金");

        Drawing customer1 = new Drawing(account, 10000, "customer1");
        Drawing customer2 = new Drawing(account, 2000000, "customer2");

        customer1.start();
        customer2.start();
    }
}

// 账户
class Account
{
    int money;  // 余额
    String name;  // 卡名

    public Account(int money, String name)
    {
        this.money = money;
        this.name = name;
    }
}

// 银行：模拟取款
class Drawing extends Thread
{
    Account account;
    int drawingMoney;  // 取了多少钱
    int leftMoney;  // 剩余钱

    public Drawing(Account account, int drawingMoney, String customerName)
    {
        super(customerName);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    // 取钱
    @Override
    public void run()
    {
        // 锁的对象是变化的量：需要增删改
        synchronized (account)
        {
            if (account.money - drawingMoney < 0)
            {
                System.out.println("金额不足，" + Thread.currentThread().getName() + " 无法取钱！");
                return;
            }

            try
            {
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // 卡内余额
            account.money -= drawingMoney;
            // 手中余额
            leftMoney += drawingMoney;

            System.out.println("客户：" + Thread.currentThread().getName() + " 取了 " + drawingMoney + " 元！" +
                    "账户：" + account.name + " 还剩余金额：" + account.money);
            // this.getName() = Thread.currentThread().getName()
            // 因为类Drawing继承了Thread
            System.out.println("客户：" + this.getName() + " 手中尚余金额：" + leftMoney);
        }
    }
}
/*
Output:
客户：customer1 取了 10000 元！账户：购房基金 还剩余金额：990000
客户：customer1 手中尚余金额：10000
金额不足，customer2 无法取钱！
 */
```

### JUC安全类型的集合
```java
package Thread;

import java.util.concurrent.CopyOnWriteArrayList;

public class TestJUC
{
    public static void main(String[] args)
    {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 200000; i++)
        {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
            }).start();

            /*
            try
            {
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }*/
        }

        try
        {
            Thread.sleep(1000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println(list.size());
    }
}
/*
Output:200000
 */
```

### 死锁
&emsp;&emsp;多个线程各自占用一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或多个线程都在等对方释放资源，都停止执行的情形。某一个同步块同时拥有“两个以上对象的锁”时，就可能会发生“死锁”的问题。

多个线程互相抱着对方需要的资源，然后形成僵持。

```java
package Thread;

public class DeadLock
{
    public static void main(String[] args)
    {
        Makeup girl1 = new Makeup(0, "Girl1");
        Makeup girl2 = new Makeup(1, "Girl2");
        girl1.start();
        girl2.start();
    }

}

class Lipstick
{

}

class Mirror
{

}

class Makeup extends Thread
{
    // 需要的资源只有一份，可由static保证
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice;  // 选择
    String girlName;  // 使用化妆品的人

    Makeup(int choice, String girlName)
    {
        this.choice = choice;
        this.girlName = girlName;
    }

    // 化妆
    @Override
    public void run()
    {
        try
        {
            makeup();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // 化妆：互相持有对方的锁，就是需要拿到对方的资源
    private void makeup() throws InterruptedException
    {
        if (choice == 0)
        {
            synchronized (lipstick)
            {
                System.out.println(this.girlName + " 拥有口红的使用权！想得到镜子的使用权！");
                Thread.sleep(1000);

                synchronized (mirror)
                {
                    System.out.println(this.girlName + " 获得了镜子的使用权！");
                }
            }
        }
        else
        {
            synchronized (mirror)
            {
                System.out.println(this.girlName + " 拥有镜子的使用权！想得到口红的使用权！");
                Thread.sleep(4000);

                synchronized (lipstick)
                {
                    System.out.println(this.girlName + " 获得了口红的使用权！");
                }
            }
        }
    }
}
/*
Output:
Girl2 拥有镜子的使用权！想得到口红的使用权！
Girl1 拥有口红的使用权！想得到镜子的使用权！

Process finished with exit code -1
 */
```

 避免死锁：
 ```java
 private void makeup() throws InterruptedException
    {
        if (choice == 0)
        {
            synchronized (lipstick)
            {
                System.out.println(this.girlName + " 拥有口红的使用权！想得到镜子的使用权！");
                Thread.sleep(1000);

                /* 死锁
                synchronized (mirror)
                {
                    System.out.println(this.girlName + " 获得了镜子的使用权！");
                }*/
            }

            synchronized (mirror)
            {
                System.out.println(this.girlName + " 获得了镜子的使用权！");
            }
        }
        else
        {
            synchronized (mirror)
            {
                System.out.println(this.girlName + " 拥有镜子的使用权！想得到口红的使用权！");
                Thread.sleep(4000);

                /* 死锁
                synchronized (lipstick)
                {
                    System.out.println(this.girlName + " 获得了口红的使用权！");
                }*/
            }

            synchronized (lipstick)
            {
                System.out.println(this.girlName + " 获得了口红的使用权！");
            }
        }
    }
 ```

#### 死锁避免方法
产生死锁的四个必要条件：
* 一个资源每次只能被一个进程使用；
* 一个进程因请求资源而阻塞时，对已获得的资源保持不放；
* 进程已获得的资源，在未使用完之前，不能强行剥夺；
* 若干进程之间形成一种头尾相接的循环等待资源关系。

### `Lock`锁
&emsp;&emsp;Java可以显式地加锁，这给协调线程带来了更多的控制功能。一个锁是一个`Lock`接口的实例，它定义了加锁和释放锁的方法。

* 从`JDK5.0`开始，`Java`提供了更强大的线程同步机制——通过显示定义同步锁对象来实现同步。同步锁使用`Lock`对象充当。
* `java.util.concurrent.locks.Lock`接口是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独占访问，每次只能有一个线程对`Lock`对象加锁，线程开始访问共享资源之前，应先获得`Lock`对象。
* `ReentrantLock`类（可重入锁）实现了`Lock`，它拥有与`synchronized`相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是`ReentrantLock`，可以显示加锁、释放锁。
    `ReentrantLock`是`Lock`的一个具体实现，用于创建相互排斥的锁。可以创建具有特定的公平策略的锁。<font color=red>公平策略值为真，则确保等待时间最长的线程首先获得锁。取值为假的公平策略将锁给任意一个在等待的线程</font>。被多个线程访问的使用公正锁的程序，其整体性能可能比那些使用默认设置的程序差，但是在获取锁且避免资源缺乏时可以有更小的时间变化。

<div align=center><img src=Thread/ReentrantLock.png width=90%></div>

#### 加锁线程安全1
```java
package Thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class AccountWithSyncUsingLock
{
    private static Account account = new Account();

    public static void main(String[] args)
    {
        // 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Create and launch 100 threads
        for (int i = 0; i < 100; i++)
        {
            executorService.execute(new AddAPennyTask());
            System.out.println(Thread.currentThread().getName() + " " + i + " 正在存钱中！");
        }

        executorService.shutdown();

        // Wait until all tasks are finished
        // 程序暂停
        while (!executorService.isTerminated()){}

        System.out.println("What's balance? " + account.getBalance());
    }

    // A thread for adding a penny to the account
    public static class AddAPennyTask implements Runnable
    {
        @Override
        public void run()
        {
            account.deposit(1);
        }
    }

    // An inner class for Account
    public static class Account
    {
        // Create a lock
        private static Lock lock = new ReentrantLock();

        private int balance = 0;

        public int getBalance(){ return balance; }

        public void deposit(int amount)
        {
            lock.lock();  // Acquire the lock

            try
            {
                int newBalance = balance + amount;
                // This delay is deliberately added to magnify the
                // data-corruption problem and make it easy to see.
                Thread.sleep(5);
                balance = newBalance;
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                lock.unlock();  // Release the lock
            }
        }
    }
}
/*
main 98 正在存钱中！
main 99 正在存钱中！
What's balance? 100
 */
```


#### 加锁线程安全2
```java
package Thread;

import java.util.concurrent.locks.ReentrantLock;

public class TestLock
{
    public static void main(String[] args)
    {
        TestLock2 testLock = new TestLock2();
        new Thread(testLock, "chen").start();
        new Thread(testLock, "zu").start();
        new Thread(testLock, "feng").start();
    }
}

class TestLock2 implements Runnable
{
    int ticketsNum = 10000;

    // 定义lock锁
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                reentrantLock.lock();  // 加锁

                if (ticketsNum >= 1)
                {
                    try
                    {
                        Thread.sleep(10);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " 获得了第 " + ticketsNum-- + " 张票！");
                }
                else
                    break;
            }

            finally
            {
                reentrantLock.unlock();  // 解锁
            }

        }
    }
}
/*
Output:
...
feng 获得了第 2461 张票！
feng 获得了第 2460 张票！
chen 获得了第 2459 张票！
chen 获得了第 2458 张票！
...
chen 获得了第 2366 张票！
chen 获得了第 2365 张票！
zu 获得了第 2364 张票！
zu 获得了第 2363 张票！

 */
 ```

#### `synchronized`与`Lock`的对比
 * `Lock`是显示锁（手动开启和关闭锁）；`sychronized`是隐式锁，出了作用域自动释放；
 * `Lock`只有代码块锁，`sychronized`有代码块锁和方法锁；
 * 使用`Lock`锁，`JVM`将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）。
 * 优先使用顺序：`Lock` > 同步代码块 > 同步方法



# 线程通信

## 锁和条件
&emsp;&emsp;通过保证在临界区上多个线程的相互排斥，线程同步完全可以避免竞争条件的发生， 但是有时候，还需要线程之间的相互协作。可以使用条件实现线程间通信。一个线程可以指定在某种条件下该做什么。条件是通过调用`Lock`对象的`newCondition()`方法而创建的对象。一旦创建了条件，就可以使用`await(), signal()`和`signalAll()`方法来实现线程之间的相互通信。`await()`方法可以让当前线程进人等待，直到条件发生。`signal()`方法唤醒一个等待的线程，而`signalAll()`唤醒所有等待的线程。
<div align=center><img src=Thread/Condition接口定义完成同步的方法.png width=90%></div>

&emsp;&emsp;假设创建并启动两个任务，一个用来向账户中存款，另一个从同一账户中提款。
* 当提款的数额大于账户的当前余额时，提款线程必须等待。
* 不管什么时候，只要向账户新存入一笔资金，存储线程必须通知提款线程重新尝试。如果余额仍未达到提款的数额，提款线程必须继续等待新的存款。
* 为了同步这些操作，使用一个具有条件的锁`newDeposit`(即增加到账户的新存款)。如果余额小于取款数额，提款任务将等待`newDeposit`条件。当存款任务给账户增加资金时，存款任务唤醒等待中的提款任务再次尝试。

<div align=center><img src=Thread/两个任务之间的交互.png width=90%></div>

从`Lock`对象中创建条件。为了使用条件，必须首先获取锁。`await()`方法让线程等待并且自动释放条件上的锁。一旦条件正确，线程重新获取锁并且继续执行。
```java
package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCooperation
{
    private static Account account = new Account();

    public static void main(String[] args)
    {
        // Create a thread pool with two threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new DepositTask());
        executorService.execute(new WithdrawTask());

        executorService.shutdown();

        System.out.println("Thread 1: DepositTask\t\t\t\t\tThread 2: WithdrawTask\t\t\t\t\t\t\t\t\tBalance");
    }

    public static class DepositTask implements Runnable
    {
        // Keep adding an amount to the account
        @Override
        public void run()
        {
            // Purposely delay it to let the withdraw method proceed
            try
            {
                while (true)
                {
                    account.deposit((int) (Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static class WithdrawTask implements Runnable
    {
        // Keep subtracting an amount from the account
        @Override
        public void run()
        {
            while (true)
                account.withdraw((int) (Math.random() * 10) + 1);
        }
    }

    private static class Account
    {
        // Create a new lock
        private static Lock lock = new ReentrantLock();

        // Create a condition, 使用一个具有条件的锁
        private static Condition newDeposit = lock.newCondition();

        private int balance = 0;

        public int getBalance(){return balance;}

        public void withdraw(int withdrawAmount)
        {
            // Acquire the lock
            lock.lock();
            try
            {
                while (balance < withdrawAmount)
                {
                    System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tWant to deposit " + withdrawAmount + "! Wait for a deposit...");
                    newDeposit.await();  // Wait on the condition
                }

                balance -= withdrawAmount;
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + Thread.currentThread().getName() + " withdraws " + withdrawAmount +
                        ". \t\t\t\t\t\t\tNow, the balance is " + balance);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                lock.unlock();  // Release the lock
            }
        }

        public void deposit(int amount)
        {
            lock.lock();
            try
            {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + " deposits " + amount +
                        ". \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tNow, the balance is " + getBalance());

                // Signal thread waiting on the condition
                newDeposit.signalAll();
            }
            finally
            {
                lock.unlock();
            }
        }
    }
}
 ```
<div align=center><img src=Thread/两个任务交互的结果.png width=90%></div>


## 监视器
&emsp;&emsp;`锁和条件`是`Java 5`中的新内容。在`Java 5`之前，线程通信是使用对象的`内置监视器`编程实现的。锁和条件比内置监视器更加强大且灵活，因此无须使用监视器。然而，如果使用遗留的Java代码，就可能会碰到Java的内置监视器。

&emsp;&emsp;监视器(monitor)是一个相互排斥且具备同步能力的对象。监视器中的一个时间点上，只能有一个线程执行一个方法。线程通过获取监视器上的锁进入监视器，并且通过释放锁退出监视摇。任意对象都可能是一个监视器。一旦一个线程锁住对象，该对象就成为监视器。加锁是通过在方法或块上使用`synchronized`关键字来实现的。在执行同步方法或块之前，线程必须获取锁。如果条件不适合线程继续在监视器内执行，线程可能在监视器中等待。可以对监视器对象调用`wait()`方法来释放锁，这样其他的一些监视器中的线程就可以获取它，也就有可能改变监视器的状态。当条件合适时，另一线程可以调用`notify()`或`notifyAll()`方法来通知一个或所有的等待线程重新获取锁并且恢复执行。

<div align=center><img src=Thread/监视器.png></div>


### 初识生产者消费者问题
* 假设仓库中只能存放一件产品，生产者将生产出来的产品放入仓库，消费者将仓库中产品取走消费。
* 如果仓库中没有产品，则生产者将产品放入仓库，否则停止生产并等待，直到仓库中的产品被消费者取走。
* 如果仓库中放有产品，则消费者可以将产品取走消费，否则停止消费并等待，直到仓库中再次放入产品。

&emsp;&emsp;这是一个线程同步问题，生产者和消费者共享同一个资源，并且生产者和消费者之间相互依赖。
* 对于生产者，没有生产产品之前，要通知消费者等待；而生产了产品之后，又需要马上通知消费者消费。
* 对于消费者，在消费之后，要通知生产者已经结束消费，需要生产新的产品以供消费。
* 在生产者消费者问题中，仅有`synchronized`是不够的。`synchronized`可以组织并发更新同一个共享资源，实现同步；但不能用来实现不同线程之间的消息传递（通信）。



#### 管程法
&emsp;&emsp;解决方式一：并发协作模型“生产者/消费者模式”->管程法。
* 生产者：负责生产数据的模块（方法、对象、线程、进程）；
* 消费者：负责处理数据的模块（方法、对象、线程、进程）；
* 缓冲区：消费者不能直接使用生产者的数据，它们之间有个“缓冲区”，<font color=red>生产者将生产好的数据放入缓冲区，消费者从缓冲区拿出数据</font>。`Producer->数据缓存区->Consumer`

```java
package Thread;

/*
* 利用管程法（缓存区）解决
 */

public class PCCookie
{
    public static void main(String[] args)
    {
        SynContainer container = new SynContainer();
        new Producer(container).start();
        new Consumer(container).start();
    }
}

class Producer extends Thread
{
    SynContainer container;

    public Producer(SynContainer container)
    {
        this.container = container;
    }

    // 进行生产
    @Override
    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            container.push(new Product(i));
            System.out.println("生产第 " + i + " 件产品！");
        }
    }
}

class Consumer extends Thread
{
    SynContainer container;

    public Consumer(SynContainer container)
    {
        this.container = container;
    }

    // 消费者消费
    @Override
    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println("消费第 " + container.pop().id + " 件产品！");
        }
    }
}

class Product
{
    int id;

    public Product(int id)
    {
        this.id = id;
    }
}

// 缓冲区
class SynContainer
{
    // 一个容器
    Product[] products = new Product[10];
    // 容器计数器
    int count = 0;

    // 生产者放入产品
     public synchronized void push(Product product)
     {
         // 如果容器满了，需要等待消费者去消费
         if (count == products.length)
         {
             // 通知消费者消费，生产者等待
             try
             {
                 this.wait();
             }catch (InterruptedException e)
             {
                 e.printStackTrace();
             }
         }

         // 如果没有满，需要丢入产品
         products[count] = product;
         count++;

         // 通知消费消费
         this.notifyAll();
     }

     // 消费者消费产品
    public synchronized Product pop()
    {
        // 判断能否消费
        if (count == 0)
        {
            // 等待生产者生产，消费者等待
            try
            {
                this.wait();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        // 如果可以消费
        count--;
        Product popProduct = products[count];

        // 消费完后通知生产者生产
        this.notifyAll();

        return popProduct;
    }
}
```

#### 信号灯法
&emsp;&emsp;解决方式二：并发协作模型“生产者/消费者模式”->信号灯法（创建一个标志位）。

```java
package Thread;

public class PCFlag
{
    public static void main(String[] args)
    {
        TV tv = new TV();
        new Actor(tv).start();
        new Audience(tv).start();
    }
}

// 生产者：演员
class Actor extends Thread
{
    TV tv;
    public Actor(TV tv)
    {
        this.tv = tv;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            if (i % 2 == 0)
                this.tv.play("快乐大本营");
            else
                this.tv.play("天天向上");
        }
    }
}

// 消费者：观众
class Audience extends Thread
{
    TV tv;
    public Audience(TV tv)
    {
        this.tv = tv;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
            this.tv.watch();
    }
}

// 产品：节目
class TV
{
    String program;  // 节目

    // 演员录节目时，观众等待 true
    // 观众观看时，演员等待反馈 false
    boolean flag = true;

    // 表演
    public synchronized void play(String program)
    {
        if (!flag)  // 观众观看时，演员等待反馈 false
        {
            try
            {
                this.wait();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("演员表演了节目：" + program);

        // 表演完后通知观众观看
        this.notifyAll();  // 通知唤醒

        this.program = program;
        this.flag = !this.flag;  // 取反
    }

    // 观看
    public synchronized void watch()
    {
        if (flag)  //演员录节目时，观众等待 true
        {
            try
            {
                this.wait();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("观众观看了节目：" + program);

        // 观众观看反馈后，通知演员表演
        this.notifyAll();
        this.flag = !this.flag;
    }
}
/*
Output:
演员表演了节目：快乐大本营
观众观看了节目：快乐大本营
演员表演了节目：天天向上
观众观看了节目：天天向上
演员表演了节目：快乐大本营
观众观看了节目：快乐大本营
 */
 ```

## 生产者/消费者示例
&emsp;&emsp;假设使用缓冲区存储整数。缓冲区的大小是受限的。缓冲区提供`write(int)`方法将一个`int`值添加到缓冲区中，还提供方法`read()`从缓冲区中读取和删除一个`int`值。为了同步这个操作，使用具有两个条件的锁：`notEmpty`(即缓冲区非空)和`notFull`(即缓冲区未满)。当任务向缓冲区添加一个`int`时，如果缓冲区是满的，那么任务将会等待`notFull`条件。当任务从缓冲区中读取一个`int`时，如果缓冲区是空的，那么任务将等待`notEmpty`条件。

<div align=center><img src=Thread/生产者消费者.png width=80%></div>

```java
package Thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ConsumerProducer
{
    // 创建一个缓存区
    private static Buffer buffer = new Buffer();

    public static void main(String[] args)
    {
        // Create a thread pool with two threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new ProducerTask());
        executorService.execute(new ConsumerTask());

        executorService.shutdown();
    }

    // A task for adding an int to the buffer
    private static class ProducerTask implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                int i = 1;
                while (true)
                {
                    System.out.println("Producer writes " + i);
                    buffer.write(i++);  // Add a value to the buffer
                    Thread.sleep((int) (Math.random() * 10 * 1000));
                }
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    // A task for reading and deleting an int from the buffer
    private static class ConsumerTask implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    System.out.println("\t\t\t\t\t\t\t\t\tConsumer reads " + buffer.read());
                    Thread.sleep((int) (Math.random() * 10 * 1000));
                }
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    // An inner class for buffer
    private static class Buffer
    {
        private static final int CAPACITY = 1;  // buffer size
        private java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();

        // Create a new lock
        private static Lock lock = new ReentrantLock();

        // Create two conditions
        // 删除时，等待notEmpty信号，等到继续删除
        private static Condition notEmpty = lock.newCondition();
        // 添加时，等待notFull信号，等到继续添加
        private static Condition notFull = lock.newCondition();


        public void write(int value)
        {
            lock.lock();
            try
            {
                while (queue.size() == CAPACITY)
                {
                    // 此时满了，等待非满信号
                    System.out.println("It's full now! Wait for notFull condition...");
                    // notFull.wait();
                    notFull.await();
                }

                // 添加数字，此时非空，发出信号可以读取删除数字了
                queue.offer(value);
                notEmpty.signal();  // Signal notEmpty condition
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        }

        public int read()
        {
            int value = 0;
            lock.lock();
            try
            {
                while (queue.isEmpty())
                {
                    // 此时空了，等待非空信号
                    System.out.println("\t\t\t\t\t\t\t\t\tIt's empty now! Wait for notEmpty condition...");
                    //notEmpty.wait();
                    notEmpty.await();
                }

                // 读取删除数字，此时非满，发出信号可以添加数字了
                value = queue.remove();
                notFull.signal();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                lock.unlock();
                return value;
            }
        }
    }
}
 ```
<div align=center><img src=Thread/生产者消费者结果.png width=90%></div>