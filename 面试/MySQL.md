参考资料：
https://blog.csdn.net/tongdanping/article/details/79878302


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

1. 有k个子树的中间节点包含有k个元素（B树中是k-1个元素），每个元素不保存数据，只用来索引，所有数据都保存在叶子节点。

2. 所有的叶子结点中包含了全部元素的信息，及指向含这些元素记录的指针，且叶子结点本身依关键字的大小自小而大顺序链接。

3. 所有的中间节点元素都同时存在于子节点，在子节点元素中是最大（或最小）元素。

<div align=center><img src=Pictures\MySQL_B+树.jpg width=70%></div>

每一个父结点的元素都出现在子结点中，是子结点的最大（或最小）元素；根结点元素8是子结点(2 5 8)里最大元素，也是叶子结点(6 8)最大的元素；根结点元素15是子结点(11 15)里最大元素，也是叶子结点(13 15)最大的元素。

根结点的最大元素也就是整个B+树最大元素。无论插入删除多少元素，始终要保持最大元素在根结点。

### 卫星数据

卫星数据指的是**索引元素所指向的数据记录**，比如数据库中的某一行。

在**B-树**中，无论中间结点还是叶子结点，都带有卫星数据：

<div align=center><img src=Pictures\卫星数据.jpg width=70%></div>

在B+树中，只有叶子结点带有卫星数据，其余中间结点仅仅是索引，没有任何数据关联。

<div align=center><img src=Pictures\卫星数据1.jpg width=70%></div>

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

1. 单一节点存储更多的元素，使得**查询的IO次数更少**。
2. 所有查询都要查找到叶子节点，**查询性能稳定**。
3. 所有叶子节点形成有序链表，**便于范围查询**。


# 什么是MySQL索引

假设有一张数据表employee(员工表)，该表有三个字段（列），分别是name、age和address。假设表employee有上万行数据，现在需要从这个表中查找出所有名字是`ZhangSan`的雇员信息，你会快速的写出SQL语句：

`select name,age,address from employee where name='ZhangSan';`

如果数据库还没有索引这个东西，一旦我们运行这个SQL查询，查找名字为ZhangSan的雇员的过程中，**数据库不得不在employee表中的每一行查找并确定雇员的名字（name）是否为‘ZhangSan’**。

由于想要得到每一个名字为ZhangSan的雇员信息，在查询到第一个符合条件的行后，不能停止查询，因为可能还有其他符合条件的行，所以必须一行一行的查找直到最后一行——这就意味**数据库不得不检查上万行数据才能找到所有名字为ZhangSan的雇员**。这就是所谓的**全表扫描**，显然这种模式效率太慢。

使用索引的全部意义就是：**通过缩小一张表中需要查询的记录/行的数目来加快搜索的速度**。

在关系型数据库中，索引是一种单独的、物理的**对数据库表中一列或多列的值进行排序的一种存储结构**，它是某个表中一列或若干列值的集合和相应的指向表中物理标识这些值的数据页的逻辑指针清单。意思是索引的作用相当于图书的目录，可以根据目录中的页码快速找到所需的内容。

一个索引是存储的表中一个特定列的值数据结构。**索引是在表的列上创建**。要记住的关键点是**索引包含一个表中列的值，并且这些值存储在一个数据结构中**。请牢记这一点：**索引是一种数据结构**。一个好的数据库表设计，从一开始就应该考虑添加索引，而不是到最后发现慢SQL了，影响业务了才来补救。

MySQL支持诸多**存储引擎**，而各种存储引擎对索引的支持也各不相同，因此MySQL数据库支持多种索引类型，如BTree索引，哈希索引，全文索引等等。主要使用的还是B+Tree索引。

## 索引的本质

MySQL官方对索引的定义为：索引(Index)是帮助MySQL高效获取数据的**数据结构**。

数据库**查询**是数据库的最主要功能之一。我们都希望查询数据的速度能尽可能的快，因此数据库系统的设计者会从**查询算法**的角度进行优化。

最基本的查询算法当然是**顺序查找**（linear search），这种复杂度为$O(n)$的算法在数据量很大时显然是糟糕的，好在计算机科学的发展提供了很多更优秀的查找算法，例如**二分查找**（binary search）、**二叉树查找**（binary tree search）等。如果稍微分析一下会发现，每种查找算法都只能应用于特定的数据结构之上，例如**二分查找要求被检索数据有序**，而**二叉树查找只能应用于二叉查找树上**。

但是**数据本身的组织结构不可能完全满足各种数据结构**（例如，理论上不可能同时将两列都按顺序进行组织），所以，在数据之外，数据库系统还维护着满足特定查找算法的数据结构，**这些数据结构以某种方式引用（指向）数据**，这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。

索引是一个排序的列表，在这个列表中存储着**索引的值**和包含这个值的数据所在行的**物理地址**，在数据十分庞大的时候，索引可以大大加快查询的速度，这是因为使用索引后可以**不用扫描全表**来定位某行的数据，而是**先通过索引表找到该行数据对应的物理地址然后访问相应的数据**。

一种可能的索引方式：

<div align=center><img src=Pictures\MySQL_索引.png width=70%></div>

左边是数据表，一共有两列七条记录，最左边的是数据记录的**物理地址**（注意逻辑上相邻的记录在磁盘上也并不是一定物理相邻的）。为了加快Col2的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含**索引键值**和**一个指向对应数据记录物理地址的指针**，这样就可以运用二叉查找在$O(logn)$的复杂度内获取到相应数据。


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

优势：可以快速检索，减少I/O次数，加快检索速度；根据索引分组和排序，可以加快分组和排序；

劣势：索引本身也是表，因此会占用存储空间，一般来说，索引表占用的空间的数据表的1.5倍；索引表的维护和创建需要时间成本，这个成本随着数据量增大而增大；构建索引会降低数据表的修改操作（删除，添加，修改）的效率，因为在修改数据表的同时还需要修改索引表；




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