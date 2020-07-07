# 合集

数据结构(data structure) 是以某种形式将数据组织在一起的`合集`(collection) 。数据结构不仅存储数据，还支持访问和处理数据的操作。

在面向对象思想里，一种数据结构也被认为是一个`容器`(container) 或者`容器对象`(container object)，它是一个能**存储其他对象**的对象，这里的其他对象常称为数据或者元素。

## 为什么需要合集及其与数组的区别

Java是一门面向对象的语言，就免不了**处理对象**；为了方便操作多个对象，那么我们就得把这多个对象存储起来；想要**存储多个对象**(变量)，很容易就能想到一个**容器**；常用的容器有`StringBuffered`，`数组`(虽然有对象数组，但是数组的长度是不可变的！)；所以，Java就为我们提供了合集(`Collection`)。

数组和合集的区别:

- 长度的区别
  - 数组的长度固定；
  - 集合的长度可变。

- 元素的数据类型
  - 数组可以存储**基本数据类型**，也可以存储**引用类型**；
  - **集合只能存储引用类型**(你存储的是简单的`int`，它会**自动装箱**成`Integer`)。


## Java合集框架

Java合集框架支持以下两种类型的容器：
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

`List`集合常用的子类有三个：
- ArrayList
    底层数据结构是**数组**。线程**不安全**。

- LinkedList
    底层数据结构是**链表**。线程**不安全**。

- Vector
    底层数据结构是**数组**。线程**安全**。

`Arraylist`和`Linkedlist`定义在`List`接口下。`List`接口继承`Collection`接口，定义了一个**允许重复**的**有序**合集。

<div align=center><img src=DataStructure\List.jpg width=90%></div>

`Collection`返回的是`Iterator`迭代器接口，而`List`中又有它自己对应的实现`ListIterator`接口。该接口比普通的Iterator接口多了几个方法：`previous(), add(E e), set(E e)`等。

方法`listIterator()`或`listIterator(startlndex)`都会返回`ListIterator`的一个实例。`ListIterator`接口继承了`Iterator`接口，以增加对线性表的双向遍历能力。

<div align=center><img src=DataStructure\ListIterator.jpg width=70%></div>


## 数组线性表类ArrayList

`ArrayList`用数组存储元素，这个数组是**动态创建**的。<font color=red>如果元素个数超过了数组的容量，就创建一个更大的新数组，并将当前数组中的所有元素都复制到新数组中</font>。`LinkedList`在一个链表(linked list)中存储元素。

要选用这两种类中的哪一个，依赖于特定需求。如果需要通过**下标随机访问元素**，而不会在线性表起始位置插入或删除元素，那么**ArrayList**提供了最高效率的合集。但是，如果应用程序需要在线性表的**起始位置上插入或删除元素**，就应该选择`LinkedList`类。

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

<div align=center><img src=DataStructure\ArrayList.png></div>

**小结：**
- `ArrayList`是基于**动态数组**实现的，在增删时候，需要数组的**拷贝复制**(`navite`方法由C/C++实现)。
- `ArrayList`的默认初始化容量是10，每次扩容时候增加原先容量的一半，也就是变为原来的**1.5倍**。
- 删除元素时不会减少容量，若希望减少容量则调用`trimToSize()`。
- 它**不是线程安全的**。它能存放`null`值。


## 链表类LinkedList

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

## 向量类和栈类

Java合集框架是在Java 2中引入的。Java 2之前的版本也支持一些数据结构，其中就有`向量类Vector`与`栈类Stack`。为了适应Java合集框架，Java 2对这些类进行了重新设计，但是为了向后兼容，保留了它们所有的以前形式的方法。

除了包含用于**访问**和**修改**向量的**同步方法**之外， Vector类与Arraylist是一样的。同步方法用于防止两个或多个线程同时访问和修改某个向量时引起数据损坏。<font color=red>对于许多不需要同步的应用程序来说，使用Arraylist比使用Vector效率更高</font>。

<div align=center><img src=DataStructure\Vector.jpg width=80%></div>

方法`elements()`返回一个`Enumeration`对象（枚举型对象）。`Enumeration`接口是在Java 2之前引入的，已经被`Iterator`接口所取代。

在Java合集框架中，栈类Stack是作为Vector类的扩展来实现的：
<div align=center><img src=DataStructure\Stack.jpg width=70%></div>

### Vector与ArrayList区别

- `Vector`底层也是数组，与`ArrayLis`t最大的区别就是：同步(**线程安全**)。如果想要`ArrayList`实现同步，可以使用`Collections`的方法：`List list = Collections.synchronizedList(new ArrayList(...));`，就可以实现同步。

- ArrayList在底层数组不够用时在原来的基础上扩展`0.5`倍，Vector是扩展`1`倍。


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

**`Comparable`用于比较实现`Comparable`的类的对象；`Comparator`用于比较没有实现`Comparable`的类的对象**。

使用`Comparable`接口来比较元素称为使用**自然顺序**(natural order)进行比较，使用`Comparator`接口来比较元素被称为使用**比较器**来进行比较。

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

队列(queue)是一种先进先出的数据结构。元素被追加到队列末尾，然后从队列头删除。在优先队列(priority queue)中，元素被赋予优先级。<font color=red>当访问元素时，拥有最高优先级的元素首先被删除</font>。

<div align=center><img src=DataStructure\Queue.jpg width=70%></div>


## 双端队列Deque和链表LinkedList

<div align=center><img src=DataStructure\Collection.jpg></div>

<div align=center><img src=DataStructure\Linkedlist实现了List和Deque.jpg width=50%></div>

Linkedlist类实现了Deque接口，Deque又继承自Queue接口。因此，**可以使用LinkedList创建一个队列**。LinkedList很适合用于进行队列操作，因为它可以高效地在线性表的两端插入和移除元素。

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


# 集合Set

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

<div align=center><img src=DataStructure\HashMap.png></div>

**小结：**
- 无序，允许为`null`，**非同步**；
- 底层由**散列表(哈希表)**实现（Java中散列表的实现是通过**数组+链表**）；
- 初始容量和装载因子对HashMap影响较大，需适中。
- **不需要线程安全**的场合可以用`HashMap`，需要线程安全的场合可以用`ConcurrentHashMap`。

在JDK8中HashMap的底层是：**<u>数组+链表</u>(散列表)+红黑树**；

在散列表中有**装载因子**这么一个属性，当`装载因子*初始容量`小于散列表元素时，该散列表会再散列，扩容`2`倍！

装载因子的默认值是`0.75`，无论是初始大了还是初始小了对我们HashMap的性能都不好：
- 装载因子初始值大了，可以减少散列表再散列(扩容的次数)，但同时会导致散列冲突的可能性变大(散列冲突也是耗性能的一个操作，要得操作链表(红黑树)！
- 装载因子初始值小了，可以减小散列冲突的可能性，但同时扩容的次数可能就会变多！

初始容量的默认值是`16`，它也一样，无论初始大了还是小了，对我们的HashMap都是有影响的：

- 初始容量过大，那么遍历时速度就会受影响；
- 初始容量过小，散列表再散列(扩容的次数)可能就变得多，扩容也是一件非常耗费性能的一件事；


HashMap数组每一个元素的初始值都是`Null`：

<div align=center><img src=DataStructure\HashMap1.png width=70%></div>

对于HashMap，我们最常使用的是两个方法：Get和Put。

### Put方法的原理

调用Put方法的时候发生了什么呢？

比如调用`hashMap.put("apple", 0)`，插入一个Key为“apple"的元素。这时候我们需要利用一个**哈希函数**来确定Entry的插入位置（index）：`index = Hash("apple")`。假定最后计算出的index是2，那么结果如下：

<div align=center><img src=DataStructure\HashMap2.png width=70%></div>

但是，因为HashMap的长度是有限的，当插入的Entry越来越多时，再完美的Hash函数也难免会**出现index冲突**的情况。比如下面这样：

<div align=center><img src=DataStructure\HashMap3.jpg width=70%></div>

这时候该怎么办呢？

我们可以利用**链表**来解决。

**HashMap数组的每一个元素不止是一个Entry对象，也是一个链表的头节点**。每一个Entry对象通过Next指针指向它的下一个Entry节点。**当新来的Entry映射到冲突的数组位置时，只需要插入到对应的链表即可**：

<div align=center><img src=DataStructure\HashMap4.png width=70%></div>

新来的Entry节点插入链表时，使用的是**头插法**。

### Get方法的原理：

使用Get方法根据Key来查找Value的时候，发生了什么呢？

首先会**把输入的Key做一次Hash映射，得到对应的index**：`index = Hash("apple")`，由于**Hash冲突，同一个位置有可能匹配到多个Entry**，这时候就需要顺着对应链表的头节点，一个一个向下来查找。假设我们要查找的Key是“apple”：

<div align=center><img src=DataStructure\HashMap5.jpg width=70%></div>

第一步，我们查看的是头节点Entry6，Entry6的Key是banana，显然不是我们要找的结果。

第二步，我们查看的是Next节点Entry1，Entry1的Key是apple，正是我们要找的结果。

之所以**把Entry6放在头节点**，是因为HashMap的发明者认为，**后插入的Entry被查找的可能性更大**。


### 默认初始长度

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


### ReHash

HashMap的容量是有限的。当经过多次元素插入，使得HashMap达到一定饱和度时，Key映射位置发生冲突的几率会逐渐提高。这时候，HashMap需要扩展它的长度，也就是进行Resize。

<div align=center><img src=DataStructure\HashMap6.png width=70%></div>

影响发生Resize的因素有两个：

1. Capacity：HashMap的当前长度。HashMap的长度是2的幂。
2. LoadFactor：HashMap负载因子，**默认值为0.75f**。

衡量HashMap是否进行Resize的条件为：`HashMap.Size >= Capacity * LoadFactor`

HashMap的Resize不是简单地把长度扩大，而是经过下面两个步骤：

1. 扩容：创建一个新的Entry空数组，长度是原数组的2倍。
2. ReHash：遍历原Entry数组，把所有的Entry重新Hash到新数组。

为什么要重新Hash呢？因为**长度扩大以后，Hash的规则也随之改变**。

回顾一下Hash公式：`index = HashCode(Key) & (Length - 1)`，当原数组长度为8时，Hash运算是和111B做与运算；新数组长度为16，Hash运算是和1111B做与运算。Hash结果显然不同。

Resize前的HashMap：

<div align=center><img src=DataStructure\HashMap7.png width=70%></div>

Resize后的HashMap：

<div align=center><img src=DataStructure\HashMap8.png width=70%></div>

**ReHash的Java代码如下：**

```java
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

### 高并发下的HashMap

高并发环境下，HashMap可能出现致命问题。






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



