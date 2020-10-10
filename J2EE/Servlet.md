# 基础

Servlet本身不能独立运行，需要在一个web应用中运行的。而一个web应用是部署在tomcat中的。所以开发一个servlet需要如下几个步骤：
- 创建web应用项目
- 编写servlet代码
- 部署到tomcat中
  
## 创建项目

首先创建一个工程
<div align=center><img src=Pictures\创建项目.jpg></div>

在`WEB-INF`目录下新建：`classes`和`lib`目录。


## 导入'servlet-api.jar'包

<div align=center><img src=Pictures\导入jar包.jpg></div>

## 编写`HelloServlet.java`：

HelloServlet继承了`HttpServlet`并且提供了一个`doGet`方法。后续测试该类的时候，会直接在浏览器输入地址`http://127.0.01/hello`。在浏览器中输入地址提交数据的方式是GET，所以该Servlet需要提供一个对应的doGet方法。在doGet中，输出html，分别是`标题元素h1`和`当前时间的字符串`。

```java
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("<h1>Hello Servlet!</h1>");
            response.getWriter().println(new Date().toLocaleString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 指定输出目录

<div align=center><img src=Pictures\指定输出目录.jpg></div>

把项目的class文件输出由原来的`out`设置到`web/WEB-INF/classes`下。

tomcat启动之后，在默认情况下，不会去out目录找这些class文件，而是到WEB-INF/classes这个目录下去寻找。

## 配置web.xml

创建目录web，接着再创建目录`web/WEB-INF`，然后在WEB-INF目录中创建web.xml：`D:\Learning_Java\Java_Code\J2EE\web\WEB-INF\web.xml`

**web.xml提供路径与servlet的映射关系**，把/hello这个路径，映射到HelloServlet这个类上。

`<servlet>`标签下的`<servlet-name>`与`<servlet-mapping>`标签下的`<servlet-name>`必须一样。`<servlet-name>`与`<servlet-class>`可以不一样，但是为了便于理解与维护，一般都会写的一样。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

<servlet>
    <servlet-name>HelloServlet</servlet-name>
    <servlet-class>HelloServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>HelloServlet</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>

</web-app>
```

## 配置Tomcat

点击`run`中的`Edit Configurations`：

<div align=center><img src=Pictures\配置Tomcat.jpg></div>

也许看不到`Tomcat Server`，点击`34items more(irrelevant)`，里面有`Tomcat Server`。

**指定Tomcat路径**：

<div align=center><img src=Pictures\指定Tomcat路径.jpg></div>

**设置部署：**
点击Artifact，自动生成j2ee.war。

<div align=center><img src=Pictures\设置部署.jpg></div>

## 运行并测试

输入`http://127.0.0.1:8090/hello`。