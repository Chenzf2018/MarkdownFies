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

参考资料：
https://www.cnblogs.com/duodushuduokanbao/p/9492952.html
https://www.jianshu.com/p/8324a34577a0

<div align=center><img src=DataStructure\HashMap.png></div>

`public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable`

<div align=center><img src=DataStructure\HashMap17.jpg></div>

### 概述

HashMap存储的是key-value的键值对，允许key为null，也允许value为null。
HashMap内部为数组+链表的结构，会**根据key的hashCode值来确定数组的索引**(确认放在哪个桶里)，如果遇到索引相同的key，桶的大小是2，假设一个key的hashCode是7，一个key的hashCode是3，那么他们就会被分到一个桶中(hash冲突)。**如果发生hash冲突，HashMap会将同一个桶中的数据以链表的形式存储**，但是如果发生hash冲突的概率比较高，就会导致**同一个桶中的链表长度过长**，遍历效率降低，所以在JDK1.8中**如果链表长度到达阀值(默认是8)，就会将链表转换成红黑二叉树**。


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

有时候两个key的hashCode可能会定位到一个桶中，这时就发生了**hash冲突**，如果HashMap的**hash算法越散列**，那么发生hash冲突的概率越低；如果数组越大，那么发生hash冲突的概率也会越低，但是数组越大带来的**空间开销越多**，但是遍历速度快。这就要在空间和时间上进行权衡，这就要看看HashMap的扩容机制，在说扩容机制之前先看几个比较重要的字段：

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


```

`threshold = 负载因子 * length`，也就是说数组长度固定以后，如果负载因子越大，所能容纳的元素个数越多，如果超过这个值就会进行扩容(默认是扩容为原来的2倍)，**0.75这个值是权衡过空间和时间得出的**，建议大家不要随意修改，如果在一些特殊情况下，比如空间比较多，但要求速度比较快，这时候就可以把扩容因子调小以较少hash冲突的概率。相反就增大扩容因子(这个值可以大于1)。

`size`就是HashMap中键值对的总个数。还有一个字段是`modCount`，记录是发生内部结构变化的次数，如果put值，但是put的值是覆盖原有的值，这样是不算内部结构变化的。

装载因子的默认值是`0.75`，无论是初始大了还是初始小了对我们HashMap的性能都不好：
- 装载因子初始值大了，可以减少散列表再散列(扩容的次数)，但同时会导致散列冲突的可能性变大(散列冲突也是耗性能的一个操作，要得操作链表(红黑树)！
- 装载因子初始值小了，可以减小散列冲突的可能性，但同时扩容的次数可能就会变多！

**与红黑树相关的参数：**

```java
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

### HashMap构造函数

```java
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




### 确定哈希桶数据索引位置

Hash，一般翻译做**散列**，也有直接音译为**哈希**的，就是**把任意长度的输入，通过散列算法，变换成固定长度的输出，该输出就是散列值**。这种转换是一种**压缩映射**，也就是，**散列值的空间通常远小于输入的空间**，不同的输入可能会散列成相同的输出，所以不可能从散列值来唯一的确定输入值。简单的说就是一种将任意长度的消息压缩到某一固定长度的消息摘要的函数。

所有散列函数都有如下一个基本特性：**根据同一散列函数计算出的散列值如果不同，那么输入值肯定也不同。但是，根据同一散列函数计算出的散列值如果相同，输入值不一定相同**。

**两个不同的输入值**，根据同一散列函数计算出的**散列值相同**的现象叫做**碰撞**。

根据元素特征计算元素**数组下标**的方法就是**哈希算法**，即`hash()函数`（当然，还包括`indexFor()函数`）

因为**HashMap扩容每次都是扩容为原来的2倍**，所以length总是2的次方，这是非常规的设置，常规设置是把桶的大小设置为素数，因为素数发生hash冲突的概率要小于合数，比如HashTable的默认值设置为11，就是桶的大小为素数的应用(HashTable扩容后不能保证是素数)。HashMap采用这种设置是为了在取模和扩容的时候做出优化。

hashMap是通过**key的hashCode的高16位和低16位异或(不同则为1)**后和桶的数量**取模**得到**索引位置**，即`key.hashcode()^(hashcode>>>16)%length`，当length是2^n时，`h & (length-1)`运算等价于`h % length`，而&操作比%效率更高。而采用高16位和低16位进行异或，也可以让所有的位数都参与运算，使得在length比较小的时候也可以做到尽量的散列。

在**扩容**的时候，**如果length每次是2^n**，那么重新计算出来的索引只有两种情况，一种是**old索引+16**，另一种是**索引不变**，所以就不需要每次都重新计算索引。

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


### Put方法的原理

```java
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
        // 1. 对传入数组的键Key计算Hash值 ->>分析1
        // 2. 再调用putVal（）添加数据进去 ->>分析2
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
     * 为了方便讲解，故提前到此讲解
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

```java
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
    // 判断table是否为空，如果是空的就创建一个table，并获取他的长度
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    
    // 如果计算出来的索引位置之前没有放过数据，就直接放入
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
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

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


### Get方法的原理

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
 * Implements Map.get and related methods
 *
 * @param hash hash for key
 * @param key the key
 * @return the node, or null if none
 */
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    // 如果表不是空的，并且要查找索引处有值，就判断位于第一个的key是否是要查找的key
    // 定位键值对所在桶的位置
    if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
        if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
            // 如果是，就直接返回
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


### JDK1.7 ReHash

HashMap的容量是有限的。当经过多次元素插入，使得HashMap达到一定饱和度时，Key映射位置发生冲突的几率会逐渐提高。这时候，HashMap需要扩展它的长度，也就是进行Resize。

<div align=center><img src=DataStructure\HashMap6.png width=70%></div>

影响发生Resize的因素有两个：

1. Capacity：HashMap的当前长度。HashMap的长度是2的幂。
2. LoadFactor：HashMap负载因子，**默认值为0.75f**。

衡量HashMap是否进行Resize的条件为：`HashMap.Size >= Capacity * LoadFactor`

HashMap的Resize不是简单地把长度扩大，而是经过下面两个步骤：

1. 扩容：创建一个新的Entry空数组，**长度是原数组的2倍**。
2. ReHash：遍历原Entry数组，把所有的Entry重新Hash到新数组。

为什么要重新Hash呢？因为**长度扩大以后，Hash的规则也随之改变**。

回顾一下Hash公式：`index = HashCode(Key) & (Length - 1)`(JDK1.7)，当原数组长度为8时，Hash运算是和111B做与运算；新数组长度为16，Hash运算是和1111B做与运算。Hash结果显然不同。

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

### JDK1.8扩容机制

在HashMap中，桶数组的长度均是2的幂，`阈值大小`为`桶数组长度与负载因子的乘积`。当HashMap中的**键值对数量超过阈值时**，进行扩容。

`h & (length-1)`

我们使用的是2次幂的扩展(指**长度扩为原来2倍**)，所以，**元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置**。看下图可以明白这句话的意思，n为table的长度，图（a）表示**扩容前**的key1和key2两种key确定索引位置的示例，图（b）表示**扩容后**key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果：

<div align=center><img src=DataStructure\HashMap10.png></div>

元素在重新计算hash之后，因为n变为2倍，那么n-1的mask范围在高位多1bit(红色)，因此新的index就会发生这样的变化：

<div align=center><img src=DataStructure\HashMap11.png></div>

因此，JDK1.8在扩充HashMap的时候，不需要像JDK1.7的实现那样重新计算hash，只需要看看**原来的hash值新增的那个bit是1还是0**就好了，**是0的话索引没变，是1的话索引变成“原索引+扩容前旧容量”**，可以看看下图为16扩充为32的resize示意图：

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
     * @return the table
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
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
        else if (oldThr > 0) // initial capacity was placed in threshold
            // 初始化时，将threshold的值赋值给 newCap
            // HashMap使用threshold变量暂时保存initialCapacity参数的值
            newCap = oldThr;
        else {      // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
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
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
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


### 1.7和1.8中HashMap的区别

<div align=left><img src=DataStructure\HashMap18.jpg></div>

**参考资料：**
https://blog.csdn.net/qq_36520235/article/details/82417949


1. JDK1.7用的是**头插法**，而JDK1.8及之后使用的都是**尾插法**。
   
   那么他们为什么要这样做呢？
   因为JDK1.7是用单链表进行的纵向延伸，当采用头插法时会容易出现**逆序且环形链表死循环**问题。但是在JDK1.8之后是因为加入了**红黑树**使用尾插法，能够避免出现逆序且链表死循环的问题。

2. 扩容后数据存储位置的计算方式也不一样：
   - 在JDK1.7的时候是**直接用hash值和需要扩容的二进制数进行&**（这里就是为什么扩容的时候为啥一定必须是2的幂次的原因所在，因为如果只有2的n次幂的情况时最后一位二进制数才一定是1，这样能最大程度减少hash碰撞）（`hash值 & length-1`）；
   - 而在JDK1.8的时候直接用了JDK1.7的时候计算的规律，也就是**扩容前的原始位置+扩容的大小值=JDK1.8的计算方式**，而不再是JDK1.7的那种异或的方法。但是这种方式就相当于**只需要判断Hash值的新增参与运算的位是0还是1**就直接迅速计算出了扩容后的储存方式。

<div align=center><img src=DataStructure\HashMap13.png></div>

**扩容流程对比图：**

<div align=center><img src=DataStructure\HashMap14.png></div>

3. JDK1.7的时候使用的是**数组+单链表**的数据结构。但是在JDK1.8及之后时，使用的是**数组+链表+红黑树**的数据结构（当链表的深度达到8的时候，也就是默认阈值，就会**自动扩容把链表转成红黑树的数据结构**来把时间复杂度从$O(n)$变成$O(logN)$提高了效率）

<div align=center><img src=DataStructure\HashMap15.png></div>

<div align=center><img src=DataStructure\HashMap.webp></div>


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



