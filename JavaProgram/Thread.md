# 基本概念

**批处理操作系统**：

最初的计算机只能接受一些特定的指令，用户每输入一个指令，计算机就做出一个操作。当用户在思考或者输入时，计算机就在等待。这样效率非常低下，在很多时候，计算机都处在等待状态。

后来有了批处理操作体统,把一系列需要操作的指令写下来，形成一个清单，一次性交给计算机。但是由于批处理操作系统的指令运行方式仍然是串行的，<font color=red>内存中始终只有一个程序在运行</font>，后面的程序需要等待前面的程序执行完成后才能开始执行。

**进程的提出**：

批处理操作系统的瓶颈在于内存中只存在一个程序，那么内存中能不能存在多个程序呢？

进程就是应用程序在内存中分配的空间，也就是正在运行的程序，各个进程之间互不干扰。此时，CPU采用**时间片轮转**的方式运行进程：CPU为每个进程分配一个时间段，称作它的时间片。如果在时间片结束时进程还在运行，则暂停这个进程的运行，并且CPU分配给另一个进程（这个过程叫做上下文切换）。如果进程在时间片结束前阻塞或结束，则CPU立即进行切换，不用等待时间片用完。

对于单核CPU来说，任意具体时刻都只有一个任务在占用CPU资源。

**线程的提出**：
如果**一个进程有多个子任务**时，只能逐个得执行这些子任务，很影响效率。

让一个线程执行一个子任务，这样一个进程就包含了多个线程，每个线程负责一个单独的子任务。


进程让**操作系统的并发性**成为了可能，而线程让**进程的内部并发**成为了可能。

**进程和线程的区别**：

进程是一个独立的运行环境，而线程是在进程中执行的一个任务。他们两个本质的区别是<font color=red>是否单独占有内存地址空间及其它系统资源（比如I/O）</font>。

- 进程单独占有一定的内存地址空间，所以进程间存在内存隔离，数据是分开的，**数据共享**复杂但是**同步**简单，各个进程之间互不干扰；而线程共享所属进程占有的内存地址空间和资源，数据共享简单，但是同步复杂。


- 进程单独占有一定的内存地址空间，一个进程出现问题不会影响其他进程，不影响主程序的稳定性，可靠性高；一个线程崩溃可能影响整个程序的稳定性，可靠性较低。

- 进程单独占有一定的内存地址空间，进程的创建和销毁不仅需要保存寄存器和栈信息，还需要资源的分配回收以及页调度，开销较大；线程只需要保存寄存器和栈信息，开销较小。
  
另外一个重要区别是，进程是**操作系统**进行**资源分配**的基本单位，而线程是操作系统进行**调度**的基本单位，即CPU分配时间的单位。

* **一个进程**(process)可以有**多个线程**(thread)：视频中声音与画面；
* 进程是程序的一次执行过程；真正的多线程是指有多个CPU（线程是CPU调度和执行的单位）。在一个CPU情况下，在一个时间点，CPU只能执行一个代码，模拟出来的多线程是因为切换得很快，产生同时执行的假象；
* 对一份资源，会存在资源抢夺的问题，需要加入并发控制。

# 线程的实现(重点)

Java程序运行时，最开始运行的只能是**主线程**。所以，必须在程序中**启动新线程**，这才能算是多线程程序。

* 继承`Thread类`
* 实现`Runnable接口`
* 实现`Callable接口`

## 继承`Thread类`
* 将一个类声明为`Thread`的子类；
* 重写`run`方法；
* 创建实例，调用`start`方法启动线程。

<div align=center><img src=Thread/Thread类.png width=90%></div>

**不推荐使用这种方法**，因为它**将任务和运行任务的机制混在了一起**。将任务从线程中分离出来是比较好的设计。



**1. 继承Thread类；2. 重写run方法；3. 创建实列；4. 调用start开启线程**

```java
package Thread;

/*
创建线程方法一：继承Thread类；重写run方法；创建实例，调用start开启线程
 */

// 1.继承Thread类
public class CreateThread extends Thread
{
    // 2.重写run方法
    @Override
    public void run()
    {
        // run方法线程体
        for (int i = 0; i < 200; i++)
            System.out.println("In run() method! " + i);
    }

    public static void main(String[] args)
    {
        // 3.创建实例
        CreateThread thread = new CreateThread();

        // 4.调用start开启线程
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
`main, start`方法是**交替进行**的；而`main, run`方法则**有先后之分**。

<div align=center><img src=Thread/普通方法调用和多线程.png width=60%></div>

调用`start()`方法后，该线程才算启动！

> 在程序里面调用了`start()`方法后，虚拟机会先为我们创建一个线程，然后等到这个线程第一次得到时间片时再调用run()方法。
> 
> 注意不可多次调用`start()`方法。在第一次调用`start()`方法后，再次调用`start()`方法会抛出异常。



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

// 1.继承Thread
public class ThreadWebDownloader extends Thread
{
    private String url;  // 图片地址
    private String name;  // 保存的文件名

    public ThreadWebDownloader(String url, String name)
    {
        this.url = url;
        this.name = name;
    }

    // 2.重写run方法
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
        // 3.创建线程实例
        ThreadWebDownloader threadWebDownloader1 = new ThreadWebDownloader(
                "http://5b0988e595225.cdn.sohucs.com/images/20180927/f245ec2141d84315ae98b0038f044eab.jpeg", "扬州1.jpeg"
        );
        ThreadWebDownloader threadWebDownloader2 = new ThreadWebDownloader(
                "http://res.yznews.cn/a/12163/201904/1e0c4d4be49aa367c10a6a26cb3988f6.jpeg", "扬州2.jpeg"
        );
        ThreadWebDownloader threadWebDownloader3 = new ThreadWebDownloader(
                "http://photocdn.sohu.com/20160227/mp60782450_1456509627780_16.jpeg", "扬州3.jpeg"
        );

        // 4.启动线程
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

`Runnable`接口：

```java
package java.lang;

@FunctionalInterface
public interface Runnable 
{
    public abstract void run();
}
```

**1. 实现`Runnable`接口；2. 重写`run`方法；3. 创建任务；4. 创建线程； 5. 启动线程**

```java
package Thread;

// Client class
public class TaskThreadDemo
{
    public static void main(String[] args)
    {
        // 1.创建实例
        Runnable printA = new PrintChar('a', 10);
        Runnable printB = new PrintChar('b', 10);

        // 2.创建线程
        // 任务必须在线程中执行
        // Thread(task : Runnable)  Create a Thread for a specified task
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
// 通过实现Runnable接口定义一个任务类
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

任务中的`run()`方法指明如何完成这个任务。Java虚拟机会自动调用该方法，无需特意调用它。**直接调用`run()`只是在同一个线程中执行该方法，而没有新线程被启动**。


```java
package Thread;

/*
创建线程方法二：实现Runnable接口；重写run方法；执行线程需要丢入Runnable接口，调用start开启线程
 */

// 1.实现Runnable
public class CreateThread2 implements Runnable
{
    // 2.重写run方法
    @Override
    public void run()
    {
        for (int i = 0; i < 200; i++)
            System.out.println("In run() method! " + i);
    }

    public static void main(String[] args)
    {
        // 3.创建实例
        // 创建实现Runnable接口的类对象
        CreateThread2 createThread = new CreateThread2();

        /*
        // 创建线程对象，丢入实现Runnable接口的类对象
        Thread thread = new Thread(createThread);

        // 开启线程
        thread.start();
        */

        // 4.创建线程；5.启动线程
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

 ## Thread类与Runnable接口的比较

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

- 由于Java“单继承，多实现”的特性，`Runnable`接口使用起来比`Thread`更灵活。
- `Runnable`接口出现更符合面向对象，将线程单独进行对象的封装。
- `Runnable`接口出现，降低了**线程对象**和**线程任务**的**耦合性**。
- 如果使用线程时不需要使用`Thread`类的诸多方法，显然使用`Runnable`接口更为轻量。

所以，通常优先使用“实现`Runnable`接口”这种方式来**自定义线程类**。

### 码源分析
Thread类运行的时候调用`start()`方法，源代码如下：
```java
    public synchronized void start() {

        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
```

调用`start()`方法，实际运行的是`start0`方法，方法声明如下：`private native void start0();`。

native表明这个方法是个原生函数，即这个函数是用C/C++实现的，被编译成DLL，由Java调用。

而`Runnable`接口：

```java
package java.lang;

@FunctionalInterface
public interface Runnable 
{
    public abstract void run();
}
```

Thread类定义一个卖票的类：
```java
public class TicketThread extends Thread {

  private int ticket = 100000;

  @Override
  public void run() {
    for (int i = 0; i < 100000; i++) {
      if (ticket > 0) {
        System.out.println("ticket=" + ticket-- + "," + Thread.currentThread().getName());
      }
    }
  }
}
```

启动三个线程：
```java
new TicketThread().start();
new TicketThread().start();
new TicketThread().start();
```

运行发现：**每个线程独立执行了卖票的任务**，每个线程中票数依次减1。

Runnable接口实现一个卖票的类：
```java
public class TicketRunnableThread implements Runnable {
  private int ticket = 1000;
  public void run() {
    for (int i = 0; i < 1000; i++) {
      if (ticket > 0) {
        System.out.println("ticket=" + ticket-- + "," + Thread.currentThread().getName());
      }
    }
  }
}
```

启动三个线程：
```java
TicketRunnableThread ticketRunnableThread = new TicketRunnableThread();
new Thread(ticketRunnableThread).start();
new Thread(ticketRunnableThread).start();
new Thread(ticketRunnableThread).start();
```

三个线程**共同完成了卖票任务**，然而三个线程**共同执行同一段代码**，会造成**线程不安全**，可以通过加锁解决。

- 实现Runnable接口的同时，还可以继承其他类，避免Java的单继承性带来局限性。
- Runnable接口可以实现**资源共享**，Thread无法完成资源共享。


## Callable、Future与FutureTask

使用`Runnable`和`Thread`来创建一个新的线程。但是它们有一个弊端，就是`run`**方法是没有返回值的**。而有时候我们希望开启一个线程去执行一个任务，并且这个任务执行完成后有一个返回值。

JDK提供了`Callable接口`与`Future类`为我们解决这个问题，这也是所谓的**异步**模型。

### 实现`Callable`接口

`Callable`与`Runnable`类似，同样是只有一个抽象方法的**函数式接口**。不同的是，`Callable`提供的方法是**有返回值**的，而且支持**泛型**。它可以抛出异常。

```java
package java.util.concurrent;

@FunctionalInterface
public interface Callable<V> 
{
    V call() throws Exception;
}
```

`Callable`一般是配合**线程池工具**`ExecutorService`来使用的。`ExecutorService`可以使用`submit`方法来让一个`Callable`接口执行。它会返回一个`Future`，可以通过这个`Future`的`get`方法得到结果。

```java
package Thread;

import java.util.concurrent.*;

public class Task implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception  // 返回值类型与Callable<Integer>中一致
    {
        // 模拟计算需要1秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        System.out.println(result.get());
    }
}
```

* 实现`Callable`接口，需要**返回值**类型；
* 重写`call`方法，需要**抛出异常**；
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

### Future接口

`Future接口`只有几个比较简单的方法：
```java
public abstract interface Future<V> 
{
    public abstract boolean cancel(boolean paramBoolean);
    public abstract boolean isCancelled();
    public abstract boolean isDone();
    public abstract V get() throws InterruptedException, ExecutionException;
    public abstract V get(long paramLong, TimeUnit paramTimeUnit)
            throws InterruptedException, ExecutionException, TimeoutException;
}
```

`cancel`方法是**试图取消**一个线程的执行。注意是试图取消，**并不一定能取消成功**。因为任务可能已完成、已取消、或者一些其它因素不能取消，存在取消失败的可能。`boolean`类型的返回值是**是否取消成功**的意思。参数`paramBoolean`表示是否采用**中断**的方式取消线程执行。

所以有时候，为了**让任务有能够取消的功能**，就使用`Callable`来代替`Runnable`。如果为了可取消性而使用`Future`但又不提供可用的结果，则可以声明`Future<?>`形式类型、并返回`null`作为底层任务的结果。

### FutureTask类

`Future接口`有一个实现类叫`FutureTask`。`FutureTask`是实现的`RunnableFuture接口`的，而`RunnableFuture接口`同时继承了`Runnable接口`和`Future接口`：

```java
public interface RunnableFuture<V> extends Runnable, Future<V> 
{
    /**
     * Sets this Future to the result of its computation unless it has been cancelled.
     */
    void run();
}
```

为什么要有一个`FutureTask`类？前面说到了`Future`只是一个接口，而它里面的`cancel`，`get`，`isDone`等方法要自己实现起来都是非常复杂的。所以JDK提供了一个`FutureTask`类来供我们使用。

```java
package Thread;

import java.util.concurrent.*;

public class Task implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception
    {
        // 模拟计算需要1秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executor.submit(futureTask);
        System.out.println(futureTask.get());
    }
}
```

使用上与之前的Demo有一点小的区别。首先，调用`submit`方法是**没有返回值**的。这里实际上是调用的`submit(Runnable task)`方法，而上面的Demo，调用的是`submit(Callable<T> task)`方法。然后，这里是使用`FutureTask`直接用`get`取值，而上面的Demo是通过`submit`方法返回的`Future`去取值。

`get`方法可能会产生阻塞，因为结果需要等待！

在很多高并发的环境下，有可能`Callable`和`FutureTask`会创建多次。`FutureTask`能够在高并发环境下确保任务只执行一次。

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
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
        catch (ExecutionException ee)
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

静态代理模式：真实对象和代理对象都要实现同一个接口；代理对象要代理真实角色。

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

&emsp;&emsp;**函数式接口**的定义：任何接口，如果**只包含唯一一个抽象方法**，那么它就是一个函数式接口。

```java
public interface Runnable
{
    public abstract void run();
}
```

<font color=red>对于函数式接口，可以通过`Lambda`表达式来创建该接口的对象</font>。下面将一步步展现代码简化过程：

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


## 线程组(ThreadGroup)

Java中用ThreadGroup来表示线程组，我们可以**使用线程组对线程进行批量控制**。

ThreadGroup和Thread的关系就如同他们的字面意思一样简单粗暴，**每个Thread必然存在于一个ThreadGroup中**，Thread不能独立于ThreadGroup存在。执行`main()方法`线程的名字是`main`，如果在`new Thread`时没有显式指定，那么默认将**父线程**（**当前执行`new Thread`的线程**）线程组设置为自己的线程组。

```java
package Thread;

public class ThreadGroup
{
    public static void main(String[] args)
    {
        Thread testThread = new Thread(() -> {
            System.out.println("testThread当前线程组名字：" +
                    Thread.currentThread().getThreadGroup().getName());
            System.out.println("testThread线程名字：" +
                    Thread.currentThread().getName());
        });

        testThread.start();
        System.out.println("执行main方法线程名字：" +
                Thread.currentThread().getName());
    }
}
/*
testThread当前线程组名字：main
执行main方法线程名字：main
testThread线程名字：Thread-0
 */
```

`ThreadGroup`管理着它下面的`Thread`，`ThreadGroup`是一个标准的向下引用的树状结构，这样设计的原因是防止"上级"线程被"下级"线程引用而无法有效地被GC回收。

# 线程状态

<div align=center><img src=Thread/线程五大状态.png width=80%></div>
<div align=center><img src=Thread/线程五大状态2.png width=80%></div>

线程方法：
<div align=center><img src=Thread/线程方法.png width=60%></div>

## 停止线程

* 不推荐使用`JDK`提供的`stop(), destroy()`方法（已废弃）。
* 推荐线程自己停下来；
* 建议**使用一个标志位**进行终止变量：当`flag == false`，则终止线程。
  
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
        /*
        public Thread(Runnable target, String name) {
            init(null, target, name, 0);
        }*/
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
* 礼让线程，**让当前正在执行的线程暂停**，但不阻塞；
* 将线程从运行状态转为就绪状态；
* 让CPU重新调度，**礼让不一定成功**。
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
* `join`合并线程，**待此线程执行完成后，再执行其他线程，其他线程阻塞**；
* 可以理解为**插队**。

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
此时主线程执行到 199  // 主线程暂停，主线程被线程VIP插队
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
 ```java
    public enum State {
        /**
         * Thread state for a thread which has not yet started.
         */
        NEW,

        /**
         * Thread state for a runnable thread.  A thread in the runnable
         * state is executing in the Java virtual machine but it may
         * be waiting for other resources from the operating system
         * such as processor.
         */
        RUNNABLE,

        /**
         * Thread state for a thread blocked waiting for a monitor lock.
         * A thread in the blocked state is waiting for a monitor lock
         * to enter a synchronized block/method or
         * reenter a synchronized block/method after calling
         * {@link Object#wait() Object.wait}.
         */
        BLOCKED,

        /**
         * Thread state for a waiting thread.
         * A thread is in the waiting state due to calling one of the
         * following methods:
         * <ul>
         *   <li>{@link Object#wait() Object.wait} with no timeout</li>
         *   <li>{@link #join() Thread.join} with no timeout</li>
         *   <li>{@link LockSupport#park() LockSupport.park}</li>
         * </ul>
         *
         * <p>A thread in the waiting state is waiting for another thread to
         * perform a particular action.
         *
         * For example, a thread that has called {@code Object.wait()}
         * on an object is waiting for another thread to call
         * {@code Object.notify()} or {@code Object.notifyAll()} on
         * that object. A thread that has called {@code Thread.join()}
         * is waiting for a specified thread to terminate.
         */
        WAITING,

        /**
         * Thread state for a waiting thread with a specified waiting time.
         * A thread is in the timed waiting state due to calling one of
         * the following methods with a specified positive waiting time:
         * <ul>
         *   <li>{@link #sleep Thread.sleep}</li>
         *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
         *   <li>{@link #join(long) Thread.join} with timeout</li>
         *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
         *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
         * </ul>
         */
        TIMED_WAITING,

        /**
         * Thread state for a terminated thread.
         * The thread has completed execution.
         */
        TERMINATED;
    }
 ```
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

Java默认的线程优先级为5，线程的执行顺序由调度程序来决定，线程的优先级会在线程被调用之前设定。通常情况下，高优先级的线程将会比低优先级的线程有**更高的几率**得到执行。

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

Java中的优先级不是特别的可靠，Java程序中对线程所设置的优先级只是给操作系统一个**建议**，操作系统不一定会采纳。而真正的调用顺序，是由操作系统的线程调度算法决定的。

```java
package Thread;

import java.util.stream.IntStream;

public class Demo
{
    public static class T1 extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            System.out.println(String.format("当前执行的线程是：%s，优先级：%d",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getPriority()));
        }
    }

    public static void main(String[] args)
    {
        IntStream.range(1, 10).forEach(i -> {
            Thread thread = new Thread(new T1());
            thread.setPriority(i);
            thread.start();
        });
    }
}
/*
当前执行的线程是：Thread-15，优先级：8
当前执行的线程是：Thread-17，优先级：9
当前执行的线程是：Thread-13，优先级：7
当前执行的线程是：Thread-5，优先级：3
当前执行的线程是：Thread-11，优先级：6
当前执行的线程是：Thread-3，优先级：2
当前执行的线程是：Thread-1，优先级：1
当前执行的线程是：Thread-7，优先级：4
当前执行的线程是：Thread-9，优先级：5
 */
```

Java提供一个**线程调度器**来监视和控制处于`RUNNABLE`状态的线程。线程的调度策略采用**抢占式**，优先级高的线程比优先级低的线程会有**更大的几率**优先执行。在优先级相同的情况下，按照“先到先得”的原则。每个Java程序都有一个默认的**主线程**，就是通过JVM启动的第一个线程**main线程**。

## 守护线程
* 线程分为**用户线程**和**守护(daemon)线程**；
* 虚拟机必须**确保用户线程(main线程)执行完毕**；
* 虚拟机**不用等待守护线程(垃圾回收线程)执行完毕**；
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
&emsp;&emsp;多个线程操作同一个资源——<font color=red>并发</font>。处理多线程问题时，多个线程访问同一个对象，并且某些线程还想修改这个对象，这时需要线程同步。<font color=red>线程同步其实就是一个等待机制，多个需要同时访问此对象的线程进入这个对象的等待池形成队列(排队)，等待前面线程使用完毕，下一个线程再使用</font>。

&emsp;&emsp;**队列与锁**：排队去厕所，第一个人进入隔间锁上门独享资源。每个对象都有一把锁来保证线程安全。

&emsp;&emsp;由于同一进程的多个线程共享同一块存储空间，在带来方便的同时，也存在访问冲突问题。为了保证数据在方法中被访问时的正确性，在访问时加入<font color=red>锁机制(synchronized)</font>，当一个线程获得对象的<font color=red>排它锁</font>，独占资源时，其他线程必须等待，使用后释放锁。

性能与安全的平衡：
* 一个线程持有锁会导致其他所有需要此锁的线程挂起；
* 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题；
* 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致<font color=red>优先级倒置</font>，引起性能问题。

## 线程不安全案例


每个线程都在自己的工作内存交互，内存控制不当会造成数据不一致。

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
        // 判断是否有票
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
&emsp;&emsp;假设创建并启动100个线程，每个线程都往同一个账户中添���一个便士。定义一个名为`Account`的类模拟账户，一个名为`AddAPennyTask`的类用来向账户里添加一便士，以及一个用于创建和启动线程的主类。
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

### synchronized

为避免竞争状态，应该防止多个线程同时进入程序的某一特定部分，程序中的这部分称为临界区(`critical region`) 。可以使用关键字`synchronized`来同步方法，以便<font color=red>一次只有一个线程可以访问这个方法</font>。
`public synchronized void deposit(int amount)`

&emsp;&emsp;一个同步方法在执行之前需要**加锁**。锁是一种实现**资源排他使用**的机制。<font color=red>对于**实例方法**，要给**调用该方法的对象加锁**。对于**静态方法**，要给这个**类**加锁</font>。如果一个线程调用一个对象上的**同步实例方法（静态方法）**，首先给该**对象（类）加锁**，然后执行该方法，最后解锁。在解锁之前，另一个调用那个对象（类）中方法的线程将被阻塞，直到解锁。
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

### 死锁
&emsp;&emsp;多个线程各自占用一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或多个线程都在等对方释放资源，都停止执行的情形。某一个同步块同时拥有“两个以上对象的锁”时，就可能会发生“死锁”的问题。

**多个线程互相抱着对方需要的资源**，然后形成僵持。

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
* `java.util.concurrent.locks.Lock`接口是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独占访问，**每次只能有一个线程对`Lock`对象加锁**，线程开始访问共享资源之前，应先获得`Lock`对象。
* `ReentrantLock`类（可重入锁）实现了`Lock`，它拥有与`synchronized`相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是`ReentrantLock`，可以显示加锁、释放锁。
    `ReentrantLock`是`Lock`的一个具体实现，用于创建相互排斥的锁。可以创建具有特定的公平策略的锁。<font color=red>公平策略值为真，则确保等待时间最长的线程首先获得锁。取值为假的公平策略将锁给任意一个在等待的线程</font>。被多个线程访问的使用公正锁的程序，其整体性能可能比那些使用默认设置的程序差，但是在获取锁且避免资源缺乏时可以有更小的时间变化。

<div align=center><img src=Thread/ReentrantLock.png width=80%></div>

```java
// ReentrantLock.java

// 非公平锁：可以插队（默认使用非公平锁）
public ReentrantLock() {sync = new NonfairSync();}

// 传入的布尔值如果为true，则为公平锁
// 公平锁：先来后到
public ReentrantLock(boolean fair) 
{
    sync = fair ? new FairSync() : new NonfairSync();
}
```

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
                    }
                    catch (InterruptedException e)
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

### `synchronized`与`Lock`的对比

**传统方式和使用Lambda创建线程**：
```java
// 传统方式
private static void thread01(){
    new Thread(new Runnable() 
    {
        @Override
        public void run() 
        {
            //业务代码
            System.out.println("传统方式创建线程-》"+Thread.currentThread().getName());
        }
    }).start();
}

// 使用Lambda
private static void thread02(){
    new Thread(() -> {
        //业务代码
        System.out.println("lambda方式创建线程-》"+Thread.currentThread().getName());
    }).start();
}
```

**使用`synchronized`同步**：

```java {.line-numbers highlight=56}
/**
 * 真正的多线程开发，公司中的开发，降低耦合性
 * 线程就是一个单独的资源类，没有任何附属的操作！
 * OOP 属性、方法
 */
public class SaleTicketDemo01
{
    public static void main(String[] args)
    {
        // 并发：多线程操作同一个资源类, 把资源类丢入线程
        Ticket ticket = new Ticket();

        // @FunctionalInterface 函数式接口，jdk1.8  lambda表达式 (参数)->{ 代码 }
        new Thread(()->{
            for (int i = 1; i < 40 ; i++)
                ticket.sale();
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 40 ; i++)
                ticket.sale();
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 40 ; i++)
                ticket.sale();
        },"C").start();

    }
}

// 资源类 OOP
class Ticket
{
    // 属性、方法
    private int number = 30;

    // 卖票的方式

    /*
    public void sale()
    {
        if (number > 0)
            System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票,剩余：" + number);
    }

    A卖出了6票,剩余：5
    A卖出了5票,剩余：4
    C卖出了9票,剩余：8
    C卖出了3票,剩余：2
    B卖出了14票,剩余：13
    B卖出了1票,剩余：0
     */

    // synchronized 本质: 队列，锁
    public synchronized void sale()
    {
        if (number > 0)
            System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票,剩余：" + number);
    }
    /*
    A卖出了第26张票,剩余：25
    A卖出了第25张票,剩余：24
    B卖出了第24张票,剩余：23
    B 卖出了第23张票,剩余：22
    B卖出了第22张票,剩余：21
     */
}
```

**使用锁**：
```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketDemo02
{
    public static void main(String[] args)
    {

        // 并发：多线程操作同一个资源类, 把资源类丢入线程
        Ticket2 ticket = new Ticket2();

        // @FunctionalInterface 函数式接口，jdk1.8  lambda表达式 (参数)->{ 代码 }
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"A").start();
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"B").start();
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"C").start();


    }
}

// Lock三部曲
// 1、 new ReentrantLock();  //建锁
// 2、 lock.lock(); // 加锁
// 3、 finally=>  lock.unlock(); // 解锁

class Ticket2
{
    // 属性、方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale()
    {

        lock.lock(); // 加锁

        try
        {
           // 业务代码
            if (number>0)
                System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票，剩余：" + number);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock(); // 解锁
        }
    }
}
```

`synchronized`和`Lock`区别：

* `synchronized`内置的Java**关键字**，`Lock`是一个Java**类**；
* `synchronized`无法判断获取锁的状态，`Lock`可以判断是否获取到了锁；
* `Lock`是显示锁（手动开启和关闭锁）必须手动释放锁！如果不释放，则会出现死锁；`sychronized`是隐式锁，出了作用域自动释放；
* `Lock`只有代码块锁，`sychronized`有代码块锁和方法锁；
* `synchronized`线程1（获得锁，阻塞）、线程2（等待，傻傻的等）；`Lock`锁就不一定会等待下去（`lock.tryLock();`）；
* 使用`Lock`锁，`JVM`将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）。
* `synchronized`可重入锁，不可以中断的，**非公平**；`Lock`可重入锁，可以判断锁，是否公平可以自己设置；
* `synchronized`适合锁少量的代码同步问题，`Lock`适合锁大量的同步代码！
* 优先使用顺序：`Lock` > 同步代码块 > 同步方法


# 线程通信

## 监视器

**锁和条件**是`Java 5`中的新内容。在`Java 5`之前，线程通信是使用对象的`内置监视器`编程实现的。锁和条件比内置监视器更加强大且灵活，因此无须使用监视器。然而，如果使用遗留的Java代码，就可能会碰到Java的内置监视器。

&emsp;&emsp;监视器(monitor)是一个相互排斥且具备同步能力的对象。监视器中的一个时间点上，只能有一个线程执行一个方法。线程通过获取监视器上的锁进入监视器，并且通过释放锁退出监视摇。任意对象都可能是一个监视器。一旦一个线程锁住对象，该对象就成为监视器。加锁是通过在方法或块上使用`synchronized`关键字来实现的。在执行同步方法或块之前，线程必须获取锁。如果条件不适合线程继续在监视器内执行，线程可能在监视器中等待。可以对监视器对象调用`wait()`方法来释放锁，这样其他的一些监视器中的线程就可以获取它，也就有可能改变监视器的状态。当条件合适时，另一线程可以调用`notify()`或`notifyAll()`方法来通知一个或所有的等待线程重新获取锁并且恢复执行。

<div align=center><img src=Thread/监视器.png></div>

## Condition

`Lock`替换`synchronized`方法和语句的使用；`Condition`取代了**对象监视器**方法的使用。

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


### 生产者/消费者示例

#### 使用synchronized

`synchronized`、`wait`、`notifyAll`

```java
/**
 * 线程之间的通信问题：生产者和消费者问题！  等待唤醒，通知唤醒
 * 线程交替执行  A   B 操作同一个变量   num = 0
 * A num+1
 * B num-1
 */

public class ProducerAndConsumer
{
    public static void main(String[] args)
    {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.increment();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.decrement();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.increment();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"C").start();


        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.decrement();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

// 判断是否需要等待，业务，通知
class Data
{ // 数字 资源类

    private int number = 0;

    //+1
    public synchronized void increment() throws InterruptedException
    {
        while (number != 0)
        {  //0
            // 等待
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，我+1完毕了
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException
    {
        while (number == 0)
        { // 1
            // 等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，我-1完毕了
        this.notifyAll();
    }

}
/*
A=>1
B=>0
A=>1
B=>0
C=>1
D=>0
C=>1
D=>0
 */
```

##### 防止虚假唤醒

虚假唤醒：线程也可以唤醒，而不被通知，中断或超时。等待应该总是出现在循环中：
```java
synchronized (obj)
{
    while(condition)
    {
        obj.wait(timeout);
    }
}
```

如果`while`换成`if`出现问题：
```java
class Data
{ // 数字 资源类

    private int number = 0;

    //+1
    public synchronized void increment() throws InterruptedException
    {
        //while (number != 0)
        if (number != 0)
        {  //0
            // 等待
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，我+1完毕了
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException
    {
        //while (number == 0)
        if (number == 0)
        { // 1
            // 等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，我-1完毕了
        this.notifyAll();
    }
}
/*
B=>0
C=>1
A=>2
C=>3
A=>4
*/
```

#### 使用JUC.Condition

`lock`、`await`、`signal`

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


```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer1
{
    public static void main(String[] args)
    {
        Data2 data = new Data2();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.increment();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.decrement();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.increment();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    data.decrement();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

// 判断等待，业务，通知
class Data2{ // 数字 资源类

    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    //condition.await(); // 等待
    //condition.signalAll(); // 唤醒全部

    //+1
    public void increment() throws InterruptedException
    {
        lock.lock();
        try
        {
            // 业务代码
            while (number != 0)
            {  //0
                // 等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，我+1完毕了
            condition.signalAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }

    }

    //-1
    public void decrement() throws InterruptedException
    {
        lock.lock();
        try
        {
            while (number == 0)
            { // 1
                // 等待
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，我-1完毕了
            condition.signalAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
}
```

##### Condition精准的通知和唤醒线程

上述结果是随机的：
```
A=>1
B=>0
A=>1
B=>0
C=>1
D=>0
C=>1
D=>0
```

希望线程有序执行：`A B C D`。

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A 执行完调用B，B执行完调用C，C执行完调用A
 */

public class ProducerAndConsumer2
{
    public static void main(String[] args)
    {
        Data3 data = new Data3();

        new Thread(() -> { for (int i = 0; i <10 ; i++) data.printA(); },"A").start();
        new Thread(() -> { for (int i = 0; i <10 ; i++) data.printB(); },"B").start();
        new Thread(() -> { for (int i = 0; i <10 ; i++) data.printC(); },"C").start();
    }
}

class Data3{ // 资源类 Lock

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int number = 1; // 1A  2B  3C

    public void printA()
    {
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            while (number != 1)
            {
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>AAAAAAA");
            // 唤醒，唤醒指定的人，B
            number = 2;
            condition2.signal();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void printB()
    {
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            while (number != 2)
            {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>BBBBBBBBB");
            // 唤醒，唤醒指定的人，C
            number = 3;
            condition3.signal();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    public void printC()
    {
        lock.lock();
        try {
            // 业务，判断-> 执行-> 通知
            // 业务，判断-> 执行-> 通知
            while (number != 3)
            {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>CCCCCCCCC");
            // 唤醒，唤醒指定的人，A
            number = 1;
            condition1.signal();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
}
/*
A=>AAAAAAA
B=>BBBBBBBBB
C=>CCCCCCCCC
A=>AAAAAAA
B=>BBBBBBBBB
 */
```

# 关于锁的8个问题

如何判断锁的是谁！永远的知道什么锁，锁到底锁的是谁！

一个同步方法在执行之前需要加锁。锁是一种实现资源排他使用的机制。对于**实例方法**，要给**调用该方法的对象加锁**。对于**静态方法**，要给这个**类**加锁。

**synchronized锁的对象是方法的调用者**
```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 关于锁的8个问题
 * 1、标准情况下(sendSms不延迟)，两个线程先打印”发短信“还是”打电话“？
 * 输出：1/发短信  2/打电话
 * 2、sendSms延迟4秒，两个线程先打印”发短信“还是”打电话“？
 * 输出：1/发短信  2/打电话
 */
public class Test1
{
    public static void main(String[] args)
    {
        Phone phone = new Phone();

        //锁的存在
        new Thread(() -> { phone.sendSms(); },"A").start();  // 先调用并不一定先执行

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(() -> { phone.call(); },"B").start();
    }
}

class Phone
{
    // synchronized锁的对象是方法的调用者Phone！
    // 两个方法用的是同一个锁，谁先拿到谁执行！
    public synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(0);  // sendSms延迟4s
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 *
 * 3、增加了一个普通方法后！先执行”发短信：还是“Hello”？
 * sendSms有延迟2s，输出：普通方法
 */
public class Test2
{
    public static void main(String[] args)
    {
        Phone2 phone = new Phone2();
        /*
        // 两个对象，两个调用者，两把锁！
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();*/

        //锁的存在
        new Thread(()->{ phone.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone.hello(); },"B").start();
    }
}


class Phone2
{
    // synchronized锁的对象是方法的调用者！
    public synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call()
    {
        System.out.println("打电话");
    }

    // 这里没有锁！不是同步方法，不受锁的影响
    public void hello(){ System.out.println("hello"); }
}
```

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 4、两个对象，两个同步方法， “发短信”还是“打电话”？
 * sendSms有延迟，输出：打电话
 */
public class Test3
{
    public static void main(String[] args)
    {
        // 两个对象，两个调用者，两把锁！
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();

        //锁的存在
        new Thread(()->{ phone1.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone2.call(); },"B").start();
    }
}


class Phone3
{
    // synchronized 锁的对象是方法的调用者！
    public synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

**锁的是Class**

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 5、增加两个静态的同步方法，只有一个对象，先打印“发短信”还是“打电话”？
 * 输出：发短信
 */
public class Test4  
{
    public static void main(String[] args)
    {
        // 两个对象的Class类模板只有一个，static，锁的是Class
        Phone4 phone = new Phone4();

        //锁的存在
        new Thread(()->{ phone.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone.call(); },"B").start();
    }
}

// Phone4唯一的一个 Class 对象
class Phone4
{
    // synchronized 锁的对象是方法的调用者！
    // static 静态方法
    // 只要类一加载就有了！锁的是Class
    public static synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 6、两个对象！增加两个静态的同步方法，先打印“发短信”还是“打电话”？
 * 输出：发短信
 */
public class Test5
{
    public static void main(String[] args)
    {
        // 两个对象的Class类模板只有一个，static，锁的是Class
        Phone5 phone1 = new Phone5();
        Phone5 phone2 = new Phone5();

        //锁的存在
        new Thread(()->{ phone1.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone2.call(); },"B").start();
    }
}

// Phone5唯一的一个 Class 对象
class Phone5
{
    // synchronized 锁的对象是方法的调用者！
    // static 静态方法 只要类一加载就有了！锁的是Class
    public static synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

**锁调用者与锁class**

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 7、1个静态的同步方法，1个普通的同步方法，一个对象，先打印“发短信”还是“打电话”？
 * 输出：打电话
 */
public class Test6
{
    public static void main(String[] args)
    {
        // 两个对象的Class类模板只有一个，static，锁的是Class
        Phone6 phone = new Phone6();

        //锁的存在
        new Thread(()->{ phone.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone.call(); },"B").start();
    }
}

// Phone6唯一的一个 Class 对象
class Phone6
{
    // 静态的同步方法 锁的是 Class 类模板
    public static synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    // 普通的同步方法  锁的调用者
    public synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 7、1个静态的同步方法，1个普通的同步方法 ，一个对象，先打印“发短信”还是“打电话”？
 * 输出：打电话
 */
public class Test6
{
    public static void main(String[] args)
    {
        // 两个对象的Class类模板只有一个，static，锁的是Class
        Phone6 phone = new Phone6();

        //锁的存在
        new Thread(()->{ phone.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone.call(); },"B").start();
    }
}

// Phone6唯一的一个 Class 对象
class Phone6
{
    // 静态的同步方法 锁的是 Class 类模板
    public static synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    // 普通的同步方法  锁的调用者
    public synchronized void call()
    {
        System.out.println("打电话");
    }
}
```

# 集合类不安全

## List不安全

`java.util.ConcurrentModificationException`并发修改异常
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestList1
{
    public static void main(String[] args)
    {
        /*
        List<String> list = Arrays.asList("1", "2", "3");
        list.forEach(System.out::print);
        输出：123
        */

        List<String> list = new ArrayList<>();

        // 增大i，出现java.util.ConcurrentModificationException 并发修改异常
        for (int i = 1; i <= 100; i++) 
        {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
```

**解决方案**
- `List<String> list = new Vector<>();`
- `List<String> list = Collections.synchronizedList(new ArrayList<>());`
- `List<String> list = new CopyOnWriteArrayList<>();`（重要）

## Set不安全

```java
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
出现java.util.ConcurrentModificationException
 */
public class SetTest
{
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        for (int i = 1; i <= 300 ; i++)
        {
            new Thread(() ->
            {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
```

解决方案：
- `Set<String> set = Collections.synchronizedSet(new HashSet<>());`(通过工具类转化成synchronized)
- `Set<String> set = new CopyOnWriteArraySet<>();`(JUC，写入时复制，保证效率)

`HashSet`**底层是什么**？

```java
public HashSet() 
{
    map = new HashMap<>();
}

// add set本质就是map key是无法重复的！
public boolean add(E e) 
{
    return map.put(e, PRESENT)==null;
}

private static final Object PRESENT = new Object(); // 不变得值！
```

## Map不安全

```java
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// ConcurrentModificationException
public class MapTest
{
    public static void main(String[] args)
    {
        // map 是这样用的吗？ 不是，工作中不用 HashMap
        // Map<String, String> map = new HashMap<>();  不安全

        // 默认等价于什么？  new HashMap<>(16,0.75); 初始化容量，加载因子

        // 研究ConcurrentHashMap的原理
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <=30; i++)
        {
            new Thread(() ->
            {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
```

## JUC安全类型的集合
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

# 常用辅助类（必会）

## CountDownLatch

```java
import java.util.concurrent.CountDownLatch;

// 减法计数器
// 倒计时结束会执行某种操作
// 等到线程结束完毕
public class CountDownLatchDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        // 总数是6，必须要执行任务的时候，再使用！
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6 ; i++)
        {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " Go out");
                countDownLatch.countDown(); // 数量-1
            }, String.valueOf(i)).start();
        }

        countDownLatch.await(); // 等待计数器归零，然后再向下执行

        System.out.println("Close Door");
    }
}
/*
2 Go out
3 Go out
4 Go out
1 Go out
6 Go out
5 Go out
Close Door
 */
```

**原理**
- `countDownLatch.countDown();` // 数量-1
- `countDownLatch.await();` // 等待计数器归零，然后再向下执行
- 每次有线程调用`countDown()`，数量-1，假设计数器变为0，`countDownLatch.await()`就会被唤醒，继续执行！


## CyclicBarrier

```java
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 简单理解为加法计数器

public class CyclicBarrierDemo
{
    public static void main(String[] args)
    {
        /*
         * 集齐7颗龙珠召唤神龙
         */
        // 召唤龙珠的线程
        // 如果parties=8则一直在等待
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙成功！"); });

        for (int i = 1; i <=7 ; i++)
        {
            final int temp = i;
            // lambda能操作到 i 吗
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集" + temp + "个龙珠");  // 不能直接得到for中的i
                try
                {
                    cyclicBarrier.await(); // 等待
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (BrokenBarrierException e)
                {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
/*
Thread-0收集1个龙珠
Thread-1收集2个龙珠
Thread-4收集5个龙珠
Thread-2收集3个龙珠
Thread-5收集6个龙珠
Thread-6收集7个龙珠
Thread-3收集4个龙珠
召唤神龙成功！
 */
```

## Semaphore信号量

抢车位！6辆车抢3个停车位置。

```java
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo
{
    public static void main(String[] args)
    {
        // 线程数量：停车位！ 限流！
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                try
                {
                    // acquire() 得到
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(0);  // 停车时间
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    // release() 释放
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
/*
6抢到车位
6离开车位
3抢到车位
5抢到车位
5离开车位
3离开车位
1抢到车位
1离开车位
2抢到车位
4抢到车位
4离开车位
2离开车位
 */
```

**原理**
- `semaphore.acquire()`获得，假设如果已经满了，等待，等待被释放为止！
- `semaphore.release();` 释放，会将当前的信号量释放+1，然后唤醒等待的线程！

作用：多个共享资源互斥的使用！并发限流，控制最大的线程数！


# 读写锁

```java
public interface ReadWriteLock
```

读可以被多线程同时读，写的时候只能有一个线程去写！

```java
package Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁（写锁） 一次只能被一个线程占有
 * 共享锁（读锁） 多个线程可以同时占有
 * ReadWriteLock
 * 读-读  可以共存！
 * 读-写  不能共存！
 * 写-写  不能共存！
 */
public class ReadWriteLockDemo
{
    public static void main(String[] args)
    {
        MyCache myCache = new MyCache();

        // MyCacheLock myCache = new MyCacheLock();

        // 写入
        for (int i = 1; i <= 5 ; i++)
        {
            final int temp = i;
            new Thread(() -> { myCache.put(temp + " ", temp + " "); }, String.valueOf(i)).start();
        }

        // 读取
        for (int i = 1; i <= 5 ; i++)
        {
            final int temp = i;
            new Thread(() -> { myCache.get(temp + " "); }, String.valueOf(i)).start();
        }
    }
}

// 加锁的
class MyCacheLock
{

    private volatile Map<String, Object> map = new HashMap<>();
    // 读写锁： 更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock lock = new ReentrantLock();

    // 存，写入的时候，只希望同时只有一个线程写
    public void put(String key, Object value)
    {
        readWriteLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入OK");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    // 取，读，所有人都可以读！
    public void get(String key)
    {
        readWriteLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取OK");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }
    /*

    3写入3
    3写入OK
    4读取4
    5读取5
    3读取3
    3读取OK
    5读取OK
    4读取OK
     */

}

/**
 * 自定义缓存
 */
class MyCache
{

    private volatile Map<String, Object> map = new HashMap<>();

    // 存，写
    public void put(String key, Object value)
    {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入OK");
    }

    // 取，读
    public void get(String key)
    {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取OK");
    }

    /*
    1写入1
    2写入2  // 写时被插队
    5写入5
    5写入OK
    3写入3
     */
}
```

# 阻塞队列

- 写入，如果队列满了，就必须阻塞等待；
- 取：如果队列是空的，必须阻塞等待生产！

多线程并发处理和线程池需要用到阻塞队列！


学会使用队列：
方式 | 抛出异常 | 有返回值，不抛出异常 | 阻塞等待 | 超时等待
:-: | :-: | :-: | :-: | :-:
添加 | add() | offer() | put() | offer(...)| 
移除 | remove()| poll() | take() | poll(...)|
检测队首元素 | element()| peek() | - | -|

```java
package BlockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test
{
    //public static void main(String[] args)
    public static void main(String[] args) throws InterruptedException  // test3()test4()需要
    {
        test4();
    }

    /**
     * 1. 抛出异常
     */
    public static void test1()
    {
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        /*
        public boolean add(E e) {return super.add(e);}
         */
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        // IllegalStateException: Queue full 抛出异常！
        // System.out.println(blockingQueue.add("d"));

        System.out.println("====");

        System.out.println(blockingQueue.element()); // 查看队首元素是谁
        System.out.println("==");

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // java.util.NoSuchElementException 抛出异常！
        // System.out.println(blockingQueue.remove());
    }

    /**
     * 2. 有返回值，不抛出异常
     */
    public static void test2()
    {
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d")); // false 不抛出异常！
        System.out.println("====");

        System.out.println(blockingQueue.peek());

        System.out.println("==");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll()); // null  不抛出异常！
    }

    /**
     * 3. 等待，阻塞（一直阻塞）
     */
    public static void test3() throws InterruptedException
    {
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        // 一直阻塞
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("d"); // 队列没有位置了，一直阻塞

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        //System.out.println(blockingQueue.take()); // 没有这个元素，一直阻塞
    }

    /**
     * 4. 等待，阻塞（等待超时）
     */
    public static void test4() throws InterruptedException
    {
        // 队列的大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        blockingQueue.offer("d",2,TimeUnit.SECONDS); // 等待超过2秒就退出
        System.out.println("===============");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        blockingQueue.poll(2, TimeUnit.SECONDS); // 等待超过2秒就退出
    }
}
```

## SynchronousQueue同步队列

没有容量，进去一个元素(put)，必须等待取出来(take)之后，才能再往里面放一个元素！

```java
package BlockQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列
 * 和其他的BlockingQueue 不一样， SynchronousQueue 不存储元素
 * put了一个元素，必须从里面先take取出来，否则不能在put进去值！
 */

public class SynchronousQueueDemo
{
    public static void main(String[] args)
    {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>(); // 同步队列

        new Thread(() -> {
            try
            {
                System.out.println(Thread.currentThread().getName() + " put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + " put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + " put 3");
                blockingQueue.put("3");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "T1").start();


        new Thread(()->{
            try
            {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " take " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " take " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " take " + blockingQueue.take());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "T2").start();
    }
}
/*
T1 put 1
T2 take 1
T1 put 2
T2 take 2
T1 put 3
T2 take 3
 */
```

# 再探线程池

程序的运行，本质：占用系统的资源！ 

创建、销毁，十分浪费资源。优化资源的使用！=> 池化技术

线程池、连接池、内存池、对象池..... 

池化技术：事先准备好一些资源，有人要用，就来我这里拿，用完之后还给我。

线程池：**三大方法、7大参数、四种拒绝策略**。

线程池的好处：线程复用、可以控制最大并发数、管理线程
1、降低资源的消耗
2、提高响应的速度
3、方便管理。

## 三大方法

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Executors 工具类、3大方法

public class Pool1
{
    public static void main(String[] args) {
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();// 单个线程
        // ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建一个固定的线程池的大小
        ExecutorService threadPool = Executors.newCachedThreadPool(); // 可伸缩的，遇强则强，遇弱则弱
        try {
            for (int i = 0; i < 100; i++) {
                // 使用了线程池之后，使用线程池来创建线程
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完，程序结束，关闭线程池
            threadPool.shutdown();
        }
    }
}
/*
ExecutorService threadPool = Executors.newSingleThreadExecutor()
...
pool-1-thread-1 ok
pool-1-thread-1 ok
pool-1-thread-1 ok
pool-1-thread-1 ok
pool-1-thread-1 ok
...

ExecutorService threadPool = Executors.newFixedThreadPool(5);
...
pool-1-thread-1 ok
pool-1-thread-2 ok
pool-1-thread-3 ok
pool-1-thread-1 ok
pool-1-thread-5 ok
pool-1-thread-5 ok
pool-1-thread-4 ok
...

ExecutorService threadPool = Executors.newCachedThreadPool();
...
pool-1-thread-6 ok
pool-1-thread-23 ok
pool-1-thread-8 ok
pool-1-thread-9 ok
pool-1-thread-37 ok
pool-1-thread-36 ok
...
 */
```

## 七大参数

源码：
```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService(
        new ThreadPoolExecutor(1, 1, 0L, 
        TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>()));
}

public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads, 0L, 
    TimeUnit.MILLISECONDS,
    new LinkedBlockingQueue<Runnable>());
}

public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, 
    TimeUnit.SECONDS,
    new SynchronousQueue<Runnable>());
}
```

本质都是调用`ThreadPoolExecutor`：
```java
public ThreadPoolExecutor(int corePoolSize,  // 1.核心线程池大小
                            int maximumPoolSize,  // 2.最大核心线程池大小
                            long keepAliveTime,  // 3.超时了没有人调用就会释放
                            TimeUnit unit,  // 4.超时单位
                            BlockingQueue<Runnable> workQueue,  // 5.阻塞队列
                            ThreadFactory threadFactory,  // 6.线程工厂：创建线程的，一般不用动
                            // 7.拒绝策略
                            RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

## ThreadPoolExecutor

《阿里巴巴Java手册》建议：线程池不允许使用`Executors`去创建，而是通过`ThreadPoolExecutor`的方式，可以更明确线程池的运行规则，规避资源耗尽的风险。

<div align=center><img src=Thread\线程池七大参数.jpg width=70%></div>

### 四种拒绝策略

* `new ThreadPoolExecutor.AbortPolicy()` // 银行满了，还有人进来，不处理这个人的，抛出异常
* `new ThreadPoolExecutor.CallerRunsPolicy()` // 哪来的去哪里！
* `new ThreadPoolExecutor.DiscardPolicy()` //队列满了，丢掉任务，不会抛出异常！
* `new ThreadPoolExecutor.DiscardOldestPolicy()` //队列满了，尝试去和最早的竞争，也不会抛出异常！

### 最大线程到底该如何设置（调优）
- CPU密集型：几核，就是几，可以保持CPU的效率最高！并行！`Runtime.getRuntime().availableProcessors()`
- IO密集型：判断你程序中十分耗IO的线程，大于其两倍（程序有15个大型任务，IO十分占用资源！，可设置成30。）

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Pool
{
    public static void main(String[] args) {
        // 自定义线程池！工作 ThreadPoolExecutor

        // 最大线程到底该如何定义
        // 1、CPU 密集型，几核，就是几，可以保持CPu的效率最高！
        // 2、IO  密集型   > 判断你程序中十分耗IO的线程，
        // 程序   15个大型任务  io十分占用资源！

        // 获取CPU的核数
        System.out.println(Runtime.getRuntime().availableProcessors());

        List list = new ArrayList();

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,  //Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());  //队列满了，尝试去和最早的竞争，也不会抛出异常！

        /* 四种拒绝策略
         * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异常
         * new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！
         * new ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！
         * new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会抛出异常！
         */

        try {
            // 最大承载：Deque + max
            // 超过 RejectedExecutionException
            for (int i = 1; i <= 9; i++) {
                // 使用了线程池之后，使用线程池来创建线程
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完，程序结束，关闭线程池
            threadPool.shutdown();
        }
    }
}
```


# 四大函数式接口（必须掌握）

新时代的程序员：**lambda表达式、链式编程、函数式接口、Stream流式计算**

函数式接口：只有一个方法的接口。

```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
// 泛型、枚举、反射：jdk1.5
// lambda表达式、链式编程、函数式接口、Stream流式计算：jdk1.8
// 超级多FunctionalInterface
// 简化编程模型，在新版本的框架底层大量应用！
// forEach(消费者类的函数式接口)
```

## Function

Function函数型接口：有一个输入参数，有一个输出！

```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
```

```java
package Function;

import java.util.function.Function;

/**
 * Function 函数型接口, 有一个输入参数，有一个输出
 * 只要是 函数型接口 可以用 lambda 表达式简化
 */

public class Demo1
{
    public static void main(String[] args) {
        /*
        Function<String, String> function = new Function<String, String>() {
           @Override
           public String apply(String str) {
                return str;
           }
        };*/

        Function<String, String> function = str -> {return str;};

        System.out.println(function.apply("asd"));
    }
}
```

## Predicate

断定型接口：有一个输入参数，返回值只能是布尔值！

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
```

```java
package Function;

import java.util.function.Predicate;

/**
 * 断定型接口：有一个输入参数，返回值只能是 布尔值！
 */
public class Demo2 {
    public static void main(String[] args) {
        // 判断字符串是否为空
        /*
       Predicate<String> predicate = new Predicate<String>(){
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };*/

        Predicate<String> predicate = (str) -> {return str.isEmpty(); };
        System.out.println(predicate.test("")); // true
        // System.out.println(predicate.test(" "));  // false
    }
}
```

## Consumer

Consumer消费型接口: 只有输入，没有返回值。

```java
public interface Consumer<T> {
    void accept(T t);
}
```

```java
package Function;

import java.util.function.Consumer;

/**
 * Consumer 消费型接口: 只有输入，没有返回值
 */
public class Demo3 {
    public static void main(String[] args) {
        /*
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str);
            }
        };*/

        Consumer<String> consumer = (str) -> {System.out.println(str);};
        consumer.accept("作用是打印字符串");
    }
}
```

## Supplier

Supplier供给型接口：没有参数，只有返回值！

```java
public interface Supplier<T> {
    T get();
}
```

```java
package Function;

import java.util.function.Supplier;

/**
 * Supplier 供给型接口 没有参数，只有返回值
 */
public class Demo4 {
    public static void main(String[] args) {
        /*
        Supplier supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("get()");
                return 1024;
            }
        };*/

        Supplier supplier = () -> { return 1024; };
        System.out.println(supplier.get());
    }
}
```


# Stream流式计算

大数据：存储（集合、MySQL本质就是存储东西的）+ 计算（计算都应该交给流来操作！）

新建`maven`项目，安装插件`lombok`，在`pom.xml`中添加：
```
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.12</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

`Project Structure`中`Project`和`Modules`下的`Project language level`都设置成`8 - Lambda...`。

```java
// User.java
package Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 有参，无参构造，get、set、toString方法！
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String name;
    private int age;
}

// Test.java
package Stream;

import java.util.Arrays;
import java.util.List;

/**
 * 题目要求：一分钟内完成此题，只能用一行代码实现！
 * 现在有5个用户！筛选：
 * 1、ID 必须是偶数
 * 2、年龄必须大于23岁
 * 3、用户名转为大写字母
 * 4、用户名字母倒着排序
 * 5、只输出一个用户！
 */
public class Test {
    public static void main(String[] args) {
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        User u4 = new User(4, "d", 24);
        User u5 = new User(6, "e", 25);

        // 集合就是存储
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        // 计算交给Stream流
        // lambda表达式、链式编程、函数式接口、Stream流式计算
        list.stream()
                .filter(u -> {return u.getId()%2 == 0;})
                .filter(u -> {return u.getAge() > 23;})
                .map(u -> {return u.getName().toUpperCase();})
                .sorted((uu1,uu2)->{return uu2.compareTo(uu1);})
                .limit(1)
                .forEach(System.out::println);
    }
}

/*
E
*/
```


# ForkJoin

ForkJoin（分支合并）在JDK 1.7，**并行执行任务**！提高效率。**大数据量**！

大数据：Map Reduce（把大任务拆分为小任务）

<div align=center><img src=Thread\ForkJoin.jpg width=60%></div>

ForkJoin特点：工作窃取！这个里面维护的都是双端队列！

A和B两个线程，A线程任务尚未执行完毕，而B线程的任务已经执行完，那么B线程会“窃取”A线程的任务去执行，不让线程去等待，提高了效率！

```java
// ForkJoinDemo.java

package ForkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * 求和（数据量大）计算的任务！
 * 简单for循环求和   ForkJoin  Stream并行流
 * // 如何使用 forkjoin
 * // 1、forkjoinPool 通过它来执行
 * // 2、计算任务 forkjoinPool.execute(ForkJoinTask task)
 * // 3. 计算类要继承 ForkJoinTask
 */

public class ForkJoinDemo extends RecursiveTask<Long>
{

    private Long start;  // 1
    private Long end;    // 1990900000

    // 临界值
    private Long temp = 10000L;  // 超过临界值，分成子任务

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    // 计算方法
    @Override
    protected Long compute() {
        if ((end - start) < temp){
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else { // ForkJoin 递归
            long middle = (start + end) / 2; // 中间值
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            task1.fork(); // 拆分任务，把任务压入线程队列

            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            task2.fork(); // 拆分任务，把任务压入线程队列

            return task1.join() + task2.join();
        }
    }
}


// Test.java

package ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 同一个任务，别人效率高你几十倍！
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // test1(); // 5820
        // test2(); // 4900
        test3(); // 110
    }

    // 普通程序员 简单for循环求和
    public static void test1(){
        Long sum = 0L;
        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + " 时间：" + (end - start));
    }

    // 会使用ForkJoin
    // 调节临界值来提高效率
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);// 提交任务
        Long sum = submit.get();

        long end = System.currentTimeMillis();

        System.out.println("sum = " + sum + " 时间：" + (end - start));
    }

    // Stream并行流
    public static void test3(){
        long start = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);

        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + "时间：" + (end - start));
    }
}
```

# 异步回调

Future设计的初衷：对将来的某个事件的结果进行建模！

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用： CompletableFuture
 * // 异步执行
 * // 成功回调
 * // 失败回调
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        // 没有返回值的 runAsync 异步回调
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "runAsync => Void");
        });

        System.out.println("1111");

        completableFuture.get(); // 获取阻塞执行结果*/

        // 有返回值的 supplyAsync 异步回调
        // ajax，成功和失败的回调
        // 失败返回的是错误信息；
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "supplyAsync => Integer");
            int i = 10/0;
            return 1024;
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t=> " + t); // 正常的返回结果
            System.out.println("u=> " + u); // 错误信息：java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233; // 可以获取到错误的返回结果
        }).get());
    }
}
/*
ForkJoinPool.commonPool-worker-19supplyAsync => Integer
t=> null
u=> java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
java.lang.ArithmeticException: / by zero
233  // 错误
 */
```

# JMM

> 请你谈谈你对`Volatile`的理解

`Volatile`是Java虚拟机提供轻量级的同步机制
- 保证可见性
- 不保证原子性
- 禁止指令重排

> 什么是JMM
> 
JMM：Java内存模型，不存在的东西，概念！约定！

关于JMM的一些同步的约定：
- 线程解锁前，必须把共享变量**立刻**刷回主存。
- 线程加锁前，必须读取主存中的最新值到工作内存中！
- 加锁和解锁是同一把锁

<div align=center><img src=Thread\JMM.jpg width=90%></div>

存在的问题：

<div align=center><img src=Thread\JMM1.jpg width=90%></div>

内存交互操作有8种，虚拟机实现必须保证每一个操作都是原子的，不可在分的（对于double和long类
型的变量来说，load、store、read和write操作在某些平台上允许例外）

- lock（锁定）：作用于主内存的变量，把一个变量标识为线程独占状态
- unlock（解锁）：作用于主内存的变量，它把一个处于锁定状态的变量释放出来，释放后的变量
才可以被其他线程锁定
- read（读取）：作用于主内存变量，它把一个变量的值从主内存传输到线程的工作内存中，以便
随后的load动作使用
- load（载入）：作用于工作内存的变量，它把read操作从主存中变量放入工作内存中
- use（使用）：作用于工作内存中的变量，它把工作内存中的变量传输给执行引擎，每当虚拟机
遇到一个需要使用到变量的值，就会使用到这个指令
- assign（赋值）：作用于工作内存中的变量，它把一个从执行引擎中接受到的值放入工作内存的变
量副本中
- store（存储）：作用于主内存中的变量，它把一个从工作内存中一个变量的值传送到主内存中，
以便后续的write使用
- write（写入）：作用于主内存中的变量，它把store操作从工作内存中得到的变量的值放入主内
存的变量中

JMM对这八种指令的使用，制定了如下规则：
- 不允许read和load、store和write操作之一单独出现。即使用了read必须load，使用了store必须write
- 不允许线程丢弃他最近的assign操作，即工作变量的数据改变了之后，必须告知主存
- 不允许一个线程将没有assign的数据从工作内存同步回主内存
- 一个新的变量必须在主内存中诞生，不允许工作内存直接使用一个未被初始化的变量。就是怼变量实施use、store操作之前，必须经过assign和load操作
- 一个变量同一时间只有一个线程能对其进行lock。多次lock后，必须执行相同次数的unlock才能解锁
- 如果对一个变量进行lock操作，会清空所有工作内存中此变量的值，在执行引擎使用这个变量前，必须重新load或assign操作初始化变量的值
- 如果一个变量没有被lock，就不能对其进行unlock操作。也不能unlock一个被其他线程锁住的变量
- 对一个变量进行unlock操作之前，必须把此变量同步回主内存

# Volatile

`Volatile`是Java虚拟机提供轻量级的同步机制
- 保证可见性
- 不保证原子性
- 禁止指令重排

## 保证可见性

```java
package VolatileTest;

import java.util.concurrent.TimeUnit;

public class JMMDemo {
    // 不加 volatile 程序就会死循环！
    // 加 volatile 可以保证可见性
    private volatile static int num = 0;  // 使线程1线程知道num发生了变化

    public static void main(String[] args) { // main线程

        new Thread(() -> { // 线程1对主内存的变化不知道的
            while (num == 0){ }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(num);
    }
}
```

<div align=center><img src=Thread\JMM2.jpg width=90%></div>

## 不保证原子性

原子性：不可分割
线程A在执行任务的时候，不能被打扰的，也不能被分割。要么同时成功，要么同时失败。

```java
package VolatileTest;

// volatile 不保证原子性
public class JMMDemo1 {

    // volatile 不保证原子性
    private volatile static int num = 0;

    public static void add(){
        num++; // 不是一个原子性操作
    }

    public static void main(String[] args) {

        //理论上num结果应该为 2 万
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000 ; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2){ // main和gc，这两个线程默认在执行
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " " + num); // main 18250
    }
}
```


如果不加`lock`和`synchronized`，怎么样保证原子性：（`num++;` // 不是一个原子性操作）

<div align=center><img src=Thread\JMM3.jpg width=90%></div>


使用**原子类**，解决原子性问题：

```java
package VolatileTest;

import java.util.concurrent.atomic.AtomicInteger;

// volatile 不保证原子性
public class JMMDemo2 {

    // volatile 不保证原子性
    // 原子类的 Integer
    private volatile static AtomicInteger num = new AtomicInteger();

    public static void add(){
        // num++; // 不是一个原子性操作
        num.getAndIncrement(); // AtomicInteger + 1 方法， CAS
    }

    public static void main(String[] args) {

        //理论上num结果应该为 2 万
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000 ; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ // main和gc，这两个线程默认在执行
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " " + num);  // main 20000
    }
}
```

`getAndIncrement(); // AtomicInteger + 1 方法， CAS`
`native`这些类的底层都直接和操作系统挂钩！在内存中修改值！`Unsafe类`是一个很特殊的存在！

## 指令重排

什么是**指令重排**：你写的程序，计算机并不是按照你写的那样去执行的。

源代码-->编译器优化的重排--> 指令并行也可能会重排--> 内存系统也会重排---> 执行

处理器在进行指令重排的时候，考虑：**数据之间的依赖性**！

```
int x = 1; // 1
int y = 2; // 2
x = x + 5; // 3
y = x * x; // 4
我们所期望的：1234 但是可能执行的时候回变成 2134 1324，不可能是 4123！
```

可能造成影响的结果： a b x y 这四个值默认都是 0：
线程A | 线程B | 
:-: | :-: | 
x = a | y = b | 
b = 1 | a = 2 | 

正常的结果： x = 0；y = 0；但是可能由于指令重排
线程A | 线程B | 
:-: | :-: | 
b = 1 | a = 2 | 
x = a | y = b | 

指令重排导致的诡异结果： x = 2；y = 1；

volatile可以避免指令重排：
**内存屏障**。CPU指令。作用：
1、保证特定的操作的执行顺序！
2、可以保证某些变量的内存可见性 （利用这些特性volatile实现了可见性）

<div align=center><img src=Thread\内存屏障.jpg width=40%></div>

Volatile是可以保持 可见性。不能保证原子性，由于内存屏障，可以保证避免指令重排的现象产生！

内存屏障在**单例模式**中使用最多！


# 彻底玩转单例模式

```java
// Hungry.jva
package Single;

// 饿汉式单例
public class Hungry {

    // 可能会浪费空间
    private byte[] data1 = new byte[1024*1024];
    private byte[] data2 = new byte[1024*1024];
    private byte[] data3 = new byte[1024*1024];
    private byte[] data4 = new byte[1024*1024];

    private Hungry(){ }

    private final static Hungry HUNGRY = new Hungry();

    public static Hungry getInstance(){
        return HUNGRY;
    }

}

// LazyMan.java

package Single;

// import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

// 懒汉式单例
// 道高一尺，魔高一丈！
public class LazyMan {

    private static boolean qinjiang = false;

    private LazyMan(){
        synchronized (LazyMan.class){
            if (qinjiang == false){
                qinjiang = true;
            }else {
                throw new RuntimeException("不要试图使用反射破坏异常");
            }
        }
    }

    private volatile static LazyMan lazyMan;

    // 双重检测锁模式的 懒汉式单例  DCL懒汉式
    public static LazyMan getInstance(){
        if (lazyMan == null){
            synchronized (LazyMan.class){
                if (lazyMan == null){
                    lazyMan = new LazyMan(); // 不是一个原子性操作
                    /*
                     * 1. 分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把这个对象指向这个空间
                     *
                     * 期望顺序：123
                     * 可能顺序：132 A
                     *                           B // 此时lazyMan还没有完成构造
                     */
                }
            }
        }
        return lazyMan;
    }

    // 反射！
    public static void main(String[] args) throws Exception {
      // LazyMan instance = LazyMan.getInstance();

        Field qinjiang = LazyMan.class.getDeclaredField("qinjiang");
        qinjiang.setAccessible(true);

        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan instance = declaredConstructor.newInstance();

        qinjiang.set(instance,false);

        LazyMan instance2 = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance2);
    }
}


// Holder.java
package Single;

// 静态内部类，不安全
public class Holder {
    // 构造其私有
    private Holder(){ }

    public static Holder getInstace(){
        return InnerClass.HOLDER;
    }

    public static class InnerClass{
        private static final Holder HOLDER = new Holder();
    }
}


// EnumSingle.java

package Single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// enum 是一个什么？ 本身也是一个Class类
public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }
}

class Test{

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingle instance1 = EnumSingle.INSTANCE;
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle instance2 = declaredConstructor.newInstance();

        // NoSuchMethodException: com.kuang.single.EnumSingle.<init>()
        System.out.println(instance1);
        System.out.println(instance2);
    }
}
```


