# 基础

## 环境配置

jdk：1.8；Maven：3.5

导入数据：`"C:\Program Files (x86)\MySQL\MySQL Server 5.5\bin\mysql.exe" -u root -padmin  --default-character-set=utf8 tmall_springboot < D:\tmall_springboot_altogether\tmall_springboot.sql`

<div><img src=tmall\tmall采用技术.jpg></div>

## 开发流程

1. 需求分析
    首先确定要做哪些功能：需求分析包括**前台**和**后台**。
    前台又分为单纯要**展示**的哪些功能，以及会**提交数据到服务端**的哪些功能--**交互**。

2. 表结构设计
    表结构设计是围绕功能需求进行，如果表结构设计有问题，那么将会影响功能的实现。除了表与表关系，建表SQL语句之外，为了更好的帮助大家理解表结构以及关系，还特意把**表与页面功能**一一对应起来。

3. 原型
    与客户沟通顺畅的项目设计流程里一定会有原型这个环节，借助**界面原型**，可以低成本，高效率的与客户达成需求的一致性。 同样的，原型分为了前台原型和后台原型。

4. 后台-分类管理
    接下来开始进行**功能开发**，按照模块之间的依赖关系，首先进行`后台-分类`管理功能开发。严格来说，这是开发的第一个功能，所以讲解的十分详细，不仅提供了可运行的项目，还详细解释了其中用到的HTML包含关系，以及每个具体的功能： `查询，分页，增加，删除，编辑，修改`。

5. 后台-其他管理
    在把后台-分类管理 消化吸收之后，就可以加速进行`后台其他页面`的学习。

6. 前台-首页
    前台也包括许多功能，与后台-分类管理类似的，首先把`前台-首页`这个功能单独拿出来，进行精讲。前台-首页消化吸收好之后，再进行其他前台功能的开发。

7. 前台无需登录
    从前台模块之间的依赖性，以及开发顺序的合理性来考虑，把前台功能分为了**无需登录**即可使用的功能，和**需要登录**才能访问的功能。 建立在前一步前台-首页的基础之上，开始进行一系列的无需登录功能开发。

8. 前台需要登录
    最后是需要登录的前台功能。这部分功能基本上都是和购物相关的。 因此，一开始先把购物流程 单独拿出来捋清楚，其中还特别注明了购物流程环节与表关系，这样能够更好的建立对前端购物功能的理解。随着这部分功能的开发，就会进入订单生成部分，在此之前，先准备了一个订单状态图，在理解了这个图之后，可以更好的进行订单相关功能的开发。


# 需求分析

1. 前端展示
    在前端页面上显示数据库中的数据，如首页，产品页，购物车，分类页面等等。
    至于这些前端页面如何组织显示，页面布局，css样式设置，Javascript交互代码等教学，在单独的模仿天猫前端教程中详细讲解。
    

2. 前端交互
    这里的前端交互指的是通过`POST`，`GET`等`http协议`，与服务端进行同步或者异步数据交互。 比如购买，购物车，生成订单，登录等等功能。

3. 后台功能
    对支撑整站需要用到的数据，进行管理维护。比如分类管理，分类属性管理，产品管理，产品图片管理，用户管理，订单管理等等。


# 表结构设计

## 创建数据库

创建数据库`tmall`，并且将数据库的编码设置为`utf8`，**便于存放中文**。

在`MySQL-Front`的`SQL编辑器`中键入：
```SQL
DROP DATABASE IF EXISTS tmall;
CREATE DATABASE tmall DEFAULT CHARACTER SET utf8;
```

## 表与表关系

<div align=center><img src=tmall\表关系图.png></div>

<div align=center><img src=tmall\表的详细内容.jpg></div>

<div align=center><img src=tmall\表的一对多关系.jpg></div>

## 创建表

**主键与外键：**

**学生表**(学号，姓名，性别，班级) 
其中每个学生的学号是唯一的，**学号就是一个主键** ；

**课程表**(课程编号，课程名，学分) 
其中课程编号是唯一的，**课程编号就是一个主键** ；

**成绩表**(学号，课程号，成绩) 
成绩表中单一一个属性无法唯一标识一条记录，**学号和课程号的组合才可以唯一标识一条记录**，所以学号和课程号的属性组是一个主键 。
  
成绩表中的学号不是成绩表的主键，但**它和学生表中的学号相对应**，并且**学生表中的学号是学生表的主键**，则称**成绩表中的学号是学生表的外键**；同理，**成绩表中的课程号是课程表的外键**。

在建表过程中，会设置外键约束，所以表和表之间有依赖关系。因此会**先建立被外键指向的表**，比如`User`，`Category`，然后再是其他表。

```SQL
DROP DATABASE IF EXISTS tmall;
CREATE DATABASE tmall DEFAULT CHARACTER SET utf8;
USE tmall;

# 被外键指向的表

#用户表
CREATE TABLE user (
       id int(11) NOT Null AUTO_INCREMENT,
       name varchar(255) DEFAULT NULL,
       salt varchar(255) DEFAULT NULL,  # 用来和Shiro结合时加密用
       PRIMARY KEY (id)  # 主键能唯一标识一条记录
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 分类表
CREATE TABLE category (
       id int(11) NOT NULL AUTO_INCREMENT,
       name varchar(255) DEFAULT NULL,
       PRIMARY KEY (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;


# 有外键约束，让表与表之间产生关联

# 属性表
CREATE TABLE property (
       id int(11) NOT NULL AUTO_INCREMENT,
       cid int(11) DEFAULT NULL, # 外键，指向分类表的id
       name varchar(255) DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_property_category FOREIGN KEY (cid) REFERENCES category (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 产品表
CREATE TABLE product (
       id int(11) NOT NULL AUTO_INCREMENT,
       name varchar(255) DEFAULT NULL,
       subTitle varchar(255) DEFAULT NULL,
       orginalPrice float DEFAULT NULL,
       promotePrice float DEFAULT NULL,  # 促销价
       stock int(11) DEFAULT NULL,  # 库存
       cid int(11) DEFAULT NULL,
       createDate datetime DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_product_category FOREIGN KEY (id) REFERENCES category (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 属性值表
CREATE TABLE propertyvalue (
       id int(11) NOT NULL AUTO_INCREMENT,
       pid int(11) DEFAULT NULL,
       ptid int(11) DEFAULT NULL,
       value varchar(255) DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_propertyvalue_property FOREIGN KEY (ptid) REFERENCES property (id),
       CONSTRAINT fk_propertyvalue_product FOREIGN KEY (pid) REFERENCES product (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 产品图片表
CREATE TABLE productimage (
       id int(11) NOT NULL AUTO_INCREMENT,
       pid int(11) DEFAULT NULL,
       type varchar(255) DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_productimge_product FOREIGN KEY (pid) REFERENCES product (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 评价表
CREATE TABLE review (
       id int(11) NOT NULL AUTO_INCREMENT,
       content varchar(4000) DEFAULT NULL,
       uid int(11) DEFAULT NULL,
       pid int(11)DEFAULT NULL,
       createDate datetime DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_review_product FOREIGN KEY (pid) REFERENCES product (id),
       CONSTRAINT fk_review_user FOREIGN KEY (uid) REFERENCES user (id) 
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 订单表
CREATE TABLE order_ (
       id int(11) NOT NULL AUTO_INCREMENT,
       orderCode varchar(255) DEFAULT NULL,  # 订单号
       address varchar(255) DEFAULT NULL,
       post varchar(255) DEFAULT NULL,
       receiver varchar(255) DEFAULT NULL,  # 收货人信息
       mobile varchar(255) DEFAULT NULL,
       userMessage varchar(255) DEFAULT NULL,
       createDate datetime DEFAULT NULL,
       payDate datetime DEFAULT NULL,
       deliveryDate datetime DEFAULT NULL,  # 发货日期
       confirmDate datetime DEFAULT NULL,  # 收货日期
       uid int(11) DEFAULT NULL,
       status varchar(255) DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_order_user FOREIGN KEY (uid) REFERENCES user (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 订单项表
CREATE TABLE orderitem (
       id int(11) NOT NULL AUTO_INCREMENT,
       pid int(11) DEFAULT NULL,
       oid int(11) DEFAULT NULL,
       uid int(11) DEFAULT NULL,
       number int(11) DEFAULT NULL,
       PRIMARY KEY (id),
       CONSTRAINT fk_orderitem_user FOREIGN KEY (uid) REFERENCES user (id),
       CONSTRAINT fk_orderitem_product FOREIGN KEY (pid) REFERENCES product (id),
       CONSTRAINT fk_orderitem_order FOREIGN KEY (oid) REFERENCES order_ (id)
)      ENGINE=InnoDB DEFAULT CHARSET=utf8;
```