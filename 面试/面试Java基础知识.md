# Java基础知识

## 正则表达式

Java中的`String`类提供了支持正则表达式操作的方法，包括：`matches()`、`replaceAll()`、`replaceFirst()`、`split()`。此外，Java中可以用`Pattern`类表示正则表达式对象，它提供了丰富的API进行各种正则表达式操作：

```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpTest
{
    public static void main(String[] args)
    {
        String str = "成都市(成华区)(武侯区)(高新区)";
        Pattern p = Pattern.compile(".*?(?=\\()");
        Matcher m = p.matcher(str);
        if (m.find())
            System.out.println(m.group());
    }
}
/*
成都市
 */
```

## `int`与`Integer`区别

Java是一个近乎纯洁的`面向对象`编程语言，但是为了编程的方便还是引入了`基本数据类型`，但是为了能够<font color=red>将这些基本数据类型当成对象操作</font>，Java为每一个基本数据类型都引入了对应的`包装类型（wrapper class）`，`int`的包装类就是`Integer`，从Java5开始引入了`自动装箱/拆箱机制`，使得二者可以相互转换。

Java为每个原始类型提供了包装类型：
- 原始类型：boolean，char，byte，short，int，long，float，double
- 包装类型：Boolean，Character，Byte，Short，Integer，Long，Float，Double

<font color=red>`new`将对象存储在`堆`里，故用`new`创建一个对象——特别是小的、简单的变量，往往不是很有效</font>。对于这些类型，Java不用`new`来创建这些变量，而是<font color=red>创建一个并非是引用的“自动”变量。这个变量直接存储“值”，并置于栈(stack)中，因此更加高效</font>。基本类型具有的包装器类，使得可以在堆中创建一个非基本对象，用来表示对应的基本类型。

```java
public class AutoUnboxingTest
{
    public static void main(String[] args)
    {
        Integer a = new Integer(3);
        Integer b = 3;  // 将3自动装箱成Integer类型
        int c = 3;

        System.out.println(a==b);  // false 两个引用没有引用同一对象
        System.out.println(a==c);  // true a自动拆箱成int类型再和c比较
    }
}
```

## ==/equals()

```java
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

* `==`和`!=`比较的是**对象的引用**。
* 如果想比较两个对象的**实际内容**是否相同，必须使用所有对象都适用的特殊方法`equals()`。但这个方法<font color=red>不适用于基本类型</font>，基本类型直接使用`==`和`!=`即可。

<font color=red>`equals()`的默认行为是比较引用</font>：

```java
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

<font color=red>`equals()`的默认行为是比较引用。所以除非在自己的新类中覆盖`equals()`方法，否则默认是比较引用，而不是实际内容</font>。**大多数Java库类通过覆写`equals()`方法比较对象的内容而不是其引用**。

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

### 注意

对于`Integer var=?`在-128至127之间的赋值，Integer对象是在`IntegerCache.cache`产生，会复用已有对象，**这个区间内的Integer值可以直接使用==进行判断**；但是**这个区间之外的所有数据**，都会**在堆上产生**，并不会复用已有对象，推荐**使用equals方法**进行判断。

```java
public class Test {
    public static void main(String[] args) {
        /**
         * 对于Integer var=?在-128至127之间的赋值，
         * Integer对象是在IntegerCache.cache产生，会复用已有对象，
         * 这个区间内的Integer值可以直接使用==进行判断，
         * 但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，
         * 推荐使用equals方法进行判断。
         */
        Integer integer1 = 100;
        Integer integer2 = 100;
        // 所有的包装类对象之间值的比较，全部使用equals方法比较
        // true
        System.out.println(integer1 == integer2);

        Integer integer3 = 1000;
        Integer integer4 = 1000;
        // false
        System.out.println(integer3 == integer4);
    }
}
```
Integer的作者在写这个类时，为了**避免重复创建对象**，对Integer值做了缓存，如果这个值在缓存范围内，直接返回缓存好的对象，否则new一个新的对象返回。

`Integer.java`
```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        // 如果在缓存的数组中，直接取数组里相应的对象返回
        return IntegerCache.cache[i + (-IntegerCache.low)];
    // 不在缓存的数组里，直接new一个对象返回
    return new Integer(i);
}
```

## 为什么重写equals()方法为什么要重写hashCode()方法

**参考：**
https://www.jianshu.com/p/3819388ff2f4
https://zhuanlan.zhihu.com/p/43001449

equals()用于**判断两个对象是否相等**；hashCode()被设计是用来**使得哈希容器能高效的工作**。

在Java中，有一些哈希容器，比如Hashtable、HashMap等等，当我们调用这些容器的诸如`get(Object obj)`方法时，**容器的内部肯定需要判断一下当前obj对象在容器中是否存在**，然后再进行后续的操作。一般来说，判断是够存在，肯定是要将obj对象和容器中的每个元素一一进行比较，要使用equals()才是正确的。

但是**如果哈希容器中的元素有很多的时候，使用equals()必然会很慢**。这个时候我们想到一种替代方案就是`hashCode()`：当我们调用哈希容器的`get(Object obj)`方法时，它会**首先利用查看当前容器中是否存在有相同哈希值的对象**，如果不存在，那么直接返回null；如果存在，**再调用当前对象的equals()方法比较一下看哈希处的对象是否和要查找的对象相同**；如果不相同，那么返回null。如果相同，则返回该哈希处的对象。

**`hashCode()`返回一个int类型，两个int类型比较起来要快很多**。所以说，`hashCode()`被设计用来使得哈希容器能高效的工作。也**只有在哈希容器中，才使用hashCode()来比较对象是否相等**，但要注意这种比较是一种弱的比较，还要利用equals()方法最终确认。

equals方法和hashCode方法都是Object类中的方法：
```java
public boolean equals(Object obj) {
    return (this == obj);
}

public native int hashCode();
```

equals方法在其内部是调用了"=="，所以说**在不重写equals方法的情况下，equals方法是比较两个对象是否具有相同的引用**，即是否指向了同一个内存地址。

而hashCode是一个本地方法，他返回的是这个**对象的内存地址**。

hashCode的通用规定：

- 在应用程序的执行期间，只要对象的equals方法的比较操作所用到的信息没有被修改，那么对同一个对象的多次调用，hashCode方法都必须始终返回同一个值。在一个应用程序与另一个应用程序的执行过程中，执行hashCode方法所返回的值可以不一致。

- 如果两个对象根据equals(Object)方法比较是相等的，那么调用这两个对象中的hashCode方法都必须产生同样的整数结果。即：**如果两个对象的equals()相等，那么他们的hashCode()必定相等**。**如果两个对象的hashCode()不相等，那么他们的equals()必定不等**。

- 如果两个对象根据equals(Object)方法比较是不相等的，那么调用这两个对象中的hashCode方法，则不一定要求hashCode方法必须产生不同的结果。但是程序员应该知道，给不相等的对象产生截然不同的整数结果，有可能提高散列表的性能。

由上面三条规定可知，**如果重写了equals方法而没有重写hashCode方法的话，就违反了第二条规定**。**相等的对象必须拥有相等的hash code**。


不重写hashCode方法所带来的严重后果：
```java {.line-numbers highlight=12-25}
import java.util.HashMap;
import java.util.Map;

public class Test {
    static class Person {
        private String name;

        public Person (String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            // 判断是否是同一对象
            if (this == obj) {
                return true;
            }

            if (obj instanceof Person) {
                Person person = (Person) obj;
                // 直接调用String的equals方法
                return name.equals(person.name);
            }

            return false;
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("czf");
        Person person2 = new Person("czf");

        Map<Person, Integer> hashMap = new HashMap<>();
        hashMap.put(person1, 1);

        // true
        System.out.println(person1.equals(person2));
        // false
        System.out.println(hashMap.containsKey(person2));
    }
}
```
对于第一个输出true我们很容易知道，因为我们重写了equals方法，只要两个对象的name属性相同就会返回ture。

但是为什么第二个为什么输出的是false呢？

就是因为我们没有重写hashCode方法。所以我们得到一个结论：**如果一个类重写了equals方法但是没有重写hashCode方法，那么该类无法结合所有基于散列的集合（HashMap，HashSet）一起正常运作**。

```java {.line-numbers highlight=28-31}
import java.util.HashMap;
import java.util.Map;

public class Test {
    static class Person {
        private String name;

        public Person (String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            // 判断是否是同一对象
            if (this == obj) {
                return true;
            }

            if (obj instanceof Person) {
                Person person = (Person) obj;
                // 直接调用String的equals方法
                return name.equals(person.name);
            }

            return false;
        }

        @Override
        public int hashCode() {
            // 直接调用String的hashCode方法
            return name.hashCode();
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("czf");
        Person person2 = new Person("czf");

        Map<Person, Integer> hashMap = new HashMap<>();
        hashMap.put(person1, 1);

        // true
        System.out.println(person1.equals(person2));
        // true
        System.out.println(hashMap.containsKey(person2));
    }
}
```



## Java字符串两种声明方式在堆内存中不同的体现

```java
public class Test {
    public static void main(String[] args) {
        String string1 = "100";
        String string2 = "100";
        // true
        System.out.println(string1 == string2);

        // 'new String("100")' is redundant
        String string3 = new String("100");
        String string4 = new String("100");
        // false
        System.out.println(string3 == string4);
    }
}
```
当代码执行到`String s1 = "100"`时，会先看常量池里有没有字符串刚好是“100”这个对象，如果没有，在常量池里创建初始化该对象，并**把引用指向它**，如下图，绿色部分为常量池，存在于**堆内存**中：

<div align=center><img src=Pictures\equals.png width=80%></div>

当执行到`String s2 = "100"`时，发现常量池已经有了100这个值，于是不再在常量池中创建这个对象，而是**把引用直接指向了该对象**：

<div align=center><img src=Pictures\equals1.png width=80%></div>

这时候我们打印`System.out.println(s1 == s2)`时，由于**==是判断两个对象是否指向同一个引用**，所以这儿打印出来的就应该是true。

继续执行到`Strings3 = new String("100")`这时候我们加了一个new关键字，这个关键字呢就是告诉JVM，你直接**在堆内存里开辟一块新的内存**：

<div align=center><img src=Pictures\equals2.png width=80%></div>

继续执行`String s4 = new String("100")`：

<div align=center><img src=Pictures\equals3.png width=80%></div>

这时候再打印`System.out.println(s3 == s4)`那一定便是false了，因为s3和s4不是指向对一个引用（对象）。

我们在写代码过程中，为了**避免重复的创建对象**，尽量使用`String s1 ="123"`而不是String s1 = new String("123")，因为**JVM对前者给做了优化**。


## `String`和`StringBuffer`, `StringBuilder`的区别

它们可以储存和操作`字符串`，即包含`多个字符`的字符数据。`String`类提供了数值<font color=red>不可改变的字符串</font>。而`StringBuffer`和`StringBuilder`类的<font color=red>对象能够被多次的修改，并且不产生新的未使用对象</font>。

`StringBuilder`类在Java5中被提出，它和`StringBuffer`之间的最大不同在于<font color=red>`StringBuilder`的方法不是线程安全的（不能同步访问）</font>。但由于` StringBuilder`相较于`StringBuffer`有<font color=red>速度优势</font>，所以多数情况下建议使用`StringBuilder`类。然而在应用程序要求线程安全的情况下，则必须使用`StringBuffer`类。

`String.java`
```java
public boolean equals(Object anObject) {
    // 判断是否是同一个对象
    if (this == anObject) {
        return true;
    }
    // 判断obj是否是Person的一个实例
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
            char v1[] = value;
            char v2[] = anotherString.value;
            int i = 0;
            while (n-- != 0) {
                if (v1[i] != v2[i])
                    return false;
                i++;
            }
            return true;
        }
    }
    return false;
}
```

```java
public class stringBuffer
{
    public static void main(String[] args)
    {
        StringBuffer sBuffer = new StringBuffer("上海");
        System.out.println(sBuffer);
        sBuffer.append("交通大学");  
        System.out.println(sBuffer);  // 不产生新的未使用对象
    }
}
```

`String`不是最基本的数据类型，`java.lang.String`类是`final`类型的，因此**不可以继承**这个类、**不能修改**这个类。为了提高效率节省空间，应该用`StringBuffer`类。


## 数组(`Array`)与列表(`ArrayList`)区别

> `ArrayList`是一种“会自动扩增容量的`Array`”

- `Array`可以包含**基本类型**和**对象类型**，`ArrayList`只能包含**对象类型**。
- `Array`数组在存放的时候一定是**同种类型的元素**。`ArrayList`就不一定了，因为`ArrayList`可以存储`Object`。
- `Array`大小是固定的，`ArrayList`的大小是动态变化的。
- `ArrayList`提供了更多的方法和特性，比如：`addAll()`，`removeAll()`，`iterator()`等等。
- 对于**基本类型数据**，集合`Array`使用**自动装箱**来减少编码工作量。但是，当处理固定大小的基本数据类型的时候，这种方式相对比较慢。


## 值传递和引用传递区别

值传递和引用传递之前的区别的重点：
| | 值传递 | 引用传递 |
|:-: | :-: | :-: |
|根本区别 | 会创建副本（copy） | 不会创建副本 | 
|结果 | 函数中无法改变原始对象 | 函数中可以改变原始对象 | 

你有一把钥匙，当你的朋友想要去你家的时候，如果你**直接**把你的钥匙给他了，这就是**引用传递**。这种情况下，如果他对这把钥匙做了什么事情，比如他在钥匙上刻下了自己名字，那么这把钥匙还给你的时候，你自己的钥匙上也会多出他刻的名字。

你有一把钥匙，当你的朋友想要去你家的时候，你**复制**了一把新钥匙给他，自己的还在自己手里，这就是值传递。这种情况下，他对这把钥匙做什么都不会影响你手里的这把钥匙。

### `基本类型`与`引用类型`

```
int num = 60;
String str = "MDove";
```

<div align=center><img src=Pictures/基本与引用类型.jpg width=30%></div>

`num`是基本类型，值就直接保存在**变量**中。而`str`是引用类型，变量中保存的只是**实际对象的地址**。一般称这种变量为"引用"，引用指向实际对象，实际对象中保存着内容。

```
num = 666;
str = "MDove is cool.";
```

<div align=center><img src=Pictures/基本与引用类型1.jpg width=40%></div>

对于基本类型num，赋值运算符会直接改变变量的值，**原来的值被覆盖掉**。对于引用类型str，赋值运算符会**改变引用中所保存的地址**，原来的地址被覆盖掉。但是原来的对象不会被改变（重要）。"MDove" 字符串对象没有被改变（没有被任何引用所指向的对象是垃圾，会被垃圾回收器回收）。

**参数传递基本上就是赋值操作。**

### 实参和形参

形式参数：是在**定义函数名和函数体**的时候使用的参数，目的是用来接收调用该函数时传入的参数。

实际参数：在**调用有参函数**时，主调函数和被调函数之间有数据传递关系。在主调函数中调用一个函数时，函数名后面括号中的参数称为“实际参数”。

```java
public static void main(String[] args) 
{
    ParamTest pt = new ParamTest();
    pt.sout("Hollis"); //实际参数为 Hollis
}

public void sout(String name) // 形式参数为 name
{ 
    System.out.println(name);
}
```

### 值传递和引用传递

值传递（pass by value）是指在调用函数时将**实际参数复制**一份传递到函数中，这样在函数中如果对参数进行修改，将不会影响到实际参数。

引用传递（pass by reference）是指在调用函数时将**实际参数的地址直接**传递到函数中，那么在函数中对参数所进行的修改，将影响到实际参数。

示例1：

```java
public static void main(String[] args) 
{
    ParamTest pt = new ParamTest();

    int i = 10;
    pt.pass(10);
    System.out.println("print in main , i is " + i);
}

public void pass(int j) 
{
    j = 20;
    System.out.println("print in pass , j is " + j);
}

/*
print in pass , j is 20
print in main , i is 10
*/
```

示例2：

```java
public static void main(String[] args) 
{
    ParamTest pt = new ParamTest();

    String name = "Hollis";
    pt.pass(name);
    System.out.println("print in main , name is " + name);
}

public void pass(String name) // 此name非彼name
{
    name = "hollischuang";
    System.out.println("print in pass , name is " + name);
}
/*
print in pass , name is hollischuang
print in main , name is Hollis
*/
```

在`pass`方法中使用`name = "hollischuang";`试着去更改`name`的值，阴差阳错的**直接改变了`name`的引用的地址**。因为，<font color=red>这段代码会`new`一个`String`，再把引用交给`name`，即等价于`name = new String("hollischuang");`</font>。而原来的那个`"Hollis"`字符串还是由实参持有着的，所以，并没有修改到实际参数的值。

<div align=center><img src=Pictures/按值传递2.jpg width=50%></div>

示例3：

```java
public static void main(String[] args) 
{
    ParamTest pt = new ParamTest();

    User hollis = new User();
    hollis.setName("Hollis");
    hollis.setGender("Male");
    pt.pass(hollis);
    System.out.println("print in main , user is " + hollis);
}

public void pass(User user) // User user = hollis;
{
    user.setName("hollischuang");
    System.out.println("print in pass , user is " + user);
}

/*
print in pass , user is User{name='hollischuang', gender='Male'}
print in main , user is User{name='hollischuang', gender='Male'}
*/
```

<div align=center><img src=Pictures/按值传递1.jpg width=80%></div>

在参数传递的过程中，实际参数的地址`0x1213456`被拷贝给了形参，在这个方法中，并没有对形参本身进行修改，而是<font color=red>修改形参持有的地址中存储的内容</font>。

值传递和引用传递的区别并不是传递的内容。而是<font color=red>实参到底有没有被复制一份给形参</font>。在判断实参内容有没有受影响的时候，要看传的的是什么，如果你传递的是个地址，那么就看这个地址的变化会不会有影响，而不是看地址指向的对象的变化。

示例4：

```java {.line-numbers highlight=14}
public static void main(String[] args) 
{
    ParamTest pt = new ParamTest();

    User hollis = new User();
    hollis.setName("Hollis");
    hollis.setGender("Male");
    pt.pass(hollis);
    System.out.println("print in main , user is " + hollis);
}

public void pass(User user) 
{
    user = new User();  // 重新创建了一个对象/可理解为不同的两个对象
    user.setName("hollischuang");
    user.setGender("Male");
    System.out.println("print in pass , user is " + user);
}
/*
print in pass , user is User{name='hollischuang', gender='Male'}
print in main , user is User{name='Hollis', gender='Male'}
*/
```

<div align=center><img src=Pictures/按值传递.jpg width=80%></div>

当在`main`中创建一个`User`对象的时候，在堆中开辟一块内存，其中保存了`name`和`gender`等数据。然后`hollis`持有该内存的地址`0x123456`。当尝试调用`pass`方法，并且`hollis`作为实际参数传递给形式参数`user`的时候，会把这个地址交给`user`，这时，**`user`也指向了这个地址**。然后在`pass`方法内对参数进行修改的时候，即`user = new User();`，会重新开辟一块`0x456789`的内存，赋值给`user`。后面对`user`的任何修改都不会改变内存`0x123456`的内容。

这里是把实际参数的引用的地址复制了一份，传递给了形式参数。所以，上面的参数其实是**值传递**，把实参对象引用的地址当做值传递给了形式参数。


Java中其实还是**值传递**的，只不过对于**对象参数**，值的内容是**对象的引用**。


## 为什么会出现4.0-3.6=0.40000001？

**二进制的小数**无法精确的表达**十进制小数**，计算机在计算十进制小数的过程中要先转换为二进制进行计算，这个过程中出现了误差。（就像十进制无法精确表达1/3一样。）

十进制的情况下：123相当于`1*10^2+2*10^1+3*10^0`；
1/10由二进制表示小数的时候只能够表示能够用`1/(2^n)`：
- 0.5能够表示，因为它可以表示成为`1/2`
- 0.75也能够表示，因为它可以表示成为`1/2+1/(2^2)`
- 0.875也能够表示，因为它可以表示成为`1/2+1/(2^2)+1/(2^3)`
- 0.9375也能够表示，因为它可以表示成为`1/2+1/(2^2)+1/(2^3)+1/(2^4)`

```
System.out.println(1-0.063);  // 0.937
System.out.println(1-0.937);  // 0.06299999999999994
```

但是0.1不能够精确表示，因为它不能表示成为`1/(2^n)`的和的形式

> 小数的进制表示了1的细分的份数，十进制的1能细分10份，二进制的1只能细分两份。
一斤肉用十进制切可以切出1两来，用二进制只能切出五两。去买三两肉，十进制可切三个一两给你；用二进制就麻烦了，给你五两太多，只能把五两肉再切2份，成了二两五，又不够三两，就再把剩下的二两五再切2份，成了一两二分五，二两五加上一两二分五就超了三两了。不行，要把那另一个一两二分五再切2份去合成三两肉……但这样切下去永远不能给你正好的三两肉。

<font color=red>浮点数值采用二进制系统表示， 而在二进制系统中无法精确地表示</font>。浮点数值不适用于无法接受舍入误差的金融计算中。例如，`System.out.println(2.0-1.1)`将打印出0.8999999999999999, 而不是人们想象的0.9。


如果在数值计算中不允许有任何舍入误差， 就应该使用`BigDecimal类`。

### `BigInteger`和`BigDecimal`

如果基本的整数和浮点数精度不能够满足需求，那么可以使用`java.math`包中的两个很有用的类：`Biglnteger`和`BigDecimal`这两个类可以处理包含任意长度数字序列的数值。`Biglnteger`类实现了任意精度的整数运算，`BigDecimal`实现了任意精度的浮点数运算。

使用静态的`valueOf`方法可以**将普通的数值转换为大数值**：`Biglnteger a = Biglnteger.valueOf(100); `

遗憾的是，不能使用人们熟悉的算术运算符（如：`+`和`*`) 处理大数值。而需要使用大数值类中的`add`和`multiply`方法：
```
Biglnteger c = a.add(b); // c = a + b 
Biglnteger d = c.multipiy(b.add(Biglnteger.valueOf(2))); // d = c * (b + 2)
```


# 多线程

## Thread.Sleep(0)的作用

`Thread.Sleep(0)`并非是真的要线程挂起0毫秒，意义在于**触发操作系统立刻重新进行一次CPU竞争**，**竞争的结果也许是当前线程仍然获得CPU控制权，也许会换成别的线程获得CPU控制权**。

<div align=center><img src=Pictures\线程状态转换.png></div>

我们可能经常会用到`Thread.Sleep`函数来使线程挂起一段时间。那么你有没有正确的理解这个函数的用法呢？

思考下面这两个问题：

- 假设现在是`2017-4-7 12:00:00.000`，如果我调用一下`Thread.Sleep(1000)`，在`2017-4-7 12:00:01.000`的时候，这个线程会不会被唤醒？
- `Thread.Sleep(0)`，既然是Sleep 0毫秒，那么和去掉这句代码相比，有啥区别么？

先回顾一下操作系统原理：

操作系统中，**CPU竞争**有很多种策略。Unix系统使用的是**时间片算法**，而Windows则属于**抢占式**的。

在**时间片**算法中，**所有的进程排成一个队列**。操作系统按照他们的顺序，给每个进程分配一段时间，即该进程允许运行的时间。如果在时间片结束时进程还在运行，则CPU将被剥夺并分配给另一个进程。**如果进程在时间片结束前阻塞或结束，则CPU当即进行切换**。调度程序所要做的就是维护一张就绪进程列表，当进程用完它的时间片后，它被移到队列的末尾。

**抢占式**算法（**Java虚拟机采用抢占式调度模型**），就是说**如果一个进程得到了CPU时间，除非它自己放弃使用CPU，否则将完全霸占 CPU**。因此可以看出，在抢占式操作系统中，操作系统假设所有的进程都是**人品好**的，**会主动退出CPU**。在抢占式操作系统中，假设有若干进程，操作系统会根据他们的**优先级、饥饿时间**（已经多长时间没有使用过CPU了），给他们算出一个总的优先级来。操作系统就会把CPU交给**总优先级**最高的这个进程。当进程执行完毕或者自己主动挂起后，操作系统就会**重新计算一次所有进程的总优先级**，然后再挑一个优先级最高的把CPU控制权交给他。


我们用分蛋糕的场景来描述这两种算法：

假设有源源不断的蛋糕（源源不断的时间），一副刀叉（一个CPU），10个等待吃蛋糕的人（10个进程）。
- 如果是Unix操作系统来负责分蛋糕，那么他会这样定规矩：**每个人上来吃1分钟，时间到了换下一个。最后一个人吃完了就再从头开始**。于是，不管这10个人是不是优先级不同、饥饿程度不同、饭量不同，每个人上来的时候都可以吃1分钟。当然，如果有人本来不太饿，或者饭量小，吃了30秒钟之后就吃饱了，那么他可以跟操作系统说：我已经吃饱了（**挂起**）。于是操作系统就会让下一个人接着来。
- 如果是Windows操作系统来负责分蛋糕的，那么场面就很有意思了。他会这样定规矩：我会根据你们的优先级、饥饿程度去给你们每个人计算一个优先级。优先级最高的那个人，可以上来吃蛋糕——吃到你不想吃为止。等这个人吃完了，我再重新根据优先级、饥饿程度来计算每个人的优先级，然后再分给优先级最高的那个人。
  可能有些人具有高优先级，于是她就可以经常来吃蛋糕。可能另外一个人优先级特别低，于是好半天了才轮到他一次（**随着时间的推移，他会越来越饥饿，因此算出来的总优先级就会越来越高，因此总有一天会轮到他的**）。而且，如果一不小心让一个大胖子得到了刀叉，因为他饭量大，可能他会霸占着蛋糕连续吃很久很久，导致旁边的人在那里咽口水。。。
  而且，还可能会有这种情况出现：操作系统现在计算出来的结果，**5号总优先级最高，而且高出别人一大截**。因此就叫5号来吃蛋糕。5号吃了一小会儿，觉得没那么饿了，于是说“我不吃了”（挂起）。因此操作系统就会重新计算所有人的优先级。因为5号刚刚吃过，因此她的饥饿程度变小了，于是总优先级变小了；而其他人因为多等了一会儿，饥饿程度都变大了，所以总优先级也变大了。不过**这时候仍然有可能5号的优先级比别的都高**，只不过现在只比其他的高一点点——但她仍然是总优先级最高的。因此操作系统就会说：5号上来吃蛋糕。

那么，`Thread.Sleep`函数是干吗的呢？

还用刚才的分蛋糕的场景来描述。上面的场景里面，5号在吃了一次蛋糕之后，觉得已经有8分饱了，她觉得在未来的半个小时之内都不想再来吃蛋糕了，那么她就会跟操作系统说：在未来的半个小时之内不要再叫我上来吃蛋糕了。这样，操作系统在随后的半个小时里面重新计算所有人总优先级的时候，就会忽略5号。Sleep函数就是干这事的，他告诉操作系统**在未来的多少毫秒内我不参与CPU竞争**。


看完了`Thread.Sleep`的作用，我们再来想想文章开头的两个问题：

对于第一个问题，答案是：不一定。因为你只是告诉操作系统：在未来的1000毫秒内我不想再参与到CPU竞争。那么1000毫秒过去之后，这时候也许另外一个线程正在使用CPU，那么这时候操作系统是不会重新分配CPU的，直到那个线程挂起或结束；况且，即使这个时候恰巧轮到操作系统进行CPU分配，那么当前线程也不一定就是总优先级最高的那个，CPU还是可能被其他线程抢占去。

与此相似的，Thread有个`Resume`函数，是用来唤醒挂起的线程的。好像上面所说的一样，这个函数只是“告诉操作系统我**从现在起开始参与CPU竞争了**”，这个函数的调用并不能马上使得这个线程获得CPU控制权。


对于第二个问题，答案是：有，而且区别很明显。

假设我们刚才的分蛋糕场景里面，有一个7号，她的优先级也非常非常高，所以操作系统总是会叫道她来吃蛋糕。而且，7号也非常喜欢吃蛋糕，而且饭量也很大。不过，**7号人品很好，她很善良，她没吃几口就会想：如果现在有别人比我更需要吃蛋糕，那么我就让给他**。因此，她可以每吃几口就跟操作系统说：我们来重新计算一下所有人的总优先级吧。不过，操作系统不接受这个建议——因为操作系统不提供这个接口。于是7号就换了个说法：“**在未来的0毫秒之内不要再叫我上来吃蛋糕了**”。这个指令操作系统是接受的，于是此时操作系统就会重新计算大家的总优先级——注意**这个时候是连7号一起计算的**，因为“0毫秒已经过去了”嘛。因此如果没有比7号更需要吃蛋糕的人出现，那么下一次7号还是会被叫上来吃蛋糕。

因此，`Thread.Sleep(0)`的作用，就是“**触发操作系统立刻重新进行一次CPU竞争**”。**竞争的结果也许是当前线程仍然获得CPU控制权，也许会换成别的线程获得CPU控制权**。


## 线程池