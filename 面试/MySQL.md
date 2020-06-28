# MySQL中索引是用什么实现的

## B树

B-树就是B树（多路查找树）；

数据库索引为什么使用树结构存储？树的查询效率高；

为什么索引没有使用二叉查找树来是实现？

二叉查找树查询的时间复杂度$O(logN)$；现实问题：磁盘IO；

数据库索引是存储在磁盘上的，当数据量比较大的时候，索引的大小可能有几个G。当利用索引查询的时候，不能把整个索引全部加载到内存里。只能逐一加载每一个磁盘页，**磁盘页对应着索引树的结点**。

如果利用二叉查找树作为索引结构，假设树的高度是4，查找的值是10：

第一次磁盘IO：
<div align=center><img src=Pictures\MySQL_第一次磁盘IO.jpg width=60%></div>

第二次磁盘IO：
<div align=center><img src=Pictures\MySQL_第二次磁盘IO.jpg width=60%></div>

第三次磁盘IO：
<div align=center><img src=Pictures\MySQL_第三次磁盘IO.jpg width=60%></div>

第四次磁盘IO：
<div align=center><img src=Pictures\MySQL_第四次磁盘IO.jpg width=60%></div>

最坏的情况下，磁盘IO次数等于索引树的高度。

为了减少磁盘IO次数，需要把原本“瘦高”的树结构变得“矮胖”，就是B-树的特征之一。

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

B-树在查询中的比较次数其实不比二叉查找树少，尤其当单一结点中的元素数量很多时。可是相比磁盘IO的速度，内存中的比较耗时几乎可以忽略(B-树一个结点可以存储多个元素，树高较低)。所以只要树的高度足够低，IO次数足够少，就可以提升查找性能。相比之下，结点内部元素多一些也没关系，仅仅是多了几次内存交互，只要不超过磁盘页的大小即可。


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

在数据库的**聚集索引**(Clustered Index)中，叶子节点直接包含卫星数据。在**非聚集索引**(NonClustered Index)中，叶子节点带有指向卫星数据的指针。


### 查询

#### 单元素查询

在单元素查询时，B+树会自顶向下逐层查找结点，最终找到匹配的叶子结点（查找元素3）：

第一次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第一次磁盘IO.jpg width=70%></div>

第二次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第二次磁盘IO.jpg width=70%></div>

第三次磁盘IO：
<div align=center><img src=Pictures\MySQL_B+树第三次磁盘IO.jpg width=70%></div>

B+树的中间结点没有卫星数据，所以同样大小的磁盘页可以容纳更多的结点元素。这就意味着，数据量相同的情况下，B+树的结构比B-树更加“矮胖”，因此查询时IO次数也更少。

B+树的查询必须最终查找到叶子结点，而B-树只要找到匹配元素即可，无论匹配元素处于中间结点还是叶子结点。因此，**B-树的查找性能不稳定**，最好情况是只查根结点，最坏情况是查到叶子结点。而**B+树的每一次查找都是稳定的**。

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

1. 单一节点存储更多的元素，使得查询的IO次数更少。
2. 所有查询都要查找到叶子节点，查询性能稳定。
3. 所有叶子节点形成有序链表，便于范围查询。


# 什么是MySQL索引

https://zhuanlan.zhihu.com/p/117419077
http://blog.codinglabs.org/articles/theory-of-mysql-index.html