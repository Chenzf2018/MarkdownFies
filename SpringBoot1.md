# 基础

在学习SSM(H)的过程中，需要做大量的**配置工作**，其实很多配置行为本身只是手段，并不是目的。基于这个考虑，把该简化的简化，该省略的省略，开发人员只用关心提供**业务功能**就行了，这就是 SpringBoot。换言之，SpringBoot可以简单地看成简化了的、按照约定开发的SSM(H)，开发速度大大提升。

**环境版本：**
- JDK8 32版本：`java version "1.8.0_65"`
- SpringBoot版本：`1.5.9.RELEASE`

先用IDEA导入Maven项目：`File->New->Project from existing sources`找到指定的`pom.xml`文件。然后运行`SpringbootApplication`；接着访问地址：`http://127.0.0.1:8080/hello`。

这是一个web程序，但启动方式无需`tomcat`。因为`SpringbootApplication.java`类的主方法把`tomcat`嵌入进去了，不需要手动启动`tomcat`了。

## 创建项目

在IDEA中开发Springboot应用本质上是一个Maven项目，可以使用IDEA自带的`SpringBoot插件`来开发。

**创建项目**

<div align=center><img src=SpringBoot1\创建项目.png></div>

**项目参数：**

<div align=center><img src=SpringBoot1\项目参数.png></div>

**项目依赖：**

<div align=center><img src=SpringBoot1\项目依赖.png></div>

这里`SpringBoot`版本与[教程](https://how2j.cn/k/springboot/springboot-idea/1641.html#nowhere)推荐版本不一致，可能是一个隐患！

更改`SpringBoot`版本：`import changes`
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>1.5.9.RELEASE</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
```

**指定项目路径：**

<div align=center><img src=SpringBoot1\项目路径.png></div>

项目创建好之后，就自带一个`Application`，其被`@SpringBootApplication`所标记，表示这个是一个Springboot应用。


**HelloController.java**：

新建包`sjtu.hello_springboot.web`，然后在其下新建类`HelloController`。这个类就是`Spring MVC`里的一个普通的**控制器**。

`@RestController`是`spring4`里新注解，是`@ResponseBody`和`@Controller`的缩写。

```java
package sjtu.hello_springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/Hello")
    public String hello() {
        return "Hello SpringBoot";
    }
}
```


**运行并测试：**

`程序包org.junit.jupiter.api不存在`
**高版本测试类：**
```java
package sjtu.hello_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloSpringbootApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

**解决方案一：**

在`pom.xml`中添加
```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <version>5.5.2</version>
  <scope>test</scope>
</dependency>
```

**解决方案二：**

[将测试类改成](https://www.cnblogs.com/suhaha/p/12050040.html)：
```java
package sjtu.hello_springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloSpringbootApplicationTests {

    @Test
    public void contextLoads() {
    }
}
```

输入：`http://127.0.0.1:8080/Hello`。


## 运行原理

SpringBoot简单的`Hello SpringBoot`代码，就两个文件`HelloController.java`和`HelloSpringBootApplication.java`，运行`HelloSpringBootApplication.java`就可以跑起来一个简单的`RESTFul Web`服务器了。

但是问题来了，在Application的`main`方法里，压根没有任何地方引用`HelloController`类，那么它的代码又是如何被服务器调用起来的呢？

这就需要深入到`SpringApplication.run()`方法中看个究竟了。不过即使不看代码，我们也很容易有这样的猜想，SpringBoot肯定是在某个地方**扫描了当前的package，将带有`RestController`注解的类作为`MVC`层的`Controller`自动注册进了`Tomcat Server`**。

### 注解传递机制

`HelloController`没有被代码引用，它是如何注册到`Tomcat`服务中去的？

它靠的是**注解传递机制**，那注解是又是如何传递的呢？

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@ComponentScan
public @interface SpringBootApplication {
...
}

public @interface ComponentScan {
    String[] basePackages() default {};
}
```
首先`main`方法可以看到的注解是`SpringBootApplication`，这个注解又是由`ComponentScan`注解来定义的。`ComponentScan`注解会定义一个被扫描的包名称，如果没有显示定义那就是**当前的包路径**。

SpringBoot在遇到`ComponentScan`注解时会扫描对应包路径下面的所有Class，根据这些Class上标注的其它注解继续进行后续处理。当它扫到`HelloController`类时发现它标注了`RestController`注解。

```java
@RestController
public class HelloController {
...
}

@Controller
public @interface RestController {
}
```

而`RestController`注解又标注了`Controller`注解。SpringBoot对`Controller`注解进行了特殊处理，它会将`Controller`注解的类当成`URL`处理器注册到`Servlet`的请求处理器中，在创建`Tomcat Server`时，会将请求处理器传递进去。`HelloController`就是如此被自动装配进`Tomcat`的。


## 部署jar方式

Springboot本质上是一个Java应用程序，那么又如何部署呢？

通常来说，Springboot部署会采用两种方式：全部打包成一个jar，或者打包成一个war。

```
C:\Users\Chenzf>d:

D:\>cd D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot

D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot>mvn install
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building hello_springboot 0.0.1-SNAPSHOT
......
[INFO] Installing D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot\target\hello_springboot-0.0.1-SNAPSHOT.jar to D:\WinSoftware\Maven\repository\sjtu\hello_springboot\0.0.1-SNAPSHOT\hello_springboot-0.0.1-SNAPSHOT.jar       
...
[INFO] BUILD SUCCESS
...
```
在该目录下生成了一个`target`目录，里面含有`hello_springboot-0.0.1-SNAPSHOT.jar`。


**运行jar：**
`D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot>java -jar target/hello_springboot-0.0.1-SNAPSHOT.jar`

就启动这个jar了。通过这种方式，把此jar上传到服务器并运行，就可达到部署的效果了。输入：`http://127.0.0.1:8080/Hello`。


## 热部署

目前的Springboot，当发生了任何修改之后，**必须关闭后再启动Application类才能够生效**，显得略微麻烦。 

Springboot提供了热部署的方式，**当发现任何类发生了改变，马上通过JVM类加载的方式，加载最新的类到虚拟机中**。这样就不需要重新启动也能看到修改后的效果了。

在pom.xml中新增加一个依赖和一个插件就行了：

**依赖：**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional> <!-- 这个需要为 true 热部署才有效 -->
</dependency>
```

**插件：**

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

**设置IDEA：**
`File -> Settings -> Default Settings -> Build -> Compiler`，然后勾选`Build project automatically`。

组合键：`Shift+Ctrl+Alt+/`，选择`Registry`，选中打勾`compiler.automake.allow.when.app.running`。

**开启热部署策略：**
<div align=center><img src=SpringBoot1\热部署.png></div>

修改`Controller`中语句后，Ctrl+s保存一下，就能看到重新启动了。

**浏览器**：
更新页面！


# thymeleaf

## 入门

Thymeleaf是一种**模板语言**，可以达到和JSP一样的效果，但是比起JSP对于前端测试更加友好，毕竟JSP需要运行起来才能看到效果，而Thymeleaf本身就是html格式，**无需服务器也能看到效果**，这样前端测试就方便多了。

thymeleaf可以配合Servlet运行，但是比较常见的是配合springboot来运行。当然本质上也是配合springboot里的 springmvc来运行的。

**修改pom.xml，增加了对thymeleaf的支持**：

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-thymeleaf</artifactId>	
</dependency>
```

**增加控制器：**
访问地址`/HelloThymeleaf`，跳转到`helloThymeleaf.html`，并带上数据`name`，其值是`thymeleaf`：

```java {.line-numbers highlight=12}
package sjtu.hello_springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/HelloThymeleaf")
    public String hello(Model model) {
            model.addAttribute("name", "thymeleaf");
            return "helloThymeleaf";  // 要和helloThymeleaf.html同名
    }
}
```

**在resources的templates目录下新建文件helloThymeleaf.html**：
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <p th:text="${name}" >name</p>
    <p th:text="'Hello ' + ${name} + ' !'" >hello world</p>
    <p th:text="|Hello ${name} !|" >hello world</p>
</body>
</html>
```

`helloThymeleaf.html`做了如下事情：
1. 声明当前文件是`thymeleaf`，里面可以用`th`开头的属性：`<html xmlns:th="http://www.thymeleaf.org">`

2. 把name的值显示在当前p里，用的是th开头的属性：`th:text`，而取值用的是`${name}`。这样取出来放进p里，从而替换到原来p标签里的4个字符`name`。
  用这种方式，就可以把服务端的数据，显示在当前`html`里了。 
  重要的是：这种写法是完全合法的`html`语法，所以可以直接通过浏览器打开`hello.html`，也是可以看到效果的。只不过看到的是 "name"，而不是服务端传过来的值"thymeleaf"。

3. 字符串拼写。 
  两种方式，一种是用加号，一种是在前后放上`||`。显然第二种方式可读性更好：
  `<p th:text="'Hello ' + ${name} + '!'">hello world</p>`
  `<p th:text="|Hello ${name}!|">hello world</p>`
  这两种方式都会得到：`hello thymeleaf`。



**application.properties：**
```
#thymeleaf 配置
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.content-type=text/html
#缓存设置为false, 这样修改之后马上生效，便于调试
spring.thymeleaf.cache=false
#上下文
server.context-path=/thymeleaf
```

运行Application，然后访问地址：`http://127.0.0.1:8080/thymeleaf/HelloThymeleaf`。


## URL

效果：可以看到一个`js`的对话框，以及灰色的边框效果。这两个效果是通过`@URL`外部引用`css`文件和`js`文件得到的。
<div align=center><img src=SpringBoot1\ThymeleafURL.png width=60%></div>

**css文件**

在`webapp`目录下新建`css`目录，然后新建`style.css`文件(`D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot\src\main\webapp\static\css\style.css`)：

```css
div.showing{
    width:80%;
    margin:20px auto;
    border:1px solid grey;
    padding:30px;
}

.even{
    background-color: red;
}

.odd{
    background-color: green;
}
```

**js文件：**

在`webapp`目录下新建`js`目录，然后新建`thymeleaf.js`文件(`D:\Learning_Java\SpringBoot\SpringBootProjects\HelloSpringBoot\src\main\webapp\static\js\thymeleaf.js`)：

```js
function testFunction(){
    alert("test Thymeleaf.js!");
}
```

**修改helloThymeleaf.html文件：**
```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" media="all" href="../../webapp/static/css/style.css" th:href="@{/static/css/style.css}"/>
    <script type="text/javascript" src="../../webapp/static/js/thymeleaf.js" th:src="@{/static/js/thymeleaf.js}"></script>

    <!-- 访问thymeleaf.js里的 testFunction函数 -->
    <script>
        testFunction();
    </script>

</head>

<body>
    <div class="showing">
        <p th:text="${name}" >name</p>
        <p th:text="'Hello ' + ${name} + '!'" >hello world</p>
        <p th:text="|Hello ${name} !|" >hello world</p>
    </div>
</body>

</html>
```

通过`th:href="@{/static/css/style.css}"`和`th:src="@{/static/js/thymeleaf.js}"`引入`css`和`js`文件。


