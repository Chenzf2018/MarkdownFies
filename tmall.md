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

# 后台

开发整站的顺序，通常来说还是按照依赖性来进行，前端需要的数据，都要先通过后台的功能维护在数据库中，才可以拿到。所以，先进行后台功能的开发，然后再是前台功能的开发。

## 分类管理

首先使用经典的springboot模式进行由浅入深地开发出第一个分类管理模块。

为了便于理解和消化这部分知识，分类管理模块的开发采用循序渐进的方式
1. 首先下载一个只有分类管理的可运行项目，先跑起来看看效果
2. 再以查询为例子，从零开始，按部就班地做出这么一个查询的功能来
3. 接着分页功能单独拿出来讲解
4. 最后讲解分类管理中其他的，增加，删除，编辑和修改功能

关于Redis, ES(ElasticSearch)和Shiro的支持，会放在后面专门来做。先做不支持Redis, ES和Shiro的功能，然后再在这个基础上进行改造。

### 可运行项目

**步骤一**：下载tmall_springboot_v1，并解压到`D:\tmall\tmall_springboot_v1`。

**步骤二**：[导入数据库的表结构](#创建表)；
    导入项目：此项目采用maven项目格式，并且使用IDEA集成开发环境开发，所以这一步要把该项目导入到IDEA中。

IDEA导入Springboot项目办法：

1. `File->New->Project From Existing Sources`
2. 选中项目中`pom.xml`
3. 点击OK，然后后面就一路Next

<div align=center><img src=tmall\IDEA导入项目.jpg></div>

**步骤三：**启动项目

问题：
```
***************************
APPLICATION FAILED TO START
***************************

Description:

The Tomcat connector configured to listen on port 8080 failed to start. The port may already be in use or the connector may be misconfigured.

Action:

Verify the connector's configuration, identify and stop any process that's listening on port 8080, or configure this application to listen on another port.
```

解决方案：

可以在结构目录`/src/main/resources`中找到一个属性文件`application.properties`，它是一个默认的配置文件，通过它可以根据自己的需要实现自定义功能。

如果当前`8080`端口已经被占用，我们希望使用`8090`端口启动`Tomcat`，那么只需要在这个文件中添加一行：`server.port=8090`。

```
s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
com.how2java.tmall.Application           : Started Application in 5.384 seconds (JVM running for 5.943)
```

通过IDEA启动并部署项目之后，访问地址：`http://127.0.0.1:8090/tmall_springboot/admin`

### 查询

**版本问题**

IDEA建议使用IDEA 2017，如果一定要用IDEA 2018, 需要做如下修改：
`File->Settings->Build,Execution,Deployment->Build Tools->Maven->Importing`取消 `Store generated project files externally`。

本项目教程SpringBoot用的是`1.5.9.RELEASE`。


**创建新项目**

点击`Create New Project`新建项目，选中`Spring Initializr`；

项目参数：
<div align=center><img src=tmall\项目参数.jpg></div>

依赖选择：
<div align=center><img src=tmall\依赖选择.jpg width=70%></div>

项目路径：
<div align=center><img src=tmall\项目路径.jpg width=70%></div>


**Maven**

可将项目需要用的`jar`包放入Maven仓库中：`D:\WinSoftware\MavenRepository`。

**删除包**

删除TmallSpringbootApplication.java，并且把其包也删除掉，仅剩`java`包。

**修改`pom.xml`**

复制如下内容到已经存在的pom里。这个过程会导致idea去下载pom里声明的相关jar包，会花一定的时间。此时会弹出如图所示的提醒，为了避免每次修改pom.xml都出现这个对话框，点击"Enable Auto-Import"。为了确保导入成功，右键点击`pom.xml->Maven->Reimport`

```XML
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>chenzf</groupId>
    <artifactId>tmall_springboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>tmall_springboot</name>
    <description>tmall_springboot</description>
    <packaging>war</packaging>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    <dependencies>
        <!-- springboot web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- springboot tomcat 支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <!-- 热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- jpa-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- springboot test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- thymeleaf -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!-- elastic search -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
        <!-- 用了 elasticsearch 就要加这么一个，不然要com.sun.jna.Native 错误 -->
        <dependency>
            <groupId>com.sun.jna</groupId>
            <artifactId>jna</artifactId>
            <version>3.0.9</version>
        </dependency>

        <!-- thymeleaf legacyhtml5 模式支持 -->
        <dependency>
            <groupId>net.sourceforge.nekohtml</groupId>
            <artifactId>nekohtml</artifactId>
            <version>1.9.22</version>
        </dependency>
        <!-- 测试支持 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- tomcat的支持.-->
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <version>8.5.23</version>
        </dependency>
        <!-- mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.21</version>
        </dependency>

        <!-- junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version> 4.12</version>
        </dependency>
        <!-- commons-lang -->
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>2.6</version>
        </dependency>
        <!-- shiro -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.3.2</version>
        </dependency>
        <!-- hsqldb -->
        <dependency>
            <groupId>org.hsqldb</groupId>
            <artifactId>hsqldb</artifactId>
        </dependency>
    </dependencies>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

#### Category.java

在`java`目录下新建包`chenzf.tmall.pojo`。

@Entity：表示这是一个实体类
@Table(name = "category")：表示对应的表名是category
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })：
因为是做前后端分离，而前后端数据交互用的是json格式。 那么Category对象就会被转换为json数据。而本项目使用jpa来做实体类的持久化，jpa默认会使用hibernate，在jpa工作过程中，就会创造代理类来继承Category，并添加handler和hibernateLazyInitializer这两个无须json化的属性，所以这里需要**用JsonIgnoreProperties把这两个属性忽略掉**。


```java
package chenzf.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer" })

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    String name;

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
}
```

#### CategoryDAO.java

在`java`目录下新建包`chenzf.tmall.dao`。

CategoryDAO类继承了JpaRepository，就提供CRUD和分页的各种常见功能。这就是采用JPA方便的地方：
```java
package chenzf.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import chenzf.tmall.pojo.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
```

#### CategoryService.java

在`java`目录下新建包`chenzf.tmall.service`。

```java
package chenzf.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import chenzf.tmall.dao.CategoryDAO;
import chenzf.tmall.pojo.Category;

@Service  // 标记这个类是Service类
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;  // 自动装配上个步骤的CategoryDAO对象

    // 首先创建一个Sort对象，表示通过id倒排序，然后通过categoryDAO进行查询
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }
}
```

#### AdminPageController.java

后台管理页面跳转专用控制器。

因为是做前后端分离，所以数据是通过RESTFUL接口来取的，而在业务上，除了RESTFUL服务要提供，还要提供页面跳转服务，所以**所有的后台页面跳转都放在`AdminPageController`这个控制器里**。而RSTFUL专门放在Category对应的控制器`CategoryController.java`里面。

在`java`目录下新建包`chenzf.tmall.web`。

```java
package chenzf.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 表示这是一个控制器
public class AdminPageController { 
    // 访问地址admin，客户端就会跳转到admin_category_list去
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }
    
    // 访问地址admin_category_list就会访问admin/listCategory.html文件
    @GetMapping(value="/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }
}
```

#### CategoryController.java

这个是专门用来提供RESTFUL服务器控制器的。位于`java`目录下新建包`chenzf.tmall.web`中。

```java
package chenzf.tmall.web;

import chenzf.tmall.pojo.Category;
import chenzf.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 表示这是一个控制器，并且对每个方法的返回值都会直接转换为json数据格式
public class CategoryController {
    @Autowired CategoryService categoryService;  // 自动装配CategoryService

    // 对于categories访问，会获取所有的Category对象集合，并返回这个集合。 
    // 因为是声明为@RestController，所以这个集合，又会被自动转换为JSON数组抛给浏览器。
    @GetMapping("/categories")
    public List<Category> list() throws Exception {
        return categoryService.list();
    }
}
```

#### Application.java

启动类，代替自动生成的TmallSpringbootApplication.java，位于`java\chenzf\tmall\Application.java`：

```java
package chenzf.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### CORSConfiguration.java

配置类，用于允许所有的请求都跨域。位于`chenzf/tmall/config/CORSConfiguration.java`：

因为是二次请求，第一次是获取html页面，第二次通过html页面上的js代码异步获取数据，一旦部署到服务器就容易面临跨域请求问题，所以允许所有访问都跨域，就不会出现通过ajax获取数据获取不到的问题了。

```java
package chenzf.tmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
```

#### GlobalExceptionHandler.java

异常处理，主要是在处理删除父类信息的时候，因为外键约束的存在，而导致违反约束。

```java
package chenzf.tmall.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        Class constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
        if(null != e.getCause()  && constraintViolationException == e.getCause().getClass()) {
            return "违反了约束，多半是外键约束";
        }
        return e.getMessage();
    }

}
```

#### 使用JDBC插入数据

刚开始用的时候，数据库里是没有数据的。这里使用简单的JDBC代码插入10条数据。Springboot可以用JPA创建测试数据。

```java
package chenzf.tmall.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTmall {

    public static void main(String[] args) {
        //准备分类测试数据：

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_springboot?useUnicode=true&characterEncoding=utf8",
                        "root", "admin");
                Statement s = c.createStatement();
        )
        {
            for (int i = 1; i <=10 ; i++) {
                String sqlFormat = "insert into category values (null, '测试分类%d')";
                String sql = String.format(sqlFormat, i);
                s.execute(sql);
            }

            System.out.println("已经成功创建10条分类测试数据");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
```

#### 配置application.properties

```
#database
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/tmall_springboot?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=admin
#驱动
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
#表结构自动生成策略
spring.jpa.hibernate.ddl-auto = none

#thymeleaf
spring.thymeleaf.mode=LEGACYHTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
spring.thymeleaf.cache=false

#context
server.context-path=/tmall_springboot

#设置上传文件大小，默认只有1 m
spring.http.multipart.maxFileSize=100Mb
spring.http.multipart.maxRequestSize=100Mb

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

#显示 hibernate运行的 sql 语句
spring.jpa.show-sql=true
```

使用thymeleaf作为视图，这个是springboot官方推荐视图，它的好处是可以是纯html。其中LEGACYHTML5表示经典html5模式，即允许非严格的html出现，元素少点什么也可以编译通过，这个比较符合大家的编写习惯，太过严格的html，写起来累。`cache=false`表示不要缓存，以免在开发过程中因为停留在缓存而给开发人员带来困扰。

上下文地址为`tmall_springboot`，所以访问的时候，都要加上这个，比如：`http://127.0.0.1:8080/tmall_springboot/admin`。

JPA对实体类的默认字段会把驼峰命名的属性，转换为字段名的时候自动加上下划线。 这个配置的作用就是去掉下划线。比如属性名称是`createDate`，JPA默认转换为字段名`create_Date`。有了这个配置之后，就会转换为同名字段createDate。

`spring.jpa.show-sql=true`显示hibernate执行的sql语句。这个在上线之后，应该是关掉的，因为大量的控制台输出会严重影响系统性能。 但是呢，因为本项目会和redis和es整合，打印sql语句的目的是为了观察缓存是否起效果。


#### 静态资源

各种静态资源，诸如jquery、bootstrap、css、图片、公用html等。css、img、js目录是样式，图片脚本等文件，位于`D:\tmall\tmall_springboot\src\main\webapp`。 include/admin目录下是4个HTML 包含关系中讲解到的被包含文件，位于`D:\tmall\tmall_springboot\src\main\resources\templates\include\admin`。

#### listCategory.html

在templates下面新建admin目录`D:\tmall\tmall_springboot\src\main\resources\templates\admin`，然后新建listCategory.html文件。它完成了两件事：**获取数据**和 **展示数据**。

**获取数据**：

`$(function(){}`，这个是jquery的代码，表示当整个html加载好了之后执行。

```
var data4Vue = {
    uri:'categories',
    beans: []
};
```
vue用到的数据，uri表示访问哪个地址去获取数据，这里的值是categories，和 CategoryController.java相呼应。

```
var vue = new Vue({
    el: '#workingArea',
    data: data4Vue,
```
创建Vue对象，el表示和本页面的<div id="workingArea" > 元素绑定，data表示vue使用上面的data4Vue对象。

```
mounted:function(){
    this.list();
},
```
加载Vue对象成功之后会调用，成功的时候去调用list()函数。

```
methods: {
    list:function(){
        var url =  this.uri;
        axios.get(url).then(function(response) {
            vue.beans = response.data;
        });
    }
}
```

list函数使用data4Vue里的uri作为地址，然后调用axios.js这个ajax库，进行异步调用。调用成功之后，把服务端返回的数据，保存在vue.beans上。


**展示数据：**

`<tr v-for="bean in beans ">`，使用v-for进行遍历，这个beans就表示data4Vue里面的beans属性。

`<td>{{bean.id}}</td>`，bean就是遍历出来的每个id，这里就是输出每个分类的id。

`<a :href="'admin_property_list?cid=' + bean.id "><span class="glyphicon glyphicon-th-list"></span></a>`，在超链里的href里拼接分类id。



```HTML
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head th:include="include/admin/adminHeader::html('分类管理')" ></head>
<body>
<div th:replace="include/admin/adminNavigator::html" ></div>
<script>
    $(function(){
        var data4Vue = {
            uri:'categories',
            beans: [],
            bean: { id: 0, name: ''},
            pagination:{},
            file: null
        };

        //ViewModel
        var vue = new Vue({
            el: '#workingArea',
            data: data4Vue,
            mounted:function(){ //mounted　表示这个 Vue 对象加载成功了
                this.list(0);
            },
            methods: {
                list:function(start){
                    var url =  this.uri+ "?start="+start;
                    axios.get(url).then(function(response) {
                        vue.pagination = response.data;
                        vue.beans = response.data.content	;
                    });
                },
                add: function () {
                    if(!checkEmpty(this.bean.name, "分类名称"))
                        return;
                    if(!checkEmpty(this.file, "分类图片"))
                        return;
                    var url = this.uri;

                    //axios.js 上传文件要用 formData 这种方式
                    var formData = new FormData();
                    formData.append("image", this.file);
                    formData.append("name", this.bean.name);
                    axios.post(url,formData).then(function(response){
                        vue.list(0);
                        vue.bean = { id: 0, name: '', hp: '0'};
                        $("#categoryPic").val('');
                        vue.file = null;
                    });
                },
                deleteBean: function (id) {
                    if(!checkDeleteLink())
                        return;
                    var url = this.uri+"/"+id;
                    axios.delete(url).then(function(response){
                        if(0!=response.data.length){
                            alert(response.data);
                        }
                        else{
                            vue.list(0);
                        }
                    });
                },
                getFile: function (event) {
                    this.file = event.target.files[0];
                },
                jump: function(page){
                    jump(page,vue); //定义在adminHeader.html 中
                },
                jumpByNumber: function(start){
                    jumpByNumber(start,vue);
                }
            }
        });
    });

</script>
<div id="workingArea" >
    <h1 class="label label-info" >分类管理</h1>
    <br>
    <br>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>图片</th>
                <th>分类名称</th>
                <th>属性管理</th>
                <th>产品管理</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="bean in beans ">
                <td>{{bean.id}}</td>
                <td>
                    <img height="40px"  :src="'img/category/'+bean.id+'.jpg'">
                </td>
                <td>
                    {{bean.name}}
                </td>
                <td>
                    <a :href="'admin_property_list?cid=' + bean.id "><span class="glyphicon glyphicon-th-list"></span></a>
                </td>
                <td>
                    <a :href="'admin_product_list?cid=' + bean.id "><span class="glyphicon glyphicon-shopping-cart"></span></a>
                </td>
                <td>
                    <a :href="'admin_category_edit?id=' + bean.id "><span class="glyphicon glyphicon-edit"></span></a>
                </td>
                <td>
                    <a href="#nowhere"  @click="deleteBean(bean.id)"><span class="   glyphicon glyphicon-trash"></span></a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div th:replace="include/admin/adminPage::html" ></div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
            <table class="addTable">
                <tr>
                    <td>分类名称</td>
                    <td><input  @keyup.enter="add" v-model.trim="bean.name" type="text" class="form-control"></td>
                </tr>
                <tr>
                    <td>分类图片</td>
                    <td>
                        <input id="categoryPic" accept="image/*" type="file" name="image" @change="getFile($event)" />
                    </td>
                </tr>
                <tr class="submitTR">
                    <td colspan="2" align="center">
                        <a href="#nowhere"  @click="add" class="btn btn-success">提交</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div th:replace="include/admin/adminFooter::html" ></div>
</body>
</html>

```