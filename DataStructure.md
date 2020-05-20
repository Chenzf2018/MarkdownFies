# 合集

数据结构(data structure) 是以某种形式将数据组织在一起的`合集`(collection) 。数据结构不仅存储数据，还支持访问和处理数据的操作。

在面向对象思想里，一种数据结构也被认为是一个`容器`(container) 或者`容器对象`(container object), 它是一个能存储其他对象的对象，这里的其他对象常称为数据或者元素。

Java合集框架支持以下两种类型的容器：
- 为了存储**键/值对**，称为**映射表(map)**。
- 为了存储**一个元素**合集，简称为**合集(collection)**；
  - `Set`用于存储一组**不重复**的元素。
  - `List`用于存储一个**有序**元素合集。
  - `Stack`用千存储采用**后进先出**方式处理的对象。
  - `Queue`用于存储采用**先进先出**方式处理的对象。
  - `Priority Queue`用于存储按照**优先级顺序**处理的对象。


`Collection接口`为线性表、向量、栈、队列，优先队列以及集合定义了共同的操作。在Java合集框架中定义的所有接口和类都分组在`java.util`包中。

<div align=center><img src=DataStructure\Collection.jpg></div>

`Co11ection`接口包含了处理合集中元素的方法，并且可以得到一个**迭代器对象**用于遍历合集中的元素。

<div align=center><img src=DataStructure\Collection方法.jpg width=80%></div>

除开`java.util.PriorityQueue`没有实现`Cloneable`接口外， Java合集框架中的其他所有具体类都实现了`java.lang.Cloneable`和`java.io.Seria1izab1e`接口。因此，除开优先队列外，所有`Collection`的实例都是**可克隆**的、**可序列化**的。

## 迭代器Iterator

每种合集都是可迭代的(Iterable)。可以获得集合的`Iterator对象`来遍历合集中的所有元素。

**`Collection`接口继承自`Iterable`接口**。`Iterable`接口中定义了`iterator`方法，<font color=red>该方法会返回一个迭代器</font>。`Iterator`接口为遍历各种类型的合集中的元素提供了一种统一的方法。`Iterable`接口中的`iterator()`方法返回一个`Iterator`的实例。

```java {.line-numbers highlight=23}
import java.util.*;

public class TestIterator
{
    public static void main(String[] args)
    {
        Collection<String> collection = new ArrayList<>();
        collection.add("New York");
        collection.add("Atlanta");
        collection.add("Dallas");
        collection.add("Madison");

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

`Arraylist`和`Linkedlist`定义在`List`接口下。`List`接口继承`Collection`接口，定义了一个**允许重复**的**有序**合集。

<div align=center><img src=DataStructure\List.jpg width=80%></div>

方法`listIterator()`或`listIterator(startlndex)`都会返回`ListIterator`的一个实例。`ListIterator`接口继承了`Iterator`接口，以增加对线性表的双向遍历能力。

<div align=center><img src=DataStructure\ListIterator.jpg width=70%></div>

## 数组线性表类ArrayList和链表类LinkedList

`ArrayList`用数组存储元素，这个数组是**动态创建**的。<font color=red>如果元素个数超过了数组的容量，就创建一个更大的新数组，并将当前数组中的所有元素都复制到新数组中</font>。`LinkedList`在一个链表(linked list)中存储元素。

要选用这两种类中的哪一个，依赖于特定需求。如果需要通过**下标随机访问元素**，而不会在线性表起始位置插入或删除元素，那么**ArrayList**提供了最高效率的合集。但是，如果应用程序需要在线性表的**起始位置上插入或删除元素**，就应该选择`LinkedList`类。

**线性表的大小**是可以**动态增大或减小**的。然而**数组**一旦被创建，它的大小就是**固定**的。如果应用程序不需要在线性表中插入或删除元素，那么数组是效率最高的数据结构。

`ArrayList`使用**可变大小的数组**实现List接口：
<div align=center><img src=DataStructure\ArrayList.jpg width=70%></div>

`LinkedList`使用**链表**实现`List`接口。除了实现`List`接口外，这个类还提供**从线性表两端**提取、插入和删除元素的方法：
<div align=center><img src=DataStructure\LinkedList.jpg width=70%></div>

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

# Comparator接口

Java API的一些类，比如`String`、`Date`、`Calendar`、`BigInteger`、`BigDecimal`以及所有**基本类型的数字包装类**都实现了`Comparable`接口。<font color=red>`Comparable`接口定义了`compareTo`方法，用于比较实现了`Comparable`接口的**同一个类**的两个元素</font>。

如果元素的类没有实现Comparable接口又将如何呢？这些元素可以比较么？

可以定义一个`比较器`(comparator)来比较**不同类**的元素。要做到这一点，需要创建一个实现`java.util.Comparator<T>`接口的类并重写它的`compare`方法。
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

`Comparable`用于比较实现`Comparable`的类的对象；`Comparator`用于比较没有实现`Comparable`的类的对象。

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

`Collections`类包含了执行合集和线性表中通用操作的静态方法。

`java.util.Collection`是一个**集合接口**（集合类的一个顶级接口）。它提供了对集合对象进行基本操作的通用接口方法。Collection接口在Java类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式，其直接继承接口有List与Set。

`Collections`则是集合类的一个**工具类/帮助类**，其中提供了一系列静态方法，用于对集合中元素进行排序、搜索以及线程安全等各种操作。

<div align=center><img src=DataStructure\Collections.jpg width=90%></div>


# 向量类和栈类

Java合集框架是在Java 2中引入的。Java 2之前的版本也支持一些数据结构，其中就有`向量类Vector`与`栈类Stack`。为了适应Java合集框架，Java 2对这些类进行了重新设计，但是为了向后兼容，保留了它们所有的以前形式的方法。

除了包含用于**访问**和**修改**向量的**同步方法**之外， Vector类与Arraylist是一样的。同步方法用于防止两个或多个线程同时访问和修改某个向量时引起数据损坏。<font color=red>对于许多不需要同步的应用程序来说，使用Arraylist比使用Vector效率更高</font>。

<div align=center><img src=DataStructure\Vector.jpg width=80%></div>

方法`elements()`返回一个`Enumeration`对象（枚举型对象）。`Enumeration`接口是在Java 2之前引入的，已经被`Iterator`接口所取代。

在Java合集框架中，栈类Stack是作为Vector类的扩展来实现的：
<div align=center><img src=DataStructure\Stack.jpg width=70%></div>


# 队列和优先队列

队列(queue)是一种先进先出的数据结构。元素被追加到队列末尾，然后从队列头删除。在优先队列(priority queue)中，元素被赋予优先级。<font color=red>当访问元素时，拥有最高优先级的元素首先被删除</font>。

<div align=center><img src=DataStructure\Queue.jpg width=70%></div>

## 双端队列Deque和链表LinkedList

<div align=center><img src=DataStructure\Linkedlist实现了List和Deque.jpg width=50%></div>

Linkedlist类实现了Deque接口，Deque又继承自Queue接口。因此、可以使用LinkedList创建一个队列。LinkedList很适合用于进行队列操作，因为它可以高效地在线性表的两端插入和移除元素。

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


默认情况下，优先队列使用`Comparable`以元素的自然顺序进行排序。拥有最小数值的元素被赋予最高优先级，因此最先从队列中删除。如果几个元素具有相同的最高优先级，则任意选择一个。也可以使用构造方法`PriorityQueue(initialCapacity, comparator)`中的`Comparator`来指定一个顺序。

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

`LinkedHashSet`用一个**链表**实现来扩展`HashSet`类，它支持对集合内的元素**排序**。`HashSet`中的元素是没有被排序的，而`LinkedHashSet`中的元素可以<font color=red>按照它们插入集合的顺序提取</font>。

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

`SortedSet`是`Set`的一个子接口，它可以确保集合中的元素是有序的。另外，它还提供方法`frst()`和`last()`以返回集合中的第一个元素和最后一个元素，以及方法`headSet(toElement)`和`tailSet(fromElement)`以分别返回集合中元素**小于**`toElement`和**大于或等于**`fromElement`的那一部分。

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

映射表(map)类似于目录，提供了使用**键值key**快速查询和荻取**值value**的功能。

映射表(map) 是一种依照**键/值对**存储元素的容器。它提供了通过键快速获取、删除和更新键／值对的功能。映射表将值和键一起保存。键很像下标。在`List`中，下标是整数；而在`Map`中，键可以是**任意类型的对象**。映射表中**不能有重复的键**，每个键都对应一个值。一个键和它的对应值构成一个条目并保存在映射表中。

<div align=center><img src=DataStructure\映射表.jpg></div>

映射表有三种类型：散列映射表`HashMap` 、链式散列映射表`LinkedHashMap`和树形映射表`TreeMap`。这些映射表的通用特性都定义在`Map`接口中：

<div align=center><img src=DataStructure\三种映射表.jpg width=90%></div>

`Map接口`提供了查询、更新和获取合集的值和合集的键的方法：

<div align=center><img src=DataStructure\Map接口.jpg></div>

`HashMap`、`LinkedHashMap`和`TreeMap`类是`Map`接口的三个具体实现：

<div align=center><img src=DataStructure\三种映射表关系.jpg></div>

对于**定位**一个值、**插入**一个条目以及**删除**一个条目而言，`HashMap`类是高效的。

- `AbstractMap`类是一个便利抽象类，它实现了Map接口中除了`entrySet()`方法之外的所有方法。
  
- `LinkedHashMap`类用链表实现来扩展`HashMap`类，它支持映射表中条目的**排序**。`HashMap`类中的条目是**没有顺序**的，但是在`LinkedHashMap`中，<font color=red>元素既可以按照它们插入映射表的顺序排序（称为插入顺序`insertion order`), 也可以按它们被最后一次访问时的顺序，从最早到最晚（称为访问顺序`access order`) 排序</font>。
  - **无参构造方法**是以**插入顺序**来创建`LinkedHashMap`对象的。
  - 要按**访问顺序**创建`LinkedHashMap`对象，应该使用构造方法`LinkedHashMap(initialCapacity,loadFactor, true)`。

- `TreeMap`类在<font color=red>遍历排好顺序的键时是很高效的</font>。
  - 键可以使用`Comparable`接口或`Comparator`接口来排序。如果使用它的**无参构造方法**创建一个`TreeMap`对象，假定键的类实现了`Comparable`接口，则可以使用`Comparable`接口中的`compareTo`方法来对映射表内的键进行比较。
  - 要使用**比较器**，必须使用构造方法`TreeMap(Comparator comparator)`来创建一个有序映射表，这样，该映射表中的条目就能使用比较器中的`compare`方法按键进行排序。

- `SortedMap`是`Map`的一个子接口，使用它可确保映射表中的条目是**排好序**的。除此之外，它还提供方法`firstKey()`和`lastKey()`来返回映射表中的第一个和最后一个键，而方法`headMap(toKey)`和`tailMap(fromKey)`分别返回键**小于**`toKey`的那部分映射表和键**大于或等于**`fromKey`的那部分映射表。

- `NavigableMap`继承了`SortedMap`, 以提供导航方法`lowerKey(key)`、`floorKey(key)`、`ceilingKey(key)`和`higherKey(key)`来分别返回**小于**、**小于或等于**、**大于或等于**、**大于**某个给定键的键，如果没有这样的键，它们都会返回`null` 。方法`pollFirstEntry()`和`pollLastEntry()`分别**删除**并**返回**树映射表中的第一个和最后一个条目。


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