# 测试开发

产品的最后一个防线；利用测试方法、理论，自动化脚本进行测试；最终生成测试报告！

- 需求设计
  - 细化需求
- 模块开发
  - 测试用例
  - 环境准备
- 模块测试
  - 测试执行
  - 问题反馈
- 整合出包
  - 产品打包

|   | 黑盒测试  | 白盒测试  |
|---|---|---|
| 功能  | 针对功能的测试  | 针对结构的测试  |
| 优势  | 确保从用户角度出发进行测试  | 能够对程序内部的特定部位进行覆盖测试  |
| 应用  | 等价类划分、边界值分析法、因果图法、判定表法  | 语句覆盖、判定覆盖、条件覆盖、判定条件覆盖、条件组合覆盖、路径覆盖  |

白盒测试重点看代码的逻辑、算法、结构是否正确，要求必须懂得代码，需要编写测试用例。

**灰盒测试**：先黑盒，哪出现问题，再查看代码白盒

- 在**单元测试**时，白盒应用相对较多
- 在**集成测试**时，灰盒应用相对较多


## 设计测试用例时应该考虑哪些方面

个测试用例就是测试人员用以**测试被测软件的某个特性或特性组合的一组数据**。这组数据可能是从用户处得来的**实际的一组数据**，也可能是测试人员**专门设计出来的测试软件某些功能的一组数据**。

测试用例需要考虑的**四个基本要素**是输入、输出、操作和测试环境；另外，测试用例需要考虑的是测试类型（**功能、性能**、安全……）。除了对整体流程及功能注意外，还要注意强度测试、性能测试、压力测试、边界值测试、稳定性测试、安全性测试等多方面。

测试用例的基本要素：功能、功能项、测试目的、前提条件、测试要点、预期结果。

## 黑盒测试

黑盒测试也称**功能测试或数据驱动测试**，它是在已知产品所应具有的功能，**通过测试来检测每个功能是否都能正常使用**。

在测试时，**把程序看作一个不能打开的黑盆子**，在完全不考虑程序内部结构和内部特性的情况下，测试者**在程序接口进行测试，它只检查程序功能是否按照需求规格说明书的规定正常使用**，程序是否能适当地接收输入数锯而产生正确的输出信息，并且保持外部信息（如数据库或文件）的完整性。

- 黑盒测试方法主要有等价类划分、边值分析、因—果图、错误推测等，主要用于**软件确认测试**。 
- “黑盒”法着**眼于程序外部结构、不考虑内部逻辑结构、针对软件界面和软件功能进行测试**。
- “黑盒”法是**穷举输入测试**，只有把所有可能的输入都作为测试情况使用，才能以这种方法查出程序中所有的错误。实际上测试情况有无穷多个，人们不仅要测试所有合法的输入，而且还要对那些**不合法但是可能的输入进行测试**。

主要考虑以下几个方面：

- 正确性：计算结果，命名等方面
- 可用性：是否可以满足软件的需求说明
- 边界条件：输入部分的边界值，就是使用一般书中说的等价类划分，试试最大最小和非法数据等等。
- 性能：正常使用的时间内系统完成一个任务需要的时间，多人同时使用的时候响应时间在可以接受范围内。
- 压力测试：**多用户情况**可以考虑使用压力测试工具，建议将压力和性能测试结合起来进行。如果有负载平衡的话还要在服务器端打开监测工具 , 查看服务器 CPU 使用率，内存占用情况，如果有必要可以模拟大量数据输入，对硬盘的影响等等信息。如果有必要的话必须进行性能优化(软硬件都可以) 。
- 错误恢复：错误处理，页面数据验证，包括突然间断电，输入脏数据等。
- 安全性测试：防火墙、补丁包、杀毒软件等。




### 等价类划分

**把所有可能的输入数据，即程序的输入域划分成若干部分，然后从每一个子集中选取少数具有代表性的数据作为测试用例**。

该方法是一种重要的，常用的**黑盒测试用例设计方法**。等价类划分可有两种不同的情况：有效等价类和无效等价类。
- 有效等价类：对于程序的规格说明来说是合理的，**有意义的输入数据构成的集合**。利用有效等价类可检验程序是否实现了规格说明中所规定的功能和性能。
- 无效等价类：与有效等价类的定义相反。

### 边界值分析法

边界值分析方法是对等价类划分方法的补充。长期的测试工作经验告诉我们，**大量的错误是发生在输入或者输出范围的边界上**，而不是发生在输入输出范围的内部，因此针对各种边界情况设计测试用例，可以查出更多的错误。

使用边界值分析方法设计测试用例，首先应**确定边界情况**，通常输入和输出等价类的边界，就是应着重测试的边界情况，**应当选取正好等于，刚刚大于或刚刚小于边界的值作为测试数据**，而不是选取边界类中的典型值或任意值作为测试数据。

### 因果图方法

前面介绍的等价类划分方法和边界值分析方法，都是**着重考虑输入条件，但未考虑输入条件之间的联系**。考虑输入条件之间的相互组合，可能会产生一些新的情况。因果图方法最终生成的是**判定表**，它适合于**检查程序输入条件之间的各种组合情况**。

### 判定表法

判定表（Decision Table）是**分析和表达多逻辑条件下执行不同操作的情况下的工具**。在程序设计发展的初期，判定表就已被当作编写程序的辅助工具了。由于它可以把复杂的逻辑关系和多种条件组合的情况表达得既具体又明确。

判定表通常由四个部分组成：
- 条件桩（Condition Stub）：列出了问题得所有条件。通常认为列出得条件的次序无关紧要。
- 动作桩（Action Stub）：列出了问题规定可能采取的操作。这些操作的排列顺序没有约束。
- 条件项（Condition Entry）：列出针对它左列条件的取值。在所有可能情况下的真假值。
- 动作项（Action Entry）：列出在条件项的各种取值情况下应该采取的动作。

规则：任何一个条件组合的特定取值及其相应要执行的操作，在判定表中贯穿条件项和动作项的一列就是一条规则。显然，判定表中列出多少组条件取值，也就有多少条规则，既条件项和动作项有多少列。

## 白盒测试

白盒测试也称**结构测试或逻辑驱动测试**，它是知道产品内部工作过程，可通过测试来检测产品内部动作是否按照规格说明书的规定正常进行，**按照程序内部的结构测试程序，检验程序中的每条通路是否都有能按预定要求正确工作**，而不顾它的功能，白盒测试的主要方法有逻辑驱动、基路测试等，主要用于软件验证。

白盒测试的方法：总体上分为**静态方法**和**动态方法**两大类。

静态分析是一种**不通过执行程序而进行测试的技术**。静态分析的关键功能是**检查软件的表示和描述是否一致，没有冲突或者没有歧义**。

动态分析的主要特点是当软件系统在模拟的或真实的环境中执行之前、之中和之后，对软件系统行为的分析。动态分析包含了程序在受控的环境下使用特定的期望结果进行正式的运行。它显示了一个系统在检查状态下是正确还是不正确。在动态分析技术中，最重要的技术是路径和分支测试。**覆盖测试方法属于动态分析方法**。

### 语句覆盖

**设计若干个测试用例，运行被测程序，使得每一可执行语句至少执行一次**。这里的“若干个”，意味着使用测试用例越少越好。语句覆盖率的公式可以表示如下：

语句覆盖率=被评价到的语句数量/可执行的语句总数 x 100%

### 判定覆盖

**使设计的测试用例保证程序中每个判断的每个取值分支（true or false）至少经历一次**。

### 条件覆盖

**条件覆盖是指选择足够的测试用例，使得运行这些测试用例时，判定中每个条件的所有可能结果至少出现一次，但未必能覆盖全部分支。**

条件覆盖要检查每个符合子表达式值为真和假两种情况，要独立衡量每个子表达式的结果，以**确保每个子表达式的值为真和假两种情况都被测试到**。

### 判定条件覆盖

判定条件覆盖就是设计足够的测试用例，**使得判断中每个条件的所有可能取值至少执行一次，同时每个判断的所有可能判断结果至少执行，即要求各个判断的所有可能的条件取值组合至少执行一次**。

### 条件组合覆盖

在白盒测试法中，选择足够的测试用例，**使所有判定中各条件判断结果的所有组合至少出现一次**，满足这种覆盖标准成为条件组合覆盖。

### 路径覆盖

**每条可能执行到的路径至少执行一次**


### 总结

语句覆盖是一种最弱的覆盖，判定覆盖和条件覆盖比语句覆盖强，满足判定/条件覆盖标准的测试用例一定也满足判定覆盖、条件覆盖和语句覆盖，条件组合覆盖是除路径覆盖外最强的，路径覆盖也是一种比较强的覆盖，但未必考虑判定条件结果的组合，并不能代替条件覆盖和条件组合覆盖。



## 淘宝登录界面测试用例设计

https://www.cnblogs.com/fuqingwei/p/11203987.html
https://www.nowcoder.com/discuss/tag/134?type=2&expTag=2678

首先你要先**了解客户的需求**
- 登录界面应该是**弹出窗口式的还是直接在网页里面的**。
- 用户名的长度、和密码的强度（就是密码组合起来后的简单程度）等。
- 用户对界面的美观是不是有特殊的要求（UI界面的测试）。
- 剩下的就是设计用例了，等价类，边界值等等。

请记住一点，任何测试不管是什么要需要从了解需求开始。

### 功能测试

- 什么都不输入操作提交按钮后代码的处理动作是如何的（**非空检查**）
- 输入正确的用户名和密码，点击提交按钮后验证是否正确登录。（**正常输入**）
- 输入错误的用户名或者密码验证点击登录后时候会提示失败（**错误校验**）
- 登录成功后是能否跳转到正确的页面（功能校验）

# Java基础知识

## Object类方法

Object是所有类的父类，任何类都默认继承Object。Object类到底实现了哪些方法？

1．clone方法
保护方法，实现对象的浅复制，只有实现了Cloneable接口才可以调用该方法，否则抛出CloneNotSupportedException异常。

2．getClass方法
final方法，获得运行时类型。

3．toString方法
该方法用得比较多，一般子类都有覆盖。

4．finalize方法
该方法用于释放资源。因为无法确定该方法什么时候被调用，很少使用。

5．equals方法
该方法是非常重要的一个方法。一般equals和==是不一样的，但是在Object中两者是一样的。子类一般都要重写这个方法。

6．hashCode方法
该方法用于哈希查找，重写了equals方法一般都要重写hashCode方法。这个方法在一些具有哈希功能的Collection中用到。

**一般必须满足obj1.equals(obj2)==true。可以推出obj1.hash Code()==obj2.hashCode()，但是hashCode相等不一定就满足equals**。不过为了提高效率，应该尽量使上面两个条件接近等价。

7．wait方法
wait方法就是使当前线程等待该对象的锁，当前线程必须是该对象的拥有者，也就是具有该对象的锁。wait()方法一直等待，直到获得锁或者被中断。wait(long timeout)设定一个超时间隔，如果在规定时间内没有获得锁就返回。

调用该方法后当前线程进入睡眠状态，直到以下事件发生。

（1）其他线程调用了该对象的notify方法。

（2）其他线程调用了该对象的notifyAll方法。

（3）其他线程调用了interrupt中断该线程。

（4）时间间隔到了。

此时该线程就可以被调度了，如果是被中断的话就抛出一个InterruptedException异常。

8．notify方法
该方法唤醒在该对象上等待的某个线程。

9．notifyAll方法
该方法唤醒在该对象上等待的所有线程。

## 递归与迭代

- 程序结构不同
    - 递归是**重复调用函数自身实现循环**。递归最大的有点就是**把一个复杂的算法分解成若干相同的可重复的步骤**。递归调用函数时需要**消耗栈空间**，栈空间的大小取决于递归调用的深度。
    - 迭代是**函数内某段代码实现循环**。 其中，迭代与普通循环的区别是：迭代时，循环代码中参与运算的变量同时是保存结果的变量，当前保存的结果作为下一次循环计算的初始值。

- 算法结束方式不同
    - 递归循环中，遇到满足终止条件的情况时**逐层返回**来结束。
    - 迭代则**使用计数器结束循环**。 

```java
// 递归
int factorial (int n) {
    if (n == 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
} 

//迭代
int factorial (int n) {
    int product = 1;
    for(int i = 2; i < n; i++) {
        product *= i;
    }
    return product;
}
```

## 内存中的堆和栈

- Stack
    - 位于通用RAM(随机访问存储器)中，栈指针若向下移动，则分配新的内存，若向上移动，则释放那些内存。这是一种快速有效的分配存储方法，仅次于寄存器。**创建程序时，Java系统必须知道存储在栈内所有项的确切生命周期**，以便上下移动栈指针。这一约束限制了程序的灵活性，所以虽然某些Java数据存储于栈中——特别是对象引用，但是Java对象并不存储于其中。
    - **对象引用、基本数据类型**存储在栈！

- Heap
    - 一种通用的内存池(也位于RAM区)，用于**存放所有的Java对象**。堆不同于栈的好处是：编译器不需要知道存储的数据在堆里存活多长时间。因此，在堆里分配存储有很大的灵活性。**当需要一个对象时，只需用new写一行简单的代码，当执行这行代码时，会自动在堆里进行存储分配**。当然，为这种灵活性必须要付出相应的代价：**用堆进行存储分配和清理可能比用栈进行存储分配需要更多的时间**。

- 堆是动态的概念，是在运行的时候确定，它的大小是动态变化的，所以数据大小不确定时，使用堆，它的访问效率较慢。同一进程的线程对堆上数据均可访问；

- 堆是由手动分配释放的，通过malloc和new等动态申请内存的语句使用，也需要用户手动回收（或可能在程序结束时OS自动回收），而对于面向对象程序来说，new出来的任何对象，无论是对象内部的成员变量，局部变量，类变量，他们指向的对象都存储在堆内存中（但指针本身存在栈中），**一般速度较栈慢**；**存储地址通常是链式的，内存较大不会溢出**。


- 栈是静态的概念，大小在编译的时候确定，函数调用的数据都是在栈上，不可被另一个函数访问(**不同函数之间的栈数据不能共享**)。

- 栈是用来存储函数内部（包括main函数）的局部变量和方法调用和函数参数值，是由系统自动分配的，一般速度较快；存储地址是连续且存在有限栈容量，会出现溢出现象。


## 为什么需要包装类

Java是面向对象的语言，但并不是“纯面向对象”的，因为我们经常用到的**基本数据类型就不是对象**。但是我们在实际应用中经常需要将基本数据转化成对象，以便于操作。比如：**将基本数据类型存储到Object[]数组或集合中的操作**等等。

为了解决这个不足，Java在设计类时为每个基本数据类型设计了一个对应的类进行代表，这样八个和基本数据类型对应的类统称为包装类(Wrapper Class)。


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

<font color=red>`new`将对象存储在`堆`里，故用`new`创建一个对象——特别是小的、简单的变量，往往不是很有效</font>。对于这些类型，Java不用`new`来创建这些变量，而是<font color=red>创建一个并非是引用的“自动”变量。这个变量直接存储“值”，并置于栈(stack)中，因此更加高效</font>。**基本类型具有的包装器类，使得可以在堆中创建一个非基本对象，用来表示对应的基本类型**。

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

- Stack
    - 位于通用RAM(随机访问存储器)中，栈指针若向下移动，则分配新的内存，若向上移动，则释放那些内存。这是一种快速有效的分配存储方法，仅次于寄存器。**创建程序时，Java系统必须知道存储在栈内所有项的确切生命周期，以便上下移动栈指针**。这一约束限制了程序的灵活性，所以虽然某些Java数据存储于栈中——特别是**对象引用**，但是Java对象并不存储于其中。
- Heap
    - 一种通用的内存池(也位于RAM区)，用于存放所有的Java对象。**堆不同于栈的好处是：编译器不需要知道存储的数据在堆里存活多长时间**。因此，**在堆里分配存储有很大的灵活性**。当需要一个对象时，只需用new写一行简单的代码，当执行这行代码时，**会自动在堆里进行存储分配**。当然，为这种灵活性必须要付出相应的代价：**用堆进行存储分配和清理可能比用栈进行存储分配需要更多的时间**。


## ==与equals()

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

- `==`和`!=`比较的是**对象的引用(对象的地址)**。
- 如果想比较两个对象的**实际内容--对象**是否相同，必须使用所有对象都适用的特殊方法`equals()`。但这个方法<font color=red>不适用于基本类型</font>，基本类型直接使用`==`和`!=`即可。

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

## 重写equals()方法为什么要重写hashCode()方法

**参考：**
https://www.jianshu.com/p/3819388ff2f4
https://zhuanlan.zhihu.com/p/43001449


<font color=red>如果只重写equals方法而不重写hashcode方法，很可能会造成两个不同的对象，它们的hashcode也相等，造成冲突。</font>

在不重写`equals`方法的情况下，`equals`方法是比较**两个对象是否具有相同的引用，即是否指向了同一个内存地址**；hashCode()<font color=red>对不同的object会返回唯一的哈希值</font>，被设计是用来**使得哈希容器能高效的工作**。

在Java中，有一些哈希容器，比如Hashtable、HashMap等等，当我们调用这些容器的诸如`get(Object obj)`方法时，**容器的内部肯定需要判断一下当前obj对象在容器中是否存在**，然后再进行后续的操作。一般来说，判断是够存在，肯定是要将obj对象和容器中的每个元素一一进行比较，要使用equals()才是正确的。

但是<font color=red>如果哈希容器中的元素有很多的时候，使用equals()必然会很慢</font>。这个时候我们想到一种替代方案就是`hashCode()`：当我们调用哈希容器的`get(Object obj)`方法时，它会<font color=red>首先利用查看当前容器中是否存在有相同哈希值的对象</font>，如果不存在，那么直接返回null；如果存在，<font color=red>再调用当前对象的equals()方法比较一下看哈希处的对象是否和要查找的对象相同</font>；如果不相同，那么返回null。如果相同，则返回该哈希处的对象。

**`hashCode()`返回一个int类型，两个int类型比较起来要快很多**。所以说，`hashCode()`被设计用来使得哈希容器能高效的工作。也<font color=red>只有在哈希容器中，才使用hashCode()来比较对象是否相等，但要注意这种比较是一种弱的比较，还要利用equals()方法最终确认</font>。

equals方法和hashCode方法都是Object类中的方法：
```java
public boolean equals(Object obj) {
    return (this == obj);
}

public native int hashCode();
```

**equals方法在其内部是调用了"=="**，所以说**在不重写equals方法的情况下，equals方法是比较两个对象是否具有相同的引用**，即是否指向了同一个内存地址。

而hashCode是一个本地方法，他返回的是这个**对象的内存地址**。

hashCode的通用规定：

- 在应用程序的执行期间，只要对象的equals方法的比较操作所用到的信息没有被修改，那么<font color=red>对同一个对象的多次调用，hashCode方法都必须始终返回同一个值</font>。在一个应用程序与另一个应用程序的执行过程中，执行hashCode方法所返回的值可以不一致。

- 如果两个对象根据equals(Object)方法比较是相等的，那么调用这两个对象中的hashCode方法都必须产生同样的整数结果。即：**<font color=red>如果两个对象的equals()相等，那么他们的hashCode()必定相等</font>**。**如果两个对象的hashCode()不相等，那么他们的equals()必定不等**。

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

        // false
        System.out.println(person1 == person2);
    }
}
```
对于第一个输出true我们很容易知道，因为我们重写了equals方法，只要两个对象的name属性相同就会返回ture。

**<font color=red>如果两个对象的equals()相等，那么他们的hashCode()必定应该相等</font>**。

但是为什么第二个为什么输出的是false呢？

就是因为我们**没有重写hashCode方法**。所以我们得到一个结论：**<font color=red>如果一个类重写了equals方法但是没有重写hashCode方法，那么该类无法结合所有基于散列的集合（HashMap，HashSet）一起正常运作</font>**。

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

        // false
        System.out.println(person1 == person2);
    }
}
```



## Java字符串两种声明方式在堆内存中不同的体现

String不可变是因为在JDK中**String类被声明为一个final类，且类内部的value字节数组也是final的，只有当字符串是不可变时字符串池才有可能实现，字符串池的实现可以在运行时节约很多heap空间，因为不同的字符串变量都指向池中的同一个字符串**。

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
当代码执行到`String s1 = "100"`时，会先看**常量池**里有没有字符串刚好是“100”这个对象，如果没有，在常量池里创建初始化该对象，并**把引用指向它**，如下图，绿色部分为常量池，存在于**堆内存**中：

<div align=center><img src=Pictures\equals.png width=80%></div>

当执行到`String s2 = "100"`时，发现常量池已经有了100这个值，于是不再在常量池中创建这个对象，而是**把引用直接指向了该对象**：

<div align=center><img src=Pictures\equals1.png width=80%></div>

这时候我们打印`System.out.println(s1 == s2)`时，由于`==`**是判断两个对象是否指向同一个引用**，所以这儿打印出来的就应该是true。

继续执行到`Strings3 = new String("100")`这时候我们加了一个new关键字，这个关键字呢就是告诉JVM，你直接**在堆内存里开辟一块新的内存**：

<div align=center><img src=Pictures\equals2.png width=80%></div>

继续执行`String s4 = new String("100")`：

<div align=center><img src=Pictures\equals3.png width=80%></div>

这时候再打印`System.out.println(s3 == s4)`那一定便是false了，因为s3和s4不是指向对一个引用（对象）。

我们在写代码过程中，为了**避免重复的创建对象**，尽量使用`String s1 ="123"`而不是`String s1 = new String("123")`，因为**JVM对前者给做了优化**。


### String.equals


`java.lang.String.java`
```java
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
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

- 首先会判断要比较的两个字符串它们的**引用是否相等**。如果引用相等的话，直接返回true，不相等的话继续下面的判断；
- 然后再判断被比较的对象**是否是String的实例**，如果不是的话直接返回false
- 如果是的话，**再比较两个字符串的长度是否相等**，如果长度不想等的话也就没有比较的必要了；
- 长度如果相同，会比较字符串中的**每个字符是否相等**，一旦有一个字符不相等，就会直接返回 false。

- String类型**比较不同对象内容**是否相同，应该用equals()

```java
// 对象不同，内容相同
String s1 = new String("java");
String s2 = new String("java");
 
System.out.println(s1==s2);           //false
System.out.println(s1.equals(s2));    //true

// 同一对象
String s1 = new String("java");
String s2 = s1;
 
System.out.println(s1==s2);           //true
System.out.println(s1.equals(s2));    //true

String s1 = "java";
String s2 = "java";
 
System.out.println(s1==s2);           //true
System.out.println(s1.equals(s2));    //true
```


## `String`和`StringBuffer`, `StringBuilder`的区别

### String

char类型只能表示一个字符。为了表示一串字符，使用称为String (字符串)的数据类型。String类型不是基本类型，而是**引用类型(reference type)**。

String不可变是因为在JDK中**String类被声明为一个final类，且类内部的value字节数组也是final的，只有当字符串是不可变时字符串池才有可能实现，字符串池的实现可以在运行时节约很多 heap 空间，因为不同的字符串变量都指向池中的同一个字符串**。

`String`方法：

<div align=left><img src=Pictures\String方法.jpg></div>

<div align=center><img src=Pictures\String方法1.jpg></div>

<div align=center><img src=Pictures\String方法2.jpg></div>

<div align=center><img src=Pictures\String方法3.jpg></div>

一、由基本数据型态转换成String

String类别中已经提供了将基本数据型态转换成**String的static方法** ，也就是`String.valueOf()`这个参数多载的方法，有以下几种：
（1）`String.valueOf(boolean b)` : 将boolean变量b转换成字符串 
（2）`String.valueOf(char c)` : 将 char 变量 c 转换成字符串 
（3）`String.valueOf(char[] data)` : 将 char 数组 data 转换成字符串 
（4）`String.valueOf(char[] data, int offset, int count)` : 将 char 数组 data 中 由 data[offset] 开始取 count 个元素 转换成字符串 
（5）`String.valueOf(double d)` : 将 double 变量 d 转换成字符串 
（6）`String.valueOf(float f)` : 将 float 变量 f 转换成字符串 
（7）`String.valueOf(int i)` : 将 int 变量 i 转换成字符串 
（8）`String.valueOf(long l)` : 将 long 变量 l 转换成字符串 
（9）`String.valueOf(Object obj)` : 将 obj 对象转换成 字符串, 等于 obj.toString() 

用法如下: 
`int i = 10; String str = String.valueOf(i); `
这时候str就会是"10" 

 

二、由String转换成数字的基本数据型态 

要将String转换成基本数据型态转，大多需要使用基本数据型态的包装类别 

比如说String转换成byte ，可以使用`Byte.parseByte(String s)`，这一类的方法如果无法将s分析则会丢出`NumberFormatException `

（1）`byte : Byte.parseByte(String s)`: 将s转换成byte 

（2）`Byte.parseByte(String s, int radix)` : 以 radix 为基底 将 s 转换为 byte ，比如说 `Byte.parseByte("11", 16)` 会得到 17 

（3）`double : Double.parseDouble(String s)` : 将 s 转换成 double 

（4）`float : Double.parseFloat(String s)` : 将 s 转换成 float 

（5）`int : Integer.parseInt(String s)` : 将 s 转换成 int 

（6）`long : Long.parseLong(String s)`

#### 字符串和数字间的转换

```java
int intValue = Integer.parseInt(intString);
double doubleValue = Double.parseDouble(doubleString);
String s = number + "";
```

#### 从控制台读取字符串
- `hasNex()`是检测还**有没有下一个输入**
- `next()`是**指针移动到当前下标，并取出下一个输入**
- `hasNextLine()`是检测**下一行有没有输入**
- `nextLine()`把**指针移动到下一行，然后取出当前这一行的输入**


- `next()`方法读取以空白字符结束的字符串(即’ ’、’\t’、'\f’、’\r’或’\n')。

```java
import java.util.Scanner;

public class ReadingString
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter three words separated by spaces: ");
        String s1 = input.next();  
        String s2 = input.next();
        String s3 = input.next();
        System.out.println("s1 is " + s1);
        System.out.println("s2 is " + s2);
        System.out.println("s3 is " + s3);
    }
}

/*
Output:
Enter three words separated by spaces: Welcome To Java
s1 is Welcome
s2 is To
s3 is Java
*/
```

- `nextLine()`方法读取一整行文本。`nextLine()`方法读取以**按下回车键**为结束标志的字符串。

```java
System.out.print("Enter a line: ");
String s = input.nextLine(); 
System.out.println("The line entered is " + s);

Enter a line: Welcome to Java
The line entered is Welcome to Java
```

- 为了避免输入错误，不要在`nextByte()、nextShort()、nextInt()、nextLong()、nextFloat、nextDouble()`和`next()`之后使用`nextLine()`

方法`nextByte()、nextShort()、nextInt()、nextLong()、nextFloat()、nextDouble()`和`next()`等都称为**标记读取方法(token-reading method)**，因为它们会读取用**分隔符**分隔开的标记。**默认情况下，分隔符是空格**。可以使用`useDelimiter(String regex)`方法设置新的分隔符模式。


一个输入方法是如何工作的呢？

一个标记读取方法首先**跳过任意分隔符(默认情况下是空格)**，然后读取一个以分隔符结束的标记。然后，对应`于nextByte()、nextShort()、nextInt()、nextLong()、nextFloat()`和`nextDouble()`, 这个标记就分别被自动地转换为一个`byte、short、int、long、float`或`double`型的值。对于`next()`方法而言是无须做转换的。如果标记和期望的类型不匹配，就会抛出一个运行异常`java.util.InputMismatchExceptio`n。


方法`next()`和`nextLine()`都会读取一个字符串。`next()`方法读取一个由**分隔符**分隔的字符串，但是`nextLine()`读取一个以**换行符**结束的行。

行分隔符字符串是由系统定义的，在Windows平台上`\r\n`，而在UNIX平台上是`\n`。为了得到特定平台上的行分隔符，使用`String lineSeparator = System.getProperty("line.separator");`
如果从键盘输入，每行就以**回车键**(Enter key)结束，它对应于**`\n`字符**。


**标记读取方法不能读取标记后面的分隔符**。如果在标记读取方法之后调用`nextLine()`，该方法**读取从这个分隔符开始，到这行的行分隔符结束的字符**。**这个行分隔符也被读取**，但是它不是`nextLine()`返回的字符串部分。

假设一个名为test.txt的**文本文件**包含一行
`34 567`

在执行完下面的代码之后：
```java
Scanner input = new Scanner(new File("test.txt"));
int intValue = input.nextInt();
String line = input.nextLine();
```
**intValue的值为34, 而line包含的字符是`‘ ’‘5’‘6’‘7’`。**

如果输入是**从键盘键入**，那会发生什么呢？

假设为下面的代码**输入34，然后按回车键，接着输入567然后再按回车键**：
```java
Scanner input = new Scanner(System.in);
int intValue = input.nextInt();
String line = input.nextLine();
```
**将会得到intValue值是34, 而line中是一个空的宇符串**。

这是为什么呢？

原因如下：标记读取方法`nextInt()`读取34, 然后在分隔符处停止，这里的分隔符是**行分隔符(回车键)**。`nextLine()`方法**会在读取行分隔符之后结束，然后返回在行分隔符之前的字符串**。因为在行分隔符之前没有字符，所以line是空的。



### StringBuild与StringBuffer

- `String`类提供了数值<font color=red>不可改变的字符串</font>。
- 而`StringBuffer`和`StringBuilder`类的<font color=red>对象能够被多次的修改，并且不产生新的未使用对象</font>。

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
        StringBuffer sBuffer = new StringBuffer("上海");  //上海 
        System.out.println(sBuffer);
        sBuffer.append("交通大学");  // 上海交通大学
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



## 数组的复制

<div align=center><img src=Pictures\数组的复制.png width=60%></div>

该语句并不能将list1引用的数组内容复制给list2, 而只是将list1的**引用值**复制给了list2。在这条语句之后，list1和list2都指向同一个数组。list2原先所引用的数组不能再引用，它就变成了垃圾，会被Java虚拟机自动收回(这个过程称为垃圾回收)。

在Java中，**可以使用赋值语句复制基本数据类型的变量，但不能复制数组**。将一个数组变量赋值给另一个数组变量，实际上是**将一个数组的引用复制给另一个变量，使两个变量都指向相同的内存地址**。

复制数组有三种方法：

- 使用循环语句逐个地复制数组的元素。
- 使用System类中的静态方法arraycopy。
- 使用clone方法复制数组。

```java
int[] sourceArray = {2, 3, 1, 5, 10};
int[] targetArray = new int[sourceArray.length];
for (int i = 0; i < sourceArray.length; i++) {
    targetArray[i] = sourceArray[i];
}
```
或者

Another approach is to use the `arraycopy` method in the `java.lang.System` class to copy arrays instead of using a loop.

`arraycopy(sourceArray, srcPos, targetArray, tarPos, length);`

The parameters `srcPos` and `tarPos` indicate the starting positions in `sourceArray` and `targetArray`, respectively.

`System.arraycopy(sourceArray, 0, targetArray, 0, sourceArray.length);`

arraycopy方法没有给目标数组分配内存空间。复制前必须创建目标数组以及分配给它的内存空间。复制完成后，sourceArray和targetArray具有相同的内容，但占有独立的内存空间。




## 值传递和引用传递区别

- **值传递**是指在调用函数时将**实际参数**复制一份到函数中，这样的话如果函数对其传递过来的形式参数进行修改，将**不会影响到实际参数**。

- **引用传递**是指在调用函数时将**对象的地址**直接传递到函数中，如果在对形式参数进行修改，**将影响到实际参数的值**。

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

对于基本类型num，赋值运算符会直接改变变量的值，**原来的值被覆盖掉**。对于引用类型str，赋值运算符会**改变引用中所保存的地址**，原来的地址被覆盖掉。但是**原来的对象不会被改变**（重要）。"MDove" 字符串对象没有被改变（**没有被任何引用所指向的对象是垃圾，会被垃圾回收器回收**）。

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



## 基本数据类型和引用数据类型的区别

Java中的数据类型分为两大类，**基本数据类型**和**引用数据类型**。

**基本数据类型只有8种(数值——byte、short、int、long(整数)、float、double、字符型(单一的16位Unicode字符)、布尔值(8位))**，引用数据类型非常多，大致包括：类、 接口类型、 数组类型、 枚举类型、 注解类型、 字符串型。例如，`String`类型就是引用类型。简单来说，**所有的非基本数据类型都是引用数据类型**。

<div align=center><img src=Pictures\数值.png></div>

每个**变量**都代表一个存储值的**内存位置**。声明一个变量时，就是在告诉编译器这个变量可以存放什么类型的值。对**基本类型变量**来说，对应内存所存储的值是**基本类型值**。对**引用类型变量**来说，对应内存所存储的值是一个**引用**，是**对象的存储地址**。

<div align=center><img src=Pictures\PrimitiveAndReferenceType.jpg></div>

将一个变量赋值给另一个变量时，另一个变量就被赋予同样的值。
- 对**基本类型变量**而言，就是将一个变量的**实际值**赋给另一个变量。
- 对**引用类型变量**而言，就是将一个变量的**引用**赋给另一个变量。

<div align=center><img src=Pictures\基本类型赋值.jpg></div>

<div align=center><img src=Pictures\引用类型赋值.jpg></div>

执行完赋值语句`c1 = c2`后，`c1`指向`c2`所指向的同一对象。`c1`以前引用的对象就不再有用。现在它就成为垃圾（garbage)。垃圾会占用内存空间。Java运行系统会检测垃圾并自动回收它所占的空间，这个过程称为**垃圾回收**（garbage collection)。



### 存储位置

- 基本变量类型
在方法中定义的非全局基本数据类型变量的具体内容是存储在**栈**中的。

- 引用变量类型
只要是引用数据类型变量，其**具体内容**都是存放在**堆**中的，而**栈**中存放的是其具体内容所在内存的**地址**。

<div align=center><img src=Pictures\基本数据与引用数据类型.png width=40%></div>

### 传递方式

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

    public Book(String name, double price)
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
        Book book = new Book("Java开发指南", 66.6);
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

**调用时为temp在栈中开辟新空间，并指向book的具体内容**，方法执行完毕后temp在栈中的内存被释放掉。

<div align=center><img src=Pictures\传递引用.png width=70%></div>

### 向方法传递对象参数

Java只有一种参数传递方式：**值传递**（pass-by-value)。**传递对象实际上是传递对象的引用**。

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

<div align=center><img src=Pictures\TestPassObject.jpg></div>



## 重载Overload与重写Override

重载方法使得你可以使用**同样的名字**来定义**不同方法**，只要它们的**签名（参数）是不同的**。

- 重载

    - 方法重载是让<font color=red>以统一的方式处理不同类型数据</font>的一种手段。多个同名函数同时存在，具有不同的参数个数/类型。

    - Java的方法重载，就是在类中可以创建多个方法，它们**具有相同的名字，但具有不同的参数**。调用方法时通过传递给它们的**不同参数个数和参数类型**来决定具体使用哪个方法。

    - 重载的时候，**方法名要一样，但是参数类型和个数不一样，返回值类型可以相同也可以不相同。无法以返回型别作为重载函数的区分标准**。

- 重写

    - 父类与子类之间的多态性，对**父类的函数进行重新定义**。如果在子类中定义某方法与其父类**有相同的名称和参数**，我们说该方法被重写。在Java中，子类可继承父类中的方法，而不需要重新编写相同的方法。但有时子类并不想原封不动地继承父类的方法，而是想作一定的修改，这就需要采用**方法的重写**。

    - 若子类中的方法与父类中的某一方法具有相同的方法名、返回类型和参数表，则新方法将覆盖原有的方法。如需父类中原有的方法，可使用**super关键字**，该关键字引用了当前类的父类。

    - 子类函数的访问修饰权限不能少于父类的。

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

使用静态的`valueOf`方法可以**将普通的数值转换为大数值**：`BigInteger a = BigInteger.valueOf(100); `

遗憾的是，不能使用人们熟悉的算术运算符（如：`+`和`*`) 处理大数值。而需要使用大数值类中的`add`和`multiply`方法：
```
BigInteger c = a.add(b); // c = a + b 
BigInteger d = c.multipiy(b.add(BigInteger.valueOf(2))); // d = c * (b + 2)
```


## 移位运算符

HashMap中有以下代码：
```java
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;  //左移
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);  //无符号右移
}
```

### 左移运算符：<<

`左移<<`其实很简单，也就是说**丢弃左边指定位数，右边补0**。

先定义一个int类型的数，十进制的value = 733183670，转换成二进制在计算机中的表示如下：

<div align=center><img src=Pictures\移位运算符.png></div>

value << 1，左移1位($*2^1$)：

<div align=center><img src=Pictures\移位运算符1.png></div>

左移1位后换算成十进制的值为：1466367340，刚好是733183670的两倍.可以在乘2操作时用左移运算符来替代。

value << 8，左移8位看一下：

<div align=center><img src=Pictures\移位运算符2.png></div>

左移8位后变成了十进制的值为：-1283541504，**移动8位后，由于首位变成了1，也就是说成了负数**，在使用中要考虑变成负数的情况。

根据这个规则，左移32位后，右边补上32个0值是不是就变成了十进制的0了？
答案是NO。

**当int类型进行左移操作时，左移位数大于等于32位操作时，会先求余（%）后再进行左移操作**。也就是说左移32位相当于不进行移位操作，左移40位相当于左移8位（40%32=8）。

当long类型进行左移操作时，long类型在二进制中的体现是64位的，因此求余操作的基数也变成了64，也就是说左移64位相当于没有移位，左移72位相当于左移8位（72%64=8）。

**byte，short移位前会先转换为int类型（32位）再进行移位。由于double，float在二进制中的表现比较特殊，因此不能来进行移位操作！**


### 右移运算符：>>

733183670 >> 1：

<div align=center><img src=Pictures\移位运算符3.png></div>

右移1位后换算成十进制的值为：366591835，刚好是733183670的一半。在**除2操作**时可以用右移运算符来替代。

value >> 8：

<div align=center><img src=Pictures\移位运算符4.png></div>

和左移一样，int类型移位大于等于32位时，long类型大于等于64位时，会**先做求余处理再位移处理**，byte，short移位前会先转换为int类型（32位）再进行移位。

以上是正数的位移，我们再来看看**负数的右移运算**，如图，负数intValue：-733183670的二进制表现如下图：

<div align=center><img src=Pictures\移位运算符5.png></div>

右移8位，intValue >> 8：

<div align=center><img src=Pictures\移位运算符6.png></div>

综上所述：右移运算符>>的运算规则也很简单，丢弃右边指定位数，左边补上符号位。


### 无符号右移运算符：>>>

无符号右移运算符>>>和右移运算符>>是一样的，只不过右移时左边是补上符号位，而**无符号右移运算符是补上0**，也就是说，对于正数移位来说等同于：>>，负数通过此移位运算符能移位成正数。

以-733183670>>>8为例：

<div align=center><img src=Pictures\移位运算符7.png></div>

无符号右移运算符>>的运算规则也很简单，丢弃右边指定位数，左边补上0。


## Java四种引用方式

| 引用类型 | GC时JVM内存充足 | GC时JVM内存不足 |
|:--------:|:---------------:|:---------------:|
|  强引用  |     不被回收    |     不被回收    |
|  弱引用  |      被回收     |      被回收     |
|  软引用  |     不被回收    |      被回收     |
|  虚引用  |         |           |

https://juejin.im/post/5a5129f5f265da3e317dfc08

https://blog.csdn.net/Light_makeup/article/details/107301647

https://www.cnblogs.com/mfrank/p/10154535.html

Java的内存回收不需要程序员负责，JVM会在必要时启动Java GC完成垃圾回收。Java以便我们**控制对象的生存周期**，提供给了我们四种引用方式，引用强度从强到弱分别为：强引用、软引用、弱引用、虚引用。

#### 内存泄漏与内存溢出

- 内存泄露本意是**申请的内存空间没有被正确释放**，导致**后续程序里这块内存被永远占用**（不可达），而且**指向这块内存空间的指针不再存在时，这块内存也就永远不可达了**，内存空间就这么一点点被蚕食
  - 比如有10张纸，本来一人一张，画完自己擦了还回去，别人可以继续画，现在有个坏蛋要了纸不擦不还，然后还跑了找不到人了，如此就只剩下9张纸给别人用了，这样的人多起来后，最后大家一张纸都没有了。
  
- 内存溢出是指**存储的数据超出了指定空间的大小**，这时数据就会越界
  - 常见的溢出，是指在栈空间里，分配了超过数组长度的数据，导致多出来的数据覆盖了栈空间其他位置的数据，这种情况发生时，可能会导致程序出现各种难排查的异常行为，或是被有心人利用，修改特定位置的变量数据达到溢出攻击的目的。
  - Java中的内存溢出，一般指(OOM)这种Error，它更像是一种内存空间不足时发生的错误，并且也不会导致溢出攻击这种问题


### 为什么需要回收

**每一个Java程序中的对象都会占用一定的计算机资源**，最常见的，如：每个对象都会在堆空间上申请一定的内存空间。但是除了内存之外，对象还会占用其它资源，如文件句柄，端口，socket等等。**当你创建一个对象的时候，必须保证它在销毁的时候会释放它占用的资源，防止内存泄露。否则程序将会在OOM中结束它的使命**。

在Java中不需要程序员来管理内存的分配和释放，Java有自动进行内存管理的神器——垃圾回收器，**垃圾回收器会自动回收那些不再使用的对象**。

在Java中，不必像C或者C++那样显式去释放内存，不需要了解其中回收的细节，也不需要担心会将同一个对象释放两次而导致内存损坏。所有这些，垃圾回收器都自动帮你处理好了。你只需要保证那些不再被使用的对象的所有引用都已经被释放掉了，否则，你的程序就会像在C++中那样结束在内存泄漏中。

虽然垃圾回收器确实让Java中的内存管理比C、C++中的内存管理容易许多，但是你不能对于内存完全不关心。如果你不清楚JVM到底会在什么条件下才会对对象进行回收，那么就有可能会不小心在代码中留下内存泄漏的bug。

因此，关注对象的回收时机，理解JVM中垃圾收集的机制，可以提高对于这个问题的敏感度，也能在发生内存泄漏问题时更快的定位问题所在。


### 为什么需要引用类型

引用类型是与JVM密切合作的类型，有些引用类型甚至允许在其引用对象在程序中仍需要的时候被JVM释放。

那么，为什么需要这些引用类型呢？

在Java中，垃圾回收器线程一直在默默的努力工作着，但你却无法在代码中对其进行控制。无法要求垃圾回收器在精确的时间点对某些对象进行回收。

**有了这些引用类型之后，可以一定程度上增加对垃圾回收的粒度把控，可以让垃圾回收器在更合适的时机回收掉那些可以被回收掉的对象，而并不仅仅是只回收不再使用的对象**。

这些引用类型各有特点，各有各的适用场景，清楚的了解和掌握它们的用法可以帮助你写出更加健壮的代码。



### 强引用

StrongReference是Java的**默认引用形式**，使用时不需要显示定义。任何通过强引用所使用的对象不管系统资源有多紧张，Java虚拟机宁愿抛出`OutOfMemoryError`错误，使程序异常终止，Java GC都**不会主动回收具有强引用的对象**。

强引用：`A a = new A()`

```java
/**
 * 强引用
 * @author Chenzf
 * @date 2020/07/27
 */

public class StrongReferenceTest {
    public static int M = 1024*1024;

    public static void printlnMemory(String tag) {
        Runtime runtime = Runtime.getRuntime();
        int M = StrongReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory()/M + "M(free)/" + runtime.totalMemory()/M + "M(total)");
    }

    public static void main(String[] args) {
        StrongReferenceTest.printlnMemory("1.原可用内存和总内存");

        //实例化10M的数组并与strongReference建立强引用
        byte[] strongReference = new byte[10 * StrongReferenceTest.M];
        StrongReferenceTest.printlnMemory("2.实例化10M的数组，并建立强引用");
        System.out.println("strongReference : " + strongReference);

        System.gc();
        StrongReferenceTest.printlnMemory("3.GC后");
        System.out.println("strongReference : " + strongReference);

        // strongReference = null;后，强引用断开了
        strongReference = null;
        StrongReferenceTest.printlnMemory("4.强引用断开后");
        System.out.println("strongReference : " + strongReference);

        System.gc();
        StrongReferenceTest.printlnMemory("5.GC后");
        System.out.println("strongReference : " + strongReference);
    }
}
```

```
D:\WinSoftware\Java1.8\jdk1.8.0_65\bin\java.exe

1.原可用内存和总内存:
13M(free)/15M(total)

2.实例化10M的数组，并建立强引用:
3M(free)/15M(total)
strongReference : [B@10bedb4

3.GC后:
14M(free)/25M(total)
strongReference : [B@10bedb4

4.强引用断开后:
14M(free)/25M(total)
strongReference : null

5.GC后:
24M(free)/25M(total)
strongReference : null

Process finished with exit code 0
```

<div align=center><img src=Pictures\StrongReference.png></div>


强引用过多的例子：

```java {.line-numbers highlight=5-7}
import java.util.ArrayList;
import java.util.List;

public class StrongReference {
    // 使用静态集合对象来存储并且在代码中随处使用它们
    // 但是这样，就会阻止垃圾回收器对集合中的对象进行回收和销毁。
    // 从而可能导致OOM的发生
    public static List<Integer> cachedObjs = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100_000_000; i++) {
            cachedObjs.add(i);
        }
    }
}

/*
Output:
Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"
*/
```


#### 总结

- 强引用就是最普通的引用
- 可以使用强引用直接访问目标对象
- 强引用指向的对象在任何时候都不会被系统回收
- 强引用可能会导致**内存泄漏**
- 过多的强引用会导致**OOM**





### 弱引用

如果一个对象只具有弱引用，**无论内存充足与否，Java GC后对象如果只有弱引用将会被自动回收**。

```java
import java.lang.ref.WeakReference;

/**
 * 弱引用
 * @author Chenzf
 * @date 2020/07/27
 */

public class WeakReferenceTest {
    public static int M = 1024*1024;

    public static void printlnMemory(String tag) {
        Runtime runtime = Runtime.getRuntime();
        int M = WeakReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory()/M + "M(free)/" + runtime.totalMemory()/M + "M(total)");
    }

    public static void main(String[] args) {
        WeakReferenceTest.printlnMemory("1.原可用内存和总内存");

        //创建弱引用
        WeakReference<Object> weakRerference = new WeakReference<>(new byte[10 * WeakReferenceTest.M]);
        WeakReferenceTest.printlnMemory("2.实例化10M的数组，并建立弱引用");
        System.out.println("weakRerference.get() : " + weakRerference.get());

        System.gc();
        StrongReferenceTest.printlnMemory("3.GC后");
        System.out.println("weakRerference.get() : " + weakRerference.get());
    }
}
```

``` {.line-numbers highlight=8-10}
1.原可用内存和总内存:
13M(free)/15M(total)

2.实例化10M的数组，并建立弱引用:
3M(free)/15M(total)
weakRerference.get() : [B@10bedb4

3.GC后:
14M(free)/15M(total)
weakRerference.get() : null

Process finished with exit code 0
```

### 软引用

软引用和弱引用的特性基本一致，主要的区别在于软引用在内存不足时才会被回收。**如果一个对象只具有软引用，Java GC在内存充足的时候不会回收它，内存不足时才会被回收**。

垃圾回收器会在内存不足，经过一次垃圾回收后，内存仍旧不足的时候回收掉软可达对象。在虚拟机抛出OOM(`OutOfMemoryError`)之前，会保证已经清除了所有指向软可达对象的软引用。

如果内存足够，并没有规定回收软引用的具体时间，所以在内存充足的情况下，软引用对象也可能存活很长时间。



```java
import java.lang.ref.SoftReference;

/**
 * 软引用
 * @author Chenzf
 * @date 2020/07/27
 * @version 1.0
 */

public class SoftReferenceTest {

    public static int M = 1024 * 1024;

    public static void printlnMemory(String tag) {
        Runtime runtime = Runtime.getRuntime();
        int M = StrongReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory()/M + "M(free)/" + runtime.totalMemory()/M + "M(total)");
    }

    public static void main(String[] args) {
        SoftReferenceTest.printlnMemory("1.原可用内存和总内存");

        //建立软引用
        SoftReference<Object> softRerference = new SoftReference<>(new byte[10 * SoftReferenceTest.M]);
        SoftReferenceTest.printlnMemory("2.实例化10M的数组，并建立软引用");
        System.out.println("softRerference.get() : " + softRerference.get());

        System.gc();
        SoftReferenceTest.printlnMemory("3.内存可用容量充足，GC后");
        System.out.println("softRerference.get() : " + softRerference.get());

        //实例化一个4M的数组,使内存不够用,并建立软引用
        //free=10M=4M+10M-4M,证明内存可用量不足时，GC后byte[10*m]被回收
        SoftReference<Object> softRerference2 = new SoftReference<>(new byte[170 * SoftReferenceTest.M]);
        SoftReferenceTest.printlnMemory("4.实例化一个170M的数组后");
        System.out.println("softRerference.get() : " + softRerference.get());
        System.out.println("softRerference2.get() : " + softRerference2.get());
    }
}
```

``` {.line-numbers highlight=12-15}
1.原可用内存和总内存:
13M(free)/15M(total)

2.实例化10M的数组，并建立软引用:
3M(free)/15M(total)
softRerference.get() : [B@10bedb4

3.内存可用容量充足，GC后:
14M(free)/25M(total)
softRerference.get() : [B@10bedb4

4.实例化一个170M的数组后:
75M(free)/247M(total)
softRerference.get() : null
softRerference2.get() : [B@103dbd3

Process finished with exit code 0
```

#### 应用场景

软引用很适合用来实现**缓存**：比如网页缓存、图片缓存等。

在很多应用中，都会出现大量的默认图片，比如说QQ的默认头像，应用内的默认图标等等，这些图片很多地方会用到。

**如果每次都去读取图片，由于读取文件速度较慢，大量重复的读取会导致性能下降**。所以可以考虑将图片缓存起来，**需要的时候直接从<font color=red>内存</font>中读取**。但是，由于图片占用内存空间比较大，**缓存的图片过多会占用比较多的内存**，就可能比较容易发生OOM。这时候，软引用就派得上用场了。


#### 总结

- 软引用弱于强引用
- 软引用指向的对象会**在内存不足时被垃圾回收清理掉**
- JVM会**优先回收长时间闲置不用的软引用对象**，对那些刚刚构建的或刚刚使用过的软引用对象会尽可能保留
- 软引用可以有效的**解决OOM问题**
- 软引用适合用作**非必须大对象的缓存**


### 虚引用 PhantomReference

从PhantomReference类的源代码可以知道，**它的`get()`方法无论何时返回的都只会是null。所以单独使用虚引用时，没有什么意义，需要和引用队列`ReferenceQueue`类联合使用**。**当执行Java GC时如果一个对象只有虚引用，就会把这个对象加入到与之关联的`ReferenceQueue`中**。

它的作用在于**跟踪垃圾回收过程**，在对象被收集器回收时收到一个系统通知。 

当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在垃圾回收后，**将这个虚引用加入引用队列，在其关联的虚引用出队前，不会彻底销毁该对象**。 

所以可以通过检查引用队列中是否有相应的虚引用来**判断对象是否已经被回收了——作为对象是否存活的监控**。

```java
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 * @author Chenzf
 * @date 2020/07/27
 * @version 1.0
 */

public class PhantomReferenceTest {

    public static int M = 1024*1024;

    public static void printlnMemory(String tag){
        Runtime runtime = Runtime.getRuntime();
        int M = PhantomReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory()/M + "M(free)/" + runtime.totalMemory()/M + "M(total)");
    }

    public static void main(String[] args) throws InterruptedException {

        PhantomReferenceTest.printlnMemory("1.原可用内存和总内存");

        byte[] object = new byte[10 * PhantomReferenceTest.M];
        PhantomReferenceTest.printlnMemory("2.实例化10M的数组后");

        //建立虚引用
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object, referenceQueue);

        PhantomReferenceTest.printlnMemory("3.建立虚引用后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

        //断开byte[10*PhantomReferenceTest.M]的强引用
        object = null;
        PhantomReferenceTest.printlnMemory("4.执行object = null;强引用断开后");

        System.gc();
        PhantomReferenceTest.printlnMemory("5.GC后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

        //断开虚引用
        phantomReference = null;
        System.gc();
        PhantomReferenceTest.printlnMemory("6.断开虚引用后GC");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());
    }
}
```

```java
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 * @author Chenzf
 * @date 2020/07/27
 * @version 1.0
 */

public class PhantomReferenceTest {

    public static int M = 1024 * 1024;

    public static void printlnMemory(String tag){
        Runtime runtime = Runtime.getRuntime();
        int M = PhantomReferenceTest.M;
        System.out.println("\n" + tag + ":");
        System.out.println(runtime.freeMemory()/M + "M(free)/" + runtime.totalMemory()/M + "M(total)");
    }

    public static void main(String[] args) throws InterruptedException {

        PhantomReferenceTest.printlnMemory("1.原可用内存和总内存");

        byte[] object = new byte[10 * PhantomReferenceTest.M];
        PhantomReferenceTest.printlnMemory("2.实例化10M的数组后");

        //建立虚引用
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object, referenceQueue);

        PhantomReferenceTest.printlnMemory("3.建立虚引用后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

        //断开byte[10*PhantomReferenceTest.M]的强引用
        object = null;
        PhantomReferenceTest.printlnMemory("4.执行object = null;强引用断开后");

        System.gc();
        PhantomReferenceTest.printlnMemory("5.GC后");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("phantomReference.get() : " + phantomReference.get());
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());

        //断开虚引用
        phantomReference = null;
        System.gc();
        PhantomReferenceTest.printlnMemory("6.断开虚引用后GC");
        System.out.println("phantomReference : " + phantomReference);
        System.out.println("referenceQueue.poll() : " + referenceQueue.poll());
    }
}
```

- PhantomReference的`get()`方法无论何时返回的都只会是null。
- 当执行Java GC时如果一个对象只有虚引用，就会把这个对象加入到与之关联的`ReferenceQueue`中。

```
1.原可用内存和总内存:
13M(free)/15M(total)

2.实例化10M的数组后:
3M(free)/15M(total)

3.建立虚引用后:
3M(free)/15M(total)
phantomReference : java.lang.ref.PhantomReference@10bedb4
phantomReference.get() : null
referenceQueue.poll() : null

4.执行object = null;强引用断开后:
3M(free)/15M(total)

5.GC后:
14M(free)/25M(total)
phantomReference : java.lang.ref.PhantomReference@10bedb4
phantomReference.get() : null
referenceQueue.poll() : java.lang.ref.PhantomReference@10bedb4

6.断开虚引用后GC:
24M(free)/25M(total)
phantomReference : null
referenceQueue.poll() : null

Process finished with exit code 0
```


### 可达性

不同的引用类型其实都是逻辑上的，而对于虚拟机来说，主要体现的是对象的不同的**可达性(reachable)状态**和**对垃圾收集(garbage collector)的影响**。


可以通过下面的流程来对对象的生命周期做一个总结：

<div align=center><img src=Pictures\对象的生命周期.png></div>

对象被创建并初始化，对象在运行时被使用，然后离开对象的作用域，对象会变成不可达并会被垃圾收集器回收。图中**用红色标明的区域表示对象处于强可达阶段**。

如果只讨论**符合垃圾回收条件的对象**，那么只有三种：软可达、弱可达和虚可达。

- 软可达：只能通过软引用才能访问的状态，软可达的对象是由`SoftReference`引用的对象，并且没有强引用的对象。
    - 软引用是用来描述一些**还有用但是非必须的对象**。
    - 垃圾收集器会尽可能长时间的保留软引用的对象，但是会在发生`OutOfMemoryError`之前，回收软引用的对象。如果回收完软引用的对象，内存还是不够分配的话，就会直接抛出`OutOfMemoryError`。

- 弱可达：弱可达的对象是`WeakReference`引用的对象。
    - 垃圾收集器可以**随时收集弱引用的对象，不会尝试保留软引用的对象**。

- 虚可达：虚可达是由`PhantomReference`引用的对象，虚可达就是没有强、软、弱引用进行关联，并且已经被`finalize`过了，只有虚引用指向这个对象的时候。


除此之外，还有强可达和不可达的两种可达性判断条件

- 强可达：就是一个对象刚被创建、初始化、使用中的对象都是处于强可达的状态

- 不可达(unreachable)：处于不可达的对象就意味着对象可以被清除了。

不同可达性状态的转换图：

<div align=center><img src=Pictures\可达性状态转换图.png width=70%></div>


### 总结

- 强引用是Java的默认引用形式，使用时不需要显示定义，是我们平时最常使用到的引用方式。**不管系统资源有多紧张，Java GC都不会主动回收具有强引用的对象**。 

- 弱引用和软引用一般在引用对象为非必需对象的时候使用，它们的区别是
    - **被弱引用关联的对象在垃圾回收时总是会被回收**
    - **被软引用关联的对象只有在内存不足时才会被回收**。 

- 虚引用的get()方法获取的永远是null，无法获取对象实例。**Java GC会把虚引用的对象放到引用队列里面**。可用来在对象被回收时做额外的一些资源清理或事物回滚等处理。 

由于无法从虚引获取到引用对象的实例。它的使用情况比较特别，所以这里不把虚引用放入表格进行对比。这里对强引用、弱引用、软引用进行对比：

| 引用类型 | GC时JVM内存充足 | GC时JVM内存不足 |
|:--------:|:---------------:|:---------------:|
|  强引用  |     不被回收    |     不被回收    |
|  弱引用  |      被回收     |      被回收     |
|  软引用  |     不被回收    |      被回收     |

设置四种引用类型，是为了更好的**控制对象的生命周期**，让代码能够一定程度上干涉GC过程，所以引用类型主要就是跟垃圾回收有关了。

对于JVM、GC和内存，可以这样理解，**内存**好比你的**抽屉**，这个抽屉有一定大小，并不能无限存放东西。

**JVM**好比**你自己**，会时不时来整理抽屉。那些申请的对象好比放在抽屉里的东西，生活中的必需品就好比强引用，而那些可能用到的东西（非必需品）就好比软引用或者弱引用。

当抽屉还很空的时候，放一些非必须品你也不会在意，但是随着买的东西越来越多，抽屉里快放不下的时候，就需要根据重要程度来选择一些东西扔出抽屉，这个过程就好比GC。

JVM在内存够用的时候，不会对软引用的对象进行回收，但是当内存紧张的时候，就会对它们进行清理。

| 引用类型 | 引用对象被垃圾回收的时间                        | 用途                                       | 是否可以转为强引用 | 对应的类                  |
|----------|-------------------------------------------------|--------------------------------------------|--------------------|---------------------------|
| 强引用   | 从来不会                                        | 一般用途，保持对象不被回收                 | 可以               | 默认                      |
| 软引用   | 发生一次GC后，JVM决定还需要进一步回收更多空间时 | 缓存，保持对象在内存足够时不被回收         | 可以               | SoftReference             |
| 弱引用   | 进行垃圾回收时，如果对象只存在弱引用            | 缓存，仅仅在对象仍被使用时保持其不被回收   | 可以               | WeakReference WeakHashMap |
| 虚引用   | 进行垃圾回收时                                  | 跟踪GC过程，在对象被回收前进行一些清理工作 | 不可以             | PhantomReference          |



## HashMap与HashTable的关系

<div align=center><img src=Pictures\Map.webp width=70%></div>

**共同点：**

- 底层都是使用哈希表 + 链表的实现方式。

**区别：**

- 从层级结构上看，HashMap、HashTable有一个共用的Map接口。另外，HashTable还单独继承了一个抽象类Dictionary(已废弃)；
- **HashTable线程安全，HashMap线程不安全**；
- 初始值和扩容方式不同。HashTable的初始值为11，扩容为原大小的2*d+1。容量大小都采用奇数且为素数，且采用取模法，这种方式散列更均匀。但有个缺点就是对素数取模的性能较低（涉及到除法运算）；而HashTable的长度都是2的次幂，这种方式的取模都是直接做位运算，性能较好。
- **HashMap的key、value都可为null**，且value可多次为null，key多次为null时会覆盖。但**HashTable的key、value都不可为null**，否则直接NPE(NullPointException)。

```java
/**
 * HashTable的put方法
 */
public synchronized V put(K key, V value) {
    // Make sure the value is not null
    if (value == null) {
        throw new NullPointerException();
    }


    // Makes sure the key is not already in the hashtable.
    Entry<?,?> tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;
    @SuppressWarnings("unchecked")
    Entry<K,V> entry = (Entry<K,V>)tab[index];
    for(; entry != null ; entry = entry.next) {
        if ((entry.hash == hash) && entry.key.equals(key)) {
            V old = entry.value;
            entry.value = value;
            return old;
        }
    }


    addEntry(hash, key, value, index);
    return null;
}
```








## 访问权限

<div align=center><img src=Pictures\访问权限.png></div>






## 构造方法

**当新对象被创建的时候，构造函数会被调用**。每一个类都有构造函数。在程序员没有给类提供构造函数的情况下，Java编译器会为这个类创建一个默认的构造函数。

Java中构造函数重载和方法重载很相似。可以为一个类创建多个构造函数。每一个构造函数必须有它自己**唯一的参数列表**。



## 异常处理

**当一个方法出现异常后便抛出一个异常对象**

Java通过面向对象的方法进行异常处理，**把各种不同的异常进行分类**，并提供了良好的接口。

在Java中，**每个异常都是一个对象，它是Throwable类或其它子类的实例**。**当一个方法出现异常后便抛出一个异常对象**，该对象中包含有异常信息，调用这个对象的方法可以捕获到这个异常并进行处理。

- **受检异常(checked exception)**是**程序执行期间**发生的严重事件的后果
    - ClassNotFoundException、FileNotFoundException、IOException、NoSuchMethodException、WriteAbortedException
    - 受检异常的所行类都是类`Exception`的子类， `Exception`是`Throwable`的后代


- **运行时异常(runtime exception)**通常是程序中**逻辑错误**的结果
    - ArithmeticException(被0除)、ArrayindexOutOfBoundsException(下标越界)、ClassCastException、EmptyStackException、IllegalArgumentException、IllegalStateException、IndexOutOfBoundsException、NoSuchElementException、NullPointerException、UnsupportedOperationException
    - 运行时异常的所有类都是类RuntimeException的子类．它是Exception的后代。

- 错误(error)是标准类Error或其后代类的一个对象。将这样的类都称为错误类。注意， Error是Throwable的后代。一般地，**错误是指发生了不正确的情况，如内存溢出**。

<div align=center><img src=Pictures\异常.png></div>


**当可能发生受检异常时，必须处理它**。对于可能引发受检异常的方法，你有两种选择：
- 在方法内处理异常；
- 或者告诉方法的客户来处理。

Java的异常处理是通过5个关键词来实现的：`try、catch、throw、throws`和`finally`。

- 一般情况下是用try来执行一段程序，如果出现异常，系统会抛出一个异常，这时候你可以**通过它的类型来捕捉（catch）它**，或**最后（finally）由缺省处理器来处理**。用try来指定一块预防所有“异常”的程序。紧跟在try程序后面，应包含一个catch子句来指定你想要捕捉的“异常”的类型。

- `throw`语句用来**明确地抛出一个“异常”**。
    - 应该仅在方法内，当你不能使用合理的方式来解决不正常或意外事件的情形下，才抛出异常
    - `throw new IOException();`

- `throws`用来标明**一个成员函数可能抛出的各种“异常”**。
    - 一个方法**可能引起受检异常但又不处理它，就必须在方法头声明这件事**
    - ` public String readString(...) throws IOException`

- `finally`为确保一段代码**不管发生什么“异常”都被执行一段代码**。可以在一个成员函数调用的外面写一个try语句，在这个成员函数内部写另一个try语句保护其他代码。每当遇到一个try语句，“异常”的框架就放到堆栈上面，直到所有的try语句都完成。如果下一级的try语句没有对某种“异常”进行处理，堆栈就会展开，直到遇到有处理这种“异常”的try语句。



## 接口与抽象类

- 接口
    - 不同的子类可以用不同的方式表示此接口

    - 由于Java**不支持多继承**，而有可能**某个类或对象要使用分别在几个类或对象里面的方法或属性**，现有的单继承机制就不能满足要求。

    - 与继承相比，接口有更高的灵活性，因为**接口中没有任何实现代码**。当一个类实现了接口以后，该类要实现接口里面所有的方法和属性，并且**接口里面的属性在默认状态下面都是`public static`，所有方法默认情况下是`public`**。

    - 一个类可以实现多个接口。

- 抽象类

    - **声明方法的存在**而**不去实现它**的类被叫做抽象类（abstract class），它用于要创建一个体现某些基本行为的类，并为该类声明方法，但不能在该类中实现该类的情况。**不能创建abstract类的实例**。
    - Abstract类的子类为它们父类中的所有抽象方法提供实现，否则它们也是抽象类为。

- 区别
    - **接口中所有的方法隐含的都是抽象的**。而抽象类则可以同时包含**抽象和非抽象的方法**。
    - 类可以实现很多个接口，但是只能继承一个抽象类。
    - 类可以不实现抽象类和接口声明的所有方法，当然，在这种情况下，类也必须得声明成是抽象的。
    - 抽象类可以在不提供接口方法实现的情况下实现接口。
    - Java接口中声明的**变量**默认都是final的。抽象类可以包含非final的变量。
    - Java接口中的**成员函数**默认是`public`的。抽象类的成员函数可以是`private，protected`或者是`public`。



## 面向对象的特征

- 抽象：
    抽象是从众多的事物中抽**取出共同的、本质性的特征**，而舍弃其非本质的特征。
- 继承：
    继承是一种联结类的层次模型，并且允许和鼓励类的重用，它提供了一种明确表述共性的方法。对象的一个新类可以从现有的类中派生，这个过程称为类继承。新类继承了原始类的特性，新类称为原始类的派生类（子类），而原始类称为新类的基类（父类）。**派生类可以从它的基类那里继承方法和实例变量，并且类可以修改或增加新的方法使之更适合特殊的需要**。
- 封装：
    - 封装是把过程和数据包围起来，**对数据的访问只能通过已定义的界面**。**隐藏对象的属性和实现细节，仅对外提供公共访问方式**。
    - “封装”通过合并特征和行为来创建新的数据类型。“实现隐藏”则通过将细节“私有化”**把接口和实现分离开来**。
- 多态性：
    - 多态性是指允许不同类的对象对同一消息作出响应。多态性包括参数化多态性和包含多态性。多态性语言具有灵活、抽象、行为共享、代码共享的优势，很好的解决了应用程序函数同名问题。


## 多态
- **对象既可以作为它自己本身的类型使用，也可以作为它的基类型使用**。而这种把对某个对象的引用视为对其基类型的引用的做法被称作向上转型——因为在继承树的画法中，基类是放置在上方的。
- Java中除了`static`方法和`final`方法(`private`方法属于`final`方法)之外，其他所有的方法都是**后期绑定**。**Java的实例方法调用是基于运行时的实际类型的动态调用，而非变量的声明类型**。
- 可以**编写只与基类打交道的程序代码**，并且这些代码对所有的派生类都可以正确运行。或者换一种说法，发送消息给某个对象，让该对象去断定应该做什么事。
- **继承+覆写+向上转型+调用子类覆写方法**=多态！
- 多态也有弊端，就是无法调用子类特有的功能。

```java
class Income
{
    protected double income;

    public Income(double income)
    {
        this.income = income;
    }
    public double getTax()
    {
        return income * 0.1;
    }
}

class Salary extends Income
{
    public Salary(double income)
    {
        super(income);
    }

    @Override
    public double getTax()
    {
        if (income <= 5000)
            return 0;
        return (income - 5000) * 0.2;
    }
}

class StateCouncilSpecialAllowance extends Income
{
    public StateCouncilSpecialAllowance(double income)
    {
        super(income);
    }

    @Override
    public double getTax()
    {
        return 0;
    }
}

public class Test
{
    public static void main(String[] args)
    {
        // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
        Income[] incomes = new Income[] {new Income(3000),
        new Salary(7500), new StateCouncilSpecialAllowance(15000)};
        System.out.println(totalTax(incomes));
    }

    public static double totalTax(Income... incomes)
    {
        double total = 0;
        for (Income income: incomes)
            total += income.getTax();
        return total;
    }
}
```

利用多态，`totalTax()`方法只需要和`Income`打交道，它**完全不需要知道`Salary`和`StateCouncilSpecialAllowance`的存在，就可以正确计算出总的税**。如果我们要新增一种稿费收入，只需要**从`Income`派生**，然后**正确覆写`getTax()`方法**就可以。把新的类型传入`totalTax()`，不需要修改任何代码。



## Comparable和Comparator接口

`Comparable-->compareTo`；`Comparator-->compare`

**<font color=red>`Comparable`用于比较实现`Comparable`的类的对象；`Comparator`用于比较没有实现`Comparable`的类的对象**。

使用`Comparable`接口来比较元素称为使用**自然顺序**(natural order)进行比较，使用`Comparator`接口来比较元素被称为使用**比较器**来进行比较</font>。

Java API的一些类，比如`String`、`Date`、`Calendar`、`BigInteger`、`BigDecimal`以及所有**基本类型的数字包装类**都实现了`Comparable`接口。<font color=red>`Comparable`接口定义了`compareTo`方法，用于比较实现了`Comparable`接口的<u>同一个类</u>的两个元素</font>。

- Java提供了**只包含一个`compareTo()`方法的`Comparable`接口**。这个方法可以个**给两个对象排序**。具体来说，它返回**负数，0，正数**来表明输入**对象小于，等于，大于已经存在的对象**。


**如果元素的类没有实现Comparable接口又将如何呢**？这些元素可以比较么？

可以定义一个**比较器**(comparator)来比较<font color=red>不同类</font>的元素。要做到这一点，需要创建一个实现`java.util.Comparator<T>`接口的类并重写它的`compare`方法。
`public int compare(T elementl, T element2)`
>如果`element1`**小于**`element2`, 就返回一个**负值**； 如果`element1`**大于**`element2`, 就返回一个**正值**； 若两者**相等**， 则返回**0**。

- Java提供了**包含`compare()`和`equals()`两个方法的`Comparator`接口**。`compare()`方法用来给两个输入参数排序，返回**负数，0，正数**表明第一个参数是小于，等于，大于第二个参数。`equals()`方法需要一个对象作为参数，它用来决定输入参数是否和`comparator`相等。只有当输入参数也是一个`comparator`并且输入参数和当前`comparator`的排序结果是相同的时候，这个方法才返回`true`。






```java
// GeometricObjectComparator.java
import java.util.Comparator;

public class GeometricObjectComparator
    implements Comparator<GeometricObject>, java.io.Serializable
{
    @Override
    public int compare(GeometricObject o1, GeometricObject o2)
    {
        double area1 = o1.getArea();
        double area2 = o2.getArea();

        if (area1 < area2)
            return -1;
        else if (area1 == area2)
            return 0;
        else
            return 1;
  }
}
```

`public int compare(GeometricObject o1, GeometricObject o2)`通过覆盖`compare`方法来比较两个几何对象。比较器类也实现了`Serializable`接口。通常对于比较器来说，实现`Serializable`是一个好主意，因为它们可以被用作可序列化数据结构的排序方法。为了使数据结构能够成功序列化，比较器（如果提供）必须实现`Serializable`接口。

```java
import java.util.Comparator;

public class TestComparator
{
    public static void main(String[] args)
    {
        GeometricObject g1 = new Rectangle(5, 5);
        GeometricObject g2 = new Circle(5);

        GeometricObject g = max(g1, g2, new GeometricObjectComparator());

        System.out.println("The area of the larger object is " + g.getArea());
    }

    public static GeometricObject max(GeometricObject g1, GeometricObject g2, Comparator<GeometricObject> c)
    {
        if (c.compare(g1, g2) > 0)
            return g1;
        else
            return g2;
    }
}
```

**<font color=red>`Comparable`用于比较实现`Comparable`的类的对象；`Comparator`用于比较没有实现`Comparable`的类的对象**。

使用`Comparable`接口来比较元素称为使用**自然顺序**(natural order)进行比较，使用`Comparator`接口来比较元素被称为使用**比较器**来进行比较</font>。

```java
// SortStringByLength.java

public class SortStringByLength
{
    public static void main(String[] args)
    {
        String[] cities = {"Atlanta", "Savannah", "New York", "Dallas"};
        java.util.Arrays.sort(cities, new MyComparator());

        for (String s : cities)
            System.out.print(s + " ");
    }

    public static class MyComparator implements java.util.Comparator<String>
    {
        @Override
        public int compare(String s1, String s2)
        {
            return s1.length() - s2.length();
        }
    }
}

// SortStringByLength1.java

public class SortStringByLength1
{
    public static void main(String[] args)
    {
        String[] cities = {"Atlanta", "Savannah", "New York", "Dallas"};
        java.util.Arrays.sort(cities, (s1, s2) -> s1.length() - s2.length());

        for (String s : cities)
            System.out.print(s + " ");
    }
}
```

```java
public class SortStringIgnoreCase
{
    public static void main(String[] args)
    {
        java.util.List<String> cities = java.util.Arrays.asList("Atlanta", "Savannah", "New York", "Dallas");

        cities.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

        for (String s : cities)
            System.out.print(s + " ");
    }
}
```


## final关键字


可能使用到final的三种情况：数据、方法和类。

- final数据
    - 在Java中，这类**恒定不变的常量**必须是**基本数据类型**，并且以关键字final表示。在对这个常量进行定义的时候，必须对其进行赋值。**一个既是static又是final的域只占据一段不能改变的存储空间**。
    - 对于**基本类型**，final使数值恒定不变；而用于**对象引用**，final使引用恒定不变。**一旦引用被初始化指向一个对象，就无法再把它改为指向另一个对象**。然而，**对象其自身却是可以被修改的**，Java并未提供使任何对象恒定不变的途径。
    - 既是static又是final的域(即编译期常量)将用大写表示，并使用下划线分隔各个单词。

- final方法
    - 使用final方法的原因有两个。第一个原因是**把方法锁定**，**以防任何继承类修改它的含义**。这是出于设计的考虑：想要确保在继承中使方法行为保持不变，并且不会被覆盖。过去建议使用final方法的第二个原因是**效率**。在最近的Java版本中，虚拟机(特别是hotspot技术)可以探测到这些情况，并优化去掉这些效率反而降低的额外的内嵌调用，因此不再需要使用final方法来进行优化了。
    - final和private关键字：**类中所有的private方法都隐式地指定为是final的**。由于无法取用private方法，所以也就无法覆盖它。可以对private方法添加final修饰词，但这并不能给该方法增加任何额外的意义。

- final类
    - 当将某个类的整体定义为final时(通过将关键字final置于它的定义之前)就表明了你不打算继承该类，而且也不允许别人这样做。由于**final类禁止继承**，所以final类中所有的方法都隐式指定为是final的，因为无法覆盖它们。




## static关键字

```java
public class Solution {
    static String solution = "chenzf";

    public static void main(String[] args) {
        System.out.println(Solution.solution);
        
        // 不建议使用该方式访问静态变量
        Solution result = new Solution();
        System.out.println(result.solution);
        
        solution = "chenzufeng";
        System.out.println(Solution.solution);
    }
}

/*
chenzf
chenzf
chenzufeng
 */
```

主要有四种用法：

- 用来修饰成员变量，将其变为类的成员，从而**实现所有对象对于该成员的共享**；
- 用来修饰成员方法，将其变为**类方法**，可以直接使用“**类名.方法名**”的方式调用，常用于**工具类**；
- 静态块用法，**将多个类成员放在一起初始化**，使得程序更加规整，其中理解对象的初始化过程非常关键；
- 静态导包用法，将类的方法直接导入到当前类中，从而直接使用“方法名”即可调用类方法，更加方便。

### 修饰成员变量

```java
public class Person {
    String name;
    int age;
    
    public String toString() {
        return "Name:" + name + ", Age:" + age;
    }
    
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "zhangsan";
        p1.age = 10;
        Person p2 = new Person();
        p2.name = "lisi";
        p2.age = 12;
        System.out.println(p1);
        System.out.println(p2);
    }
    /**Output
     * Name:zhangsan, Age:10
     * Name:lisi, Age:12
     *///~
}
```

<div align=center><img src=Pictures\static关键字.jpg></div>

**两个Person对象的方法实际上只是指向了同一个方法定义**。这个方法定义是位于内存中的一块不变区域（由jvm划分），我们暂称它为**静态存储区**。这一块存储区不仅存放了方法的定义，实际上从更大的角度而言，它存放的是各种类的定义，当我们通过new来生成对象时，会根据这里定义的类的定义去创建对象。**多个对象仅会对应同一个方法**，这里有一个让我们充分信服的理由，那就是不管多少的对象，他们的方法总是相同的，尽管最后的输出会有所不同，但是方法总是会按照我们预想的结果去操作，即不同的对象去调用同一个方法，结果会不尽相同。

```java
public class Person {
    String name;
    static int age;
    
    /* 其余代码不变... */

    /**Output
     * Name:zhangsan, Age:12
     * Name:lisi, Age:12
     *///~
}
```

<div align=center><img src=Pictures\static关键字1.jpg></div>

给age属性加了static关键字之后，Person对象就不再拥有age属性了，age属性会统一交给Person类去管理，即多个Person对象只会对应一个age属性，一个对象如果对age属性做了改变，其他的对象都会受到影响。我们看到此时的age和toString()方法一样，都是交由类去管理。

虽然我们看到static可以让对象共享属性，但是实际中我们很少这么用，也不推荐这么使用。因为这样会让该属性变得难以控制，因为它在任何地方都有可能被改变。如果我们想共享属性，一般我们会采用其他的办法：

```java {.line-numbers highlight=8}
public class Person {
    private static int count = 0;
    int id;
    String name;
    int age;
    
    public Person() {
        id = ++count;
    }
    
    public String toString() {
        return "Id:" + id + ", Name:" + name + ", Age:" + age;
    }
    
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "zhangsan";
        p1.age = 10;
        Person p2 = new Person();
        p2.name = "lisi";
        p2.age = 12;
        System.out.println(p1);
        System.out.println(p2);
    }
    /**Output
     * Id:1, Name:zhangsan, Age:10
     * Id:2, Name:lisi, Age:12
     *///~
}
```

上面的代码起到了给Person的对象创建一个唯一id以及记录总数的作用，其中count由static修饰，是Person类的成员属性，每次创建一个Person对象，就会使该属性自加1然后赋给对象的id属性，这样，count属性记录了创建Person对象的总数，由于count使用了private修饰，所以从类外面无法随意改变。

### 修饰成员方法

相比于修饰成员属性，修饰成员方法对于数据的存储上面并没有多大的变化，因为我们从上面可以看出，方法本来就是存放在类的定义当中的。static修饰成员方法最大的作用，就是可以使用"**类名.方法名**"的方式操作方法，避免了先要new出对象的繁琐和资源消耗，我们可能会经常在帮助类中看到它的使用：

```java
public class PrintHelper {

    public static void print(Object o){
        System.out.println(o);
    }
    
    public static void main(String[] args) {
        PrintHelper.print("Hello world");
    }
}
```

<font color=red>在static方法的内部不能调用非静态方法(反之可以)。</font>

static变量在Java中是属于类的，它在所有的实例中的值是一样的。当类被Java虚拟机载入的时候，会对static变量进行初始化。**如果你的代码尝试不用实例来访问非static的变量**，编译器会报错，因为**这些变量还没有被创建出来，还没有跟任何实例关联上**。

当创建类时，就是在描述那个类的对象的外观与行为。**除非用new创建那个类的对象，否则，实际上并未获得任何对象**。执行new来创建对象时，数据存储空间才被分配，其方法才供外界调用。

有两种情形用上述方法是无法解决的？

- 一种情形是，**只想为某特定域分配单一存储空间，而不去考虑究竞要创建多少对象，甚至根本就不创建任何对象**。

- 另一种情形是，希望某个方法不与包含它的类的任何对象关联在一起。也就是说，**即使没有创建对象，也能够调用这个方法**。有些面向对象语言采用类数据和类方法两个术语，代表那些数据和方法只是作为整个类，而不是类的某个特定对象而存在的。

当我们说某个事物是静态时，就意味着**该字段或方法不依赖于任何特定的对象实例**。**即使我们从未创建过该类的对象，也可以调用其静态方法或访问其静态字段**。

相反，**对于普通的非静态字段和方法，我们必须要先创建一个对象并使用该对象来访问字段或方法，因为非静态字段和方法必须与特定对象关联**。

```java
public class StaticTest
{
    static int i = 47;

    public static void main(String[] args)
    {
        StaticTest st1 = new StaticTest();
        StaticTest st2 = new StaticTest();

        System.out.println("st1: " + st1 + " st2: " + st2);
        // st1: Object.StaticTest@3b07d329 st2: Object.StaticTest@41629346
        
        System.out.println("st1:" + st1.i + " st2:" + st2.i);  // st1:47 st2:47
	System.out.println(StaticTest.i);  // 47
    }
}
```

即使创建了两个`StaticTest`对象，`StaticTest.i`也只有一份存储空间，这两个对象共享同一个`i`。因此，`st1.i`和`st2.i`指向同一存储空间。
	
引用static变最有两种方法：可以**通过一个对象**去定位它，如`st1.i`。也可以通过其**类名直接引用**(`StaticTest.i`)，这对于非静态成员则不行。**使用类名是引用`static`变量的首选方式**，这不仅是因为它强调了变最的static结构，而且在某些情况下它还为编译器进行优化提供了更好的机会。



### 静态块

```java {.line-numbers highlight=12-15}
class Book{
    public Book(String msg) {
        System.out.println(msg);
    }
}

public class Person {

    Book book1 = new Book("book1成员变量初始化");
    static Book book2;
    
    static {
        book2 = new Book("static成员book2成员变量初始化");
        book4 = new Book("static成员book4成员变量初始化");
    }
    
    public Person(String msg) {
        System.out.println(msg);
    }
    
    Book book3 = new Book("book3成员变量初始化");
    static Book book4;
    
    public static void funStatic() {
        System.out.println("static修饰的funStatic方法");
    }
    
    public static void main(String[] args) {
        Person.funStatic();
        System.out.println("****************");
        Person p1 = new Person("p1初始化");
    }
    /**Output
     * static成员book2成员变量初始化
     * static成员book4成员变量初始化
     * static修饰的funStatic方法
     * ***************
     * book1成员变量初始化
     * book3成员变量初始化
     * p1初始化
     *///~
}
```

- 当我们没有创建对象，而是通过类去调用类方法时，尽管该方法没有使用到任何的类成员，类成员还是在方法调用之前就初始化了，这说明，**当我们第一次去使用一个类时，就会触发该类的成员初始化**。第二个是
- 当我们使用了类方法，完成类的成员的初始化后，再new该类的对象时，static修饰的类成员没有再次初始化，这说明，static修饰的类成员，在程序运行过程中，**只需要初始化一次即可，不会进行多次的初始化**。

## Java程序初始化顺序

**父类静态变量；父类静态代码块；子类静态变量；子类静态代码块
父类非静态变量；父类非静态代码块；父类构造器
子类非静态变量；子类非静态代码块；子类构造器**。

Java程序初始化一般遵循3个原则：

1. 静态对象(变量)先于非静态对象(变量)初始化。其中**静态对象(变量)只初始化一次**，因为static在jvm中只有一块区域存储，方法区(Method Area)，之所以被称为静态是因为**从程序创建到死亡他的地址值都不会改变**，其只在class类对象初次加载时初始化，因此static只需要初始化一次，而非静态对象(变量)可能会初始化很多次。
2. 如果类之间存在继承关系，那么父类优先于子类进行初始化。
3. 按照成员变量的定义顺序进行初始化。即使变量定义散布于方法之中，它们依然在任何方法(包括构造函数)被调用前先初始化。




## 深拷贝和浅拷贝

在Java中数据类型可以分为两大类：**基本类型和引用类型**。

基本类型也称为值类型，分别是字符类型char，布尔类型boolean以及数值类型byte、short、int、long、float、double。

引用类型则包括类、接口、数组、枚举等。

Java将内存空间分为**堆和栈**。**基本类型直接在栈中存储数值，而引用类型是将引用放在栈中，实际存储的值是放在堆中，通过栈中的引用指向堆中存放的数据**。

**浅拷贝：引用类型仅复制引用；深拷贝：所有属性都复制独立一份。**

<div align=center><img src=Pictures\深拷贝和浅拷贝.png></div>



## 泛型

泛型(generic)可以**参数化类型**。这个能力使我们可以定义带泛型类型的类或方法，**随后编译器会用具体的类型来替换它**。例如，Java定义了一个泛型类`Arraylist`用于存储泛型类型的元素。基于这个泛型类，可以创建用于保存**字符串的Arraylist对象**，以及保存**数字的Arraylist对象**。这里，**字符串和数字是取代泛型类型的具体类型**。

这里的`<T>`表示形式泛型类型(formal generic type)，**随后可以用一个实际具体类型(actual concrete type)来替换它**。替换泛型类型称为泛型实例化(generic instantiation)。按照惯例，像`E`或`T`这样的单个大写字母用于表示形式泛型类型。

### 通配泛型

可以使用**非受限通配、受限通配**或者**下限通配**来对一个泛型类型指定范围。

通配泛型类型有三种形式——`?`、`? extends T`或者`? super T`，其中T是泛型类型。

- 第一种形式：`?`称为**非受限通配**(unbounded wildcard)，它和`? extends Object`是一样的。

```java
public class AnyWildCardDemo
{
    public static void main(String[] args)
    {
        GenericStack<Integer> intStack = new GenericStack<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(-2);
        print(intStack);
    }

    private static void print(GenericStack<?> stack)
    {
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
    }
}
```

`<?>`是一个通配符，表示**任何一种对象类型**。它等价于`<? Extends Object>`。

- 第二种形式：`? extends T`称为**受限通配**(bounded wildcard)，表示`T`或`T的一个子类型`。

`private static double max(GenericStack<? extends Number> stack){…}`

- 第三种形式：`? super T`称为**下限通配**(lower-bound wildcard )，表示`T`或`T的一个父类型`。

```java
public class SuperWildCardDemo
{
    public static void main(String[] args)
    {
        GenericStack<String> stack1 = new GenericStack<>();
        GenericStack<Object> stack2 = new GenericStack<>();

        stack2.push("Java");
        stack2.push(2);
        stack1.push("Sun");
        add(stack1, stack2);
        AnyWildCardDemo.print(stack2);
    }

    private static <T> void add(GenericStack<T> stack1, GenericStack<? super T> stack2)
    {
        while (!stack1.isEmpty())
            stack2.push(stack1.pop());
    }
}
/*
Output:
Sun 2 Java
 */
```


### 使用泛型的限制

由于泛型类型在运行时被消除，因此，对于如何使用泛型类型是有一些限制的：

- 限制1：不能使用`new E()`
    **不能使用泛型类型参数创建实例**：`E object = new E();`原因是运行时执行的是new E()，但是运行时泛型类型E是不可用的。

- 限制2：不能使用new E[]
    不能使用泛型类型参数创建数组：E[] elements = new E[capacity];


- 限制3：异常类不能是泛型的

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


