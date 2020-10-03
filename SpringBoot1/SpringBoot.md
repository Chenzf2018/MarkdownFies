# 路线
javaSE：OOP；MySQL：持久层；HTML+CSS+JS+JQuery+框架：视图层(框架不熟练，CSS进阶难)；JavaWeb：独立开发MVC三层框架的网站(原始)；SSM：框架(简化开发流程，但配置复杂)，打War包，Tomcat运行；Spring再简化：Spring Boot(打Jar包(内嵌Tomcat)，微服务架构)。
<div align=center><img src=SpringBootImages/学习路线.png width=80%></div>

# SpringBoot发展
&emsp;&emsp;在“遥远”的EJB(Enterprise Java Bean)年代，开发一个EJB需要大量的接口和配置文件，直至EJB2.0的年代，开发一个EJB还需要配置两个文件，其结果就是<font color=red>配置的工作量比开发的工作量还要大</font>。其次<font color=red>EJB是运行在EJB容器中的</font>，而Sun公司定义的JSP和Serviet却是运行在Web容器中的，因此需要<font color=red>使用Web容器去调用EJB容器的服务</font>。

这就意味着存在以下的弊端：需要增加调用的配置文件才能让Web容器调用EJB容器；与此同时需要开发两个容器，非常多的配置内容和烦琐的规范导致开发效率十分低下；对于Web容器调用EJB容器的服务这种模式，注定了需要通过网络传递，造成性能不佳；对于测试人员还需要了解许多EJB烦琐的细节，才能进行配置和测试，这样测试也难以进行。

&emsp;&emsp;Spring是为了解决企业级应用开发的复杂性而创建，它以强大的<font color=red>控制反转(loC)来管理各类Java资源</font>，从而<u>降低了各种资源的藕合</u>；Spring的<font color=red>面向切面的编程(AOP)通过动态代理技术，允许我们按照约定进行配置编程</font>，进而增强了Bean的功能，它<u>擦除了大量重复的代码</u>。

配置方法的不成文共识：<font color=red>对于业务类使用注解</font>，例如，对于MVC开发，``控制器``使用`＠Controller`，``业务层``使用`＠Service`，``持久层``使用`＠Repository`；而<font color=red>对于一些公用的Bean</font>，例如，对于数据库(如Redis)、第三方资源等则<font color=red>使用XML进行配置</font>。

为了降低Java开发的复杂性，Spring采用了以下4种关键策略来简化开发：
* 基于POJO的轻量级和最小侵入性编程；
* <font color=red>通过IOC，依赖注入(DI)和面向接口实现松耦合</font>；
* <font color=red>基于切面(AOP)和惯例进行声明式编程</font>；
* 通过切面和模版减少样式代码；

&emsp;&emsp;Spring Boot使用“<font color=red>约定优于配置</font>”(项目中存在大量的配置，此外还内置一个习惯性的配置，无须手动进行配置)的理念让项目快速运行起来。使用Spring Boot很容易创建一个独立运行(运行jar，内嵌Servlet容器)、准生产级别的基于Spring框架的项目，使用Spring Boot可以不用或者只需要很少的Spring配置。

Spring Boot的核心功能有：
* 独立运行的Spring项目：Spring Boot可以以`jar`包的形式独立运行，运行一个Spring Boot项目只需通过`java -jar xx.jar`来运行。
* 内嵌Servlet容器：Spring Boot可选择内嵌`Tomcat、Jetty`或者`Undertow`，这样就无须以`war`包形式部署项目。
* 提供`starter`简化Maven配置：Spring提供了一系列的`starter pom`来简化Maven的依赖加载。例如，当使用了`spring-boot-starter-web`时，会自动加入依赖包。
* <font color=red>自动配置</font>Spring：Spring Boot会根据在类路径中的`jar包、类`，<font color=red>为jar包里的类自动配置Bean</font>，这样会极大地减少要使用的配置；若在实际开发中需要自动配置Bean，而Spring Boot没有提供支持，则可以自定义自动配置；<font color=red>针对很多Spring应用程序常见的应用功能，Spring Boot能自动提供相关配置。自动配置消除了传统Spring应用程序里的很多样板配置</font>。
* <font color=red>起步依赖</font>：告诉Spring Boot需要什么功能，它就能引入需要的库。Spring Boot起步依赖让你能<font color=red>通过库所提供的功能而非名称与版本号来指定构建依赖。</font>
* 准生产的应用监控：Spring Boot提供基于`http, ssh, telnet`对运行时的项目进行监控。
* <font color=red>无冗余代码生成和xml配置</font>：Spring Boot不是借助于代码生成来实现的，而是通过条件注解来实现的。不需要任何`xml`配置即可实现Spring的所有配置。

# 搭建开发环境
&emsp;&emsp;使用Spring Boot开发项目需要有<font color=red>两个基础环境和一个开发工具</font>，这两个环境是指<font color=red>Java编译环境和构建工具环境</font>，一个开发工具是指IDE开发工具。Spring Boot 2.0要求Java 8作为最低版本，需要在本机安装JDK1.8并进行环境变量配置，同时需要安装构建工具编译Spring Boot项目，最后准备一个顺手的IDE开发工具即可。

&emsp;&emsp;<font color=red>构建工具是一个把源代码生成可执行应用程序的自动化工具</font>，Java领域中主要有三大构建工具：Ant、Maven和Gradle。
* Ant(AnotherNeatTool)的核心是由Java编写，采用XML作为构建脚本，这样就允许你在任何环境下运行构建。Ant是Java领域最早的构建工具，不过因为操作复杂，慢慢的已经被淘汰了。
* Maven，Maven发布于2004年，目的是解决程序员使用Ant所带来的一些问题，它的好处在于可以将项目过程规范化、自动化、高效化以及强大的可扩展性。
* Gradle，Gradle是一个基于Apache Ant和Apache Maven概念的项目自动化建构工具。它使用一种基于Groovy的特定领域语言来声明项目设置，而不是传统的XML。结合了前两者的优点，在此基础之上做了很多改进，它具有Ant的强大和灵活，又有Maven的生命周期管理且易于使用。

Spring Boot官方支持Maven和Gradle作为项目构建工具。Gradle虽然有更好的理念，但是相比Maven来讲其行业使用率偏低，并且Spring Boot官方默认使用Maven。

## 构建项目
&emsp;&emsp;有两种方式来构建Spring Boot项目基础框架，第一种是使用Spring官方提供的构建页面；第二种是使用IntelliJ IDEA中的Spring插件来创建。

### 使用Spring官方提供页面构建
* 访问 [http://start.spring.io](http://start.spring.io/)网址。
* 选择构建工具Maven Project，编程语言选择Java、Spring Boot版本以及一些工程基本信息，具体可参考下图：
<div align=center><img src=SpringBootImages/网页构建.png></div>

* 单击Generat下载项目压缩包，<font color=red>此处生成的是一个简单的基于Maven的项目</font>。
* 解压后，使用IDEA导入项目，选择File | New | Model from Existing Source.. | 选择解压后的文件夹 | OK命令，选择 Maven，一路单击 Next 按钮，OK done!

## 使用IDEA构建
* 选择File | New | Project... 命令，弹出新建项目的对话框。
* 选择Spring Initializr | Next，也会出现上述类似的配置界面，IDEA帮我们做了集成。
* 填写相关内容后，单击Next按钮，选择依赖的包再单击Next按钮，最后确定信息无误单击Finish按钮。

&emsp;&emsp;首先是启动IntelliJ IDEA开发环境，然后选择`Create New Project`，就可以看到一个新的窗口。选择`Spring Initializr`，并且将JDK切换为安装的版本。
<div align=center><img src=SpringBootImages/创建新工程.png width=80%></div>

`Artifact`的命名规则需要进一步了解：不能出现大写字母！这里项目名为`hello-springboot`。

点击Next，到了可以选择starter(依赖)的窗口。当这些技术的`starter pom`被选中后，与这项技术相关的Spring的Bean将会被自动配置：
<div align=center><img src=SpringBootImages/项目添加依赖.png width=80%></div>

接着需要导入Maven工程/设置Maven：
<div align=center><img src=SpringBootImages/Maven设置.png width=80%></div>

运行`HelloSpringBootApplication`就可以启动Spring Boot工程，而`pom.xml`则配置好了选中的`starter`依赖，这样就能够基于IntelliJ IDEA开发Spring Boot工程了。

## 项目结构
&emsp;&emsp;Spring Boot的基础结构共三个文件，具体如下：
<div align=center><img src=SpringBootImages/SpringBoot项目结构.png></div>

*	`src/main/java`：程序开发以及主程序入口；
*	`src/main/resources`：配置文件；
*	`src/test/java`：测试程序。

&emsp;&emsp;另外，Spring Boot建议的目录结构如下：
`com.example.myproject`目录下：
<div align=left><img src=SpringBootImages/myproject项目结构.png width=40%></div>

*	`Application.java`建议放到根目录下面，是项目的启动类，Spring Boot项目只能有一个main()方法；
*	`comm`目录建议放置公共的类，如全局的配置文件、工具类等；
*	`model`目录主要用于实体(Entity)与数据访问层(Repository)；
*	`repository`层主要是数据库访问层代码；
*	`service`层主要是业务类代码；
*	`web`层负责页面访问控制。

`resources`目录下：
*	`static`目录存放web访问的静态资源，如js、css、图片等；
*	`templates`目录存放页面模板；
*	`application.properties`存放项目的配置信息。

`test`目录存放单元测试的代码；`pom.xml`用于配置项目依赖包，以及其他配置。

<font color=red>采用默认配置可以省去很多设置</font>，也可以根据公司的规范进行修改，至此一个 Java项目搭建好了！


## 使用自定义配置
&emsp;&emsp;在没有任何配置下就能用Spring Boot启动Spring MVC项目，这些都是<font color=red>Spring Boot通过Maven依赖找到对应的jar包和嵌入的服务器，然后使用默认自动配置类来创建默认的开发环境</font>。但是有时候，我们需要对这些默认的环境进行修改以适应个性化的要求。

通过约定的配置就可以在很大程度上自定义开发环境，以适应真实需求。这就是Spring Boot的理念，<font color=red>配置尽量简单并且存在约定</font>，屏蔽Spring内部的细节，使得Spring能够开箱后经过简单的配置后即可让开发者使用，以满足快速开发、部署和测试的需要。

&emsp;&emsp;可以在结构目录`/src/main/resources`中找到一个属性文件`application.properties`，它是一个默认的配置文件，通过它可以根据自己的需要实现自定义功能。

例如，假设当前`8080`端口已经被占用，我们希望使用`8090`端口启动`Tomcat`，那么只需要在这个文件中添加一行：`server.port=8090`。
```xml {.line-numbers highlight=1}
2020-02-16 19:30:27.812  INFO 13440 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8090 (http) with context path ''
2020-02-16 19:30:27.815  INFO 13440 --- [  restartedMain] c.e.h.HelloSpringbootApplication         : Started HelloSpringbootApplication in 1.632 seconds (JVM running for 2.427)
2020-02-16 19:31:21.079  INFO 13440 --- [nio-8090-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-02-16 19:31:21.080  INFO 13440 --- [nio-8090-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-02-16 19:31:21.087  INFO 13440 --- [nio-8090-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 7 ms
```

&emsp;&emsp;Spring Boot的参数配置除了使用`properties`文件之外，还可以使用`yml`文件等。实际上，`yml文件`的配置与`proerties文件`只是简写和缩进区别，差异并不大。

## 运行程序
&emsp;&emsp;在`HelloWorldApplication.java`同级下创建`controller`目录：
<div align=center><img src=SpringBootImages/controller.png width=50%></div>

添加`HelloWorldController`：
```java
package com.example.hello_world.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController
{
    //接口：http://localhost:8080/HelloWorld
    @RequestMapping("/HelloWorld")
    // 调用业务，接受前端的参数！
    public String helloWorld(){ return "Hello World!"; }
}
```
<div align=center><img src=SpringBootImages/HelloWorld.png width=50%></div>

`pom.xml`中添加的`web`依赖：
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
集成了`Tomcat, DispatcherServlet, xml`等，毋需再去配置！`spring-boot-starter-web`用于实现`Http`接口（该依赖中包含了`Spring MVC`）；使用`Tomcat`作为默认嵌入式容器。

所有的`SpringBoot`依赖都是使用`spring-boost-starter`作为开头的：`spring-boot-starter-web, spring-boot-starter-test(单元测试)`。

# 全注解下的Spring Ioc
&emsp;&emsp;Spring最成功的是其提出的理念，而不是技术本身。它所依赖的<font color=red>两个核心理念，一个是控制反转(Inversion of Control, IoC)，另一个是面向切面编程(Aspect Oriented Programming, AOP)</font>。

IoC容器是Spring的核心，可以说<font color=red>Spring是一种基于IoC容器编程的框架</font>。因为Spring Boot是基于注解的开发Spring IoC，所以使用全注解的方式讲述Spring IoC技术。<font color=red>IoC是一种通过描述来生成或者获取对象的技术</font>，而这个技术不是Spring甚至不是Java独有的。对于Java初学者更多的时候所熟悉的是使用new关键字来创建对象，而在Spring中则不是，它是<font color=red>通过描述来创建对象</font>。只是<font color=red>Spring Boot并不建议使用XML，而是通过注解的描述生成对象</font>。

一个系统可以生成各种对象，并且这些对象都需要进行管理。还值得一提的是，对象之间并不是孤立的，它们之间还可能<font color=red>存在依赖的关系</font>。例如，一个班级是由多个老师和同学组成的，那么班级就依赖于多个老师和同学了。为此Spring还提供了<font color=red>依赖注入的功能</font>，使得我们能够<font color=red>通过描述来管理各个对象之间的关系。为了描述上述的班级、同学和老师这3个对象关系，我们需要一个容器</font>。

&emsp;&emsp;总结一下就是：Spring使用简单的POJO(Plain Old Java Object, 即无任何限制的普通Java对象)来进行企业级开发。<font color=red>每一个被Spring管理的Java对象都称之为Bean；而Spring提供了一个IoC容器用来初始化对象，解决对象间的依赖管理和对象的使用。控制反转是通过依赖注入实现的。所谓依赖注入指的是容器负责创建对象和维护对象间的依赖关系，而不是通过对象本身负责自己的创建和解决自己的依赖。</font>

## IoC容器介绍
&emsp;&emsp;<font color=red>在Spring中把每一个需要管理的对象称为Spring Bean(简称Bean)，而Spring管理这些Bean的容器，被我们称为Spring IoC容器(或者简称IoC容器)</font>。IoC容器需要具备两个基本的功能：
* 通过描述管理Bean，包括发布和获取Bean；
* 通过描述完成Bean之间的依赖关系。

Spring IoC容器(ApplicationContext)负责创建Bean，并通过容器将功能类Bean注入到需要的Bean中。Spring提供使用xml、注解、Java配置、groovy配置实现Bean的创建和注入。

&emsp;&emsp;Spring IoC容器是一个管理Bean的容器，在Spring的定义中，它要求所有的IoC 容器都需要实现接口`BeanFactory`，它是一个顶级容器接口。在Spring IoC容器中，允许我们通过`getBean`按``类型``或者``名称``获取Bean。并且，默认的情况下，Bean都是<font color=red>以单例存在的</font>，也就是<font color=red>使用`getBean`方法返回的都是同一个对象</font>。

由于`BeanFactory`的功能还不够强大，因此Spring在`BeanFactory`的基础上，还设计了一个更为高级的接口`ApplicationContext`。它是`BeanFactory`的子接口之一，在Spring的体系中BeanFactory和ApplicationContext是最为重要的接口设计，在现实中我们使用的大部分Spring IoC容器是`ApplicationContext`接口的实现类，它们的关系如下图所示：
<div align=center><img src=SpringBootImages/SpringIoC容器的接口设计.png></div>

&emsp;&emsp;在Spring Boot中，主要是<font color=red>通过注解来装配Bean到Spring IoC容器中</font>，AnnotationConfigApplicationContext是一个基于注解的IoC容器。<font color=red>Spring Boot装配和获取Bean</font>的方法与它如出一辙。

## 依赖注入
&emsp;&emsp;<font color=red>Spring IoC通过依赖注入(Dependency Injection, DI)来获取Bean，然后再将Bean装配到IoC容器中。控制反转是通过依赖注入实现的。所谓依赖注入指的是容器负责创建对象和维护对象间的依赖关系，而不是通过对象本身负责自己的创建和解决自己的依赖。</font>

&emsp;&emsp;依赖注入的主要目的是为了解耦，体现了一种“组合”的理念。如果希望类具备某项功能的时候，是继承自一个具有此功能的父类好呢？还是组合另外一个具有这个功能的类好呢？答案是不言而喻的，继承一个父类，子类将与父类耦合，组合另外一个类则使耦合度大大降低。

可以通过人类依赖于动物的例子来理解“依赖注入”：人类(Person)可以利用一些动物(Animal)去完成一些事情，比方说狗(Dog)是用来看门的，猫(Cat)是用来抓老鼠的……于是做一些事情就依赖于那些动物。

# AOP
&emsp;&emsp;Spring的AOP的存在目的是为了<font color=red>解耦</font>。AOP可以让一组类共享相同的行为。在OOP中只能通过继承类和实现接口，来使代码的耦合度增强，且类继承只能为单继承，阻碍更多行为添加到一组类上，AOP弥补了OOP的不足。

&emsp;&emsp;对于约定编程， 首先需要记住的是约定的流程是什么，然后就可以完成对应的任务，却不需要知道底层设计者是怎么将约定的内容织入对应的流程中的。只要按照一定的规则，就可以将代码织入事先约定的流程中。实际上Spring AOP也是一种约定流程的编程，在Spring中可以使用多种方式配置AOP，但Spring Boot采用了注解的方式。

AOP可以<font color=red>减少大量重复的工作</font>。获取数据库事务连接、事务操控和关闭数据库连接的过程，都需要使用大量的try...catch...finally...语句去操作，这显然存在大量重复的工作。因为这里存在着一个默认的流程，所以利用AOP可以替换这些没有必要重复的工作。

&emsp;&emsp;使用Spring AOP可以处理一些无法使用OOP实现的业务逻辑。其次，通过约定，可以将一些业务逻辑织入流程中，并且可以将一些通用的逻辑抽取出来，然后给予默认实现，这样你只需要完成部分的功能就可以了，这样做可以使得开发者的代码更加简短，同时可维护性也得到提高。

# Spring MVC
&emsp;&emsp;<font color=red>MVC：Model + View + Controller (数据模型＋视图＋控制器)。三层架构：Presentation tier + Application tier + Data tier (展现层＋应用层＋数据访问层)。</font>

那`MVC`和`三层架构`有什么关系呢？

<font color=red>MVC只存在三层架构的展现层(`SSM`：展现层：Spring MVC；应用层：Spring；数据访问层：Mybatis)</font>，`MVC`中`M`是数据模型，是包含数据的对象。在Spring MVC里，有一个专门的类叫`Model`, 用来和`V`之间的数据交互、传值；`V`指的是视图页面，包含`JSP, freeMarker, Velocity, Thymeleaf, Tile`等；`C`是控制器(Spring MVC的注解`@Controller`的类)。

三层架构是整个应用的架构，是由Spring框架负责管理的。一般项目结构中都有Service层、DAO层，这两个反馈在应用层和数据访问层。

# 开发一个简单的Spring Boot项目
&emsp;&emsp;`Spring MVC`的<font color=red>视图解析器(ViewResolver)</font>的作用主要是<font color=red>定位视图</font>，也就是当控制器只是返回一个逻辑名称的时候，是没有办法直接对应找到视图的，这就需要视图解析器进行解析了。在实际的开发中最常用的视图之一就是JSP，例如，现在控制器中返回一个字符串“index”，那么我们希望它对应的是开发项目的/WEB-INF/jsp/index.jsp文件。

首先我们需要在Maven的`pom.xml`中加入`JSP`和`JSTL`的依赖包：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-jasper</artifactId>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>jstl</artifactId>
  <scope>provided</scope>
</dependency>
```
<font color=red>添加了`spring-boot-starter-web`依赖，会自动添加`Tomcat`和`Spring MVC`的依赖，那么Spring Boot会对`Tomcat`和`Spring MVC`进行自动配置。</font>

为了配置视图解析器(ViewResolver)，将application.properties文件修改为：
```
server.port=8090
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```
这里的`spring.mvc.view.prefix`和`spring.mvc.view.suffix`是Spring Boot与我们约定的视图前缀和后缀配置，意思是找到文件夹`／WEB-INF/jsp`下，以`.jsp`为后缀的JSP文件。前缀和后缀之间显然缺了一个文件名称，在Spring MVC机制中，<font color=red>这个名称则是由控制器(Controller)给出的</font>，为此新建一个控制器`lndexController`：
```java
package com.example.hellospringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
    @RequestMapping("/index")
    public String index() { return "index"; }
}
```

还需要定义一个映射为`/index`的路径，然后方法返回“index”，这样它就与之前配置的前缀和后缀结合起来找对应的jsp文件。因此开发一个对应的jsp文件，先建一个`/webapp/WEB-INF/jsp/index.jsp`文件(开发视图)：
```js
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring boot 视图解析器</title>
  </head>
  <body>
    <h1>测试视图解析器</h1>
  </body>
</html>
```
<div align=center><img src=SpringBootImages/jsp文件路径.png width=70%></div>

这样就完成了一个简单的<font color=red>控制器</font>，并且<font color=red>让视图解析器找到视图的功能</font>。从上面来看<font color=red>定义视图解析器，在Spring Boot中只需要通过配置文件定义视图解析器的前后缀即可，而无须任何代码，这是因为Spring Boot 给了我们自定义配置项，它会读入这些自定义的配置项，为我们生成Spring MVC中的视图解析器</font>。正如它所承诺的尽可能地配置Spring 开发环境，然后再看到即将运行HelloSpringbootApplication.java文件：
```java {.line-numbers highlight=10}
package com.example.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringbootApplication
{
    public static void main(String[] args)
    {SpringApplication.run(HelloSpringbootApplication.class, args);}
}
```
这里的注解`@SpringBootApplication`标志着这是一个<font color=red>Spring Boot入门文件</font>，主要目的是开启自动配置。而`main方法`主要作用是作为项目启动的入口。高亮的代码(第10行)则是<font color=red>以`HelloSpringbootApplication`类作为配置类来运行Spring Boot项目</font>。于是Spring Boot就会根据你在Maven加载的依赖来完成运行了。接下来我们以Java Application的形式运行类`HelloSpringbootApplication`，就可以看到`Tomcat`的运行日志。由于已经把端口修改为了8090，因此打开浏览器后输入[http://localhost:8090](http://localhost:8090)，就可以看到运行的结果：
<div align=center><img src=SpringBootImages/简单项目运行结果.png width=40%></div>

&emsp;&emsp;至此，Spring Boot的开发环境就搭建完成了！

## 运行原理探究
&emsp;&emsp;上述项目到底是怎么运行的呢，我们来看`pom.xml`文件，其中，它主要依赖一个父项目：
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.2.4.RELEASE</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
```
进入父项目：`D:\WinSoftware\Maven\repository\org\springframework\boot\spring-boot-starter-parent\2.2.4.RELEASE\spring-boot-starter-parent-2.2.4.RELEASE.pom`
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.2.4.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```  
这里才是真正管理SpringBoot应用里面所有依赖版本的地方，SpringBoot的版本控制中心；以后我们导入依赖默认是不需要写版本；但是如果导入的包没有在依赖中管理着就需要手动配置版本了。

### 启动器
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
`springboot-boot-starter`是`SpringBoot`的场景启动器；`spring-boot-starter-web `帮我们导入了web模块正常运行所依赖的组件；

<font color=red>SpringBoot将所有的功能场景都抽取出来，做成一个个的starter(启动器)，只需要在项目中引入这些starter即可，所有相关的依赖都会导入进来，我们要用什么功能就找到对应的场景启动器即可。</font>

### 主程序
```java
package com.example.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 标注一个主程序类 ， 说明这是一个Spring Boot应用
public class HelloSpringbootApplication
{
    public static void main(String[] args)
    {
        //将SpringBoot应用启动起来
        SpringApplication.run(HelloSpringbootApplication.class, args);
    }
}
```

### 自动装配原理
#### `@SpringBootApplication`
&emsp;&emsp;SpringBoot应用标注在某个类上说明这个类是SpringBoot的主配置类， SpringBoot就应该运行这个类的`main`方法来启动SpringBoot应用；

进入这个注解：可以看到上面还有很多其他注解：
```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
  @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
	@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {}
```
##### `@ComponentScan`
&emsp;&emsp;这个注解在Spring中很重要，它对应XML配置中的元素。<font color=red>`@ComponentScan`的功能就是自动扫描并加载符合条件的组件或者bean，将这个bean定义加载到IOC容器中。</font>

##### `@SpringBootConfiguration`
&emsp;&emsp;这是SpringBoot的配置类，标注在某个类上，表示这是一个SpringBoot的配置类：
```java
// 进入@SpringBootConfiguration这个注释
@Configuration
public @interface SpringBootConfiguration {}

// 进入@Configuration这个注释
@Component
public @interface Configuration {}
```
`@Configuration`：配置类上来标注这个注解，说明这是一个配置类，配置类即配置文件；我们继续点进去，发现配置类也是容器中的一个组件。`@Component`，这就说明，启动类本身也是Spring中的一个组件而已，负责启动应用！

##### `@EnableAutoConfiguration`
&emsp;&emsp;`@EnableAutoConfiguration`用来<font color=red>开启自动配置功能</font>。以前我们需要自己配置的东西，而现在SpringBoot可以自动帮我们配置；`@EnableAutoConfiguration`告诉SpringBoot开启自动配置功能，这样自动配置才能生效。
```java
// 进入@EnableAutoConfiguration注释
@AutoConfigurationPackage  // 自动配置包
@Import(AutoConfigurationImportSelector.class)  // 导入哪些组件的选择器
public @interface EnableAutoConfiguration {}

// 进入@AutoConfigurationPackage这个注释
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}
```
Spring底层注解`@import`，给容器中导入一个组件。`Registrar.class`将主配置类(即@SpringBootApplication标注的类)的所在包及包下面所有子包里面的所有组件扫描到Spring容器。

`@Import({AutoConfigurationImportSelector.class})`给容器导入组件；`AutoConfigurationImportSelector`：自动配置导入选择器，那么它会导入哪些组件的选择器呢？点击这个类看源码(`Ctrl + B`)：
```java
// 获得候选的配置
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes)
{
    //这里的getSpringFactoriesLoaderFactoryClass()方法返回的就是我们最开始看的启动自动导入配置文件的注解类；EnableAutoConfiguration
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```
这个方法又调用了`SpringFactoriesLoader`类的静态方法！进入`SpringFactoriesLoader`类`loadFactoryNames()`方法：
```java
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader)
{
    String factoryTypeName = factoryType.getName();
    //这里它又调用了 loadSpringFactories 方法
    return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
}
```  
继续点击查看`loadSpringFactories`方法：
```java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader)
{
    //获得classLoader，我们返回可以看到这里得到的就是EnableAutoConfiguration标注的类本身
    MultiValueMap<String, String> result = cache.get(classLoader);
    if (result != null) {return result;}
    try
    {
      //去获取一个资源 "META-INF/spring.factories"
      Enumeration<URL> urls = (classLoader != null ?
        classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
        ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
      result = new LinkedMultiValueMap<>();

      //将读取到的资源遍历，封装成为一个Properties
      while (urls.hasMoreElements())
      {
        URL url = urls.nextElement();
        UrlResource resource = new UrlResource(url);
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        for (Map.Entry<?, ?> entry : properties.entrySet())
        {
          String factoryTypeName = ((String) entry.getKey()).trim();
          for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue()))
          {result.add(factoryTypeName, factoryImplementationName.trim());}
        }
      }
      cache.put(classLoader, result);
      return result;
    }
    catch (IOException ex)
    {
      throw new IllegalArgumentException("Unable to load factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
    }
}
```
根据源头打开`spring.factories`的配置文件，看到了很多自动配置的文件；这就是自动配置根源所在`D:\WinSoftware\Maven\repository\org\springframework\boot\spring-boot-autoconfigure\2.2.4.RELEASE\spring-boot-autoconfigure-2.2.4.RELEASE.jar\spring.factories`。

所以，自动配置真正实现是从`classpath`中搜寻所有的`META-INF/spring.factories`配置文件 ，并将其中对应的`org.springframework.boot.autoconfigure`包下的配置项，通过反射实例化为对应标注了`@Configuration`的`JavaConfig`形式的IOC容器配置类，然后将这些都汇总成为一个实例并加载到IOC容器中。

`SpringBoot`所有自动配置都在启动的时候扫描并加载。`spring.factories`所有的自动配置类都在这里，但不一定生效。只要导入了对应的`starter`，就有对应的启动器，就会自动装配。

#### 小结
* SpringBoot在启动的时候从类路径下的`META-INF/spring.factories`中获取`EnableAutoConfiguration`指定的值，将这些值作为自动配置类导入容器，自动配置类就生效，帮我们进行自动配置工作。以前我们需要自己配置的东西，自动配置类都帮我们解决了。
* 整个J2EE的整体解决方案和自动配置都在`springboot-autoconfigure`的jar包中。它将所有需要导入的组件以全类名的方式返回，这些组件就会被添加到容器中；
* 它会给容器中导入非常多的自动配置类(`xxxAutoConfiguration`), 就是给容器中导入这个场景需要的所有组件，并配置好这些组件；
* 有了自动配置类，免去了我们手动编写配置注入功能组件等的工作。


### `SpringApplication.run`
&emsp;&emsp;分析该方法主要分两部分，一部分是`SpringApplication`的实例化，二是`run方法`的执行。

&emsp;&emsp;`SpringApplication`这个类主要做了以下四件事情：
1. 推断应用的类型是普通的项目还是`Web`项目；
2. 查找并加载所有可用初始化器，设置到`initializers`属性中；
3. 找出所有的应用程序监听器，设置到`listeners`属性中；
4. 推断并设置`main`方法的定义类，找到运行的主类。

#### `run方法`流程分析
<div align=center><img src=SpringBootImages/run方法.png></div>

# ReadList项目
&emsp;&emsp;先初始化一个项目。从技术角度来看，要用`Spring MVC`来处理Web请求，用`Thymeleaf`来定义Web视图，用`Spring Data JPA`来把阅读列表持久化到嵌入式的`H2`数据库里。用IDEA构建项目时，可以用`Initializr`勾选`Web, Thymeleaf, JPA, H2`这几个复选框。

## 目录结构
&emsp;&emsp;构建完成后，会得到一个Maven项目结构：
<div align=center><img src=SpringBootImages/readinglist项目结构.png width=40%></div>

整个项目结构遵循Maven项目的布局，即主要应用程序代码位于`src/main/java`目录里，资源都在`src/main/resources`目录里，测试代码则在`src/test/java`目录里。此刻还没有测试资源，但如果有的话，要放在`src/test/resources`里。
* `ReadingListApplication.java`：应用程序的启动引导类(bootstrap class)，也是主要的Spring配置类。
* `application.properties`：用于配置应用程序和Spring Boot的属性。
* `ReadingListApplicationTests.java`：一个基本的集成测试类。

### 启动引导Spring
&emsp;&emsp;`ReadingListApplication.java`在Spring Boot应用程序里有两个作用：配置和启动引导。这是主要的Spring配置类。
```java
package com.example.readinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication  // 开启组件扫描和自动配置
public class ReadingListApplication extends WebMvcConfigurerAdapter
{
    public static void main(String[] args)
    {
        // 负责启动引导应用程序
        SpringApplication.run(ReadingListApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addRedirectViewController("/", "/readingList");
    }
}

```
`@SpringBootApplication`开启了Spring的组件扫描和Spring Boot的自动配置功能。实际
上，`@SpringBootApplication`将三个有用的注解组合在了一起：
* Spring的`@Configuration`：标明该类使用Spring基于Java的配置。
* Spring的`@ComponentScan`：启用组件扫描，这样Web控制器类和其他组件才能被自动发现并注册为Spring应用程序上下文里的Bean。一个简单的Spring MVC控制器，使用`@Controller`进行注解，这样组件扫描才能找到它。
* Spring Boot的`@EnableAutoConfiguration`：这个注解也可以称为`@Abracadabra`，就是这一行配置开启了Spring Boot自动配置的魔力，让你不用再写成篇的配置了。

`ReadingListApplication`还是一个启动引导类。要运行Spring Boot应用程序有几种方式，其中包含传统的`WAR`文件部署。但这里的`main()方法`让你可以在命令行里把该应用程序当作一个可执行`JAR`文件来运行。这里向`SpringApplication.run()`传递了一个`ReadingListApplication`类的引用，还有命令行参数，通过这些东西启动应用程序。

应用程序此时能正常运行，启动一个监听`8080`端口的`Tomcat`服务器。可以用浏览器访问[http://localhost:8080](http://localhost:8080)，但由于还没写控制器类，会看到错误页面。

几乎不需要修改`ReadingListApplication.java`。如果应用程序需要Spring Boot自动配置以外的其他Spring配置，一般来说，最好把它写到一个单独的`@Configuration`标注的类里(组件扫描会发现并使用这些类的)。简单的情况下，可以把自定义配置加入`ReadingListApplication.java`。

### 配置应用程序属性
&emsp;&emsp;`Initializr`为你生成的`application.properties`文件是一个空文件。实际上，这个文件完全是可选的。但该文件可以很方便地帮你细粒度地调整Spring Boot的自动配置。还可以用它来指定应用程序代码所需的配置项。完全不用告诉Spring Boot为你加载`application.properties`，只要它存在就会被加载，Spring和应用程序代码都能获取其中的属性。

## Spring Boot项目构建过程解析
&emsp;&emsp;Spring Boot为Gradle和Maven提供了构建插件，以便辅助构建Spring Boot项目。要是选择用Maven来构建应用程序，`Initializr`会替你生成一个`pom.xml`文件，其中使用了Spring Boot的Maven插件。

从`spring-boot-starter-parent`继承版本号：
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```
`<dependencies>...</dependencies>`插入<font color=red>起步依赖</font>；`<build><plugins>...</plugins></build>`插入运用的Spring Boot<font color=red>插件</font>。

构建插件的主要功能是把项目打包成一个可执行的超级`JAR(uber-JAR)`，包括把应用程序的
所有依赖打入JAR文件内，并为JAR添加一个描述文件，其中的内容能让你用`java -jar`来运行应用程序。

除了构建插件，Maven构建说明中还将`spring-boot-starter-parent`作为上一级，这样就能<font color=red>利用Maven的依赖管理功能，继承很多常用库的依赖版本，在声明依赖时就不用再去指定版本号了。</font>`pom.xml`里的`<dependency>`都没有指定版本。

五个依赖中，除了手工添加的H2之外，其他的`Artifact ID`都有`spring-boot-starter-`前缀。这些都是Spring Boot<font color=red>起步依赖</font>，它们都有助于Spring Boot应用程序的构建。

### 使用起步依赖
&emsp;&emsp;要理解Spring Boot起步依赖带来的好处，先让我们假设它们尚不存在。如果没用Spring Boot的话，你会向项目里添加哪些依赖呢？要用Spring MVC的话，你需要哪个Spring依赖？你还记得Thymeleaf的Group和Artifact ID吗？你应该用哪个版本的Spring Data JPA呢？它们放在一起兼容吗？

如果没有Spring Boot起步依赖，你就有不少功课要做。而你想要做的只不过是<font color=red>开发一个Spring Web应用程序，使用Thymeleaf视图，通过JPA进行数据持久化</font>。但在开始编写第一行代码之前，你得搞明白，要支持你的计划，需要往构建说明里加入哪些东西。

&emsp;&emsp;<font color=red>Spring Boot通过提供众多起步依赖降低项目依赖的复杂度。起步依赖本质上是一个Maven项目对象模型(Project Object Model，POM)，定义了对其他库的传递依赖，这些东西加在一起即支持某项功能。</font>

很多起步依赖的命名都暗示了它们提供的某种或某类功能。举例来说，你打算把这个阅读列表应用程序做成一个Web应用程序。与其向项目的构建文件里添加一堆单独的库依赖，还不如声明这是一个Web应用程序来得简单。你<font color=red>只要添加Spring Boot的Web起步依赖就好了。</font>我们还想以Thymeleaf为Web视图，用JPA来实现数据持久化，因此在构建文件里还需要Thymeleaf和Spring Data JPA的起步依赖。<font color=red>为了能进行测试，我们还需要能在Spring Boot上下文里运行集成测试的库，因此要添加SpringBoot的test起步依赖，这是一个测试时依赖。</font>统统放在一起，就有了这五个依赖，也就是Initializr在Maven的构建文件里提供的：
```xml {.line-numbers}
<artifactId>spring-boot-starter-data-jpa</artifactId>
<artifactId>spring-boot-starter-thymeleaf</artifactId>
<artifactId>spring-boot-starter-web</artifactId>
<artifactId>h2</artifactId>
<artifactId>spring-boot-starter-test</artifactId>
```

我们并不需要指定版本号，起步依赖本身的版本是由正在使用的Spring Boot的版本来决定的，而起步依赖则会决定它们引入的传递依赖的版本。Spring Boot能确保引入的全部依赖都能相互兼容。

大部分情况下，你都无需关心每个Spring Boot起步依赖分别声明了些什么东西。Web起步依赖能让你构建Web应用程序，Thymeleaf起步依赖能让你用Thymeleaf模板，Spring Data JPA起步依赖能让你用Spring Data JPA将数据持久化到数据库里，通常只要知道这些就足够了。

### 使用自动配置
&emsp;&emsp;<font color=red>Spring Boot的自动配置是一个运行时(更准确地说，是应用程序启动时)的过程，考虑了众多因素，才决定Spring配置应该用哪个，不该用哪个。</font>举几个例子，下面这些情况都是Spring Boot的自动配置要考虑的。
* Spring的JdbcTemplate是不是在Classpath里？如果是，并且有DataSource的Bean，则自动配置一个JdbcTemplate的Bean。
* Thymeleaf是不是在Classpath里？如果是，则配置Thymeleaf的模板解析器、视图解析器以及模板引擎。
* Spring Security是不是在Classpath里？如果是，则进行一个非常基本的Web安全设置。

每当应用程序启动的时候，Spring Boot的自动配置都要做将近200个这样的决定，涵盖安全、集成、持久化、Web开发等诸多方面。所有这些自动配置就是为了尽量不让你自己写配置。

## 开发应用程序功能
&emsp;&emsp;既然知道Spring Boot会替我们料理这些事情，那么与其浪费时间讨论这些Spring配置，还不如看看如何利用Spring Boot的自动配置，让我们专注于应用程序代码。

### 定义领域模型
 &emsp;&emsp;我们应用程序里的核心领域概念是读者阅读列表上的书。因此我们需要定义一个实体类来表示这个概念(如何定义一本书：表示列表里的书的Book类)：
```java
package com.example.readinglist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String reader;
    private String isbn;
    private String title;
    private String author;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReader() { return reader; }
    public void setReader(String reader) { this.reader = reader; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```
`Book`类就是简单的Java对象，其中有些描述书的属性，还有必要的访问方法。`@Entity`注解表明它是一个JPA实体，id属性加了`@Id`和`@GeneratedValue`注解，说明这个字段是实体的唯一标识，并且这个字段的值是自动生成的。

### 定义仓库接口
&emsp;&emsp;接下来，我们就要定义用于把Book对象持久化到数据库的仓库了。因为用了Spring Data JPA，所以我们要做的就是简单地定义一个接口，扩展一下Spring Data JPA的`JpaRepository`接口：
```java
package com.example.readinglist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book, Long>
{
    List<Book> findByReader(String reader);
}
```
通过扩展`JpaRepository`，`ReadingListRepository`直接继承了18个执行常用持久化操作的方法。`JpaRepository`是个泛型接口，有两个参数：仓库操作的领域对象类型，及其`ID`属性的类型。此外，我还增加了一个`findByReader()`方法，可以根据读者的用户名来查找阅读列表。如果你好奇谁来实现这个`ReadingListRepository`及其继承的18个方法，请不用担心，`Spring Data`提供了很神奇的魔法，<font color=red>只需定义仓库接口，在应用程序启动后，该接口在运行时会自动实现</font>。

### 创建Web界面
&emsp;&emsp;现在，我们定义好了应用程序的领域模型，还有把领域对象持久化到数据库里的仓库接口，剩下的就是创建Web前端了。作为阅读列表应用程序前端的Spring MVC控制器(`ReadingListController`)就能为应用程序处理HTTP请求：
```java
package com.example.readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/readingList")
public class ReadingListController
{
    private static final String reader = "craig";
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository)
    {
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String readersBooks(Model model)
    {
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToReadingList(Book book)
    {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList";
    }
}
```
`ReadingListController`使用了`@Controller`注解，这样组件扫描会自动将其注册为Spring应用程序上下文里的一个Bean。它还用了`@RequestMapping`注解，将其中所有的处理器方法都映射到了“/”这个URL路径上。

该控制器有两个方法。
* `readersBooks()`：处理`/{readerList}`上的`HTTP GET`请求，根据路径里指定的读者，从(通过控制器的构造器注入的)仓库获取Book列表。随后将这个列表塞入模型，用的键是`books`，最后返回`readingList`作为呈现模型的视图逻辑名称。
* `addToReadingList()`：处理`/{readerList}`上的`HTTP POST`请求，将请求正文里的数据绑定到一个Book对象上。该方法把Book对象的`reader`属性设置为读者的姓名，随后通过仓库的`save()`方法保存修改后的Book对象，最后重定向到`/{readerList}`(控制器中的另一个方法会处理该请求)。

`readersBooks()`方法最后返回`readingList`作为逻辑视图名，为此必须创建该视图。因为在项目开始之初我就决定要用`Thymeleaf`来定义应用程序的视图，所以接下来就在`src/main/resources/templates`里创建一个名为`readingList.html`的文件；为了美观，`Thymeleaf`模板引用了一个名为`style.css`的样式文件，该文件位于`src/main/resources/static`目录中。

### 运行应用程序
&emsp;&emsp;运行应用程序，此时启动一个监听`8080`端口的`Tomcat`服务器。可以用浏览器访问[http://localhost:8080](http://localhost:8080)，结果如下图所示：
<div align=center><img src=SpringBootImages/readinglist项目运行结果.png width=70%></div>

### 将项目打成jar包
&emsp;&emsp;将项目打成jar包后，就可以在任何地方运行了：
<div align=center><img src=SpringBootImages/将项目打成jar包.png></div>


## 小结
&emsp;&emsp;通过Spring Boot的起步依赖和自动配置，你可以更加快速、便捷地开发Spring应用程序。起步依赖帮助你专注于应用程序需要的功能类型，而非提供该功能的具体库和版本。与此同时，自动配置把你从样板式的配置中解放了出来。这些配置在没有Spring Boot的Spring应用程序里非常常见。
