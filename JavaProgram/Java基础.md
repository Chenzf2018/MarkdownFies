# 一切皆对象

## 用引用操纵对象
&emsp;&emsp;在Java里，操纵的`标识符`实际上是`对象的一个“引用" (reference)`。可以将这一情形想像成用`遥控器(引用)`来操纵`电视机(对象)`。只要握住这个遥控器，就能保持与电视机的连接。<font color=red>即使没有电视机，遥控器亦可独立存在。也就是说，拥有一个引用，并不一定需要有一个对象与它关联</font>(创建一个`String`引用)。

`String s`这里所创建的只是引用，并不是对象。直接拿来使用会出现错误：因为此时你并没有给变量`s`赋值——指向任何对象。通常更安全的做法是：<font color=red>创建一个引用的同时进行初始化</font>：`String s = “chenzf”;`。

`引用`用来关联`对象`。在`Java`中，使用`new`操作符来创建一个新对象。`new`关键字代表：创建一个新的对象实例，它的意思是“给我一个新对象”：`String s = new String("chenzf");`。

## 数据存储
&emsp;&emsp;程序在运行时是如何存储的呢？尤其是内存是怎么分配的呢？
* 寄存器
这是最快的存储区，它位于处理器内部。但是寄存器的数量极其有限，所以寄存器根据需求进行分配。你不能直接控制，也不能在程序中感觉到寄存器存在的任何迹象。

* Stack
位于通用RAM(随机访问存储器)中，栈指针若向下移动，则分配新的内存，若向上移动，则释放那些内存。
这是一种快速有效的分配存储方法，仅次于寄存器。创建程序时，Java系统必须知道存储在栈内所有项的确切生命周期，以便上下移动栈指针。这一约束限制了程序的灵活性，所以虽然某些Java数据存储于栈中——特别是<font color=red>对象引用</font>，但是Java对象并不存储于其中。

* Heap
一种通用的内存池(也位于RAM区)，用于<font color=red>存放所有的Java对象</font>。
堆不同于栈的好处是：编译器不需要知道存储的数据在堆里存活多长时间。因此，在堆里分配存储有很大的灵活性。当需要一个对象时，只需用`new`写一行简单的代码，当执行这行代码时，会自动在堆里进行存储分配。当然，为这种灵活性必须要付出相应的代价：<font color=red>用堆进行存储分配和清理可能比用栈进行存储分配需要更多的时间</font>。

* 常量存储：常量值通常直接放在程序代码中，因为它们永远不会改变。如需严格保护，可考虑将它们置于只读存储器ROM(只读存储器，Read Only Memory)中。

* 非RAM存储：如果数据完全存活于程序之外，那么它可以不受程序的任何控制，在程序没有运行时也可以存在。其中两个基本的例子是流对象和持久化对象。

## 基本类型

基本数据类型只有8种，可按照如下分类
- 整数类型：`long`、`int`、`short`、`byte`
- 浮点类型：`float`、`double`
- 字符类型：`char`
- 布尔类型：`boolean`


&emsp;&emsp;<font color=red>`new`将对象存储在`堆`里，故用`new`创建一个对象——特别是小的、简单的变量，往往不是很有效</font>。对于这些类型，Java不用`new`来创建这些变量，而是<font color=red>创建一个并非是引用的“自动”变量。这个变量直接存储“值”，并置于栈(stack)中，因此更加高效</font>。

<div align=center><img src=Basic/基本数据类型.png width=80%></div>

Java要确定每种基本类型所占存储空间的大小。它们的大小并不像其他大多数语言那样随机器硬件架构的变化而变化。这种<font color=red>所占存储空间大小的不变性</font>是Java程序比用其他大多数语言编写的程序更具可移植性的原因之一。

创建一个对象会用到`new`关键字，但是给一个基本类型变量赋值却不是用`new`。因为基本类型是Java语言里的一种内置的特殊数据类型，并不是某个类的对象。**给基本类型的变量赋值的方式叫做`字面值`**。

### 包装器类
&emsp;&emsp;基本类型具有的包装器类，使得可以<font color=red>在堆中创建一个非基本对象，用来表示对应的基本类型</font>：

```java
char c = 'X';
// Character ch = new Character(c);
Character character_c = 'X';  // 自动包装
char char_c = character_c;  // 反向转换
```

## 对象清理
&emsp;&emsp;Java对象不具备和基本类型一样的生命周期。当用new创建一个Java对象时，它可以存活于作用域之外：`String s = new String("chen"); // End of scope`，引用`s`在作用域终点就消失了。然而，<font color=red>`s`指向的`String对象`仍继续占据内存空间。在这一小段代码中，我们无法在这个作用域之后访问这个对象，因为对它唯一的引用已超出了作用域的范围。由`new`创建的对象，只要你需要，就会一直保留下去</font>。

如果Java让对象继续存在，那么靠什么才能防止这些对象填满内存空间，进而阻塞你的程序呢？Java有一个<font color=red>垃圾回收器</font>，用来监视用`new`创建的所有对象，并辨别那些不会再被引用的对象。随后，释放这些对象的内存空间，以便供其他新的对象使用。


## 创建新的数据类型：类
&emsp;&emsp;如果一切都是对象，那么是什么决定了某一类<font color=red>对象的外观与行为</font>呢？换句话说，是什么确定了<font color=red>对象的类型</font>？

### 字段field和方法
&emsp;&emsp;一旦定义了一个类，就可以在类中设置两种类型的元素: <font color=red>字段(数据成员)和方法(成员函数)</font>。类的字段可以是<font color=red>基本类型</font>，也可以是<font color=red>引用类型</font>。<font color=red>如果类的字段是对某个对象的引用，那么必须要初始化该引用将其关联到一个实际的对象上</font>（通过之前介绍的创建对象的方法）。每个对象都有用来存储其字段的空间。通常，<font color=red>字段不在对象间共享</font>。

### 基本类型默认值
&emsp;&emsp;如果类的成员变量（字段）是基本类型，那么在类初始化时，即使没有进行显式的初始化，这些类型将会被赋予一个初始值：
<div align=center><img src=Basic/基本类型默认值.png width=40%></div>
这些默认值仅在Java初始化类的时候才会被赋予。

&emsp;&emsp;上述确保初始化的方法并不适用于`局部变量`(即不属于类的字段的变量)。因此，如果在某个方法定义中有`int x;`那么变量`x`得到的可能是任意值，而不会被自动初始化为零。所以在使用`x`前，应先对其赋一个适当的值。

### 方法签名和返回值
&emsp;&emsp;`方法名`和`参数列表`(它们合起来被称为`方法签名`)唯一地标识出某个方法。方法的`参数列表`指定要传递给方法什么样的信息，这些信息像Java中的其他信息一样，采用的都是<font color=red>对象形式</font>。因此，<font color=red>在参数列表中必须指定每个所传递对象的类型及名字，这里传递的实际上也是引用</font>。

可以定义方法返回任意想要的类型，如果不想返回任何值，可以指示此方法返回`void`(空)。若返回类型是`void`，`return`关键字的作用只是用来<font color=red>退出方法</font>。


## 基本数据类型和引用数据类型

Java中的数据类型分为两大类，**基本数据类型**和**引用数据类型**。

基本数据类型只有8种，引用数据类型非常多，大致包括：类、 接口类型、 数组类型、 枚举类型、 注解类型、 字符串型。例如，`String`类型就是引用类型。简单来说，**所有的非基本数据类型都是引用数据类型**。

### 基本数据类型和引用数据类型的区别

每个**变量**都代表一个存储值的**内存位置**。声明一个变量时，就是在告诉编译器这个变量可以存放什么类型的值。对**基本类型变量**来说，对应内存所存储的值是**基本类型值**。对**引用类型变量**来说，对应内存所存储的值是一个**引用**，是**对象的存储地址**。

<div align=center><img src=Basic\PrimitiveAndReferenceType.jpg></div>

将一个变量赋值给另一个变量时，另一个变量就被赋予同样的值。对**基本类型变量**而言，就是将一个变量的**实际值**赋给另一个变量。对**引用类型变量**而言，就是将一个变量的**引用**赋给另一个变量。

<div align=center><img src=Basic\基本类型赋值.jpg></div>

<div align=center><img src=Basic\引用类型赋值.jpg></div>

执行完赋值语句`c1 = c2`后，`c1`指向`c2`所指向的同一对象。`c1`以前引用的对象就不再有用。现在它就成为垃圾（garbage)。垃圾会占用内存空间。Java运行系统会检测垃圾并自动回收它所占的空间，这个过程称为**垃圾回收**（garbage collection)。



#### 存储位置

- 基本变量类型
在方法中定义的非全局基本数据类型变量的具体内容是存储在**栈**中的。

- 引用变量类型
只要是引用数据类型变量，其**具体内容**都是存放在**堆**中的，而**栈**中存放的是其具体内容所在内存的**地址**。
<div align=center><img src=Basic\基本数据与引用数据类型.png width=40%></div>

#### 传递方式

```java
public class Test
{
    public static void main(String[] args)
    {
        int msg = 100;
        System.out.println("调用方法前msg的值："+ msg);    //100
        fun(msg);  // 无返回
        System.out.println("调用方法后msg的值："+ msg);    //100
        msg = func(msg);
        System.out.println("调用方法后msg的值："+ msg);
    }

    public static void fun(int temp)
    {
        temp = 0;
    }

    // public static int fun(int temp)
    public static int func(int temp)
    {
        return temp = 0;
    }
}
```

```java
class Book
{
    String name;
    double price;

    public Book(String name,double price)
    {
        this.name = name;
        this.price = price;
    }

    public void getInfo()
    {
        System.out.println("图书名称："+ name + "，价格：" + price);
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}

public class Test
{
    public static void main(String[] args)
    {
        Book book = new Book("Java开发指南",66.6);
        book.getInfo();  //图书名称：Java开发指南，价格：66.6
        fun(book);
        book.getInfo();  //图书名称：Java开发指南，价格：99.9
    }

    public static void fun(Book temp)
    {
        temp.setPrice(99.9);
    }
}
/*
图书名称：Java开发指南，价格：66.6
图书名称：Java开发指南，价格：99.9
 */
```

调用时为temp在栈中开辟新空间，并指向book的具体内容，方法执行完毕后temp在栈中的内存被释放掉。

<div align=center><img src=Basic\传递引用.png width=70%></div>

#### 向方法传递对象参数

Java只有一种参数传递方式：**值传递**（pass-by-value)。传递对象实际上是传递对象的引用。

```java
public class TestPassObject
{
    public static void main(String[] args)
    {
        // Create a Circle object with radius 1
        CircleWithPrivateDataFields myCircle =
                new CircleWithPrivateDataFields(1);

        // Print areas for radius 1, 2.
        int n = 2;
        printAreas(myCircle, n);

        // See myCircle.radius and times
        System.out.println("\n" + "Radius is " + myCircle.getRadius());
        System.out.println("n is " + n);
    }

    /** Print a table of areas for radius */
    public static void printAreas(
            CircleWithPrivateDataFields c, int times)
    {
        System.out.println("Radius \t\tArea");
        while (times >= 1)
        {
            System.out.println(c.getRadius() + "\t\t" + c.getArea());
            c.setRadius(c.getRadius() + 1);
            times--;
        }
    }
}
/*
Radius 		Area
1.0		3.141592653589793
2.0		12.566370614359172

Radius is 3.0
n is 2
 */
```

当传递**基本数据类型**参数时，传递的是**实参的值**。传递**引用类型**的参数时，传递的是**对象的引用**。

<div align=center><img src=Basic\TestPassObject.jpg></div>


### 重载方法Overload

重栽方法使得你可以使用**同样的名字**来定义**不同方法**，只要它们的**签名**（参数）是不同的。

```java
public class TestMethodOverloading
{
    public static void main(String[] args) 
    {
        // Invoke the max method with int parameters
        System.out.println("The maximum between 3 and 4 is " + max(3, 4));

        // Invoke the max method with the double parameters
        System.out.println("The maximum between 3.0 and 5.4 is " + max(3.0, 5.4));

        // Invoke the max method with three double parameters
        System.out.println("The maximum between 3.0, 5.4, and 10.14 is " + max(3.0, 5.4, 10.14));    
    }

    /** Return the max between two int values */
    public static int max(int num1, int num2)
    {
        if (num1 > num2)
            return num1;
        else
            return num2;
    }

    /** Find the max between two double values */
    public static double max(double num1, double num2) 
    {
        if (num1 > num2)
            return num1;
        else
            return num2;
    }

    /** Return the max among three double values */
    public static double max(double num1, double num2, double num3) 
    {
        return max(max(num1, num2), num3);
    }
}
```

## static关键字
&emsp;&emsp;当创建类时，就是在描述那个类的对象的外观与行为。除非用`new`创建那个类的对象，否则，实际上并未获得任何对象。<font color=red>执行`new`来创建对象时，数据存储空间才被分配，其方法才供外界调用</font>。

有两种情形用上述方法是无法解决的：
* 只想为特定字段（注：也称为属性、域）分配一个共享存储空间，而不去考虑究竞要创建多少对象，甚至根本就不创建任何对象。
* 希望某个方法不与包含它的类的任何对象关联在一起。也就是说，即使没有创建对象，也能够调用这个方法。

有些面向对象语言采用`类数据`和`类方法`两个术语，代表那些数据和方法只是作为整个类，而不是类的某个特定对象而存在的。

当我们说某个事物是静态时，就意味着<font color=red>该字段或方法不依赖于任何特定的对象实例</font>。即使我们从未创建过该类的对象，也可以调用其静态方法或访问其静态字段。

对于普通的非静态字段和方法，我们必须要<font color=red>先创建一个对象并使用该对象来访问字段或方法</font>，因为<font color=red>非静态字段和方法必须与特定对象关联</font>。

```java {highlight=17}
package Object;

public class StaticTest
{
    static int i = 47;  // 静态变量 i 仍只占一份存储空间

    public static void main(String[] args)
    {
        StaticTest st1 = new StaticTest();
        StaticTest st2 = new StaticTest();

        System.out.println("st1: " + st1 + ", st2: " + st2);
        // st1: Object.StaticTest@3b07d329 st2: Object.StaticTest@41629346

        // 两个对象都会共享相同的变量 i
        System.out.println("st1:" + st1.i + " st2:" + st2.i);  // st1:47 st2:47
        System.out.println(StaticTest.i);  // 47
    }
}
/*
st1: Object.StaticTest@2f2c9b19, st2: Object.StaticTest@31befd9f
st1:47 st2:47
47
 */
```

&emsp;&emsp;一个`static`字段对每个类来说都只有**一份**存储空间，而`非static`字段则是对每个对象有一个存储空间。但是如果`static`作用于某个方法，差别却没有那么大。

`static`方法的一个重要用法就是在不创建任何对象的前提下就可以调用它。这一点对定义`main()方法`很重要，这个方法是运行一个应用时的入口点。

## 第一个程序：Date类
使用`Java`标准库中的`Date`类来展示一个字符串和日期：

```java
package Object;

import java.util.*;

/**
 * Display a string and today's date
 * @author Chenzf
 * @version 1.0
 */

public class HelloDate
{
    public static void main(String[] args)
    {
        System.out.println("Hello, it's: ");
        System.out.println(new Date());
    }
}
/*
Output:
Hello, it's:
Wed Sep 18 20:48:16 CST 2019
 */
```

* 有一个特定类会自动被导入到每一个`Java`文件中：`java.lang`。由于`java.lang`是默认导入到每个`Java`文件中的，所以它的所有类都可以被直接使用。`Date`类位于`util`类库中，并且必须书写`import java.util. *;`才能使用`Date`类。
* `main()`方法的参数是一个`String对象`的数组，`args`要用来存储命令行参数。
* 打印日期这一行代码：传递的参数是一个`Date对象`，一旦创建它之后，就可以直接将它的值(<font color=red>被自动转换为`String`类型</font>)发送给`println()`。当这条语句执行完毕后，`Date对象`就不再被使用，而`垃圾回收器`会发现这一情况，并在任意时候将其回收。

# 运算符
&emsp;&emsp;`System.out.println()`语句中包含`+`操作符。在这种上下文环境中，`+`意味着`字符串连接`，并且如果必要，它还要执行`字符串转换`。

## 赋值
&emsp;&emsp;<font color=red>基本类型的赋值都是直接的，而不像对象(赋予的只是其内存的引用)。基本类型存储了实际的数值，而并非指向一个对象的引用，所以在为其赋值的时候，是直接将一个地方的内容复制到了另一个地方</font>。例如，对基本数据类型使用`a=b`，那么`b`的内容就复制给`a`。若接着又修改了`a`，而`b`根本不会受这种修改的影响。

&emsp;&emsp;如果是<font color=red>为对象赋值</font>，那么结果就不一样了。对一个对象进行操作时，我们实际上操作的是它的引用。所以我们<font color=red>将右边的对象赋予给左边时，赋予的只是该对象的引用。此时，两者指向的`堆`中的对象还是同一个</font>。假若对对象使用`c = d;`，那么`c`和`d`都指向原本只有`d`指向的那个对象。

```java
package Operator;

class Tank
{
    int level;
}

public class Assignment
{
    public static void main(String[] args)
    {
        Tank tank1 = new Tank();
        Tank tank2 = new Tank();
        tank1.level = 9;
        tank2.level = 47;
        System.out.println("1: tank1.level: " + tank1.level + ", tank2.level: " + tank2.level);

        tank1.level = tank2.level;
        System.out.println("\ntank1.level = tank2.level;");
        System.out.println("2: tank1.level: " + tank1.level + ", tank2.level: " + tank2.level);
        tank1.level = 27;
        System.out.println("tank1.level = 27;");
        System.out.println("3: tank1.level: " + tank1.level + ", tank2.level: " + tank2.level);

        tank1 = tank2;
        System.out.println("\ntank1 = tank2;");
        System.out.println("4: tank1.level: " + tank1.level + ", tank2.level: " + tank2.level);
        tank1.level = 27;
        System.out.println("tank1.level = 27;");
        System.out.println("5: tank1.level: " + tank1.level + ", tank2.level: " + tank2.level);
    }
}
/*Output:
1: tank1.level: 9, tank2.level: 47

tank1.level = tank2.level;
2: tank1.level: 47, tank2.level: 47
tank1.level = 27;
3: tank1.level: 27, tank2.level: 47

tank1 = tank2;
4: tank1.level: 47, tank2.level: 47
tank1.level = 27;
5: tank1.level: 27, tank2.level: 27
 */
 ```

 ### 方法调用中的别名现象
 `方法名`和`参数列表`(它们合起来被称为`方法签名`)唯一地标识出某个方法。方法的`参数列表`指定要传递给方法什么样的信息，这些信息像Java中的其他信息一样，采用的都是<font color=red>对象形式</font>。因此，<font color=red>在参数列表中必须指定每个所传递对象的类型及名字，这里传递的实际上也是引用</font>。

 ```java
 package Operator;

public class PassObject
{
    static void f(Letter l)
    {
        l.c = 'Z';
    }
    public static void main(String[] args)
    {
        Letter x = new Letter();
        x.c = 'A';
        System.out.println("1: x.c " + x.c);
        f(x);
        System.out.println("2: x.c " + x.c);

    }
}

class Letter
{
    char c;
}
/*Output:
1: x.c A
2: x.c Z
 */
```

## Random类
整数除法会直接去掉结果的小数位(`import java.util.*;`)：
```java
package Operator;

import java.util.*;

public class MathOperators
{
    public static void main(String[] args)
    {
        // Create a seeded random number generator:
        Random rand = new Random(47);
        int i, j, k;
        // Choose value from 1 to 100:
        j = rand.nextInt(100) + 1;
        k = rand.nextInt(100) + 1;
        i = j / k;
        System.out.println("j: " + j + ", k: " + k + ", j / k: " + i);
    }
}
/*
j: 59, k: 56, j / k: 1
 */
 ```
 * 要生成数字，程序首先会创建一个`Random`类的对象。<font color=red>如果在创建过程中没有传递任何参数，那么Java就会将当前时间作为随机数生成器的种子，并由此在程序每一次执行时都产生不同的输出</font>。通过在创建`Random`对象时提供种子(<font color=red>用于随机数生成器的初始化值，随机数生成器对于特定的种子值总是产生相同的随机数序列</font>)，就可以在每一次执行程序时都生成相同的随机数。
* 传递给`nextInt()`的参数设置了所产生的随机数的上限，而其下限为0。

## ++/--
对于前缀递增和前缀递减（如`++a`或`--a`）会<font color=red>先执行运算，再生成值</font>。而对于后缀递增和后缀递减（如`a++`或`a--`），会先生成值，再执行运算。
```java
package Operator;

public class AutoInc
{
    public static void main(String[] args)
    {
        int i = 1, j, k;
        System.out.println("j = ++i: " + ++i);
        System.out.println("k = i++: " + i++);
        System.out.println("i: " + i);
    }
}
/*
Output:(int i = 1, j, k;)
j = ++i: 2
k = i++: 2
i: 3
 */
```

## ==与equals()
```java
package Operator;

public class Equivalence
{
    public static void main(String[] args)
    {
        // integer1与integer2不同对象
        Integer integer1 = new Integer(23);
        Integer integer2 = new Integer(23);
        System.out.println("integer1 == integer2: " + (integer1 == integer2));
        System.out.println("integer1.equals(integer2): " + integer1.equals(integer2));

        // integer3与integer4相同对象
        Integer integer3 = 23;
        Integer integer4 = 23;
        System.out.println("integer3 == integer4: " + (integer3 == integer4));
        System.out.println("integer3.equals(integer4): " + integer3.equals(integer4));
    }
}
/*
Output:
integer1 == integer2: false
integer1.equals(integer2): true
integer3 == integer4: true
integer3.equals(integer4): true
 */
 ```
 * `==`和`!=`比较的是对象的引用。
 * 如果想比较两个对象的实际内容是否相同，必须使用所有对象都适用的特殊方法`equals()`。但这个方法<font color=red>不适用于基本类型</font>，基本类型直接使用`==`和`!=`即可。

 <font color=red>`equals()`的默认行为是比较引用</font>：

 ```java
 package Operator;

class Value
{
    int i;
}

public class EqualsMethod2
{
    public static void main(String[] args)
    {
        Value value1 = new Value();
        Value value2 = new Value();
        // value1 = value2 = 1;
        value1.i = value2.i = 1;
        System.out.println("value1.equals(value2): " + value1.equals(value2));
    }
}
/*
Output:
value1.equals(value2): false
 */
 ```
 <font color=red>`equals()`的默认行为是比较引用。所以除非在自己的新类中覆盖`equals()`方法，否则默认是比较引用，而不是实际内容</font>。大多数Java库类通过覆写`equals()`方法比较对象的内容而不是其引用。

 在`Object.java`中：
```java
public boolean equals(Object obj) 
{
    return (this == obj);
}
```
在`Integer.java`中：
```java
public boolean equals(Object obj) 
{
    if (obj instanceof Integer) 
    {
        return value == ((Integer)obj).intValue();
    }
    
    return false;
}
```

## 字面值常量
当我们向程序中插入一个字面值常量(`Literal`)时，编译器会确切地识别它的类型。当类型不明确时，必须辅以字面值常量关联来帮助编译器识别。
```java
public class Literals {
    public static void main(String[] args) {
        int i1 = 0x2f; // 16进制 (小写)
        System.out.println("i1: " + Integer.toBinaryString(i1));
        int i2 = 0X2F; // 16进制 (大写)
        System.out.println("i2: " + Integer.toBinaryString(i2));
        int i3 = 0177; // 8进制 (前导0)
        System.out.println("i3: " + Integer.toBinaryString(i3));

        char c = 0xffff; // 最大 char 型16进制值
        System.out.println("c: " + Integer.toBinaryString(c));
        byte b = 0x7f; // 最大 byte 型16进制值  10101111;
        System.out.println( "b: " + Integer.toBinaryString(b));
        short s = 0x7fff; // 最大 short 型16进制值
        System.out.println("s: " + Integer.toBinaryString(s));

        long n1 = 200L; // long 型后缀
        long n2 = 200l; // long 型后缀 (容易与数值1混淆)
        long n3 = 200;

        // Java 7 二进制字面值常量:
        byte blb = (byte)0b00110101;
        System.out.println("blb: " + Integer.toBinaryString(blb));
        short bls = (short)0B0010111110101111;
        System.out.println("bls: " + Integer.toBinaryString(bls));
        int bli = 0b00101111101011111010111110101111;
        System.out.println("bli: " + Integer.toBinaryString(bli));
        long bll = 0b00101111101011111010111110101111;
        System.out.println("bll: " + Long.toBinaryString(bll));
        float f1 = 1;
        float f2 = 1F; // float 型后缀
        float f3 = 1f; // float 型后缀
        double d1 = 1d; // double 型后缀
        double d2 = 1D; // double 型后缀
        // (long 型的字面值同样适用于十六进制和8进制 )
    }
}
```

## 三元操作符/条件操作符
`Boolean-exp ? value0 : value1`：`(i < 10 ? i * 100 : i * 10)`

如果`boolean-exp`(布尔表达式)的结果为`true`，就计算`value0`，而且这个计算结果也就是操作符最终产生的值。如果`boolean-exp`的结果为`false`，就计算`value1`，同样，它的结果也就成为了操作符最终产生的值。


## 类型转换操作符

<div align=center><img src=Basic\基本类型转换.jpg></div>

- 小范围类型的变量转换为大范围类型的变量称为**拓宽类型(widening a type)**
- 大范围类型的变量转换为小范围类型的变量称为**缩窄类型(narrowing a type)**

Java将自动拓宽一个类型，但是，缩窄类型必须显式完成：
```java
System.out.println((int)1.7);
System.out.println((double)1 / 2);  // displays 0.5
System.out.println(1 / 2);  // displays 0
```

&emsp;&emsp;`类型转换(Casting)`的作用是“与一个模型匹配”。在适当的时候，Java会将一种数据类型自动转换成另一种。例如，假设我们为某浮点变量赋以一个整数值，编译器会将`int`自动转换成`float`。

&emsp;&emsp;如果要执行一种名为`窄化转换(narrowing conversion)`的操作(也就是说，将能容纳更多信息的数据类型转换成无法容纳那么多信息的类型)，就有可能面临`信息丢失`的危险。<font color=red>必须显式地进行类型转换</font>。对于`扩展转换(widening conversion)`，则不必显式地进行类型转换，因为新类型肯定能容纳原来类型的信息，不会造成任何信息的丢失。

&emsp;&emsp;Java允许我们把任何基本数据类型转换成别的基本数据类型，但布尔型除外，后者根本不允许进行任何类型的转换处理。<font color=red>“类数据类型”不允许进行类型转换。基本类型可以自动从较小的类型转型为较大的类型</font>。

精度高的数据类型就像容量大的杯子，可以放更大的数据；精度低的数据类型就像容量小的杯子，只能放更小的数据。小杯子往大杯子里倒东西，大杯子怎么都放得下；大杯子往小杯子里倒东西，有的时候放的下，有的时候就会有溢出。

需要注意的一点是，虽然`short`和`char`都是16位的，长度是一样的，但是彼此之间，依然需要进行强制转换。

如果任何运算单元的长度都不超过`int`，那么运算结果就按照`int`来计算：
```
byte a = 1;
byte b = 2;
a+b -> int 类型
```

### 截尾与舍入
&emsp;&emsp;在执行窄化转换时，必须注意截尾与舍入问题。<font color=red>将`float`或`double`转型为整型值时，总是对该数字执行截尾</font>。如果想要得到舍入的结果，就需要使用`java.lang.Math`中的`round()`方法：

```java
package Operator;

public class CastingNumbers
{
    public static void main(String[] args)
    {
        double above = 0.7D, below = 0.4D;
        float fabove = 0.7F, fbelow = 0.4F;
        System.out.println("(int)above: " + (int)above + ", (int)below: " + (int)below);
        System.out.println("(int)fabove: " + (int)fabove + ", (int)fbelove: " + (int)fbelow);
        System.out.println("Math.round(above): " + Math.round(above) + ", Math.round(below): " + Math.round(below));
        System.out.println("Math.round(above): " + Math.round(fabove) + ", Math.round(below): " + Math.round(fbelow));
    }
}
/*
(int)above: 0, (int)below: 0
(int)fabove: 0, (int)fbelove: 0
Math.round(above): 1, Math.round(below): 0
Math.round(above): 1, Math.round(below): 0
*/
```

### 类型提升
&emsp;&emsp;如果对小于`int`的基本数据类型(即`char`、`byte`或`short`)执行任何算术或按位操作，这些值会在执行操作之前类型提升为`int`，并且结果值的类型为`int`。若想重新使用较小的类型，必须使用强制转换(由于重新分配回一个较小的类型，结果可能会丢失精度)。

通常，<font color=red>表达式中出现的最大的数据类型决定了表达式最终结果的数据类型</font>。如果将一个`float`值与一个`double`值相乘，结果就是`double`，如果将一个`int`和一个`long`值相加，则结果为`long`。

## for-in
&emsp;&emsp;`Java SE5`引入了一种新的更加简洁的`for语法`用于数组和容器，不必创建`int`变量去对由访问项构成的序列进行计数，`for-in`将自动产生每一项。`for-in`可以用于任何`Iterable对象`。

```java
package Control;

import java.util.*;

public class ForEachFloat
{
    public static void main(String[] args)
    {
        Random rand = new Random(47);
        float array[] = new float[5];
        for (int i = 0; i < 5; i++)
            array[i] = rand.nextFloat();
        for (float i : array)
            System.out.println(i);
    }
}
/*
0.72711575
0.39982635
0.5309454
0.0534122
0.16020656
*/
```