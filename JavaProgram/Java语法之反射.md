# 反射解释一
[参考资料——廖雪峰](https://www.liaoxuefeng.com/wiki/1252599548343744/1255945147512512)

## 什么是反射
&emsp;&emsp;反射就是Reflection，Java的反射是指<font color=red>程序在运行期可以拿到一个对象的所有信息。</font>

正常情况下，如果我们要调用一个对象的方法，或者访问一个对象的字段，通常会传入对象实例：
```java
// Main.java
import com.itranswarp.learnjava.Person;

public class Main
{
    String getFullName(Person p) {return p.getFirstName() + " " + p.getLastName();}
}
```
但是，<font color=red>如果不能获得Person类，只有一个Object实例</font>：
```java
String getFullName(Object obj) {return ???}
```
怎么办？有人会说：强制转型啊！
```java
String getFullName(Object obj)
{
    Person p = (Person) obj;
    return p.getFirstName() + " " + p.getLastName();
}
```
强制转型的时候，你会发现一个问题：编译上面的代码，仍然需要引用Person类。所以，<font color=red>反射是为了解决在运行期，对某个实例一无所知的情况下，如何调用其方法。</font>

## Class类
&emsp;&emsp;除了`int`等<font color=red>基本类型</font>外，Java的其他类型全部都是`class(包括interface)`。例如：`String, Object, Runnable, Exception...`。`class(包括interface)`的本质是数据类型(Type)。<font color=red>无继承关系的数据类型之间无法赋值</font>：
```java {.line-numbers highlight=2}
Number n = new Double(123.456); // OK
String s = new Double(123.456); // compile error!
```
而`class`是由JVM在执行过程中动态加载的。JVM在第一次读取到一种class类型时，将其加载进内存。<font color=red>每加载一种class，JVM就为其创建一个Class类型的实例，并关联起来</font>。注意：这里的`Class类型`是一个名叫`Class`的`class`。它长这样：
```Java
public final class Class
{
  private Class() {}
}
```
以`String`类为例，当JVM加载`String`类时，它首先读取`String.class`文件到内存，然后，为`String`类创建一个`Class`实例并关联起来：`Class cls = new Class(String);`。这个`Class`实例是JVM内部创建的，如果我们查看JDK源码，可以发现`Class`类的构造方法是`private`，只有JVM能创建`Class`实例，我们自己的Java程序是无法创建Class实例的。所以，JVM持有的每个`Class`实例都指向一个数据类型(class或interface)：
<div align=center><img src=Reflection/JVM持有的实例指向一个数据类型.png></div>

一个`Class`实例包含了该`class`的所有完整信息：
<div align=center><img src=Reflection/Class实例包含class完整信息.png></div>

&emsp;&emsp;<font color=red>由于JVM为每个加载的`class`创建了对应的`Class`实例，并在实例中保存了该`class`的所有信息，包括类名、包名、父类、实现的接口、所有方法、字段等，因此，如果获取了某个`Class`实例，我们就可以通过这个`Class`实例获取到该实例对应的`class`的所有信息。<u>这种通过`Class`实例获取`class`信息的方法称为反射(Reflection)</u>。</font>

如何获取一个`class`的`Class`实例？

1. 方法一：直接通过一个`class`的静态变量`class`获取：`Class cls = String.class;`。
2. 方法二：如果有一个实例变量，可以通过该实例变量提供的`getClass()`方法获取：
`String s = "Hello"; Class cls = s.getClass();`。
3. 方法三：如果知道一个`class`的完整类名，可以通过静态方法`Class.forName()`获取：
`Class cls = Class.forName("java.lang.String");`。

因为`Class实例`在JVM中是唯一的，所以，上述方法获取的`Class实例`是同一个实例。可以用`==`比较两个`Class`实例：
```Java {.line-numbers highlight=6}
Class cls1 = String.class;

String s = "Hello";
Class cls2 = s.getClass();

boolean sameClass = cls1 == cls2; // true
```
注意一下`Class`实例比较和`instanceof`的差别：
```java
Integer n = new Integer(123);

boolean b1 = n instanceof Integer; // true，因为n是Integer类型
boolean b2 = n instanceof Number; // true，因为n是Number类型的子类

boolean b3 = n.getClass() == Integer.class; // true，因为n.getClass()返回Integer.class
boolean b4 = n.getClass() == Number.class; // false，因为Integer.class!=Number.class
```
<font color=red>用`instanceof`不但匹配指定类型，还匹配指定类型的子类。而用`==`判断`class实例`可以精确地判断数据类型，但不能作子类型比较。</font>

通常情况下，我们应该用`instanceof`判断数据类型，因为面向抽象编程的时候，我们不关心具体的子类型。只有在需要精确判断一个类型是不是某个`class`的时候，我们才使用`==`判断`class`实例。

因为反射的目的是为了获得某个实例的信息。因此，当我们拿到某个`Object`实例时，我们可以通过反射获取该`Object`的`class`信息：
`void printObjectInfo(Object obj) {Class cls = obj.getClass();}`

要从`Class`实例获取获取的基本信息，参考下面的代码：

```java
public class Hello
{
    public static void main(String[] args)
    {
        printClassInfo("".getClass());
        System.out.println();
        printClassInfo(Runnable.class);
        System.out.println();
        printClassInfo(java.time.Month.class);
        System.out.println();
        printClassInfo(String[].class);
        System.out.println();
        printClassInfo(int.class);
    }

    static void printClassInfo(Class cls)
    {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null)
        {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}
```

运行结果：
```
Class name: java.lang.String
Simple name: String
Package name: java.lang
is interface: false
is enum: false
is array: false
is primitive: false

Class name: java.lang.Runnable
Simple name: Runnable
Package name: java.lang
is interface: true
is enum: false
is array: false
is primitive: false

Class name: java.time.Month
Simple name: Month
Package name: java.time
is interface: false
is enum: true
is array: false
is primitive: false

Class name: [Ljava.lang.String;
Simple name: String[]
is interface: false
is enum: false
is array: true
is primitive: false

Class name: int
Simple name: int
is interface: false
is enum: false
is array: false
is primitive: true
```

注意到数组(例如`String[]`)也是一种Class，而且不同于`String.class`，它的类名是`[Ljava.lang.String`。此外，JVM为每一种基本类型(如int)也创建了Class，通过`int.class`访问。

如果获取到了一个`Class`实例，我们就可以通过该`Class`实例来创建对应类型的实例：
```Java
// 获取String的Class实例:
Class cls = String.class;
// 创建一个String实例:
String s = (String) cls.newInstance();
```

上述代码相当于`new String()`。通过`Class.newInstance()`可以创建类实例，它的局限是：只能调用`public`的无参数构造方法。带参数的构造方法，或者`非public`的构造方法都无法通过`Class.newInstance()`被调用。

### 动态加载
&emsp;&emsp;JVM在执行Java程序的时候，并不是一次性把所有用到的`class`全部加载到内存，而是第一次需要用到`class`时才加载。例如：
```java
// Main.java
public class Main
{
    public static void main(String[] args)
    {
        if (args.length > 0) {create(args[0]);}
    }

    static void create(String name) {Person p = new Person(name);}
}
```
当执行`Main.java`时，由于用到了`Main`，因此，JVM首先会把`Main.class`加载到内存。然而，并不会加载`Person.class`，除非程序执行到`create()`方法，JVM发现需要加载`Person`类时，才会首次加载`Person.class`。如果没有执行`create()`方法，那么`Person.class`根本就不会被加载。这就是JVM`动态加载class`的特性。

`动态加载class`的特性对于Java程序非常重要。<font color=red>利用JVM`动态加载class`的特性，我们才能在运行期根据条件加载不同的实现类</font>。例如，Commons Logging总是优先使用Log4j，只有当Log4j不存在时，才使用JDK的logging。利用JVM动态加载特性，大致的实现代码如下：
```java
// Commons Logging优先使用Log4j:
LogFactory factory = null;
if (isClassPresent("org.apache.logging.log4j.Logger"))
{factory = createLog4j();}
else {factory = createJdkLog();}

boolean isClassPresent(String name)
{
    try
    {
        Class.forName(name);
        return true;
    } catch (Exception e) {return false;}
}
```
这就是为什么我们只需要把Log4j的jar包放到classpath中，Commons Logging就会自动使用Log4j的原因。

### 小结
* JVM为每个加载的`class`及`interface`创建了对应的`Class`实例来保存`class`及`interface`的所有信息；
* 获取一个`class`对应的`Class`实例后，就可以获取该`class`的所有信息；
* 通过`Class`实例获取`class`信息的方法称为`反射(Reflection)`；
* JVM总是`动态加载class`，可以在运行期根据条件来控制加载`class`。


# 反射解释二
[参考资料——HOW2J](https://how2j.cn/k/reflection/reflection-class/108.html)

## 获取类对象

类对象概念： 所有的类，都存在一个类对象，**这个类对象用于提供类本身的信息**，比如有几种构造方法， 有多少属性，有哪些普通方法。

**什么是类对象：**

在理解类对象之前，先说我们熟悉的对象之间的区别：
garen和teemo都是Hero对象，他们的区别在于，各自有不同的名称，血量，伤害值。

然后说说类之间的区别：
Hero和Item都是类，他们的区别在于有不同的方法，不同的属性。

类对象，就是用于**描述这种类，都有什么属性，什么方法**的。


**获取类对象：**

获取类对象有3种方式：
1. Class.forName
2. Hero.class
3. new Hero().getClass()

在一个JVM中，**一种类，只会有一个类对象存在**。所以以上三种方式取出来的类对象，都是一样的。

注：准确的讲是一个`ClassLoader`下，一种类，只会有一个类对象存在。通常一个JVM下，只会有一个``ClassLoader。

```java {.line-numbers highlight=5}
package reflection;

public class TestReflection {
    public static void main(String[] args) {
        String className = "reflection.Hero";  // 不能只写成"Hero"
        try{
            Class class1 = Class.forName(className);
            Class class2 = Hero.class;
            Class class3 = new Hero().getClass();
            System.out.println("class1: " + class1 + "! class2: " + class2 + "! class3: " + class3);
            System.out.println(class1 == class2);
            System.out.println(class1 == class3);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
/*
class1: class reflection.Hero! class2: class reflection.Hero! class3: class reflection.Hero
true
true
 */

```

**获取类对象的时候，会导致类属性被初始化**

为`Hero`增加一个**静态属性**，并且在**静态初始化块**里进行初始化。

无论什么途径获取类对象，都会导致静态属性被初始化，而且只会执行一次。（除了直接使用`Class c = Hero.class`这种方式，这种方式不会导致静态属性被初始化）。

```java
package reflection;

public class Hero {
    String name;
    private float hp;  // 血量
    private int damage;
    private int id;

    static String copyright;

    static {
        System.out.println("在静态初始化块里对静态属性进行初始化。。。");
        copyright = "版权所有！";
    }
}
```

## 创建对象

与传统的通过`new`来获取对象的方式不同，反射机制，会**先拿到Hero的`类对象`，然后通过类对象获取`构造器对象`，再通过构造器对象创建一个`对象`**。

```java
package reflection;

import java.lang.reflect.Constructor;

public class CreateObject {
    public static void main(String[] args) {
        // 传统的使用new的方式创建对象
        Hero hero1 = new Hero();
        hero1.name = "hero1";
        System.out.println(hero1);

        //使用反射的方式创建对象
        try{
            String className = "reflection.Hero";
            // 类对象
            Class class1 = Class.forName(className);  // ClassNotFoundException
            // 构造器
            Constructor constructor = class1.getConstructor();  // NoSuchMethodException
            //通过构造器实例化
            Hero hero2 = (Hero) constructor.newInstance();  // ...Exception
            hero2.name = "hero2";
            System.out.println(hero2);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
/*
reflection.Hero@10bedb4
reflection.Hero@103dbd3
 */
```

## 访问并修改属性

为了访问属性，把`Hero`中`name`修改为`public`。

```java
package reflection;

import java.lang.reflect.Field;

public class ModifyValue {
    public static void main(String[] args) {
        Hero hero = new Hero();
        System.out.println(hero.name);

        //使用传统方式修改name的值
        hero.name = "hero1";
        System.out.println(hero.name);

        // 通过反射修改属性的值
        try{
            //获取类Hero的名字叫做name的字段
            Field field = hero.getClass().getDeclaredField("name");
            //修改这个字段的值
            field.set(hero, "hero2");
            System.out.println(hero.name);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
/*
null
hero1
hero2
 */
```

### getField和getDeclaredField的区别

这两个方法都是用于获取字段：
- `getField`只能获取`public`的，包括从父类继承来的字段。

- `getDeclaredField`可以获取本类所有的字段，包括`private`的，但是不能获取继承来的字段。 (注：这里只能获取到private的字段，但并**不能访问该private字段的值**，除非加上`setAccessible(true))`。


## 调用方法

首先为Hero的name属性，增加setter和getter。通过反射机制调用Hero的setName。

```java
package reflection;

import java.lang.reflect.Method;

public class UsingMethod {
    public static void main(String[] args) {
        Hero hero = new Hero("hero");

        try{
            // 获取名字叫做setName，参数类型是String的方法
            Method method = hero.getClass().getMethod("setName", String.class);
            // 对hero对象，调用这个方法
            method.invoke(hero, "hero1");

            System.out.println(hero.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 反射机制的用处

反射非常强大，但是学习了之后，会不知道该如何使用，反而觉得还不如直接调用方法来的直接和方便。

通常来说，需要在学习了Spring的`依赖注入`，`反转控制`之后，才会对反射有更好的理解。

这里举两个例子，来演示一下反射的一种实际运用。


**业务类**

首先准备两个业务类，这两个业务类很简单，就是各自都有一个业务方法，分别打印不同的字符串：

```java
package reflection;

public class Service1 {
    public void deService1(){
        System.out.println("业务1");
    }
}

package reflection;

public class Service2 {
    public void deService2(){
        System.out.println("业务2");
    }
}
```


**非反射方式**

当需要从第一个业务方法切换到第二个业务方法的时候，使用非反射方式，必须修改代码，并且重新编译运行，才可以达到效果：

```java
package reflection;
 
public class Test {
    public static void main(String[] args) {
        // new Service1().doService1();
        new Service2().doService2();
    }
}
```

**反射方式**

使用反射方式，首先准备一个**配置文件**`spring.txt`，放在`src`目录下。里面存放的是**类的名称**，和要**调用的方法名**。

在测试类Test中，首先取出类名称和方法名，然后通过反射去调用这个方法。

当需要从调用第一个业务方法，切换到调用第二个业务方法的时候，不需要修改一行代码，也不需要重新编译，**只需要修改配置文件**`spring.txt`，再运行即可。

这也是Spring框架的最基本的原理，只是它做的更丰富，安全，健壮。

```java
package reflection;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

public class Test {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) {
        try {
            //从spring.txt中获取类名称和方法名称
            File springConfigFile = new File("D:\\Learning_Java\\Java_Code\\src\\spring.txt");
            Properties springConfig = new Properties();
            springConfig.load(new FileInputStream(springConfigFile));
            String className = (String) springConfig.get("class");  // springConfig.get("class")是Object
            String methodName = (String) springConfig.get("method");

            //根据类名称获取类对象
            Class class1 = Class.forName(className);
            //根据方法名称，获取方法对象
            Method method = class1.getMethod(methodName);
            //获取构造器
            Constructor constructor = class1.getConstructor();
            //根据构造器，实例化出对象
            Object service = constructor.newInstance();
            //调用对象的指定方法
            method.invoke(service);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```