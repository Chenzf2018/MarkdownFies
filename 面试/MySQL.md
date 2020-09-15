参考资料：
https://blog.csdn.net/tongdanping/article/details/79878302


# 关系型数据库

- 依据**关系模型**来创建的数据库；
- 所谓关系模型就是**一对一、一对多、多对多**等关系模型，关系模型就是指二维表格模型，因而一个关系型数据库就是由二维表及其之间的联系组成的一个数据组织。
- 关系型数据可以很好地存储一些关系模型的数据，比如一个老师对应多个学生的数据（“多对多”），一本书对应多个作者（“一对多”），一本书对应一个出版日期（“一对一”）

# 增删改查

## 创建表

如果test数据库不存在，就创建test数据库：`CREATE DATABASE IF NOT EXISTS test;`
切换到test数据库：`USE test;`
删除students表（如果存在）：`DROP TABLE IF EXISTS student;`
创建students表：
```SQL
-- 创建表
CREATE TABLE students (
    id BIGINT NOT NULL AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(1) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
插入students记录：`INSERT INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'M', 90);`


## 查询

查询students表的所有数据：`SELECT * FROM students;`
查询分数在**80分或以上**的**男生**：`SELECT * FROM students WHERE score >= 80 AND gender = 'M';`
按照成绩从高到底排序：`SELECT id, name, gender, score FROM students ORDER BY score DESC;`
查询一班的学生成绩，并按照倒序排序：`SELECT id, name, gender, score FROM students WHERE class_id = 1 ORDER BY score DESC;`
统计有多少男同学：`SELECT COUNT(*) boys FROM students WHERE gender = 'M';`

## 修改

### INSERT
向students表插入一条新记录：`INSERT INTO students (class_id, name, gender, score) VALUES (2, 'chen', 'M', 80);`

### UPDATE
更新id=1的记录：`UPDATE students SET name='大牛', score=66 WHERE id=1;`
更新score<80的记录：`UPDATE students SET score=score+10 WHERE score<80;`
将id=1的A账户余额减去100：`UPDATE accounts SET balance = balance - 100 WHERE id = 1;`

### DELETE

`DELETE FROM students WHERE id>=5 AND id<=7;`



# MySQL中索引是用什么实现的

## B树

B-树就是B树（**多路查找树**）；

数据库索引为什么使用树结构存储？**树的查询效率高**；

为什么索引没有使用二叉查找树来是实现？

二叉查找树查询的时间复杂度$O(logN)$；现实问题：**磁盘IO**；

**数据库索引是存储在磁盘上的**，当数据量比较大的时候，索引的大小可能有几个G。**当利用索引查询的时候，不能把整个索引全部加载到内存里**。只能逐一加载每一个磁盘页，**磁盘页对应着索引树的结点**。

**最坏的情况下，磁盘IO次数等于索引树的高度**。

**为了减少磁盘IO次数，需要把原本“瘦高”的树结构变得“矮胖”**，就是B-树的特征之一。

如果利用二叉查找树作为索引结构，假设树的高度是4，查找的值是10：

第一次磁盘IO：
<div align=center><img src=Pictures\MySQL_第一次磁盘IO.jpg width=60%></div>

第二次磁盘IO：
<div align=center><img src=Pictures\MySQL_第二次磁盘IO.jpg width=60%></div>

第三次磁盘IO：
<div align=center><img src=Pictures\MySQL_第三次磁盘IO.jpg width=60%></div>

第四次磁盘IO：
<div align=center><img src=Pictures\MySQL_第四次磁盘IO.jpg width=60%></div>

**最坏的情况下，磁盘IO次数等于索引树的高度**。

**为了减少磁盘IO次数，需要把原本“瘦高”的树结构变得“矮胖”**，就是B-树的特征之一。

B树是一种多路平衡查找树，它的每一个结点最多包含k个孩子，k被称为B树的阶，k的大小取决于磁盘页的大小。

### B树特征

一个m阶的B树具有如下几个特征：
- 根结点至少有两个子女；
- 每个中间节点都包含$k-1$个元素和$k$个孩子，其中$\frac{m}{2}\leq k\leq m$；
- 每一个叶子节点都包含$k-1$个元素，其中$\frac{m}{2}\leq k\leq m$；
- 所有的叶子结点都位于同一层；
- 每个节点中的元素从小到大排列，节点当中$k-1$个元素正好是$k$个孩子包含的元素的值域分划。

以一个3阶B-树为例：
<div align=center><img src=Pictures\MySQL_3阶B-树.jpg width=60%></div>

以结点(2 6)为例，它有三个孩子，每个节点中的元素从小到大排列。

假设查询的数值是5，B-树查询过程：

第一次磁盘IO：
<div align=center><img src=Pictures\MySQL_B树第一次磁盘IO.jpg width=60%></div>

第二次磁盘IO：
<div align=center><img src=Pictures\MySQL_B树第二次磁盘IO.jpg width=60%></div>

第三次磁盘IO：
<div align=center><img src=Pictures\MySQL_B树第三次磁盘IO.jpg width=60%></div>

B-树在查询中的比较次数其实不比二叉查找树少，尤其当单一结点中的元素数量很多时。可是**相比磁盘IO的速度，内存中的比较耗时几乎可以忽略**(B-树一个结点可以存储多个元素，树高较低)。所以**只要树的高度足够低，IO次数足够少，就可以提升查找性能**。相比之下，结点内部元素多一些也没关系，仅仅是多了几次内存交互，只要不超过磁盘页的大小即可。


### B树插入新结点

自顶向下查找4的节点位置，发现4应当插入到节点元素3，5之间：

<div align=center><img src=Pictures\MySQL_B-树插入.jpg width=60%></div>

节点(3 5)已经是两元素节点，无法再增加。父亲节点(2 6)也是两元素节点，也无法再增加。根节点9是单元素节点，可以升级为两元素节点。于是拆分节点(3 5)与节点(2 6)，让根节点9升级为两元素节点(4 9)。节点6独立为根节点的第二个孩子：

<div align=center><img src=Pictures\MySQL_B-树插入1.jpg width=60%></div>

为了插入一个元素，让整个B树的多个结点发生了连锁改变，但此举让B树能够始终维持多路平衡——自平衡。

### B树删除

自顶向下查找元素11的节点位置：
<div align=center><img src=Pictures\MySQL_B-树删除.jpg width=60%></div>

删除11后，节点12只有一个孩子，不符合B树规范。因此找出12，13，15三个节点的中位数13，取代节点12，而节点12自身下移成为第一个孩子。（这个过程称为**左旋**）

<div align=center><img src=Pictures\MySQL_B-树删除1.jpg width=60%></div>


### B树应用

B树主要应用于文件系统以及部分数据库索引，比如非关系型数据库MongoDB。

大部分关系型数据库，比如MySQL则使用B+树作为索引。



## B+ Tree原理

`B Tree`指的是`Balance Tree`，也就是平衡树。平衡树是一颗查找树，并且所有叶子节点位于同一层。

`B+ Tree`是基于**B Tree**和**叶子节点顺序访问指针**进行实现，它具有B Tree的平衡性，并且通过顺序访问指针来提高区间查询的性能。

`B+ Tree`是基于B树的一种变体，有着比B树更高的查询性能。

一个m阶的B+树具有如下几个特征：

1. 有k个子树的中间节点包含有k个元素（B树中是k-1个元素），<font color=red>每个元素不保存数据，只用来索引，所有数据都保存在叶子节点</font>。

2. <font color=red>所有的叶子结点中包含了全部元素的信息，及指向含这些元素记录的指针，且叶子结点本身依关键字的大小自小而大顺序链接</font>。

3. 所有的中间节点元素都同时存在于子节点，在子节点元素中是最大（或最小）元素。

<div align=center><img src=Pictures\MySQL_B+树.jpg width=70%></div>

<font color=red>每一个父结点的元素都出现在子结点中，是子结点的最大（或最小）元素</font>；根结点元素8是子结点(2 5 8)里最大元素，也是叶子结点(6 8)最大的元素；根结点元素15是子结点(11 15)里最大元素，也是叶子结点(13 15)最大的元素。

<font color=red>根结点的最大元素也就是整个B+树最大元素。无论插入删除多少元素，始终要保持最大元素在根结点</font>。

### 卫星数据

卫星数据指的是**索引元素所指向的数据记录**，比如数据库中的某一行。

在**B-树**中，无论中间结点还是叶子结点，都带有卫星数据：

<div align=center><img src=Pictures\卫星数据.jpg width=70%></div>

在B+树中，只有叶子结点带有卫星数据，其余中间结点仅仅是索引，没有任何数据关联。

<div align=center><img src=Pictures\卫星数据1.jpg width=70%></div>

#### 聚集索引与非聚集索引

在数据库的**聚集索引**(Clustered Index)中，**叶子节点直接包含卫星数据**。在**非聚集索引**(NonClustered Index)中，**叶子节点带有指向卫星数据的指针**。


### 查询

#### 单元素查询

在单元素查询时，B+树会自顶向下逐层查找结点，最终找到匹配的叶子结点（查找元素3）：

第一次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第一次磁盘IO.jpg width=70%></div>

第二次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第二次磁盘IO.jpg width=70%></div>

第三次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第三次磁盘IO.jpg width=70%></div>

**B+树的中间结点没有卫星数据，所以同样大小的磁盘页可以容纳更多的结点元素。这就意味着，数据量相同的情况下，B+树的结构比B-树更加“矮胖”，因此查询时IO次数也更少。**

B+树的查询必须最终查找到叶子结点，而B-树只要找到匹配元素即可，无论匹配元素处于中间结点还是叶子结点。因此，**B-树的查找性能不稳定**，**最好情况**是只查根结点，**最坏情况**是查到叶子结点。而**B+树的每一次查找都是稳定的**。

#### 范围查询

假设要查询范围为3-11的元素，**B-树只能依靠繁琐的中序遍历**：

自顶向下，查找到范围的下限（3）：
<div align=center><img src=Pictures\MySQL_B-树的范围查询.jpg width=70%></div>

中序遍历到元素6：
<div align=center><img src=Pictures\MySQL_B-树的范围查询1.jpg width=70%></div>

中序遍历到元素8：
<div align=center><img src=Pictures\MySQL_B-树的范围查询2.jpg width=70%></div>

中序遍历到元素9：
<div align=center><img src=Pictures\MySQL_B-树的范围查询3.jpg width=70%></div>

中序遍历到元素11，遍历结束：
<div align=center><img src=Pictures\MySQL_B-树的范围查询4.jpg width=70%></div>


**B+树的范围查询只需要在链表上做遍历即可**：

自顶向下，查找到范围的下限（3）：
<div align=center><img src=Pictures\MySQL_B+树的范围查询.jpg width=70%></div>

通过链表指针，遍历到元素6，8：
<div align=center><img src=Pictures\MySQL_B+树的范围查询1.jpg width=70%></div>

通过链表指针，遍历到元素9, 11，遍历结束：
<div align=center><img src=Pictures\MySQL_B+树的范围查询2.jpg width=70%></div>


### B+树的优势

数据库索引为什么使用树结构存储？
**树的查询效率高**；

为什么索引没有使用二叉查找树来是实现？
二叉查找树查询的时间复杂度$O(logN)$；现实问题：**磁盘IO**；

**数据库索引是存储在磁盘上的**，当数据量比较大的时候，索引的大小可能有几个G。**当利用索引查询的时候，不能把整个索引全部加载到内存里**。只能逐一加载每一个磁盘页，**磁盘页对应着索引树的结点**。

**最坏的情况下，磁盘IO次数等于索引树的高度**。

**为了减少磁盘IO次数，需要把原本“瘦高”的树结构变得“矮胖”**，就是B-树的特征之一。

**B+树的中间结点没有卫星数据，所以同样大小的磁盘页可以容纳更多的结点元素。这就意味着，数据量相同的情况下，B+树的结构比B-树更加“矮胖”，因此查询时IO次数也更少。**

B+树的查询必须最终查找到叶子结点，而B-树只要找到匹配元素即可，无论匹配元素处于中间结点还是叶子结点。因此，**B-树的查找性能不稳定**，**最好情况**是只查根结点，**最坏情况**是查到叶子结点。而**B+树的每一次查找都是稳定的**。

1. 单一节点存储更多的元素，使得**查询的IO次数更少**。
2. 所有查询都要查找到叶子节点，**查询性能稳定**。
3. 所有叶子节点形成有序链表，**便于范围查询**。


# 什么是MySQL索引

在关系数据库中，如果有上万甚至上亿条记录，在查找记录的时候，想要获得非常快的速度，就需要使用索引。

<font color=red>索引是关系数据库中对某一列或多个列的值进行预排序的数据结构。通过使用索引，可以让数据库系统不必扫描整个表，而是直接定位到符合条件的记录，这样就大大加快了查询速度</font>。


假设有一张数据表employee(员工表)，该表有三个字段（列），分别是name、age和address。假设表employee有上万行数据，现在需要从这个表中查找出所有名字是`ZhangSan`的雇员信息，你会快速的写出SQL语句：

`select name,age,address from employee where name='ZhangSan';`

<font color=red>如果数据库还没有索引这个东西，一旦我们运行这个SQL查询，查找名字为ZhangSan的雇员的过程中，**数据库不得不在employee表中的每一行查找并确定雇员的名字（name）是否为‘ZhangSan’**。

由于想要得到每一个名字为ZhangSan的雇员信息，在查询到第一个符合条件的行后，不能停止查询，因为可能还有其他符合条件的行，所以必须一行一行的查找直到最后一行——这就意味**数据库不得不检查上万行数据才能找到所有名字为ZhangSan的雇员**。这就是所谓的**全表扫描**，显然这种模式效率太慢。

使用索引的全部意义就是：**通过缩小一张表中需要查询的记录/行的数目来加快搜索的速度**。

在关系型数据库中，索引是一种单独的、物理的**对数据库表中一列或多列的值进行排序的一种存储结构**</font>，它是某个表中一列或若干列值的集合和相应的指向表中物理标识这些值的数据页的逻辑指针清单。意思是索引的作用相当于图书的目录，可以根据目录中的页码快速找到所需的内容。

一个索引是存储的表中一个特定列的值数据结构。**索引是在表的列上创建**。要记住的关键点是**索引包含一个表中列的值，并且这些值存储在一个数据结构中**。请牢记这一点：**索引是一种数据结构**。一个好的数据库表设计，从一开始就应该考虑添加索引，而不是到最后发现慢SQL了，影响业务了才来补救。

MySQL支持诸多**存储引擎**，而各种存储引擎对索引的支持也各不相同，因此MySQL数据库支持多种索引类型，如BTree索引，哈希索引，全文索引等等。主要使用的还是B+Tree索引。

## 索引的本质

MySQL官方对索引的定义为：索引(Index)是帮助MySQL高效获取数据的**数据结构**。

<font color=red>数据库**查询**是数据库的最主要功能之一。我们都希望查询数据的速度能尽可能的快，因此数据库系统的设计者会从**查询算法**的角度进行优化</font>。

最基本的查询算法当然是**顺序查找**（linear search），这种复杂度为$O(n)$的算法在数据量很大时显然是糟糕的，好在计算机科学的发展提供了很多更优秀的查找算法，例如**二分查找**（binary search）、**二叉树查找**（binary tree search）等。如果稍微分析一下会发现，每种查找算法都只能应用于特定的数据结构之上，例如**二分查找要求被检索数据有序**，而**二叉树查找只能应用于二叉查找树上**。

但是**数据本身的组织结构不可能完全满足各种数据结构**（例如，理论上不可能同时将两列都按顺序进行组织），所以，在数据之外，数据库系统还维护着满足特定查找算法的数据结构，**这些数据结构以某种方式引用（指向）数据**，这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。

索引是一个排序的列表，在这个列表中存储着**索引的值**和包含这个值的数据所在行的**物理地址**，在数据十分庞大的时候，索引可以大大加快查询的速度，这是因为使用索引后可以**不用扫描全表**来定位某行的数据，而是**先通过索引表找到该行数据对应的物理地址然后访问相应的数据**。

一种可能的索引方式：

<div align=center><img src=Pictures\MySQL_索引.png width=70%></div>

左边是数据表，一共有两列七条记录，最左边的是数据记录的**物理地址**（注意逻辑上相邻的记录在磁盘上也并不是一定物理相邻的）。为了加快Col2的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含**索引键值**和**一个指向对应数据记录物理地址的指针**，这样就可以运用二叉查找在$O(logn)$的复杂度内获取到相应数据。


## MySQL索引类型

普通索引（key），唯一索引（unique key），主键索引（primary key），全文索引（fulltext key）

三种索引的索引方式是一样的，只不过对索引的关键字有不同的限制：

- 普通索引：对关键字没有限制
    - 创建索引：`CREATE INDEX indexName ON mytable(username(length)); `
    - 修改表结构：`ALTER mytable ADD INDEX [indexName] ON (username(length)) `
    - 删除索引：`DROP INDEX [indexName] ON mytable; `
```SQL
CREATE TABLE mytable(
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    INDEX [indexName] (username(length))
);  
```

- 唯一索引：要求记录提供的关键字不能重复；索引列的值必须唯一，但允许有空值。
    - 创建索引：`CREATE UNIQUE INDEX indexName ON mytable(username(length)) `
    - 修改表结构：`ALTER mytable ADD UNIQUE [indexName] ON (username(length)) `
```SQL
CREATE TABLE mytable(
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    UNIQUE [indexName] (username(length))
); 
```

- 主键索引：要求关键字唯一且不为null，一般是在建表的时候同时创建主键索引：
```SQL
CREATE TABLE mytable(  
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    PRIMARY KEY(ID) 
); 
```

## MySQL中索引的语法

**创建索引**

在创建表的时候添加索引：

```SQL
CREATE TABLE mytable(  
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    INDEX [indexName] (username(length))  
); 
```

在创建表以后添加索引：
```SQl
ALTER TABLE my_table ADD [UNIQUE] INDEX index_name(column_name);
或者
CREATE INDEX index_name ON my_table(column_name);
```

1. 索引需要占用磁盘空间，因此在创建索引时要考虑到磁盘空间是否足够

2. 创建索引时需要对表加锁，因此实际操作中需要在业务空闲期间进行

**根据索引查询**

```SQl
具体查询：
SELECT * FROM table_name WHERE column_1=column_2;(为column_1建立了索引)
 
或者模糊查询
SELECT * FROM table_name WHERE column_1 LIKE '%三'
SELECT * FROM table_name WHERE column_1 LIKE '三%'
SELECT * FROM table_name WHERE column_1 LIKE '%三%'
 
SELECT * FROM table_name WHERE column_1 LIKE '_好_'
 
如果要表示在字符串中既有A又有B，那么查询语句为：
SELECT * FROM table_name WHERE column_1 LIKE '%A%' AND column_1 LIKE '%B%';
 
SELECT * FROM table_name WHERE column_1 LIKE '[张李王]三';  //表示column_1中有匹配张三、李三、王三的都可以
SELECT * FROM table_name WHERE column_1 LIKE '[^张李王]三';  //表示column_1中有匹配除了张三、李三、王三的其他三都可以
 
//在模糊查询中，%表示任意0个或多个字符；_表示任意单个字符（有且仅有），通常用来限制字符串长度;[]表示其中的某一个字符；[^]表示除了其中的字符的所有字符
 
或者在全文索引中模糊查询
SELECT * FROM table_name WHERE MATCH(content) AGAINST('word1','word2',...);
```

**删除索引**

```SQL
DROP INDEX my_index ON tablename；
或者
ALTER TABLE table_name DROP INDEX index_name;
```

**查看表中的索引**

```SQL
SHOW INDEX FROM tablename
```

**查看查询语句使用索引的情况**

```SQL
//explain 加查询语句
explain SELECT * FROM table_name WHERE column_1='123';
```


## 索引的优缺点

优势：
- 可以快速检索，减少I/O次数，加快检索速度；
- 根据索引分组和排序，可以加快分组和排序；

劣势：
- <font color=red>索引本身也是表，因此会占用存储空间</font>，一般来说，索引表占用的空间的数据表的1.5倍；
- <font color=red>索引表的维护和创建需要时间成本</font>，这个成本随着数据量增大而增大；
- 构建索引会降低数据表的修改操作（删除，添加，修改）的效率，因为<font color=red>在修改数据表的同时还需要修改索引表</font>；




## [为什么使用B-Tree/B+Tree](#b树)

红黑树等数据结构也可以用来实现索引，但是**文件系统及数据库系统**普遍采用B-/+Tree作为索引结构。

一般来说，**索引本身也很大，不可能全部存储在内存中**，因此**索引往往以索引文件的形式存储的磁盘上**。这样的话，索引查找过程中就要产生**磁盘I/O消耗**，相对于内存存取，I/O存取的消耗要高几个数量级，所以评价一个数据结构作为索引的优劣最重要的指标就是**在查找过程中磁盘I/O操作次数的渐进复杂度**。换句话说，**索引的结构组织要尽量减少查找过程中磁盘I/O的存取次数**。


## MyISAM存储引擎索引实现--非聚集

MyISAM引擎使用B+Tree作为索引结构，**叶节点的data域存放的是数据记录的地址**。下图是MyISAM索引的原理图：

<div align=center><img src=Pictures\MySQL_MyISAM索引的原理图.png width=70%></div>

这里设表一共有三列，假设我们**以Col1为主键**，则上图是一个MyISAM表的**主索引（Primary key）**示意。可以看出**MyISAM的索引文件仅仅保存数据记录的地址**。在MyISAM中，主索引和**辅助索引**（Secondary key）在结构上没有任何区别，只是主索引要求key是唯一的，而辅助索引的key可以重复。

如果我们在Col2上建立一个辅助索引，则此索引的结构如下图所示：
<div align=center><img src=Pictures\MySQL_MyISAM辅助索引的原理图.png width=70%></div>

同样也是一颗B+Tree，data域保存数据记录的地址。因此，MyISAM中索引检索的算法为**首先按照B+Tree搜索算法搜索索引，如果指定的Key存在，则取出其data域的值，然后以data域的值为地址，读取相应数据记录**。

MyISAM的索引方式也叫做“**非聚集**”的，之所以这么称呼是为了与**InnoDB的聚集索引**区分。


## InnoDB存储引擎索引实现--聚集

InnoDB使用B+Tree作为索引结构：

**InnoDB的数据文件本身就是索引文件**：在InnoDB中，**表数据文件本身就是按B+Tree组织的一个索引结构**，这棵树的**叶节点data域**保存了完整的数据记录。这个**索引的key是数据表的主键**，因此InnoDB表数据文件本身就是主索引。

<div align=center><img src=Pictures\MySQL_InnoDB主索引.png width=70%></div>

上图是InnoDB主索引（同时也是数据文件）的示意图，可以看到叶节点包含了完整的数据记录。这种索引叫做**聚集索引**。因为InnoDB的数据文件本身要按**主键聚集**，所以InnoDB要求表必须有主键，如果没有显式指定，则MySQL系统会自动选择一个可以唯一标识数据记录的列作为主键，如果不存在这种列，则MySQL自动为InnoDB表生成一个隐含字段作为主键，这个字段长度为6个字节，类型为长整形。

InnoDB的辅助索引data域存储相应记录主键的值而不是地址。换句话说，**InnoDB的所有辅助索引都引用主键作为data域**。例如，下图为定义在Col3上的一个辅助索引：

<div align=center><img src=Pictures\MySQL_InnoDB辅助索引.png width=70%></div>

这里以英文字符的ASCII码作为比较准则。**聚集索引这种实现方式使得按主键的搜索十分高效**，但是**辅助索引搜索需要检索两遍索引**：首先检索辅助索引获得主键，然后用主键到主索引中检索获得记录。

了解不同存储引擎的索引实现方式对于正确使用和优化索引都非常有帮助：
- 知道了InnoDB的索引实现后，就很容易明白为什么**不建议使用过长的字段作为主键**，因为所有辅助索引都引用主索引，过长的主索引会令辅助索引变得过大。
- 用非单调的字段作为主键在InnoDB中不是个好主意，因为InnoDB数据文件本身是一颗B+Tree，非单调的主键会造成在插入新记录时数据文件为了维持B+Tree的特性而频繁的分裂调整，十分低效，而**使用自增字段作为主键**则是一个很好的选择。


# 数据库优化

数据库优化的几个方面：
- SQL语句
- 有效索引
- 数据结构
- 系统配置
- 硬件

<div align=center><img src=Pictures\数据库优化.png></div>

1. SQL以及索引的优化是最重要的。**首先要根据需求写出结构良好的SQL，然后根据SQL在表中建立有效的索引**。但是**如果索引太多，不但会影响写入的效率，对查询也有一定的影响**。

    - 数据类型选择
        - 尽量选择float（与double比较）
        - 能够用数字类型的字段尽量选择数字类型而不用字符串类型的
        - 避免使用NULL字段，很难查询优化且占用额外索引空间
    - 索引优化
        - 较频繁的作为查询条件字段应该创建索引；
        - 唯一性太差的字段不适合单独创建索引，即使频繁作为查询条件；
        - 更新非常频繁的字段不适合创建索引
        - 不会出现在WHERE子句中的字段不该创建索引
        - 尽量不要对数据库中某个含有大量重复的值的字段建立索引


2. 要**根据一些范式来进行表结构的设计**。设计表结构时，就需要考虑如何设计才能够更有效的查询。
    - 第一范式1NF：**字段原子性**：数据库表的每一列都是不可分割的基本数据项。
        - 学生信息组成学生信息表，有姓名、年龄、性别、学号等信息组成。姓名不可拆分，所以可以作为该表的一个字段。
    - 第二范式2NF：**消除对主键的部分依赖**。即在表中加上一个与业务逻辑无关的字段作为主键(**可以唯一标识记录的字段或者字段集合**)。
        - 2NF的使用是需要满足1NF为前提，**在表中添加一个业务字段(消除对主键的部分依赖)，而主键不用来做业务处理**，比如我们的商品表有**商品id(1,2,3)**，商品id为商品的主键，但是需要创建一个**商品编号(ab1, ab2, ab3)**列来专门处理业务，因为id太敏感，我们处理业务都是用商品编号来处理。
    - 第三范式3NF：**消除对主键的传递依赖**。传递依赖：B字段依赖于A，C字段又依赖于B。
        - 订单表中有客户相关信息，在分离出客户表之后，订单表中只需要有一个用户id即可（外键），而不能有其他的客户信息。因为其他的客户信息直接关联于用户id，而不是直接与订单id直接相关。如下图所示：
<div align=center><img src=Pictures\第三范式_1.png></div>
分离之后：
<div align=center><img src=Pictures\第三范式_2.png></div>


3. 系统配置的优化。MySQL数据库是基于文件的，如果打开的文件数达到一定的数量，无法打开之后就会进行频繁的IO操作。


4. 硬件优化。**更快的IO、更多的内存**。一般来说内存越大，对于数据库的操作越好。但是CPU多就不一定了，因为他并不会用到太多的CPU数量，有很多的查询都是单CPU。另外使用高的IO（SSD、RAID），但是IO并不能减少数据库锁的机制。所以说如果查询缓慢是因为数据库内部的一些锁引起的，那么硬件优化就没有什么意义。


# 事务

## 事务简述

在MySQL中只有使用了InnoDB数据库引擎的数据库或表才支持事务

在执行SQL语句的时候，**某些业务要求，一系列操作必须全部执行，而不能仅执行一部分**。
例如，一个转账操作：
```SQL
-- 从id=1的账户给id=2的账户转账100元
-- 第一步：将id=1的A账户余额减去100
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
-- 第二步：将id=2的B账户余额加上100
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
```

这两条SQL语句必须全部执行，或者，由于某些原因，如果第一条语句成功，第二条语句失败，就必须全部撤销。

**这种把多条语句作为一个整体进行操作的功能，被称为数据库事务**。数据库事务可以确保该事务范围内的所有操作都可以全部成功或者全部失败。如果事务失败，那么效果就和没有执行这些SQL一样，不会对数据库数据有任何改动。


可见，数据库事务具有ACID这4个特性：

- A：Atomic，原子性，将所有SQL作为原子工作单元执行，**要么全部执行，要么全部不执行**；
- C：Consistent，一致性，**事务完成后，所有数据的状态都是一致的**，即A账户只要减去了100，B账户则必定加上了100；
- I：Isolation，隔离性，**如果有多个事务并发执行，每个事务作出的修改必须与其他事务隔离。比如操作同一张表时，数据库为每一个用户开启的事务，不能被其他事务的操作所干扰，多个并发事务之间要相互隔离。**；
    - 对于任意两个并发的事务T1和T2，在事务T1看来，T2要么在T1开始之前就已经结束，要么在T1结束之后才开始，这样每个事务都感觉不到有其他事务在并发地执行。
- D：Duration，持久性，即事务完成后，**对数据库数据的修改被持久化存储**。

对于单条SQL语句，数据库系统自动将其作为一个事务执行，这种事务被称为**隐式事务**。

要手动把多条SQL语句作为一个事务执行，使用BEGIN开启一个事务，使用COMMIT提交一个事务，这种事务被称为显式事务，例如，把上述的转账操作作为一个显式事务：
```SQL
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```

很显然多条SQL语句要想作为一个事务执行，就必须使用显式事务。COMMIT是指提交事务，即试图把事务内的所有SQL所做的修改永久保存。如果COMMIT语句执行失败了，整个事务也会失败。

有些时候，我们希望主动让事务失败，这时，可以用ROLLBACK回滚事务，整个事务会失败：
```SQL
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
ROLLBACK;
```

数据库事务是由数据库系统保证的，我们只需要根据业务逻辑使用它就可以。

## 事务四大特性之一：隔离级别

对于两个并发执行的事务，如果涉及到操作同一条记录的时候，可能会发生问题。因为并发操作会带来数据的不一致性，包括脏读、不可重复读、幻读等。**数据库系统提供了隔离级别来让我们有针对性地选择事务的隔离级别，避免数据不一致的问题**。

SQL标准定义了4种隔离级别，分别对应可能出现的数据不一致的情况：
- 读未提交：read uncommitted
    - Read Uncommitted是隔离级别最低的一种事务级别，这种级别一般是在理论上存在，**数据库隔离级别一般都高于该级别**。在这种隔离级别下，**一个事务会读到另一个事务更新后但未提交的数据**，如果另一个事务回滚，那么当前事务读到的数据就是脏数据，这就是脏读(Dirty Read)。
- 读已提交：read committed
    - 这种隔离级别高于读未提交
    - 事物A和事物B，**事物A提交的数据，事物B才能读取到**，不能读取另一个事务更新数据后**未提交**的数据。这种级别可以避免“脏数据”。
    - 在Read Committed隔离级别下，一个事务可能会遇到**不可重复读**(Non Repeatable Read)的问题。
    - 不可重复读是指，在一个事务内，**多次读同一数据**，在这个事务读数据还没有结束时，如果**另一个事务恰好修改了这个数据**，那么，在第一个事务中，**两次读取的数据就可能不一致**。

- 可重复读：repeatable read
    - MySQL的Innodb的默认事务隔离级别是重复读
    - 这种隔离级别高于读已提交
    - 可重复读确保同一事务的多个实例在并发读取数据时，会看到同样的数据行。这种隔离级别可以避免“不可重复读取”，达到可重复读取。
    - 整个事务过程中，对同一笔数据的读取结果是相同的，不管其他事务是否在对共享数据进行更新，也不管更新提交与否。
    - 事务A和事务B，**事务A提交之后的数据，事务B读取不到**。事务A更新了数据，并进行了提交，在事务B中进行查询，发现**查询到的还是事务A更新之前的数据(读已提交则会查询到更新后的数据，导致不可重复)**。
    - 会导致“幻像读”：在一个事务中，第一次查询某条记录，发现没有，但是，当试图**更新这条不存在的记录**时，竟然能成功，并且，再次读取同一条记录，它就神奇地出现了。

- 串行化：serializable
    - Serializable是最严格的隔离级别。在Serializable隔离级别下，**所有事务按照次序依次执行**，因此，脏读、不可重复读、幻读都不会出现。
    - 虽然Serializable隔离级别下的事务具有最高的安全性，但是，**由于事务是串行执行，所以效率会大大下降**，应用程序的性能会急剧降低。如果没有特别重要的情景，一般都不会使用Serializable隔离级别。

如果使用锁机制来实现这两种隔离级别，在可重复读中，该sql第一次读取到数据后，就将这些数据加锁，其它事务无法修改这些数据，就可以实现可重复读了。

但这种方法却无法锁住insert的数据，所以**当事务A先前读取了数据，或者修改了全部数据，事务B还是可以insert数据提交，这时事务A就会发现莫名其妙多了一条之前没有的数据，这就是幻读**，不能通过行锁来避免。需要Serializable隔离级别，读用读锁，写用写锁，读锁和写锁互斥，这么做可以有效的避免幻读、不可重复读、脏读等问题，但会极大的降低数据库的并发能力。

<div align=center><img src=Pictures\事务隔离级别.png></div>
