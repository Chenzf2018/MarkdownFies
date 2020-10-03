# 注解解释一
[参考资料：廖雪峰](https://www.liaoxuefeng.com/wiki/1252599548343744/1255945389098144)

## 使用注解

&emsp;&emsp;注解(Annotation)是Java程序的一种特殊“注释”。它是放在Java源码的类、方法、字段、参数前的一种特殊“注释”：
```java
package Annotation;

import javax.annotation.Resource;
import javax.annotation.PostConstruct;

@Resource("hello")
public class Hello
{
    @Inject
    int n;

    @PostConstruct
    public void hello(@Param String name){System.out.println(name);}

    @Override
    public String toString(){return "Hello";}
}
```
注释会被编译器直接忽略，注解则可以被编译器打包进入class文件，因此，注解是一种用作标注的“元数据”。

### 注解的作用
&emsp;&emsp;从JVM的角度看，注解本身对代码逻辑没有任何影响，如何使用注解完全由工具决定。Java的注解可以分为三类：

第一类是由编译器使用的注解，例如：
* `@Override`：让编译器检查该方法是否正确地实现了覆写；
* `@SuppressWarnings`：告诉编译器忽略此处代码产生的警告。
这类注解不会被编译进入`.class`文件，它们在编译后就被编译器扔掉了。

第二类是由工具处理`.class`文件使用的注解，比如有些工具会在加载class的时候，对class做动态修改，实现一些特殊的功能。这类注解会被编译进入`.class`文件，但加载结束后并不会存在于内存中。这类注解只被一些底层库使用，一般我们不必自己处理。

第三类是在程序运行期能够读取的注解，它们在加载后一直存在于JVM中，这也是最常用的注解。例如，一个配置了`@PostConstruct`的方法会在调用构造方法后自动被调用(这是Java代码读取该注解实现的功能，JVM并不会识别该注解)。

&emsp;&emsp;定义一个注解时，还可以定义配置参数。配置参数可以包括：
* 所有基本类型；
* String；
* 枚举类型；
* 基本类型、String以及枚举的数组。

因为配置参数必须是常量，所以，上述限制保证了注解在定义时就已经确定了每个参数的值。

注解的配置参数可以有默认值，缺少某个配置参数时将使用默认值。此外，大部分注解会有一个名为`value`的配置参数，对此参数赋值，可以只写常量，相当于省略了`value`参数。如果只写注解，相当于全部使用默认值。

对以下代码：
```java
public class Hello
{
    @Check(min=0, max=100, value=55)
    public int n;

    @Check(value=99)
    public int p;

    @Check(99)
    public int x;

    @Check
    public int y;
}
```
`@Check`就是一个注解。第一个`@Check(min=0, max=100, value=55)`明确定义了三个参数，第二个`@Check(value=99)`只定义了一个`value`参数，它实际上和`@Check(99)`是完全一样的。最后一个`@Check`表示所有参数都使用默认值。

## 定义注解
&emsp;&emsp;Java语言使用`@interface`语法来定义注解，它的格式如下：
```java
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```
注解的参数类似无参数方法，可以用`default`设定一个默认值(强烈推荐)。最常用的参数应当命名为`value`。

### 元注解
&emsp;&emsp;<font color=red>有一些注解可以修饰其他注解，这些注解就称为元注解(meta annotation)</font>。Java标准库已经定义了一些元注解，我们只需要使用元注解，通常不需要自己去编写元注解。

#### `@Target`

`@Target`表示这个注解能放在什么位置上，是只能放在类上？还是即可以放在方法上，又可以放在属性上。

使用`@Target`可以<font color=red>定义`Annotation`能够被应用于源码的哪些位置</font>：
* 类或接口：`ElementType.TYPE`；
* 字段：`ElementType.FIELD`；
* 方法：`ElementType.METHOD`；
* 构造方法：`ElementType.CONSTRUCTOR`；
* 方法参数：`ElementType.PARAMETER`。

例如，定义注解`@Report`可用在方法上，我们必须添加一个`@Target(ElementType.METHOD)`：
```java
@Target(ElementType.METHOD)
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```
定义注解`@Report`可用在方法或字段上，可以把`@Target`注解参数变为数组`{ElementType.METHOD, ElementType.FIELD}`：
```java
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Report {...}
```
实际上`@Target`定义的`value`是`ElementType[]`数组，只有一个元素时，可以省略数组的写法。

#### `@Retention`
&emsp;&emsp;另一个重要的元注解`@Retention`定义了Annotation的生命周期：
* 仅编译期：`RetentionPolicy.SOURCE`；注解只在源代码中存在，编译成class之后，就没了。@Override 就是这种注解。
* 仅class文件：`RetentionPolicy.CLASS`；注解在java文件编程成.class文件后，依然存在，但是运行起来后就没了。@Retention是默认值，即当没有显式指定@Retention的时候，就会是这种类型。
* 运行期：`RetentionPolicy.RUNTIME`。注解在运行起来之后依然存在，程序可以通过反射获取这些信息。

如果`@Retention`不存在，则该Annotation默认为CLASS。因为通常我们自定义的Annotation都是RUNTIME，所以，务必要加上`@Retention(RetentionPolicy.RUNTIME)`这个元注解：
```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

#### `@Repeatable`
&emsp;&emsp;使用`@Repeatable`这个元注解可以定义Annotation是否可重复。这个注解应用不是特别广泛。
```java
@Repeatable(Reports.class)
@Target(ElementType.TYPE)
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}

@Target(ElementType.TYPE)
public @interface Reports {Report[] value();}
```

经过`@Repeatable`修饰后，在某个类型声明处，就可以添加多个`@Report`注解：
```java
@Report(type=1, level="debug")
@Report(type=2, level="warning")
public class Hello {
}
```

#### `@Inherited`
&emsp;&emsp;使用`@Inherited`定义子类是否可继承父类定义的Annotation。`@Inherited`仅针对`@Target(ElementType.TYPE)`类型的annotation有效，并且仅针对class的继承，对`interface`的继承无效：
```java
@Inherited
@Target(ElementType.TYPE)
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

在使用的时候，如果一个类用到了`@Report`：
```java
@Report(type=1)
public class Person {}
```
则它的子类默认也定义了该注解：
```java
public class Student extends Person {}
```

### 如何定义Annotation
&emsp;&emsp;总结一下定义Annotation的步骤：
第一步，用`@interface`定义注解：
```java
public @interface Report {}
```
第二步，添加参数、默认值：
```java
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```
把最常用的参数定义为value()，推荐所有参数都尽量设置默认值。

第三步，用元注解配置注解：
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Report
{
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```
其中，必须设置`@Target`和`@Retention`，`@Retention`一般设置为`RUNTIME`，因为自定义的注解通常要求在运行期读取。一般情况下，不必写`@Inherited`和`@Repeatable`。

## 处理注解
&emsp;&emsp;Java的注解本身对代码逻辑没有任何影响。根据`@Retention`的配置：
* `SOURCE`类型的注解在编译期就被丢掉了；
* `CLASS`类型的注解仅保存在class文件中，它们不会被加载进JVM；
* `RUNTIME`类型的注解会被加载进JVM，并且在运行期可以被程序读取。

<font color=red>如何使用注解完全由工具决定。`SOURCE`类型的注解主要由编译器使用，因此我们一般只使用，不编写。`CLASS`类型的注解主要由底层工具库使用，涉及到class的加载，一般我们很少用到。只有`RUNTIME`类型的注解不但要使用，还经常需要编写。</font>因此，只讨论如何读取`RUNTIME`类型的注解。

因为<font color=red>注解定义后也是一种class</font>，所有的注解都继承自`java.lang.annotation.Annotation`，因此，读取注解，需要使用反射API。Java提供的使用反射API读取Annotation的方法包括：

1. 判断某个注解是否存在于`Class, Field, Method`或`Constructor`：
* Class.isAnnotationPresent(Class)
* Field.isAnnotationPresent(Class)
* Method.isAnnotationPresent(Class)
* Constructor.isAnnotationPresent(Class)

例如：
```java
// 判断@Report是否存在于Person类:
Person.class.isAnnotationPresent(Report.class);
```

2. 使用反射API读取Annotation：
* Class.getAnnotation(Class)
* Field.getAnnotation(Class)
* Method.getAnnotation(Class)
* Constructor.getAnnotation(Class)

例如：
```java
// 获取Person定义的@Report注解:
Report report = Person.class.getAnnotation(Report.class);
int type = report.type();
String level = report.level();
```

使用反射API读取Annotation有两种方法：

* 方法一是先判断Annotation是否存在，如果存在，就直接读取：
```java
Class cls = Person.class;
if (cls.isAnnotationPresent(Report.class))
{
    Report report = cls.getAnnotation(Report.class);
    ...
}
```
* 第二种方法是直接读取Annotation，如果Annotation不存在，将返回`null`：
```java
Class cls = Person.class;
Report report = cls.getAnnotation(Report.class);
if (report != null) {...}
```

读取方法、字段和构造方法的`Annotation`和`Class`类似。但要读取方法参数的Annotation就比较麻烦一点，因为方法参数本身可以看成一个数组，而每个参数又可以定义多个注解，所以，一次获取方法参数的所有注解就必须用一个二维数组来表示。例如，对于以下方法定义的注解：
```java
public void hello(@NotNull @Range(max=5) String name, @NotNull String prefix) {}
```
要读取方法参数的注解，我们先用反射获取Method实例，然后读取方法参数的所有注解：
```java
// 获取Method实例:
Method m = ...
// 获取所有参数的Annotation:
Annotation[][] annos = m.getParameterAnnotations();
// 第一个参数（索引为0）的所有Annotation:
Annotation[] annosOfName = annos[0];
for (Annotation anno : annosOfName)
{
    // @Range注解
    if (anno instanceof Range) { Range r = (Range) anno;}
    // @NotNull注解
    if (anno instanceof NotNull) { NotNull n = (NotNull) anno;}
}
```

### 使用注解
&emsp;&emsp;注解如何使用，完全由程序自己决定。例如，`JUnit`是一个测试框架，它会自动运行所有标记为`@Test`的方法。

来看一个`@Range`注解，希望用它来定义一个String字段的规则：字段长度满足`@Range`的参数定义：
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range
{
    int min() default 0;
    int max() default 255;
}
```

在某个JavaBean中，可以使用该注解：
```java
public class Person {
    @Range(min=1, max=20)
    public String name;

    @Range(max=10)
    public String city;
}
```

但是，<font color=red>定义了注解，本身对程序逻辑没有任何影响。必须自己编写代码来使用注解</font>。这里，我们编写一个`Person`实例的检查方法，它可以<font color=red>检查Person实例的String字段长度是否满足`@Range`的定义</font>：
```java
void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
    // 遍历所有Field:
    for (Field field : person.getClass().getFields())
    {
        // 获取Field定义的@Range:
        Range range = field.getAnnotation(Range.class);
        // 如果@Range存在:
        if (range != null)
        {
            // 获取Field的值:
            Object value = field.get(person);
            // 如果值是String:
            if (value instanceof String)
            {
                String s = (String) value;
                // 判断值是否满足@Range的min/max:
                if (s.length() < range.min() || s.length() > range.max())
                {
                    throw new IllegalArgumentException("Invalid field: " + field.getName());
                }
            }
        }
    }
}
```
通过`@Range`注解，配合`check()`方法，就可以完成Person实例的检查。注意检查逻辑完全是我们自己编写的，JVM不会自动给注解添加任何额外的逻辑。

### 总结
&emsp;&emsp;可以在运行期通过反射读取`RUNTIME`类型的注解，注意千万不要漏写`@Retention(RetentionPolicy.RUNTIME)`，否则运行期无法读取到该注解。可以通过程序处理注解来实现相应的功能：
* 对JavaBean的属性值按规则进行检查；
* JUnit会自动运行`@Test`标记的测试方法。


# 注解解释二
[参考资料：HOW2J](https://how2j.cn/k/annotation/annotation-system/1060.html)

## 基本内置注解

`@Override`用在方法上，表示这个方法**重写了父类的方法**，如toString()。如果父类没有这个方法，那么就无法编译通过；

`@Deprecated`表示这个方法已经过期，不建议开发者使用。(暗示在将来某个不确定的版本，就有可能会取消掉)；

`@SuppressWarnings`：`Suppress`英文的意思是抑制的意思，这个注解的用处是**忽略警告信息**。

比如大家使用集合的时候，有时候为了偷懒，会不写泛型，像这样：`List heros = new ArrayList();` 那么就会导致编译器出现警告。而加上`@SuppressWarnings({"rawtypes", "unused"})`，就对这些警告进行了抑制，即忽略掉这些警告信息。

`@SuppressWarnings`有常见的值，分别对应如下意思：
1. `deprecation`：使用了不赞成使用的类或方法时的警告(使用`@Deprecated`使得编译器产生的警告)；
2. `unchecked`：执行了未检查的转换时的警告，例如当使用集合时没有用泛型(Generics)来指定集合保存的类型; 关闭编译器警告；
3. `fallthrough`：当`Switch`程序块直接通往下一种情况而没有`Break`时的警告;
4. `path`：在类路径、源文件路径等中有不存在的路径时的警告;
5. `serial`：当在可序列化的类上缺少`serialVersionUID`定义时的警告;
6. `finally`：任何`finally`子句不能正常完成时的警告;
7. `rawtypes`：泛型类型未指明
8. `unused`：引用定义了，但是没有被使用
9. `all`：关于以上所有情况的警告。


`@SafeVarargs`这是1.7之后新加入的基本注解；`@FunctionalInterface`这是Java1.8新增的注解，用于约定函数式接口。

以上5个都是内置注解，从开发过程中的体验来讲，有也可以，没有也可以，重点还是后面要讲解的自定义注解。


## 自定义注解

在本例中，把数据库连接的工具类`DBUtil`改造成为注解的方式，来举例演示**怎么自定义注解**以及**如何解析这些自定义注解**。

### 非注解方式DBUtil

通常来讲，在一个基于`JDBC`开发的项目里，都会有一个`DBUtil`这么一个类，在这个类里统一提供连接数据库的IP地址，端口，数据库名称， 账号，密码，编码方式等信息。如例所示，在这个`DBUtil`类里，这些信息，就是以属性的方式定义在类里的。

大家可以运行试试，运行结果是获取一个连接数据库test的连接Connection实例。运行需要用到连接mysql的jar包`mysql-connector-java-5.0.8-bin.jar`。

```java
package Annotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    static String ip = "127.0.0.1";
    static int port = 3306;
    static String database = "test";
    static String encoding = "UTF-8";
    static String loginName = "root";
    static String password = "admin";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try{
            String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",
                    ip, port, database, encoding);
            return DriverManager.getConnection(url, loginName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        System.out.println(getConnection());
    }
}
/*
com.mysql.jdbc.Connection@12b1dae
 */
```

### 自定义注解@JDBCConfig

接下来，就要把DBUtil这个类改造成为支持自定义注解的方式。 首先创建一个注解`JDBCConfig`：

```java
package Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JDBCConfig {
    String ip();
    int port() default 3306;
    String database();
    String encoding();
    String loginName();
    String password();
}
```

**元注解：**
`@Target({METHOD, TYPE})`：表示这个注解可以用在类/接口上，还可以用在方法上；
`@Retention(RetentionPolicy.RUNTIME)`：表示这是一个运行时注解，即运行起来之后，才获取注解中的相关信息，而不像基本注解如@Override 那种不用运行；
`@Inherited`：表示这个注解可以被子类继承；
`@Documented`：表示当执行`javadoc`的时候，本注解会生成相关文档。


### 注解方式DBUtil

有了自定义注解`@JDBCConfig`之后，我们就把`非注解方式DBUtil`改造成为`注解方式DBUtil`。

如例所示，数据库相关配置信息本来是以**属性**的方式存放的，现在改为了以**注解**的方式，提供这些信息了。

```java
package Annotation;

@JDBCConfig(ip = "127.0.0.1", database = "test", encoding = "UTF-8",
                        loginName = "root", password = "admin")
public class DBUtilAnnotation {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
```

目前只是以注解的方式提供这些信息，但是还没有解析，接下来进行解析。

### 解析注解

接下来就通过反射，获取这个DBUtil这个类上的注解对象：

```java
package Annotation;

import java.sql.Connection;
import java.sql.DriverManager;

@JDBCConfig(ip = "127.0.0.1", database = "test", encoding = "UTF-8",
                        loginName = "root", password = "admin")

public class DBUtilAnnotation {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            // 通过反射，获取这个DBUtil这个类上的注解对象
            JDBCConfig config = DBUtilAnnotation.class.getAnnotation(JDBCConfig.class);
            
            // 拿到注解对象之后，通过其方法，获取各个注解元素的值
            String ip = config.ip();
            int port = config.port();
            String database = config.database();
            String encoding = config.encoding();
            String loginName = config.loginName();
            String password = config.password();
            String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",
                    ip, port, database, encoding);
            return DriverManager.getConnection(url, loginName, password);

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
/*
com.mysql.jdbc.Connection@1a7cec2
 */
```

