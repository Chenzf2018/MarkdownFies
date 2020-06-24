# 基本概念

**进程和线程的区别**：


* **一个进程**(process)可以有**多个线程**(thread)：视频中声音与画面；
* 进程是程序的一次执行过程；**真正的多线程是指有多个CPU**（线程是CPU调度和执行的单位）。在一个CPU情况下，在一个时间点，CPU只能执行一个代码，模拟出来的多线程是因为**切换**得很快，产生同时执行的假象；
* 对一份资源，会存在资源抢夺的问题，需要加入并发控制。

CPU：工厂；
进程：车间；
资源：车间里的房间；
线程：工人。

1.计算机的核心是CPU，它承担了所有的计算任务。它就像一座**工厂**，时刻在运行。假定**工厂的电力有限**，一次只能供给一个车间使用。也就是说，一个车间开工的时候，其他车间都必须停工。背后的含义就是，**单个CPU一次只能运行一个任务**。

2.**进程**就好比**工厂的车间**，它代表CPU所能处理的单个任务。**任一时刻，CPU总是运行一个进程**，其他进程处于非运行状态。

3.**线程**就好比车间里的**工人**。一个进程可以包括多个线程。

4.车间的空间是工人们共享的，比如许多**房间**是每个工人都可以进出的。这象征**一个进程的内存空间是共享的**，每个线程都可以使用这些共享内存。

5.可是，每间房间的大小不同，有些房间最多只能容纳一个人，比如厕所。里面有人的时候，其他人就不能进去了。这代表**一个线程使用某些共享内存时，其他线程必须等它结束，才能使用这一块内存**。一个防止他人进入的简单方法，就是**门口加一把锁。先到的人锁上门，后到的人看到上锁，就在门口排队，等锁打开再进去**。这就叫 “**互斥锁**”（Mutual exclusion，缩写**Mutex**），**防止多个 线程同时读写某一块内存区域**。

6.还有些房间，可以同时容纳n个人，比如厨房。也就是说，如果人数大于n，多出来的人只能在外面等着。这好比某些内存区域，只能供给**固定数目的线程**使用。这时的解决方法，就是**在门口挂n把钥匙**。**进去的人就取一把钥匙，出来时再把钥匙挂回原处。后到的人发现钥匙架空了，就知道必须在门口排队等着了**。这种做法叫做 “**信号量**”（Semaphore），用来保证多个线程不会互相冲突。

**Mutex(互斥锁)是Semaphore(信号量)的一种特殊情况(n=1时)**。也就是说，完全可以用后者替代前者。但是，因为Mutex较为简单，且效率高，所以在必须保证资源独占的情况下，还是采用这种设计。


## 并发编程三个核心

并发编程可以抽象成三个核心: **分工、同步/协作、互斥**！

- 分工：合适的线程才能更好的完成整块工作，当然一个线程可以轻松搞定的就没必要多线程；主线程应该做的事交给子线程显然是解决不了问题的，每个线程做正确的事才能发挥作用。常见的**Executor，生产者-消费者模式，Fork/Join**等，都是分工思想的体现。

- 同步/协作：任务拆分完毕，我要等张三的任务，张三要等李四的任务，也就是说**任务之间存在依赖关系**，前面的任务执行完毕，后面的任务才可以执行面对程序。我们需要了解**程序的沟通方式**，**一个线程执行完任务，如何通知后续线程执行**。Java SDK中**`CountDownLatch`和`CyclicBarrier`**就是用来解决线程协作问题的。

- 互斥：**同一时刻，只允许一个线程访问共享变量**。**分工和同步强调的是性能，但是互斥是强调正确性**，就是我们常常提到的**线程安全**。当多个线程同时访问一个共享变量/成员变量时，就可能发生不确定性。造成不确定性主要是有**可见性、原子性、有序性**这三大问题，而解决这些问题的核心就是互斥。**synchronized关键字，Lock，ThreadLocal**等就是互斥的解决方案。

<div align=center><img src=Thread\并发编程三大核心.png></div>


## 造成线程不安全的三大问题

### 可见性

**一个线程对共享变量的修改，另外一个线程能够立刻看到**，我们称为可见性。

谈到可见性，要先引出**JMM(Java Memory Model)**概念，即**Java内存模型**，Java内存模型规定：
- 将所有的变量都存放在`主内存`中
- 当线程使用变量时，会把主内存里面的变量`复制`到自己的工作空间或者叫作`私有内存`，**线程读写变量时操作的是自己工作内存中的变量**。
- 线程解锁前，必须把共享变量立刻刷回主存；线程加锁前，必须读取主存中的最新值到工作内存中；加锁和解锁是同一把锁。

用Git的工作流程理解上面的描述就很简单了，**Git远程仓库就是主内存，Git本地仓库就是自己的工作内存**：

<div align=center><img src=Thread\可见性.png width=50%></div>

**线程可见性问题**：

- 主内存中有变量x，初始值为0；
- 线程A要将x加1：先将x=0拷贝到自己的私有内存中，然后更新x的值；
- 线程A将更新后的x值回刷到主内存的**时间是不固定的**；
- **刚好在线程A没有回刷x到主内存时，线程B同样从主内存中读取x，此时为0**。和线程A一样的操作，最后期盼的x=2就会变成x=1。

在Java中，所有的**实例域，静态域和数组元素都存储在堆内存**中，堆内存在线程之间共享，称之为**共享变量**(共享变量才会存在可见性问题)。局部变量，方法定义参数和异常处理器参数不会在线程之间共享，所以他们不会有内存可见性的问题，也就不受内存模型的影响。


### 原子性

所谓原子操作是指**不会被线程调度机制打断的操作**；**这种操作一旦开始，就一直运行到结束，中间不会有任何context switch**。

```java{.line-numbers highlight=9}
package Thread;

public class UnsafeCounter {
    private long count;

    private void counter() {
        long start = 0;
        while (start++ < 10000)
            count++;
    }

    private long getCount() {
        return count;
    }

    public static void main(String[] args) {
        try {
            UnsafeCounter unsafeCounter = new UnsafeCounter();

            Thread thread1 = new Thread(unsafeCounter::counter, "线程thread1");
            Thread thread2 = new Thread(unsafeCounter::counter, "线程thread2");

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println(unsafeCounter.getCount());  // 13572

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

不能用高级语言思维来理解CPU的处理方式，**`count++`转换成CPU指令需要三步**，不是单一操作，所以不是原子性的！

<div align=center><img src=Thread\原子性.png width=80%></div>

多线程计数器，如何保证多个操作的原子性呢？最粗暴的方式是在方法上加 `synchronized`关键字：

```java
private synchronized void counter() {
    long start = 0;
    while (start++ < 10000)
        count++;
}

private synchronized long getCount() {
    return count;
}
```

问题是解决了，如果synchronized是万能良方，那么也许并发就没那么多事了，可以靠一个synchronized走天下了，事实并不是这样。

synchronized是**独占锁** (同一时间只能有一个线程可以调用)，没有获取锁的线程会被**阻塞**；另外也会带来很多线程切换的**上下文开销**（*你(CPU)在看两本书(**两个线程**)，看第一本书很短时间后要去看第二本书，看第二本书很短时间后又回看第一本书，并**要精确的记得看到第几行**，当初看到了什么(CPU记住**线程级别的信息**)，当让你“同时”看10本甚至更多，切换的开销就很大了*）。

所以JDK中就有了**非阻塞CAS(Compare and Swap)算法**实现的原子操作类 `AtomicLong`等工具类。

所有原子类中都有下面这样一段代码:`private static final Unsafe unsafe = Unsafe.getUnsafe();`，这个类是JDK的`rt.jar`包中的`Unsafe`类提供了`硬件级别`的原子性操作，类中的方法都是`native`修饰的。

**减少上下文切换**的方法有**无锁并发编程、CAS算法、使用最少线程和使用协程**（在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换）。


### 有序性

**指令重排**：你写的程序，计算机并不是按照你写的那样去执行的。

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


**总结：**

你所看到的程序并不一定是**编译器优化/编译后的CPU指令**！**大象装冰箱是是个程序，但其隐含三个步骤**。学习并发编程，你要按照CPU的思维考虑问题，所以你需要深刻理解**可见性/原子性/有序性** ，这是产生并发Bug的源头。


# 线程的实现(重点)

在Java中，当我们启动`main函数`时其实就启动了一个JVM的进程，而main函数所在的线程就是这个进程中的一个线程，也称主线程。一个进程中有多个线程，多个线程**共享进程的堆和方法区资源**，但是每个线程有自己的**程序计数器**和**栈区域**。

- 程序计数器是一块内存区域，用来记录**线程**当前要执行的指令**地址**。
  - 线程是占用CPU执行的基本单位，而CPU一般是使用**时间片轮转**方式让线程轮询占用的，所以当前线程CPU时间片用完后，要让出CPU，等下次轮到自己的时候再执行。
  - 程序计数器就是为了**记录该线程让出CPU时的执行地址**的，待再次分配到时间片时线程就可以从自己私有的计数器指定地址继续执行。
- 每个线程都有自己的栈资源，用于**存储该线程的局部变量**，这些局部变量是该线程私有的。
- 堆是一个进程中最大的一块内存，**堆是被进程中的所有线程共享的**，是进程创建时分配的，堆里面主要存放使用new操作创建的对象实例。
- 方法区则用来存放JVM加载的类、常量及静态变量等信息，也是线程共享的。

Java程序运行时，最开始运行的只能是**主线程**。所以，必须在程序中**启动新线程**，这才能算是多线程程序。

`Thread.java`中`run`方法：
```java
@Override
public void run() {
    // 如果构造Thread时传递了Runnbale，则会执行runnable的run方法
    if (target != null) {
        target.run();
    }
    // 否则需要重写Thread类的run方法
}
```

从中可以知道：**创建线程只有一种方式那就是构造`Thread`类**，而**实现线程的执行单元**则有两种方式，第一种是重写`Thread`的`run`方法；第二种是实现`Runnable`接口的`run`方法，并且**将`Runnable`实例用作构造`Thread`的参数**。

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

其实调用`start`方法后线程并没有马上执行而是处于**就绪状态**，这个就绪状态是指该线程已经获取了**除CPU资源**外的其他资源，等待获取CPU资源后才会真正处于运行状态。一旦run方法执行完毕，该线程就处于**终止状态**。

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

使用继承方式的好处是，**在`run()`方法内获取当前线程直接使用`this`**就可以了，无须使用`Thread.currentThread()方法`；不好的地方是**Java不支持多继承**，如果继承了Thread类，那么就不能再继承其他类。另外**任务与代码没有分离**，当多个线程执行一样的任务时需要多份任务代码。

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

        System.out.println(Thread.currentThread().getName() + " 线程结束！");
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
main 线程结束！
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


### 使用Lambda创建线程

**原始方法**：

```java
public class LambdaThread implements Runnable{
    @Override
    public void run(){
        for (int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName() + " 在听歌。。。");
        }
    }

    public static void main(String[] args) {
        Runnable lambdaThread = new LambdaThread();
        // LambdaThread lambdaThread = new LambdaThread();
        Thread thread = new Thread(lambdaThread);
        thread.start();
        
        for (int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName() + " 在敲代码。。。");
        }
    }
}
```

**使用匿名内部类**：

```java
public class LambdaThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    System.out.println(Thread.currentThread().getName() + " 在听歌。。。");
                }
            }
        }).start();

        for (int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName() + " 在敲代码。。。");
        }
    }
}
```

**使用Lambda**：

我们可以对比一下Lambda表达式和传统的Java对同一个**接口的实现**：
<div align=center><img src=Lambda\Lambda7.jpg></div>

这两种写法本质上是等价的。但是显然，Java 8中的写法更加优雅简洁。

并且，由于Lambda可以**直接赋值给一个变量**，我们就可以**直接把Lambda作为参数传给函数**, 而传统的Java必须有明确的接口实现的定义，初始化才行：
<div align=center><img src=Lambda\Lambda8.jpg></div>

```java
public class LambdaThread {
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread().getName() + " 在听歌。。。");
            }
        }).start();

        for (int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName() + " 在敲代码。。。");
        }
    }
}
```


 ## Thread类与Runnable接口的比较

 继承`Thread`类：
 * 子类继承`Thread`类，具备多线程能力；
 * 启动线程：`子类对象.start()`。
 * 不建议使用：OOP单继承的局限性。

实现`Runnable`接口：
* 实现`Runnable`接口，具有多线程能力；
* 启动线程：`传入实现接口类的对象 + Thread对象.start()`。
* 推荐使用：避免了OOP单继承的局限性；**方便同一个对象被多个线程使用**。

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

`native`表明这个方法是个原生函数，即这个函数是用C/C++实现的，被编译成DLL，由Java调用。

而`Runnable`接口：

```java
package java.lang;

@FunctionalInterface
public interface Runnable 
{
    public abstract void run();
}
```

Thread类定义一个卖票的类并启动两个线程：
```java
public class TicketThread extends Thread {

    private int ticket = 5;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if (ticket > 0) {
                System.out.println("ticket=" + ticket-- + "," + Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        new TicketThread().start();
        new TicketThread().start();
    }
}
/*
ticket=5,Thread-0
ticket=4,Thread-0
ticket=3,Thread-0
ticket=2,Thread-0
ticket=1,Thread-0
ticket=5,Thread-1
ticket=4,Thread-1
ticket=3,Thread-1
ticket=2,Thread-1
ticket=1,Thread-1
 */
```

运行发现：**每个线程独立执行了卖票的任务**，每个线程中票数依次减1。

虽然**可以对`ticket`用`static`进行修饰，做到多线程下共享资源的唯一性**，但一方面**数字很大则会出现线程安全问题**，另一方面，如果共享资源很多，不可能都用`static`进行修饰，而且`static`修饰的变量**生命周期很长**。

```java{.line-numbers highlight=3}
public class TicketThread extends Thread {

    private static int ticket = 5;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if (ticket > 0) {
                System.out.println("ticket=" + ticket-- + "," + Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        new TicketThread().start();
        new TicketThread().start();
    }
}
/*
ticket=4,Thread-1
ticket=3,Thread-1
ticket=2,Thread-1
ticket=5,Thread-0
ticket=1,Thread-1
 */
```

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

```
ticket: 100000, Thread-0
ticket: 99998, Thread-0
...
ticket: 99993, Thread-0
ticket: 99992, Thread-1
...
ticket: 99987, Thread-1
ticket: 99999, Thread-2  // 第99999张票是由Thread-2卖出去的
...
ticket: 3, Thread-1
ticket: 2, Thread-1
ticket: 10, Thread-0
ticket: 1, Thread-1
ticket: 4, Thread-2
```

<font color=red>三个线程**共同完成了卖票任务**，然而三个线程**共同执行同一段代码**，会造成**线程不安全**，可以通过加锁解决</font>。

- 实现Runnable接口的同时，还可以继承其他类，避免Java的单继承性带来局限性。
- <font color=red>`Runnable`接口可以实现**资源共享**，`Thread`如果不使用`static`则无法完成资源共享</font>。


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

// 1.实现Callable接口
public class Task implements Callable<Integer>
{
    // 重写call方法
    @Override
    public Integer call() throws Exception  // 返回值类型与Callable<Integer>中一致
    {
        // 模拟计算需要1秒
        Thread.sleep(1000);
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        // 3.创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 4.创建任务
        Task task = new Task();
        // 5.提交任务
        Future<Integer> result = executor.submit(task);
        // 注意调用get方法会阻塞当前线程，直到得到结果。
        // 所以实际编码中建议使用可以设置超时时间的重载get方法。
        // 6.得到结果
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

<font color=red>在很多高并发的环境下，有可能`Callable`和`FutureTask`会创建多次。`FutureTask`能够在高并发环境下确保任务只执行一次</font>。

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


# 线程组(ThreadGroup)

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

# 线程状态与方法

## 线程生命周期的状态

**1.操作系统通用线程状态**：

<div align=center><img src=Thread\操作系统通用线程状态.png width=60%></div>

除去**生**【初始状态】**死**【终止状态】，其实只是三种状态的各种转换。

**初始状态**：线程已被创建，但是**还不被允许分配CPU执行**。注意，这个被创建其实是属于**编程语言层面**的(比如Java语言中的new Thread())，**实际在操作系统里，真正的线程还没被创建**。

**可运行状态**：线程可以分配CPU执行，这时，操作系统中线程已经被创建成功了。

**运行状态**：操作系统会为处在可运行状态的线程**分配CPU时间片**，处在可运行状态的线程就会变为运行状态。

**休眠状态**：如果处在运行状态的线程**调用某个阻塞的API**或**等待某个事件条件可用**，那么线程就会转换到休眠状态。注意：此时线程会释放CPU使用权，只有当**等待事件出现**后，线程会从休眠状态转换到可运行状态。

**终止状态**：线程执行完或者出现异常(被interrupt那种不算)就会进入终止状态，正式走到生命的尽头，没有起死回生的机会。


**2.Java语言线程状态**：

在Thread的源码中，定义了一个枚举类State，里面清晰明了的写了Java语言中线程的6种状态：
```
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

在给定的时间点上，线程只能处于一种状态。这些状态是虚拟机状态，不反映任何操作系统线程状态。

<div align=center><img src=Thread\Java语言中的线程状态.png></div>

Java语言中：
- 将通用线程状态的可运行状态和运行状态合并为Runnable；
- 将休眠状态细分为三种`BLOCKED/WAITING/TIMED_WAITING`
    反过来理解这句话，就是这三种状态在操作系统的眼中都是休眠状态，同样不会获得CPU使用权。

除去线程生死，我们只要玩转**`RUNNABLE`和`休眠状态`的转换**就可以了，编写并发程序也多数是这两种状态的转换。所以我们需要了解，有哪些时机，会触发这些状态转换。

<div align=center><img src=Thread\线程状态转换.png></div>

- **RUNNABLE与BLOCKED状态转换**：
    **当且仅有（just only）一种情况**会从RUNNABLE状态进入到BLOCKED状态，就是**线程在等待synchronized内置隐式锁**！
    如果等待的线程获取到了synchronized内置隐式锁，也就会从BLOCKED状态变为RUNNABLE状态了。

- **RUNNABLE与WAITING状态转换**：
    调用**不带时间参数的等待API**，就会从RUNNABLE状态进入到WAITING状态；当**被唤醒**就会从WAITING进入RUNNABLE状态

- **RUNNABLE与TIMED-WAITING状态转换**：
    调用**带时间参数的等待API**，自然就从RUNNABLE状态进入TIMED-WAITING状态；当**被唤醒或超时、时间到了**就会从TIMED_WAITING进入RUNNABLE状态。


**NEW**：
通过继承Thread或实现Runnable接口定义线程后，这时的状态都是NEW
```java
Thread thread = new Thread(() -> {});
System.out.println(thread.getState());
```

**RUNNABLE**：
**调用start()方法之后**，线程就处在RUNNABLE状态了
```java
Thread thread = new Thread(() -> {});
thread.start();
System.out.println(thread.getState());
```

**BLOCKED**：
等待synchronized内置锁，就会处在BLOCKED状态
```java
public class ThreadStateTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoThreadB());
        Thread t2 = new Thread(new DemoThreadB());

        t1.start();
        t2.start();

        Thread.sleep(1000);

        System.out.println((t2.getState()));
    }
}

class DemoThreadB implements Runnable {
    @Override
    public void run() {
        commonResource();
    }

    public static synchronized void commonResource() {
        while(true) {

        }
    }
}
```

**WAITING**：
调用线程的join()等方法，从RUNNABLE变为WAITING状态
```java
public static void main(String[] args) throws InterruptedException {
    Thread main = Thread.currentThread();

    Thread thread2 = new Thread(() -> {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println(main.getState());
    });

    thread2.start();
    thread2.join();
}
```

**TIMED-WAITING**：
调用了`sleep(long)`等方法，线程从RUNNABLE变为TIMED-WAITING状态
```java
public static void main(String[] args) throws InterruptedException {
    Thread thread3 = new Thread(() -> {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // 为什么要调用interrupt方法？
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    });
    
    thread3.start();

    Thread.sleep(1000);
    System.out.println(thread3.getState());
}
```

**TERMINATED**：
线程执行完自然就到了TERMINATED状态了
```java
Thread thread = new Thread(() -> {});
thread.start();
Thread.sleep(1000);
System.out.println(thread.getState());
```


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
Causes the currently executing thread to sleep.
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



# 线程的同步(重点)

&emsp;&emsp;<font color=red>多个线程操作同一个资源——并发</font>。处理多线程问题时，多个线程**访问**同一个对象，并且某些线程还想**修改**这个对象，这时需要线程同步。<font color=red>线程同步其实就是一个**等待机制**，多个需要同时访问此对象的线程进入这个对象的**等待池**形成队列(排队)，等待前面线程使用完毕，下一个线程再使用</font>。

&emsp;&emsp;**队列与锁**：排队去厕所，第一个人进入隔间锁上门独享资源。每个对象都有一把锁来保证线程安全。

&emsp;&emsp;由于**同一进程的多个线程共享同一块存储空间**，在带来方便的同时，也存在访问冲突问题。为了保证数据在方法中被访问时的正确性，在访问时加入<font color=red>锁机制(synchronized)</font>，**当一个线程获得对象的<font color=red>排它锁</font>，独占资源时，其他线程必须等待，使用后释放锁**。

性能与安全的平衡：
* 一个线程持有锁会导致其他所有需要此锁的线程**挂起**；
* 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题；
* 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致<font color=red>优先级倒置</font>，引起性能问题。


## 初识并发问题

**并发**是指同**一个时间段内**多个任务同时都在执行，并且都没有执行结束，而**并行**是说在**单位时间内**多个任务同时在执行。并发任务强调在一个时间段内同时执行，而**一个时间段由多个单位时间累积而成**，所以说**并发的多个任务在单位时间内不一定同时在执行**。

**在单CPU的时代多个任务都是并发执行的**，这是因为**单个CPU同时只能执行一个任务**。在单CPU时代多任务是共享一个CPU的，当一个任务占用CPU运行时，其他任务就会被挂起，当**占用CPU的任务时间片**用完后，会把CPU让给其他任务来使用，所以在单CPU时代多线程编程是没有太大意义的，并且线程间频繁的**上下文切换**还会带来额外开销。

在多线程编程实践中，线程的个数往往多于CPU的个数，所以一般都称多线程并发编程而不是多线程并行编程。

如果多个线程都只是读取共享资源，而不去修改，那么就不会存在线程安全问题；只有当至少一个线程**修改共享资源**时才会存在线程安全问题。

最典型的就是计数器类的实现，计数变量count本身是一个共享变量，多个线程可以对其进行递增操作，如果不使用同步措施，由于递增操作是`获取一计算一保存`三步操作， 因此可能导致计数不准确：

|   | t1 |  t2  |  t3  |  t4 |
|:-:|:-:|:-:|:-:|:-:|
| 线程A  | 从内存读取count=0  | 递增本线程count=1  | 写回主内存count=1  |   |
| 线程B |   | 从内存读取count=0  | 递增本线程count=1  | 写回主内存count=1  |

t2时刻线程B从内存中读取的count值为0，因为此时线程A尚未将改变后的count值写回主内存。故明明两次计数，最终的count值却仍为1。



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


### 线程不安全案例


每个线程都在自己的工作内存交互，内存控制不当会造成数据不一致。

#### 不安全地买票
`chen 买到了第-1票！`：当票还剩一张时，由于没有排队，每个人都买了，于是变成-1了。
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

#### 不安全地取钱
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

#### 线程不安全的集合
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

#### 线程不安全案例四
&emsp;&emsp;假设创建并启动100个线程，每个线程都往同一个账户中添一个便士。定义一个名为`Account`的类模拟账户，一个名为`AddAPennyTask`的类用来向账户里添加一便士，以及一个用于创建和启动线程的主类。
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

#### 英雄回血掉血

使用`匿名类`，继承Thread，重写run方法，直接在run方法中写业务代码。匿名类的一个好处是可以很方便的访问外部的局部变量。前提是外部的局部变量需要被声明为`final`。(JDK7以后就不需要了)

```java
package HowJThread;

public class Hero {
    public String name;
    public float hp;
    public int damage;

    // 回血
    public void recover(){
        hp += 1;
    }

    // 掉血
    public void hurt(){
        hp -=1;
    }

    public void attackHero(Hero hero){
        hero.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s 的血变成了 %.0f%n",
                name, hero.name, hero.name, hero.hp);
        if (hero.isDead())
            System.out.println(hero.name + " is dead.");
    }

    public boolean isDead(){
        return hp <= 0 ? true : false;
    }
}


package HowJThread;

public class TestThread {
    public static void main(String[] args) {
        final Hero gareen = new Hero();
        gareen.name = "盖伦";
        gareen.hp = 10000;
        System.out.printf("%s 的初始血量是 %.0f%n", gareen.name, gareen.hp);

        //多线程同步问题指的是多个线程同时修改一个数据的时候，导致的问题

        //假设盖伦有10000滴血，并且在基地里，同时又被对方多个英雄攻击

        //用JAVA代码来表示，就是有多个线程在减少盖伦的hp，同时又有多个线程在恢复盖伦的hp


        int n = 10000;
        Thread[] addThreads = new Thread[n];
        Thread[] reduceThreads = new Thread[n];

        //n个线程增加盖伦的hp
        for (int i = 0; i < n; i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    gareen.recover();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
            addThreads[i] = thread;
        }

        // 继承Thread，重写run方法，直接在run方法中写业务代码

        //n个线程减少盖伦的hp
        for (int i = 0; i < n; i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    gareen.hurt();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
            reduceThreads[i] = thread;
        }

        //等待所有增加线程结束
        for (Thread thread : addThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //等待所有减少线程结束
        for (Thread thread : reduceThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //代码执行到这里，所有增加和减少线程都结束了

        //增加和减少线程的数量是一样的，每次都增加，减少1.
        //那么所有线程都结束后，盖伦的hp应该还是初始值

        //但是事实上观察到的是：
        System.out.printf("%d 个回血线程和 %d 个掉血线程结束后\n" +
                "盖伦的血量变成%.0f\n", n, n, gareen.hp);
    }
}

/*
盖伦 的初始血量是 10000
10000 个回血线程和 10000 个掉血线程结束后
盖伦的血量变成10001
 */
```

解释：
1. 假设掉血线程先进入，得到的hp是10000
2. 进行掉血运算
3. 正在做掉血运算的时候，还没有来得及修改hp的值，回血线程来了
4. 回血线程得到的hp的值也是10000
5. 回血线程进行回血运算
6. 掉血线程运算结束，得到值9999，并把这个值赋予hp
7. 回血线程也运算结束，得到值10001，并把这个值赋予hp

hp，最后的值就是10001。

<div align=center><img src=Thread\英雄掉血回血不同步.png></div>


## ThreadLocal

多线程访问同**一个共享变量**时特别容易出现并发问题，特别是在多个线程需要对一个共享变量进行写入时。为了保证线程安全，一般使用者在访问共享变量时需要进行适当的同步。

同步的措施一般是**加锁**，这就需要使用者对锁有一定的了解，这显然加重了使用者的负担。那么有没有一种方式可以做到，**当创建一个变量后，每个线程对其进行访问的时候访问的是自己线程的变量**呢？

`ThreadLocal`是`JDK包`提供的，它提供了**线程本地变量**，也就是**如果你创建了一个`ThreadLocal`变量，那么访问这个变量的每个线程都会有这个变量的一个本地副本**。当多个线程操作这个变量时，实际操作的是自己本地内存里面的变量，从而避免了线程安全问题。**创建一个`ThreadLocal`变量后，每个线程都会复制一个变量到自己的本地内存**。

```java
public class ThreadLocalTest {
    // 创建ThreadLocal变量
    private static ThreadLocal<String> localVariable = new ThreadLocal<>();

    // print函数
    public static void print(String string) {
        // 打印当前线程本地内存中localVariable变量的值
        System.out.println(string + " : " + localVariable.get());

        // 清除当前线程本地内存中的localVariable变量
        // localVariable.remove();
    }

    public static void main(String[] args) {
        // 创建线程threadOne
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置threadOne中本地变量localVariable的值
                localVariable.set("threadOne local variable");
                print("threadOne");
                // 打印本地变量值
                System.out.println("threadOne 不使用 localVariable.remove() : " + localVariable.get());
            }
        });

        // 创建线程threadTwo
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置threadTwo中本地变量localVariable的值
                localVariable.set("threadTwo local variable");
                print("threadTwo");
                // 打印本地变量值
                System.out.println("threadTwo 不使用 localVariable.remove() : " + localVariable.get());
            }
        });

        threadOne.start();
        threadTwo.start();
    }

}
/*
threadOne : threadOne local variable
threadTwo : threadTwo local variable
threadOne 不使用 localVariable.remove() : threadOne local variable
threadTwo 不使用 localVariable.remove() : threadTwo local variable
*/
```

线程threadOne中的代码`localVariable.set("threadOne local variable");`通过`set方法`设置了`localVariable`的值，这其实**设置的是线程threadOne本地内存中的一个副本，这个副本线程threadTwo是访问不了的**。


### ThreadLocal实现原理

<div align=center><img src=Thread\threadLocal.png></div>

Thread类中有一个`threadLocals`(`ThreadLocal.ThreadLocalMap threadLocals = null;`)和一个`inheritableThreadLocals`(`ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;`)，它们都**是`ThreadLocalMap`类型的变量**，而`ThreadLocalMap`是一个定制化的`Hashmap`。

**在默认情况下，每个线程中的这两个变量都为null，只有当前线程第一次调用`ThreadLocal`的`set`或者`get`方法时才会创建它们**。

其实**每个线程的本地变量不是存放在`ThreadLocal`实例里面，而是存放在调用线程的`threadLocals`变量里面**。也就是说，**`ThreadLocal`类型的本地变量存放在具体的线程内存空间中**。`ThreadLocal`就是一个工具壳，它**通过set方法把value值放入调用线程的`threadLocals`里面并存放起来**，当调用线程调用它的get方法时，再从**当前线程的`threadLocals`变量**里面将其拿出来使用。如果调用线程一直不终止，那么这个本地变量会一直存放在调用线程的`threadLocals`变量里面，所以当不需要使用本地变量时可以通过调用`ThreadLocal`变量的remove方法，从**当前线程的`threadLocals`**里面删除该本地变量。

Thread里面的threadLocals为何被设计为map结构？

很明显是因为**每个线程可以关联多个ThreadLocal变量**。


### ThreadLocal方法码源分析

```java
public void set(T value) {
    // 获取当前线程
    Thread t = Thread.currentThread();

    // 将当前线程作为key，去查找对应的线程变量，找到则设置
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        // 如果是第一次调用，就创建当前线程对应的HashMap
        createMap(t, value);
}
```

```java
ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```
`getMap(t`的作用是**获取线程自己的变量`threadLocals`**。

```java
void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
```

```java
public T get() {
    // 获取当前线程
    Thread t = Thread.currentThread();

    // 获取当前线程的threadlocals变量
    ThreadLocalMap map = getMap(t);

    // 如果threadLocals不为null，则返回对应本地变量的值
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    // threadLocals为空则初始化当前线程的threadLocals成员变量
    return setInitialValue();
}
```

```java
private T setInitialValue() {
    // 初始化为null
    T value = initialValue();
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
    return value;
}

protected T initialValue() {
    return null;
}
```

```java
public void remove() {
    ThreadLocalMap m = getMap(Thread.currentThread());
    if (m != null)
        m.remove(this);
}
```

总结：

在每个线程内部都有一个名为`threadLocals`的成员变量，该变量的类型为`HashMap`，其中`key`为我们定义的`ThreadLocal变量`的`this引用`，`value`则为我们使用set方法设置的值。

每个线程的本地变量存放在线程自己的内存变量`threadLocals`中，如果当前线程一直不消亡，那么这些本地变量会一直存在，所以**可能会造成内存溢出**，因此使用完毕后要记得调用ThreadLocal 的remove方法删除对应线程的threadLocals中的本地变量。


### InheritableThreadLocal类

`ThreadLocal`不支持继承性：

```java
public class ThreadLocalTest1 {
    // 创建线程变量
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 设置main线程变量
        threadLocal.set("main线程");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 子线程输出线程变量的值
                System.out.println("thread线程：" + threadLocal.get());
            }
        });

        thread.start();

        // 主线程输出线程变量的值
        System.out.println("main线程：" + threadLocal.get());
    }
}
/*
main线程：main线程
thread线程：null
 */
```

同一个ThreadLocal变量在父线程中被设置值后，在子线程中是获取不到的。因为在子线程thread里面调用get方法时当前线程为thread线程，而这里调用set方法设置线程变量的是main线程，两者是不同的线程，自然子线程访问时返回null。

那么有没有办法让子线程能访问到父线程中的值？答案是有。

`InheritableThreadLocal`继承自`ThreadLocal`，其提供了一个特性，就是**让子线程可以访问在父线程中设置的本地变量**。

```java
public class InheritableThreadLocal<T> extends ThreadLocal<T> {
    /**
     * @param parentValue the parent thread's value
     * @return the child thread's initial value
     */
     // (1)
    protected T childValue(T parentValue) {
        return parentValue;
    }

    /**
     * Get the map associated with a ThreadLocal.
     *
     * @param t the current thread
     */
     // (2)
    ThreadLocalMap getMap(Thread t) {
       return t.inheritableThreadLocals;
    }

    /**
     * Create the map associated with a ThreadLocal.
     *
     * @param t the current thread
     * @param firstValue value for the initial entry of the table.
     */
     // (3)
    void createMap(Thread t, T firstValue) {
        t.inheritableThreadLocals = new ThreadLocalMap(this, firstValue);
    }
}
```

下面我们看一下重写的代码(1)何时执行，以及如何让子线程可以访问父线程的本地变量。这要从创建Thread的代码说起，打开Thread类的默认构造函数，代码如下：

```java
public Thread(Runnable target) {
    init(null, target, "Thread-" + nextThreadNum(), 0);
}
...

private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc) {
    // (4)获取当前线程
    Thread parent = currentThread();
    ...
    // (5)如果父线程的inheritableThreadLocals变量不为null
    if (parent.inheritableThreadLocals != null)
        // (6)设置子线程中的InheritableThreadLocals变量
        this.inheritableThreadLocals =
            ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
}
```

在创建线程时，在构造函数里面会调用`init`方法。代码(4)获取了当前线程（这里是指**main函数所在的线程**，也就是父线程），然后代码(5)判断main函数所在线程里面的`inheritableThreadLocals`属性是否为null。

```java
static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
    return new ThreadLocalMap(parentMap);
}
```

在`createInheritedMap`内部使用父线程的`inheritableThreadLocals`变量作为构造函数创建了一个新的`ThreadLocalMap`变量， 然后赋值给了子线程的`inheritableThreadLocals`变量。

**总结：**

`InheritableThreadLocal`类通过重写代码(2)和(3)让本地变量保存到了具体线程的`inheritableThreadLocals`变量里面，那么线程在通过`InheritableThreadLocal`类实例的set或者get方法设置变量时，就会创建当前线程的`inheritableThreadLocals`变量。当父线程创建子线程时，构造函数会把父线程中`inheritableThreadLocals`变量里面的本地变量复制一份保存到子线程的`inheritableThreadLocals`变量里面。

```java
public class ThreadLocalTest1 {
    // 创建线程变量
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        // 设置main线程变量
        threadLocal.set("main线程");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 子线程输出线程变量的值
                System.out.println("thread线程：" + threadLocal.get());
            }
        });

        thread.start();

        // 主线程输出线程变量的值
        System.out.println("main线程：" + threadLocal.get());
    }
}
/*
main线程：main线程
thread线程：main线程
 */
```



## synchronized

**原子性问题的源头就是线程切换**，但在多核CPU的大背景下，不允许线程切换是不可能的。

**互斥: 同一时刻只有一个线程执行！**

上面这句话的意思是: **对共享变量的修改是互斥的**，也就是说线程A修改共享变量时其他线程不能修改，这就不存在操作被打断的问题了！

为避免竞争状态，应该防止多个线程同时进入程序的某一特定部分，程序中的这部分称为临界区(`critical region`) 。可以使用关键字`synchronized`来同步方法，以便<font color=red>一次只有一个线程可以访问这个方法</font>。
`public synchronized void deposit(int amount)`

一个同步方法在执行之前需要**加锁**。锁是一种实现**资源排他使用**的机制。

<font color=red>对于`实例方法`，要给`调用该方法的对象加锁`。对于`静态方法`，要给这个`类`加锁</font>。

如果一个**线程**调用一个**对象**上的**同步实例方法（静态方法）**，首先给该**对象（类）加锁**，然后执行该方法，最后解锁。在解锁之前，另一个调用那个对象（类）中方法的线程将被阻塞，直到解锁。
<div align=center><img src=Thread/同步.png width=60%></div>

* `synchronized`方法控制`对象`的访问，每个对象对应一把锁。每个`synchronized`方法都必须获得调用该方法对象的锁才能执行，否则线程会阻塞。方法一旦执行，就独占该锁，直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行。
* 若将一个大的方法声明为`synchronized`将会影响效率。
例如，A代码：只读；B代码：修改，方法里需要修改的内容才需要锁，锁得太多，浪费资源。

**调用一个对象上的同步实例方法，需要给该对象加锁。而调用一个类上的同步静态方法，需要给该类加锁**。当执行方法中某一个代码块时，同步语句不仅可用于**对`this对象`加锁**，而且可用于**对任何对象加锁**。这个代码块称为`同步块(synchronized block)`。

* 同步块`sychronized(Obj){}`中，`Obj`被称为`同步监视器`，它可以是**任何对象**，但推荐使用**共享资源**作为同步监视器；同步方法中无需指定监视器，因为同步方法中监视器就是`this`，即该对象本身。或者是`class`(反射中讲解)。
* 同步监视器得执行过程：1.第一个线程访问，锁定同步监视器，执行其中代码；2.第二个线程访问，发现同步监视器被锁定，无法访问；3.第一个线程访问完毕，解锁同步监视器；4.第二个线程访问，发现同步监视器没有锁，然后锁定并访问。


### 临界区

#### synchronized的三种用法

```java
public class ThreeSync {
    private static final Object object = new Object();
    
    public synchronized void normalSyncMethod(){
        //临界区
    }
    
    public static synchronized void staticSyncMethod(){
        //临界区
    }
    
    public void syncBlockMethod(){
        synchronized (object){
            //临界区
        }
    }
}
```

三种`synchronized`锁的内容有一些差别:

- 对于**普通同步方法**，锁的是**当前实例对象**，通常指`this`；
- 对于**静态同步方法**，锁的是当前类的**Class对象**，如`ThreeSync.class`；
- 对于**同步方法块**，锁的是**synchronized括号内的对象**。


#### 临界区含义

把**需要互斥执行的代码**看成为临界区！

如何**用锁保护有效的临界区**是关键，无论是**隐式锁/内置锁--synchronized**还是**显示锁--Lock**的使用都是在找寻这种关系。

<div align=center><img src=Thread\简易锁模型.png></div>

线程进入临界区之前，尝试**加锁lock()**，加锁成功，则**进入临界区**(对共享变量进行修改)，持有锁的线程执行完临界区代码后，执行**unlock()释放锁**。

**两个问题：**
- 我们锁的是什么？
- 我们保护的又是什么？
  
<div align=center><img src=Thread\临界区.png></div>

资源R(共享变量)就是我们要保护的资源，所以我们就要创建资源R的锁LR来保护资源R。

**LR和R之间有明确的指向关系**！

我们编写程序时，往往脑子中的模型是对的，但是忽略了这个指向关系，导致自己的锁不能起到保护资源R的作用(用别人家的锁保护自己家的东西或用自己家的锁保护别人家的东西)，最终引发并发bug，所以在勾画草图时，要明确找到这个关系！

左图LR虚线指向了非共享变量。我们写程序的时候很容易这么做，不确定哪个是要保护的资源，直接大杂烩，用LR将要保护的资源R和没必要保护的非共享变量一起保护起来了，举两个例子来说你就明白这么做的坏处了：

- 编写串行程序时，是不建议try…catch整个方法的，这样如果出现问是很难定位的。道理一样，我们要用锁精确的锁住我们要保护的资源就够了，其他无意义的资源是不要锁的。
- 锁保护的东西越多，临界区就越大，一个线程从走入临界区到走出临界区的时间就越长，这就让其他线程等待的时间越久，这样并发的效率就有所下降。

```java
public class ValidLock {
    private static final Object object = new Object();
    private int count;
    
    public synchronized void badSync(){  // 差的使用方法
        //其他与共享变量count无关的业务逻辑
        count++;
    }
    
    public void goodSync(){  // 好的使用方法
        //其他与共享变量count无关的业务逻辑
        synchronized (object){
            count++;
        }
    }
}
```

在计数器程序例子中，我们会经常这么写:
```java
public class SafeCounter {
    private int count;
    
    public synchronized void counter(){
        count++;
    }
    
    public synchronized int getCount(){
        return count;
    }
}
```

下图就是上面程序的模型展示：

<div align=center><img src=Thread\一锁多个资源.png></div>

这里我们**锁的是this**，可以**保护this.count**。但有人认为getCount方法没必要加synchronized关键字，因为是读的操作，不会对共享变量做修改。**如果不加上synchronized关键字，就违背了happens-before规则中的监视器锁规则**：对一个锁的解锁happens-before于随后对这个锁的加锁！也就是说**对count的写很可能对count的读不可见**，也就导致脏读。


上面我们看到**一个this锁是可以保护多个资源**的，那用**多个不同的锁保护一个资源**可以吗？

```java
public class UnsafeCounter {
    private static int count;
    
    public synchronized void counter(){
        count++;
    }
    
    public static synchronized int calc(){
        return count++;
    }
}
```
**一个锁的是this，一个锁的是UnsafeCounter.class**，他们都想保护共享变量count。
<div align=center><img src=Thread\多锁一个资源.png></div>

两个临界区是用两个不同的锁来保护的，所以**临界区没有互斥关系，也就不能保护count**，所以这样加锁是无意义的。


**总结：**
- 解决原子性问题，就是要**互斥**，就是要保证中间状态对外不可见；
- **锁是解决原子性问题的关键**，明确知道我们锁的是什么，要保护的资源是什么，更重要的要知道你的锁能否保护这个受保护的资源(图中的箭头指向)；
- **有效的临界区是一个入口和一个出口**。多个临界区保护一个资源，也就是**一个资源有多个并行的入口和多个出口，这就没有起到互斥的保护作用**，临界区形同虚设；
- 锁自己家门能保护资源就没必要锁整个小区，如果锁了整个小区，这严重影响其他业主的活动(**锁粒度**的问题)。


#### 保护单个资源

要保护单个资源并对其进行修改其实很简单，只需按照下图分三步走：
<div align=center><img src=Thread\单个资源锁模型.png></div>

- 创建受保护资源R的锁
- 加锁进入临界区
- 解锁走出临界区
  
上图的关键是**R1的锁LR1保护R1**的指向关系是否正确！


#### 保护多个没有关系的资源

如果多个资源没有关系，那就是保护一个资源模型的复制：
<div align=center><img src=Thread\多个无关联资源锁模型.png></div>

比如现实中银行**取款**和**修改密码**操作：
银行取款操作对应的资源是**余额**，修改密码操作对应的资源是**密码**，余额和密码两个资源完全没有关系，所以各自用自家的锁保护自家的资源就好了。

#### 保护多个有关系的资源

拿经典的银行转账案例来说明，**账户A给账户B转账**，账户A余额减少100元，账户B余额增加100 元。这个操作要是原子性的，那么资源「A 余额」和资源「B 余额」就这样“有了关系”，先来看程序:

```java{.line-numbers highlight=7}
class Account {
    private int balance;
    // 转账
    synchronized void transfer(Account target, int amt){
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    } 
}
```
用synchronized直接保护transfer方法，然后操作资源「A 余额」和资源「B 余额」就可以了...

⚠️: 真的是这样吗？

上面程序的结构模型为：

<div align=center><img src=Thread\转账模型.png></div>

我们通常容易忽略**锁和资源的指向关系**，我们想当然的用锁this来保护target资源了，也就没有起到保护作用！

假设A，B，C账户初始余额都是200元，A向B转账100，B向C转账100。我们期盼最终的结果是：
账户A余额：100元；
账户B余额：200元；
账户C余额：300元。

假设线程1「A 向 B 转账」与线程2「B 向 C 转账」两个操作同时执行，根据JMM模型可知，线程1和线程2读取线程B当前的余额都是200元。

- 线程1执行transfer方法锁定的是**A的实例(A.this)**，并**没有锁定B的实例**
- 线程2执行transfer方法锁定的是**B的实例(B.this)**，并**没有锁定C的实例**

所以**线程1和线程2可以同时进入transfer临界区**，上面你认为对的模型其实就会变成这个样子：
<div align=center><img src=Thread\银行转账.png></div>

根据**监视器锁规则**(对一个锁的解锁happens-before于随后对这个锁的加锁)和**传递性规则**，**资源`B.alance`存在于两个“临界区”中**，所以这个“临界区”对`B.balance`来说形同虚设，也就不满足监视器锁规则，进而导致传递性规则也不生效，说白了，**前序线程的更改结果对后一个线程不可见**。


这样最终导致:

- 账户B的余额可能是100：线程1写`B.balance(balance = 300)` **先于**线程2写`B.balance(balance = 100)`，也就是说线程1的结果会被线程2覆盖，导致最终账户B的余额为100。

- 账户B的余额可能是300：与上述情况相反，线程1写`B.balance(balance = 300)`**后于**线程2写`B.balance(balance = 100)`，也就是说线程2的结果被线程1覆盖，导致最终账户B的余额为300。


**正确方法：**

```java{.line-numbers highlight=5}
class Account {
    private int balance;
    // 转账
    void transfer(Account target, int amt){
        synchronized(Account.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    } 
}
```

我们**将this锁变为Account.class锁**，`Account.class`是虚拟机加载Account类时创建的，肯定是**唯一的**，**所有Account对象都共享`Account.class`**，也就是说，**`Account.class`锁能保护所有Account对象**。

<div align=center><img src=Thread\银行转账1.png></div>

如果**多个资源有关联**，为了让锁起到保护作用，我们需要**将锁的粒度变大**，比如将this锁变成了Account.class锁。

转账业务非常常见，并发量非常大，如果我们将锁的粒度都提升到Account.class这个级别(分久必合)，假设每次转账业务都很耗时，那么显然这个**锁的性能是比较低**的，所以还需继续优化这个模型，选择合适的锁粒度，同时能保护多个有关联的资源。


### synchronized同步对象概念

```
Object someObject = new Object();
synchronized (someObject){
  //此处的代码只有占有了someObject后才可以执行
}
```

`synchronized`表示当前线程**独占**对象`someObject`。当前线程独占了对象`someObject`，如果有其他线程试图占有对象`someObject`，就会等待，直到当前线程释放对`someObject`的占用。

`someObject`又叫**同步对象**，**所有的对象，都可以作为同步对象**。为了达到同步的效果，必须**使用同一个同步对象**。

释放同步对象的方式：`synchronized`块自然结束，或者有异常抛出。

```java {.line-numbers highlight=21}
package HowJThread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadSyn {
    public static String now(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void main(String[] args){
        final Object someObject = new Object();

        Thread thread1 = new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println(now() + " thread1 is running...");
                    System.out.println(now() + " " + this.getName() + " 试图占有对象：someObject！(thread1)");

                    synchronized(someObject) {
                        System.out.println(now() + " " + this.getName() + " 占有对象：someObject！(thread1)");
                        Thread.sleep(5000);
                        System.out.println(now() + " " + this.getName() + " 释放对象：someObject！(thread1)");
                    }

                    System.out.println(now() + " thread1 is end.");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread1.setName("thread1");
        thread1.start();

        Thread thread2 = new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println(now() + " thread2 is running...");
                    System.out.println(now() + " " + this.getName() + " 试图占有对象：someObject！(thread2)");

                    synchronized(someObject) {
                        System.out.println(now() + " " + this.getName() + " 占有对象：someObject！(thread2)");
                        Thread.sleep(5000);
                        System.out.println(now() + " " + this.getName() + " 释放对象：someObject！(thread2)");
                    }

                    System.out.println(now() + " thread2 is end.");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread2.setName("thread2");
        thread2.start();
    }
}
/*
18:10:57 thread2 is running...
18:10:57 thread1 is running...
18:10:57 thread2 试图占有对象：someObject！(thread2)
18:10:57 thread1 试图占有对象：someObject！(thread1)
18:10:57 thread2 占有对象：someObject！(thread2)  // 资源被thread2抢到
18:11:02 thread2 释放对象：someObject！(thread2)
18:11:02 thread2 is end.
18:11:02 thread1 占有对象：someObject！(thread1)
18:11:07 thread1 释放对象：someObject！(thread1)
18:11:07 thread1 is end.
 */
```

对`英雄回血掉血`示例进行修改，变成线程安全的：

```java
final Object someObject = new Object();
final Hero gareen = new Hero();
...
public void run(){
    //任何线程要修改hp的值，必须先占用someObject
    synchronized (someObject) {
        gareen.recover();
        }
...
}
...
public void run(){
    //任何线程要修改hp的值，必须先占用someObject
    synchronized (someObject) {
        gareen.hurt();
    }
...
}
```

**既然任意对象都可以用来作为同步对象，而所有的线程访问的都是同一个`hero`对象，索性就使用`gareen`来作为同步对象**。

进一步的，对于`Hero`的`hurt`方法，加上：`synchronized (this) {}`，表示**当前对象为同步对象**，即也是`gareen`为同步对象。

**注意第15行；66行；86行！**

```java {.line-numbers}
package HowJThread;

public class Hero {
    public String name;
    public float hp;
    public int damage;

    // 回血
    public void recover(){
        hp += 1;
    }

    // 掉血
    public void hurt(){
        //使用this作为同步对象
        synchronized (this){
            hp -=1;
        }
    }

    public void attackHero(Hero hero){
        hero.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s 的血变成了 %.0f%n",
                name, hero.name, hero.name, hero.hp);
        if (hero.isDead())
            System.out.println(hero.name + " is dead.");
    }

    public boolean isDead(){
        return hp <= 0 ? true : false;
    }
}


package HowJThread;

public class TestThread {
    public static void main(String[] args) {
        //final Object someObject = new Object();
        final Hero gareen = new Hero();
        gareen.name = "盖伦";
        gareen.hp = 1000;
        System.out.printf("%s 的初始血量是 %.0f%n", gareen.name, gareen.hp);

        //多线程同步问题指的是多个线程同时修改一个数据的时候，导致的问题

        //假设盖伦有10000滴血，并且在基地里，同时又被对方多个英雄攻击

        //用JAVA代码来表示，就是有多个线程在减少盖伦的hp，同时又有多个线程在恢复盖伦的hp


        int n = 1000;
        Thread[] addThreads = new Thread[n];
        Thread[] reduceThreads = new Thread[n];

        //n个线程增加盖伦的hp
        for (int i = 0; i < n; i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    // gareen.recover();
                    /*synchronized (someObject) {
                        gareen.recover();
                    }*/
                    //使用gareen作为synchronized
                    synchronized(gareen){
                        gareen.recover();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
            addThreads[i] = thread;
        }

        //n个线程减少盖伦的hp
        for (int i = 0; i < n; i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    //使用gareen作为synchronized
                    //在方法hurt中有synchronized(this)
                    gareen.hurt();
                    /*synchronized (someObject) {
                        gareen.hurt();
                    }*/

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
            reduceThreads[i] = thread;
        }

        //等待所有增加线程结束
        for (Thread thread : addThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //等待所有减少线程结束
        for (Thread thread : reduceThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //代码执行到这里，所有增加和减少线程都结束了

        //增加和减少线程的数量是一样的，每次都增加，减少1.
        //那么所有线程都结束后，盖伦的hp应该还是初始值

        //但是事实上观察到的是：
        System.out.printf("%d 个回血线程和 %d 个掉血线程结束后\n" +
                "盖伦的血量变成%.0f\n", n, n, gareen.hp);
    }
}
```

#### 在方法前，加上修饰符synchronized

在`recover`前，直接加上`synchronized`，其所对应的同步对象就是`this`，和`hurt`方法达到的效果是一样。

外部线程访问`gareen`的方法，就不需要额外使用`synchronized`了。

```java
// Hero.java
// 回血
//直接在方法前加上修饰符synchronized
//其所对应的同步对象，就是this
//和hurt方法达到的效果一样
public synchronized void recover(){
    hp += 1;
}

// 掉血
public void hurt(){
    //使用this作为同步对象
    synchronized (this){
        hp -=1;
    }
}

// TestThread.java
public void run(){
    gareen.recover();
}

public void run(){
    gareen.hurt();
}
```

#### 对`unSafeBuyTickets.java`进行修改，变成线程安全的
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

### 线程安全的类

如果一个类，其方法都是有`synchronized`修饰的，那么该类就叫做线程安全的类。

同一时间，只有一个线程能够进入 这种类的一个实例的去修改数据，进而保证了这个实例中的数据的安全(不会同时被多线程修改而变成脏数据)。

比如`StringBuffer`和`StringBuilder`的区别：`StringBuffer`的方法都是有`synchronized`修饰的，`StringBuffer`就叫做线程安全的类；而`StringBuilder`就不是线程安全的类。

#### HashMap和Hashtable的区别

`HashMap`和`Hashtable`都实现了`Map`接口，都是键值对保存数据的方式：
区别1：
- `HashMap`可以存放`null`
- `Hashtable`不能存放`null`
  
区别2：
- `HashMap`不是线程安全的类
- `Hashtable`是线程安全的类

#### StringBuffer和StringBuilder的区别

`StringBuffer`是线程安全的；`StringBuilder`是非线程安全的。

所以当进行大量字符串拼接操作的时候，如果是**单线程**就用`StringBuilder`会更快些（不需要同步，省略了时间）；如果是多线程，就需要用`StringBuffer`保证数据的安全性。

#### ArrayList和Vector的区别

```java
// ArrayList类的声明：
 
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
 
// Vector类的声明：

public class Vector<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

一模一样的。他们的区别在于，`Vector`是线程安全的类，而`ArrayList`是非线程安全的。

#### 把非线程安全的集合转换为线程安全

`ArrayList`是非线程安全的，换句话说，多个线程可以同时进入一个`ArrayList`对象的`add`方法。

借助`Collections.synchronizedList`，可以把`ArrayList`转换为线程安全的`List`。

与此类似的，还有`HashSet, LinkedList, HashMap`等等非线程安全的类，都通过**工具类**`Collections`转换为线程安全的。

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
public class TestThread {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = Collections.synchronizedList(list1);
    }       
}
```

### 关于synchronized的8个问题

如何判断锁的是谁！永远的知道什么锁，锁到底锁的是谁！

一个同步方法在执行之前需要加锁。锁是一种实现资源排他使用的机制。对于**实例方法**，要给**调用该方法的对象加锁**。对于**静态方法**，要给这个**类**加锁。

**synchronized锁的对象是方法的调用者（一个对象）**：**先调用先执行**！不看延时情况！
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
            TimeUnit.SECONDS.sleep(6);
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
            TimeUnit.SECONDS.sleep(4);  // sendSms延迟4s
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
/*
// TimeUnit.SECONDS.sleep(2);
// 两个方法不加synchronized；TimeUnit.SECONDS.sleep(4);  // sendSms延迟4s
// 2秒等待：由于两个线程中间的TimeUnit.SECONDS.sleep(2);小于两个方法间4秒的延时
打电话
发短信

// 加synchronized，永远发短信在前：phone.sendSms();先调用

// 两个线程中间TimeUnit.SECONDS.sleep(2);
// // 两个方法加synchronized；TimeUnit.SECONDS.sleep(4);  // sendSms延迟4s
// 4秒等待：由于两个方法间的TimeUnit.SECONDS.sleep(4);
发短信
打电话

// 如果两个线程中间TimeUnit.SECONDS.sleep(6);
// 4秒等待：由于两个方法间的TimeUnit.SECONDS.sleep(4);
发短信
// 2秒等待（6-4）
打电话
 */
```


```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 3、增加了一个普通方法后！先执行”发短信：还是“Hello”？
 * 看线程间和方法间的相对延时大小
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
            TimeUnit.SECONDS.sleep(2);
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
            TimeUnit.SECONDS.sleep(4);
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

/*
// 等2秒：由于两线程之间 TimeUnit.SECONDS.sleep(2);
hello
// 等2秒：由于两方法之间 TimeUnit.SECONDS.sleep(4);
发短信
 */
```

两个对象，两个调用者，两把锁：

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 4、两个对象，两个同步方法， “发短信”还是“打电话”？
 * 看线程间和方法间的相对延时大小
 */
public class Test3
{
    public static void main(String[] args)
    {
        // 两个对象，两个调用者，两把锁！所以按时间调用！
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

**锁的是Class**：**先调用先执行**！不看延时情况！

```java
package Lock;

import java.util.concurrent.TimeUnit;

/**
 * 5、增加两个静态的同步方法，只有一个对象，先打印“发短信”还是“打电话”？
 * 输出：永远是发短信在前
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
 * 输出：永远发短信在前
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
 * 看线程间和方法间的相对延时大小
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
            TimeUnit.SECONDS.sleep(2);
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
/*
// 等2秒：线程间延时2秒
打电话
// 等2秒；方法间延时4秒，线程间延时2秒
发短信
*/
```

```java
import java.util.concurrent.TimeUnit;

/**
 * 8、1个静态的同步方法，1个普通的同步方法，两个对象，先打印“发短信”还是“打电话”？
 * 输出：看线程间和方法间的相对延时大小
 */

public class Test7
{
    public static void main(String[] args)
    {
        Phone7 phone1 = new Phone7();
        Phone7 phone2 = new Phone7();

        //锁的存在
        new Thread(()->{ phone1.sendSms(); },"A").start();

        // 捕获
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new Thread(()->{ phone2.call(); },"B").start();
    }
}

// Phone7唯一的一个 Class 对象
class Phone7
{
    // 静态的同步方法 锁的是 Class 类模板
    public static synchronized void sendSms()
    {
        try
        {
            TimeUnit.SECONDS.sleep(1);
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


## 死锁

&emsp;&emsp;**多个线程各自占用一些共享资源，并且互相等待其他线程占有的资源才能运行，而导致两个或多个线程都在等对方释放资源，都停止执行的情形**。某一个同步块同时拥有“两个以上对象的锁”时，就可能会发生“死锁”的问题。

**多个线程互相抱着对方需要的资源**，然后形成僵持。

### 产生死锁的四个必要条件

* **互斥**条件：指线程对己经获取到的资源进行**排它性使用**，即该资源同时只由一个线程占用。如果此时还有其他线程请求获取该资源，则请求者只能等待，直至占有资源的线程释放该资源；
* **请求并持有**条件：一个线程己经持有了至少一个资源，但又提出了新的资源请求，而新资源己被其他线程占有，所以当前线程会被阻塞，但阻塞的同时并不释放自己己经获取的资源；
* **不可剥夺条件**：指线程获取到的资源在自己使用完之前不能被其他线程抢占，只有在自己使用完毕后才由自己释放该资源；
* **环路等待**条件：若干线程之间形成一种**头尾相接的循环等待资源关系**。

### 死锁示例

#### 示例一

```java
public class DeadLock {
    // 创建资源
    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public static void main(String[] args) {
        // 创建线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + " gets resourceA.");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " is waiting for resourceB...");

                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread().getName() + " gets resourceB.");
                    }
                }
            }
        });

        // 创建线程B
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + " gets resourceB.");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " is waiting for resourceA...");

                    synchronized (resourceA) {
                        System.out.println(Thread.currentThread().getName() + " gets resourceA.");
                    }
                }
            }
        });

        // 启动线程
        threadA.start();
        threadB.start();
    }
}
/*
Thread-0 gets resourceA.
Thread-1 gets resourceB.
Thread-1 is waiting for resourceA...
Thread-0 is waiting for resourceB...
 */
```

线程调度器先调度了线程A，也就是把CPU资源分配给了线程A，**线程A使用`synchronized( resourceA)`方法获取到了resourceA的`监视器锁`**，然后调用`sleep函数`休眠1s，**休眠ls是为了保证线程A在获取resourceB对应的锁前，让线程B抢占到CPU，获取到资源resourceB上的锁**。线程A调用`sleep`方法后线程B会执行`synchronized(resource B)`方法，这代表线程B获取到了resourceB对象的监视器锁资源，然后调用sleep函数休眠1s。到了这里线程A获取到了resourceA资源，线程B获取到了resourceB资源。

线程A休眠结束后会企图获取resourceB资源，而resourceB资源被线程B所持有，所以线程A会被阻塞而等待。而同时线程B休眠结束后会企图获取resourceA资源，而resourceA资源己经被线程A持有，所以线程A和线程B就陷入了相互等待的状态，也就产生了死锁。


#### 示例二

柜员1正在办理给铁蛋儿转账的业务，但只拿到了你的账本；柜员2正在办理铁蛋儿给你转账的业务，但只拿到了铁蛋儿的账本。此时双方出现了尴尬状态，两位柜员都在等待对方归还账本为当前客户办理转账业务。

<div align=center><img src=Thread\银行转帐死锁.png></div>

程序代码描述一下上面这个模型：

```java
class Account {
    private int balance;

    // 转账
    void transfer(Account target, int amt){
        // 锁定转出账户
        synchronized(this) { 
             
            // 锁定转入账户
            synchronized(target) {           
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    } 
}
```
synchronized内置锁非常执着，它会告诉你「死等」，最终出现死锁。

### 避免线程死锁

要想避免死锁，只需要破坏掉至少一个构造死锁的必要条件即可。互斥条件是并发编程的根基，这个条件没办法改变。根据操作系统的知识，目前只有**请求并持有**和**环路等待条件**是可以被破坏的。

造成死锁的原因其实和**申请资源的顺序**有很大关系，**使用资源申请的有序性原则**就可以避免死锁。

让在线程B中获取资源的顺序和在线程A 中获取资源的顺序保持一致：
```java
// 创建线程B
Thread threadB = new Thread(new Runnable() {
    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " gets resourceA.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " is waiting for resourceB...");

            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " gets resourceB.");
            }
        }
    }
});
```

线程A和线程B同时执行到了`synchronized(resourceA)`，**只有一个线程可以获取到resourceA上的监视器锁**，假如线程A获取到了，那么线程B就会被阻塞而不会再去获取资源A，线程A获取到resourceA的监视器锁后会去申请resourceB的监视器锁资源，这时候线程A是可以获取到的，线程A获取到resourceB资源并使用后会放弃对资源resourceB的持有，然后再释放对resourceA的持有，**释放resourceA后线程B才会被从阻塞状态变为激活状态**。

所以**获取资源的有序性**破坏了资源的请求并持有条件和环路等待条件，因此避免了死锁。


#### 破坏请求和保持条件

每个柜员都可以取放账本，很容易出现互相等待的情况。要想破坏请求和保持条件，就要**一次性拿到所有资源**。

任何软件工程遇到的问题都可以通过**增加一个中间层**来解决。

我们不允许柜员都可以取放账本，账本要由单独的**账本管理员**来管理：
<div align=center><img src=Thread\银行转帐账本管理员.png></div>

也就是说账本管理员拿取账本是临界区，如果只拿到其中之一的账本，那么不会给柜员，而是等待柜员下一次询问是否两个账本都在：

```java
public class Account {
    //单例的账本管理员
    private AccountBookManager accountBookManager;
    
    private int balance;

    public void transfer(Account target, int amt){
        // 一次性申请转出账户和转入账户，直到成功
        while(accountBookManager.getAllRequiredAccountBook(this, target));

        try{
            // 锁定转出账户
            synchronized(this){
                // 锁定转入账户
                synchronized(target){
                    if (this.balance > amt){
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            accountBookManager.releaseObtainedAccountBook(this, target);
        }

    }
}

public class AccountBookManager {

    List<Object> accounts = new ArrayList<>(2);

    synchronized boolean getAllRequiredAccountBook(Object from, Object to){
        if(accounts.contains(from) || accounts.contains(to)){
            return false;
        } else{
            accounts.add(from);
            accounts.add(to);
            return true;
        }
    }
    // 归还资源
    synchronized void releaseObtainedAccountBook(Object from, Object to){
        accounts.remove(from);
        accounts.remove(to);
    }
}
```

#### 破坏不可剥夺条件

为了解决内置锁的执着，Java显示锁支持通知(notify/notifyall)和等待(wait)，也就是说该功能可以实现喊一嗓子*老铁，铁蛋儿的账本先给我用一下，用完还给你*的功能。

#### 破坏环路等待条件

只需要**将资源序号大小排序获取**就会解决这个问题，将环路拆除：
<div align=center><img src=Thread\银行转帐排序.png></div>

```java
class Account {
    private int id;
    private int balance;
    // 转账
    void transfer(Account target, int amt){
        Account smaller = this
        Account larger = target;
        // 排序
        if (this.id > target.id) {
            smaller = target;
            larger = this;
        }
        // 锁定序号小的账户
        synchronized(smaller){
            // 锁定序号大的账户
            synchronized(larger){
                if (this.balance > amt){
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
```

**当smaller被占用时，其他线程就会被阻塞**，也就不会存在死锁了。



## 中断

在学习/编写并发程序时，总会听到/看到如下词汇：

- 线程被中断或抛出InterruptedException
- 设置了中断标识
- 清空了中断标识
- 判断线程是否被中断

在Java`Thread类`里又提供了三个方法来处理并发中断问题：

- interrupt()
- interrupted()
- isInterrupted()

### 什么是中断机制

在多线程编程中，中断是一种**协同**机制

就像你妈妈叫你吃饭，你收到了中断游戏通知，但**是否马上放下手中的游戏去吃饭看你心情**。在程序中怎样演绎这个心情就看具体的业务逻辑了。

在多线程的场景中，有的线程可能迷失在怪圈无法自拔（自旋浪费资源），这时就可以用**其他线程在恰当的时机给它个中断通知**，被“中断”的线程可以选择在恰当的时机选择跳出怪圈，最大化的利用资源。


### 中断三个方法

Java的每个线程对象里都有一个boolean类型的标识，代表是否有中断请求，可你寻遍Thread类你也不会找到这个标识，因为这是通过底层native方法实现的。

**interrupt()**：

**`interrupt()`方法是唯一一个可以将上面提到中断标志设置为true的方法**
- 这是一个Thread类public的对象方法
- 任何线程对象都可以调用该方法
- 可以一个线程interrupt其他线程，也可以interrupt自己。中断标识的设置是通过native方法`interrupt0`完成的。

```java
public void interrupt() {
    if (this != Thread.currentThread())
        checkAccess();

    synchronized (blockerLock) {
        Interruptible b = blocker;
        if (b != null) {
            interrupt0(); // Just to set the interrupt flag
            b.interrupt(this);
            return;
        }
    }
    interrupt0();
}

private native void interrupt0();
```

在Java中，线程被中断的反应是不一样的，脾气不好的直接就抛出了`InterruptedException()`：

```java
* <p> If this thread is blocked in an invocation of the {@link
* Object#wait() wait()}, {@link Object#wait(long) wait(long)}, or {@link
* Object#wait(long, int) wait(long, int)} methods of the {@link Object}
* class, or of the {@link #join()}, {@link #join(long)}, {@link
* #join(long, int)}, {@link #sleep(long)}, or {@link #sleep(long, int)},
* methods of this class, then its interrupt status will be cleared and it
* will receive an {@link InterruptedException}.
```

该方法注释上写的很清楚，当线程被阻塞在：`wait(), join(), sleep()`这些方法时，如果被中断，就会抛出`InterruptedException`受检异常（也就是必须要求我们catch进行处理的）

这些可能阻塞的方法如果声明有`throws InterruptedException`，也就暗示我们它们是可中断的。

**调用interrput()方法后，中断标识就被设置为true了**，那我们怎么利用这个中断标识，来判断某个线程中断标识到底什么状态呢？


**isInterrupted()**：

```java
public boolean isInterrupted() {
    return isInterrupted(false);
}

private native boolean isInterrupted(boolean ClearInterrupted);
```

这个方法名起的非常好，因为比较符合boolean类型字段的get方法规范。该方法就是返回中断标识的结果：
- true：线程被中断，
- false：线程**没被中断**或**被清空了中断标识**

拿到这个标识后，线程就可以判断这个标识来执行后续的逻辑了。


**interrupted()**：

```java
public static boolean interrupted() {
    return currentThread().isInterrupted(true);
}

public boolean isInterrupted() {
    return isInterrupted(false);
}

private native boolean isInterrupted(boolean ClearInterrupted);
```

按照常规翻译，过去时时态，这就是“被打断了/被打断的”，其实和上面的`isInterrupted()`方法差不多，两个方法都是调用private的isInterrupted()方法， 唯一差别就是**会清空中断标识**（这是从方法名中怎么也看不出来的）。

调用该方法，会返回当前中断标识，同时会清空中断标识。

中断标识被清空，如果该方法被连续调用两次，第二次调用将返回false；除非当前线程在第一次和第二次调用该方法之间被再次interrupt。

```java
Thread.currentThread().isInterrupted(); // true
Thread.interrupted() // true，返回true后清空了中断标识将其置为 false
Thread.currentThread().isInterrupted(); // false
Thread.interrupted() // false
```

当你**可能要被大量中断并且想确保只处理一次中断时**，就可以使用这个方法了！


### 中断使用场景与注意事项

通常，中断的使用场景有以下几个

- 点击某个桌面应用中的关闭按钮时（比如你关闭IDEA，不保存数据直接中断好吗？）；
- 某个操作超过了一定的执行时间限制需要中止时；
- 多个线程做相同的事情，只要一个线程成功其它线程都可以取消时；
- 一组线程中的一个或多个出现错误导致整组都无法继续时；

因为中断是一种协同机制，提供了更优雅中断方式，也提供了更多的灵活性，所以当遇到如上场景等，我们就可以考虑使用中断机制了。

使用中断机制无非就是注意上面说的两项内容：

- 中断标识
- InterruptedException

可将其总结为两个通用原则：

**原则-1**：

如果遇到的是可中断的阻塞方法，并抛出InterruptedException，可以继续向方法调用栈的上层抛出该异常；如果检测到中断，则可清除中断状态并抛出InterruptedException，使当前方法也成为一个可中断的方法。

**原则-2**：

若有时候不太方便在方法上抛出InterruptedException，比如要实现的某个接口中的方法签名上没有`throws InterruptedException`，这时就可以**捕获**可中断方法的InterruptedException并通过`Thread.currentThread.interrupt()`来重新设置中断状态。

```java
// 希望当前线程被中断之后，退出while

Thread th = Thread.currentThread();
while(true) {
    if(th.isInterrupted()) {
    break;
    }
    
    // 省略业务代码
    
    try {
        Thread.sleep(100);
    }catch (InterruptedException e){
        e.printStackTrace();
    }
}
```

```java
/**
* @throws InterruptedException
*   if any thread has interrupted the current thread. The
*   <i>interrupted status</i> of the current thread is
*   cleared when this exception is thrown.
*/
public static native void sleep(long millis) throws InterruptedException;
```

sleep方法抛出InterruptedException后，中断标识也被清空置为false，我们在catch没有通过调用`th.interrupt()`方法再次将中断标识置为true，这就导致无限循环了。

这两个原则很好理解。总的来说，我们应该留意InterruptedException，当我们捕获到该异常时，绝不可以默默的吞掉它，什么也不做，因为这会导致上层调用栈什么信息也获取不到。其实在编写程序时，捕获的任何受检异常我们都不应该吞掉。


### JDK使用中断机制的地方

ThreadPoolExecutor中的shutdownNow方法会遍历线程池中的工作线程并调用线程的interrupt方法来中断线程；

FutureTask中的cancel方法，如果传入的参数为true，它将会在正在运行异步任务的线程上调用interrupt方法，如果正在执行的异步任务中的代码没有对中断做出响应，那么cancel方法中的参数将不会起到什么效果。






## Lock锁

&emsp;&emsp;Java可以**显式地加锁**，这给协调线程带来了更多的控制功能。一个锁是一个`Lock`接口的实例，它定义了加锁和释放锁的方法。

* 从`JDK5.0`开始，`Java`提供了更强大的线程同步机制——通过显示定义同步锁对象来实现同步。同步锁使用`Lock`对象充当。
* `java.util.concurrent.locks.Lock`**接口**是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独占访问，**每次只能有一个线程对`Lock`对象加锁**，线程开始访问共享资源之前，应先获得`Lock`对象。
* `ReentrantLock`类（可重入锁）实现了`Lock`(`public class ReentrantLock implements Lock, java.io.Serializable`)，它拥有与`synchronized`相同的并发性和内存语义，**在实现线程安全的控制中，比较常用的是`ReentrantLock`，可以显示加锁、释放锁**。
    `ReentrantLock`是`Lock`的一个具体实现，用于创建相互排斥的锁。可以创建具有特定的公平策略的锁。<font color=red>公平策略值为真，则确保等待时间最长的线程首先获得锁。取值为假的公平策略将锁给任意一个在等待的线程</font>。被多个线程访问的使用公正锁的程序，其整体性能可能比那些使用默认设置的程序差，但是在获取锁且避免资源缺乏时可以有更小的时间变化。

<div align=center><img src=Thread/ReentrantLock.png width=80%></div>

```java
package java.util.concurrent.locks;
import java.util.concurrent.TimeUnit;


public interface Lock {
    void lock();

    void lockInterruptibly() throws InterruptedException;

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

    Condition newCondition();
}
```

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

### Java SDK为什么要设计Lock

四个可以发生死锁的情形，其中【不可剥夺条件】是指：线程已经获得资源，在未使用完之前，不能被剥夺，只能在使用完时自己释放。

要想破坏这个条件，就需要具有申请不到进一步资源就释放已有资源的能力。很显然，这个能力是synchronized不具备的，使用synchronized，如果线程申请不到资源就会进入阻塞状态，我们做什么也改变不了它的状态，这是synchronized的致命弱点，这就强有力的给了Lock出现的理由。


### 显式锁Lock

**Lock具有不会阻塞的功能**，下面的三个方案都是解决这个问题的好办法：

| 特性             | 描述                                                                                                                         | API                          |
|------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------|
| 能响应中断       | 如果不能自己释放，那可以响应中断也是很好的。Java多线程中断机制专门描述了中断过程，目的是通过中断信号来跳出某种状态，比如阻塞 | lockInterruptbly()           |
| 非阻塞式的获取锁 | 尝试获取，获取不到不会阻塞，直接返回                                                                                         | tryLock()                    |
| 支持超时         | 给定一个时间限制，如果一段时间内没获取到，不是进入阻塞状态，同样直接返回                                                     | tryLock(long time, timeUnit) |

#### Lock使用范式

```java
Lock lock = new ReentrantLock();
lock.lock();
try{
    ...
}finally{
    lock.unlock();
}
```

在`try{}`外获取锁主要考虑两个方面：

- 如果**没有获取到锁就抛出异常**，最终释放锁肯定是有问题的，因为还未曾拥有锁谈何释放锁呢；
- 如果**在获取锁时抛出了异常**，也就是当前线程并未获取到锁，但执行到finally代码时，如果恰巧别的线程获取到了锁，则会被释放掉（无故释放）。


### Lock是怎样起到锁的作用

使用synchronized在程序编译成CPU指令后，在临界区会有`moniterenter`和`moniterexit`指令的出现，可以理解成进出临界区的标识。

从范式上来看：

- `lock.lock()`获取锁，“等同于”synchronized的`moniterenter`指令；

- `lock.unlock()`释放锁，“等同于”synchronized的`moniterexit`指令。

那Lock是怎么做到的呢？

其实很简单，比如在ReentrantLock内部维护了一个volatile修饰的变量state，通过CAS来进行读写（最底层还是交给硬件来保证原子性和可见性），如果CAS更改成功，即获取到锁，线程进入到try代码块继续执行；如果没有更改成功，线程会被【挂起】，不会向下执行。

但Lock是一个接口，里面根本没有state这个变量的存在。

它怎么处理这个state呢？

Lock接口的实现类基本都是通过【聚合】了一个【队列同步器】的子类完成线程访问控制的。


### 加锁示例

#### 加锁线程安全1

```java
package HowJThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static String now(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void log(String msg){
        System.out.printf("%s %s %s %n", now(), Thread.currentThread().getName(), msg);
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(){
            @Override
            public void run(){
                try {
                    log("线程启动。。。");
                    log("试图占有对象：lock");

                    lock.lock();

                    log("占有对象：lock");
                    log("进行5秒的操作。。。");
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log("释放对象：lock");
                    lock.unlock();
                }

                log("线程结束！");
            }
        };

        thread1.setName("thread1");
        thread1.start();

        //先让thread1执行
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread2 = new Thread() {
            public void run(){
                try {
                    log("线程启动。。。");
                    log("试图占有对象：lock");

                    lock.lock();

                    log("占有对象：lock");
                    log("进行5秒的操作。。。");
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log("释放对象：lock");
                    lock.unlock();
                }

                log("线程结束！");
            }
        };

        thread2.setName("thread2");
        thread2.start();
    }
}

/*
20:46:29 thread1 线程启动。。。 
20:46:29 thread1 试图占有对象：lock 
20:46:29 thread1 占有对象：lock 
20:46:29 thread1 进行5秒的操作。。。 
20:46:31 thread2 线程启动。。。 
20:46:31 thread2 试图占有对象：lock 
20:46:34 thread1 释放对象：lock 
20:46:34 thread1 线程结束！ 
20:46:34 thread2 占有对象：lock 
20:46:34 thread2 进行5秒的操作。。。 
20:46:39 thread2 释放对象：lock 
20:46:39 thread2 线程结束！
 */
```

#### 加锁线程安全2
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


#### 加锁线程安全3
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

### tryLock

`synchronized`是**不占用到手不罢休**的，会一直试图占用下去。与`synchronized`的钻牛角尖不一样，`Lock`接口还提供了一个`tryLock`方法。`tryLock`会在指定时间范围内试图占用。如果时间到了，还占用不成功，就不会一直等下去。

注意： 因为使用`tryLock`有可能成功，有可能失败，所以后面`unlock`释放锁的时候，**需要判断是否占用成功了**，如果没占用成功也`unlock`，就会抛出异常。

```java
package HowJThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestTryLock {

    public static String now() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void log(String msg) {
        System.out.printf("%s %s %s %n", now() , Thread.currentThread().getName() , msg);
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread() {
            public void run() {
                boolean locked = false;
                try {
                    log("线程启动");
                    log("试图占有对象：lock");

                    locked = lock.tryLock(1, TimeUnit.SECONDS);
                    if(locked) {
                        log("占有对象：lock");
                        log("进行5秒的业务操作");
                        Thread.sleep(5000);
                    }
                    else {
                        log("经过1秒钟的努力，还没有占有对象，放弃占有");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(locked) {
                        log("释放对象：lock");
                        lock.unlock();
                    }
                }
                log("线程结束");
            }
        };

        thread1.setName("thread1");
        thread1.start();

        try {
            //先让thread1启动
            Thread.sleep(5000);
            System.out.println("5秒后。。。");
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Thread thread2 = new Thread() {
            public void run() {
                boolean locked = false;
                try {
                    log("线程启动");
                    log("试图占有对象：lock");

                    locked = lock.tryLock(1,TimeUnit.SECONDS);
                    if(locked) {
                        log("占有对象：lock");
                        log("进行5秒的业务操作");
                        Thread.sleep(5000);
                    }
                    else{
                        log("经过1秒钟的努力，还没有占有对象，放弃占有");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(locked){
                        log("释放对象：lock");
                        lock.unlock();
                    }
                }
                log("线程结束");
            }
        };

        thread2.setName("thread2");
        thread2.start();
    }
}
/*
21:31:47 thread1 线程启动
21:31:47 thread1 试图占有对象：lock
21:31:47 thread1 占有对象：lock
21:31:47 thread1 进行5秒的业务操作
3秒后。。。
21:31:50 thread2 线程启动
21:31:50 thread2 试图占有对象：lock
21:31:51 thread2 经过1秒钟的努力，还没有占有对象，放弃占有
21:31:51 thread2 线程结束
21:31:52 thread1 释放对象：lock
21:31:52 thread1 线程结束

21:33:33 thread1 线程启动 
21:33:33 thread1 试图占有对象：lock 
21:33:33 thread1 占有对象：lock 
21:33:33 thread1 进行5秒的业务操作 
5秒后。。。
21:33:38 thread2 线程启动 
21:33:38 thread2 试图占有对象：lock 
21:33:38 thread1 释放对象：lock 
21:33:38 thread1 线程结束 
21:33:38 thread2 占有对象：lock 
21:33:38 thread2 进行5秒的业务操作 
21:33:43 thread2 释放对象：lock 
21:33:43 thread2 线程结束 
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
            System.out.println("传统方式创建线程-》" + Thread.currentThread().getName());
        }
    }).start();
}

// 使用Lambda
private static void thread02(){
    new Thread(() -> {
        //业务代码
        System.out.println("lambda方式创建线程-》" + Thread.currentThread().getName());
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

* 一旦`synchronized`块结束，就会自动释放对`someObject`的占用。`Lock`却必须调用`unlock`方法进行手动释放，为了保证释放的执行，往往会把`unlock()`放在`finally`中进行。
* `synchronized`内置的Java**关键字**，`Lock`是一个Java**接口**；
* `synchronized`无法判断获取锁的状态，`Lock`可以判断是否获取到了锁；
* `Lock`是显示锁（手动开启和关闭锁）必须手动释放锁！如果不释放，则会出现死锁；`sychronized`是隐式锁，出了作用域自动释放；
* `Lock`只有代码块锁，`sychronized`有代码块锁和方法锁；
* `synchronized`线程1（获得锁，阻塞）、线程2（等待，傻傻的等）；`Lock`锁就不一定会等待下去（`lock.tryLock();`）；
* 使用`Lock`锁，`JVM`将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）。
* `synchronized`可重入锁，不可以中断的，**非公平**；`Lock`可重入锁，可以判断锁，是否公平可以自己设置；
* `synchronized`适合锁少量的代码同步问题，`Lock`适合锁大量的同步代码！
* 优先使用顺序：`Lock` > 同步代码块 > 同步方法


### 队列同步器AQS

Lock接口的实现类基本都是通过【聚合】了一个【队列同步器】的子类完成线程访问控制的。

队列同步器(AbstractQueuedSynchronizer)，简称同步器或AQS。`ReentrantLock, ReentrantReadWriteLock, Semaphore(信号量), CountDownLatch, 公平锁, 非公平锁,ThreadPoolExecutor`都和AQS有直接关系。

**锁是面向使用者的**，它定义了使用者与锁交互的接口，隐藏了实现细节，我们只要像使用范式那样就可以了；**同步器面向的是锁的实现者**，比如我们自定义的同步器，它简化了锁的实现方式，屏蔽了同步状态管理、线程排队、等待/唤醒等底层操作。

从AQS的类名称和修饰上来看，这是一个**抽象类**，所以从设计模式的角度来看同步器一定是基于【模版模式】来设计的，使用者需要**继承同步器**，实现自定义同步器，并**重写指定方法**，随后将同步器组合在自定义的同步组件中，并调用同步器的模版方法，而这些模版方法又回调用使用者重写的方法。

想理解上面这句话，我们只需要知道下面两个问题就好了：

- 哪些是自定义同步器可重写的方法？
- 哪些是抽象同步器提供的模版方法？

#### 同步器可重写的方法

同步器提供的可重写方法只有5个：

<div align=center><img src=Thread\同步器可重写的方法.png></div>

自定义的同步组件或者锁**不可能既是独占式又是共享式**，为了避免强制重写不相干方法，所以就没有abstract来修饰了，但**要抛出异常**告知不能直接使用该方法：

```java
protected boolean tryAcquire(int arg) {
    throw new UnsupportedOperationException();
}
```

表格方法描述中所说的同步状态就是上文提到的有volatile修饰的state。所以我们在重写上面几个方法时，还要通过同步器提供的下面三个方法（AQS 提供的）来**获取或修改同步状态**：

<div align=center><img src=Thread\同步状态方法.png></div>

而独占式和共享式操作state变量的区别也就很简单了：

<div align=center><img src=Thread\独占式和共享式操作state变量.png></div>

所以`ReentrantLock, ReentrantReadWriteLock, Semaphore(信号量), CountDownLatch`这几个类其实仅仅是在实现以上几个方法上略有差别，其他的实现都是通过同步器的模版方法来实现的。


#### 同步器提供的模版方法

上面我们将同步器的实现方法分为**独占式**和**共享式**两类，模版方法其实除了提供以上两类模版方法之外，只是多了**响应中断**和**超时限制**的模版方法供Lock使用：

<div align=center><img src=Thread\同步器提供的模版方法.png></div>

上面的方法都有final关键字修饰，说明子类不能重写这个方法。

<div align=center><img src=Thread\AQS队列同步器.png></div>

```java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义互斥锁
 */

public class MyMutex implements Lock {

    // 静态内部类--自定义同步器
    private static class MySync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            // 调用AQS提供的方法，通过CAS保证原子性
            if (compareAndSetState(0, arg)) {
                // 实现的是互斥锁，所以标记获取到同步状态（更新state成功）的线程，主要为了判断是否可重入
                setExclusiveOwnerThread(Thread.currentThread());
                //获取同步状态成功，返回 true
                return true;
            }

            // 获取同步状态失败，返回 false
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 未拥有锁却让释放，会抛出IMSE
            if (getState() == 0)
                throw new IllegalStateException();

            // 可以释放，清空排它线程标记
            setExclusiveOwnerThread(null);

            // 设置同步状态为0，表示释放锁
            setState(0);
            return true;
        }

        // 是否独占式持有
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 后续会用到，主要用于等待/通知机制，每个condition都有一个与之对应的条件等待队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }


    // 聚合自定义同步器
    private final MySync sync = new MySync();

    // 重写Lock接口中定义的方法

    @Override
    public void lock() {
        // 阻塞式的获取锁，调用同步器模版方法独占式，获取同步状态
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // 调用同步器模版方法可中断式获取同步状态
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        // 调用自己重写的方法，非阻塞式的获取同步状态
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // 调用同步器模版方法，可响应中断和超时时间限制
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        // 释放锁
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        // 使用自定义的条件
        return sync.newCondition();
    }
}
```

`ReentrantLock, ReentrantReadWriteLock, Semaphore(信号量), CountDownLatch`都是按照这个结构实现，所以我们就来看一看AQS的模版方法到底是怎么实现锁。


### AQS实现分析

https://dayarch.top/p/java-aqs-and-reentrantlock.html


## volatile

`Volatile`是Java虚拟机提供的**轻量级的同步机制**
- 保证可见性
- 不保证原子性
- 禁止指令重排

在多线程下，处理共享变量时Java的内存模型：Java内存模型规定，将所有的变量都存放在**主内存**中，当线程使用变量时，会把主内存里面的变量复制到自己的工作空间或者叫作工作内存，**线程读写变量时操作的是自己工作内存中的变量**，处理完后将变量值更新到主内存。

### JMM

假设CPU执行一条普通指令需要一天，那么CPU读写内存就得等待一年的时间。在CPU眼里，程序的整体性能都被内存的办事效率拉低了，为了解决这个短板，缓存Cache应运而生。

**CPU增加了缓存均衡了与内存的速度差异**，这一增加还是好几层：
<div align=center><img src=Thread\缓存.jpg></div>

此时内存的短板不再那么明显，CPU甚喜。

但随之却带来很多问题：
<div align=center><img src=Thread\缓存1.jpg></div>

每个核都有自己的一级缓存(L1 Cache)，有的架构里面还有所有核共用的二级缓存(L2 Cache)。**使用缓存之后，当线程要访问共享变量时，如果L1中存在该共享变量，就不会再逐级访问直至主内存了**。所以，通过这种方式，就补上了**访问内存慢**的短板。

具体来说，线程**读/写共享变量**的步骤是这样：

- 从主内存复制共享变量到自己的工作内存
- 在工作内存中对变量进行处理
- 处理完后，将变量值更新回主内存


假设现在主内存中有共享变量X，其初始值为0。线程1先访问变量X：

- L1和L2中都没有发现变量X，直到在主内存中找到
- 拷贝变量X到L1和L2中
- 在L1中将X的值修改为1，并逐层写回到主内存中
  
此时，在线程1眼中，X的值是这样的：
<div align=center><img src=Thread\缓存2.jpg width=60%></div>

接下来，线程2同样按照上面的步骤访问变量X：

- L1中没有发现变量X
- **L2中发现了变量X**
- 从L2中拷贝变量到L1中
- 在L1中将X的值修改为2，并逐层写回到主内存中
  
此时，线程2眼中，X的值是这样的：
<div align=center><img src=Thread\缓存3.jpg width=60%></div>

结合刚刚的两次操作，当线程1再访问变量x，就会出现问题：
<div align=center><img src=Thread\缓存4.jpg width=80%></div>

此刻，如果线程1再次将`x=1`回写，就会覆盖线程2`x=2`的结果，**同样的共享变量，线程拿到的结果却不一样**（线程1眼中`x=1`；线程2眼中`x=2`），这就是**共享变量内存不可见**的问题。


**`synchronized`关键字是怎么解决上面提到的共享变量内存不可见性问题的呢？**

- 【进入】synchronized块的内存语义是**把在synchronized块内使用的变量从线程的工作内存中清除，从主内存中读取**
- 【退出】synchronized块的内存语义事把在synchronized块内对共享变量的修改**刷新到主内存中**


### Happens-before规则

**可见性/原子性/有序性**三个问题导致了很多并发Bug：

- 为了解决CPU、内存、IO的短板，增加了**缓存**，但这导致了**可见性**问题；
- 编译器/处理器擅自**优化** (Java代码在编译后会变成**Java字节码**，**字节码被类加载器加载到JVM里**，JVM执行字节码，最终需要转化为**汇编指令**在CPU上执行) ，导致有序性问题。

既然不能完全禁止缓存和编译优化，那就**按需禁用缓存和编译优化**，按需就是要加一些约束，约束中就包括了**volatile，synchronized，final**三个关键字，同时还有**Happens-Before原则**(包含可见性和有序性的约束)。

- 对于会改变程序执行结果的重排序，JMM要求编译器和处理器必须禁止这种重排序。
- 对于不会改变程序执行结果的重排序，JMM对编译器和处理器不做要求 (JMM允许这种重排序)。
  
Happens-before规则主要用来约束两个操作，两个操作之间具有happens-before关系，**并不意味着前一个操作必须要在后一个操作之前执行**。happens-before仅仅要求**前一个操作(执行的结果)对后一个操作可见**，(the first is visible to and ordered before the second)。



**1. 程序顺序性规则**：一个线程中，按照程序的顺序，前面的操作happens-before后续的任何操作！

顺序性是指，我们可以按照顺序推演程序的执行结果，但是编译器未必一定会按照这个顺序编译，但是**编译器保证结果一定`==`顺序推演的结果**。

这里是一个线程中的操作，其实隐含了「as-if-serial」语义: 就是**只要执行结果不被改变，无论怎么“排序”，都是对的**。

**2. volatile变量规则**：对一个volatile变量的写操作，happens-before后续对这个变量的读操作。

```java{.line-numbers highlight=5}
public class ReorderExample {
    private int x = 0;
    private int y = 1;

    private volatile boolean flag = false;
    
    public void writer(){
        x = 42;	//1
        y = 50;	//2
        flag = true;	//3
    }
    
    public void reader(){
        if (flag){	//4
        System.out.println("x:" + x);	//5
        System.out.println("y:" + y);	//6
        }
    }
}
```

|  能否重排序 | 第二个操作 |  第二个操作 |  第二个操作 |
|:-----------:|:----------:|:-----------:|:-----------:|
|  第一个操作 |  普通读/写 | volatile 读 | volatile 写 |
|  普通读/写  |      -     |      -      |      NO     |
| volatile 读 |     NO     |      NO     |      NO     |
| volatile 写 |      -     |      NO     |      NO     |

- **如果第二个操作为`volatile写`，不管第一个操作是什么，都不能重排序**！这就确保了`volatile写之前的操作`不会被重排序到`volatile写`之后。

    拿上面的代码来说，代码1和2不会被重排序到代码3的后面，但代码1和2可能被重排序 (没有依赖也不会影响到执行结果)。

- **如果第一个操作为`volatile读`，不管第二个操作是什么，都不能重排序**，这确保了`volatile读之后的操作`不会被重排序到`volatile读`之前
    拿上面的代码来说，代码4是读取volatile变量，代码5和6不会被重排序到代码4之前。


volatile内存语义的实现是应用到了**内存屏障**。


**3.传递性规则**：如果 A happens-before B, 且 B happens-before C, 那么 A happens-before C。

<div align=center><img src=Thread\线程切换图.png></div>

- `x=42`和`y=50` Happens-before `flag = true`，这是`规则1`；
- 写变量(代码3)`flag=true` Happens-before 读变量(代码 4)`if(flag)`，这是`规则2`；
- 根据`规则3传递性规则`，`x=42` Happens-before 读变量`if(flag)`。

**如果线程B读到了flag是true，那么`x=42`和`y=50`对线程B就一定可见了**，这就是Java1.5的增强。


**4.监视器锁规则**：对一个锁的**解锁**操作，happens-before后续对这个锁的**加锁**操作。
```java
public class SynchronizedExample {
    private int x = 0;

    public void synBlock(){
        // 1.加锁
        synchronized (SynchronizedExample.class){
            x = 1; // 对x赋值
        }
        // 3.解锁
    }
    
    // 1.加锁
    public synchronized void synMethod(){
        x = 2; // 对x赋值
    }
    // 3. 解锁
}
```

**5.start()规则**：如果线程A执行操作`ThreadB.start()`(启动线程B)，那么A线程的`ThreadB.start()`操作 happens-before 于线程B中的任意操作！也就是说，**主线程A启动子线程B后，子线程B能看到主线程在启动子线程B前的操作**。

```java
public class StartExample {
    private int x = 0;
    private int y = 1;
    private boolean flag = false;

    public void write() {
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("flag =" + flag);
    }

    public static void main(String[] args) {
        StartExample startExample = new StartExample();

        Thread thread = new Thread(startExample::write, "线程thread");

        startExample.x = 10;
        startExample.y = 20;
        startExample.flag = true;

        thread.start();

        System.out.println("主线程结束！");
    }
}
/*
主线程结束！
x = 10
y = 20
flag =true
 */
```
线程thread看到了主线程调用`thread1.start()`之前的所有赋值结果！


**6.join()规则**：如果线程A执行操作`ThreadB.join()`并成功返回, 那么线程B中的任意操作 happens-before 于线程`A从ThreadB.join()`操作成功返回，和`start规则`刚好相反。主线程A等待子线程B完成，当子线程B执行完毕后，主线程能够看到子线程B的所有操作。

```java
public class JoinExample {
    private int x = 0;
    private int y = 1;
    private boolean flag = false;

    public void writer(){
        this.x = 100;
        this.y = 200;
        this.flag = true;
    }

    public static void main(String[] args) throws InterruptedException {
        JoinExample joinExample = new JoinExample();

        Thread thread1 = new Thread(joinExample::writer, "线程1");
        thread1.start();

        thread1.join();

        System.out.println("x:" + joinExample.x );
        System.out.println("y:" + joinExample.y );
        System.out.println("flag:" + joinExample.flag );
        System.out.println("主线程结束");
    }

}
/*
x:100
y:200
flag:true
主线程结束
 */
```

**总结**：

- Happens-before 重点是解决**前一个操作结果对后一个操作可见**。这些规则解决了多线程编程的可见性与有序性问题，但还没有完全解决原子性问题(除了synchronized)；
- start()和join()规则也是解决主线程与子线程通信的方式之一；
- 从内存语义的角度来说，`volatile的写-读`与`锁的释放-获取`有相同的内存效果；`volatile写`和`锁的释放`有相同的内存语义；`volatile读`与`锁的获取`有相同的内存语义。
- volatile解决的是**可见性**问题，synchronized解决的是**原子性**问题。



### 使用volatile消除共享内存不可见问题

当一个变量被声明为volatile时：

- 线程在【读取】共享变量时，会**先清空本地内存变量值，再从主内存获取最新值**；
- 线程在【写入】共享变量时，不会把值缓存在寄存器或其他地方（就是刚刚说的所谓的「工作内存」），而是会**把值刷新回主内存**。

<div align=center><img src=Thread\volatile.jpg width=50%></div>

两者处理方式换汤不换药！

所以，当使用`synchronized`或`volatile`后，多线程操作共享变量的步骤就变成了这样：
<div align=center><img src=Thread\volatile1.jpg width=70%></div>

简单点来说就是不再参考L1和L2中共享变量的值，而是**直接访问主内存**！

注意第22，26行和第35行！
```java {.line-numbers}
public class ThreadNotSafeInteger {
    /**
    * 共享变量 value
    */
    private int value;
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}

public class ThreadSafeInteger {
    /**
    * 共享变量 value
    */
    private int value;
    
    public synchronized int getValue() {
        return value;
    }
    
    public synchronized void setValue(int value) {
        this.value = value;
    }
}

public class ThreadSafeInteger {
    /**
    * 共享变量 value
    */
    private volatile int value;
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}
```
这两个结果是完全相同，在解决【当前】共享变量数据可见性的问题上，二者算是等同的！



#### volatile 写-读的内存语义

假定线程A先执行writer方法，随后线程B执行reader方法：

```java
public class ReorderExample {
    
    private int x = 0;
    private int y = 1;
    private volatile boolean flag = false;
    
    public void writer(){
        x = 42;	//1
        y = 50;	//2
        flag = true;  //3
    }
    
    public void reader(){
        if (flag){  //4
            System.out.println("x:" + x);  //5
            System.out.println("y:" + y);  //6
        }
    }
}
```

当线程A执行writer方法时：

<div align=center><img src=Thread\volatile可见性.png></div>

**线程A将本地内存更改的变量写回到主内存中**！

**volatile读的内存语义**：当读一个volatile变量时，JMM会**把该线程对应的本地内存置为无效**。线程接下来将从主内存中读取共享变量。

所以当线程B执行reader方法时：

<div align=center><img src=Thread\volatile可见性读.png></div>
线程B本地内存变量无效，从主内存中读取变量到本地内存中，也就得到了线程A更改后的结果，这就是volatile如何保证可见性的过程！

- 线程A写一个volatile变量，实质上是线程A向接下来将要读这个volatile变量的某个线程发出了(其对共享变量所做修改的)消息；
- 线程B读一个volatile变量，实质上是线程B接收了之前某个线程发出的(在写这个volatile变量之前对共享变量所做修改的)消息。
- 线程A写一个volatile变量，随后线程B读这个volatile变量，这个过程实质上是**线程A通过主内存向线程B发送消息**。


从内存语义的角度来说，
- `volatile的写-读`与`锁的释放-获取`有相同的内存效果；
- `volatile写`和`锁的释放`有相同的内存语义；
- `volatile读`与`锁的获取`有相同的内存语义。


### synchronized与volatile区别

如果说`synchronized`和`volatile`是完全等同的，那是不是就没必要设计两个关键字了？

继续看个例子：

```java
package Thread;

public class VisibilityIssue {
    private static final int TOTAL = 10000;

    // 即便像下面这样加了volatile关键字修饰不会解决问题，因为并没有解决原子性问题
    private volatile int count;

    public static void main(String[] args) {
        VisibilityIssue visibilityIssue = new VisibilityIssue();

        Thread thread1 = new Thread(() -> visibilityIssue.add10KCount());
        Thread thread2 = new Thread(() -> visibilityIssue.add10KCount());

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count的值为：" + visibilityIssue.count);
    }

    private void add10KCount() {
        int start = 0;
        while (start++ < TOTAL)
            this.count++;
    }
}
/*
count的值为：15259
 */
```

其实就是将上面`setValue`简单赋值操作`this.value = value;`变成了`this.count++;`形式。count的值始终是处于1w和2w之间。

将上面方法再以`synchronized`的形式做改动：

```java
private int count;
...
private synchronized void add10KCount() {
    int start = 0;
    while (start++ < TOTAL)
        this.count++;
}

/*
20000
*/
```

**`count++`程序代码是一行，但是翻译成CPU指令却是三行**(可以用`javap -c`命令查看)。

`synchronized`是**独占锁/排他锁**（**有你没我**），同时只能有一个线程调用 `add10KCount`方法，其他调用线程会被阻塞。所以三行CPU指令都是同一个线程执行完之后别的线程才能继续执行，这就是通常说说的**原子性**（**线程执行多条指令不被中断**）

但`volatile`是**非阻塞算法**（**不排他**），**当遇到三行CPU指令自然就不能保证别的线程不插足了**，这就是通常所说的：**`volatile`能保证内存可见性，但是不能保证原子性**。

那什么时候才能用volatile关键字呢？

**如果写入变量值不依赖变量当前值，那么就可以用volatile**！

比如上面`count++`，是`获取-计算-写入`三步操作，也就是依赖当前值的，所以不能靠volatile解决问题。


就好像：如果让你同一段时间内【写几行代码】就要去【数钱】，数几下钱就要去【唱歌】，唱完歌又要去【写代码】，反复频繁这样操作，还要接上上一次的操作（代码接着写，钱累加着数，歌接着唱）还需要保证不出错，你累不累？

synchronized是排他的，**线程排队就要有切换**，这个切换就好比上面的例子，**要完成切换，还得记准线程上一次的操作**，很累CPU大脑，这就是通常说的**上下文切换会带来很大开销**。

volatile就不一样了，它是非阻塞的方式，所以在**解决共享变量可见性问题**的时候，volatile就是synchronized的弱同步体现了。





### 内存屏障(Memory Barriers / Fences)

JMM针对编译器定制了`volatile重排序`的规则(`volatile变量规则`)，那JMM是怎样**禁止重排序**的呢？

答案是**内存屏障**！

为了实现volatile的内存语义，**编译器在生成字节码时，会在指令序列中插入内存屏障**来禁止特定类型的处理器重排序。即：volatile通过内存屏障保证程序不被”擅自”排序！

想象内存屏障是一面高墙，如果两个变量之间有这个屏障，那么他们就不能互换位置(重排序)了。变量有读(Load)有写(Store)，操作有前有后，JMM就将内存屏障插入策略分为4种：
1. 在每个`volatile写`操作的**前面**插入一个`StoreStore`屏障
2. 在每个`volatile写`操作的**后面**插入一个`StoreLoad`屏障
3. 在每个`volatile读`操作的**后面**插入一个`LoadLoad`屏障
4. 在每个`volatile读`操作的**后面**插入一个`LoadStore`屏障

1和2用图形描述以及对应表格规则如下图所示：
<div align=center><img src=Thread\内存屏障1和2.png></div>

3和4用图形描述以及对应表格规则如下图所示：
<div align=center><img src=Thread\内存屏障3和4.png></div>

```java
public class VolatileBarrierExample {
    private int a;
    private volatile int v1 = 1;
    private volatile int v2 = 2;

    void readAndWrite(){
        int i = v1; //第一个volatile读
        int j = v2;	//第二个volatile读
        a = i + j;	//普通写
        v1 = i + 1;	//第一个volatile写
        v2 = j * 2;	//第二个volatile写
    }
}
```

将屏障指令带入到程序：
<div align=center><img src=Thread\内存屏障.png></div>

- 彩色是将屏障指令带入到程序中生成的全部内容，也就是编译器生成的「最稳妥」的方案；
- 显然有很多屏障是重复多余的，右侧虚线框指向的屏障是可以被「优化」删除掉的屏障。


## 读写锁

互斥锁都是排他锁，也就是说同一时刻只允许一个线程进行访问，当面对可共享读的业务场景，互斥锁显然是比较低效的一种处理方式。为了提高效率，读写锁模型就诞生了。

一个写线程改变了缓存中的值，其他读线程一定是可以 “感知” 到的，否则可能导致查询到的值不准确。所以关于读写锁模型就了下面这3条规定：

- 允许多个线程同时读共享变量
- 只允许一个线程写共享变量
- 如果写线程正在执行写操作，此时则禁止其他读线程读共享变量

ReadWriteLock是一个接口，其内部只有两个方法：
```java
public interface ReadWriteLock {
    // 返回用于读的锁
    Lock readLock();

    // 返回用于写的锁
    Lock writeLock();
}
```

ReentrantReadWriteLock是ReadWriteLock的实现类：
<div align=center><img src=Thread\对比ReentrantReadWriteLock与ReentrantLock的类结构.png></div>

ReentrantReadWriteLock的基本特性：
<div align=center><img src=Thread\ReentrantReadWriteLock的基本特性.png></div>

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
    //private Lock lock = new ReentrantLock();

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



# 线程通信

- [并发编程为什么会有等待/通知机制](#破坏不可剥夺条件)？
- 如何应用等待/通知机制？
- 为什么说尽量使用notifyAll？
- 什么时候使用notify不会有问题？
- MESA监视器模型简介

解决死锁的思路之一就是**破坏请求和保持条件**，所有柜员都要通过唯一的**账本管理员**一次性拿到所有转账业务需要的账本。没有**等待/通知机制**之前，所有柜员都通过死循环的方式不断向账本管理员申请所有账本：`while(!accountBookManager.getAllRequiredAccountBook(this, target));`，但程序无限申请，浪费CPU。

无限循环实在太浪费CPU，而理想情况应该是这样：

- 柜员A如果拿不到所有账本，就傲娇的不再继续问了（线程阻塞自己`wait`）
- 柜员B归还了柜员A需要的账本之后就主动通知柜员A账本可用（通知等待的线程`notify/notifyAll`）

<div aling=center><img src=Thread\等待通知机制.png width=80%></div>

- **一个锁对应一个【入口等待队列】，不同锁的入口等待队列没任何关系**，说白了他们就不存在竞争关系。
  不同患者进入眼科和耳鼻喉科看大夫一点冲突都没有。
- `wait(), notify()/notifyAll()`要在synchronized内部被使用，并且，如果锁的对象是this，就要`this.wait(), this.notify()/this.notifyAll()`，否则JVM就会抛出`java.lang.IllegalMonitorStateException`的。
  等待/通知机制就是从【竞争】环境逐渐衍生出来的策略，不在锁竞争内部使用或等待/通知错了对象，自然是不符合常理的。

要想将无限循环策略改为等待通知策略，还需要解决四个问题([以钱庄账本管理员为例](#破坏请求和保持条件))：
<div aling=center><img src=Thread\等待通知机制1.png></div>

```java{.line-numbers highlight=8}
public class AccountBookManager {

    List<Object> accounts = new ArrayList<>(2);

    synchronized boolean getAllRequiredAccountBook(Object from, Object to){
        if(accounts.contains(from) || accounts.contains(to)){
            try{
                this.wait();
            }catch(Exception e){

            }
        } else{
            accounts.add(from);
            accounts.add(to);

            return true;
        }
    }
    // 归还资源
    synchronized void releaseObtainedAccountBook(Object from, Object to){
        accounts.remove(from);
        accounts.remove(to);
        notify();
    }
}
```

**问题一**：在上面`this.wait()`处，使用了`if`条件判断，会出现天大的麻烦，来看下图（从下往上看）：
<div align=center><img src=Thread\等待通知机制2.png width=30%></div>

notify唤醒的那一刻，线程【曾经/曾经/曾经】要求的条件得到了满足。从这一刻开始，到去条件等队列中唤醒线程，再到再次尝试获取锁是有**时间差**的。当再次获取到锁时，**线程曾经要求的条件是不一定满足**，所以需要重新进行条件判断，所以需要将if判断改成while判断：

```java{.line-numbers highlight=6}
public class AccountBookManager {

    List<Object> accounts = new ArrayList<>(2);

    synchronized boolean getAllRequiredAccountBook(Object from, Object to){
        while(accounts.contains(from) || accounts.contains(to)){
            try{
                this.wait();
            }catch(Exception e){

            }
        } else{
            accounts.add(from);
            accounts.add(to);

            return true;
        }
    }
    // 归还资源
    synchronized void releaseObtainedAccountBook(Object from, Object to){
        accounts.remove(from);
        accounts.remove(to);
        notify();
    }
}
```

一个线程可以从`挂起状态`变为`可运行状态`（也就是`被唤醒`），即使线程没有被其他线程调用`notify()/notifyAll()`方法进行通知，或被中断，或者等待超时，这就是所谓的【虚假唤醒】。虽然虚假唤醒很少发生，但要防患于未然，做法就是**不停的去测试该线程被唤醒条件是否满足**。

**被唤醒的线程再次获取到锁之后，是从原来的wait之后开始执行的**，wait在循环里面，所以会再次进入循环条件**重新进行条件判断**。

从哪里跌倒就从哪里爬起来；在哪里wait，就从wait那里继续向后执行。这也就成了使用wait()的标准范式：
<div align=center><img src=Thread\wait()标准范式.jpg width=20%></div>

**问题二**：线程归还所使用的账户之后使用`notify`而不是`notifyAll`进行通知。


## 监视器

**锁和条件**是`Java 5`中的新内容。在`Java 5`之前，线程通信是使用对象的`内置监视器`编程实现的。锁和条件比内置监视器更加强大且灵活，因此无须使用监视器。然而，如果使用遗留的Java代码，就可能会碰到Java的内置监视器。

&emsp;&emsp;监视器(monitor)是一个相互排斥且具备同步能力的对象。监视器中的一个时间点上，只能有一个线程执行一个方法。线程通过获取监视器上的锁进入监视器，并且通过释放锁退出监视摇。任意对象都可能是一个监视器。**一旦一个线程锁住对象，该对象就成为监视器**。加锁是通过在方法或块上使用`synchronized`关键字来实现的。在执行同步方法或块之前，线程必须获取锁。如果条件不适合线程继续在监视器内执行，线程可能在监视器中等待。可以对监视器对象调用`wait()`方法来释放锁，这样其他的一些监视器中的线程就可以获取它，也就有可能改变监视器的状态。当条件合适时，另一线程可以调用`notify()`或`notifyAll()`方法来通知一个或所有的等待线程重新获取锁并且恢复执行。

<div align=center><img src=Thread/监视器.png></div>

**`wait`方法和`notify`方法，并不是`Thread`线程上的方法，它们是`Object上`的方法。因为所有的`Object`都可以被用来作为同步对象，所以准确的讲，`wait`和`notify`是**同步对象上的方法。

`wait()`的意思是： 让占用了这个同步对象的线程，临时释放当前的占用，并且等待。 所以**调用`wait`是有前提条件的，一定是在`synchronized`块里**，否则就会出错。

`notify()`的意思是，通知一个等待在这个同步对象上的线程，你可以苏醒过来了，有机会重新占用当前对象了。

`notifyAll()`的意思是，通知所有的等待在这个同步对象上的线程，你们可以苏醒过来了，有机会重新占用当前对象了。

### 使用wait和notify进行线程交互

在`Hero`类中：`hurt()`减血方法：当`hp=1`的时候，执行`this.wait()`。`this.wait()`表示**让占有this的线程等待，并临时释放占有**。进入`hurt`方法的线程必然是减血线程，`this.wait()`会**让减血线程临时释放对`this`的占有**。 这样加血线程，就有机会进入`recover()`加血方法了。


`recover()`加血方法：增加了血量，执行`this.notify();`。`this.notify()`表示**通知那些等待在`this`的线程，可以苏醒过来了**。等待`this`的线程，恰恰就是减血线程。 一旦`recover()`结束，加血线程释放了`this`，减血线程就可以重新占有`this`，并执行后面的减血工作。

<div align=center><img src=Thread\使用wait和notify进行线程交互.png></div>

```java
// Hero.java
package HowJThread;

public class Hero {
    public String name;
    public float hp;

    public int damage;

    public synchronized void recover() {
        hp = hp + 1;
        System.out.printf("%s 回血1点，增加血后，%s的血量是%.0f%n", name, name, hp);
        // 通知那些等待在this对象上的线程，可以醒过来了
        this.notify();
    }

    public synchronized void hurt() {
        if (hp == 1) {
            try {
                // 让占有this的减血线程，暂时释放对this的占有，并等待
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        hp = hp - 1;
        System.out.printf("%s 减血1点，减少血后，%s的血量是%.0f%n", name, name, hp);
    }

    public void attackHero(Hero hero) {
        hero.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s的血变成了 %.0f%n", name, hero.name, hero.name, hero.hp);
        if (hero.isDead())
            System.out.println(hero.name + "死了！");
    }

    public boolean isDead() {
        return 0 >= hp ? true : false;
    }
}


// TestThread.java
package HowJThread;

public class TestThread {
    public static void main(String[] args) {

        final Hero gareen = new Hero();
        gareen.name = "盖伦";
        gareen.hp = 6;

        Thread thread1 = new Thread(){
            public void run(){
                while(true){
                    gareen.hurt();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        thread1.start();

        Thread thread2 = new Thread(){
            public void run(){
                while(true){
                    gareen.recover();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        thread2.start();
    }
}

/*
盖伦 减血1点，减少血后，盖伦的血量是5
盖伦 回血1点，增加血后，盖伦的血量是6
盖伦 减血1点，减少血后，盖伦的血量是5
盖伦 减血1点，减少血后，盖伦的血量是4
盖伦 减血1点，减少血后，盖伦的血量是3
盖伦 减血1点，减少血后，盖伦的血量是2
盖伦 减血1点，减少血后，盖伦的血量是1
盖伦 回血1点，增加血后，盖伦的血量是2
盖伦 减血1点，减少血后，盖伦的血量是1
盖伦 回血1点，增加血后，盖伦的血量是2
 */
```



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

### 尽量使用notifyAll()

- notify()函数
  **随机唤醒一个**：一个线程调用共享对象的notify()方法，会唤醒一个在该共享变量上调用wait()方法后被挂起的线程，一个共享变量上可能有多个线程在等待，具体唤醒那一个，是随机的。

- notifyAll()函数
  **唤醒所有**：与notify()不同，notifyAll()会唤醒在该共享变量上由于调用wait()方法而被挂起的所有线程。


```java
package Thread;

public class NotifyTest {

    private static volatile Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(() -> {
            synchronized (resourceA){
                System.out.println("threadA get resourceA lock");

                try{
                    System.out.println("threadA begins to wait...");
                    resourceA.wait();
                    System.out.println("threadA ends wait");

                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (resourceA){
                System.out.println("threadB get resourceA lock");

                try{
                    System.out.println("threadB begins to wait...");
                    resourceA.wait();
                    System.out.println("threadB ends wait");

                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        Thread threadC = new Thread(() -> {
            synchronized (resourceA){
                System.out.println("threadC begin to notify");
                resourceA.notify();
            }
        });

        threadA.start();
        threadB.start();

        Thread.sleep(1000);

        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        System.out.println("main thread over now");
    }
}
/*
threadA get resourceA lock
threadA begins to wait...
threadB get resourceA lock
threadB begins to wait...
threadC begin to notify
threadA ends wait
...//等待
 */
```

程序中我们使用notify()随机通知resourceA的等待队列的一个线程，threadA被唤醒，threadB却没有打印出`threadB ends wait`这句话。


使用notifyAll()确实不会遗落等待队列中的线程，但也**产生了比较强烈的竞争**。

如果notify()设计的本身就是bug，那么这个函数应该早就从JDK中移除了，它随机通知一个线程的形式必定是有用武之地的。


### 什么时候可以使用notify()

1. 所有等待线程拥有相同的等待条件；
2. 所有等待线程被唤醒后，执行相同的操作；
3. 只需要唤醒一个线程。

notify()的典型的应用就是线程池！





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


# 线程池
&emsp;&emsp;**经常创建和销毁、使用量特别大的资源**，比如并发情况下的线程，对性能影响很大。因此，可以**提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中**。可以避免频繁创建、销毁，实现重复利用。<font color=red>类似每次需要骑车时，去站点使用共享单车，而不是每次都去买一辆</font>。 

这么做可以：
* 提高响应速度（减少了创建新线程的时间）；
* 降低资源消耗（**重复利用线程池中线程，不需要每次都创建*8）；
* 便于线程管理：
    `corePoolSize`：管理核心池的大小；
    `maximumPoolSize`：管理最大线程数；
    `keepAliveTime`：管理线程没有任务时最多保持多长时间后会终止。


## 手动创建线程有什么缺点

**不受控风险；频繁创建开销大**：

系统资源有限，每个人针对不同业务都可以手动创建线程，并且创建标准不一样（比如线程没有名字）。当系统运行起来，所有线程都在疯狂抢占资源。

过多的线程自然也会引起**上下文切换的开销**。

创建一个线程干了什么导致开销大了？和我们创建一个普通Java对象有什么差别？

`new Thread()`在操作系统层面并没有创建新的线程，这是编程语言特有的。真正转换为操作系统层面创建一个线程，还要调用操作系统内核的API，然后操作系统要为该线程分配一系列的资源。


**`new Object()`过程**：

当需要【对象】时，可以new一个，其过程为：

- 分配一块内存M
- 在内存M上初始化该对象
- 将内存M的地址赋值给引用变量obj

**创建一个线程的过程**：

创建一个线程还要调用操作系统内核API。为了更好的理解创建并启动一个线程的开销，我们需要看看JVM在背后帮我们做了哪些事情：

- 它为一个线程栈分配内存，该栈为每个线程方法调用保存一个栈帧
- 每一栈帧由一个局部变量数组、返回值、操作数堆栈和常量池组成
- 一些支持本机方法的jvm也会分配一个本机堆栈
- 每个线程获得一个**程序计数器**，告诉它当前处理器执行的指令是什么
- 系统创建一个与Java线程对应的本机线程
- 将与线程相关的描述符添加到JVM内部数据结构中
- 线程共享堆和方法区域

这段描述稍稍有点抽象，用数据来说明创建一个线程（即便不干什么）需要多大空间呢？答案是大约1M左右。

对于性能要求严苛的现在，频繁手动创建/销毁线程的代价是非常巨大的。


常见的数据库连接池，实例池等都是一种池化（pooling）思想，简而言之就是**为了最大化收益，并最小化风险，将资源统一在一起管理**的思想。

Java也提供了它自己实现的线程池模型——`ThreadPoolExecutor`。套用上面池化的想象来说，Java线程池就是为了最大化高并发带来的性能提升，并最小化手动创建线程的风险，将多个线程统一在一起管理的思想。

## 线程池设计思路

线程池的思路和生产者消费者模型是很接近的。
1. 准备一个任务容器
2. **一次性启动10个消费者线程**
3. 刚开始任务容器是空的，所以线程都wait在上面。
4. **直到一个外部线程往这个任务容器中扔了一个“任务”，就会有一个消费者线程被唤醒notify**
5. 这个消费者线程取出“任务”，并且执行这个任务，执行完毕后，**继续等待下一次任务的到来**。
6. 如果短时间内，有较多的任务加入，那么就会有多个线程被唤醒，去执行这些任务。

在整个过程中，都不需要创建新的线程，而是循环使用这些已经存在的线程。

<div align=center><img src=Thread\线程池设计思路.png></div>


## 使用线程池

&emsp;&emsp;实现`java.lang.Runnable`来定义一个任务类，以及如何创建一个线程来运行一个任务`Runnable task = new TaskClass(task); new Thread(task).start();`该方法对单一任务的执行是很方便的，但是由于必须为每个任务创建一个线程，因此对大量的任务而言是不够高效的。为每个任务开始一个新线程可能会限制吞吐最并且造成性能降低。线程池是管理并发执行任务个数的理想方法。

Java提供`Executor`**接口**来执行线程池中的任务，提供`ExecutorService`**接口**来管理和控制任务。`ExecutorService`是`Executor`的子接口。


* `JDK5.0`起提供了线程池相关`API`：`ExecutorService`和`Executors`；
* `ExecutorService`：真正的**线程池接口**。常见子类`ThreadPoolExecutor`：
    1. `void execute(Runnable command)`：**执行任务/命令，没有返回值**，一般用来执行`Runnable`；
    2. `<T>Future<T> submit(Callable<T> task)`：**执行任务，有返回值**，一般用来执行`Callable`；
    3. `void shutdown()`：关闭连接池。
* `Executors`：工具类、线程池的工厂类，用于创建并返回不同类型的线程池。

`Executor`接口（`Execute方法`）**执行线程**，而子接口`ExecutorService`**管理线程**：
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


## 线程池三大方法、七大参数、四种拒绝策略

### 三大方法

```java
ExecutorService threadPool = Executors.newSingleThreadExecutor();// 单个线程
ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建一个固定的线程池的大小
ExecutorService threadPool = Executors.newCachedThreadPool(); // 可伸缩的，遇强则强，遇弱则弱
```

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

### 七大参数

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

Executors大大的简化了我们创建各种类型线程池的方式，为什么还不让使用呢？

其实，只要你打开看看它的静态方法参数就会明白了：传入的`workQueue`是一个边界为`Integer.MAX_VALUE`队列，我们也可以变相的称之为无界队列了，因为**边界太大**了，这么大的等待队列也是非常**消耗内存**的：
```java
public LinkedBlockingQueue() {
    this(Integer.MAX_VALUE);
}
```

三大方法本质都是调用`ThreadPoolExecutor`：
```java
public ThreadPoolExecutor(int corePoolSize,  // 1.核心线程池大小；线程池初始化线程的个数
                          int maximumPoolSize,  // 2.最大核心线程池大小
                          long keepAliveTime,  // 3.超时了没有人调用多出的线程就会释放，最后保持池子里就corePoolSize个线程
                          TimeUnit unit,  // 4.超时单位
                          BlockingQueue<Runnable> workQueue,  // 5.阻塞队列，用来放任务的集合
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

| 序号 | 参数名称        | 参数解释                                                                                                                     | 春运形象说明                                                                                                               |
|------|-----------------|------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| 1    | corePoolSize    | 表示常驻核心线程数，如果大于0，即使本地任务执行完也不会被销毁                                                                | 日常固定的列车数辆（不管是不是春运，都要有固定这些车次运行）                                                               |
| 2    | maximumPoolSize | 表示线程池能够容纳可同时执行的最大线程数（结合`workQueue`）                                                                                     | 春运客流量大，临时加车，加车后，总列车次数不能超过这个最大值，否则就会出现调度不开等问题 （结合`workQueue`）                 |
| 3    | keepAliveTime   | 表示线程池中线程空闲的时间，当空闲时间达到该值时，线程会被销毁，只剩下`corePoolSize`个线程位置                               | 春运压力过后，临时的加车（如果空闲时间超过`keepAliveTime`）就会被撤掉，只保留日常固定的列车车次数量用于日常运营            |
| 4    | unit            | `keepAliveTime`的时间单位，最终都会转换成`纳秒`，因为CPU的执行速度杠杠滴                                                     | `keepAliveTime`的单位，春运以天为计算单位                                                                                  |
| 5    | workQueue       | **当请求的线程数大于`corePoolSize`时，线程进入该阻塞队列**                                                                   | 春运压力异常大，达到`corePoolSize`也不能满足要求，所有乘坐请求都会进入该阻塞队列中排队, 队列满，还有额外请求，就需要加车了 |
| 6    | threadFactory   | 顾名思义，线程工厂，用来生产一组相同任务的线程，同时也可以通过它增加前缀名，虚拟机栈分析时更清晰                             | 比如（北京——上海）就属于该段列车所有前缀，表明列车运输职责                                                                 |
| 7    | handler         | 执行拒绝策略，当`workQueue`达到上限，同时也达到`maximumPoolSize`就要通过这个来处理，比如拒绝，丢弃等，这是一种限流的保护措施 | 当`workQueue`排队也达到队列最大上线，`maximumPoolSize`就要提示无票等拒绝策略了,因为我们不能加车了，当前所有车次已经满负载  |


### ThreadPoolExecutor

《阿里巴巴Java手册》建议：**线程池不允许使用`Executors`去创建，而是通过`ThreadPoolExecutor`的方式**，可以更明确线程池的运行规则，规避资源耗尽的风险。

<div align=center><img src=Thread\ThreadPoolExecutor任务调度流程图.png></div>


Core and maximum pool sizes
A ThreadPoolExecutor will automatically adjust the pool size (see getPoolSize()) according to the bounds set by `corePoolSize` (see getCorePoolSize()) and `maximumPoolSize` (see getMaximumPoolSize()). 

When a new task is submitted in method `execute(Runnable)`, and **fewer than `corePoolSize` threads are running, a new thread is created to handle the request**, even if other worker threads are idle. 

If there are **more than `corePoolSize` but less than `maximumPoolSize` threads running, a new thread will be created only if the queue is full**. 

By setting corePoolSize and maximumPoolSize the same, you create a fixed-size thread pool. By setting maximumPoolSize to an essentially unbounded value such as Integer.MAX_VALUE, you allow the pool to accommodate an arbitrary number of concurrent tasks. Most typically, core and maximum pool sizes are set only upon construction, but they may also be changed dynamically using setCorePoolSize(int) and setMaximumPoolSize(int).

https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html

1. 当池中正在运行的线程数（包括空闲线程数）小于`corePoolSize`时，新建线程执行任务：
   `If fewer than corePoolSize threads are running, the Executor always prefers adding a new thread rather than queuing.`

```java
package Thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));

        // 任务1
        pool.execute(() -> System.out.println("任务一：" + Thread.currentThread().getName()));


        //任务2
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("任务二：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
    }
}
/*
任务二：pool-1-thread-1
任务一：pool-1-thread-2

 */
```
当执行任务一的线程（thread-1）执行完成之后，任务二并没有去复用thread-1而是新建线程（thread-2）去执行任务

2. 当池中正在运行的线程数大于等于`corePoolSize`时，**新插入的任务进入`workQueue`排队**(如果`workQueue`长度允许)，等待空闲线程来执行。
   `If corePoolSize or more threads are running, the Executor always prefers queuing a request rather than adding a new thread.`

```java
package HowJThread.ThreadPools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));

        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("任务一：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(4000);
                System.out.println("任务二：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务3
        pool.execute(() -> System.out.println("任务三：" + Thread.currentThread().getName()));

        pool.shutdown();
    }
}
/*
任务一：pool-1-thread-1
任务三：pool-1-thread-1
任务二：pool-1-thread-2

 */
```
任务二在执行过程中，不会为任务三新建线程，因为**有一个空的队列**，先将其放入队列中，等thread-1执行完任务一，再去执行任务三。此时，`maximumPoolSize=3`这个参数不起作用。

3. **当队列里的任务达到上限**，并且池中正在进行的线程小于`maxinumPoolSize`，对于新加入的任务，新建线程。

```java
package HowJThread.ThreadPools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));

        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("任务一：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务二：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务3
        pool.execute(() -> System.out.println("任务三：" + Thread.currentThread().getName()));

        // 任务4
        pool.execute(() -> System.out.println("任务四：" + Thread.currentThread().getName()));

        pool.shutdown();
    }
}
/*
任务四：pool-1-thread-3
任务三：pool-1-thread-3
任务一：pool-1-thread-1
任务二：pool-1-thread-2

 */
```

任务一、二启动后，**任务三在队列，队列就满了**，由于正在进行的线程数是`2<maximumPoolSize`，只能**新建一个线程**了。然后任务四就进了新线程`thread-3`，任务四结束，队列里的任务四在线程`thread-3`进行处理。

4. 队列里的任务达到上限，并且池中正在运行的线程等于`maximumPoolSize`，对于新加入的任务，执行拒绝策略（线程池默认的策略是抛异常）。

```java
package HowJThread.ThreadPools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));

        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("任务一：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("任务二：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务3
        pool.execute(() -> System.out.println("任务三：" + Thread.currentThread().getName()));

        // 任务4
        pool.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务四：" + Thread.currentThread().getName());
        });

        // 任务5
        pool.execute(() -> System.out.println("任务五：" + Thread.currentThread().getName()));

        pool.shutdown();
    }
}
/*
Exception in thread "main" java.util.concurrent.RejectedExecutionException...
任务四：pool-1-thread-3
任务三：pool-1-thread-3
任务一：pool-1-thread-1
任务二：pool-1-thread-2
 */
```
队列达到上限，线程池达到最大值，故抛出异常。



### 四种拒绝策略

* `new ThreadPoolExecutor.AbortPolicy()` // 银行满了，还有人进来，不处理这个人的，抛出异常
* `new ThreadPoolExecutor.CallerRunsPolicy()` // 哪来的去哪里！
* `new ThreadPoolExecutor.DiscardPolicy()` //队列满了，丢掉任务，不会抛出异常！
* `new ThreadPoolExecutor.DiscardOldestPolicy()` //队列满了，尝试去和最早的竞争，也不会抛出异常！

我们需要经过**调优**的过程来设置**最佳线程参数值**：

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

### 线程池缺点

1. 适用于生存周期较短的的任务，不适用于又长又大的任务。

2. 线程池所有线程都处于多线程单元中，如果想把线程放到单线程单元中，线程池就废掉了。

3. 如果想标识线程的各个状态，比如启动线程，终止线程，那么线程池就不能完成这些工作。

4. 不能对于线程池中任务设置优先级。

5. 对于任意给定的应用程序域，只能允许一个线程池与之对应。


### 创建多少个线程合适

使用多线程就是在正确的场景下通过设置正确个数的线程来最大化程序的运行速度。将这句话翻译到硬件级别就是要充分的利用CPU和I/O。

#### CPU密集型程序

一个完整请求，I/O操作可以在很短时间内完成，CPU还有很多运算要处理，也就是说CPU计算的比例占很大一部分。

假如我们要计算 1+2+…100亿的总和，很明显，这就是一个CPU密集型程序。在【单核】CPU下，如果我们创建4个线程来分段计算，即：线程1计算 [1, 25亿）…… 以此类推线程4计算[75亿, 100亿]。

<div align=center><img src=Thread\CPU密集型.png width=80%></div>

由于是单核CPU，所有线程都在等待CPU时间片。按照理想情况来看，四个线程执行的时间总和与一个线程5独自完成是相等的，实际上我们还忽略了四个线程上下文切换的开销。

所以，单核CPU处理CPU密集型程序，这种情况并不太适合使用多线程。

此时如果在4核CPU下，同样创建四个线程来分段计算，看看会发生什么？

<div align=center><img src=Thread\CPU密集型1.png width=80%></div>

每个线程都有CPU来运行，并不会发生等待CPU时间片的情况，也没有线程切换的开销。理论情况来看效率提升了4倍。

所以，如果是**多核CPU处理CPU密集型程序**，我们完全可以最大化的利用CPU核心数，应用并发编程来提高效率。

**CPU密集型程序创建多少个线程合适？**

对于CPU密集型来说，理论上`线程数量 = CPU 核数（逻辑）`就可以了，但是实际上，**数量一般会设置为`CPU核数（逻辑）+ 1`**。

这样可以确保计算（CPU）密集型的线程恰好在某时因为发生一个页错误或者因其他原因而暂停时，刚好有一个“额外”的线程，可以确保在这种情况下CPU周期不会中断工作。

#### I/O密集型程序

与CPU密集型程序相对的是，一个完整请求，CPU运算操作完成之后还有很多I/O操作要做，也就是说I/O操作占比很大部分。

在进行I/O操作时，CPU是空闲状态，所以我们要最大化的利用CPU，不能让其是空闲状态。

同样在单核CPU的情况下：
<div align=center><img src=Thread\IO密集型.png width=80%></div>

从上图中可以看出，每个线程都执行了相同长度的CPU耗时和I/O耗时，如果你将上面的图多画几个周期，CPU操作耗时固定，**将I/O操作耗时变为CPU耗时的3倍，你会发现，CPU又有空闲了**，这时你就可以新建线程4，来继续最大化的利用CPU。

**I/O密集型程序创建多少个线程合适？**

一个CPU核心的最佳线程数：
`最佳线程数 = (1/CPU利用率) = 1 + (I/O耗时/CPU耗时)`

如果**多个核心**，那么I/O密集型程序的最佳线程数就是：

`最佳线程数 = CPU核心数 * (1/CPU利用率) = CPU核心数 * （1 + （I/O耗时/CPU耗时)）`

综上两种情况我们可以做出这样的总结：

**线程等待时间所占比例越高，需要越多线程；线程CPU时间所占比例越高，需要越少线程。**


即便算出了理论线程数，但实际CPU核数不够，会带来线程上下文切换的开销，所以下一步就需要增加CPU核数，那我们盲目的增加CPU核数就一定能解决问题吗？

阿姆达尔定律(处理器并行运算后效率提升的能力)：假如我们的串行率是5%，那么我们无论采用什么技术，最高也就只能提高20倍的性能。


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

