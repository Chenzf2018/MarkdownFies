# SpringBoot发展
&emsp;&emsp;在“遥远”的EJB(Enterprise Java Bean)年代，开发一个EJB需要大量的接口和配置文件，直至EJB2.0的年代，开发一个EJB还需要配置两个文件，其结果就是<font color=red>配置的工作量比开发的工作量还要大</font>。其次<font color=red>EJB是运行在EJB容器中的</font>，而Sun公司定义的JSP和Serviet却是运行在Web容器中的，因此需要<font color=red>使用Web容器去调用EJB容器的服务</font>。

这就意味着存在以下的弊端：需要增加调用的配置文件才能让Web容器调用EJB容器；与此同时需要开发两个容器，非常多的配置内容和烦琐的规范导致开发效率十分低下；对于Web容器调用EJB容器的服务这种模式，注定了需要通过网络传递，造成性能不佳；对于测试人员还需要了解许多EJB烦琐的细节，才能进行配置和测试，这样测试也难以进行。

&emsp;&emsp;Spring以强大的<font color=red>控制反转(loC)来管理各类Java资源</font>，从而<u>降低了各种资源的藕合</u>；Spring的<font color=red>面向切面的编程(AOP)通过动态代理技术，允许我们按照约定进行配置编程</font>，进而增强了Bean的功能，它<u>擦除了大量重复的代码</u>。

&emsp;&emsp;配置方法的不成文共识：<font color=red>对于业务类使用注解</font>，例如，对于MVC开发，``控制器``使用`＠Controller`，``业务层``使用`＠Service`，``持久层``使用`＠Repository`；而<font color=red>对于一些公用的Bean</font>，例如，对于数据库(如Redis)、第三方资源等则<font color=red>使用XML进行配置</font>。

&emsp;&emsp;<font color=red>约定优于配置</font>，这是Spring Boot 的主导思想。

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

* 单击Generat下载项目压缩包。
* 解压后，使用IDEA导入项目，选择File | New | Model from Existing Source.. | 选择解压后的文件夹 | OK命令，选择 Maven，一路单击 Next 按钮，OK done!

## 使用IDEA构建
* 选择File | New | Project... 命令，弹出新建项目的对话框。
* 选择Spring Initializr | Next，也会出现上述类似的配置界面，IDEA帮我们做了集成。
* 填写相关内容后，单击Next按钮，选择依赖的包再单击Next按钮，最后确定信息无误单击Finish按钮。

&emsp;&emsp;首先是启动IntelliJ IDEA开发环境，然后选择`Create New Project`，就可以看到一个新的窗口。选择`Spring Initializr`，并且将JDK切换为安装的版本。
<div align=center><img src=SpringBootImages/创建新工程.png width=80%></div>

`Artifact`的命名规则需要进一步了解：不能出现大写字母！这里项目名为`hello-springboot`。

点击Next，到了可以选择starter(依赖)的窗口：
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

# 开发一个简单的Spring Boot项目
&emsp;&emsp;`Spring MVC`的<font color=red>视图解析器(ViewResolver)</font>的作用主要是<font color=red>定位视图</font>，也就是当控制器只是返回一个逻辑名称的时候，是没有办法直接对应找到视图的，这就需要视图解析器进行解析了。在实际的开发中最常用的视图之一就是JSP，例如，现在控制器中返回一个字符串“index”，那么我们希望它对应的是开发项目的／WEB-INF/jsp/index.jsp文件。

首先我们需要在Maven的`pom.xml`中加入`JSP`和`JSTL`的依赖包：
```xml
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
为了配置视图解析器(ViewResolver)，将application.properties文件修改为：
```
server.port=8090
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```
这里的`spring.mvc.view.prefix`和`spring.mvc.view.suffix`是Spring Boot与我们约定的视图前缀和后缀配置，意思是找到文件夹`／WEB-INF/jsp`下，以`.jsp`为后缀的JSP文件，那么前缀和后缀之间显然又缺了一个文件名称，在Spring MVC机制中，<font color=red>这个名称则是由控制器(Controller)给出的</font>，为此新建一个控制器`lndexController`：
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
这里的注解`@SpringBootApplication`标志着这是一个<font color=red>Spring Boot入门文件</font>。高亮的代码(第10行)则是<font color=red>以`HelloSpringbootApplication`类作为配置类来运行Spring Boot项目</font>。于是Spring Boot就会根据你在Maven加载的依赖来完成运行了。接下来我们以Java Application的形式运行类`HelloSpringbootApplication`，就可以看到`Tomcat`的运行日志。由于已经把端口修改为了8090，因此打开浏览器后输入[http://localhost:8090/index](http://localhost:8090/index)，就可以看到运行的结果：
<div align=center><img src=SpringBootImages/简单项目运行结果.png width=40%></div>

&emsp;&emsp;至此，Spring Boot的开发环境就搭建完成了！

# 全注解下的Spring Ioc
