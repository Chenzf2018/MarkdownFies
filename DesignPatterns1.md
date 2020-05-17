# 设计模式

参考资料：[Java设计模式](http://c.biancheng.net/design_pattern/)

## 创建型模式

创建型模式的主要关注点是<font color=red>“怎样创建对象”</font>，它的主要特点是<font color=red>“将对象的创建与使用分离”</font>。这样可以降低系统的耦合度，使用者不需要关注对象的创建细节，对象的创建由相关的工厂来完成。就像我们去商场购买商品时，不需要知道商品是怎么生产出来一样，因为它们由专门的厂商生产。

创建型模式分为以下几种。
- 单例（Singleton）模式：某个类只能生成一个实例，<font color=red>该类提供了一个全局访问点供外部获取该实例</font>，其拓展是有限多例模式。
- 原型（Prototype）模式：将一个对象作为原型，通过对其进行复制而克隆出多个和原型类似的新实例。
- 工厂方法（FactoryMethod）模式：定义一个用于创建产品的接口，由子类决定生产什么产品。
- 抽象工厂（AbstractFactory）模式：提供一个创建<font color=red>产品族</font>的接口，其每个子类可以生产一系列相关的产品。
- 建造者（Builder）模式：将一个复杂对象分解成多个相对简单的部分，然后根据不同需要分别创建它们，最后构建成该复杂对象。

以上 5 种创建型模式，除了工厂方法模式属于<font color=red>类创建型模式</font>，其他的全部属于<font color=red>对象创建型模式</font>。

### 单例模式

单例（Singleton）模式的定义：指一个类只有一个实例，且该类能自行创建这个实例的一种模式。

例如，<font color=red>Windows中只能打开一个任务管理器，这样可以避免因打开多个任务管理器窗口而造成内存资源的浪费，或出现各个窗口显示内容的不一致等错误</font>。在计算机系统中，还有Windows的回收站、操作系统中的文件系统、多线程中的线程池、显卡的驱动程序对象、打印机的后台处理服务、应用程序的日志对象、数据库的连接池、网站的计数器、Web应用的配置对象、应用程序中的对话框、系统中的缓存等常常被设计成单例。

它通常适用的场景的特点：
- <font color=red>某类只要求生成一个对象的时候</font>：如一个班中的班长、每个人的身份证号等。
- <font color=red>当对象需要被共享的场合</font>：由于单例模式只允许创建一个对象，共享该对象可以节省内存，并加快对象访问速度。如 Web 中的配置对象、数据库的连接池等。
- <font color=red>当某类需要频繁实例化，而创建的对象又频繁被销毁的时候</font>：如多线程的线程池、网络连接池等。

单例模式有3个特点：
- 单例类只有一个实例对象；
- 该单例对象必须由单例类自行创建；
- 单例类对外提供一个访问该单例的全局访问点；

#### 单例模式的结构

通常，<font color=red>普通类的构造函数是公有的，外部类可以通过`new构造函数()`来生成多个实例</font>。但是，如果<font color=red>将类的构造函数设为私有的</font>，外部类就无法调用该构造函数，也就无法生成多个实例。这时<font color=red>该类自身必须定义一个静态私有实例，并向外提供一个静态的公有函数用于创建或获取该静态私有实例</font>。

单例模式的主要结构如下。
- 单例类：包含一个实例且能自行创建这个实例的类。
- 访问类：使用单例的类。

<div align=center><img src=DesignPatterns1/单例模式结构图.png width=70%></div>

#### 单例模式的实现

Singleton模式通常有两种实现形式：

##### 懒汉式单例

该模式的特点是<font color=red>类加载时没有生成单例，只有当第一次调用`getlnstance`方法时才去创建这个单例</font>：

```java
public class LazySingleton
{
    //保证instance在所有线程中同步
    private static volatile LazySingleton instance = null;    
    private LazySingleton(){}    //private 避免类在外部被实例化

    public static synchronized LazySingleton getInstance()
    {
        //getInstance 方法前加同步
        if(instance == null)
        {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

如果编写的是多线程程序，则不要删除代码中的关键字`volatile`和`synchronized`，否则将存在线程非安全的问题。如果不删除这两个关键字就能保证<font color=red>线程安全，但是每次访问时都要同步，会影响性能，且消耗更多的资源</font>，这是懒汉式单例的缺点。

##### 饿汉式单例

该模式的特点是<font color=red>类一旦加载就创建一个单例，保证在调用`getInstance`方法之前单例已经存在了</font>：

```java
public class HungrySingleton
{
    private static final HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance()
    {
        return instance;
    }
}
```

饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以是线程安全的，可以直接用于多线程而不会出现问题。

##### 懒汉式单例模式模拟产生美国总统

在每一届任期内，美国的总统只有一人，所以本实例适合用单例模式实现：

<div align=center><img src=DesignPatterns1/懒汉式单例模式模拟产生美国总统.png width=70%></div>

```java
// President.java

package Singleton2;

public class President
{
    private static volatile President instance = null;    //保证instance在所有线程中同步

    //private避免类在外部被实例化
    private President()
    {
        System.out.println("产生一个总统！");
    }

    public static synchronized President getInstance()
    {
        //在getInstance方法上加同步
        if(instance == null)
        {
            instance = new President();
        }
        else
        {
            System.out.println("已经有一个总统，不能产生新总统！");
        }
        return instance;
    }
    public void getName()
    {
        System.out.println("我是美国总统：特朗普。");
    }
}


// SingletonLazy.java

package Singleton2;

public class SingletonLazy
{
    public static void main(String[] args)
    {
        President zt1 = President.getInstance();
        zt1.getName();    //输出总统的名字

        President zt2 = President.getInstance();
        zt2.getName();    //输出总统的名字

        if(zt1 == zt2)
        {
            System.out.println("他们是同一人！");
        }
        else
        {
            System.out.println("他们不是同一人！");
        }
    }
}
```

运行结果：
```
产生一个总统！
我是美国总统：特朗普。
已经有一个总统，不能产生新总统！
我是美国总统：特朗普。
他们是同一人！
```

##### 用饿汉式单例模式模拟产生猪八戒

同上例类似，猪八戒也只有一个，所以本实例同样适合用单例模式实现。本实例由于要显示猪八戒的图像，所以用到了框架窗体`JFrame`组件，这里的猪八戒类是单例类，可以将其定义成面板`JPanel`的子类，里面包含了标签，用于保存猪八戒的图像，客户窗体可以获得猪八戒对象，并显示它：

<div align=center><img src=DesignPatterns1/用饿汉式单例模式模拟产生猪八戒.png width=70%></div>

```java
// Bajie.java

package Singleton3;

import javax.swing.*;

public class Bajie extends JPanel
{
    private static Bajie instance = new Bajie();

    private Bajie()
    {
        JLabel l1 = new JLabel(new ImageIcon("src/Singleton3/Bajie.jpg"));
        this.add(l1);
    }

    public static Bajie getInstance()
    {
        return instance;
    }
}


// SingletonEager.java
package Singleton3;

import javax.swing.*;
import java.awt.*;

public class SingletonEager
{
    public static void main(String[] args)
    {
        JFrame jf = new JFrame("饿汉单例模式测试");
        jf.setLayout(new GridLayout(1,2));
        Container contentPane = jf.getContentPane();

        Bajie obj1 = Bajie.getInstance();
        contentPane.add(obj1);

        Bajie obj2 = Bajie.getInstance();
        contentPane.add(obj2);

        if(obj1 == obj2)
        {
            System.out.println("他们是同一人！");
        }
        else
        {
            System.out.println("他们不是同一人！");
        }

        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```


#### 单例模式扩展

单例模式可扩展为有限的多例（Multitcm）模式，这种模式可生成有限个实例并保存在`ArmyList`中，客户需要时可随机获取：

<div align=center><img src=DesignPatterns1/有限的多例模式.png width=70%></div>


### 原型模式

当存在<font color=red>大量相同或相似对象的创建问题</font>时，如果用传统的构造函数来创建对象，会比较复杂且耗时耗资源，用原型模式生成对象就很高效，就像孙悟空拔下猴毛轻轻一吹就变出很多孙悟空一样简单。

原型（Prototype）模式的定义如下：<font color=red>用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象</font>。在这里，原型实例指定了要创建的对象的种类。用这种方式创建对象非常高效，根本无须知道对象创建的细节。

原型模式通常适用于以下场景：
- 对象之间相同或相似，即只是个别的几个属性不同的时候。
- 对象的创建过程比较麻烦，但复制比较简单的时候。

#### 原型模式的结构

由于`Java`提供了对象的`clone()`方法，所以用`Java`实现原型模式很简单。

原型模式包含以下主要结构：
- 抽象原型类：规定了具体原型对象必须实现的接口。
- 具体原型类：实现抽象原型类的`clone()`方法，<font color=red>它是可被复制的对象</font>。
- 访问类：使用具体原型类中的`clone()`方法来复制新的对象。

<div align=center><img src=DesignPatterns1/原型模式的结构.png width=70%></div>

#### 原型模式的实现

原型模式的克隆分为`浅克隆`和`深克隆`，`Java`中的`Object`类提供了`浅克隆`的`clone()`方法，具体原型类只要实现`Cloneable`接口就可实现对象的浅克隆，这里的`Cloneable`接口就是抽象原型类。其代码如下：

```java
// Realizetype.java
package Prototype1;

//具体原型类

public class Realizetype implements Cloneable
{
    Realizetype()
    {
        System.out.println("具体原型创建成功！");
    }

    public Object clone() throws CloneNotSupportedException
    {
        System.out.println("具体原型复制成功！");
        return (Realizetype)super.clone();
    }
}


// PrototypeTest.java
package Prototype1;

public class PrototypeTest
{
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Realizetype obj1 = new Realizetype();
        Realizetype obj2 = (Realizetype)obj1.clone();
        System.out.println("obj1==obj2? " + (obj1 == obj2));  // 比较对象地址
        System.out.println("obj1.equals(obj2)? " + (obj1.equals(obj2)));  // 比较对象内容
    }
}
```

##### 复制孙悟空

孙悟空拔下猴毛轻轻一吹就变出很多孙悟空，这实际上是用到了原型模式。这里的孙悟空类`SunWukong`是具体原型类，而`Java`中的`Cloneable`接口是抽象原型类。

由于要显示孙悟空的图像，所以将孙悟空类定义成面板`JPanel`的子类，里面包含了标签，用于保存孙悟空的图像。

另外，重写了`Cloneable`接口的`clone()`方法，用于复制新的孙悟空。访问类可以通过调用孙悟空的`clone()`方法复制多个孙悟空，并在框架窗体`JFrame`中显示。

<div align=center><img src=DesignPatterns1/原型模式复制孙悟空.png width=70%></div>

```java
// SunWukong.java

package Prototype2;

import javax.swing.*;

public class SunWukong extends JPanel implements Cloneable
{
    //private static final long serialVersionUID = 5543049531872119328L;

    public SunWukong()
    {
        JLabel l1=new JLabel(new ImageIcon("src/Prototype2/Wukong.jpg"));
        this.add(l1);
    }

    @Override
    public Object clone()
    {
        SunWukong wukong = null;
        try
        {
            wukong = (SunWukong)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            System.out.println("拷贝悟空失败!");
        }
        return wukong;
    }
}


// PrototypeWukong.java

package Prototype2;

import javax.swing.*;
import java.awt.*;

public class PrototypeWukong
{
    public static void main(String[] args)
    {
        JFrame jf = new JFrame("原型模式测试");
        jf.setLayout(new GridLayout(1,2));
        Container contentPane = jf.getContentPane();

        SunWukong obj1 = new SunWukong();
        contentPane.add(obj1);

        SunWukong obj2 = (SunWukong)obj1.clone();
        contentPane.add(obj2);

        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```

##### 原型模式生成奖状

用原型模式除了可以生成相同的对象，还可以生成相似的对象：

同一学校的“三好学生”奖状除了获奖人姓名不同，其他都相同，属于<font color=red>相似对象的复制</font>，同样可以用原型模式创建，然后再做简单修改就可以了：

<div align=center><img src=DesignPatterns1/原型模式生成奖状.png width=70%></div>

```java
// Citation.java

package Prorotype3;

public class Citation implements Cloneable
{
    private String name;
    private String info;
    private String college;

    Citation(String name, String info, String college)
    {
        this.name = name;
        this.info = info;
        this.college = college;
        System.out.println("奖状创建成功！");
    }

    void setName(String name) { this.name=name; }
    String getName() { return(this.name); }
    void display() { System.out.println(name + info + college); }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        System.out.println("奖状拷贝成功！");
        return (Citation)super.clone();
    }
}


// PrototypeCitation.java

package Prorotype3;

public class PrototypeCitation
{
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Citation obj1=new Citation("张三","同学：在2016学年第一学期中表现优秀，被评为三好学生。","sjtu");
        obj1.display();

        Citation obj2=(Citation) obj1.clone();

        obj2.setName("李四");
        obj2.display();
    }
}
```

#### 原型模式的扩展

原型模式可扩展为`带原型管理器`的原型模式，它在原型模式的基础上增加了一个原型管理器`PrototypeManager`类。该类用`HashMap`保存多个复制的原型，`Client`类可以通过管理器的`get(String id)`方法从中获取复制的原型：

<div align=center><img src=DesignPatterns1/带原型管理器的原型模式的结构图.png width=80%></div>

##### 生成不同图像并计算面积

用带原型管理器的原型模式来生成包含`圆`和`正方形`等图形的原型，并计算其面积。本实例中由于存在不同的图形类，它们计算面积的方法不一样，所以需要用一个原型管理器来管理它们：

<div align=center><img src=DesignPatterns1/带原型管理器的图形生成器.png width=80%></div>

```java
// Shape.java

package Prototype4;

interface Shape extends Cloneable
{
    public Object clone();    //拷贝
    public void countArea();    //计算面积
}


// Circle.java

package Prototype4;

import java.util.Scanner;

public class Circle implements Shape
{
    @Override
    public Object clone()
    {
        Circle circle = null;
        try
        {
            circle = (Circle)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            System.out.println("拷贝圆失败!");
        }
        return circle;
    }

    @Override
    public void countArea()
    {
        int r = 0;
        System.out.print("这是一个圆，请输入圆的半径：");

        Scanner input = new Scanner(System.in);
        r = input.nextInt();

        System.out.println("该圆的面积 = " + Math.PI * r * r + "\n");
    }
}


// Square.java
package Prototype4;

import java.util.Scanner;

public class Square implements Shape
{
    @Override
    public Object clone()
    {
        Square square = null;
        try
        {
            square = (Square)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            System.out.println("拷贝正方形失败!");
        }
        return square;
    }
    public void countArea()
    {
        int length = 0;
        System.out.print("这是一个正方形，请输入它的边长：");

        Scanner input=new Scanner(System.in);
        length = input.nextInt();

        System.out.println("该正方形的面积 = " + length * length + "\n");
    }
}


// PrototypeManager.java

package Prototype4;

import java.util.HashMap;

public class PrototypeManager
{
    private HashMap<String, Shape> ht=new HashMap<String,Shape>();

    public PrototypeManager()
    {
        ht.put("Circle", new Circle());
        ht.put("Square", new Square());
    }

    public void addShape(String key, Shape obj)
    {
        ht.put(key,obj);
    }

    public Shape getShape(String key)
    {
        Shape shape = ht.get(key);
        return (Shape) shape.clone();
    }
}


// PrototypeShape.java

package Prototype4;

public class PrototypeShape
{
    public static void main(String[] args)
    {
        PrototypeManager prototypeManager=new PrototypeManager();

        Shape obj1 = (Circle)prototypeManager.getShape("Circle");
        obj1.countArea();

        Shape obj2 = (Shape)prototypeManager.getShape("Square");
        obj2.countArea();
    }
}
```


### 工厂方法模式

工厂方法（FactoryMethod）模式的定义：<font color=red>定义一个创建产品对象的工厂接口，将产品对象的实际创建工作推迟到具体子工厂类当中</font>。这满足创建型模式中所要求的`创建与使用相分离`的特点。

我们把`被创建的对象`称为`产品`，把`创建产品的对象`称为`工厂`。如果要创建的产品不多，只要一个工厂类就可以完成，这种模式叫`简单工厂模式`，它不属于23种经典设计模式，它的缺点是增加新产品时会违背`开闭原则`(软件实体应当对扩展开放，对修改关闭)。

`工厂方法模式`是对`简单工厂模式`的进一步抽象化，其好处是可以使系统在不修改原来代码的情况下引进新的产品，即满足开闭原则。

工厂方法模式的主要优点有：
- 用户只需要知道具体工厂的名称就可得到所要的产品，无须知道产品的具体创建过程；
- 在系统增加新的产品时只需要添加`具体产品类`和`对应的具体工厂类`，无须对原工厂进行任何修改，满足开闭原则。

其缺点是：每增加一个产品就要增加一个`具体产品类`和一个`对应的具体工厂类`，这增加了系统的复杂度。

工厂方法模式通常适用于以下场景：
- 客户只知道创建产品的工厂名，而不知道具体的产品名。如`TCL`电视工厂、海信电视工厂等。
- 创建对象的任务由多个具体子工厂中的某一个完成，而抽象工厂只提供创建产品的接口。
- 客户不关心创建产品的细节，只关心产品的品牌。

#### 工厂方法模式的结构

工厂方法模式由抽象工厂、具体工厂、抽象产品和具体产品等4个要素构成：
- 抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法`newProduct()`来创建产品。
- 具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
- 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
- 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，<font color=red>由具体工厂来创建，它同具体工厂之间一一对应</font>。

<div align=center><img src=DesignPatterns1/工厂方法模式结构.png width=100%></div>

#### 工厂方法模式设计畜牧场

有很多种类的畜牧场，如养马场用于养马，养牛场用于养牛，所以该实例用工厂方法模式比较适合。

对养马场和养牛场等`具体工厂类`，只要定义一个生成动物的方法`newAnimal()`即可。由于要显示马类和牛类等具体产品类的图像，所以它们的构造函数中用到了`JPanel`、`Labd`和`ImageIcon`等组件，并定义一个`show()`方法来显示它们。

客户端程序通过`对象生成器类ReadXML`读取`XML`配置文件中的数据来决定养马还是养牛。

<div align=center><img src=DesignPatterns1/用工厂模式设计畜牧场.png width=100%></div>

```java

// Animal.java

package Factory_Method1.animals;

// 抽象产品：动物类

public interface Animal
{
    public void show();
}


// Cattle.java

package Factory_Method1.animals;

//具体产品：牛类

import javax.swing.*;
import java.awt.*;

public class Cattle implements Animal
{
    JScrollPane sp;
    JFrame jf = new JFrame("工厂方法模式测试");

    public Cattle()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));
        sp = new JScrollPane(p1);
        contentPane.add(sp,BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("src/Factory_Method1/A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// Horse.java

package Factory_Method1.animals;

// 具体产品：马类

import javax.swing.*;
import java.awt.*;

public class Horse implements Animal
{
    JScrollPane sp;
    JFrame jf = new JFrame("工厂方法模式测试");

    public Horse()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：马"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("src/Factory_Method1/A_Horse.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// AnimalFarm.java

package Factory_Method1.factory;

//抽象工厂：畜牧场

import Factory_Method1.animals.Animal;

public interface AnimalFarm
{
    public Animal newAnimal();
}


// CattleFarm.java

package Factory_Method1.factory;

//具体工厂：养牛场

import Factory_Method1.animals.Animal;
import Factory_Method1.animals.Cattle;

public class CattleFarm implements AnimalFarm
{
    @Override
    public Animal newAnimal()
    {
        System.out.println("新牛出生！");
        return new Cattle();
    }
}


// HorseFarm.java

package Factory_Method1.factory;

//具体工厂：养马场

import Factory_Method1.animals.Animal;
import Factory_Method1.animals.Horse;

public class HorseFarm implements AnimalFarm
{
    @Override
    public Animal newAnimal()
    {
        System.out.println("新马出生！");
        return new Horse();
    }
}


// ReadXML.java

package Factory_Method1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

class ReadXML
{
    //该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
    public static Object getObject()
    {
        try
        {
            //创建文档对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("src/Factory_Method1/config.xml"));

            //获取包含类名的文本节点
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String farmName = classNode.getNodeValue();
            System.out.println(farmName);
            String cName = "Factory_Method1.factory." + classNode.getNodeValue();
            System.out.println("新类名：" + cName);

            //通过类名生成实例对象并将其返回
            Class<?> c = Class.forName(cName);  // cName是"包名.类名"
            // Object object = c.newInstance();
            // Object object = c.getDeclaredConstructor().newInstance();
            return c.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}


// AnimalTest.java

package Factory_Method1;

import Factory_Method1.animals.Animal;
import Factory_Method1.factory.AnimalFarm;

public class AnimalTest
{
    public static void main(String[] args)
    {
        try
        {
            Animal animal;
            AnimalFarm animalFarm;
            animalFarm = (AnimalFarm) ReadXML.getObject();
            // animalFarm = new HorseFarm();
            animal = animalFarm.newAnimal();
            animal.show();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}


// config.xml

<?xml version="1.0" encoding="UTF-8"?>
<config>
	<className>HorseFarm</className>
</config>

```

#### 简单工厂模式

当需要生成的产品不多且不会增加，一个具体工厂类就可以完成任务时，可删除抽象工厂类。这时工厂方法模式将退化到简单工厂模式

<div align=center><img src=DesignPatterns1/简单工厂模式结构图.png width=70%></div>


### 抽象工厂模式

工厂方法模式中考虑的是<font color=red>一类产品的生产</font>，如畜牧场只养动物、电视机厂只生产电视机、计算机软件学院只培养计算机软件专业的学生等。

`同种类`称为`同等级`，也就是说：工厂方法模式只考虑生产同等级的产品，但是在现实生活中许多工厂是综合型的工厂，能生产`多等级（种类）`的产品，如农场里既养动物又种植物，电器厂既生产电视机又生产洗衣机或空调，大学既有软件专业又有生物专业等。

`抽象工厂模式`将考虑`多等级产品的生产`，将同一个具体工厂所生产的位于不同等级的一组产品称为一个`产品族`。

海尔工厂和TCL工厂所生产的电视机与空调对应的关系图：
<div align=center><img src=DesignPatterns1/抽象工厂模式的产品等级与产品族.png width=60%></div>

抽象工厂（AbstractFactory）模式的定义：是一种为访问类提供一个创建一组相关或相互依赖对象的接口，且访问类无须指定所要产品的具体类就能得到同族的不同等级的产品的模式结构。

抽象工厂模式是工厂方法模式的升级版本，<font color=red>工厂方法模式只生产一个等级的产品，而抽象工厂模式可生产多个等级的产品</font>。

使用抽象工厂模式一般要满足以下条件：
- 系统中有多个产品族，每个具体工厂创建同一族但属于不同等级结构的产品。
- 系统一次只可能消费其中某一族产品，即同族的产品一起使用。

抽象工厂模式除了具有工厂方法模式的优点外，其他主要优点如下：
- 可以在类的内部对产品族中相关联的多等级产品共同管理，而不必专门引入多个新的类来进行管理。
- 当增加一个新的产品族时不需要修改原代码，满足开闭原则。

其缺点是：当产品族中需要增加一个新的产品时，所有的工厂类都需要进行修改。

抽象工厂模式通常适用于以下场景：
- 当需要创建的对象是一系列相互关联或相互依赖的产品族时，如电器工厂中的电视机、洗衣机、空调等。
- 系统中有多个产品族，但<font color=red>每次只使用其中的某一族产品。如有人只喜欢穿某一个品牌的衣服和鞋</font>。
- 系统中提供了产品的类库，且所有产品的接口相同，客户端不依赖产品实例的创建细节和内部结构。


#### 抽象工厂的结构

抽象工厂模式同工厂方法模式一样，也是由抽象工厂、具体工厂、抽象产品和具体产品4个要素构成，但抽象工厂中方法个数不同，抽象产品的个数也不同。

抽象工厂模式的主要结构如下：
- 抽象工厂（Abstract Factory）：提供了创建产品的接口，它包含多个创建产品的方法`newProduct()`，可以创建多个不同等级的产品。
- 具体工厂（Concrete Factory）：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
- 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。
- 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间是多对一的关系。

<div align=center><img src=DesignPatterns1/抽象工厂模式结构图.png width=80%></div>


#### 抽象工厂模式设计农场

农场中除了像畜牧场一样可以养动物，还可以培养植物，如养马、养牛、种菜、种水果等，所以必须用抽象工厂模式来实现。

本例用抽象工厂模式来设计两个农场，一个是`韶关农场`用于`养牛`和`种菜`，一个是`上饶农场`用于`养马`和`种水果`，可以在以上两个农场中定义一个生成动物的方法`newAnimal()`和一个培养植物的方法`newPlant()`。

对马类、牛类、蔬菜类和水果类等具体产品类，由于要显示它们的图像，所以它们的构造函数中用到了`JPanel`、`JLabel`和`ImageIcon`等组件，并定义一个`show()`方法来显示它们。

客户端程序通过`对象生成器类ReadXML`读取`XML`配置文件中的数据来决定养什么动物和培养什么植物。

<div align=center><img src=DesignPatterns1/抽象工厂模式设计农场.png width=90%></div>

```java
// Animal.java

package AbstractFactory1.animals;

//抽象产品：动物类

public interface Animal
{
    public void show();
}


// Cattle.java

package AbstractFactory1.animals;

//具体产品：牛类

import javax.swing.*;
import java.awt.*;

public class Cattle implements Animal
{
    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public Cattle()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1=new JLabel(new ImageIcon("src/AbstractFactory1/source/A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// Horse.java

package AbstractFactory1.animals;

//具体产品：马类

import javax.swing.*;
import java.awt.*;

public class Horse implements Animal
{
    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public Horse()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：马"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("src/AbstractFactory1/source/A_Horse.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// Farm.java

package AbstractFactory1.factory;

//抽象工厂：农场类

import AbstractFactory1.animals.Animal;
import AbstractFactory1.plants.Plant;

public interface Farm
{
    public Animal newAnimal();
    public Plant newPlant();
}


// SGfarm.java

package AbstractFactory1.factory;

//具体工厂：韶关农场类

import AbstractFactory1.animals.Animal;
import AbstractFactory1.animals.Cattle;
import AbstractFactory1.plants.Plant;
import AbstractFactory1.plants.Vegetables;

public class SGfarm implements Farm
{
    @Override
    public Animal newAnimal()
    {
        System.out.println("新牛出生！");
        return new Cattle();
    }

    @Override
    public Plant newPlant()
    {
        System.out.println("蔬菜长成！");
        return new Vegetables();
    }
}


// SRfarm.java

package AbstractFactory1.factory;

//具体工厂：上饶农场类

import AbstractFactory1.animals.Animal;
import AbstractFactory1.animals.Horse;
import AbstractFactory1.plants.Fruitage;
import AbstractFactory1.plants.Plant;

public class SRfarm implements Farm
{
    @Override
    public Animal newAnimal()
    {
        System.out.println("新马出生！");
        return new Horse();
    }

    @Override
    public Plant newPlant()
    {
        System.out.println("水果长成！");
        return new Fruitage();
    }
}


// Plant.java

package AbstractFactory1.plants;

//抽象产品：植物类

public interface Plant
{
    public void show();
}


// Fruitage.java

package AbstractFactory1.plants;

//具体产品：水果类

import javax.swing.*;
import java.awt.*;

public class Fruitage implements Plant
{
    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public Fruitage()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("植物：水果"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("src/AbstractFactory1/source/P_Fruitage.jpeg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// Vegetables.java

package AbstractFactory1.plants;

//具体产品：蔬菜类

import javax.swing.*;
import java.awt.*;

public class Vegetables implements Plant
{
    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public Vegetables()
    {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("植物：蔬菜"));
        sp=new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("src/AbstractFactory1/source/P_Vegetables.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用户点击窗口关闭
    }

    @Override
    public void show()
    {
        jf.setVisible(true);
    }
}


// config.xml

<?xml version="1.0" encoding="UTF-8"?>
<config>
	<className>SRfarm</className>
</config>


// ReadXML.java

package AbstractFactory1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class ReadXML
{
    //该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
    public static Object getObject()
    {
        try
        {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("src/AbstractFactory1/source/config.xml"));

            //获取包含类名的文本节点
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            // cName是“包名.类名”
            String cName = "AbstractFactory1.factory." + classNode.getNodeValue();
            System.out.println("新类名：" + cName);

            //通过类名生成实例对象并将其返回
            Class<?> c = Class.forName(cName);
            return c.getDeclaredConstructor().newInstance();
            // Object obj = c.newInstance();
            // return obj;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}


// FarmTest.java

package AbstractFactory1;

import AbstractFactory1.animals.Animal;
import AbstractFactory1.factory.Farm;
import AbstractFactory1.plants.Plant;

public class FarmTest
{
    public static void main(String[] args)
    {
        try
        {
            Farm farm;
            Animal animal;
            Plant plant;
            farm = (Farm) ReadXML.getObject();
            animal = farm.newAnimal();
            plant = farm.newPlant();
            animal.show();
            plant.show();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
```


### 建造者模式

在软件开发过程中有时需要创建一个复杂的对象，<font color=red>这个复杂对象通常由多个子部件按一定的步骤组合而成</font>。例如，计算机是由 OPU、主板、内存、硬盘、显卡、机箱、显示器、键盘、鼠标等部件组装而成的，采购员不可能自己去组装计算机，而是将计算机的配置要求告诉计算机销售公司，计算机销售公司安排技术人员去组装计算机，然后再交给要买计算机的采购员。

生活中这样的例子很多，如游戏中的不同角色，其性别、个性、能力、脸型、体型、服装、发型等特性都有所差异；还有汽车中的方向盘、发动机、车架、轮胎等部件也多种多样；每封电子邮件的发件人、收件人、主题、内容、附件等内容也各不相同。

以上所有这些产品都是由多个部件构成的，各个部件可以灵活选择，但其创建步骤都大同小异。

建造者（Builder）模式的定义：将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示，这样的设计模式被称为建造者模式。它是<font color=red>将一个复杂的对象分解为多个简单的对象，然后一步一步构建而成。它将变与不变相分离，即产品的组成部分是不变的，但每一部分是可以灵活选择的</font>。

该模式的主要优点如下：
- 各个具体的建造者相互独立，有利于系统的扩展。
- 客户端不必知道产品内部组成的细节，便于控制细节风险。

其缺点如下：
- 产品的组成部分必须相同，这限制了其使用范围。
- 如果产品的内部变化复杂，该模式会增加很多的建造者类。

建造者（Builder）模式和工厂模式的关注点不同：<font color=red>建造者模式注重零部件的组装过程，而工厂方法模式更注重零部件的创建过程</font>，但两者可以结合使用。


#### 建造者模式的结构

建造者（Builder）模式的主要结构如下：
- 产品角色（Product）：它是包含多个组成部件的复杂对象，由具体建造者来创建其各个部件。
- 抽象建造者（Builder）：它是一个包含创建产品各个子部件的抽象方法的接口，通常还包含一个返回复杂产品的方法 getResult()。
- 具体建造者(Concrete Builder）：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。
- 指挥者（Director）：它<font color=red>调用建造者对象中的部件构造与装配方法完成复杂对象的创建</font>，在指挥者中不涉及具体产品的信息。

<div align=center><img src=DesignPatterns1/建造者模式的结构.png width=70%></div>


#### 用建造者模式描述客厅装修

客厅装修是一个复杂的过程，它包含墙体的装修、电视机的选择、沙发的购买与布局等。客户把装修要求告诉项目经理，项目经理指挥装修工人一步步装修，最后完成整个客厅的装修与布局，所以本实例用建造者模式实现比较适合。

这里`客厅是产品`，包括`墙`、`电视`和`沙发`等组成部分。`具体装修工人`是`具体建造者`，他们负责装修与墙、电视和沙发的布局。`项目经理`是`指挥者`，他负责指挥装修工人进行装修。

另外，客厅类中提供了`show()`方法，可以将装修效果图显示出来。客户端程序通过对象生成器类`ReadXML`读取`XML`配置文件中的装修方案数据，调用项目经理进行装修。

<div align=center><img src=DesignPatterns1/用建造者模式描述客厅装修.png width=70%></div>

```java

// Parlour.java

package Builder1.product;

//产品：客厅

import javax.swing.*;
import java.awt.*;

public class Parlour
{
    private String wall;    //墙
    private String TV;    //电视
    private String sofa;    //沙发

    public void setWall(String wall)
    {
        this.wall=wall;
    }

    public void setTV(String TV)
    {
        this.TV=TV;
    }

    public void setSofa(String sofa)
    {
        this.sofa=sofa;
    }

    public void show()
    {
        JFrame jf = new JFrame("建造者模式测试");
        Container contentPane = jf.getContentPane();
        JPanel p = new JPanel();
        JScrollPane sp = new JScrollPane(p);
        String parlour = wall + TV + sofa;
        // JLabel l = new JLabel(new ImageIcon("src/"+parlour+".jpg"));
        JLabel l = new JLabel(new ImageIcon("src/Builder1/source/Parlour.jpg"));
        p.setLayout(new GridLayout(1,1));
        p.setBorder(BorderFactory.createTitledBorder("客厅"));
        p.add(l);
        contentPane.add(sp,BorderLayout.CENTER);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


// Builder.java

package Builder1.builder;

//抽象建造者：装修工人

import Builder1.product.Parlour;

public abstract class Builder
{
    //创建产品对象
    protected Parlour product = new Parlour();

    public  abstract void buildWall();
    public  abstract void buildTV();
    public  abstract void buildSofa();

    //返回产品对象
    public  Parlour getResult()
    {
        return  product;
    }
}


// ConcreteBuilder1.java

package Builder1.builder;

//具体建造者：具体装修工人1

public class ConcreteBuilder1 extends Builder
{
    @Override
    public void buildWall()
    {
        product.setWall("Wall1");
    }

    @Override
    public void buildTV()
    {
        product.setTV("TV1");
    }

    @Override
    public void buildSofa()
    {
        product.setSofa("Sofa1");
    }
}


// ConcreteBuilder2.java

package Builder1.builder;

//具体建造者：具体装修工人2

public class ConcreteBuilder2 extends Builder
{
    @Override
    public void buildWall()
    {
        product.setWall("Wall2");
    }

    @Override
    public void buildTV()
    {
        product.setTV("TV2");
    }

    @Override
    public void buildSofa()
    {
        product.setSofa("Sofa2");
    }
}


// ProjectManager.java

package Builder1.director;

//指挥者：项目经理

import Builder1.builder.Builder;
import Builder1.product.Parlour;

public class ProjectManager
{
    private Builder builder;

    public ProjectManager(Builder builder)
    {
        this.builder = builder;
    }
    //产品构建与组装方法
    public Parlour decorate()
    {
        builder.buildWall();
        builder.buildTV();
        builder.buildSofa();
        return builder.getResult();
    }
}


// ReadXML.java

package Builder1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class ReadXML
{
    public static Object getObject()
    {
        try
        {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("src/Builder1/source/config.xml"));

            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String cName = "Builder1.builder." + classNode.getNodeValue();
            System.out.println("新类名：" + cName);

            Class<?> c = Class.forName(cName);
            return c.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}


// ParlourDecorate.java

package Builder1;

import Builder1.builder.Builder;
import Builder1.director.ProjectManager;
import Builder1.product.Parlour;

public class ParlourDecorate
{
    public static void main(String[] args)
    {
        try
        {
            Builder builder;
            builder = (Builder) ReadXML.getObject();
            ProjectManager projectManager = new ProjectManager(builder);
            Parlour parlour = projectManager.decorate();
            parlour.show();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}


// config.xml

<?xml version="1.0" encoding="UTF-8"?>
<config>
    <className>ConcreteBuilder1</className>
</config>
```


## 结构型模式

结构型模式描述如何将类或对象按某种布局组成更大的结构。它分为`类结构型模式`和`对象结构型模式`，前者采用`继承`机制来组织接口和类，后者釆用`组合或聚合`来组合对象。

由于组合关系或聚合关系比继承关系耦合度低，满足“合成复用原则”，所以对象结构型模式比类结构型模式具有更大的灵活性。

结构型模式分为以下 7 种：
- 代理（Proxy）模式：为某对象提供一种代理以控制对该对象的访问。即客户端通过代理间接地访问该对象，从而限制、增强或修改该对象的一些特性。
- 适配器（Adapter）模式：将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
- 桥接（Bridge）模式：将抽象与实现分离，使它们可以独立变化。它是用组合关系代替继承关系来实现的，从而降低了抽象和实现这两个可变维度的耦合度。
- 装饰（Decorator）模式：动态地给对象增加一些职责，即增加其额外的功能。
- 外观（Facade）模式：为多个复杂的子系统提供一个一致的接口，使这些子系统更加容易被访问。
- 享元（Flyweight）模式：运用共享技术来有效地支持大量细粒度对象的复用。
- 组合（Composite）模式：将对象组合成树状层次结构，使用户对单个对象和组合对象具有一致的访问性。

以上7种结构型模式，除了`适配器模式`分为`类结构型模式`和`对象结构型模式`两种，其他的全部属于`对象结构型模式`。


### 代理模式

在有些情况下，一个客户不能或者不想直接访问另一个对象，这时需要找一个中介帮忙完成某项任务，这个中介就是代理对象。例如，购买火车票不一定要去火车站买，可以通过`12306`网站或者去火车票代售点买。

在软件设计中，使用代理模式的例子也很多，例如，要访问的远程对象比较大（如视频或大图像等），其下载要花很多时间。还有因为安全原因需要屏蔽客户端直接访问真实对象，如某单位的内部数据库等。

代理模式的定义：由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时，<font color=red>访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介</font>。

代理模式的主要优点有：
- 代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用；
- 代理对象可以扩展目标对象的功能；
- 代理模式能将客户端与目标对象分离，在一定程度上降低了系统的耦合度；

其主要缺点是：
- 在客户端和目标对象之间增加一个代理对象，会造成请求处理速度变慢；
- 增加了系统的复杂度；

代理模式应用场景：
- 远程代理，这种方式通常是为了隐藏目标对象存在于不同地址空间的事实，方便客户端访问。例如，用户申请某些网盘空间时，会在用户的文件系统中建立一个虚拟的硬盘，用户访问虚拟硬盘时实际访问的是网盘空间。
- 虚拟代理，这种方式通常用于要创建的目标对象开销很大时。例如，下载一幅很大的图像需要很长时间，因某种计算比较复杂而短时间无法完成，这时可以先用小比例的虚拟代理替换真实的对象，消除用户对服务器慢的感觉。
- 安全代理，这种方式通常用于控制不同种类客户对真实对象的访问权限。
- 智能指引，主要用于调用目标对象时，代理附加一些额外的处理功能。例如，增加计算真实对象的引用次数的功能，这样当该对象没有被引用时，就可以自动释放它。
- 延迟加载，指为了提高系统的性能，延迟对目标的加载。例如，Hibernate 中就存在属性的延迟加载和关联表的延时加载。

#### 代理模式的结构

代理模式的结构比较简单，主要是<font color=red>通过定义一个继承抽象主题的代理来包含真实主题，从而实现对真实主题的访问</font>：

代理模式的主要结构如下：
- 抽象主题（Subject）类：通过接口或抽象类声明真实主题和代理对象实现的业务方法。
- 真实主题（Real Subject）类：实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。
- 代理（Proxy）类：提供了与真实主题相同的接口，<font color=red>其内部含有对真实主题的引用，它可以访问、控制或扩展真实主题的功能</font>。

<div align=center><img src=DesignPatterns1/代理模式的结构图.png width=80%></div>


#### 代理模式实现特产代理公司

本实例中的`婺源特产公司`经营许多婺源特产，它是`真实主题`，提供了显示特产的`display()`方法，可以用窗体程序实现。而`韶关天街e角公司`是`婺源特产公司`特产的`代理`，通过调用婺源特产公司的`display()`方法显示代理产品，当然它可以增加一些额外的处理，如`包裝`或`加价`等。客户可通过`天街e角`代理公司间接访问`婺源特产公司`的产品：

<div align=center><img src=DesignPatterns1/代理模式实现特产代理公司.png width=80%></div>

```java

// Specialty.java

package Proxy;

//抽象主题：特产

public interface Specialty
{
    void display();
}


// WYSpecialty.java

package Proxy;

//真实主题：婺源特产

import javax.swing.*;
import java.awt.*;

public class WYSpecialty extends JFrame implements Specialty
{
    public WYSpecialty()
    {
        super("韶关代理婺源特产测试");
        this.setLayout(new GridLayout(1,1));
        JLabel l1 = new JLabel(new ImageIcon("src/Proxy/Specialty.png"));
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void display()
    {
        this.setVisible(true);
    }
}


// SGProxy.java

package Proxy;

//代理：韶关代理

public class SGProxy implements Specialty
{
    private WYSpecialty realSubject = new WYSpecialty();

    @Override
    public void display()
    {
        preRequest();
        realSubject.display();
        postRequest();
    }


    public void preRequest()
    {
        System.out.println("韶关代理婺源特产（额外操作）：加价");
    }


    public void postRequest()
    {
        System.out.println("韶关代理婺源特产（额外操作）：包装。");
    }
}


// WYSpecialtyProxy.java

package Proxy;

public class WYSpecialtyProxy
{
    public static void main(String[] args)
    {
        SGProxy proxy = new SGProxy();
        proxy.display();
    }
}
```

#### 代理模式的扩展

在前面介绍的代理模式中，代理类中包含了对真实主题的引用，这种方式存在两个缺点：
- 真实主题与代理主题一一对应，增加真实主题也要增加代理。
- 设计代理以前真实主题必须事先存在，不太灵活。

采用动态代理模式可以解决以上问题，如`Spring AOP`。



### 适配器模式

在现实生活中，经常出现两个对象因接口不兼容而不能在一起工作的实例，这时需要第三者进行适配。例如，讲中文的人同讲英文的人对话时需要一个翻译，用直流电的笔记本电脑接交流电源时需要一个电源适配器，用计算机访问照相机的SD内存卡时需要一个读卡器等。

在软件设计中也可能出现：需要开发的具有某种业务功能的组件在现有的组件库中已经存在，但它们与当前系统的接口规范不兼容，如果重新开发这些组件成本又很高，这时用适配器模式能很好地解决这些问题。

适配器模式（Adapter）的定义如下：<font color=red>将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作</font>。适配器模式分为`类结构型模式`和`对象结构型模式`两种，前者类之间的耦合度比后者高，且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。

该模式的主要优点如下：
- 客户端通过适配器可以透明地调用目标接口。
- 复用了现存的类，程序员不需要修改原有代码而重用现有的适配者类。
- 将目标类和适配者类解耦，解决了目标类和适配者类接口不一致的问题。

其缺点是：对类适配器来说，更换适配器的实现过程比较复杂。

适配器模式（Adapter）通常适用于以下场景：
- 以前开发的系统存在满足新系统功能需求的类，但其接口同新系统的接口不一致。
- 使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。


#### 适配器模式的结构

类适配器模式可采用多重继承方式实现，Java不支持多继承，但可以定义一个适配器类来实现当前系统的业务接口，同时又继承现有组件库中已经存在的组件。

对象适配器模式可釆用将现有组件库中已经实现的组件引入适配器类中，该类同时实现当前系统的业务接口。

适配器模式（Adapter）包含以下主要结构：
- 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
- 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
- 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，<font color=red>把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者</font>。

类适配器模式的结构图：
<div align=center><img src=DesignPatterns1/类适配器模式的结构图.png width=80%></div>

```java
// Target.java

package Adapter.ClassAdapter;

//目标接口

public interface Target
{
    public void request();
}


// Adaptee.java

package Adapter.ClassAdapter;

//适配者接口

public class Adaptee
{
    public void specificRequest()
    {
        System.out.println("适配者中的业务代码被调用！");
    }
}


// ClassAdapter.java

package Adapter.ClassAdapter;

//类适配器类

public class ClassAdapter extends Adaptee implements Target
{
    @Override
    public void request()
    {
        specificRequest();
    }
}


// ClassAdapterTest.java

package Adapter.ClassAdapter;

//客户端代码

public class ClassAdapterTest
{
    public static void main(String[] args)
    {
        System.out.println("类适配器模式测试：");
        Target target = new ClassAdapter();
        target.request();
    }
}
```


对象适配器模式的结构图：
<div align=center><img src=DesignPatterns1/对象适配器模式的结构图.png width=80%></div>

```java

// ObjectAdapter.java

package Adapter.ObjectAdapter;

//对象适配器类

public class ObjectAdapter implements Target
{
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee)
    {
        this.adaptee = adaptee;
    }

    @Override
    public void request()
    {
        adaptee.specificRequest();
    }
}


// ObjectAdapterTest.java

package Adapter.ObjectAdapter;

//客户端代码

public class ObjectAdapterTest
{
    public static void main(String[] args)
    {
        System.out.println("对象适配器模式测试：");
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request();
    }
}
```
对象适配器模式中的“目标接口”和“适配者类”的代码同类适配器模式一样，只要修改适配器类和客户端的代码即可。


#### 用适配器模式（Adapter）模拟新能源汽车的发动机

新能源汽车的发动机有电能发动机（Electric Motor）和光能发动机（Optical Motor）等，各种发动机的驱动方法不同，例如，电能发动机的驱动方法`electricDrive()`是用电能驱动，而光能发动机的驱动方法`opticalDrive()`是用光能驱动，它们是适配器模式中被访问的适配者。

<font color=red>客户端希望用统一的发动机驱动方法`drive()`访问这两种发动机，所以必须定义一个统一的目标接口`Motor`，然后再定义电能适配器（Electric Adapter）和光能适配器（Optical Adapter）去适配这两种发动机</font>。

我们把客户端想访问的新能源发动机的适配器的名称放在`XML`配置文件中，客户端可以通过对象生成器类`ReadXML`去读取。这样，客户端就可以通过`Motor`接口随便使用任意一种新能源发动机去驱动汽车。

<div align=center><img src=DesignPatterns1/发动机适配器的结构图.png width=80%></div>

```java

// Motor.java

package Adapter1.target;

//目标：发动机

public interface Motor
{
    public void drive();
}


// ElectricMotor.java

package Adapter1.adaptee;

//适配者1：电能发动机

public class ElectricMotor
{
    public void electricDrive()
    {
        System.out.println("电能发动机驱动汽车！");
    }
}


// OpticalMotor.java

package Adapter1.adaptee;

//适配者2：光能发动机

public class OpticalMotor
{
    public void opticalDrive()
    {
        System.out.println("光能发动机驱动汽车！");
    }
}


// ElectricAdapter.java

package Adapter1.adapter;

//电能适配器

import Adapter1.adaptee.ElectricMotor;
import Adapter1.target.Motor;

public class ElectricAdapter implements Motor
{
    private ElectricMotor electricMotor;

    public ElectricAdapter()
    {
        electricMotor = new ElectricMotor();
    }

    @Override
    public void drive()
    {
        electricMotor.electricDrive();
    }
}


// OpticalAdapter.java

package Adapter1.adapter;

//光能适配器

import Adapter1.adaptee.OpticalMotor;
import Adapter1.target.Motor;

public class OpticalAdapter implements Motor
{
    private OpticalMotor opticalMotor;

    public OpticalAdapter()
    {
        opticalMotor = new OpticalMotor();
    }

    @Override
    public void drive()
    {
        opticalMotor.opticalDrive();
    }
}


// ReadXML.java

package Adapter1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class ReadXML
{
    public static Object getObject()
    {
        try
        {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("src/Adapter1/config.xml"));

            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String cName = "Adapter1.adapter." + classNode.getNodeValue();

            Class<?> c = Class.forName(cName);
            return c.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}


// Client.java

package Adapter1;

import Adapter1.target.Motor;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("适配器模式测试：");
        Motor motor = (Motor) ReadXML.getObject();
        motor.drive();
    }
}
```

#### 双向适配器模式

适配器模式（Adapter）可扩展为双向适配器模式，<font color=red>双向适配器类既可以把适配者接口转换成目标接口，也可以把目标接口转换成适配者接口</font>，其结构图如图所示：

<div align=center><img src=DesignPatterns1/双向适配器模式结构图.png width=80%></div>

```java

// TwoWayTarget.java

package Adapter2;

//目标接口

public interface TwoWayTarget
{
    public void request();
}


// TargetRealize.java

package Adapter2;

//目标实现

public class TargetRealize implements TwoWayTarget
{
    @Override
    public void request()
    {
        System.out.println("目标代码被调用！");
    }
}


// TwoWayAdaptee.java

package Adapter2;

//适配者接口

public interface TwoWayAdaptee
{
    public void specificRequest();
}


// AdapteeRealize.java

package Adapter2;

//适配者实现

public class AdapteeRealize implements TwoWayAdaptee
{
    @Override
    public void specificRequest()
    {
        System.out.println("适配者代码被调用！");
    }
}


// TwoWayAdapter.java

package Adapter2;

//双向适配器

public class TwoWayAdapter implements TwoWayTarget, TwoWayAdaptee
{
    private TwoWayTarget target;
    private TwoWayAdaptee adaptee;

    public TwoWayAdapter(TwoWayTarget target)
    {
        this.target=target;
    }

    public TwoWayAdapter(TwoWayAdaptee adaptee)
    {
        this.adaptee=adaptee;
    }

    @Override
    public void request()
    {
        adaptee.specificRequest();
    }

    @Override
    public void specificRequest()
    {
        target.request();
    }
}


// Client.java

package Adapter2;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("目标通过双向适配器访问适配者：");
        TwoWayAdaptee adaptee = new AdapteeRealize();
        TwoWayTarget target = new TwoWayAdapter(adaptee);
        target.request();

        System.out.println("-------------------");
        System.out.println("适配者通过双向适配器访问目标：");
        target = new TargetRealize();
        adaptee = new TwoWayAdapter(target);
        adaptee.specificRequest();
    }
}
```


### 桥接模式

在现实生活中，<font color=red>某些类具有两个或多个维度的变化</font>，如图形既可按形状分，又可按颜色分。如何设计类似于Photoshop这样的软件，能画不同形状和不同颜色的图形呢？如果用继承方式，m种形状和n种颜色的图形就有m*n种，不但对应的子类很多，而且扩展困难。

这样的例子还有很多，如不同颜色和字体的文字、不同品牌和功率的汽车、不同性别和职业的男女、支持不同平台和不同文件格式的媒体播放器等。如果用桥接模式就能很好地解决这些问题。

桥接（Bridge）模式的定义：将抽象与实现分离，使它们可以独立变化。它是<font color=red>用组合关系代替继承关系来实现</font>，从而降低了抽象和实现这两个可变维度的耦合度。

桥接（Bridge）模式的优点是：
- 由于抽象与实现分离，所以扩展能力强；
- 其实现细节对客户透明。

缺点是：由于聚合关系建立在抽象层，要求开发者针对抽象化进行设计与编程，这增加了系统的理解与设计难度。

桥接模式通常适用于以下场景：
- 当一个类存在两个独立变化的维度，且这两个维度都需要进行扩展时。
- 当一个系统不希望使用继承或因为多层次继承导致系统类的个数急剧增加时。
- 当一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性时。

#### 桥接模式的结构

可以将抽象化部分与实现化部分分开，取消二者的继承关系，改用组合关系。

桥接（Bridge）模式包含以下主要部分：
- 抽象化（Abstraction）角色：定义抽象类，并包含一个对实现化对象的引用。
- 扩展抽象化（Refined Abstraction）角色：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
- 实现化（Implementor）角色：定义实现化角色的接口，供扩展抽象化角色调用。
具体实现化（Concrete Implementor）角色：给出实现化角色接口的具体实现。

<div align=center><img src=DesignPatterns1/桥接模式的结构图.png width=90%></div>

```java

// Implementor.java

package Bridge;

// 实现化（Implementor）角色：定义实现化角色的接口，供扩展抽象化角色调用

public interface Implementor
{
    public void OperationImpl();
}


// ConcreteImplementorA.java

package Bridge;

//具体实现化角色

public class ConcreteImplementorA implements Implementor
{
    @Override
    public void OperationImpl()
    {
        System.out.println("具体实现化(Concrete Implementor)角色被访问" );
    }
}


// Abstraction.java

package Bridge;

//抽象化（Abstraction）角色：定义抽象类，并包含一个对实现化对象的引用。

public abstract class Abstraction
{
    protected Implementor implementor;

    protected Abstraction(Implementor implementor)
    {
        this.implementor = implementor;
    }

    public abstract void Operation();
}


// RefinedAbstraction.java

package Bridge;

public class RefinedAbstraction extends Abstraction
{
    protected RefinedAbstraction(Implementor implementor)
    {
        super(implementor);
    }

    @Override
    public void Operation()
    {
        System.out.println("扩展抽象化(Refined Abstraction)角色被访问" );
        implementor.OperationImpl();
    }
}

// Client.java

package Bridge;

public class Client
{
    public static void main(String[] args)
    {
        Implementor implementor = new ConcreteImplementorA();
        Abstraction abstraction = new RefinedAbstraction(implementor);
        abstraction.Operation();
    }
}
```

#### 用桥接模式模拟女士皮包的选购

女士皮包有很多种，可以按用途分、按皮质分、按品牌分、按颜色分、按大小分等，存在<font color=red>多个维度的变化</font>，所以采用桥接模式来实现女士皮包的选购比较合适。

本实例按用途分可选钱包（Wallet）和挎包（HandBag），按颜色分可选黄色（Yellow）和红色（Red）。可以按两个维度定义为颜色类和包类。

`颜色类（Color）`是一个维度，定义为`实现化角色`，它有两个`具体实现化角色`：黄色和红色，通过`getColor()`方法可以选择颜色；包类（Bag）是另一个维度，定义为`抽象化角色`，它有两个`扩展抽象化角色`：挎包和钱包，它包含了颜色类对象，通过`getName()`方法可以选择相关颜色的挎包和钱包。

客户类通过`ReadXML`类从`XML`配置文件中获取包信息，并把选到的产品通过窗体显示出现。

<div align=center><img src=DesignPatterns1/用桥接模式模拟女士皮包的选购.png width=90%></div>

```java

// Color.java

package Bridge1.implementor;

//实现化角色：颜色

public interface Color
{
    public String getColor();
}


// Red.java

package Bridge1.concreteImplementor;

//具体实现化角色：红色

import Bridge1.implementor.Color;

public class Red implements Color
{
    public String getColor()
    {
        return "red";
    }
}


// Yellow.java

package Bridge1.concreteImplementor;

//具体实现化角色：黄色

import Bridge1.implementor.Color;

public class Yellow implements Color
{
    @Override
    public String getColor()
    {
        return "yellow";
    }
}


// Bag.java

package Bridge1.abstraction;

//抽象化角色：包

import Bridge1.implementor.Color;

public abstract class Bag
{
    protected Color color;

    public void setColor(Color color)
    {
        this.color = color;
    }

    public abstract String getName();
}


// HandBag.java

package Bridge1.refinedAbstraction;

//扩展抽象化角色：挎包

import Bridge1.abstraction.Bag;

public class HandBag extends Bag
{
    @Override
    public String getName()
    {
        return color.getColor() + "HandBag";
    }
}


// Wallet.java

package Bridge1.refinedAbstraction;

//扩展抽象化角色：钱包

import Bridge1.abstraction.Bag;

public class Wallet extends Bag
{
    @Override
    public String getName()
    {
        return color.getColor() + "Wallet";
    }
}


// ReadXML.java

package Bridge1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

public class ReadXML
{
    public static Object getObject(String args)
    {
        try
        {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File("src/Bridge1/source/config.xml"));

            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = null;
            // String cName;
            String cName = "";
            if(args.equals("color"))
            {
                classNode = nl.item(0).getFirstChild();
                //String cName="Bridge1.concreteImplementor." + classNode.getNodeValue();
                cName="Bridge1.concreteImplementor." + classNode.getNodeValue();
            }
            else if(args.equals("bag"))
            {
                classNode = nl.item(1).getFirstChild();
                //String cName="Bridge1.refinedAbstraction." + classNode.getNodeValue();
                cName="Bridge1.refinedAbstraction." + classNode.getNodeValue();
            }
            System.out.println(cName);
            Class<?> c = Class.forName(cName);
            return c.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}


// Client.java

package Bridge1;

import Bridge1.abstraction.Bag;
import Bridge1.implementor.Color;

import javax.swing.*;
import java.awt.*;

public class Client
{
    public static void main(String[] args)
    {
        Color color;
        Bag bag;

        color = (Color)ReadXML.getObject("color");
        bag = (Bag)ReadXML.getObject("bag");
        bag.setColor(color);
        String name = bag.getName();
        show(name);
    }

    public static void show(String name)
    {
        JFrame jf = new JFrame("桥接模式测试");
        Container contentPane = jf.getContentPane();
        JPanel p = new JPanel();
        JLabel l = new JLabel(new ImageIcon("src/Bridge1/source/" + name + ".jpg"));
        p.setLayout(new GridLayout(1,1));
        p.setBorder(BorderFactory.createTitledBorder("女士皮包"));
        p.add(l);
        contentPane.add(p, BorderLayout.CENTER);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```


### 装饰模式

在现实生活中，常常需要对现有产品增加新的功能或美化其外观，如房子装修、相片加相框等。在软件开发过程中，有时想用一些现存的组件。这些组件可能只是完成了一些核心功能。但<font color=red>在不改变其结构的情况下，可以动态地扩展其功能</font>。所有这些都可以釆用装饰模式来实现。

装饰（Decorator）模式的定义：指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式，它属于对象结构型模式。

装饰（Decorator）模式的主要优点有：
- 采用装饰模式扩展对象的功能比采用继承方式更加灵活。
- 可以设计出多个不同的具体装饰类，创造出多个不同行为的组合。

其主要缺点是：装饰模式增加了许多子类，如果过度使用会使程序变得很复杂。

装饰模式通常在以下几种情况使用：
- 当需要给一个现有类添加附加职责，而又不能采用生成子类的方法进行扩充时。例如，该类被隐藏或者该类是终极类或者采用继承方式会产生大量的子类。
- 当需要通过对现有的一组基本功能进行排列组合而产生非常多的功能时，采用继承关系很难实现，而采用装饰模式却很好实现。
- 当对象的功能要求可以动态地添加，也可以再动态地撤销时。

装饰模式在`Java`语言中的最著名的应用莫过于`Java I/O`标准库的设计了。例如，`InputStream`的子类`FilterInputStream`，`OutputStream`的子类`FilterOutputStream`，`Reader`的子类`BufferedReader`以及`FilterReader`，还有`Writer`的子类`BufferedWriter`、`FilterWriter`以及`PrintWriter`等，它们都是抽象装饰类。

下面代码是为`FileReader`增加缓冲区而采用的装饰类`BufferedReader`的例子：
```java
BufferedReader in = new BufferedReader(new FileReader("filename.txtn));
String s = in.readLine();
```


#### 装饰模式的结构

通常情况下，扩展一个类的功能会使用继承方式来实现。但<font color=red>继承具有静态特征，耦合度高</font>，并且随着扩展功能的增多，子类会很膨胀。如果使用组合关系来创建一个包装对象（即装饰对象）来包裹真实对象，并在保持真实对象的类结构不变的前提下，为其提供额外的功能，这就是装饰模式的目标。

装饰模式主要包含以下部分：
- 抽象构件（Component）角色：定义一个抽象接口以规范准备接收附加责任的对象。
- 具体构件（Concrete Component）角色：实现抽象构件，通过装饰角色为其添加一些职责。
- 抽象装饰（Decorator）角色：继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
- 具体装饰（Concrete Decorator）角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。

<div align=center><img src=DesignPatterns1/装饰模式的结构图.png width=80%></div>

```java

// Component.java

package Decorator;

//抽象构件角色

public interface Component
{
    public void operation();
}


// ConcreteComponent.java

package Decorator;

//具体构件角色

public class ConcreteComponent implements Component
{
    public ConcreteComponent()
    {
        System.out.println("创建具体构件角色");
    }

    @Override
    public void operation()
    {
        System.out.println("调用具体构件角色的方法operation()");
    }
}


// Decorator.java

package Decorator;

public class Decorator implements Component
{
    private Component component;

    public Decorator(Component component)
    {
        this.component = component;
    }

    @Override
    public void operation()
    {
        component.operation();
    }
}


// ConcreteDecorator.java

package Decorator;

//具体装饰角色

public class ConcreteDecorator extends Decorator
{
    public ConcreteDecorator(Component component)
    {
        super(component);
    }

    @Override
    public void operation()
    {
        super.operation();
        addedFunction();
    }

    public void addedFunction()
    {
        System.out.println("为具体构件角色增加额外的功能addedFunction()");
    }
}


// Client.java

package Decorator;

public class Client
{
    public static void main(String[] args)
    {
        Component component = new ConcreteComponent();
        component.operation();

        System.out.println("---------------------------------");
        Component decorator = new ConcreteDecorator(component);
        decorator.operation();
    }
}
```

运行结果：
```
创建具体构件角色
调用具体构件角色的方法operation()
---------------------------------
调用具体构件角色的方法operation()
为具体构件角色增加额外的功能addedFunction()
```

#### 用装饰模式实现游戏角色的变身

在《恶魔战士》中，游戏角色“莫莉卡·安斯兰”的原身是一个可爱少女，但当她变身时，会变成头顶及背部延伸出蝙蝠状飞翼的女妖，当然她还可以变为穿着漂亮外衣的少女。这些都可用装饰模式来实现，在本实例中的“莫莉卡”原身有`setImage(String t)`方法决定其显示方式，而其变身“蝙蝠状女妖”和“着装少女”可以用`setChanger()`方法来改变其外观，原身与变身后的效果用`display()`方法来显示。

<div align=center><img src=DesignPatterns1/用装饰模式实现游戏角色的变身.png width=80%></div>

```java

// Morrigan.java

package Decorator1.Component;

//抽象构件角色：莫莉卡

public interface Morrigan
{
    public void display();
}


// Original.java

package Decorator1.ConcreteComponent;

//具体构件角色：原身

import Decorator1.Component.Morrigan;

import javax.swing.*;
import java.awt.*;

public class Original extends JFrame implements Morrigan
{
    private String imageName = "Morrigan0.jpg";

    public Original()
    {
        super("《恶魔战士》中的莫莉卡·安斯兰");
    }
    public void setImage(String imageName)
    {
        this.imageName = imageName;
    }

    @Override
    public void display()
    {
        this.setLayout(new FlowLayout());
        JLabel l1 = new JLabel(new ImageIcon("src/Decorator1/source/" + imageName));
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}


// Changer.java

package Decorator1.Decorator;

//抽象装饰角色：变形

import Decorator1.Component.Morrigan;

public class Changer implements Morrigan
{
    public Morrigan morrigan;

    public Changer(Morrigan morrigan)
    {
        this.morrigan = morrigan;
    }

    @Override
    public void display()
    {
        morrigan.display();
    }
}


// Succubus.java

package Decorator1.ConcreteDecorator;

//具体装饰角色：女妖

import Decorator1.Component.Morrigan;
import Decorator1.ConcreteComponent.Original;
import Decorator1.Decorator.Changer;

public class Succubus extends Changer
{
    public Succubus(Morrigan morrigan)
    {
        super(morrigan);
    }

    @Override
    public void display()
    {
        setChanger();
        super.display();
    }

    public void setChanger()
    {
        ((Original) super.morrigan).setImage("Morrigan1.jpg");
    }
}


// Girl.java

package Decorator1.ConcreteDecorator;

//具体装饰角色：少女

import Decorator1.Component.Morrigan;
import Decorator1.ConcreteComponent.Original;
import Decorator1.Decorator.Changer;

public class Girl extends Changer
{
    public Girl(Morrigan morrigan)
    {
        super(morrigan);
    }

    @Override
    public void display()
    {
        setChanger();
        super.display();
    }

    public void setChanger()
    {
        ((Original) super.morrigan).setImage("Morrigan2.jpg");
    }
}


// Client.java

package Decorator1;

import Decorator1.Component.Morrigan;
import Decorator1.ConcreteComponent.Original;
import Decorator1.ConcreteDecorator.Girl;
import Decorator1.ConcreteDecorator.Succubus;

public class Client
{
    public static void main(String[] args)
    {
        Morrigan morrigan0 = new Original();
        morrigan0.display();

        Morrigan morrigan1 = new Succubus(morrigan0);
        morrigan1.display();

        Morrigan morrigan2 = new Girl(morrigan0);
        morrigan2.display();
    }
}
```


### 外观模式

在现实生活中，常常存在办事较复杂的例子，<font color=red>如办房产证或注册一家公司，有时要同多个部门联系，这时要是有一个综合部门能解决一切手续问题就好了</font>。

软件设计也是这样，当一个系统的功能越来越强，子系统会越来越多，客户对系统的访问也变得越来越复杂。这时如果系统内部发生改变，客户端也要跟着改变，这违背了“开闭原则”，也违背了“迪米特法则”，所以有必要为多个子系统提供一个统一的接口，从而降低系统的耦合度，这就是外观模式的目标。

<div align=center><img src=DesignPatterns1/外观模式.png width=70%></div>

外观（Facade）模式的定义：是一种<font color=red>通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问</font>的模式。该模式对外有一个统一接口，外部应用程序不用关心内部子系统的具体的细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。

外观（Facade）模式是“迪米特法则”的典型应用，它有以下主要优点：
- 降低了子系统与客户端之间的耦合度，使得子系统的变化不会影响调用它的客户类。
- 对客户屏蔽了子系统组件，减少了客户处理的对象数目，并使得子系统使用起来更加容易。
- 降低了大型软件系统中的编译依赖性，简化了系统在不同平台之间的移植过程，因为编译一个子系统不会影响其他的子系统，也不会影响外观对象。

外观（Facade）模式的主要缺点如下：
- 不能很好地限制客户使用子系统类。
- 增加新的子系统可能需要修改外观类或客户端的源代码，违背了“开闭原则”。

通常在以下情况下可以考虑使用外观模式：
- 对分层结构系统构建时，使用外观模式定义子系统中每层的入口点可以简化子系统之间的依赖关系。
- 当一个复杂系统的子系统很多时，外观模式可以为系统设计一个简单的接口供外界访问。
- 当客户端与多个子系统之间存在很大的联系时，引入外观模式可将它们分离，从而提高子系统的独立性和可移植性。


#### 外观模式的结构

外观（Facade）模式的结构比较简单，主要是定义了一个高层接口。它包含了对各个子系统的引用，客户端可以通过它访问各个子系统的功能。现在来分析其基本结构和实现方法。

外观（Facade）模式包含以下主要部分：
- 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
- 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。
- 客户（Client）角色：通过一个外观角色访问各个子系统的功能。

<div align=center><img src=DesignPatterns1/外观模式的结构.png width=70%></div>

```java

// SubSystem01.java

package Facade;

public class SubSystem01
{
    public  void method1()
    {
        System.out.println("子系统01的method1()被调用！");
    }
}


// SubSystem02.java

package Facade;

public class SubSystem02
{
    public  void method2()
    {
        System.out.println("子系统02的method2()被调用！");
    }
}


// Facade.java

package Facade;

//外观角色

public class Facade
{
    private SubSystem01 obj1 = new SubSystem01();
    private SubSystem02 obj2 = new SubSystem02();

    public void method()
    {
        obj1.method1();
        obj2.method2();
    }
}


// Client.java

package Facade;

public class Client
{
    public static void main(String[] args)
    {
        Facade facade = new Facade();
        facade.method();
    }
}
```


#### 用外观模式设计特产选购界面

本实例的外观角色`WySpecialty`是`JPanel`的子类，它拥有8个子系统角色`Specialty1~Specialty8`，它们是图标类`ImageIcon`的子类对象，用来保存该婺源特产的图标。

外观类`WySpecialty`用`JTree`组件来管理婺源特产的名称，并定义一个事件处理方法`valueClianged(TreeSelectionEvent e)`，当用户从树中选择特产时，该特产的图标对象保存在标签`JLabd`对象中。

客户窗体对象用分割面板来实现，左边放外观角色的目录树，右边放显示所选特产图像的标签。

<div align=center><img src=DesignPatterns1/用外观模式设计特产选购界面.png width=70%></div>

```java

// Specialty1.java

package Facade1.Specialty;

import javax.swing.*;

public class Specialty1 extends ImageIcon
{
    public Specialty1()
    {
        super("src/Facade1/source/Specialty11.jpg");
    }
}

// Specialty2.java~Specialty8.java与上述代码类似


// WySpecialty.java

package Facade1;

import Facade1.Specialty.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class WySpecialty extends JPanel implements TreeSelectionListener
{
    final JTree tree;
    JLabel label;

    private Specialty1 s1 = new Specialty1();
    private Specialty2 s2 = new Specialty2();
    private Specialty3 s3 = new Specialty3();
    private Specialty4 s4 = new Specialty4();
    private Specialty5 s5 = new Specialty5();
    private Specialty6 s6 = new Specialty6();
    private Specialty7 s7 = new Specialty7();
    private Specialty8 s8 = new Specialty8();

    public WySpecialty()
    {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("婺源特产");
        DefaultMutableTreeNode node1 = null, node2 = null, tempNode = null;

        node1 = new DefaultMutableTreeNode("婺源四大特产（红、绿、黑、白）");
        tempNode = new DefaultMutableTreeNode("婺源荷包红鲤鱼");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源绿茶");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源龙尾砚");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源江湾雪梨");
        node1.add(tempNode);
        top.add(node1);

        node2 = new DefaultMutableTreeNode("婺源其它土特产");
        tempNode = new DefaultMutableTreeNode("婺源酒糟鱼");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源糟米子糕");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源清明果");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源油煎灯");
        node2.add(tempNode);

        top.add(node2);

        tree = new JTree(top);
        tree.addTreeSelectionListener(this);
        label = new JLabel();
    }

    @Override
    public void valueChanged(TreeSelectionEvent event)
    {
        if(event.getSource() == tree)
        {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if(node == null) return;

            if(node.isLeaf())
            {
                Object object = node.getUserObject();
                String sele = object.toString();
                label.setText(sele);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);

                sele = sele.substring(2,4);
                if(sele.equalsIgnoreCase("荷包")) label.setIcon(s1);
                else if(sele.equalsIgnoreCase("绿茶")) label.setIcon(s2);
                else if(sele.equalsIgnoreCase("龙尾")) label.setIcon(s3);
                else if(sele.equalsIgnoreCase("江湾")) label.setIcon(s4);
                else if(sele.equalsIgnoreCase("酒糟")) label.setIcon(s5);
                else if(sele.equalsIgnoreCase("糟米")) label.setIcon(s6);
                else if(sele.equalsIgnoreCase("清明")) label.setIcon(s7);
                else if(sele.equalsIgnoreCase("油煎")) label.setIcon(s8);
                label.setHorizontalAlignment(JLabel.CENTER);
            }
        }
    }
}


// Client.java

package Facade1;

import javax.swing.*;
import java.awt.*;

public class Client
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame ("外观模式: 婺源特产选择测试");
        Container cp = f.getContentPane();

        WySpecialty wys = new WySpecialty();

        JScrollPane treeView = new JScrollPane(wys.tree);
        JScrollPane scrollpane = new JScrollPane(wys.label);
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,treeView,scrollpane); //分割面版
        splitpane.setDividerLocation(230);     //设置splitpane的分隔线位置
        splitpane.setOneTouchExpandable(true); //设置splitpane可以展开或收起
        cp.add(splitpane);
        f.setSize(650,350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```


### 享元模式

在面向对象程序设计过程中，有时会面临要<font color=red>创建大量相同或相似对象实例</font>的问题。创建那么多的对象将会耗费很多的系统资源，它是系统性能提高的一个瓶颈。例如，围棋和五子棋中的黑白棋子，图像中的坐标点或颜色，局域网中的路由器、交换机和集线器，教室里的桌子和凳子等。这些对象有很多相似的地方，如果能<font color=red>把它们相同的部分提取出来共享</font>，则能节省大量的系统资源，这就是享元模式的产生背景。

享元（Flyweight）模式的定义：运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似类的开销，从而提高系统资源的利用率。

享元模式的主要优点是：相同对象只要保存一份，这降低了系统中对象的数量，从而降低了系统中细粒度对象给内存带来的压力。

其主要缺点是：
- 为了使对象可以共享，需要将一些不能共享的状态外部化，这将增加程序的复杂性。
- 读取享元模式的外部状态会使得运行时间稍微变长。


#### 享元模式的结构

享元模式中存在以下两种状态：
- 内部状态，即不会随着环境的改变而改变的可共享部分；
- 外部状态，指随环境改变而改变的不可以共享的部分。

享元模式的实现要领就是区分应用中的这两种状态，并将外部状态外部化。

享元模式的主要部分如下：
- 抽象享元角色（Flyweight）：是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，<font color=red>非享元的外部状态以参数的形式通过方法传入</font>。
- 具体享元（Concrete Flyweight）角色：实现抽象享元角色中所规定的接口。
- 非享元（Unsharable Flyweight)角色：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
- 享元工厂（Flyweight Factory）角色：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。

享元模式的结构图中的`UnsharedConcreteFlyweight`是`非享元角色`，里面包含了非共享的外部状态信息`info`；而`Flyweight`是`抽象享元角色`，里面包含了享元方法 `operation(UnsharedConcreteFlyweight state)`，<font color=red>非享元的外部状态以参数的形式通过该方法传入</font>；`ConcreteFlyweight`是具体享元角色，包含了关键字`key`，它实现了抽象享元接口；`FlyweightFactory`是享元工厂角色，它用关键字`key`来管理具体享元；客户角色通过享元工厂获取具体享元，并访问具体享元的相关方法。

<div align=center><img src=DesignPatterns1/享元模式的结构图.png width=90%></div>

```java

// Flyweight.java

package Flyweight;

//抽象享元角色

public interface Flyweight
{
    public void operation(UnsharedConcreteFlyweight state);
}


// ConcreteFlyweight.java

package Flyweight;

public class ConcreteFlyweight implements Flyweight
{
    private String key;

    ConcreteFlyweight(String key)
    {
        this.key = key;
        System.out.println("具体享元" + key + "被创建！");
    }

    @Override
    public void operation(UnsharedConcreteFlyweight outState)
    {
        System.out.print("具体享元" + key + "被调用，");
        System.out.println("非享元信息是: "+ outState.getInfo());
    }
}


// FlyweightFactory.java

package Flyweight;

import java.util.HashMap;

public class FlyweightFactory
{
    private HashMap<String, Flyweight> flyweights = new HashMap<String, Flyweight>();

    public Flyweight getFlyweight(String key)
    {
        Flyweight flyweight = (Flyweight)flyweights.get(key);

        if(flyweight != null)
        {
            System.out.println("具体享元" + key + "已经存在，被成功获取！");
        }
        else
        {
            flyweight = new ConcreteFlyweight(key);
            flyweights.put(key, flyweight);
        }
        return flyweight;
    }
}


// UnsharedConcreteFlyweight.java

package Flyweight;

//非享元角色

public class UnsharedConcreteFlyweight
{
    private String info;

    public UnsharedConcreteFlyweight(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
}


// Client.java

package Flyweight;

public class Client
{
    public static void main(String[] args)
    {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight f01 = factory.getFlyweight("a");
        Flyweight f02 = factory.getFlyweight("a");
        Flyweight f03 = factory.getFlyweight("a");
        Flyweight f11 = factory.getFlyweight("b");
        Flyweight f12 = factory.getFlyweight("b");

        f01.operation(new UnsharedConcreteFlyweight("第1次调用a。"));
        f02.operation(new UnsharedConcreteFlyweight("第2次调用a。"));
        f03.operation(new UnsharedConcreteFlyweight("第3次调用a。"));
        f11.operation(new UnsharedConcreteFlyweight("第1次调用b。"));
        f12.operation(new UnsharedConcreteFlyweight("第2次调用b。"));
    }
}
```

#### 用享元模式实现围棋

围棋包含多个“黑”或“白”颜色的棋子，所以用享元模式比较好。

本实例中的`棋子（ChessPieces）类`是`抽象享元角色`，它包含了一个落子的`DownPieces(Graphics graphics,Point point)`方法；`白子（WhitePieces）`和`黑子（BlackPieces）`类是`具体享元角色`，它实现了落子方法；`Point`是`非享元角色`，它指定了`落子的位置`；`WeiqiFactory`是`享元工厂角色`，它通过`ArrayList`来管理棋子，并且提供了`获取白子或者黑子`的`getChessPieces(String type)`方法；`客户类（Chessboard）`利用`Graphics组件`在框架窗体中绘制一个棋盘，并实现`mouseClicked(MouseEvent e)`事件处理方法，<font color=red>该方法根据用户的选择从享元工厂中获取白子或者黑子并落在棋盘上</font>。

<div align=center><img src=DesignPatterns1/用享元模式实现围棋.png width=90%></div>

```java

// ChessPieces.java

package Flyweight1;

//抽象享元角色：棋子

import java.awt.*;

public interface ChessPieces
{
    public void DownPieces(Graphics graphics, Point point);    //下子
}


// WhitePieces.java

package Flyweight1;

//具体享元角色：白子

import java.awt.*;

public class WhitePieces implements ChessPieces
{
    @Override
    public void DownPieces(Graphics graphics, Point point)
    {
        graphics.setColor(Color.WHITE);
        graphics.fillOval(point.x, point.y, 30, 30);
    }
}


// BlackPieces.java

package Flyweight1;

//具体享元角色：黑子

import java.awt.*;

public class BlackPieces implements ChessPieces
{
    @Override
    public void DownPieces(Graphics graphics, Point point)
    {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(point.x, point.y, 30, 30);
    }
}


// WeiqiFactory.java

package Flyweight1;

//享元工厂角色

import java.util.ArrayList;

public class WeiqiFactory
{
    private ArrayList<ChessPieces> qz;

    public WeiqiFactory()
    {
        qz = new ArrayList<ChessPieces>();
        ChessPieces whitePieces = new WhitePieces();
        qz.add(whitePieces);
        ChessPieces blackPieces = new BlackPieces();
        qz.add(blackPieces);
    }

    public ChessPieces getChessPieces(String type)
    {
        if(type.equalsIgnoreCase("white"))
        {
            return (ChessPieces) qz.get(0);
        }
        else if(type.equalsIgnoreCase("black"))
        {
            return (ChessPieces) qz.get(1);
        }
        else
        {
            return null;
        }
    }
}


// Chessboard.java

package Flyweight1;

//客户角色：棋盘

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Chessboard extends MouseAdapter
{
    private WeiqiFactory weiqiFactory;
    private JFrame jFrame;
    private Graphics graphics;
    private JRadioButton whitePieces;
    private JRadioButton blackPieces;

    private final int x = 50;
    private final int y = 50;
    private final int w = 40;    //小方格宽度和高度
    private final int chessboardw = 400;    //棋盘宽度和高度

    Chessboard()
    {
        weiqiFactory = new WeiqiFactory();
        jFrame = new JFrame("享元模式在围棋中的应用");
        jFrame.setBounds(100,100,500,550);
        jFrame.setLocationRelativeTo(null);
        //jFrame.setVisible(true);
        //jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel SouthJP = new JPanel();
        jFrame.add("South", SouthJP);
        whitePieces = new JRadioButton("白子", true);
        blackPieces = new JRadioButton("黑子");
        ButtonGroup group = new ButtonGroup();
        group.add(whitePieces);
        group.add(blackPieces);
        SouthJP.add(whitePieces);
        SouthJP.add(blackPieces);

        jFrame.setVisible(true);
        jFrame.setResizable(false);

        JPanel CenterJP = new JPanel();
        CenterJP.setLayout(null);
        CenterJP.setSize(500, 500);
        CenterJP.addMouseListener(this);
        jFrame.add("Center", CenterJP);

        try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        graphics = CenterJP.getGraphics();
        graphics.setColor(Color.BLUE);
        graphics.drawRect(x, y, chessboardw, chessboardw);

        for(int i = 1; i < 10; i++)
        {
            //绘制第i条竖直线
            graphics.drawLine(x + ( i * w), y, x + (i * w), y + chessboardw);
            //绘制第i条水平线
            graphics.drawLine(x, y + (i * w), x + chessboardw, y + (i * w));
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        Point point = new Point(mouseEvent.getX() - 15, mouseEvent.getY() - 15);
        if(whitePieces.isSelected())
        {
            ChessPieces chessPieces = weiqiFactory.getChessPieces("white");
            chessPieces.DownPieces(graphics, point);
        }
        else if(blackPieces.isSelected())
        {
            ChessPieces chessPieces = weiqiFactory.getChessPieces("black");
            chessPieces.DownPieces(graphics, point);
        }
    }
}


// WeiQiGame.java

package Flyweight1;

public class WeiQiGame
{
    public static void main(String[] args)
    {
        new Chessboard();
    }
}
```


### 组合模式

在现实生活中，存在很多<font color=red>部分-整体</font>的关系，例如，大学中的部门与学院、总公司中的部门与分公司、学习用品中的书与书包、生活用品中的衣服与衣柜以及厨房中的锅碗瓢盆等。在软件开发中也是这样，例如，文件系统中的文件与文件夹、窗体程序中的简单控件与容器控件等。对这些简单对象与复合对象的处理，如果用组合模式来实现会很方便。

组合（Composite）模式的定义：有时又叫作部分-整体模式，<font color=red>它是一种将对象组合成树状的层次结构的模式，用来表示“部分-整体”的关系，使用户对单个对象和组合对象具有一致的访问性</font>。

组合模式的主要优点有：
- 组合模式使得客户端代码可以一致地处理单个对象和组合对象，无须关心自己处理的是单个对象，还是组合对象，这简化了客户端代码；
- 更容易在组合体内加入新的对象，客户端不会因为加入了新的对象而更改源代码，满足“开闭原则”。

其主要缺点是：
- 设计较复杂，客户端需要花更多时间理清类之间的层次关系；
- 不容易限制容器中的构件；
- 不容易用继承的方法来增加构件的新功能。

组合模式适用于以下应用场景：
- 在需要表示一个对象整体与部分的<font color=red>层次结构</font>的场合。
- 要求对用户隐藏组合对象与单个对象的不同，用户可以用统一的接口使用组合结构中的所有对象的场合。


#### 组合模式的结构

组合模式包含以下主要部分：
- 抽象构件（Component）角色：它的主要作用是为树叶构件和树枝构件声明公共接口，并实现它们的默认行为。在`透明式`的组合模式中抽象构件还声明访问和管理子类的接口；在`安全式`的组合模式中不声明访问和管理子类的接口，管理工作由树枝构件完成。
- 树叶构件（Leaf）角色：是组合中的叶节点对象，它没有子节点，用于实现抽象构件角色中 声明的公共接口。
- 树枝构件（Composite）角色：是组合中的分支节点对象，它有子节点。它实现了抽象构件角色中声明的接口，它的主要作用是存储和管理子部件，通常包含`Add()`、`Remove()`、`GetChild()`等方法。

组合模式分为`透明式`的组合模式和`安全式`的组合模式。

**透明方式**：在该方式中，由于抽象构件声明了所有子类中的全部方法，所以客户端无须区别树叶对象和树枝对象，对客户端来说是透明的。但其缺点是：树叶构件本来没有`Add()`、`Remove()`及`GetChild()`方法，却要实现它们（空实现或抛异常），这样会带来一些安全性问题。

<div align=center><img src=DesignPatterns1/透明式的组合模式的结构图.png width=80%></div>

假如要访问集合c0={leaf1,{leaf2,leaf3}}中的元素，其对应的树状图如图所示：
<div align=center><img src=DesignPatterns1/透明式的组合模式访问节点.png width=30%></div>

```java

// Component.java

package Composite;

//抽象构件

public interface Component
{
    public void add(Component component);
    public void remove(Component component);
    public Component getChild(int i);
    public void operation();
}


// Leaf.java

package Composite;

//树叶构件

public class Leaf implements Component
{
    private String name;

    public Leaf(String name)
    {
        this.name = name;
    }

    public void add(Component component){ }
    public void remove(Component component){ }

    public Component getChild(int i)
    {
        return null;
    }

    public void operation()
    {
        System.out.println("树叶 " + name + "：被访问！");
    }
}


// Composite.java

package Composite;

//树枝构件

import java.util.ArrayList;

public class Composite implements Component
{
    private ArrayList<Component> children = new ArrayList<Component>();

    public void add(Component component)
    {
        children.add(component);
    }

    public void remove(Component component)
    {
        children.remove(component);
    }

    public Component getChild(int i)
    {
        return children.get(i);
    }

    public void operation()
    {
        for(Object obj : children)
        {
            ((Component)obj).operation();
        }
    }
}


// CompositePattern.java

package Composite;

public class CompositePattern
{
    public static void main(String[] args)
    {
        Component c0 = new Composite();
        Component c1 = new Composite();
        Component leaf1 = new Leaf("1");
        Component leaf2 = new Leaf("2");
        Component leaf3 = new Leaf("3");

        c0.add(leaf1);
        c0.add(c1);
        c1.add(leaf2);
        c1.add(leaf3);
        c0.operation();
    }
}
```

运行结果：
```
树叶 1：被访问！
树叶 2：被访问！
树叶 3：被访问！
```

**安全方式**：在该方式中，将管理子构件的方法移到树枝构件中，抽象构件和树叶构件没有对子对象的管理方法，这样就避免了上一种方式的安全性问题，但由于叶子和分支有不同的接口，客户端在调用时要知道树叶对象和树枝对象的存在，所以失去了透明性。

<div align=center><img src=DesignPatterns1/安全式的组合模式的结构图.png width=80%></div>


#### 组合模式的应用实例

用组合模式实现当用户在商店购物后，显示其所选商品信息，并计算所选商品总价的功能。

说明：假如李先生到韶关“天街e角”生活用品店购物，用1个红色小袋子装了2包婺源特产（单价7.9元）、1张婺源地图（单价9.9元）；用1个白色小袋子装了2包韶关香菇（单价68元）和3包韶关红茶（单价180元）；用1个中袋子装了前面的红色小袋子和1个景德镇瓷器（单价380元）；用1个大袋子装了前面的中袋子、白色小袋子和1双李宁牌运动鞋（单价198元）。

最后“大袋子”中的内容有：{1双李宁牌运动鞋（单价198元）、白色小袋子{2包韶关香菇（单价68元）、3包韶关红茶（单价180元）}、中袋子{1个景德镇瓷器（单价380元）、红色小袋子{2包婺源特产（单价7.9元）、1 张婺源地图（单价9.9元）}}}，现在要求编程显示李先生放在大袋子中的所有商品信息并计算要支付的总价。

本实例可按安全组合模式设计：

<div align=center><img src=DesignPatterns1/安全组合模式实例.png width=80%></div>

```java

// Articles.java

package Composite1;

//抽象构件：物品

public interface Articles
{
    public float calculation(); //计算
    public void show();
}


// Goods.java

package Composite1;

//树叶构件：商品

public class Goods implements Articles
{
    private String goodsName;     //名商品字
    private int quantity;    //数量
    private float unitPrice; //单价

    public Goods(String name, int quantity, float unitPrice)
    {
        this.goodsName = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public float calculation()
    {
        return quantity * unitPrice;
    }

    public void show()
    {
        System.out.println(goodsName + "(数量：" + quantity + "，单价：" + unitPrice + "元)");
    }
}


// Bags.java

package Composite1;

//树枝构件：袋子

import java.util.ArrayList;

public class Bags implements Articles
{
    private String bagsName;     //袋子名字

    private ArrayList<Articles> bags = new ArrayList<Articles>();

    public Bags(String bagsName)
    {
        this.bagsName = bagsName;
    }

    public void add(Articles articles)
    {
        bags.add(articles);
    }

    public void remove(Articles articles)
    {
        bags.remove(articles);
    }

    public Articles getChild(int i)
    {
        return bags.get(i);
    }

    public float calculation()
    {
        float sum = 0;
        for(Object obj : bags)
        {
            sum += ((Articles)obj).calculation();
        }
        return sum;
    }

    public void show()
    {
        for(Object obj : bags)
        {
            ((Articles)obj).show();
        }
    }
}


// ShoppingTest.java

package Composite1;

public class ShoppingTest
{
    public static void main(String[] args)
    {
        float sum = 0;
        Bags BigBag, mediumBag, smallRedBag, smallWhiteBag;
        Goods goods;

        BigBag = new Bags("大袋子");
        mediumBag = new Bags("中袋子");
        smallRedBag = new Bags("红色小袋子");
        smallWhiteBag = new Bags("白色小袋子");

        goods = new Goods("婺源特产",2,7.9f);
        smallRedBag.add(goods);
        goods = new Goods("婺源地图",1,9.9f);
        smallRedBag.add(goods);

        goods = new Goods("韶关香菇",2,68);
        smallWhiteBag.add(goods);
        goods = new Goods("韶关红茶",3,180);
        smallWhiteBag.add(goods);

        goods = new Goods("景德镇瓷器",1,380);
        mediumBag.add(goods);
        mediumBag.add(smallRedBag);

        goods = new Goods("李宁牌运动鞋",1,198);
        BigBag.add(goods);
        BigBag.add(mediumBag);
        BigBag.add(smallWhiteBag);

        System.out.println("您选购的商品有：");
        BigBag.show();
        sum = BigBag.calculation();
        System.out.println("要支付的总价是：" + sum + "元");
    }
}
```


## 行为型模式

行为型模式用于<font color=red>描述程序在运行时复杂的流程控制，即描述多个类或对象之间怎样相互协作共同完成单个对象都无法单独完成的任务</font>，它涉及算法与对象间职责的分配。

行为型模式分为类行为模式`和`对象行为模式`，前者采用`继承机制`来在类间分派行为，后者采用`组合`或`聚合`在对象间分配行为。由于<font color=red>组合关系或聚合关系比继承关系耦合度低</font>，满足“合成复用原则”，所以<font color=red>对象行为模式比类行为模式具有更大的灵活性</font>。

行为型模式是GoF设计模式中最为庞大的一类，它包含以下11种模式：

- 模板方法（Template Method）模式：定义一个操作中的算法骨架，将算法的一些步骤延迟到子类中，使得子类在可以不改变该算法结构的情况下重定义该算法的某些特定步骤。
- 策略（Strategy）模式：定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的改变不会影响使用算法的客户。
- 命令（Command）模式：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。
- 职责链（Chain of Responsibility）模式：把请求从链中的一个对象传到下一个对象，直到请求被响应为止。通过这种方式去除对象之间的耦合。
- 状态（State）模式：允许一个对象在其内部状态发生改变时改变其行为能力。
- 观察者（Observer）模式：多个对象间存在一对多关系，当一个对象发生改变时，把这种改变通知给其他多个对象，从而影响其他对象的行为。
- 中介者（Mediator）模式：定义一个中介对象来简化原有对象之间的交互关系，降低系统中对象间的耦合度，使原有对象之间不必相互了解。
- 迭代器（Iterator）模式：提供一种方法来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。
- 访问者（Visitor）模式：在不改变集合元素的前提下，为一个集合中的每个元素提供多种访问方式，即每个元素有多个访问者对象访问。
- 备忘录（Memento）模式：在不破坏封装性的前提下，获取并保存一个对象的内部状态，以便以后恢复它。
- 解释器（Interpreter）模式：提供如何定义语言的文法，以及对语言句子的解释方法，即解释器。

以上11种行为型模式，除了`模板方法模式`和`解释器模式`是类行为型模式，其他的全部属于对象行为型模式。


### 模板方法模式

在面向对象程序设计过程中，程序员常常会遇到这种情况：设计一个系统时知道了算法所需的关键步骤，而且确定了这些步骤的执行顺序，但某些步骤的具体实现还未知，或者说某些步骤的实现与具体的环境相关。

例如，去银行办理业务一般要经过以下4个流程：取号、排队、办理具体业务、对银行工作人员进行评分等，其中<font color=red>取号、排队和对银行工作人员进行评分的业务对每个客户是一样的，可以在父类中实现，但是办理具体业务却因人而异，它可能是存款、取款或者转账等，可以延迟到子类中实现</font>。

这样的例子在生活中还有很多，例如，一个人每天会起床、吃饭、做事、睡觉等，其中“做事”的内容每天可能不同。我们<font color=red>把这些规定了流程或格式的实例定义成模板，允许使用者根据自己的需求去更新它</font>，例如，简历模板、论文模板、Word 中模板文件等。

**模板方法（Template Method）模式**的定义如下：定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。它是一种类行为型模式。

该模式的主要优点如下：
- 它封装了不变部分，扩展可变部分。它把认为是不变部分的算法封装到父类中实现，而把可变部分算法由子类继承实现，便于子类继续扩展。
- 它在父类中提取了公共的部分代码，便于代码复用。
- 部分方法是由子类实现的，因此子类可以通过扩展方式增加相应的功能，符合开闭原则。

该模式的主要缺点如下：
- 对每个不同的实现都需要定义一个子类，这会导致类的个数增加，系统更加庞大，设计也更加抽象。
- 父类中的抽象方法由子类实现，子类执行的结果会影响父类的结果，这导致一种反向的控制结构，它提高了代码阅读的难度。

模板方法模式通常适用于以下场景：
- 算法的整体步骤很固定，但其中个别部分易变时，这时候可以使用模板方法模式，将容易变的部分抽象出来，供子类实现。
- 当多个子类存在公共的行为时，可以将其提取出来并集中到一个公共父类中以避免代码重复。首先，要识别现有代码中的不同之处，并且将不同之处分离为新的操作。最后，用一个调用这些新的操作的模板方法来替换这些不同的代码。
- 当需要控制子类的扩展时，模板方法只在特定点调用钩子操作，这样就只允许在这些点进行扩展。

#### 模板方法模式的结构

模板方法模式需要注意抽象类与具体子类之间的协作。它用到了虚函数的多态性技术以及“不用调用我，让我来调用你”的反向控制技术。

模板方法模式包含以下主要部分：

- **抽象类（Abstract Class）**：负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成。这些方法的定义如下：
    - 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。
    - 基本方法：是整个算法中的一个步骤，包含以下几种类型：
        - 抽象方法：在抽象类中申明，由具体子类实现。
        - 具体方法：在抽象类中已经实现，在具体子类中可以继承或重写它。
        - 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种。

- **具体子类（Concrete Class）**：实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的一个组成步骤。

<div align=center><img src=DesignPatterns1/模板方法模式的结构图.png width=70%></div>

```java

// AbstractClass.java

package TemplateMethod;

//抽象类

public abstract class AbstractClass
{
    public void TemplateMethod() //模板方法
    {
        SpecificMethod();
        abstractMethod1();
        abstractMethod2();
    }

    public void SpecificMethod() //具体方法
    {
        System.out.println("抽象类中的具体方法被调用...");
    }

    public abstract void abstractMethod1(); //抽象方法1
    public abstract void abstractMethod2(); //抽象方法2
}


// ConcreteClass.java

package TemplateMethod;

//具体子类

public class ConcreteClass extends AbstractClass
{
    public void abstractMethod1()
    {
        System.out.println("抽象方法1的实现被调用...");
    }

    public void abstractMethod2()
    {
        System.out.println("抽象方法2的实现被调用...");
    }
}


// TemplateMethodPattern.java

package TemplateMethod;

public class TemplateMethodPattern
{
    public static void main(String[] args)
    {
        AbstractClass templateMethod = new ConcreteClass();
        templateMethod.TemplateMethod();
    }
}
```


#### 用模板方法模式办理出国留学手续

出国留学手续一般经过以下流程：索取学校资料，提出入学申请，办理因私出国护照、出境卡和公证，申请签证，体检、订机票、准备行装，抵达目标学校等，<font color=red>其中有些业务对各个学校是一样的，但有些业务因学校不同而不同</font>，所以比较适合用模板方法模式来实现。

在本实例中，我们先定义一个出国留学的抽象类`StudyAbroad`，里面包含了一个模板方法`TemplateMethod()`，该方法中包含了办理出国留学手续流程中的各个基本方法，其中有些方法的处理由于各国都一样，所以在抽象类中就可以实现。但<font color=red>有些方法的处理各国是不同的，必须在其具体子类（如美国留学类`StudyInAmerica`）中实现</font>。如果再增加一个国家，只要增加一个子类就可以了。

<div align=center><img src=DesignPatterns1/用模板方法模式办理出国留学手续.png width=80%></div>

```java

// StudyAbroad.java

package TemplateMethod1;

//抽象类: 出国留学

public abstract class StudyAbroad
{
    public void TemplateMethod() //模板方法
    {
        LookingForSchool(); //索取学校资料
        ApplyForEnrol();    //入学申请

        ApplyForPassport(); //办理因私出国护照、出境卡和公证
        ApplyForVisa();     //申请签证
        ReadyGoAbroad();    //体检、订机票、准备行装

        Arriving();         //抵达
    }

    public void ApplyForPassport()
    {
        System.out.println("三.办理因私出国护照、出境卡和公证：");
        System.out.println("  1）持录取通知书、本人户口簿或身份证向户口所在地公安机关申请办理因私出国护照和出境卡。");
        System.out.println("  2）办理出生公证书，学历、学位和成绩公证，经历证书，亲属关系公证，经济担保公证。");
    }

    public void ApplyForVisa()
    {
        System.out.println("四.申请签证：");
        System.out.println("  1）准备申请国外境签证所需的各种资料，包括个人学历、成绩单、工作经历的证明；个人及家庭收入、资金和财产证明；家庭成员的关系证明等；");
        System.out.println("  2）向拟留学国家驻华使(领)馆申请入境签证。申请时需按要求填写有关表格，递交必需的证明材料，缴纳签证。有的国家(比如美国、英国、加拿大等)在申请签证时会要求申请人前往使(领)馆进行面试。");
    }

    public void ReadyGoAbroad()
    {
        System.out.println("五.体检、订机票、准备行装：");
        System.out.println("  1）进行身体检查、免疫检查和接种传染病疫苗；");
        System.out.println("  2）确定机票时间、航班和转机地点。");
    }

    public abstract void LookingForSchool();//索取学校资料
    public abstract void ApplyForEnrol();   //入学申请
    public abstract void Arriving();        //抵达
}


// StudyInAmerica.java

package TemplateMethod1;

//具体子类: 美国留学

public class StudyInAmerica extends StudyAbroad
{
    @Override
    public void LookingForSchool()
    {
        System.out.println("一.索取学校以下资料：");
        System.out.println("  1）对留学意向国家的政治、经济、文化背景和教育体制、学术水平进行较为全面的了解；");
        System.out.println("  2）全面了解和掌握国外学校的情况，包括历史、学费、学制、专业、师资配备、教学设施、学术地位、学生人数等；");
        System.out.println("  3）了解该学校的住宿、交通、医疗保险情况如何；");
        System.out.println("  4）该学校在中国是否有授权代理招生的留学中介公司？");
        System.out.println("  5）掌握留学签证情况；");
        System.out.println("  6）该国政府是否允许留学生合法打工？");
        System.out.println("  8）毕业之后可否移民？");
        System.out.println("  9）文凭是否受到我国认可？");
    }

    @Override
    public void ApplyForEnrol()
    {
        System.out.println("二.入学申请：");
        System.out.println("  1）填写报名表；");
        System.out.println("  2）将报名表、个人学历证明、最近的学习成绩单、推荐信、个人简历、托福或雅思语言考试成绩单等资料寄往所申请的学校；");
        System.out.println("  3）为了给签证办理留有充裕的时间，建议越早申请越好，一般提前1年就比较从容。");
    }

    @Override
    public void Arriving()
    {
        System.out.println("六.抵达目标学校：");
        System.out.println("  1）安排住宿；");
        System.out.println("  2）了解校园及周边环境。");
    }
}


// StudyAbroadProcess.java

package TemplateMethod1;

public class StudyAbroadProcess
{
    public static void main(String[] args)
    {
        StudyAbroad tm = new StudyInAmerica();
        tm.TemplateMethod();
    }
}
```

#### 模板方法模式的扩展

在模板方法模式中，基本方法包含：`抽象方法`、`具体方法`和`钩子方法`，正确使用`钩子方法`可以使得子类控制父类的行为。如下面例子中，可以通过在具体子类中重写钩子方法`HookMethod1()`和`HookMethod2()`来改变抽象父类中的运行结果：

<div align=center><img src=DesignPatterns1/模板方法模式的扩展.png width=80%></div>

```java

// HookAbstractClass.java

package TemplateMethod2;

//含钩子方法的抽象类

public abstract class HookAbstractClass
{
    public void TemplateMethod() //模板方法
    {
        abstractMethod1();
        HookMethod1();
        if(HookMethod2())
        {
            SpecificMethod();
        }
        abstractMethod2();
    }

    public void SpecificMethod() //具体方法
    {
        System.out.println("抽象类中的具体方法被调用...");
    }

    public void HookMethod1(){}  //钩子方法1

    public boolean HookMethod2() //钩子方法2
    {
        return true;
    }

    public abstract void abstractMethod1(); //抽象方法1
    public abstract void abstractMethod2(); //抽象方法2
}


// HookConcreteClass.java

package TemplateMethod2;

//含钩子方法的具体子类

public class HookConcreteClass extends HookAbstractClass
{
    public void abstractMethod1()
    {
        System.out.println("抽象方法1的实现被调用...");
    }

    public void abstractMethod2()
    {
        System.out.println("抽象方法2的实现被调用...");
    }

    public void HookMethod1()
    {
        System.out.println("钩子方法1被重写...");
    }

    public boolean HookMethod2()
    {
        return true;
    }
}


// HookTemplateMethod.java

package TemplateMethod2;

public class HookTemplateMethod
{
    public static void main(String[] args)
    {
        HookAbstractClass tm = new HookConcreteClass();
        tm.TemplateMethod();
    }
}
```

如果钩子方法`HookMethod1()`和钩子方法`HookMethod2()`的代码改变，则程序的运行结果也会改变。


### 策略模式

在现实生活中常常遇到实现某种目标存在多种策略可供选择的情况，例如，出行旅游可以乘坐飞机、乘坐火车、骑自行车或自己开私家车等，超市促销可以釆用打折、送商品、送积分等方法。

在软件开发中也常常遇到类似的情况，当实现某一个功能存在多种算法或者策略，我们可以根据环境或者条件的不同选择不同的算法或者策略来完成该功能，如数据排序策略有冒泡排序、选择排序、插入排序、二叉树排序等。

如果使用多重条件转移语句实现（即硬编码），不但使条件语句变得很复杂，而且增加、删除或更换算法要修改原代码，不易维护，违背开闭原则。如果采用策略模式就能很好解决该问题。

策略（Strategy）模式的定义：该模式定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。策略模式属于对象行为模式，它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。

策略模式的主要优点如下：
- 多重条件语句不易维护，而使用策略模式可以避免使用多重条件语句。
- 策略模式提供了一系列的可供重用的算法族，恰当使用继承可以把算法族的公共代码转移到父类里面，从而避免重复的代码。
- 策略模式可以提供相同行为的不同实现，客户可以根据不同时间或空间要求选择不同的。
- 策略模式提供了对开闭原则的完美支持，可以在不修改原代码的情况下，灵活增加新算法。
- 策略模式把算法的使用放到环境类中，而算法的实现移到具体策略类中，实现了二者的分离。

其主要缺点如下：
- 客户端必须理解所有策略算法的区别，以便适时选择恰当的算法类。
- 策略模式造成很多的策略类。

策略模式在很多地方用到，如`Java SE`中的容器布局管理就是一个典型的实例，`Java SE`中的每个容器都存在多种布局供用户选择。在程序设计中，通常在以下几种情况中使用策略模式较多：

- 一个系统需要动态地在几种算法中选择一种时，可将每个算法封装到策略类中。
- 一个类定义了多种行为，并且这些行为在这个类的操作中以多个条件语句的形式出现，可将每个条件分支移入它们各自的策略类中以代替这些条件语句。
- 系统中各算法彼此完全独立，且要求对客户隐藏具体算法的实现细节时。
- 系统要求使用算法的客户不应该知道其操作的数据时，可使用策略模式来隐藏与算法相关的数据结构。
- 多个类只区别在表现行为不同，可以使用策略模式，在运行时动态选择具体要执行的行为。


#### 策略模式的结构

策略模式是准备一组算法，并将这组算法封装到一系列的策略类里面，作为一个抽象策略类的子类。策略模式的重心不是如何实现算法，而是<font color=red>如何组织这些算法</font>，从而让程序结构更加灵活，具有更好的维护性和扩展性。

策略模式的主要部分如下：
- 抽象策略（Strategy）类：定义了一个公共接口，各种不同的算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法，一般使用接口或抽象类实现。
- 具体策略（Concrete Strategy）类：实现了抽象策略定义的接口，提供具体的算法实现。
- 环境（Context）类：持有一个策略类的引用，最终给客户端调用。

<div align=center><img src=DesignPatterns1/策略模式的结构.png width=90%></div>

```java

// Strategy.java

package Strategy;

//抽象策略类

public interface Strategy
{
    public void strategyMethod();    //策略方法
}


// ConcreteStrategyA.java

package Strategy;

//具体策略类A

public class ConcreteStrategyA implements Strategy
{
    public void strategyMethod()
    {
        System.out.println("具体策略A的策略方法被访问！");
    }
}


// ConcreteStrategyB.java

package Strategy;

//具体策略类B

public class ConcreteStrategyB implements Strategy
{
    public void strategyMethod()
    {
        System.out.println("具体策略B的策略方法被访问！");
    }
}


// Context.java

package Strategy;

//环境类

public class Context
{
    private Strategy strategy;

    public Strategy getStrategy()
    {
        return strategy;
    }

    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void strategyMethod()
    {
        strategy.strategyMethod();
    }
}


// StrategyPattern.java

package Strategy;

public class StrategyPattern
{
    public static void main(String[] args)
    {
        Context context = new Context();
        Strategy strategy = new ConcreteStrategyA();
        context.setStrategy(strategy);
        context.strategyMethod();

        System.out.println("-----------------");
        strategy = new ConcreteStrategyB();
        context.setStrategy(strategy);
        context.strategyMethod();
    }
}
```

#### 大闸蟹做菜策略

关于大闸蟹的做法有很多种，我们以清蒸大闸蟹和红烧大闸蟹两种方法为例，介绍策略模式的应用。

首先，定义一个大闸蟹加工的抽象策略类（`CrabCooking`），里面包含了一个做菜的抽象方法`CookingMethod()`；然后，定义清蒸大闸蟹（`SteamedCrabs`）和红烧大闸蟹（`BraisedCrabs`）的具体策略类，它们实现了抽象策略类中的抽象方法；由于本程序要显示做好的结果图，所以将具体策略类定义成`JLabel`的子类；最后，定义一个厨房（`Kitchen`）环境类，它具有设置和选择做菜策略的方法；客户类通过厨房类获取做菜策略，并把做菜结果图在窗体中显示出来。

<div align=center><img src=DesignPatterns1/大闸蟹做菜策略.png width=90%></div>

```java

// CrabCooking.java

package Strategy1;

//抽象策略类：大闸蟹加工类

public interface CrabCooking
{
    public void CookingMethod();    //做菜方法
}


// SteamedCrabs.java

package Strategy1;

//具体策略类：清蒸大闸蟹

import javax.swing.*;

public class SteamedCrabs extends JLabel implements CrabCooking
{
    public void CookingMethod()
    {
        this.setIcon(new ImageIcon("src/Strategy1/source/SteamedCrabs.jpg"));
        this.setHorizontalAlignment(CENTER);
    }
}


// BraisedCrabs.java

package Strategy1;

//具体策略类：红烧大闸蟹

import javax.swing.*;

public class BraisedCrabs extends JLabel implements CrabCooking
{
    public void CookingMethod()
    {
        this.setIcon(new ImageIcon("src/Strategy1/source/BraisedCrabs.jpg"));
        this.setHorizontalAlignment(CENTER);
    }
}


// Kitchen.java

package Strategy1;

//环境类：厨房

public class Kitchen
{
    private CrabCooking strategy;    //抽象策略

    public void setStrategy(CrabCooking strategy)
    {
        this.strategy = strategy;
    }

    public CrabCooking getStrategy()
    {
        return strategy;
    }

    public void CookingMethod()
    {
        strategy.CookingMethod();    //做菜
    }
}


// CrabCookingStrategy.java

package Strategy1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CrabCookingStrategy implements ItemListener
{
    private JFrame jFrame;
    private JRadioButton steamed, braised;
    private JPanel CenterJP, SouthJP;
    private Kitchen kitchen;    //厨房
    private CrabCooking steamedCrabs, braisedCrabs;    //大闸蟹加工方式

    public CrabCookingStrategy()
    {
        jFrame = new JFrame("策略模式在大闸蟹做菜中的应用");
        jFrame.setBounds(100,100,500,400);
        // jFrame.setVisible(true);
        // jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SouthJP = new JPanel();
        CenterJP = new JPanel();
        jFrame.add("South", SouthJP);
        jFrame.add("Center", CenterJP);

        steamed = new JRadioButton("清蒸大闸蟹");
        braised = new JRadioButton("红烧大闸蟹");
        steamed.addItemListener(this);
        braised.addItemListener(this);
        ButtonGroup group=new ButtonGroup();
        group.add(steamed);
        group.add(braised);
        SouthJP.add(steamed);
        SouthJP.add(braised);

        jFrame.setVisible(true);
        jFrame.setResizable(true);

        kitchen = new Kitchen();    //厨房
        steamedCrabs = new SteamedCrabs();    //清蒸大闸蟹类
        braisedCrabs = new BraisedCrabs();    //红烧大闸蟹类
    }

    public void itemStateChanged(ItemEvent e)
    {
        JRadioButton jRadioButton = (JRadioButton) e.getSource();
        if(jRadioButton == steamed)
        {
            kitchen.setStrategy(steamedCrabs);
            kitchen.CookingMethod(); //清蒸
        }
        else if(jRadioButton == braised)
        {
            kitchen.setStrategy(braisedCrabs);
            kitchen.CookingMethod(); //红烧
        }

        CenterJP.removeAll();
        CenterJP.repaint();
        CenterJP.add((Component) kitchen.getStrategy());
        jFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new CrabCookingStrategy();
    }
}
```


### 命令模式

在软件开发系统中，常常出现`方法的请求者`与`方法的实现者`之间存在紧密的耦合关系。这不利于软件功能的扩展与维护。例如，想对行为进行“撤销、重做、记录”等处理都很不方便，因此“如何将方法的请求者与方法的实现者解耦”变得很重要，命令模式能很好地解决这个问题。

在现实生活中，这样的例子也很多，例如，电视机遥控器（命令发送者）通过按钮（具体命令）来遥控电视机（命令接收者），还有计算机键盘上的“功能键”等。

命令（Command）模式的定义如下：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。这样两者之间通过命令对象进行沟通，这样方便将命令对象进行储存、传递、调用、增加与管理。

命令模式的主要优点如下：
- 降低系统的耦合度。命令模式能将调用操作的对象与实现该操作的对象解耦。
- 增加或删除命令非常方便。采用命令模式增加与删除命令不会影响其他类，它满足“开闭原则”，对扩展比较灵活。
- 可以实现宏命令。命令模式可以与组合模式结合，将多个命令装配成一个组合命令，即宏命令。
- 方便实现`Undo`和`Redo`操作。命令模式可以与后面介绍的`备忘录模式`结合，实现命令的撤销与恢复。

其缺点是：可能产生大量具体命令类。因为计对每一个具体操作都需要设计一个具体命令类，这将增加系统的复杂性。


命令模式通常适用于以下场景：
- 当系统需要将请求调用者与请求接收者解耦时，命令模式使得调用者和接收者不直接交互。
- 当系统需要随机请求命令或经常增加或删除命令时，命令模式比较方便实现这些功能。
- 当系统需要执行一组操作时，命令模式可以定义宏命令来实现该功能。
- 当系统需要支持命令的撤销（Undo）操作和恢复（Redo）操作时，可以将命令对象存储起来，采用备忘录模式来实现。


#### 命令模式的结构

可以将系统中的相关操作抽象成命令，使调用者与实现者相关分离，其结构如下：

- 抽象命令类（Command）角色：声明执行命令的接口，拥有执行命令的抽象方法 execute()。
- 具体命令角色（Concrete Command）角色：是抽象命令类的具体实现类，它拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
- 实现者/接收者（Receiver）角色：执行命令功能的相关操作，是具体命令对象业务的真正实现者。
- 调用者/请求者（Invoker）角色：是请求的发送者，它通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，它不直接访问接收者。

<div align=center><img src=DesignPatterns1/命令模式的结构图.png width=80%></div>

```java

// Command.java

package Command;

//抽象命令

public interface Command
{
    public abstract void execute();
}


// ConcreteCommand.java

package Command;

public class ConcreteCommand implements Command
{
    private Receiver receiver;

    public ConcreteCommand()
    {
        receiver = new Receiver();
    }

    public void execute()
    {
        receiver.action();
    }
}


// Receiver.java

package Command;

//接收者

public class Receiver
{
    public void action()
    {
        System.out.println("接收者的action()方法被调用...");
    }
}


// Invoker.java

package Command;

//调用者

public class Invoker
{
    private Command command;

    public Invoker(Command command)
    {
        this.command = command;
    }

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public void call()
    {
        System.out.println("调用者执行命令command...");
        command.execute();
    }
}


// CommandPattern.java

package Command;

public class CommandPattern
{
    public static void main(String[] args)
    {
        Command cmd = new ConcreteCommand();
        Invoker invoker = new Invoker(cmd);
        System.out.println("客户访问调用者的call()方法...");
        invoker.call();
    }
}
```

执行结果：
```
客户访问调用者的call()方法...
调用者执行命令command...
接收者的action()方法被调用...
```

#### 用命令模式实现餐馆吃早餐

客户去餐馆可选择的早餐有肠粉、河粉和馄饨等，客户可向服务员选择以上早餐中的若干种，服务员将客户的请求交给相关的厨师去做。这里的`点早餐`相当于`命令`，`服务员`相当于`调用者`，`厨师`相当于`接收者`，所以用命令模式实现比较合适。

首先，定义一个早餐类（`Breakfast`），它是`抽象命令类`，有抽象方法`cooking()`，说明要做什么；再定义其子类肠粉类（`ChangFen`）、馄饨类（`HunTun`）和河粉类（`HeFen`），它们是`具体命令类`，实现早餐类的`cooking()`方法，但它们不会具体做，而是交给具体的厨师去做；具体厨师类有肠粉厨师（`ChangFenChef`）、馄蚀厨师（`HunTunChef`）和河粉厨师（`HeFenChef`），他们是`命令的接收者`，由于本实例要显示厨师做菜的效果图，所以把每个厨师类定义为`JFrame`的子类；最后，定义服务员类（`Waiter`），它接收客户的做菜请求，并`发出做菜的命令`。客户类是通过服务员类来点菜的。

<div align=center><img src=DesignPatterns1/用命令模式实现餐馆吃早餐.png width=90%></div>

##### command

```java
// Breakfast.java

package Command1.command;

public interface Breakfast
{
    public abstract void cooking();
}


// ChangFen.java

package Command1.command;

//具体命令：肠粉

import Command1.receiver.ChangFenChef;

public class ChangFen implements Breakfast
{
    private ChangFenChef receiver;

    public ChangFen()
    {
        receiver = new ChangFenChef();
    }

    public void cooking()
    {
        receiver.cooking();
    }
}


// HeFen.java

package Command1.command;

//具体命令：河粉

import Command1.receiver.HeFenChef;

public class HeFen implements Breakfast
{
    private HeFenChef receiver;

    public HeFen()
    {
        receiver = new HeFenChef();
    }

    public void cooking()
    {
        receiver.cooking();
    }
}


// HunTun.java

package Command1.command;

//具体命令：馄饨

import Command1.receiver.HunTunChef;

public class HunTun implements Breakfast
{
    private HunTunChef receiver;

    public HunTun()
    {
        receiver = new HunTunChef();
    }

    public void cooking()
    {
        receiver.cooking();
    }
}
```

##### receiver

```java

// ChangFenChef.java

package Command1.receiver;

//接收者：肠粉厨师

import javax.swing.*;

public class ChangFenChef extends JFrame
{
    JLabel jLabel = new JLabel();

    public ChangFenChef()
    {
        super("煮肠粉");
        jLabel.setIcon(new ImageIcon("src/Command1/source/ChangFen.jpg"));
        this.add(jLabel);
        this.setLocation(30, 30);
        this.pack();
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void cooking()
    {
        this.setVisible(true);
    }
}


// HeFenChef.java

package Command1.receiver;

//接收者：河粉厨师

import javax.swing.*;

public class HeFenChef extends JFrame
{
    JLabel jLabel = new JLabel();

    public HeFenChef()
    {
        super("煮河粉");
        jLabel.setIcon(new ImageIcon("src/Command1/source/HeFen.jpg"));
        this.add(jLabel);
        this.setLocation(200, 280);
        this.pack();
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void cooking()
    {
        this.setVisible(true);
    }
}


// HunTunChef.java

package Command1.receiver;

import javax.swing.*;

public class HunTunChef extends JFrame
{
    JLabel jLabel = new JLabel();

    public HunTunChef()
    {
        super("煮馄饨");
        jLabel.setIcon(new ImageIcon("src/Command1/source/HunTun.jpg"));
        this.add(jLabel);
        this.setLocation(350, 50);
        this.pack();
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void cooking()
    {
        this.setVisible(true);
    }
}
```

##### invoker

```java

// Waiter

package Command1.invoker;

//调用者：服务员

import Command1.command.Breakfast;

public class Waiter
{
    private Breakfast changFen, hunTun, heFen;

    public void setChangFen(Breakfast breakfast)
    {
        changFen = breakfast;
    }

    public void setHunTun(Breakfast breakfast)
    {
        hunTun = breakfast;
    }

    public void setHeFen(Breakfast breakfast)
    {
        heFen = breakfast;
    }

    public void chooseChangFen()
    {
        changFen.cooking();
    }

    public void chooseHunTun()
    {
        hunTun.cooking();
    }

    public void chooseHeFen()
    {
        heFen.cooking();
    }
}
```

##### Client

```java
// CookingCommand.java

package Command1;

import Command1.command.*;
import Command1.invoker.Waiter;

public class CookingCommand
{
    public static void main(String[] args)
    {
        Breakfast food1 = new ChangFen();
        Breakfast food2 = new HunTun();
        Breakfast food3 = new HeFen();
        Waiter waiter = new Waiter();

        waiter.setChangFen(food1);//设置肠粉菜单
        waiter.setHunTun(food2);  //设置河粉菜单
        waiter.setHeFen(food3);   //设置馄饨菜单

        waiter.chooseChangFen();  //选择肠粉
        waiter.chooseHeFen();     //选择河粉
        waiter.chooseHunTun();    //选择馄饨
    }
}
```

#### 命令模式的扩展

在软件开发中，有时将`命令模式`与`组合模式`联合使用，这就构成了`宏命令模式`，也叫`组合命令模式`。宏命令包含了一组命令，它充当了具体命令与调用者的双重角色，执行它时将递归调用它所包含的所有命令，其具体结构图如图所示：

<div align=center><img src=DesignPatterns1/组合命令模式的结构图.png width=90%></div>

```java

// AbstractCommand.java

package Command2;

//抽象命令

public interface AbstractCommand
{
    public abstract void execute();
}


// ConcreteCommand1.java

package Command2;

//树叶构件: 具体命令1

public class ConcreteCommand1 implements AbstractCommand
{
    private CompositeReceiver receiver;

    public ConcreteCommand1()
    {
        receiver = new CompositeReceiver();
    }

    public void execute()
    {
        receiver.action1();
    }
}


// ConcreteCommand2.java

package Command2;

//树叶构件: 具体命令2

public class ConcreteCommand2 implements AbstractCommand
{
    private CompositeReceiver receiver;

    public ConcreteCommand2()
    {
        receiver = new CompositeReceiver();
    }

    public void execute()
    {
        receiver.action2();
    }
}


// CompositeReceiver.java

package Command2;

//接收者

public class CompositeReceiver
{
    public void action1()
    {
        System.out.println("接收者的action1()方法被调用...");
    }

    public void action2()
    {
        System.out.println("接收者的action2()方法被调用...");
    }
}


// CompositeInvoker.java

package Command2;

//树枝构件: 调用者

import java.util.ArrayList;

public class CompositeInvoker implements AbstractCommand
{
    private ArrayList<AbstractCommand> children = new ArrayList<AbstractCommand>();

    public void add(AbstractCommand c)
    {
        children.add(c);
    }

    public void remove(AbstractCommand c)
    {
        children.remove(c);
    }

    public AbstractCommand getChild(int i)
    {
        return children.get(i);
    }

    public void execute()
    {
        for(Object obj:children)
        {
            ((AbstractCommand) obj).execute();
        }
    }
}


// CompositeCommandPattern.java

package Command2;

public class CompositeCommandPattern
{
    public static void main(String[] args)
    {
        AbstractCommand cmd1 = new ConcreteCommand1();
        AbstractCommand cmd2 = new ConcreteCommand2();
        CompositeInvoker invoker = new CompositeInvoker();
        invoker.add(cmd1);
        invoker.add(cmd2);
        System.out.println("客户访问调用者的execute()方法...");
        invoker.execute();
    }
}
```


### 责任链模式

在现实生活中，常常会出现这样的事例：<font color=red>一个请求有多个对象可以处理，但每个对象的处理条件或权限不同</font>。例如，公司员工请假，可批假的领导有部门负责人、副总经理、总经理等，但每个领导能批准的天数不同，员工必须根据自己要请假的天数去找不同的领导签名，也就是说员工必须记住每个领导的姓名、电话和地址等信息，这增加了难度。这样的例子还有很多，如找领导出差报销、生活中的“击鼓传花”游戏等。

在计算机软硬件中也有相关例子，如总线网中数据报传送，每台计算机根据目标地址是否同自己的地址相同来决定是否接收；还有异常处理中，处理程序根据异常的类型决定自己是否处理该异常；还有`Struts2`的拦截器、`JSP`和`Servlet`的`Filter`等，所有这些，如果用责任链模式都能很好解决。

**责任链（Chain of Responsibility）模式**的定义：为了避免请求发送者与多个请求处理者耦合在一起，<font color=red>将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止</font>。


在责任链模式中，客户只需要将请求发送到责任链上即可，无须关心请求的处理细节和请求的传递过程，所以责任链将请求的发送者和请求的处理者解耦了。

责任链模式是一种对象行为型模式，其主要优点如下：
- 降低了对象之间的耦合度。该模式使得一个对象无须知道到底是哪一个对象处理其请求以及链的结构，发送者和接收者也无须拥有对方的明确信息。
- 增强了系统的可扩展性。可以根据需要增加新的请求处理类，满足开闭原则。
- 增强了给对象指派职责的灵活性。当工作流程发生变化，可以动态地改变链内的成员或者调动它们的次序，也可动态地新增或者删除责任。
- 责任链简化了对象之间的连接。每个对象只需保持一个指向其后继者的引用，不需保持其他所有处理者的引用，这避免了使用众多的`if`或者`if···else`语句。
- 责任分担。每个类只需要处理自己该处理的工作，不该处理的传递给下一个对象完成，明确各类的责任范围，符合类的单一职责原则。

其主要缺点如下：
- 不能保证每个请求一定被处理。由于一个请求没有明确的接收者，所以不能保证它一定会被处理，该请求可能一直传到链的末端都得不到处理。
- 对比较长的职责链，请求的处理可能涉及多个处理对象，系统性能将受到一定影响。
- 职责链建立的合理性要靠客户端来保证，增加了客户端的复杂性，可能会由于职责链的错误设置而导致系统出错，如可能会造成循环调用。

责任链模式通常在以下几种情况使用：
- 有多个对象可以处理一个请求，哪个对象处理该请求由运行时刻自动确定。
- 可动态指定一组对象处理请求，或添加新的处理者。
- 在不明确指定请求处理者的情况下，向多个处理者中的一个提交请求。


#### 责任链模式的结构

职责链模式主要包含以下部分：

- 抽象处理者（Handler）角色：定义一个处理请求的接口，包含抽象处理方法和一个后继连接。
- 具体处理者（Concrete Handler）角色：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者。
- 客户类（Client）角色：创建处理链，并向链头的具体处理者对象提交请求，它不关心处理细节和请求的传递过程。

<div align=center><img src=DesignPatterns1/责任链模式的结构图.png width=90%></div>

<div align=center><img src=DesignPatterns1/责任链.png width=90%></div>

```java

// Handler.java

package ChainOfResponsibility;

//抽象处理者角色

public abstract class Handler
{
    private Handler next;

    public void setNext(Handler next)
    {
        this.next = next;
    }

    public Handler getNext()
    {
        return next;
    }

    //处理请求的方法
    public abstract void handleRequest(String request);
}


// ConcreteHandler1.java

package ChainOfResponsibility;

//具体处理者角色1

public class ConcreteHandler1 extends Handler
{
    public void handleRequest(String request)
    {
        if(request.equals("one"))
        {
            System.out.println("具体处理者1负责处理该请求！");
        }
        else
        {
            if(getNext() != null)
            {
                getNext().handleRequest(request);
            }
            else
            {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}


// ConcreteHandler2.java

package ChainOfResponsibility;

//具体处理者角色2

public class ConcreteHandler2 extends Handler
{
    public void handleRequest(String request)
    {
        if(request.equals("two"))
        {
            System.out.println("具体处理者2负责处理该请求！");
        }
        else
        {
            if(getNext() != null)
            {
                getNext().handleRequest(request);
            }
            else
            {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}


// Client.java

package ChainOfResponsibility;

public class Client
{
    public static void main(String[] args)
    {
        //组装责任链
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        handler1.setNext(handler2);

        //提交请求
        handler1.handleRequest("two");
    }
}
```

运行结果：
```
具体处理者2负责处理该请求！
```


#### 用责任链模式设计一个请假条审批模块

假如规定学生请假小于或等于2天，班主任可以批准；小于或等于7天，系主任可以批准；小于或等于10天，院长可以批准；其他情况不予批准；这个实例适合使用职责链模式实现。

首先，定义一个领导类（`Leader`），它是`抽象处理者`，包含了一个指向下一位领导的指针`next`和一个处理假条的抽象处理方法`handleRequest(int LeaveDays)`；然后，定义班主任类（`ClassAdviser`）、系主任类（`DepartmentHead`）和院长类（`Dean`），它们是抽象处理者的子类，是`具体处理者`，必须根据自己的权力去实现父类的`handleRequest(int LeaveDays)`方法，如果无权处理就将假条交给下一位具体处理者，直到最后；`客户类`负责创建处理链，并将假条交给链头的具体处理者（班主任）。

<div align=center><img src=DesignPatterns1/用责任链模式设计一个请假条审批模块.png width=90%></div>

```java

// Leader.java

package ChainOfResponsibility1;

//抽象处理者：领导类

public abstract class Leader
{
    private Leader next;

    public void setNext(Leader next)
    {
        this.next = next;
    }
    public Leader getNext()
    {
        return next;
    }

    //处理请求的方法
    public abstract void handleRequest(int LeaveDays);
}


// ClassAdviser.java

package ChainOfResponsibility1;

//具体处理者1：班主任类

public class ClassAdviser extends Leader
{
    public void handleRequest(int LeaveDays)
    {
        if(LeaveDays <= 2)
        {
            System.out.println("班主任批准您请假" + LeaveDays + "天。");
        }
        else
        {
            if(getNext() != null)
            {
                getNext().handleRequest(LeaveDays);
            }
            else
            {
                System.out.println("请假天数太多，没有人批准该假条！");
            }
        }
    }
}


// DepartmentHead.java

package ChainOfResponsibility1;

//具体处理者2：系主任类

public class DepartmentHead extends Leader
{
    public void handleRequest(int LeaveDays)
    {
        if(LeaveDays <= 7)
        {
            System.out.println("系主任批准您请假" + LeaveDays + "天。");
        }
        else
        {
            if(getNext() != null)
            {
                getNext().handleRequest(LeaveDays);
            }
            else
            {
                System.out.println("请假天数太多，没有人批准该假条！");
            }
        }
    }
}


// Dean.java

package ChainOfResponsibility1;

//具体处理者3：院长类

public class Dean extends Leader
{
    public void handleRequest(int LeaveDays)
    {
        if(LeaveDays <= 10)
        {
            System.out.println("院长批准您请假" + LeaveDays + "天。");
        }
        else
        {
            if(getNext() != null)
            {
                getNext().handleRequest(LeaveDays);
            }
            else
            {
                System.out.println("请假天数太多，没有人批准该假条！");
            }
        }
    }
}


// Client.java

package ChainOfResponsibility1;

public class Client
{
    public static void main(String[] args)
    {
        //组装责任链
        Leader teacher1 = new ClassAdviser();
        Leader teacher2 = new DepartmentHead();
        Leader teacher3 = new Dean();

        teacher1.setNext(teacher2);
        teacher2.setNext(teacher3);

        //提交请求
        teacher1.handleRequest(8);
    }
}
```

运行结果：
```
院长批准您请假8天。
```


### 状态模式

在软件开发过程中，<font color=red>应用程序中的有些对象可能会根据不同的情况做出不同的行为</font>，我们把这种对象称为有状态的对象，而把影响对象行为的一个或多个动态变化的属性称为状态。当有状态的对象与外部事件产生互动时，其内部状态会发生改变，从而使得其行为也随之发生改变。如人的情绪有高兴的时候和伤心的时候，不同的情绪有不同的行为，当然外界也会影响其情绪变化。

对这种有状态的对象编程，传统的解决方案是：<font color=red>将这些所有可能发生的情况全都考虑到，然后使用`if-else`语句来做状态判断，再进行不同情况的处理</font>。但当对象的状态很多时，程序会变得很复杂。而且增加新的状态要添加新的`if-else`语句，这违背了“开闭原则”，不利于程序的扩展。

以上问题如果采用状态模式就能很好地得到解决。状态模式的解决思想是：当控制一个对象状态转换的条件表达式过于复杂时，把相关`判断逻辑`提取出来，放到一系列的状态类当中，这样可以把原来复杂的逻辑判断简单化。

**状态（State）模式**的定义：对有状态的对象，把复杂的`判断逻辑`提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

状态模式是一种对象行为型模式，其主要优点如下：

- 状态模式将与特定状态相关的行为局部化到一个状态中，并且将不同状态的行为分割开来，满足“单一职责原则”。
- 减少对象间的相互依赖。将不同的状态引入独立的对象中会使得状态转换变得更加明确，且减少对象间的相互依赖。
- 有利于程序的扩展。通过定义新的子类很容易地增加新的状态和转换。

状态模式的主要缺点如下：

- 状态模式的使用必然会增加系统的类与对象的个数。
- 状态模式的结构与实现都较为复杂，如果使用不当会导致程序结构和代码的混乱。

通常在以下情况下可以考虑使用状态模式：
- 当一个对象的行为取决于它的状态，并且它必须在运行时根据状态改变它的行为时，就可以考虑使用状态模式。
- 一个操作中含有庞大的分支结构，并且这些分支决定于对象的状态时。


#### 状态模式的结构

状态模式把受环境改变的对象行为包装在不同的状态对象里，其意图是让一个对象在其内部状态改变的时候，其行为也随之改变。

状态模式包含以下主要部分：

- 环境（Context）角色：也称为上下文，它定义了客户感兴趣的接口，维护一个当前状态，并将与状态相关的操作委托给当前状态对象来处理。
- 抽象状态（State）角色：定义一个接口，用以封装环境对象中的特定状态所对应的行为。
- 具体状态（Concrete State）角色：实现抽象状态所对应的行为。

<div align=center><img src=DesignPatterns1/状态模式的结构图.png width=80%></div>

```java

// State.java

package State;

//抽象状态类

public abstract class State
{
    public abstract void Handle(Context context);
}


// ConcreteStateA.java

package State;

public class ConcreteStateA extends State
{
    public void Handle(Context context)
    {
        System.out.println("当前状态是 A.");
        context.setState(new ConcreteStateB());
    }
}


// ConcreteStateB.java

package State;

public class ConcreteStateB extends State
{
    public void Handle(Context context)
    {
        System.out.println("当前状态是 B.");
        context.setState(new ConcreteStateA());
    }
}


// Context.java

package State;

//环境类

public class Context
{
    private State state;

    //定义环境类的初始状态
    public Context()
    {
        this.state = new ConcreteStateA();
    }

    //设置新状态
    public void setState(State state)
    {
        this.state = state;
    }

    //读取状态
    public State getState()
    {
        return state;
    }

    //对请求做处理
    public void Handle()
    {
        state.Handle(this);
    }
}


// Client.java

package State;

public class Client
{
    public static void main(String[] args)
    {
        Context context = new Context();    //创建环境
        context.Handle();    //处理请求
        context.Handle();
        context.Handle();
        context.Handle();
    }
}
```


#### 用状态模式设计一个学生成绩的状态转换程序

本实例包含了`不及格`，`中等`和`优秀`3种状态，当学生的分数小于60分时为“不及格”状态，当分数大于等于60分且小于90分时为“中等”状态，当分数大于等于90分时为“优秀”状态，我们用状态模式来实现这个程序。

首先，定义一个抽象状态类（`AbstractState`），其中包含了环境属性、状态名属性和当前分数属性，以及加减分方法`addScore(intx)`和检查当前状态的抽象方法`checkState()`；然后，定义“不及格”状态类`LowState`、“中等”状态类`MiddleState`和“优秀”状态类`HighState`，它们是`具体状态类`，实现`checkState()`方法，负责检査自己的状态，并根据情况转换；最后，定义环境类`（ScoreContext）`，其中包含了当前状态对象和加减分的方法`add(int score)`，客户类通过该方法来改变成绩状态。

<div align=center><img src=DesignPatterns1/用状态模式设计一个学生成绩的状态转换程序.png width=90%></div>

```java

// AbstractState.java

package State1;

public abstract class AbstractState
{
    protected ScoreContext scoreContext;  //环境
    protected String stateName; //状态名
    protected int score; //分数
    public abstract void checkState(); //检查当前状态

    public void addScore(int x)
    {
        score += x;
        System.out.print("加上：" + x + "分，\t当前分数：" + score );
        checkState();
        System.out.println("分，\t当前状态：" + scoreContext.getState().stateName);
    }
}


// LowState.java

package State1;

//具体状态类：不及格

public class LowState extends AbstractState
{
    public LowState(ScoreContext context)
    {
        scoreContext = context;
        stateName = "不及格";
        score = 0;
    }

    public LowState(AbstractState state)
    {
        scoreContext = state.scoreContext;
        stateName = "不及格";
        score = state.score;
    }

    public void checkState()
    {
        if(score >= 90)
        {
            scoreContext.setState(new HighState(this));
        }
        else if(score >= 60)
        {
            scoreContext.setState(new MiddleState(this));
        }
    }
}


// MiddleState.java

package State1;

public class MiddleState extends AbstractState
{
    public MiddleState(AbstractState state)
    {
        scoreContext = state.scoreContext;
        stateName = "中等";
        score = state.score;
    }

    public void checkState()
    {
        if(score < 60)
        {
            scoreContext.setState(new LowState(this));
        }
        else if(score >= 90)
        {
            scoreContext.setState(new HighState(this));
        }
    }
}


// HighState.java

package State1;

//具体状态类：优秀

public class HighState extends AbstractState
{
    public HighState(AbstractState state)
    {
        scoreContext = state.scoreContext;
        stateName = "优秀";
        score = state.score;
    }
    public void checkState()
    {
        if(score < 60)
        {
            scoreContext.setState(new LowState(this));
        }
        else if(score < 90)
        {
            scoreContext.setState(new MiddleState(this));
        }
    }
}


// ScoreContext.java

package State1;

public class ScoreContext
{
    private AbstractState state;

    public ScoreContext()
    {
        state = new LowState(this);
    }

    public void setState(AbstractState state)
    {
        this.state = state;
    }

    public AbstractState getState()
    {
        return state;
    }

    public void add(int score)
    {
        state.addScore(score);
    }
}


// Client.java

package State1;

public class Client
{
    public static void main(String[] args)
    {
        ScoreContext account = new ScoreContext();
        System.out.println("学生成绩状态测试：");
        account.add(30);
        account.add(40);
        account.add(25);
        account.add(-15);
        account.add(-25);
    }
}
```

运行结果：
```
学生成绩状态测试：
加上：30分，	当前分数：30分，	当前状态：不及格
加上：40分，	当前分数：70分，	当前状态：中等
加上：25分，	当前分数：95分，	当前状态：优秀
加上：-15分，	当前分数：80分，	当前状态：中等
加上：-25分，	当前分数：55分，	当前状态：不及格
```


#### 用状态模式设计一个多线程的状态转换程序

多线程存在5种状态，分别为新建状态、就绪状态、运行状态、阻塞状态和死亡状态，<font color=red>各个状态当遇到相关方法调用或事件触发时会转换到其他状态</font>，其状态转换规律如图所示：

<div align=center><img src=DesignPatterns1/线程状态转换图.png width=80%></div>

现在先定义一个抽象状态类（`TheadState`），然后为`线程状态转换图`所示的每个状态设计一个具体状态类，它们是新建状态（`New`）、就绪状态（`Runnable`）、运行状态（`Running`）、阻塞状态（`Blocked`）和死亡状态（`Dead`），每个状态中有触发它们转变状态的方法，<font color=red>环境类（`ThreadContext`）中先生成一个初始状态（`New`）</font>，并提供相关触发方法。

<div align=center><img src=DesignPatterns1/用状态模式设计一个多线程的状态转换程序.png width=80%></div>

```java

// ThreadState.java

package State2;

//抽象状态类：线程状态

public abstract class ThreadState
{
    protected String stateName; //状态名
}


// New.java

package State2.concreteState;

import State2.ThreadContext;
import State2.ThreadState;

public class New extends ThreadState
{
    public New()
    {
        stateName = "新建状态";
        System.out.println("当前线程处于：新建状态.");
    }

    public void start(ThreadContext threadContext)
    {
        System.out.print("调用start()方法-->");
        if(stateName.equals("新建状态"))
        {
            threadContext.setState(new Runnable());
        }
        else
        {
            System.out.println("当前线程不是新建状态，不能调用start()方法.");
        }
    }
}


// Runnable.java

package State2.concreteState;

//具体状态类：就绪状态

import State2.ThreadContext;
import State2.ThreadState;

public class Runnable extends ThreadState
{
    public Runnable()
    {
        stateName = "就绪状态";
        System.out.println("当前线程处于：就绪状态.");
    }

    public void getCPU(ThreadContext threadContext)
    {
        System.out.print("获得CPU时间-->");
        if(stateName.equals("就绪状态"))
        {
            threadContext.setState(new Running());
        }
        else
        {
            System.out.println("当前线程不是就绪状态，不能获取CPU.");
        }
    }
}


// Running.java

package State2.concreteState;

//具体状态类：运行状态

import State2.ThreadContext;
import State2.ThreadState;

public class Running extends ThreadState
{
    public Running()
    {
        stateName = "运行状态";
        System.out.println("当前线程处于：运行状态.");
    }

    public void suspend(ThreadContext threadContext)
    {
        System.out.print("调用suspend()方法-->");
        if(stateName.equals("运行状态"))
        {
            threadContext.setState(new Blocked());
        }
        else
        {
            System.out.println("当前线程不是运行状态，不能调用suspend()方法.");
        }
    }

    public void stop(ThreadContext threadContext)
    {
        System.out.print("调用stop()方法-->");
        if(stateName.equals("运行状态"))
        {
            threadContext.setState(new Dead());
        }
        else
        {
            System.out.println("当前线程不是运行状态，不能调用stop()方法.");
        }
    }
}


// Blocked.java

package State2.concreteState;

import State2.ThreadContext;
import State2.ThreadState;

public class Blocked extends ThreadState
{
    public Blocked()
    {
        stateName = "阻塞状态";
        System.out.println("当前线程处于：阻塞状态.");
    }

    public void resume(ThreadContext threadContext)
    {
        System.out.print("调用resume()方法-->");
        if(stateName.equals("阻塞状态"))
        {
            threadContext.setState(new Runnable());
        }
        else
        {
            System.out.println("当前线程不是阻塞状态，不能调用resume()方法.");
        }
    }
}


// Dead.java

package State2.concreteState;

import State2.ThreadState;

public class Dead extends ThreadState
{
    public Dead()
    {
        stateName = "死亡状态";
        System.out.println("当前线程处于：死亡状态.");
    }
}


// ThreadContext.java

package State2;

//环境类

import State2.concreteState.*;
import State2.concreteState.Runnable;

public class ThreadContext
{
    private ThreadState state;

    public ThreadContext()
    {
        state = new New();
    }

    public void setState(ThreadState state)
    {
        this.state = state;
    }

    public ThreadState getState()
    {
        return state;
    }

    public void start()
    {
        ((New) state).start(this);
    }

    public void getCPU()
    {
        ((Runnable) state).getCPU(this);
    }

    public void suspend()
    {
        ((Running) state).suspend(this);
    }

    public void stop()
    {
        ((Running) state).stop(this);
    }

    public void resume()
    {
        ((Blocked) state).resume(this);
    }
}


// Client.java

package State2;

public class Client
{
    public static void main(String[] args)
    {
        ThreadContext context = new ThreadContext();
        context.start();
        context.getCPU();
        context.suspend();
        context.resume();
        context.getCPU();
        context.stop();
    }
}
```

运行结果：
```
当前线程处于：新建状态.
调用start()方法-->当前线程处于：就绪状态.
获得CPU时间-->当前线程处于：运行状态.
调用suspend()方法-->当前线程处于：阻塞状态.
调用resume()方法-->当前线程处于：就绪状态.
获得CPU时间-->当前线程处于：运行状态.
调用stop()方法-->当前线程处于：死亡状态.
```


#### 状态模式的扩展

在有些情况下，可能有多个环境对象需要共享一组状态，这时需要引入`享元模式`，将这些具体状态对象放在集合中供程序共享：

<div align=center><img src=DesignPatterns1/共享状态模式的结构图.png width=80%></div>

共享状态模式的不同之处是在环境类中增加了一个`HashMap`来保存相关状态，当需要某种状态时可以从中获取：

```java

// ShareState.java

package State3;

//抽象状态类

public abstract class ShareState
{
    public abstract void Handle(ShareContext context);
}


// ConcreteState1.java

package State3;

public class ConcreteState1 extends ShareState
{
    public void Handle(ShareContext context)
    {
        System.out.println("当前状态是：状态1");
        context.setState(context.getState("2"));
    }
}


// ConcreteState2.java

package State3;

public class ConcreteState2 extends ShareState
{
    public void Handle(ShareContext context)
    {
        System.out.println("当前状态是：状态2");
        context.setState(context.getState("1"));
    }
}


// ShareContext.java

package State3;

import java.util.HashMap;

public class ShareContext
{
    private ShareState state;
    private HashMap<String, ShareState> stateHashMap = new HashMap<String, ShareState>();

    public ShareContext()
    {
        state = new ConcreteState1();
        stateHashMap.put("1", state);
        state = new ConcreteState2();
        stateHashMap.put("2", state);
        state = getState("1");
    }

    //设置新状态
    public void setState(ShareState state)
    {
        this.state = state;
    }

    //读取状态
    public ShareState getState(String key)
    {
        ShareState state = (ShareState) stateHashMap.get(key);
        return state;
    }

    //对请求做处理
    public void Handle()
    {
        state.Handle(this);
    }
}


// Client.java

package State3;

public class Client
{
    public static void main(String[] args)
    {
        ShareContext context = new ShareContext(); //创建环境
        context.Handle(); //处理请求
        context.Handle();
        context.Handle();
        context.Handle();
    }
}
```

运行结果：
```
当前状态是：状态1
当前状态是：状态2
当前状态是：状态1
当前状态是：状态2
```


### 观察者模式

在现实世界中，许多对象并不是独立存在的，其中一个对象的行为发生改变可能会导致一个或者多个其他对象的行为也发生改变。例如，某种商品的物价上涨时会导致部分商家高兴，而消费者伤心；还有，当我们开车到交叉路口时，遇到红灯会停，遇到绿灯会行。这样的例子还有很多，例如，股票价格与股民、微信公众号与微信用户、气象局的天气预报与听众、小偷与警察等。

在软件世界也是这样，例如，Excel中的数据与折线图、饼状图、柱状图之间的关系；MVC模式中的模型与视图的关系；事件模型中的事件源与事件处理者。所有这些，如果用观察者模式来实现就非常方便。

**观察者（Observer）模式**的定义：指<font color=red>多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新</font>。这种模式有时又称作`发布-订阅模式`、`模型-视图模式`，它是对象行为型模式。

观察者模式是一种对象行为型模式，其主要优点如下：

- 降低了目标与观察者之间的耦合关系，两者之间是抽象耦合关系。
- 目标与观察者之间建立了一套触发机制。

它的主要缺点如下：

- 目标与观察者之间的依赖关系并没有完全解除，而且有可能出现循环引用。
- 当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率。

观察者模式适合以下几种情形：
- 对象间存在一对多关系，一个对象的状态发生改变会影响其他对象。
- 当一个抽象模型有两个方面，其中一个方面依赖于另一方面时，可将这二者封装在独立的对象中以使它们可以各自独立地改变和复用。


#### 观察者模式的结构

实现观察者模式时要注意`具体目标对象`和`具体观察者对象`之间不能直接调用，否则将使两者之间紧密耦合起来，这违反了面向对象的设计原则。

观察者模式的主要部分如下：

- 抽象主题（Subject）角色：也叫抽象目标类，它提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法。
- 具体主题（Concrete Subject）角色：也叫具体目标类，它实现抽象目标中的通知方法，当具体主题的内部状态发生改变时，<font color=red>通知所有注册过的观察者对象</font>。
- 抽象观察者（Observer）角色：它是一个抽象类或接口，它包含了一个更新自己的抽象方法，当接到具体主题的更改通知时被调用。
- 具体观察者（Concrete Observer）角色：实现抽象观察者中定义的抽象方法，以便<font color=red>在得到目标的更改通知时更新自身的状态</font>。

<div align=center><img src=DesignPatterns1/观察者模式的结构.png width=80%></div>

```java

// Subject.java

package Observer;

//抽象目标

import java.util.ArrayList;
import java.util.List;

public abstract class Subject
{
    protected List<Observer> observers = new ArrayList<Observer>();

    //增加观察者方法
    public void add(Observer observer)
    {
        observers.add(observer);
    }

    //删除观察者方法
    public void remove(Observer observer)
    {
        observers.remove(observer);
    }

    //通知观察者方法
    public abstract void notifyObserver();
}


// ConcreteSubject.java

package Observer;

//具体目标

public class ConcreteSubject extends Subject
{
    public void notifyObserver()
    {
        System.out.println("具体目标发生改变...");
        System.out.println("--------------");

        for(Object obs : observers)
        {
            ((Observer) obs).response();
        }
    }
}


// Observer.java

package Observer;

//抽象观察者

public interface Observer
{
    void response(); //反应
}


// ConcreteObserver1.java

package Observer;

//具体观察者1

public class ConcreteObserver1 implements Observer
{
    public void response()
    {
        System.out.println("具体观察者1作出反应！");
    }
}


// ConcreteObserver2.java

package Observer;

//具体观察者2

public class ConcreteObserver2 implements Observer
{
    public void response()
    {
        System.out.println("具体观察者2作出反应！");
    }
}


// Client.java

package Observer;

public class Client
{
    public static void main(String[] args)
    {
        Subject subject = new ConcreteSubject();
        Observer obs1 = new ConcreteObserver1();
        Observer obs2 = new ConcreteObserver2();

        subject.add(obs1);
        subject.add(obs2);
        subject.notifyObserver();
    }
}
```


#### 观察者模式的实例一

利用观察者模式设计一个程序，分析`人民币汇率`”`的升值或贬值对进口公司的`进口产品成本`或出口公司的`出口产品收入`以及公司的`利润率`的影响。

当人民币汇率升值时，进口公司的进口产品成本降低且利润率提升，出口公司的出口产品收入降低且利润率降低；当人民币汇率贬值时，进口公司的进口产品成本提升且利润率降低，出口公司的出口产品收入提升且利润率提升。

这里的汇率（`Rate`）类是`抽象目标类`，它包含了保存`观察者（Company）`的`List`和增加/删除观察者的方法，以及有关汇率改变的抽象方法`change(int number)`；而人民币汇率（`RMBrate`）类是`具体目标`， 它实现了父类的`change(int number)`方法，即<font color=red>当人民币汇率发生改变时通知相关公司</font>；公司（`Company`）类是`抽象观察者`，它定义了一个有关汇率反应的抽象方法`response(int number)`；进口公司（`ImportCompany`）类和出口公司（`ExportCompany`）类是`具体观察者类`，它们实现了父类的`response(int number)`方法，即<font color=red>当它们接收到汇率发生改变的通知时作为相应的反应</font>。

<div align=center><img src=DesignPatterns1/观察者模式的实例一.png width=80%></div>


```java

// Company.java

package Observer1.observer;

//抽象观察者：公司

public interface Company
{
    void response(int number);
}


// ImportCompany.java

package Observer1.observer;

//具体观察者1：进口公司

public class ImportCompany implements Company
{
    public void response(int number)
    {
        if(number > 0)
        {
            System.out.println("人民币汇率升值" + number + "个基点，降低了进口产品成本，提升了进口公司利润率。");
        }
        else if(number < 0)
        {
            System.out.println("人民币汇率贬值" + (- number) + "个基点，提升了进口产品成本，降低了进口公司利润率。");
        }
    }
}


// ExportCompany.java

package Observer1.observer;

//具体观察者2：出口公司

public class ExportCompany implements Company
{
    public void response(int number)
    {
        if(number > 0)
        {
            System.out.println("人民币汇率升值" + number + "个基点，降低了出口产品收入，降低了出口公司的销售利润率。");
        }
        else if(number < 0)
        {
            System.out.println("人民币汇率贬值" + (- number) + "个基点，提升了出口产品收入，提升了出口公司的销售利润率。");
        }
    }
}


// Rate.java

package Observer1.subject;

//抽象目标：汇率

import Observer1.observer.Company;

import java.util.ArrayList;
import java.util.List;

public abstract class Rate
{
    protected List<Company> companiness = new ArrayList<Company>();

    //增加观察者方法
    public void add(Company company)
    {
        companiness.add(company);
    }

    //删除观察者方法
    public void remove(Company company)
    {
        companiness.remove(company);
    }

    //通知观察者方法
    public abstract void change(int number);
}


// RMBrate.java

package Observer1.subject;

import Observer1.observer.Company;

public class RMBrate extends Rate
{
    public void change(int number)
    {
        for(Company obs : companiness)
        {
            ((Company) obs).response(number);
        }
    }
}


// Client.java

package Observer1;

import Observer1.observer.*;
import Observer1.subject.*;

public class Client
{
    public static void main(String[] args)
    {
        Rate rate = new RMBrate();
        Company watcher1 = new ImportCompany();
        Company watcher2 = new ExportCompany();

        rate.add(watcher1);
        rate.add(watcher2);
        rate.change(10);
        rate.change(-9);
    }
}
```

#### 用观察者模式处理学校铃声事件

观察者模式在软件幵发中用得最多的是`窗体程序设计中的事件处理`，窗体中的所有组件都是“事件源”，也就是目标对象，而事件处理程序类的对象是具体观察者对象。下面以一个学校铃声的事件处理程序为例，介绍Windows中的`事件处理模型`的工作原理。

在本实例中，学校的“铃”是事件源和目标，“老师”和“学生”是事件监听器和具体观察者，“铃声”是事件类。学生和老师来到学校的教学区，都会注意学校的铃，这叫事件绑定；当上课时间或下课时间到，会触发铃发声，这时会生成“铃声”事件；学生和老师听到铃声会开始上课或下课，这叫事件处理。这个实例非常适合用观察者模式实现

<div align=center><img src=DesignPatterns1/学校铃声的事件模型图.png width=60%></div>

现在用“观察者模式”来实现该事件处理模型。首先，定义一个铃声事件（`RingEvent`）类，它记录了铃声的类型（上课铃声/下课铃声）；再定义一个学校的铃（`BellEventSource`）类，它是事件源，是`观察者目标类`，该类里面包含了监听器容器`listener`，可以绑定监听者（学生或老师），并且<font color=red>有产生铃声事件和通知所有监听者的方法</font>；然后，定义一声事件监听者（`BellEventListener`）类，它是`抽象观察者`，它包含了铃声事件处理方法`heardBell(RingEvent e)`；最后，定义老师类（`TeachEventListener`）和学生类（`StuEventListener`），它们是事件监听器，是`具体观察者`，听到铃声会去上课或下课。

<div align=center><img src=DesignPatterns1/用观察者模式处理学校铃声事件.png width=90%></div>

```java

// RingEvent.java

package Observer2;

//铃声事件类：用于封装事件源及一些与事件相关的参数

import java.util.EventObject;

public class RingEvent extends EventObject
{
    private boolean sound;    //true表示上课铃声, false表示下课铃声

    public RingEvent(Object source, boolean sound)
    {
        super(source);
        this.sound = sound;
    }

    public void setSound(boolean sound)
    {
        this.sound = sound;
    }

    public boolean getSound()
    {
        return this.sound;
    }
}


// BellEventSource.java

package Observer2;

//目标类：事件源，铃

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BellEventSource
{
    private List<BellEventListener> listener; //监听器容器

    public BellEventSource()
    {
        listener = new ArrayList<BellEventListener>();
    }

    //给事件源绑定监听器
    public void addPersonListener(BellEventListener bellEventListener)
    {
        listener.add(bellEventListener);
    }

    //事件触发器：敲钟，当铃声sound的值发生变化时，触发事件。
    public void ring(boolean sound)
    {
        String type = sound ? "上课铃" : "下课铃";
        System.out.println(type + "响！");
        RingEvent event = new RingEvent(this, sound);
        notifies(event);    //通知注册在该事件源上的所有监听器
    }

    //当事件发生时,通知绑定在该事件源上的所有监听器做出反应（调用事件处理方法）
    protected void notifies(RingEvent ringEvent)
    {
        BellEventListener bellEventListener = null;
        Iterator<BellEventListener> iterator = listener.iterator();

        while(iterator.hasNext())
        {
            bellEventListener = iterator.next();
            bellEventListener.heardBell(ringEvent);
        }
    }
}


// BellEventListener.java

package Observer2;

//抽象观察者类：铃声事件监听器

import java.util.EventListener;

public interface BellEventListener extends EventListener
{
    //事件处理方法，听到铃声
    public void heardBell(RingEvent ringEvent);
}


// TeachEventListener.java

package Observer2;

public class TeachEventListener implements BellEventListener
{
    public void heardBell(RingEvent ringEvent)
    {
        if(ringEvent.getSound())
        {
            System.out.println("老师上课了...");
        }
        else
        {
            System.out.println("老师下课了...");
        }
    }
}


// StuEventListener.java

package Observer2;

public class StuEventListener implements BellEventListener
{
    public void heardBell(RingEvent ringEvent)
    {
        if(ringEvent.getSound())
        {
            System.out.println("同学们，上课了...");
        }
        else
        {
            System.out.println("同学们，下课了...");
        }
    }
}


// Client.java

package Observer2;

public class Client
{
    public static void main(String[] args)
    {
        BellEventSource bell = new BellEventSource();    //铃（事件源）
        bell.addPersonListener(new TeachEventListener()); //注册监听器（老师）
        bell.addPersonListener(new StuEventListener());    //注册监听器（学生）

        bell.ring(true);   //打上课铃声
        System.out.println("------------");
        bell.ring(false);  //打下课铃声
    }
}
```


#### 观察者模式的扩展

在Java中，通过`java.util.Observable`类和`java.util.Observer`接口定义了观察者模式，只要实现它们的子类就可以编写观察者模式实例。

- `Observable`类
`Observable`类是`抽象目标类`，它有一个`Vector`向量，用于保存所有要通知的观察者对象，下面来介绍它最重要的3个方法：
    - `void addObserver(Observer o)`方法：用于将新的观察者对象添加到向量中。
    - `void notifyObservers(Object arg)`方法：调用向量中的所有观察者对象的`update()`方法，通知它们数据发生改变。通常越晚加入向量的观察者越先得到通知。
    `void setChange()`方法：用来设置一个`boolean`类型的内部标志位，注明目标对象发生了变化。当它为真时，`notifyObservers()`才会通知观察者。

- `Observer`接口
`Observer`接口是`抽象观察者`，它监视目标对象的变化，当目标对象发生变化时，观察者得到通知，并调用`void update(Observable o, Object arg)`方法，进行相应的工作。

利用`Observable类`和`Observer接口`实现原油期货的观察者模式实例：

当原油价格上涨时，空方伤心，多方高兴；当油价下跌时，空方高兴，多方伤心。本实例中的`抽象目标（Observable`)类在Java中已经定义，可以直接定义其子类，即原油期货（`OilFutures`)类，它是`具体目标类`，该类中定义一个`SetPriCe(float price)`方法，当原油数据发生变化时调用其父类的`notifyObservers(Object arg)`方法来通知所有观察者；另外，本实例中的`抽象观察者接口（Observer）`在Java中已经定义，只要定义其子类，即`具体观察者类`（包括`多方类Bull`和`空方类Bear`），并实现`update(Observable o, Object arg)`方法即可。

<div align=center><img src=DesignPatterns1/观察者模式的扩展.png width=90%></div>

```java

// OilFutures.java

package Observer3;

//具体目标类：原油期货

import java.util.Observable;

public class OilFutures extends Observable
{
    private float price;

    public float getPrice()
    {
        return this.price;
    }

    public void setPrice(float price)
    {
        super.setChanged() ;  //设置内部标志位，注明数据发生变化
        super.notifyObservers(price);    //通知观察者价格改变了
        this.price = price ;
    }
}


// Bear.java

package Observer3;

//具体观察者类：空方

import java.util.Observable;
import java.util.Observer;

public class Bear implements Observer
{
    public void update(Observable o, Object arg)
    {
        Float price = ((Float) arg).floatValue();

        if(price > 0)
        {
            System.out.println("油价上涨" + price + "元，空方伤心了！");
        }
        else
        {
            System.out.println("油价下跌" + (- price) + "元，空方高兴了！");
        }
    }
}


// Bull.java

package Observer3;

//具体观察者类：多方

import java.util.Observable;
import java.util.Observer;

public class Bull implements Observer
{
    public void update(Observable o, Object arg)
    {
        Float price = ((Float) arg).floatValue();

        if(price > 0)
        {
            System.out.println("油价上涨" + price + "元，多方高兴了！");
        }
        else
        {
            System.out.println("油价下跌" + (- price) + "元，多方伤心了！");
        }
    }
}


// Client.java

package Observer3;

import java.util.Observer;

public class Client
{
    public static void main(String[] args)
    {
        OilFutures oil = new OilFutures();
        Observer bull = new Bull(); //多方
        Observer bear = new Bear(); //空方

        oil.addObserver(bull);
        oil.addObserver(bear);
        oil.setPrice(10);
        oil.setPrice(-8);
    }
}
```

### 中介者模式

在现实生活中，常常会出现好多对象之间存在复杂的交互关系，这种交互关系常常是`网状结构`”`，它要求每个对象都必须知道它需要交互的对象。例如，每个人必须记住他（她）所有朋友的电话；而且，朋友中如果有人的电话修改了，他（她）必须告诉其他所有的朋友修改，这叫作“牵一发而动全身”，非常复杂。

如果把这种“网状结构”改为`星形结构`的话，将大大降低它们之间的“耦合性”，这时只要找一个`中介者`就可以了。如前面所说的<font color=red>“每个人必须记住所有朋友电话”的问题，只要在网上建立一个每个朋友都可以访问的“通信录”就解决了</font>。这样的例子还有很多，例如，你刚刚参加工作想租房，可以找“房屋中介”；或者，自己刚刚到一个陌生城市找工作，可以找“人才交流中心”帮忙。

在软件的开发过程中，这样的例子也很多，例如，在MVC框架中，控制器（C）就是模型（M）和视图（V）的中介者；还有大家常用的QQ聊天程序的“中介者”是QQ服务器。所有这些，都可以采用“中介者模式”来实现，它将大大降低对象之间的耦合性，提高系统的灵活性。

**中介者（Mediator）模式**的定义：定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。中介者模式又叫`调停模式`，它是迪米特法则的典型应用。

中介者模式是一种对象行为型模式，其主要优点如下：

- 降低了对象之间的耦合性，使得对象易于独立地被复用。
- 将对象间的一对多关联转变为一对一的关联，提高系统的灵活性，使得系统易于维护和扩展。

其主要缺点是：当同事类太多时，中介者的职责将很大，它会变得复杂而庞大，以至于系统难以维护。


#### 中介者模式的结构

中介者模式实现的关键是找出“中介者”。

中介者模式包含以下主要部分：

- 抽象中介者（Mediator）角色：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。
- 具体中介者（ConcreteMediator）角色：实现中介者接口，定义一个`List`来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。
- 抽象同事类（Colleague）角色：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。
- 具体同事类（Concrete Colleague）角色：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。

<div align=center><img src=DesignPatterns1/中介者模式的结构图.png width=90%></div>

```java

// Mediator.java

package Mediator;

public abstract class Mediator
{
    public abstract void register(Colleague colleague);
    public abstract void relay(Colleague colleague); //转发
}


// ConcreteMediator.java

package Mediator;

//具体中介者

import java.util.ArrayList;
import java.util.List;

public class ConcreteMediator extends Mediator
{
    private List<Colleague> colleagues=new ArrayList<Colleague>();

    public void register(Colleague colleague)
    {
        if(! colleagues.contains(colleague))
        {
            colleagues.add(colleague);
            colleague.setMedium(this);
        }
    }

    public void relay(Colleague colleague)
    {
        for(Colleague ob : colleagues)
        {
            if(!ob.equals(colleague))
            {
                ((Colleague) ob).receive();
            }
        }
    }
}


// Colleague.java

package Mediator;

public abstract class Colleague
{
    protected Mediator mediator;

    public void setMedium(Mediator mediator)
    {
        this.mediator = mediator;
    }

    public abstract void receive();
    public abstract void send();
}


// ConcreteColleague1.java

package Mediator;

//具体同事类1

public class ConcreteColleague1 extends Colleague
{
    public void receive()
    {
        System.out.println("具体同事类1收到请求。");
    }

    public void send()
    {
        System.out.println("具体同事类1发出请求。");
        mediator.relay(this); //请中介者转发
    }
}


// ConcreteColleague2.java

package Mediator;

//具体同事类2

public class ConcreteColleague2 extends Colleague
{
    public void receive()
    {
        System.out.println("具体同事类2收到请求。");
    }

    public void send()
    {
        System.out.println("具体同事类2发出请求。");
        mediator.relay(this); //请中介者转发
    }
}


// Client.java

package Mediator;

public class Client
{
    public static void main(String[] args)
    {
        Mediator mediator = new ConcreteMediator();
        Colleague colleague1, colleague2;
        colleague1 = new ConcreteColleague1();
        colleague2 = new ConcreteColleague2();
        mediator.register(colleague1);
        mediator.register(colleague2);
        colleague1.send();
        System.out.println("--------------------------");
        colleague2.send();
    }
}
```

运行结果：
```
具体同事类1发出请求。
具体同事类2收到请求。
--------------------------
具体同事类2发出请求。
具体同事类1收到请求。
```


#### 用中介者模式编写一个交流平台程序

韶关房地产交流平台是`房地产中介公司`提供给`卖方客户`与`买方客户`进行信息交流的平台，比较适合用中介者模式来实现。

首先，定义一个中介公司（`Medium`）接口，它是`抽象中介者`，它包含了客户注册方法`register(Customer member)`和信息转发方法`relay(String from, String ad)`；再定义一个韶关房地产中介（`EstateMedium`）公司，它是`具体中介者类`，它包含了保存客户信息的`List`对象，并实现了中介公司中的抽象方法。

然后，定义一个客户（`Customer`）类，它是`抽象同事类`，其中包含了中介者的对象，和发送信息的`send(String ad)`方法与接收信息的`receive(String from，String ad)`方法的接口，由于本程序是窗体程序，所以本类继承`JPmme`类，并实现动作事件的处理方法`actionPerformed(ActionEvent e)`。

最后，定义卖方（`Seller`）类和买方（`Buyer`）类，它们是`具体同事类`，是客户（`Customer`）类的子类，它们实现了父类中的抽象方法，通过中介者类进行信息交流。

<div align=center><img src=DesignPatterns1/用中介者模式编写一个交流平台程序.png width=90%></div>

```java

// Medium.java

package Mediator1.mediator;

//抽象中介者：中介公司

import Mediator1.colleague.Customer;

public interface Medium
{
    void register(Customer member); //客户注册
    void relay(String from, String ad); //转发
}


// EstateMedium.java

package Mediator1.mediator;

//具体中介者：房地产中介

import Mediator1.colleague.Customer;

import java.util.ArrayList;
import java.util.List;

public class EstateMedium implements Medium
{
    private List<Customer> members = new ArrayList<Customer>();

    public void register(Customer member)
    {
        if(! members.contains(member))
        {
            members.add(member);
            member.setMedium(this);
        }
    }

    public void relay(String from, String ad)
    {
        for(Customer ob : members)
        {
            String name = ob.getName();

            if(! name.equals(from))
            {
                ((Customer) ob).receive(from, ad);
            }
        }
    }
}


// Customer.java

package Mediator1.colleague;

//抽象同事类：客户

import Mediator1.mediator.Medium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Customer extends JFrame implements ActionListener
{
    protected Medium medium;
    protected String name;
    JTextField SentText;
    JTextArea ReceiveArea;

    public Customer(String name)
    {
        super(name);
        this.name = name;
    }

    void ClientWindow(int x, int y)
    {
        Container container;
        JScrollPane jScrollPane;
        JPanel jPanel1, jPanel2;

        container = this.getContentPane();
        SentText = new JTextField(18);
        ReceiveArea = new JTextArea(10,18);
        ReceiveArea.setEditable(false);

        jPanel1 = new JPanel();
        jPanel1.setBorder(BorderFactory.createTitledBorder("接收内容："));
        jPanel1.add(ReceiveArea);
        jScrollPane = new JScrollPane(jPanel1);
        container.add(jScrollPane, BorderLayout.NORTH);

        jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createTitledBorder("发送内容："));
        jPanel2.add(SentText);
        container.add(jPanel2, BorderLayout.SOUTH);

        SentText.addActionListener(this);
        this.setLocation(x,y);
        this.setSize(250, 330);
        this.setResizable(false); //窗口大小不可调整
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String tempInfo = SentText.getText().trim();
        SentText.setText("");
        this.send(tempInfo);
    }

    public String getName() { return name; }

    public void setMedium(Medium medium) { this.medium=medium; }

    public abstract void send(String ad);
    public abstract void receive(String from, String ad);
}


// Seller.java

package Mediator1.colleague;

public class Seller extends Customer
{
    public Seller(String name)
    {
        super(name);
        ClientWindow(50, 100);
    }

    public void send(String ad)
    {
        ReceiveArea.append("我(中介)说: " + ad + "\n");
        //使滚动条滚动到最底端
        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());
        medium.relay(name, ad);
    }

    public void receive(String from, String ad)
    {
        ReceiveArea.append(from + "说: " + ad + "\n");
        //使滚动条滚动到最底端
        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());
    }
}


// Buyer.java

package Mediator1.colleague;

//具体同事类：买方

public class Buyer extends Customer
{
    public Buyer(String name)
    {
        super(name);
        ClientWindow(350, 100);
    }

    public void send(String ad)
    {
        ReceiveArea.append("我(中介)说: " + ad + "\n");
        //使滚动条滚动到最底端
        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());
        medium.relay(name, ad);
    }

    public void receive(String from, String ad)
    {
        ReceiveArea.append(from + "说: " + ad + "\n");
        //使滚动条滚动到最底端
        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());
    }
}


// Client.java

package Mediator1;

import Mediator1.colleague.*;
import Mediator1.mediator.*;

public class Client
{
    public static void main(String[] args)
    {
        Medium medium = new EstateMedium();    //房产中介
        Customer customer1, customer2;
        customer1 = new Seller("张三(卖方)");
        customer2 = new Buyer("李四(买方)");
        medium.register(customer1); //客户注册
        medium.register(customer2);
    }
}
```


#### 中介者模式的扩展

在实际开发中，通常采用以下两种方法来简化中介者模式，使开发变得更简单：
- 不定义中介者接口，把具体中介者对象实现成为单例。
- 同事对象不持有中介者，而是在需要的时f矣直接获取中介者对象并调用。

<div align=center><img src=DesignPatterns1/简化中介者模式的结构图.png width=90%></div>

```java

// SimpleColleague.java

package Mediator2;

//抽象同事类

public interface SimpleColleague
{
    void receive();
    void send();
}


// SimpleConcreteColleague1.java

package Mediator2;

public class SimpleConcreteColleague1 implements SimpleColleague
{
    public SimpleConcreteColleague1()
    {
        SimpleMediator simpleMediator = SimpleMediator.getMedium();
        simpleMediator.register(this);
    }

    public void receive() { System.out.println("具体同事类1：收到请求。"); }

    public void send()
    {
        SimpleMediator simpleMediator = SimpleMediator.getMedium();
        System.out.println("具体同事类1：发出请求...");
        simpleMediator.relay(this); //请中介者转发
    }
}


// SimpleConcreteColleague2.java

package Mediator2;

public class SimpleConcreteColleague2 implements SimpleColleague
{
    public SimpleConcreteColleague2()
    {
        SimpleMediator simpleMediator = SimpleMediator.getMedium();
        simpleMediator.register(this);
    }

    public void receive() { System.out.println("具体同事类2：收到请求。"); }

    public void send()
    {
        SimpleMediator simpleMediator = SimpleMediator.getMedium();
        System.out.println("具体同事类2：发出请求...");
        simpleMediator.relay(this); //请中介者转发
    }
}


// SimpleMediator.java

package Mediator2;

//简单单例中介者

import java.util.ArrayList;
import java.util.List;

public class SimpleMediator
{
    private static SimpleMediator simpleMediator = new SimpleMediator();
    private List<SimpleColleague> colleagues = new ArrayList<SimpleColleague>();

    private SimpleMediator(){}

    public static SimpleMediator getMedium() { return(simpleMediator); }

    public void register(SimpleColleague colleague)
    {
        if(!colleagues.contains(colleague))
        {
            colleagues.add(colleague);
        }
    }

    public void relay(SimpleColleague colleague)
    {
        for(SimpleColleague ob : colleagues)
        {
            if(!ob.equals(colleague))
            {
                ((SimpleColleague) ob).receive();
            }
        }
    }
}


// Client.java

package Mediator2;

public class Client
{
    public static void main(String[] args)
    {
        SimpleColleague colleague1, colleague2;
        colleague1 = new SimpleConcreteColleague1();
        colleague2 = new SimpleConcreteColleague2();

        colleague1.send();
        System.out.println("------------------------------");
        colleague2.send();
    }
}
```


### 迭代器模式

在现实生活以及程序设计中，经常要访问一个聚合对象中的各个元素，如“数据结构”中的链表遍历，通常的做法是将链表的创建和遍历都放在同一个类中，但这种方式不利于程序的扩展，如果要更换遍历方法就必须修改程序源代码，这违背了 “开闭原则”。

既然将遍历方法封装在聚合类中不可取，那么聚合类中不提供遍历方法，将遍历方法由用户自己实现是否可行呢？答案是同样不可取，因为这种方式会存在两个缺点：暴露了聚合类的内部表示，使其数据不安全；增加了客户的负担。

“迭代器模式”能较好地克服以上缺点，它在客户访问类与聚合类之间插入一个迭代器，这分离了聚合对象与其遍历行为，对客户也隐藏了其内部细节，且满足“单一职责原则”和“开闭原则”，如Java中的`Collection`、`List`、`Set`、`Map`等都包含了迭代器。

**迭代器（Iterator）模式**的定义：提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。

迭代器模式是一种对象行为型模式，其主要优点如下：

- 访问一个聚合对象的内容而无须暴露它的内部表示。
- 遍历任务交由迭代器完成，这简化了聚合类。
- 它支持以不同方式遍历一个聚合，甚至可以自定义迭代器的子类以支持新的遍历。
- 增加新的聚合类和迭代器类都很方便，无须修改原有代码。
- 封装性良好，为遍历不同的聚合结构提供一个统一的接口。

其主要缺点是：增加了类的个数，这在一定程度上增加了系统的复杂性。

迭代器模式通常在以下几种情况使用：

- 当需要为聚合对象提供多种遍历方式时。
- 当需要为遍历不同的聚合结构提供一个统一的接口时。
- 当访问一个聚合对象的内容而无须暴露其内部细节的表示时。

由于聚合与迭代器的关系非常密切，所以大多数语言在实现聚合类时都提供了迭代器类，因此大数情况下使用语言中已有的聚合类的迭代器就已经够了。


#### 迭代器模式的结构

迭代器模式是通过将聚合对象的遍历行为分离出来，抽象成迭代器类来实现的，其目的是在不暴露聚合对象的内部结构的情况下，让外部代码透明地访问聚合的内部数据。

迭代器模式主要包含以下部分：

- 抽象聚合（Aggregate）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
- 具体聚合（Concrete Aggregate）角色：实现抽象聚合类，返回一个具体迭代器的实例。
- 抽象迭代器（Iterator）角色：定义访问和遍历聚合元素的接口，通常包含`hasNext()`、`first()`、`next()`等方法。
- 具体迭代器（Concrete lterator）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。

<div align=center><img src=DesignPatterns1/迭代器模式的结构图.png width=70%></div>

```java

// Aggregate.java

package Iterator;

//抽象聚合

public interface Aggregate
{
    public void add(Object obj);
    public void remove(Object obj);
    public Iterator getIterator();
}


// ConcreteAggregate.java

package Iterator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate
{
    private List<Object> list = new ArrayList<Object>();

    public void add(Object obj)
    {
        list.add(obj);
    }

    public void remove(Object obj)
    {
        list.remove(obj);
    }

    public Iterator getIterator()
    {
        return(new ConcreteIterator(list));
    }
}


// Iterator.java

package Iterator;

public interface Iterator
{
    Object first();
    Object next();
    boolean hasNext();
}


// ConcreteIterator.java

package Iterator;

import java.util.List;

public class ConcreteIterator implements Iterator
{
    private List<Object> list = null;
    private int index = -1;

    public ConcreteIterator(List<Object> list)
    {
        this.list = list;
    }

    public boolean hasNext()
    {
        if(index < list.size()-1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Object first()
    {
        index = 0;
        Object obj = list.get(index);;
        return obj;
    }

    public Object next()
    {
        Object obj = null;

        if(this.hasNext())
        {
            obj = list.get(++index);
        }
        return obj;
    }
}


// Client.java

package Iterator;

public class Client
{
    public static void main(String[] args)
    {
        Aggregate aggregate = new ConcreteAggregate();
        aggregate.add("上海交通大学");
        aggregate.add("复旦大学");

        System.out.print("上海重点高校有：");
        Iterator iterator = aggregate.getIterator();
        while(iterator.hasNext())
        {
            Object ob = iterator.next();
            System.out.print(ob.toString() + "\t");
        }

        Object ob = iterator.first();
        System.out.println("\nFirst：" + ob.toString());
    }
}
```


#### 用迭代器模式编写一个浏览婺源旅游风景图的程序

婺源的名胜古迹较多，要设计一个查看相关景点图片和简介的程序，用“迭代器模式”设计比较合适。

首先，设计一个婺源景点（`WyViewSpot`）类来保存每张图片的名称与简介；再设计一个景点集（`ViewSpotSet`）接口，它是`抽象聚合类`，提供了增加和删除婺源景点的方法，以及获取迭代器的方法。

然后，定义一个婺源景点集（`WyViewSpotSet`）类，它是`具体聚合类`，用`ArrayList`来保存所有景点信息，并实现父类中的抽象方法；再定义婺源景点的`抽象迭代器（ViewSpotltemtor）`接口，其中包含了查看景点信息的相关方法。

最后，定义婺源景点的`具体迭代器（WyViewSpotlterator）类`，它实现了父类的抽象方法；客户端程序设计成窗口程序，它初始化婺源景点集（`ViewSpotSet`）中的数据，并实现`ActionListener`接口，它通过婺源景点迭代器（`ViewSpotlterator`）来査看婺源景点（`WyViewSpot`）的信息。

<div align=center><img src=DesignPatterns1/用迭代器模式编写一个浏览婺源旅游风景图的程序.png width=90%></div>

```java

// WyViewSpot.java

package Iterator1;

//婺源景点类

public class WyViewSpot
{
    private String Name;
    private String Introduce;

    WyViewSpot(String Name, String Introduce)
    {
        this.Name = Name;
        this.Introduce = Introduce;
    }

    public String getName()
    {
        return Name;
    }

    public String getIntroduce()
    {
        return Introduce;
    }
}


// ViewSpotSet.java

package Iterator1;

//抽象聚合：婺源景点集接口

public interface ViewSpotSet
{
    void add(WyViewSpot wyViewSpot);
    void remove(WyViewSpot wyViewSpot);
    ViewSpotIterator getIterator();
}


// WyViewSpotSet.java

package Iterator1;

//具体聚合：婺源景点集

import java.util.ArrayList;

public class WyViewSpotSet implements ViewSpotSet
{
    private ArrayList<WyViewSpot> list = new ArrayList<WyViewSpot>();

    public void add(WyViewSpot wyViewSpot)
    {
        list.add(wyViewSpot);
    }

    public void remove(WyViewSpot wyViewSpot)
    {
        list.remove(wyViewSpot);
    }

    public ViewSpotIterator getIterator()
    {
        return(new WyViewSpotIterator(list));
    }
}


// ViewSpotIterator.java

package Iterator1;

//抽象迭代器：婺源景点迭代器接口

public interface ViewSpotIterator
{
    boolean hasNext();
    WyViewSpot first();
    WyViewSpot next();
    WyViewSpot previous();
    WyViewSpot last();
}


// WyViewSpotIterator.java

package Iterator1;

//具体迭代器：婺源景点迭代器

import java.util.ArrayList;

public class WyViewSpotIterator implements ViewSpotIterator
{
    private ArrayList<WyViewSpot> list = null;
    private int index = -1;
    WyViewSpot wyViewSpot = null;

    public WyViewSpotIterator(ArrayList<WyViewSpot> list)
    {
        this.list = list;
    }

    public boolean hasNext()
    {
        if(index < list.size() - 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public WyViewSpot first()
    {
        index = 0;
        wyViewSpot = list.get(index);
        return wyViewSpot;
    }

    public WyViewSpot next()
    {
        if(this.hasNext())
        {
            wyViewSpot = list.get(++index);
        }
        return wyViewSpot;
    }

    public WyViewSpot previous()
    {
        if(index > 0)
        {
            wyViewSpot = list.get(--index);
        }
        return wyViewSpot;
    }

    public WyViewSpot last()
    {
        index = list.size()-1;
        wyViewSpot = list.get(index);
        return wyViewSpot;
    }
}


// PictureFrame.java

package Iterator1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PictureFrame extends JFrame implements ActionListener
{
    ViewSpotSet viewSpotSet; //婺源景点集接口
    ViewSpotIterator viewSpotIterator; //婺源景点迭代器接口
    WyViewSpot wyViewSpot;    //婺源景点类

    public PictureFrame()
    {
        super("中国最美乡村“婺源”的部分风景图");
        this.setResizable(false);
        viewSpotSet = new WyViewSpotSet();
        viewSpotSet.add(new WyViewSpot("江湾", "江湾景区是婺源的一个国家5A级旅游景区，景区内有萧江宗祠、永思街、滕家老屋、婺源人家、乡贤园、百工坊等一大批古建筑，精美绝伦，做工精细。"));
        viewSpotSet.add(new WyViewSpot("李坑", "李坑村是一个以李姓聚居为主的古村落，是国家4A级旅游景区，其建筑风格独特，是著名的徽派建筑，给人一种安静、祥和的感觉。"));
        viewSpotSet.add(new WyViewSpot("思溪延村", "思溪延村位于婺源县思口镇境内，始建于南宋庆元五年（1199年），当时建村者俞氏以（鱼）思清溪水而名。"));
        viewSpotSet.add(new WyViewSpot("晓起村", "晓起有“中国茶文化第一村”与“国家级生态示范村”之美誉，村屋多为清代建筑，风格各具特色，村中小巷均铺青石，曲曲折折，回环如棋局。"));
        viewSpotSet.add(new WyViewSpot("菊径村", "菊径村形状为山环水绕型，小河成大半圆型，绕村庄将近一周，四周为高山环绕，符合中国的八卦“后山前水”设计，当地人称“脸盆村”。"));
        viewSpotSet.add(new WyViewSpot("篁岭", "篁岭是著名的“晒秋”文化起源地，也是一座距今近六百历史的徽州古村；篁岭属典型山居村落，民居围绕水口呈扇形梯状错落排布。"));
        viewSpotSet.add(new WyViewSpot("彩虹桥", "彩虹桥是婺源颇有特色的带顶的桥——廊桥，其不仅造型优美，而且它可在雨天里供行人歇脚，其名取自唐诗“两水夹明镜，双桥落彩虹”。"));
        viewSpotSet.add(new WyViewSpot("卧龙谷", "卧龙谷是国家4A级旅游区，这里飞泉瀑流泄银吐玉、彩池幽潭碧绿清新、山峰岩石挺拔奇巧，活脱脱一幅天然泼墨山水画。"));
        viewSpotIterator = viewSpotSet.getIterator(); //获取婺源景点迭代器
        wyViewSpot = viewSpotIterator.first();
        this.showPicture(wyViewSpot.getName(), wyViewSpot.getIntroduce());
    }

    //显示图片
    void showPicture(String Name, String Introduce)
    {
        Container container = this.getContentPane();
        JPanel picturePanel = new JPanel();
        JPanel controlPanel = new JPanel();
        String FileName="src/Iterator1/source/" + Name + ".jpg";
        JLabel jLabel = new JLabel(Name, new ImageIcon(FileName), JLabel.CENTER);
        JTextArea jTextArea = new JTextArea(Introduce);
        jLabel.setHorizontalTextPosition(JLabel.CENTER);
        jLabel.setVerticalTextPosition(JLabel.TOP);
        jLabel.setFont(new Font("宋体", Font.BOLD, 20));
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        //ta.setBackground(Color.orange);
        picturePanel.setLayout(new BorderLayout(5,5));
        picturePanel.add("Center",jLabel);
        picturePanel.add("South",jTextArea);

        JButton first, last, next, previous;
        first = new JButton("第一张");
        next = new JButton("下一张");
        previous = new JButton("上一张");
        last = new JButton("最末张");
        first.addActionListener(this);
        next.addActionListener(this);
        previous.addActionListener(this);
        last.addActionListener(this);
        controlPanel.add(first);
        controlPanel.add(next);
        controlPanel.add(previous);
        controlPanel.add(last);

        container.add("Center", picturePanel);
        container.add("South", controlPanel);
        this.setSize(630, 550);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        String command = arg0.getActionCommand();

        if(command.equals("第一张"))
        {
            wyViewSpot = viewSpotIterator.first();
            this.showPicture(wyViewSpot.getName(), wyViewSpot.getIntroduce());
        }
        else if(command.equals("下一张"))
        {
            wyViewSpot = viewSpotIterator.next();
            this.showPicture(wyViewSpot.getName(), wyViewSpot.getIntroduce());
        }
        else if(command.equals("上一张"))
        {
            wyViewSpot = viewSpotIterator.previous();
            this.showPicture(wyViewSpot.getName(), wyViewSpot.getIntroduce());
        }
        else if(command.equals("最末张"))
        {
            wyViewSpot = viewSpotIterator.last();
            this.showPicture(wyViewSpot.getName(), wyViewSpot.getIntroduce());
        }
    }
}


// Client.java

package Iterator1;

public class Client
{
    public static void main(String[] args)
    {
        new PictureFrame();
    }
}
```


### 访问者模式

在现实生活中，<font color=red>有些集合对象中存在多种不同的元素，且每种元素也存在多种不同的访问者和处理方式</font>。例如，<font color=red>公园中存在多个景点，也存在多个游客，不同的游客对同一个景点的评价可能不同</font>；医院医生开的处方单中包含多种药元素，査看它的划价员和药房工作人员对它的处理方式也不同，划价员根据处方单上面的药品名和数量进行划价，药房工作人员根据处方单的内容进行抓药。

这样的例子还有很多，例如，电影或电视剧中的人物角色，不同的观众对他们的评价也不同；还有顾客在商场购物时放在“购物车”中的商品，顾客主要关心所选商品的性价比，而收银员关心的是商品的价格和数量。

这些被处理的数据元素相对稳定而访问方式多种多样的数据结构，如果用“访问者模式”来处理比较方便。访问者模式能把处理方法从数据结构中分离出来，并可以根据需要增加新的处理方法，且不用修改原来的程序代码与数据结构，这提高了程序的扩展性和灵活性。

**访问者（Visitor）模式**的定义：将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，为数据结构中的每个元素提供多种访问方式。它将对数据的操作与数据结构进行分离，是行为类模式中最复杂的一种模式。

访问者（Visitor）模式是一种对象行为型模式，其主要优点如下：
- 扩展性好。能够在不修改对象结构中的元素的情况下，为对象结构中的元素添加新的功能。
- 复用性好。可以通过访问者来定义整个对象结构通用的功能，从而提高系统的复用程度。
- 灵活性好。访问者模式将数据结构与作用于结构上的操作解耦，使得操作集合可相对自由地演化而不影响系统的数据结构。
- 符合单一职责原则。访问者模式把相关的行为封装在一起，构成一个访问者，使每一个访问者的功能都比较单一。

访问者（Visitor）模式的主要缺点如下：
- 增加新的元素类很困难。在访问者模式中，每增加一个新的元素类，都要在每一个具体访问者类中增加相应的具体操作，这违背了“开闭原则”。
- 破坏封装。访问者模式中具体元素对访问者公布细节，这破坏了对象的封装性。
- 违反了依赖倒置原则。访问者模式依赖了具体类，而没有依赖抽象类。

访问者（Visitor）模式的应用场景：
- 对象结构相对稳定，但其操作算法经常变化的程序。
- 对象结构中的对象需要提供多种不同且不相关的操作，而且要避免让这些操作的变化影响对象的结构。
- 对象结构包含很多类型的对象，希望对这些对象实施一些依赖于其具体类型的操作。


#### 访问者模式的结构

访问者（Visitor）模式实现的关键是如何将作用于元素的操作分离出来封装成独立的类。

访问者模式包含以下主要部分：

- 抽象访问者（Visitor）角色：定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作`visit()`，该操作中的参数类型标识了被访问的具体元素。
- 具体访问者（Concrete Visitor）角色：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
- 抽象元素（Element）角色：声明一个包含接受操作`accept()`的接口，被接受的访问者对象作为`accept()`方法的参数。
- 具体元素（Concrete Element）角色：实现抽象元素角色提供的`accept()`操作，其方法体通常都是`visitor.visit(this)`，另外具体元素中可能还包含本身业务逻辑的相关操作。
- 对象结构（Object Structure）角色：是一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常由`List`、`Set`、`Map`等聚合类实现。

<div align=center><img src=DesignPatterns1/访问者模式的结构.png width=90%></div>

```java

// Visitor.java

package Visitor;

//抽象访问者

public interface Visitor
{
    void visit(ConcreteElementA element);
    void visit(ConcreteElementB element);
}


// ConcreteVisitorA.java

package Visitor;

public class ConcreteVisitorA implements Visitor
{
    public void visit(ConcreteElementA element)
    {
        System.out.println("具体访问者A访问-->" + element.operationA());
    }

    public void visit(ConcreteElementB element)
    {
        System.out.println("具体访问者A访问-->" + element.operationB());
    }
}


// ConcreteVisitorB.java

package Visitor;

public class ConcreteVisitorB implements Visitor
{
    public void visit(ConcreteElementA element)
    {
        System.out.println("具体访问者B访问-->" + element.operationA());
    }

    public void visit(ConcreteElementB element)
    {
        System.out.println("具体访问者B访问-->" + element.operationB());
    }
}


// Element.java

package Visitor;

//抽象元素类

public interface Element
{
    void accept(Visitor visitor);
}


// ConcreteElementA.java

package Visitor;

public class ConcreteElementA implements Element
{
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    public String operationA()
    {
        return "具体元素A的操作。";
    }
}


// ConcreteElementB.java

package Visitor;

public class ConcreteElementB implements Element
{
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    public String operationB()
    {
        return "具体元素B的操作。";
    }
}


// ObjectStructure.java

package Visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectStructure
{
    private List<Element> list = new ArrayList<Element>();

    public void accept(Visitor visitor)
    {
        Iterator<Element> elementIterator = list.iterator();
        while(elementIterator.hasNext())
        {
            ((Element) elementIterator.next()).accept(visitor);
        }
    }

    public void add(Element element)
    {
        list.add(element);
    }

    public void remove(Element element)
    {
        list.remove(element);
    }
}


// Client.java

package Visitor;

public class Client
{
    public static void main(String[] args)
    {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.add(new ConcreteElementA());
        objectStructure.add(new ConcreteElementB());

        Visitor visitor = new ConcreteVisitorA();
        objectStructure.accept(visitor);
        System.out.println("----------------------------------------------");
        visitor = new ConcreteVisitorB();
        objectStructure.accept(visitor);
    }
}
```

运行结果：
```
具体访问者A访问-->具体元素A的操作。
具体访问者A访问-->具体元素B的操作。
-------------------------------
具体访问者B访问-->具体元素A的操作。
具体访问者B访问-->具体元素B的操作。
```


#### 用访问者模式模拟艺术公司与造币公司

艺术公司利用“铜”可以设计出铜像，利用“纸”可以画出图画；造币公司利用“铜”可以印出铜币，利用“纸”可以印出纸币。对“铜”和“纸”这两种元素，两个公司的处理方法不同，所以该实例用访问者模式来实现比较适合。

首先，定义一个公司（`Company`）接口，它是`抽象访问者`，提供了两个根据纸（`Paper`）或铜（`Cuprum`）这两种元素创建作品的方法；再定义艺术公司（`ArtCompany`）类和造币公司（`Mint`）类，它们是`具体访问者`，实现了父接口的方法；然后，定义一个材料（`Material`）接口，它是`抽象元素`，提供了`accept(Company visitor)`方法来接受访问者（`Company`）对象访问；再定义纸（`Paper`）类和铜（`Cuprum`）类，它们是`具体元素类`，实现了父接口中的方法；最后，定义一个材料集（`SetMaterial`）类，它是`对象结构角色`，拥有保存所有元素的容器`List`，并提供让访问者对象遍历容器中的所有元素的`accept(Company visitor)`方法；客户类设计成窗体程序，它提供材料集（`SetMaterial`）对象供访问者（`Company`）对象访问，实现了`ItemListener`接口，处理用户的事件请求。

<div align=center><img src=DesignPatterns1/用访问者模式模拟艺术公司与造币公司.png width=90%></div>

```java

// Company.java

package Visitor1;

//抽象访问者:公司

public interface Company
{
    String create(Paper element);
    String create(Cuprum element);
}


// ArtCompany.java

package Visitor1;

//具体访问者：艺术公司

public class ArtCompany implements Company
{
    public String create(Paper element)
    {
        return "讲学图";
    }

    public String create(Cuprum element)
    {
        return "朱熹铜像";
    }
}


// Mint.java

package Visitor1;

//具体访问者：造币公司

public class Mint implements Company
{
    public String create(Paper element)
    {
        return "纸币";
    }

    public String create(Cuprum element)
    {
        return "铜币";
    }
}


// Material.java

package Visitor1;

//抽象元素：材料

public interface Material
{
    String accept(Company visitor);
}


// Paper.java

package Visitor1;

//具体元素：纸

public class Paper implements Material
{
    public String accept(Company company)
    {
        return(company.create(this));
    }
}


// Cuprum.java

package Visitor1;

//具体元素：铜

public class Cuprum implements Material
{
    public String accept(Company company)
    {
        return(company.create(this));
    }
}


// SetMaterial.java

package Visitor1;

//对象结构角色:材料集

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetMaterial
{
    private List<Material> list = new ArrayList<Material>();

    public String accept(Company visitor)
    {
        Iterator<Material> materialIterator = list.iterator();
        String tmp = "";

        while(materialIterator.hasNext())
        {
            tmp += ((Material) materialIterator.next()).accept(visitor) + " ";
        }
        return tmp; //返回某公司的作品集
    }

    public void add(Material element)
    {
        list.add(element);
    }

    public void remove(Material element)
    {
        list.remove(element);
    }
}


// MaterialWin.java

package Visitor1;

//窗体类

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MaterialWin extends JFrame implements ItemListener
{
    JPanel CenterJP;
    SetMaterial setMaterial;    //材料集对象
    Company visitor1, visitor2;    //访问者对象
    String[] select;

    MaterialWin()
    {
        super("利用访问者模式设计艺术公司和造币公司");
        JRadioButton Art;
        JRadioButton Mint;
        setMaterial = new SetMaterial();
        setMaterial.add(new Cuprum());
        setMaterial.add(new Paper());
        visitor1 = new ArtCompany();//艺术公司
        visitor2 = new Mint(); //造币公司
        this.setBounds(10,10,750,350);
        this.setResizable(true);

        CenterJP = new JPanel();
        this.add("Center", CenterJP);
        JPanel SouthJP = new JPanel();
        JLabel jLabel = new JLabel("原材料有：铜和纸，请选择生产公司：");
        Art = new JRadioButton("艺术公司",true);
        Mint = new JRadioButton("造币公司");
        Art.addItemListener(this);
        Mint.addItemListener(this);
        ButtonGroup group = new ButtonGroup();
        group.add(Art);
        group.add(Mint);
        SouthJP.add(jLabel);
        SouthJP.add(Art);
        SouthJP.add(Mint);
        this.add("South", SouthJP);
        select = (setMaterial.accept(visitor1)).split(" ");    //获取产品名
        showPicture(select[0], select[1]);    //显示产品
    }

    //显示图片
    void showPicture(String Cuprum, String paper)
    {
        CenterJP.removeAll();    //清除面板内容
        CenterJP.repaint();    //刷新屏幕
        String FileName1 = "src/Visitor1/Picture/" + Cuprum + ".jpg";
        String FileName2 = "src/Visitor1/Picture/" + paper + ".jpg";
        JLabel jLabel1 = new JLabel(new ImageIcon(FileName1), JLabel.CENTER);
        JLabel jLabel2 = new JLabel(new ImageIcon(FileName2), JLabel.CENTER);
        CenterJP.add(jLabel1);
        CenterJP.add(jLabel2);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void itemStateChanged(ItemEvent arg0)
    {
        JRadioButton jRadioButton = (JRadioButton) arg0.getSource();
        if (jRadioButton.isSelected())
        {
            if (jRadioButton.getText() == "造币公司")
            {
                select = (setMaterial.accept(visitor2)).split(" ");
            }
            else
            {
                select = (setMaterial.accept(visitor1)).split(" ");
            }

            showPicture(select[0], select[1]);    //显示选择的产品
        }
    }
}


// Client.java

package Visitor1;

public class Client
{
    public static void main(String[] args)
    {
        new MaterialWin();
    }
}
```


### 备忘录模式

每个人都有犯错误的时候，都希望有种“后悔药”能弥补自己的过失，让自己重新开始，但现实是残酷的。在计算机应用中，客户同样会常常犯错误，能否提供“后悔药”给他们呢？当然是可以的，而且是有必要的。这个功能由“备忘录模式”来实现。

其实很多应用软件都提供了这项功能，如Word、记事本、Photoshop、Eclipse等软件在编辑时按`Ctrl+Z`组合键时能撤销当前操作，使文档恢复到之前的状态；还有在IE中的后退键、数据库事务管理中的回滚操作、玩游戏时的中间结果存档功能、数据库与操作系统的备份操作、棋类游戏中的悔棋功能等都属于这类。

备忘录模式能记录一个对象的内部状态，当用户后悔时能撤销当前操作，使数据恢复到它原先的状态。

**备忘录（Memento）模式**的定义：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。该模式又叫**快照模式**。

备忘录模式是一种对象行为型模式，其主要优点如下：
- 提供了一种可以恢复状态的机制。当用户需要时能够比较方便地将数据恢复到某个历史的状态。
- 实现了内部状态的封装。除了创建它的发起人之外，其他对象都不能够访问这些状态信息。
- 简化了发起人类。发起人不需要管理和保存其内部状态的各个备份，所有状态信息都保存在备忘录中，并由管理者进行管理，这符合单一职责原则。

其主要缺点是：资源消耗大。如果要保存的内部状态信息过多或者特别频繁，将会占用比较大的内存资源。

备忘录模式的应用场景：

- 需要保存与恢复数据的场景，如玩游戏时的中间结果的存档功能。
- 需要提供一个可回滚操作的场景，如Word、记事本、Photoshop，Eclipse等软件在编辑时按`Ctrl+Z`组合键，还有数据库中事务操作。


#### 备忘录模式的结构

备忘录模式的核心是设计备忘录类以及用于管理备忘录的管理者类。

备忘录模式的主要部分如下：

- 发起人（Originator）角色：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，它可以访问备忘录里的所有信息。
- 备忘录（Memento）角色：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。
- 管理者（Caretaker）角色：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。

<div align=center><img src=DesignPatterns1/备忘录模式的结构图.png width=80%></div>

```java

// Memento.java

package Memento;

//备忘录

public class Memento
{
    private String state;

    public Memento(String state)
    {
        this.state = state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
}


// Originator.java

package Memento;

//发起人

public class Originator
{
    private String state;

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public Memento createMemento()
    {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento)
    {
        this.setState(memento.getState());
    }
}


// Caretaker.java

package Memento;

//管理者

public class Caretaker
{
    private Memento memento;

    public void setMemento(Memento m)
    {
        memento = m;
    }

    public Memento getMemento()
    {
        return memento;
    }
}


// Client.java

package Memento;

public class Client
{
    public static void main(String[] args)
    {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("S0");
        System.out.println("初始状态:" + originator.getState());
        caretaker.setMemento(originator.createMemento()); //保存状态

        originator.setState("S1");
        System.out.println("新的状态:" + originator.getState());

        originator.restoreMemento(caretaker.getMemento()); //恢复状态
        System.out.println("恢复状态:" + originator.getState());
    }
}
```

运行结果：
```
初始状态:S0
新的状态:S1
恢复状态:S0
```


#### 用备忘录模式设计相亲游戏

假如有西施、王昭君、貂蝉、杨玉环四大美女同你相亲，你可以选择其中一位作为你的爱人；当然，<font color=red>如果你对前面的选择不满意，还可以重新选择</font>，但希望你不要太花心；这个游戏提供后悔功能，用“备忘录模式”设计比较合适。

首先，先设计一个美女（`Girl`）类，它是`备忘录角色`，提供了获取和存储美女信息的功能；然后，设计一个相亲者（`You`）类，它是`发起人角色`，它记录当前时刻的内部状态信息（临时妻子的姓名），并提供创建备忘录和恢复备忘录数据的功能；最后，定义一个美女栈（`GirlStack`）类，它是`管理者角色`，负责对备忘录进行管理，用于保存相亲者（`You`）前面选过的美女信息，不过最多只能保存4个，提供后悔功能。

客户类设计成窗体程序，它包含美女栈（`GirlStack`）对象和相亲者（`You`）对象，它实现了`ActionListener`接口的事件处理方法`actionPerformed(ActionEvent e)`，并将4大美女图像和相亲者（You）选择的美女图像在窗体中显示出来。

<div align=center><img src=DesignPatterns1/用备忘录模式设计相亲游戏.png width=80%></div>

```java

// Girl.java

package Memento1;

//备忘录：美女

public class Girl
{
    private String name;

    public Girl(String name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}


// You.java

package Memento1;

//发起人：您

public class You
{
    private String wifeName;    //妻子

    public void setWife(String name)
    {
        wifeName = name;
    }

    public String getWife()
    {
        return wifeName;
    }

    public Girl createMemento()
    {
        return new Girl(wifeName);
    }

    public void restoreMemento(Girl girl)
    {
        setWife(girl.getName());
    }
}


// GirlStack.java

package Memento1;

//管理者：美女栈

public class GirlStack
{
    private Girl girls[];
    private int top;

    GirlStack()
    {
        girls = new Girl[5];
        top = -1;
    }

    public boolean push(Girl girl)
    {
        if(top >= 4)
        {
            System.out.println("你太花心了，变来变去的！");
            return false;
        }
        else
        {
            girls[++top] = girl;
            return true;
        }
    }

    public Girl pop()
    {
        if(top <= 0)
        {
            System.out.println("美女栈空了！");
            return girls[0];
        }
        else return girls[top--];
    }
}


// DatingGameWin.java

package Memento1;

//客户窗体类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatingGameWin extends JFrame implements ActionListener
{
    JPanel CenterJP, EastJP;
    JRadioButton girl1, girl2, girl3, girl4;
    JButton button1, button2;
    String FileName;
    JLabel jLabel;
    You you;
    GirlStack girls;

    DatingGameWin()
    {
        super("利用备忘录模式设计相亲游戏");
        you = new You();
        girls = new GirlStack();
        this.setBounds(0,0,900,380);
        this.setResizable(true);
        FileName = "src/Memento1/Photo/四大美女.jpg";
        jLabel = new JLabel(new ImageIcon(FileName), JLabel.CENTER);
        CenterJP = new JPanel();
        CenterJP.setLayout(new GridLayout(1,4));
        CenterJP.setBorder(BorderFactory.createTitledBorder("四大美女如下："));
        CenterJP.add(jLabel);
        this.add("Center", CenterJP);
        EastJP = new JPanel();
        EastJP.setLayout(new GridLayout(1,1));
        EastJP.setBorder(BorderFactory.createTitledBorder("您选择的爱人是："));
        this.add("East", EastJP);
        JPanel SouthJP = new JPanel();
        JLabel info = new JLabel("四大美女有“沉鱼落雁之容、闭月羞花之貌”，您选择谁？");
        girl1 = new JRadioButton("西施",true);
        girl2 = new JRadioButton("貂蝉");
        girl3 = new JRadioButton("王昭君");
        girl4 = new JRadioButton("杨玉环");
        button1 = new JButton("确定");
        button2 = new JButton("返回");
        ButtonGroup group = new ButtonGroup();
        group.add(girl1);
        group.add(girl2);
        group.add(girl3);
        group.add(girl4);
        SouthJP.add(info);
        SouthJP.add(girl1);
        SouthJP.add(girl2);
        SouthJP.add(girl3);
        SouthJP.add(girl4);
        SouthJP.add(button1);
        SouthJP.add(button2);
        button1.addActionListener(this);
        button2.addActionListener(this);
        this.add("South", SouthJP);
        showPicture("空白");
        you.setWife("空白");
        girls.push(you.createMemento());    //保存状态
    }

    //显示图片
    void showPicture(String name)
    {
        EastJP.removeAll(); //清除面板内容
        EastJP.repaint(); //刷新屏幕
        you.setWife(name);
        FileName="src/Memento1/Photo/" + name + ".jpg";
        jLabel = new JLabel(new ImageIcon(FileName), JLabel.CENTER);
        EastJP.add(jLabel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        boolean ok = false;
        if(e.getSource() == button1)
        {
            ok = girls.push(you.createMemento());    //保存状态
            if(ok && girl1.isSelected())
            {
                showPicture("西施");
            }
            else if(ok && girl2.isSelected())
            {
                showPicture("貂蝉");
            }
            else if(ok && girl3.isSelected())
            {
                showPicture("王昭君");
            }
            else if(ok && girl4.isSelected())
            {
                showPicture("杨玉环");
            }
        }
        else if(e.getSource() == button2)
        {
            you.restoreMemento(girls.pop()); //恢复状态
            showPicture(you.getWife());
        }
    }
}


// Client.java

package Memento1;

public class Client
{
    public static void main(String[] args)
    {
        new DatingGameWin();
    }
}
```


#### 备忘录模式的扩展

备忘录模式中，有单状态备份的例子，也有多状态备份的例子。下面介绍备忘录模式如何同`原型模式`混合使用。在备忘录模式中，通过定义“备忘录”来备份“发起人”的信息，而原型模式的`clone()`方法具有自备份功能，所以，如果让发起人实现`Cloneable`接口就有备份自己的功能，这时可以删除备忘录类。

<div align=center><img src=DesignPatterns1/带原型的备忘录模式的结构图.png width=80%></div>

```java

// OriginatorPrototype.java

package Memento2;

//发起人原型

public class OriginatorPrototype implements Cloneable
{
    private String state;

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public OriginatorPrototype createMemento()
    {
        return this.clone();
    }

    public void restoreMemento(OriginatorPrototype opt)
    {
        this.setState(opt.getState());
    }

    public OriginatorPrototype clone()
    {
        try
        {
            return (OriginatorPrototype) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}


// PrototypeCaretaker.java

package Memento2;

//原型管理者

public class PrototypeCaretaker
{
    private OriginatorPrototype opt;

    public void setMemento(OriginatorPrototype opt)
    {
        this.opt = opt;
    }

    public OriginatorPrototype getMemento()
    {
        return opt;
    }
}


// Client.java

package Memento2;

public class Client
{
    public static void main(String[] args)
    {
        OriginatorPrototype originatorPrototype = new OriginatorPrototype();
        PrototypeCaretaker caretaker = new PrototypeCaretaker();

        originatorPrototype.setState("S0");
        System.out.println("初始状态:" + originatorPrototype.getState());
        caretaker.setMemento(originatorPrototype.createMemento()); //保存状态

        originatorPrototype.setState("S1");
        System.out.println("新的状态:" + originatorPrototype.getState());

        originatorPrototype.restoreMemento(caretaker.getMemento()); //恢复状态
        System.out.println("恢复状态:" + originatorPrototype.getState());
    }
}
```


### 解释器模式

在软件开发中，会遇到有些问题多次重复出现，而且有一定的相似性和规律性。如果将它们归纳成一种简单的语言，那么这些问题实例将是该语言的一些句子，这样就可以用“编译原理”中的解释器模式来实现了。

虽然使用解释器模式的实例不是很多，但对于满足以上特点，且对运行效率要求不是很高的应用实例，如果用解释器模式来实现，其效果是非常好的。

**解释器（Interpreter）模式**的定义：给分析对象定义一个语言，并定义该语言的文法表示，再设计一个解析器来解释语言中的句子。也就是说，用编译语言的方式来分析应用中的实例。这种模式实现了文法表达式处理的接口，该接口解释一个特定的上下文。

这里提到的文法和句子的概念同编译原理中的描述相同，“文法”指语言的语法规则，而“句子”是语言集中的元素。例如，汉语中的句子有很多，“我是中国人”是其中的一个句子，可以用一棵语法树来直观地描述语言中的句子。

解释器模式是一种类行为型模式，其主要优点如下：
- 扩展性好。由于在解释器模式中使用类来表示语言的文法规则，因此可以通过继承等机制来改变或扩展文法。
- 容易实现。在语法树中的每个表达式节点类都是相似的，所以实现其文法较为容易。

解释器模式的主要缺点如下：
- 执行效率较低。解释器模式中通常使用大量的循环和递归调用，当要解释的句子较复杂时，其运行速度很慢，且代码的调试过程也比较麻烦。
- 会引起类膨胀。解释器模式中的每条规则至少需要定义一个类，当包含的文法规则很多时，类的个数将急剧增加，导致系统难以管理与维护。
- 可应用的场景比较少。在软件开发中，需要定义语言文法的应用实例非常少，所以这种模式很少被使用到。

解释器模式应用场景：

- 当语言的文法较为简单，且执行效率不是关键问题时。
- 当问题重复出现，且可以用一种简单的语言来进行表达时。
- 当一个语言需要解释执行，并且语言中的句子可以表示为一个抽象语法树的时候，如`XML`文档解释。

注意：解释器模式在实际的软件开发中使用比较少，因为它会引起效率、性能以及维护等问题。如果碰到对表达式的解释，在Java中可以用`Expression4J`或`Jep`等来设计。


#### 解释器模式结构

解释器模式常用于对简单语言的编译或分析实例中，为了掌握好它的结构与实现，必须先了解编译原理中的“文法、句子、语法树”等相关概念。

- 文法
文法是用于描述语言的语法结构的形式规则。没有规矩不成方圆，不管是机器语言还是自然语言，都有它自己的文法规则。例如，中文中的“句子”的文法如下：
    ```
    〈句子〉::=〈主语〉〈谓语〉〈宾语〉
    〈主语〉::=〈代词〉|〈名词〉
    〈谓语〉::=〈动词〉
    〈宾语〉::=〈代词〉|〈名词〉
    〈代词〉你|我|他
    〈名词〉7大学生I筱霞I英语
    〈动词〉::=是|学习
    ```
符号`::=`表示`定义为`的意思，用`〈 〉`括住的是非终结符，没有括住的是终结符。

- 句子
句子是语言的基本单位，是语言集中的一个元素，它由终结符构成，能由“文法”推导出。例如，上述文法可以推出“我是大学生”，所以它是句子。

- 语法树
语法树是句子结构的一种树型表示，它代表了句子的推导结果，它有利于理解句子语法结构的层次。

<div align=center><img src=DesignPatterns1/语法树.png width=50%></div>

有了以上基础知识，现在来介绍解释器模式的结构就简单了。

解释器模式的结构与组合模式相似，不过其包含的组成元素比组合模式多，而且组合模式是对象结构型模式，而解释器模式是类行为型模式。

解释器模式包含以下主要部分：

- 抽象表达式（Abstract Expression）角色：定义解释器的接口，约定解释器的解释操作，主要包含解释方法`interpret()`。
- 终结符表达式（Terminal Expression）角色：是抽象表达式的子类，用来实现文法中与终结符相关的操作，文法中的每一个终结符都有一个具体终结表达式与之相对应。
- 非终结符表达式（Nonterminal Expression）角色：也是抽象表达式的子类，用来实现文法中与非终结符相关的操作，文法中的每条规则都对应于一个非终结符表达式。
- 环境（Context）角色：通常包含各个解释器需要的数据或是公共的功能，一般用来传递被所有解释器共享的数据，后面的解释器可以从这里获取这些值。
- 客户端（Client）：主要任务是将需要分析的句子或表达式转换成使用解释器对象描述的抽象语法树，然后调用解释器的解释方法，当然也可以通过环境角色间接访问解释器的解释方法。

<div align=center><img src=DesignPatterns1/解释器模式的结构图.png width=80%></div>


#### 用解释器模式设计一个韶粵通公交车卡的读卡器

假如“韶粵通”公交车读卡器可以判断乘客的身份，如果是“韶关”或者“广州”的“老人” “妇女”“儿童”就可以免费乘车，其他人员乘车一次扣2元。

本实例用“解释器模式”设计比较适合，首先设计其文法规则如下：
```
<expression> ::= <city>的<person>
<city> ::= 韶关|广州
<person> ::= 老人|妇女|儿童
```

然后，根据文法规则按以下步骤设计公交车卡的读卡器程序的类图：

- 定义一个抽象表达式（`Expression`）接口，它包含了解释方法`interpret(String info)`。
- 定义一个终结符表达式（`Terminal Expression`）类，它用集合（`Set`）类来保存满足条件的城市或人，并实现抽象表达式接口中的解释方法`interpret(String info)`，用来判断被分析的字符串是否是集合中的终结符。
- 定义一个非终结符表达式（`AndExpression`）类，它也是抽象表达式的子类，它包含满足条件的城市的终结符表达式对象和满足条件的人员的终结符表达式对象，并实现 `interpret(String info)`方法，用来判断被分析的字符串是否是满足条件的城市中的满足条件的人员。
- 最后，定义一个环境（`Context`）类，它包含解释器需要的数据，完成对终结符表达式的初始化，并定义一个方法`freeRide(String info)`调用表达式对象的解释方法来对被分析的字符串进行解释。

<div align=center><img src=DesignPatterns1/用解释器模式设计一个韶粵通公交车卡的读卡器.png width=90%></div>

```java

// Expression.java

package Interpreter;

//抽象表达式类

public interface Expression
{
    public boolean interpret(String info);
}


// TerminalExpression.java

package Interpreter;

//终结符表达式类

import java.util.HashSet;
import java.util.Set;

public class TerminalExpression implements Expression
{
    private Set<String> set = new HashSet<String>();

    public TerminalExpression(String[] data)
    {
        for(int i=0; i < data.length; i++)
            set.add(data[i]);
    }

    public boolean interpret(String info)
    {
        if(set.contains(info))
        {
            return true;
        }
        return false;
    }
}


// AndExpression.java

package Interpreter;

//非终结符表达式类

public class AndExpression implements Expression
{
    private Expression city = null;
    private Expression person = null;

    public AndExpression(Expression city, Expression person)
    {
        this.city = city;
        this.person = person;
    }

    public boolean interpret(String info)
    {
        String s[] = info.split("的");
        return city.interpret(s[0]) && person.interpret(s[1]);
    }
}


// Context.java

package Interpreter;

//环境类

public class Context
{
    private String[] citys = {"韶关", "广州"};
    private String[] persons = {"老人", "妇女", "儿童"};
    private Expression cityPerson;

    public Context()
    {
        Expression city = new TerminalExpression(citys);
        Expression person = new TerminalExpression(persons);
        cityPerson = new AndExpression(city, person);
    }

    public void freeRide(String info)
    {
        boolean ok = cityPerson.interpret(info);
        if(ok)
            System.out.println("您是" + info + "，您本次乘车免费！");
        else
            System.out.println(info + "，您不是免费人员，本次乘车扣费2元！");
    }
}


// Client.java

package Interpreter;

public class Client
{
    public static void main(String[] args)
    {
        Context bus = new Context();
        bus.freeRide("韶关的老人");
        bus.freeRide("韶关的年轻人");
        bus.freeRide("广州的妇女");
        bus.freeRide("广州的儿童");
        bus.freeRide("山东的儿童");
    }
}
```


#### 模式的扩展

在项目开发中，如果要对数据表达式进行分析与计算，无须再用解释器模式进行设计了，Java提供了以下强大的`数学公式解析器`：`Expression4J`、`MESP(Math Expression String Parser)`和`Jep`等，它们可以解释一些复杂的文法，功能强大，使用简单。

现在以`Jep`为例来介绍该工具包的使用方法。`Jep`是`Java expression parser`的简称，即`Java表达式分析器`，它是一个用来`转换和计算数学表达式`的Java库。通过这个程序库，用户可以<font color=red>以字符串的形式输入一个任意的公式，然后快速地计算出其结果</font>。而且`Jep`支持用户自定义变量、常量和函数，它包括许多常用的数学函数和常量。

使用前先下载`Jep`压缩包，解压后，将`jep-x.x.x.jar`文件移到选择的目录中，在Eclipse的“Java构建路径”对话框的“库”选项卡中选择“添加外部JAR(X)...”，将该`Jep`包添加项目中后即可使用其中的类库。

下面以计算存款利息为例来介绍。存款利息的计算公式是：`本金x利率x时间=利息`。

