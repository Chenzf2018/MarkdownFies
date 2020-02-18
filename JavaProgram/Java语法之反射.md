# 什么是反射
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

# Class类
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

## 动态加载
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

## 小结
* JVM为每个加载的`class`及`interface`创建了对应的`Class`实例来保存`class`及`interface`的所有信息；
* 获取一个`class`对应的`Class`实例后，就可以获取该`class`的所有信息；
* 通过`Class`实例获取`class`信息的方法称为`反射(Reflection)`；
* JVM总是`动态加载class`，可以在运行期根据条件来控制加载`class`。
