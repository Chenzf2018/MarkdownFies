# 集合

数据结构(data structure) 是以某种形式将数据组织在一起的`合集`(collection) 。数据结构不仅存储数据，还支持访问和处理数据的操作。

在面向对象思想里，一种数据结构也被认为是一个`容器`(container) 或者`容器对象`(container object)，它是一个能**存储其他对象**的对象，这里的其他对象常称为数据或者元素。

## 为什么需要合集及其与数组的区别

Java是一门面向对象的语言，就免不了**处理对象**；为了方便操作多个对象，那么我们就得把这多个对象存储起来；想要**存储多个对象**(变量)，很容易就能想到一个**容器**；常用的容器有`StringBuffered`，`数组`(虽然有对象数组，但是数组的长度是不可变的！)；所以，Java就为我们提供了合集(`Collection`)。

数组和合集的区别:

- 长度的区别
  - 数组的长度固定；
  - 合集的长度可变。

- 元素的数据类型
  - 数组可以存储**基本数据类型**，也可以存储**引用类型**；
  - <font color=red>合集只能存储引用类型</font>(存储的是简单的`int`，它会**自动装箱**成`Integer`)。


## Java集合框架

Java集合，也称作容器，主要是由两大接口(Interface)派生出来的：`Collection`和`Map`。

Java集合框架支持以下两种类型的容器：

- 为了存储**键/值对**，称为**映射表(map)**。
  - 如果要**保持插入顺序**的，我们可以选择`LinkedHashMap`；
  - 如果不需要则选择`HashMap`；
  - 如果要**排序**则选择`TreeMap`。
- 为了存储**一个元素**合集，简称为**合集(collection)**；
  - `Set`用于存储一组**不重复**的元素。
  - `List`用于存储一个**有序**元素合集。
    - `Stack`用千存储采用**后进先出**方式处理的对象。
  - `Queue`用于存储采用**先进先出**方式处理的对象。
    - `Priority Queue`用于存储按照**优先级顺序**处理的对象。

<div align=center><img src=DataStructure\容器1.png></div>

<div align=center><img src=DataStructure\容器.jpg></div>

### API(增删查)

`Collection接口`为线性表、向量、栈、队列，优先队列以及集合定义了共同的操作。在Java合集框架中定义的所有接口和类都分组在`java.util`包中。

<div align=center><img src=DataStructure\Collection.jpg></div>

`Co11ection`接口包含了处理合集中元素的方法，并且可以得到一个**迭代器对象**用于遍历合集中的元素：`public interface Collection<E> extends Iterable<E>`。

<div align=center><img src=DataStructure\Collection方法.jpg width=80%></div>

除开`java.util.PriorityQueue`没有实现`Cloneable`接口外， Java合集框架中的其他所有具体类都实现了`java.lang.Cloneable`和`java.io.Seria1izab1e`接口。因此，除开优先队列外，所有`Collection`的实例都是**可克隆**的、**可序列化**的。

**常用的合集类：**

`ArrayList, LinkedList, Vector; TreeSet, HashSet, LinkedHashSet`。

## 迭代器Iterator

每种合集都是可迭代的(Iterable)。可以获得集合的`Iterator对象`来遍历合集中的所有元素。

**`Collection`接口继承自`Iterable`接口**(`public interface Collection<E> extends Iterable<E>`)。

`Iterable`接口中定义了`iterator`方法，<font color=red>该方法会返回一个迭代器</font>。
`Iterable`接口中的`iterator()`方法返回一个`Iterator`的实例(`Iterator<T> iterator();`)。
`Iterator`也是一个接口，它有三个方法：`hasNext(); next(); remove()`。
`Iterator`接口为**遍历各种类型的合集中的元素**提供了一种统一的方法。
`iterator`是在`ArrayList`以内部类的方式实现的！并且，从源码可知：`Iterator`实际上就是在遍历集合。

```java
public Iterator<E> iterator() {
    return new Itr();
}

private class Itr implements Iterator<E> {...}
```

遍历合集(Collection)的元素都可以使用`Iterator`，至于它的具体实现是以内部类的方式实现的！

```java
// 创建合集对象
Collection<String> collection = new ArrayList<>();
// iterator()方法返回一个Iterator的实例
Iterator<String> iterator = collection.iterator();
```

```java {.line-numbers highlight=18}
import java.util.*;

public class TestIterator
{
    public static void main(String[] args)
    {
        // 1.创建合集对象
        Collection<String> collection = new ArrayList<>();

        // 2.创建元素对象；并把元素添加到合集
        collection.add("New York");
        collection.add("Atlanta");
        collection.add("Dallas");
        collection.add("Madison");

        // 3.遍历合集
        // iterator()方法返回一个Iterator的实例
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next().toUpperCase() + " ");

        System.out.println();
        for(String s : collection)
            System.out.print(s.toUpperCase() + " ");

        System.out.println();
        collection.forEach(element -> System.out.print(element.toUpperCase() + " "));
    }
}
```

The statement in line 23 uses a `lambda expression` in (a), which is equivalent to using an `anonymous inner class` as shown in (b).

<div align=center><img src=DataStructure\lambda.jpg width=80%></div>


# 线性表List

**<font color=red>有序！可重复！</font>**

<div align=center><img src=DataStructure\Collection.jpg></div>

`List`集合常用的子类有三个：
- ArrayList
    底层数据结构是**数组**。线程**不安全**。

- LinkedList
    底层数据结构是**链表**。线程**不安全**。

- Vector
    底层数据结构是**数组**。线程**安全**。

`Arraylist`和`Linkedlist`定义在`List`接口下。`List`接口继承`Collection`接口，定义了一个**允许重复**的**有序**合集。

List接口中的通用方法：

<div align=center><img src=DataStructure\List.jpg width=90%></div>

`Collection`返回的是`Iterator`迭代器接口，而`List`中又有它自己对应的实现`ListIterator`接口。该接口比普通的Iterator接口多了几个方法：`previous(), add(E e), set(E e)`等。

方法`listIterator()`或`listIterator(startlndex)`都会返回`ListIterator`的一个实例。`ListIterator`接口继承了`Iterator`接口，以增加对线性表的双向遍历能力。

<div align=center><img src=DataStructure\ListIterator.jpg width=70%></div>


## 数组线性表类ArrayList

<div align=center><img src=DataStructure\Collection.jpg></div>

<div align=center><img src=DataStructure\ArrayList.png></div>

`ArrayList`用数组存储元素，这个数组是**动态创建**的。<font color=red>如果元素个数超过了数组的容量，就创建一个更大的新数组，并将当前数组中的所有元素都复制到新数组中</font>。`LinkedList`在一个链表(linked list)中存储元素。

要选用这两种类中的哪一个，依赖于特定需求。

- 如果需要通过**下标随机访问元素**，而不会在线性表起始位置插入或删除元素，那么**ArrayList**提供了最高效率的合集。

- 但是，如果应用程序需要在线性表的**起始位置上插入或删除元素**，就应该选择`LinkedList`类。

**线性表的大小**是可以**动态增大或减小**的。然而**数组**一旦被创建，它的大小就是**固定**的。如果应用程序不需要在线性表中插入或删除元素，那么数组是效率最高的数据结构。

`ArrayList`使用**可变大小的数组**实现List接口：
<div align=center><img src=DataStructure\ArrayList.jpg width=70%></div>

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
    * 指定该ArrayList容量为0时，返回该空数组。
    */
    private static final Object[] EMPTY_ELEMENTDATA = {};
}
```

`ArrayList`**底层其实就是一个数组**，`ArrayList`中有**扩容**这么一个概念，正因为它扩容，所以它能够实现“动态”增长。

```java {.line-numbers highlight=10}
    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```



**小结：**
- `ArrayList`是基于**动态数组**实现的，在增删时候，需要数组的**拷贝复制**(`navite`方法由C/C++实现)。
- `ArrayList`的**默认初始化容量是10，每次扩容时候增加原先容量的一半**，也就是变为原来的**1.5倍**。
- 删除元素时不会减少容量，若希望减少容量则调用`trimToSize()`。
- 它**不是线程安全的**。它能存放`null`值。


## 链表类LinkedList

<div align=center><img src=DataStructure\Collection.jpg></div>

`LinkedList`使用**双向链表**实现`List`接口。除了实现`List`接口外，这个类还提供**从线性表两端**提取、插入和删除元素的方法：

<div align=center><img src=DataStructure\LinkedList.jpg width=70%></div>

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

<div align=center><img src=DataStructure\LinkedList.png></div>

`LinkedList`实现了`Deque`接口，因此，我们可以操作`LinkedList`像操作队列和栈一样。

**总结：**

**查询**多用`ArrayList`，**增删**多用`LinkedList`。

`ArrayList`增删慢不是绝对的(在数量大的情况下，已测试)：
- 如果增加元素一直是使用`add()`(**增加到末尾**)的话，那是`ArrayList`要快；
- 一直**删除末尾**的元素也是`ArrayList`要快(不用复制移动位置)；
- 至于如果删除的是**中间的位置**的话，还是`ArrayList`要快！
    - 如果删除的是中间的位置的话，需要两个步骤：1.遍历找到这个元素；2.进行删除增加操作。大样本时，`ArrayList`的第一步操作会快很多。

但一般来说：增删多还是用`LinkedList`。

```java
import java.util.*;

public class TestArrayAndLinkedList
{
    public static void main(String[] args)
    {
        List<Integer> arrayList = new ArrayList<>();

        arrayList.add(1); // 1 is autoboxed to new Integer(1)
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(4);
        arrayList.add(0, 10);
        arrayList.add(3, 30);

        System.out.println("A list of integers in the array list:");
        System.out.println(arrayList);
        arrayList.forEach(element -> System.out.print(element + " "));
        System.out.println();

        LinkedList<Object> linkedList = new LinkedList<>(arrayList);

        linkedList.add(1, "red");
        linkedList.removeLast();
        linkedList.addFirst("green");

        System.out.println("Display the linked list backward:");
        for (int i = linkedList.size() - 1; i >= 0; i--)
            System.out.print(linkedList.get(i) + " ");
  }
}
```




## 向量类

<div align=center><img src=DataStructure\Collection.jpg></div>

Java合集框架是在Java 2中引入的。Java 2之前的版本也支持一些数据结构，其中就有`向量类Vector`与`栈类Stack`。为了适应Java合集框架，Java 2对这些类进行了重新设计，但是为了向后兼容，保留了它们所有的以前形式的方法。

除了包含用于**访问**和**修改**向量的**同步方法**之外， Vector类与Arraylist是一样的。同步方法用于防止两个或多个线程同时访问和修改某个向量时引起数据损坏。<font color=red>对于许多不需要同步的应用程序来说，使用Arraylist比使用Vector效率更高</font>。

<div align=center><img src=DataStructure\Vector.jpg width=80%></div>

方法`elements()`返回一个`Enumeration`对象（枚举型对象）。`Enumeration`接口是在Java 2之前引入的，已经被`Iterator`接口所取代。

```java {.line-numbers highlight=4-5}
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                         capacityIncrement : oldCapacity);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

因为通常`capacityIncrement`并不定义，所以默认情况下它是**扩容两倍**。



### Vector与ArrayList区别

- `Vector`底层也是数组，与`ArrayLis`t最大的区别就是：同步(**线程安全**)。如果想要`ArrayList`实现同步，可以使用`Collections`的方法：`List list = Collections.synchronizedList(new ArrayList(...));`，就可以实现同步。

- ArrayList在底层数组不够用时在原来的基础上扩展`0.5`倍，即新容量是原容量的1.5倍；Vector是扩展`1`倍，即新容量是原容量的2倍。


## 栈类

<div align=center><img src=DataStructure\容器1.png></div>

在Java合集框架中，栈类Stack是作为Vector类的扩展来实现的，所以官方文档都说不让用了：
<div align=center><img src=DataStructure\Stack.jpg width=70%></div>

Stack是**后进先出**(LIFO)的线性数据结构。leetcode中“**有效的括号**”、“**二叉树的遍历**”都会使用它。

**浏览器“回退”功能的实现，底层使用的就是栈存储结构**。当你关闭页面A时，浏览器会将页面A入栈；同样，当你关闭页面B时，浏览器也会将B入栈。因此，当你执行回退操作时，才会首先看到的是页面B，然后是页面A，这是栈中数据依次出栈的效果。

想实现Stack的语义，就用`ArrayDeque`：`Deque<Integer> stack = new ArrayDeque<>();`


## 总结
<div align=center><img src=DataStructure\List总结.jpg></div>


# Comparator接口

`Comparable-->compareTo`；`Comparator-->compare`

Java API的一些类，比如`String`、`Date`、`Calendar`、`BigInteger`、`BigDecimal`以及所有**基本类型的数字包装类**都实现了`Comparable`接口。<font color=red>`Comparable`接口定义了`compareTo`方法，用于比较实现了`Comparable`接口的**同一个类**的两个元素</font>。

**如果元素的类没有实现Comparable接口又将如何呢**？这些元素可以比较么？

可以定义一个**比较器**(comparator)来比较**不同类**的元素。要做到这一点，需要创建一个实现`java.util.Comparator<T>`接口的类并重写它的`compare`方法。
`public int compare(T elementl, T element2)`
>如果`element1`**小于**`element2`, 就返回一个**负值**； 如果`element1`**大于**`element2`, 就返回一个**正值**； 若两者**相等**， 则返回**0**。

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

# 线性表和合集Collections的静态方法

**`Collections`类包含了执行合集和线性表中通用操作的静态方法**。

`java.util.Collection`是一个**集合接口**（集合类的一个顶级接口）。它提供了对集合对象进行**基本操作的通用接口方法**。Collection接口在Java类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式，其直接继承接口有List与Set。

`Collections`则是集合类的一个**工具类/帮助类**，其中提供了一系列**静态方法**，用于对集合中元素进行排序、搜索以及线程安全等各种操作。

<div align=center><img src=DataStructure\Collections.jpg width=90%></div>



# 队列和优先队列

<div align=center><img src=DataStructure\容器1.png></div>

<div align=center><img src=DataStructure\Collection.jpg></div>

队列(queue)是一种**先进先出**的数据结构。元素被追加到队列末尾，然后从队列头删除。在优先队列(priority queue)中，元素被赋予优先级。<font color=red>当访问元素时，拥有最高优先级的元素首先被删除</font>。

<div align=center><img src=DataStructure\Queue.jpg width=70%></div>

队列(queue)有两组API，基本功能是一样的，但是，一组是会抛异常的；另一组会返回一个特殊值：

| 功能 |   抛异常  |  返回值  |
|:----:|:---------:|:--------:|
|  增  |   add(e)  | offer(e) |
|  删  |  remove() |  poll()  |
|  瞧  | element() |  peek()  |

如果**队列空了**，那`remove()`就会抛异常，但是`poll()`就返回`null`；`element()`就会抛异常，而`peek()`就返回`null`。

`add(e)`怎么会抛异常呢？

有些Queue它会**有容量的限制**，比如`BlockingQueue`，如果已经**达到了它最大的容量且不会扩容**，就会抛异常；但使用`offer(e)`，就会返回`false`。

那怎么选择呢？

- 首先，要用就用同一组 API，前后要统一；
- 其次，根据需求。如果你需要它抛异常，那就是用抛异常的；不过做算法题时基本不用，所以选那组返回特殊值的就好了。


## 双端队列Deque和链表LinkedList

<div align=center><img src=DataStructure\Collection.jpg></div>

<div align=center><img src=DataStructure\Linkedlist实现了List和Deque.jpg width=50%></div>

Deque是两端都可以进出的，那自然是有针对First端的操作和对Last端的操作，那每端都有两组，一组抛异常，一组返回特殊值：

| 功能 | 抛异常                      | 返回值                      |
|------|-----------------------------|-----------------------------|
| 增   | addFirst(e)/ addLast(e)     | offerFirst(e)/ offerLast(e) |
| 删   | removeFirst()/ removeLast() | pollFirst()/ pollLast()     |
| 瞧   | getFirst()/ getLast()       | peekFirst()/ peekLast()     |

LinkedList类实现了Deque接口，Deque又继承自Queue接口。因此，**<font color=red>可以使用LinkedList创建一个队列</font>**。LinkedList很适合用于进行队列操作，因为它可以高效地在线性表的两端插入和移除元素。




```java
public class TestQueue
{
    public static void main(String[] args)
    {
        java.util.Queue<String> queue = new java.util.LinkedList<>();
        queue.offer("Oklahoma");
        queue.offer("Indiana");
        queue.offer("Georgia");
        queue.offer("Texas");

        while (queue.size() > 0)
            System.out.print(queue.remove() + " ");
    }
}

/*
Oklahoma Indiana Georgia Texas 
 */
```


默认情况下，优先队列使用`Comparable`以元素的**自然顺序**进行排序。拥有最小数值的元素被赋予最高优先级，因此最先从队列中删除。如果几个元素具有相同的最高优先级，则任意选择一个。也可以使用构造方法`PriorityQueue(initialCapacity, comparator)`中的`Comparator`来指定一个顺序。

<div align=center><img src=DataStructure\PriorityQueue.jpg width=70%></div>

```java
import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueDemo
{
    public static void main(String[] args)
    {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

        priorityQueue.offer("Oklahoma");
        priorityQueue.offer("Indiana");
        priorityQueue.offer("Georgia");
        priorityQueue.offer("Texas");

        System.out.println("Priority queue using Comparable: ");
        while (priorityQueue.size() > 0)
            System.out.print(priorityQueue.remove() + " ");

        PriorityQueue<String> priorityQueue1 = new PriorityQueue<>(4, Collections.reverseOrder());
        priorityQueue1.offer("Oklahoma");
        priorityQueue1.offer("Indiana");
        priorityQueue1.offer("Georgia");
        priorityQueue1.offer("Texas");

        System.out.println("\n\nPriority queue using Comparator: ");
        while (priorityQueue1.size() > 0)
            System.out.print(priorityQueue1.remove() + " ");
    }
}
```


## 实现类：LinkedList, ArrayDeque, PriorityQueue




# 集合Set

**<font color=red>无序！不可重复！</font>**

<div align=center><img src=DataStructure\Collection.jpg></div>

集合(set)是一个用于存储和处理**无重复元素**的高效数据结构。

Set集合常用子类：
- HashSet集合
    底层数据结构是**哈希表**(是一个元素为链表的数组)

- TreeSet集合
    - 底层数据结构是**红黑树**(是一个自平衡的二叉树)
    - 保证元素的**排序**方式

- LinkedHashSet集合
    底层数据结构由**哈希表和链表**组成。

`Set`接口扩展了`Collection`合集接口：
<div align=center><img src=DataStructure\集合类.jpg width=80%></div>

`AbstractSet`类继承`AbstractCollection`类并部分实现`Set`接口。`AbstractSet`类提供`equals`方法和`hashCode`方法的具体实现。一个集合的散列码是这个集合中所有元素散列码的和。由于`AbstractSet`类没有实现`size`方法和`iterator`方法，所以`AbstractSet`类是一个抽象类。

## 散列类HashSet

`HashSet`类可以用来存储互不相同的任何元素。考虑到效率的因素，<font color=red>添加到散列集中的对象必须以一种正确分散散列码的方式来实现`hashCode`方法</font>。回顾在`Object`类中定义的`hashCode`, <font color=red>如果两个对象相等，那么这两个对象的散列码必须一样。两个不相等的对象可能会有相同的散列码</font>，因此应该实现`hashCode`方法以避免出现太多这样的清况。Java API中的大多数类都实现了`hashCode`方法。例如，`Integer`类中的`hashCode`方法返回它的`int`值， `Character`类中的`hashCode`方法返回这个`字符的统一码`。

```java
import java.util.Set;
import java.util.HashSet;

public class TestHashSet
{
    public static void main(String[] args)
    {
        // Create a has set
        Set<String> set = new HashSet<>();

        // Add strings to the set
        set.add("London");
        set.add("Beijing");
        set.add("New York");
        set.add("Beijing");

        System.out.println(set);

        // Display the elements in the hash set
        for (String s : set)
            System.out.print(s.toUpperCase() + " ");
    }
}

/*
[Beijing, New York, London]
BEIJING NEW YORK LONDON 
 */
```

- `Beijing`被添加多次，但是**只有一个被存储**，因为集合不允许有重复的元素。
- 字符串没有按照它们被插入集合时的顺序存储，因为散列集中的**元素没有特定的顺序**。要强加给它们一个顺序，就需要使用`LinkedHashSet`类。
- `Collection`接口继承`Iterable`接口，因此集合中的元素是可遍历的。使用了`foreach`循环来遍历集合中的所有元素。


## 链式散列集LinkedHashSet

>LinkedHashSet extends HashSet with a linked-list implementation that supports an ordering of the elements in the set.

`HashSet`中的元素是没有被排序的，而`LinkedHashSet`中的元素可以<font color=red>按照它们插入集合的顺序提取</font>。

```java
import java.util.LinkedHashSet;
import java.util.Set;

public class TestLinkedHashSet
{
    public static void main(String[] args)
    {
        Set<String> set = new LinkedHashSet<>();

        set.add("London");
        set.add("Beijing");
        set.add("New York");
        set.add("Beijing");

        System.out.println(set);

        for (String s : set)
            System.out.print(s + " ");
    }
}
/*
[London, Beijing, New York]
London Beijing New York 
 */
```

如果不需要维护元素被插入的顺序，就应该使用`HashSet`, 它会比`LinkedHashSet`更加高效。

## 树形集TreeSet

`SortedSet`是`Set`的一个子接口，它可以确保集合中的元素是**有序**的。另外，它还提供方法`frst()`和`last()`以返回集合中的第一个元素和最后一个元素，以及方法`headSet(toElement)`和`tailSet(fromElement)`以分别返回集合中元素**小于**`toElement`和**大于或等于**`fromElement`的那一部分。

`NavigableSet`扩展了`SortedSet`, 并提供导航方法`lower(e)`、`floor(e)`、`ceiling(e)`和`higher(e)`以分别返回**小于**、**小于或等于**、**大于或等于**以及**大于**一个给定元素的元素。如果没有这样的元素，方法就返回`null`。方法`pollFirst()`和`pollLast()`会分别**删除**和**返回**树形集中的第一个元素和最后一个元素。

`TreeSet`实现了`SortedSet`接口。只要**对象是可以互相比较的**，就可以将它们添加到一个树形集( tree set) 中。

使用`TreeSet`类<font color=red>按照字母顺序来显示这些字符串</font>：

```java
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestTreeSet
{
    public static void main(String[] args)
    {
        Set<String> set = new HashSet<>();

        set.add("London");
        set.add("Paris");
        set.add("New York");
        set.add("San Francisco");
        set.add("Beijing");
        set.add("New York");

        TreeSet<String> treeSet = new TreeSet<>(set);
        System.out.println("Sorted tree set: " + treeSet);

        // Use the methods in SortedSet interface
        System.out.println("first(): " + treeSet.first());
        System.out.println("last(): " + treeSet.last());
        System.out.println("headSet(\"New York\"): " + treeSet.headSet("New York"));
        System.out.println("tailSet(\"New York\"): " + treeSet.tailSet("New York"));

        // Use the methods in NavigableSet interface
        System.out.println("lower(\"P\"): " + treeSet.lower("P"));
        System.out.println("higher(\"P\"): " + treeSet.higher("P"));
        System.out.println("floor(\"P\"): " + treeSet.floor("P"));
        System.out.println("ceiling(\"P\"): " + treeSet.ceiling("P"));
        System.out.println("pollFirst(): " + treeSet.pollFirst());
        System.out.println("pollLast(): " + treeSet.pollLast());
        System.out.println("New tree set: " + treeSet);
    }
}
/*
Sorted tree set: [Beijing, London, New York, Paris, San Francisco]
first(): Beijing
last(): San Francisco
headSet("New York"): [Beijing, London]
tailSet("New York"): [New York, Paris, San Francisco]
lower("P"): New York
higher("P"): Paris
floor("P"): New York
ceiling("P"): Paris
pollFirst(): Beijing
pollLast(): San Francisco
New tree set: [London, New York, Paris]
 */
```

当**更新一个集合**时，如果不需要保持元素的排序关系，就应该使用散列集，因为在散列集中插入和删除元素所花的时间比较少。当需要一个排好序的集合时，可以从这个散列集创建一个树形集。


<font color=red>如果使用**无参构造方法**创建一个`TreeSet`, 则会假定元素的类实现了`Comparable`接口，并使用`compareTo`方法来比较集合中的元素</font>。要使用`comparator`, 则必须用构造方法`TreeSet(Comparator comparator)`, 使用比较器中的`compare`方法来创建一个排好序的集合。

```java
// GeometricObject.java

public abstract class GeometricObject
{
    private String color = "white";
    private boolean filled;
    private java.util.Date dateCreated;

    /** Construct a default geometric object */
    protected GeometricObject()
    {
        dateCreated = new java.util.Date();
    }

    /** Construct a geometric object with color and filled value */
    protected GeometricObject(String color, boolean filled)
    {
        dateCreated = new java.util.Date();
        this.color = color;
        this.filled = filled;
    }

    /** Return color */
    public String getColor()
    {
        return color;
    }

    /** Set a new color */
    public void setColor(String color)
    {
        this.color = color;
    }

    /** Return filled. Since filled is boolean,
     *  the get method is named isFilled */
    public boolean isFilled()
    {
        return filled;
    }

    /** Set a new filled */
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }

    /** Get dateCreated */
    public java.util.Date getDateCreated()
    {
        return dateCreated;
    }

    /** Return a string representation of this object */
    public String toString()
    {
        return "created on " + dateCreated + "\ncolor: " + color + " and filled: " + filled;
    }

    /** Abstract method getArea */
    public abstract double getArea();

    /** Abstract method getPerimeter */
    public abstract double getPerimeter();
}


// Rectangle.java
public class Rectangle extends GeometricObject
{
    private double width;
    private double height;

    public Rectangle() { }

    public Rectangle(double width, double height)
    {
        this.width = width;
        this.height = height;
    }

    /** Return width */
    public double getWidth()
    {
        return width;
    }

    /** Set a new width */
    public void setWidth(double width)
    {
        this.width = width;
    }

    /** Return height */
    public double getHeight()
    {
        return height;
    }

    /** Set a new height */
    public void setHeight(double height)
    {
        this.height = height;
    }

    @Override /** Return area */
    public double getArea()
    {
        return width * height;
    }

    @Override /** Return perimeter */
    public double getPerimeter()
    {
        return 2 * (width + height);
    }
}


// Circle.java

public class Circle extends GeometricObject
{
    private double radius;

    public Circle() { }

    public Circle(double radius)
    {
        this.radius = radius;
    }

    /** Return radius */
    public double getRadius()
    {
        return radius;
    }

    /** Set a new radius */
    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    @Override /** Return area */
    public double getArea()
    {
        return radius * radius * Math.PI;
    }

    /** Return diameter */
    public double getDiameter()
    {
        return 2 * radius;
    }

    @Override /** Return perimeter */
    public double getPerimeter()
    {
        return 2 * radius * Math.PI;
    }

    /* Print the circle info */
    public void printCircle()
    {
        System.out.println("The circle is created " + getDateCreated() + " and the radius is " + radius);
    }
}


// GeometricObjectComparator.java

import java.util.Comparator;

public class GeometricObjectComparator
    implements Comparator<GeometricObject>, java.io.Serializable
{
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


// TestTreeSetWithComparator.java
import java.util.Set;
import java.util.TreeSet;

public class TestTreeSetWithComparator
{
    public static void main(String[] args)
    {
        // Create a tree set for geometric objects using a comparator
        Set<GeometricObject> set = new TreeSet<>(new GeometricObjectComparator());
        set.add(new Rectangle(4, 5));
        set.add(new Circle(40));
        set.add(new Circle(40));
        set.add(new Rectangle(4, 1));

        System.out.println("A sorted set of geometric objects");
        for (GeometricObject element : set)
            System.out.println("area = " + element.getArea());
    }
}

/*
A sorted set of geometric objects
area = 4.0
area = 20.0
area = 5026.548245743669
*/
```

## 比较集合和线性表的性能

在**无重复**元素进行排序方面，集合比线性表更加高效。线性表在通过**索引**来访问元素方面非常有用。

线性表中的元素可以通过索引来访问。而集合不支持索引，因为集合中的元素是无序的。要遍历集合中的所有元素，使用`foreach循环`。

在测试<font color=red>一个元素是否在集合或者线性表的方面，集合比线性表更加高效</font>。

## 统计关键字

对于Java源文件中的每个单词，需要确定该单词是否是一个关键字。为了高效处理这个问题，将所有的关键字保存在一个`HashSet`中，并且使用`contains`方法来测试一个单词是否在关键字集合中。

```java
import java.util.*;
import java.io.*;

public class CountKeywords
{
    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a Java source file: ");
        String filename = input.nextLine();

        File file = new File(filename);
        if (file.exists())
            System.out.println("The number of keywords in " + filename + " is " + countKeywords(file));
        else
            System.out.println("File " + filename + " does not exist");
    }

    public static int countKeywords(File file) throws Exception
    {
        // Array of all Java keywords + true, false and null
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        Scanner input = new Scanner(file);

        while (input.hasNext())
        {
            String word = input.next();
            if (keywordSet.contains(word))
                count++;
        }

        return count;
    }
}

/*
Enter a Java source file: D:\Learning_Java\Java_Code\Inheritance\src\inheritance\Manager.java
The number of keywords in D:\Learning_Java\Java_Code\Inheritance\src\inheritance\Manager.java is 17
 */
```



# 映射表Map

映射表(map)类似于目录，提供了使用**键key**快速查询和荻取**值value**的功能。

映射表(map) 是一种依照**键/值对**存储元素的容器。它提供了**通过键快速获取、删除和更新键/值对**的功能。映射表将值和键一起保存。键很像下标。在`List`中，下标是整数；而在`Map`中，键可以是**任意类型的对象**。映射表中**不能有重复的键**，每个键都对应一个值。一个键和它的对应值构成一个条目并保存在映射表中。

<div align=center><img src=DataStructure\映射表.jpg></div>


**链表**和**数组**都可以按照人们的意愿来排列元素的次序，他们可以说是有序的(存储的顺序和取出的顺序是一致的)。但同时，这会带来缺点：想要获取某个元素，就要访问所有的元素，直到找到为止。而**散列表**不在意元素的顺序，能够快速的查找元素的数据。


## 散列表Hash Table工作原理

散列表为每个对象计算出一个整数，称为**散列码**（例如：用`hashCode`函数得出`Lee`的散列码为`76268`）。根据这些计算出来的整数(散列码)保存在对应的位置上！在Java中，**散列表用的是链表+数组实现的**，每个列表称之为桶。


## 映射表三种类型

映射表有三种类型：散列映射表`HashMap` 、链式散列映射表`LinkedHashMap`和树形映射表`TreeMap`。这些映射表的通用特性都定义在`Map`接口中：

<div align=center><img src=DataStructure\三种映射表.jpg width=90%></div>

`Map接口`提供了查询、更新和获取合集的值和合集的键的方法：

<div align=center><img src=DataStructure\Map接口.jpg width=80%></div>

`HashMap`、`LinkedHashMap`和`TreeMap`类是`Map`接口的三个具体实现：

<div align=center><img src=DataStructure\三种映射表关系.jpg width=80%></div>

对于**定位**一个值、**插入**一个条目以及**删除**一个条目而言，`HashMap`类是高效的。

- 1.**AbstractMap类**是一个便利抽象类，它实现了Map接口中除了`entrySet()`方法之外的所有方法。
  
- 2.**LinkedHashMap类**用链表实现来扩展`HashMap`类，它支持映射表中条目的**排序**。`HashMap`类中的条目是**没有顺序**的，但是在`LinkedHashMap`中，<font color=red>元素既可以按照它们插入映射表的顺序排序（称为插入顺序`insertion order`), 也可以按它们被最后一次访问时的顺序，从最早到最晚（称为访问顺序`access order`) 排序</font>。
  - **无参构造方法**是以**插入顺序**来创建`LinkedHashMap`对象的。
  - 要按**访问顺序**创建`LinkedHashMap`对象，应该使用构造方法`LinkedHashMap(initialCapacity,loadFactor, true)`。

- 3.**TreeMap类**在<font color=red>遍历排好顺序的键时是很高效的</font>。
  - 键可以使用`Comparable`接口或`Comparator`接口来排序。如果使用它的**无参构造方法**创建一个`TreeMap`对象，假定键的类实现了`Comparable`接口，则可以使用`Comparable`接口中的`compareTo`方法来对映射表内的键进行比较。
  - 要使用**比较器**，必须使用构造方法`TreeMap(Comparator comparator)`来创建一个有序映射表，这样，该映射表中的条目就能使用比较器中的`compare`方法按键进行排序。

- 4.`SortedMap`是`Map`的一个子接口，使用它可确保映射表中的条目是**排好序**的。除此之外，它还提供方法`firstKey()`和`lastKey()`来返回映射表中的第一个和最后一个键，而方法`headMap(toKey)`和`tailMap(fromKey)`分别返回键**小于**`toKey`的那部分映射表和键**大于或等于**`fromKey`的那部分映射表。

- 5.`NavigableMap`继承了`SortedMap`, 以提供导航方法`lowerKey(key)`、`floorKey(key)`、`ceilingKey(key)`和`higherKey(key)`来分别返回**小于**、**小于或等于**、**大于或等于**、**大于**某个给定键的键，如果没有这样的键，它们都会返回`null` 。方法`pollFirstEntry()`和`pollLastEntry()`分别**删除**并**返回**树映射表中的第一个和最后一个条目。


## HashMap

参考资料：
https://www.cnblogs.com/duodushuduokanbao/p/9492952.html
https://www.jianshu.com/p/8324a34577a0

<div align=center><img src=DataStructure\Map.webp width=70%></div>

<div align=center><img src=DataStructure\HashMap.png></div>

`public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable`

<div align=center><img src=DataStructure\HashMap17.jpg></div>

### 概述

HashMap存储的是key-value的键值对，允许key为null，也允许value为null。

HashMap内部为数组+链表的结构，会**根据key的hashCode值来确定数组的索引**(确认放在哪个桶里)。遇到索引相同的key，那么他们就会被分到一个桶中(hash冲突)。**如果发生hash冲突，HashMap会将同一个桶中的数据以链表的形式存储**，但是如果发生hash冲突的概率比较高，就会导致**同一个桶中的链表长度过长**，遍历效率降低，所以在JDK1.8中**如果链表长度到达阀值(默认是8)，就会将链表转换成红黑二叉树**。


- 无序，允许为`null`，**非同步**；
- 底层由**散列表(哈希表)**实现（Java中散列表的实现是通过**数组+链表**）；
- **初始容量**和**装载因子**对HashMap影响较大，需适中。
- **不需要线程安全**的场合可以用`HashMap`，需要线程安全的场合可以用`ConcurrentHashMap`。

在JDK8中HashMap的底层是：**<u>数组+链表</u>(散列表)+红黑树**；

在散列表中有**装载因子**这么一个属性，当`装载因子*初始容量`小于散列表元素时，该散列表会再散列，扩容`2`倍！


初始容量的默认值是`16`，它也一样，无论初始大了还是小了，对我们的HashMap都是有影响的：

- 初始容量过大，那么遍历时速度就会受影响；
- 初始容量过小，散列表再散列(扩容的次数)可能就变得多，扩容也是一件非常耗费性能的一件事；


HashMap数组每一个元素的初始值都是`Null`：

<div align=center><img src=DataStructure\HashMap1.png width=70%></div>

### HashMap数据结构

在Java中，保存数据有两种比较简单的数据结构：**数组**和**链表**。数组的特点是：**寻址容易，插入和删除困难**；而链表的特点是：**寻址困难，插入和删除容易**。

常用的哈希函数的冲突解决办法中有一种方法叫做**链地址法**，其实就是将数组和链表组合在一起，发挥了两者的优势，我们可以将其理解为链表的数组。

链式哈希表从根本上说是由一组链表构成。**每个链表都可以看做是一个“桶”**，我们将所有的元素通过散列的方式放到具体的不同的桶中。插入元素时，首先将其键传入一个哈希函数（该过程称为哈希键），函数通过散列的方式告知元素属于哪个“桶”，然后在相应的链表头插入元素。查找或删除元素时，用同样的方式先找到元素的“桶”，然后遍历相应的链表，直到发现我们想要的元素。因为每个“桶”都是一个链表，所以链式哈希表并不限制包含元素的个数。然而，如果表变得太大，它的性能将会降低。

在JDK1.7中，**当Hash冲突严重时，在桶上形成的链表会变的越来越长**，这样在查询时的效率就会越来越低；时间复杂度为$O(N)$。JDK1.8中对大链表做了优化，修改为红黑树之后查询效率直接提高到了$O(logN)$。

<div align=center><img src=DataStructure\HashMap34.jpg></div>

<div align=center><img src=DataStructure\HashMap9.png></div>

<div align=center><img src=DataStructure\HashMap9_1.jpg></div>

HashMap中的数组元素和链表节点采用**Node类**实现，与JDK 1.7的相比（**Entry类**），仅仅只是换了名字！

```java
// Node实现了Map.Entry接口，本质上是一个映射(k-v)
// Node本质上是一个Map，存储着key-value。
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;  // 保存该桶的hash值
    final K key;     // 不可变的key
    V value;
    Node<K,V> next;  // 指向一个数据的指针

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
...
}
```

HashMap中的**红黑树节点** 采用**TreeNode类**实现：
```java
    /**
     * Entry for Tree bins. Extends LinkedHashMap.Entry (which in turn
     * extends Node) so can be used as extension of either regular or
     * linked node.
     */
    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }
    ...
    }
```

### HashMap中的重要参数

**有时候两个key的hashCode可能会定位到一个桶中**，这时就发生了**hash冲突**，如果HashMap的**hash算法越散列**，那么发生hash冲突的概率越低；如果数组越大，那么发生hash冲突的概率也会越低，但是数组越大带来的**空间开销越多**，但是遍历速度快。这就要在空间和时间上进行权衡，这就要看看HashMap的扩容机制，在说扩容机制之前先看几个比较重要的字段：

```java
/**
 * The default initial capacity - MUST be a power of two.
 * 默认桶16个：1 << 4 = 1 * 2^4
 */
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

/**
 * 默认桶最多有2^30个
 * The maximum capacity, used if a higher value is implicitly specified
 * by either of the constructors with arguments.
 * MUST be a power of two <= 1<<30.
 */
static final int MAXIMUM_CAPACITY = 1 << 30;

/**
 * 加载因子：HashMap在其容量自动增加前可达到多满的一种尺度
 * 默认负载因子是0.75
 * The load factor used when none specified in constructor.
 */
static final float DEFAULT_LOAD_FACTOR = 0.75f;

/**
 * 实际加载因子
 * The load factor for the hash table.
 */
final float loadFactor;

/**
 * HashMap的大小，即 HashMap中存储的键值对的数量
 * The number of key-value mappings contained in this map.
 */
transient int size;

/**
 * 扩容阈值
 * 当前HashMap所能容纳键-值对数量的最大值，超过这个值，则需要扩容！
 * The next size value at which to resize (capacity * load factor).
 */
int threshold;


/**
 * The table, initialized on first use, and resized as
 * necessary. When allocated, length is always a power of two.
 * (We also tolerate length zero in some operations to allow
 * bootstrapping mechanics that are currently not needed.)
 */
transient Node<K,V>[] table;
```

`threshold = 负载因子 * length`，也就是说数组长度固定以后，如果负载因子越大，所能容纳的元素个数越多，如果超过这个值就会进行扩容(**默认是扩容为原来的2倍**)，**0.75这个值是权衡过空间和时间得出的**，建议大家不要随意修改，如果在一些特殊情况下，比如空间比较多，但要求速度比较快，这时候就可以把扩容因子调小以较少hash冲突的概率。相反就增大扩容因子(这个值可以大于1)。

`size`就是HashMap中键值对的总个数。还有一个字段是`modCount`，记录是发生内部结构变化的次数，如果put值，但是put的值是覆盖原有的值，这样是不算内部结构变化的。


#### 默认初始长度

HashMap的**默认初始长度是16**，并且每次自动扩展或是手动初始化时，**长度必须是2的幂次**。

之所以选择16是为了服务于从Key映射到index的Hash算法。从Key映射到HashMap数组的对应位置，会用到一个Hash函数：`index = Hash("apple")`。

如何实现一个**尽量均匀分布的Hash函数**呢？

我们通过**利用Key的HashCode值来做某种运算**。

为了实现高效的Hash算法，采用了**位运算**：`index = HashCode(Key) & (Length - 1)`，其中Length是**HashMap的长度**。

以值为“book”的Key来演示整个过程：

1. **计算book的hashcode**，结果为十进制的3029737，二进制的101110001110101110 1001。
2. 假定HashMap长度是默认的16，计算Length-1的结果为十进制的15，**二进制的1111**。
3. 把以上两个结果做**与运算**，101110001110101110 1001 & 1111 = 1001，十进制是9，所以 index=9。

可以说，**Hash算法最终得到的index结果，完全取决于Key的Hashcode值的最后几位**。

如果取HashMap长度为10，有些index结果的出现几率会更大，而有些index结果永远不会出现（比如0111）！这样，显然不符合**Hash算法均匀分布**的原则。


反观长度16或者其他2的幂，**Length-1的值是所有二进制位全为1**，这种情况下，**index的结果等同于HashCode后几位的值。只要输入的HashCode本身分布均匀，Hash算法的结果就是均匀的**。


#### 加载因子

装载因子的默认值是`0.75`，无论是初始大了还是初始小了对我们HashMap的性能都不好：
- 装载因子初始值**大**了，可以减少散列表再散列(**减少扩容的次数**)，但同时会导致**散列冲突的可能性变大**！
- 装载因子初始值**小**了，可以**减小散列冲突的可能性**，但同时**扩容的次数可能就会变多**！

<div align=center><img src=DataStructure\HashMap21.jpg></div>


#### 与红黑树相关的参数

```java {.line-numbers highlight=26-29}
/**
 * The bin count threshold for using a tree rather than list for a bin.
 * Bins are converted to trees when adding an element to a
 * bin with at least this many nodes. The value must be greater
 * than 2 and should be at least 8 to mesh with assumptions in
 * tree removal about conversion back to plain bins upon
 * shrinkage.
 */
// 1. 桶的树化阈值：即 链表转成红黑树的阈值，在存储数据时，当链表长度 > 该值时，则将链表转换成红黑树
static final int TREEIFY_THRESHOLD = 8;

/**
 * The bin count threshold for untreeifying a (split) bin during a
 * resize operation. Should be less than TREEIFY_THRESHOLD, and at
 * most 6 to mesh with shrinkage detection under removal.
 */
// 2. 桶的链表还原阈值：即 红黑树转为链表的阈值，当在扩容resize()时（此时HashMap的数据存储位置会重新计算），在重新计算存储位置后，当原有的红黑树内数量 < 6时，则将 红黑树转换成链表
static final int UNTREEIFY_THRESHOLD = 6;
   
/**
 * The smallest table capacity for which bins may be treeified.
 * (Otherwise the table is resized if too many nodes in a bin.)
 * Should be at least 4 * TREEIFY_THRESHOLD to avoid conflicts
 * between resizing and treeification thresholds.
 */
// 3. 最小树形化容量阈值：即 当哈希表中的容量 > 该值时，才允许树形化链表（即 将链表 转换成红黑树）
// 否则，若桶内元素太多时，则直接扩容，而不是树形化
// 为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
static final int MIN_TREEIFY_CAPACITY = 64;
```

<div align=center><img src=DataStructure\HashMap5.webp></div>

**为什么在JDK1.8中进行对HashMap优化的时候，把链表转化为红黑树的阈值是8,而不是7或者不是20呢？**

- 如果选择6和8（如果链表小于等于6树还原转为链表，大于等于8转为树），中间有个差值7可以**有效防止链表和树频繁转换**。
  假设设计成链表个数超过8则链表转换成树结构，链表个数小于8则树结构转换成链表，如果一个HashMap不停的插入、删除元素，链表个数在8左右徘徊，就会频繁的发生树转链表、链表转树，效率会很低。
  
- 由于TreeNodes的大小大约是常规节点的两倍，因此我们仅在容器包含足够的节点以保证使用时才使用它们，当它们变得太小（由于移除或调整大小）时，它们会被转换回普通的node节点，**容器中节点分布在hash桶中的频率遵循泊松分布，桶的长度超过8的概率非常非常小**。所以作者应该是根据概率统计而选择了8作为阀值。

```
Because TreeNodes are about twice the size of regular nodes, we use them only when bins contain enough nodes to warrant use (see TREEIFY_THRESHOLD). 

And when they become too small (due to removal or resizing) they are converted back to plain bins.  In usages with well-distributed user hashCodes, tree bins are rarely used.  

Ideally, under random hashCodes, the frequency of nodes in bins follows a Poisson distribution (http://en.wikipedia.org/wiki/Poisson_distribution) with a parameter of about 0.5 on average for the default resizing threshold of 0.75, although with a large variance because of resizing granularity. Ignoring variance, the expected occurrences of list size k are (exp(-0.5) * pow(0.5, k) / factorial(k)). The first values are:
*
* 0:    0.60653066
* 1:    0.30326533
* 2:    0.07581633
* 3:    0.01263606
* 4:    0.00157952
* 5:    0.00015795
* 6:    0.00001316
* 7:    0.00000094
* 8:    0.00000006
* more: less than 1 in ten million
```



### HashMap构造函数

```java {.line-numbers highlight=4}
 /**
  * 函数使用原型
  */
  Map<String,Integer> map = new HashMap<String,Integer>();

  /**
   * 源码分析：主要是HashMap的构造函数 = 4个
   * 仅贴出关于HashMap构造函数的源码
   */

public class HashMap<K,V>
    extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable{

    // 省略之前阐述的参数
    
   /**
    * 构造函数1：默认构造函数（无参）
    * 加载因子、容量 = 默认 = 0.75、16
    */
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     * 构造函数2：指定“容量大小”的构造函数
     * 加载因子 = 默认 = 0.75 、容量 = 指定大小
     */
    public HashMap(int initialCapacity) {
        // 实际上是调用指定“容量大小”和“加载因子”的构造函数
        // 只是在传入的加载因子参数 = 默认加载因子
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
        
    }

    /**
     * 构造函数3：指定“容量大小”和“加载因子”的构造函数
     * 加载因子、容量 = 自己指定
     */
    public HashMap(int initialCapacity, float loadFactor) {

        // 指定初始容量必须非负，否则报错  
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity); 

        // HashMap的最大容量只能是MAXIMUM_CAPACITY，哪怕传入的 > 最大容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;

        // 填充比必须为正  
        if (loadFactor <= 0 || Float.isNaN(loadFactor))  
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);  
        // 设置 加载因子
        this.loadFactor = loadFactor;

        // 设置 扩容阈值
        // 注：此处不是真正的阈值，仅仅只是将传入的容量大小转化为：>传入容量大小的最小的2的幂，该阈值后面会重新计算
        // 下面会详细讲解 ->> 分析1
        this.threshold = tableSizeFor(initialCapacity); 

    }

    /**
     * 构造函数4：包含“子Map”的构造函数
     * 即 构造出来的HashMap包含传入Map的映射关系
     * 加载因子 & 容量 = 默认
     */

    public HashMap(Map<? extends K, ? extends V> m) {

        // 设置容量大小 & 加载因子 = 默认
        this.loadFactor = DEFAULT_LOAD_FACTOR; 

        // 将传入的子Map中的全部元素逐个添加到HashMap中
        putMapEntries(m, false); 
    }
}

   /**
     * 分析1：tableSizeFor(initialCapacity)
     * 作用：将传入的容量大小转化为：>传入容量大小cap的最小的2的幂
     * 与JDK 1.7对比：类似于JDK 1.7 中 inflateTable()里的 roundUpToPowerOf2(toSize)
     */
    static final int tableSizeFor(int cap) {
     int n = cap - 1;
     n |= n >>> 1;
     n |= n >>> 2;
     n |= n >>> 4;
     n |= n >>> 8;
     n |= n >>> 16;
     return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

<div align=center><img src=DataStructure\HashMap19.jpg></div>

- 此处仅用于接收初始容量大小（capacity）、加载因子(Load factor)，但仍**并未真正初始化哈希表，即初始化存储数组table**。
- **真正初始化哈希表（初始化存储数组table）是在第1次添加键值对时，即第1次调用`put()`时**。

```java
    /**
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    transient Node<K,V>[] table;
```



### 确定哈希桶数据索引位置

Hash，一般翻译做**散列**，也有直接音译为**哈希**的，就是**把任意长度的输入，通过散列算法，变换成固定长度的输出，该输出就是散列值**。这种转换是一种**压缩映射**，也就是，**散列值的空间通常远小于输入的空间**，不同的输入**可能会散列成相同的输出**，所以不可能从散列值来唯一的确定输入值。简单的说就**是一种将任意长度的消息压缩到某一<font color=red>固定长度</font>的消息摘要的函数**。

所有散列函数都有如下一个基本特性：**根据同一散列函数计算出的散列值如果不同，那么输入值肯定也不同。但是，根据同一散列函数计算出的散列值如果相同，输入值不一定相同**。

**两个不同的输入值**，根据同一散列函数计算出的**散列值相同**的现象叫做**碰撞**。

根据元素特征计算元素**数组下标**的方法就是**哈希算法**，即`hash()函数`（当然，还包括`indexFor()函数`）

因为**HashMap扩容每次都是扩容为原来的2倍**，所以length总是2的次方，这是非常规的设置，常规设置是把桶的大小设置为素数，因为素数发生hash冲突的概率要小于合数，比如HashTable的默认值设置为11，就是桶的大小为素数的应用(HashTable扩容后不能保证是素数)。HashMap采用这种设置是为了在取模和扩容的时候做出优化。

hashMap是通过**key的hashCode的高16位和低16位异或(不同则为1)**后和桶的数量**取模**得到**索引位置**，即`key.hashcode()^(hashcode>>>16)%length`，当length是2^n时，`h & (length-1)`运算等价于`h % length`，而&操作比%效率更高。而**采用高16位和低16位进行异或，也可以让所有的位数都参与运算，使得在length比较小的时候也可以做到尽量的散列**。

在**扩容**的时候，**如果length每次是2^n**，那么重新计算出来的索引只有两种情况，一种是**old索引+16**，另一种是**索引不变**，所以就**不需要每次都重新计算索引**(JDK1.7中HashMap需要)。

#### 扰动函数


```java
// JDK1.8扰动函数
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// JDK1.7
final int hash(Object k) {
    int h = hashSeed;
    if (0 != h && k instanceof String) {
        return sun.misc.Hashing.stringHash32((String) k);
    }

    h ^= k.hashCode();
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}

//JDK1.7的源码，jdk1.8没有这个方法，但是实现原理一样的
static int indexFor(int h, int length) {  
    return h & (length-1);  // 取模运算
}
```

`key.hashCode()`函数调用的是key键值类型自带的哈希函数，**返回int型散列值**。理论上散列值是一个int型，如果直接拿散列值作为下标访问HashMap主数组的话，考虑到2进制32位带符号的int表值范围从-2147483648到2147483648，前后加起来大概40亿的映射空间。只要**哈希函数映射得比较均匀松散**，一般应用是很难出现碰撞的。

但问题是一个40亿长度的数组，内存是放不下的。HashMap扩容之前的数组初始大小才16。所以**这个散列值是不能直接拿来用的**。**用之前还要先做对数组的长度取模运算，得到的余数才能用来访问数组下标**。源码中模运算是在这个`indexFor( )`函数中完成：`bucketIndex = indexFor(hash, table.length);`。

`indexFor`的代码也很简单，就是把**散列值和数组长度**做一个“与”运算：
```java
//jdk1.7的源码，jdk1.8没有这个方法，但是实现原理一样的
static int indexFor(int h, int length) {  
    return h & (length-1);  // 取模运算
}
```

这也正好解释了**为什么HashMap的数组长度要取2的整次幂**。

因为这样（`数组长度-1`）正好相当于一个“**低位掩码**”。“与”操作的结果就是**散列值的高位全部归零，只保留低位值，用来做数组下标访问**。

以初始长度16为例，16-1=15。2进制表示是：`00000000 00000000 00001111`，和某散列值做“与”操作如下，结果就是截取了最低的四位值：
```
    10100101 11000100 00100101
&   00000000 00000000 00001111
----------------------------------
    00000000 00000000 00000101    //高位全部归零，只保留末四位
```

但这时候问题就来了，这样**就算散列值分布再松散，要是只取最后几位的话，碰撞也会很严重**。更要命的是如果散列本身做得不好，分布上成等差数列的漏洞，会恰好使最后几个低位呈现规律性重复。

这时候**扰动函数**的价值就体现出来了！

<div align=center><img src=DataStructure\HashMap16.png></div>

右位移16位，正好是32bit的一半，**自己的高半区和低半区做异或**，就是为了**混合原始哈希码的高位和低位，以此来加大低位的随机性**。而且混合后的低位掺杂了高位的部分特征，这样高位的信息也被变相保留下来。


### Get方法的原理

<div align=center><img src=DataStructure\HashMap8.webp></div>

<div align=center><img src=DataStructure\HashMap14.webp></div>

使用Get方法根据Key来查找Value的时候，发生了什么呢？

首先会**把输入的Key做一次Hash映射，得到对应的index**：`index = Hash("apple")`，由于**Hash冲突，同一个位置有可能匹配到多个Entry**，这时候就需要顺着对应链表的头节点，一个一个向下来查找。假设我们要查找的Key是“apple”：

<div align=center><img src=DataStructure\HashMap5.jpg width=70%></div>

第一步，我们查看的是头节点Entry6，Entry6的Key是banana，显然不是我们要找的结果。

第二步，我们查看的是Next节点Entry1，Entry1的Key是apple，正是我们要找的结果。

之所以**把Entry6放在头节点**，是因为HashMap的发明者认为，**后插入的Entry被查找的可能性更大**(JDK1.7)。


**HashMap的get方法实现思路：**

1. 判断表或key是否是null，如果是直接返回null
2. 判断索引处第一个key与传入key是否相等，如果相等直接返回
3. 如果不相等，**判断链表是否是红黑二叉树，如果是，直接从树中取值**
4. **如果不是树，就遍历链表查找**

```java
/**
 * 函数原型
 * 作用：根据键key，向HashMap获取对应的值
 */ 
map.get(key)；

public V get(Object key) {
    Node<K,V> e;
    // 1. 计算需获取数据的hash值
    // 2. 通过getNode()获取所查询的数据
    // 3. 获取后，判断数据是否为空
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

/**
 * Implements Map.get and related methods
 *
 * @param hash hash for key
 * @param key the key
 * @return the node, or null if none
 */
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    // 如果表不是空的，并且要查找索引处有值，就判断位于第一个的key是否是要查找的key
    // 定位键值对所在桶的位置(计算存放在数组table中的位置)
    if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
        // 先在数组中找，若存在，则直接返回
        if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // 如果不是就判断链表是否是红黑二叉树，如果是，就从树中取值
        if ((e = first.next) != null) {
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            // 如果不是树，就遍历链表
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```

这里通过`(n - 1)& hash`即可算出在桶数组中的位置。HashMap中**桶数组的大小length总是2的幂**，此时，`(n - 1)& hash` 等价于**对length取余**。但**取余的计算效率没有位运算高**。

### Put方法的原理

```java {.line-numbers highlight=16}
  /**
   * 函数使用原型
   */
   map.put("Android", 1);
   map.put("Java", 2);
   map.put("iOS", 3);
   map.put("数据挖掘", 4);
   map.put("产品经理", 5);

   /**
    * 源码分析：主要分析HashMap的put函数
    */
    public V put(K key, V value) {
        // 1. 对传入数组的键Key计算Hash值
        // 2. 再调用putVal()添加数据进去
        return putVal(hash(key), key, value, false, true);
    }
```
<div align=center><img src=DataStructure\HashMap4.webp></div>

<div align=center><img src=DataStructure\HashMap1.webp></div>

#### hash(key)

`HashTable`对key直接`hashCode()`，若key为null时，会抛出异常，所以**HashTable的key不可为null**。

```java {.line-numbers highlight=24}
    /**
     * 分析1：hash(key)
     * 作用：计算传入数据的哈希码（哈希值、Hash值）
     * 该函数在JDK 1.7 和 1.8 中的实现不同，但原理一样 
     * = 扰动函数 = 使得根据key生成的哈希码（hash值）分布更加均匀、更具备随机性，避免出现hash值冲突（即指不同key但生成同1个hash值）
     * JDK 1.7 做了9次扰动处理 = 4次位运算 + 5次异或运算
     * JDK 1.8 简化了扰动函数 = 只做了2次扰动 = 1次位运算 + 1次异或运算
     */

    // JDK 1.7实现：将 键key 转换成 哈希码（hash值）操作  = 使用hashCode() + 4次位运算 + 5次异或运算（9次扰动）
    static final int hash(int h) {
        h ^= k.hashCode(); 
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    // JDK 1.8实现：将 键key 转换成 哈希码（hash值）操作 = 使用hashCode() + 1次位运算 + 1次异或运算（2次扰动）
    // 1. 取hashCode值： h = key.hashCode() 
    // 2. 高位参与低位的运算：h ^ (h >>> 16)  
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        // a. 当key = null时，hash值 = 0，所以HashMap的key 可为null      
        // 注：对比HashTable，HashTable对key直接hashCode()，若key为null时，会抛出异常，所以HashTable的key不可为null
        // b. 当key ≠ null时，则通过先计算出 key的 hashCode()（记为h），然后 对哈希码进行 扰动处理： 按位 异或（^） 哈希码自身右移16位后的二进制
    }

   /**
     * 计算存储位置的函数分析：indexFor(hash, table.length)
     * 注：该函数仅存在于JDK 1.7 ，JDK 1.8中实际上无该函数（直接用1条语句判断写出），但原理相同
     */
     static int indexFor(int h, int length) {  
        return h & (length-1); 
        // 将对哈希码扰动处理后的结果 与运算(&) （数组长度-1），最终得到存储在数组table的位置（即数组下标、索引）
    }
```

**计算存放在数组table中的位置（即数组下标、索引）的过程**：

<div align=center><img src=DataStructure\HashMap2.webp width=70%></div>

**计算示意图：**

<div align=center><img src=DataStructure\HashMap16.png></div>



**问题1：为什么不直接采用经过hashCode()处理的哈希码作为存储数组table的下标位置？**

`HashTable`对key直接`hashCode()`，若key为null时，会抛出异常，所以**HashTable的key不可为null**。

结论：容易出现**哈希码与数组大小范围不匹配**的情况，即**计算出来的哈希码可能不在数组大小范围内**，从而导致无法匹配存储位置。

<div align=center><img src=DataStructure\HashMap3.webp></div>


为了解决**哈希码与数组大小范围不匹配**的问题，HashMap给出了解决方案：**哈希码 &（数组长度-1）**。


**问题2：为什么采用“哈希码 与运算(&) （数组长度-1）”计算数组下标？**

结论：根据HashMap的容量大小（数组长度），按需**取哈希码一定数量的低位作为存储的数组下标位置**，从而 解决 “哈希码与数组大小范围不匹配” 的问题。

<div align=center><img src=DataStructure\HashMap20.jpg></div>

**问题3：为什么在计算数组下标前，需对哈希码进行二次处理：扰动处理？**

结论：从上述可知，**实际上只能根据数组长度，取哈希码的低几位作为存储数组的下标位置**。而哈希码的低几位位数有限，非常容易发生Hash冲突。因此通过扰动处理，**加大哈希码低位的随机性**，使得分布更均匀，从而提高对应数组存储下标位置的随机性h和均匀性，最终减少Hash冲突。


所有处理的根本目的，都是为了**提高存储`key-value`的数组下标位置的随机性和分布均匀性，尽量避免出现hash值冲突。即：对于不同key，存储的数组下标位置要尽可能不一样**。


#### putVal(hash(key), key, value, false, true);

此处有2个主要讲解点：

- 计算完存储位置后，具体该**如何存放数据**到哈希表中
- 具体如何扩容，即**扩容机制**


##### 如何存放数据


调用Put方法的时候发生了什么呢？


比如调用`hashMap.put("apple", 0)`，插入一个Key为“apple"的元素。这时候我们需要利用一个**哈希函数**来确定Entry的插入位置（index）：`index = Hash("apple")`。假定最后计算出的index是2，那么结果如下：

<div align=center><img src=DataStructure\HashMap2.png width=70%></div>

但是，因为HashMap的长度是有限的，当插入的Entry越来越多时，再完美的Hash函数也难免会**出现index冲突**的情况。比如下面这样：

<div align=center><img src=DataStructure\HashMap3.jpg width=70%></div>

这时候该怎么办呢？

我们可以利用**链表**来解决。

**HashMap数组的每一个元素不止是一个Entry对象，也是一个链表的头节点**。每一个Entry对象通过Next指针指向它的下一个Entry节点。**当新来的Entry映射到冲突的数组位置时，只需要插入到对应的链表即可**：

<div align=center><img src=DataStructure\HashMap4.png width=70%></div>

新来的Entry节点插入链表时，使用的是**头插法(JDK1.7)**。

JDK1.8中HashMap的put方法实现思路：

1. table[]是否为空
2. 判断table[i]处是否插入过值
3. **判断链表长度是否大于8，如果大于就转换为红黑二叉树(JDK1.8)**，并插入树中
4. 判断key是否和原有key相同，如果相同就覆盖原有key的value，并返回原有value
5. 如果key不相同，就插入一个key，记录结构变化一次

```java {.line-numbers highlight=62-64}
/**
* Implements Map.put and related methods
*
* @param hash hash for key
* @param key the key
* @param value the value to put
* @param onlyIfAbsent if true, don't change existing value
* @param evict if false, the table is in creation mode.
* @return previous value, or null if none
*/
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    // 1. 判断table是否为空，如果是空的就创建一个table，并获取他的长度
    // 初始化哈希表的时机 = 第1次调用put函数时，即调用resize() 初始化创建
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    
    // 如果计算出来的索引位置之前没有放过数据，就直接放入
    // 2. 计算插入存储的数组索引i：根据键值key计算的hash值 得到
    // 此处的数组下标计算方式 = i = (n - 1) & hash，同JDK 1.7中的indexFor()
    // 3. 插入时，需判断是否存在Hash冲突：
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        // 进入这里说明索引位置已经放入过数据了
        Node<K,V> e; K k;
        // 判断put的数据和之前的数据是否重复
        // key的地址或key的equals()只要有一个相等就认为key重复了，就直接覆盖原来key的value
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 判断是否是红黑树，如果是红黑树就直接插入树中
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 如果不是红黑树，就遍历每个节点，判断链表长度是否大于8，如果大于就转换为红黑树
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 链表长度如果大于8就转换为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 判断索引每个元素的key是否可要插入的key相同，如果相同就直接覆盖
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        // 如果e不是null，说明没有迭代到最后就跳出了循环，说明链表中有相同的key，因此只需要将value覆盖，并将oldValue返回即可
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
            }
    }
    // 说明没有key相同，因此要插入一个key-value，并记录内部结构变化次数
    ++modCount;

    // 插入成功后，判断实际存在的键值对数量size > 最大容量threshold，若 > ，则进行扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

由于数据结构中加入了红黑树，所以在存放数据到哈希表中时，需进行多次数据结构的判断：数组、红黑树、链表。

<div align=center><img src=DataStructure\HashMap6.webp></div>


##### JDK1.7 ReHash

HashMap的容量是有限的。当经过多次元素插入，使得HashMap达到一定饱和度时，Key映射位置发生冲突的几率会逐渐提高。这时候，HashMap需要扩展它的长度，也就是进行Resize。

<div align=center><img src=DataStructure\HashMap6.png width=70%></div>

影响发生Resize的因素有两个：

1. Capacity：HashMap的当前长度。HashMap的长度是2的幂。
2. LoadFactor：HashMap负载因子，**默认值为0.75f**。

衡量HashMap是否进行Resize的条件为：`HashMap.Size >= Capacity * LoadFactor`

HashMap的Resize不是简单地把长度扩大，而是经过下面两个步骤：

1. 扩容：创建一个新的Entry空数组，**长度是原数组的2倍**。
2. ReHash：遍历原Entry数组，把所有的Entry重新Hash到新数组。

**JDK1.7在扩容后，需按照原来方法重新计算**，即`hashCode()->> 扰动处理 ->>(h & length-1)`！

为什么要重新Hash呢？因为**长度扩大以后，Hash的规则也随之改变**。

回顾一下Hash公式：`index = HashCode(Key) & (Length - 1)`(JDK1.7)，当原数组长度为8时，Hash运算是和111B做与运算；新数组长度为16，Hash运算是和1111B做与运算。Hash结果显然不同。

Resize前的HashMap：

<div align=center><img src=DataStructure\HashMap7.png width=70%></div>

Resize后的HashMap：

<div align=center><img src=DataStructure\HashMap8.png width=70%></div>


**高并发环境下**，JDK1.7中HashMap可能出现的致命问题：

假设一个HashMap已经到了Resize的临界点。此时有两个线程A和B，在同一时刻对HashMap进行Put操作：

<div align=center><img src=DataStructure\HashMap25.jpg></div>

此时达到Resize条件，两个线程各自进行Rezie的第一步，也就是扩容：

<div align=center><img src=DataStructure\HashMap26.png width=70%></div>

这时候，两个线程都走到了ReHash的步骤：

```java {.line-numbers highlight=8}
/**
 * Transfers all entries from current table to newTable.
 */
void transfer(Entry[] newTable, boolean rehash) {
    int newCapacity = newTable.length;
    for (Entry<K,V> e : table) {
        while(null != e) {
            Entry<K,V> next = e.next;
            if (rehash) {
                e.hash = null == e.key ? 0 : hash(e.key);
            }
            int i = indexFor(e.hash, newCapacity);
            e.next = newTable[i];
            newTable[i] = e;
            e = next;
        }
    }
}
```

假如此时线程B遍历到Entry3对象，刚执行完`Entry<K,V> next = e.next;`这行代码，线程就被挂起。对于线程B来说：`e = Entry3; next = Entry2`。

这时候线程A畅通无阻地进行着Rehash，当ReHash完成后，结果如下（图中的e和next，代表线程B的两个引用）：
<div align=center><img src=DataStructure\HashMap27.png width=70%></div>

直到这一步，还没出现问题。

接下来线程B恢复，继续执行属于它自己的ReHash。线程B刚才的状态是：`e = Entry3; next = Entry2`。

```java {.line-numbers highlight=12}
/**
 * Transfers all entries from current table to newTable.
 */
void transfer(Entry[] newTable, boolean rehash) {
    int newCapacity = newTable.length;
    for (Entry<K,V> e : table) {
        while(null != e) {
            Entry<K,V> next = e.next;
            if (rehash) {
                e.hash = null == e.key ? 0 : hash(e.key);
            }
            int i = indexFor(e.hash, newCapacity);
            e.next = newTable[i];
            newTable[i] = e;
            e = next;
        }
    }
}
```
当执行到`int i = indexFor(e.hash, newCapacity);`这一行时，显然`i = 3`，因为刚才线程A对于Entry3的hash结果也是3。

继续执行`newTable[i] = e; e = next;`，Entry3放入了线程B的数组下标为3的位置，并且e指向了Entry2。此时e和next的指向如下：`e = Entry2; next = Entry2`

<div align=center><img src=DataStructure\HashMap28.png width=70%></div>

接着是新一轮循环，又执行到`Entry<K,V> next = e.next;`代码行，此时`e = Entry2; next = Entry3`：

<div align=center><img src=DataStructure\HashMap29.png width=70%></div>

接下来执行`e.next = newTable[i]; newTable[i] = e; e = next;`的三行，用头插法把Entry2插入到了线程B的数组的头结点：

<div align=center><img src=DataStructure\HashMap30.png width=70%></div>

第三次循环开始，又执行到`Entry<K,V> next = e.next;`，此时`e = Entry3; next = Entry3.next = null`。

最后一步，当我们执行`e.next = newTable[i];`这一行的时候：
```
newTable[i] = Entry2
e = Entry3
Entry2.next = Entry3
Entry3.next = Entry2
```
**链表出现了环形**：

<div align=center><img src=DataStructure\HashMap31.png width=70%></div>

此时，问题还没有直接产生。**当调用Get查找一个不存在的Key**，而这个Key的Hash结果恰好等于3的时候，由于位置3带有环形链表，所以程序将会**进入死循环**！

##### JDK1.8扩容机制

什么场景下会触发扩容？

- 场景1：哈希table为null或长度为0；
- 场景2：Map中存储的k-v对数量超过了阈值threshold；
- 场景3：链表中的长度超过了TREEIFY_THRESHOLD，但表长度却小于MIN_TREEIFY_CAPACITY。



在HashMap中，桶数组的长度均是2的幂，`阈值大小`为`桶数组长度与负载因子的乘积`。当HashMap中的**键值对数量超过阈值时**，进行扩容。

<div align=center><img src=DataStructure\HashMap7.webp></div>

`h & (length-1)`
<div align=center><img src=DataStructure\HashMap22.jpg></div>


我们使用的是2次幂的扩展(指**长度扩为原来2倍**)，所以，**元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置**。看下图可以明白这句话的意思，n为table的长度，图（a）表示**扩容前**的key1和key2两种key确定索引位置的示例，图（b）表示**扩容后**key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果：

<div align=center><img src=DataStructure\HashMap10.png></div>

元素在重新计算hash之后，**因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)**，因此新的index就会发生这样的变化：

<div align=center><img src=DataStructure\HashMap11.png></div>

<div align=center><img src=DataStructure\HashMap13.png></div>

因此，JDK1.8在扩充HashMap的时候，不需要像JDK1.7的实现那样**重新计算hash**，只需要看看**原来的hash值新增的那个bit是1还是0**就好了，**是0的话索引没变，是1的话索引变成“原索引+扩容前旧容量”**。

可以看看下图为16扩充为32的resize示意图：

<div align=center><img src=DataStructure\HashMap12.png></div>

这个设计确实非常的巧妙，既**省去了重新计算hash值的时间**，而且同时，**由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，均匀的把之前的冲突的节点分散到新的bucket了**。这一块就是JDK1.8新增的优化点。有一点注意区别，JDK1.7中rehash的时候，旧链表迁移新链表的时候，如果在新表的数组索引位置相同，则**链表元素会倒置**，但是从上图可以看出，JDK1.8不会倒置。

```java
    /**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * 该函数有2种使用情况：1.初始化哈希表 2.当前数组容量过小，需扩容
     *
     * @return the table
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;  // 扩容前的数组（当前数组）
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;  // 扩容前的数组的阈值
        int newCap, newThr = 0;
        if (oldCap > 0) {
            // 当table容量超过容量最大值，则不再扩容
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            // 按旧容量和阈值的2倍计算新容量和阈值的大小
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        // 针对情况1：初始化哈希表（采用指定 or 默认值）
        else if (oldThr > 0) // initial capacity was placed in threshold
            // 初始化时，将threshold的值赋值给 newCap
            // HashMap使用threshold变量暂时保存initialCapacity参数的值
            newCap = oldThr;
        else {      // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        // 计算新的resize上限
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;

        // 创建新的桶数组，桶数组的初始化也是在这里完成的
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        // 如果旧的桶数组不为空，则遍历桶数组，并将键值对映射到新的桶数组中
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                    // 重新映射时，需要对红黑树进行拆分
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        // 遍历链表，并将链表节点按原顺序进行分组
                        do {
                            next = e.next;
                            // 原索引
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            // 原索引 + oldCap
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        // 原索引放到bucket里
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        // 原索引+oldCap放到bucket里
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

上面的源码总共做了3件事，分别是：
- 计算新桶数组的容量newCap和新阈值newThr
- 根据计算出的newCap创建新的桶数组，桶数组table也是在这里进行初始化的
- 将键值对节点重新映射到新的桶数组里。如果节点是TreeNode类型，则需要拆分红黑树。如果是普通节点，则节点按原顺序进行分组。


**扩容流程对比图：**

<div align=center><img src=DataStructure\HashMap14.png></div>

为什么在**JDK1.7**的时候是**先进行扩容后进行插入**，而在**JDK1.8**的时候则是**先插入后进行扩容**的呢？

在JDK1.8后该操作相当于对HashMap的优化，**当插入后，发现发生hash冲突则扩容，没有发生hash冲突则不扩容**。在JDK1.8之前，扩容是先达到阈值时，当下一个数据需要插入，则直接扩容，但是当插入的数据没有发生hash冲突则也会进行扩容操作，会产生无效扩容，所有1.8之后使用先插入后扩容可以减少一次无用的扩容，减少内存的使用


### 1.7和1.8中HashMap的区别

<div align=left><img src=DataStructure\HashMap18.jpg></div>

**参考资料：**
https://blog.csdn.net/qq_36520235/article/details/82417949


1. JDK1.7用的是**头插法**，而JDK1.8及之后使用的都是**尾插法**。
   
   那么他们为什么要这样做呢？
   因为JDK1.7是用单链表进行的纵向延伸，当采用头插法时会容易出现**逆序且环形链表死循环**问题。但是在JDK1.8之后是因为加入了**红黑树**使用尾插法，能够避免出现逆序且链表死循环的问题。

2. **扩容后数据存储位置的计算方式**也不一样：
   - 在JDK1.7的时候是**直接用hash值和需要扩容的二进制数进行&**（这里就是为什么扩容的时候为啥一定必须是2的幂次的原因所在，因为如果只有2的n次幂的情况时最后一位二进制数才一定是1，这样能**最大程度减少hash碰撞**）（`hash值 & length-1`）；
   - 而在JDK1.8的时候直接用了JDK1.7的时候计算的规律，却简化成**扩容前的原始位置+扩容的大小值=JDK1.8的计算方式**。这种方式相当于**只需要判断Hash值的新增参与运算的位是0还是1**就直接迅速计算出了扩容后的储存方式。
3. JDK1.7的时候使用的是**数组+单链表**的**数据结构**。但是在JDK1.8及之后时，使用的是**数组+链表+红黑树**的数据结构（当链表的深度达到8的时候，也就是默认阈值，就会**自动扩容把链表转成红黑树的数据结构**来把时间复杂度从$O(n)$变成$O(logN)$提高了效率）

**数据结构的区别：**
<div align=center><img src=DataStructure\HashMap5.webp></div>

**插入数据的区别：**
<div align=center><img src=DataStructure\HashMap.webp></div>

**扩容机制的区别：**
<div align=center><img src=DataStructure\HashMap9.webp></div>


### HashMap小结

#### 哈希表如何解决Hash冲突

<div align=center><img src=DataStructure\HashMap23.jpg></div>

#### HashMap具备的特点

**键-值(key-value)都允许为空、线程不安全、不保证有序、存储位置随时间变化**：

<div align=center><img src=DataStructure\HashMap24.jpg></div>

##### 线程不安全

**数据覆盖问题**

两个线程执行put()操作时，可能导致数据覆盖。JDK7版本和JDK8版本的都存在此问题，这里以JDK7为例。

假设A、B两个线程同时执行put()操作，且两个key都指向同一个buekct，那么此时两个结点，都会做头插法。

```java
public V put(K key, V value) {
    ...
    addEntry(hash, key, value, i);
}


void addEntry(int hash, K key, V value, int bucketIndex) {
    ...
    createEntry(hash, key, value, bucketIndex);
}


void createEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    size++;
}
```

`createEntry()`方法首先**获取到了bucket上的头结点，然后再将新结点作为bucket的头部，并指向旧的头结点，完成一次头插法的操作**。

当线程A和线程B都获取到了bucket的头结点后，若此时线程A的时间片用完，线程B将其新数据完成了头插法操作，此时轮到线程A操作，但这时线程A所据有的旧头结点已经过时了（并未包含线程B刚插入的新结点），线程A再做头插法操作，就会抹掉B刚刚新增的结点，导致数据丢失。

其实不光是put()操作，删除操作、修改操作，同样都会有覆盖问题。

**扩容时导致死循环**

**在并发插入元素的时候，有可能出现带环链表，让下一次读操作出现死循环**！


下面主要讲解**JDK1.7**中HashMap线程不安全的其中一个重要原因：**多线程下容易出现resize()死循环**！

JDK1.7能造成死循环，就是因为**resize()时使用了头插法，将原本的顺序做了反转**，才留下了死循环的机会。

本质是**并发执行put()操作导致触发扩容行为**，从而导致**环形链表**，使得在获取数据遍历链表时形成**死循环**，即Infinite Loop。

```java {.line-numbers highlight=24}
  /**
   * JDK1.7
   * 源码分析：resize(2 * table.length)
   * 作用：当容量不足时（容量 > 阈值），则扩容（扩到2倍）
   */ 
   void resize(int newCapacity) {  
    
    // 1. 保存旧数组（old table） 
    Entry[] oldTable = table;  

    // 2. 保存旧容量（old capacity ），即数组长度
    int oldCapacity = oldTable.length; 

    // 3. 若旧容量已经是系统默认最大容量了，那么将阈值设置成整型的最大值，退出    
    if (oldCapacity == MAXIMUM_CAPACITY) {  
        threshold = Integer.MAX_VALUE;  
        return;  
    }  
  
    // 4. 根据新容量（2倍容量）新建1个数组，即新table  
    Entry[] newTable = new Entry[newCapacity];  

    // 5. （重点分析）将旧数组上的数据（键值对）转移到新table中，从而完成扩容->>分析1.1 
    transfer(newTable); 

    // 6. 新数组table引用到HashMap的table属性上
    table = newTable;  

    // 7. 重新设置阈值  
    threshold = (int)(newCapacity * loadFactor); 
} 

  /**
   * 分析1.1：transfer(newTable); 
   * 作用：将旧数组上的数据（键值对）转移到新table中，从而完成扩容
   * 过程：按旧链表的正序遍历链表、在新链表的头部依次插入
   */ 
void transfer(Entry[] newTable) {
      // 1. src引用了旧数组
      Entry[] src = table; 

      // 2. 获取新数组的大小 = 获取新容量大小                 
      int newCapacity = newTable.length;

      // 3. 通过遍历 旧数组，将旧数组上的数据（键值对）转移到新数组中
      for (int j = 0; j < src.length; j++) { 
          // 3.1 取得旧数组的每个元素  
          Entry<K,V> e = src[j];           
          if (e != null) {
              // 3.2 释放旧数组的对象引用（for循环后，旧数组不再引用任何对象）
              src[j] = null; 

              do { 
                  // 3.3 遍历 以该数组元素为首 的链表
                  // 注：转移链表时，因是单链表，故要保存下1个结点，否则转移后链表会断开
                  Entry<K,V> next = e.next; 
                 // 3.3 重新计算每个元素的存储位置
                 int i = indexFor(e.hash, newCapacity); 
                 // 3.4 将元素放在数组上：采用单链表的头插入方式 = 在链表头上存放数据 = 将数组位置的原有数据放在后1个指针、将需放入的数据放到数组位置中
                 // 即 扩容后，可能出现逆序：按旧链表的正序遍历链表、在新链表的头部依次插入
                 e.next = newTable[i]; 
                 newTable[i] = e;  
                 // 访问下1个Entry链上的元素，如此不断循环，直到遍历完该链表上的所有节点
                 e = next;             
             } while (e != null);
             // 如此不断循环，直到遍历完数组上的所有数据元素
         }
     }
 }
```

从上面可看出：在扩容resize()过程中，在将旧数组上的数据转移到新数组上时，转移数据操作为**按旧链表的正序遍历链表、在新链表的头部依次插入**，即在转移数据、扩容后，容易出现**链表逆序**的情况。

假设JDK1.7中`reHash`重新计算存储位置后不变，即扩容前为1->2->3，扩容后依然为3->2->1。此时若（多线程）**并发执行put()操作**，一旦出现扩容情况，则容易出现**环形链表**，从而**在获取数据、遍历链表时形成死循环(Infinite Loop)，即死锁的状态**。

<div align=center><img src=DataStructure\HashMap10.webp></div>

<div align=center><img src=DataStructure\HashMap11.webp></div>

<div align=center><img src=DataStructure\HashMap12.webp></div>


JDK1.7中的扩容代码片段：
```java
void transfer(Entry[] newTable, boolean rehash) {
    int newCapacity = newTable.length;
    for (Entry<K,V> e : table) {
        while(null != e) {
            Entry<K,V> next = e.next;
            if (rehash) {
                e.hash = null == e.key ? 0 : hash(e.key);
            }
            int i = indexFor(e.hash, newCapacity);
            e.next = newTable[i];
            newTable[i] = e;
            e = next;
        }
    }
}
```

其实就是简单的链表反转，再进一步简化的话，分为当`前结点e`，以及`下一个结点e.next`。我们以链表`a->b->c->null`为例，两个线程A和B，分别做扩容操作。

原表：

<div align=center><img src=DataStructure\HashMap13.webp width=70%></div>

线程A和B各自新增了一个新的哈希table，在线程A已做完扩容操作后，线程B才开始扩容。

此时**对于线程B来说**，当`前结点e`指向a结点，`下一个结点e.next`仍然指向b结点（此时在线程A的链表中，已经是c->b->a的顺序）。

**按照头插法，哈希表的bucket指向a结点，此时a结点成为线程B中链表的头结点**，如下图所示：

<div align=center><img src=DataStructure\HashMap15.webp></div>

a结点成为线程B中链表的头结点后，下一个结点e.next为b结点。

既然下一个结点e.next不为null，那么当前结点e就变成了b结点，下一个结点e.next变为a结点。

**继续执行头插法，将b变为链表的头结点，同时next指针指向旧的头节点a**，如下图：

<div align=center><img src=DataStructure\HashMap16.webp></div>

此时，下一个结点e.next为a节点，不为null，继续头插法。

指针后移，那么当前结点e就成为了a结点，下一个结点为null。将a结点作为线程B链表中的头结点，并将next指针指向原来的旧头结点b，如下图所示：

<div align=center><img src=DataStructure\HashMap17.webp></div>

此时，已形成**环链表**。同时下一个结点e.next为null，流程结束。


由于JDK1.8转移数据操作为**按旧链表的正序遍历链表、在新链表的尾部依次插入，所以不会出现链表 逆序、倒置的情况，故不容易出现环形链表的情况**。

但JDK1.8还是线程不安全，因为**无加同步锁保护**。


#### Key的类型

为什么HashMap中String、Integer这样的**包装类适合作为key键**？

<div align=center><img src=DataStructure\HashMap32.jpg></div>

HashMap中的**key若Object类型**，则需实现哪些方法？

<div align=center><img src=DataStructure\HashMap33.jpg></div>


### HashMap与HashTable的关系

<div align=center><img src=DataStructure\Map.webp width=70%></div>

**共同点：**

- 底层都是使用哈希表 + 链表的实现方式。

**区别：**

- 从层级结构上看，HashMap、HashTable有一个共用的Map接口。另外，HashTable还单独继承了一个抽象类Dictionary(已废弃)；
- HashTable线程安全，HashMap线程不安全；
- 初始值和扩容方式不同。HashTable的初始值为11，扩容为原大小的2*d+1。容量大小都采用奇数且为素数，且采用取模法，这种方式散列更均匀。但有个缺点就是对素数取模的性能较低（涉及到除法运算）；而HashTable的长度都是2的次幂，这种方式的取模都是直接做位运算，性能较好。
- HashMap的key、value都可为null，且value可多次为null，key多次为null时会覆盖。但HashTable的key、value都不可为null，否则直接NPE(NullPointException)。

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


## ConcurrentHashMap

想要避免HashMap的线程安全问题，可以改用`HashTable`或者`Collections.synchronizedMap`。但是，这两者有着共同的问题——性能！无论读操作还是写操作，它们都会给整个集合加锁，导致同一时间的其他操作因其而阻塞。

### JDK1.7

`static final class Segment<K,V> extends ReentrantLock implements Serializable`

Segment本身就相当于一个**HashMap对象**。同HashMap一样，**Segment包含一个HashEntry数组，数组中的每一个HashEntry既是一个键值对，也是一个链表的头节点**。

单一的Segment结构如下：

<div align=center><img src=DataStructure\ConcurrentHashMap.png width=40%></div>

像这样的Segment对象，在ConcurrentHashMap集合中有多少个呢？有$2^N$个，**共同保存在一个名为segments的数组当中**。

因此整个ConcurrentHashMap的结构如下：

<div align=center><img src=DataStructure\ConcurrentHashMap1.png width=60%></div>

**ConcurrentHashMap是一个二级哈希表，在一个总的哈希表下面，有若干个子哈希表**。

ConcurrentHashMap优势就是采用了**锁分段**技术，每一个Segment就相当于一个自治区，读写操作高度自治，Segment之间互不影响。

ConcurrentHashMap采用了分段锁技术，其中**Segment继承于ReentrantLock**。不会像HashTable那样**不管是put还是get操作都需要做同步处理**。理论上ConcurrentHashMap支持CurrencyLevel (Segment 数组数量)的线程并发。**每当一个线程占用锁访问一个Segment时，不会影响到其他的 Segment**。


#### 并发读写情形

**不同Segment的并发写入：**
<div align=center><img src=DataStructure\ConcurrentHashMap2.png width=60%></div>

不同Segment的写入是可以并发执行的。

**同一Segment的写读：**
<div align=center><img src=DataStructure\ConcurrentHashMap3.png width=60%></div>

同一Segment的写和读是可以并发执行的。

**同一Segment的并发写入：**
<div align=center><img src=DataStructure\ConcurrentHashMap4.png width=60%></div>

Segment的写入是需要上锁的，因此**对同一Segment的并发写入会被阻塞**。


由此可见，ConcurrentHashMap当中**每个Segment各自持有一把锁**。在保证线程安全的同时降低了锁的粒度，让并发操作效率更高。

#### get方法

1. 为输入的Key做Hash运算，得到hash值。
2. 通过hash值，定位到对应的Segment对象
3. 再次通过hash值，定位到Segment当中数组的具体位置。

ConcurrentHashMap的读需要二次定位：首先定位到Segment，然后再定位到Segment内的具体数组下标！

#### put方法

1. 为输入的Key做Hash运算，得到hash值。
2. 通过hash值，定位到对应的Segment对象
3. 获取可重入锁
4. 再次通过hash值，定位到Segment当中数组的具体位置。
5. 插入或覆盖HashEntry对象。
6. 释放锁。

ConcurrentHashMap的写需要二次定位：首先定位到Segment，然后再定位到Segment内的具体数组下标！

#### size方法

既然每一个Segment都各自加锁，那么在调用size方法时，如何解决一致性问题？

size方法的目的是**统计ConcurrentHashMap的总元素数量**，自然需要把各个Segment内部的元素数量汇总起来。但是，如果在统计Segment元素数量的过程中，已统计过的Segment瞬间插入新的元素，这时候该怎么办呢？

`线程A：size = 3 + 3 ？`

<div align=center><img src=DataStructure\ConcurrentHashMap5.png width=80%></div>

ConcurrentHashMap的size方法是一个嵌套循环，大体逻辑如下：

1. 遍历所有的Segment。
2. 把Segment的元素数量累加起来。
3. 把Segment的**修改次数累加起来**。
4. 判断所有Segment的总修改次数是否大于上一次的总修改次数。如果大于，说明统计过程中有修改，重新统计，尝试次数+1；如果不是。说明没有修改，统计结束。
5. 如果尝试次数超过阈值，则对每一个Segment加锁，再重新统计。
6. 再次判断所有Segment的总修改次数是否大于上一次的总修改次数。由于已经加锁，次数一定和上次相等。
7. 释放锁，统计结束。

为什么这样设计呢？

这种思想和乐观锁悲观锁的思想如出一辙。为了尽量不锁住所有Segment，首先乐观地假设Size过程中不会有修改。当尝试一定次数，才无奈转为悲观锁，锁住所有Segment保证强一致性。


### JDK1.8 


抛弃了原有的Segment分段锁，而采用了`CAS + synchronized`来保证并发安全性。也将1.7中存放数据的HashEntry改为Node，但作用都是相同的。其中的**`val, next`都用了volatile修饰，保证了可见性**。


## LinkedHashMap

- 底层是**散列表**和**双向链表**；
- 允许为`null`，**不同步**；
- 插入的顺序是有序的(底层链表致使有序)
- 装载因子和初始容量对LinkedHashMap影响是很大的


## TreeMap

- `TreeMap`实现了`NavigableMap`接口，而`NavigableMap`接口继承着`SortedMap`接口，致使我们的`TreeMap`是**有序**的！
- TreeMap底层是红黑树，它方法的时间复杂度都不会太高$log(n)$；
- 非同步
- 使用`Comparator`或者`Comparable`来比较`key`是否相等与排序的问题。
- key**不能为null**，为null为抛出NullPointException的。


## 示例

```java
import java.util.*;

public class TestMap
{
    public static void main(String[] args)
    {
        // Create a HashMap
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Smith", 30);
        hashMap.put("Anderson", 31);
        hashMap.put("Lewis", 29);
        hashMap.put("Cook", 29);

        System.out.println("Display entries in HashMap");
        System.out.println(hashMap + "\n");

        // Create a TreeMap from the preceding HashMap
        Map<String, Integer> treeMap = new TreeMap<>(hashMap);
        System.out.println("Display entries in ascending order of key");
        System.out.println(treeMap);

        // Create a LinkedHashMap
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("Smith", 30);
        linkedHashMap.put("Anderson", 31);
        linkedHashMap.put("Lewis", 29);
        linkedHashMap.put("Cook", 29);

        // Display the age for Lewis
        System.out.println("\nThe age for " + "Lewis is " + linkedHashMap.get("Lewis"));

        System.out.println("Display entries in LinkedHashMap");
        System.out.println(linkedHashMap);

        // Display each entry with name and age
        System.out.print("\nNames and ages are ");
        treeMap.forEach((name, age) -> System.out.print(name + ": " + age + " "));
  }
}
/*
Display entries in HashMap
{Lewis=29, Smith=30, Cook=29, Anderson=31}

Display entries in ascending order of key
{Anderson=31, Cook=29, Lewis=29, Smith=30}

The age for Lewis is 29
Display entries in LinkedHashMap
{Smith=30, Anderson=31, Cook=29, Lewis=29}

Names and ages are Anderson: 31 Cook: 29 Lewis: 29 Smith: 30 
 */
```

输出结果显示：`HashMap`中条目的顺序是**随机**的，而`TreeMap`中的条目是**按键的升序排列**的， `LinkedHashMap`中的条目则是按元素最后一次被访问的时间从早到晚排序的。

实现`Map`接口的所有具体类至少有两种构造方法： 一种是**无参构造方法**，它可用来创建一个**空映射表**，而另一种构造方法是**从Map的一个实例来创建映射表**。所以，语句`new TreeMap <String , Integer>(hashMap)`就是从一个散列映射表来创建一个树形映射表。

- 如果更新映射表时**不需要保持映射表中元素的顺序**，就使用`HashMap`;
- 如果需要保持映射表中元素的**插入顺序**或**访问顺序**，就使用`LinkedHashMap`; 
- 如果需要使映射表**按照键排序**，就使用`TreeMap`。

## 单词出现的次数

统计一个文本中单词的出现次数，然后按照单词的宇母顺序显示这些单词以及它们对应的出现次数。

本程序使用一个`TreeMap`来存储包含单词及其次数的条目。对于每一个单词来说，都要判断它是否已经是映射表中的一个键。如果不是，将由这个**单词为键**而**1为值**构成的条目存入该映射表中。否则，将映射表中该单词（键）对应的值加1。假定单词是不区分大小写的。

```java
import java.util.*;

public class CountOccurrenceOfWords
{
    public static void main(String[] args)
    {
        // Set text in a string
        String text = "Good morning. Have a good class. " +
                "Have a good visit. Have fun!";

        // Create a TreeMap to hold words as key and count as value
        Map<String, Integer> map = new TreeMap<>();

        String[] words = text.split("[\\s+\\p{P}]");
        for (int i = 0; i < words.length; i++)
        {
            String key = words[i].toLowerCase();

            if (key.length() > 0)
            {
                if (!map.containsKey(key))
                    map.put(key, 1);
                else
                    {
                        int value = map.get(key);
                        value++;
                        map.put(key, value);
                    }
            }
        }

        // Display key and value for each entry
        map.forEach((k, v) -> System.out.println(k + "\t" + v));
    }
}
/*
a	2
class	1
fun	1
good	3
have	3
morning	1
visit	1
 */
```


## 总结

<div align=center><img src=DataStructure\Map总结.jpg></div>


# 红黑树

要学习红黑树，需要先了解二叉查找树。

## 二叉查找树


一棵二叉查找树(BST)是一棵二叉树，其中每个结点都含有一个Comparable的键(以及相关联的值)且每个结点的键都**大于其左子树中的任意结点的键**，而**小于右子树的任意结点的键**。

画出二叉查找树时，会将键写在结点上，用键来指代结点：
<div align=center><img src=DataStructure\BST结构.jpg></div>

二叉查找树的特点：

- 左子树上所有结点的值均小于或等于它的根结点的值。
- 右子树上所有结点的值均大于或等于它的根结点的值。
- 左、右子树也分别为二叉排序树。

### 数据表示

嵌套定义了一个私有类来表示二叉查找树上的一个结点，每个结点都含有一个键、一个值、一条左链接、一条右链接和一个结点计数器：

```java
private class Node {
    private Key key;                     // 键
    private Value value;                 // 值
    private Node left, right;            // 指向子树的链接
    private int number;                  // 以该结点为根的子树中的结点总数

    public Node(Key key, Value value, int number) {
        this.key = key;
        this.value = value;
        this.number = number;
    }
}
```

一棵二叉查找树代表了一组键及其相应的值的集合，而同一个集合可以用多棵不同的二叉查找树来表示，将一棵二叉查找树的所有键投影到一条直线上，保证一个结点的左子树中的键出现在它的左边，右子树中的键出现在它的右边：

<div align=center><img src=DataStructure\两棵BST代表同一个集合.jpg></div>

### 查找

<div align=center><img src=DataStructure\查找.jpg></div>

查找的过程运用了二分查找的思想，查找所需的**最大次数**等于二叉查找树的高度。

```java
public Value get(Key key) {
    return get(root, key);
}

private Value get(Node node, Key key) {

    // 在以node为根结点的子树中查找，并返回key所对应的值
    if (node == null) {
        return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
        return get(node.left, key);
    } else if (cmp > 0) {
        return get(node.right, key);
    } else {
        return node.value;
    }
}
```

### 插入
<div align=center><img src=DataStructure\插入.jpg></div>

```java
    // 插入
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        node.number = size(node.left) + size(node.right) + 1;
        return node;

    }
```

### 删除

二叉查找树的删除分成三种情况：

**情况1，待删除的结点没有子节点：**

<div align=center><img src=DataStructure\二叉查找树删除.jpg width=50%></div>

上图中，待删除的节点12是叶子节点，没有孩子，因此直接删除即可：

<div align=center><img src=DataStructure\二叉查找树删除1.jpg width=50%></div>

**情况2，待删除的节点有一个孩子：**

<div align=center><img src=DataStructure\二叉查找树删除2.jpg width=50%></div>

待删除的节点13只有左孩子，于是我们让左孩子节点11取代被删除的节点，节点11以下的节点关系无需变动：

<div align=center><img src=DataStructure\二叉查找树删除3.jpg width=50%></div>

**情况3，待删除的结点有两个孩子：**

<div align=center><img src=DataStructure\二叉查找树删除4.jpg width=50%></div>

上图中，待删除的节点5有两个孩子，这种情况比较复杂。此时，我们需要选择与待删除节点最接近的节点来取代它。节点3仅小于节点5，节点6仅大于节点5，两者都是合适的选择。但习惯上我们选择仅大于待删除节点的节点，也就是节点6来取代它。

于是我们复制节点6到原来节点5的位置：

<div align=center><img src=DataStructure\二叉查找树删除5.jpg width=50%></div>

被选中的节点6，仅大于节点5，因此一定没有左孩子。所以我们按照情况1或情况2的方式，删除多余的节点6:

<div align=center><img src=DataStructure\二叉查找树删除6.jpg width=50%></div>


### 二叉查找树的缺陷

**二叉查找树的缺陷**体现在插入新节点的时候：

假设初始的二叉查找树只有三个节点，根节点值为9，左孩子值为8，右孩子值为12。接下来我们依次插入如下五个节点：7,6,5,4,3：

<div align=center><img src=DataStructure\二叉查找树的缺陷.jpg width=40%></div>

二叉查找树多次插入新节点可能会导致**不平衡**，此时查找的性能大打折扣，机会变成了线性查找。


## RedBlackTree规则

红黑树是一种自平衡的二叉查找树，除了符合二叉查找树的基本特性外，它还具备以下特点：

1. 根结点是黑色。
2. 结点是红色或黑色。
3. 每个叶子结点都是黑色的空结点（NIL结点）。
4. 每个红色结点的两个子结点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色结点)
5. 从任一结点到其每个叶子的所有路径都包含相同数目的黑色结点。

<div align=center><img src=DataStructure\红黑树.jpg></div>

正是因为这些规则限制，才保证了红黑树的自平衡。**红黑树从根节点到叶子的最长路径不会超过最短路径的2倍**。

当插入或删除节点时，红黑树的规则有可能被打破。这时候就需要做出一些调整，从而继续满足红黑树的规则。

## 红黑树调整方法

向原红黑树插入值为14的新节点，由于父节点15是黑色节点，因此这种情况并不会破坏红黑树的规则，无需做任何调整：

<div align=center><img src=DataStructure\红黑树1.jpg></div>

向原红黑树插入值为21的新节点，由于父节点22是红色节点，因此这种情况打破了红黑树的规则（每个红色结点的两个子结点都是黑色），必须进行调整，使之重新符合红黑树的规则：

<div align=center><img src=DataStructure\红黑树2.jpg></div>

调整的方法有两种：变色和旋转，而选装又包含两种方式：左旋转和右旋转。

### 变色

为了重新符合红黑树的规则，尝试把红色结点变为黑色，或者把黑色结点变为红色。

下图所表示的是红黑树的一部分（子树），新插入的节点Y是红色节点，它的父亲节点X也是红色的，不符合规则，因此可以把节点X从红色变成黑色：

<div align=center><img src=DataStructure\红黑树3.jpg></div>

但是，仅仅把一个节点变色，会导致相关路径凭空多出一个黑色节点，这样就打破了规则5。因此，需要对其他节点做进一步的调整。

### 左旋转

**逆时针**旋转红黑树的两个节点，使得**父节点被自己的右孩子取代，而自己成为自己的左孩子**。

<div align=center><img src=DataStructure\红黑树4.jpg width=70%></div>

### 右旋转

顺时针旋转红黑树的两个节点，使得父节点被自己的左孩子取代，而自己成为自己的右孩子。

<div align=center><img src=DataStructure\红黑树5.jpg width=70%></div>

### 插入新节点出现的情形

在红黑树插入新节点时，可以分为5种不同的情形：

**情形一：新节点（A）位于树根，没有父结点**

<div align=center><img src=DataStructure\红黑树6.jpg width=20%></div>

上图中，空心三角形代表节点下面的子树。

出现这种情形时，直接让新节点变色为黑色，规则1得到满足。同时，黑色的根节点使得每条路径上的黑色节点数目都增加了1，所以并没有打破规则5。

<div align=center><img src=DataStructure\红黑树7.jpg width=50%></div>

**情形二：新节点（B）的父节点是黑色**

出现这种情形时，新插入的红色结点B并没有打破红黑树的规则，所以不需要做任何调整。

<div align=center><img src=DataStructure\红黑树8.jpg width=20%></div>

**情形三：新节点D的父节点和叔叔节点都是红色**

<div align=center><img src=DataStructure\红黑树9.jpg width=30%></div>

出现这种情形时，两个红色节点B和D连续，违反了规则4。因此我们先让节点B变为黑色：

<div align=center><img src=DataStructure\红黑树10.jpg width=60%></div>

这样一来，节点B所在路径凭空多了一个黑色节点，打破了规则5。因此我们让节点A变为红色：

<div align=center><img src=DataStructure\红黑树11.jpg width=60%></div>

这时候，节点A和C又成为了连续的红色节点，我们再让节点C变为黑色：

<div align=center><img src=DataStructure\红黑树12.jpg width=60%></div>

经过上面的调整，**这一局部**重新符合了红黑树的规则。

**情形四：新节点（D）的父节点是红色，叔叔节点是黑色或者没有叔叔，且新节点是父节点的右孩子，父节点（B）是祖父节点的左孩子。**

<div align=center><img src=DataStructure\红黑树13.jpg width=30%></div>

我们以节点B为轴，做一次**左旋转**，使得新节点D成为父节点，原来的父节点B成为D的左孩子：

<div align=center><img src=DataStructure\红黑树14.jpg width=60%></div>

这样一来，就变成了情形五。

**情形五：新节点（D）的父节点是红色，叔叔节点是黑色或者没有叔叔，且新节点是父节点的左孩子，父节点（B）是祖父节点的左孩子。**

<div align=center><img src=DataStructure\红黑树15.jpg width=30%></div>

以结点A为轴，做一次右旋转，使得节点B成为祖父节点，节点A成为节点B的右孩子：

<div align=center><img src=DataStructure\红黑树16.jpg width=65%></div>

接下来，我们让节点B变为黑色，节点A变为红色：

<div align=center><img src=DataStructure\红黑树17.jpg width=65%></div>

经过上面的调整，**这一局部**重新符合了红黑树的规则。

如果情形4和情形5当中的父节点B是祖父节点A的右孩子该怎么办呢？

很简单，如果情形4中的父节点B是右孩子，则成为了情形5的镜像，原本的右旋操作改为左旋；如果情形5中的父节点B是右孩子，则成为了情形4的镜像，原本的左旋操作改为右旋。


### 红黑树插入示例

给定下面这颗红黑树，新插入的节点是21：

<div align=center><img src=DataStructure\红黑树18.jpg></div>

显然，新节点21和它的父节点22是连续的红色节点，违背了规则4，我们应该如何调整呢？

当前的情况符合情形3：“新结点的父结点和叔叔结点都是红色。”

于是经过三次变色，22变为黑色，25变为红色，27变为黑色：

<div align=center><img src=DataStructure\红黑树19.jpg></div>

经过上面的调整，以节点25为根的子树符合了红黑树规则，但节点25和节点17成为了连续的红色节点，违背规则4。

于是，我们把节点25看做一个新节点，正好符合情形5的镜像：“新节点的父节点是红色，叔叔节点是黑色或者没有叔叔，且新节点是父节点的右孩子，父节点是祖父节点的右孩子”

于是我们以根节点13为轴进行左旋转，使得节点17成为了新的根节点：

<div align=center><img src=DataStructure\红黑树20.jpg></div>

接下来，让节点17变为黑色，节点13变为红色：

<div align=center><img src=DataStructure\红黑树21.jpg></div>

如此一来，调整后的红黑树变得重新符合规则。



### 红黑树删除步骤

待删除的是黑色节点1，有一个右孩子：

<div align=center><img src=DataStructure\红黑树22.jpg></div>

根据二叉查找树的删除流程，让右孩子节点6直接取代结点1：

<div align=center><img src=DataStructure\红黑树23.jpg></div>

显然，这棵新的二叉树打破了两个规则：规则4.每个红色节点的两个子节点都是黑色。规则5.从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。


**第一步：如果待删除节点有两个非空的孩子节点，转化成待删除节点只有一个孩子（或没有孩子）的情况。**

<div align=center><img src=DataStructure\红黑树24.jpg width=30%></div>

上面例子是一颗红黑树的局部，标数字的三角形代表任意形态的子树，假设结点8是待删除节点。

根据二叉查找树删除流程，由于节点8有两个孩子，我们选择仅大于8的节点10复制到8的位置，节点颜色变成待删除节点的颜色：

<div align=center><img src=DataStructure\红黑树25.jpg width=30%></div>

接下来我们需要删除节点10。节点10能成为仅大于8的节点，必定没有左孩子节点，所以问题转换成了待删除节点只有一个右孩子（或没有孩子）的情况。接下来我们进入第二步。


**第二步：根据待删除节点和其唯一子节点的颜色，分情况处理。**

- **情况1，自身是红色，子节点是黑色：**

<div align=center><img src=DataStructure\红黑树26.jpg width=20%></div>

这种情况最简单，按照二叉查找树的删除操作，删除节点1即可：

<div align=center><img src=DataStructure\红黑树27.jpg width=15%></div>

- **情况2，自身是黑色，子节点是红色：**

<div align=center><img src=DataStructure\红黑树28.jpg width=20%></div>

首先按照二叉查找树的删除操作，删除节点1。此时，这条路径凭空减少了一个黑色节点，那么我们把节点2变成黑色即可：

<div align=center><img src=DataStructure\红黑树27.jpg width=15%></div>

- **情况3，自身是黑色，子节点也是黑色，或者子节点是空叶子节点：**

<div align=center><img src=DataStructure\红黑树29.jpg width=20%></div>

这种情况最复杂，涉及到很多变化。首先我们还是按照二叉查找树的删除操作，删除结点1：

<div align=center><img src=DataStructure\红黑树27.jpg width=15%></div>

显然，这条路径上减少了一个黑色节点，而且节点2再怎么变色也解决不了。

这时候我们进入第三步，专门**解决父子双黑的情况**。

**第三步：遇到双黑节点，在子节点顶替父节点之后，分成6种子情况处理。**

- **子情况1，节点2是红黑树的根节点：**

<div align=center><img src=DataStructure\红黑树30.jpg width=20%></div>

此时所有路径都减少了一个黑色节点，并未打破规则，不需要调整。

- **子情况2，节点2的父亲、兄弟、侄子节点都是黑色：**

<div align=center><img src=DataStructure\红黑树31.jpg width=40%></div>

此时，我们直接把节点2的兄弟节点B改为红色：

<div align=center><img src=DataStructure\红黑树32.jpg width=40%></div>

这样一来，原本节点2所在的路径少了一个黑色节点，现在节点B所在的路径也少了一个黑色节点，两边“扯平”了。可是，**节点A以下的每一条路径都减少了一个黑色节点，与节点A之外的其他路径又造成了新的不平衡**！

此时，让节点A扮演原先节点2的角色，进行递归操作，重新判断各种情况。


- **子情况3，节点2的兄弟节点是红色：**

<div align=center><img src=DataStructure\红黑树32.jpg width=40%></div>

首先以节点2的父节点A为轴，进行左旋：

<div align=center><img src=DataStructure\红黑树33.jpg width=40%></div>

然后节点A变成红色、节点B变成黑色：

<div align=center><img src=DataStructure\红黑树34.jpg width=40%></div>

这样的意义是什么呢？节点2所在的路径仍然少一个黑色节点呀？

这样的变化有可能转换成子情况4、5、6中的任意一种，在子情况4、5、6当中会进一步解决。

- **子情况4，节点2的父节点是红色，兄弟和侄子节点是黑色：**

<div align=center><img src=DataStructure\红黑树35.jpg width=40%></div>

遇到这种情况，我们直接让节点2的父节点A变成黑色，兄弟节点B变成红色：

<div align=center><img src=DataStructure\红黑树36.jpg width=40%></div>

这样一来，节点2的路径补充了黑色节点，而节点B的路径并没有减少黑色节点，重新符合了红黑树的规则。

- **子情况5，节点2的父节点随意，兄弟节点B是黑色右孩子，左侄子节点是红色，右侄子节点是黑色：**

<div align=center><img src=DataStructure\红黑树37.jpg width=40%></div>

这种情况下，首先以节点2的兄弟节点B为轴进行右旋：

<div align=center><img src=DataStructure\红黑树38.jpg width=40%></div>

接下来节点B变为红色，节点C变为黑色：

<div align=center><img src=DataStructure\红黑树39.jpg width=40%></div>

这样的变化转换成了子情况6。

- **子情况6，节点2的父节点随意，兄弟节点B是黑色右孩子，右侄子节点是红色：**

<div align=center><img src=DataStructure\红黑树40.jpg width=40%></div>

首先以节点2的父节点A为轴左旋：

<div align=center><img src=DataStructure\红黑树41.jpg width=40%></div>

接下来让节点A和节点B的颜色交换，并且节点D变为黑色：

<div align=center><img src=DataStructure\红黑树42.jpg width=40%></div>

经过结点2的路径由（随意+黑）变成了（随意+黑+黑），补充了一个黑色结点；

经过结点D的路径由（随意+黑+红）变成了（随意+黑），黑色结点并没有减少。

所以，这时候重新符合了红黑树的规则。


### 红黑树删除示例

给定下面这颗红黑树，待删除的是节点17：

<div align=center><img src=DataStructure\红黑树43.jpg width=70%></div>

第一步，由于节点17有两个孩子，子树当中仅大于17的节点是25，所以把节点25复制到17位置，保持黑色：

<div align=center><img src=DataStructure\红黑树44.jpg width=70%></div>

接下来，我们需要删除原本的节点25。这个情况正好对应于第二步的情况三，即待删除节点是黑色，子节点是空叶子节点。

于是我们删除原来的节点25，进入第三步：

<div align=center><img src=DataStructure\红黑树45.jpg width=70%></div>

此时，框框中的节点虽然是空叶子节点，但仍然可以用于判断局面，当前局面符合子情况5的镜像：

<div align=center><img src=DataStructure\红黑树46.jpg width=70%></div>

<div align=center><img src=DataStructure\红黑树47.jpg width=70%></div>

于是我们通过左旋和变色，把子树转换成情况6的镜像：

<div align=center><img src=DataStructure\红黑树48.jpg width=70%></div>

再经过右旋、变色，子树最终成为了下面的样子：

<div align=center><img src=DataStructure\红黑树49.jpg width=70%></div>


## AVL树与红黑树的区别

AVL树也是一种自平衡的二叉树，它和红黑树有什么差别呢？

AVL树是严格平衡的二叉树，要求每个节点的左右子树高度差不超过1；而红黑树要宽松一些，要求任何一条；路径的长度不超过其他路径长度的2倍。

正是因为这个差别，**AVL树的查找效率更高，但平衡调整的成本也更高**。在需要**频繁查找**时，选用AVL树更合适。在需要**频繁插入删除**时，选用红黑树更合适！


