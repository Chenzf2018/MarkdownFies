参考资料：[Lambda表达式详解](https://www.cnblogs.com/haixiang/p/11029639.html)

# Lambda解释一

对于一个Java变量，我们可以赋给其一个“值”：
```
int value = 129;
String aString = "Hello World!";
```

如果想把**一块代码**赋给一个Java变量，应该怎么做呢？

比如，想把右边那块代码，赋给一个叫做`aBlockOfCode`的Java变量：
<div align=center><img src=Lambda\Lambda1.jpg></div>

在Java 8之前，这个是做不到的。但是Java 8问世之后，利用Lambda特性，就可以做到了：
<div align=center><img src=Lambda\Lambda2.jpg></div>

当然，这个并不是一个很简洁的写法。所以，为了使这个赋值操作更加elegant, 可以移除一些没用的声明：
<div align=center><img src=Lambda\Lambda3.jpg></div>

这样，我们就成功的非常优雅的把“一块代码”赋给了一个变量。而“这块代码”，或者说“这个<font color=red>被赋给一个变量的函数</font>”，就是一个Lambda表达式。

但是这里仍然有一个问题，就是变量`aBlockOfCode`的类型应该是什么？

在Java 8里面，<font color=red>所有的Lambda的类型都是一个接口</font>，而Lambda表达式本身，也就是”那段代码“，需要是这个接口的实现。<font color=red>Lambda表达式本身就是一个接口的实现</font>。

给上面的`aBlockOfCode`加上一个类型：
<div align=center><img src=Lambda\Lambda4.jpg></div>

这种<font color=red>只有一个接口函数需要被实现的接口类型</font>，我们叫它**函数式接口**。为了避免后来的人在这个接口中增加接口函数导致其有多个接口函数需要被实现，变成"非函数接口”，我们可以在这个上面加上一个声明`@FunctionalInterface`, 这样别人就无法在里面添加新的接口函数了：
<div align=center><img src=Lambda\Lambda5.jpg></div>

这样，我们就得到了一个完整的Lambda表达式声明：
<div align=center><img src=Lambda\Lambda6.jpg></div>

## Lambda表达式作用

最直观的作用就是使得代码变得异常简洁。

我们可以对比一下Lambda表达式和传统的Java对同一个接口的实现：
<div align=center><img src=Lambda\Lambda7.jpg></div>

这两种写法本质上是等价的。但是显然，Java 8中的写法更加优雅简洁。

并且，由于Lambda可以**直接赋值给一个变量**，我们就可以**直接把Lambda作为参数传给函数**, 而传统的Java必须有明确的接口实现的定义，初始化才行：
<div align=center><img src=Lambda\Lambda8.jpg></div>

有些情况下，这个接口实现只需要用到一次。传统的Java 7必须要求你定义一个“污染环境”的接口实现`MyInterfaceImpl`，而相较之下Java 8的Lambda, 就显得干净很多！

## 结合FunctionalInterface Lib, forEach等

Lambda结合FunctionalInterface Lib, forEach, stream()，method reference等新特性可以使代码变的更加简洁！

假设Person的定义和`List<Person>`的值都给定：
<div align=center><img src=Lambda\Lambda9.jpg></div>

现在需要你打印出`guiltyPersons List`里面所有LastName以"Z"开头的人的FirstName。

原生态Lambda写法：定义两个函数式接口，定义一个静态函数，调用静态函数并给参数赋值Lambda表达式：
<div align=center><img src=Lambda\Lambda10.jpg></div>

这个代码实际上已经比较简洁了，但是我们还可以更简洁么？

当然可以。在Java 8中有一个函数式接口的包，里面定义了大量可能用到的函数式接口（`java.util.function`(Java Platform SE 8 )）。所以，我们在这里压根都不需要定义`NameChecke`r和`Executor`这两个函数式接口，直接用Java 8函数式接口包里的`Predicate<T>`和`Consumer<T>`就可以了——因为他们这一对的接口定义和`NameChecker/Executor`其实是一样的。

<div align=center><img src=Lambda\Lambda11.jpg></div>

第一步简化 - 利用函数式接口包：
<div align=center><img src=Lambda\Lambda12.jpg></div>

静态函数里面的`for each`循环其实是非常碍眼的。这里可以利用`Iterable`自带的`forEach()`来替代。`forEach()`本身可以接受一个`Consumer<T>`参数。

第二步简化 - 用`Iterable.forEach()`取代foreach loop：
<div align=center><img src=Lambda\Lambda13.jpg></div>

由于静态函数其实只是对List进行了一通操作，这里我们可以甩掉静态函数，直接使用`stream()`特性来完成。`stream()`的几个方法都是接受`Predicate<T>`，`Consumer<T>`等参数的（`java.util.stream`(Java Platform SE 8 )）。理解了上面的内容，`stream()`这里就非常好理解了，并不需要多做解释。

第三步简化 - 利用`stream()`替代`静态函数`：
<div align=center><img src=Lambda\Lambda14.jpg></div>

对比最开始的Lambda写法，这里已经非常非常简洁了。但是如果，我们的要求变一下，变成print这个人的全部信息，及`p -> System.out.println(p);`那么还可以利用`Method reference`来继续简化。所谓`Method reference`，就是用已经写好的别的`Object/Class`的`method`来代替`Lambda expression`。格式如下：
<div align=center><img src=Lambda\Lambda15.jpg></div>

第四步简化 - 如果是`println(p)`，则可以利用`Method reference`代替`forEach`中的Lambda表达式：
<div align=center><img src=Lambda\Lambda16.jpg></div>

## Lambda配合Optional<T>

Lambda配合`Optional<T>`可以使Java对于`null`的处理变的异常优雅。

这里假设我们有一个person object，以及一个person object的Optional wrapper:
<div align=center><img src=Lambda\Lambda17.jpg></div>

`Optional<T>`如果不结合Lambda使用的话，并不能使原来繁琐的`null check`变的简单：
<div align=center><img src=Lambda\Lambda18.jpg></div>

只有当`Optional<T>`结合Lambda一起使用的时候，才能发挥出其真正的威力！

我们现在就来对比一下下面四种常见的null处理中，Java 8的`Lambda+Optional<T>`和传统Java两者之间对于null的处理差异。

情况一 - 存在则开干：
<div align=center><img src=Lambda\Lambda19.jpg></div>

情况二 - 存在则返回，无则返回屁：
<div align=center><img src=Lambda\Lambda20.jpg></div>

情况三 - 存在则返回，无则由函数产生：
<div align=center><img src=Lambda\Lambda21.jpg></div>

情况四 - 夺命连环null检查：
<div align=center><img src=Lambda\Lambda22.jpg></div>

由上述四种情况可以清楚地看到，`Optional<T>+Lambda`可以让我们少写很多`ifElse`块。尤其是对于情况四那种夺命连环null检查，传统java的写法显得冗长难懂，而新的`Optional<T>+Lambda`则清新脱俗，清楚简洁。


# Lambda解释二

## Lambda简介

Lambda表达式是JDK8的一个新特性，可以取代大部分的**匿名内部类**，写出更优雅的Java代码，尤其在**集合的遍历**和其他集合操作中，可以极大地优化代码结构。

JDK也提供了大量的内置**函数式接口**供我们使用，使得Lambda表达式的运用更加方便、高效。

`Lambda`表达式，也可称为**闭包**，它允许<font color=red>把函数作为一个方法的参数（函数作为参数传递进方法中）</font>。使用`Lambda`表达式可以使代码变的更加简洁紧凑。

### 对接口的要求

Lambda规定接口中<font color=red>只能有一个需要被实现的方法</font>，不是规定接口中只能有一个方法。

## 语法

语法形式为`() -> {}`，其中`()`用来描述**参数列表**，`{}`用来描述**方法体**，`->`为`lambda`运算符，读作`goes to`。

```java
(parameters) -> expression
or
(parameters) ->{ statements; }
```

- 可选类型声明：**不需要声明参数类型**，编译器可以统一识别参数值。
- 可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
- 可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
- 可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。

```java
// 1. 不需要参数,返回值为 5  
() -> 5  
  
// 2. 接收一个参数(数字类型),返回其2倍的值  
x -> 2 * x  
  
// 3. 接受2个参数(数字),并返回他们的差值  
(x, y) -> x – y  
  
// 4. 接收2个int型整数,返回他们的和  
(int x, int y) -> x + y  
  
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)  
(String s) -> System.out.print(s)
```


## 示例

**示例一**：

```java
public class LambdaTest
{
    public static void main(String[] args)
    {
        LambdaTest tester = new LambdaTest();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message -> System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
    }

    interface MathOperation
    {
        int operation(int a, int b);
    }

    interface GreetingService
    {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation)
    {
        return mathOperation.operation(a, b);
    }
}
```

`@FunctionalInterface`修饰函数式接口的，要求**接口中的抽象方法只有一个**。这个注解往往会和`lambda`表达式一起出现。

**示例二**：

```java
public class TestLambda
{
    /**多参数无返回*/
    @FunctionalInterface
    public interface NoReturnMultiParam
    {
        void method(int a, int b);
    }

    /**多个参数有返回值*/
    @FunctionalInterface
    public interface ReturnMultiParam
    {
        int method(int a, int b);
    }

    /**无参无返回值*/
    @FunctionalInterface
    public interface NoReturnNoParam
    {
        void method();
    }

    /*** 无参有返回*/
    @FunctionalInterface
    public interface ReturnNoParam
    {
        int method();
    }

    /**一个参数无返回*/
    @FunctionalInterface
    public interface NoReturnOneParam
    {
        void method(int a);
    }

    /**一个参数有返回值*/
    @FunctionalInterface
    public interface ReturnOneParam
    {
        int method(int a);
    }

    public static void main(String[] args)
    {
        //无参无返回
        NoReturnNoParam noReturnNoParam = () -> {
            System.out.println("NoReturnNoParam");
        };
        noReturnNoParam.method();

        //一个参数无返回
        NoReturnOneParam noReturnOneParam = (int a) -> {
            System.out.println("NoReturnOneParam param:" + a);
        };
        noReturnOneParam.method(6);

        //多个参数无返回
        NoReturnMultiParam noReturnMultiParam = (int a, int b) -> {
            System.out.println("NoReturnMultiParam param:" + "{" + a +"," + + b +"}");
        };
        noReturnMultiParam.method(6, 8);

        //无参有返回值
        ReturnNoParam returnNoParam = () -> {
            System.out.print("ReturnNoParam");
            return 1;
        };

        int res = returnNoParam.method();
        System.out.println("return:" + res);

        //一个参数有返回值
        ReturnOneParam returnOneParam = (int a) -> {
            System.out.println("ReturnOneParam param:" + a);
            return 1;
        };

        int res2 = returnOneParam.method(6);
        System.out.println("return:" + res2);

        //多个参数有返回值
        ReturnMultiParam returnMultiParam = (int a, int b) -> {
            System.out.println("ReturnMultiParam param:" + "{" + a + "," + b +"}");
            return 1;
        };

        int res3 = returnMultiParam.method(6, 8);
        System.out.println("return:" + res3);
    }
}
```