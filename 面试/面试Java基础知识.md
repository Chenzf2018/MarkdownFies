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

* `==`和`!=`比较的是`对象的引用`。
* 如果想比较两个对象的实际内容是否相同，必须使用所有对象都适用的特殊方法`equals()`。但这个方法<font color=red>不适用于基本类型</font>，基本类型直接使用`==`和`!=`即可。

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


## `String`和`StringBuffer`, `StringBuilder`的区别

它们可以储存和操作`字符串`，即包含`多个字符`的字符数据。`String`类提供了数值<font color=red>不可改变的字符串</font>。而`StringBuffer`和`StringBuilder`类的<font color=red>对象能够被多次的修改，并且不产生新的未使用对象</font>。

`StringBuilder`类在Java5中被提出，它和`StringBuffer`之间的最大不同在于<font color=red>`StringBuilder`的方法不是线程安全的（不能同步访问）</font>。但由于` StringBuilder`相较于`StringBuffer`有<font color=red>速度优势</font>，所以多数情况下建议使用`StringBuilder`类。然而在应用程序要求线程安全的情况下，则必须使用`StringBuffer`类。

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
