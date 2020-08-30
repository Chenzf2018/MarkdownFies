# 基础

## 项目简介

本项目是基于`Swing`和`JDBC`开发的**图形界面桌面应用**，涵盖了J2SE的绝大部分基础知识，通过这个项目能运用和锻炼几乎大部分的J2SE知识和技能。

涉及到如下内容：

- 基础内容：
    面向对象，字符串数字，日期

- 中级内容：
    异常，集合，JDBC，反射机制，I/O，Swing，利用`TableModel`更新数据，图形界面的皮肤

- 高级内容：
    图表chart动态生成，数据库的备份与恢复，自定义圆形进度条

- 软件设计思想：
    单例模式，面板类与监听器类松耦合，Entity层设计，DAO层设计，Service层设计

- 业务常见处理手法：
    CRUD操作，配置信息，配置信息初始化，报表生成，一对多关系，多对一关系

## 可执行文件

因为本项目需要基于`mysql`数据库才能运行，所以需要先安装`mysql`，并且把`root`账号密码设置为`admin`。注：要运行这个exe，需要java1.8或者更高版本以上环境。

可以下载`hutubill.rar`，解压后点击`run.bat`即可运行（无需安装数据库）。


## 功能预览

## 开发流程

一、表结构设计：把表有哪些字段搞清楚，表与表之间的关系理顺。同时还要校验这样的表结构，是否能够支撑功能上的需要。

二、原型设计：在开始正式的功能开发之前，要进行原型设计。什么是原型设计呢？简单说，就是先把界面做出来。界面上的数据，都是假数据，并不是从数据库中读取的真实数据。

三、实体类与DAO的设计：在开始功能开发之前，首先要设计实体类与DAO。相关的数据库操作，都经由DAO来完成。

四、功能开发

首先是确定多层结构，基于事件驱动，规划DAO层，Service层。 并且在开发过程中，演示重构并解释重构的理由和好处。

接着是开发顺序。各个模块之间是互相依赖的，有的需要先行开发，有的模块必须建立在其他模块的基础上才可以执行。

最后，引用第三方的包。比如动态生成chart图片，演示如何使用这些API。

<div align=center><img src=Pictures\功能开发.png width=40%></div>


# 表结构设计

## 数据库与表

### 创建数据库

**步骤一：创建数据库**

数据库命名为`hutubill`，在程序中的JDBC相关代码，都需要连接这个数据库。

```SQL
mysql> CREATE DATABASE hutubill;  # 创建数据库
Query OK, 1 row affected (0.01 sec)

mysql> USE hutubill;  # 切换数据库
Database changed
```

### 创建表

**步骤二：确定需要的表**

根据业务上的需要，一共要3个表：

- 配置表信息`config`
    用于保存每月预算和Mysql的安装路径( 用于备份还原用)

- 消费分类表`category`
    用于保存消费分类，比如餐饮，交通，住宿

- 消费记录表`record`
    用于存放每一笔的消费记录，并且会用到消费分类

**步骤三：配置信息表**

配置信息表`config`有如下字段：
- `id`主键：每个表都有一个主键，类型是`int`；
  - 表中**每一行都应该有可以唯一标识自己的一列**（或一组列）
- `key_`：配置信息按照键值对的形式出现 ，类型是`varchar(255)`；
- `value`：配置信息的值，类型是`varchar(255)`。

```SQL
CREATE TABLE config(
    id INT,
    key_ VARCHAR(225),
    value VARCHAR(225)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

注:
1. 键值对
进一步解释一下键值对，比如要存放每个月的预算，则需要在config表中增加一条记录，key="budget" value="500"，就表示预算是500。

2. varchar(255) 表示变长字符，如果实际存放只有30个字符，那么在数据库中只占用30的空间，最多占用255。

3. **`key`是关键字，不适合用于作为字段名，所以在`key`后面加了一个下划线`key_`就不会有任何问题了**，识别性一样很好，一眼就知道这个字段是干什么用的。

4. `ENGINE=InnoDB`：MySQL有多种存储引擎，MyISAM和InnoDB是其中常用的两种， 他们之间的区别很多。这里使用`ENGINE=InnoDB`是因为后续要使用的外键约束只有在InnoDB中才生效。

5. `DEFAULT CHARSET=utf8;`表示该表按照UTF-8的编码存放中文。

**步骤四：消费分类表category**

消费分类表`category`有如下字段：
- `id`主键：每个表都有一个主键，类型是`int`；
- `name`分类的名称，类型是`varchar(255)`。

```SQL
CREATE TABLE category(
    id INT,
    name VARCHAR(225)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

**步骤五：消费记录表record**

消费记录表`record`有如下字段：
- `id`主键，每个表都有一个主键，类型是int；
- `spend`本次花费，类型是int；
- `cid`对应的消费分类表的中记录的id，类型是int；
- `comment`备注，比如分类是娱乐，但是你希望记录更详细的内容，那么就存放在这里。
- `date`日期，本次记录发生的时间。

```SQL
CREATE TABLE record(
    id INT,
    spend INT,
    cid INT,
    comment VARCHAR(225),
    date DATE
) ENGINE=InnoDB DEFAULT CAHRSET=utf8;
```

**利用脚本创建表：**

编写脚本，然后在命令行运行命令：
`mysql> source D:\HutuBill\hutubillTables.sql`，
就可以自动创建数据库，并且在数据库下创建表，以及必要的初始化数据。对MySQL数据库做的所有修改，都会保存下来。如果你希望恢复到初始状态，可以再次运行该脚本。

`hutubillTables.sql`

```SQL
-- 如果hutubill数据库不存在，就创建
CREATE DATABASE IF NOT EXISTS butubill;

-- 切换到hutubill数据库
USE hutubill;

-- 如果存在删除表
DROP TABLE IF EXISTS config;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS record;

-- 创建表

CREATE TABLE config(
    id INT,
    key_ VARCHAR(225),
    value VARCHAR(225)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE category(
    id INT,
    name VARCHAR(225)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE record(
    id INT,
    spend INT,
    cid INT,
    comment VARCHAR(225),
    date DATE
) ENGINE=InnoDB DEFAULT CAHRSET=utf8;

-- OK:
SELECT 'ok' as 'result:';
```

## 表关系

表和表之间的关系，常见的有一对多，多对一，多对多。

**分析表之间的关系：**

消费分类表和消费记录表之间是有关系的，那么如何分析他们之间的关系呢？

分类表有多条数据，记录表也有多条数据，那么他们之间的关系是多对多吗？

其实不然，正确的分析办法是这样的:

- **一条记录数据，只能对应一种分类**
    比如说这次消费是吃火锅，那么只能对应餐饮，不能同时又对应住宿。
    那么就在分类这边写下符号`一`

- **一条分类数据，对应多条记录数据**
    比如餐饮下，有多次消费记录，火锅，冒菜，盒饭。
    那么就在记录这边写下符号`多`

所以`分类`和`记录`之间的关系是`一对多`，又叫`ONE TO MANY 1：M`。

<div align=center><img src=Pictures\表关系.png width= 80%></div>


### 确定外键

确定好分类和记录之间的关系之后，就需要在数据库表结构中**通过外键表现出来**：外键用来**建立主表与从表的关联关系，为两个表的数据建立连接**，约束两个表中数据的一致性和完整性。

**外键是加在`多`表(`record`表)中的，也就是加在Record表中的，在这里就是`cid`**：

```SQL {.line-numbers highlight=4}
CREATE TABLE record(
    id INT,
    spend INT,
    cid INT,
    comment VARCHAR(225),
    date DATE
) ENGINE=InnoDB DEFAULT CAHRSET=utf8;
```

`cid`指向了`category`表的主键id。

## 约束

### 主键

所有的表都有一个**主键id**，但是这只是语意上的(我们认为的)，为了让数据库把id识别为主键，需要加上**主键约束**。

主键约束自带**非空**和**唯一**属性，即不能插入空，也不能重复。如果仅仅通过语句`id INT,`来创建，仅表示普通字段，不满足非空和唯一。故要加上主键。

分析这一条增加约束的SQL语句：
`alter table category add constraint pk_category_id primary key (id);`

- `alter table category`：表示**修改表**`category`；
- `add constraint`：**增加约束**；
- `pk_category_id`：**约束名称**；`pk`是`primary key`的缩写；`category`是表名；`id`表示约束加在`id字段`上。
    约束名称是可以自己定义的，你可以写成abc，但是尽量使用好的命名，使得一眼就能够看出来这个约束是什么意思。 能够降低维护成本。
- `primary key`：**约束类型**是主键约束；
- `(id)`：表示约束加在id字段上。

对三张表都加上主键约束：

```SQL
ALTER TABLE category ADD CONSTRAINT pk_category_id PRIMARY KEY (id);
ALTER TABLE config ADD CONSTRAINT pk_config_id PRIMARY KEY (id);
ALTER TABLE record ADD CONSTRAINT pk_record_id PRIMARY KEY (id);
```

#### 查看表的结构命令

```
mysql> DESC category;
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | int          | NO   | PRI | NULL    |       |
| name  | varchar(225) | YES  |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

### 设置id为自增长

设置id为自增长是常用的插入数据策略。 换句话说，插入消费分类数据的时候，只用提供分类名称即可，不需要自己提供id，**id由数据库自己生成**。

不同的数据库采用的自增长方式是不一样的，比如Oracle使用Sequence来实现，而MySQL就使用`AUTO_INCREMENT`来实现。

`alter table category change id id int auto_increment;`

- `alter table category`：表示修改表category；
- `change id`：表示**修改字段id**；
- `id int auto_increment;`：修改后的id是int类型，并且是`auto_increment`(修改之前仅仅是int类型，没有auto_increment)。

以下SQL语句，分别为不同的表设置自增长：

```SQL
ALTER TABLE category CHANGE id id INT AUTO_INCREMENT;
ALTER TABLE config CHANGE id id INT AUTO_INCREMENT;
ALTER TABLE record CHANGE id id INT AUTO_INCREMENT;
```

修改前：
```
mysql> DESC category;
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | int          | NO   | PRI | NULL    |       |
| name  | varchar(225) | YES  |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

修改后：

```
mysql> DESC category;
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int          | NO   | PRI | NULL    | auto_increment |
| name  | varchar(225) | YES  |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+
```

### 外键

外键约束的作用是**保持数据的一致性**。

比如增加一条消费记录，金额是500，cid是5。但是cid=5在分类表category中找不到对应的数据，那么这就破坏了数据的一致性，会带来一系列的后续问题，比如根据分类进行统计，就会找不到这条数据。

增加外键约束之前**首先确定record表的外键是`cid`，指向了category表的id主键**。所以增加外键之前，**必须把category的id字段设置为主键**。从而保证cid=5的数据在category中只能找到一条，而不是找到多条。

以下是增加外键约束的SQL：
`alter table record add constraint fk_record_category foreign key (cid) references category(id)`

- `alter table record`：修改表record；
- `add constraint`：增加约束；
- `fk_record_category`：**约束名称**`fk_record_category` 
`fk`是`foreign key`的缩写；`record_category`表示是**从record表指向category表的约束**。 
    与主键一样，约束名称也是可以自己定义的，比如写成abc. 不过依然建议使用可读性好的命名方式。
- `foreign key`：约束类型，外键；
- `(cid) references category(id)`：本表record的字段`cid`指向category表的字段`id`。

```SQL
ALTER TABLE record ADD CONSTRAINT fk_record_category FOREIGN KEY (cid) REFERENCES category(id);
```

修改前：
```
mysql> DESC record;
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int          | NO   | PRI | NULL    | auto_increment |
| spend   | int          | YES  |     | NULL    |                |
| cid     | int          | YES  |     | NULL    |                |
| comment | varchar(225) | YES  |     | NULL    |                |
| date    | date         | YES  |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+
```

修改后：
```
mysql> DESC record;
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int          | NO   | PRI | NULL    | auto_increment |
| spend   | int          | YES  |     | NULL    |                |
| cid     | int          | YES  | MUL | NULL    |                |
| comment | varchar(225) | YES  |     | NULL    |                |
| date    | date         | YES  |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+
```

### 代码整合

```SQL
create database hutubill;

use  hutubill;
 
CREATE TABLE config (
  id int AUTO_INCREMENT,
  key_ varchar(255) ,
  value varchar(255) ,
  PRIMARY KEY (id)
)  DEFAULT CHARSET=utf8;
 
CREATE TABLE category (
  id int AUTO_INCREMENT,
  name varchar(255) ,
  PRIMARY KEY (id)
)  DEFAULT CHARSET=utf8;
 
CREATE TABLE record (
  id int AUTO_INCREMENT,
  spend int,
  cid int,
  comment varchar(255) ,
  date Date,
  PRIMARY KEY (id),
  CONSTRAINT `fk_record_category` FOREIGN KEY (`cid`) REFERENCES `category` (`id`)
)  DEFAULT CHARSET=utf8;

```

#### 查看创建表的代码命令

```
mysql> SHOW CREATE TABLE record;
+--------+---------------------------+
| Table  | Create Table |
+--------+---------------------------+
| record | CREATE TABLE `record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `spend` int DEFAULT NULL,
  `cid` int DEFAULT NULL,
  `comment` varchar(225) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_record_category` (`cid`),
  CONSTRAINT `fk_record_category` FOREIGN KEY (`cid`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
+--------+-----------------------------+
```


# 原型（基础）

原型这个章节将从最粗陋的`new JFrame`开始做起，并分析其弊端。 然后逐一演化和重构出不同形式的开发界面的设计方法和思路，以及阐述这样设计带来的好处。

虽然`Swing`界面设计在Java软件开发过程中用得比重并不高，但是通过学习这个演化和重构的过程，本身就是对Java设计思想的一种学习。

设计思想是在各个层面都会用到，比如J2SE中也会用到一些类似的设计思想，都相通的。

原型这个模块涵盖包的设计与规划，工具类的抽象，自定义界面类，各种面板类等等。

## 粗陋的JFrame

**原型概念**

什么是原型设计呢？ 

简单说，就是先把界面做出来。界面上的数据，都是假数据，并不是从数据库中读取的真实数据。

为什么要这么做呢？ 

有了界面，才直观，你才会更有感觉，也才能更清楚各个功能之间怎么实现。更重要的是，有了界面，才能更有效的和客户沟通，哪些功能需要修改，哪些功能可以删减。

**直接在main方法中new JFrame**

```java
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HutuMainFrame {
    public static void main(String[] args){
        JFrame jFrame = new JFrame();
        jFrame.setSize(500, 450);
        jFrame.setTitle("记账本");
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JToolBar jToolBar = new JToolBar();
        JButton jButtonSpend = new JButton("消费一览");
        JButton jButtonRecord = new JButton("记一笔");
        JButton jButtonCategory = new JButton("消费分类");
        JButton jButtonReport = new JButton("月消费报表");
        JButton jButtonConfig = new JButton("设置");
        JButton jButtonBackup = new JButton("备份");
        JButton jButtonRecover = new JButton("恢复");

        jToolBar.add(jButtonSpend);
        jToolBar.add(jButtonRecord);
        jToolBar.add(jButtonCategory);
        jToolBar.add(jButtonReport);
        jToolBar.add(jButtonConfig);
        jToolBar.add(jButtonBackup);
        jToolBar.add(jButtonRecover);

        jFrame.setLayout(new BorderLayout());
        jFrame.add(jToolBar, BorderLayout.NORTH);
        jFrame.add(new JPanel(), BorderLayout.CENTER);
        jFrame.setVisible(true);

        jButtonSpend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButtonRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButtonCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // 教程中没有
        jButtonReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButtonConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButtonBackup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButtonRecover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
```

<div align=center><img src=Pictures\JFrame.png width=70%></div>


**问题**

当界面代码变得复杂之后，就出现这么一个问题。仅仅用于编写界面的代码就会很长，可读性变差，难以维护，而且有各种**匿名内部类**，非常不好定位。

`jFrame.add(new JPanel(), BorderLayout.CENTER);`
这是下方用于显示各种功能界面的JPanel。目前这是一个空白Panel, 为了将来显示各种功能，可以想象，这一块的代码会变得非常的复杂

```
jButtonSpend.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

    }
});
...
```
这是**给各个按钮加监听器**，目前都还是没有任何实际功能的空白监听器，一旦将来把各种功能都加上去之后，代码会变得非常冗长和难以阅读。

## 界面包的规划

由于预见到了直接在main中编写JFrame带来的代码的冗长和难以维护性的增加。我们需要把界面相关的类，独立出来，并且放在不同的包下面进行管理和维护。

- Frame
    整个程序**只有一个主`Frame`**，所以把这个类规划到包`gui.frame`下；

- Panel
    **JFrame本身有一个Panel**，然后**每一个功能模块都有一个Panel**，所以把这些Panel规划到`gui.panel`下面去；
    (原来写法`jFrame.add(new JPanel(), BorderLayout.CENTER);`)

- Listener
    把所有的**监听器**，都做成独立的类，**实现ActionListener接口**，并放在`gui.listener`包下；

- Model
    Model用于**存放数据**，在这个项目中会用到`TableModel`和`ComboBoxModel`，放在`gui.model`包下。

<div align=center><img src=Pictures\包结构.png width=50%></div>


gui包结构：
<div align=center><img src=Pictures\gui包结构.png width=50%></div>

## **单例的面板类

**监听器访问组件**

在本项目中，各种**按钮监听器**的主要作用是**获取组件的值**和**修改组件的值**。这就涉及到了在`Listener`中如何**访问组件**的问题。

那么如何使得监听器可以方便得获取组件呢？ 这就需要用到两个设计手段
1. 在面板类中，把组件声明为`public`的属性；
2. 把面板类设计为**单例模式**。


**在面板类中，把组件声明为public的属性**

以SpendPanel为例子，其中有各种标签对象，把这些标签对象设计成`public`的属性，那么只要能够获取SpendPanel实例，就能很方便的获取这些组件：

```
SpendPanel p = new SpendPanel();
p.vMonthSpend.setText("￥3333");
```

```java
package gui.panel;

import javax.swing.JLabel;

public class SpendPanel {
    JLabel lMonthSpend = new JLabel("本月消费");
    JLabel lTodaySpend = new JLabel("今日消费");
    JLabel lAvgSpendPerDay = new JLabel("日均消费");
    JLabel lMonthLeft = new JLabel("本月剩余");
    JLabel lDayAvgAvailable = new JLabel("日均可用");
    JLabel lMonthLeftDay = new JLabel("距离月末");

    JLabel vMonthSpend = new JLabel("￥2300");
    JLabel vTodaySpend = new JLabel("￥25");
    JLabel vAvgSpendPerDay = new JLabel("￥120");
    JLabel vMonthAvailable = new JLabel("￥2084");
    JLabel vDayAvgAvailable = new JLabel("￥389");
    JLabel vMonthLeftDay = new JLabel("15天");
}
```

<div align=center><img src=Pictures\SpendPanel.png width=70%></div>

**把面板类设计为单例模式**

接着就是如何获取这些面板类呢？ 

通过分析，其实，**面板类的实例只需要有一个就行了**。

比如SpendPanel，只需要一个实例就可以了，**不需要每次显示这个面板的时候都创建新的实例**。不仅是不需要，也不应该每一次都创建新的实例。

所以把SpendPanel设计成**单例模式**就能很好的解决这个问题。

而单例模式有很多种写法，像**饿汉式单例模式**，**懒汉式单例模式**等等，其不同的方式总计有八种，甚至更多，这里我们进一步简化，活学活用，使用最简单的单例模式。**直接声明一个SpendPanel类型的静态属性，并指向当前实例**。

`public static SpendPanel instance = new SpendPanel();`

这样的写法虽然不常见，但是刚好满足我们的需要。

```java
package gui.panel;

import javax.swing.JLabel;

public class SpendPanel {
    public static SpendPanel instance = new SpendPanel();

    JLabel lMonthSpend = new JLabel("本月消费");
    JLabel lTodaySpend = new JLabel("今日消费");
    JLabel lAvgSpendPerDay = new JLabel("日均消费");
    JLabel lMonthLeft = new JLabel("本月剩余");
    JLabel lDayAvgAvailable = new JLabel("日均可用");
    JLabel lMonthLeftDay = new JLabel("距离月末");

    JLabel vMonthSpend = new JLabel("￥2300");
    JLabel vTodaySpend = new JLabel("￥25");
    JLabel vAvgSpendPerDay = new JLabel("￥120");
    JLabel vMonthAvailable = new JLabel("￥2084");
    JLabel vDayAvgAvailable = new JLabel("￥389");
    JLabel vMonthLeftDay = new JLabel("15天");

    private SpendPanel(){
        
    }
}
```

**监听器通过单例模式的面板访问组件**

监听器就可以通过单例模式的面板访问组件了：

```
SpendPanel p = SpendPanel.instance;
p.vMonthSpend.setText("￥3333");
```

这样做就非常的方便。**任何监听器，要访问任何面板上的任何组件，都会变得非常的便捷。与直接在main方法中`new JFrame`中使用传统的内部类方式添加监听器相比，代码更容易维护**。

这一块的理解在大家接触到后面的功能模块的时候，会感觉更明显。


## 面板居中

**Swing并没有提供一种可以很简单就居中的布局器**，但是这样的布局器又很常见，所以在这里就自己开发一个专门用于居中的面板。

首先设计一个类，继承了JPanel：

- 有3个属性
    ```
    private double rate;  //拉伸比例
    private JComponent c;  //显示的组件
    private boolean strech;  //是否拉伸
    ```

- `show(JComponent c)`
    使用show方法显示组件，show方法中的思路是：先把这个容器中的组件都移出，然后把新的组件加进来，并且调用`updateUI`进行界面渲染。
    `updateUI`会导致swing去调用`repaint()`方法。

- `repaint()`
    在repaint方法中，就会使用绝对定位的方式把组件放在中间位置。
    如果strech是true，就会根据整个容器的大小，设置组件的大小，达到拉伸的效果
    如果strech是false, 就使用组件的`preferredSize`，即非拉伸效果。

- 构造方法：`public CenterPanel(double rate, boolean strech) `
    `rate`表示拉伸比例，1就是填满，0.5就是填一半。
    `strech`表示是否拉伸。

```java
package util;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Component;

public class CenterPanel extends JPanel
{
    private double rate;  //拉伸比例
    private JComponent c;  //显示的组件
    private boolean stretch;  //是否拉伸

    public CenterPanel(double rate, boolean stretch) {
        this.setLayout(null);
        this.rate = rate;
        this.stretch = stretch;
    }

    public CenterPanel(double rate) {
        this(rate, true);
    }

    public void repaint() {
        if (null != c) {
            Dimension containerSize = this.getSize();
            Dimension componentSize = c.getPreferredSize();

            if(stretch)
                c.setSize((int) (containerSize.width * rate), (int) (containerSize.height * rate));
            else
                c.setSize(componentSize);

            c.setLocation(containerSize.width / 2 - c.getSize().width / 2, containerSize.height / 2 - c.getSize().height / 2);
        }
        super.repaint();
    }

    public void show(JComponent p) {
        this.c = p;
        Component[] cs = getComponents();
        for (Component c : cs) {
            remove(c);
        }
        add(p);
        this.updateUI();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(200, 200);
        f.setLocationRelativeTo(null);
        CenterPanel cp = new CenterPanel(0.85,true);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        JButton b  = new JButton("abc");
        cp.show(b);
    }
}
```

<div align=center><img src=Pictures\面板居中.png width=40%></div>


## GUIUtil

在开发图形界面的过程中，有很多功能在各个地方都会用到，并且重复使用的概率比较高。比如判断一个输入框是否是数字，是否是空，是否为0等等类似的，所以把这些使用比较普遍的功能，抽象到一个工具类里。这样以后再用的时候，就直接调用就可以了，而不需要每次都单独再写一遍。这样可以节约开发时间，提高效率，降低维护成本，代码看上去也更加简洁。

工具类的名字，往往都会以`Util`结尾，`Util`是`Utility`的缩写，`Utility`是工具的意思。

`GUIUtil`就表示专门用在图形界面上的工具类。

下面是整个类的代码：

```java
package util;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIUtil {
    private static String imageFolder = "D:\\HutuBill\\hutubill\\img";

    /**
     * 给按钮设置图标和文本以及提示文字
     * @param b
     * @param fileName
     * @param tip
     */
    public static void setImageIcon(JButton b, String fileName, String tip) {
        ImageIcon i = new ImageIcon(new File(imageFolder, fileName).getAbsolutePath());
        b.setIcon(i);
        b.setPreferredSize(new Dimension(61, 81));
        b.setToolTipText(tip);
        b.setVerticalTextPosition(JButton.BOTTOM);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setText(tip);
    }

    /**
     * 给多个组件设置前景色
     * @param color
     * @param cs
     */
    public static void setColor(Color color, JComponent... cs) {
        for (JComponent c : cs) {
            c.setForeground(color);
        }
    }

    /**
     * 快速显示一个面板的内容
     * @param p
     * @param stretchRate 拉伸比例1表示满屏幕
     */
    public static void showPanel(JPanel p, double stretchRate) {
        GUIUtil.useLNF();
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        CenterPanel cp = new CenterPanel(stretchRate);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        cp.show(p);
    }

    public static void showPanel(JPanel p) {
        showPanel(p,0.85);
    }


    /**
     * 校验一个组件内容是否是数字格式
     * @param tf
     * @param input
     * @return
     */
    public static boolean checkNumber(JTextField tf, String input) {
        if (!checkEmpty(tf, input))
            return false;
        String text = tf.getText().trim();
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, input + " 需要是整数");
            tf.grabFocus();
            return false;
        }
    }

    /**
     * 校验一个组件的内容是否是零
     * @param tf
     * @param input
     * @return
     */
    public static boolean checkZero(JTextField tf, String input) {
        if (!checkNumber(tf, input))
            return false;
        String text = tf.getText().trim();

        if (0 == Integer.parseInt(text)) {
            JOptionPane.showMessageDialog(null, input + " 不能为零");
            tf.grabFocus();
            return false;
        }
        return true;
    }

    /**
     * 校验一个输入框内容是否是空
     * @param tf
     * @param input
     * @return
     */
    public static boolean checkEmpty(JTextField tf, String input) {
        String text = tf.getText().trim();
        if (0 == text.length()) {
            JOptionPane.showMessageDialog(null, input + " 不能为空");
            tf.grabFocus();
            return false;
        }
        return true;

    }

    public static int getInt(JTextField tf) {
        return Integer.parseInt(tf.getText());
    }

    public static void useLNF() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

**使用水晶皮肤**
Java提供很多种皮肤，但是大部分都比较难看，水晶皮肤还算是看得过去的一种。

注： 
`javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");`
这句话必须加在第一行才能保证生效。

下载需要的`jar`包，并放在`lib目录`(`D:\HutuBill\hutubill\lib`)下，然后**导入到项目中**。

把jar包导入到项目中，导包办法：
`project->properties->java build path->libaries->add external jars`

IDEA：右键lib目录->Add as Library... ，弹出个对话框，点击ok。


**showPanel**
快速显示一个面板的内容。本项目会规划一个一个的独立的面板Panel。但是面板本身不能独立显示出来，所以准备这么一个
showPanel方法，就可以很方便的显示这些面板了。显示的时候还用到了`居中面板`。

在`package test;`中准备了一个很简单的JPanel的测试文件，直接调用GUIUtil.showPanel()就可以显示出来了，在后面设计界面类的时候，用这种方式就可以很方便的进行测试。

```java
package test;

import util.GUIUtil;

import javax.swing.JPanel;
import javax.swing.JButton;

public class Test {
    public static void main(String[] args) {
        GUIUtil.useLNF();
        JPanel jPanel = new JPanel();
        jPanel.add(new JButton("按钮1"));
        jPanel.add(new JButton("按钮2"));
        GUIUtil.showPanel(jPanel);
    }
}
```

<div align=center><img src=Pictures\水晶按钮.png width=50%></div>


## ColorUtil

与GUIUtil一样，关于颜色也有很多重用的地方，都会被抽象到一个类里面来，方便共用。

ColorUtil定义了几种颜色：blueColor 淡蓝色；grayColor 灰色；backgroundColor 背景色；warningColor 警告红色。

```java
package util;

import java.awt.Color;

public class ColorUtil {
    public static Color blueColor = Color.decode("#3399FF");
    public static Color grayColor = Color.decode("#999999");
    public static Color backgroundColor = Color.decode("#eeeeee");
    public static Color warningColor = Color.decode("#FF3333");

    public static Color getByPercentage(int per) {
        if (per > 100)
            per = 100;
        int r = 51;
        int g = 255;
        int b = 51;
        float rate = per / 100f;
        r = (int) ((255 - 51) * rate + 51);
        g = 255 - r + 51;
        Color color = new Color(r, g, b);
        return color;
    }
}
```

`getByPercentage`根据进度显示不同的颜色。当进度是接近0的时候，就会显示绿色；当进度接近100的时候，就会显示红色。并在不同的颜色间过渡。


## CircleProgressBar

在开发这个项目的时候，需要用到一个环形进度条。但是Swing只有自带的，横向条状进度条，为了达到效果，就需要自己设计一个环形进度条。

自己开发一个组件涉及比较多的内容，包括画笔Graphics2D，画不同的形状，对`paint(Graphics g)`方法的理解。 这些需要一定的基础才可以做到。

在本项目中，这个知识不是重点，所以就不展开了。大家主要是掌握如何使用这个环形进度条。环形进度条，其实就是画一个有宽度的圆弧。

这个理解不了也没关系，掌握如何使用就行了，比如日期控件，大家也是主要掌握如何使用，而不是怎么去开发一个日期控件。等以后功力上升了，才来做这方面的研究和学习。现阶段还是放在如何使用，以及对整个项目的学习上。

```java
package util;

import java.awt.BasicStroke;
// import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// import javax.swing.JButton;
import javax.swing.JPanel;
// import javax.swing.SwingWorker;

public class CircleProgressBar extends JPanel {

    private int minimumProgress;
    private int maximumProgress;
    private int progress;
    private String progressText;
    private Color backgroundColor;
    private Color foregroundColor;

    public CircleProgressBar() {
        minimumProgress = 0;
        maximumProgress = 100;
        progressText = "0%";
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2d = (Graphics2D) g;
        // 开启抗锯齿
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        int fontSize = 0;
        if (getWidth() >= getHeight()) {
            x = (getWidth() - getHeight()) / 2 + 25;
            y = 25;
            width = getHeight() - 50;
            height = getHeight() - 50;
            fontSize = getWidth() / 8;
        } else {
            x = 25;
            y = (getHeight() - getWidth()) / 2 + 25;
            width = getWidth() - 50;
            height = getWidth() - 50;
            fontSize = getHeight() / 8;
        }
        graphics2d.setStroke(new BasicStroke(20.0f));
        graphics2d.setColor(backgroundColor);
        graphics2d.drawArc(x, y, width, height, 0, 360);
        graphics2d.setColor(foregroundColor);
        graphics2d.drawArc(x, y, width, height, 90,
                -(int) (360 * ((progress * 1.0) / (maximumProgress - minimumProgress))));
        graphics2d.setFont(new Font("黑体", Font.BOLD, fontSize));
        FontMetrics fontMetrics = graphics2d.getFontMetrics();
        int digitalWidth = fontMetrics.stringWidth(progressText);
        int digitalAscent = fontMetrics.getAscent();
        graphics2d.setColor(foregroundColor);
        graphics2d.drawString(progressText, getWidth() / 2 - digitalWidth / 2, getHeight() / 2 + digitalAscent / 2);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress >= minimumProgress && progress <= maximumProgress)
            this.progress = progress;
        if (progress > maximumProgress)
            this.progress = maximumProgress;

        this.progressText = String.valueOf(progress + "%");

        this.repaint();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.repaint();
    }
}
```

测试文件：

```java
package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import util.CircleProgressBar;
import util.ColorUtil;
import util.GUIUtil;

public class TestCircleProgressBar {
    public static void main(String[] args) {
        GUIUtil.useLNF();
        //面板
        JPanel p = new JPanel();

        //进度条组件
        CircleProgressBar cpb = new CircleProgressBar();
        cpb.setBackgroundColor(ColorUtil.blueColor);
        cpb.setProgress(0);

        //按钮
        JButton b = new JButton("点击");
        //添加组件
        p.setLayout(new BorderLayout());
        p.add(cpb, BorderLayout.CENTER);
        p.add(b, BorderLayout.SOUTH);

        //显示面板，启动程序
        GUIUtil.showPanel(p);

        //给按钮加监听
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 长耗时任务线程SwingWorker 来改动进度条
                new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        for (int i = 0; i < 100; i++) {
                            cpb.setProgress(i + 1);
                            cpb.setForegroundColor(ColorUtil.getByPercentage(i + 1));  // 获取不同进度的颜色
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                        return null;
                    }

                }.execute();

            }
        });

    }
}
```

<div align=center><img src=Pictures\圆形进度条.png width=70%></div>


## ChartUtil

在图表这个面板上，需要生成柱状图。而Swing本身不支持直接生成柱状图，需要借助第三方的jar包`chart.jar`。将其导入工程。

在`ChartUtil`中有一个`getImage`方法，返回一个模拟数据的柱状图表。然后把这个图表放在`JLabel`里，再把JLabel放在`JPanel`里，把JPanel显示出来，就可以看到图表的效果了。

```
JPanel p = new JPanel();
JLabel l = new JLabel();
Image img = ChartUtil.getImage(300, 200);
Icon icon = new ImageIcon(img);
l.setIcon(icon);
p.add(l);
GUIUtil.showPanel(p);
``` 

```java
package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.objectplanet.chart.BarChart;
import com.objectplanet.chart.Chart;

public class ChartUtil {

    public static int max(double[] sampleValues) {
        int max = 0;
        for (double v : sampleValues) {
            if (v > max)
                max = (int) v;
        }
        return max;
    }

    private static String[] sampleLabels() {
        String[] sampleLabels = new String[30];

        for (int i = 0; i < sampleLabels.length; i++) {
            if (0 == i % 5)
                sampleLabels[i] = String.valueOf(i + 1 + "日");
        }
        return sampleLabels;
    }

    public static Image getImage(int width, int height) {
        // 模拟样本数据
        double[] sampleValues = sampleValues();
        // 下方显示的文字
        String[] sampleLabels = sampleLabels();
        // 样本中的最大值
        int max = max(sampleValues);

        // 数据颜色
        Color[] sampleColors = new Color[] { ColorUtil.blueColor };

        // 柱状图
        BarChart chart = new BarChart();

        // 设置样本个数
        chart.setSampleCount(sampleValues.length);
        // 设置样本数据
        chart.setSampleValues(0, sampleValues);
        // 设置文字
        chart.setSampleLabels(sampleLabels);
        // 设置样本颜色
        chart.setSampleColors(sampleColors);
        // 设置取值范围
        chart.setRange(0, max * 1.2);
        // 显示背景横线
        chart.setValueLinesOn(true);
        // 显示文字
        chart.setSampleLabelsOn(true);
        // 把文字显示在下方
        chart.setSampleLabelStyle(Chart.BELOW);

        // 样本值的字体
        chart.setFont("rangeLabelFont", new Font("Arial", Font.BOLD, 12));
        // 显示图例说明
        chart.setLegendOn(true);
        // 把图例说明放在左侧
        chart.setLegendPosition(Chart.LEFT);
        // 图例说明中的文字
        chart.setLegendLabels(new String[] { "月消费报表" });
        // 图例说明的字体
        chart.setFont("legendFont", new Font("Dialog", Font.BOLD, 13));
        // 下方文字的字体
        chart.setFont("sampleLabelFont", new Font("Dialog", Font.BOLD, 13));
        // 图表中间背景颜色
        chart.setChartBackground(Color.white);
        // 图表整体背景颜色
        chart.setBackground(ColorUtil.backgroundColor);
        // 把图表转换为Image类型
        Image im = chart.getImage(width, height);
        return im;
    }

    private static double[] sampleValues() {

        double[] result = new double[30];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * 300);
        }
        return result;

    }

    public static void main(String[] args) {
        JPanel p = new JPanel();
        JLabel l = new JLabel();
        Image img = ChartUtil.getImage(400, 300);
        Icon icon = new ImageIcon(img);
        l.setIcon(icon);
        p.add(l);
        GUIUtil.showPanel(p);
    }
}
```

<div align=center><img src=Pictures\ChartUtil.png width=70%></div>


## 图片资源

本项目用到的图片资源都放在img目录下：`D:\HutuBill\hutubill\img`

# 原型（界面类）

## MainPanel

MainPanel是主窗体的ContentPanel，采用的是BorderLayerout的布局方式。北边是一个工具条；中间是一个空白的Panel，用于将来显示不同的功能面板。

```java
package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import util.CenterPanel;
import util.GUIUtil;

public class MainPanel extends JPanel {
    /**
     * 在静态初始化块中调用LookAndFeel，设置水晶皮肤。
     * 因为这段话必须放在最前面才能生效，所以需要在这里调用
     */
    static {
        GUIUtil.useLNF();
    }

    /**
     * 接着采用单例模式，为的是后续的监听器访问这个容器里的组件的便利性
     */
    public static MainPanel instance = new MainPanel();

    public JToolBar tb = new JToolBar();

    /**
     * 把各种按钮声明为public 的属性，同样也是为了后续的监听器访问这个容器里的组件的便利性
     */
    public JButton bSpend = new JButton();
    public JButton bRecord = new JButton();
    public JButton bCategory = new JButton();
    public JButton bReport = new JButton();
    public JButton bConfig = new JButton();
    public JButton bBackup = new JButton();
    public JButton bRecover = new JButton();

    /**
     * 工作面板，用于将来显示不同的功能面板。 目前暂时是空白的
     */
    public CenterPanel workingPanel;

    private MainPanel() {

        /**
         * 使用GUI.setImageIcon设置这些按钮的图标、文字和提示
         */
        GUIUtil.setImageIcon(bSpend, "home.png", "消费一览");
        GUIUtil.setImageIcon(bRecord, "record.png", "记一笔");
        GUIUtil.setImageIcon(bCategory, "category2.png", "消费分类");
        GUIUtil.setImageIcon(bReport, "report.png", "月消费报表");
        GUIUtil.setImageIcon(bConfig, "config.png", "设置");
        GUIUtil.setImageIcon(bBackup, "backup.png", "备份");
        GUIUtil.setImageIcon(bRecover, "restore.png", "恢复");

        tb.add(bSpend);
        tb.add(bRecord);
        tb.add(bCategory);
        tb.add(bReport);
        tb.add(bConfig);
        tb.add(bBackup);
        tb.add(bRecover);
        tb.setFloatable(false);

        workingPanel = new CenterPanel(0.8);

        setLayout(new BorderLayout());
        add(tb, BorderLayout.NORTH);
        add(workingPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        /**
         * 最后使用GUIUtil.showPanel方法，显示这个面板实例，测试起来非常方便。
         * 参数"1" 表示满屏显示
         */
        GUIUtil.showPanel(MainPanel.instance, 1);
    }
}
```

<div align=center><img src=Pictures\MainPanel.png width=70%></div>


## SpendPanel

### 布局分析

**整体布局**

首先整体是一个BorderLayerout；中间是一个Panel，采用的BorderLayerout.CENTER；下面也是一个Panel，采用的BorderLayerout.SOUTH：

```
this.add(center(), BorderLayout.CENTER);
this.add(south(), BorderLayout.SOUTH);
```

<div align=center><img src=Pictures\整体布局.png width=70%></div>


**南面**

南面这部分使用的是GridLayerout，采用的是2行4列的网格布局器：

```
private JPanel south() {
    JPanel p = new JPanel();
	p.setLayout(new GridLayout(2, 4));
	p.add(lAvgSpendPerDay);
	p.add(lMonthLeft);
	p.add(lDayAvgAvailable);
	p.add(lMonthLeftDay);
	p.add(vAvgSpendPerDay);
	p.add(vMonthAvailable);
	p.add(vDayAvgAvailable);
	p.add(vMonthLeftDay);
	return p;
}
```

<div align=center><img src=Pictures\南面.png width=70%></div>


**西面**

北面这个Panel，本身也是用的BorderLayout。其自身分中西两部分，CENTER就直接使用的环形进度条；WEST使用的是一个JPanel，这个JPanel用的是4行1列的GridLayout。

```
private JPanel center() {
	JPanel p = new JPanel();
	p.setLayout(new BorderLayout());
	p.add(west(), BorderLayout.WEST);
	p.add(center2(),BorderLayout.CENTER);
	return p;
}
```

<div align=center><img src=Pictures\西面.png width=70%></div>


```java
package gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.CircleProgressBar;
import util.ColorUtil;
import util.GUIUtil;

public class SpendPanel extends JPanel{
    // 设置皮肤
    static{
        GUIUtil.useLNF();
    }

    // 单例化，单例化的目的是为了监听器方便获取组件
    public static SpendPanel instance = new SpendPanel();

    // 各种组件，组件设计为public属性，也是为了监听器方便获取组件
    public JLabel lMonthSpend = new JLabel("本月消费");
    public JLabel lTodaySpend = new JLabel("今日消费");
    public JLabel lAvgSpendPerDay = new JLabel("日均消费");
    public JLabel lMonthLeft = new JLabel("本月剩余");
    public JLabel lDayAvgAvailable = new JLabel("日均可用");
    public JLabel lMonthLeftDay = new JLabel("距离月末");

    public JLabel vMonthSpend = new JLabel("￥2300");
    public JLabel vTodaySpend = new JLabel("￥25");
    public JLabel vAvgSpendPerDay = new JLabel("￥120");
    public JLabel vMonthAvailable = new JLabel("￥2084");
    public JLabel vDayAvgAvailable = new JLabel("￥389");
    public JLabel vMonthLeftDay = new JLabel("15天");

    CircleProgressBar bar;

    public SpendPanel() {
        this.setLayout(new BorderLayout());
        bar = new CircleProgressBar();
        bar.setBackgroundColor(ColorUtil.blueColor);

        // 为不同的组件上不同的颜色
        GUIUtil.setColor(ColorUtil.grayColor, lMonthSpend, lTodaySpend, lAvgSpendPerDay, lMonthLeft, lDayAvgAvailable,
                lMonthLeftDay, vAvgSpendPerDay, vMonthAvailable, vDayAvgAvailable, vMonthLeftDay);
        GUIUtil.setColor(ColorUtil.blueColor, vMonthSpend, vTodaySpend);

        // 设置字体
        vMonthSpend.setFont(new Font("微软雅黑", Font.BOLD, 23));
        vTodaySpend.setFont(new Font("微软雅黑", Font.BOLD, 23));

        // 进行布局规划
        this.add(center(), BorderLayout.CENTER);
        this.add(south(), BorderLayout.SOUTH);

    }

    private JPanel center() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(west(), BorderLayout.WEST);
        p.add(center2(),BorderLayout.CENTER);

        return p;
    }

    private Component center2() {
        return bar;
    }

    private Component west() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 1));
        p.add(lMonthSpend);
        p.add(vMonthSpend);
        p.add(lTodaySpend);
        p.add(vTodaySpend);
        return p;
    }

    private JPanel south() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 4));

        p.add(lAvgSpendPerDay);
        p.add(lMonthLeft);
        p.add(lDayAvgAvailable);
        p.add(lMonthLeftDay);
        p.add(vAvgSpendPerDay);
        p.add(vMonthAvailable);
        p.add(vDayAvgAvailable);
        p.add(vMonthLeftDay);

        return p;
    }

    public static void main(String[] args) {

        GUIUtil.showPanel(SpendPanel.instance);
    }

}
```

<div align=center><img src=Pictures\SpendPanel1.png width=70%></div>



## RecordPanel

### 布局分析

这个面板采用BorderLayout分为北面和中间。北面是一个JPanel，中间也是一个JPanel。北面的JPanel使用4行2列的GridLayout。
中间的JPanel使用默认的FlowLayout：

```
this.setLayout(new BorderLayout());
this.add(pInput,BorderLayout.NORTH);
this.add(pSubmit,BorderLayout.CENTER);
```

<div align=center><img src=Pictures\RecordPanel布局分析.png width=70%></div>


### CategoryComboBoxModel

下拉框用到了JComboBox，在创建这个下拉框的时候，借助了类似于TableModel的形式，使用ComboBoxModel把数据分离开来，这样做的目的是为了便于下拉框中分类信息的更新。因为在后续的开发中，分类信息是从数据库中取出来的，很有可能发生变化。

```
public CategoryComboBoxModel cbModel = new CategoryComboBoxModel();
public JComboBox<String> cbCategory = new JComboBox<>(cbModel);
```

`CategoryComboBoxModel`类实现了`ComboBoxModel`接口，由此实现了一系列的方法；`Category`英文是分类信息的意思，在本项目表示消费分类。以下是`CategoryComboBoxModel`代码的解读：

cs表示分类信息，是一个String字符串集合。(在后续改进中，会变为Category对象集合，因为截止目前为止，还只是原型阶段，尚未涉及到dao和实体类entity. 所以暂时使用String代替。 这里先打一个预防针，表示String类型只是暂时的。)

```java
package gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class CategoryComboBoxModel implements ComboBoxModel<String>{

    // cs表示分类信息，是一个String字符串集合
    public List<String> cs = new ArrayList<>();

    // c表示被选中的字符串
    String c;

    //构造方法中初始化模拟数据。
    // 这些数据不是从数据库中读出来的，是专门用于原型数据显示的
    public CategoryComboBoxModel(){
        cs.add("餐饮");
        cs.add("交通");
        cs.add("住宿");
        cs.add("话费");
        c = cs.get(0);
    }

    // 获取这个下拉框的大小，就是前面定义的字符串集合的大小
    @Override
    public int getSize() {
        return cs.size();
    }

    // 获取指定位置的数据
    @Override
    public String getElementAt(int index) {
        return cs.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        // TODO Auto-generated method stub

    }

    // 当界面上选中了某一个下拉框项，就会调用这个方法
    @Override
    public void setSelectedItem(Object anItem) {
        c = (String) anItem;

    }

    // 获取被选中的数据
    @Override
    public Object getSelectedItem() {
        // TODO Auto-generated method stub
        return c;
    }

}
```


### 日期控件

本面板使用到了`JXDatePicker`这个日期控件，用于显示当前日期。需要用到swingx-core-1.6.2.jar，下载后，导入到项目中。

`public JXDatePicker datepick = new JXDatePicker(new Date());`

<div align=center><img src=Pictures\日期控件.png width=60%></div>


### RecordPanel

```java
package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import gui.model.CategoryComboBoxModel;
import util.ColorUtil;
import util.GUIUtil;

public class RecordPanel extends JPanel{

    // 设置皮肤
    static{
        GUIUtil.useLNF();
    }

    // 单例化
    public static RecordPanel instance = new RecordPanel();

    // public的属性
    JLabel lSpend = new JLabel("花费(￥)");
    JLabel lCategory = new JLabel("分类");
    JLabel lComment = new JLabel("备注");
    JLabel lDate = new JLabel("日期");

    public JTextField tfSpend = new JTextField("0");

    public CategoryComboBoxModel cbModel = new CategoryComboBoxModel();
    public JComboBox<String> cbCategory = new JComboBox<>(cbModel);
    public JTextField tfComment = new JTextField();
    public JXDatePicker datepick = new JXDatePicker(new Date());

    JButton bSubmit = new JButton("记一笔");

    public RecordPanel() {
        // 给标签和按钮分别上灰色和淡蓝色
        GUIUtil.setColor(ColorUtil.grayColor, lSpend,lCategory,lComment,lDate);
        GUIUtil.setColor(ColorUtil.blueColor, bSubmit);
        JPanel pInput =new JPanel();
        JPanel pSubmit = new JPanel();
        int gap = 40;
        pInput.setLayout(new GridLayout(4,2,gap,gap));

        pInput.add(lSpend);
        pInput.add(tfSpend);
        pInput.add(lCategory);
        pInput.add(cbCategory);
        pInput.add(lComment);
        pInput.add(tfComment);
        pInput.add(lDate);
        pInput.add(datepick);

        pSubmit.add(bSubmit);

        // 把不同的子面板进行布局
        this.setLayout(new BorderLayout());
        this.add(pInput,BorderLayout.NORTH);
        this.add(pSubmit,BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // 使用GUIUtil.showPanel进行测试
        GUIUtil.showPanel(RecordPanel.instance);
    }

}
```

<div align=center><img src=Pictures\RecordPanel.png width=70%></div>


## CategoryPanel

### 布局

本面板同样采用BorderLayout，分居中和南面。居中使用的是JScrollPane，其中放着JTable。南面使用一个JPanel，里面放3个按钮。

### CategoryTableModel

显示Table信息用到了CategoryTableModel，与记一笔面板RecordPanel原型中的CategoryComboBoxModel一样，也是放的一些模拟数据。

```java
package gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CategoryTableModel implements TableModel{

    String[] columnNames = new String[]{"分类名称","消费次数"};
    List<String> cs = new ArrayList<>();

    public CategoryTableModel(){
        cs.add("餐饮");
        cs.add("交通");
        cs.add("住宿");
        cs.add("话费");
    }
    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return cs.size();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        // TODO Auto-generated method stub
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // TODO Auto-generated method stub
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        if(0==columnIndex)
            return cs.get(rowIndex);
        if(1==columnIndex)
            return 0;
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

}
```

### CategoryPanel代码

```java
package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.model.CategoryTableModel;
import util.ColorUtil;
import util.GUIUtil;

public class CategoryPanel extends JPanel{
    // 设置皮肤
    static{
        GUIUtil.useLNF();
    }

    // 单例化
    public static CategoryPanel instance = new CategoryPanel();

    // public属性
    public JButton bAdd = new JButton("新增");
    public JButton bEdit = new JButton("编辑");
    public JButton bDelete = new JButton("删除");
    String columNames[] = new String[]{"分类名称","消费次数"};

    public CategoryTableModel ctm = new CategoryTableModel();
    public JTable t =new JTable(ctm);

    public CategoryPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bAdd,bEdit,bDelete);
        JScrollPane sp =new JScrollPane(t);
        JPanel pSubmit = new JPanel();
        pSubmit.add(bAdd);
        pSubmit.add(bEdit);
        pSubmit.add(bDelete);

        // 布局
        this.setLayout(new BorderLayout());
        this.add(sp,BorderLayout.CENTER);
        this.add(pSubmit,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // 测试
        GUIUtil.showPanel(CategoryPanel.instance);
    }

}
```

<div align=center><img src=Pictures\CategoryPanel.png width=70%></div>


## ReportPanel

ReportPanel就比较简单了，使用的是ChartUtil现成的方法生成了当月的模拟数据。

<div align=center><img src=Pictures\ReportPanel.png width=70%></div>


## ConfigPanel

**布局规划**

本面板使用BorderLayout，分北面和居中。北面是一个JPanel，里面放4个组件，使用4行1列的GridLayout的布局。居中是一个JPanel，就放了一个按钮。

```java
package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.ColorUtil;
import util.GUIUtil;

public class ConfigPanel extends JPanel {
    static{
        GUIUtil.useLNF();
    }

    public static ConfigPanel instance = new ConfigPanel();

    JLabel lBudget = new JLabel("本月预算(￥)");
    public JTextField tfBudget = new JTextField("0");

    JLabel lMysql = new JLabel("Mysql安装目录");
    public JTextField tfMysqlPath = new JTextField("");

    JButton bSubmit = new JButton("更新");

    public ConfigPanel() {
        GUIUtil.setColor(ColorUtil.grayColor, lBudget,lMysql);
        GUIUtil.setColor(ColorUtil.blueColor, bSubmit);

        JPanel pInput =new JPanel();
        JPanel pSubmit = new JPanel();
        int gap =40;
        pInput.setLayout(new GridLayout(4,1,gap,gap));

        pInput.add(lBudget);
        pInput.add(tfBudget);
        pInput.add(lMysql);
        pInput.add(tfMysqlPath);

        this.setLayout(new BorderLayout());
        this.add(pInput,BorderLayout.NORTH);
        pSubmit.add(bSubmit);
        this.add(pSubmit,BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        GUIUtil.showPanel(ConfigPanel.instance);
    }

}
```

<div align=center><img src=Pictures\ConfigPanel.png width=70%></div>


## BackupPanel

```java
package gui.panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import util.ColorUtil;
import util.GUIUtil;

public class BackupPanel extends JPanel {
    static{
        GUIUtil.useLNF();
    }

    public static BackupPanel instance = new BackupPanel();

    // 组件属性
    JButton bBackup =new JButton("备份");

    public BackupPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bBackup);
        this.add(bBackup);
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(BackupPanel.instance);
    }

}
```

<div align=center><img src=Pictures\BackupPanel.png width=70%></div>


## RecoverPanel

```java
package gui.panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import util.ColorUtil;
import util.GUIUtil;

public class RecoverPanel extends JPanel {
    static{
        GUIUtil.useLNF();
    }
    public static RecoverPanel instance = new RecoverPanel();

    JButton bRecover =new JButton("恢复");

    public RecoverPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bRecover);
        this.add(bRecover);
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(RecoverPanel.instance);
    }

}
```

<div align=center><img src=Pictures\RecoverPanel.png width=70%></div>


## MainFrame

前面做的界面类都是面板类，不能独立运行。 整个项目的运行，还是需要一个顶级容器类 JFrame来容纳这些面板类。

在主窗体中通过`setContentPane`把MainPanel设置为内容面板：
`this.setContentPane(MainPanel.instance);`

```java
package gui.frame;

import javax.swing.JFrame;

import gui.panel.MainPanel;
import util.GUIUtil;

public class MainFrame extends JFrame{

    public static MainFrame instance = new MainFrame();

    private MainFrame(){
        this.setSize(500,450);
        this.setTitle("一本糊涂账");
        this.setContentPane(MainPanel.instance);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        instance.setVisible(true);
    }

}
```

<div align=center><img src=Pictures\MainFrame.png width=70%></div>


# **实体类和DAO

## 实体类Entity

实体类Entity有很多种其他的说法，比如Bean，JavaBean，pojo等等，其实都是相近的东西。

实体类Entity是**用于映射表中的一条一条数据的**。**比如分类表Category，有很多条分类记录，那么就有一个类也叫做Category，这个类的一个一个的实例，就对应了表Category中的一条一条的记录**。

**步骤一：创建实体包**

首先创建entity包，专门用于存放各种实体类。

**步骤二：配置信息类Config**

**配置信息Config类**与**配置信息表config**相对应：id 主键；key 键；value 值：

```java
package entity;

public class Config {
    public int id;
    public String key;
    public String value;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

}
```

**步骤三：消费分类Category**

消费分类Category有3个字段：id 主键；name 名称；recordNumber 消费记录数。

回头看看**消费分类表**category的表结构，可以发现**消费记录数**`recordNumber`这个属性并不会出现在数据库中的。它是根据这种分类在消费记录表Record中有多少条对应信息，**临时查出来**的。

在设计表结构的时候，这样的字段是不建议存放在数据库中的。 假设在表Category中设计了这么一个字段recordNumber，那么关于这种分类对应的消费记录数，其实在数据库中有两个：一是这个字段，二是实际Record表中存在的数据数量。

一个信息，在两个地方存在，就需要增加数据一致性的维护成本，也存在数据出现不一致的风险(比如忘记更新recordNumber中的信息)。

注：这里提供了一个`toString`方法，在后续JComboBox中显示的时候，会调用此方法，用于显示分类名称。

```java
package entity;

public class Category {
    public int id;
    public String name;

    public int recordNumber;

    public int getRecordNumber() {
        return recordNumber;
    }
    
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
```

**步骤四：消费信息类Record**

消费信息类Record与消费记录表record相对应：id 主键；spend 消费金额；cid 分类信息id；comment 备注；date 日期。

```java
package entity;

import java.util.Date;

public class Record {
    public int spend;
    public int id;
    public int cid;
    public String comment;
    public Date date;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCid() {
        return cid;
    }
    
    public void setCid(int cid) {
        this.cid = cid;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public int getSpend() {
        return spend;
    }
    
    public void setSpend(int spend) {
        this.spend = spend;
    }

}
```

## DBUtil

**工具类DBUtil**

在这个项目里有多个DAO里都需要获取数据库的连接，并且在本项目中都是一样的数据库连接。所以就可以把获取数据库连接的代码重构到一个类里。

这样做的好处是有两个：
1. 不需要DAO里分别进行编写，直接调用就可以了；
2. 如果账号密码发生了变化，值需要修改这一个地方，而不用每个DAO里就分别修改，降低了维护成本，也降低了因为忘记修改而出错的概率。

将jar包`mysql-connector-java-5.0.8-bin.jar`导入项目。

```java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // 定义了很多和数据库连接相关的信息，如果账号密码发生改变，修改起来很容易
    static String ip = "127.0.0.1";
    static int port = 3306;
    static String database = "hutubill";
    static String encoding = "UTF-8";
    static String loginName = "root";
    static String password = "admin";

    // 驱动初始化放在了静态初始化块里，因为这行代码需要先执行，而且只需要执行一次就足够了。
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 提供了一个静态的public的getConnection方法获取连接
    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
        return DriverManager.getConnection(url, loginName, password);
    }
}
```


## DateUtil

为什么要用到日期工具类DateUtil呢？ 

在业务上，比如消费一览这个界面，需要获取今日的消费总额，本月的消费总额，今天距离月末还有多长时间等信息，那么把关于日期的相关功能都抽象出来放在DateUtil进行调用，代码层次结构更加清晰，也更容易维护，在将来也可以重用。


```java
package util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    static long millisecondsOfOneDay = 1000*60*60*24;

    public static java.sql.Date util2sql(java.util.Date d){
        return  new java.sql.Date(d.getTime());
    }

    /**
     * 获取今天，并且把时，分，秒和毫秒都置0。 因为通过日期控件获取到的日期，就是没有时分秒和毫秒的。
     * @return
     */
    public static Date today(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取月初。使用Calendar获取本月第一天。 在统计消费一览信息的时候，查看本月的消费数据，其实就是从数据库中去把从本月第一天到最后一天的数据查出来，再进行简单统计，所以需要一个获取本月第一天的方法。
     * @return
     */
    public static Date monthBegin() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    /**
     * 获取月末
     * @return
     */
    public static Date monthEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 获取本月一共有多少天
     * @return
     */
    public static int thisMonthTotalDay(){

        long lastDayMilliSeconds = monthEnd().getTime();
        long firstDayMilliSeconds = monthBegin().getTime();

        return (int) ((lastDayMilliSeconds - firstDayMilliSeconds) / millisecondsOfOneDay) + 1;
    }

    /**
     *获取本月还剩多少天
     * @return
     */
    public static int thisMonthLeftDay(){
        long lastDayMilliSeconds = monthEnd().getTime();
        long toDayMilliSeconds = today().getTime();
        return (int) ((lastDayMilliSeconds - toDayMilliSeconds) / millisecondsOfOneDay) + 1;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.monthEnd());
    }
}
```


## **ConfigDAO

DAO(Data Access objects 数据存取对象)是指位于业务逻辑和持久化数据之间实现对持久化数据的访问。通俗来讲，就是**将数据库操作都封装起来**。

首先创建dao包，所有的DAO相关类都会放在这里：`D:\HutuBill\hutubill\src\dao`。

`ConfigDAO`专门用于把`Config实例`与`Config表`进行`ORM映射`。

ORM映射(对象关系映射--Object Relational Mapping)是什么呢？ 

简单说，**一个对象，对应数据库里的一条记录**

**简单点理解就是`ConfigDAO`负责把`Config实例`转换成一条`Config表`中的记录，反过来，又把Config表中的记录转换为一个Config实例**。

这其中就用到了DBUtil来获取连接，其他的DAO也统一使用DBUtil的方式获取连接。

```java
package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Config;
import util.DBUtil;

public class ConfigDAO {

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT COUNT (*) FROM config";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }

            System.out.println("total:" + total);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Config config) {

        String sql = "INSERT INTO config VALUES (null, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, config.key);
            ps.setString(2, config.value);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                config.id = id;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Config config) {

        String sql = "UPDATE config SET key_ = ?, value = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setString(1, config.key);
            ps.setString(2, config.value);
            ps.setInt(3, config.id);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "DELETE FROM config WHERE id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Config get(int id) {
        Config config = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT * FROM config WHERE id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                config = new Config();
                String key = rs.getString("key_");
                String value = rs.getString("value");
                config.key = key;
                config.value = value;
                config.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return config;
    }

    public List<Config> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Config> list(int start, int count) {
        List<Config> configs = new ArrayList<Config>();

        String sql = "SELECT * FROM config ORDER BY id DESC limit ?, ? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Config config = new Config();
                int id = rs.getInt(1);
                String key = rs.getString("key_");
                String value = rs.getString("value");
                config.id = id;
                config.key = key;
                config.value = value;
                configs.add(config);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return configs;
    }


    /**
     * 通过键获取Config实例，比如预算对应的Config实例，就会通过这种方式获取： 
     * new ConfigDAO().getByKey("budget");
     * @param key
     * @return
     */
    public Config getByKey(String key) {
        Config config = null;
        String sql = "SELECT * FROM config WHERE key_ = ?" ;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, key);
            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                config = new Config();
                int id = rs.getInt("id");
                String value = rs.getString("value");
                config.key = key;
                config.value = value;
                config.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return config;
    }

}
```


## CategoryDAO

CategoryDAO专门用于把Category实例与Category表进行ORM映射。其方法和ConfigDAO很接近，也是增删改查一套。

```java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import util.DBUtil;

public class CategoryDAO {

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT COUNT (*) FROM category";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }

            System.out.println("total:" + total);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Category category) {

        String sql = "INSERT INTO category VALUES (null, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setString(1, category.name);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                category.id = id;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Category category) {

        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setString(1, category.name);
            ps.setInt(2, category.id);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "DELETE FROM category WHERE id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Category get(int id) {
        Category category = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT * FROM category WHERE id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                category = new Category();
                String name = rs.getString(2);
                category.name = name;
                category.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return category;
    }

    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Category> list(int start, int count) {
        List<Category> categorys = new ArrayList<Category>();

        String sql = "SELECT * FROM category ORDER BY id DESC limit ?, ? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                category.id = id;
                category.name = name;
                categorys.add(category);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return categorys;
    }

}
```

## RecordDAO

RecordDAO专门用于把Record实例与Record表进行ORM映射。

```java
package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Record;
import util.DBUtil;
import util.DateUtil;

public class RecordDAO {

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT COUNT (*) FROM record";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }

            System.out.println("total:" + total);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Record record) {

        String sql = "INSERT INTO record VALUES (null, ?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, record.spend);
            ps.setInt(2, record.cid);
            ps.setString(3, record.comment);
            ps.setDate(4, DateUtil.util2sql(record.date));

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                record.id = id;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Record record) {

        String sql = "UPDATE record SET spend = ?, cid = ?, comment = ?, date = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, record.spend);
            ps.setInt(2, record.cid);
            ps.setString(3, record.comment);
            ps.setDate(4, DateUtil.util2sql(record.date));
            ps.setInt(5, record.id);

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "DELETE FROM record WHERE id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Record get(int id) {
        Record record = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "SELECT * FROM record WHERE id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                record = new Record();
                int spend = rs.getInt("spend");
                int cid = rs.getInt("cid");
                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                record.spend=spend;
                record.cid=cid;
                record.comment=comment;
                record.date=date;
                record.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return record;
    }

    public List<Record> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Record> list(int start, int count) {
        List<Record> records = new ArrayList<Record>();

        String sql = "SELECT * FROM record ORDER BY id DESC limit ?, ? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Record record = new Record();
                int id = rs.getInt("id");
                int spend = rs.getInt("spend");
                int cid = rs.getInt("cid");

                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                record.spend=spend;
                record.cid=cid;
                record.comment=comment;
                record.date=date;
                record.id = id;
                records.add(record);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return records;
    }

    public List<Record> list(int cid) {
        List<Record> records = new ArrayList<Record>();

        String sql = "SELECT * FROM record WHERE cid = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, cid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Record record = new Record();
                int id = rs.getInt("id");
                int spend = rs.getInt("spend");

                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                record.spend=spend;
                record.cid=cid;
                record.comment=comment;
                record.date=date;
                record.id = id;
                records.add(record);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return records;
    }

    // 获取当天的消费记录信息
    public List<Record> listToday(){
        return list(DateUtil.today());
    }

    // 获取某天的消费记录信息
    public List<Record> list(Date day) {
        List<Record> records = new ArrayList<Record>();
        String sql = "SELECT * FROM record WHERE date = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setDate(1, DateUtil.util2sql(day));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                int id = rs.getInt("id");
                int cid = rs.getInt("cid");
                int spend = rs.getInt("spend");

                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                record.spend=spend;
                record.cid=cid;
                record.comment=comment;
                record.date=date;
                record.id = id;
                records.add(record);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return records;
    }

    // 获取本月的消费记录信息
    public List<Record> listThisMonth(){
        return list(DateUtil.monthBegin(),DateUtil.monthEnd());
    }

    // 获取某个时间范围内的消费记录信息
    public List<Record> list(Date start, Date end) {
        List<Record> records = new ArrayList<Record>();
        String sql = "SELECT * FROM record WHERE date >= ? AND date <= ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setDate(1, DateUtil.util2sql(start));
            ps.setDate(2, DateUtil.util2sql(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                int id = rs.getInt("id");
                int cid = rs.getInt("cid");
                int spend = rs.getInt("spend");

                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                record.spend=spend;
                record.cid=cid;
                record.comment=comment;
                record.date=date;
                record.id = id;
                records.add(record);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return records;
    }

}
```

# 功能

## 启动类Bootstrap

创建包startup用于存放类Bootstrap：`D:\HutuBill\hutubill\src\startup`。

**启动入口Bootstrap**

所有的进程都有个启动入口，一般说来，稍微复杂点的程序，就会有一个类，专门负责启动的事情，通常这个类会叫做`startup.Bootstrap`：

```java
package startup;

import javax.swing.SwingUtilities;

import gui.frame.MainFrame;
import gui.panel.MainPanel;
import gui.panel.SpendPanel;
import util.GUIUtil;

public class Bootstrap {
    public static void main(String[] args) throws Exception{
        // 设置皮肤。
        // 一旦在这里设置了皮肤，其他的Panel类里就不需要进行皮肤设置了。
        GUIUtil.useLNF();

        // 使用SwingUtilities.invokeAndWait的方式启动图形界面
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                // 显示主窗体，并让主面板下方的workingPanel显示消费一览Panel
                MainFrame.instance.setVisible(true);
                MainPanel.instance.workingPanel.show(SpendPanel.instance);
            }
        });
    }
}
```

<div align=center><img src=Pictures\Bootstrap.png width=80%></div>


## 开发顺序

**模块依赖**

本项目是由多个模块组成，各个模块之间是相互依赖的，有的模块不完成，其他模块就无法工作。比如要"记一笔"就必须要先有"消费分类数据"；要"消费一览”就必须先在设置里预先设置预算；要备份和恢复，也必须先在设置里预先设置mysql的安装路径。

所以需要按照依赖的关系先开发一些模块，然后再是其他的。

根据模块依赖关系，按照如下顺序进行功能开发：

1. 主窗体工具栏事件响应
2. 设置
3. 消费分类
4. 记一笔
5. 消费一览
6. 月消费报表
7. 备份
8. 恢复

注：主窗体工具栏事件响应指的是主窗体工具栏中的按钮，点击之后，切换不同的面板。这个可以先做。

## 主窗体工具栏

### 监听器ToolBarListener

设计一个独立的监听器类`ToolBarListener`，实现接口`ActionListener`，重写`actionPerformed`方法。

**这个监听器是为工具栏上的几个按钮添加的，并且这几个按钮都使用这么一个监听器**。

通过`ActionEvent.getSource()`获取事件是哪个按钮发出来的，根据不同的按钮，发出切换不同的功能面板。

比如开始这段代码，如果按钮是`bReport`，那么就切换到报表面板`ReportPanel`：

```java
Button b = (JButton) e.getSource();
if (b == p.bReport)
    p.workingPanel.show(ReportPanel.instance);
```

```java
package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.panel.BackupPanel;
import gui.panel.CategoryPanel;
import gui.panel.ConfigPanel;
import gui.panel.SpendPanel;
import gui.panel.MainPanel;
import gui.panel.RecordPanel;
import gui.panel.RecoverPanel;
import gui.panel.ReportPanel;

public class ToolBarListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainPanel p = MainPanel.instance;
        JButton b = (JButton) e.getSource();
        if (b == p.bReport)
            p.workingPanel.show(ReportPanel.instance);
        if (b == p.bCategory)
            p.workingPanel.show(CategoryPanel.instance);
        if (b == p.bSpend)
            p.workingPanel.show(SpendPanel.instance);
        if (b == p.bRecord)
            p.workingPanel.show(RecordPanel.instance);
        if (b == p.bConfig)
            p.workingPanel.show(ConfigPanel.instance);
        if (b == p.bBackup)
            p.workingPanel.show(BackupPanel.instance);
        if (b == p.bRecover)
            p.workingPanel.show(RecoverPanel.instance);

    }
}
```

### 在主面板中增加监听器

实例化一个ToolBarListener监听器，工具栏上的按钮，都加上这么一个监听器对象即可。这样点击不同的按钮，就可以在不同的面板之间来回切换。

修改`MainPanel`，见74行：

```java {.line-numbers highlight=74}
package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import gui.listener.ToolBarListener;
import util.CenterPanel;
import util.GUIUtil;

public class MainPanel extends JPanel {
    /**
     * 在静态初始化块中调用LookAndFeel，设置水晶皮肤。
     * 因为这段话必须放在最前面才能生效，所以需要在这里调用
     */
    static {
        GUIUtil.useLNF();
    }

    /**
     * 接着采用单例模式，为的是后续的监听器访问这个容器里的组件的便利性
     */
    public static MainPanel instance = new MainPanel();

    public JToolBar tb = new JToolBar();

    /**
     * 把各种按钮声明为public 的属性，同样也是为了后续的监听器访问这个容器里的组件的便利性
     */
    public JButton bSpend = new JButton();
    public JButton bRecord = new JButton();
    public JButton bCategory = new JButton();
    public JButton bReport = new JButton();
    public JButton bConfig = new JButton();
    public JButton bBackup = new JButton();
    public JButton bRecover = new JButton();

    /**
     * 工作面板，用于将来显示不同的功能面板。 目前暂时是空白的
     */
    public CenterPanel workingPanel;

    private MainPanel() {

        /**
         * 使用GUI.setImageIcon设置这些按钮的图标、文字和提示
         */
        GUIUtil.setImageIcon(bSpend, "home.png", "消费一览");
        GUIUtil.setImageIcon(bRecord, "record.png", "记一笔");
        GUIUtil.setImageIcon(bCategory, "category2.png", "消费分类");
        GUIUtil.setImageIcon(bReport, "report.png", "月消费报表");
        GUIUtil.setImageIcon(bConfig, "config.png", "设置");
        GUIUtil.setImageIcon(bBackup, "backup.png", "备份");
        GUIUtil.setImageIcon(bRecover, "restore.png", "恢复");

        tb.add(bSpend);
        tb.add(bRecord);
        tb.add(bCategory);
        tb.add(bReport);
        tb.add(bConfig);
        tb.add(bBackup);
        tb.add(bRecover);
        tb.setFloatable(false);

        workingPanel = new CenterPanel(0.8);

        setLayout(new BorderLayout());
        add(tb, BorderLayout.NORTH);
        add(workingPanel, BorderLayout.CENTER);

        // 实例化一个ToolBarListener 监听器，工具栏上的按钮，都加上这么一个监听器对象即可。
        addListener();
    }

    private void addListener() {
        ToolBarListener listener = new ToolBarListener();

        bSpend.addActionListener(listener);
        bRecord.addActionListener(listener);
        bCategory.addActionListener(listener);
        bReport.addActionListener(listener);
        bConfig.addActionListener(listener);
        bBackup.addActionListener(listener);
        bRecover.addActionListener(listener);

    }

    public static void main(String[] args) {
        /**
         * 最后使用GUIUtil.showPanel方法，显示这个面板实例，测试起来非常方便。
         * 参数"1" 表示满屏显示
         */
        GUIUtil.showPanel(MainPanel.instance, 1);
    }
}
```

### 独立的监听器与面板单例化

至此面板单例化好处就开始显现出来了，在监听器里可以很方便的访问各种界面类。

独立的监听器规划在`gui.listener`包下面，如果需要做监听器方面的改动，定位也会非常方便。


## 配置

### 业务类Service

ConfigService设置业务类，这个类是监听器直接调用的类，然后再通过ConfigService去调用ConfigDAO。

为什么需要一个业务类呢？ 

DAO是直接和数据库打交道的，在和数据库打交道之前，还需要对数据进行预处理，这些就可以放在业务类里进行。

如图所示，由监听器调用Service业务类，然后再调用DAO访问数据库：

<div align=center><img src=Pictures\业务类Service.png></div>

### ConfigService

ConfigService 业务类做了几个事情
1. 初始化 init()
    因为设置信息里有两个数据，一个是预算，一个是Mysql路径。 这两个信息，无论如何都应该是存在数据库中的。 所以会调用`init()`把他们俩初始化。

2. init(String key, String value) 方法
    首先根据key去查找，如果不存在，就使用value的值插入一条数据。

3. get(String key)
    根据键获取相应的值

4. update(String key, String value)
    更新键对应的值


```java
package service;

import dao.ConfigDAO;
import entity.Config;

public class ConfigService {
    public static final String budget = "budget";
    public static final String mysqlPath = "mysqlPath";
    public static final String default_budget = "500";

    static ConfigDAO dao= new ConfigDAO();
    static{
        init();
    }

    public static void init(){
        init(budget, default_budget);
        init(mysqlPath, "");
    }

    private static void init(String key, String value) {

        Config config= dao.getByKey(key);
        if(config==null){
            Config c = new Config();
            c.setKey(key);
            c.setValue(value);
            dao.add(c);
        }
    }

    public String get(String key) {
        Config config= dao.getByKey(key);
        return config.getValue();
    }

    public void update(String key, String value){
        Config config= dao.getByKey(key);
        config.setValue(value);
        dao.update(config);
    }

    public int getIntBudget() {
        return Integer.parseInt(get(budget));
    }

}
```

### ConfigListener

监听器ConfigListener，这个监听器是用在**更新按钮**上的：

1. 首先判断输入的预算值是否是整数；

2. 接着判断输入的MYSQL安装路径是否正确。判断办法是看路径对应的bin子目录下是否有mysql.exe文件存在；

3. 如果上述两个判断都通过了，那么就调用业务类ConfigService进行数据更新。
    ```
    ConfigService cs= new ConfigService();
    cs.update(ConfigService.budget,p.tfBudget.getText());
    cs.update(ConfigService.mysqlPath,mysqlPath);
    ```

4. 最后提示设置修改成功


注1：在这里进一步体现了界面类单例化和组件设置为public的好处，即获取非常的方便，比之传统的匿名类方式快捷很多，维护起来也更容易。

注2：在第3步中，也能更明显的体现业务类ConfigService的好处。如果不使用ConfigService，那么就需要自己创建Config对象，对象是否存在的校验，设置属性等等，看上去就会显示比较繁杂。而使用业务类，看上去就会比较简练。

```java
package gui.listener;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import gui.panel.ConfigPanel;
import service.ConfigService;
import util.GUIUtil;

public class ConfigListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        ConfigPanel p = ConfigPanel.instance;
        if(!GUIUtil.checkNumber(p.tfBudget, "本月预算"))
            return;
        String mysqlPath =p.tfMysqlPath.getText();
        if(0!=mysqlPath.length()){
            File commandFile = new File(mysqlPath,"bin/mysql.exe");
            if(!commandFile.exists()){
                JOptionPane.showMessageDialog(p, "Mysql路径不正确");
                p.tfMysqlPath.grabFocus();
                return;
            }
        }

        ConfigService cs= new ConfigService();
        cs.update(ConfigService.budget,p.tfBudget.getText());
        cs.update(ConfigService.mysqlPath,mysqlPath);

        JOptionPane.showMessageDialog(p, "设置修改成功");

    }

}
```


### 在ConfigPanel添加监听器

在ConfigPanel添加监听器：
```
public void addListener() {
    ConfigListener l =new ConfigListener();
    bSubmit.addActionListener(l);
}
```
并在构造方法中调用addListener()。


```java
package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.listener.ConfigListener;
import util.ColorUtil;
import util.GUIUtil;

public class ConfigPanel extends JPanel {
    static{
        GUIUtil.useLNF();
    }

    public static ConfigPanel instance = new ConfigPanel();

    JLabel lBudget = new JLabel("本月预算(￥)");
    public JTextField tfBudget = new JTextField("0");

    JLabel lMysql = new JLabel("Mysql安装目录");
    public JTextField tfMysqlPath = new JTextField("");

    JButton bSubmit = new JButton("更新");

    public ConfigPanel() {
        GUIUtil.setColor(ColorUtil.grayColor, lBudget,lMysql);
        GUIUtil.setColor(ColorUtil.blueColor, bSubmit);

        JPanel pInput =new JPanel();
        JPanel pSubmit = new JPanel();
        int gap =40;
        pInput.setLayout(new GridLayout(4,1,gap,gap));

        pInput.add(lBudget);
        pInput.add(tfBudget);
        pInput.add(lMysql);
        pInput.add(tfMysqlPath);

        this.setLayout(new BorderLayout());
        this.add(pInput,BorderLayout.NORTH);
        pSubmit.add(bSubmit);
        this.add(pSubmit,BorderLayout.CENTER);

        // 添加监听器，并在构造方法中调用
        addListener();
    }

    public void addListener() {
        ConfigListener l = new ConfigListener();
        bSubmit.addActionListener(l);
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(ConfigPanel.instance);
    }

}
```

## 消费分类

### CategoryService

Category业务类
1. list()
    查询出所有的Category，然后根据每种分类，再把分类对应的消费记录总数查出来，并且设置在对应分类实例的recordNumer上。
    最后再根据recordNumer进行倒排序，让消费频率高的分类放在前面。

2. add(String name)
    增加一种分类

3. update(int id, String name)
    更新分类

4. delete(int id)
    删除分类


```java
package service;

import java.util.Collections;
import java.util.List;

import dao.CategoryDAO;
import dao.RecordDAO;
import entity.Category;
import entity.Record;

public class CategoryService {

    CategoryDAO categoryDao = new CategoryDAO();
    RecordDAO recordDao = new RecordDAO();

    public List<Category> list() {
        List<Category> cs = categoryDao.list();
        for (Category c : cs) {
            List<Record> rs = recordDao.list(c.id);
            c.recordNumber = rs.size();
        }
        Collections.sort(cs, (c1,c2) -> c2.recordNumber - c1.recordNumber);

        return cs;
    }

    public void add(String name) {
        Category c = new Category();
        c.setName(name);
        categoryDao.add(c);
    }

    public void update(int id, String name) {
        Category c = new Category();
        c.setName(name);
        c.setId(id);
        categoryDao.update(c);
    }

    public void delete(int id) {
        categoryDao.delete(id);
    }

}
```

### CategoryTableModel

之前做分类信息原型的时候`CategoryTableModel`里使用的都是**模拟数据**，现在应该**使用数据库中的数据**了。

几个改动如下：
1. 存放的数据由String集合，改为了`Category集合`，并且从CategoryService的list()方法获取数据。
`public List<Category> cs = new CategoryService().list();`

2. getValueAt方法，第一列取Category对象的name值，第二列取recordNumber值。

```java
public Object getValueAt(int rowIndex, int columnIndex) {
    Category h = cs.get(rowIndex);
    if (0 == columnIndex)
        return h.name;
    if (1 == columnIndex)
        return h.recordNumber;
    return null;
}
```

```java
package gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Category;
import service.CategoryService;

public class CategoryTableModel extends AbstractTableModel {

    String[] columnNames = new String[] { "分类名称", "消费次数"};

    // 使用从Service返回的List作为TableModel的数据

    public List<Category> cs = new CategoryService().list();

    public int getRowCount() {

        return cs.size();
    }

    public int getColumnCount() {

        return columnNames.length;
    }

    public String getColumnName(int columnIndex) {

        return columnNames[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    // 先通过cs.get(rowIndex)获取行对应的Category对象
    // 然后根据columnIndex返回对应的属性
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category h = cs.get(rowIndex);
        if (0 == columnIndex)
            return h.name;
        if (1 == columnIndex)
            return h.recordNumber;

        return null;
    }

}
```


### CategoryPanel

为CategoryPanel新增加一个`getSelectedCategory`，方便获取JTable上当前选中的Category对象：

```
public Category getSelectedCategory() {
    int index = t.getSelectedRow();
    return ctm.cs.get(index);
}
```

增加一个`updateData`方法，用于在增加，删除，和修改之后，更新表格中的信息，并默认选中第一行。除此之外，还进行判断，如果表格里没有数据，删除和修改按钮不可使用。

```
public void updateData(){
	ctm.cs = new CategoryService().list();
	t.updateUI();
	t.getSelectionModel().setSelectionInterval(0, 0);
		
	if(0==ctm.cs.size()){
		bEdit.setEnabled(false);
		bDelete.setEnabled(false);
	}
	else{
		bEdit.setEnabled(true);
		bDelete.setEnabled(true);
	}
}
```

```java
package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entity.Category;
import gui.model.CategoryTableModel;
import service.CategoryService;
import util.ColorUtil;
import util.GUIUtil;

public class CategoryPanel extends JPanel{
    // 设置皮肤
    static{
        GUIUtil.useLNF();
    }

    // 单例化
    public static CategoryPanel instance = new CategoryPanel();

    // public属性
    public JButton bAdd = new JButton("新增");
    public JButton bEdit = new JButton("编辑");
    public JButton bDelete = new JButton("删除");
    String columNames[] = new String[]{"分类名称","消费次数"};

    public CategoryTableModel ctm = new CategoryTableModel();
    public JTable t = new JTable(ctm);

    public CategoryPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bAdd,bEdit,bDelete);
        JScrollPane sp =new JScrollPane(t);
        JPanel pSubmit = new JPanel();
        pSubmit.add(bAdd);
        pSubmit.add(bEdit);
        pSubmit.add(bDelete);

        // 布局
        this.setLayout(new BorderLayout());
        this.add(sp,BorderLayout.CENTER);
        this.add(pSubmit,BorderLayout.SOUTH);
    }

    public Category getSelectedCategory() {
        int index = t.getSelectedRow();
        return ctm.cs.get(index);
    }

    public void updateData() {
        ctm.cs = new CategoryService().list();
        t.updateUI();
        t.getSelectionModel().setSelectionInterval(0, 0);

        if(0==ctm.cs.size()){
            bEdit.setEnabled(false);
            bDelete.setEnabled(false);
        }
        else{
            bEdit.setEnabled(true);
            bDelete.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        // 测试
        GUIUtil.showPanel(CategoryPanel.instance);
    }

}
```

### CategoryListener

CategoryListener监听器，增加，编辑和删除按钮都使用这个监听器。

1. 与监听器ToolBarListener 类似的，根据事件的发出源，判断是哪个按钮触发了这个监听器，并做相应的功能。

2. 如果是增加，弹出输入框，校验输入内容不为空后，通过CategoryService.add()添加到数据库。

3. 如果是编辑，弹出输入框，校验输入内容不为空后，根据CategoryPanel中getSelectedCategory()获取id, 然后再通过CategoryService.update更新到数据库

4. 如果是删除，首先判断是否有消费记录，如果有消费记录，则不能删除。 接着进行删除确认提示，确认后，通过CategoryService.delete()进行删除。

5. 最后调用p.updateData();更新数据

```java
package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import entity.Category;
import gui.panel.CategoryPanel;
import service.CategoryService;

public class CategoryListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CategoryPanel p = CategoryPanel.instance;

        JButton b = (JButton) e.getSource();
        if (b == p.bAdd) {
            String name = JOptionPane.showInputDialog(null);
            if (0 == name.length()) {
                JOptionPane.showMessageDialog(p, "分类名称不能为空");
                return;
            }

            new CategoryService().add(name);

        }
        if (b == p.bEdit) {
            Category c = p.getSelectedCategory();
            int id = c.id;
            String name = JOptionPane.showInputDialog("修改分类名称", c.name);
            if (0 == name.length()) {
                JOptionPane.showMessageDialog(p, "分类名称不能为空");
                return;
            }

            new CategoryService().update(id, name);
        }
        if (b == p.bDelete) {
            Category c = p.getSelectedCategory();
            if (0 != c.recordNumber) {
                JOptionPane.showMessageDialog(p, "本分类下有消费记录存在，不能删除");
                return;
            }
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(p, "确认要删除？"))
                return;

            int id = c.id;
            new CategoryService().delete(id);
        }
        p.updateData();
    }

}
```

### 在CategoryPanel中加上监听器

为3个按钮加上监听器(第48行)：
```
CategoryListener listener = new CategoryListener();
bAdd.addActionListener(listener);
bEdit.addActionListener(listener);
bDelete.addActionListener(listener);
```

```java {.line-numbers highlight=48}
package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entity.Category;
import gui.listener.CategoryListener;
import gui.model.CategoryTableModel;
import service.CategoryService;
import util.ColorUtil;
import util.GUIUtil;

public class CategoryPanel extends JPanel{
    // 设置皮肤
    static{
        GUIUtil.useLNF();
    }

    // 单例化
    public static CategoryPanel instance = new CategoryPanel();

    // public属性
    public JButton bAdd = new JButton("新增");
    public JButton bEdit = new JButton("编辑");
    public JButton bDelete = new JButton("删除");
    String columNames[] = new String[]{"分类名称","消费次数"};

    public CategoryTableModel ctm = new CategoryTableModel();
    public JTable t = new JTable(ctm);

    public CategoryPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bAdd,bEdit,bDelete);
        JScrollPane sp =new JScrollPane(t);
        JPanel pSubmit = new JPanel();
        pSubmit.add(bAdd);
        pSubmit.add(bEdit);
        pSubmit.add(bDelete);

        // 布局
        this.setLayout(new BorderLayout());
        this.add(sp,BorderLayout.CENTER);
        this.add(pSubmit,BorderLayout.SOUTH);

        addListener();
    }

    public void addListener() {
        CategoryListener listener = new CategoryListener();
        bAdd.addActionListener(listener);
        bEdit.addActionListener(listener);
        bDelete.addActionListener(listener);
    }

    public Category getSelectedCategory() {
        int index = t.getSelectedRow();
        return ctm.cs.get(index);
    }

    public void updateData() {
        ctm.cs = new CategoryService().list();
        t.updateUI();
        t.getSelectionModel().setSelectionInterval(0, 0);

        if(0==ctm.cs.size()){
            bEdit.setEnabled(false);
            bDelete.setEnabled(false);
        }
        else{
            bEdit.setEnabled(true);
            bDelete.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        // 测试
        GUIUtil.showPanel(CategoryPanel.instance);
    }

}
```

## WorkingPanel

### 共同性

功能开发到这个阶段，可以总结出一些规律出来
1. 在设置面板ConfigPanel和分类面板CategoryPanel里都有addListener接口；

2. 面板在显示的时候，都需要从数据库中读取信息，并显示在界面上，比如CategoryPanel的updateData方法 ConfigPanel虽然暂时没有updateData这么一个方法，但是也是有这个需要的(当点击工具栏上的设置按钮的时候，也是需要把预算信息读取出来，并显示在面板上的)。

可以预见到，后面的各种面板类，都有类似的功能需求。 这样，就可以抽象出一个WorkingPanel类，来提供这样的功能。

### WorkingPanel

WorkingPanel是一个抽象类，其中声明了方法addListener()和updateData()。

不同的面板类，都应该继承这个抽象类，进而必须提供addListener和updateData方法。

```java
package gui.panel;

import javax.swing.JPanel;

public abstract class WorkingPanel  extends JPanel{
    public abstract void updateData();
    public abstract void addListener();
}
```

### 让CategoryPanel继承WorkingPanel

`public class CategoryPanel extends WorkingPanel`

CategoryPanel本来就有addListener()和updateData()方法，所以无需做其他改动。

### 让ConfigPanel继承WorkingPanel

因为ConfigPanel之前没有updateData()，所以需要提供该方法。在updateData()中，通过ConfigService获取预算和MYSQL路径数据，并显示在组件上：

```
public void updateData() {
    String budget = new ConfigService().get(ConfigService.budget);
    String mysqlPath = new ConfigService().get(ConfigService.mysqlPath);
    tfBudget.setText(budget);
    tfMysqlPath.setText(mysqlPath);
    tfBudget.grabFocus();
}
```

```java {.line-numbers highlight=60}
package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.listener.ConfigListener;
import service.ConfigService;
import util.ColorUtil;
import util.GUIUtil;

public class ConfigPanel extends WorkingPanel {
    static{
        GUIUtil.useLNF();
    }

    public static ConfigPanel instance = new ConfigPanel();

    JLabel lBudget = new JLabel("本月预算(￥)");
    public JTextField tfBudget = new JTextField("0");

    JLabel lMysql = new JLabel("Mysql安装目录");
    public JTextField tfMysqlPath = new JTextField("");

    JButton bSubmit = new JButton("更新");

    public ConfigPanel() {
        GUIUtil.setColor(ColorUtil.grayColor, lBudget,lMysql);
        GUIUtil.setColor(ColorUtil.blueColor, bSubmit);

        JPanel pInput =new JPanel();
        JPanel pSubmit = new JPanel();
        int gap =40;
        pInput.setLayout(new GridLayout(4,1,gap,gap));

        pInput.add(lBudget);
        pInput.add(tfBudget);
        pInput.add(lMysql);
        pInput.add(tfMysqlPath);

        this.setLayout(new BorderLayout());
        this.add(pInput,BorderLayout.NORTH);
        pSubmit.add(bSubmit);
        this.add(pSubmit,BorderLayout.CENTER);

        // 添加监听器，并在构造方法中调用
        addListener();
    }

    public void addListener() {
        ConfigListener l = new ConfigListener();
        bSubmit.addActionListener(l);
    }

    @Override
    public void updateData(){
        String budget = new ConfigService().get(ConfigService.budget);
        String mysqlPath = new ConfigService().get(ConfigService.mysqlPath);
        tfBudget.setText(budget);
        tfMysqlPath.setText(mysqlPath);
        tfBudget.grabFocus();
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(ConfigPanel.instance);
    }

}
```


### 改进CenterPanel

监听器ToolBarListener目前做的工作是把面板显示出来，但是并不能保证面板上刷新数据库中的信息，比如点击设置后，虽然能够切换到预算面板，但是看到的预算值依然是0。

<div align=center><img src=Pictures\改进CenterPanel前.png width=70%></div>

那么怎么解决这个问题呢？

留意监听器ToolBarListener中的这段代码：
`p.workingPanel.show(ConfigPanel.instance);`

WorkingPanel是CenterPanel类型的，那么只需要在show方法中判断，如果是WorkingPanel类型，那么就可以调用其updateData来做到把数据更新到界面上了：

```
if (p instanceof WorkingPanel)
    ((WorkingPanel) p).updateData();
```

这样就达到了这样的效果：点击工具栏上的按钮，**不仅可以切换到不同的面板，而且面板上的数据也马上从数据库中更新**。


```java {.line-numbers highlight=52}
package util;

import gui.panel.WorkingPanel;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Component;

public class CenterPanel extends JPanel
{
    private double rate;  //拉伸比例
    private JComponent c;  //显示的组件
    private boolean stretch;  //是否拉伸

    public CenterPanel(double rate, boolean stretch) {
        this.setLayout(null);
        this.rate = rate;
        this.stretch = stretch;
    }

    public CenterPanel(double rate) {
        this(rate, true);
    }

    public void repaint() {
        if (null != c) {
            Dimension containerSize = this.getSize();
            Dimension componentSize = c.getPreferredSize();

            if(stretch)
                c.setSize((int) (containerSize.width * rate), (int) (containerSize.height * rate));
            else
                c.setSize(componentSize);

            c.setLocation(containerSize.width / 2 - c.getSize().width / 2, containerSize.height / 2 - c.getSize().height / 2);
        }
        super.repaint();
    }

    public void show(JComponent p) {
        this.c = p;
        Component[] cs = getComponents();
        for (Component c : cs) {
            remove(c);
        }
        add(p);

        if (p instanceof WorkingPanel)
            ((WorkingPanel) p).updateData();

        this.updateUI();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(200, 200);
        f.setLocationRelativeTo(null);
        CenterPanel cp = new CenterPanel(0.85,true);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        JButton b  = new JButton("abc");
        cp.show(b);
    }
}
```

这时，点击设置按钮，就可以看到界面上显示的是数据库中的数据：
<div align=center><img src=Pictures\改进CenterPanel后.png width=70%></div>


## 记一笔

### RecordService

RecordService消费记录业务类只有一个方法add。根据消费金额，消费分类，备注和日期添加一条消费记录。

```java
package service;

import java.util.Date;

import dao.RecordDAO;
import entity.Category;
import entity.Record;

public class RecordService {
    RecordDAO recordDao = new RecordDAO();
    public void add(int spend, Category c, String comment, Date date){
        Record r = new Record();
        r.spend = spend;
        r.cid = c.id;
        r.comment = comment;
        r.date = date;
        recordDao.add(r);
    }
}
```

### CategoryComboBoxModel

CategoryComboBoxModel和CategoryTableModel一样，由原来的存放字符串模拟数据的CategoryComboBoxModel，改为存放Category实时信息：

```java
package gui.model;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import entity.Category;
import service.CategoryService;

public class CategoryComboBoxModel implements ComboBoxModel<Category>{

    public List<Category> cs = new CategoryService().list();

    public Category c;

    public CategoryComboBoxModel(){
        if(!cs.isEmpty())
            c=cs.get(0);
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub

        return cs.size();
    }

    @Override
    public Category getElementAt(int index) {
        // TODO Auto-generated method stub
        return cs.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSelectedItem(Object anItem) {
        c = (Category) anItem;
    }

    @Override
    public Object getSelectedItem() {
        if(!cs.isEmpty())
            return c;
        else
            return null;

    }

}
```

### RecordListener

给界面上的"记一笔“ 按钮添加监听：
1. 首先判断是否有消费分类信息，如果没有提示先增加消费分类；
2. 输入的金额不能为0；
3. 调用RecordService的add添加消费记录；
4. 提示添加成功；
5. 添加成功后，切换到消费一览。

```java
package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;

import entity.Category;
import gui.panel.CategoryPanel;
import gui.panel.MainPanel;
import gui.panel.RecordPanel;
import gui.panel.SpendPanel;
import service.RecordService;
import util.GUIUtil;

public class RecordListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        RecordPanel p  = RecordPanel.instance;
        if(0==p.cbModel.cs.size()){
            JOptionPane.showMessageDialog(p, "暂无消费分类，无法添加，请先增加消费分类");
            MainPanel.instance.workingPanel.show(CategoryPanel.instance);
            return;
        }

        if(!GUIUtil.checkZero(p.tfSpend,"花费金额"))
            return;
        int spend = Integer.parseInt(p.tfSpend.getText());
        Category c = p.getSelectedCategory();
        String comment = p.tfComment.getText();
        Date d = p.datepick.getDate();
        new RecordService().add(spend, c, comment, d);
        JOptionPane.showMessageDialog(p, "添加成功");

        MainPanel.instance.workingPanel.show(SpendPanel.instance);

    }

}
```


### RecordPanel

RecordPanel 消费记录面板做了如下改动：
1. 继承了WorkingPanel，从而必须提供addListener()和updateData()方法
2. 提供getSelectedCategory()用于获取当前选中的分类对象。
3. addListener() 给按钮添加监听
4. updateData()更新数据，主要是更新下拉框中的分类信息，并且让各个输入框信息重置，以及让焦点停留在金额的输入框上

```java
package gui.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import entity.Category;
import gui.listener.RecordListener;
import gui.model.CategoryComboBoxModel;
import service.CategoryService;
import util.ColorUtil;
import util.GUIUtil;

public class RecordPanel extends WorkingPanel {
    static{
        GUIUtil.useLNF();
    }
    public static RecordPanel instance = new RecordPanel();

    JLabel lSpend = new JLabel("花费(￥)");
    JLabel lCategory = new JLabel("分类");
    JLabel lComment = new JLabel("备注");
    JLabel lDate = new JLabel("日期");

    public JTextField tfSpend = new JTextField("0");

    public CategoryComboBoxModel cbModel = new CategoryComboBoxModel();
    public JComboBox<Category> cbCategory = new JComboBox<>(cbModel);
    public JTextField tfComment = new JTextField();
    public JXDatePicker datepick = new JXDatePicker(new Date());

    JButton bSubmit = new JButton("记一笔");

    public RecordPanel() {
        GUIUtil.setColor(ColorUtil.grayColor, lSpend,lCategory,lComment,lDate);
        GUIUtil.setColor(ColorUtil.blueColor, bSubmit);
        JPanel pInput =new JPanel();
        JPanel pSubmit = new JPanel();
        int gap = 40;
        pInput.setLayout(new GridLayout(4,2,gap,gap));

        pInput.add(lSpend);
        pInput.add(tfSpend);
        pInput.add(lCategory);
        pInput.add(cbCategory);
        pInput.add(lComment);
        pInput.add(tfComment);
        pInput.add(lDate);
        pInput.add(datepick);

        pSubmit.add(bSubmit);

        this.setLayout(new BorderLayout());
        this.add(pInput,BorderLayout.NORTH);
        this.add(pSubmit,BorderLayout.CENTER);

        addListener();
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(RecordPanel.instance);
    }

    public Category getSelectedCategory(){
        return (Category) cbCategory.getSelectedItem();
    }

    @Override
    public void updateData() {
        cbModel.cs = new CategoryService().list();
        cbCategory.updateUI();
        resetInput();
        tfSpend.grabFocus();
    }

    public void resetInput(){
        tfSpend.setText("0");
        tfComment.setText("");
        if(0!=cbModel.cs.size())
            cbCategory.setSelectedIndex(0);
        datepick.setDate(new Date());
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        RecordListener listener = new RecordListener();
        bSubmit.addActionListener(listener);
    }

}
```


## 消费一览

### SpendPage

这是一个专门用于SpendPanel 消费一览面板 的页面类，换句话说，消费一览面板上需要什么信息，这个类就封装什么信息。

所以其字段都是界面上对应的信息：
```
//本月消费
public String monthSpend;
//今日消费
public String todaySpend;
//日均消费
public String avgSpendPerDay;
//本月剩余
public String monthAvailable;
//日均可用
public String dayAvgAvailable;
//距离月末
public String monthLeftDay;
//使用比例
public int usagePercentage;
//是否超支
public boolean isOverSpend = false; 
```

```java
package gui.page;

public class SpendPage {
    //本月消费
    public String monthSpend;
    //今日消费
    public String todaySpend;
    //日均消费
    public String avgSpendPerDay;
    //本月剩余
    public String monthAvailable;
    //日均可用
    public String dayAvgAvailable;
    //距离月末
    public String monthLeftDay;
    //使用比例
    public int usagePercentage;
    //是否超支
    public boolean isOverSpend = false;

    public SpendPage(int monthSpend, int todaySpend, int avgSpendPerDay, int monthAvailable, int dayAvgAvailable,
                     int monthLeftDay, int usagePercentage) {
        this.monthSpend = "￥" + monthSpend;
        this.todaySpend = "￥" + todaySpend;
        this.avgSpendPerDay = "￥" + avgSpendPerDay;
        if (monthAvailable < 0)
            isOverSpend = true;

        if (!isOverSpend) {
            this.monthAvailable = "￥" + monthAvailable;
            this.dayAvgAvailable = "￥" + dayAvgAvailable;
        } else {
            this.monthAvailable = "超支" + (0 - monthAvailable);
            this.dayAvgAvailable = "￥0";
        }

        this.monthLeftDay = monthLeftDay + "天";
        this.usagePercentage = usagePercentage;
    }
}
```

### SpendService

SpendService消费一览业务类的getSpendPage()方法，返回SpendPage用于界面显示。getSpendPage()方法中的代码说明都在注释中。

```java
package service;

import java.util.List;

import dao.RecordDAO;
import entity.Record;
import gui.page.SpendPage;
import util.DateUtil;

public class SpendService {

    public SpendPage getSpendPage() {
        RecordDAO dao = new RecordDAO();
        // 本月数据
        List<Record> thisMonthRecords = dao.listThisMonth();
        // 今日数据
        List<Record> toDayRecords = dao.listToday();
        // 本月总天数
        int thisMonthTotalDay = DateUtil.thisMonthTotalDay();

        int monthSpend = 0;
        int todaySpend = 0;
        int avgSpendPerDay = 0;
        int monthAvailable = 0;
        int dayAvgAvailable = 0;
        int monthLeftDay = 0;
        int usagePercentage = 0;

        // 预算
        int monthBudget = new ConfigService().getIntBudget();

        // 统计本月消费
        for (Record record : thisMonthRecords) {
            monthSpend += record.getSpend();
        }

        // 统计今日消费
        for (Record record : toDayRecords) {
            todaySpend += record.getSpend();
        }
        // 计算日均消费
        avgSpendPerDay = monthSpend / thisMonthTotalDay;
        // 计算本月剩余
        monthAvailable = monthBudget - monthSpend;

        // 距离月末
        monthLeftDay = DateUtil.thisMonthLeftDay();

        // 计算日均可用
        dayAvgAvailable = monthAvailable / monthLeftDay;

        // 计算使用比例
        usagePercentage = monthSpend * 100 / monthBudget;

        // 根据这些信息，生成SpendPage对象

        return new SpendPage(monthSpend, todaySpend, avgSpendPerDay, 
                monthAvailable, dayAvgAvailable, monthLeftDay, usagePercentage);
    }
}
```


### SpendPanel

SpendPanel消费一览面板类，也继承了WorkingPanel。 虽然SpendPanel不需要添加监听，但是要用到updateData()方法更新界面信息。

在updateData()方法中根据SpendService获取页面对象：
`SpendPage spend = new SpendService().getSpendPage();`
然后把这个页面对象的值，更新到组件里。

如果超支了，那么就把相应的组件设置为红色，否则就是正常的颜色。

```java
package gui.panel;

import static util.GUIUtil.setColor;
import static util.GUIUtil.showPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.page.SpendPage;
import service.SpendService;
import util.CircleProgressBar;
import util.ColorUtil;

public class SpendPanel extends WorkingPanel {
    public static SpendPanel instance = new SpendPanel();

    JLabel lMonthSpend = new JLabel("本月消费");
    JLabel lTodaySpend = new JLabel("今日消费");
    JLabel lAvgSpendPerDay = new JLabel("日均消费");
    JLabel lMonthLeft = new JLabel("本月剩余");
    JLabel lDayAvgAvailable = new JLabel("日均可用");
    JLabel lMonthLeftDay = new JLabel("距离月末");

    JLabel vMonthSpend = new JLabel("￥2300");
    JLabel vTodaySpend = new JLabel("￥25");
    JLabel vAvgSpendPerDay = new JLabel("￥120");
    JLabel vMonthAvailable = new JLabel("￥2084");
    JLabel vDayAvgAvailable = new JLabel("￥389");
    JLabel vMonthLeftDay = new JLabel("15天");

    CircleProgressBar bar;

    public SpendPanel() {
        this.setLayout(new BorderLayout());
        bar = new CircleProgressBar();
        bar.setBackgroundColor(ColorUtil.blueColor);

        setColor(ColorUtil.grayColor, lMonthSpend, lTodaySpend, lAvgSpendPerDay, lMonthLeft, lDayAvgAvailable,
                lMonthLeftDay, vAvgSpendPerDay, vMonthAvailable, vDayAvgAvailable, vMonthLeftDay);
        setColor(ColorUtil.blueColor, vMonthSpend, vTodaySpend);

        vMonthSpend.setFont(new Font("微软雅黑", Font.BOLD, 23));
        vTodaySpend.setFont(new Font("微软雅黑", Font.BOLD, 23));

        this.add(center(), BorderLayout.CENTER);
        this.add(south(), BorderLayout.SOUTH);

    }

    private JPanel center() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(west(), BorderLayout.WEST);
        p.add(east());

        return p;
    }

    private Component east() {

        return bar;
    }

    private Component west() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 1));
        p.add(lMonthSpend);
        p.add(vMonthSpend);
        p.add(lTodaySpend);
        p.add(vTodaySpend);
        return p;
    }

    private JPanel south() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 4));

        p.add(lAvgSpendPerDay);
        p.add(lMonthLeft);
        p.add(lDayAvgAvailable);
        p.add(lMonthLeftDay);
        p.add(vAvgSpendPerDay);
        p.add(vMonthAvailable);
        p.add(vDayAvgAvailable);
        p.add(vMonthLeftDay);

        return p;
    }

    public static void main(String[] args) {
        showPanel(SpendPanel.instance);
    }

    @Override
    public void updateData() {
        SpendPage spend = new SpendService().getSpendPage();
        vMonthSpend.setText(spend.monthSpend);
        vTodaySpend.setText(spend.todaySpend);
        vAvgSpendPerDay.setText(spend.avgSpendPerDay);
        vMonthAvailable.setText(spend.monthAvailable);
        vDayAvgAvailable.setText(spend.dayAvgAvailable);
        vMonthLeftDay.setText(spend.monthLeftDay);

        bar.setProgress(spend.usagePercentage);
        if (spend.isOverSpend) {
            vMonthAvailable.setForeground(ColorUtil.warningColor);
            vMonthSpend.setForeground(ColorUtil.warningColor);
            vTodaySpend.setForeground(ColorUtil.warningColor);

        } else {
            vMonthAvailable.setForeground(ColorUtil.grayColor);
            vMonthSpend.setForeground(ColorUtil.blueColor);
            vTodaySpend.setForeground(ColorUtil.blueColor);
        }
        bar.setForegroundColor(ColorUtil.getByPercentage(spend.usagePercentage));
        addListener();

    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub

    }
}
```


## 月消费报表

### ReportService

报表业务类ReportService

`listThisMonthRecords()`
返回一个消费记录集合，假设本月有30天，那么这个集合就有30条Record。每个Record对应一天的消费总金额，如果那天没有消费，则消费金额为0。

`getDaySpend()`
获取某一天的消费总金额，这天的消费可能有多笔，把这几笔消费加起来算在一起。

```java
package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.RecordDAO;
import entity.Record;
import util.DateUtil;

public class ReportService {

    /**
     * 获取某一天的消费金额
     * @param d
     * @param monthRawData
     * @return
     */
    public int getDaySpend(Date d,List<Record> monthRawData){
        int daySpend = 0;
        for (Record record : monthRawData) {
            if(record.date.equals(d))
                daySpend += record.spend;
        }
        return daySpend;
    }

    /**
     * 获取一个月的消费记录集合
     * @return
     */
    public List<Record> listThisMonthRecords() {
        RecordDAO dao = new RecordDAO();
        List<Record> monthRawData = dao.listThisMonth();
        List<Record> result = new ArrayList<>();
        Date monthBegin = DateUtil.monthBegin();
        int monthTotalDay = DateUtil.thisMonthTotalDay();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < monthTotalDay; i++) {
            Record r = new Record();
            c.setTime(monthBegin);
            c.add(Calendar.DATE, i);
            Date eachDayOfThisMonth =c.getTime() ;
            int daySpend = getDaySpend(eachDayOfThisMonth,monthRawData);
            r.spend = daySpend;
            result.add(r);
        }
        return result;

    }

}
```


### ChartUtil

以前的ChartUtil是模拟数据，现在ChartUtil要根据ReportService.listThisMonthRecords() 返回的集合来生成相应的图表：

```
public static Image getImage(List<Record> rs, int width, int height) {
    // 根据消费记录得到的样本数据
    double[] sampleValues = sampleValues(rs);
    // 根据消费记录得到的下方日期文本
    String[] sampleLabels = sampleLabels(rs);
    ....
    ....
}
```

```java
package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.objectplanet.chart.BarChart;
import com.objectplanet.chart.Chart;

import entity.Record;

public class ChartUtil {
    private static String[] sampleLabels(List<Record> rs) {
        String[] sampleLabels = new String[rs.size()];
        for (int i = 0; i < sampleLabels.length; i++) {
            if (0 == i % 5)
                sampleLabels[i] = String.valueOf(i + 1 + "日");
        }

        return sampleLabels;

    }

    public static double[] sampleValues(List<Record> rs) {
        double[] sampleValues = new double[rs.size()];
        for (int i = 0; i < sampleValues.length; i++) {
            sampleValues[i] = rs.get(i).spend;
        }

        return sampleValues;
    }

    public static Image getImage(List<Record> rs, int width, int height) {
        // 根据消费记录得到的样本数据
        double[] sampleValues = sampleValues(rs);
        // 根据消费记录得到的下方日期文本
        String[] sampleLabels = sampleLabels(rs);
        // 样本中的最大值
        int max = max(sampleValues);

        // 数据颜色
        Color[] sampleColors = new Color[] { ColorUtil.blueColor };

        // 柱状图
        BarChart chart = new BarChart();

        // 设置样本个数
        chart.setSampleCount(sampleValues.length);
        // 设置样本数据
        chart.setSampleValues(0, sampleValues);
        // 设置文字
        chart.setSampleLabels(sampleLabels);
        // 设置样本颜色
        chart.setSampleColors(sampleColors);
        // 设置取值范围
        chart.setRange(0, max * 1.2);
        // 显示背景横线
        chart.setValueLinesOn(true);
        // 显示文字
        chart.setSampleLabelsOn(true);
        // 把文字显示在下方
        chart.setSampleLabelStyle(Chart.BELOW);

        // 样本值的字体
        chart.setFont("rangeLabelFont", new Font("Arial", Font.BOLD, 12));
        // 显示图例说明
        chart.setLegendOn(true);
        // 把图例说明放在左侧
        chart.setLegendPosition(Chart.LEFT);
        // 图例说明中的文字
        chart.setLegendLabels(new String[] { "月消费报表" });
        // 图例说明的字体
        chart.setFont("legendFont", new Font("Dialog", Font.BOLD, 13));
        // 下方文字的字体
        chart.setFont("sampleLabelFont", new Font("Dialog", Font.BOLD, 13));
        // 图表中间背景颜色
        chart.setChartBackground(Color.white);
        // 图表整体背景颜色
        chart.setBackground(ColorUtil.backgroundColor);
        // 把图表转换为Image类型
        Image im = chart.getImage(width, height);
        return im;
    }

    public static int max(double[] sampleValues) {
        int max = 0;
        for (double v : sampleValues) {
            if (v > max)
                max = (int) v;
        }
        return max;

    }

    private static String[] sampleLabels() {
        String[] sampleLabels = new String[30];

        for (int i = 0; i < sampleLabels.length; i++) {
            if (0 == i % 5)
                sampleLabels[i] = String.valueOf(i + 1 + "日");
        }
        return sampleLabels;
    }

    public static Image getImage(int width, int height) {
        // 模拟样本数据
        double[] sampleValues = sampleValues();
        // 下方显示的文字
        String[] sampleLabels = sampleLabels();
        // 样本中的最大值
        int max = max(sampleValues);

        // 数据颜色
        Color[] sampleColors = new Color[] { ColorUtil.blueColor };

        // 柱状图
        BarChart chart = new BarChart();

        // 设置样本个数
        chart.setSampleCount(sampleValues.length);
        // 设置样本数据
        chart.setSampleValues(0, sampleValues);
        // 设置文字
        chart.setSampleLabels(sampleLabels);
        // 设置样本颜色
        chart.setSampleColors(sampleColors);
        // 设置取值范围
        chart.setRange(0, max * 1.2);
        // 显示背景横线
        chart.setValueLinesOn(true);
        // 显示文字
        chart.setSampleLabelsOn(true);
        // 把文字显示在下方
        chart.setSampleLabelStyle(Chart.BELOW);

        // 样本值的字体
        chart.setFont("rangeLabelFont", new Font("Arial", Font.BOLD, 12));
        // 显示图例说明
        chart.setLegendOn(true);
        // 把图例说明放在左侧
        chart.setLegendPosition(Chart.LEFT);
        // 图例说明中的文字
        chart.setLegendLabels(new String[] { "月消费报表" });
        // 图例说明的字体
        chart.setFont("legendFont", new Font("Dialog", Font.BOLD, 13));
        // 下方文字的字体
        chart.setFont("sampleLabelFont", new Font("Dialog", Font.BOLD, 13));
        // 图表中间背景颜色
        chart.setChartBackground(Color.white);
        // 图表整体背景颜色
        chart.setBackground(ColorUtil.backgroundColor);
        // 把图表转换为Image类型
        Image im = chart.getImage(width, height);
        return im;
    }

    private static double[] sampleValues() {

        double[] result = new double[30];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * 300);
        }
        return result;

    }

    public static void main(String[] args) {
        JPanel p = new JPanel();
        JLabel l = new JLabel();
        Image img = ChartUtil.getImage(400, 300);
        Icon icon = new ImageIcon(img);
        l.setIcon(icon);
        p.add(l);
        GUIUtil.showPanel(p);
    }

}
```


### ReportPanel

ReportPanel报表面板类继承WorkingPanel，实现updateDate()方法来更新界面上的数据：

```
public void updateData() {
    List<Record> rs = new ReportService().listThisMonthRecords(); 
    Image i = ChartUtil.getImage(rs, 350, 250);
    ImageIcon icon = new ImageIcon(i);
    l.setIcon(icon);
}
```

```java
package gui.panel;

import static util.GUIUtil.showPanel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import entity.Record;
import service.ReportService;
import util.ChartUtil;

public class ReportPanel extends WorkingPanel {
    public static ReportPanel instance = new ReportPanel();

    JLabel l = new JLabel();

    public ReportPanel() {
        this.setLayout(new BorderLayout());
        List<Record> rs = new ReportService().listThisMonthRecords();
        Image i = ChartUtil.getImage(rs, 400, 300);
        ImageIcon icon = new ImageIcon(i);
        l.setIcon(icon);
        this.add(l);
        addListener();
    }

    public static void main(String[] args) {
        showPanel(ReportPanel.instance);
    }

    @Override
    public void updateData() {
        List<Record> rs = new ReportService().listThisMonthRecords();
        Image i = ChartUtil.getImage(rs, 350, 250);
        ImageIcon icon = new ImageIcon(i);
        l.setIcon(icon);
    }

    @Override
    public void addListener() {

    }
}
```


## MysqlUtil

备份方法backup()
通过Runtime调用mysqldump.exe进行备份

恢复方法recover
通过Runtime调用mysql.exe进行数据的还原

```java
package util;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MysqlUtil {

    public static void backup(String mysqlPath, String backupfile) throws IOException {
        String commandFormat = "\"%s/bin/mysqldump.exe\" -u%s -p%s   -hlocalhost   -P%d %s -r \"%s\"";

        String command = String.format(commandFormat, mysqlPath, DBUtil.loginName, DBUtil.password, DBUtil.port,
                DBUtil.database, backupfile);
        Runtime.getRuntime().exec(command);
    }

    public static void recover(String mysqlPath, String recoverfile) {
        try {
            String commandFormat = "\"%s/bin/mysql.exe\" -u%s -p%s   %s ";
            String command = String.format(commandFormat, mysqlPath, DBUtil.loginName, DBUtil.password,
                    DBUtil.database);

            Process p = Runtime.getRuntime().exec(command);
            OutputStream out = p.getOutputStream();
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(recoverfile), "utf8"));
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
            writer.write(outStr);
            writer.flush();
            out.close();
            br.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        String mysqlPath = "D:\\WinSoftware\\MySQL\\mysql-8.0.19-winx64";
        String file = "D:\\HutuBill\\hutubill.sql";

        // backup(mysqlPath, file);
        // restore();
        // recover(mysqlPath, file);
        // recover(file);
        recover(mysqlPath, file);

    }

}
```


## 备份

### BackupListener

BackupListener备份监听器：

1. 首先判断MYSQL安装路径是否配置

2. 打开文件选择器，指定要保存的文件
    文件名默认设置为hutubill.sql；以后缀名.sql过滤文件

3. 调用MysqlUtil进行备份

4. 提示备份成功

```java
package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import gui.panel.BackupPanel;
import gui.panel.ConfigPanel;
import gui.panel.MainPanel;
import service.ConfigService;
import util.MysqlUtil;

public class BackupListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        BackupPanel p  =BackupPanel.instance;
        String mysqlPath= new ConfigService().get(ConfigService.mysqlPath);
        if(0==mysqlPath.length()){
            JOptionPane.showMessageDialog(p, "备份前请事先配置mysql的路径");
            MainPanel.instance.workingPanel.show(ConfigPanel.instance);
            ConfigPanel.instance.tfMysqlPath.grabFocus();
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("hutubill.sql"));
        fc.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return ".sql";
            }

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".sql");
            }
        });

        int returnVal =  fc.showSaveDialog(p);
        File file = fc.getSelectedFile();
        System.out.println(file);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //如果保存的文件名没有以.sql结尾，自动加上.sql
            System.out.println(file);
            if(!file.getName().toLowerCase().endsWith(".sql"))
                file = new File(file.getParent(),file.getName()+".sql");
            System.out.println(file);

            try {
                MysqlUtil.backup(mysqlPath, file.getAbsolutePath());
                JOptionPane.showMessageDialog(p, "备份成功,备份文件位于:\r\n"+file.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(p, "备份失败\r\n,错误:\r\n"+e1.getMessage());
            }

        }
    }

}
```


### BackupPanel

BackupPanel备份面板类继承WorkingPanel类，并实现addListener：

```
BackupListener listener = new BackupListener();
bBackup.addActionListener(listener);
```

```java
package gui.panel;

import javax.swing.JButton;

import gui.listener.BackupListener;
import util.ColorUtil;
import util.GUIUtil;

public class BackupPanel extends WorkingPanel {
    static {
        GUIUtil.useLNF();
    }

    public static BackupPanel instance = new BackupPanel();
    JButton bBackup = new JButton("备份");

    public BackupPanel() {
        GUIUtil.setColor(ColorUtil.blueColor, bBackup);
        this.add(bBackup);
        addListener();
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(BackupPanel.instance);
    }

    @Override
    public void updateData() {

    }

    @Override
    public void addListener() {
        BackupListener listener = new BackupListener();
        bBackup.addActionListener(listener);
    }

}
```

## 恢复

### RecoverListener

RecoverListener恢复监听器：

1. 首先判断MYSQL安装路径是否配置

2. 打开文件选择器，指定要打开的文件
    文件名默认设置为hutubill.sql；根据后缀名.sql过滤文件

3. 调用MysqlUtil进行恢复

4. 提示恢复成功

```java
package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import gui.panel.BackupPanel;
import gui.panel.ConfigPanel;
import gui.panel.MainPanel;
import service.ConfigService;
import util.MysqlUtil;

public class RecoverListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        BackupPanel p  =BackupPanel.instance;
        String mysqlPath= new ConfigService().get(ConfigService.mysqlPath);
        if(0==mysqlPath.length()){
            JOptionPane.showMessageDialog(p, "恢复前请事先配置mysql的路径");
            MainPanel.instance.workingPanel.show(ConfigPanel.instance);
            ConfigPanel.instance.tfMysqlPath.grabFocus();
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("hutubill.sql"));
        fc.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return ".sql";
            }

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".sql");
            }
        });

        int returnVal =  fc.showOpenDialog(p);
        File file = fc.getSelectedFile();
        System.out.println(file);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                MysqlUtil.recover(mysqlPath,file.getAbsolutePath());
//              MysqlUtil.recover(mysqlPath, file.getAbsolutePath());
                JOptionPane.showMessageDialog(p, "恢复成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(p, "恢复失败\r\n,错误:\r\n"+e1.getMessage());
            }

        }
    }

}
```


### RecoverPanel

RecoverPanel恢复面板类继承WorkingPanel 类，并实现addListener：

```
RecoverListener listener = new RecoverListener();
bRecover.addActionListener(listener);
```

```java
package gui.panel;

import javax.swing.JButton;

import gui.listener.RecoverListener;
import util.ColorUtil;
import util.GUIUtil;

public class RecoverPanel extends WorkingPanel {
    static{
        GUIUtil.useLNF();
    }
    public static RecoverPanel instance = new RecoverPanel();

    JButton bRecover = new JButton("恢复");

    public RecoverPanel() {

        GUIUtil.setColor(ColorUtil.blueColor, bRecover);
        this.add(bRecover);

        addListener();
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(RecoverPanel.instance);
    }

    @Override
    public void updateData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addListener() {
        RecoverListener listener = new RecoverListener();
        bRecover.addActionListener(listener);
    }

}
```

# 总结